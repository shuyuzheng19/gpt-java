package com.zsy;

import java.io.IOException;

/**
 * @author 郑书宇
 * @create 2022/12/11 22:15
 * @desc
 */
public class Test {
    public static void main(String[] args) throws IOException {

        String token="";

        Client client=new Client(token,null);

        Answer result = client.sendMessage(null, "hello");

        System.out.println(result);

    }
}
