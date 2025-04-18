package site.danjam.mate.user_service.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info()
                .title("단잠 user-service API")
                .version("1.0.0")
                .description("단잠 user-service API 명세서");

        // SecuritySecheme명
        String jwtSchemeName = "access";
        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes("access",
                        new SecurityScheme()
                                .name("access")                        // 실제 헤더 키
                                .type(SecurityScheme.Type.APIKEY)     // APIKEY 타입 사용
                                .in(SecurityScheme.In.HEADER)         // Header에 넣겠다
                                .description("Access 헤더에 토큰 입력"));


        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components)
                .servers(List.of(new Server().url("http://localhost:8600")));
    }
}
