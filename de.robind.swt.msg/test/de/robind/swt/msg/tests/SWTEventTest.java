package de.robind.swt.msg.tests;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.robind.swt.msg.SWTEvent;
import de.robind.swt.msg.SWTMessage;

public class SWTEventTest extends AbstractMessageTest {
  @Test
  public void isSWTMessage() {
    SWTEvent msg = this.factory.createEvent(new HashMap<String, Object>());
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }

  @Test
  public void nullHash() {
    SWTEvent msg = this.factory.createEvent(null);
    assertThat(msg.getAttributes().length, is(0));
  }

  @Test
  public void emptyHash() {
    SWTEvent msg = this.factory.createEvent(new HashMap<String, Object>());
    assertThat(msg.getAttributes().length, is(0));
  }

  @Test
  public void nullAttr() {
    exception.expect(NullPointerException.class);
    exception.expectMessage("attr cannot be null");

    Map<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("foo", 4711);
    attributes.put("bar", "blubber");

    SWTEvent msg = this.factory.createEvent(attributes);
    msg.getAttributeValue(null);
  }

  @Test
  public void unknownAttr() {
    exception.expect(NullPointerException.class);
    exception.expectMessage("No such attribute: xxx");

    Map<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("foo", 4711);
    attributes.put("bar", "blubber");

    SWTEvent msg = this.factory.createEvent(attributes);
    msg.getAttributeValue("xxx");
  }

  @Test
  public void fetchAttr() {
    Map<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("foo", 4711);
    attributes.put("bar", "blubber");

    SWTEvent msg = this.factory.createEvent(attributes);
    assertThat((Integer)msg.getAttributeValue("foo"), is(4711));
    assertThat((String)msg.getAttributeValue("bar"), is(equalTo("blubber")));
  }
}
