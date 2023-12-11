package com.resourcetracker.service.scheduler;

import jakarta.annotation.PreDestroy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** Provides access to task schedule opportunities. */
public class SchedulerHelper {
  private static final ScheduledExecutorService scheduledExecutorService =
          Executors.newSingleThreadScheduledExecutor();
  private static final ExecutorService executorService =
          Executors.newVirtualThreadPerTaskExecutor();

  /**
   * Schedules given callback with the specified period.
   *
   * @param callback callback to be scheduled.
   * @param period period of the task execution.
   */
  static public void scheduleTask(Runnable callback, Integer period) {
    scheduledExecutorService.scheduleAtFixedRate(
        () -> executorService.execute(callback), 0, period, TimeUnit.MILLISECONDS);
  }

  @PreDestroy
  private void close() {
    scheduledExecutorService.close();
    executorService.close();
  }
}
