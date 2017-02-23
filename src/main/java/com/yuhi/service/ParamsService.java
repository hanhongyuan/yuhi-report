package com.yuhi.service;

import java.io.File;

import net.sf.jasperreports.engine.JRException;

import com.alibaba.fastjson.JSONObject;

public interface ParamsService extends BaseService {
	public void insertListByFile(File file , JSONObject template);
	
	public JSONObject getEntityListByTemplateId(String TemplateId,String Version);
}
