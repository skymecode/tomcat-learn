package org.skyme.core;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:Skyme
 * @create: 2023-09-09 00:34
 * @Description: http响应
 */
public class Response {
    private OutputStream outputStream;
    public Response(OutputStream outputStream) {
        this.outputStream=outputStream;
    }
    /*
    响应也分为三个部分 响应行,响应头,响应体
    响应行 协议 状态码 描述
    必须添加的响应头 内容类型 告诉客户端服务器响应是什么
     */

    public static final String HTML_HEADER="HTTP/1.1 200 \r\nContent-Type: text/html;charset=utf-8\r\n\r\n";
    public static final String CSS_HEADER="HTTP/1.1 200 \r\nContent-Type: text/css;charset=utf-8\r\n\r\n";
    public static final String JS_HEADER="HTTP/1.1 200 \r\nContent-Type: application/javascript;charset=utf-8\r\n\r\n";
    public static final String IMAGE_HEADER="HTTP/1.1 200 \r\nContent-Type: image/jpeg;charset=utf-8\r\n\r\n";
    public static final String JSON_HEADER="HTTP/1.1 200 \r\nContent-Type: application/json;charset=utf-8\r\n\r\n";
    public static final String NOT_FOUND_HEADER="HTTP/1.1 200 \r\nContent-Type: text/html;charset=utf-8\r\n\r\n";

    private String contentType = HTML_HEADER;

    private String message;

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public void response(){
        String str=contentType+message;
        try {
            outputStream.write(str.getBytes("utf-8"));
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void responseNotFound(){
        contentType=NOT_FOUND_HEADER;
        message="404 资源未找到";
        response();
    }
    //响应静态资源

    public void responseStaticFile(String header,String path){
        contentType=header;
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("res" + path);
        if(resourceAsStream==null){
            responseNotFound();
            return;
        }
        StringBuilder sb = new StringBuilder();
        InputStreamReader inputStreamReader = null;
        BufferedReader br=null;
        try {
            inputStreamReader = new InputStreamReader(resourceAsStream,"utf-8");
           br =new BufferedReader(inputStreamReader);
            String st=null;
            while ((st=br.readLine())!=null){
                sb.append(st);
                sb.append("\n");
            }
            message=sb.toString();
            response();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(resourceAsStream!=null){
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
    public void responseImage(String path){
        contentType=IMAGE_HEADER;
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("res" + path);
        if(resourceAsStream==null){
            responseNotFound();
            return;
        }
        StringBuilder sb = new StringBuilder();
        InputStreamReader inputStreamReader = null;
        try {
            outputStream.write(contentType.getBytes("UTF-8"));

            byte[] bytes = new byte[1024];
            int len=0;
            while ((len=resourceAsStream.read(bytes))!=-1){
                outputStream.write(bytes, 0, len);

            }
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(resourceAsStream!=null){
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
