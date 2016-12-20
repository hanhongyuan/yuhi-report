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

/*
 * 判断传入Object与表单是否发生改变
 * return 0:没有变化/1发生改变
 */
function isChange(Templet){
	var all_input = document.getElementsByTagName('input');
	for(var i=0;i<all_input.length;i++){
		var name = all_input[i].name;
		var value = all_input[i].value;
		if(name!=''&&Templet.hasOwnProperty(name)){
			if(Templet[name]!=value){
				return 1;
			}
		}
	}
	return 0;
}