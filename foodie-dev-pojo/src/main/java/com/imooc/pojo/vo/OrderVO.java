package com.imooc.pojo.vo;

/**
 * @FileName OrderVO
 * @Description
 * @Author jiuhao
 * @Date 2020/5/26 10:55
 * @Modified
 * @Version 1.0
 */
public class OrderVO {
    private String orderId;

    private MerchantOrderVO merchantOrderVO;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchantOrderVO getMerchantOrderVO() {
        return merchantOrderVO;
    }

    public void setMerchantOrderVO(MerchantOrderVO merchantOrderVO) {
        this.merchantOrderVO = merchantOrderVO;
    }
}
