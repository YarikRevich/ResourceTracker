package com.resourcetracker.entity;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * Represents default AWS credentials file structure.
 */
@Getter
@NoArgsConstructor
public class AWSCredentialsEntity {
    @CsvBindByPosition(position = 0)
    String accessKey;

    @CsvBindByPosition(position = 1)
    String secretKey;
}
