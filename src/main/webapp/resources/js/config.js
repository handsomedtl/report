/**
 * 判断系统，加载loadfinish
 */

(function(){
	if(window.systemType == "portal"){
		document.write("<script src='"+window.contaxtrot+"/resources/js/addtabs/loadfinish.js'><\/script>");
	}
}());