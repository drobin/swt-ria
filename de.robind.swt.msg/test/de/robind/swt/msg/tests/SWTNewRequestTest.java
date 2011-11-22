package de.robind.swt.msg.tests;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Assert;
import org.junit.Test;

import de.robind.swt.msg.SWTMessage;
import de.robind.swt.msg.SWTNewRequest;
import de.robind.swt.msg.SWTOpNew;
import de.robind.swt.msg.SWTRequest;
import de.robind.swt.msg.SWTResponse;
import de.robind.swt.msg.SWTTrap;

public class SWTNewRequestTest {
  @Test
  public void isSWTMessage() {
    SWTNewRequest msg = new SWTNewRequest(0, Object.class);
    assertThat(msg, is(instanceOf(SWTMessage.class)));
  }

  @Test
  public void isSWTRequest() {
    SWTNewRequest msg = new SWTNewRequest(0, Object.class);
    assertThat(msg, is(instanceOf(SWTRequest.class)));
    assertThat(msg, is(not(instanceOf(SWTResponse.class))));
    assertThat(msg, is(not(instanceOf(SWTTrap.class))));
  }

  @Test
  public void isOpNew() {
    SWTNewRequest msg = new SWTNewRequest(0, Object.class);
    assertThat(msg, is(instanceOf(SWTOpNew.class)));
  }

  @Test(expected = NullPointerException.class)
  public void nullClassName() throws ClassNotFoundException {
    new SWTNewRequest(0, (String)null);
  }

  @Test(expected = ClassNotFoundException.class)
  public void illegalClassName() throws ClassNotFoundException {
    new SWTNewRequest(0, "blubber");
  }

  @Test(expected = NullPointerException.class)
  public void nullClass() {
    new SWTNewRequest(0, (Class<?>)null);
  }

  @Test
  public void getId() {
    SWTNewRequest msg = new SWTNewRequest(4711, Object.class);
    assertThat(msg.getId(), is(4711));
  }

  @Test
  public void getObjectClass() {
    SWTNewRequest msg = new SWTNewRequest(0, Integer.class);
    if (!msg.getObjClass().equals(Integer.class)) {
      Assert.fail("getObjClass() is not an Integer");
    }
  }

  @Test
  public void getObjectClassFromString() throws ClassNotFoundException {
    SWTNewRequest msg = new SWTNewRequest(0, "java.lang.Integer");
    if (!msg.getObjClass().equals(Integer.class)) {
      Assert.fail("getObjClass() is not an Integer");
    }
  }

  @Test
  public void noArguments() {
    SWTNewRequest msg = new SWTNewRequest(0, Object.class);
    assertThat(msg.getArguments().length, is(0));
  }

  @Test
  public void getArguments() {
    Object obj = new Object();
    SWTNewRequest msg = new SWTNewRequest(0, Object.class, "foo", 4711, obj);
    assertThat(msg.getArguments().length, is(3));
    assertThat((String)msg.getArguments()[0], is(equalTo("foo")));
    assertThat((Integer)msg.getArguments()[1], is(4711));
    assertThat(msg.getArguments()[2], is(sameInstance(obj)));
  }
}
