package de.robind.swt.protocol.datatypes.tests;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.jboss.netty.buffer.ChannelBuffers.dynamicBuffer;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.protocol.SWTProtocolException;
import de.robind.swt.protocol.datatype.SWTBoolean;
import de.robind.swt.protocol.datatype.SWTInteger;
import de.robind.swt.protocol.datatype.SWTMap;
import de.robind.swt.protocol.datatype.SWTNull;
import de.robind.swt.protocol.datatype.SWTString;

public class SWTMapTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void readMapNullBuffer() throws SWTProtocolException {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTMap.readMap(null);
  }

  @Test
  public void readMapUnexpectedType() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Unexpected type read from buffer. Expected 7, but read 0");

    SWTMap.readMap(buffer);
  }

  @Test
  public void readMapUnexpectedFlags() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(7);
    buffer.writeByte(1);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Unexpected flags read from buffer. Expected 0, but read 1");

    SWTMap.readMap(buffer);
  }

  @Test
  public void readMapNegativeNumberOfMappings() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(7);
    buffer.writeByte(0);
    buffer.writeByte(-1);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("The number of mappings cannot be negative");

    SWTMap.readMap(buffer);
  }

  @Test
  public void readMapNegativeKeyLength() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(7);
    buffer.writeByte(0);
    buffer.writeByte(1);
    buffer.writeByte(-1);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Length of key cannot be negative");

    SWTMap.readMap(buffer);
  }

  @Test
  public void readMapNegativeEmptyKey() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(7);
    buffer.writeByte(0);
    buffer.writeByte(1);
    buffer.writeByte(0);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("The key cannot be empty");

    SWTMap.readMap(buffer);
  }

  @Test
  public void readMapDoubleKey() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(7);
    buffer.writeByte(0);
    buffer.writeByte(2);
    buffer.writeByte(3);
    buffer.writeBytes(new byte[] {'f', 'o', 'o'});
    SWTInteger.writeInteger(buffer, 1);
    buffer.writeByte(3);
    buffer.writeBytes(new byte[] {'f', 'o', 'o'});
    SWTInteger.writeInteger(buffer, 1);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Key foo is not unique");

    SWTMap.readMap(buffer);
  }

  @Test
  public void readMapEmpty() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(7);
    buffer.writeByte(0);
    buffer.writeByte(0);

    Map<String, Object> mappings = SWTMap.readMap(buffer);
    assertThat(mappings, is(notNullValue()));
    assertThat(mappings.size(), is(0));
  }

  @Test
  public void readMapNonEmpty() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(7);
    buffer.writeByte(0);
    buffer.writeByte(2);
    buffer.writeByte(3);
    buffer.writeBytes(new byte[] {'f', 'o', 'o'});
    SWTInteger.writeInteger(buffer, 1);
    buffer.writeByte(3);
    buffer.writeBytes(new byte[] {'b', 'a', 'r'});
    SWTString.writeString(buffer, "holla");

    Map<String, Object> mappings = SWTMap.readMap(buffer);
    assertThat(mappings, is(notNullValue()));
    assertThat(mappings.size(), is(2));
    assertThat((Integer)mappings.get("foo"), is(1));
    assertThat((String)mappings.get("bar"), is(equalTo("holla")));
  }

  @Test
  public void writeMapNullBuffer() throws SWTProtocolException {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    SWTMap.writeMap(null, new HashMap<String, Object>());
  }

  @Test
  public void writeMapNullMap() throws SWTProtocolException {
    exception.expect(NullPointerException.class);
    exception.expectMessage("map cannot be null");

    SWTMap.writeMap(dynamicBuffer(), null);
  }

  @Test
  public void writeMapTooMuchMappings() throws SWTProtocolException {
    Map<String, Object> mappings = new HashMap<String, Object>();

    for (int i = 0; i < Byte.MAX_VALUE + 2; i++) {
      mappings.put(String.valueOf(i), i);
    }

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Number of mappings cannot be greater than 127");

    SWTMap.writeMap(dynamicBuffer(), mappings);
  }

  @Test
  public void writeMapEmptyKey() throws SWTProtocolException {
    Map<String, Object> mappings = new HashMap<String, Object>();
    mappings.put("", 1);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Key cannot be empty");

    SWTMap.writeMap(dynamicBuffer(), mappings);
  }

  @Test
  public void writeMapKeyTooLong() throws SWTProtocolException {
    String key = "x";
    while (key.length() <= (Byte.MAX_VALUE + 1)) {
      key = key + key;
    }

    Map<String, Object> mappings = new HashMap<String, Object>();
    mappings.put(key, 1);

    exception.expect(SWTProtocolException.class);
    exception.expectMessage("Length of key cannot be greater than 127");

    SWTMap.writeMap(dynamicBuffer(), mappings);
  }

  @Test
  public void writeMapEmpty() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    SWTMap.writeMap(buffer, new HashMap<String, Object>());

    assertThat(buffer.readByte(), is((byte)7));
    assertThat(buffer.readByte(), is((byte)0));
    assertThat(buffer.readByte(), is((byte)0));
  }

  @Test
  public void writeMapNonEmpty() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    Map<String, Object> mappings = new HashMap<String, Object>();

    mappings.put("foo", true);
    mappings.put("bar", null);

    SWTMap.writeMap(buffer, mappings);

    assertThat(buffer.readByte(), is((byte)7));
    assertThat(buffer.readByte(), is((byte)0));
    assertThat(buffer.readByte(), is((byte)2));

    assertThat(buffer.readByte(), is((byte)3));
    assertThat(buffer.readByte(), is((byte)'f'));
    assertThat(buffer.readByte(), is((byte)'o'));
    assertThat(buffer.readByte(), is((byte)'o'));

    assertThat(SWTBoolean.readBoolean(buffer), is(true));

    assertThat(buffer.readByte(), is((byte)3));
    assertThat(buffer.readByte(), is((byte)'b'));
    assertThat(buffer.readByte(), is((byte)'a'));
    assertThat(buffer.readByte(), is((byte)'r'));

    SWTNull.readNull(buffer);
  }
}
