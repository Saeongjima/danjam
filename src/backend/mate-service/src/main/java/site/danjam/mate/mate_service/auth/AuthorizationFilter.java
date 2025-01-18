package site.danjam.mate.mate_service.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import site.danjam.mate.mate_service.global.common.annotation.MethodDescription;
import site.danjam.mate.mate_service.global.exception.BaseException;
import site.danjam.mate.mate_service.global.exception.Code;

@Slf4j
@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    @MethodDescription(description = "사용자 인증 필터. Mate 서비스는 모든 요청에 대해 AuthUser 권한이 필요하다.")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String auth = request.getHeader("Auth");

        if(!"true".equals(auth)){
            throw new BaseException(Code.ACCESS_DENIED, Code.ACCESS_DENIED.getMessage());
        }

        // 헤더에서 userId와 Role 추출
        String userId = request.getHeader("userId");
        String role = request.getHeader("Role");

        // 헤더가 없는 경우 예외 발생
        if (userId == null || role == null) {
            throw new BaseException(Code.REQUIRED_LOGIN, Code.REQUIRED_LOGIN.getMessage());
        }

        // Role 검증
        if (!"AuthUser".equals(role)) {
            throw new BaseException(Code.ACCESS_DENIED, Code.ACCESS_DENIED.getMessage());
        }

        // 로그 기록
        log.info("Authorized request: userId={}, role={}", userId, role);

        // 요청 필터 체인 계속 진행
        filterChain.doFilter(request, response);
    }
}
