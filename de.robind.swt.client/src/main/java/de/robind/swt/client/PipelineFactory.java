package de.robind.swt.client;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

import de.robind.swt.msg.SWTMessage;
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
   * Factory used to create {@link SWTMessage}-instances
   */
  private SWTMessageFactory factory = null;

  /**
   * Creates a new {@link PipelineFactory}.
   *
   * @param env The environment of the client
   * @param factory used to create {@link SWTMessage}-instances
   */
  public PipelineFactory(SWTClientEnvironment env, SWTMessageFactory factory) {
    this.env = env;
    this.factory = factory;
  }

  /* (non-Javadoc)
   * @see org.jboss.netty.channel.ChannelPipelineFactory#getPipeline()
   */
  public ChannelPipeline getPipeline() throws Exception {
    ChannelPipeline pipeline = Channels.pipeline();

    pipeline.addLast("decoder", new SWTMessageDecoder(this.factory));
    pipeline.addLast("encoder", new SWTMessageEncoder());
    pipeline.addLast("application", new SWTClientHandler(this.env));

    return (pipeline);
  }
}
