package org.skyme.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author:Skyme
 * @create: 2023-09-09 00:34
 * @Description: http请求
 */
public class Request {
    private InputStream inputStream;

    private String method;//请求方式

    private String uri;//资源定位

    private String protocol;//协议

    private HashMap<String,String> headers = new HashMap<>();//请求头

    private HashMap<String,List<String>> params = new HashMap<>();//参数存放
    public Request(InputStream inputStream) {
        this.inputStream=inputStream;
        try{
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br=new BufferedReader(inputStreamReader);
            String str=null;
            List<String>    list=new ArrayList<>();
            while (!(str=br.readLine()).equals("")){
                list.add(str);
            }
            String httpFirst=list.get(0);//请求行
            String[] split = httpFirst.split(" ");
            this.method=split[0];
            setUri(split[1]);
            setParams(split[1]);
            //请求头
            for(int i=1;i<list.size();i++){
                String s = list.get(i);
                if(s.indexOf(":")>-1){
                    String headerName = s.substring(0, s.indexOf(":"));
                    String headerValue = s.substring(s.indexOf(":")+2);
                    headers.put(headerName,headerValue);
                }

            }


        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void setUri(String uri) {
        if(uri.indexOf("?")>-1){
            this.uri=uri.substring(0,uri.indexOf("?"));
        }else{
            this.uri=uri;
        }

    }
    public void setParams(String uri){
        if(uri.indexOf("?")>-1){
            String substring = uri.substring(uri.indexOf("?")+1);
            String[] split = substring.split("&");
            for (String s : split) {
                String[] split1 = s.split("=");
                String param = split1[0];
                String value=split1[1];
                if(params.containsKey(param)){
                    List<String> strings = params.get(param);
                    strings.add(value);
                }else{
                    ArrayList<String> strings = new ArrayList<>();
                    strings.add(value);
                    params.put(param,strings);
                }

            }

        }
    }
    public String getParameter(String param){
        return params.get(param)==null?null:  params.get(param).get(0);
    }
    //获取多个参数
    public String[] getParameters(String param) {
        return params.get(param)==null?null: (String[]) params.get(param).toArray();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public HashMap<String, List<String>> getParams() {
        return params;
    }

    public void setParams(HashMap<String, List<String>> params) {
        this.params = params;
    }
}
