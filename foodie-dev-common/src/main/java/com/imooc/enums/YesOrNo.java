package com.imooc.enums;

/**
 * @FileName YesOrNo
 * @Description
 * @Author jiuhao
 * @Date 2020/5/19 10:47
 * @Modified
 * @Version 1.0
 */
public enum  YesOrNo {

    YES(1, "是"),
    NO(0, "否");

    public final Integer type;

    public final String value;

    YesOrNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
