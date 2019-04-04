package com.okres.basis.controller;

import com.okres.basis.service.CheckConnection;
import com.okres.basis.service.ClientChanellProvider;
import com.okres.basis.service.DataDecoder;
import com.okres.basis.service.DataEncoder;
import com.okres.basis.util.Util;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client implements Runnable {

    private String clientName;
    private EventLoopGroup workerGroup;

    public Client(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public void run() {
        workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bs = new Bootstrap();
            bs.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new DataDecoder(),
                                    new CheckConnection(),
                                    new DataEncoder(),
                                    new ClientChanellProvider(clientName)
                            );
                        }
                    }).option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture cf = bs.connect(Util.property.getProperty("HOST"), portDef(clientName)).sync();
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            closeWorkerGroup();
        }
    }

    public void closeWorkerGroup() {
        workerGroup.shutdownGracefully();
    }

    public int portDef(String client) {
        if (client.toUpperCase().equals(Util.property.getProperty("MARKET"))) {
            return Integer.parseInt(Util.property.getProperty("MARKET_PORT"));
        }
        return Integer.parseInt(Util.property.getProperty("BROKER_PORT"));
    }
}
