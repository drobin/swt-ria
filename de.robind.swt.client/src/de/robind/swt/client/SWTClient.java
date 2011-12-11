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
import de.robind.swt.msg.SWTRegRequest;
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

    SWTEventListenerFactory listenerFactory = new SWTEventListenerFactory(
        future.getChannel(), messageFactory);

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
          objMap.put(((SWTNewRequest)request).getObjId(), display);
          response = messageFactory.createNewResponse();
        } else {
          response =
              handleRequest(messageFactory, listenerFactory, objMap, request);
        }

        Channels.write(future.getChannel(), response);
      }
    }

    display.dispose();
    future.getChannel().close();

    future.getChannel().getCloseFuture().awaitUninterruptibly();
    channelFactory.releaseExternalResources();
  }

  private static SWTResponse handleRequest(SWTMessageFactory messageFactory,
      SWTEventListenerFactory listenerFactory, SWTObjectMap objMap,
      SWTRequest request) {

    if (request instanceof SWTNewRequest) {
      return (handleNewRequest(messageFactory, objMap, (SWTNewRequest)request));
    } else if (request instanceof SWTCallRequest) {
      return (handleCallRequest(messageFactory, objMap, (SWTCallRequest)request));
    } else if (request instanceof SWTRegRequest) {
      return (handleRegRequest(messageFactory, listenerFactory, objMap,
          (SWTRegRequest)request));
    } else {
      return (null);
    }
  }

  private static SWTResponse handleNewRequest(SWTMessageFactory factory,
      SWTObjectMap objMap, SWTNewRequest request) {

    try {
      SWTObject.createObject(objMap, request.getObjId(), request.getObjClass(),
          request.getArguments());

      return (factory.createNewResponse());
    } catch (InvocationTargetException e) {
      return (factory.createException(e.getCause()));
    } catch (Exception e) {
      return factory.createException(e);
    }
  }

  private static SWTResponse handleCallRequest(SWTMessageFactory factory,
      SWTObjectMap objMap, SWTCallRequest request) {

    try {
      Object result = SWTObject.callMethod(objMap, request.getObjId(),
          request.getMethod(), request.getArguments());

      return (factory.createCallResponse(result));
    } catch (InvocationTargetException e) {
      return (factory.createException(e.getCause()));
    } catch (Exception e) {
      return (factory.createException(e));
    }
  }

  private static SWTResponse handleRegRequest(SWTMessageFactory messageFactory,
      SWTEventListenerFactory listenerFactory,
      SWTObjectMap objMap, SWTRegRequest request) {

    try {
      SWTObject.registerEvent(objMap, listenerFactory, request.getObjId(),
          request.getEventType(), request.enable());
      return (messageFactory.createRegResponse());
    } catch (Exception e) {
      return (messageFactory.createException(e));
    }
  }
}
