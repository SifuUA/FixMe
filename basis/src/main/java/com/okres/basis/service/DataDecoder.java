package com.okres.basis.service;

import com.okres.basis.model.Message;
import com.okres.basis.model.AcceptMsg;
import com.okres.basis.model.OperationMessage;
import com.okres.basis.model.TypeMsg;
import com.okres.basis.util.Util;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.Charset;
import java.util.List;

public class DataDecoder extends ReplayingDecoder<Object> {
    @Override//a1bfb92344ba5004f9251cc68f645850
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Message message = new Message();
        String msgType = byteBuf.readCharSequence(byteBuf.readInt(),
                Util.charSet).toString();
        message.setMsgType(msgType);
        if (message.getMsgType().equals(TypeMsg.ACCEPT.toString())) {
            accept(byteBuf, list, message, msgType);
        } else if (Util.isOperation(message)) {
            doTransaction(byteBuf, list, message, msgType);
        }
    }

    private void accept(ByteBuf byteBuf, List<Object> list, Message message, String msgType) {
        AcceptMsg acceptMsg = new AcceptMsg();
//        acceptMsg.setCheckSum(DigestUtils.md5Hex(String.valueOf(acceptMsg.getId())).toLowerCase());
        acceptMsg.setMsgType(message.getMsgType());
        acceptMsg.setId(byteBuf.readInt());
        acceptMsg.setCheckSum(byteBuf.readCharSequence(byteBuf.readInt(), Util.charSet).toString());
        list.add(acceptMsg);
    }

    private void doTransaction(ByteBuf byteBuf, List<Object> list, Message message, String msgType) {
        OperationMessage operationMsg = new OperationMessage.Builder().
                msgType(message.getMsgType()).
                operation(byteBuf.readCharSequence(byteBuf.readInt(), Util.charSet).toString()).
                id(byteBuf.readInt()).
                instrument(byteBuf.readCharSequence(byteBuf.readInt(), Util.charSet).toString()).
                msgId(byteBuf.readInt()).
                quantity(byteBuf.readInt()).
                price(byteBuf.readInt()).
//                checkSum(). e7a4f57d9287ee994f02fb19241d76f1
                build();
        list.add(operationMsg);
    }
}
