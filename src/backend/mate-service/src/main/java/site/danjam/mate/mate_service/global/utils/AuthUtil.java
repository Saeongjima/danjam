package site.danjam.mate.mate_service.global.utils;

public class AuthUtil {
    public static boolean checkAuthUser(String role){
        return role.equals("ROLE_AUTH_USER");
    }
}
