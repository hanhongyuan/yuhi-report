//数据序列化
function pagerFilter(data){
	var d={};
	d.total=data.count;
	d.rows=data.obj;
	return d;
}
//日期格式化
Date.prototype.Format = function(fmt){  
	var o = {   
		"M+" : this.getMonth()+1,                 //月份   
	    "d+" : this.getDate(),                    //日   
	    "h+" : this.getHours(),                   //小时   
	    "m+" : this.getMinutes(),                 //分   
	    "s+" : this.getSeconds(),                 //秒   
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
	    "S"  : this.getMilliseconds()             //毫秒   
  	};   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
};
//下拉列表序列化
function comboboxFilter(data){
	var d={};
	d=data.obj;
	return d;
}
//格式化json
function parseResults(data){
//	    var reg = /<pre.+?>(.+)<\/pre>/g;
//	    reg = /<audio.+?>(.+)<\/audio>/g;
//	    var result = data.match(reg);
//	    data = RegExp.$1;
//	    return data;
	return data.substring(0,data.indexOf("}")+1);
}
//异步上传文件
function uploadFile(id,callback,url,ElementId,parse) {
	 $("#"+id).click(function(){
		var _file=$("#upfile");
		if(callback===""||callback==null||!typeof callback === "function") {
			console.log("callback is function and Cannot be empty");
			return;
		}
		$("#upfile").click();
		_file.change(function(){
			var _value=_file.val();
			if(_value){
				if(parse instanceof Array&&parse.length>0){
					var _suffix=_value.substring(_value.lastIndexOf(".")+1); //文件后缀名;
					if(parse.indexOf(_suffix)<0){
						alert("请上传后缀为"+parse+"的文件");
						return;
					}
				}
			 	$.ajaxFileUpload({  
                   url:url==null?"http://localhost:8080/yuhiwy/uploadController/upload.do":url,  
                   secureuri:false,  
                   fileElementId:ElementId==null?"upfile":ElementId,  
                   dataType: 'json',  
                   success:callback,  
                   error: function (s, xml, status, e){  
                       console.info('上传失败:未知异常!');  
                   }  
               }); 
	 	}; 
	});
});
}
function uploadimg(id,callback,url,ElementId){
	var parse=["jpg","jpeg","png"];
	uploadFile(id,callback,url,ElementId,parse);
}

//是否选中行
function IsSelectRow(grid){
	var row=grid.datagrid('getSelected');   
	 if(!row){
		 msg('操作不正确,请选择一行数据');   
	     return null; 
	 }
	 return row;
}
//打开窗口只能一次
function OpenWindow(box,title,url){
			box.window({    
				href:url,
				title:title,
			    width:600,    
			    height:400,    
			    modal:true   
				}).window('open');
		
}
//数据表
function dataGrid(url,toolbarid,columns,grid){
	grid.datagrid({ 
		loadFilter:pagerFilter,
		iconCls: 'icon-edit',
		singleSelect: true,
		selectOnCheck:true,
		nowrap: false,
		striped: true,
		url: url,
		method: 'get',
		pageList:[5,10,15],
		pageSize:15,
		rownumbers:true,
		singleSelect:true,
		pagination:true,   
	    toolbar: toolbarid,
	    columns:columns    
});
	
}
//打开对话框
function OpenDialog(box,title,url,successCall,canelCall,width,height,ibuttons){
	var buttons=[];
	if(!ibuttons){
		buttons=[{
			text:'保存',
			handler:function(){
				successCall();
			}
		},{
			text:'关闭',
			handler:function(){
				if(canelCall)canelCall();
				box.dialog("close");
			}
		}];
	}
	box.dialog({  
		closable: false,
	    title: title,    
	    width: width==null?400:width,    
	    height:  height==null?400:height,    
	    closed: false,    
	    cache: false,    
	    modal:true,
	    href: url,    
	    modal: true,
	    resizable:true,
	    onOpen:function(){
	    	$(".panel").css("z-index","999");
	    	$(".window-shadow").css("z-index","997");
	    	$(".window-mask").css("z-index","998");
	    },
	    onMove:function(){
//	    	$(".panel").css("z-index","999");
//	    	$(".window-shadow").css("z-index","998");
//	    	$(".window-mask").css("z-index","997");
	    },
	    buttons:buttons
	});  
}
function loading(loadtext){
	$.messager.progress({
        text : loadtext==null||loadtext==""?'页面加载中....':loadtext,
        interval : 200
    });
	
}
function closeLoading(){
	 $.messager.progress('close');
}
//刷新表格
function reloadgrid(grid){
	grid.datagrid("reload");
}
//关闭对话框
function closeDialog(box){
	box.dialog("close");
}
//消息框
function msg(msgs){
//	$.messager.alert('警告',msg);  
	$.messager.show({ msg:msgs, position: "bottomRight" });
}
//日志
function log(msg){console.log(msg);}
//异步json
function remotejson(grid,url,callback){
	if(grid)grid.datagrid("loading");
    $.getJSON(url,function(result){
	 		if(result.success){
	 			if(!callback){
	 				msg('操作成功');
	 		 		grid.datagrid("reload");
	 			}else{
	 				callback(result);
	 			}
	 		}
	 	});
}
//全选反选  
function treeChecked(selected, treeMenu) {  
  var roots = $('#' + treeMenu).tree('getRoots');//返回tree的所有根节点数组  
  if (selected) {  
      for ( var i = 0; i < roots.length; i++) {  
          var node = $('#' + treeMenu).tree('find', roots[i].id);//查找节点  
          $('#' + treeMenu).tree('check', node.target);//将得到的节点选中  
      }  
  } else {  
      for ( var i = 0; i < roots.length; i++) {  
          var node = $('#' + treeMenu).tree('find', roots[i].id);  
          $('#' + treeMenu).tree('uncheck', node.target);  
      }  
  }  
} 
/********************ui*****************************/

function switchStylestyle(styleName)
{
	$('link[rel=stylesheet][title]').each(function(i) 
	{
		this.disabled = true;
		if (this.getAttribute('title') == styleName) this.disabled = false;
	});
	
	$("iframe").contents().find('link[rel=stylesheet][title]').each(function(i) 
	{
		this.disabled = true;
		if (this.getAttribute('title') == styleName) this.disabled = false;
	});
	
	createCookie('style', styleName, 365);
}
//cookie
function createCookie(name,value,days)
{
	if (days)
	{
		var date = new Date();
		date.setTime(date.getTime()+(days*24*60*60*1000));
		var expires = "; expires="+date.toGMTString();
	}
	else var expires = "";
	document.cookie = name+"="+value+expires+"; path=/";
}
function readCookie(name)
{
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for(var i=0;i < ca.length;i++)
	{
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1,c.length);
		if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
	}
	return null;
}
function eraseCookie(name)
{
	createCookie(name,"",-1);
}

function Clock() {
	var date = new Date();
	this.year = date.getFullYear();
	this.month = date.getMonth() + 1;
	this.date = date.getDate();
	this.day = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")[date.getDay()];
	this.hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
	this.minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
	this.second = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

	this.toString = function() {
		return "现在是:" + this.year + "年" + this.month + "月" + this.date + "日 " + this.hour + ":" + this.minute + ":" + this.second + " " + this.day; 
	};
	
	this.toSimpleDate = function() {
		return this.year + "-" + this.month + "-" + this.date;
	};
	
	this.toDetailDate = function() {
		return this.year + "-" + this.month + "-" + this.date + " " + this.hour + ":" + this.minute + ":" + this.second;
	};
	
	this.display = function(ele) {
		var clock = new Clock();
		ele.innerHTML = clock.toString();
		window.setTimeout(function() {clock.display(ele);}, 1000);
	};
}
/*************************layout***********************************/
function tabClose()
{
				    $(".tabs-inner").bind('contextmenu',function(e){
				         $('#mainmm').menu('show', {
				             left: e.pageX,
				             top: e.pageY,
				         });
				         var subtitle =$(this).children("span").text();
				         $('#mainmm').data("currtab",subtitle);
				         return false;
				     });
}

 function tabCloseEven()
 {
     //关闭当前
     $('#mm-tabclose').click(function(){
         var currtab_title = $('#mainmm').data("currtab");
         if(currtab_title=='主页')return;
         $('#tabs').tabs('close',currtab_title);
     })
     //全部关闭
     $('#mm-tabcloseall').click(function(){
         $('.tabs-inner span').each(function(i,n){
             var t = $(n).text();
             if(t!='主页')
             $('#tabs').tabs('close',t);
         });    
     });
     //关闭除当前之外的TAB
     $('#mm-tabcloseother').click(function(){
         var currtab_title = $('#mainmm').data("currtab");
         $('.tabs-inner span').each(function(i,n){
             var t = $(n).text();
             if(t!=currtab_title&&t!='主页')
                 $('#tabs').tabs('close',t);
         });    
     });

     //关闭当前右侧的TAB
     $('#mm-tabcloseright').click(function(){
         var nextall = $('.tabs-selected').nextAll();
         if(nextall.length==0){
             //msgShow('系统提示','后边没有啦~~','error');
             return false;
         }
         nextall.each(function(i,n){
             var t=$('a:eq(0) span',$(n)).text();
             $('#tabs').tabs('close',t);
         });
         return false;
     });
   
}
//添加Tabs
	function openNewTab(id,tabname,action){
		loading(null);
		if(!$("#tabs").tabs('exists', tabname)){
				$('#tabs').tabs('add',{    
				  	id:id,
				    title:tabname,    
				    href:action,
				    fit:true,
				    closable:true, 
				    onLoad:function(){
				    	var form=$("#loginform").html();
				    	if(form)location.reload();
				    }
					/*,tools:[{    
				        iconCls:'icon-mini-refresh',    
				        handler:function(){    
				        	refreshTab();  
				        }    
				    }]    */
				});
				tabClose();
	     		tabCloseEven();
		}else $('#tabs').tabs('select',tabname);
		 closeLoading();
	} 
	
	
/**************************选项卡iframe添加****************************************/	
	// 添加选项卡
	function addTab(iframeId, subtitle, url, closable) {
		loading(null);
	    // 是否关闭标志
		debugger
	    var closeFlg = true;
	    if (closable != null) {
	        closeFlg = closable;
	    }
	    if (!$('#tabs').tabs('exists', subtitle)) {
	        // iframe方式
	        var content = '<iframe id="' + iframeId + '" src="' +  url + '" frameborder="0" style="border:0;width:100%;height:99.4%;overflow: hidden;"></iframe>';
	        $('#tabs').tabs('add', {
	            title : subtitle,
	            content : content,
	            closable : closeFlg
	           /* tools : [ {
	                iconCls : 'icon-mini-refresh',
	                handler : function() {
	                    refreshTab();
	                }
	            } ]*/
	        });

	        // 自带href的方式
	        // $('#maintabs').tabs('add', {
	        // title : subtitle,
	        // href : url,
	        // closable : true
	        // });
	    } else {
	        $('#tabs').tabs('select', subtitle);
	    }
	    closeLoading();
	}

	function refreshTab() {
	    var currTab = $('#maintabs').tabs('getSelected');
	    var iframe = $(currTab.panel('options').content);
	    var id = iframe.attr('id');
	    var src = iframe.attr('src');
	    document.getElementById(id).src = src;
	}
/**************************选项卡iframe添加****************************************/	
/**刷新组件
 * 
<script type="text/javascript">
    var path = '${pageContext.request.contextPath}';//上下文路径
    var basePath = '${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/';//全路径
    function closeGlobalLoading(){
        $("#GlobalLoading").fadeOut("normal",function(){
            $(this).remove();
        });
    }
    var globalLoading;
    $.parser.onComplete = function(){
        closeGlobalLoading();
    }
</script>
<div id='GlobalLoading' style="position:absolute;z-index:9999;top:0px;left:0px;width:100%;height:100%;background:#FFFFFF;text-align:center;padding-top: 20%;">
 * 
 */	
	