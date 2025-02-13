package site.danjam.mate.user_service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-service")
@Slf4j
public class HelloController {

    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request){
        log.info("Server Port : {}", request.getServerPort());
        String userId = request.getHeader("userId");
        String role = request.getHeader("role");
        return "Welcome to User Service : " + request.getServerPort() + "\nuserId : " +userId + "\nrole : "+ role;
    }

}
