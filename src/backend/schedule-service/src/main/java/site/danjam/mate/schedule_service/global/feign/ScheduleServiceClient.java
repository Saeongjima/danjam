package site.danjam.mate.schedule_service.global.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.danjam.mate.schedule_service.global.entity.ApiResponseData;

@FeignClient(name = "user-service")
public interface ScheduleServiceClient {
    @GetMapping("/user-service/api/user/inquiry")
    ResponseEntity<ApiResponseData<Long>> getUserIdByUsername(@RequestParam String username);
}

