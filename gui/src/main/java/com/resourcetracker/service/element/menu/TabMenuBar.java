package com.resourcetracker.service.element.menu;

import com.resourcetracker.service.element.IElement;
import java.util.UUID;

import com.resourcetracker.service.element.storage.ElementStorage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import org.springframework.stereotype.Service;

/** Represents tab menu bar. */
@Service
public class TabMenuBar implements IElement<MenuBar> {
  UUID id = UUID.randomUUID();

  public TabMenuBar() {
    Menu preferenciesMenu = new Menu("Preferences");
    Menu helpMenu = new Menu("Help");

    MenuBar menuBar = new MenuBar();

    menuBar.getMenus().addAll(preferenciesMenu, helpMenu);
    menuBar.useSystemMenuBarProperty().set(true);

    ElementStorage.setElement(id, menuBar);
  }

  public MenuBar getContent() {
    return ElementStorage.getElement(id);
  }
}
