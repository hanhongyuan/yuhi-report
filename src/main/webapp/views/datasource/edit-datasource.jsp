<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head> 
  <body>
  	<form id="datasource_form" method="post">
  		<input type="hidden" name="id" value="${DataSource.id}">
    	<table cellpadding="5">
    		<tr>
    			<td>数据源名称:</td>
    			<td><input class="easyui-textbox" type="text" name="name" value="${DataSource.name }" data-options="required:true"></input></td>
    		</tr>
    		<tr>
    			<td>数据库表:</td>
    			<td><input class="easyui-textbox" type="text" name="table_name" value="${DataSource.table_name}" data-options="required:true"></input></td>
    		</tr>
    		<tr>
    			<td>参数:</td>
    			<td><input class="easyui-textbox" type="text" name="param" value="${DataSource.param}" data-options="required:true"></input></td>
    		</tr>
    		<tr>
    			<td>排序:</td>
    			<td><input class="easyui-textbox" type="text" name="sort" value="${DataSource.sort}" data-options="required:true"></input></td>
    		</tr>
    	</table>
    </form>
    <script type="text/javascript" src="${basePath}js/tools.js"></script>
    <script type="text/javascript">
		var DataSource = '${DataSource}'==""?"":JSON.parse('${DataSource}');
    
    	function sendform(){
			if($('#datasource_form').form('validate')){
				if(isChange(DataSource)){
					var jsonData = JSON.stringify($("#datasource_form").serializeObject());
					$.ajax({
						type:"post",
						url:"${basePath}datasource/editDataSource.do",
						data:{data:jsonData},
						success:function(data){
							if(data>=0){
								msg("成功");
						    	closeDialog($("#datasource_edit"));
						    	$("#datasource_table").datagrid("reload");
							} else {
								msg("异常");
							}
						}
					});
				}else{
					$('#datasource_edit').dialog("close");
				}
			}else{
				alert('请完善表单数据');
			}
		} 
	</script>
  </body>
</html>
