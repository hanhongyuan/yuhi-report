package com.yuhi.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yuhi.service.ParamsService;
import com.yuhi.service.VersionService;

@Controller
@RequestMapping("/params")
public class ParamsController {
	
	@Resource
	private ParamsService paramsService;
	
	@RequestMapping(value="/getDataByTemplateId")
	@ResponseBody
	public JSONObject getDataByTemplateId (String TemplateId,String TemplateVersion){
		JSONObject data = paramsService.getEntityListByTemplateId(TemplateId,TemplateVersion);
		return data;
	}
}
