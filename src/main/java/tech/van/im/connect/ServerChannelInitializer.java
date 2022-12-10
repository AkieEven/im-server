package tech.van.im.connect;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.van.im.handler.HandlerFactory;
import tech.van.im.handler.ServerHandler;
import tech.van.im.transfer.MessageProtobuf;

@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    ServerHandler serverHandler;
    @Autowired
    HandlerFactory handlerFactory;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        //拆包封包过程
        channelPipeline.addLast("frameEncoder", new LengthFieldPrepender(2));
        channelPipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
        channelPipeline.addLast(new ProtobufDecoder(MessageProtobuf.Msg.getDefaultInstance()));
        channelPipeline.addLast(new ProtobufEncoder());

        handlerFactory.init();
        //业务handler
        channelPipeline.addLast(serverHandler);
    }
}
