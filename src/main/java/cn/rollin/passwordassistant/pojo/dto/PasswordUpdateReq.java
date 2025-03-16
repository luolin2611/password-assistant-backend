package cn.rollin.passwordassistant.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PasswordUpdateReq {
    
    @NotNull(message = "记录ID不能为空")
    private Integer id;
    
    @Size(min = 1, max = 64, message = "平台名称长度必须在1-64之间")
    private String platformName;
    
    @Size(max = 1024, message = "平台图标URL长度不能超过1024")
    private String platformIcon;
    
    @Size(max = 256, message = "描述信息长度不能超过256")
    private String description;
    
    @Size(min = 1, max = 64, message = "密码长度必须在1-64之间")
    private String password;
} 