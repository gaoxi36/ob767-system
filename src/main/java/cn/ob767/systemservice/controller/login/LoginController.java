package cn.ob767.systemservice.controller.login;

import cn.ob767.systemservice.mapper.UserMapper;
import cn.ob767.systemservice.md5.MD5;
import cn.ob767.systemservice.model.login.User;
import cn.ob767.systemservice.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/test")
    public boolean checkoutUser(HttpServletRequest request) {
        redisUtils.redisSet("gaoxi_token", "123456789", 100L);
        return true;
    }
}
