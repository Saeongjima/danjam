//package site.danjam.mate.mate_service.config;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import site.danjam.mate.mate_service.global.common.dto.ApiResponseData;
//
//@FeignClient(name = "user-service", path = "user-service/api/user/inquiry")
//public interface UserInquiryFeignClient {
//
//    @GetMapping
//    ResponseEntity<Object> getUserIdByUsername(@RequestParam("username") String username);
//}
