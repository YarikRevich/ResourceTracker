package com.resourcetracker.service.scheduler;

import com.resourcetracker.service.scheduler.storage.SchedulerStorage;

import java.util.UUID;
import java.util.concurrent.*;

/** Provides access to task schedule opportunities. */
public class SchedulerHelper {
  private static final ScheduledExecutorService scheduledExecutorService =
      Executors.newSingleThreadScheduledExecutor();
  private static final ExecutorService executorService =
      Executors.newVirtualThreadPerTaskExecutor();

  /**
   * Schedules given task with the specified period.
   *
   * @param callback task to be scheduled.
   * @param period period of the task execution.
   */
  public static void scheduleTask(Runnable callback, Integer period) {
    ScheduledFuture<?> element = scheduledExecutorService.scheduleAtFixedRate(
            () -> executorService.execute(callback), 0, period, TimeUnit.MILLISECONDS);

    SchedulerStorage.setThread(UUID.randomUUID(), element);
  }

  /**
   * Schedules given task with the specified delay.
   *
   * @param callback task to be scheduled.
   * @param delay delay of the task execution.
   */
  public static void scheduleTimer(Runnable callback, Integer delay) {
    scheduledExecutorService.schedule(
        () -> executorService.execute(callback), delay, TimeUnit.MILLISECONDS);
  }

  /** Closes schedulers and finishes awaited tasks. */
  public static void close() {
    scheduledExecutorService.close();
    executorService.close();
  }
}
