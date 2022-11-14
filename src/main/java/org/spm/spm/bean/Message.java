package org.spm.spm.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private String name;
    private String msg;
    private Date time;
}
