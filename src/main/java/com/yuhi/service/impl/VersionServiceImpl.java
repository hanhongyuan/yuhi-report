package com.yuhi.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yuhi.common.Constants;
import com.yuhi.dao.VersionDao;
import com.yuhi.service.VersionService;

@Service
public class VersionServiceImpl implements VersionService{
	
	@Autowired
	public VersionDao dao;
	
	@Override
	public JSONObject getEntityList() {
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
		jsonObject.put("status", Constants.STATUS_ENABLE);
		jsonObject.put("create_time", new Date());
		int flag = dao.save(jsonObject);
		return flag;
	}

	@Override
	public Integer updateEntity(JSONObject jsonObject) {
		int flag = dao.update(jsonObject);
		return flag;
	}
	
	@Override
	public JSONObject getEntityListByTemplateId(String id) {
		JSONObject data = new JSONObject();
		data.put("success", Boolean.TRUE);
		data.put("totalRows", 10);
		data.put("curPage", 1);
		data.put("data", dao.findAllByTemplateId(id));
		return data;
	}

}
