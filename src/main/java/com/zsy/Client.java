package com.zsy;

import com.zsy.utils.ZSY;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 郑书宇
 * @create 2022/12/11 23:37
 * @desc
 */
public class Client {


    private String token;

    private Proxy proxy;

    private static final String URL="https://chat.openai.com/backend-api/conversation";

    private static final String contentType="application/json";

    private static final String userAgent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36";


    public Client(String token){
        this(token,null);
    }

    public Client(String token,Proxy proxy){
        this.token=token;
        this.proxy=proxy;
    }

    public Answer sendMessage(String conversationId,String... message) throws IOException {

        String requestBody=GptChat.buildRequestBody(Arrays.asList(message),conversationId);

        ZSY.Builder builder = new ZSY.Builder();

        if(proxy!=null) {
            builder.connect(URL, proxy);
        }else{
            builder.connect(URL);
        }

        ZSY zsy = builder.method("POST").headers(getHeaders()).body(requestBody).build();

        if(zsy.getStatusCode()==200){
            return zsy.getGptResultMessage();
        }

        return null;
    }


    private Map<String,String> getHeaders(){
        Map<String,String > map=new HashMap<>();
        map.put("sec-ch-ua","\"Not?A_Brand\";v=\"8\", \"Chromium\";v=\"108\", \"Google Chrome\";v=\"108\"");
        map.put("sec-ch-ua-mobile","?0");
        map.put("Authorization","Bearer "+token);
        map.put("User-Agent",userAgent);
        map.put("Content-Type",contentType);
        map.put("accept","text/event-stream");
        map.put("X-OpenAI-Assistant-App-Id","");
        map.put("sec-ch-ua-platform","\"Windows\"");
        map.put("Sec-Fetch-Site","same-origin");
        map.put("Sec-Fetch-Mode","cors");
        map.put("Sec-Fetch-Dest","empty");
        map.put("host","chat.openai.com");
        map.put("Connection","close");
        map.put("Referer","https://chat.openai.com/chat");
        return map;
    }
}
