package de.robind.swt.client;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

import de.robind.swt.msg.SWTMessageFactory;
import de.robind.swt.protocol.SWTMessageDecoder;
import de.robind.swt.protocol.SWTMessageEncoder;

/**
 * SWTClient pipeline-factory.
 * <p>
 * Creates the pipeline for the client-application.
 *
 * @author Robin Doer
 */
public class PipelineFactory implements ChannelPipelineFactory {
  /**
   * The environment of the client
   */
  private SWTClientEnvironment env = null;

  /**
   * Creates a new {@link PipelineFactory}.
   *
   * @param env The environment of the client
   */
  public PipelineFactory(SWTClientEnvironment env) {
    this.env = env;
  }

  /* (non-Javadoc)
   * @see org.jboss.netty.channel.ChannelPipelineFactory#getPipeline()
   */
  public ChannelPipeline getPipeline() throws Exception {
    ChannelPipeline pipeline = Channels.pipeline();
    SWTMessageFactory factory = new SWTMessageFactory();

    pipeline.addLast("decoder", new SWTMessageDecoder(factory));
    pipeline.addLast("encoder", new SWTMessageEncoder());
    pipeline.addLast("application", new SWTClientHandler(this.env));

    return (pipeline);
  }
}
