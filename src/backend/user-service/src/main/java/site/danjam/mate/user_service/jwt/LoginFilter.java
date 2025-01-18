package site.danjam.mate.user_service.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;
import site.danjam.mate.user_service.domain.RefreshToken;
import site.danjam.mate.user_service.repository.RefreshTokenRepository;
import site.danjam.mate.user_service.dto.LoginInputDTO;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    // 생성자에서 경로 설정
    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, RefreshTokenRepository refreshTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
        this.setFilterProcessesUrl("/user-service/api/users/login"); // 경로 설정
    }

    @MethodDescription(description = "로그인 시도를 받아서 AuthenticationManager에게 전달한다.")
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        LoginInputDTO loginInputDTO;

        try {
            ObjectMapper objectMapper = new ObjectMapper();//json을 객체로 변환하기 위한 objectMapper
            ServletInputStream inputStream = request.getInputStream();//요청에서 데이터를 추출하기 위한 inputStream
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);//inputStream을 문자열로 변환
            loginInputDTO = objectMapper.readValue(messageBody, LoginInputDTO.class);//json을 객체로 변환
        }

        //json에 없는 필드가 들어왔을 때 400 에러를 반환
        catch (UnrecognizedPropertyException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String username = loginInputDTO.getUsername();
        String password = loginInputDTO.getPassword();

        //authenticationManager에게 username과 password를 검증하라고 요청
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authToken);
    }

    @MethodDescription(description = "로그인 성공 시 실행되는 메소드")
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) {

        //유저 정보
        String username = authentication.getName();
        //반복자를 이용하여 role을 획득
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //토큰 생성
        String accessToken = jwtUtil.createJwt("access", username, role, 86400000L);  // 1일 : 86400000L
        String refreshToken = jwtUtil.createJwt("refresh", username, role, 86400000L*30L);    // 30일 : 2592000000L

        //Refresh 토큰 저장
        addRefreshToken(username, refreshToken, 1800000L); // 30분

        //응답 설정
        response.setHeader("access", accessToken);
        response.addCookie(createCookie("refresh", refreshToken));
        response.setStatus(HttpStatus.OK.value());
    }


    @MethodDescription(description = "로그인 실패 시 실행되는 메소드")
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) {
        response.setStatus((401));
    }

    @MethodDescription(description = "쿠키 생성 메소드")
    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
//        cookie.setMaxAge(24*60*60); // 24시간
        cookie.setMaxAge(1800); // 30분
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    @MethodDescription(description = "RefreshToken 저장 메소드")
    private void addRefreshToken(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUsername(username);
        refreshToken.setRefresh(refresh);
        refreshToken.setExpiration(date.toString());

        refreshTokenRepository.save(refreshToken);
    }
}