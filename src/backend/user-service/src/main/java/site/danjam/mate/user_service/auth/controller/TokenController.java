package site.danjam.mate.user_service.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.danjam.mate.user_service.auth.service.ReissueTokenService;
import site.danjam.mate.user_service.domain.user.domain.Role;
import site.danjam.mate.user_service.global.common.dto.ApiResponseMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-service/api/token")
public class TokenController {

    private final ReissueTokenService reissueTokenService;

    @PostMapping("reissue")
    public ResponseEntity<ApiResponseMessage> reissueToken(
            HttpServletRequest request,
            HttpServletResponse response)
    {
        reissueTokenService.reissueToken(request,response);
        return ResponseEntity.ok(ApiResponseMessage.of("정상적으로 재발급 되었습니다."));
    }
}
