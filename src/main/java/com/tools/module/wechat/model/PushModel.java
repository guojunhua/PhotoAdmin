package com.tools.module.wechat.model;

import lombok.Data;

/**
 * @author 小柒2012
 */
@Data
public class PushModel {

    private String page;

    private String templateId;

    private String openid;

    private String title;

    private String time;

    private String author;

}
