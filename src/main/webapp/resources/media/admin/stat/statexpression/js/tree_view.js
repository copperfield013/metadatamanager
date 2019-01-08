
seajs.use(['dialog','utils', 'ajax', '$CPF'], function(Dialog, Utils, Ajax, $CPF){
	var $page = $("#statEntity-wrap");
	/*var parentId = $(".entity-title", $page).attr("data-id");	*/
	console.log(parentId);
   /* $(function(){
    	$CPF.showLoading();
    	 getChildByParentId(parentId);
	    $CPF.closeLoading();
    })*/
    
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
     * 跟实体添加页面弹出方法
     * @param {当前点击元素,dom对象} el 
     */
    function pop(el) {
    	 debugger;
    	var $collapseHeader = $(el).closest(".collapse-header");
    	var $content = $collapseHeader.siblings(".collapse-content");
    	var liCount = $content.children("li").length;
    	if ($collapseHeader.hasClass("entity-title")) {
    		if (liCount>=1) {
    			Dialog.notice("只能有一个节点！", "warning");
        		return;
    		}
    	} else {//这里判断function有几个孩子
    		var options=$(".functionType option:selected");  //获取选中的项
    		
    		var pageSize = options.attr("parasize");
    		
    		if (liCount>=pageSize) {
    			Dialog.notice("只能有"+pageSize+"节点！", "warning");
        		return;
    		}
    	}
    	
    	
    	
    	 var html = "<ul class='card'>";        
        /* html += "<li class='card-list add-dict'>" +
             "<i class='icon icon-card-tag'></i>" +
             "<span class='text'>添加ATTRVAR</span>" +
             "</li>" +
             "<li class='card-list add-subselection'>" +
             "<i class='icon icon-card-attr'></i>" +
             "<span class='text'>添加CONSTANT</span>" +
             "</li>"+
             "<li class='card-list add-subselection'>" +
             "<i class='icon icon-card-attr'></i>" +
             "<span class='text'>添加FUNCTION</span>" +
             "</li>"+
             "</ul>";*/
    	 
    	 html += "<li class='card-list add-ATTRVAR'>" +
         "<i class='icon icon-card-tag'></i>" +
         "<span class='text'>添加ATTRVAR</span>" +
         "</li>" +
         "<li class='card-list add-CONSTANT'>" +
         "<i class='icon icon-card-attr'></i>" +
         "<span class='text'>添加CONSTANT</span>" +
         "</li>"+
         "<li class='card-list add-FUNCTION'>" +
         "<i class='icon icon-card-attr'></i>" +
         "<span class='text'>添加FUNCTION</span>" +
         "</li>"+
         "</ul>";
    	
    	 var labelBar = $(el).closest(".label-bar");
       /*  if (labelBar.hasClass("attr-subselection")) {
        	 html = "<ul class='card'>";        
             html += "<li class='card-list add-subselect-child-dict'>" +
                 "<i class='icon icon-card-tag'></i>" +
                 "<span class='text'>选择字典</span>" +
                 "</li>" +
                 "</ul>";
         }*/
            
        var wrap = $("#tree_view_panel");
        var offsetx = $(el).offset().left;
        var offsety = $(el).offset().top;
        var wrapOffsetx = wrap.offset().left;
        var wrapOffsety = wrap.offset().top;
        var top = offsety - wrapOffsety + 30;
        var left = offsetx -wrapOffsetx - 90;
        var popHtml = $(html).appendTo(wrap);
        popHtml.css({
            "top": top,
            "left": left
        });

    };
    
    
    //添加页中的事件绑定
    $("#tree_view_panel").on("click", ".card>li.card-list", function (e) {
    	e.stopPropagation();
    	
        if ($("#tree_view_panel").find(".icon-add.active").length > 0) {
            var el = $("#tree_view_panel").find(".icon-add.active")[0];
        } else if ($("#tree_view_panel").find(".icon-add-abc.active").length > 0) {
            var el = $("#tree_view_panel").find(".icon-add-abc.active")[0];
        }
       /* if ($(this).hasClass("add-ATTRVAR")) {
            addDict(el);
        } else if ($(this).hasClass("add-CONSTANT")) {
            addSubselection(el);
        } else if ($(this).hasClass("add-FUNCTION")) {
        	addSubChildDict(el);
        }*/
        
        if ($(this).hasClass("add-ATTRVAR")) {
        	addATTRVAR(el);
        } else if ($(this).hasClass("add-CONSTANT")) {
        	addCONSTANT(el);
        } else if ($(this).hasClass("add-FUNCTION")) {
        	addFUNCTION(el);
        }
        removePop();
        $(el).removeClass("active");
    	
    	
    });
    
    
    /**
     * 添加addCONSTANT
      */
    function addCONSTANT(el) {
    	//var parentId = $(el).closest(".collapse-header").attr("data-parentid");
    	var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
        $CPF.showLoading();
        var nodeHtml = "<li class='attr-relative'>" + 
			"<div class='attr-relative-title attr-relative collapse-header' data-id=''  data-parentId=''>" + 
				"<div class='icon-label attr-relative-sub attr-relative'>" + 
					"<i class='icon icon-attr-group'></i><span class='text'>CONSTANT</span>" +
				"</div>" + 
				"<div class='label-bar attr-relative CONSTANT-save  al-save edit'>"+
						"<span id='bianhaospan' style='color: #363636;padding-right: 1em;' title='编号'></span>"+
						"<input type='text'  class='edit-input text parameter0' name='parameter0' title='parameter0' placeholder='parameter0' value=''>" +
					"<div class='btn-wrap'>" + 
						"<i class='icon icon-save'></i>" +
						/*"<i class='icon icon-add-abc abc'></i>" +*/
						"<i class='icon icon-trash'></i>" + 
						/*"<i class='icon icon-arrow-sm active'></i>" +*/
					"</div>" +
				"</div>" +
			"</div>" +
//			"<ul class='drag-wrap-repeat need-ajax dragEdit-wrap collapse-content collapse-content-inactive' id='dragEdit-"+dragWrapLen+"'>" +
//			"</ul>" + 
			
        "</li>";       		    
		    var $html = $(nodeHtml).prependTo($content);
            $html.find("select").css({"width":"12%","marginLeft":"16px"}).select2();
            addUnfold(el);
		    $CPF.closeLoading();	
    }
    
    /**
     * 添加addFUNCTION
      */
    function addFUNCTION(el) {
    	
    	var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
    	
    	//这里获取可以选择的字典
    	var dictParentId = $(el).closest(".collapse-header").attr("data-parentid");
    	
    	$CPF.showLoading();
    	Ajax.ajax("admin/stat/statExpression/getSQLFunctionType", {
		},function(data){
			var sqlFunctionType = data.sqlFunctionType;
			if (data.code==200 && sqlFunctionType.length>0) {
				
		        var nodeHtml = "<li class='attr-relative'>" + 
					"<div class='attr-relative-title attr-relative collapse-header' data-id=''  data-parentId=''>" + 
						"<div class='icon-label attr-relative-dict attr-relative'>" + 
							"<i class='icon icon-attr-relative'></i><span class='text'>FUNCTION</span>" +
						"</div>" + 
						"<div class='label-bar attr-relative  FUNCTION-save  al-save edit'>"+
								"<span id='bianhaospan' style='color: #363636;padding-right: 1em;' title='编号'></span>"+
								"<select name='functionType' class='abc-attr functionType'>";
								 for (var key in sqlFunctionType){
									 nodeHtml+="<option  title='"+sqlFunctionType[key][1]+"' paraSize='"+sqlFunctionType[key][2]+"' value='"+sqlFunctionType[key][1]+"'>"+sqlFunctionType[key][0]+"</option>";
								 }
								 nodeHtml +="</select>"+
								
				            	"<input type='text' class='edit-input text parameter1' name='parameter1' placeholder='parameter1'  title='parameter1' value=''>"+
				            	"<input type='text'  class='edit-input text parameter2' name='parameter2' placeholder='parameter2'  title='parameter2' value=''>"+
				            	"<input type='text' class='edit-input text parameter3' name='parameter3' placeholder='parameter3'  title='parameter3' value=''>"+
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
			} else if (data.code==200 && data.sqlFunctionType.length==0) {
				Dialog.notice("没有SQLFunctionType可供选择", "warning");
			} else {
				Dialog.notice("数据加载错误！", "error");
			}
			$CPF.closeLoading();
		});
    	
    }
	
    /**
     * 添加add-ATTRVAR
     * @param el
     * @returns
     */
    function addATTRVAR(el) {
    	
    	var sourcecode = $(el).closest(".properOperate").attr("sourcecode");
    	
    	var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
        $CPF.showLoading();
        var nodeHtml = "<li class='attr-relative'>" + 
			"<div class='attr-relative-title attr-relative-dict attr-relative collapse-header' data-id=''  data-parentId=''>" + 
				"<div class='icon-label attr-relative-dict attr-relative'>" + 
					"<i class='icon icon-attr-relative'></i><span class='text'>ATTRVAR</span>" +
				"</div>" + 
				"<div class='label-bar attr-relative ATTRVAR-save al-save edit'>"+
				"<span id='bianhaospan' style='color: #363636;padding-right: 1em;' title='编号'></span>";
						
				/*
				 * 获取来源实体的普通孩子
				 * */
				Ajax.ajax("admin/dictionary/basicItem/getComm", {
					entityId:sourcecode
				},function(data){
					var commList = data.commList;
					if (data.code==200) {
						nodeHtml +="<select name='parameter0' title='parameter0' class='abc-attr parameter0'>";
						for(var key in commList) {
							nodeHtml +="<option value='"+commList[key][0]+"'>"+commList[key][1]+"</option>";		
						}
						nodeHtml+="</select>"+
							"<div class='btn-wrap'>" + 
								"<i class='icon icon-save'></i>" +
								/*"<i class='icon icon-add-abc abc'></i>" +*/
								"<i class='icon icon-trash'></i>" + 
								/*"<i class='icon icon-arrow-sm active'></i>" +*/
							"</div>" +
							"</div>" +
						"</div>" +
//						"<ul class='drag-wrap-repeat need-ajax dragEdit-wrap collapse-content collapse-content-inactive' id='dragEdit-"+dragWrapLen+"'>" +
//						"</ul>" + 
			        "</li>";       		    
					    var $html = $(nodeHtml).prependTo($content);
			            $html.find("select").css({"width":"15%","marginLeft":"16px"}).select2();
			            addUnfold(el);
					    $CPF.closeLoading();
					} else {
						Dialog.notice("来源实体普通属性加载错误！", "error");
					}
	})
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
            "</div>";

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
    
  //subselection保存修改方法
    function subselectionSave(el) {
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
    	Ajax.ajax('admin/cascadedict/cascadedictSubsection/saveOrUpdate', {
    		id:id,
    		name: name,
    		status: status,	
    		order: order,
    		parentId:parentId
		 }, function(data) {
			 	if (data.code == 200) {
			 		var creteria = data.creteria;
			    	$(el).closest(".collapse-header").attr("data-id", creteria.id);
			    	$(el).closest(".collapse-header").attr("data-parentid", creteria.parentId);
			    	$(el).closest(".collapse-header").find(".label-bar").find("#bianhaospan").html(creteria.id);
			    	saveSuccess(el)
			    	
			 		Dialog.notice("保存成功！", "success");
			 	} else {
			 		Dialog.notice("保存失败！", "error");
			 	}
			 	 $CPF.closeLoading();
		 });
    };
    
  //subselection孩子的保存修改方法
    function CONSTANTSave(el) {
    	var $relativeBar = $(el).closest(".label-bar");
    	var id = $(el).closest(".collapse-header").attr("data-id");
    	var parameter0 = $relativeBar.children(".parameter0").val();
    	var type=2;
    	
    	var dragWrapLen = $(".dragEdit-wrap").length + 1 ;    	
    	$CPF.showLoading();
    	Ajax.ajax('admin/stat/statExpression/do_add', {
    		id:id,
    		type:type,
    		parameter0:parameter0
		 }, function(data) {
			 	if (data.code == 200) {
			 		var creteria = data.creteria;
			    	$(el).closest(".collapse-header").attr("data-id", creteria.id);
			    	$(el).closest(".collapse-header").parent().attr("data-id", creteria.id);
			    	$(el).closest(".collapse-header").find(".label-bar").find("#bianhaospan").html(creteria.id);
			    	saveSuccess(el)
			    	
			 		Dialog.notice("保存成功！", "success");
			 	} else {
			 		Dialog.notice("保存失败！", "error");
			 	}
			 	 $CPF.closeLoading();
		 });
    };
    
    //ATTRVAR保存修改方法
    function FUNCTIONSave(el) {  
    	var $relativeBar = $(el).closest(".label-bar");
    	var id = $(el).closest(".collapse-header").attr("data-id");
    	/*var parentId = $(el).closest(".collapse-content").siblings(".collapse-header").attr("data-id");*/
    	
    	var $collapseContent = $(el).closest(".collapse-header").siblings(".collapse-content");
    	
    	var count= 1;
    		$collapseContent.children("li").each(function(){
    			var dataId =  $(this).attr("data-id");
    			$relativeBar.children(".parameter" + count).val(dataId);
    			
    			count++;
    		  });
    	
    	var functionType = $relativeBar.children(".functionType").find("option:selected").val();
    	var parameter1 = $relativeBar.children(".parameter1").val();
    	var parameter2 = $relativeBar.children(".parameter2").val();
    	var parameter3 = $relativeBar.children(".parameter3").val();
    	var type=1;
    	
    	console.log("functionType：" + functionType);
    	console.log("parameter1：" + parameter1);
    	console.log("parameter2：" + parameter2);
    	console.log("parameter3：" + parameter3);
    	
    	var dragWrapLen = $(".dragEdit-wrap").length + 1 ;    	
    	$CPF.showLoading();
    	Ajax.ajax('admin/stat/statExpression/do_add', {
    		id:id,
    		type:type,
    		functionType:functionType,
    		parameter1:parameter1,
    		parameter2:parameter2,
    		parameter3:parameter3
		 }, function(data) {
			 	if (data.code == 200) {
			 		var creteria = data.creteria;
			    	$(el).closest(".collapse-header").attr("data-id", creteria.id);
			    	$(el).closest(".collapse-header").parent().attr("data-id", creteria.id);
			    	$(el).closest(".collapse-header").find(".label-bar").find("#bianhaospan").html(creteria.id);
			    	saveSuccess(el)
			    	
			 		Dialog.notice("保存成功！", "success");
			 	} else {
			 		Dialog.notice("保存失败！", "error");
			 	}
			 	 $CPF.closeLoading();
		 });
    };
    
    //ATTRVAR保存修改方法
    function ATTRVARSave(el) {  
    	var $relativeBar = $(el).closest(".label-bar");
    	var id = $(el).closest(".collapse-header").attr("data-id");
    	/*var parentId = $(el).closest(".collapse-content").siblings(".collapse-header").attr("data-id");*/
    	var parameter0 = $relativeBar.children(".parameter0").find("option:selected").val();
    	var type=3;
    	
    	var dragWrapLen = $(".dragEdit-wrap").length + 1 ;    	
    	$CPF.showLoading();
    	Ajax.ajax('admin/stat/statExpression/do_add', {
    		id:id,
    		type:type,
    		parameter0:parameter0
		 }, function(data) {
			 	if (data.code == 200) {
			 		var creteria = data.creteria;
			    	$(el).closest(".collapse-header").attr("data-id", creteria.id);
			    	$(el).closest(".collapse-header").parent().attr("data-id", creteria.id);
			    	$(el).closest(".collapse-header").find(".label-bar").find("#bianhaospan").html(creteria.id);
			    	saveSuccess(el)
			    	
			 		Dialog.notice("保存成功！", "success");
			 	} else {
			 		Dialog.notice("保存失败！", "error");
			 	}
			 	 $CPF.closeLoading();
		 });
    };
    
    //跟实体删除方法
   /* function entityDelete(el) {
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
    }*/
    
    
    //关系删除方法
   /* function relativeDelete(el) {
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
    };   */
    
    
  //statExpression 删除方法
    function statExpressionDel(el) {
    	
    	
    	debugger;
    	
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
        	Ajax.ajax('admin/stat/statExpression/do_delete', {	
        		id:id
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
    
    //subChild 删除方法
   /* function subChildDel(el) {
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
        	Ajax.ajax('admin/cascadedict/cascadedictSubsection/doDelSubChild/' + id, {			
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
    };  */
    
    //tag删除
    $page.on("click", function () {  
        removePop();
    });
      

    //收缩事件绑定
    $("#tree_view_panel").on("click", ".icon-arrow, .icon-arrow-sm", function (e) {
    	var attr_relative = $(this).closest(".collapse-header").hasClass("attr-relative");
    	e.stopPropagation();
    	
    	
    	debugger;
    	
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
    	  var FUNCTIONSave =  $(this).closest(".label-bar").hasClass("FUNCTION-save");
    	  if (FUNCTIONSave) {//这里是加载subChild
    		 // getFUNCATIONchild();
    	  }
    	   
    	   $content.removeClass("need-ajax");
        } else {
        	$content.addClass("need-ajax");
        }
    })

    //跟实体添加事件绑定
    $(".properOperate").on("click", ".icon-add, .icon-add-abc", function (e) {
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
    $(".properOperate", $page).on("click", ".icon-save", function() {       
    	
    	
        var entityTitle = $(this).closest(".entity-title");
        var labelBar = $(this).closest(".label-bar");
        if(entityTitle.length > 0) {
        	entitySave(this);
        	return;
        }
        
        
      if(labelBar.hasClass("FUNCTION-save")) {
        	FUNCTIONSave(this);
        }else if(labelBar.hasClass("ATTRVAR-save")) {
        	ATTRVARSave(this);
        }else if(labelBar.hasClass("CONSTANT-save")) {
        	CONSTANTSave(this);
        } else if(labelBar.hasClass("filters")) {
        	filtersSave(this);
        } else if(labelBar.hasClass("filterGroup")) {
        	filterGroupSave(this);
        }else if(labelBar.hasClass("rFilter")) {
        	rFilterSave(this);
        } else if(labelBar.hasClass("filter")) {
        	filterSave(this);
        }

    });
    
    
  //filters  保存修改方法
    function filtersSave(el) {
    	
    	debugger;
    	
    	var $abcBar = $(el).closest(".label-bar");
    	var name = $abcBar.children(".edit-input").val();    	
    	var id = $abcBar.closest(".collapse-header").attr("data-id");
    	var parentId = $abcBar.closest(".collapse-content").prev(".collapse-header")
    						.attr("data-id"); 
    	var opt = $abcBar.children(".node-ops-type").find("option:selected").val();
    	
    	
    	return;
    	
    	$CPF.showLoading();
    	Ajax.ajax('admin/node/binFilterBody/saveOrUpdate', {
			 name: name,
			 parentId: parentId,
			 id: id,
			 isFilters: true,
			 dataType: 10,
			 opt:opt
		 }, function(data) {
			if(data.code == "400") {
				 Dialog.notice(data.msg, "warning");
				 $CPF.closeLoading();
				 return;
			 }
			
			if (data.binFilter!= undefined) {
				var data = data.binFilterBody;
				 //设置当前节点order和id
				 var order = data.order;
				 var id = data.id;
				 $abcBar.closest(".collapse-header")
				 	.attr("data-order",order)
				 	.attr("data-id", id);
			}
			 
			 saveSuccess(el)
			 $CPF.closeLoading();
		});
    };
    
  //filterGroup  保存修改方法
    function filterGroupSave(el) {
    	var $abcBar = $(el).closest(".label-bar");
    	var name = $abcBar.children(".edit-input").val();    	
    	var id = $abcBar.closest(".collapse-header").attr("data-id");
    	var parentId = $abcBar.closest(".collapse-content").prev(".collapse-header")
    						.attr("data-id"); 
    	var opt = $abcBar.children(".node-ops-type").find("option:selected").val();
    	$CPF.showLoading();
    	Ajax.ajax('admin/node/binFilterBody/saveOrUpdate', {
			 name: name,
			 parentId: parentId,
			 id: id,
			 isFilters: false,
			 dataType: 11,
			 opt:opt
		 }, function(data) {
			if(data.code == "400") {
				 Dialog.notice(data.msg, "warning");
				 $CPF.closeLoading();
				 return;
			 }
			
				var data = data.binFilterBody;
				 //设置当前节点order和id
				 var order = data.order;
				 var id = data.id;
				 $abcBar.closest(".collapse-header")
				 	.attr("data-order",order)
				 	.attr("data-id", id);
			 
			 saveSuccess(el)
			 $CPF.closeLoading();
		});
    };
    
  //filterSave  保存修改方法
    function filterSave(el) {
    	var $abcBar = $(el).closest(".label-bar");
    	var name = $abcBar.children(".filterName").val();    	
    	var id = $abcBar.closest(".collapse-header").attr("data-id");
    	var parentId = $abcBar.closest(".collapse-content").prev(".collapse-header")
    						.attr("data-id"); 
    	var opt = $abcBar.children(".node-ops-type").find("option:selected").val();
    	var filterType = $abcBar.children(".node-Symbol-type").find("option:selected").val();
    	var valueStr = $abcBar.children(".valueStr").val();  
    	
    	var abcattrCode = $abcBar.children(".abcattrCodeData").find("option:selected").val();
    	
    	$CPF.showLoading();
    	Ajax.ajax('admin/node/binFilterBody/saveOrUpdate', {
			 name: name,
			 parentId: parentId,
			 id: id,
			 isFilters: false,
			 dataType: 12,
			 opt:opt,
			 filterType: filterType,
			 value:valueStr,
			 abcattrCode:abcattrCode
		 }, function(data) {
			if(data.code == "400") {
				 Dialog.notice(data.msg, "warning");
				 $CPF.closeLoading();
				 return;
			 }
			
				var data = data.binFilterBody;
				 //设置当前节点order和id
				 var order = data.order;
				 var id = data.id;
				 $abcBar.closest(".collapse-header")
				 	.attr("data-order",order)
				 	.attr("data-id", id);
			 
			 saveSuccess(el)
			 $CPF.closeLoading();
		});
    };
    
  //rFilterSave  保存修改方法
    function rFilterSave(el) {
    	var $abcBar = $(el).closest(".label-bar");
    	var name = $abcBar.children(".edit-input").val();    	
    	var id = $abcBar.closest(".collapse-header").attr("data-id");
    	var parentId = $abcBar.closest(".collapse-content").prev(".collapse-header")
    						.attr("data-id"); 
    	var opt = $abcBar.children(".node-ops-type").find("option:selected").val();
    	var filterType = $abcBar.children(".node-Symbol-type").find("option:selected").val();
    	
    	var relationData = $abcBar.children(".relationData").find("option:selected").val();
    	var rightRecordType = $abcBar.children(".relationData").find("option:selected").attr("rightRecordType");
    	$CPF.showLoading();
    	Ajax.ajax('admin/node/binFilterBody/saveOrUpdate', {
			 name: name,
			 parentId: parentId,
			 id: id,
			 isFilters: false,
			 dataType: 13,
			 opt:opt,
			 filterType: filterType,
			 subdomain:relationData
		 }, function(data) {
			if(data.code == "400") {
				 Dialog.notice(data.msg, "warning");
				 $CPF.closeLoading();
				 return;
			 }
				var data = data.binFilterBody;
				 //设置当前节点order和id
				 var order = data.order;
				 var id = data.id;
				 $abcBar.closest(".collapse-header")
				 	.attr("data-order",order)
				 	.attr("data-id", id)
				 	.attr("entityid", rightRecordType);
			 
			 saveSuccess(el)
			 $CPF.closeLoading();
		});
    };
    
    //删除-全部
    $("#tree_view_panel").on("click", ".opera.confirm", function(e) {  
    	e.stopPropagation();    
        var entityTitle = $(".icon-trash.active").closest(".entity-title");
        var labelBar = $(".icon-trash.active").closest(".label-bar");
        var el = $(".icon-trash.active")[0]; 
        if(entityTitle.length > 0) {
        	entityDelete(el);
        	return;
        } 
        statExpressionDel(el);
        
        /*if (labelBar.hasClass("attr-subselection-child")) {
        	subChildDel(el);
        	return;
        }*/
        
       /* relativeDelete(el);*/
    })
    
    
})