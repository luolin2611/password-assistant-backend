package cn.rollin.passwordassistant.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录响应体
 *
 * @author rollin
 * @since 2025-03-15 11:06:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRes {

    /**
     * 用户信息
     */
    private LoginUser user;

    /**
     * 令牌
     */
    private String token;
}
