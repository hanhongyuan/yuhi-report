 		var gridObj;
 		var citynodes = [];  
 		var zTreeDemo;
 		var rolebox;
 		var selectpostid;
 		
        $(function () {
        	rolebox=$("#roleTable");
            gridObj = $.fn.bsgrid.init('searchTable', {
                url: 'SysPostController/datagrid.do',
                // autoLoad: false,
                ajaxType: 'GET',
                stripeRows: true,
                pageSizeSelect: true,
                displayBlankRows: true,
                multiSort: true, // multi column sort support, default false
                pageSize: 10,
                event: {
                    customRowEvents: {
                        click: function (record, rowIndex, trObj, options) {
                        		trObj[0].children[0].children[0].checked=!trObj[0].children[0].children[0].checked;
//                        		角色联动
                        		selectpostid=record.id;
                        		getRoleData({"postid":selectpostid});
                        		$(".postname").html(record.name);
                        }
                    }
                },
                extend: {
                    settings: {
                        fixedGridHeader: true, // fixed grid header, auto height scroll, default false
                        fixedGridHeight: '200px' // fixed grid height, auto scroll
                    }
                }
            });
            zTreeDemo= $.fn.zTree.init($("#treeDemo"),setting, citynodes);
            getRoleData({});
           
        });
      
        
        /**组织树**/
        /**初始化树参数**/
        var setting = {  
        		 check: { /**复选框**/  
        		  enable: true,  
        		  chkboxType: {"Y":"", "N":""},
        		  nocheckInherit:true
        		 },  
        		 view: {                                    
        		  //dblClickExpand: false,  
        		  fontCss: function(treeId, treeNode) {
        				return treeNode.level == 0 ? {color:"#BD2222"} : {};
        		  },
        		  expandSpeed: 300 //设置树展开的动画速度，IE6下面没效果，  
        		 },                            
        		 data: {                                    
        		  simpleData: {   
        		   enable: true,  
        		   idKey: "id",  
        		   pIdKey: "pid",  
        		   rootPId: 0    
        		  }                            
        		 },async: { 
        			 enable: true, 
        			 url:"SysOrganizationController/treegrid.do", 
        			 autoParam:["id", "name"], 
        			 otherParam:{"otherParam":"zTreeAsyncTest"}, 
        			 // dataType: "text",//默认text 
        			 type:"get",//默认post 
        			 dataFilter: function(treeId, parentNode, data){
        				 for (var int = 0; int < data.length; int++) {
							if(data[int].pid==1){
								data[int].open=true;
								data[int].icon="resource/images/house.png";
							}
							data[int].name=data[int].name+'('+data[int].orgcode+')';
						}
        				 return data;
        			 } 
        		},
        		 callback: {     
        			 onClick:resetGrid
        		 }  
        		};  
        	
        /**根据项目变化数据（树连动）**/
        function resetGrid(event,treeId, treeNode) { 
    			var  searchParames = {};
    			switch (treeNode.level) {
				case 0:
					gridObj.search(searchParames);
					break;
				default:
//					数据表格联动
        			var  searchParames = {};
					searchParames.orgid=treeNode.id;
					gridObj.search(searchParames);
					break;
				}
        	//表单联动
        	$.ajax({
        		url:'SysOrganizationController/getObjByid.do',
        		data:{"id":treeNode.id},
        		success:function(data){
        			if(data){
        				$('[name=orgcode]').val(data.orgcode);
        				$('[name=name]').val(data.name);
        				$('[name=porgcode]').val(data.porgcode);
        				$('[name=id]').val(data.id);
        				$('[name=createtime]').val(getMyDate(data.createtime));
        				$('[name=switchs]').attr("checked",data.orgcode===data.porgcode);
        				$('[name=flag]').attr("checked",data.flag==1);
        				fromdisabled();
        			}
        		}
        	});
        }  
        /**提交组织树操作**/
        function saveOrg(){
        	fromdisabled(true);
        	var searchParames = $('#orgForm').serializeArray();
        	var flag=false; //编辑
        	var flagvalue=0;
        	for (var int = 0; int < searchParames.length; int++) {
        		if(searchParames[int].name=='id')flag=searchParames[int].value=='';
        		if(searchParames[int].name=='flag')searchParames[int].value='1';
			}
        	parent.layer.confirm('确定保存的组织信息吗？', {
      		  btn: ['确定','取消'] //按钮
      		}, function(){
        	$.ajax({
        		url:'SysOrganizationController/doUpdateOrg.do',
        		type:'POST',
        		data:searchParames,
        		success:function(data){
        			if(data.success){
        				parent.layer.msg('操作成功'); 
        				zTreeDemo.reAsyncChildNodes(null,'refresh','refresh');      				
        			}else 
        				parent.layer.msg('数据异常');
        		}
        	});
        	fromdisabled();
      		});
        }
        //添加节点
        function addOrg(){
        	$("#orgForm").find("input").each(function(i){
        		if($(this).attr('type')=='text'||$(this).attr('type')=='hidden')
        		$(this).val("")
        		else if($(this).attr('type')=='checkbox')
        			$(this).attr("checked",false);
        	});
        	fromdisabled(true);
        }
        //删除节点
        function delOrg(){
        	var nodes=zTreeDemo.getChangeCheckedNodes();
        	if(nodes.length<=0){
        		parent.layer.msg('请勾选节点进行删除！'); 
        		return false;
        	}
        	var ids='';
        	for (var int = 0; int < nodes.length; int++) {
        		if(nodes[int].isParent){
        			parent.layer.msg('勾选的节点还包含子节点！'); 
            		return false;
        		}
        		ids+=nodes[int].id+',';
			}
        	parent.layer.confirm('确定删除勾选的组织吗？', {
        		  btn: ['确定','取消'] //按钮
        		}, function(){
        			$.ajax({
                		url:'SysOrganizationController/dodel.do',
                		data:{"id":ids},
                		success:function(data){
                			if(data.success){
                				parent.layer.msg('操作成功'); 
                				zTreeDemo.reAsyncChildNodes(null,'refresh','refresh');      				
                			}else 
                				parent.layer.msg('数据异常');
                		}
                	});	
        	});
        }
        
        
        
		/**数据表格**/
		function fieldoperate(record, rowIndex, colIndex, options) {
        	console.log(colIndex);
        	switch (colIndex) {
        	case 2:
				return '<a href="javascript:void(0);" onclick="showObj(\''+record.id+'\')">'+record.name+'</a>';
			case 6:
				return record.state>0?'可用':'禁用';
			}
        }	
		//删除
		function deleteObj(){
			var index=gridObj.getCheckedRowsIndexs();
			console.log(index);
			if(index!=-1){
				parent.layer.alert(index+'');
			}else{
				parent.layer.alert("请选择一条数据！");
			}
			//checkbox
//			var indexary=gridObj.getCheckedRowsIndexs();
//			if(indexary&&indexary.length==1){
//				var objary=gridObj.getCheckedValues(0);
//				parent.layer.alert(objary[0].id);
//			}else{
//				parent.layer.alert("请选择一条数据！");
//			}
		}	
		//查看
		function showObj(id){
			parent.layer.alert(id+'');
		}
		//修改
		function updateobj() {
			var index=gridObj.getSelectedRowIndex();
			if(index!=-1){
				var obj= gridObj.getRecord(index);
				debugger
				parent.layer.open({
					title: '完善房间信息',
					maxmin: true,
					type: 2,
					content: 'wybudding.html?update&id='+obj.id,
					area: ['800px', '500px']
				});
			}else{
				parent.layer.alert("请选择一条数据！");
			}
		}
		//搜索
		function searchobj() {
			var serachfiled=$("[name=serachfiled]").val()+"";
			var keyword=$("[name=keyword]").val();
	        if(!serachfiled||serachfiled==""){
	        	parent.layer.alert('请正确选择查询项');
	        	return false;
	        }
//	        parent.layer.msg('加载中', {
//	        	icon: 16
//	        	,shade: 0.01
//	        });
	        var param=serachfiled+"="+keyword;
	        gridObj.search(param);
			
		}
		//添加
		function addobj() {
			parent.layer.open({
					title: '完善房间信息',
					maxmin: true,
					type: 2,
					content: 'wybudding.html?update',
					area: ['800px', '500px']
				});
		}
		var checkdRole='';
		var boxindex;
		//添加角色用户
		function addusertoRole(){
			if(!selectpostid){
				parent.layer.msg("选择一个岗位，用以添加角色");
				return false;
			}
			boxindex=parent.layer.open({
						btn: ['确定添加', '取消'],
						title: '请选择角色',
						maxmin: true,
						type: 2,
						content: 'SysPostController.do?addusertoRole&postid='+selectpostid,
						area: ['816px', '550px'],
						yes: function(index,layero){
							var iframeWin = parent.window[layero.find('iframe')[0]['name']];
							var ids=iframeWin.getcheckrole();
							/**添加岗位角色**/
								$.ajax({
					               url: 'Sysservice/addrole.do?roleid='+ids+"&postid="+selectpostid,
					               type:'get',
					               success:function(data){
					               	if(data.success){
					               		parent.layer.msg('操作成功'); 
					               		parent.layer.close(boxindex);
					               	}else parent.layer.msg('数据异常');
					               }
					           });
						}
					});
		}
		  /**岗位角色**/
        function getRoleData(datas){
        	 //角色树
            $.ajax({
                url: 'SysRoleController/datagrid.do',
                type:'get',
                data:datas,
                success:function(data){
                	if(data.success){
                		rolebox.html("");
                		for (var int = 0; int < data.data.length; int++) {
                			rolebox.html(rolebox.html()+'<blockquote class="layui-elem-quote">&nbsp;&nbsp;&nbsp;'+data.data[int].name+'&nbsp;&nbsp;&nbsp;<i class="layui-icon" onclick="deleteroleForpost(\''+data.data[int].id+'\')">&#x1006;</i></blockquote>');
						}
                	}else parent.layer.msg('数据异常');
//                	rolebox
//                	<blockquote class="layui-elem-quote"></blockquote>
                	//渲染角色盒子
                }
            });
        }
        //删除岗位包含的角色
        function deleteroleForpost(ids){
        	alert(ids);
        }
        
		/**其他**/
		//元素初始化
		layui.config({
			base: '/yuhi-admin-web/resource/plugins/layui/modules/'
		});
		layui.use(['laydate'], function() {
//			var form = layui.form(),
//				layer = layui.layer,
//				layedit = layui.layedit,
//				laydate = layui.laydate;
			//创建一个编辑器
		});
		
		//工具方法
		function fromdisabled(flag){
			if(flag){
				$('[name=orgcode]').removeAttr("disabled");
				$('[name=porgcode]').removeAttr("disabled");
				$('[name=createtime]').removeAttr("disabled");
			}else{
				$('[name=orgcode]').attr("disabled","disabled");
				$('[name=porgcode]').attr("disabled","disabled");
				$('[name=createtime]').attr("disabled","disabled");
			}
		}
		function getMyDate(str){  
            var oDate = new Date(str),  
            oYear = oDate.getFullYear(),  
            oMonth = oDate.getMonth()+1,  
            oDay = oDate.getDate(),  
            oHour = oDate.getHours(),  
            oMin = oDate.getMinutes(),  
            oSen = oDate.getSeconds(),  
            oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) +' '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间  
            return oTime;  
        };  
        //补0操作  
        function getzf(num){  
            if(parseInt(num) < 10){  
                num = '0'+num;  
            }  
            return num;  
        }  
		