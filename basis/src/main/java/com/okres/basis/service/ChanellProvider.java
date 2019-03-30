package com.okres.basis.service;

import com.okres.basis.model.Message;
import com.okres.basis.model.OperationMessage;
import com.okres.basis.model.TypeMsg;
import com.okres.basis.util.Util;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.HashMap;

public class ChanellProvider extends ChannelInboundHandlerAdapter {

    private static HashMap<Long, ChannelHandlerContext> routs = new HashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object objMsg) throws Exception {
        Message msg = (Message) objMsg;

        if (Util.isOperation(msg)) {

        } else if (msg.getMsgType().equals(TypeMsg.ACCEPT.toString())) {
            OperationMessage opMsg = (OperationMessage) msg;

            if (opMsg.getMd5().equals(opMsg.getCheckSum()) && routs.containsKey(opMsg.getMsgId())) {

            }

        }
    }
}
