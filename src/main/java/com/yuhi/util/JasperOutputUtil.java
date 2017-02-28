package com.yuhi.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;

import com.yuhi.common.Constants;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleOdsReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.export.type.HtmlSizeUnitEnum;


/**
 * @author Kyle.Chan
 * @version 2017-02-24
 */
public class JasperOutputUtil{
	public static void show(File file,Map<String,Object> parameters,JRDataSource dataSource,HttpServletResponse response) {
		try {
			JRXhtmlExporter exporter = new JRXhtmlExporter();
			JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(file), parameters,dataSource);
			/*设置显示pt*/
			SimpleHtmlReportConfiguration conf=new SimpleHtmlReportConfiguration();
			conf.setSizeUnit(HtmlSizeUnitEnum.POINT);
			exporter.setConfiguration(conf);
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getOutputStream()));
			exporter.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void export(File file,Map<String,Object> parameters,JRDataSource dataSource,String type){
		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport((JasperReport) JRLoader.loadObject(file), parameters,dataSource);
			switch(type){
				case Constants.FILE_PDF :pdf(jasperPrint);break;
				case Constants.FILE_XLS :xls(jasperPrint);break;
				case Constants.FILE_JXL :jxl(jasperPrint);break;
				case Constants.FILE_HTML:html(jasperPrint);break;
				case Constants.FILE_DOCX:docx(jasperPrint);break;
				case Constants.FILE_PPTX:pptx(jasperPrint);break;
				case Constants.FILE_XLSX:xlsx(jasperPrint);break;
				case Constants.FILE_RTF :rtf(jasperPrint);break;
				case Constants.FILE_ODT :odt(jasperPrint);break;
//				case Constants.FILE_ODS :ods(jasperPrint);break; //未完善
				case Constants.FILE_XML :xml(jasperPrint);break;
			}
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	public static void pdf(JasperPrint jasperPrint) throws JRException{
		JasperExportManager.exportReportToPdfFile(jasperPrint, "E:/aa.pdf");;
	}
	
	public static void xls(JasperPrint jasperPrint) throws JRException{
		JRXlsExporter exporter = new JRXlsExporter();
		
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("E:/aa.xls"));
		SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
		configuration.setOnePagePerSheet(true);
		exporter.setConfiguration(configuration);
		
		exporter.exportReport();
	}

	@SuppressWarnings("deprecation")
	public static void jxl(JasperPrint jasperPrint) throws JRException{
		JExcelApiExporter exporter = new JExcelApiExporter();
		
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("E:/aa.jxl"));
		net.sf.jasperreports.export.SimpleJxlReportConfiguration configuration = new net.sf.jasperreports.export.SimpleJxlReportConfiguration();
		configuration.setOnePagePerSheet(true);
		exporter.setConfiguration(configuration);

		exporter.exportReport();
	}

//	public void html() throws JRException
//	{
//		long start = System.currentTimeMillis();
//		JasperExportManager.exportReportToHtmlFile("build/reports/HtmlComponentReport.jrprint");
//		System.err.println("HTML creation time : " + (System.currentTimeMillis() - start));
//	}
	
	@SuppressWarnings("deprecation")
	public static void html(JasperPrint jasperPrint) throws JRException{
		JRXhtmlExporter exporter = new JRXhtmlExporter();
		
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleHtmlExporterOutput("E:/aa.html"));
		
		exporter.exportReport();
	}

	public static void docx(JasperPrint jasperPrint) throws JRException{
		JRDocxExporter exporter = new JRDocxExporter();
		
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("E:/aa.docx"));
		
		exporter.exportReport();
	}

	public static void pptx(JasperPrint jasperPrint) throws JRException{
		JRPptxExporter exporter = new JRPptxExporter();
		
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("E:/aa.pptx"));

		exporter.exportReport();
	}

	public static void xlsx(JasperPrint jasperPrint) throws JRException{
		JRXlsxExporter exporter = new JRXlsxExporter();
		
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("E:/aa.xlsx"));
		SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
		configuration.setOnePagePerSheet(true);
		exporter.setConfiguration(configuration);
		
		exporter.exportReport();
	}

	public static void rtf(JasperPrint jasperPrint) throws JRException{
		JRRtfExporter exporter = new JRRtfExporter();
		
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleWriterExporterOutput("E:/aa.rtf"));
		
		exporter.exportReport();
	}

	public static void odt(JasperPrint jasperPrint) throws JRException{
		JROdtExporter exporter = new JROdtExporter();
		
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("E:/aa.odt"));
		
		exporter.exportReport();
	}

	public static void ods(JasperPrint jasperPrint) throws JRException{
		JROdsExporter exporter = new JROdsExporter();
		
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("E:/aa.ods"));
		SimpleOdsReportConfiguration configuration = new SimpleOdsReportConfiguration();
		configuration.setOnePagePerSheet(true);
		exporter.setConfiguration(configuration);
		
		exporter.exportReport();
	}

	public static void xml(JasperPrint jasperPrint) throws JRException{
		JasperExportManager.exportReportToXmlFile(jasperPrint, "E:/aa.xml", false);;
	}

}
