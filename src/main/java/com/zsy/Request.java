package com.zsy;

import lombok.Data;

import java.util.Map;

/**
 * @author 郑书宇
 * @create 2022/12/11 21:52
 * @desc
 */
@Data
public class Request {

    private String method;

    private String url;

    private Map<String,String> map;

    private String userAgent;
}
