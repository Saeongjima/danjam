package site.danjam.mate.api_gateway_service.utils;

import java.util.logging.Logger;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CustomPostFilter implements GlobalFilter{

	private static final Logger logger = Logger.getLogger(CustomPostFilter.class.getName());

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			ServerHttpResponse response = exchange.getResponse();
			logger.info("Post Filter: Response status code is " + response.getStatusCode());
			// Add any custom logic here
		}));
	}
}