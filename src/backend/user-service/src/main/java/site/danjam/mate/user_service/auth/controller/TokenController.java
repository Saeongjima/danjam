package site.danjam.mate.user_service.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.danjam.mate.common.response.ApiResponseMessage;
import site.danjam.mate.user_service.auth.service.ReissueTokenService;

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
