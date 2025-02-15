package site.danjam.mate.user_service.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.GenericFilterBean;
import site.danjam.mate.user_service.auth.exception.ExpiredTokenException;
import site.danjam.mate.user_service.auth.exception.NotValidTokenException;
import site.danjam.mate.user_service.auth.service.RefreshTokenService;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;
import site.danjam.mate.user_service.global.common.dto.ApiResponseMessage;
import site.danjam.mate.user_service.global.common.dto.ApiResponseError;
import site.danjam.mate.user_service.global.exception.BadRequestException;
import site.danjam.mate.user_service.global.exception.Code;

@RequiredArgsConstructor
public class CustomLogoutFilter extends GenericFilterBean {

    private static final String LOGOUT_URL = "/user-service/api/logout"; // 로그아웃 URL 상수
    private final ObjectMapper objectMapper; // JSON 직렬화를 위한 ObjectMapper
    private final RefreshTokenService refreshTokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (!LOGOUT_URL.equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String refresh = getRefreshTokenFromCookie(request);

        try {
            // 🔥 Service에서 모든 검증 & 삭제 처리
            refreshTokenService.logout(refresh);

            // 로그아웃 성공 시 쿠키 삭제
            removeRefreshTokenCookie(response);
            sendSuccessResponse(response, HttpServletResponse.SC_OK, "성공적으로 로그아웃 되었습니다.");
        } catch (BadRequestException e) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage(), Code.BAD_REQUEST_EXCEPTION);
        } catch (ExpiredTokenException e) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage(), Code.EXPIRED_TOKEN);
        } catch (NotValidTokenException e) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage(), Code.INVALID_TOKEN);
        } catch (Exception e) {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 오류", Code.INTERNAL_SERVER_ERROR);
        }
    }

    @MethodDescription(description = "성공 응답을 생성하는 메서드")
    private void sendSuccessResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.getWriter().write(objectMapper.writeValueAsString(ApiResponseMessage.of(message)));
    }

    @MethodDescription(description = "에러 응답을 생성하는 메서드")
    private void sendErrorResponse(HttpServletResponse response, int status, String message, Code code) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.getWriter().write(objectMapper.writeValueAsString(ApiResponseError.of(code, message)));
    }

    @MethodDescription(description = "쿠키에서 refresh 토큰 삭제")
    private void removeRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @MethodDescription(description = "쿠키에서 refresh 토큰 가져오기")
    private String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refresh".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
