package com.yuhi.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRConditionalStyle;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRDefaultStyleProvider;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRParagraph;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.JRQuery;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.type.FillEnum;
import net.sf.jasperreports.engine.type.HorizontalAlignEnum;
import net.sf.jasperreports.engine.type.LineSpacingEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.RotationEnum;
import net.sf.jasperreports.engine.type.ScaleImageEnum;
import net.sf.jasperreports.engine.type.VerticalAlignEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.HtmlExporterConfiguration;
import net.sf.jasperreports.export.HtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterConfiguration;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.type.HtmlSizeUnitEnum;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.yuhi.common.BaseTools;
import com.yuhi.common.MapData;
import com.yuhi.common.PropertiesUtil;
import com.yuhi.common.Constants;
import com.yuhi.dao.DataDao;
import com.yuhi.service.AssemblyReportService;
import com.yuhi.service.DataService;
import com.yuhi.service.ParamsService;
import com.yuhi.service.TemplateService;
import com.yuhi.service.VersionService;
import com.yuhi.util.HtmlComponentApp;
import com.yuhi.util.JasperHelper;
import com.yuhi.util.JasperOutputUtil;

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
	
	@Resource
	private ParamsService paramsService;
	
	@Autowired
	private DataDao datadao;
	
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
	public void checkTemplet(ModelMap model,String id,HttpServletResponse response) throws JRException {
		JSONObject data = dataService.getEntityById(id);
		JSONObject templet = templateService.getEntityById(data.getString("template"));
		switch (templet.getInteger("mode")){
			case Constants.ASSEMBLY_DATA:assemblyReportService.AssemblyByData(model,data,templet,response);break;
			case Constants.ASSEMBLY_SOURCE:assemblyReportService.AssemblyBySource(model,data,templet,response);break;
			case Constants.ASSEMBLY_DATA_SOURCE:assemblyReportService.AssemblyByDataAndSource(model,data,templet,response);break;
		}
//		return "iReportView";
	}
	
	@RequestMapping(value = "/loadTemplet")
	public void loadTemplet(String id,HttpServletRequest request,HttpServletResponse response) throws JRException {
		JSONObject data = dataService.getEntityById(id);
		JSONObject templet = templateService.getEntityById(data.getString("template"));
		switch (templet.getInteger("mode")){
			case Constants.ASSEMBLY_DATA:assemblyReportService.DownloadByData(data,templet, request, response);break;
			case Constants.ASSEMBLY_SOURCE:assemblyReportService.DownLoadBySource(data,templet, request, response);break;
			case Constants.ASSEMBLY_DATA_SOURCE:assemblyReportService.DownLoadByDataAndSource(data,templet, request, response);break;
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
	
	@RequestMapping(params = "goParams")
	public String goParams(ModelMap map,String id){
		if(id!=null){
			map.put("Templet", templateService.getEntityById(id));
		}else {
			map.put("Templet",new JSONObject());
		}
		return "modules/templet/comp/templet-params";
	}
	
	@RequestMapping(params = "goInvocation")
	public String goInvocation(ModelMap map,String id){
		if(id!=null){
			map.put("Templet", templateService.getEntityById(id));
		}else {
			map.put("Templet",new JSONObject());
		}
		return "modules/templet/comp/templet-invocation";
	}
	
	@RequestMapping(value = "/editTemplet")
	@ResponseBody
	public int editTemplet(HttpServletRequest req){
		JSONObject templet = new JSONObject(new MapData(req));
		JSONObject version = new JSONObject();
		Integer id = null;
		Integer versionFlag = templet.getInteger("version_flag");
		templet.remove("version_flag");
		
		if(templet.getInteger("id")!=null){
			if(versionFlag==1){
				templet.put("version", templet.getInteger("version")+1);
				//历史版本
				version.put("templet_id", templet.get("id"));
				version.put("version", templet.get("version"));
				version.put("jasper_url", templet.get("jasperurl"));
				version.put("jrxml_url", templet.get("jrxmlurl"));
				versionService.insertEntity(version);
			}
			id = templateService.updateEntity(templet);
		}else {
			id = templateService.insertEntity(templet);
			if(versionFlag==1){
				templet.put("id", id);
				//历史版本
				version.put("templet_id", id);
				version.put("version", 1);
				version.put("jasper_url", templet.get("jasperurl"));
				version.put("jrxml_url", templet.get("jrxmlurl"));
				versionService.insertEntity(version);
				
				File file = new File("G:/apache-tomcat-7.0.57-windows-x64/apache-tomcat-7.0.57/webapps/file"+templet.get("jasperurl"));
				paramsService.insertListByFile(file, templet);
			}
		}
		return id;
	}
	
	@RequestMapping(value = "/uploadfile")
	@ResponseBody
	public String uploadfile(HttpServletRequest req,String type,String id,Integer version) throws IllegalStateException, IOException{
//				String rootPath = req.getRealPath("/");
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
        
        return new ResponseEntity<byte[]>(buffer,headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/testTemplet")
	public void testTemplet(String id,HttpServletRequest request,HttpServletResponse response) throws JRException, IOException {
		JSONObject data = dataService.getEntityById(id);
		JSONObject templet = templateService.getEntityById(data.getString("template"));
		
		File file = new File("G:\\apache-tomcat-7.0.57-windows-x64\\apache-tomcat-7.0.57\\webapps\\file"+templet.getString("jasperurl"));
		
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(file);
		
		JRDataset[] dataSet =  jasperReport.getDatasets();
		
		Map<String,Object> parameters = new HashMap<String, Object>();
		
		if(data.getString("params").length()>2)parameters = JSON.parseObject(data.getString("params"), Map.class);
		
		List<JSONObject> dataSource = datadao.findAllBySQL(jasperReport.getQuery().getText());
		
		if(dataSet!=null){
			for (int i = 0; i < dataSet.length; i++) {
				List<JSONObject> data1 = datadao.findAllBySQL(dataSet[i].getQuery().getText());
				parameters.put(dataSet[i].getName(), BaseTools.toJRMapDataSource(null, data1));
			}
		}
//		Map<String,Object> parameter = new HashMap<String, Object>();
//		parameters.put("people", "Kyle");
//		parameters.put("department", "Cloud");
		
//		JasperOutputUtil.export(file, null, null, Constants.FILE_DOCX);
//		JasperOutputUtil.fill(file, parameters, BaseTools.toJRMapDataSource(null, dataSource), Constants.FILE_HTML);
//		JasperOutputUtil.fill(file, parameters, BaseTools.toJRMapDataSource(null, dataSource), Constants.FILE_JXL);
//		JasperOutputUtil.fill(file, parameters, BaseTools.toJRMapDataSource(null, dataSource), Constants.FILE_ODS);
//		JasperOutputUtil.fill(file, parameters, BaseTools.toJRMapDataSource(null, dataSource), Constants.FILE_ODT);
//		JasperOutputUtil.fill(file, parameters, BaseTools.toJRMapDataSource(null, dataSource), Constants.FILE_PDF);
//		JasperOutputUtil.fill(file, parameters, BaseTools.toJRMapDataSource(null, dataSource), Constants.FILE_PPTX);
//		JasperOutputUtil.fill(file, parameters, BaseTools.toJRMapDataSource(null, dataSource), Constants.FILE_RTF);
//		JasperOutputUtil.fill(file, parameters, BaseTools.toJRMapDataSource(null, dataSource), Constants.FILE_XLS);
//		JasperOutputUtil.fill(file, parameters, BaseTools.toJRMapDataSource(null, dataSource), Constants.FILE_XLSX);
//		JasperOutputUtil.fill(file, parameters, BaseTools.toJRMapDataSource(null, dataSource), Constants.FILE_XML);
		
//		long start = System.currentTimeMillis();
//		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(file);
//		JRQuery query = jasperReport.getQuery();
//		System.err.print(query.getText());
		
//		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,BaseTools.toJRMapDataSource(null, dataSource));
//		jasperPrint.setLeftMargin(50);
//		jasperPrint.setRightMargin(50);
//		//JasperExportManager.exportReportToHtmlFile(jasperPrint);
//		
//		JRXhtmlExporter exporter = new JRXhtmlExporter();
//		
////		SimpleHtmlReportConfiguration conf=new SimpleHtmlReportConfiguration();
////		conf.setSizeUnit(HtmlSizeUnitEnum.POINT);
////		exporter.setConfiguration(conf);
//		
////		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
////		exporter.setExporterOutput(new SimpleHtmlExporterOutput(new File("E:/aa.html")));
//		
//		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//		exporter.setExporterOutput(new SimpleHtmlExporterOutput(new File("E:/aa.html")));
//		
//		exporter.exportReport();
//		
//		System.err.println("HTML creation time : " + (System.currentTimeMillis() - start));
		
//		JasperHelper.export(data.getString("type"), templet.getString("name"), file, request, response, parameters, BaseTools.toJRMapDataSource(null, dataSource));
	}
}
