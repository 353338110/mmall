package util;

import com.mmall.netty.LauncherNetty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartInit implements ServletContextListener {
    static final Logger logger = LoggerFactory.getLogger(StartInit.class);
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // 获取项目根目录
        String root_path  = servletContextEvent.getServletContext().getRealPath("/");
        logger.info("application path : {}",root_path);
       /* try {
            LauncherNetty.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
