(function (root, factory) {

    "use strict";

    // CommonJS module is defined
    if (typeof module !== 'undefined' && module.exports) {
        var isNode = (typeof process === "object") && (typeof process.versions === "object");
        var isElectron = isNode && ('electron' in process.versions);
        if (isElectron) {
            root.BDialog = factory(root.jQuery);
        } else {
            module.exports = factory(require('jquery'), require('bootstrap'), require('bootstrap-dialog'));
        }
    }
    // AMD module is defined
    else if (typeof define === "function" && define.amd) {
        define("bdialog", ["jquery", "bootstrap", "bootstrap-dialog"], function ($) {
            return factory($);
        });
    } else {
        // planted over the root!
        root.BDialog = factory(root.jQuery);
    }

}(this, function ($) {

	"use strict";
	
	var BDialog = {};
	
	BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_DEFAULT] = '提示信息';
	BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_INFO] = '提示信息';
	BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_PRIMARY] = '提示信息';
	BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_SUCCESS] = '成功提示';
	BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_WARNING] = '注意提示';
	BootstrapDialog.DEFAULT_TEXTS[BootstrapDialog.TYPE_DANGER] = '系统异常';
	BootstrapDialog.DEFAULT_TEXTS['OK'] = '确定';
	BootstrapDialog.DEFAULT_TEXTS['CANCEL'] = '取消';
	BootstrapDialog.DEFAULT_TEXTS['CONFIRM'] = '确认信息';
	
	BDialog.alert = function () {
        var alertOptions = {};

        if(arguments.length > 2){
			alertOptions = $.extend(true, alertOptions, {
				title: arguments[0],
                message: arguments[1],
                callback: typeof arguments[2] !== 'undefined' ? arguments[2] : null
            });
		} else {
			alertOptions = $.extend(true, alertOptions, {
                message: arguments[0],
                callback: typeof arguments[1] !== 'undefined' ? arguments[1] : null
            });
		}

        return BootstrapDialog.alert(alertOptions);
    };
	
	BDialog.confirm = function(){
		var alertOptions = {};
		
        if(arguments.length > 2){
			alertOptions = $.extend(true, alertOptions, {
				title: arguments[0],
                message: arguments[1],
                callback: typeof arguments[2] !== 'undefined' ? arguments[2] : null
            });
		} else {
			alertOptions = $.extend(true, alertOptions, {
                message: arguments[0],
                callback: typeof arguments[1] !== 'undefined' ? arguments[1] : null
            });
		}
		
		return BootstrapDialog.confirm(alertOptions);
	};
	
	BDialog.error = function(){
		var alertOptions = {
			type: BootstrapDialog.TYPE_DANGER
		};

        if(arguments.length > 2){
			alertOptions = $.extend(true, alertOptions, {
				title: arguments[0],
                message: arguments[1],
                callback: typeof arguments[2] !== 'undefined' ? arguments[2] : null
            });
		} else {
			alertOptions = $.extend(true, alertOptions, {
                message: arguments[0],
                callback: typeof arguments[1] !== 'undefined' ? arguments[1] : null
            });
		}
		
		return BootstrapDialog.alert(alertOptions);

	};
	return BDialog;
}))