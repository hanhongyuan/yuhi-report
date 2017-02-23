package com.yuhi.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.yuhi.base.JdbcTemplatesDao;
import com.yuhi.common.BaseTools;

@Component
public class ParamsDao extends JdbcTemplatesDao{
	public Integer save(JSONObject jsonObject) {
		return super.insert(jsonObject);
	}
	
	public Integer update(JSONObject jsonObject) {
		super.update(BaseTools.JsonToMap(jsonObject), jsonObject.getString("id"));
		return jsonObject.getInteger("id");
	}
	
	public List<JSONObject> findAll(){
		return super.queryForJsonList("SELECT * FROM PARAMS");
	}
	
	public JSONObject findOneById(String id){
		return super.queryForJsonObject("SELECT * FROM PARAMS WHERE ID = ?", id);
	}
	
	public List<JSONObject> findAllByTemplateId(String TemplateId ,String TemplateVersion) {
		return super.queryForJsonList("SELECT * FROM PARAMS WHERE TEMPLET_ID = ? AND TEMPLET_VERSION = ?", TemplateId, TemplateVersion );
	}

	@Override
	protected String setControllerTable() {
		return "PARAMS";
	}
}
