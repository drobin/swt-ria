package de.robind.swt.protocol.datatypes.tests;

import static org.hamcrest.core.Is.is;
import static org.jboss.netty.buffer.ChannelBuffers.dynamicBuffer;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.protocol.SWTProtocolException;
import de.robind.swt.protocol.datatype.SWTNull;

public class SWTNullTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void readNullNullBuffer() throws SWTProtocolException {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTNull.readNull(null);
  }

  @Test
  public void readNullUnexpectedType() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Unexpected type read from buffer. Expected 5, but read 0");

    SWTNull.readNull(buffer);
  }

  @Test
  public void readNullUnexpectedFlags() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(5);
    buffer.writeByte(1);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Unexpected flags read from buffer. Expected 0, but read 1");

    SWTNull.readNull(buffer);
  }

  @Test
  public void readNullSuccess() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(5);
    buffer.writeByte(0);

    SWTNull.readNull(buffer);
  }

  @Test
  public void writeNullNullBuffer() {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTNull.writeNull(null);
  }

  @Test
  public void writeNullSuccess() {
    ChannelBuffer buffer = dynamicBuffer();
    SWTNull.writeNull(buffer);

    assertThat(buffer.readByte(), is((byte)5));
    assertThat(buffer.readByte(), is((byte)0));
  }
}
