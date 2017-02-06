 		var gridObj;
 		var citynodes = [];  
 		var zTreeDemo;
 		
        $(function () {
        	gridObj = $.fn.bsgrid.init('searchTable', {
                url: 'SysPermissionController/datagrid.do',
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
                        }
                    }
                }
            });
           
        });
      
		/**数据表格**/
		function fieldoperate(record, rowIndex, colIndex, options) {
        	switch (colIndex) {
        	case 3:
        		if(record.type=='menu')return '菜单';
        		else if(record.type=='permission')return '操作权限';
        	case 9:
				return record.available>0?'可用':'禁用';
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
			var systemcode=$('[name=systemcode]').val();
			var typecode=$('[name=typecode]').val();
			var keyword=$("[name=keyword]").val();
	        if(!serachfiled||serachfiled==""){
	        	parent.layer.alert('请正确选择查询项');
	        	return false;
	        }
	        var param=serachfiled+"="+keyword+(systemcode.length>0?'&systemid='+systemcode:'')+(typecode.length>0?'&type='+typecode:'');
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
		