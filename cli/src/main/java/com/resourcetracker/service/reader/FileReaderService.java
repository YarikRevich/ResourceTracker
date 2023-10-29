package com.resourcetracker.service.reader;

import com.resourcetracker.service.config.ConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileReaderService {
    private static final Logger logger = LogManager.getLogger(FileReaderService.class);

    String readFile(String path) {
         File file = new File(path);
         if (!file.exists()) {
             logger.fatal(new IOException().getMessage());
         }

         BufferedReader reader;
         try {
             reader = new BufferedReader(new FileReader(file));
         } catch (FileNotFoundException e) {
             throw new RuntimeException(e);
         }

         String data = null;

         try {
             data = reader.readLine();
         } catch (IOException e) {
             logger.fatal(e.getMessage());
         }

         return data;
    }
}
