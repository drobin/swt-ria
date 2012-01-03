package de.robind.swt.protocol.datatypes.tests;

import static org.hamcrest.core.Is.is;
import static org.jboss.netty.buffer.ChannelBuffers.dynamicBuffer;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.protocol.SWTProtocolException;
import de.robind.swt.protocol.datatype.SWTInteger;

public class SWTIntegerTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void readIntegerNullBuffer() throws SWTProtocolException {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTInteger.readInteger(null);
  }

  @Test
  public void readIntegerUnexpectedType() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Unexpected type read from buffer. Expected 2, but read 0");

    SWTInteger.readInteger(buffer);
  }

  @Test
  public void readIntegerUnexpectedFlags() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(2);
    buffer.writeByte(1);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Unexpected flags read from buffer. Expected 0, but read 1");

    SWTInteger.readInteger(buffer);
  }

  @Test
  public void readIntegerNegative() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(2);
    buffer.writeByte(0);
    buffer.writeInt(-1);

    assertThat(SWTInteger.readInteger(buffer), is(-1));
  }

  @Test
  public void readIntegerNull() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(2);
    buffer.writeByte(0);
    buffer.writeInt(0);

    assertThat(SWTInteger.readInteger(buffer), is(0));
  }

  @Test
  public void readIntegerPositive() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(2);
    buffer.writeByte(0);
    buffer.writeInt(1);

    assertThat(SWTInteger.readInteger(buffer), is(1));
  }

  @Test
  public void writeIntegerNullBuffer() {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTInteger.writeInteger(null, 0);
  }

  @Test
  public void writeIntegerNegative() {
    ChannelBuffer buffer = dynamicBuffer();
    SWTInteger.writeInteger(buffer, -1);

    assertThat(buffer.readByte(), is((byte)2));
    assertThat(buffer.readByte(), is((byte)0));
    assertThat(buffer.readInt(), is(-1));
  }

  @Test
  public void writeIntegerNull() {
    ChannelBuffer buffer = dynamicBuffer();
    SWTInteger.writeInteger(buffer, 0);

    assertThat(buffer.readByte(), is((byte)2));
    assertThat(buffer.readByte(), is((byte)0));
    assertThat(buffer.readInt(), is(0));
  }

  @Test
  public void writeIntegerPositive() {
    ChannelBuffer buffer = dynamicBuffer();
    SWTInteger.writeInteger(buffer, 1);

    assertThat(buffer.readByte(), is((byte)2));
    assertThat(buffer.readByte(), is((byte)0));
    assertThat(buffer.readInt(), is(1));
  }
}
