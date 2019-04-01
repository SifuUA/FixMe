package com.okres.basis.service;

import com.okres.basis.exception.NotEqualCheckSumExeption;
import com.okres.basis.model.AcceptMsg;
import com.okres.basis.model.Message;
import com.okres.basis.model.OperationMessage;
import com.okres.basis.model.TypeMsg;
import com.okres.basis.util.Util;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.codec.digest.DigestUtils;

public class ClientChanellProvider extends ChannelInboundHandlerAdapter {

    private String clientName;
    private long id;

    public ClientChanellProvider(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        if (message.getMsgType().equals(TypeMsg.ACCEPT.toString())) {
            AcceptMsg acceptMsg = (AcceptMsg) msg;
            id = acceptMsg.getId();
            System.out.println(String.format("%s id: %d",
                    Util.property.getProperty("CONNECT_TO"), id));
        } else if (Util.isOperation(message)) {
            OperationMessage opMessage = (OperationMessage) msg;
            try {
                if (!opMessage.getMd5().equals(opMessage.getCheckSum())) {
                    throw new NotEqualCheckSumExeption();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            if (Util.isSendOrReject(opMessage)) {
                System.out.println(
                        String.format("%s: %s",
                                Util.property.getProperty("ANSWER"), opMessage.getOperation()));
                return;
            }
            if (message.getMsgType().equals(TypeMsg.SELL.toString())) {
                String str;
                int num = (int) (Math.random() * 2);
                if (num > 0) {
                    str = String.format("%s ! %s", TypeMsg.ACCEPT, Util.property.getProperty("THANKS"));
                    opMessage.setOperation(TypeMsg.ACCEPT.toString());
                } else {
                    str = String.format("%s ! %s", TypeMsg.REJECT, Util.property.getProperty("NOPE"));
                    opMessage.setOperation(TypeMsg.REJECT.toString());
                }
                System.out.println(str);
                opMessage.setCheckSum(DigestUtils.md5Hex(opMessage.getMd5()));
                ctx.writeAndFlush(opMessage);
            } else {
                String str;
                int num = (int) (Math.random() * 3);
                if (num == 0) {
                    str = String.format("%s ! %s", TypeMsg.REJECT, Util.property.getProperty("WRONG_INSTRUM"));
                    opMessage.setOperation(TypeMsg.REJECT.toString());
                } else if (num == 1) {
                    str = String.format("%s ! %s", TypeMsg.REJECT, Util.property.getProperty("NO_ENOUGH"));
                    opMessage.setOperation(TypeMsg.REJECT.toString());
                } else {
                    str = String.format("%s ! %s", TypeMsg.REJECT, Util.property.getProperty("THANKS"));
                    opMessage.setOperation(TypeMsg.ACCEPT.toString());
                    System.out.println(str);
                    opMessage.setCheckSum(DigestUtils.md5Hex(opMessage.getMd5()));
                    ctx.writeAndFlush(opMessage);
                }
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        long default_id = Integer.parseInt(Util.property.getProperty("DEFAULT_ID"));

        System.out.println(String.format("%s %s",
                clientName, Util.property.getProperty("CONNECT_TO")));
        AcceptMsg acceptMsg = new AcceptMsg(TypeMsg.ACCEPT.toString(), default_id, default_id);
        ctx.writeAndFlush(acceptMsg);
    }


}
