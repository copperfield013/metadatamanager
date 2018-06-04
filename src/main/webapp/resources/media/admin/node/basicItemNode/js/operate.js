
seajs.use(['dialog','utils', 'ajax', '$CPF'], function(Dialog, Utils, Ajax, $CPF){	
	
	/**
     * 获取实体信息方法 示例     
     */
	function getEntity() {
		$CPF.showLoading();  //必写
		 Ajax.ajax('admin/dictionary/dictParentItem/getDictPitem', '', function(data) {			 
			 $("#operate .entity-edit-wrap").addClass("active");
			//操作,渲染实体
			 
			 $CPF.closeLoading(); //必写
		 });
	}
	
	
    /**
     * 跟实体添加页面弹出方法
     * @param {当前点击元素,dom对象} el 
     */
    function pop(el) {
        var addTagLength = $(el).closest(".collapse-header")
            .next(".add-tag").length;
        var relativeLength = $(el).closest(".collapse-header")
            .siblings(".collapse-content")
            .children(".attr-relative").length;
        var html = "<ul class='card'>";
        if (addTagLength > 0) {
            html += "<li class='card-list add-attr'>" +
                "<i class='icon icon-card-attr'></i>" +
                "<span class='text'>添加属性</span>" +
                "</li>" +
                "<li class='card-list add-attr-group'>" +
                "<i class='icon icon-card-attr-group'></i>" +
                "<span class='text'>添加属性组</span>" +
                "</li>" +
                "<li class='card-list add-more-attr'>" +
                "<i class='icon icon-card-more-attr'></i>" +
                "<span class='text'>添加多值属性</span>" +
                "</li>"
        } else {
            html += "<li class='card-list add-tag'>" +
                "<i class='icon icon-card-tag'></i>" +
                "<span class='text'>添加标签</span>" +
                "</li>" +
                "<li class='card-list add-attr'>" +
                "<i class='icon icon-card-attr'></i>" +
                "<span class='text'>添加属性</span>" +
                "</li>" +
                "<li class='card-list add-attr-group'>" +
                "<i class='icon icon-card-attr-group'></i>" +
                "<span class='text'>添加属性组</span>" +
                "</li>" +
                "<li class='card-list add-more-attr'>" +
                "<i class='icon icon-card-more-attr'></i>" +
                "<span class='text'>添加多值属性</span>" +
                "</li>"
        };
        if (relativeLength > 0) {
            html += "</ul>";
        } else {
            html += "<li class='card-list add-relative'>" +
                "<i class='icon icon-card-relative'></i>" +
                "<span class='text'>添加关系</span>" +
                "</li>" +
                "</ul>";
        }

        var wrap = $("#operate");
        var offsetx = $(el).offset().left;
        var offsety = $(el).offset().top;
        var top = offsety + 10;
        var left = offsetx - 90;       
        var popHtml = $(html).appendTo(wrap);
        var wrapTop = wrap.offset().top;
        var wrapLeft = wrap.offset().left;
        popHtml.css({
            "top": top - wrapTop + 20,
            "left": left - wrapLeft
        });

    };

    /**
     * 属性组，多值属性添加 页面弹出方法
    */
    function popsm(el) {
        var addTagLength = $(el).closest(".collapse-header")
            .next(".add-tag").length;
        var html = "<ul class='card'>";
        if (addTagLength > 0) {
            html += "<li class='card-list add-attr'>" +
                "<i class='icon icon-card-attr'></i>" +
                "<span class='text'>添加属性</span>" +
                "</li>" +
                "</ul>";
        } else {
            html += "<li class='card-list add-tag'>" +
                "<i class='icon icon-card-tag'></i>" +
                "<span class='text'>添加标签</span>" +
                "</li>" +
                "<li class='card-list add-attr'>" +
                "<i class='icon icon-card-attr'></i>" +
                "<span class='text'>添加属性</span>" +
                "</li>" +
                "</ul>";
        }

        var wrap = $("#operate");
        var offsetx = $(el).offset().left;
        var offsety = $(el).offset().top;
        var top = offsety + 10;
        var left = offsetx - 90;
        var popHtml = $(html).appendTo(wrap);
        var wrapTop = wrap.offset().top;
        var wrapLeft = wrap.offset().left;
        popHtml.css({
            "top": top - wrapTop + 20,
            "left": left - wrapLeft
        });
    };

    /**
     * 添加标签页面弹出方法
    */
    function popTag(el) {
        var html = "<ul class='tag-card'>"+
            "<li class='tag-card-list'>" +
            "<span class='tag-checkbox'>" +
            "<input type='checkbox' class='tag-checkbox-input' value='1'>" +
            "<span class='tag-checkbox-inner'></span>" +
            "</span>" +
            "<span class='text'>标签一</span>" +
            "</li>" +
            "<li class='tag-card-list'>" +
            "<span class='tag-checkbox'>" +
            "<input type='checkbox' class='tag-checkbox-input' value='2'>" +
            "<span class='tag-checkbox-inner'></span>" +
            "</span>" +
            "<span class='text'>标签2</span>" +
            "</li>" +
            "<li class='tag-card-list'>" +
            "<span class='tag-checkbox'>" +
            "<input type='checkbox' class='tag-checkbox-input' value='3'>" +
            "<span class='tag-checkbox-inner'></span>" +
            "</span>" +
            "<span class='text'>标签3</span>" +
            "</li>" +
            "<li class='tag-card-list'>" +
            "<span class='tag-checkbox'>" +
            "<input type='checkbox' class='tag-checkbox-input' value='4'>" +
            "<span class='tag-checkbox-inner'></span>" +
            "</span>" +
            "<span class='text'>标签4</span>" +
            "</li>"+
            "</ul>"

        var wrap = $("#operate");
        var offsetx = $(el).offset().left;
        var offsety = $(el).offset().top;
        var top = offsety + 10;
        var left = offsetx - 90;
        var popHtml = $(html).appendTo(wrap);
        var wrapTop = wrap.offset().top;
        var wrapLeft = wrap.offset().left;
        popHtml.css({
            "top": top - wrapTop + 20,
            "left": left - wrapLeft
        });
    };

    /**
     * 删除属性标签页弹出方法
      */
    function popAttr(el) {
        var html = "<div class='delete-list'>"+
                        "<p>"+
                            "<i class='icon icon-mark'></i><span class='text'>确定要删除此条数据?</span>"+
                        "</p>"+
                        "<div class='delete-list-btn'>"+
                            "<span class='opera cancel'>取消</span>"+
                            "<span class='opera confirm'>确认</span>"+
                        "</div>"+
                    "</div>"

        var wrap = $("#operate");
        var offsetx = $(el).offset().left;
        var offsety = $(el).offset().top;
        var top = offsety - 144;
        var left = offsetx - 121;
        var popHtml = $(html).appendTo(wrap);
        var wrapTop = wrap.offset().top;
        var wrapLeft = wrap.offset().left;
        popHtml.css({
            "top": top - wrapTop + 20,
            "left": left - wrapLeft
        });
    }

    /**
     * 删除组别标签页弹出方法
      */
    function popGroupAttr(el) {
        var html = "<div class='delete-list-c'>"+
                        "<p>"+
                            "<i class='icon icon-mark'></i><span class='text'>确定同时删除组和组内内容?</span>"+                
                        "</p>"+
                        "<div class='delete-list-btn'>"+
                            "<span class='opera cancel'>取消</span>"+
                            "<span class='opera confirm'>确认</span>"+
                            "<span class='opera only-group'>仅删除组</span>"+
                        "</div>"+
                    "</div>"

        var wrap = $("#operate");
        var offsetx = $(el).offset().left;
        var offsety = $(el).offset().top;
        var top = offsety - 154;
        var left = offsetx - 121;
        var popHtml = $(html).appendTo(wrap);
        var wrapTop = wrap.offset().top;
        var wrapLeft = wrap.offset().left;
        popHtml.css({
            "top": top - wrapTop + 20,
            "left": left - wrapLeft
        });
    }


    /**
     * remove 添加页方法
      */
    function removePop() {
        $(".card").remove();
        $(".tag-card").remove();
        $(".delete-list").remove();
        $(".delete-list-c").remove();
        $(".icon-add").removeClass("active");
        $(".icon-add-tag").removeClass("active");
        $(".icon-trash").removeClass("active");
        $(".icon-trash-sm").removeClass("active");

    };


    /**
     * 添加标签方法
     * @param {当前点击元素对应的加号} el
      */
    function addTag(el) {        
        var $header = $(el).closest(".collapse-header");
        var tagHtml = "<div class='add-tag no-dragger clear-fix'>" +
            "<div class='icon-label tag'>" +
            "<i class='icon icon-tag'></i>" +
            "<span class='text'>标签</span>" +
            "</div>" +
            "<div class='label-bar tag'>" +
            "<input type='text' readonly class='edit-input' value='标签名称'>" +
            "<div class='btn-wrap'>" +
            "<i class='icon tag icon-add-tag'></i>" +
            "<i class='icon-simulate-trashsm'></i>" +
            "</div>" +
            "</div>" +
            "</div>"            
        $header.after(tagHtml);
    };

    /**
     * 添加属性方法
      */
    function addAttr(el) {
        var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        var attrHtml = "<li class='add-attr clear-fix'>" +
            "<div class='icon-label attr'>" +
            "<i class='icon icon-attr'></i>" +
            "<span class='text'>属性</span>" +
            "</div>" +
            "<div class='label-bar attr'>" +
            "<input type='text' class='edit-input' value='属性名'>" +
            "<select name='' id=''>" +
            "<option value='孩子5'>孩子5</option>" +
            "<option value='孩子4'>孩子4</option>" +
            "<option value='孩子3'>孩子3</option>" +
            "<option value='孩子2'>孩子2</option>" +
            "</select>" +
            "<select name='' id=''>" +
            "<option value='string'>string</option>" +
            "<option value='number'>number</option>" +
            "<option value='enum'>enum</option>" +
            "</select>" +
            "<select name='' id=''>" +
            "<option value='读'>读</option>" +
            "<option value='写'>写</option>" +
            "</select>" +
            "<div class='btn-wrap'>" +
            "<i class='icon icon-save'></i>" +
            "<i class='icon icon-trash-sm'></i>" +
            "<i class='icon-simulate-trashsm'></i>" +
            "</div>" +
            "</div>" +
            "</li>"
        $content.prepend(attrHtml);
    };

    /**
     * 添加属性组方法
      */
    function addGroup(el) {
        var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        var attrGroupHtml = "<li class='attr-group'>" +
            "<div class='attr-group-title collapse-header'>" +
            "<div class='icon-label attr-group'>" +
            "<i class='icon icon-attr-group'></i>" +
            "<span class='text'>属性组</span>" +
            "</div>" +
            "<div class='label-bar attr-group'>" +
            "<input type='text' class='edit-input' value='属性组名称'>" +
            "<div class='btn-wrap'>" +
            "<i class='icon icon-save'></i>" +
            "<i class='icon icon-add-sm'></i>" +
            "<i class='icon icon-trash-sm'></i>" +
            "<i class='icon icon-arrow-sm'></i>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<ul class='attr-group-drag-wrap collapse-content collapse-content-active'>" +
            "</ul>" +
            "</li>"
        $content.prepend(attrGroupHtml);
    };

    /**
     * 添加多值属性方法
      */
    function addMoreAttr(el) {
        var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        var moreAttrHtml = "<li class='more-attr'>" +
            "<div class='more-attr-title collapse-header'>" +
            "<div class='icon-label more-attr'>" +
            "<i class='icon icon-more-attr'></i>" +
            "<span class='text'>多值属性</span>" +
            "</div>" +
            "<div class='label-bar more-attr'>" +
            "<input type='text' class='edit-input' value='多值属性名称'>" +
            "<select name='' id=''>" +
            "<option value='孩子5'>孩子5</option>" +
            "<option value='孩子4'>孩子4</option>" +
            "<option value='孩子3'>孩子3</option>" +
            "<option value='孩子2'>孩子2</option>" +
            "</select>" +
            "<select name='' id=''>" +
            "<option value='string'>string</option>" +
            "<option value='number'>number</option>" +
            "<option value='enum'>enum</option>" +
            "</select>" +
            "<select name='' id=''>" +
            "<option value='读'>读</option>" +
            "<option value='写'>写</option>" +
            "</select>" +
            "<div class='btn-wrap'>" +
            "<i class='icon icon-save'></i>" +
            "<i class='icon icon-add-sm'></i>" +
            "<i class='icon icon-trash-sm'></i>" +
            "<i class='icon icon-arrow-sm'></i>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<ul class='more-attr-drag-wrap collapse-content collapse-content-active'>" +
            "</ul>" +
            "</li>"
        $content.prepend(moreAttrHtml);
    };

    /**
     * 添加关系方法
      */
    function addRelative(el) {
        var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        var relativeHtml = "<li class='attr-relative'>" +
            "<div class='attr-relative-title collapse-header'>" +
            "<div class='icon-label attr-relative'>" +
            "<i class='icon icon-attr-relative'></i><span class='text'>关系</span>" +
            "</div>" +
            "<div class='label-bar attr-relative'>" +
            "<input type='text' class='edit-input' value='关系名称'>" +
            "<select name='entityName' id=''>" +
            "<option value='人口1'>人口1</option>" +
            "<option value='人口2'>人口2</option>" +
            "<option value='人口3'>人口3</option>" +
            "<option value='人口4'>人口4</option>" +
            "</select>" +
            "<div class='btn-wrap'>" +
            "<i class='icon icon-save'></i>" +
            "<i class='icon icon-trash-sm'></i>" +
            "<i class='icon icon-arrow-sm'></i>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div class='add-tag no-dragger clear-fix'>" +
            "<div class='icon-label tag'>" +
            "<i class='icon icon-tag'></i>" +
            "<span class='text'>标签</span>" +
            "</div>" +
            "<div class='label-bar tag'>" +
            "<input type='text' readonly class='edit-input' value='标签名称'>" +
            "<div class='btn-wrap'>" +
            "<i class='icon tag icon-add-tag'></i>" +
            "<i class='icon-simulate-trashsm'></i>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<ul class='attr-relative-drag-wrap collapse-content collapse-content-active'>" +
            "<li class='entity-ch-wrap'>" +
            "<div class='attr-abc-title collapse-header'>" +
            "<div class='icon-label abc'>" +
            "<i class='icon icon-abc'></i><span class='text'>abc</span>" +
            "</div>" +
            "<div class='label-bar abc'>" +
            "<input type='text' class='edit-input' value='abc'>" +           
            "<div class='btn-wrap'>" +
            "<i class='icon icon-add-abc'></i>" +
            "<i class='icon icon-trash-sm'></i>" +
            "<i class='icon icon-arrow-sm'></i>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<ul class='drag-wrap-repeat collapse-content collapse-content-active'>" +
            "</ul>" +
            "</li>" +
            "</ul>" +
            "</li>";
        $content.prepend(relativeHtml);
    };

    //删除单条属性数据确认方法 
    function confirmDeleteSingle(el) {
        var $content = $(el).closest(".label-bar").parent("li");
        $content.remove();
        removePop();
    }  
    
    //删除整体数据确认方法
    function confirmDeleteAll(el) {
        var $content = $(el).closest(".collapse-header").parent("li");
        $content.remove();
        removePop();
    }

    //仅删除组确认方法
    function confirmDeleteOnly(el) {        
        var html = $(el).closest(".collapse-header").siblings(".collapse-content").html();
        var $group = $(el).closest(".collapse-header").parent("li");
        $group.after(html);
        $group.remove();
        removePop();

    }

    $(document).on("click", function () {
        removePop();                
    });

    //input事件绑定
    $(".edit-input").on("blur", function () {
        //传输给后台                  
    });
    
    //实体选择点击事件绑定
    $("#operate").on("click", ".entity_attr", function() {
    	$(this).addClass("active")
    		.siblings().removeClass("active");
    	getEntity();
    })

    //收缩事件绑定
    $("#operate").on("click", ".icon-arrow, .icon-arrow-sm, .icon-label", function () {
        var $content = $(this).closest(".collapse-header")
            .siblings(".collapse-content");
        $(this).toggleClass("active");
        if ($content.hasClass("collapse-content-active")) {
            $content
                .removeClass("collapse-content-active")
                .addClass("collapse-content-inactive");
        } else {
            $content
                .removeClass("collapse-content-inactive")
                .addClass("collapse-content-active");
        }
    })

    //跟实体添加事件绑定
    $("#operate").on("click", ".icon-add, .icon-add-abc", function (e) {
        e.stopPropagation();
        removePop();
        pop(this);
        $(this).addClass("active")
    });

    //属性组 多值属性 添加事件绑定
    $("#operate").on("click", ".icon-add-sm", function (e) {
        e.stopPropagation();
        removePop();        
        popsm(this);
        $(this).addClass("active")
    });

    //标签添加
    $("#operate").on("click", ".icon-add-tag", function (e) {
        e.stopPropagation();        
        removePop();
        popTag(this);
        $(this).addClass("active");
    });


    //删除属性事件绑定
    $("#operate").on("click", ".icon-trash, .icon-trash-sm", function(e) {
        e.stopPropagation();
        removePop();
        var $header = $(this).closest(".attr-group");
        if($header.length > 0) { //delete-list-c
            popGroupAttr(this);
        }else { //delete-list
            popAttr(this);
        }        
        $(this).addClass("active")
    })

    //添加页中的事件绑定
    $("#operate").on("click", ".card>li.card-list", function (e) {
        e.stopPropagation();
        if ($("#operate").find(".icon-add.active").length > 0) {
            var el = $("#operate").find(".icon-add.active")[0];
        } else if ($("#operate").find(".icon-add-sm.active").length > 0) {
            var el = $("#operate").find(".icon-add-sm.active")[0];
        } else if($("#operate").find(".icon-add-abc.active").length > 0) {
            var el = $("#operate").find(".icon-add-abc.active")[0];
        }
        if ($(this).hasClass("add-tag")) {
            addTag(el);
        } else if ($(this).hasClass("add-attr")) {
            addAttr(el);
        } else if ($(this).hasClass("add-attr-group")) {
            addGroup(el);
        } else if ($(this).hasClass("add-more-attr")) {
            addMoreAttr(el);
        } else if ($(this).hasClass("add-relative")) {
            addRelative(el);
        }
        removePop();
        $(el).removeClass("active");
    });

    //添加标签弹出页中的事件绑定
    $("#operate").on("click", ".tag-checkbox-input", function (e) {
        e.stopPropagation();
        var $parent = $(this).parent(".tag-checkbox");
        if ($parent.hasClass("tag-checkbox-checked")) {
            $parent.removeClass("tag-checkbox-checked");
        } else {
            $parent.addClass("tag-checkbox-checked");
        }
    });

    //删除数据弹出页中的事件绑定
    $("#operate").on("click", ".delete-list .opera, .delete-list-c .opera", function (e) {
        e.stopPropagation();   
        if ($("#operate").find(".icon-trash-sm.active").length > 0) {
            var el = $("#operate").find(".icon-trash-sm.active")[0];
        } else if ($("#operate").find(".con-trash.active").length > 0) {
            var el = $("#operate").find(".icon-trash.active")[0];
        } 
        if($(this).hasClass("cancel")) {
            removePop();
        }else if($(this).hasClass("confirm")) {
            var $header = $(el).closest(".collapse-header");
            if($header.length > 0 ) {
                confirmDeleteAll(el);
            }else {
                confirmDeleteSingle(el);
            }      
        }else if($(this).hasClass("only-group")) {                       
            confirmDeleteOnly(el);
        }else {
            removePop();
        }

    })


    var dragWrap1 = document.getElementById("drag-1");
    var sortable = Sortable.create(dragWrap1, {
        group: {
            name: "drag-1",
            pull: false,
            put: false,
        },
        filter: ".no-dragger",
        forceFallback: true,
        sort: true,
        animation: 100,
        onStart: function (evt) {

        },
        onEnd: function (evt) {

        }
    });

})