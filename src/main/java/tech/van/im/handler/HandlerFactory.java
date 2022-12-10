package tech.van.im.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import tech.van.im.transfer.MessageProtobufType;

import java.util.HashMap;

/**
 * 使用前需调用init
 */
@Component
public class HandlerFactory {
    private final HashMap<Integer, ServerEventHandler> HANDLERS = new HashMap<>();

    @Autowired
    RegisterHandler registerHandler;
    @Autowired
    HeartbeatHandler heartbeatHandler;
    @Autowired
    LoginHandler loginHandler;
    @Autowired
    FriendStatusHandler friendStatusHandler;
    @Autowired
    FriendSearchHandler friendSearchHandler;
    @Autowired
    FriendApplyHandler friendApplyHandler;
    @Autowired
    FriendApplyResponseHandler friendApplyResponseHandler;

    public void init() {
        HANDLERS.put(MessageProtobufType.HANDSHAKE.getMsgType(), loginHandler);
        HANDLERS.put(MessageProtobufType.HEARTBEAT.getMsgType(), heartbeatHandler);
        HANDLERS.put(MessageProtobufType.REGISTER.getMsgType(), registerHandler);
        HANDLERS.put(MessageProtobufType.FRIEND_STATUS_REQUEST.getMsgType(), friendStatusHandler);
        HANDLERS.put(MessageProtobufType.FRIEND_SEARCH_REQUEST.getMsgType(), friendSearchHandler);
        HANDLERS.put(MessageProtobufType.FRIEND_APPLY.getMsgType(), friendApplyHandler);
        HANDLERS.put(MessageProtobufType.FRIEND_APPLY_RESPONSE.getMsgType(), friendApplyResponseHandler);
    }

    public ServerEventHandler getHandler(Integer msgType) {
        return HANDLERS.get(msgType);
    }
}
