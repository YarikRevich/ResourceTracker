package com.resourcetracker.service.terraform.workspace;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.exception.WorkspaceUnitFileNotFound;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import lombok.SneakyThrows;

/** Represents local Terraform state workspace for different users. */
@ApplicationScoped
public class WorkspaceService {
  @Inject PropertiesEntity properties;

  /**
   * Creates unit key from the given segments.
   *
   * @param segments given segments to be used for unit key creation.
   * @return created unit key from the given segments.
   */
  @SneakyThrows
  public String createUnitKey(String... segments) {
    MessageDigest md = MessageDigest.getInstance("SHA3-256");
    return DatatypeConverter.printHexBinary(md.digest(String.join(".", segments).getBytes()));
  }

  /**
   * Creates workspace unit with the help of the given key.
   *
   * @param key given workspace unit key.
   * @throws IOException if IO operation failed.
   */
  public void createUnitDirectory(String key) throws IOException {
    Path unitDirectoryPath = Path.of(properties.getWorkspaceDirectory(), key);

    if (Files.notExists(unitDirectoryPath)) {
      Files.createDirectory(unitDirectoryPath);
    }
  }

  /**
   * Removes  workspace unit with the help of the given key.
   * @param key given workspace unit key.
   * @throws IOException if IO operation failed.
   */
  public void removeUnitDirectory(String key) throws IOException {
    Files.deleteIfExists(Path.of(properties.getWorkspaceDirectory(), key));
  }

  /**
   * Retrieves path for the workspace unit with the help of the given key.
   *
   * @param key given workspace unit key.
   * @throws WorkspaceUnitFileNotFound if workspace unit with the given name does not exist.
   */
  public String getUnitDirectory(String key) throws WorkspaceUnitFileNotFound {
    Path unitDirectoryPath = Path.of(properties.getWorkspaceDirectory(), key);

    if (Files.notExists(unitDirectoryPath)) {
      throw new WorkspaceUnitFileNotFound();
    }

    return unitDirectoryPath.toString();
  }
}
