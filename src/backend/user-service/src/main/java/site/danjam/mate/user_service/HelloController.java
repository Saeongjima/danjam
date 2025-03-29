package site.danjam.mate.user_service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.danjam.mate.common.security.GlobalCustomUserDetails;

@RestController
@RequestMapping("/user-service/api")
@Slf4j
public class HelloController {

    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request, @AuthenticationPrincipal GlobalCustomUserDetails customUserDetails) {
        log.info("Server Port : {}", request.getServerPort());
        Long userId = customUserDetails.getUserId();
        String role = customUserDetails.getRole();
        return "Welcome to User Service : " + request.getServerPort() + "\nuserId : " +userId + "\nrole : "+ role;
    }

}
