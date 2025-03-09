package site.danjam.mate.mate_service.global.feign;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import site.danjam.mate.mate_service.global.common.dto.ApiResponseData;
import site.danjam.mate.mate_service.global.feign.dto.UserFilterInputDTO;
import site.danjam.mate.mate_service.global.feign.dto.UserFilterOutputDTO;

@FeignClient(name="user-service")
public interface UserInquiryFeignClient {

    @PostMapping("/user-service/api/user/inquiry/filters")
    ResponseEntity<ApiResponseData<List<UserFilterOutputDTO>>> getUserFilterOutputList(@RequestBody UserFilterInputDTO userFilterInputDTO);
}
