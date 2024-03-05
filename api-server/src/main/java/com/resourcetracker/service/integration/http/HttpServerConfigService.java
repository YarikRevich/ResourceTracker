package com.resourcetracker.service.integration.http;

import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.service.config.ConfigService;
import io.quarkus.vertx.http.HttpServerOptionsCustomizer;
import io.vertx.core.http.HttpServerOptions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/** Provides http server configuration used as a source of properties for all defined resources. */
@ApplicationScoped
public class HttpServerConfigService implements HttpServerOptionsCustomizer {
    @Inject
    ConfigService configService;

    /**
     * @see HttpServerOptionsCustomizer
     */
    @Override
    public void customizeHttpServer(HttpServerOptions options) {
        ConfigEntity.Connection connection = configService.getConfig().getConnection();

        options.setHost(connection.getHost());
        options.setPort(connection.getPort());
    }
}
