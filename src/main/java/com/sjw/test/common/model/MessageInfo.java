package com.sjw.test.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by Sunny on 2019/6/27 11:20
 */
@Getter
@Setter
public class MessageInfo {
    //@NameCN("发件人地址")
    private String  from;

    //@NameCN("收件人地址")
    private List<String> to;

    //@NameCN("发送时间")
    private Date sendDate;

    //@NameCN("邮件主题")
    private String subject;

    //@NameCN("消息正文")
    private String msg;

}
