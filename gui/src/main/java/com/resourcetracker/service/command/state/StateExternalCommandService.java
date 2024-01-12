package com.resourcetracker.service.command.state;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.model.TopicLogsResult;
import com.resourcetracker.service.client.command.LogsClientCommandService;
import com.resourcetracker.service.command.ICommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/** Represents state external command service. */
@Service
public class StateExternalCommandService implements ICommand {
  private static final Logger logger = LogManager.getLogger(StateExternalCommandService.class);

  @Autowired private LogsClientCommandService logsClientCommandService;

  /**
   * @see ICommand
   */
  public void process() throws ApiServerException {
    TopicLogsResult topicLogsResult;

    try {
      topicLogsResult = logsClientCommandService.process(null);
    } catch (WebClientResponseException e) {
      throw new ApiServerException(
          new ApiServerNotAvailableException(e.getResponseBodyAsString()).getMessage());
    }

    ObjectMapper mapper = new ObjectMapper();
    try {
      System.out.println(mapper.writeValueAsString(topicLogsResult));
    } catch (JsonProcessingException e) {
      logger.fatal(e.getMessage());
    }
  }
}
