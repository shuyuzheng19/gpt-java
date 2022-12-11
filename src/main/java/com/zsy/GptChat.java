package com.zsy;

import com.alibaba.fastjson.JSONArray;
import java.util.List;
import java.util.UUID;

/**
 * @author 郑书宇
 * @create 2022/12/11 22:56
 * @desc
 */
public class GptChat {

    private static String ID= UUID.randomUUID().toString();

    private static final String ROLE="user";

    private static final String contentType="text";

    private static final String parentId=UUID.randomUUID().toString();

    private static final String MODEL="text-davinci-002-render";

    public static String buildRequestBody(List<String> messages,String id){

        if(id!=null)ID=id;

        String parts=JSONArray.toJSON(messages).toString();

        String requestBody="{\"action\":\"next\",\"messages\":[{\"id\":\""+ID+"\",\"role\":\""+ROLE+"\",\"content\":{\"content_type\":\""+contentType+"\",\"parts\":"+parts+"}}],\"parent_message_id\":\""+parentId+"\",\"model\":\""+MODEL+"\"}";

        return requestBody;

    }
}

