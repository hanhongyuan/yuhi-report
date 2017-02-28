<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Invocation Testing</title>
<style type="text/css">
	input {width: 400px;}
	select {width: 400px;}
</style>
</head>
<body>
	<form id="invocation_form">
		<table>
		<tr>
			<td><h5>模板编号:</h5></td>
			<td><input type="text" name="template"></td>
		</tr>
		<tr>
			<td><h5>报表类型:</h5></td>
			<td>
				<select name="type">
					<option>pdf</option>
					<option>html</option>
					<option>excel</option>
				</select>
			</td>
		</tr>
		<tr>
			<td><h5>数据集名称:</h5></td>
			<td><input type="text" name="dataset"></td>
		</tr>
		<tr>
			<td><h5>参数:</h5></td>
			<td><input type="text" name="params" readonly="readonly" style="width: 1200px;"></td>
		</tr>
		</table>
	</form>
	<table>
	<tr>
		<td><h5>参数名:</h5></td>
		<td><input name="key"></td>
		<td style="text-align: right;"><h5>参数值:</h5></td>
		<td><input name="value"></td>
		<td><button onclick="push_params()">新增参数</button></td>
		<td><button onclick="remove_params()">移除参数</button></td>
	</tr>
	<tr>
		<td><button onclick="submit()">提交表单</button></td>
	</tr>
	<tr>
		<td><h5>浏览链接:&nbsp;</h5></td>
		<td><input id="back_link" name="back_link" readonly="readonly"></td>
		<td style="text-align: right;"><button onclick="visit_url('back_link')">浏览</button></td>
	</tr>
	<tr>
		<td><h5>下载链接:&nbsp;</h5></td>
		<td><input id="load_link" name="load_link" readonly="readonly"></td>
		<td style="text-align: right;"><button onclick="visit_url('load_link')">下载</button></td>
	</tr>
	</table>
	<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
	<script type="text/javascript">
		var params=new Object();	
		
		function submit(){
			var json_data = JSON.stringify($("#invocation_form").serializeObject());
			$.ajax({
				type:"post",
				url:"templet/saveTemplet.do",
				data:{data:json_data},
				/* jsonp:'callback',
				dataType:'jsonp',
				contentType:"application/json;charset=UTF-8", */
				success:function(data){
					$("[name=back_link]").val(data.url);
					var download_link = (data.url).replace('checkTemplet','loadTemplet');
					$("[name=load_link]").val(download_link);
				}
			})
		}
		
		function push_params(){
			var key = $("[name=key]").val();
			var value = $("[name=value]").val();
			params[key]=value;
			refreshValue();
		}
		
		function remove_params(){
			var key = $("[name=key]").val();
			if(params.hasOwnProperty(key)){
				delete params[key];
				refreshValue();
			} else{
				alert("删除错误,不存在Params属性!");
				refreshValue();
			}
		}
		
		function visit_url(id){
			var link = $("#"+id).val();
			link="http://"+link;
			window.open(link);
		}
		
		function refreshValue(){
			$("[name=key]").val("");
			$("[name=value]").val("");
			$("[name=params]").val(JSON.stringify(params));
		}
		
		/*格式化为JSON*/
		$.fn.serializeObject = function(){    
			var o = {};    
		   var a = this.serializeArray();    
		   $.each(a, function() {    
			   if (o[this.name]) {    
		    	   if (!o[this.name].push) {    
		        	   o[this.name] = [o[this.name]];    
		           }    
		           o[this.name].push(this.value || '');    
		       } else {    
		    	   o[this.name] = this.value || '';    
		       }    
		   });    
		   return o;
		};
	</script>
</body>
</html>