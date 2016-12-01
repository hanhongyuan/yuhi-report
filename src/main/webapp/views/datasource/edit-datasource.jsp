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
    		<%-- <tr>
    			<td>状态:</td>
    			<td><input class="easyui-textbox" type="text" name="status" value="${DataSource.status }" data-options="required:true"></input></td>
    		</tr> --%>
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
    	/* var jasperurl_old = '${DataSource.jasperurl}';
    	var jrxmlurl_old = '${DataSource.jrxmlurl}';
    	var DataSourceId = '${DataSource.id}';
    	var DataSourceVersion = '${DataSource.version}'==""?0:'${DataSource.version}';
    	var version_flag = 0; */
    	
    	function sendform(){
			if($('#datasource_form').form('validate')){
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
				alert('请完善表单数据');
			}
		} 
		
		/* function cancelform(){
			if(jasperurl_old!=$("#jasperurl").val()){
			alert('jasperurl');
				$.post('${basePath}datasource/dropfile.do',{file:$("#jasperurl").val()},function(flag){
					if(flag){
						console.info('jasper文件回删成功!'); 
					}
				});
			}
			if(jrxmlurl_old!=$("#jrxmlurl").val()){
			alert('jrxmlurl');
				$.post('${basePath}datasource/dropfile.do',{file:$("#jrxmlurl").val()},function(flag){
					if(flag){
						console.info('jrxml文件回删成功!'); 
					}
				});
			}
		} */
		
		/* function uploadfile(id){
			var name = document.getElementById(id).value.substring(document.getElementById(id).value.lastIndexOf("."));
			if(name==""){
				alert("请选择文件!");
				return
			} else if(name.substring(name.lastIndexOf("."))!="."+id){
				alert("请选择后缀名为"+id+"的文件!");
				return
			}
			$.ajaxFileUpload({
               	url:"${basePath}datasource/uploadfile.do?type="+id+"&id="+DataSourceId+"&version="+DataSourceVersion,
               	secureuri:false,
               	fileElementId:id,
              	dataType: 'json',
               	success:function(data){
               		if(data.success){
               			var a = document.getElementsByName(id+'url');
               			$("#"+id+"url").textbox('setValue',data.url);
               			$("#"+id+"_td").html('<p>上传成功!</p>');
               			//版本更新标记
               			version_flag=1;
               		};
                },
                error: function (){  
					console.info('上传失败:未知异常!');  
               	}
			});
		} */
	</script>
  </body>
</html>
