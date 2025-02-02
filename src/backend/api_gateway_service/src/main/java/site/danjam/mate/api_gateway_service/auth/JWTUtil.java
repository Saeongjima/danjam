package site.danjam.mate.api_gateway_service.auth;

import java.nio.charset.StandardCharsets;
import io.jsonwebtoken.Jwts;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import site.danjam.mate.api_gateway_service.global.common.annotation.MethodDescription;

@Component
public class JWTUtil {

    private final SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    @MethodDescription(description = "HTTP 헤더에서 토큰을 추출한다.")
    public List<String> getHeaderToken(ServerHttpRequest request, String headerName) {
        return request.getHeaders().getOrDefault(headerName, Collections.emptyList());
    }

    @MethodDescription(description = "토큰의 유효성을 확인한다.")
    public boolean isValidToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;  // 서명 검증 성공
        } catch (Exception e) {
            return false;  // 서명 실패 또는 기타 예외 발생
        }
    }

    @MethodDescription(description = "토큰이 만료되었는지 확인한다.")
    public Boolean isExpired(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    @MethodDescription(description = "토큰 검증과정에서 발생한 에러를 처리한다.")
    public Mono<Void> onError(ServerWebExchange exchange, String errMsg, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // JSON 형식의 에러 메시지 생성
        String errorResponse = String.format("{\"message\": \"%s\", \"status\": %d}", errMsg, status.value());
        DataBuffer buffer = response.bufferFactory().wrap(errorResponse.getBytes(StandardCharsets.UTF_8));

        // 클라이언트로 응답 반환
        return response.writeWith(Mono.just(buffer));
    }

    @MethodDescription(description = "토큰에서 username을 추출한다.")
    public String getUsername(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username",String.class);
    }

    @MethodDescription(description = "토큰에서 role을 추출한다.")
    public String getRole(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role",String.class);
    }

    @MethodDescription(description = "토큰에서 category를 추출한다.")
    public String getCategory(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category",String.class);
    }


}
