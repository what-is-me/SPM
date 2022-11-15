package org.spm.spm.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private String name;
    private String msg;
    @JsonFormat(pattern = "yyyy年MM月dd日HH时mm分ss秒", locale = "zh", timezone = "GMT+8")
    private Date time;
}
