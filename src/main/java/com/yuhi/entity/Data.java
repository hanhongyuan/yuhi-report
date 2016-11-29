package com.yuhi.entity;

public class Data {
	private Integer id;
	
	private String key;
	
	private String template;
	
	private String params;
	
	private String type;
	
	private String datasource;
	
	public Data(){
		
	}
	
	public Data(Integer id,String template,String params,String type,String datasource,
			String key){
		this.id = id;
		this.template = template;
		this.params = params;
		this.type = type;
		this.datasource = datasource;
		this.key = key;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

}
