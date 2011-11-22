package de.robind.swt.msg.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
  SWTAttrRequestTest.class,
  SWTAttrResponseTest.class,
  SWTCallRequestTest.class,
  SWTCallResponseTest.class,
  SWTEventTest.class,
  SWTNewRequestTest.class,
  SWTNewResponseTest.class,
  SWTObjectIdTest.class,
  SWTOpAttrTest.class,
  SWTOpCallTest.class,
  SWTOpEventTest.class,
  SWTOpNewTest.class,
  SWTopRegTest.class,
  SWTRegRequestTest.class,
  SWTRegResponseTest.class,
  SWTRequestTest.class,
  SWTTrapTest.class
})
public class TestSuite {
}
