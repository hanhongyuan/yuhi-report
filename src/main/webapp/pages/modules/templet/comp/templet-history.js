var gridObj;

$(function () {
	var template_id = $('#template_id').val(); 
	gridObj = $.fn.bsgrid.init('versionTable', {
        url: 'version/getDataByTemplateId.do?TemplateId='+template_id,
        // autoLoad: false,
        ajaxType: 'GET',
        stripeRows: true,
        pageSizeSelect: true,
        displayBlankRows: true,
       	multiSort: true, // multi column sort support, default false
        pageSize: 10,
    });
}); 

function fieldoperate(record, rowIndex, colIndex, options) {
	console.log(record.jasper_url);
	var code = '<button class="layui-btn layui-btn-mini" onclick=\'download("'+rowIndex+
			'","jasper")\'>下载jasper文件</button><button class="layui-btn layui-btn-mini" onclick=\'download("'+rowIndex+
			'","jrxml")\'>下载jrxml文件</button>';
	switch (colIndex) {
	case 3:
		return record.status==0?'可用':'禁用';
	case 6:
		return code;
	}
}

function download(index,type){
	var obj = gridObj.getRecord(index);
	var url;
	if(type=='jasper'){
		if(obj.jasper_url!=''&&obj.jasper_url!=null){
			url=obj.jasper_url;	
		}else{
			layer.msg("下载失败,文件被损坏!", {icon: 6});
			return;
		}
	}else if(type=='jrxml'){
		if(obj.jrxml_url!=''&&obj.jrxml_url!=null){
			url=obj.jrxml_url;
		}else{
			layer.msg("下载失败,文件被损坏!", {icon: 6});
			return;
		}
	}else {
		layer.msg("下载失败,文件被损坏!", {icon: 6});
		return;
	}
	window.open("templet/downloadfile.do?url="+url, "下载页面");
}

layui.use(['form', 'layedit', 'laydate','element'], function() {
	var form = layui.form(),
		layer = layui.layer,
		layedit = layui.layedit,
		laydate = layui.laydate;
	var element = layui.element();
	
	element.on('tab(fromtab)', function(data){
		console.log(data);
	});
	//创建一个编辑器
	var editIndex = layedit.build('LAY_demo_editor');
});
function getfromdata(){
	return $('#systemres').serializeArray();
}