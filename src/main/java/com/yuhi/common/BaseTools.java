package com.yuhi.common;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class BaseTools {
	@SuppressWarnings("unchecked")
	public static Map<String,Object> JsonToMap(JSONObject jsonObject){
		return JSON.parseObject(jsonObject.toJSONString(),Map.class);
	}
}
