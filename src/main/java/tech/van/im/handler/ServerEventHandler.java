package tech.van.im.handler;

import io.netty.channel.ChannelHandlerContext;
import tech.van.im.transfer.MessageProtobuf;

public interface ServerEventHandler {
    void handle(MessageProtobuf.Msg msg, ChannelHandlerContext context);
}
