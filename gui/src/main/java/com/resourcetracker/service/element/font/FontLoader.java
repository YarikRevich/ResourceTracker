package com.resourcetracker.service.element.font;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.exception.ApplicationFontFileNotFoundException;
import com.resourcetracker.service.element.common.ElementHelper;
import jakarta.annotation.PostConstruct;
import java.net.URL;
import java.util.Objects;
import javafx.scene.text.Font;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/** Represents basic font loader. */
@Component
@EnableAsync
public class FontLoader {
  @Autowired private PropertiesEntity properties;

  @Setter private static Font font20;

  @Setter private static Font font12;

  @SneakyThrows
  public static Font getFont20() {
    return font20;
  }

  @SneakyThrows
  public static Font getFont12() {
    return font12;
  }

  @SneakyThrows
  @PostConstruct
  public void loadFont() {
    URL url = ElementHelper.class.getClassLoader().getResource(properties.getFontDefaultName());
    if (Objects.isNull(url)) {
      throw new ApplicationFontFileNotFoundException();
    }

    FontLoader.setFont20(Font.loadFont(url.toExternalForm(), 20));

    FontLoader.setFont12(Font.loadFont(url.toExternalForm(), 12));
  }
}
