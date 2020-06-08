package com.imooc.pojo.vo;

import java.util.List;

/**
 * @FileName categoryVO
 * @Description 商品搜索
 * @Author jiuhao
 * @Date 2020/5/19 12:02
 * @Modified
 * @Version 1.0
 */
public class SearchItemsVO {

    private String itemId;

    private String itemName;

    private Integer sellCounts;

    private String imgUrl;

    private int price;

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

    public Integer getSellCounts() {
        return sellCounts;
    }

    public void setSellCounts(Integer sellCounts) {
        this.sellCounts = sellCounts;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
