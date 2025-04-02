package site.danjam.mate.user_service.auth.security;


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
import java.util.Iterator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;
import site.danjam.mate.common.annotation.MethodDescription;
import site.danjam.mate.common.exception.Code;
import site.danjam.mate.common.response.ApiResponseError;
import site.danjam.mate.user_service.auth.domain.RefreshToken;
import site.danjam.mate.user_service.auth.dto.LoginInputDTO;
import site.danjam.mate.user_service.auth.service.RefreshTokenService;
import site.danjam.mate.user_service.domain.certification.domain.Certification;
import site.danjam.mate.user_service.domain.certification.repository.CertificationRepository;
import site.danjam.mate.common.exception.global.RequiredArgumentException;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final CertificationRepository certificationRepository;
    private final RefreshTokenService refreshTokenService;
    private final Long EXPIRED_MS = 86400000L*100L; //todo - 토큰 만료시간 변경 필요

    // 생성자에서 경로 설정
    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, RefreshTokenService refreshTokenService, CertificationRepository certificationRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
        this.setFilterProcessesUrl("/user-service/api/login"); // 경로 설정
        this.certificationRepository = certificationRepository;
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
            throw new RequiredArgumentException(Code.USER_REQUIRED_ARGUMENT, "아이디, 비밀번호를 모두 입력해주세요");
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
        Certification certification = certificationRepository.findByUsername(authentication.getName());

        //반복자를 이용하여 role을 획득
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //토큰 생성
        String accessToken = jwtUtil.createJwt("access", certification.getUser().getId(), role, EXPIRED_MS);
        String refreshToken = jwtUtil.createJwt("refresh", certification.getId(), role, EXPIRED_MS);

        //Refresh 토큰 저장
        addRefreshToken(certification.getId(), refreshToken, EXPIRED_MS);

        //응답 설정
        response.setHeader("access", accessToken);
        response.addCookie(createCookie("refresh", refreshToken));
        response.setStatus(HttpStatus.OK.value());
    }


    @MethodDescription(description = "로그인 실패 시 실행되는 메소드")
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ResponseEntity<ApiResponseError> responseBody = ResponseEntity.badRequest().body(ApiResponseError.of(Code.USER_MISMATCH_ID_PW));
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(responseBody.getBody()));
    }

    //todo - 추후에 reissue에서도 사용되는 메서드 이므로 공통 메서드로 리팩토링해야함.
    @MethodDescription(description = "쿠키 생성 메소드")
    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge((EXPIRED_MS).intValue());
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    @MethodDescription(description = "RefreshToken 저장 메소드")
    private void addRefreshToken(Long userId, String refresh, Long expiredMs) {

        RefreshToken refreshToken = new RefreshToken(userId,refresh,expiredMs);

        refreshTokenService.saveRefreshToken(refreshToken);
    }
}