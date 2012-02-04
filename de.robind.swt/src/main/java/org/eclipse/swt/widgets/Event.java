package org.eclipse.swt.widgets;

import java.lang.reflect.Field;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

import de.robind.swt.base.SWTObject;

/**
 * Instances of this class provide a description of a particular event which
 * occurred within SWT. The SWT untyped listener API uses these instances for
 * all event dispatching.
 * <p>
 * Note: For a given event, only the fields which are appropriate will be
 * filled in. The contents of the fields which are not used by the event are
 * unspecified.
 */
public class Event {
  /**
   * HINT Extension of SWT-server.
   * Contains the {@link SWTObject#getId() id} of the object, where the event
   * was emitted.
   */
  public int swtObjectId;

  /**
   * the button that was pressed or released; 1 for the first button, 2 for the
   * second button, and 3 for the third button, etc.
   */
  public int button;

  /**
   * depending on the event, the character represented by the key that was
   * typed. This is the final character that results after all modifiers have
   * been applied. For example, when the user types Ctrl+A, the character value
   * is 0x01 (ASCII SOH). It is important that applications do not attempt to
   * modify the character value based on a stateMask (such as SWT.CTRL) or the
   * resulting character will not be correct.
   */
  public char character;

  /**
   * depending on the event type, the number of following paint events that are
   * pending which may always be zero on some platforms, or the number of lines
   * or pages to scroll using the mouse wheel, or the number of times the mouse
   * has been clicked
   */
  public int count;

  /**
   * a field for application use
   */
  public Object data;

  /**
   * the event specific detail field, as defined by the detail constants in
   * class SWT
   */
  public int detail;

  /**
   * the display where the event occurred.
   */
  public Display display;

  /**
   * depending on the event, a flag indicating whether the operation should be
   * allowed. Setting this field to false will cancel the operation.
   */
  public boolean doit;

  /**
   * depending on the event, the range of text being modified. Setting these
   * fields only has effect during ImeComposition events.
   */
  public int end;

  /**
   * the graphics context to use when painting that is configured to use the
   * colors, font and damaged region of the control. It is valid only during
   * the paint and must not be disposed
   */
  public GC gc;

  /**
   * the height of the bounding rectangle of the region that requires painting.
   */
  public int height;

  /**
   * the index of the item where the event occurred
   */
  public int index;

  /**
   * the item that the event occurred in (can be null)
   */
  public Widget item;

  /**
   * depending on the event, the key code of the key that was typed, as defined
   * by the key code constants in class SWT. When the character field of the
   * event is ambiguous, this field contains the unaffected value of the
   * original character. For example, typing Ctrl+M or Enter both result in the
   * character '\r' but the keyCode field will also contain '\r' when Enter was
   * typed and 'm' when Ctrl+M was typed.
   */
  public int keyCode;

  /**
   * depending on the event, the location of key specified by the keyCode or
   * character. The possible values for this field are SWT.LEFT, SWT.RIGHT,
   * SWT.KEYPAD, or SWT.NONE representing the main keyboard area.
   * <p>
   * The location field can be used to differentiate key events that have the
   * same key code and character but are generated by different keys in the
   * keyboard. For example, a key down event with the key code equals to
   * SWT.SHIFT can be generated by the left and the right shift keys in the
   * keyboard. The location field can only be used to determine the location of
   * the key code or character in the current event. It does not include
   * information about the location of modifiers in state mask.
   */
  public int keyLocation;

  /**
   * depending on the event, the range of text being modified. Setting these
   * fields only has effect during ImeComposition events.
   */
  public int start;

  /**
   * depending on the event, the state of the keyboard modifier keys and mouse
   * masks at the time the event was generated.
   */
  public int stateMask;

  /**
   * depending on the event, the new text that will be inserted. Setting this
   * field will change the text that is about to be inserted or deleted.
   */
  public String text;

  /**
   * the time that the event occurred. NOTE: This field is an unsigned integer
   * and should be AND'ed with 0xFFFFFFFFL so that it can be treated as a
   * signed long.
   */
  public int time;

  /**
   * the type of event, as defined by the event type constants in class SWT
   */
  public int type;

  /**
   * the widget that issued the event
   */
  public Widget widget;

  /**
   * the width of the bounding rectangle of the region that requires painting.
   */
  public int width;

  /**
   * depending on the event type, the x offset of the bounding rectangle of the
   * region that requires painting or the widget-relative, x coordinate of the
   * pointer at the time the mouse button was pressed or released
   */
  public int x;

  /**
   * depending on the event type, the y offset of the bounding rectangle of the
   * region that requires painting or the widget-relative, y coordinate of the
   * pointer at the time the mouse button was pressed or released
   */
  public int y;

  public Event() {
  }

  public Event(Properties properties) {
    for (Object key: properties.keySet()) {
      try {
        Field field = getClass().getField((String)key);
        field.set(this, properties.getProperty((String)key));
      } catch (Exception cause) {
        SWTException e = new SWTException(SWT.ERROR_INVALID_ARGUMENT);
        e.throwable = cause;
        throw e;
      }
    }
  }

  /**
   * Gets the bounds.
   *
   * @return a rectangle that is the bounds.
   */
  public Rectangle getBounds () {
    return new Rectangle(this.x, this.y, this.width, this.height);
  }

  /**
   * Sets the bounds.
   *
   * @param rect the new rectangle
   */
  public void setBounds (Rectangle rect) {
    this.x = rect.x;
    this.y = rect.y;
    this.width = rect.width;
    this.height = rect.height;
  }

  /**
   * Returns a string containing a concise, human-readable
   * description of the receiver.
   *
   * @return a string representation of the event
   */
  @Override
  public String toString () {
    return getClass().getSimpleName() + " {type=" + type + " " + widget +
        " time=" + time + " data=" + data + " x=" + x + " y=" + y +
        " width=" + width + " height=" + height + " detail=" + detail + "}";
  }
}
