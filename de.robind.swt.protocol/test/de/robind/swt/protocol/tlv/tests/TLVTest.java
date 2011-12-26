package de.robind.swt.protocol.tlv.tests;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.jboss.netty.buffer.ChannelBuffers.directBuffer;
import static org.jboss.netty.buffer.ChannelBuffers.dynamicBuffer;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.robind.swt.protocol.SWTProtocolException;
import de.robind.swt.protocol.tlv.StringTLV;
import de.robind.swt.protocol.tlv.TLV;

public class TLVTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void readNullBuffer() throws SWTProtocolException {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    TLV.read(null);
  }

  @Test
  public void readHeaderNotComplete() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeShort(4711);

    assertThat(TLV.read(buffer), is(nullValue()));
    assertThat(buffer.readerIndex(), is(0));
    assertThat(buffer.writerIndex(), is(2));
  }

  @Test
  public void readNegativeLength() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(0);
    buffer.writeShort(-1);

    try {
      TLV.read(buffer);
      fail("SWTProtocolException expected");
    } catch (SWTProtocolException e) {
      assertThat(e.getMessage(), is(equalTo("TLV-length cannot be negative")));
      assertThat(buffer.readerIndex(), is(0));
      assertThat(buffer.writerIndex(), is(3));
    }
  }

  @Test
  public void readValueNotComplete() throws SWTProtocolException {
    ChannelBuffer buffer = dynamicBuffer();
    buffer.writeByte(0);
    buffer.writeShort(5);
    buffer.writeInt(4711);

    assertThat(TLV.read(buffer), is(nullValue()));
    assertThat(buffer.readerIndex(), is(0));
    assertThat(buffer.writerIndex(), is(7));
  }

  @Test
  public void writeNullTLV() throws SWTProtocolException {
    exception.expect(NullPointerException.class);
    exception.expectMessage("tlv cannot be null");

    TLV.write(null, dynamicBuffer());
  }

  @Test
  public void writeNullBuffer() throws SWTProtocolException {
    exception.expect(NullPointerException.class);
    exception.expectMessage("buffer cannot be null");

    TLV.write(new StringTLV(""), null);
  }

  @Test
  public void writeBufferNotBigEnoughForTypeLength() throws SWTProtocolException {
    TLV tlv = new TLV() {
      @Override
      protected void writeToBuffer(ChannelBuffer buffer)
          throws SWTProtocolException {
      }

      @Override
      protected void readFromBuffer(ChannelBuffer buffer)
          throws SWTProtocolException {
      }
    };

    exception.expect(IndexOutOfBoundsException.class);

    ChannelBuffer buffer = directBuffer(2);
    TLV.write(tlv, buffer);
  }

  public void writeBufferNotBigEnoughForValue() throws SWTProtocolException {
    TLV tlv = new TLV() {
      @Override
      protected void writeToBuffer(ChannelBuffer buffer)
          throws SWTProtocolException {

        buffer.writeInt(4711);
      }

      @Override
      protected void readFromBuffer(ChannelBuffer buffer)
          throws SWTProtocolException {
      }
    };

    exception.expect(IndexOutOfBoundsException.class);

    ChannelBuffer buffer = directBuffer(6);
    TLV.write(tlv, buffer);
  }
}
