package tech.van.im.handler;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.van.im.transfer.MessageProtobuf;
import tech.van.im.transfer.MessageProtobufType;
import tech.van.im.util.channel.ChannelManager;


@Slf4j
@Component
public class HeartbeatHandler implements ServerEventHandler{

    @Autowired
    ChannelManager channelManager;
    @Override
    public void handle(MessageProtobuf.Msg msg, ChannelHandlerContext context) {
        log.info("receive heartbeat from client ID:{}",msg.getFromId());
        channelManager.findChannelByUserId(Long.valueOf(msg.getFromId())).writeAndFlush(msg);
    }
}
