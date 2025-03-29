package site.danjam.mate.user_service.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import site.danjam.mate.common.annotation.MethodDescription;
import site.danjam.mate.common.security.GlobalSecurityContextFilter;
import site.danjam.mate.user_service.auth.security.CustomLogoutFilter;
import site.danjam.mate.user_service.auth.service.LogoutService;
import site.danjam.mate.user_service.auth.service.RefreshTokenService;
import site.danjam.mate.user_service.domain.user.repository.UserRepository;
import site.danjam.mate.user_service.auth.security.JWTUtil;
import site.danjam.mate.user_service.auth.security.LoginFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final RefreshTokenService refreshTokenService;
    private final LogoutService logoutService;
    private final GlobalSecurityContextFilter globalSecurityContextFilter;


    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil,
                          RefreshTokenService refreshTokenService,
                          UserRepository userRepository,
                          ObjectMapper objectMapper,
                          LogoutService logoutService,
                          GlobalSecurityContextFilter globalSecurityContextFilter) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.logoutService = logoutService;
        this.globalSecurityContextFilter = globalSecurityContextFilter;
    }

    @MethodDescription(description = "AuthenticationManager를 빈으로 등록한다.")
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterCHain(HttpSecurity http) throws Exception {

        //csrf disable. 세션방식에서는 항상 고정되기 때문에 방어해야 하지만, jwt방식은 stateless하기 때문에 disable해도 된다.
        http
                .csrf((auth) -> auth.disable());
        //Form 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

        //httpBasic 로그인 방식 disable
        http
                .httpBasic((auth) -> auth.disable());

        http
                .authorizeHttpRequests((auth) -> auth
                        .anyRequest().permitAll());

        //로그인 필터 추가
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil,
                        refreshTokenService, this.userRepository), UsernamePasswordAuthenticationFilter.class);

        // GlobalSecurityContextFilter 추가
        http
                .addFilterBefore(globalSecurityContextFilter, UsernamePasswordAuthenticationFilter.class);

        //로그아웃 필터 추가
        http
                .addFilterBefore(new CustomLogoutFilter(objectMapper, refreshTokenService,logoutService ), LogoutFilter.class);

        //세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
