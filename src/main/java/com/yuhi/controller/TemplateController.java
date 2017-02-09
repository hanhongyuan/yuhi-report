package com.yuhi.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuhi.common.MapData;
import com.yuhi.common.PropertiesUtil;
import com.yuhi.common.Constants;
import com.yuhi.service.AssemblyReportService;
import com.yuhi.service.DataService;
import com.yuhi.service.TemplateService;
import com.yuhi.service.VersionService;

@Controller
@RequestMapping("/templet")
public class TemplateController {
	
	@Resource
	private TemplateService templateService;
	
	@Resource
	private DataService dataService;
	
	@Resource
	private AssemblyReportService assemblyReportService;
	
	@Resource
	private VersionService versionService;
	
	@RequestMapping(value = "/saveTemplet")
	@ResponseBody
    public String saveTemplet(String data,HttpServletRequest request) throws JRException, UnsupportedEncodingException {
		//ISO转UTF-8
		data = new String(data.getBytes("iso8859-1"),"UTF-8");
		JSONObject obj = JSON.parseObject(data);
		Integer data_id = dataService.insertEntity(obj);
		String url = (new PropertiesUtil("application.properties")).readProperty("host.url");
		
		String callback = request.getParameter("callback");
		return callback+"({\"url\":\""+url+"templet/checkTemplet.do?id="+data_id+"\"})";
	}
	
	@RequestMapping(value = "/checkTemplet")
	public String checkTemplet(ModelMap model,String id) throws JRException {
		JSONObject data = dataService.getEntityById(id);
		JSONObject templet = templateService.getEntityById(data.getString("template"));
		switch (templet.getInteger("mode")){
			case Constants.ASSEMBLY_DATA:assemblyReportService.AssemblyByData(model,data,templet);break;
			case Constants.ASSEMBLY_SOURCE:assemblyReportService.AssemblyBySource(model,data,templet);break;
			case Constants.ASSEMBLY_DATA_SOURCE:assemblyReportService.AssemblyByDataAndSource(model,data,templet);break;
		}
		return "iReportView";
	}
	
	@RequestMapping(value = "/loadTemplet")
	public void loadTemplet(String id,HttpServletRequest request,HttpServletResponse response) throws JRException {
		JSONObject data = dataService.getEntityById(id);
		JSONObject templet = templateService.getEntityById(data.getString("template"));
		switch (templet.getInteger("mode")){
			case Constants.ASSEMBLY_DATA:assemblyReportService.DownloadByData(data,templet, request, response);break;
			case Constants.ASSEMBLY_SOURCE:assemblyReportService.DownLoadBySource(data,templet, request, response);break;
			case Constants.ASSEMBLY_DATA_SOURCE:assemblyReportService.DownLoadBySource(data,templet, request, response);break;
		}
	}
	
	@RequestMapping(params = "go")
	public String goTemplet(){
		return "modules/templet/templet-list";
	}
	
	@RequestMapping(value = "/getData")
	@ResponseBody
	public JSONObject getData() {
		JSONObject data = templateService.getEntityList();
		return data;
	}
	
	@RequestMapping(params = "goEdit")
	public String goEditTemplet(ModelMap map,String id){
		if(id!=null){
			map.put("Templet", templateService.getEntityById(id));
		}else {
			map.put("Templet",new JSONObject());
		}
		return "modules/templet/comp/templet-edit";
	}
	
	@RequestMapping(params = "goHistory")
	public String goHistoryVersion(ModelMap map,String id){
		if(id!=null){
			map.put("Templet", templateService.getEntityById(id));
		}else {
			map.put("Templet",new JSONObject());
		}
		return "modules/templet/comp/templet-history";
	}
	
	/*@RequestMapping(value = "/goTestTemplet")
	public String goTestTemplet(){
		return "views/templet/test-templet";
	}*/
	
	@RequestMapping(value = "/editTemplet")
	@ResponseBody
	public int editTemplet(HttpServletRequest req){
		JSONObject templet = new JSONObject(new MapData(req));
		if(templet.getInteger("version_flag")==1){
			//添加version历史记录
			JSONObject version = new JSONObject();
			version.put("templet_id", templet.get("id"));
			version.put("version", templet.get("version"));
			version.put("jasper_url", templet.get("jasperurl"));
			version.put("jrxml_url", templet.get("jrxmlurl"));
			
			versionService.insertEntity(version);
			
			templet.put("version", templet.getInteger("version")+1);
		}
		templet.remove("version_flag");
		if(templet.getInteger("id")!=null){
			return templateService.updateEntity(templet);
		}else {
			return templateService.insertEntity(templet);
		}
	}
	
	@RequestMapping(value = "/uploadfile")
	@ResponseBody
	public String uploadfile(HttpServletRequest req,String type,String id,Integer version) throws IllegalStateException, IOException{
		//		String rootPath = req.getRealPath("/");
		String rootPath = "G:\\apache-tomcat-7.0.57-windows-x64\\apache-tomcat-7.0.57\\webapps\\file";
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
			String url = "/report/"+id+"/version"+(version+1)+"/"+newFileName;
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
	
	/*@RequestMapping(value="/downloadfile")
//	@ResponseBody
	public void downloadfile(String url,HttpServletResponse response){
		String path = "G:\\apache-tomcat-7.0.57-windows-x64\\apache-tomcat-7.0.57\\webapps\\file"+url;
		File file = new File(path);
		String filename = file.getName();
		
//		response.reset();
		response.addHeader("Conteawnt-Disposition", "attachment;filename=a.pdf");
//		response.addHeader("Content-Length", ""+file.length());
		response.setContentType("application/octet-stream");
		
		try {
//			InputStream fis = new BufferedInputStream(new FileInputStream(path));
//			byte[] buffer = new byte[fis.available()];
//			fis.read(buffer);
//			fis.close();
//			OutputStream out = new BufferedOutputStream(response.getOutputStream());
//			out.write(buffer);
//			out.flush();
//			out.close();
			FileInputStream inputStream = new FileInputStream(file);
			ServletOutputStream out = response.getOutputStream();
			int b = 0;
			byte[] buffer = new byte[512];
			while (b != -1){
				b = inputStream.read();
				if(b==-1)break;
				out.write(buffer, 0, b);
			}
			inputStream.close();
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	@RequestMapping(value = "/downloadfile",produces = {"application/vnd.ms-excel;charset=UTF-8"})
	public ResponseEntity<byte[]> downLoad(HttpServletRequest request,String url) throws Exception {
		String path = "G:\\apache-tomcat-7.0.57-windows-x64\\apache-tomcat-7.0.57\\webapps\\file"+url;
		File file = new File(path);
		String filename = file.getName();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", filename);
        
        InputStream fis = new BufferedInputStream(new FileInputStream(path));
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
        
//	  	byte[] byes=buffer;
        return new ResponseEntity<byte[]>(buffer,headers, HttpStatus.CREATED);
	}
}
