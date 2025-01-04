package site.danjam.mate.user_service.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import site.danjam.mate.user_service.domain.user.service.UserService;
import site.danjam.mate.user_service.global.common.dto.ApiResponseMessage;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("signup")
    public ResponseEntity<ApiResponseMessage> signup() {
        return ResponseEntity.ok(userService.signup());
    }
}
