package com.resourcetracker.service.scheduler;

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

  //
  //  // then, when you want to schedule a task
  //  Runnable task = ....
  //          executor.schedule(task, 5, TimeUnit.SECONDS);
  //
  //// and finally, when your program wants to exit
  // executor.shutdown();

  /**
   * Schedules given task with the specified period.
   *
   * @param callback task to be scheduled.
   * @param period period of the task execution.
   */
  public static void scheduleTask(Runnable callback, Integer period) {
    scheduledExecutorService.scheduleAtFixedRate(
        () -> executorService.execute(callback), 0, period, TimeUnit.MILLISECONDS);
  }

  /**
   * Schedules given task with the specified delay.
   *
   * @param callback task to be scheduled.
   * @param period delay of the task execution.
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
