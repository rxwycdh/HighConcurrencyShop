package com.imooc.enums;

/**
 * @FileName PayMethod
 * @Description
 * @Author jiuhao
 * @Date 2020/5/24 20:26
 * @Modified
 * @Version 1.0
 */
public enum PayMethod {

    WEXIN(1, "微信"),
    ALIPAY(2, "支付宝");

    public Integer type;

    public String value;

    PayMethod(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
