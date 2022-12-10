package tech.van.im.util.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ChannelManager {
    private final Map<Long, Channel> USER_TO_CHANNEL = new ConcurrentHashMap<>();

    /**
     * save channel and userId to a map
     * @param channel the channel that the user owns
     * @param userId the unique id of user
     * @return true if save successfully, else return false
     */
    public boolean save(Long userId, Channel channel) {
        if(channel != null) {
            USER_TO_CHANNEL.put(userId,channel);
            return true;
        }
        return false;
    }

    public Channel findChannelByUserId(Long userId) {
        return USER_TO_CHANNEL.get(userId);
    }

    public boolean removeIfConnectNoActive(Channel channel) {
        for(Map.Entry<Long,Channel> e : USER_TO_CHANNEL.entrySet()) {
            if(e.getValue() == channel && !channel.isActive()) {
                USER_TO_CHANNEL.remove(e.getKey());
                return true;
            }
        }
        return false;
    }


}
