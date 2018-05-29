package com.mmall.netty;



import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;


public class LauncherNetty {
    private static final Logger logger = Logger.getLogger(LauncherNetty.class);
    public static void main(String[] args) throws InterruptedException {
        //xBasicConfigurator.configure(); //自动快速地使用缺省Log4j环境。
        logger.info("···开始");
        start();
    }

    public static void start() throws InterruptedException {

        // 启动服务
        new Service().initAndStart();

        NettyConfig config = new NettyConfigImpl();
        config.setParentGroup(1);
        config.setChildGroup();
        config.setChannel(NioServerSocketChannel.class);
        config.setHandler();
        config.bind(2347);
    }
}
