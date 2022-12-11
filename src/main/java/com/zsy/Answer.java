package com.zsy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author 郑书宇
 * @create 2022/12/12 0:50
 * @desc
 */
@ToString
@Getter
@AllArgsConstructor
public class Answer {

    private String content;

    private String conversationId;

}
