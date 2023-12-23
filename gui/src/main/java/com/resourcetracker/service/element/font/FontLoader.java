package com.resourcetracker.service.element.font;

import com.resourcetracker.exception.ApplicationFontFileNotFoundException;
import com.resourcetracker.service.element.common.ElementHelper;
import jakarta.annotation.PostConstruct;
import java.net.URL;
import java.util.Objects;
import javafx.scene.text.Font;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/** Represents font loader */
@EnableAsync
@Component
public class FontLoader {
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
  public static void loadFont() {
    URL url = ElementHelper.class.getClassLoader().getResource("e-Ukraine-UltraLight.otf");
    if (Objects.isNull(url)) {
      throw new ApplicationFontFileNotFoundException();
    }

    FontLoader.setFont20(Font.loadFont(url.toExternalForm(), 20));

    FontLoader.setFont12(Font.loadFont(url.toExternalForm(), 12));
  }
}
