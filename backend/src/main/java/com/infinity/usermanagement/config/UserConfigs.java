package com.infinity.usermanagement.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "couchbase-doc-maintainer.user-management.user-resource")
@Data
@Configuration
public class UserConfigs {

    private String classIdValue;

    private String initialValue;
}