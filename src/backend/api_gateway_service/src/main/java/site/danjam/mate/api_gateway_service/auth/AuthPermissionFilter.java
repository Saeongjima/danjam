package site.danjam.mate.api_gateway_service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import site.danjam.mate.api_gateway_service.auth.AuthPermissionFilter.Config;
import site.danjam.mate.common.annotation.MethodDescription;
import site.danjam.mate.common.exception.BaseException;
import site.danjam.mate.common.exception.Code;
import site.danjam.mate.common.exception.global.ExpiredTokenException;
import site.danjam.mate.common.exception.global.InvalidTokenException;
import site.danjam.mate.common.exception.global.RequiredArgumentException;
import site.danjam.mate.common.response.ApiResponseError;

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
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();

            List<String> accessToken;
            // 요청 객체에서 Access 토큰을 가져온다.
            try {
                accessToken = jwtUtil.getHeaderToken(request, "access");
                // Access 토큰이 없다면 401 에러를 반환한다.
                if (accessToken == null || accessToken.isEmpty() || accessToken.get(0).trim().isEmpty()){
                    throw new RequiredArgumentException(Code.GATEWAY_REQUIRED_LOGIN);
                }

                // Access 토큰 검증
                if (!jwtUtil.isValidToken(accessToken.get(0))) {
                    if (jwtUtil.isExpired(accessToken.get(0))) {
                        throw new ExpiredTokenException(Code.GATEWAY_EXPIRED_TOKEN);
                    } else {
                        throw new InvalidTokenException(Code.GATEWAY_INVALID_TOKEN);
                    }
                }
            } catch (BaseException e) {
                return onError(exchange, e.getErrorCode());
            } catch (Exception e) {
                return onError(exchange, Code.INTERNAL_SERVER_ERROR);
            }
            // 사용자 정보를 헤더에 추가
            String userId = jwtUtil.getUserId(accessToken.get(0));
            String role = jwtUtil.getRole(accessToken.get(0));

            // 사용자 정보를 헤더에 추가
            ServerHttpRequest authRequest = request.mutate()
                    .header("Auth", "true")
                    .header("X-USER-ID", userId)
                    .header("X-USER-ROLE", role)
                    .build();

            return chain.filter(exchange.mutate().request(authRequest).build());
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, Code code) {
        exchange.getResponse().setStatusCode(code.getStatus());
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ApiResponseError response = ApiResponseError.of(code, code.getMessage());
        byte[] bytes;
        try {
            bytes = new ObjectMapper().writeValueAsBytes(response);
        } catch (JsonProcessingException e) {
            bytes = "{}".getBytes();
        }

        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}
