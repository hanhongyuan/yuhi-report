package com.yuhi.report;


import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.yuhi.datasource.StudentDataSource;

@Controller
public class JasperBean {

	private JRDataSource jrDatasource;

	public JasperBean() throws JRException {
		StudentDataSource dsStudent =  new StudentDataSource();
		jrDatasource = dsStudent.create(null);
	}

	@RequestMapping(value = "/jrreport", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
//		model.addAttribute("url", "/WEB-INF/jasper/JRStudent.jasper");
//		model.addAttribute("jrMainDataSource", jrDatasource);
//		model.addAttribute("format", "pdf");
//		return "iReportView";
		StudentDataSource dsStudent =  new StudentDataSource();
		try {
			jrDatasource = dsStudent.create(null);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("url", "/WEB-INF/jasper/report2.jasper");
		model.addAttribute("jrMainDataSource", jrDatasource);
		model.addAttribute("format", "pdf");
		return "iReportView";
	}
	
	
	
	
	@RequestMapping(value = "/jrreporthtml", method = RequestMethod.GET)
	public String print(ModelMap model) {
		StudentDataSource dsStudent =  new StudentDataSource();
		try {
			jrDatasource = dsStudent.create(null);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("url", "/WEB-INF/jasper/report12.jasper");
		model.addAttribute("jrMainDataSource", jrDatasource);
		model.addAttribute("format", "pdf");
		return "iReportView";
	}
	
	
	
	
	
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String prints(ModelMap model) {
		StudentDataSource dsStudent =  new StudentDataSource();
		try {
			jrDatasource = dsStudent.create(null);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("url", "/WEB-INF/jasper/report13.jasper");
		model.addAttribute("jrMainDataSource", jrDatasource);
		Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("age", "10");
		model.addAttribute("param", map);
		model.addAttribute("format", "pdf");
		return "iReportView";
	}
	
	@RequestMapping(value = "/stjrreporthtml", method = RequestMethod.GET)
	public String printss(ModelMap model) {
		StudentDataSource dsStudent =  new StudentDataSource();
		try {
			jrDatasource = dsStudent.create(null);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("url", "/WEB-INF/jasper/report10.jasper");
		model.addAttribute("jrMainDataSource", jrDatasource);
		/*Map<Object, Object> map=new HashMap<Object, Object>();
		map.put("age", "10");
		model.addAttribute("param", map);*/
		model.addAttribute("format", "html");
		return "iReportView";
	}
//	
//	@Autowired
//	@Qualifier(value="mdataSource")
//	private DataSource ds;
//	
//	@RequestMapping(value = "/queryIncomeSumServlet", method = RequestMethod.GET)
//    public void queryIncomeSumHtml(HttpServletRequest request,
//            HttpServletResponse response,Templet templet,HashMap<?, ?> map) throws IOException, JRException, SQLException {
//		//STEP 1 : 查询数据 
//		//STEP 2 : 指定数据源
//		//STEP 3 : 指定模板文件
//		StudentDataSource dsStudent =  new StudentDataSource();
//		try {
//			jrDatasource = dsStudent.create(null);
//		} catch (JRException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		String sql = "select * from templet where id=?";
//		Connection con = ds.getConnection();
//		PreparedStatement pstmt = con.prepareStatement(sql);
//		pstmt.setInt(1, templet.getId());
//		ResultSet rs=pstmt.executeQuery();
//		Templet t=new Templet();
//		if(rs.next()){
//			t.setId(rs.getInt("id"));
//			t.setJasperurl(rs.getString("jasperurl"));
//			t.setJrxmlurl(rs.getString("jrxmlurl"));
//			t.setCode(rs.getString("code"));
//			t.setVersion(rs.getInt("version"));
//			t.setName(rs.getString("name"));
//			t.setStatus(rs.getInt("status"));
//		}
//		
//		if(t.getCode().equals(templet.getCode())){
//			ServletContext context = request.getSession().getServletContext();
//			File reportFile = new File(context.getRealPath(t.getJasperurl()));
//			String exportFilePath = "tempPath";
//		}
//		rs.close();
//		pstmt.close();
//		con.close();
//	}
//	
//	@RequestMapping(value = "/goTemplet")
//	public String goTemplet(){
//		return "templet";
//	}
//	
//	@RequestMapping(value = "/getData")
//	@ResponseBody
//	public AjaxJson getData() {
//		String sql = "select * from templet";
//		Connection con;
//		AjaxJson data = new AjaxJson();
//		try {
//			con = ds.getConnection();
//			PreparedStatement pstmt = con.prepareStatement(sql);
//			ResultSet rs=pstmt.executeQuery();
//			List<Templet> Templetlist=new ArrayList<Templet>();
//			while(rs.next()){
//				Templet t=new  Templet();
//				t.setId(rs.getInt("id"));
//				t.setJasperurl(rs.getString("jasperurl"));
//				t.setJrxmlurl(rs.getString("jrxmlurl"));
//				t.setCode(rs.getString("code"));
//				t.setVersion(rs.getInt("version"));
//				t.setName(rs.getString("name"));
//				t.setStatus(rs.getInt("status"));
//				Templetlist.add(t);
//				data.setObj(Templetlist);
//			}
//			rs.close();
//			pstmt.close();
//			con.close();
//		} catch (SQLException e) {
//			Log.debug("执行错误!");
//			e.printStackTrace();
//		}
//		return data;
//	}
//	
//	@RequestMapping(value = "/goEditTemplet")
//	public String goEditTemplet(Templet templet,ModelMap map){
//		if(templet.getId()!=null){
//			String sql = "select * from templet where id=?";
//			try {
//				Connection con = ds.getConnection();
//				PreparedStatement pstmt = con.prepareStatement(sql);
//				pstmt.setInt(1, templet.getId());
//				ResultSet rs=pstmt.executeQuery();
//				if(rs.next()){
//					Templet t=new  Templet();
//					t.setId(rs.getInt("id"));
//					t.setJasperurl(rs.getString("jasperurl"));
//					t.setJrxmlurl(rs.getString("jrxmlurl"));
//					t.setCode(rs.getString("code"));
//					t.setVersion(rs.getInt("version"));
//					t.setName(rs.getString("name"));
//					t.setStatus(rs.getInt("status"));
//					map.put("Templet", t);
//				}
//				rs.close();
//				pstmt.close();
//				con.close();
//			} catch (SQLException e) {
//				Log.debug("执行错误!");
//				e.printStackTrace();
//			}
//		}
//		return "edit-templet";
//	}
//	
//	@RequestMapping(value = "/editTemplet")
//	@ResponseBody
//	public int editTemplet(Templet templet){
//		int flag = 0;
//		try {
//			String sql = null;
//			Connection con = ds.getConnection();
//			if(templet.getId()!=null){
//				sql = "UPDATE templet SET jrxmlurl=?,jasperurl=?,version=?,name=?,status=? WHERE id=?";
//				PreparedStatement pstmt = con.prepareStatement(sql);
//				pstmt.setString(1, templet.getJrxmlurl());
//				pstmt.setString(2, templet.getJasperurl());
//				pstmt.setInt(3, templet.getVersion()+1);
//				pstmt.setString(4, templet.getName());
//				pstmt.setInt(5, templet.getStatus());
//				pstmt.setInt(6, templet.getId());
//				flag=pstmt.executeUpdate();
//				pstmt.close();
//			}else {
//				sql = "INSERT INTO templet (jrxmlurl,jasperurl,code,version,name,status) VALUES(?,?,?,?,?,?)";
//				PreparedStatement pstmt = con.prepareStatement(sql);
//				pstmt.setString(1, templet.getJrxmlurl());
//				pstmt.setString(2, templet.getJasperurl());
//				pstmt.setString(3, UUID.randomUUID().toString());
//				pstmt.setInt(4, 1);
//				pstmt.setString(5, templet.getName());
//				pstmt.setInt(6, templet.getStatus());
//				flag=pstmt.executeUpdate();
//				pstmt.close();
//			}
//			con.close();
//		} catch (SQLException e) {
//			Log.debug("执行错误!");
//			e.printStackTrace();
//		}
//		return flag;
//	}
//	
//	@RequestMapping(value = "/uploadfile")
//	@ResponseBody
//	public String uploadfile(HttpServletRequest req,String id) throws IllegalStateException, IOException{
//		@SuppressWarnings("deprecation")
//		String rootPath = req.getRealPath("/");
//		JSONObject obj=new JSONObject();
//		if(req instanceof MultipartHttpServletRequest){
//			MultipartFile pictureFile=	((MultipartHttpServletRequest) req).getMultiFileMap().getFirst(id);
//			String pictureFile_name =  pictureFile.getOriginalFilename();
//			String suffix = pictureFile_name.substring(pictureFile_name.lastIndexOf("."));
//			if (suffix==null){
//				obj.put("success", false);
//				return obj.toString();
//			}
//			String newFileName = UUID.randomUUID().toString()+suffix;
//			String url = "/report/"+id+"/version/"+newFileName;
//			File uploadPic = new java.io.File(rootPath+url);   
//			if(!uploadPic.exists()){				//判断文件路径是否存在
//				uploadPic.mkdirs();					//不存在即创建
//			}
//			pictureFile.transferTo(uploadPic);		//向磁盘写文件
//			obj.put("url", url);
//			obj.put("success", true);
//			return obj.toString();
//		}else{
//			obj.put("success", false);
//			return obj.toString();
//		}
//	}
//	
//	@RequestMapping(value = "/dropfile")
//	@ResponseBody
//	public Integer dropfile(HttpServletRequest req,String file){
//		int flag = 0;
//		String rootPath = req.getRealPath("/");
//		File dropfile = new File(rootPath+file);
//		if(dropfile.exists()){
//			dropfile.delete();
//			flag = 1;
//		}
//		return flag;
//	}
	
}
