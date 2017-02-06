package com.yuhi.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.yuhi.base.JdbcTemplatesDao;
import com.yuhi.common.BaseTools;

@Component
public class DataDao extends JdbcTemplatesDao{
	
	
	public Integer save(JSONObject jsonObject) {
		return super.insertOrReturnId(jsonObject);
	}
	
	public Integer update(JSONObject jsonObject) {
		super.update(BaseTools.JsonToMap(jsonObject), jsonObject.getString("id"));
		return jsonObject.getInteger("id");
	}
	
	public List<JSONObject> findAll(){
		return super.queryForJsonList("SELECT * FROM DATA");
	}
	
	public JSONObject findOneById(String id){
		return super.queryForJsonObject("SELECT * FROM DATA WHERE ID = ?", id);
	}
	
	public List<JSONObject> findAllBySQL(String SQL,Object... params){
		if(params[0].equals("")){
			return super.queryForJsonList(SQL);
		}
		return super.queryForJsonList(SQL,params);
	}

	@Override
	protected String setControllerTable() {
		return "DATA";
	}
}
