package site.danjam.mate.user_service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('AUTH_USER')")
    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request, @AuthenticationPrincipal GlobalCustomUserDetails customUserDetails) {
        log.info("Server Port : {}", request.getServerPort());
        Long userId = customUserDetails.getUserId();
        String role = customUserDetails.getRole();
        String role2 = customUserDetails.getAuthorities().toString();
        return "Welcome to User Service : " + request.getServerPort() + "\nuserId : " +userId + "\nrole : "+ role + "\nrole2 : "+ role2;
    }

}
