
seajs.use(['dialog','utils', 'ajax', '$CPF'], function(Dialog, Utils, Ajax, $CPF){
	var $page = $("#tree_view_panel");
	var parentId = $(".entity-title", $page).attr("data-id");	
	console.log(parentId);
    $(function(){
    	$CPF.showLoading();
    	 getChildByParentId(parentId);
	    $CPF.closeLoading();
    })
    
	function addUnfold(el) {	
		if($(el).hasClass("icon-add") && $(el).siblings(".icon-arrow").hasClass("active")) {
        	$(el).siblings(".icon-arrow").trigger("click");        	
        }else if($(el).hasClass("icon-add-sm") && $(el).siblings(".icon-arrow-sm").hasClass("active")){
        	$(el).siblings(".icon-arrow-sm").trigger("click");
        }else if($(el).hasClass("icon-add-abc") && $(el).siblings(".icon-arrow-sm").hasClass("active")){
        	$(el).siblings(".icon-arrow-sm").trigger("click");
        } 
	} 	
	function saveSuccess(el) {
		 $(el).closest(".label-bar").removeClass("edit");
		 $(el).closest(".entity-title").removeClass("edit");
	     $(el).closest(".entity-edit-wrap").removeClass("edit");
	     $(el).closest(".label-bar").find(".edit-input").attr("disabled", "true");
	     $(el).closest(".entity-title").find(".edit-input").attr("disabled", "true");
	     $(el).closest(".label-bar").find("select").attr("disabled", "true");
	     $(el).closest(".entity-title").find("select").attr("disabled", "true");
	     $(el).closest(".label-bar").addClass("al-save");
	}
	
	 /**
     * 加载子节点
     */
    function getChildByParentId(parentId){
    	$CPF.showLoading();
    	Ajax.ajax("admin/cascadedict/cascadedictBasicItem/getChildByParentId", {
    		parentId:parentId
		},function(data){
			console.log("children data is :");
			console.log(data);
			console.log("============"+data.code);
			if (data.code==200 && data.childList.length>0) {
				initTreeNode(data.childList);
				Dialog.notice("数据加载成功！", "success");
			} else if (data.code==200 && data.childList.length==0) {
				Dialog.notice("当前节点没有孩子！", "warning");
			} else {
				Dialog.notice("孩子数据加载错误！", "error");
			}
			$CPF.closeLoading();
		});
    }
    function initTreeNode(childList){
    	$CPF.showLoading();
    	
    	var dragWrapLen = $(".dragEdit-wrap", $page).length + 1 ;
    	var id = childList[0].parentId;
    	var parent = $(".collapse-header[data-id='" + id + "']", $page).next(".collapse-content")[0];	
    	var nodeHtml='';
    	 for (var nodeValue of childList){
				 nodeHtml = nodeHtml + 
				"<li class='attr-relative'>" + 
					"<div class='attr-relative-title attr-relative collapse-header' data-id='" + nodeValue.id + "'  data-parentId='"+nodeValue.parentId+"'>" + 
						"<div class='icon-label attr-relative'>" + 
							"<i class='icon icon-attr-relative'></i><span class='text'>节点</span>" +
						"</div>" + 
						"<div class='label-bar attr-relative al-save'>"
								nodeHtml = nodeHtml + "<input type='text' disabled class='edit-input text name' name='name' title='名称' value='"+nodeValue.name+"'>" +
								"<input type='text' disabled class='edit-input text enName' name='enName' title='英文名称' value='"+nodeValue.enName+"'>" +
								"<select disabled name='status' class='abc-attr status'>";
								 if(nodeValue.status == "启用") {            		
									 nodeHtml = nodeHtml +  "<option value='启用' selected>启用</option><option value='废弃'>废弃</option>";			    			
						          }else {            				            		
						        	  nodeHtml = nodeHtml +  "<option value='启用' >启用</option><option value='废弃' selected>废弃</option>";
						          }	
				            	nodeHtml = nodeHtml +"</select>"+
								"<input type='text' style='width:65px;' disabled class='edit-input text order' name='order'  title='排序' value='"+nodeValue.order+"'>"
							nodeHtml = nodeHtml + 
							"<div class='btn-wrap'>" + 
								"<i class='icon icon-save'></i>" +
								"<i class='icon icon-add-abc abc'></i>" +
								"<i class='icon icon-trash'></i>" + 
								"<i class='icon icon-arrow-sm active'></i>" +
							"</div>" +
						"</div>" +
					"</div>" +
					"<ul class='drag-wrap-repeat need-ajax dragEdit-wrap collapse-content collapse-content-inactive' id='dragEdit-"+dragWrapLen+"'>" +
					"</ul>" + 
		        "</li>";
				 
				 dragWrapLen = dragWrapLen + 1;
    	}
    	
    	$(parent).html(""); //清空子节点
    	var $html = $(nodeHtml).appendTo($(parent));
    	$html.find("select").css({"width":"12%","marginLeft":"16px"}).select2();
    	$CPF.closeLoading();
    }
	
    /**
     * 添加孩子
     * @param el
     * @returns
     */
    function pop(el) {
    	var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
        $CPF.showLoading();
        var nodeHtml = "<li class='attr-relative'>" + 
			"<div class='attr-relative-title attr-relative collapse-header' data-id=''  data-parentId=''>" + 
				"<div class='icon-label attr-relative'>" + 
					"<i class='icon icon-attr-relative'></i><span class='text'>节点</span>" +
				"</div>" + 
				"<div class='label-bar attr-relative al-save edit'>"+
						"<input type='text'  class='edit-input text name' name='name' title='名称' placeholder='名称' value=''>" +
						"<input type='text'  class='edit-input text enName' name='enName' title='英文名称' placeholder='英文名称' value=''>" +
						"<select name='status' class='abc-attr status'>"+
							"<option value='启用' selected>启用</option><option value='废弃'>废弃</option>"+			    			
		            	"</select>"+
						"<input type='text' style='width:65px;' class='edit-input text order' name='order' placeholder='排序'  title='排序' value=''>"+
					"<div class='btn-wrap'>" + 
						"<i class='icon icon-save'></i>" +
						"<i class='icon icon-add-abc abc'></i>" +
						"<i class='icon icon-trash'></i>" + 
						"<i class='icon icon-arrow-sm active'></i>" +
					"</div>" +
				"</div>" +
			"</div>" +
			"<ul class='drag-wrap-repeat need-ajax dragEdit-wrap collapse-content collapse-content-inactive' id='dragEdit-"+dragWrapLen+"'>" +
			"</ul>" + 
        "</li>";       		    
		    var $html = $(nodeHtml).prependTo($content);
            $html.find("select").css({"width":"12%","marginLeft":"16px"}).select2();
            addUnfold(el);
		    $CPF.closeLoading();			    
    };
	
    
    
	/**
     * 获取实体信息方法 示例     
     */
	function getEntity(entity) {		
		var cnName = $(entity).attr("data-cnname");		
		$("#tree_view_panel .entity-title>.edit-input").val(cnName);
		$("#tree_view_panel .entity-title>.entity-only-title").html(cnName);
		$("#tree_view_panel .entity-edit-wrap").addClass("active");
	}
    
    /**
     * 删除属性标签页弹出方法
      */
    function popAttr(el) {
        var html = "<div class='delete-list'>" +
            "<p>" +
            "<i class='icon icon-mark'></i><span class='text'>确定要删除此条数据?</span>" +
            "</p>" +
            "<div class='delete-list-btn'>" +
            "<span class='opera cancel'>取消</span>" +
            "<span class='opera confirm'>确认</span>" +
            "</div>" +
            "</div>"

        var wrap = $("#tree_view_panel");
        var offsetx = $(el).offset().left;
        var offsety = $(el).offset().top;
        var wrapOffsetx = wrap.offset().left;
        var wrapOffsety = wrap.offset().top;
        var top = offsety - wrapOffsety - 114;
        var left = offsetx - wrapOffsetx - 121;
        var popHtml = $(html).appendTo(wrap);
        popHtml.css({
            "top": top,
            "left": left
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
        $(".icon-add-tag-relative").removeClass("active");
        $(".icon-trash").removeClass("active");
        $(".icon-trash-sm").removeClass("active");

    };

    /**
     * 添加关系方法
      */

    //提醒有未保存的节点
    function judgeSave() {    	
        var editBar = $("#tree_view_panel").find(".label-bar.edit");
        var editEntity = $("#tree_view_panel").find(".entity-edit-wrap.edit");
        if(editBar.length > 0 || editEntity.length > 0) {
            Dialog.notice("请先保存正在编辑的节点！", "warning");
            return true;
        }
    }
    
    //关系保存修改方法
    function relativeSave(el) {  
    	var $relativeBar = $(el).closest(".label-bar");
    	var id = $(el).closest(".collapse-header").attr("data-id");
    	var parentId = $(el).closest(".collapse-content").siblings(".collapse-header").attr("data-id");
    	var name = $relativeBar.children(".name").val();
    	var enName = $relativeBar.children(".enName").val();  
    	var status = $relativeBar.children(".status").find("option:selected").val();
    	var order = $relativeBar.children(".order").val();
    	
    	var reg = /^[0-9]*$/;
    	if (!reg.test(order)) {
    		Dialog.notice("【排序】只能输入数字！", "warning");
    		return false;
    	}
    	if (name.length ==0) {
    		Dialog.notice("【名称】不能为空！", "warning");
    		return false;
    	}
    	
    	var dragWrapLen = $(".dragEdit-wrap").length + 1 ;    	
    	$CPF.showLoading();
    	Ajax.ajax('admin/cascadedict/cascadedictBasicItem/saveOrUpdate', {
    		id:id,
    		name: name,
    		enName: enName,
    		status: status,	
    		order: order,
    		parentId:parentId
		 }, function(data) {
			 	if (data.code == 200) {
			 		var creteria = data.creteria;
			    	$(el).closest(".collapse-header").attr("data-id", creteria.id);
			    	saveSuccess(el)
			    	
			 		Dialog.notice("保存成功！", "success");
			 	} else {
			 		Dialog.notice("保存失败！", "error");
			 	}
			 	 $CPF.closeLoading();
		 });
    };
    
    //跟实体删除方法
    function entityDelete(el) {
    	var $entityTitle = $(el).closest(".entity-title");
    	var id = $entityTitle.attr("data-id");
    	var callback = function() {
    		$entityTitle.next(".collapse-content").html();
    		$entityTitle.parent(".entity-edit-wrap")
    			.removeClass("active")
    			.addClass("edit");
    	};
    	if($entityTitle.hasClass("al-save")) {
    		$CPF.showLoading();
        	Ajax.ajax('admin/cascadedict/cascadedictBasicItem/doDelte/' + id, {			
    		 }, function(data) {
    			if(data.code==200) {
    				callback();
    	    		removePop();
    	    		Dialog.notice("删除成功！", "success");
    			} else {
    	    		removePop();
    	    		Dialog.notice(data.msg, "error");
    			}
    		});
        	$CPF.closeLoading();
    	}
    }
    
    
    //关系删除方法
    function relativeDelete(el) {
    	var $relativeBar = $(el).closest(".label-bar");
    	var id = $relativeBar.closest(".collapse-header").attr("data-id");
    	var callback = function() {
    		$relativeBar.closest("li.attr-relative").remove();    		
    	};
    	
    	if (id.length==0) {
    		callback();
    		removePop();
    		return;
    	}
    	if($relativeBar.hasClass("al-save")){
    		$CPF.showLoading();
        	Ajax.ajax('admin/cascadedict/cascadedictBasicItem/doDelte/' + id, {			
    		 }, function(data) {
    			if(data.code==200) {
    				callback();
    	    		removePop();
    	    		Dialog.notice("删除成功！", "success");
    			}  else {
    				removePop();
    				Dialog.notice(data.msg, "error");
    			}
    		});
        	$CPF.closeLoading();
    	} 
    };   
    
    //tag删除
    $page.on("click", function () {  
        removePop();
    });
      

    //收缩事件绑定
    $("#tree_view_panel").on("click", ".icon-arrow, .icon-arrow-sm", function (e) {
    	var attr_relative = $(this).closest(".collapse-header").hasClass("attr-relative");
    	e.stopPropagation();
    	var bar = $(this).closest(".label-bar")[0];
        var $content = $(this).closest(".collapse-header")
            .siblings(".collapse-content");
        var isRelative = $(this).closest(".label-bar").hasClass("attr-relative");        
        var needAjax = $content.hasClass("need-ajax");  //判断是否需要ajax获取数据   
        var parentId = $(this).closest(".collapse-header").attr("data-id");
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
       if(needAjax) {
    	   getChildByParentId(parentId);
    	   $content.removeClass("need-ajax");
        } else {
        	$content.addClass("need-ajax");
        }
    })

    //跟实体添加事件绑定
    $("#tree_view_panel").on("click", ".icon-add, .icon-add-abc", function (e) {
        e.stopPropagation();
        var hasSave = judgeSave();
        if(hasSave){
            return;
        }
        removePop();
        pop(this);
        $(this).addClass("active")
    });

    //关系下标签添加

    //删除属性事件绑定
    $("#tree_view_panel").on("click", ".icon-trash, .icon-trash-sm", function (e) {
        e.stopPropagation();
        removePop();
        var $header = $(this).closest(".label-bar").hasClass("attr-group");
        
        if ($header) { //delete-list-c
            popGroupAttr(this);
        } else { //delete-list
            popAttr(this);
        }
        $(this).addClass("active")
    })

    //添加页中的事件绑定
    $("#tree_view_panel").on("click", ".card>li.card-list", function (e) {
        e.stopPropagation();
        if ($("#tree_view_panel").find(".icon-add.active").length > 0) {
            var el = $("#tree_view_panel").find(".icon-add.active")[0];
        } else if ($("#tree_view_panel").find(".icon-add-sm.active").length > 0) {
            var el = $("#tree_view_panel").find(".icon-add-sm.active")[0];
        } else if ($("#tree_view_panel").find(".icon-add-abc.active").length > 0) {
            var el = $("#tree_view_panel").find(".icon-add-abc.active")[0];
        }
        if ($(this).hasClass("add-relative")) {
            addRelative(el);
        }
        removePop();
        $(el).removeClass("active");
    });


    //双击编辑
    $("#tree_view_panel").on("dblclick", ".label-bar", function(){
		$(this).find(".edit-input").removeAttr("disabled");
    	$(this).find("select").removeAttr("disabled");
        $(this).addClass("edit");
    })
    
    //双击编辑
    $("#tree_view_panel").on("dblclick", ".entity-title", function(){   
    	$(this).find(".edit-input").removeAttr("disabled");
    	$(this).find("select").removeAttr("disabled");
        $(this).addClass("edit");
    })
    
    //保存
    $("#tree_view_panel").on("click", ".icon-save", function() {        
        var entityTitle = $(this).closest(".entity-title");
        var labelBar = $(this).closest(".label-bar");
        if(entityTitle.length > 0) {
        	entitySave(this);
        	return;
        }
        if(labelBar.hasClass("tag")) {        	
        	tagSave(this);
        }else if(labelBar.hasClass("attr")) {
        	attrSave(this);
        }else if(labelBar.hasClass("more-attr")) {
        	moreAttrSave(this);
        }else if(labelBar.hasClass("attr-group")) {
        	attrGroupSave(this);
        }else if(labelBar.hasClass("attr-relative")) {
        	relativeSave(this);
        }else if(labelBar.hasClass("abc")) {
        	abcSave(this);
        }

    });
    
    //删除-全部
    $("#tree_view_panel").on("click", ".opera.confirm", function(e) {  
    	e.stopPropagation();    
        var entityTitle = $(".icon-trash.active").closest(".entity-title");
        var labelBar = $(".icon-trash-sm.active").closest(".label-bar");
        if(entityTitle.length > 0) {
        	var el = $(".icon-trash.active")[0];
        	entityDelete(el);
        	return;
        } 
        var el = $(".icon-trash.active")[0];        
        relativeDelete(el);
    })
    
    
})