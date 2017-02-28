$(function () {
	var template = $('#template').val();
	$.ajax({
		type:"post",
		url:"params/getDataByTemplateId.do",
		data:{
			TemplateId:template,
			TemplateVersion:'1'
		},
		success:function(data){
			var tab2 = '';
			var tab3 = '';
			for (var int = 0; int < data.data.length; int++) {
				if(data.data[int].type==0){
					tab2+='<div class="layui-form-item"><label class="layui-form-label">';
					tab2+=data.data[int].name;
					tab2+='</label><div class="layui-input-block"><input type="text" name="';
					tab2+=data.data[int].name;
					tab2+='" autocomplete="off" class="layui-input"/></div></div>';
				}else{
					tab3+='<div class="layui-form-item"><label class="layui-form-label">';
					tab3+=data.data[int].name;
					tab3+='</label><div class="layui-input-block"><input type="text" name="';
					tab3+=data.data[int].name;
					tab3+='" autocomplete="off" class="layui-input"/></div></div>';
				}
			}
			document.getElementById("tab2").innerHTML=tab2;
			document.getElementById("tab3").innerHTML=tab3;
		}
	})
}); 

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