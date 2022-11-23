package tech.van.im.entity;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private Long msgId;
    private Long msgFrom;
    private Long msgTo;
    private String msgContent;
    private Integer msgType;
    private Date msgSendTime;
    private Integer msgStatus;
}
