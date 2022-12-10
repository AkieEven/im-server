package tech.van.im.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.van.im.dao.UserMapper;
import tech.van.im.entity.User;
import tech.van.im.transfer.MessageProtobuf;
import tech.van.im.transfer.MessageProtobufType;

@Component
public class RegisterHandler implements ServerEventHandler {

    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public void handle(MessageProtobuf.Msg msg, ChannelHandlerContext context) {
        //解析User
        JSONObject jsonObject = JSON.parseObject(msg.getExtend());
        User user = new User();
        user.setUserPassword(jsonObject.getString("password"));
        user.setUserSalt(jsonObject.getString("salt"));
        user.setUserDisplayName(jsonObject.getString("displayName"));
        user.setUserEmail(jsonObject.getString("email"));

        //返回消息构建
        JSONObject responseJson = new JSONObject();
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
        builder.setMsgId(msg.getMsgId());
        builder.setMsgType(MessageProtobufType.REGISTER.getMsgType());
        builder.setTimestamp(System.currentTimeMillis());

        if(userMapper.selectByEmail(user.getUserEmail()) == null) {
            int status = userMapper.save(user);
            if (status == 1) {
                responseJson.put("status", 1);
                responseJson.put("info", "注册成功");
            } else {
                responseJson.put("status", 0);
                responseJson.put("error", "注册失败,服务器故障");
            }
        } else {
            responseJson.put("status", 0);
            responseJson.put("error", "注册失败，用户已存在");
        }

        builder.setExtend(responseJson.toString());

        context.channel().writeAndFlush(builder.build());
    }
}
