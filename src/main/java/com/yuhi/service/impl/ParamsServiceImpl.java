package com.yuhi.service.impl;

import java.io.File;
import java.util.Date;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yuhi.common.Constants;
import com.yuhi.dao.ParamsDao;
import com.yuhi.service.ParamsService;

@Service
public class ParamsServiceImpl implements ParamsService{
	
	@Autowired
	public ParamsDao dao;
	
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
	public void insertListByFile(File file , JSONObject template){
		try {
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(file);
			JRParameter[] param = jasperReport.getParameters();
			JRField[] field = jasperReport.getFields();
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("templet_id", template.getInteger("id"));
			jsonObject.put("templet_version", template.getInteger("version"));
			jsonObject.put("create_time", new Date());
			jsonObject.put("status", Constants.STATUS_ENABLE);
			
			for (int i = 20; i < param.length; i++) {
				jsonObject.put("name", param[i].getName());
				jsonObject.put("class", param[i].getValueClassName());
				jsonObject.put("type", Constants.TYPE_PARAM);
				dao.save(jsonObject);
			}
			if(field!=null&&field.length>0){
				for (int i = 0; i < field.length; i++) {
					jsonObject.put("name", field[i].getName());
					jsonObject.put("class", field[i].getValueClassName());
					jsonObject.put("type", Constants.TYPE_FIELD);
					dao.save(jsonObject);
				}
			}
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	@Override
	public JSONObject getEntityListByTemplateId(String id,String version) {
		JSONObject data = new JSONObject();
		data.put("success", Boolean.TRUE);
		data.put("totalRows", 10);
		data.put("curPage", 1);
		data.put("data", dao.findAllByTemplateId(id,version));
		return data;
	}
}
