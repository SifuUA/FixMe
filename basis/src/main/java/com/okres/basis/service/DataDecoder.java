package com.okres.basis.service;

import com.okres.basis.model.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.List;

public class DataDecoder extends ReplayingDecoder<Object> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Message message = new Message();
        message.setMsgType(byteBuf.readCharSequence());
    }
}
