package de.robind.swt.base;

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
   * @throws SWTBaseException failed to execute the changelog-entry
   */
  void run(Key key) throws SWTBaseException;
}
