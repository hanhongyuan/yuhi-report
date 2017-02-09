package com.yuhi.service;

import com.alibaba.fastjson.JSONObject;

public interface VersionService extends BaseService {
	public JSONObject getEntityListByTemplateId(String TemplateId);
}
