package de.robind.swt.server;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

import de.robind.swt.msg.SWTMessageFactory;
import de.robind.swt.protocol.SWTMessageDecoder;
import de.robind.swt.protocol.SWTMessageEncoder;

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
    ChannelPipeline pipeline = Channels.pipeline();
    SWTMessageFactory factory = new SWTMessageFactory();

    pipeline.addLast("encoder", new SWTMessageEncoder());
    pipeline.addLast("decoder", new SWTMessageDecoder(factory));
    pipeline.addLast("application", new SWTServerHandler(factory));

    return (pipeline);
  }
}
