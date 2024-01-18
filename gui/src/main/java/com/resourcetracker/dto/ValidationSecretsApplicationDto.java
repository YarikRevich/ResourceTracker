package com.resourcetracker.dto;

import com.resourcetracker.model.Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** Represents secrets validation application used for secrets acquiring process. */
@Getter
@AllArgsConstructor(staticName = "of")
public class ValidationSecretsApplicationDto {
  private Provider provider;

  private String filePath;
}
