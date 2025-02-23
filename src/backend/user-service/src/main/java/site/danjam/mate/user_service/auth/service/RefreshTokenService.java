package site.danjam.mate.user_service.auth.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.danjam.mate.user_service.auth.domain.RefreshToken;
import site.danjam.mate.user_service.auth.exception.CanNotFindTokenException;
import site.danjam.mate.user_service.auth.exception.ExpiredTokenException;
import site.danjam.mate.user_service.auth.exception.InValidTokenException;
import site.danjam.mate.user_service.auth.exception.RefreshTokenOwnerMismatchException;
import site.danjam.mate.user_service.auth.jwt.JWTUtil;
import site.danjam.mate.user_service.auth.repository.RefreshTokenRepository;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;
import site.danjam.mate.user_service.global.exception.RequiredArgumentException;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTUtil jwtUtil;

    @MethodDescription(description = "refresh 토큰 저장 메서드")
    @Transactional
    public void saveRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }

    @MethodDescription(description = "쿠키에서 refresh 토큰 삭제")
    public void removeRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @MethodDescription(description = "쿠키에서 refresh 토큰 가져오기")
    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refresh".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        throw new RequiredArgumentException("쿠키에 refresh토큰이 존재하지 않습니다.");
    }

    @MethodDescription(description = "refreshToken을 검증한뒤 return한다.")
    public RefreshToken getRefreshTokenAfterCheck(String refreshToken){
        checkExpired(refreshToken);
        checkRefreshTokenCategory(refreshToken);
        return getRefreshTokenInDB(refreshToken);
    }

    @MethodDescription(description = "RefreshToken만료 여부 확인")
    private void checkExpired(String refreshToken){
        if (jwtUtil.isExpired(refreshToken)) {
            throw new ExpiredTokenException("리프레시 토큰이 만료되었습니다.");
        }
    }

    @MethodDescription(description = "RefreshToken이 맞는지 확인")
    private void checkRefreshTokenCategory(String refreshToken){
        String category = jwtUtil.getCategory(refreshToken);
        if (!"refresh".equals(category)) {
            throw new RequiredArgumentException("리프레시 토큰이 아닙니다.");
        }
    }

    @MethodDescription(description = "RefreshToken이 DB에 존재하는지 확인")
    public RefreshToken getRefreshTokenInDB(String refreshToken){
        return refreshTokenRepository.getRefreshTokenByValue(refreshToken)
                .orElseThrow(()->new CanNotFindTokenException("존재하지 않는 리프레시 토큰입니다."));

    }
}

