package com.example.a50067.huanhuan.SQLTable;

import org.litepal.crud.LitePalSupport;

/**
 * Created by 50067 on 2018/6/14.
 */

public class TBComComments  extends LitePalSupport {        //商品下的评论
    private int id;     //评论id
    private int ccomtsType;      //评论类型 0:别人对商品的回复 1:别人对自己的回复
    private int commodityId;        //商品Id
    private int userId;             //评论者id
    private int comtsToUserId;      //被评论者id
    private String ccomtsContent;   //评论内容
    private String ccomtsDeletes;   //删除标记

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCcomtsType() {
        return ccomtsType;
    }

    public void setCcomtsType(int ccomtsType) {
        this.ccomtsType = ccomtsType;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getComtsToUserId() {
        return comtsToUserId;
    }

    public void setComtsToUserId(int comtsToUserId) {
        this.comtsToUserId = comtsToUserId;
    }

    public String getCcomtsContent() {
        return ccomtsContent;
    }

    public void setCcomtsContent(String ccomtsContent) {
        this.ccomtsContent = ccomtsContent;
    }

    public String getCcomtsDeletes() {
        return ccomtsDeletes;
    }

    public void setCcomtsDeletes(String ccomtsDeletes) {
        this.ccomtsDeletes = ccomtsDeletes;
    }
}
