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
  	<div class="easyui-layout" style="width:1320px;height:620px;">
		<div data-options="region:'center',title:'DataSource-Management',iconCls:'icon-ok'">
			<div id="datasource_tb">
				<a class="easyui-linkbutton" iconCls="icon-add" onclick="datasource_save()">添加</a>
				<a class="easyui-linkbutton" iconCls="icon-edit" onclick="datasource_edit()">編輯</a>
			</div>
		    <table id="datasource_table"></table>
		    <div id="datasource_edit"></div>
		</div>
	</div>
    <script type="text/javascript" src="<%=basePath%>js/curstomjs.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript">
    	$('#datasource_table').datagrid({    
    		loadFilter:pagerFilter,
		    url:'<%=basePath%>datasource/getData.do',
		    columns:[[
		    	{field:'name',title:'数据源名称',width:100},
		   		{field:'table_name',title:'数据库表',width:500},
		   		{field:'param',title:'参数',width:500},
		   		{field:'sort',title:'排序',width:100},
				{field:'status',title:'状态',width:50,
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
		    pagination:true,
		    toolbar: '#datasource_tb'
		}); 
		
		function datasource_save(){
			OpenDialog($('#datasource_edit'),'新增','<%=basePath%>datasource/goEditDataSource.do',
						function(){
							sendform();
						},800,450);
		}
		
		function datasource_edit(){
			var row=IsSelectRow($('#datasource_table'));
			if(row){
				OpenDialog($('#datasource_edit'),'编辑','<%=basePath%>datasource/goEditDataSource.do?id='+row.id,
					function(){
						sendform();
					},800,450);
			}
		}
	</script>
  </body>
</html>
