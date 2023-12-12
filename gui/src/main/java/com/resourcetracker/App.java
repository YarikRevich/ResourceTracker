package com.resourcetracker;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.observer.ElementObserver;
import com.resourcetracker.service.element.stage.MainStage;
import com.resourcetracker.service.event.integrated.StageReadyEvent;
import com.resourcetracker.service.event.state.LocalState;
import com.resourcetracker.service.resource.observer.ResourceObserver;
import com.resourcetracker.service.scheduler.SchedulerHelper;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class App extends Application {
  private ConfigurableApplicationContext applicationContext;

  @Autowired private PropertiesEntity properties;

  @Autowired private LocalState localState;

  @Autowired private ElementObserver elementObserver;

  @Autowired private ResourceObserver resourceObserver;

  @Autowired private MainStage mainStage;

  /** Launches application by internal application state. */
  public void launch() {
    Application.launch();
  }

  /** Responsible for the initialization of OS related functionality. */
  @Override
  public void init() {
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

    System.setProperty("apple.laf.useScreenMenuBar", "true");
  }

  /** Stops internal application by internal calls. */
  @Override
  public void stop() {
    applicationContext.close();
    SchedulerHelper.close();
    Platform.exit();
  }

  /**
   * @param stage
   */
  @Override
  public void start(Stage stage) {
    mainStage.getContent().show();

    applicationContext.publishEvent(new StageReadyEvent(mainStage.getContent()));
  }
}
