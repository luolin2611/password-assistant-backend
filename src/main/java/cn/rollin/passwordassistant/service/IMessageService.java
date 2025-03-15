package cn.rollin.passwordassistant.service;

/**
 * 发送消息服务
 *
 * @author rollin
 * @since 2024-12-28 07:31:48
 */
public interface IMessageService {
    void sendEmail(String to, String subject, String content);
}
