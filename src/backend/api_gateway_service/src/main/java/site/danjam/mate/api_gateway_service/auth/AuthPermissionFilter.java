package site.danjam.mate.api_gateway_service.auth;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import site.danjam.mate.api_gateway_service.MethodDescription;
import site.danjam.mate.api_gateway_service.auth.AuthPermissionFilter.Config;
import site.danjam.mate.api_gateway_service.global.exception.BaseException;
import site.danjam.mate.api_gateway_service.global.exception.Code;

@Slf4j
@Component
public class AuthPermissionFilter extends AbstractGatewayFilterFactory<Config> {

    private final JWTUtil jwtUtil;

    @Autowired
    public AuthPermissionFilter(JWTUtil jwtUtil) {
        super(AuthPermissionFilter.Config.class);
        this.jwtUtil = jwtUtil;
    }

    // 필터에 필요한 설정이 있다면 여기에 추가
    public static class Config {
    }

    @MethodDescription(description = "사용자의 권한을 확인하는 필터")
    @Override
    public GatewayFilter apply(AuthPermissionFilter.Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            // 요청 객체에서 Access 토큰을 가져온다.
            List<String> accessToken = jwtUtil.getHeaderToken(request, "access");

            // Access 토큰이 없다면 401 에러를 반환한다.
            if (accessToken == null || accessToken.isEmpty()){
                throw new BaseException(Code.REQUIRED_LOGIN, Code.REQUIRED_LOGIN.getMessage());
            }

            // Access 토큰 검증
            if(!jwtUtil.isValidToken(accessToken.get(0))){
                if(jwtUtil.isExpired(accessToken.get(0))){
                    throw new BaseException(Code.EXPIRED_TOKEN, Code.EXPIRED_TOKEN.getMessage());
                } else {
                    throw new BaseException(Code.INVALID_TOKEN, Code.INVALID_TOKEN.getMessage());
                }
            }

            // 사용자 정보를 헤더에 추가
            Long userId = jwtUtil.getUserId(accessToken.get(0));
            String role = jwtUtil.getRole(accessToken.get(0));

            // 사용자 정보를 헤더에 추가
            ServerHttpRequest authRequest = request.mutate()
                    .header("Auth", "true")
                    .header("userId", String.valueOf(userId))
                    .header("role", role)
                    .build();

            return chain.filter(exchange.mutate().request(authRequest).build());
        };
    }
}
