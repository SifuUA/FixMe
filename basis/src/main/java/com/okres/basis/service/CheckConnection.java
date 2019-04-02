package com.okres.basis.service;

import com.okres.basis.model.AcceptMsg;
import com.okres.basis.model.TypeMsg;
import com.okres.basis.util.Util;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class CheckConnection extends MessageToByteEncoder<AcceptMsg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, AcceptMsg acceptMsg, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(acceptMsg.getMsgType().length());
        byteBuf.writeCharSequence(acceptMsg.getMsgType(),
                Util.charSet);
        if (acceptMsg.getMsgType().equals(TypeMsg.ACCEPT.toString())) {
            String md5 = acceptMsg.getMd5();
            acceptMsg.setCheckSum(md5);
            byteBuf.writeLong(acceptMsg.getId());
            byteBuf.writeInt(acceptMsg.getCheckSum().length());
            byteBuf.writeCharSequence(acceptMsg.getCheckSum(), Util.charSet);
        }
    }
}
