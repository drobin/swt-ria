package de.robind.swt.test;

import static de.robind.swt.test.utils.SWTExceptionMatcher.swtCode;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FontTest {
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void ctorDeviceFontDataNullDevice() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    new Font(null, new FontData());
  }

  @Test
  public void ctorDeviceFontDataNullFontData() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    new Font(new Device() {}, (FontData)null);
  }

  @Test
  public void ctorDeviceFontData() {
    Device device = new Device() {};
    FontData fd = new FontData();
    Font font = new Font(device, fd);

    assertThat(font.getDevice(), is(sameInstance(device)));
    assertThat(font.getFontData().length, is(1));
    assertThat(font.getFontData()[0], is(sameInstance(fd)));
  }

  @Test
  public void ctorDeviceFontDataArrayNullDevice() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    new Font(null, new FontData[] { new FontData() });
  }

  @Test
  public void ctorDeviceFontDataArrayNullNullArray() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    new Font(new Device() {}, (FontData[])null);
  }

  @Test
  public void ctorDeviceFontDataArrayNullEmptyArray() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    new Font(new Device() {}, new FontData[] {});
  }

  @Test
  public void ctorDeviceFontDataArrayNullNullArrayElement() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    new Font(new Device() {}, new FontData[] { null });
  }

  @Test
  public void ctorDeviceFontDataArray() {
    Device device = new Device() {};
    FontData fds[] = new FontData[] {
        new FontData(),
        new FontData()
    };
    Font font = new Font(device, fds);

    assertThat(font.getDevice(), is(sameInstance(device)));
    assertThat(font.getFontData().length, is(2));
    assertThat(font.getFontData()[0], is(sameInstance(fds[0])));
    assertThat(font.getFontData()[1], is(sameInstance(fds[1])));
  }

  @Test
  public void ctorDeviceStringIntIntNullDevice() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    new Font(null, "foo", 1, 2);
  }

  @Test
  public void ctorDeviceStringIntIntNullName() {
    exception.expect(swtCode(SWT.ERROR_NULL_ARGUMENT));

    new Font(new Device() {}, null, 1, 2);
  }

  @Test
  public void ctorDeviceStringIntIntNegativeHeight() {
    exception.expect(swtCode(SWT.ERROR_INVALID_ARGUMENT));

    new Font(new Device() {}, "foo", -1, 2);
  }

  @Test
  public void ctorDeviceStringIntInt() {
    Device device = new Device() {};
    Font font = new Font(device, "foo", 1, 2);

    assertThat(font.getDevice(), is(sameInstance(device)));
    assertThat(font.getFontData().length, is(1));
    assertThat(font.getFontData()[0].getName(), is(equalTo("foo")));
    assertThat(font.getFontData()[0].getHeight(), is(1));
    assertThat(font.getFontData()[0].getStyle(), is(2));
  }

  @Test
  public void getFontDataDisposed() {
    exception.expect(swtCode(SWT.ERROR_GRAPHIC_DISPOSED));

    Font font = new Font(new Device() {}, "foo", 1, 2);
    font.dispose();
    font.getFontData();
  }

  @Test
  public void equals() {
    Device device = new Device() {};
    Font font = new Font(device, "foo", 1, 2);

    assertThat(font.equals(font), is(true));
    assertThat(font.equals(null),is(false));
    assertThat(font.equals(1), is(false));
    assertThat(font.equals(new FontData()), is(false));
    assertThat(font.equals(new Font(new Device() {}, "foo", 1, 2)), is(false));
    assertThat(font.equals(new Font(device, "foo", 1, 2)), is(true));
  }
}
