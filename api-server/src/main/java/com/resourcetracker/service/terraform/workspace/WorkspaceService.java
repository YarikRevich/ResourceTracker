package com.resourcetracker.service.terraform.workspace;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.entity.VariableFileEntity;
import com.resourcetracker.exception.WorkspaceUnitDirectoryNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.FileSystemUtils;

/** Represents local Terraform state workspace for different users. */
@ApplicationScoped
public class WorkspaceService {
  private static final Logger logger = LogManager.getLogger(WorkspaceService.class);

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
   * Removes workspace unit with the help of the given key.
   *
   * @param key given workspace unit key.
   * @throws IOException if IO operation failed.
   */
  public void removeUnitDirectory(String key) throws IOException {
    FileSystemUtils.deleteRecursively(Path.of(properties.getWorkspaceDirectory(), key));
  }

  /**
   * Checks if workspace unit directory with the help of the given key.
   *
   * @param key given workspace unit key.
   * @return result if workspace unit directory exists with the help of the given key.
   */
  public boolean isUnitDirectoryExist(String key) {
    return Files.exists(Paths.get(properties.getWorkspaceDirectory(), key));
  }

  /**
   * Retrieves path for the workspace unit with the help of the given key.
   *
   * @param key given workspace unit key.
   * @throws WorkspaceUnitDirectoryNotFoundException if workspace unit with the given name does not
   *     exist.
   */
  public String getUnitDirectory(String key) throws WorkspaceUnitDirectoryNotFoundException {
    Path unitDirectoryPath = Path.of(properties.getWorkspaceDirectory(), key);

    if (Files.notExists(unitDirectoryPath)) {
      throw new WorkspaceUnitDirectoryNotFoundException();
    }

    return unitDirectoryPath.toString();
  }

  /**
   * Writes variable file input to the given workspace unit directory.
   *
   * @param workspaceUnitDirectory given workspace unit directory.
   * @param input given variable file entity input.
   * @throws IOException if variable file cannot be created.
   */
  public void createVariableFile(String workspaceUnitDirectory, VariableFileEntity input)
      throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    File variableFile =
        new File(
            Paths.get(workspaceUnitDirectory, properties.getWorkspaceVariablesFileName())
                .toString());

    objectMapper.writeValue(variableFile, input);
  }

  /**
   * Checks if variable file exists in the given workspace unit directory.
   *
   * @param workspaceUnitDirectory given workspace unit directory.
   * @return result if variable file exists in the given workspace unit directory.
   */
  public boolean isVariableFileExist(String workspaceUnitDirectory) {
    return Files.exists(
        Paths.get(workspaceUnitDirectory, properties.getWorkspaceVariablesFileName()));
  }

  /**
   * Retrieves variable file content with the help of the given workspace unit directory.
   *
   * @param workspaceUnitDirectory given workspace unit directory.
   * @return variable file entity.
   * @throws FileNotFoundException if the requested variable not found.
   */
  public VariableFileEntity getVariableFileContent(String workspaceUnitDirectory)
      throws FileNotFoundException {
    ObjectMapper mapper =
        new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    ObjectReader reader = mapper.reader().forType(new TypeReference<VariableFileEntity>() {});

    InputStream variableFile =
        new FileInputStream(
            Paths.get(workspaceUnitDirectory, properties.getWorkspaceVariablesFileName())
                .toString());
    try {
      return reader.<VariableFileEntity>readValues(variableFile).readAll().getFirst();
    } catch (IOException e) {
      logger.fatal(e.getMessage());
    }

    return null;
  }
}
