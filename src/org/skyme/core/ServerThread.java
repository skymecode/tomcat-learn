package org.skyme.core;

import org.skyme.controller.Controller;

import java.io.IOException;
import java.net.Socket;

/**
 * @author:Skyme
 * @create: 2023-09-09 00:30
 * @Description: 连接线程
 */
public class ServerThread implements Runnable{
    private Socket socket;
    private Server server;
    public ServerThread(Socket accept, Server server) {
        this.server = server;
        this.socket = accept;
    }

    @Override
    public void run() {
        try {
            Request request = new Request(socket.getInputStream());
            Response response = new Response(socket.getOutputStream());
            String uri = request.getUri();
            if(uri.endsWith(".html")){
                response.responseStaticFile(Response.HTML_HEADER,uri);
                return;
            }
            if(uri.endsWith(".css")){
                response.responseStaticFile(Response.CSS_HEADER,uri);
                return;
            }
            if(uri.endsWith(".js")){
                response.responseStaticFile(Response.JS_HEADER,uri);
                return;
            }
            if(uri.endsWith(".jpg")||uri.endsWith(".ico")){
                response.responseImage(uri);
                return;
            }
            System.out.println(uri);
            String[] split = uri.split("/");
            if(split.length <=2){
                response.responseNotFound();
                return;
            }
            uri = uri.substring(0,uri.indexOf("/",1));
            System.out.println("更改后的uri:"+uri);
            String name = server.getServletMap().get(uri + "/*");
            if(name == null){
                response.responseNotFound();
                return;
            }
            Controller controller = server.getServletToContollerMap().get(name);
            if(controller==null){
                response.responseNotFound();
                return;
            }
            controller.service(request,response);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }



    }
}
