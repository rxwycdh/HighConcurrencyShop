package com.imooc.pojo.vo;

/**
 * @FileName SimpleItemVO
 * @Description 商品的简单数据显示类行
 * @Author jiuhao
 * @Date 2020/5/19 15:39
 * @Modified
 * @Version 1.0
 */
public class  SimpleItemVO {

    private String  itemId;

    private String itemName;

    private String itemUrl;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }
}
