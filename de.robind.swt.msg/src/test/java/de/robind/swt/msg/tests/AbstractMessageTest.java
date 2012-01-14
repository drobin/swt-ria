package de.robind.swt.msg.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import de.robind.swt.msg.SWTMessageFactory;

public class AbstractMessageTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  protected SWTMessageFactory factory = null;

  @Before
  public void setup() {
    this.factory = new SWTMessageFactory();
  }

  @After
  public void teardown() {
    this.factory = null;
  }
}
