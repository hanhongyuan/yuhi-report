package com.yuhi.entity;

public class Template {
	private Integer id;
	
	private String jasperurl;
	
	private String code;
	
	private Integer version;
	
	private String name;
	
	private Integer status;
	
	private String jrxmlurl;
	
	public Template(){
		
	}
	
	public Template(Integer id,String jasperurl,String code,Integer version,String name,
			Integer status,String jrxmlurl){
		this.id = id;
		this.jasperurl = jasperurl;
		this.code = code;
		this.version = version;
		this.name = name;
		this.status = status;
		this.jrxmlurl = jrxmlurl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJasperurl() {
		return jasperurl;
	}

	public void setJasperurl(String jasperurl) {
		this.jasperurl = jasperurl;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getJrxmlurl() {
		return jrxmlurl;
	}

	public void setJrxmlurl(String jrxmlurl) {
		this.jrxmlurl = jrxmlurl;
	}

}
