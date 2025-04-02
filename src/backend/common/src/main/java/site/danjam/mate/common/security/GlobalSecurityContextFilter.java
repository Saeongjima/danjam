package site.danjam.mate.common.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class GlobalSecurityContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String role = request.getHeader("X-USER-ROLE");
        String userId = request.getHeader("X-USER-ID");

        if (role != null && userId != null) {
            try {
                Long id = Long.parseLong(userId);
                GlobalCustomUserDetails customUserDetails = new GlobalCustomUserDetails(role, id);
                Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null,
                        customUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (NumberFormatException e) {
                // 로그만 남기고 계속 진행
            }
        }
        filterChain.doFilter(request, response);
    }
}