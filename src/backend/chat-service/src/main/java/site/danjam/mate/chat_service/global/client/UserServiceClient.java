package site.danjam.mate.chat_service.global.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import site.danjam.mate.chat_service.global.client.dto.UserInfoDTO;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/user-service/api/user/inquiry/info/{id}")
    UserInfoDTO getUserInfoById(@PathVariable("id") Long userId);

    @GetMapping("/user-service/api/user/inquiry/info/{username}")
    UserInfoDTO getUserInfoByUsername(@PathVariable("username") String username);

    @GetMapping("/user-service/api/user/inquiry/info/ids")
    List<UserInfoDTO> getUserInfoByIds(@RequestParam("userIds") List<Long> userIds);
}

