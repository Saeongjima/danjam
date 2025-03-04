package site.danjam.mate.chat_service.global.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.danjam.mate.chat_service.global.client.dto.UserInfoDTO;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/user-service/api/user/info")
    UserInfoDTO getUserInfo(@RequestParam("userId") Long userId);

    @GetMapping("/user-service/api/user/info")
    UserInfoDTO getUserInfo(@RequestParam("username") String username);

    @GetMapping("/user-service/api/user/info")
    List<UserInfoDTO> getUserInfo(@RequestParam("userIds") List<Long> userIds);
}

