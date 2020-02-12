String.prototype.endWith = function(str) {
    if (str == null || str == "" || this.length == 0 || str.length > this.length)
        return false;
    if (this.substring(this.length - str.length) == str)
        return true;
    else
        return false;
    return true;
}
String.prototype.startWith = function(str) {
    if (str == null || str == "" || this.length == 0 || str.length > this.length)
        return false;
    if (this.substr(0, str.length) == str)
        return true;
    else
        return false;
    return true;
}

(function($) {

    FormulaEditor = function(e, options) {
        var defaults = {

        }
        this.init(e, options);

    }

    $.extend(FormulaEditor.prototype, {
        config: {},
        element: "",
        editor: "",
        input: "",
        self: this,
        editing: false,
        disabled:false,
        showevt:function(e){},
        //初始化
        init: function(e, options) {
            this.config = $.extend({}, $.fn.formulaEditor.defaults, options);

            var self = this;
            this.element = $(e);
            var showfun = function(e) {
                self.show()
            }
            this.element.on("dblclick",showfun );
            this.showevt = showfun;
            //this.element.on("blur",this._hide);

        },

        //显示编辑器
        show: function() {
        	if(this.disabled){
        		return;
        	}
            if (this.editing)
                return;

            if(typeof this.config.onBeforEdit=="function" && !this.config.onBeforEdit(this.element)){
            	return;
            }
            this.editing = true;
            //构建编辑器
            this.editor = $(this.config.template);
            this.input = $(this.editor.find("input").eq(0));
            //this.input.on('blur',this._hide);
            this.input.get(0).editor = this;
            this.input.autocomplete({
                source: this.config.autoComplateTags
            });

            //设置原来的数据
            this.input.val(this.element.text().trim());
            this.element.html(this.editor);
            this.input.focus();
            var self = this;

            //事件绑定
            this.input.on("blur", function(e) {
                self.hide();
            })

            this.input.on("keyup", function(e) {
                self.showTip($(this).val());
            })
            this.input.on("change", function(e) {
                self.showTip($(this).val());
            })

            // 回车结束编辑
            this.input.on("keypress",function(e){
                if(e.keyCode==13){
                    $(this).trigger("blur");

                }
            })

            this.input.tooltipster({
                animation: 'fade',
                theme: 'tooltipster-light',
                contentAsHTML:true,
                updateAnimation:"scale"
            });

            //设置提示
            this.showTip(this.input.val());

        },
        //隐藏
        hide: function(e) {
            this.editing = false;
            this.element.text(this.input.val());
            if(this.config.onEditEnd && typeof this.config.onEditEnd =="function"){
                this.config.onEditEnd(this.input.val(),e);
            }
            // var editor = this.editor;
            // editor.element.text( editor.input.val());
            // editor.element.get(0).editing=false;
        },
        showTip: function(val) {
            var keys = this.config.showTipPrefix||[];

            for (i = 0; i < keys.length; i++) {
                if (startWith(val.trim().toUpperCase(), keys[i].toUpperCase())) {
                    if(this.tipkey == keys[i]){
                        var state = this.input.tooltipster('status');
                        if(!state.open){
                            this.input.tooltipster('open');
                        }
                        return;
                    }
                    this.tipkey = keys[i];
                    var tip = "";

                    if (this.config.tips && this.config.tips[keys[i]]) {
                        tip = this.config.tips[keys[i]];
                    } else if (this.config.tips && this.config.tips.default) {
                        tip = this.config.tips.default;
                    }


                    // at some point you may decide to update its content:
                    this.input.tooltipster('content', tip);
                    // ...and open it:
                    this.input.tooltipster('open');
                    return;
                }
            }

//            if(this.tipkey==""){
//                
//                return;
//            }
            var state = this.input.tooltipster('status');
            if(!state.open){
               // this.input.tooltipster('open');
            
            this.input.tooltipster('content', this.config.editTip||"");

            this.input.tooltipster('open');
            
            }
            this.tipkey="";

        },

        disable:function(){
        	this.disabled=true;
        },
        enable:function(){
        	this.disabled=false;
        }


    })

    function startWith(org, str) {
        if (str == null || str == "" || org.length == 0 || str.length > org.length)
            return false;
        if (org.substr(0, str.length) == str)
            return true;
        else
            return false;
        return true;
    }
    $.fn.formulaEditor = function(options) {
        var defaults
        if(typeof options =="string"){
        	this.each(function() {
        		if(this.editor && typeof this.editor[options]=="function")
        			return this.editor[options]( Array.prototype.slice.call(arguments, 1));
            })

        }

        this.each(function() {
            if(!this.editor)
                this.editor = new FormulaEditor(this, options);
        })
    }
    $.fn.formulaEditor.defaults = {
        template: "<div> <input value='' > </div>",
        autoComplateTags: ["=SUM()", "=AVG()", "=COUNT()", "=MAX()", "=MIN()"],
        showTipPrefix: ["=SUM(", "=AVG(", "=COUNT(", "=MAX(", "=MIN("],
        editTip:"直接输入常量，以 ＝ 开头输入公式",
        tips: {
            "default": "请在括号里面填写表格列索引如A，可以写+-*%表达式 如 A+B-C*D",
            "=SUM(":"SUM(A) 合计 A 列数据 <br> SUM(A+B) A,B 列加起来计算合计 <br> SUM(A+B-C)"

        },

        //编辑结束 val:编辑的内容 e:编辑器容器
        onEditEnd: function(val,e) {},
        onBeginEdit: function(e) {},

        onBeforEdit:function(e){
        	//如果返回false 不初始化编辑器
        	return true;
        }
    }

}(jQuery))