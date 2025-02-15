package site.danjam.mate.user_service.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.danjam.mate.user_service.auth.domain.RefreshToken;
import site.danjam.mate.user_service.auth.exception.ExpiredTokenException;
import site.danjam.mate.user_service.auth.exception.NotValidTokenException;
import site.danjam.mate.user_service.auth.jwt.JWTUtil;
import site.danjam.mate.user_service.auth.repository.RefreshTokenRepository;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;
import site.danjam.mate.user_service.global.exception.BadRequestException;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTUtil jwtUtil;
    @MethodDescription(description = "리프레시 토큰 삭제")
    @Transactional
    public void logout(String refreshToken) {
        // 1. Refresh Token 유효성 검사
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new BadRequestException("리프레시 토큰이 요청에 존재하지 않습니다.");
        }

        // 2. Refresh Token 만료 여부 확인
        if (jwtUtil.isExpired(refreshToken)) {
            throw new ExpiredTokenException("리프레시 토큰이 만료되었습니다.");
        }

        // 3. Refresh Token이 맞는지 확인
        String category = jwtUtil.getCategory(refreshToken);
        if (!"refresh".equals(category)) {
            throw new BadRequestException("리프레시 토큰이 아닙니다.");
        }

        // 4. Refresh Token이 DB에 존재하는지 확인
        if (!refreshTokenRepository.isExistToken(refreshToken)) {
            throw new NotValidTokenException("존재하지 않는 리프레시 토큰입니다.");
        }

        // 5. Refresh Token 삭제
        refreshTokenRepository.deleteRefreshToken(refreshToken);
    }

    @MethodDescription(description = "refresh 토큰 저장 메서드")
    @Transactional
    public void saveRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }
}

