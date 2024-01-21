package com.resourcetracker;

import com.resourcetracker.service.client.observer.ResourceObserver;
import com.resourcetracker.service.element.font.FontLoader;
import com.resourcetracker.service.element.observer.ElementObserver;
import com.resourcetracker.service.element.stage.MainStage;
import com.resourcetracker.service.event.state.LocalState;
import com.resourcetracker.service.scheduler.SchedulerHelper;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/** Represents entrypoint for the application. */
public class App extends Application {
  private ConfigurableApplicationContext applicationContext;

  @Autowired private LocalState localState;

  @Autowired private ElementObserver elementObserver;

  @Autowired private ResourceObserver resourceObserver;

  @Autowired private FontLoader fontLoader;

  @Autowired private MainStage mainStage;

  /**
   * @see Application
   */
  public void launch() {
    Application.launch();
  }

  /**
   * @see Application
   */
  @Override
  public void init() {
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    System.setProperty("apple.awt.UIElement", "true");

    ApplicationContextInitializer<GenericApplicationContext> initializer =
        applicationContext -> {
          applicationContext.registerBean(Application.class, () -> App.this);
          applicationContext.registerBean(Parameters.class, this::getParameters);
          applicationContext.registerBean(HostServices.class, this::getHostServices);
        };

    applicationContext =
        new SpringApplicationBuilder()
            .sources(GUI.class)
            .initializers(initializer)
            .run(getParameters().getRaw().toArray(new String[0]));
  }

  /**
   * @see Application
   */
  @Override
  public void stop() {
    applicationContext.close();
    SchedulerHelper.close();
    Platform.exit();
  }

  /**
   * @see Application
   */
  @Override
  @SneakyThrows
  public void start(Stage stage) {
    mainStage.getContent().show();
  }
}
