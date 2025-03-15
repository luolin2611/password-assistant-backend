package cn.rollin.passwordassistant.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 注册用户请求体
 *
 * @author rollin
 * @since 2024-12-28 22:35:34
 */
@Data
public class RegisterReq {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    private String email;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String verifyCode;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
