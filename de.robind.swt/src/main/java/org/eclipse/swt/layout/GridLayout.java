package org.eclipse.swt.layout;

import java.awt.Composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.server.Trackable;
import org.eclipse.swt.widgets.LayoutAdapter;

/**
 * Instances of this class lay out the control children of a {@link Composite}
 * in a grid.
 * <p>
 * {@link GridLayout} has a number of configuration fields, and the controls
 * it lays out can have an associated layout data object, called
 * {@link GridData}. The power of {@link GridLayout} lies in the ability to
 * configure {@link GridData} for each control in the layout.
 * <p>
 * The following code creates a shell managed by a {@link GridLayout} with
 * 3 columns:
 *
 * <pre>
 *  Display display = new Display();
 *  Shell shell = new Shell(display);
 *  GridLayout gridLayout = new GridLayout();
 *  gridLayout.numColumns = 3;
 *  shell.setLayout(gridLayout);
 * </pre>
 *
 * The {@link #numColumns} field is the most important field in a
 * {@link GridLayout}. Widgets are laid out in columns from left to right, and
 * a new row is created when {@link #numColumns} + 1 controls are added to the
 * Composite.
 */
public class GridLayout extends LayoutAdapter {
  /**
   * horizontalSpacing specifies the number of pixels between the right edge of
   * one cell and the left edge of its neighbouring cell to the right. The
   * default value is 5.
   */
  @Trackable
  public int horizontalSpacing = 5;

  /**
   * makeColumnsEqualWidth specifies whether all columns in the layout will be
   * forced to have the same width. The default value is false.
   */
  @Trackable
  public boolean makeColumnsEqualWidth = false;

  /**
   * marginBottom specifies the number of pixels of vertical margin that will
   * be placed along the bottom edge of the layout. The default value is 0.
   */
  @Trackable
  public int marginBottom = 0;

  /**
   * marginHeight specifies the number of pixels of vertical margin that will
   * be placed along the top and bottom edges of the layout. The default value
   * is 5.
   */
  @Trackable
  public int marginHeight = 5;

  /**
   * marginLeft specifies the number of pixels of horizontal margin that will
   * be placed along the left edge of the layout. The default value is 0.
   */
  @Trackable
  public int marginLeft = 0;

  /**
   * marginRight specifies the number of pixels of horizontal margin that will
   * be placed along the right edge of the layout. The default value is 0.
   */
  @Trackable
  public int marginRight = 0;

  /**
   * marginTop specifies the number of pixels of vertical margin that will be
   * placed along the top edge of the layout. The default value is 0.
   */
  @Trackable
  public int marginTop = 0;

  /**
   * marginWidth specifies the number of pixels of horizontal margin that will
   * be placed along the left and right edges of the layout. The default value
   * is 5.
   */
  @Trackable
  public int marginWidth = 5;

  /**
   * numColumns specifies the number of cell columns in the layout. If
   * numColumns has a value less than 1, the layout will not set the size
   * and position of any controls. The default value is 1.
   */
  @Trackable
  public int numColumns = 1;

  /**
   * verticalSpacing specifies the number of pixels between the bottom edge of
   * one cell and the top edge of its neighbouring cell underneath. The default
   * value is 5.
   */
  @Trackable
  public int verticalSpacing = 5;

  /**
   * Constructs a new instance of this class.
   */
  public GridLayout() {
    createObject();
  }

  /**
   * Constructs a new instance of this class given the number of columns, and
   * whether or not the columns should be forced to have the same width.
   * If numColumns has a value less than 1, the layout will not set the size
   * and position of any controls.
   *
   * @param numColumns the number of columns in the grid
   * @param makeColumnsEqualWidth whether or not the columns will have equal
   *                              width
   */
  public GridLayout(int numColumns, boolean makeColumnsEqualWidth) {
    createObject(numColumns, makeColumnsEqualWidth);

    this.numColumns = numColumns;
    this.makeColumnsEqualWidth = makeColumnsEqualWidth;
  }

  /**
   * Invoked, if an attribute of the class has changed.
   *
   * @param field The name of the attribute-field, which has changed
   * @throws SWTException if the attribute was not updated at the client
   */
  public void attributeChanged(String field) throws SWTException {
    Object value;

    try {
      value = getClass().getField(field).get(this);
    } catch (Exception cause) {
      SWTException e = new SWTException(SWT.ERROR_INVALID_ARGUMENT);
      e.throwable = cause;
      throw e;
    }

    updateAttribute(field, value);
  }

  /**
   * Returns a string containing a concise, human-readable
   * description of the receiver.
   *
   * @return a string representation of the layout
   */
  @Override
  public String toString () {
    String string = getClass().getSimpleName() +" {";
    if (numColumns != 1) string += "numColumns="+numColumns+" ";
    if (makeColumnsEqualWidth) string += "makeColumnsEqualWidth="+makeColumnsEqualWidth+" ";
    if (marginWidth != 0) string += "marginWidth="+marginWidth+" ";
    if (marginHeight != 0) string += "marginHeight="+marginHeight+" ";
    if (marginLeft != 0) string += "marginLeft="+marginLeft+" ";
    if (marginRight != 0) string += "marginRight="+marginRight+" ";
    if (marginTop != 0) string += "marginTop="+marginTop+" ";
    if (marginBottom != 0) string += "marginBottom="+marginBottom+" ";
    if (horizontalSpacing != 0) string += "horizontalSpacing="+horizontalSpacing+" ";
    if (verticalSpacing != 0) string += "verticalSpacing="+verticalSpacing+" ";
    string = string.trim();
    string += "}";
    return string;
  }
}
