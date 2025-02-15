package site.danjam.mate.user_service.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.user_service.auth.domain.RefreshToken;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @MethodDescription(description = "기존 리프레시 토큰을 삭제하는 메서드.")
    public boolean deleteRefreshToken(String refresh){
        refreshTokenJpaRepository.deleteByRefresh(refresh);
        return true;
    }

    @MethodDescription(description = "해당 refreshToken이 DB에 존재하는지 확인")
    public boolean isExistToken(String refresh){
        return refreshTokenJpaRepository.existsByRefresh(refresh);
    }

    @MethodDescription(description = "refreshToken 저장")
    public boolean save(RefreshToken refreshToken){
        refreshTokenJpaRepository.save(refreshToken);
        return true;
    }
}
