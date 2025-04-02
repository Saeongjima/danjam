package site.danjam.mate.user_service.domain.certification.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.danjam.mate.common.response.ApiResponseMessage;
import site.danjam.mate.user_service.domain.certification.service.JoinCheckService;
@RestController
@RequestMapping("user-service/api")
@RequiredArgsConstructor
public class JoinCheckController {
    private final JoinCheckService joinCheckService;

    @GetMapping("/username")
    public ResponseEntity<ApiResponseMessage> checkUsernameAvailability(@RequestParam("username") String username) {
        return ResponseEntity.ok(ApiResponseMessage.of(String.valueOf(joinCheckService.isUsernameDuplicate(username))));
    }

    @GetMapping("/email")
    public ResponseEntity<ApiResponseMessage> checkEmailAvailability(@RequestParam("email") String email) {
        return ResponseEntity.ok(ApiResponseMessage.of(String.valueOf(joinCheckService.isEmailDuplicate(email))));
    }

    //
    @GetMapping("/nickname")
    public ResponseEntity<ApiResponseMessage> checkNicknameAvailability(@RequestParam("nickname") String nickname) {
        return ResponseEntity.ok(ApiResponseMessage.of(String.valueOf(joinCheckService.isNicknameDuplicate(nickname))));
    }
}
