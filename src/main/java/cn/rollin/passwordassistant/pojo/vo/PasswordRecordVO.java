package cn.rollin.passwordassistant.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PasswordRecordVO {
    private Integer id;
    private Integer userId;
    private String platformName;
    private String platformIcon;
    private String description;
    private String password;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
} 