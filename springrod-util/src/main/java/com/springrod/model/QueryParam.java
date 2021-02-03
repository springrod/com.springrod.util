package com.springrod.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数。
 * @author see!rafe
 * @time 2018-04-27。
 */
public class QueryParam extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = 1L;
    private int start;//开始
    private int length;//结束
    private int page;
 

    public QueryParam(Map<String, Object> params) { 
        this.putAll(params);
        // 分页参数
        this.page = params.get("page") == null ? 1 : Integer.parseInt(params.get("page").toString());
        this.start = params.get("start") == null ? 0 : Integer.parseInt(params.get("start").toString());
        this.length = params.get("length") == null ? 8 : Integer.parseInt(params.get("length").toString());
        this.put("page", page);
        this.put("length", length);
        this.put("start", start);
    }

 
    public QueryParam() {
        this.put("page", 1);
        this.put("start", 0); 
        this.put("length", 8);
    }

 
}
