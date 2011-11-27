package de.robind.swt.server;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * SWTServer pipeline-factory.
 * <p>
 * Creates the pipeline for the server-application.
 *
 * @author Robin Doer
 */
public class PipelineFactory implements ChannelPipelineFactory {
  /* (non-Javadoc)
   * @see org.jboss.netty.channel.ChannelPipelineFactory#getPipeline()
   */
  public ChannelPipeline getPipeline() throws Exception {
    return (Channels.pipeline());
  }
}
