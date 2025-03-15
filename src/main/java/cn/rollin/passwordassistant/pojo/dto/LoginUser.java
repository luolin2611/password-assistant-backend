package cn.rollin.passwordassistant.pojo.dto;

import lombok.Data;

/**
 * 登录用户
 *
 * @author rollin
 * @since 2025-01-15 23:01:01
 */
@Data
public class LoginUser {
    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 头像URL
     */
    private String avatarUrl;
}
