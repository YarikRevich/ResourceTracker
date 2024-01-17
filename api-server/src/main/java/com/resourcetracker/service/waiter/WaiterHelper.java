package com.resourcetracker.service.waiter;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.concurrent.CountDownLatch;

/** Represents waiter helper for general usage. */
@ApplicationScoped
public class WaiterHelper {
  private static final CountDownLatch latch = new CountDownLatch(1);

  /** Indefinitely waits for the result. */
  //  public static void waitForResult(Runn) {
  //    try {
  //      latch.await();
  //    } catch (InterruptedException e) {
  //      throw new RuntimeException(e);
  //    }
  //  }
}
