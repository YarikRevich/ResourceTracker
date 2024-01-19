package com.resourcetracker.service.visualization;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.visualization.state.VisualizationState;
import java.util.concurrent.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Represents visualization service used to indicate current execution steps. */
@Service
public class VisualizationService {
  @Autowired private PropertiesEntity properties;

  @Autowired private VisualizationState visualizationState;

  private final ScheduledExecutorService scheduledExecutorService =
      Executors.newScheduledThreadPool(2);

  private final CountDownLatch latch = new CountDownLatch(1);

  /** Starts progress visualization processor. */
  public void process() {
    scheduledExecutorService.scheduleAtFixedRate(
        () -> {
          if (visualizationState.getLabel().isNext()) {
            System.out.println(visualizationState.getLabel().getCurrent());
          }

          if (visualizationState.getLabel().isEmpty()) {
            latch.countDown();
          }
        },
        0,
        properties.getProgressVisualizationPeriod(),
        TimeUnit.MILLISECONDS);
  }

  /** Awaits for visualization service to end its processes. */
  @SneakyThrows
  public void await() {
    latch.await();

    System.out.print("\n");

    visualizationState.getResult().forEach(System.out::println);
  }
}
