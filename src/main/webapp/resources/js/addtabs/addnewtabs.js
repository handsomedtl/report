/**
 * add tabs by iframe
 */
$(document).ready(
		function() {
			$('[data-addtab]').click(
					function() {
						if(window.systemType == 'workflow'){
							parent.Addtabs.add({
								id : $(this).attr('data-addtab'),
								title : $(this).attr('title') ? $(this).attr(
										'title') : $(this).html(),
								url : $(this).attr('url'),
								ajax : $(this).attr('ajax') ? true : false,
								callback : function() {

								}
							});
						}
						else if(window.systemType == 'portal'){
							var title = $(this).attr('title') ? $(this).attr('title') : $(this).html();
				    		var url = $(this).attr('url');
				    		parent.parent.addNavFrame('contentframe',url,title);
						}
					});
		});
function addtab(obj) {
	if(window.systemType == 'workflow'){
		parent.Addtabs.add({
			id : $(obj).attr('data-addtab'),
			title : $(obj).attr('title') ? $(obj).attr('title') : $(obj).html(),
			url : $(obj).attr('url'),
			ajax : $(obj).attr('ajax') ? true : false,
			callback : function() {
			}
		});
	}
	else if(window.systemType == 'portal'){
		var title = $(obj).attr('title') ? $(obj).attr('title') : $(obj).html();
		var url = $(obj).attr('url');
		parent.parent.addNavFrame('contentframe',url,title);
	}
	
}

function addTabByGeneLink(id, title, url, refreshtable){
	if(window.systemType == 'workflow'){
		parent.Addtabs.add({
			id : id,
			title : title,
			url : url,
			ajax : false,
			callback : function() {
				$("#"+refreshtable).DataTable().refreshtable();
			}
		});
	}
	else if(window.systemType == 'portal'){
		parent.parent.addNavFrame('contentframe',url,title);
	}
}

function closeTab(id){
	if(window.systemType == 'workflow'){
		parent.Addtabs.close(id);
	}
	else if(window.systemType == 'portal'){
		var frameid = window.frameElement.id;
		frameid = frameid.substring(9);
		var menuid = "navmenu_" + frameid;
		parent.parent.closeNavFrame(parent, menuid, "closeOnEsc");
	}
}