
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
    	
    	var parent = $(".collapse-header[data-id='" + parentId + "']", $page).next(".collapse-content")[0];
    	$(parent).html(""); //清空子节点
    	
    	Ajax.ajax("admin/cascadedict/cascadedictSubsection/getSubSelectByParentId", {
    		parentId:parentId
		},function(data){
			if (data.code==200 && data.childList.length>0) {
				initTreeNodeSubselection(data.childList, parent);
				Dialog.notice("子集数据加载成功！", "success");
			} else if (data.code==200 && data.childList.length==0) {
				Dialog.notice("当前节点没有子集！", "warning");
			} else {
				Dialog.notice("子集数据加载错误！", "error");
			}
			
	    	Ajax.ajax("admin/cascadedict/cascadedictBasicItem/getChildByParentId", {
	    		parentId:parentId
			},function(data){
				if (data.code==200 && data.childList.length>0) {
					initTreeNode(data.childList, parent);
					Dialog.notice("字典数据加载成功！", "success");
				} else if (data.code==200 && data.childList.length==0) {
					Dialog.notice("当前节点没有孩子字典！", "warning");
				} else {
					Dialog.notice("孩子字典数据加载错误！", "error");
				}
				$CPF.closeLoading();
			});
			$CPF.closeLoading();
		});
    	
    	$CPF.closeLoading();
    }
    
    
    /**
     * 加载subChild
     */
    function getSubChildByPid(parentId){
    	$CPF.showLoading();
    	var $colHeader = $(".collapse-header[data-id='" + parentId + "']", $page);
    	var parent = $(".collapse-header[data-id='" + parentId + "']", $page).next(".collapse-content")[0];
    	$(parent).html(""); //清空子节点
    	
    	Ajax.ajax("admin/cascadedict/cascadedictSubsection/getSubChildByPid", {
    		subsectionId:parentId
		},function(data){
			if (data.code==200 && data.childList.length>0) {
				initTreeNodeSubChild(data.childList, parent, $colHeader);
				Dialog.notice("子集孩子加载成功！", "success");
			} else if (data.code==200 && data.childList.length==0) {
				Dialog.notice("当前子集没有孩子！", "warning");
			} else {
				Dialog.notice("子集孩子数据加载错误！", "error");
			}
			$CPF.closeLoading();
		});
    }
    
    //加载子集数据
    function initTreeNodeSubselection(childList, parent){
    	$CPF.showLoading();
    	
    	var dragWrapLen = $(".dragEdit-wrap", $page).length + 1 ;
    	
    	//var id = childList[0].parentId;
    		
    	var nodeHtml='';
    	 for (var nodeValue of childList){
				 nodeHtml = nodeHtml + 
				"<li class='attr-relative'>" + 
					"<div class='attr-relative-title attr-relative collapse-header' data-id='" + nodeValue.id + "'  data-parentId='"+nodeValue.parentId+"'>" + 
						"<div class='icon-label attr-relative-sub attr-relative'>" + 
							"<i class='icon icon-attr-group'></i><span class='text'>子集</span>" +
						"</div>" + 
						"<div class='label-bar attr-relative attr-subselection al-save'>"
								nodeHtml = nodeHtml + "<span style='color: #363636;padding-right: 1em;' title='子集号'>"+nodeValue.id+"</span><input type='text' disabled class='edit-input text name' name='name' title='名称' value='"+nodeValue.name+"'>" +
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
    	
    	//$(parent).html(""); //清空子节点
    	var $html = $(nodeHtml).appendTo($(parent));
    	$html.find("select").css({"width":"12%","marginLeft":"16px"}).select2();
    	$CPF.closeLoading();
    }
    
    //加载子集孩子数据
    function initTreeNodeSubChild(childList, parent,colHeader){
    	$CPF.showLoading();
    	var dragWrapLen = $(".dragEdit-wrap", $page).length + 1 ;
    	//这里获取可以选择的字典
    	var dictParentId = $(colHeader).attr("data-parentid");
    	
    	$CPF.showLoading();
    	Ajax.ajax("admin/cascadedict/cascadedictBasicItem/getChildByParentId", {
    		parentId:dictParentId
		},function(data){
			var dictList = data.childList;
			if (data.code==200 && dictList.length>0) {
		    	var nodeHtml='';
		    	 for (var nodeValue of childList){
						 nodeHtml = nodeHtml + 
						"<li class='attr-relative'>" + 
							"<div class='attr-relative-title attr-relative collapse-header' data-id='" + nodeValue.id + "'  data-parentId='"+nodeValue.subsectionId+"'>" + 
								"<div class='icon-label attr-relative-dict attr-relative'>" + 
									"<i class='icon icon-attr-relative'></i><span class='text'>字典</span>" +
								"</div>" + 
								"<div class='label-bar attr-relative attr-subselection-child al-save'>"
										nodeHtml = nodeHtml + "<span style='color: #363636;padding-right: 1em;' title='子集孩子号'>"+nodeValue.id+"</span>" +
										"<select disabled name='childId' class='abc-attr childId'>";
											 for (var nodeKey of dictList){
												 if (nodeKey.id == nodeValue.childId) {
													 nodeHtml+="<option  value='"+nodeKey.id+"' selected>"+nodeKey.name+"</option>";
												 } else {
													 nodeHtml+="<option  value='"+nodeKey.id+"'>"+nodeKey.name+"</option>";
												 }
											 }
										nodeHtml +="</select>"+
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
										/*"<i class='icon icon-add-abc abc'></i>" +*/
										"<i class='icon icon-trash'></i>" + 
										/*"<i class='icon icon-arrow-sm active'></i>" +*/
									"</div>" +
								"</div>" +
							"</div>" +
							"<ul class='drag-wrap-repeat need-ajax dragEdit-wrap collapse-content collapse-content-inactive' id='dragEdit-"+dragWrapLen+"'>" +
							"</ul>" + 
				        "</li>";
						 
						 dragWrapLen = dragWrapLen + 1;
		    	}
		    	
		    	var $html = $(nodeHtml).appendTo($(parent));
		    	$html.find("select").css({"width":"12%","marginLeft":"16px"}).select2();
		    	$CPF.closeLoading();
		    	
			} else if (data.code==200 && data.childList.length==0) {
				Dialog.notice("没有可选字典，请先添加！", "warning");
			} else {
				Dialog.notice("可选字典数据加载错误！", "error");
			}
			$CPF.closeLoading();
		});
    }
    
    //加载孩子字典
    function initTreeNode(childList, parent){
    	$CPF.showLoading();
    	
    	var dragWrapLen = $(".dragEdit-wrap", $page).length + 1 ;
    	//var id = childList[0].parentId;
    	//var parent = $(".collapse-header[data-id='" + id + "']", $page).next(".collapse-content")[0];	
    	var nodeHtml='';
    	 for (var nodeValue of childList){
				 nodeHtml = nodeHtml + 
				"<li class='attr-relative'>" + 
					"<div class='attr-relative-title attr-relative collapse-header' data-id='" + nodeValue.id + "'  data-parentId='"+nodeValue.parentId+"'>" + 
						"<div class='icon-label attr-relative-dict attr-relative'>" + 
							"<i class='icon icon-attr-relative'></i><span class='text'>字典</span>" +
						"</div>" + 
						"<div class='label-bar attr-relative-dict attr-relative attr-relative-save al-save'>"
								nodeHtml = nodeHtml + "<span style='color: #363636;padding-right: 1em;' title='编号'>"+nodeValue.id+"</span><input type='text' disabled class='edit-input text name' name='name' title='名称' value='"+nodeValue.name+"'>" +
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
    	
    	//$(parent).html(""); //清空子节点
    	var $html = $(nodeHtml).appendTo($(parent));
    	$html.find("select").css({"width":"12%","marginLeft":"16px"}).select2();
    	$CPF.closeLoading();
    }
    
    
    /**
     * 跟实体添加页面弹出方法
     * @param {当前点击元素,dom对象} el 
     */
    function pop(el) {
    	
    	 var html = "<ul class='card'>";        
         html += "<li class='card-list add-dict'>" +
             "<i class='icon icon-card-tag'></i>" +
             "<span class='text'>添加字典</span>" +
             "</li>" +
             "<li class='card-list add-subselection'>" +
             "<i class='icon icon-card-attr'></i>" +
             "<span class='text'>添加子集</span>" +
             "</li>"+
             "</ul>";
    	
    	 var labelBar = $(el).closest(".label-bar");
         if (labelBar.hasClass("attr-subselection")) {
        	 html = "<ul class='card'>";        
             html += "<li class='card-list add-subselect-child-dict'>" +
                 "<i class='icon icon-card-tag'></i>" +
                 "<span class='text'>选择字典</span>" +
                 "</li>" +
                 "</ul>";
         }
            
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
        if ($(this).hasClass("add-dict")) {
            addDict(el);
        } else if ($(this).hasClass("add-subselection")) {
            addSubselection(el);
        } else if ($(this).hasClass("add-subselect-child-dict")) {
        	addSubChildDict(el);
        }
        removePop();
        $(el).removeClass("active");
    	
    	
    });
    
    
    /**
     * 添加子集
      */
    function addSubselection(el) {
    	//var parentId = $(el).closest(".collapse-header").attr("data-parentid");
    	var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
        $CPF.showLoading();
        var nodeHtml = "<li class='attr-relative'>" + 
			"<div class='attr-relative-title attr-relative collapse-header' data-id=''  data-parentId=''>" + 
				"<div class='icon-label attr-relative-sub attr-relative'>" + 
					"<i class='icon icon-attr-group'></i><span class='text'>子集</span>" +
				"</div>" + 
				"<div class='label-bar attr-relative attr-subselection  al-save edit'>"+
						"<span id='bianhaospan' style='color: #363636;padding-right: 1em;' title='子集号'></span>"+
						"<input type='text'  class='edit-input text name' name='name' title='名称' placeholder='名称' value=''>" +
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
    }
    
    /**
     * 添加子集的孩子
      */
    function addSubChildDict(el) {
    	
    	var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
    	
    	//这里获取可以选择的字典
    	var dictParentId = $(el).closest(".collapse-header").attr("data-parentid");
    	
    	$CPF.showLoading();
    	Ajax.ajax("admin/cascadedict/cascadedictBasicItem/getChildByParentId", {
    		parentId:dictParentId
		},function(data){
			var dictList = data.childList;
			if (data.code==200 && dictList.length>0) {
				
		        var nodeHtml = "<li class='attr-relative'>" + 
					"<div class='attr-relative-title attr-relative collapse-header' data-id=''  data-parentId=''>" + 
						"<div class='icon-label attr-relative-dict attr-relative'>" + 
							"<i class='icon icon-attr-relative'></i><span class='text'>字典</span>" +
						"</div>" + 
						"<div class='label-bar attr-relative attr-subselection-child  al-save edit'>"+
								"<span id='bianhaospan' style='color: #363636;padding-right: 1em;' title='子集孩子编号'></span>"+
								"<select name='childId' class='abc-attr childId'>";
								 for (var nodeValue of dictList){
									 nodeHtml+="<option  title='"+nodeValue.id+"' value='"+nodeValue.id+"'>"+nodeValue.name+"</option>";
								 }
								 nodeHtml +="</select>"+
								 "<select name='status' class='abc-attr status'>"+
									"<option value='启用' selected>启用</option><option value='废弃'>废弃</option>"+			    			
				            	"</select>"+
				            	"<input type='text' style='width:65px;' class='edit-input text order' name='order' placeholder='排序'  title='排序' value=''>"+
							"<div class='btn-wrap'>" + 
								"<i class='icon icon-save'></i>" +
								/*"<i class='icon icon-add-abc abc'></i>" +*/
								"<i class='icon icon-trash'></i>" + 
								/*"<i class='icon icon-arrow-sm active'></i>" +*/
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
			} else if (data.code==200 && data.childList.length==0) {
				Dialog.notice("没有可选字典，请先添加！", "warning");
			} else {
				Dialog.notice("可选字典数据加载错误！", "error");
			}
			$CPF.closeLoading();
		});
    	
    }
	
    /**
     * 添加字典
     * @param el
     * @returns
     */
    function addDict(el) {
    	var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
        $CPF.showLoading();
        var nodeHtml = "<li class='attr-relative'>" + 
			"<div class='attr-relative-title attr-relative-dict attr-relative collapse-header' data-id=''  data-parentId=''>" + 
				"<div class='icon-label attr-relative-dict attr-relative'>" + 
					"<i class='icon icon-attr-relative'></i><span class='text'>字典</span>" +
				"</div>" + 
				"<div class='label-bar attr-relative attr-relative-save al-save edit'>"+
						"<span id='bianhaospan' style='color: #363636;padding-right: 1em;' title='编号'></span>"+
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
    function attrSubChildSave(el) {
    	var $relativeBar = $(el).closest(".label-bar");
    	var id = $(el).closest(".collapse-header").attr("data-id");
    	var subsectionId = $(el).closest(".collapse-content").siblings(".collapse-header").attr("data-id");
    	var childId = $relativeBar.children(".childId").find("option:selected").val();
    	var status = $relativeBar.children(".status").find("option:selected").val();
    	var order = $relativeBar.children(".order").val();
    	
    	var reg = /^[0-9]*$/;
    	if (!reg.test(order)) {
    		Dialog.notice("【排序】只能输入数字！", "warning");
    		return false;
    	}
    	
    	var dragWrapLen = $(".dragEdit-wrap").length + 1 ;    	
    	$CPF.showLoading();
    	Ajax.ajax('admin/cascadedict/cascadedictSubsection/saveOrUpSubChild', {
    		id:id,
    		subsectionId: subsectionId,
    		status: status,	
    		order: order,
    		childId:childId
		 }, function(data) {
			 	if (data.code == 200) {
			 		var creteria = data.creteria;
			    	$(el).closest(".collapse-header").attr("data-id", creteria.id);
			    	$(el).closest(".collapse-header").find(".label-bar").find("#bianhaospan").html(creteria.id);
			    	saveSuccess(el)
			    	
			 		Dialog.notice("保存成功！", "success");
			 	} else {
			 		Dialog.notice("保存失败！", "error");
			 	}
			 	 $CPF.closeLoading();
		 });
    };
    
    
    //字典保存修改方法
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
    
    
  //subselection 删除方法
    function subselectionDel(el) {
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
        	Ajax.ajax('admin/cascadedict/cascadedictSubsection/doDelte/' + id, {			
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
    function subChildDel(el) {
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
    	  var subselection =  $(this).closest(".label-bar").hasClass("attr-subselection");
    	  if (subselection) {//这里是加载subChild
    		  getSubChildByPid(parentId);
    		  return;
    	  }
    	   
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
        }else if(labelBar.hasClass("attr-subselection-child")) {
        	attrSubChildSave(this);
        }else if(labelBar.hasClass("attr-relative-save")) {
        	relativeSave(this);
        }else if(labelBar.hasClass("attr-subselection")) {
        	subselectionSave(this);
        }

    });
    
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
        if (labelBar.hasClass("attr-subselection")) {
        	subselectionDel(el);
        	return;
        }
        
        if (labelBar.hasClass("attr-subselection-child")) {
        	subChildDel(el);
        	return;
        }
        
        relativeDelete(el);
    })
    
    
})