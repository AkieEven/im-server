package tech.van.im.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.van.im.dao.FriendshipMapper;
import tech.van.im.dao.UserMapper;
import tech.van.im.entity.Friendship;
import tech.van.im.transfer.MessageProtobuf;
import tech.van.im.transfer.MessageProtobufType;

@Slf4j
@Component
public class FriendApplyResponseHandler implements ServerEventHandler{
    @Autowired
    FriendshipMapper friendshipMapper;
    @Autowired
    UserMapper userMapper;
    @Override
    public void handle(MessageProtobuf.Msg msg, ChannelHandlerContext context) {
        Long userId = Long.parseLong(msg.getFromId());
        Long friendId = Long.parseLong(msg.getToId());
        JSONObject jsonObject = JSON.parseObject(msg.getExtend());
        boolean accept = jsonObject.getBooleanValue("accept");

        //TODO：检查好友申请的合理性

        if(accept) {
            friendshipMapper.save(new Friendship(userId,friendId,userMapper.selectNameById(friendId)));
            friendshipMapper.save(new Friendship(friendId,userId,userMapper.selectNameById(userId)));
        }

        JSONObject responseJson = new JSONObject();
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
        builder.setMsgId(msg.getMsgId());
        builder.setMsgType(MessageProtobufType.FRIEND_INFO_RESPONSE.getMsgType());
        builder.setToId(msg.getToId());
        builder.setTimestamp(System.currentTimeMillis());
    }
}
