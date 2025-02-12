package site.danjam.mate.user_service.domain.user.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.danjam.mate.user_service.domain.user.service.UserInquiryService;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;
import site.danjam.mate.user_service.global.common.dto.ApiResponseData;

@RestController
@RequestMapping("user-service/api/user/inquiry")
@RequiredArgsConstructor()
public class UserInquiryController {
    private final UserInquiryService userInquiryService;

    @MethodDescription(description = "외부 서비스에서 username을 바탕으로 userId를 조죄할 때 사용합니다.")
    @GetMapping
    public ResponseEntity<ApiResponseData<Long>> getUserIdByUsername(@RequestParam String username) {
        return ResponseEntity.ok(ApiResponseData.of(userInquiryService.getUserId(username), "User Id 조회 성공"));
    }
}
