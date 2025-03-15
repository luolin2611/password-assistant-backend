package cn.rollin.passwordassistant.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author rollin
 * @since 2024-12-27 23:53:06
 */
@Getter
@Setter
@Builder
@TableName("t_user")
public class User implements Serializable {

    /**
     * 用户ID，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 电子邮件地址
     */
    @TableField("email")
    private String email;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 密码盐值
     */
    @TableField("salt")
    private String salt;

    /**
     * 个性签名
     */
    @TableField("signature")
    private String signature;

    /**
     * 用户状态：0-有效，1-无效
     */
    @TableField("status")
    private String status;

    /**
     * 头像URL
     */
    @TableField("avatar_url")
    private String avatarUrl;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @TableField("updated_time")
    private LocalDateTime updatedTime;
}
