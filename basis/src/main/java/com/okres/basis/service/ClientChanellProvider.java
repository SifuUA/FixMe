package com.okres.basis.service;

import com.okres.basis.exception.InvalidInputException;
import com.okres.basis.exception.NotEqualCheckSumExeption;
import com.okres.basis.exception.BlanckInput;
import com.okres.basis.model.AcceptMsg;
import com.okres.basis.model.Message;
import com.okres.basis.model.OperationMessage;
import com.okres.basis.model.TypeMsg;
import com.okres.basis.util.Util;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientChanellProvider extends ChannelInboundHandlerAdapter {

    private String clientName;
    private int idMain;

    public ClientChanellProvider(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        if (message.getMsgType().equals(TypeMsg.ACCEPT.toString())) {
            AcceptMsg acceptMsg = (AcceptMsg) msg;
            idMain = acceptMsg.getId();
            System.out.println(String.format("%s\n%s id: %d",
                    Util.property.getProperty("CONNECT_IS"), this.clientName, idMain));
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
                }
                System.out.println(str);
                opMessage.setCheckSum(DigestUtils.md5Hex(opMessage.getMd5()));
                ctx.writeAndFlush(opMessage);
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int default_id = Integer.parseInt(Util.property.getProperty("DEFAULT_ID"));

        System.out.println(String.format("%s %s",
                clientName, Util.property.getProperty("CONNECT_TO")));
        AcceptMsg acceptMsg = new AcceptMsg(TypeMsg.ACCEPT.toString(), default_id, default_id);
        ctx.writeAndFlush(acceptMsg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        userInputReader(ctx);
    }

    private void userInputReader(ChannelHandlerContext ctx) throws IOException {
        if (clientName.toUpperCase().equals(Util.property.getProperty("BROKER"))) {
            System.out.println(Util.property.getProperty("REQUEST"));
            String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
            try {
                if (str.length() == 0) {
                    throw new BlanckInput();
                } else if (clientName.toUpperCase().equals(Util.property.getProperty("BROKER"))) {
                    String[] inputList = str.split("\\s+");
                    if (inputList.length != Integer.parseInt(Util.property.getProperty("WORD_COUNT"))) {
                        throw new InvalidInputException();
                    }
                    if (inputList[1].length() != 6) {
                        throw new InvalidInputException();
                    }
                    int id = Integer.parseInt(inputList[1]);
                    String instrument = inputList[2];
                    int quant = Integer.parseInt(inputList[3]);
                    int price = Integer.parseInt(inputList[4]);
                    OperationMessage message;
                    if (inputList[0].toLowerCase().equals(Util.property.getProperty("SELL"))) {
                        message =
                                new OperationMessage
                                        .Builder()
                                        .msgType(TypeMsg.SELL.toString())
                                        .operation("-")
                                        .msgId(id)
                                        .id(idMain)
                                        .instrument(instrument)
                                        .quantity(quant)
                                        .price(price)
                                        .build();
                    } else if (inputList[0].toLowerCase().equals(Util.property.getProperty("BUY"))) {
                        message =
                                new OperationMessage
                                        .Builder()
                                        .msgType(TypeMsg.BUY.toString())
                                        .operation("-")
                                        .msgId(id)
                                        .id(idMain)
                                        .instrument(instrument)
                                        .quantity(quant)
                                        .price(price)
                                        .build();
                    } else {
                        throw new InvalidInputException();
                    }
   //                 message.setCheckSum(message.getMd5());
                    ctx.writeAndFlush(message);
                    System.out.println(Util.property.getProperty("SEND_REQ"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                userInputReader(ctx);
            }
        }
    }
}
