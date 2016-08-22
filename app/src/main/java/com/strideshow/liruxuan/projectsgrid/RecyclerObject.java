package com.strideshow.liruxuan.projectsgrid;

import org.json.JSONObject;

/**
 * Created by liruxuan on 2016-08-16.
 */
public class RecyclerObject {
    private int type;
    private JSONObject data;

    RecyclerObject(JSONObject jsonObject, int type) {
        this.data = jsonObject;
        this.type = type;
    }

    public JSONObject getData() {
        return data;
    }

    public int getType() {
        return type;
    }
}
