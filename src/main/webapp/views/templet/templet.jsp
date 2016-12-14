<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
session.setAttribute("basePath", basePath);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/easyui/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/easyui/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/easyui/demo.css">
  </head>
  <body>
	<div class="easyui-panel" style="padding:5px;width: 1320px">
		<a href="<%=basePath%>templet/goTemplet.do" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-ok'">Template-Management</a>
		<a href="<%=basePath%>datasource/goDataSource.do" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-ok'">DataSource-Management</a>
	</div>
  	<div class="easyui-layout" style="width:1320px;height:580px;">
		<div data-options="region:'center',title:'Templet-Management',iconCls:'icon-ok'">
			<div id="templet_tb">
				<a class="easyui-linkbutton" iconCls="icon-add" onclick="templet_save()">添加</a>
				<a class="easyui-linkbutton" iconCls="icon-edit" onclick="templet_edit()">編輯</a>
				<a class="easyui-linkbutton" onclick="templet_test()">测试调用</a>
			</div>
		    <table id="templet_table"></table>
		    <div id="templet_edit"></div>
		</div>
	</div>
    <script type="text/javascript" src="<%=basePath%>js/curstomjs.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript">
    	$('#templet_table').datagrid({    
    		loadFilter:pagerFilter,
		    url:'<%=basePath%>templet/getData.do',
		    columns:[[
		    	{field:'name',title:'报表名称',width:200},
		    	{field:'mode',title:'报表模式',width:200,
		   			formatter: function(value){
		        		if(value=='0'){
		        			return "纯外部参数";
		        		}else if(value=='1'){
		        			return "纯数据源参数";
		        		}else if(value=='2'){
		        			return "外部参数混合数据源参数";
		        		} else{
		        			return value;
		        		}
		        }},
		   		{field:'sql_sentence',title:'SQL语句',width:500},
		   		{field:'params',title:'SQL参数',width:290},
		   		{field:'version',title:'版本号',width:35},
				{field:'status',title:'状态',width:35,
		   			formatter: function(value){
		        		if(value=='1'){
		        			return "正常";
		        		}else{
		        			return "禁用";
		        		}
		        }},
		    ]],
		    singleSelect: true,
		    method: 'get',
		    pageSize:10,
		    /* pagination:true, *//* 暂时不实现分页功能 */
		    toolbar: '#templet_tb'
		}); 
		
		function templet_save(){
			OpenDialog($('#templet_edit'),'新增','<%=basePath%>templet/goEditTemplet.do',
						function(){
							sendform();
						},function(){
							cancelform();
						},800,450);
		}
		
		function templet_edit(){
			var row=IsSelectRow($('#templet_table'));
			if(row){
				OpenDialog($('#templet_edit'),'编辑','<%=basePath%>templet/goEditTemplet.do?id='+row.id,
					function(){
						sendform();
					},
					function(){
						cancelform();
					},800,450);
			}
		}
		
		function templet_test(){
			OpenDialog($('#templet_edit'),'测试','<%=basePath%>templet/goTestTemplet.do',
					null,null,800,450);
		}
	</script>
  </body>
</html>
