package cn.ob767.systemservice.controller;

import cn.ob767.systemservice.interceptor.ResponseInterceptor;
import cn.ob767.systemservice.mapper.UserMapper;
import cn.ob767.systemservice.md5.MD5;
import cn.ob767.systemservice.model.login.User;
import cn.ob767.systemservice.utils.RedisUtils;
import cn.ob767.systemservice.utils.enumerations.ResponseStatus;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class test {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/set")
    public ResponseInterceptor<JSONObject> set() {
        User user = new User();
        user.setUserEmail("gaoxi@meicai.com");
        user.setUserName("gaoxi");
        user.setUserPassword(MD5.getMD5("#Gxob767318000"));
        user.setIsDelete(0);
        user.setCreateTime(System.currentTimeMillis() / 1000);
        user.setUpdateTime(System.currentTimeMillis() / 1000);
        redisUtils.redisSet("name", user, 10L);
        ResponseInterceptor<JSONObject> responseInterceptor = new ResponseInterceptor<>(ResponseStatus.SUCCESS);
        JSONObject userJson = JSONObject.parseObject(JSONObject.toJSONString(user));
        responseInterceptor.setData(userJson);
        return responseInterceptor;
    }

    @PostMapping("/get")
    public String get() {
        return String.valueOf(redisUtils.redisGet("name"));
    }

    @PostMapping("/register")
    public String register() {
        if (userMapper.selectUser("gaoxi@meicai.cn") != 0) {
            return "账号已注册";
        } else {
            User user = new User();
            user.setUserEmail("gaoxi@meicai.cn");
            user.setUserName("gaoxi");
            user.setUserPassword(MD5.getMD5("#Gxob767318000"));
            user.setIsDelete(0);
            user.setCreateTime(System.currentTimeMillis() / 1000);
            user.setUpdateTime(System.currentTimeMillis() / 1000);
            return String.valueOf(userMapper.insertUser(user));
        }
    }

    @PostMapping("/login")
    public boolean login() {
        String password = userMapper.selectPassword("gaoxi@meicai.cn");
        return password.equals(MD5.getMD5("#Gxob7673180001"));
    }
}
