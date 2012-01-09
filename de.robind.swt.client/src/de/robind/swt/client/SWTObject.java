package de.robind.swt.client;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

import de.robind.swt.msg.SWTObjectId;

/**
 * Methods used to create/manipulate SWT-objects.
 *
 * @author Robin Doer
 */
public class SWTObject {
  /**
   * The logger of the class.
   */
  private static final Logger logger = Logger.getLogger(SWTObject.class);

  /**
   * Creates a new SWT-object.
   * <p>
   * On success, the new object is placed into <code>objMap</code> under the
   * given <code>id</code>.
   *
   * @param objMap The mapping between object-id and object. On success the
   *               new object is placed into this mapping. If one of the
   *               arguments points to a SWT-object, then the method searches
   *               for the object inside this mapping.
   * @param id The id of the object to be created. If the object was
   *           successfully created, the new object is placed inside
   *           <code>objMap</code> under this id.
   * @param objClass The class of the object to be created
   * @param args Arguments passed to the constructor
   * @return The new object is returned.
   * @throws NoSuchMethodException if the constructor is not available
   * @throws InvocationTargetException if the constructor throws an exception.
   * @throws IllegalAccessException if the constructor is inaccessible.
   * @throws InstantiationException if the class represents an abstract class.
   */
  public static Object createObject(SWTObjectMap objMap, int id,
      Class<?> objClass, Object... args) throws NoSuchMethodException,
      InvocationTargetException, IllegalAccessException,
      InstantiationException {

    Object arguments[] = normalizeArguments(objMap, args);
    Constructor<?> ctor = findConstructor(objMap, objClass, arguments);
    Object obj = ctor.newInstance(arguments);

    objMap.put(id, obj);

    return (obj);
  }

  /**
   * Invokes a method on a SWT-object.
   * <p>
   * The method with the name <code>methodName</code> is located in
   * <code>objMap</code> under the given <code>id</code>.
   *
   * @param objMap The mapping between object-id and object. If one of the
   *               arguments points to a SWT-object, then the method searches
   *               for the object inside this mapping.
   * @param id The id of the object inside <code>objMap</code>
   * @param methodName The name of the method to be invoked
   * @param args Arguments passed to the method
   * @return Result returned by the method (which can be <code>null</code>).
   * @throws NoSuchMethodException if the method is not available
   * @throws InvocationTargetException if the method throws an exception.
   * @throws IllegalAccessException if the method is inaccessible.
   */
  public static Object callMethod(SWTObjectMap objMap, int id,
      String methodName, Object... args) throws NoSuchMethodException,
      InvocationTargetException, IllegalAccessException {

    Object obj = objMap.get(id);
    Object arguments[] = normalizeArguments(objMap, args);
    Method method = findMethod(objMap, obj.getClass(), methodName, arguments);

    return (method.invoke(obj, arguments));
  }

  /**
   * Enables event-handling for an object and a event-type.
   *
   * @param objMap The mapping between object-id and object.
   * @param listenerFactory Factory used to create listern (which are used to
   *                        send events to the server).
   * @param id The id of the object inside <code>objMap</code>
   * @param eventType The event-type to be enabled resp. disabled
   * @param enable If set to <code>true</code>, the event-handling should be
   *               enabled. If it is set to <code>false</code>, it is disabled.
   * @throws IllegalArgumentException if the destination object is not a
   *         {@link Widget}. Only {@link Widget widgets} are receiver of
   *         events.
   */
  public static void registerEvent(SWTObjectMap objMap,
      SWTEventListenerFactory listenerFactory, int id, int eventType,
      boolean enable) throws IllegalArgumentException {

    Object obj = objMap.get(id);

    if (!(obj instanceof Widget)) {
      throw new IllegalArgumentException(
          "Widget expected, is " + obj.getClass().getName());
    }

    Widget widget = (Widget)obj;

    if (enable) {
      SWTEventListener listener = listenerFactory.createListener(id);
      widget.addListener(eventType, listener);
    } else {
      for (Listener listener: widget.getListeners(eventType)) {
        widget.removeListener(eventType, listener);
      }
    }
  }

  /**
   * Updates an attribute of an {@link SWTObject}.
   *
   * @param objMap The mapping between object-id and object. If one of the
   *               arguments points to a SWT-object, then the method searches
   *               for the object inside this mapping.
   * @param id The id of the object inside <code>objMap</code>
   * @param attrName The name of the attribute to update
   * @param attrValue The new value
   * @throws NoSuchFieldException of no such attribute exists
   * @throws IllegalAccessException if <code>attrName</code> is not defined in
   *         the object
   */
  public static void updateAttribute(SWTObjectMap objMap, int id,
      String attrName, Object attrValue)
          throws NoSuchFieldException, IllegalAccessException {

    Object obj = objMap.get(id);
    Field field = obj.getClass().getField(attrName);
    field.set(obj, normalizeArgument(objMap, attrValue));
  }

  /**
   * Searches for a constructor, which can be called with the given arguments.
   *
   * @param objMap The mapping from object-id to object
   * @param objClass The related class
   * @param arguments Arguments, which should be passed to the constructor.
   * @return The matching constructor.
   * @throws NoSuchMethodException if no matching constructor is available in
   *         <code>objClass</code>.
   */
  private static Constructor<?> findConstructor(SWTObjectMap objMap,
      Class<?> objClass, Object arguments[]) throws NoSuchMethodException {

    for (Constructor<?> ctor: objClass.getConstructors()) {
      logger.debug("Checking: " + ctor);

      // Check the parameter-list of the constructor
      // If the parameter-list does not match the argument-list, you can skip
      // the constructor.
      if (!checkParameter(objMap, ctor.getParameterTypes(), arguments)) {
        continue;
      }

      logger.debug("Using: " + ctor);
      return (ctor);
    }

    // Constructor not found
    throw new NoSuchMethodException(
        objClass.getSimpleName() + "(" + Arrays.toString(arguments) + ")");
  }

  /**
   * Searches for a method in <code>objClass</code> that can be invoked with
   * the given arguments.
   *
   * @param objMap The mapping from the object-id to the SWT-object
   * @param objClass The related class
   * @param name Name of method
   * @param arguments Arguments, which should be passed to the method
   * @return The related method
   * @throws NoSuchMethodException if no matching method is available in
   *         <code>objClass</code>.
   */
  private static Method findMethod(SWTObjectMap objMap, Class<?> objClass,
      String name, Object arguments[]) throws NoSuchMethodException {

    for (Method method: objClass.getMethods()) {
      logger.debug("Checking: " + method);

      // The name needs to be match.
      // Otherwise you can skip the following tests.
      if (!method.getName().equals(name)) {
        continue;
      }

      // Check the parameter-list of the method
      // If the parameter-list does not match the argument-list, you can skip
      // the method.
      if (!checkParameter(objMap, method.getParameterTypes(), arguments)) {
        continue;
      }

      logger.debug("Using: " + method);
      return (method);
    }

    // Method not found
    throw new NoSuchMethodException(
        objClass.getSimpleName() + "." + name +
        "(" + Arrays.toString(arguments) + ")");
  }

  /**
   * Checks the paramater-list, if it is callable with the given argument-list.
   *
   * @param objMap The mapping from object-id to object
   * @param parameterTypes The parameter-types to check
   * @param arguments Arguments, which should be passed to the constructor.
   * @return If the parameter-list is callable with the given arguments
   *         <code>true</code> is returned, <code>false</code> otherwise.
   */
  private static boolean checkParameter(
      SWTObjectMap objMap, Class<?> parameterTypes[], Object arguments[]) {

    if (parameterTypes.length != arguments.length) {
      logger.debug("Parameter-list has wrong length, skipping...");
      return (false);
    }

    for (int i = 0; i < parameterTypes.length; i++) {
      Object argument = arguments[i];
      Class<?> parameter = argument.getClass();

      if (argument instanceof SWTObjectId) {
        // Need to transform it into the SWT-object with the given id
        int id = ((SWTObjectId)argument).getId();
        argument = objMap.get(id);
        parameter = argument.getClass();
      } else if (argument instanceof Boolean) {
        parameter = boolean.class;
      } else if (argument instanceof Byte) {
        parameter = byte.class;
      } else if (argument instanceof Character) {
        parameter = char.class;
      } else if (argument instanceof Short) {
        parameter = short.class;
      } else if (argument instanceof Integer) {
        parameter = int.class;
      } else if (argument instanceof Long) {
        parameter = long.class;
      } else if (argument instanceof Float) {
        parameter = float.class;
      } else if (argument instanceof Double) {
        parameter = double.class;
      }

      if (!parameterTypes[i].isAssignableFrom(parameter)) {
        logger.debug(parameter + " not instanceof " +
            parameterTypes[i] + ", skipping...");
        return (false);
      }
    }

    return (true);
  }

  /**
   * Transforms the given argument into a usable form.
   * <p>
   * If the argument is a {@link SWTObjectId}, which is unknown in the
   * SWT-framework, then the if is replaced with the related SWT-object located
   * in the given object-map.
   *
   * @param objMap The mapping from object-id to object
   * @param argument The argument to check
   * @return
   */
  private static Object normalizeArgument(SWTObjectMap objMap, Object argument) {

    if (argument instanceof SWTObjectId) {
      // Need to transform it into the SWT-object with the given id
      int id = ((SWTObjectId)argument).getId();
      return (objMap.get(id));
    } else {
      return (argument);
    }
  }

  /**
   * Transforms the given argument-list into a usable form.
   * <p>
   * The argument-list can contain instances of {@link SWTObjectId}, which are
   * unknown in the SWT-framework. Find such instances an replace them with
   * the related SWT-objects.
   *
   * @param objMap The mapping from object-id to object
   * @param arguments The source arguments
   * @return Arguments, which can be passed into the SWT-framework
   */
  private static Object[] normalizeArguments(SWTObjectMap objMap, Object arguments[]) {
    Object result[] = new Object[arguments.length];

    for (int i = 0; i < arguments.length; i++) {
      result[i] = normalizeArgument(objMap, arguments[i]);
    }

    return (result);
  }
}
