package com.resourcetracker.service.element.scene.settings;

import com.resourcetracker.service.element.IElementResizable;
import com.resourcetracker.service.element.menu.TabMenuBar;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import org.springframework.stereotype.Service;

@Service
public class SettingsLanguagesScene implements IElementResizable<Scene> {
  private final Scene settingsLanguagesScene;

  public SettingsLanguagesScene() {
    TabMenuBar tabMenuBar = new TabMenuBar();

    var label = new Label("Hello world!");

    Group group = new Group();
    group.getChildren().add(tabMenuBar.getContent());
    group.getChildren().add(label);

    group.minHeight(200);
    group.minWidth(300);

    this.settingsLanguagesScene = new Scene(group);
  }

  public Scene getContent() {
    return settingsLanguagesScene;
  }

  /**
   * @param value window width value.
   */
  @Override
  public void prefWidth(Double value) {}

  /**
   * @param value window height value.
   */
  @Override
  public void prefHeight(Double value) {}
}
