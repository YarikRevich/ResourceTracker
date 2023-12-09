package com.resourcetracker.service.element.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public class TabMenuBar {
  private final MenuBar menuBar;

  public TabMenuBar() {
    Menu preferenciesMenu = new Menu("Preferences");
    Menu helpMenu = new Menu("Help");

    MenuBar menuBar = new MenuBar();

    menuBar.getMenus().addAll(preferenciesMenu, helpMenu);
    menuBar.useSystemMenuBarProperty().set(true);

    this.menuBar = menuBar;
  }

  public MenuBar getContent() {
    return menuBar;
  }
}
