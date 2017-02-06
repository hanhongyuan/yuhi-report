package com.yuhi.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yuhi.common.AjaxJson;
import com.yuhi.dao.TemplateDao;
import com.yuhi.service.TemplateService;

@Service
public class TemplateServiceImpl implements TemplateService {
	
	@Autowired
	public TemplateDao dao;
	
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
		jsonObject.put("code", UUID.randomUUID().toString());
		jsonObject.put("version",1);
		int flag = dao.save(jsonObject);
		return flag;
	}

	@Override
	public Integer updateEntity(JSONObject jsonObject) {
		int flag = dao.update(jsonObject);
		return flag;
	}

}
