package com.okres.basis.controller;

import com.okres.basis.service.ChanellProvider;
import com.okres.basis.service.DataDecoder;
import com.okres.basis.service.CheckConnection;
import com.okres.basis.service.DataEncoder;
import com.okres.basis.util.Util;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;

public class Server implements Runnable {
    private int serverPort;
    private static Logger logger = Logger.getLogger(Server.class);


    public Server(int serverPort) {
        this.serverPort = serverPort;
        Util.loadProp();
    }

    @Override
    public void run() {
        runServer(serverPort);
    }

    private void runServer(int serverPort) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGroup, workerGroup).
                    channel(NioServerSocketChannel.class).
                    childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new DataDecoder(),
                                    new CheckConnection(),
                                    new DataEncoder(),
                                    new ChanellProvider(serverPort));
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128).
                    childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture cf = sb.
                    bind(serverPort).sync();
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error(e);
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
