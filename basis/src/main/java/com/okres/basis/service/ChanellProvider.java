package com.okres.basis.service;

import com.okres.basis.exception.NotEqualCheckSumExeption;
import com.okres.basis.exception.NotFindInRoutsException;
import com.okres.basis.model.AcceptMsg;
import com.okres.basis.model.Message;
import com.okres.basis.model.OperationMessage;
import com.okres.basis.model.TypeMsg;
import com.okres.basis.util.Util;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;

public class ChanellProvider extends ChannelInboundHandlerAdapter {

    private static HashMap<Long, ChannelHandlerContext> routs = new HashMap<>();
    private static Logger logger = Logger.getLogger(ChanellProvider.class);
    private int serverPort;

    public ChanellProvider(int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object objMsg) throws Exception {
        Message msg = (Message) objMsg;
        if (Util.isOperation(msg)) {
            OperationMessage opMsg = (OperationMessage) msg;
            try {
                if (!opMsg.getMd5().equals(opMsg.getCheckSum())) {
                    throw new NotEqualCheckSumExeption();
                }
                if (!routs.containsKey(opMsg.getMsgId())) {
                    throw new NotFindInRoutsException();
                }
                if (Util.isSendOrReject(opMsg)) {
                    if (!opMsg.getMd5().equals(opMsg.getCheckSum())) {
                        throw new NotEqualCheckSumExeption();
                    }
                    routs.get(opMsg.getId()).writeAndFlush(opMsg);
                    return;
                }
                System.out.println(Util.property.getProperty("CONNECT")
                        .concat(String.format("%d", opMsg.getId())));
                routs.get(opMsg.getId())
                        .channel().writeAndFlush(opMsg);
            } catch (Exception e) {
                opMsg.setOperation(TypeMsg.REJECT.toString());
                opMsg.setCheckSum(opMsg.getMd5());
                ctx.writeAndFlush(opMsg);
                e.printStackTrace();
                logger.error(e);
            }
        } else if (msg.getMsgType().equals(TypeMsg.ACCEPT.toString())) {
            AcceptMsg acceptMsg = (AcceptMsg) objMsg;
            String id = ctx.channel().remoteAddress().toString().substring(11);
            String server;
            if (serverPort != Integer.parseInt(Util.property.getProperty("MARKET_PORT"))) {
                id = id.concat("2");
                server = Util.property.getProperty("BROKER");
            } else {
                id = id.concat("3");
                server = Util.property.getProperty("MARKET");
            }
            acceptMsg.setId(Integer.parseInt(id));
            acceptMsg.setCheckSum(DigestUtils.md5Hex(id).toLowerCase());
            ctx.writeAndFlush(acceptMsg);
            routs.put(acceptMsg.getId(), ctx);
            System.out.println(Util.property.getProperty("CONNECT_FROM")
                    .concat(String.format("%s : %s", server, id)));
        }
    }
}
