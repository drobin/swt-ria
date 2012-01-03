package de.robind.swt.protocol.datatypes.tests;

import static org.hamcrest.core.Is.is;
import static org.jboss.netty.buffer.ChannelBuffers.dynamicBuffer;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.protocol.SWTProtocolException;
import de.robind.swt.protocol.datatype.SWTByte;

public class SWTByteTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void readByteNullBuffer() throws SWTProtocolException {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTByte.readByte(null);
  }

  @Test
  public void readByteUnexpectedType() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Unexpected type read from buffer. Expected 3, but read 0");

    SWTByte.readByte(buffer);
  }

  @Test
  public void readByteUnexpectedFlags() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(3);
    buffer.writeByte(1);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Unexpected flags read from buffer. Expected 0, but read 1");

    SWTByte.readByte(buffer);
  }

  @Test
  public void readByteNegative() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(3);
    buffer.writeByte(0);
    buffer.writeByte(-1);

    assertThat(SWTByte.readByte(buffer), is((byte)-1));
  }

  @Test
  public void readByteNull() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(3);
    buffer.writeByte(0);
    buffer.writeByte(0);

    assertThat(SWTByte.readByte(buffer), is((byte)0));
  }

  @Test
  public void readBytePositive() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(3);
    buffer.writeByte(0);
    buffer.writeByte(1);

    assertThat(SWTByte.readByte(buffer), is((byte)1));
  }

  @Test
  public void writeByteNullBuffer() {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTByte.writeByte(null, (byte)0);
  }

  @Test
  public void writeByteNegative() {
    ChannelBuffer buffer = dynamicBuffer();
    SWTByte.writeByte(buffer, (byte)-1);

    assertThat(buffer.readByte(), is((byte)3));
    assertThat(buffer.readByte(), is((byte)0));
    assertThat(buffer.readByte(), is((byte)-1));
  }

  @Test
  public void writeByteNull() {
    ChannelBuffer buffer = dynamicBuffer();
    SWTByte.writeByte(buffer, (byte)0);

    assertThat(buffer.readByte(), is((byte)3));
    assertThat(buffer.readByte(), is((byte)0));
    assertThat(buffer.readByte(), is((byte)0));
  }

  @Test
  public void writeBytePositive() {
    ChannelBuffer buffer = dynamicBuffer();
    SWTByte.writeByte(buffer, (byte)1);

    assertThat(buffer.readByte(), is((byte)3));
    assertThat(buffer.readByte(), is((byte)0));
    assertThat(buffer.readByte(), is((byte)1));
  }
}
