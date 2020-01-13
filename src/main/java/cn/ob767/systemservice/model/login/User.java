package cn.ob767.systemservice.model.login;

import cn.ob767.systemservice.model.ModelTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends ModelTemplate implements Serializable {

    private static final long serialVersionUID = 403631776508577639L;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 用户密码
     */
    private String userPassword;
}
