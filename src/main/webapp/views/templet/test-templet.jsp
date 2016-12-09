<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="yh" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  <body>
  	<form id="test_templet_form" action="${basePath}templet/loadTemplet.do" method="post">
    	<table cellpadding="5">
    		<tr>
    			<td>报表名称:</td>
    			<td><yh:select name="template" url="${basePath}templet/getData.do"></yh:select></td>
    		</tr>
    		<tr>
    			<td>类型:</td>
    			<td><input class="easyui-textbox" type="text" name="type" value="pdf" data-options="required:true"></input></td>
    		</tr>
    	</table>
    </form>
    <button onclick="show()" >展示</button>
    <button onclick="download()" >下载</button>
	<script type="text/javascript" src="${basePath}js/tools.js"></script>
    <script type="text/javascript">
		function show(){
			if($('#test_templet_form').form('validate')){
				var jsonData = JSON.stringify($("#test_templet_form").serializeObject());
				$.ajax({
					type:"post",
					url:"${basePath}templet/saveTemplet.do",
					data:{data:jsonData},
					success:function(data){
						alert(data);
					}
				});
			}else{
				alert('请完善表单数据');
			}
		}
		
		function download(){
			if($('#test_templet_form').form('validate')){
				$('#test_templet_form').submit();
			}else{
				alert('请完善表单数据');
			}
		}
	</script>
  </body>
</html>
