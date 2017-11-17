package com.cif.winds.beans;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * Created by puhui on 2017/5/3.
 */
@Data
public class TagInfo {
    private int lable_id;
    private String lable_name;
    private int channelId;
    private String lable_type_name;
    private String lable_status;
    private String lable_sql;
    private Date create_time;
    private Date update_time;
}
