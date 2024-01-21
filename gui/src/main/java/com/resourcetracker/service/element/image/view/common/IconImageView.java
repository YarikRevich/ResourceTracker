package com.resourcetracker.service.element.image.view.common;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.exception.ApplicationImageFileNotFoundException;
import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.element.text.common.IElement;
import com.resourcetracker.service.element.text.common.IElementActualizable;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;
import javafx.scene.image.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Represents main icon image view. */
@Service
public class IconImageView implements IElement<Image> {
  UUID id = UUID.randomUUID();

  public IconImageView(@Autowired PropertiesEntity properties)
      throws ApplicationImageFileNotFoundException {
    InputStream imageIconURL =
        getClass().getClassLoader().getResourceAsStream(properties.getImageIconName());
    if (Objects.isNull(imageIconURL)) {
      throw new ApplicationImageFileNotFoundException();
    }

    Image image = new Image(imageIconURL);

    ElementStorage.setElement(id, image);
  }

  /**
   * @see IElementActualizable
   */
  @Override
  public Image getContent() {
    return ElementStorage.getElement(id);
  }
}
