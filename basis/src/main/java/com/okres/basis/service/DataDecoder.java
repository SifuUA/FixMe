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
    @Override
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
        acceptMsg.setCheckSum(DigestUtils.md5Hex(String.valueOf(acceptMsg.getId())).toLowerCase());
        acceptMsg.setMsgType(message.getMsgType());
        acceptMsg.setId(byteBuf.readInt());
        acceptMsg.setCheckSum(msgType);
        list.add(acceptMsg);
    }

    private void doTransaction(ByteBuf byteBuf, List<Object> list, Message message, String msgType) {
        OperationMessage operationMsg = new OperationMessage.Builder()
                .msgType(message.getMsgType())
                .operation(msgType)
                .id(byteBuf.readInt())
                .instrument(msgType)
                .msgId(byteBuf.readLong())
                .quantity(byteBuf.readInt())
                .price(byteBuf.readInt())
                .checkSum()
                .build();
        list.add(operationMsg);
    }
}
