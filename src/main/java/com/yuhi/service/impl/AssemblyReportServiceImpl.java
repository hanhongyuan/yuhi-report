package com.yuhi.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuhi.common.BaseTools;
import com.yuhi.dao.DataDao;
import com.yuhi.service.AssemblyReportService;
import com.yuhi.util.JasperHelper;

@Service
public class AssemblyReportServiceImpl implements AssemblyReportService{
	
	@Autowired
	private DataDao datadao;
	
	/**
	 * 根据外部数据组装
	 * @param model
	 * @param data
	 */
	@Override
	public void AssemblyByData(ModelMap model,JSONObject data,JSONObject template){
		Properties parameters = new Properties();
		if(data.getString("params").length()>2)parameters = JSON.parseObject(data.getString("params"), Properties.class);
		//模板地址,设置外部参数,设置输出类型
		model.addAttribute("url", template.getString("jasperurl"));
		model.addAttribute("model", parameters);
		model.addAttribute("jrMainDataSource", BaseTools.toJRMapDataSource(null, new ArrayList<JSONObject>()));
		model.addAttribute("format", data.getString("type"));
	}
	
	/**
	 * 根据数据源组装
	 * @param model
	 * @param data
	 * @throws JRException
	 */
	@Override
	public void AssemblyBySource(ModelMap model,JSONObject data,JSONObject template) throws JRException{
		List<JSONObject> dataSource = datadao.findAllBySQL(template.getString("sql_sentence"),template.getString("params").split(","));
		//配置数据集
		Properties parameters = new Properties();
		parameters.put(data.getString("dataset"), BaseTools.toJRMapDataSource(null, dataSource));
		//模板地址,设置数据源,设置输出类型
		model.addAttribute("url", template.getString("jasperurl"));
		model.addAttribute("model", parameters);
		model.addAttribute("jrMainDataSource", BaseTools.toJRMapDataSource(null, dataSource));
		model.addAttribute("format", data.getString("type"));
	}
	
	/**
	 * 根据外部数据和数据源组装
	 * @param model
	 * @param data
	 * @throws JRException
	 */
	@Override
	public void AssemblyByDataAndSource(ModelMap model,JSONObject data,JSONObject template) throws JRException {
		Properties parameters = new Properties();
		if(data.getString("params").length()>2)parameters = JSON.parseObject(data.getString("params"), Properties.class);
		List<JSONObject> dataSource = datadao.findAllBySQL(template.getString("sql_sentence"),template.getString("params").split(","));
		//配置数据集
		parameters.put(data.getString("dataset"), BaseTools.toJRMapDataSource(null, dataSource));
		//模板地址,设置外部参数,设置数据源,设置输出类型
		model.addAttribute("url", template.getString("jasperurl"));
		model.addAttribute("model", parameters);
		model.addAttribute("jrMainDataSource", BaseTools.toJRMapDataSource(null, dataSource));
		model.addAttribute("format", data.getString("type"));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void DownloadByData(JSONObject data,JSONObject template,HttpServletRequest request,HttpServletResponse response){
		Properties parameters = new Properties();
		if(data.getString("params").length()>2)parameters = JSON.parseObject(data.getString("params"), Properties.class);
		File file = new File(request.getRealPath("/")+template.getString("jasperurl"));
		
		JasperHelper.export(data.getString("type"), template.getString("name"), file, request, response, parameters, BaseTools.toJRMapDataSource(null, new ArrayList<JSONObject>()));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void DownLoadBySource(JSONObject data,JSONObject template,HttpServletRequest request,HttpServletResponse response) throws JRException{
		File file = new File(request.getRealPath("/")+template.getString("jasperurl"));
		List<JSONObject> dataSource = datadao.findAllBySQL(template.getString("sql_sentence"),template.getString("params").split(","));
		Properties parameters = new Properties();
		parameters.put(data.getString("dataset"), BaseTools.toJRMapDataSource(null, dataSource));
		JasperHelper.export(data.getString("type"), template.getString("name"), file, request, response, parameters, BaseTools.toJRMapDataSource(null, dataSource));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void DownLoadByDataAndSource(JSONObject data,JSONObject template,HttpServletRequest request,HttpServletResponse response) throws JRException {
		Properties parameters = new Properties();
		if(data.getString("params").length()>2)parameters = JSON.parseObject(data.getString("params"), Properties.class);
		File file = new File(request.getRealPath("/")+template.getString("jasperurl"));
		List<JSONObject> dataSource = datadao.findAllBySQL(template.getString("sql_sentence"),template.getString("params").split(","));
		parameters.put(data.getString("dataset"), BaseTools.toJRMapDataSource(null, dataSource));
		JasperHelper.export(data.getString("type"), template.getString("name"), file, request, response, parameters, BaseTools.toJRMapDataSource(null, dataSource));
	}
}
