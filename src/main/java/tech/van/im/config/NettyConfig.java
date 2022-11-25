package tech.van.im.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties(prefix = "netty")
public class NettyConfig {
    /**
     * boss线程数，如不设置，Netty框架中默认为2*CPU
     */
    private Integer boss;
    /**
     * worker线程数，如不设置，Netty框架中默认为2*CPU
     */
    private Integer worker;
    /**
     * 连接超时时间，默认为20s
     */
    private Integer timeout = 30000;
    /**
     * 服务器端口 默认为1007
     */
    private Integer port = 1007;
}
