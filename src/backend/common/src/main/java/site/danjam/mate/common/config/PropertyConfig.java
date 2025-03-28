package site.danjam.mate.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:/properties/env.properties", ignoreResourceNotFound = true) // 파일이 없으면 무시
public class PropertyConfig {
}