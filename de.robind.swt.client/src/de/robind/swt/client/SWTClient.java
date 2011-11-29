package de.robind.swt.client;

import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import de.robind.swt.msg.SWTCallRequest;
import de.robind.swt.msg.SWTMessageFactory;
import de.robind.swt.msg.SWTNewRequest;
import de.robind.swt.msg.SWTNewResponse;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;

public class SWTClient {
  public static void main(String[] args) throws Exception {
    Display display = new Display();

    Shell shell = new Shell(display);
    shell.open();

    SWTClientEnvironment env = new SWTClientEnvironment(display);

    SWTObjectMap objMap = new SWTObjectMap();
    SWTMessageFactory messageFactory = new SWTMessageFactory();

    ChannelFactory channelFactory = new NioClientSocketChannelFactory(
        Executors.newCachedThreadPool(),
        Executors.newCachedThreadPool());

    ClientBootstrap bootstrap = new ClientBootstrap(channelFactory);
    bootstrap.setPipelineFactory(new PipelineFactory(env, messageFactory));
    bootstrap.setOption("tcpNoDelay", true);
    bootstrap.setOption("keepAlive", true);

    ChannelFuture future =
        bootstrap.connect(new InetSocketAddress("localhost", 4711));
    future.awaitUninterruptibly();

    while (future.getChannel().isConnected() && !shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }

      SWTRequest request;
      while ((request = env.requestQueue.poll()) != null) {
        SWTResponse response = null;

        if (request instanceof SWTNewRequest &&
            ((SWTNewRequest)request).getObjClass().equals(Display.class)) {

          // Creating a Display is a special task because you already have a
          // Display. But we assign the object-id to our display.
          objMap.put(((SWTNewRequest)request).getId(), display);
          response = SWTNewResponse.success();
        } else {
          response = handleRequest(messageFactory, objMap, request);
        }

        Channels.write(future.getChannel(), response);
      }
    }

    display.dispose();
    future.getChannel().close();

    future.getChannel().getCloseFuture().awaitUninterruptibly();
    channelFactory.releaseExternalResources();
  }

  private static SWTResponse handleRequest(SWTMessageFactory factory,
      SWTObjectMap objMap, SWTRequest request) {

    if (request instanceof SWTNewRequest) {
      return (handleNewRequest(objMap, (SWTNewRequest)request));
    } else if (request instanceof SWTCallRequest) {
      return (handleCallRequest(factory, objMap, (SWTCallRequest)request));
    } else {
      return (null);
    }
  }

  private static SWTNewResponse handleNewRequest(
      SWTObjectMap objMap, SWTNewRequest request) {

    try {
      SWTObject.createObject(objMap, request.getId(), request.getObjClass(),
          request.getArguments());

      return (SWTNewResponse.success());
    } catch (InvocationTargetException e) {
      Throwable cause = e.getCause();
      return (new SWTNewResponse(
          cause.getClass().getName(), cause.getMessage()));
    } catch (Exception e) {
      return (new SWTNewResponse(e.getClass().getName(), e.getMessage()));
    }
  }

  private static SWTResponse handleCallRequest(SWTMessageFactory factory,
      SWTObjectMap objMap, SWTCallRequest request) {

    try {
      Object result = SWTObject.callMethod(objMap,
          request.getDestinationObject().getId(),
          request.getMethod(), request.getArguments());

      return (factory.createCallResponse(result));
    } catch (InvocationTargetException e) {
      return (factory.createException(e.getCause()));
    } catch (Exception e) {
      return (factory.createException(e));
    }
  }
}
