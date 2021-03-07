package com.controller.dto;

import lombok.Data;

/**
 * 通知服务——提供发送邮件功能的实体
 * @author roger
 * @since  2020/11/5 16:48
 */
@Data
public class SendEmailDTO {
    /**
     * 邮件的内容
     */
    private String content;
    private String subject;
}
