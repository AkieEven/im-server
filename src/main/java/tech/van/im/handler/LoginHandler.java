package tech.van.im.handler;

import com.alibaba.fastjson2.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.van.im.dao.UserMapper;
import tech.van.im.entity.User;
import tech.van.im.transfer.MessageProtobuf;
import tech.van.im.util.MD5Util;
import tech.van.im.util.channel.ChannelContainer;
import tech.van.im.util.channel.ChannelManager;

@Slf4j
@Component
public class LoginHandler implements ServerEventHandler{

    @Autowired
    UserMapper userMapper;
    @Autowired
    ChannelManager channelManager;

    @Override
    public void handle(MessageProtobuf.Msg msg, ChannelHandlerContext context) {
        //解析
        JSONObject jsonObject = JSONObject.parseObject(msg.getExtend());
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");

        //返回消息
        JSONObject responseJson = new JSONObject();
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
        builder.setMsgId(msg.getMsgId());
        builder.setMsgType(1001);
        builder.setTimestamp(System.currentTimeMillis());

        User user = userMapper.selectByEmail(email);

        if(user == null) {
            responseJson.put("status",0);
            responseJson.put("error","账户未注册");
            builder.setExtend(responseJson.toString());
            context.channel().writeAndFlush(builder.build());
            channelManager.removeIfConnectNoActive(context.channel());
            return;
        }

        if(!MD5Util.check(password,user.getUserSalt(),user.getUserPassword())) {
            responseJson.put("status",0);
            responseJson.put("error","密码不正确");
            builder.setExtend(responseJson.toString());
            context.channel().writeAndFlush(builder.build());
            channelManager.removeIfConnectNoActive(context.channel());
            return;
        }

        //登录成功，加入manager并构建返回消息
        channelManager.save(user.getUserId(),context.channel());
        responseJson.put("status",1);
        responseJson.put("userId",user.getUserId());
        responseJson.put("displayName",user.getUserDisplayName());
        builder.setExtend(responseJson.toString());
        context.channel().writeAndFlush(builder.build());
        log.info("user login successfully, ID:{}, name:{}",user.getUserId(),user.getUserDisplayName());
    }
}
