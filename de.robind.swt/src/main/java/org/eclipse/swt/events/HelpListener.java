package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Control;

/**
 * Classes which implement this interface provide a method that deals with the
 * vent that is generated when help is requested for a control, typically when
 * the user presses F1.
 * <p>
 * After creating an instance of a class that implements this interface it can
 * be added to a control using the
 * {@link Control#addHelpListener(HelpListener)} method and removed using the
 * {@link Control#removeHelpListener(HelpListener)} method. When help is
 * requested for a control, the {@link #helpRequested(HelpEvent)} method will
 * be invoked.
 */
public interface HelpListener extends TypedListener {
  /**
   * Sent when help is requested for a control, typically when the user
   * presses F1.
   *
   * @param e an event containing information about the help
   */
  void helpRequested(HelpEvent e);
}
