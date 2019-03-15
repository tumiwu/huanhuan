package com.example.a50067.huanhuan.SQLTable;

import org.litepal.crud.LitePalSupport;

import cn.bmob.v3.BmobObject;

/**
 * Created by 50067 on 2018/6/14.
 */

public class TBComComments  extends BmobObject {        //商品下的评论
    private int ccomtsType;      //评论类型 0:别人对商品的回复 1:别人对自己的回复
    private String commodityId;        //商品Id
    private String userId;             //评论者id
    private String comtsToUserId;      //被评论者id
    private String ccomtsContent;   //评论内容
    private String ccomtsDeletes;   //删除标记


    public int getCcomtsType() {
        return ccomtsType;
    }

    public void setCcomtsType(int ccomtsType) {
        this.ccomtsType = ccomtsType;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComtsToUserId() {
        return comtsToUserId;
    }

    public void setComtsToUserId(String comtsToUserId) {
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
