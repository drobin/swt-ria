package de.robind.swt.client;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

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
  /* (non-Javadoc)
   * @see org.jboss.netty.channel.ChannelPipelineFactory#getPipeline()
   */
  public ChannelPipeline getPipeline() throws Exception {
    ChannelPipeline pipeline = Channels.pipeline();

    pipeline.addLast("decoder", new SWTMessageDecoder());
    pipeline.addLast("encoder", new SWTMessageEncoder());

    return (pipeline);
  }
}
