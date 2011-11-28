package de.robind.swt.client;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class SWTClient {
  public static void main(String[] args) {
    Display display = new Display();

    Shell shell = new Shell(display);
    shell.open();

    SWTClientEnvironment env = new SWTClientEnvironment(display);

    SWTObjectMap objMap = new SWTObjectMap();

    ChannelFactory factory = new NioClientSocketChannelFactory(
        Executors.newCachedThreadPool(),
        Executors.newCachedThreadPool());

    ClientBootstrap bootstrap = new ClientBootstrap(factory);
    bootstrap.setPipelineFactory(new PipelineFactory(env));
    bootstrap.setOption("tcpNoDelay", true);
    bootstrap.setOption("keepAlive", true);

    ChannelFuture future =
        bootstrap.connect(new InetSocketAddress("localhost", 4711));
    future.awaitUninterruptibly();

    while (future.getChannel().isConnected() && !shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }

    display.dispose();
    future.getChannel().close();

    future.getChannel().getCloseFuture().awaitUninterruptibly();
    factory.releaseExternalResources();
  }
}
