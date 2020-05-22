package com.imooc.enums;

/**
 * @FileName CommentLevel
 * @Description 商品评价等级
 * @Author jiuhao
 * @Date 2020/5/20 19:59
 * @Modified
 * @Version 1.0
 */
public enum  CommentLevel {

    GOOD(1,"好评"),
    NORMAL(2, "中评"),
    BAD(3, "差评");

    public Integer type;

    public String value;

    CommentLevel(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
