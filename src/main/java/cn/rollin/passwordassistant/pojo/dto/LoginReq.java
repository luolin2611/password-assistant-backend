package cn.rollin.passwordassistant.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录请求体
 *
 * @author rollin
 * @since 2025-01-15 22:38:23
 */
@Data
public class LoginReq {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;


    /**
     * 登录密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
