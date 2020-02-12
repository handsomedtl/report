
(function($) {


    // window.AIPropertyToolbar = AIPropertyToolbar;
    var methods = {
        self: this,
        init: function(options) {
            this.config = $.extend({
                changes: {}
            }, options);
            var self = this;
            var data = {};
            var $this = $(this);
            $this.find(".tool-section").each(function(i, e) {
                if ($(e).attr("id")) {

                    var id = $(e).attr("id");

                    var $section = $this.find("#" + id);
                    function elementchange(){
                       var data = $section.data("data");
                       if (data) {
                           var d=$section.find("form").serializeJSON()
                           data = $.extend(data,d)
                       } else {
                           data = $section.find("form").serializeJSON();
                       }
                       if (typeof self.config.changes[$(e).attr("id")] == "function") {
                           var fun = self.config.changes[id];
                           fun(data)
                       }
                   }
                    $section.find("input").on("change", elementchange)

                    $section.find('.colorpicker-element').on('changeColor', elementchange);
                }

                //data[$(e).attr("id")] = {};
                //eval("data."+$(e).attr("id")+"={}");
            });


            $this.find(".tool-btn-dropdown").each(function(idx, e) {
                var btn = $(e);
                var inpt = btn.prev();
                $(e).next().find("a").each(function(i, ae) {
                    var self = $(ae);

                    $(ae).click(function(evt) {
                        btn.find("div.btn-text").html($(ae).html());
                        inpt.val($(ae).attr("value")).trigger('change');
                        var section = btn.parents(".tool-section");


                    })
                })
            })
            
            $this.find('[data-toggle="buttons"]').find("input").on("synstate",function(e){
                var $btn=$(this).closest('.btn');
               
                if($(this).prop("checked") && !$btn.hasClass("active")){
                	$btn.addClass("active")
                }else{
                	 $btn.removeClass("active")
                }
               // e.preventDefault();
            })
        },
        disable: function(id) {
            $(this).find("#" + id).find("*").prop("disabled", true);
            $(this).find("#" + id).addClass("tool-disabled");
            $(this).find("#" + id).data("data", {});
        },
        enable: function(id, data) {
            var $section =$(this).find("#" + id);
            $section.find("*").prop("disabled", false);
            $section.removeClass("tool-disabled");

            //clear data
            $section.data("data", {});
            clearSectionData($section);
           
            //set new data
            $section.data("data", data||{});
            //sleep(100);
            setPropertyData($(this).find("#" + id), data);


        },
        data: function(id) {
            return $(this).find("#" + id).find("form").serializeJSON();
        },
        update: function(content) {
            // !!!
        }
    };

    function clearSectionData($section){
    	 var old = getSerctionData($section);
         $section.find('[data-toggle="buttons"]')
            .find(":checked")
            .prop("checked",false)
            .trigger("synstate")
        for (var variable in old) {
            if (old.hasOwnProperty(variable)) {
                    old[variable]="";
            }
        }
       
        setPropertyData($section, old);
        
    }
    function getSerctionData($section){
        return $section.find("form").serializeJSON();
    }
    function setPropertyData($section, data) {

        var mydata = $.extend({}, data);
        for (var variable in mydata) {
            if (mydata.hasOwnProperty(variable)) {
                var elm = $section.find("input[name=\"" + variable + "\"]");
                if (elm.length == 0) {
                    elm = $section.find("input[name=\"" + variable + "[]\"]");
                }
                elm.each(
                    function(idx, e) {
                        var type = $(this).prop("type").toLowerCase();
                        if (type == "hidden") {
                            $(this).val(mydata[variable]);
                            if ($(this).hasClass("colorpicker")) { //
                                var v = mydata[variable];
                                if(!v) v="#ffffff";
                                $(this).val(v);
                                $(this).parent().colorpicker('setValue', v);
                            } else if ($(this).hasClass("tool-dropdown")) {
                                $(this).parent().find("a[value=\"" + mydata[variable] + "\"]").click();
                            }


                        } else if (type == "radio") {
                            if ($(this).val() == mydata[variable]) {
                                $(this).click();
                            }
                        } else if (type == "checkbox") {
                            var val = [];
                            if (typeof mydata[variable] == "string") {
                                val.push(mydata[variable]);
                            } else if (typeof mydata[variable] == "object") {
                                val = mydata[variable];
                            }
                            if ($.inArray($(this).val(), val) != -1) {
                                $(this).prop("checked", true).click();
                            }
                        } else {
                            $(this).val(mydata[variable]).change();
                        }
                    }
                )
            }
        }

    }
    $.fn.AIPropertyToolbar = function(method) {

        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {

            return methods.init.apply(this, arguments);
        } else {
            $.error('Method' + method + 'does not exist on jQuery.tooltip');
        }
    }
})(jQuery)
