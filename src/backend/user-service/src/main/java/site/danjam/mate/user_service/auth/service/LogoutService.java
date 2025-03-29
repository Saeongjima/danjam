package site.danjam.mate.user_service.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.danjam.mate.common.annotation.MethodDescription;
import site.danjam.mate.user_service.auth.jwt.JWTUtil;
import site.danjam.mate.user_service.auth.repository.RefreshTokenRepository;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTUtil jwtUtil;

    @MethodDescription(description = "로그아웃 처리 한다.(리프레시 토큰 검증 및 삭제)")
    @Transactional
    public void logout(String refreshToken) {
        refreshTokenRepository.deleteRefreshToken(refreshTokenService.getRefreshTokenAfterCheck(refreshToken));
    }
}
