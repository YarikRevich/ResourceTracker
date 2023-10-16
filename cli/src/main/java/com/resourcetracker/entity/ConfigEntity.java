package com.resourcetracker.entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.time.Duration;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Data;

/**
 * Model used for YAML configuration file parsing
 */
@Data
public class ConfigEntity {
    // Represents request, which will be executed on a remote machine
    public static class Request {
        public String name;

        public String file;

        public String run;

        // TODO: move this to separated service used to read script files
//        public void setData(String data) throws ConfigException {
//                    File file = new File(data);
//                    if (!file.exists()) {
//                        throw new ConfigException();
//                    }
//                    BufferedReader reader = null;
//                    try {
//                        reader = new BufferedReader(new FileReader(file));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        this.data = reader.readLine();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//            }
//        }
        @Pattern(regexp = "^((^(((\\d+)(s|m|h|d|w))))|(^once))$")
        public String frequency;
    }

    public List<Request> requests;

    @Data
    public static class Cloud {
        @JsonFormat(shape = JsonFormat.Shape.OBJECT)
        public enum Provider {
            @JsonProperty("aws")
            AWS,
        }

        public Provider provider;

        @Pattern(regexp = "^(((./)?)|((~/.)?)|((/?))?)([a-zA-Z/]*)((\\.([a-z]+))?)$")
        public String credentials;

        public String profile;
        public String region;
    };

    public Cloud cloud;

//    /**
//     * Formats raw data to context passed to
//     * TerraformAPI and then formats it to JSON string
//     * @deprecated Should be replaced with Protobuf generation
//     * of requested amount of data
//     */
//    public String toJSON() {
//        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//        String result = "";
//        try {
//            result = ow.writeValueAsString(this);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
}
