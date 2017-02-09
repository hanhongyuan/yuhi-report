package com.yuhi.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yuhi.service.VersionService;

@Controller
@RequestMapping("/version")
public class VersionController {
	
	@Resource
	private VersionService versionService;
	
	@RequestMapping(value="/getDataByTemplateId")
	@ResponseBody
	public JSONObject getDataByTemplateId (String TemplateId){
		JSONObject data = versionService.getEntityListByTemplateId(TemplateId);
		return data;
	}
}
