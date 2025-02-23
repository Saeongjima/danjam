package site.danjam.mate.user_service.global.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import site.danjam.mate.user_service.domain.user.domain.Role;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;
import site.danjam.mate.user_service.global.exception.AccessDeniedException;

@Aspect
@Component
public class AuthUserAspect {
    @MethodDescription(description = "CheckAuthorization Annotation을 사용한 메소드에 대한 권한 체크. role이 AUTH_USER가 아닌 경우 접근 거부. 메서드의 마지막 인자로 role을 받아야 함.")
    @Before("@annotation(checkAuth) && args(.., userRole)")
    public void beforeMethod(JoinPoint joinPoint, RequiredAuthUser checkAuth, String userRole) {
        if(!userRole.equals(Role.AUTH_USER.getRoleValue())){
            throw new AccessDeniedException();
        }
    }
}