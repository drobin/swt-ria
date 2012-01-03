package de.robind.swt.protocol.datatypes.tests;

import static org.hamcrest.core.Is.is;
import static org.jboss.netty.buffer.ChannelBuffers.dynamicBuffer;
import static org.junit.Assert.assertThat;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.msg.SWTObjectId;
import de.robind.swt.protocol.SWTProtocolException;
import de.robind.swt.protocol.datatype.SWTObj;

public class SWTObjTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void readObjIdNullBuffer() throws SWTProtocolException {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTObj.readObjId(null);
  }

  @Test
  public void readObjIdUnexpectedType() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Unexpected type read from buffer. Expected 6, but read 0");

    SWTObj.readObjId(buffer);
  }

  @Test
  public void readObjIdUnexpectedFlags() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(6);
    buffer.writeByte(1);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Unexpected flags read from buffer. Expected 0, but read 1");

    SWTObj.readObjId(buffer);
  }

  @Test
  public void readObjIdDefined() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(6);
    buffer.writeByte(0);
    buffer.writeInt(4711);

    SWTObjectId objId = SWTObj.readObjId(buffer);
    assertThat(objId.isValid(), is(true));
    assertThat(objId.getId(), is(4711));
  }

  @Test
  public void readObjIdUndefined() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(6);
    buffer.writeByte(0);
    buffer.writeInt(-1);

    SWTObjectId objId = SWTObj.readObjId(buffer);
    assertThat(objId.isValid(), is(false));
  }

  @Test
  public void writeObjIdNullBuffer() {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTObj.writeIObjId(null, SWTObjectId.undefined());
  }

  @Test
  public void writeObjIdNullObjId() {
    exception.expect(NullPointerException.class);
    exception.expectMessage("objId cannot be null");

    SWTObj.writeIObjId(dynamicBuffer(), null);
  }

  @Test
  public void writeObjIdDefined() {
    ChannelBuffer buffer = dynamicBuffer();

    SWTObj.writeIObjId(buffer, new SWTObjectId(4711));

    assertThat(buffer.readByte(), is((byte)6));
    assertThat(buffer.readByte(), is((byte)0));
    assertThat(buffer.readInt(), is(4711));
  }

  @Test
  public void writeObjIdUndefined() {
    ChannelBuffer buffer = dynamicBuffer();

    SWTObj.writeIObjId(buffer, SWTObjectId.undefined());

    assertThat(buffer.readByte(), is((byte)6));
    assertThat(buffer.readByte(), is((byte)0));
    assertThat(buffer.readInt(), is(-1));
  }
}
