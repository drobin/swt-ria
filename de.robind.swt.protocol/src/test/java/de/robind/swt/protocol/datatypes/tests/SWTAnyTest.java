package de.robind.swt.protocol.datatypes.tests;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.jboss.netty.buffer.ChannelBuffers.dynamicBuffer;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.msg.SWTObjectId;
import de.robind.swt.protocol.SWTProtocolException;
import de.robind.swt.protocol.datatype.SWTAny;
import de.robind.swt.protocol.datatype.SWTBoolean;
import de.robind.swt.protocol.datatype.SWTByte;
import de.robind.swt.protocol.datatype.SWTInteger;
import de.robind.swt.protocol.datatype.SWTMap;
import de.robind.swt.protocol.datatype.SWTNull;
import de.robind.swt.protocol.datatype.SWTObj;
import de.robind.swt.protocol.datatype.SWTString;

public class SWTAnyTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void readAnyNullBuffer() throws SWTProtocolException {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTAny.readAny(null);
  }

  @Test
  public void readAnyHeaderIncomplete() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(1);

    exception.expect(IndexOutOfBoundsException.class);

    SWTAny.readAny(buffer);
  }

  @Test
  public void readAnyUnknownType() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(0);
    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Could not decode the type/flag-combination 0/0");

    SWTAny.readAny(buffer);
  }

  @Test
  public void readAnyBoolean() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTBoolean.writeBoolean(buffer, true);

    Object result = SWTAny.readAny(buffer);
    assertThat(result, is(instanceOf(Boolean.class)));
    assertThat((Boolean)result, is(true));
  }

  @Test
  public void readAnyByte() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTByte.writeByte(buffer, (byte)1);

    Object result = SWTAny.readAny(buffer);
    assertThat(result, is(instanceOf(Byte.class)));
    assertThat((Byte)result, is(((byte)1)));
  }

  @Test
  public void readAnyInteger() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTInteger.writeInteger(buffer, 1);

    Object result = SWTAny.readAny(buffer);
    assertThat(result, is(instanceOf(Integer.class)));
    assertThat((Integer)result, is((1)));
  }

  @Test
  public void readAnyIntegerArray() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTInteger.writeIntegerArray(buffer, new int[] {1, 2});

    Object result = SWTAny.readAny(buffer);
    assertThat(result.getClass().isArray(), is(true));
    assertThat(((int[])result)[0], is((1)));
    assertThat(((int[])result)[1], is((2)));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void readAnyMap() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    Map<String, Object> mappings = new HashMap<String, Object>();

    mappings.put("1", 1);
    mappings.put("2", "hmmm");

    SWTMap.writeMap(buffer, mappings);

    Object result = SWTAny.readAny(buffer);
    assertThat(result, is(instanceOf(Map.class)));
    assertThat(((Map<String, Object>)result).size(), is(2));
    assertThat((Integer)((Map<String, Object>)result).get("1"), is(1));
    assertThat((String)((Map<String, Object>)result).get("2"), is(equalTo("hmmm")));
  }

  @Test
  public void readAnyNull() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTNull.writeNull(buffer);

    Object result = SWTAny.readAny(buffer);
    assertThat(result, is(nullValue()));
  }

  @Test
  public void readAnyString() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTString.writeString(buffer, "foo");

    Object result = SWTAny.readAny(buffer);
    assertThat(result, is(instanceOf(String.class)));
    assertThat((String)result, is(equalTo("foo")));
  }

  @Test
  public void readAnyStringArray() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTString.writeStringArray(buffer, new String[] { "foo", "bar" });

    Object result = SWTAny.readAny(buffer);
    assertThat(result, is(instanceOf(String[].class)));
    assertThat(((String[])result)[0], is(equalTo("foo")));
    assertThat(((String[])result)[1], is(equalTo("bar")));
  }

  @Test
  public void readAnySwtObj() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTObj.writeIObjId(buffer, new SWTObjectId(4711));

    Object result = SWTAny.readAny(buffer);
    assertThat(result, is(instanceOf(SWTObjectId.class)));
    assertThat(((SWTObjectId)result).isValid(), is(true));
    assertThat(((SWTObjectId)result).getId(), is(4711));
  }

  @Test
  public void writeAnyNullBuffer() throws SWTProtocolException {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTAny.writeAny(null, null);
  }

  @Test
  public void writeAnyUnsupportedSimple() throws SWTProtocolException {
    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Could not write simple datatype of class java.lang.Double");

    SWTAny.writeAny(dynamicBuffer(), 1.23);
  }

  @Test
  public void writeAnyUnsupportedArray() throws SWTProtocolException {
    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Could not write an array datatype of class java.lang.Object");

    SWTAny.writeAny(dynamicBuffer(), new Object[] {});
  }

  @Test
  public void writeAnyBoolean() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTAny.writeAny(buffer, true);

    assertThat(SWTBoolean.readBoolean(buffer), is(true));
  }

  @Test
  public void writeAnyByte() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTAny.writeAny(buffer, (byte)1);

    assertThat(SWTByte.readByte(buffer), is((byte)1));
  }

  @Test
  public void writeAnyInteger() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTAny.writeAny(buffer, 1);

    assertThat(SWTInteger.readInteger(buffer), is(1));
  }

  @Test
  public void writeAnyIntegerArray() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTAny.writeAny(buffer, new int[] {1, 2});

    int result[] = SWTInteger.readIntegerArray(buffer);
    assertThat(result[0], is(1));
    assertThat(result[1], is(2));
  }

  @Test
  public void writeAnyMap() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();

    Map<String, Object> src = new HashMap<String, Object>();
    src.put("1", true);
    src.put("2", "foo");

    SWTAny.writeAny(buffer, src);

    Map<String, Object> dest = SWTMap.readMap(buffer);
    assertThat(dest.size(), is(2));
    assertThat((Boolean)dest.get("1"), is(true));
    assertThat((String)dest.get("2"), is(equalTo("foo")));
  }

  @Test
  public void writeAnyNull() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTAny.writeAny(buffer, null);

    SWTNull.readNull(buffer);
  }

  @Test
  public void writeAnyString() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTAny.writeAny(buffer, "foo");

    assertThat(SWTString.readString(buffer), is(equalTo("foo")));
  }

  @Test
  public void writeAnyStringArray() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTAny.writeAny(buffer, new String[] { "foo", "bar" });

    String result[] = SWTString.readStringArray(buffer);
    assertThat(result.length, is(2));
    assertThat(result[0], is(equalTo("foo")));
    assertThat(result[1], is(equalTo("bar")));
  }

  @Test
  public void writeAnySwtObj() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTAny.writeAny(buffer, new SWTObjectId(4711));

    assertThat(SWTObj.readObjId(buffer).getId(), is(4711));
  }
}
