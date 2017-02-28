package com.yuhi.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.yuhi.util.JasperOutputUtil;

@Service
public class AssemblyReportServiceImpl implements AssemblyReportService{
	
	@Autowired
	private DataDao datadao;
	
	private static String DOWNLOAD_PATH = "G:\\apache-tomcat-7.0.57-windows-x64\\apache-tomcat-7.0.57\\webapps\\file";
	/**
	 * 根据外部数据组装
	 * @param model
	 * @param data
	 */
	@Override
	public void AssemblyByData(ModelMap model,JSONObject data,JSONObject template,HttpServletResponse response){
		Map<String,Object> parameters = new HashMap<String, Object>();
		if(data.getString("params").length()>2)parameters = JSON.parseObject(data.getString("params"), Map.class);
		File file = new File(DOWNLOAD_PATH+template.getString("jasperurl"));
		//模板地址,设置外部参数,设置输出类型
//		model.addAttribute("url", template.getString("jasperurl"));
//		model.addAttribute("model", parameters);
//		model.addAttribute("jrMainDataSource", BaseTools.toJRMapDataSource(null, new ArrayList<JSONObject>()));
//		model.addAttribute("format", data.getString("type"));
		
		JasperOutputUtil.show(file, parameters, null,response);
	}
	
	/**
	 * 根据数据源组装
	 * @param model
	 * @param data
	 * @throws JRException
	 */
	@Override
	public void AssemblyBySource(ModelMap model,JSONObject data,JSONObject template,HttpServletResponse response){
		List<JSONObject> dataSource = datadao.findAllBySQL(template.getString("sql_sentence"),template.getString("params").split(","));
		File file = new File(DOWNLOAD_PATH+template.getString("jasperurl"));
		//配置数据集
		Map<String,Object> parameters = new HashMap<String, Object>();
		parameters.put(data.getString("dataset"), BaseTools.toJRMapDataSource(null, dataSource));
		//模板地址,设置数据源,设置输出类型
//		model.addAttribute("url", template.getString("jasperurl"));
//		model.addAttribute("model", parameters);
//		model.addAttribute("jrMainDataSource", BaseTools.toJRMapDataSource(null, dataSource));
//		model.addAttribute("format", data.getString("type"));
		
		JasperOutputUtil.show(file, parameters, BaseTools.toJRMapDataSource(null, dataSource),response);
	}
	
	/**
	 * 根据外部数据和数据源组装
	 * @param model
	 * @param data
	 * @throws JRException
	 */
	@Override
	public void AssemblyByDataAndSource(ModelMap model,JSONObject data,JSONObject template,HttpServletResponse response){
		Map<String,Object> parameters = new HashMap<String, Object>();
		if(data.getString("params").length()>2)parameters = JSON.parseObject(data.getString("params"), Map.class);
		List<JSONObject> dataSource = datadao.findAllBySQL(template.getString("sql_sentence"),template.getString("params").split(","));
		File file = new File(DOWNLOAD_PATH+template.getString("jasperurl"));
		//配置数据集
		parameters.put(data.getString("dataset"), BaseTools.toJRMapDataSource(null, dataSource));
		//模板地址,设置外部参数,设置数据源,设置输出类型
//		model.addAttribute("url", template.getString("jasperurl"));
//		model.addAttribute("model", parameters);
//		model.addAttribute("jrMainDataSource", BaseTools.toJRMapDataSource(null, dataSource));
//		model.addAttribute("format", data.getString("type"));
		
		JasperOutputUtil.show(file, parameters, BaseTools.toJRMapDataSource(null, dataSource),response);
	}
	
	@Override
	public void DownloadByData(JSONObject data,JSONObject template,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> parameters = new HashMap<String, Object>();
		if(data.getString("params").length()>2)parameters = JSON.parseObject(data.getString("params"), Map.class);
		File file = new File(DOWNLOAD_PATH+template.getString("jasperurl"));
		
		JasperOutputUtil.export(file, parameters, null, data.getString("type"));
	}
	
	@Override
	public void DownLoadBySource(JSONObject data,JSONObject template,HttpServletRequest request,HttpServletResponse response) throws JRException{
		File file = new File(DOWNLOAD_PATH+template.getString("jasperurl"));
		List<JSONObject> dataSource = datadao.findAllBySQL(template.getString("sql_sentence"),template.getString("params").split(","));
		Map<String,Object> parameters = new HashMap<String, Object>();
		parameters.put(data.getString("dataset"), BaseTools.toJRMapDataSource(null, dataSource));
		
		JasperOutputUtil.export(file, parameters, BaseTools.toJRMapDataSource(null, dataSource), data.getString("type"));
	}
	
	@Override
	public void DownLoadByDataAndSource(JSONObject data,JSONObject template,HttpServletRequest request,HttpServletResponse response) throws JRException {
		Map<String,Object> parameters = new HashMap<String, Object>();
		if(data.getString("params").length()>2)parameters = JSON.parseObject(data.getString("params"), Map.class);
		File file = new File(DOWNLOAD_PATH+template.getString("jasperurl"));
		List<JSONObject> dataSource = datadao.findAllBySQL(template.getString("sql_sentence"),template.getString("params").split(","));
		parameters.put(data.getString("dataset"), BaseTools.toJRMapDataSource(null, dataSource));
		
		JasperOutputUtil.export(file, parameters, BaseTools.toJRMapDataSource(null, dataSource), data.getString("type"));
	}
}
