package org.eclipse.swt.events;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

/**
 * Classes which implement this interface provide a method that deals with the
 * events that are generated when a traverse event occurs in a control.
 * <p>
 * After creating an instance of a class that implements this interface it can
 * be added to a control using the
 * {@link Control#addTraverseListener(TraverseListener)} method and removed
 * using the {@link Control#removeTraverseListener(TraverseListener)} method.
 * When a traverse event occurs in a control, the
 * {@link #keyTraversed(TraverseEvent)} method will be invoked.
 */
public interface TraverseListener extends TypedListener {
  /**
   * Sent when a traverse event occurs in a control.
   * <p>
   * A traverse event occurs when the user presses a traversal key. Traversal
   * keys are typically tab and arrow keys, along with certain other keys on
   * some platforms. Traversal key constants beginning with
   * <code>TRAVERSE_</code> are defined in the {@link SWT} class.
   *
   * @param e an event containing information about the traverse
   */
  void  keyTraversed(TraverseEvent e);
}
