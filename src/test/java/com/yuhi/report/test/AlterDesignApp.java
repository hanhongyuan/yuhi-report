package com.yuhi.report.test;
/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2013 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.util.ClasspathResourceLoader;
import org.springframework.core.io.ClassPathResource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRRectangle;
import net.sf.jasperreports.engine.JRStaticText;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;
import net.sf.jasperreports.engine.base.JRBaseStaticText;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.type.FillEnum;
import net.sf.jasperreports.engine.util.AbstractSampleApp;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: AlterDesignApp.java 7056 2014-04-28 09:30:15Z teodord $
 */
public class AlterDesignApp extends AbstractSampleApp
{
	
	
	/**
	 *
	 */
	public static void main(String[] args)
	{
//		main(new AlterDesignApp(), new String[]{"fill"});
		try {
			new AlterDesignApp().test();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	/**
	 *
	 */
	public void test() throws JRException
	{
		try {
//			fill();
//			pdf();
			xhtml();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void xhtml() throws JRException, IOException
	{
		long start = System.currentTimeMillis();
//		File sourceFile = new File("build/reports/HtmlComponentReport.jrprint");
		ClassPathResource cs=new ClassPathResource("build/reports/AlterDesignReport.jrprint");
		File sourceFile = cs.getFile();
		JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

		File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".x.html");
		
		/*net.sf.jasperreports.engine.export.JRXhtmlExporter exporter = 
			new net.sf.jasperreports.engine.export.JRXhtmlExporter();
		exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT,"pt"); //默认情况下用的是px,会导致字体缩小
//		exporter.setParameter(JRHtmlExporterParameter.FRAMES_AS_NESTED_TABLES,false); 
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleHtmlExporterOutput(destFile));
		
		exporter.exportReport();*/

//		 JasperExportManager.exportReportToHtmlFile(jasperPrint, destFile.getAbsolutePath());
		
		 
		 JRHtmlExporter jrHtmlExporter = new JRHtmlExporter();   
//		 jrHtmlExporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT,"pt");
		 jrHtmlExporter.setParameter(JRExporterParameter.OUTPUT_WRITER, new SimpleHtmlExporterOutput(destFile));
		 
//		 jrHtmlExporter.setParameter(JRHtmlExporterParameter.FRAMES_AS_NESTED_TABLES,false); 
		 jrHtmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		 jrHtmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(destFile));
		 jrHtmlExporter.exportReport();
		 
		System.err.println("XHTML creation time : " + (System.currentTimeMillis() - start));
	}
	/**
	 * @throws IOException 
	 *
	 */
	public void fill() throws JRException, IOException
	{
		long start = System.currentTimeMillis();
		ClassPathResource cs=new ClassPathResource("build/reports/AlterDesignReport.jasper");
		File sourceFile = cs.getFile();
		System.err.println(" : " + sourceFile.getAbsolutePath());
		JasperReport jasperReport = (JasperReport)JRLoader.loadObject(sourceFile);
		
		JRStaticText jrStaticText=(JRStaticText) jasperReport.getTitle().getElementByKey("stext");
		jrStaticText.setText("sadsadsadsad");
		
		JRRectangle rectangle = (JRRectangle)jasperReport.getTitle().getElementByKey("first.rectangle");
//		rectangle.setFill(FillEnum.valueOf("sadsadsa"));
		rectangle.setForecolor(new Color((int)(16000000 * Math.random())));
		rectangle.setBackcolor(new Color((int)(16000000 * Math.random())));

		rectangle = (JRRectangle)jasperReport.getTitle().getElementByKey("second.rectangle");
		rectangle.setForecolor(new Color((int)(16000000 * Math.random())));
		rectangle.setBackcolor(new Color((int)(16000000 * Math.random())));

		rectangle = (JRRectangle)jasperReport.getTitle().getElementByKey("third.rectangle");
		rectangle.setForecolor(new Color((int)(16000000 * Math.random())));
		rectangle.setBackcolor(new Color((int)(16000000 * Math.random())));

		JRStyle style = jasperReport.getStyles()[0];
		style.setFontSize(16f);
		style.setItalic(true);

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, (JRDataSource)null);
		
		File destFile = new File(sourceFile.getParent(), jasperReport.getName() + ".jrprint");
		JRSaver.saveObject(jasperPrint, destFile);
		
		System.err.println("Filling time : " + (System.currentTimeMillis() - start));
	}


	/**
	 *
	 */
	public void print() throws JRException
	{
		long start = System.currentTimeMillis();
		JasperPrintManager.printReport("build/reports/AlterDesignReport.jrprint", true);
		System.err.println("Printing time : " + (System.currentTimeMillis() - start));
	}

	
	/**
	 * @throws IOException 
	 *
	 */
	public void pdf() throws JRException, IOException
	{
		long start = System.currentTimeMillis();
		ClassPathResource cs=new ClassPathResource("build/reports/AlterDesignReport.jrprint");
		File sourceFile = cs.getFile();
		JasperExportManager.exportReportToPdfFile(sourceFile.getAbsolutePath());
		System.err.println("PDF creation time : " + (System.currentTimeMillis() - start));
	}

	
}
