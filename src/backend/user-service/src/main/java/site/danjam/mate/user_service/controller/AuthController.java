package site.danjam.mate.user_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.danjam.mate.user_service.dto.SignUpRequestDTO;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;
import site.danjam.mate.user_service.global.common.dto.ApiResponseMessage;
import site.danjam.mate.user_service.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-service/api/users")
public class AuthController {

    private final UserService userService;

    @MethodDescription(description = "회원가입")
    @PostMapping("")
    public ResponseEntity<ApiResponseMessage> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        userService.registerUser(signUpRequestDTO.getUsername(), signUpRequestDTO.getPassword());
        return ResponseEntity.ok(new ApiResponseMessage("회원가입 성공"));
    }

}
