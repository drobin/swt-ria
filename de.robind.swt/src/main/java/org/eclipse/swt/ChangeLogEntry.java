package org.eclipse.swt;

import org.eclipse.swt.server.Key;

/**
 * An entry in the {@link SWTObject#changeLog changelog}.
 * <p>
 * The entry can be executed by calling {@link #run(Key)}.
 *
 * @author Robin Doer
 */
interface ChangeLogEntry {
  /**
   * Executes the changelog-entry.
   *
   * @param key The key passed to the entry
   * @throws SWTException failed to execute the procedure
   */
  void run(Key key) throws SWTException;
}
