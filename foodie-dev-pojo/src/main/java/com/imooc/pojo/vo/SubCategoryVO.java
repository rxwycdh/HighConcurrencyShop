package com.imooc.pojo.vo;

/**
 * @FileName SubCategoryVO
 * @Description
 * @Author jiuhao
 * @Date 2020/5/19 13:54
 * @Modified
 * @Version 1.0
 */
public class SubCategoryVO {

    private Integer subId;

    private String subName;

    private String subType;

    private Integer subFatherId;

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Integer getSubFatherId() {
        return subFatherId;
    }

    public void setSubFatherId(Integer subFatherId) {
        this.subFatherId = subFatherId;
    }
}
