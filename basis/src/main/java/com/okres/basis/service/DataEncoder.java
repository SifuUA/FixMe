package com.okres.basis.service;

import com.okres.basis.model.OperationMessage;
import com.okres.basis.model.TypeMsg;
import com.okres.basis.util.Util;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class DataEncoder extends MessageToByteEncoder<OperationMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, OperationMessage message, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(message.getMsgType().length());
        byteBuf.writeCharSequence(message.getMsgType(), Util.charSet);
        if (isSellOrBuy(message)) {
            buffWritting(message, byteBuf);
        }
    }

    private void buffWritting(OperationMessage message, ByteBuf byteBuf) {
        byteBuf.writeInt(message.getOperation().length());
        byteBuf.writeCharSequence(message.getOperation(), Util.charSet);
        byteBuf.writeInt(message.getId());
        byteBuf.writeInt(message.getInstrument().length());
        byteBuf.writeCharSequence(message.getInstrument(), Util.charSet);
        byteBuf.writeInt(message.getMsgId());
        byteBuf.writeInt(message.getPrice());
        byteBuf.writeInt(message.getQuantity());
        byteBuf.writeInt(message.getCheckSum().length());
        byteBuf.writeCharSequence(message.getCheckSum(), Util.charSet);
    }

    private boolean isSellOrBuy(OperationMessage message) {
        return message.getMsgType().equals(TypeMsg.SELL.toString())
                || message.getMsgType().equals(TypeMsg.BUY.toString());
    }
}