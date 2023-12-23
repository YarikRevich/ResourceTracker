package com.resourcetracker.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Represents default AWS secrets file structure. */
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class AWSSecretsDto {
  @CsvBindByPosition(position = 0)
  String accessKey;

  @CsvBindByPosition(position = 1)
  String secretKey;
}
