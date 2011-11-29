package de.robind.swt.protocol.tests.encoder;

import static de.robind.swt.protocol.tests.CauseMatcher.causeClass;
import static de.robind.swt.protocol.tests.CauseMatcher.causeMsg;

import org.jboss.netty.handler.codec.embedder.EncoderEmbedder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.protocol.SWTMessageEncoder;
import de.robind.swt.protocol.SWTProtocolException;

public class GeneralTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void wrongMesssageType() {
    SWTMessageEncoder encoder = new SWTMessageEncoder();
    EncoderEmbedder<Object> embedder = new EncoderEmbedder<Object>(encoder);

    exception.expect(causeClass(SWTProtocolException.class));
    exception.expect(causeMsg("Invalid message-class: java.lang.Integer"));

    embedder.offer(new Integer(4711));
    embedder.poll();
  }

}
