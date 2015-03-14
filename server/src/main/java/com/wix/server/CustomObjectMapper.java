/**
 *
 */
package com.wix.server;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomObjectMapper extends ObjectMapper {

    private static final long serialVersionUID = 1L;

    public CustomObjectMapper() {
        super();
        // getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        setVisibilityChecker(getSerializationConfig()
                .getDefaultVisibilityChecker()
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
    }

}
