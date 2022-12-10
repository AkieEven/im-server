package tech.van.im.connect;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ServerChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import tech.van.im.config.NettyConfig;

@Component
@Slf4j
public class ServerBoot implements ApplicationRunner, ApplicationListener<ContextClosedEvent> {
    private final ServerBootstrap serverBootstrap;
    private final NettyConfig nettyConfig;

    private Channel serverChannel;


    public ServerBoot(final ServerBootstrap serverBootstrap, final NettyConfig nettyConfig) {
        this.serverBootstrap = serverBootstrap;
        this.nettyConfig = nettyConfig;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            Channel channel = serverBootstrap.bind(nettyConfig.getPort()).sync().channel();
            if(channel != null) {
                this.serverChannel = channel;
                log.info("server boot successfully in {}",channel.remoteAddress());
                channel.closeFuture().sync();
            }
        } catch (Exception e) {
            log.error("server boot error : {}",e.getMessage());
        }
        finally {
            serverBootstrap.config().childGroup().shutdownGracefully();
            serverBootstrap.config().group().shutdownGracefully();
        }
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        if(serverChannel != null) {
            serverChannel.close();
        }
        log.info("server stop ...");
    }
}
