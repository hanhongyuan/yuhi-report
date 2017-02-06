package com.yuhi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yuhi.common.AjaxJson;
import com.yuhi.dao.DataSourceDao;
import com.yuhi.service.DataSourceService;

@Service
public class DataSourceServiceImpl implements DataSourceService{

	@Autowired
	public DataSourceDao dao;
	
	@Override
	public JSONObject getEntityList() {
//		AjaxJson data = new AjaxJson();
//		data.setObj(dao.findAll());
		JSONObject data = new JSONObject();
		data.put("success", Boolean.TRUE);
		data.put("totalRows", 10);
		data.put("curPage", 1);
		data.put("data", dao.findAll());
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
