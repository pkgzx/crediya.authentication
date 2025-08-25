package com.creadiya.authentication.api.config.path;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "routes.paths.permission")
public class PermissionPath {
  private String create;
  private String list;
}
