package de.robind.swt.aj;

import org.eclipse.swt.server.Trackable;
import org.aspectj.lang.Signature;

/**
 * Aspect tracks fields annotated with the {@link Trackable}-annotation.
 * <p>
 * The new value is transferred to the client.
 *
 * @author Robin Doer
 */
public aspect TrackableAspect {
  /**
   * The pointcut tracks fields annotated with the
   * {@link Trackable}-annotation.
   */
  pointcut setTrackable():
    set(@Trackable * *);
  
  /**
   * Hook into after {@link Trackable}-annotated fields are updated.
   */
  after(): setTrackable() {
    Signature sig = thisJoinPoint.getSignature();
    System.err.println("Updated: " + sig);
  }
}
