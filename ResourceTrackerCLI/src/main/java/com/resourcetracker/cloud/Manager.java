package com.resourcetrackersdk.cloud;

import org.javatuples.Pair;

import com.resourcetrackersdk.config.*;
import com.resourcetrackersdk.decorators.log.*;
import com.resourcetrackersdk.cloud.providers.*;
import com.resourcetrackersdk.tools.exceptions.CloudError;

public class Manager {
    private final Config config = Config.getInstance();

    Provider provider;

    public Manager() throws Throwable {
        if (config.isCloud()) {
            switch (config.getCloudProvider()) {
                case AWS:
                    provider = new AWS();
                case GCP:
                    provider = new GCP();
                case AZ:
                    provider = new AZ();
                case NONE:
                    throw new CloudError("'NONE' not available for initalisation");
            }
            Pair<String, String> credentials = config.getCloudProviderCredentials();
            provider.init(credentials);
            Log.activateCloudBackuping(credentials);
        }else{
            Log.cleanCloudBackuping();
        }
    }
}
