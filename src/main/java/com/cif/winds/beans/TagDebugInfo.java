package com.cif.winds.beans;

import lombok.Data;
import org.apache.commons.collections.map.HashedMap;

import java.util.Date;
import java.util.Map;

/**
 * Created by Martin on 2017/4/28.
 */
@Data
public class TagDebugInfo {
    private String tagId;
    private String sql;
    private Map<String,Object> params = new HashedMap();
    private long castTime;
    private Date beginTime;
    private Date endTime;
}
