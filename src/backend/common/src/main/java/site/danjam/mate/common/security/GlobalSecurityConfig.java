package site.danjam.mate.common.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class GlobalSecurityConfig {

    private final GlobalSecurityContextFilter globalSecurityContextFilter;

    @Bean
    public SecurityFilterChain globalSecurityFilterChain(HttpSecurity http) throws Exception {

        // CORS 설정 todo: 특정 도메인만 허용하도록 수정해야함
        http
                .cors((cors) -> cors
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.addAllowedOriginPattern("*");
                            config.addAllowedMethod("*");
                            config.addAllowedHeader("*");
                            config.setAllowCredentials(true);
                            return config;
                        }))
                .csrf(csrf -> csrf.disable());

        http
                .csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())  // 로그인 페이지 비활성화
                .httpBasic(httpBasic -> httpBasic.disable())  // HTTP 기본 인증 비활성화
                .logout(logout -> logout.disable())  // 로그아웃 기능 비활성화
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED))  // 인증 실패 시 401 반환
                )
                .authorizeHttpRequests((auth) -> auth
                        .anyRequest().permitAll())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(globalSecurityContextFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
