package com.yuhi.controller;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuhi.common.AjaxJson;
import com.yuhi.common.PropertiesUtil;
import com.yuhi.datasource.StudentDataSource;
import com.yuhi.entity.Template;
import com.yuhi.service.DataService;
import com.yuhi.service.TemplateService;
import com.yuhi.util.JasperHelper;

@Controller
public class TemplateController {
	
	private JRDataSource jrDatasource;

	@Resource
	private TemplateService templateService;
	
	@Resource
	private DataService dataService;
	
	@RequestMapping(value = "/saveTemplet")
	@ResponseBody
    public String saveTemplet(ModelMap model,String id,
    		String params,String type) throws JRException {
		
		JSONObject param = new JSONObject();
		param.put("people", "kyle");
		
		JSONObject data = new JSONObject();
		data.put("template", id);
		data.put("params", param.toJSONString());
		data.put("type", type);
		
		Integer data_id = dataService.insertEntity(data);
		
		PropertiesUtil url = new PropertiesUtil("application.properties");
		
		return url.readProperty("host.url")+"checkTemplet.do?id="+data_id;
	}
	
	@RequestMapping(value = "/checkTemplet")
	public String checkTemplet(ModelMap model,String id) throws JRException {
		
		JSONObject data = dataService.getEntityById(id);
		
		Properties parameters = JSON.parseObject(data.getString("params"), Properties.class);
		String type = data.getString("type");
		JSONObject template = templateService.getEntityById(data.getString("template"));
		
		StudentDataSource dsStudent =  new StudentDataSource();
		jrDatasource = dsStudent.create(null);
		
		//模板地址
		model.addAttribute("url", template.getString("jasperurl"));
		//设置外部参数
		model.addAttribute("model", parameters);
		//设置数据源
		model.addAttribute("jrMainDataSource", jrDatasource);
		//设置输出类型
		model.addAttribute("format", type);
		return "iReportView";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/loadTemplet")
	public void loadTemplet(String type, String id, String params,
            HttpServletRequest request, HttpServletResponse response) throws JRException {
		
		//外部参数遍历转型
		HashedMap par= JSON.parseObject(params, HashedMap.class);
		HashedMap parameters = par==null?new HashedMap():par;
		
		parameters.put("people", "kyle");
		
		StudentDataSource dsStudent =  new StudentDataSource();
		jrDatasource = dsStudent.create(null);
		
		JSONObject t = templateService.getEntityById(id);
		File file = new File(request.getRealPath("/")+t.getString("jasperurl"));
		
		JasperHelper.export(type, t.getString("name"), file, request, response, parameters, jrDatasource);
	}
	
	@RequestMapping(value = "/goTemplet")
	public String goTemplet(){
		return "templet";
	}
	
	@RequestMapping(value = "/getData")
	@ResponseBody
	public AjaxJson getData() {
		AjaxJson data = templateService.getEntityList();
		return data;
	}
	
	@RequestMapping(value = "/goEditTemplet")
	public String goEditTemplet(ModelMap map,String id){
		if(id!=null){
			map.put("Templet", templateService.getEntityById(id));
		}
		return "edit-templet";
	}
	
	@RequestMapping(value = "/goTestTemplet")
	public String goTestTemplet(){
		return "test-templet";
	}
	
	@RequestMapping(value = "/editTemplet")
	@ResponseBody
	public int editTemplet(String data){
		JSONObject templet = JSON.parseObject(data);
		if(templet.getInteger("id")!=null){
			return templateService.updateEntity(templet);
		}else {
			templet.remove("id");
			return templateService.insertEntity(templet);
		}
	}
	
	@RequestMapping(value = "/uploadfile")
	@ResponseBody
	public String uploadfile(HttpServletRequest req,String type,Template template) throws IllegalStateException, IOException{
		@SuppressWarnings("deprecation")
		String rootPath = req.getRealPath("/");
		JSONObject obj=new JSONObject();
		if(req instanceof MultipartHttpServletRequest){
			MultipartFile pictureFile=	((MultipartHttpServletRequest) req).getMultiFileMap().getFirst(type);
			String pictureFile_name =  pictureFile.getOriginalFilename();
			String suffix = pictureFile_name.substring(pictureFile_name.lastIndexOf("."));
			if (suffix==null){
				obj.put("success", false);
				return obj.toString();
			}
			String newFileName = UUID.randomUUID().toString()+suffix;
			String url = "/report/"+template.getId()+"/version"+(template.getVersion()+1)+"/"+newFileName;
			File uploadPic = new java.io.File(rootPath+url);   
			if(!uploadPic.exists()){				//判断文件路径是否存在
				uploadPic.mkdirs();					//不存在即创建
			}
			pictureFile.transferTo(uploadPic);		//向磁盘写文件
			obj.put("url", url);
			obj.put("success", true);
			return obj.toString();
		}else{
			obj.put("success", false);
			return obj.toString();
		}
	}
	
	@RequestMapping(value = "/dropfile")
	@ResponseBody
	public Integer dropfile(HttpServletRequest req,String file){
		int flag = 0;
		@SuppressWarnings("deprecation")
		String rootPath = req.getRealPath("/");
		File dropfile = new File(rootPath+file);
		if(dropfile.exists()){
			dropfile.delete();
			flag = 1;
		}
		return flag;
	}
}
