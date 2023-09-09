package org.skyme.core;

import org.skyme.controller.Controller;
import org.skyme.util.XmlUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author:Skyme
 * @create: 2023-09-09 00:30
 * @Description: 服务器核心
 */
public class Server {
    private ServerSocket serverSocket;
    private final HashMap<String, Controller> servletToContollerMap = new HashMap<>();

    private final HashMap<String,String> servletMap=new HashMap<>();

    private ExecutorService executorService;//线程池

    public HashMap<String, Controller> getServletToContollerMap() {
        return servletToContollerMap;
    }

    public HashMap<String, String> getServletMap() {
        return servletMap;
    }

    //服务器初始化
        private void  init(){
            //读取服务器properties配置
            InputStream resourceAsStream = Server.class.getClassLoader().getResourceAsStream("server.properties");
            Properties properties = new Properties();
            try {
                properties.load(resourceAsStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String readPort = properties.getProperty("port");
             int port = Integer.parseInt(readPort);
            List<String> servletName = XmlUtil.getServletName();
            List<String> servletClass = XmlUtil.getServletClass();
            List<String> servletPaths = XmlUtil.getServletPaths();
            //存放servlet
            for (int i=0;i<servletClass.size();i++) {
                try {
                    Object o = Class.forName(servletClass.get(i)).getConstructor().newInstance();
                    servletToContollerMap.put(servletName.get(i), (Controller) o);
                    servletMap.put(servletPaths.get(i),servletName.get(i));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
            this.serverSocket= new ServerSocket();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(port);
        try {
            //监听端口
            serverSocket.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.executorService= Executors.newFixedThreadPool(25);

    }
    private  void start(){
        init();
        while(true){
            Socket accept=null;
            try {
                accept = serverSocket.accept();
                executorService.execute(new ServerThread(accept,this));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }



        }
    }

    public static void main(String[] args) {
        new Server().start();
    }

}
