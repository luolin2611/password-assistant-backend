package cn.rollin.passwordassistant.pojo.dto;

import lombok.Data;

/**
 * 发送邮件DTO
 *
 * @author rollin
 * @since 2024-12-28 08:05:15
 */
@Data
public class SendEmailDto {

    /**
     * 收件人
     */
    private String recipient;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;
}
