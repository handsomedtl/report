//////////////////////
//调用方法，返回json
function jsonForm(formId) {
	var str=$("#"+formId).serialize();
	
	var params = str.split('&');
	var param_arr = new Array();
	
	for(var i in params){
		var param = params[i].split('=');
		if(param[1] != null && param[1] != ''){
			param_arr.push('"'+param[0] + '":"' + decodeURIComponent(param[1],true)+'"');
		}
	}
	if(param_arr.length != 0){
		str = '({' + param_arr.join(',') + '})'; 
		var jsonObj = eval(str);
		return jsonObj;
	} else {
		return null;
	}
	
}