package com.imooc.pojo.vo;

/**
 * @FileName MerchantOrderVO
 * @Description 传给支付中心的数据
 * @Author jiuhao
 * @Date 2020/5/26 10:52
 * @Modified
 * @Version 1.0
 */
public class MerchantOrderVO {

    private String merchantOrderId;

    private String merchantUserId;

    private Integer amount;

    private Integer payMethod;

    private String returnUrl;

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(String merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
