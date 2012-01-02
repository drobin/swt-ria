package org.eclipse.swt.test.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SWTTestUtils {
  public static <T> T asyncExec(Callable<T> task) throws Throwable {
    ExecutorService executorService = Executors.newFixedThreadPool(1);
    Future<T> future = executorService.submit(task);

    try {
      return (future.get());
    } catch (ExecutionException e) {
      throw e.getCause();
    }
  }
}
