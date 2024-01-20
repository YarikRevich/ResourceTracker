package com.resourcetracker.service.client.kafkastarter;

import com.resourcetracker.service.client.kafkastarter.common.IKafkaStarterClientService;
import java.net.URI;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

/** Represents Kafka starter service client facade. */
public class KafkaStarterClientServiceFacade {
  private final IKafkaStarterClientService kafkaStarterClientService;

  public KafkaStarterClientServiceFacade(String host, Integer port) {
    kafkaStarterClientService =
        RestClientBuilder.newBuilder()
            .baseUri(URI.create(String.format("http://%s:%d", host, port)))
            .build(IKafkaStarterClientService.class);
  }

  /**
   * Sends query to the Kafka starter client to deploy Kafka with the given external host.
   *
   * @param host external host to be used as advertised listener.
   */
  public void deploy(String host) {
    kafkaStarterClientService.qDeployPost(host);
  }
}
