package com.yuhi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping("/index")
	public String getData(){
		return "/index";
	}
	
//	@RequestMapping("/menu")
//	public String menu(){
//		List<JSONObject> meun = new ArrayList<JSONObject>();
//		JSONObject meun1 = new JSONObject();
//		List<JSONObject> chirdmenus1 = new ArrayList<JSONObject>();
//		JSONObject meun11 = new JSONObject();
//		JSONObject meun12 = new JSONObject();
//		
//		meun11.put("id",11);
//		meun11.put("name", "模板管理");
//		meun11.put("type", "menu");
//		meun11.put("url", "");
//		meun11.put("parentid", 2);
//		meun11.put("parentids", "");
//		meun11.put("sortstring", "");
//		meun11.put("available", "1");
//		meun11.put("icon", "fa-table");
//		meun11.put("systemid", "report");
//		
//		meun12.put("id",12);
//		meun12.put("name", "数据源管理");
//		meun12.put("type", "menu");
//		meun12.put("url", "");
//		meun12.put("parentid", 2);
//		meun12.put("parentids", "");
//		meun12.put("sortstring", "");
//		meun12.put("available", "1");
//		meun12.put("icon", "fa-table");
//		meun12.put("systemid", "report");
//		
//		chirdmenus1.add(meun11);
//		chirdmenus1.add(meun12);
//		
//		meun1.put("id",2);
//		meun1.put("name", "数据源管理");
//		meun1.put("type", "menu");
//		meun1.put("url", "");
//		meun1.put("percode", "api:query");
//		meun1.put("parentid", 1);
//		meun1.put("parentids", "");
//		meun1.put("sortstring", "");
//		meun1.put("available", "1");
//		meun1.put("icon", "fa-table");
//		meun1.put("systemid", "report");
//		meun1.put("chirdmenus", chirdmenus1);
//		
//		meun.add(meun1);
//		String ss = meun.toString();
//		return ss;
//	}
}
