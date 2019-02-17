package com.example.a50067.huanhuan.SQLTable;

import org.litepal.crud.LitePalSupport;

/**
 * Created by 50067 on 2018/6/14.
 */

public class TBCategory extends LitePalSupport{
    private int id;
    private String caName;
    private String caCount;
    private String caDelete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
