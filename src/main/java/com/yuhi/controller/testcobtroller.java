package com.yuhi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.yuhi.dao.DataDao;

//@Controller
public class testcobtroller {

	@Autowired
	private DataDao dd;
	
	@RequestMapping(value = "/test")
    public String checkTemplet() {
		List<JSONObject> list = dd.findAll();
		System.out.println(list);
		return null;
	}
			
}
