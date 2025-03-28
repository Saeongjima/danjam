package site.danjam.mate.mate_service.global.feign.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("site.danjam.mate.mate_service.global.feign")
class OpenFeignConfig {
}