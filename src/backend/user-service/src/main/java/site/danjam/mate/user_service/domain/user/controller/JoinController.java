package site.danjam.mate.user_service.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import site.danjam.mate.user_service.domain.user.dto.JoinDTO;
import site.danjam.mate.user_service.domain.user.service.JoinService;
import site.danjam.mate.user_service.global.common.dto.ApiResponseMessage;

@RestController
@RequestMapping("user-service/api")
@RequiredArgsConstructor()
public class JoinController {
    private final JoinService joinService;

    @PostMapping(value = "/signup", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponseMessage> signup(@RequestPart @Valid JoinDTO joinDto,
                                                     @RequestPart(required = false) MultipartFile authImgFile)
            throws Exception {
        return ResponseEntity.ok(ApiResponseMessage.of(joinService.signup(joinDto, authImgFile)));
    }
}
