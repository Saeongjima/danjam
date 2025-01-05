package site.danjam.mate.user_service.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.danjam.mate.user_service.domain.user.dto.JoinDto;
import site.danjam.mate.user_service.domain.user.service.UserService;
import site.danjam.mate.user_service.global.common.dto.ApiResponseMessage;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = "signup", consumes = {"multipart/form-data", "application/json"})
    public ResponseEntity<ApiResponseMessage> signup(@RequestPart @Valid JoinDto joinDto,
                                                     @RequestPart MultipartFile authImgFile) throws Exception {
        return ResponseEntity.ok(ApiResponseMessage.of(userService.signup(joinDto, authImgFile)));
    }
}
