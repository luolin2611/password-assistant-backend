package cn.rollin.passwordassistant.common.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送简单文本邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            log.info("简单邮件发送成功！to={}", to);
        } catch (Exception e) {
            log.error("发送简单邮件时发生异常！", e);
        }
    }

    /**
     * 发送HTML邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content HTML内容
     */
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
            log.info("HTML邮件发送成功！to={}", to);
        } catch (MessagingException e) {
            log.error("发送HTML邮件时发生异常！", e);
        }
    }
}
