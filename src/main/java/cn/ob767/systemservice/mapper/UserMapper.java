package cn.ob767.systemservice.mapper;

import cn.ob767.systemservice.model.login.User;
import org.springframework.stereotype.Component;

@Component(value = "userMapper")
public interface UserMapper {

    int insertUser(User user);

    String selectPassword(String userEmail);

    Integer selectUser(String userEmail);
}
