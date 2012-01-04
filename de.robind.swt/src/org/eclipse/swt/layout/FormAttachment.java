package org.eclipse.swt.layout;

/**
 * Instances of this class are used to define the edges of a control within a
 * {@link FormLayout}.
 * <p>
 * {@link FormAttachment}s are set into the top, bottom, left, and right fields
 * of the {@link FormData} for a control. For example:
 *
 * <pre>
 *  FormData data = new FormData();
 *  data.top = new FormAttachment(0,5);
 *  data.bottom = new FormAttachment(100,-5);
 *  data.left = new FormAttachment(0,5);
 *  data.right = new FormAttachment(100,-5);
 *  button.setLayoutData(data);
 * </pre>
 *
 * A FormAttachment defines where to attach the side of a control by using the
 * equation, y = ax + b. The "a" term represents a fraction of the parent
 * composite's width (from the left) or height (from the top). It can be
 * defined using a numerator and denominator, or just a percentage value. If a
 * percentage is used, the denominator is set to 100. The "b" term in the
 * equation represents an offset, in pixels, from the attachment position.
 * For example:
 *
 * <pre>
 *  FormAttachment attach = new FormAttachment (20, -5);
 * </pre>
 *
 * specifies that the side to which the {@link FormAttachment} object belongs
 * will lie at 20% of the parent composite, minus 5 pixels.
 * <p>
 * Control sides can also be attached to another control. For example:
 *
 * <pre>
 *  FormAttachment attach = new FormAttachment (button, 10);
 * </pre>
 *
 * specifies that the side to which the {@link FormAttachment} object belongs
 * will lie in the same position as the adjacent side of the button control,
 * plus 10 pixels. The control side can also be attached to the opposite side
 * of the specified control. For example:
 *
 * <pre>
 *  FormData data = new FormData ();
 *  data.left = new FormAttachment (button, 0, SWT.LEFT);
 * </pre>
 *
 * specifies that the left side of the control will lie in the same position
 * as the left side of the button control. The control can also be attached in
 * a position that will center the control on the specified control. For
 * example:
 *
 * <pre>
 *  data.left = new FormAttachment (button, 0, SWT.CENTER);
 * </pre>
 *
 * specifies that the left side of the control will be positioned so that it is
 * centered between the left and right sides of the button control. If the
 * alignment is not specified, the default is to attach to the adjacent side.
 */
public class FormAttachment {

}
