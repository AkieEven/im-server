package tech.van.im.connect;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.van.im.config.NettyConfig;

@Configuration
@EnableConfigurationProperties(NettyConfig.class)
public class NettyServerComponent {

    private final NettyConfig nettyConfig;
    private final ServerChannelInitializer serverChannelInitializer;

    public NettyServerComponent(final NettyConfig nettyConfig, final ServerChannelInitializer serverChannelInitializer) {
        this.nettyConfig = nettyConfig;
        this.serverChannelInitializer = serverChannelInitializer;
    }

    @Bean
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup(nettyConfig.getBoss());
    }

    @Bean
    public NioEventLoopGroup workerGroup() {
        return nettyConfig.getWorker() == null ? new NioEventLoopGroup() : new NioEventLoopGroup(nettyConfig.getWorker());
    }

    @Bean
    public ServerBootstrap serverBootstrap() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup(),workerGroup())
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childOption(ChannelOption.TCP_NODELAY,true)
                .childHandler(serverChannelInitializer);
        return serverBootstrap;
    }
}
