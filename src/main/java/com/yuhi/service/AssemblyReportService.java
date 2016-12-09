package com.yuhi.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSONObject;

public interface AssemblyReportService {
	
	public void AssemblyByData(ModelMap model,JSONObject data,JSONObject template);
	
	public void AssemblyBySource(ModelMap model,JSONObject data,JSONObject template) throws JRException;
	
	public void AssemblyByDataAndSource(ModelMap model,JSONObject data,JSONObject template) throws JRException;
	
	public void DownloadByData(JSONObject data,JSONObject template,HttpServletRequest request,HttpServletResponse response);
	
	public void DownLoadBySource(JSONObject data,JSONObject template,HttpServletRequest request,HttpServletResponse response) throws JRException;
	
	public void DownLoadByDataAndSource(JSONObject data,JSONObject template,HttpServletRequest request,HttpServletResponse response) throws JRException;
}
