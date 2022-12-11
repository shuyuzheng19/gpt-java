package com.zsy.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zsy.Answer;
import com.zsy.Response;
import lombok.ToString;
import java.io.*;
import java.net.*;
import java.util.Map;

/**
 * @author 郑书宇
 * @create 2022/12/11 22:01
 * @desc
 */
@ToString
public class ZSY {

    private HttpURLConnection connection;

    private ZSY(){

    }

    private ZSY(Builder builder){
        this.connection=builder.connection;
    }

    public int getStatusCode() throws IOException {
        return connection.getResponseCode();
    }

    public Response getResponse() throws IOException {
        InputStream inputStream = null;
        try{
            inputStream=connection.getInputStream();
        }catch (Exception e){
            inputStream=connection.getErrorStream();
        }
        BufferedReader bufferedInputStream=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        StringBuilder builder=new StringBuilder();
        String len=null;
        while((len=bufferedInputStream.readLine())!=null){
            System.out.println("len="+len);
            builder.append(len+"\n");
        }
        return new Response(connection.getResponseCode(),connection.getResponseMessage(),connection.getContentType(),builder.toString());
    }

    public Answer getGptResultMessage() throws IOException {
        InputStream inputStream = null;
        try{
            inputStream=connection.getInputStream();
        }catch (Exception e){
            inputStream=connection.getErrorStream();
        }
        BufferedReader bufferedInputStream=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        String content="";
        String len=null;
        String conversationId=null;
        while((len=bufferedInputStream.readLine())!=null){
            if(len.startsWith("data: {")){
                JSONObject jsonObject = JSONObject.parseObject(len.substring("data: ".length()));
                JSONArray parts = jsonObject.getJSONObject("message").getJSONObject("content").getJSONArray("parts");
                if(conversationId==null)conversationId=jsonObject.getString("conversation_id");
                if(!parts.isEmpty()){
                    content=parts.getString(0);
                }
            }
        }
        return new Answer(content,conversationId);
    }


    public static class Builder{

        private HttpURLConnection connection;

        public Builder connect(String url) throws IOException {
            this.connection= (HttpURLConnection) new URL(url).openConnection();
            this.connection.setDoOutput(true);
            this.connection.setDoInput(true);
            this.connection.setInstanceFollowRedirects(true);
            return this;
        }

        public Builder connect(String url, com.zsy.Proxy proxy) throws IOException {
            this.connection= (HttpURLConnection) new URL(url).openConnection(new Proxy(Proxy.Type.HTTP,new InetSocketAddress(proxy.getHost(),proxy.getPort())));
            this.connection.setDoOutput(true);
            this.connection.setDoInput(true);
            this.connection.setInstanceFollowRedirects(true);
            return this;
        }

        public Builder method(String method) throws ProtocolException {
            if(method.toUpperCase().equals("POST")) this.connection.setUseCaches(false);
            this.connection.setRequestMethod(method);
            return this;
        }

        public Builder data(Map<String, String> data) throws IOException {
            OutputStream outputStream = this.connection.getOutputStream();
            for (String str : data.keySet()) {
                outputStream.write((str+"="+ URLEncoder.encode(data.get(str),"UTF-8")+"&").getBytes());
            }
            outputStream.flush();
            outputStream.close();
            return this;
        }

        public Builder data(String key,String value) throws IOException {
            OutputStream outputStream = this.connection.getOutputStream();
            outputStream.write((key+"="+ URLEncoder.encode(value,"UTF-8")+"&").getBytes());
            outputStream.flush();
            outputStream.close();
            return this;
        }

        public Builder proxy(String host,int port) throws IOException {
            this.connection= (HttpURLConnection) this.connection.getURL().openConnection(new Proxy(Proxy.Type.HTTP,new InetSocketAddress(host,port)));
            return this;
        }

        public Builder contentType(String contentType){
            this.connection.addRequestProperty("Content-Type",contentType);
            return this;
        }

        public Builder body(String data) throws IOException {
            PrintWriter outputStream=new PrintWriter(new OutputStreamWriter(this.connection.getOutputStream(),"UTF-8"));
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
            return this;
        }

        public Builder header(String key,String value){
            this.connection.setRequestProperty(key,value);
            return this;
        }

        public Builder headers(Map<String,String> headers){
            for (String key : headers.keySet()) {
                this.connection.setRequestProperty(key,headers.get(key));
            }
            return this;
        }

        public Builder userAgent(String userAgent){
            this.connection.setRequestProperty("User-Agent",userAgent);
            return this;
        }

        public ZSY build(){
            return new ZSY(this);
        }

    }
}
