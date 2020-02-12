/**
 * 在portal中，页面加载完成后，调用该方法，去除加载状态
 */

window.onload = function() {
	if(window.systemType=='portal'){
		var frameid = window.frameElement.id;
		var id = frameid + "_loading";
		var span = parent.frames["menuframe"].document.getElementById(id);
		span.className = '';
	}
};
