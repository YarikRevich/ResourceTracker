package com.resourcetracker.converter;

import com.opencsv.bean.CsvToBeanBuilder;
import com.resourcetracker.exception.SecretsConversionException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class SecretsConverter {
  /**
   * Converts given credentials CSV file to a certain object. Exposed as a static method to be used
   * with Terraform command definitions.
   *
   * @param file given file stream to be processed.
   * @return converted credentials.
   * @throws SecretsConversionException if any operation in conversion flow failed.
   */
  @SuppressWarnings("unchecked")
  public static <T> List<T> convert(Class<T> obj, InputStream file)
      throws SecretsConversionException {
    try {
      return new CsvToBeanBuilder(new BufferedReader(new InputStreamReader(file)))
          .withType(obj.getDeclaredConstructor().newInstance().getClass())
          .withIgnoreLeadingWhiteSpace(true)
          .build()
          .parse();
    } catch (InstantiationException
        | IllegalAccessException
        | NoSuchMethodException
        | InvocationTargetException e) {
      throw new SecretsConversionException(e.getMessage());
    }
  }
}
