package com.yuhi.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.yuhi.base.JdbcTemplatesDao;
import com.yuhi.common.BaseTools;

@Component
public class TemplateDao extends JdbcTemplatesDao {
	
	public Integer save(JSONObject jsonObject) {
		return super.insert(jsonObject);
	}
	
	public Integer update(JSONObject jsonObject) {
		super.update(BaseTools.JsonToMap(jsonObject), jsonObject.getString("id"));
		return jsonObject.getInteger("id");
	}
	
	public List<JSONObject> findAll(){
//		return super.queryForJsonList("SELECT * FROM TEMPLET");
		return super.queryForJsonListPage("SELECT * FROM TEMPLET", 1, 10);
	}
	
	public JSONObject findOneById(String id){
		return super.queryForJsonObject("SELECT * FROM TEMPLET WHERE ID = ?", id);
	}

	@Override
	protected String setControllerTable() {
		return "TEMPLET";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Autowired
//	@Qualifier(value="mdataSource")
//	private DataSource ds;
//	
//	public Integer save(Template entity) {
//		int flag = 0;
//		String sql = "INSERT INTO templet (jrxmlurl,jasperurl,code,version,name,status) VALUES(?,?,?,?,?,?)";
//		try{
//			Connection con = ds.getConnection();
//			PreparedStatement pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, entity.getJrxmlurl());
//			pstmt.setString(2, entity.getJasperurl());
//			pstmt.setString(3, entity.getCode());
//			pstmt.setInt(4, entity.getVersion());
//			pstmt.setString(5, entity.getName());
//			pstmt.setInt(6, entity.getStatus());
//			flag = pstmt.executeUpdate();
//			pstmt.close();
//			con.close();
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}
//	
//	public Integer update(Template entity) {
//		int flag = 0;
//		String sql = "UPDATE templet SET jrxmlurl=?,jasperurl=?,version=?,name=?,status=? WHERE id=?";
//		try{
//			Connection con = ds.getConnection();
//			PreparedStatement pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, entity.getJrxmlurl());
//			pstmt.setString(2, entity.getJasperurl());
//			pstmt.setInt(3, entity.getVersion());
//			pstmt.setString(4, entity.getName());
//			pstmt.setInt(5, entity.getStatus());
//			pstmt.setInt(6, entity.getId());
//			flag=pstmt.executeUpdate();
//			pstmt.close();
//			con.close();
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}
//	
//	public List<Template> findAll()  {
//		String sql = "select * from templet";
//		List<Template> Templetlist=new ArrayList<Template>();
//		try {
//			Connection con = ds.getConnection();
//			PreparedStatement pstmt = con.prepareStatement(sql);
//			ResultSet rs=pstmt.executeQuery();
//			while(rs.next()){
//				Template t=new  Template();
//				t.setId(rs.getInt("id"));
//				t.setJasperurl(rs.getString("jasperurl"));
//				t.setJrxmlurl(rs.getString("jrxmlurl"));
//				t.setCode(rs.getString("code"));
//				t.setVersion(rs.getInt("version"));
//				t.setName(rs.getString("name"));
//				t.setStatus(rs.getInt("status"));
//				Templetlist.add(t);
//			}
//			rs.close();
//			pstmt.close();
//			con.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return Templetlist;
//	}
//	
//	public Template findOneById(Template entity){
//		String sql = "select * from templet where id=?";
//		Template t=new  Template();
//		try {
//			Connection con = ds.getConnection();
//			PreparedStatement pstmt;
//			pstmt = con.prepareStatement(sql);
//			pstmt.setInt(1, entity.getId());
//			ResultSet rs=pstmt.executeQuery();
//			if(rs.next()){
//				t.setId(rs.getInt("id"));
//				t.setJasperurl(rs.getString("jasperurl"));
//				t.setJrxmlurl(rs.getString("jrxmlurl"));
//				t.setCode(rs.getString("code"));
//				t.setVersion(rs.getInt("version"));
//				t.setName(rs.getString("name"));
//				t.setStatus(rs.getInt("status"));
//			}
//			rs.close();
//			pstmt.close();
//			con.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return t;
//	}
}
