package site.danjam.mate.user_service.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import site.danjam.mate.common.config.JpaAuditingConfig;
import site.danjam.mate.common.config.PropertyConfig;
import site.danjam.mate.common.exception.GlobalExceptionHandler;
import site.danjam.mate.common.security.GlobalSecurityConfig;
import site.danjam.mate.common.security.GlobalSecurityContextFilter;

@Configuration
@Import({
        JpaAuditingConfig.class,
        PropertyConfig.class,
        GlobalSecurityContextFilter.class,
        GlobalExceptionHandler.class,
})
public class GlobalConfig {
}
