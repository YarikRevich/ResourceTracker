package com.resourcetracker.service.terraform.provider;

import com.resourcetracker.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AWSProvider {
    @Autowired
    ConfigService configService;


}
