package com.resourcetracker.entity;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Getter
@ApplicationScoped
public class PropertiesEntity {
    @ConfigProperty(name = "terraform.directory")
    public String terraformDirectory;
}
