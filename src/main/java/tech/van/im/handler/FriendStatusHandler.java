package tech.van.im.handler;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tech.van.im.transfer.MessageProtobuf;

@Component
@Slf4j
public class FriendStatusHandler implements ServerEventHandler{
    @Override
    public void handle(MessageProtobuf.Msg msg, ChannelHandlerContext context) {
        //todo:finish
    }
}
