package com.imooc.enums;

/**
 * @FileName Sex
 * @Description 性别枚举
 * @Author jiuhao
 * @Date 2020/5/16 12:08
 * @Modified
 * @Version 1.0
 */
public enum Sex {

    woman(0,"女"),
    man(1,"男"),
    secret(2, "保密");

    public final Integer type;

    public final String value;

    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
