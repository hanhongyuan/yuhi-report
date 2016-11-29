<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="yh" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  <body>
  	<form id="test_templet_form" method="post">
    	<table cellpadding="5">
    		<tr>
    			<td>报表名称:</td>
    			<td><yh:select name="id" url="${basePath}getData.do"></yh:select></td>
    		</tr>
    		<tr>
    			<td>类型:</td>
    			<td><input class="easyui-textbox" type="text" name="type" value="pdf" data-options="required:true"></input></td>
    		</tr>
    	</table>
    </form>
    <button onclick="show()" >展示</button>
    <button onclick="download()" >下载</button>
    <script type="text/javascript" src="${basePath}js/fileupload/ajaxfileupload.js"></script>
    <script type="text/javascript" src="${basePath}js/fileupload/jquery.fileupload.js"></script>
    <script type="text/javascript">
		function openwindow(aa){
		    return window.open(aa,'newindow','height=600,width=900,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
		}
		
		function show(){
			var url="${basePath}saveTemplet.do?id="+$("[name=id]").val()+"&type="+$("[name=type]").val();
			openwindow(url);
		}
		
		function download(){
			//var url="${basePath}loadTemplet.do?id="+$("[name=id]").val()+"&type="+$("[name=type]").val()+"&code="+$("[name=code]").val();
			//openwindow(url);
			//$.post('${basePath}loadTemplet.do',{id:$("[name=id]").val(),type:$("[name=type]").val()});
		}
	</script>
  </body>
</html>
