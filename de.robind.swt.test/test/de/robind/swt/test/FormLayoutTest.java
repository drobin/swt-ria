package de.robind.swt.test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.eclipse.swt.layout.FormLayout;
import org.junit.Test;

public class FormLayoutTest {
  @Test
  public void ctor() {
    FormLayout layout = new FormLayout();

    assertThat(layout.marginLeft, is(0));
    assertThat(layout.marginRight, is(0));
    assertThat(layout.marginTop, is(0));
    assertThat(layout.marginBottom, is(0));
    assertThat(layout.marginWidth, is(0));
    assertThat(layout.marginHeight, is(0));
    assertThat(layout.spacing, is(0));
  }
}
