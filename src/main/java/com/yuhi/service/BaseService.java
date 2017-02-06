package com.yuhi.service;

import com.alibaba.fastjson.JSONObject;
import com.yuhi.common.AjaxJson;

public interface BaseService {

	public JSONObject getEntityList();
	
	public JSONObject getEntityById(String id);
	
	public Integer insertEntity(JSONObject jsonObject);
	
	public Integer updateEntity(JSONObject jsonObject);
}
