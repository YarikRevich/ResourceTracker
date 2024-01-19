package com.resourcetracker.service.visualization.common.label;

import com.resourcetracker.dto.VisualizationLabelDto;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.visualization.common.IVisualizationLabel;
import java.util.ArrayDeque;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Represents label set used for apply command service. */
@Service
public class VersionCommandVisualizationLabel implements IVisualizationLabel {
  private final ArrayDeque<VisualizationLabelDto> stepsQueue = new ArrayDeque<>();

  private final ArrayDeque<String> batchQueue = new ArrayDeque<>();

  private final ReentrantLock mutex = new ReentrantLock();

  public VersionCommandVisualizationLabel(@Autowired PropertiesEntity properties) {
    stepsQueue.add(
        VisualizationLabelDto.of(
            properties.getProgressVisualizationVersionInfoRequestLabel(), 100));
  }

  /**
   * @see IVisualizationLabel
   */
  @Override
  public boolean isEmpty() {
    return stepsQueue.isEmpty();
  }

  /**
   * @see IVisualizationLabel
   */
  @Override
  public boolean isNext() {
    mutex.lock();

    try {
      return !batchQueue.isEmpty();
    } finally {
      mutex.unlock();
    }
  }

  /**
   * @see IVisualizationLabel
   */
  @Override
  public void pushNext() {
    if (!isEmpty()) {
      mutex.lock();

      batchQueue.push(stepsQueue.pop().toString());

      mutex.unlock();
    }
  }

  /**
   * @see IVisualizationLabel
   */
  @Override
  public String getCurrent() {
    return batchQueue.pop();
  }
}
