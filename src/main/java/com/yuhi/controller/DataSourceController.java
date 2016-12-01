package com.yuhi.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuhi.common.AjaxJson;
import com.yuhi.service.DataSourceService;

@Controller("dataSource")
public class DataSourceController {

	@Resource
	private DataSourceService dataSourceService;
	
	@RequestMapping("/goDataSource")
	public String goDataSource(){
		return "views/datasource/datasource";
	}
	
	@RequestMapping("/getData")
	@ResponseBody
	public AjaxJson getData(){
		AjaxJson data = dataSourceService.getEntityList();
		return data;
	}
	
	@RequestMapping("/goEditDataSource")
	public String goEditDataSource(ModelMap map,String id){
		if(id!=null){
			map.put("DataSource", dataSourceService.getEntityById(id));
		}
		return "views/datasource/edit-datasource";
	}
	
	@RequestMapping("/editDataSource")
	public Integer editDataSource(String data){
		JSONObject dataSource = JSON.parseObject(data);
		if(dataSource.getInteger("id")!=null){
			return dataSourceService.updateEntity(dataSource);
		}else{
			dataSource.remove("id");
			return dataSourceService.insertEntity(dataSource);
		}
	}
}
