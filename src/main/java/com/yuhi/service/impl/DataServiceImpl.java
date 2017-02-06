package com.yuhi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.yuhi.common.AjaxJson;
import com.yuhi.dao.DataDao;
import com.yuhi.service.DataService;

@Service
public class DataServiceImpl implements DataService{

	@Autowired
	public DataDao dao;
	
	@Override
	public JSONObject getEntityList() {
//		AjaxJson data = new AjaxJson();
//		data.setObj(dao.findAll());
		JSONObject data = new JSONObject();
		return data;
	}

	@Override
	public JSONObject getEntityById(String id) {
		JSONObject data = dao.findOneById(id);
		return data;
	}

	@Override
	public Integer insertEntity(JSONObject jsonObject) {
		int flag = dao.save(jsonObject);
		return flag;
	}

	@Override
	public Integer updateEntity(JSONObject jsonObject) {
		int flag = dao.update(jsonObject);
		return flag;
	}
}
