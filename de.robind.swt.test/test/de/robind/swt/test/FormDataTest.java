package de.robind.swt.test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.junit.Test;

public class FormDataTest {
  @Test
  public void stdCtor() {
    FormData data = new FormData();

    assertThat(data.left, is(nullValue()));
    assertThat(data.right, is(nullValue()));
    assertThat(data.top, is(nullValue()));
    assertThat(data.bottom, is(nullValue()));
    assertThat(data.width, is(SWT.DEFAULT));
    assertThat(data.height, is(SWT.DEFAULT));
  }

  @Test
  public void ctorIntInt() {
    FormData data = new FormData(1, 2);

    assertThat(data.left, is(nullValue()));
    assertThat(data.right, is(nullValue()));
    assertThat(data.top, is(nullValue()));
    assertThat(data.bottom, is(nullValue()));
    assertThat(data.width, is(1));
    assertThat(data.height, is(2));
  }
}
