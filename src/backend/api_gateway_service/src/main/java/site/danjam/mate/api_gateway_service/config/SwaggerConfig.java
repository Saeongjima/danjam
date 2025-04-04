package site.danjam.mate.api_gateway_service.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("단잠 API")
                        .version("1.0.0")
                        .description("단잠 API 명세서")
                )
                .addSecurityItem(new SecurityRequirement().addList("AccessHeader"))
                .components(new Components().addSecuritySchemes("AccessHeader",
                        new SecurityScheme()
                                .name("access")
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .description("커스텀 access 헤더를 통해 access token 전달")
                ));
    }
}
