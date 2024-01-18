package com.resourcetracker.service.element.list;

import com.resourcetracker.model.TopicLogsResult;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.IElementActualizable;
import com.resourcetracker.service.element.IElementResizable;
import com.resourcetracker.service.element.list.cell.ListVisualizerCell;
import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.event.state.LocalState;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import org.springframework.stereotype.Service;

/** Represents list visualizer for deployment data. */
@Service
public class ListVisualizer
    implements IElementResizable, IElementActualizable, IElement<ListView<ListVisualizerCell>> {
  UUID id = UUID.randomUUID();

  public ListVisualizer() {
    ListView<ListVisualizerCell> listView = new ListView<>();

    ElementStorage.setElement(id, listView);
    ElementStorage.setActualizable(this);
  }

  /**
   * @see IElement
   */
  @Override
  public ListView<ListVisualizerCell> getContent() {
    return ElementStorage.getElement(id);
  }

  /**
   * @see IElementResizable
   */
  @Override
  public void handlePrefWidth() {
    getContent().setPrefWidth(LocalState.getMainWindowWidth());
  }

  /**
   * @see IElementResizable
   */
  @Override
  public void handlePrefHeight() {
    getContent().setPrefHeight(LocalState.getMainWindowHeight());
  }

  /**
   * @see IElementActualizable
   */
  @Override
  public void handleBackgroundUpdates() {
    TopicLogsResult deploymentState = LocalState.getDeploymentState();
    if (!Objects.isNull(deploymentState)) {
      List<ListVisualizerCell> items =
          deploymentState.getResult().stream()
              .map(element -> new ListVisualizerCell(element.getName(), ""))
              .toList();

      ObservableList<ListVisualizerCell> myObservableList = FXCollections.observableList(items);
      getContent().setItems(myObservableList);
    }
  }
}
