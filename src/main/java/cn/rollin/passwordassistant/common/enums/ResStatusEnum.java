package cn.rollin.passwordassistant.common.enums;

import lombok.Getter;

/**
 * 响应码 - 枚举
 *
 * @author rollin
 * @since 2024-12-27 23:56:52
 */
@Getter
public enum ResStatusEnum {

    /**
     * 通用 - 请求成功共用
     */
    SUCCESS("SUC0000", "请求成功。"),

    /**
     * 通用 - 请求失败共用
     */
    FAILURE("ERM0000", "请求失败，请稍后重试。"),

    CHECK_CODE_EFFECTIVE("ERM0001", "验证码还在生效中，稍后再试。"),
    CHECK_CODE_INVALID("ERM0002", "验证码错误，请检查。"),
    REGISTER_USER_REPEAT("ERM0003", "用户已存在，请检查。"),
    REGISTER_EMAIL_REPEAT("ERM0004", "电子邮箱已存在，请检查。"),
    REGISTER_USER_ERROR("ERM0005", "用户未注册，或者密码错误。"),
    EMAIL_EMPTY("ERM0002", "电子邮箱不能为空。"),
    TOKEN_EMPTY("ERM0003", "认证信息不能为空"),
    RECORD_NOT_FOUND("ERM0006", "记录不存在"),
    TOKEN_INVALID("ERM0007", "认证信息无效"),
    FILE_UPLOAD_ERROR("ERM0008", "文件上传失败"),
    FILE_DOWNLOAD_ERROR("ERM0009", "文件下载失败"),
    ;


    /**
     * 响应码：前两位表示微服务(例如：01，表示用户微服务)，后四位表示错误码
     */
    private final String code;

    private final String message;

    ResStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
