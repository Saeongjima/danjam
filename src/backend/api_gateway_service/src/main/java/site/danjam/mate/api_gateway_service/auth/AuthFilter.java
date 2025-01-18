//package site.danjam.mate.api_gateway_service.auth;
//
//import java.util.List;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import site.danjam.mate.api_gateway_service.global.common.annotation.MethodDescription;
//
//@Slf4j
//@Component
//public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
//
//    private final JWTUtil jwtUtil;
//
//    @Autowired
//    public AuthFilter(JWTUtil jwtUtil){
//        super(AuthFilter.Config.class);
//        this.jwtUtil = jwtUtil;
//    }
//
////    @MethodDescription(description = "필터 동작을 동적으로 설정할 수 있는 Config 클래스. 현재는 사용하지 않는다.")
//    public static class Config{
//    }
//
//    @Override
//    public GatewayFilter apply(AuthFilter.Config config){
//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            List<String> accessToken = jwtUtil.getHeaderToken(request, "access");
//
//        }
//    }
//
//}
