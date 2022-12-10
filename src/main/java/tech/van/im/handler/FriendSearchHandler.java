package tech.van.im.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import tech.van.im.dao.UserMapper;
import tech.van.im.entity.User;
import tech.van.im.transfer.MessageProtobuf;
import tech.van.im.transfer.MessageProtobufType;

@Slf4j
@Component
public class FriendSearchHandler implements ServerEventHandler{
    @Autowired
    UserMapper userMapper;
    @Override
    public void handle(MessageProtobuf.Msg msg, ChannelHandlerContext context) {
        JSONObject jsonObject = JSON.parseObject(msg.getExtend());
        String friendEmail = jsonObject.getString("email");

        User user = userMapper.selectByEmail(friendEmail);

        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
        builder.setMsgId(msg.getMsgId());
        builder.setMsgType(MessageProtobufType.FRIEND_SEARCH_RESPONSE.getMsgType());
        builder.setTimestamp(System.currentTimeMillis());

        JSONObject responseObject = new JSONObject();

        if(user == null) {
            responseObject.put("error","该用户未注册");
            builder.setStatus(0);
        } else {
            responseObject.put("userId",user.getUserId());
            responseObject.put("displayName",user.getUserDisplayName());
            builder.setStatus(1);
        }
        builder.setExtend(responseObject.toString());

        context.writeAndFlush(builder.build());
    }
}
