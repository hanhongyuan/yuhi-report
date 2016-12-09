package com.yuhi.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class BaseTools {
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> JsonToMap(JSONObject jsonObject){
		return JSON.parseObject(jsonObject.toJSONString(),Map.class);
	}
	
	public static JRDataSource toJRMapDataSource(JasperReport jrReport,List<JSONObject> data){
		Collection<Map<String, ?>> list = new ArrayList<Map<String,?>>();
		for(JSONObject jsonObject:data){
			list.add(jsonObject);
		}
		return new JRMapCollectionDataSource(list);
	}
}
