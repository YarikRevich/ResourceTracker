package com.resourcetracker.resource;

import com.resourcetracker.api.ValidationResourceApi;
import com.resourcetracker.model.Provider;
import com.resourcetracker.model.ValidationCredentialsApplicationResult;
import com.resourcetracker.model.ValidationScriptApplicationResult;

/**
 * Contains implementation of ValidationResource.
 */
public class ValidationResource implements ValidationResourceApi {
    /**
     * Implementation for declared in OpenAPI configuration v1CredentialsAcquirePost method.
     * @param provider remote cloud provider to be selected for processing.
     * @param _file given file to be processed.
     * @return Credentials validation result.
     */
    @Override
    public ValidationCredentialsApplicationResult v1CredentialsAcquirePost(Provider provider, String _file) {
        return ValidationCredentialsApplicationResult.of(false, null);
    }

    /**
     * Implementation for declared in OpenAPI configuration v1ScriptAcquirePost method.
     * @param _file given file to be processed.
     * @return Script validation result.
     */
    @Override
    public ValidationScriptApplicationResult v1ScriptAcquirePost(String _file) {
        System.out.println(_file);
        return null;
    }
}
