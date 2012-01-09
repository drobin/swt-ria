package de.robind.swt.aj;

import org.aspectj.lang.Signature;
import org.eclipse.swt.server.Trackable;

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
   * 
   * @param obj The object, where the field is located
   * @param value The new value
   */
  pointcut setTrackable(Object obj, Object value):
    (set(@Trackable * *)) && target(obj) && args(value);

  /**
   * Hook into after {@link Trackable}-annotated fields are updated.
   */
  after(Object obj, Object value): setTrackable(obj, value) {
    Signature sig = thisJoinPoint.getSignature();

    System.err.println(
        "Updated: " + obj.getClass().getName() + "." + sig.getName() +
        " with " + value);
  }
}
