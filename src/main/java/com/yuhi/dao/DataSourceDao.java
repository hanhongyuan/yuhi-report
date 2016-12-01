package com.yuhi.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.yuhi.base.BaseJdbcDao;
import com.yuhi.common.BaseTools;

@Component
public class DataSourceDao extends BaseJdbcDao{
	
	private static final String SQL_TABLE_NAME = "DATA_SOURCE";
	
	public Integer save(JSONObject jsonObject) {
		int aa = super.insertOrReturnId(SQL_TABLE_NAME, jsonObject);
		return aa;
	}
	
	public Integer update(JSONObject jsonObject) {
		super.update(SQL_TABLE_NAME, BaseTools.JsonToMap(jsonObject), jsonObject.getString("id"));
		return jsonObject.getInteger("id");
	}
	
	public List<JSONObject> findAll(){
		return super.queryForJsonList("SELECT * FROM DATA_SOURCE");
	}
	
	public JSONObject findOneById(String id){
		return super.queryForJsonObject("SELECT * FROM DATA_SOURCE WHERE ID = ?", id);
	}
}
