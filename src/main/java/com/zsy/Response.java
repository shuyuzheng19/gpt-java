package com.zsy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * @author 郑书宇
 * @create 2022/12/11 22:21
 * @desc
 */
@Getter
@ToString
@AllArgsConstructor
public class Response {
    private int status;

    private String statusMessage;

    private String contentType;

    private String responseBody;

}
