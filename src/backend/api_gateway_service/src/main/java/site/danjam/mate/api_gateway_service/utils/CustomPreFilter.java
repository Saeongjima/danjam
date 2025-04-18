package site.danjam.mate.api_gateway_service.utils;

import java.util.logging.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CustomPreFilter implements GlobalFilter{

	private static final Logger logger = Logger.getLogger(CustomPreFilter.class.getName());

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		logger.info("Pre Filter: Request URI is " + request.getURI());
		// Add any custom logic here

		return chain.filter(exchange);
	}
}
