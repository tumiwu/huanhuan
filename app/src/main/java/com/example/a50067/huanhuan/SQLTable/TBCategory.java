package com.example.a50067.huanhuan.SQLTable;

import org.litepal.crud.LitePalSupport;

import cn.bmob.v3.BmobObject;

/**
 * Created by 50067 on 2018/6/14.
 */

public class TBCategory extends BmobObject {
    private String caName;
    private String caCount;
    private String caDelete;

    public String getCaName() {
        return caName;
    }

    public void setCaName(String caName) {
        this.caName = caName;
    }

    public String getCaCount() {
        return caCount;
    }

    public void setCaCount(String caCount) {
        this.caCount = caCount;
    }

    public String getCaDelete() {
        return caDelete;
    }

    public void setCaDelete(String caDelete) {
        this.caDelete = caDelete;
    }
}
