var gridObj;

$(function () {
	var template_id = $('#template_id').val();
	var template_version = $('#template_version').val();
	gridObj = $.fn.bsgrid.init('versionTable', {
        url: 'params/getDataByTemplateId.do?TemplateId='+template_id+"&TemplateVersion="+template_version,
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
	switch (colIndex) {
		case 5:
			return record.type==0?'Params':'Field';
		case 6:
			return record.status==0?'可用':'禁用';
	}
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