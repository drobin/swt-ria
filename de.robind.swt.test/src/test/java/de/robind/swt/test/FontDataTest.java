package de.robind.swt.test;

import static de.robind.swt.test.utils.SWTExceptionMatcher.swtCode;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FontDataTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void stdCtor() {
    FontData data = new FontData();

    assertThat(data.getName(), is(nullValue()));
    assertThat(data.getHeight(), is(0));
    assertThat(data.getStyle(), is(0));
    assertThat(data.getLocale(), is(nullValue()));
  }

  @Test
  public void ctorStringNullString() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    new FontData(null);
  }

  @Test
  public void ctorStringInvalidString() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    new FontData("xxx");
  }

  @Test
  public void ctorStringIntInt() {
    FontData data = new FontData("foo", 1, 2);

    assertThat(data.getName(), is(equalTo("foo")));
    assertThat(data.getHeight(), is(1));
    assertThat(data.getStyle(), is(2));
    assertThat(data.getLocale(), is(nullValue()));
  }

  @Test
  public void setNameNullName() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    FontData data = new FontData();
    data.setName(null);
  }

  @Test
  public void setName() {
    FontData data = new FontData();

    data.setName("foo");
    assertThat(data.getName(), is(equalTo("foo")));
  }

  @Test
  public void setHeightNegativeHeight() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    FontData data = new FontData();
    data.setHeight(-1);
  }

  @Test
  public void setHeight() {
    FontData data = new FontData();

    data.setHeight(4711);
    assertThat(data.getHeight(), is(4711));
  }

  @Test
  public void setStyle() {
    FontData data = new FontData();

    data.setStyle(SWT.NORMAL | 0x80);
    assertThat(data.getStyle(), is(SWT.NORMAL));

    data.setStyle(SWT.BOLD | 0x80);
    assertThat(data.getStyle(), is(SWT.BOLD));

    data.setStyle(SWT.ITALIC | 0x80);
    assertThat(data.getStyle(), is(SWT.ITALIC));
  }

  @Test
  public void setLocaleNullLocale() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));
    FontData data = new FontData();
    data.setLocale(null);
  }

  @Test
  public void setLocale() {
    FontData data = new FontData();
    assertThat(data.getLocale(), is(nullValue()));

    data.setLocale("foo");
    assertThat(data.getLocale(), is(equalTo("foo")));
  }

  @Test
  public void equals() {
    FontData data = new FontData("foo", 1, 2);
    data.setLocale("bar");

    FontData data2 = new FontData("foo", 1, 2);
    data2.setLocale("xxx");

    assertThat(data.equals(data), is(true));
    assertThat(data.equals(null), is(false));
    assertThat(data.equals(1), is(false));
    assertThat(new FontData().equals(data), is(false));
    assertThat(new FontData("foo", 1, 2).equals(data), is(false));
    assertThat(data.equals(new FontData("foo", 99, 2)), is(false));
    assertThat(data.equals(new FontData("foo", 1, 99)), is(false));
    assertThat(data.equals(data2), is(false));
    assertThat(data.equals(new FontData("xxx", 1, 2)), is(false));
  }
}
