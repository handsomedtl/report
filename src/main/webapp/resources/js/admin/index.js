if (!Array.prototype.map)
	Array.prototype.map = function(fn, scope) {
		var result = [], ri = 0;
		for (var i = 0, n = this.length; i < n; i++) {
			if (i in this) {
				result[ri++] = fn.call(scope, this[i], i, this);
			}
		}
		return result;
	};

var getWindowSize = function() {
	return [ "Height", "Width" ].map(function(name) {
		return window["inner" + name] || document.compatMode === "CSS1Compat"
				&& document.documentElement["client" + name]
				|| document.body["client" + name]
	});
}
window.onload = function() {
	if (!+"\v1" && !document.querySelector) { // for IE6 IE7
		document.body.onresize = resize;
	} else {
		window.onresize = resize;
	}
	function resize() {
		wSize();
		return false;
	}
}
function wSize() {
	// 这是一字符串
	var str = getWindowSize();
	var strs = new Array(); // 定义一数组
	strs = str.toString().split(","); // 字符分割
	var heights = strs[0] - 150, Body = $('body');
	$('#rightMain').height(heights);
	// iframe.height = strs[0]-46;
	if (strs[1] < 980) {
		$('.header').css('width', 980 + 'px');
		$('#content').css('width', 980 + 'px');
		Body.attr('scroll', '');
		Body.removeClass('objbody');
	} else {
		$('.header').css('width', 'auto');
		$('#content').css('width', 'auto');
		Body.attr('scroll', 'no');
		Body.addClass('objbody');
	}

	var openClose = $("#rightMain").height() + 39;
	$('#center_frame').height(openClose + 9);
	$("#openClose").height(openClose + 30);
	$("#Scroll").height(openClose - 20);
	windowW();
}
wSize();
function windowW() {
	if ($('#Scroll').height() < $("#leftMain").height()) {
		$(".scroll").show();
	} else {
		$(".scroll").hide();
	}
}
windowW();
// 站点下拉菜单
$(function() {
	var offset = $(".tab_web").offset();
	var tab_web_panel = $(".tab-web-panel");
	$(".tab_web").mouseover(function() {
		tab_web_panel.css({
			"left" : +$(this).offset().left + 4,
			"top" : +offset.top + $('.tab_web').height()
		});
		tab_web_panel.show();
		if (tab_web_panel.height() > 200) {
			tab_web_panel.children("ul").addClass("tab-scroll");
		}
	});
	$(".tab_web span").mouseout(function() {
		hidden_site_list_1()
	});
	$(".tab-web-panel").mouseover(function() {
		clearh();
		$('.tab_web a').addClass('on')
	}).mouseout(function() {
		hidden_site_list_1();
		$('.tab_web a').removeClass('on')
	});
	// 默认载入左侧菜单
	$("#leftMain").load("/admin/leftmenu.jhtml");

	// 面板切换
	$("#btnx").removeClass("btns2");
	$("#Site_model,#btnx h6").css("display", "none");
	$("#btnx").hover(function() {
		$("#Site_model,#btnx h6").css("display", "block");
		$(this).addClass("btns2");
		$(".bg_btn").hide();
	}, function() {
		$("#Site_model,#btnx h6").css("display", "none");
		$(this).removeClass("btns2");
		$(".bg_btn").show();
	});
	$("#Site_model li").hover(function() {
		$(this).toggleClass("hvs");
	}, function() {
		$(this).toggleClass("hvs");
	});
	$("#Site_model li").click(function() {
		$("#Site_model li").removeClass("ac");
		$(this).addClass("ac");
	});
})
// 站点选择
function site_select(id, name, domain, siteid) {
	$(".tab_web span").html(name);
	$.get("?m=admin&c=index&a=public_set_siteid&siteid=" + id, function(data) {
		if (data == 1) {
			window.top.right.location.reload();
			window.top.center_frame.location.reload();
			$.get("?m=admin&c=index&a=public_menu_left&menuid=0&parentid="
					+ $("#bigid").val(), function(data) {
				$('.top_menu').remove();
				$('#top_menu').prepend(data)
			});
		}
	});
	$('#site_homepage').attr('href', domain);
	$('#site_search').attr('href', 'index.php?m=search&siteid=' + siteid);
}
// 隐藏站点下拉。
var s = 0;
var h;
function hidden_site_list() {
	s++;
	if (s >= 3) {
		$('.tab-web-panel').hide();
		clearInterval(h);
		s = 0;
	}
}
function clearh() {
	if (h)
		clearInterval(h);
}
function hidden_site_list_1() {
	h = setInterval("hidden_site_list()", 1);
}

// 左侧开关
$("#openClose").click(function() {
	if ($(this).data('clicknum') == 1) {
		$("html").removeClass("on");
		$(".left_menu").removeClass("left_menu_on");
		$(this).removeClass("close");
		$(this).data('clicknum', 0);
		$(".scroll").show();
	} else {
		$(".left_menu").addClass("left_menu_on");
		$(this).addClass("close");
		$("html").addClass("on");
		$(this).data('clicknum', 1);
		$(".scroll").hide();
	}
	return false;
});

function _M(menuid, targetUrl) {
	$("#menuid").val(menuid);
	$("#bigid").val(menuid);
	$("#paneladd").html(
			'<a class="panel-add" href="javascript:add_panel();">添加</em></a>');
	if (menuid != 8) {
		$("#leftMain").load(
				"?m=admin&c=index&a=public_menu_left&menuid=" + menuid, {
					limit : 25
				}, function() {
					windowW();
				});
	} else {
		$("#leftMain").load(
				"?m=admin&c=phpsso&a=public_menu_left&menuid=" + menuid, {
					limit : 25
				}, function() {
					windowW();
				});
	}
	// $("#rightMain").attr('src', targetUrl);
	$('.top_menu').removeClass("on");
	$('#_M' + menuid).addClass("on");
	$.get("?m=admin&c=index&a=public_current_pos&menuid=" + menuid, function(
			data) {
		$("#current_pos").html(data);
	});
	// 当点击顶部菜单后，隐藏中间的框架
	$('#display_center_id').css('display', 'none');
	// 显示左侧菜单，当点击顶部时，展开左侧
	$(".left_menu").removeClass("left_menu_on");
	$("#openClose").removeClass("close");
	$("html").removeClass("on");
	$("#openClose").data('clicknum', 0);
	$("#current_pos").data('clicknum', 1);
}
function _MP(menuid, targetUrl) {
	$("#menuid").val(menuid);
	$("#paneladd")
			.html(
					'<a class="panel-add" href="javascript:add_panel();"><em>添加</em></a>');
	$("#rightMain").attr('src',
			targetUrl );
	$('.sub_menu').removeClass("on fb blue");
	$('#_MP' + menuid).addClass("on fb blue");
	/*
	$.get("?m=admin&c=index&a=public_current_pos&menuid=" + menuid, function(
			data) {
		$("#current_pos").html(data + '<span id="current_pos_attr"></span>');
	});
	$("#current_pos").data('clicknum', 1);*/
	// show_help(targetUrl);
}

function show_help(targetUrl) {
	$("#help").slideUp("slow");
	var str = '';
	$
			.getJSON(
					"http://v9.help.phpcms.cn/api.php?jsoncallback=?",
					{
						op : 'help',
						targetUrl : targetUrl
					},
					function(data) {
						if (data != null) {
							$("#help").slideDown("slow");
							$.each(data, function(i, item) {
								str += '<a href="' + item.url
										+ '" target="_blank">' + item.title
										+ '</a>';
							});

							str += '<a class="panel-delete" href="javascript:;" onclick="$(\'#help\').slideUp(\'slow\')"></a>';
							$('#help').html(str);
						}
					});
	$("#help").data('time', 1);
}
setInterval("hidden_help()", 30000);
function hidden_help() {
	var htime = $("#help").data('time') + 1;
	$("#help").data('time', htime);
	if (htime > 2)
		$("#help").slideUp("slow");
}
function add_panel() {
	var menuid = $("#menuid").val();
	$.ajax({
		type : "POST",
		url : "?m=admin&c=index&a=public_ajax_add_panel",
		data : "menuid=" + menuid,
		success : function(data) {
			if (data) {
				$("#panellist").html(data);
			}
		}
	});
}
function delete_panel(menuid, id) {
	$.ajax({
		type : "POST",
		url : "?m=admin&c=index&a=public_ajax_delete_panel",
		data : "menuid=" + menuid,
		success : function(data) {
			$("#panellist").html(data);
		}
	});
}

function paneladdclass(id) {
	$("#panellist span a[class='on']").removeClass();
	$(id).addClass('on')
}
setInterval("session_life()", 160000);
function session_life() {
	$.get("?m=admin&c=index&a=public_session_life");
}
function lock_screen() {
	$.get("?m=admin&c=index&a=public_lock_screen");
	$('#dvLockScreen').css('display', '');
}
function check_screenlock() {
	var lock_password = $('#lock_password').val();
	if (lock_password == '') {
		$('#lock_tips').html('<font color="red">密码不能空</font>');
		return false;
	}
	$.get("?m=admin&c=index&a=public_login_screenlock", {
		lock_password : lock_password
	}, function(data) {
		if (data == 1) {
			$('#dvLockScreen').css('display', 'none');
			$('#lock_password').val('');
			$('#lock_tips').html('锁屏');
		} else if (data == 3) {
			$('#lock_tips').html('<font color="red">锁屏提示 </font>');
		} else {
			strings = data.split('|');
			$('#lock_tips').html('<font color="red">密码不正确不能锁屏 </font>');
		}
	});
}
$(document).bind('keydown', 'return', function(evt) {
	check_screenlock();
	return false;
});

(function() {
	var addEvent = (function() {
		if (window.addEventListener) {
			return function(el, sType, fn, capture) {
				if (!el) {
					return;
				}
				el.addEventListener(sType, fn, (capture));
			};
		} else if (window.attachEvent) {
			return function(el, sType, fn, capture) {
				if (!el) {
					return;
				}
				el.attachEvent("on" + sType, fn);
			};
		} else {
			return function() {
			};
		}
	})(), Scroll = document.getElementById('Scroll');
	// IE6/IE7/IE8/IE10/IE11/Opera 10+/Safari5+
	addEvent(Scroll, 'mousewheel', function(event) {
		event = window.event || event;
		if (event.wheelDelta <= 0 || event.detail > 0) {
			Scroll.scrollTop = Scroll.scrollTop + 29;
		} else {
			Scroll.scrollTop = Scroll.scrollTop - 29;
		}
	}, false);

	// Firefox 3.5+
	addEvent(Scroll, 'DOMMouseScroll', function(event) {
		event = window.event || event;
		if (event.wheelDelta <= 0 || event.detail > 0) {
			Scroll.scrollTop = Scroll.scrollTop + 29;
		} else {
			Scroll.scrollTop = Scroll.scrollTop - 29;
		}
	}, false);

})();
function menuScroll(num) {
	var Scroll = document.getElementById('Scroll');
	if (num == 1) {
		Scroll.scrollTop = Scroll.scrollTop - 60;
	} else {
		Scroll.scrollTop = Scroll.scrollTop + 60;
	}
}
function _Site_M(project) {
	var id = '';
	$('#top_menu li').each(function() {
		var S_class = $(this).attr('class');
		if ($(this).attr('id')) {
			$(this).hide();
		}
		if (S_class == 'on top_menu' || S_class == 'top_menu on') {
			id = $(this).attr('id');
		}
	});
	$('#' + id).show();
	id = id.substring(2, id.length);
	if (!project) {
		project = 0;
	}
	$.ajaxSettings.async = false;
	$.getJSON('index.php', {
		m : 'admin',
		c : 'index',
		a : 'public_set_model',
		'site_model' : project,
		'time' : Math.random()
	}, function(data) {
		$.each(data, function(i, n) {
			$('#_M' + n).show();
		})
	})
	$("#leftMain").load(
			"?m=admin&c=index&a=public_menu_left&menuid=" + id + '&time='
					+ Math.random());
}