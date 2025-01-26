package site.danjam.mate.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;
import site.danjam.mate.user_service.jwt.JWTUtil;
import site.danjam.mate.user_service.jwt.LoginFilter;
import site.danjam.mate.user_service.repository.RefreshTokenRepository;
import site.danjam.mate.user_service.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil,
                          RefreshTokenRepository refreshTokenRepository,
                          CustomUserDetailsService customUserDetailsService) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    @MethodDescription(description = "AuthenticationManager를 빈으로 등록한다.")
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @MethodDescription(description = "비밀번호 암호화 메소드. 해당 메서드가 반환하는 객체가 빈으로 등록된다.")
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
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
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshTokenRepository), UsernamePasswordAuthenticationFilter.class);

//        //로그아웃 필터 추가
//        http
//                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshTokenRepository), LogoutFilter.class);

        //세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
