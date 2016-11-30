<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  <body>
  	<form id="templet_form" method="post">
  		<input type="hidden" name="id" value="${Templet.id}">
  		<input type="hidden" name="version" value="${Templet.version}">
    	<table cellpadding="5">
    		<tr>
    			<td>报表名称:</td>
    			<td><input class="easyui-textbox" type="text" name="name" value="${Templet.name }" data-options="required:true"></input></td>
    		</tr>
    		<tr>
    			<td>状态:</td>
    			<td><input class="easyui-textbox" type="text" name="status" value="${Templet.status }" data-options="required:true"></input></td>
    		</tr>
    		<tr>
    			<td>jasper地址:</td>
    			<td><input id="jasperurl" class="easyui-textbox" type="text" name="jasperurl" value="${Templet.jasperurl}" readonly="readonly" data-options="required:true" style="width:500px"></input></td>
    		</tr>
    		<tr>
    			<td>jrxml地址:</td>
    			<td><input id="jrxmlurl" class="easyui-textbox" type="text" name="jrxmlurl" value="${Templet.jrxmlurl}" readonly="readonly" data-options="required:true" style="width:500px"></input></td>
    		</tr>
    	</table>
    </form>
    <table>
    	<tr>
   			<td>jasper模板文件:</td>
   			<td id="jasper_td"><input id="jasper" type="file" name="jasper" value=""><button onclick="uploadfile('jasper')">上传</button></td>
   		</tr>
   		<tr>
   			<td>jrxml模板文件:</td>
   			<td id="jrxml_td"><input id="jrxml" type="file" name="jrxml" value=""><button onclick="uploadfile('jrxml')">上传</button></td>
   		</tr>
    </table>
    <script type="text/javascript" src="${basePath}js/fileupload/ajaxfileupload.js"></script>
    <script type="text/javascript" src="${basePath}js/fileupload/jquery.fileupload.js"></script>
    <script type="text/javascript" src="${basePath}js/tools.js"></script>
    <script type="text/javascript">
    	var jasperurl_old = '${Templet.jasperurl}';
    	var jrxmlurl_old = '${Templet.jrxmlurl}';
    	var TempletId = '${Templet.id}';
    	var TempletVersion = '${Templet.version}'==""?0:'${Templet.version}';
    	var version_flag = 0;
    	
    	function sendform(){
			if($('#templet_form').form('validate')){
				if(version_flag)$("[name=version]").val(Number(TempletVersion)+1);
				var jsonData = JSON.stringify($("#templet_form").serializeObject());
				$.ajax({
					type:"post",
					url:"${basePath}editTemplet.do",
					data:{data:jsonData},
					success:function(data){
						if(data>=0){
							msg("成功");
					    	closeDialog($("#templet_edit"));
					    	$("#templet_table").datagrid("reload");
						} else {
							msg("异常");
						}
					}
				});
			}else{
				alert('请完善表单数据');
			}
		} 
		
		function cancelform(){
			if(jasperurl_old!=$("#jasperurl").val()){
			alert('jasperurl');
				$.post('${basePath}dropfile.do',{file:$("#jasperurl").val()},function(flag){
					if(flag){
						console.info('jasper文件回删成功!'); 
					}
				});
			}
			if(jrxmlurl_old!=$("#jrxmlurl").val()){
			alert('jrxmlurl');
				$.post('${basePath}dropfile.do',{file:$("#jrxmlurl").val()},function(flag){
					if(flag){
						console.info('jrxml文件回删成功!'); 
					}
				});
			}
		}
		
		function uploadfile(id){
			var name = document.getElementById(id).value.substring(document.getElementById(id).value.lastIndexOf("."));
			if(name==""){
				alert("请选择文件!");
				return
			} else if(name.substring(name.lastIndexOf("."))!="."+id){
				alert("请选择后缀名为"+id+"的文件!");
				return
			}
			$.ajaxFileUpload({
               	url:"${basePath}uploadfile.do?type="+id+"&id="+TempletId+"&version="+TempletVersion,
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
		}
	</script>
  </body>
</html>
