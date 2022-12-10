package tech.van.im.handler;

import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.van.im.transfer.MessageProtobuf;

@Component
@ChannelHandler.Sharable
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    HandlerFactory handlerFactory;

    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {
        super.channelActive(context);
        log.info("client post a link in {}",context.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext context) throws Exception {
        super.channelInactive(context);
        log.info("client link from {} removed",context.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object o) {
        MessageProtobuf.Msg msg = (MessageProtobuf.Msg) o;
        log.info("receive message , type is : {}",msg.getMsgType());
        handlerFactory.getHandler(msg.getMsgType()).handle(msg,context);
    }

}
