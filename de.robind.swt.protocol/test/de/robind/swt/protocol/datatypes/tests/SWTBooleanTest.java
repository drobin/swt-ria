package de.robind.swt.protocol.datatypes.tests;

import static org.hamcrest.core.Is.is;
import static org.jboss.netty.buffer.ChannelBuffers.dynamicBuffer;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.protocol.SWTProtocolException;
import de.robind.swt.protocol.datatype.SWTBoolean;

public class SWTBooleanTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void readBooleanNullBuffer() throws SWTProtocolException {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTBoolean.readBoolean(null);
  }

  @Test
  public void readBooleanUnexpectedType() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Unexpected type read from buffer. Expected 4, but read 0");

    SWTBoolean.readBoolean(buffer);
  }

  @Test
  public void readBooleanUnexpectedFlags() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(4);
    buffer.writeByte(1);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Unexpected flags read from buffer. Expected 0, but read 1");

    SWTBoolean.readBoolean(buffer);
  }

  @Test
  public void readBooleanTrue() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(4);
    buffer.writeByte(0);
    buffer.writeByte(1);

    assertThat(SWTBoolean.readBoolean(buffer), is(true));
  }

  @Test
  public void readBooleanFalse() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(4);
    buffer.writeByte(0);
    buffer.writeByte(0);

    assertThat(SWTBoolean.readBoolean(buffer), is(false));
  }

  @Test
  public void writeBooleanNullBuffer() {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTBoolean.writeBoolean(null, true);
  }

  @Test
  public void writeBooleanTrue() {
    ChannelBuffer buffer = dynamicBuffer();
    SWTBoolean.writeBoolean(buffer, true);

    assertThat(buffer.readByte(), is((byte)4));
    assertThat(buffer.readByte(), is((byte)0));
    assertThat(buffer.readByte(), is((byte)1));
  }

  @Test
  public void writeBooleanFalse() {
    ChannelBuffer buffer = dynamicBuffer();
    SWTBoolean.writeBoolean(buffer, false);

    assertThat(buffer.readByte(), is((byte)4));
    assertThat(buffer.readByte(), is((byte)0));
    assertThat(buffer.readByte(), is((byte)0));
  }
}
