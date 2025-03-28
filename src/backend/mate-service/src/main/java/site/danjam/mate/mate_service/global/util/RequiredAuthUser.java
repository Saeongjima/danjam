package site.danjam.mate.mate_service.global.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 해당 어노테이션이 메소드에 붙어있을 경우, 해당 메소드는 인증된 사용자(AUTH_USER)만 접근 가능하다.
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiredAuthUser {
}
