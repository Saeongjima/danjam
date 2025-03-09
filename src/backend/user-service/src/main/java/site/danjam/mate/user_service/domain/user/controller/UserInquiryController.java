package site.danjam.mate.user_service.domain.user.controller;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.danjam.mate.user_service.domain.user.dto.UserFilterInputDTO;
import site.danjam.mate.user_service.domain.user.dto.UserFilterOutputDTO;
import site.danjam.mate.user_service.domain.user.service.UserFilterService;
import site.danjam.mate.user_service.domain.user.service.UserInquiryService;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;
import site.danjam.mate.user_service.global.common.dto.ApiResponseData;

@RestController
@RequestMapping("user-service/api/user/inquiry")
@RequiredArgsConstructor()
public class UserInquiryController {

    private final UserInquiryService userInquiryService;
    private final UserFilterService userFilterService;

    @MethodDescription(description = "외부 서비스에서 username을 바탕으로 userId를 조죄할 때 사용합니다.")
    @GetMapping
    public ResponseEntity<ApiResponseData<Long>> getUserIdByUsername(@RequestParam String username) {
        return ResponseEntity.ok(ApiResponseData.of(userInquiryService.getUserId(username), "User Id 조회 성공"));
    }

    @MethodDescription(description = "외부 서비스에서 userId 리스트(1차필터링)와 필터링 항목들을 바탕으로 필터링할 때 사용합니다.")
    @PostMapping("/filters")
    public ResponseEntity<ApiResponseData<List<UserFilterOutputDTO>>> getUserFilterOutputList(
        @RequestBody UserFilterInputDTO userFilterInputDTO
    ){
        return ResponseEntity.ok(ApiResponseData.of(userFilterService.getUserListByFilters(
                userFilterInputDTO), "유저 필터링 조회 성공"));
    }
}
