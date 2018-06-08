
seajs.use(['dialog','utils', 'ajax', '$CPF'], function(Dialog, Utils, Ajax, $CPF){
	
	var $page = $("#operate");

	
	/**
     * 获取实体信息方法 示例     
     */
	function getEntity(entity) {		
		var cnName = $(entity).attr("data-cnname");		
		$("#operate .entity-title>.edit-input").val(cnName);
		$("#operate .entity-title>.entity-only-title").html(cnName);
		$("#operate .entity-edit-wrap").addClass("active");
	}
	
    /**
     * 跟实体添加页面弹出方法
     * @param {当前点击元素,dom对象} el 
     */
    function pop(el) {
//        var addTagLength = $(el).closest(".collapse-header")
//            .siblings(".collapse-content")
//            .children(".add-tag").length;
        var relativeLength = $(el).closest(".collapse-header")
            .siblings(".collapse-content")
            .children(".attr-relative").length;
        var html = "<ul class='card'>";        
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
        var wrapOffsetx = wrap.offset().left;
        var wrapOffsety = wrap.offset().top;
        var top = offsety - wrapOffsety + 30;
        var left = offsetx - wrapOffsetx - 90;
        var popHtml = $(html).appendTo(wrap);
        popHtml.css({
            "top": top,
            "left": left
        });
    };

    /**
     * 添加标签页面弹出方法
    */
    function popTag(el) {
        var $tag = $(el).closest(".label-bar.tag")
            .find(".tag-content").find("li:not(.icon)");
        var hasArray = [];
        for (var i = 0; i < $tag.length; i++) {
            hasArray.push($($tag[i]).children("span").text());
        };        
        $CPF.showLoading();
		Ajax.ajax('admin/node/basicItemNode/getCommLab', '', function(data) {
			var data = data.commLab;
			var html = "<ul class='tag-card'>";
			var has; //判断是否已经选中
			for(var i=0; i<data.length; i++) {
				has = false; //每次都重置
				for(var j=0; j<hasArray.length; j++) {
					if(hasArray[j] == data[i].name) {
						has = true;
						break;
					}
				};
				if(has) {
					 html += "<li class='tag-card-list'>" +
			            "<span class='tag-checkbox tag-checkbox-checked'>" +
			            "<input data-id='"+data[i].id+"' type='checkbox' class='tag-checkbox-input' value='"+data[i].name+"'>" +
			            "<span class='tag-checkbox-inner'></span>" +
			            "</span>" +
			            "<span>"+data[i].name+"</span>" +
			            "</li>" 
				}else {
					 html += "<li class='tag-card-list'>" +
			            "<span class='tag-checkbox'>" +
			            "<input data-id='"+data[i].id+"' type='checkbox' class='tag-checkbox-input' value='"+data[i].name+"'>" +
			            "<span class='tag-checkbox-inner'></span>" +
			            "</span>" +
			            "<span>"+data[i].name+"</span>" +
			            "</li>" 
				}				
			};
			html += "</ul>"
			var wrap = $("#operate");
		    var offsetx = $(el).offset().left;
		    var offsety = $(el).offset().top;
		    var wrapOffsetx = wrap.offset().left;
		    var wrapOffsety = wrap.offset().top;
		    var top = offsety - wrapOffsety + 25;
		    var left = offsetx - wrapOffsetx - 90;
		    var popHtml = $(html).appendTo(wrap);
		    popHtml.css({
		        "top": top,
		        "left": left
		     });
		    $CPF.closeLoading();
	    });		                
    };
    
    /**
     * 添加关系下标签页面弹出方法
    */
    function popRelativeTag(el) {
        var $tag = $(el).closest(".label-bar.tag")
            .find(".tag-content").find("li:not(.icon)");
        var hasArray = [];
        for (var i = 0; i < $tag.length; i++) {
            hasArray.push($($tag[i]).children("span").text());
        };     
        var leftRecordType = $(".entity_attr.active").attr("data-code");        
        var rightRecordType = $(el).closest(".label-bar.tag")
        						.closest(".collapse-content")
        						.prev(".collapse-header")
        						.find(".abc-attr")
        						.find("option:selected")
        						.attr("data-id");               
        $CPF.showLoading();
		Ajax.ajax('admin/node/basicItemNode/getLabRela', {
			leftRecordType: leftRecordType, 
			rightRecordType: rightRecordType
		}, function(data) {			
			var data = data.labRela;
			if(data.length == 0) {
				alert("需先在数据模型中添加关系");
			};
			var html = "<ul class='tag-card'>";
			var has; //判断是否已经选中
			for(var i=0; i<data.length; i++) {
				has = false; //每次都重置
				for(var j=0; j<hasArray.length; j++) {
					if(hasArray[j] == data[i].name) {
						has = true;
						break;
					}
				};
				if(has) {
					 html += "<li class='tag-card-list'>" +
			            "<span class='tag-checkbox tag-checkbox-checked'>" +
			            "<input data-id='"+data[i].typeCode+"' type='checkbox' class='tag-checkbox-input' value='"+data[i].name+"'>" +
			            "<span class='tag-checkbox-inner'></span>" +
			            "</span>" +
			            "<span>"+data[i].name+"</span>" +
			            "</li>" 
				}else {
					 html += "<li class='tag-card-list'>" +
			            "<span class='tag-checkbox'>" +
			            "<input data-id='"+data[i].typeCode+"' type='checkbox' class='tag-checkbox-input' value='"+data[i].name+"'>" +
			            "<span class='tag-checkbox-inner'></span>" +
			            "</span>" +
			            "<span>"+data[i].name+"</span>" +
			            "</li>" 
				}				
			};
			html += "</ul>"
			var wrap = $("#operate");
		    var offsetx = $(el).offset().left;
		    var offsety = $(el).offset().top;
		    var wrapOffsetx = wrap.offset().left;
		    var wrapOffsety = wrap.offset().top;
		    var top = offsety - wrapOffsety + 25;
		    var left = offsetx - wrapOffsetx - 90;
		    var popHtml = $(html).appendTo(wrap);
		    popHtml.css({
		        "top": top,
		        "left": left
		     });
		    $CPF.closeLoading();
	    });		                
    };

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

        var wrap = $("#operate");
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
     * 删除组别标签页弹出方法
      */
    function popGroupAttr(el) {
        var html = "<div class='delete-list-c'>" +
            "<p>" +
            "<i class='icon icon-mark'></i><span class='text'>确定同时删除组和组内内容?</span>" +
            "</p>" +
            "<div class='delete-list-btn'>" +
            "<span class='opera cancel'>取消</span>" +
            "<span class='opera confirm'>确认</span>" +
            "<span class='opera only-group'>仅删除组</span>" +
            "</div>" +
            "</div>"

        var wrap = $("#operate");
        var offsetx = $(el).offset().left;
        var offsety = $(el).offset().top;
        var wrapOffsetx = wrap.offset().left;
        var wrapOffsety = wrap.offset().top;
        var top = offsety - wrapOffsety - 124;
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
        $(".icon-trash").removeClass("active");
        $(".icon-trash-sm").removeClass("active");

    };

    //标签页标签ul长度设置
    function menuWidth(ul) {
        var menuW = 0;
        var $li = $(ul).children("li");
        for (var i = 0; i < $li.length; i++) {
            menuW += parseFloat($($li[i]).css("width")) + 8;
        }
        if(menuW == 0){
        	menuW = "auto"
        }
        $(ul).width(menuW);
    }

    //标签删除的方法
    function tagRemoveTag(el, text) {
        var $tagUl = $(el).closest(".label-bar.tag")
            .find(".tag-content")
            .children("ul");
        var $li = $tagUl.children("li");
        for (var i = 0; i < $li.length; i++) {
            if ($($li[i]).children("span").text() == text) {
                $($li[i]).remove();
                menuWidth($tagUl[0]);
                return;
            }
        }
    }

    //标签添加上去的方法
    function tagAddTag(el, text, id) {
        var $tagUl = $(el).closest(".label-bar.tag")
            .find(".tag-content")
            .children("ul");
        var html = "<li data-id='" + id + "' data-text='" + text + "'>" +
            "<span>" + text + "</span>" +
            "<i class='icon icon-delete'></i>" +
            "</li>"
        $tagUl.append(html);
        menuWidth($tagUl[0]);
    }

    //标签两边箭头的展现和隐藏
    function judegArrow(ul) {
        var ulWidth = parseFloat($(ul).width());
        var wrapWidth = parseFloat($(ul).parent(".tag-content").width());
        if (ulWidth > wrapWidth) {
            $(ul).closest(".label-bar.tag").find(".icon-toleft")
                .addClass("active");
            $(ul).closest(".label-bar.tag").find(".icon-toright")
                .addClass("active");
        } else {
            $(ul).closest(".label-bar.tag").find(".icon-toleft")
                .removeClass("active");
            $(ul).closest(".label-bar.tag").find(".icon-toright")
                .removeClass("active");
        }
    }

    /**
     * 添加标签方法
     * @param {当前点击元素对应的加号} el
      */
    function addTag(el) {
        var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        var tagHtml = "<li class='add-tag clear-fix'>" +
            "<div class='icon-label tag'>" +
            "<i class='icon icon-tag'></i>" +
            "<span class='text'>标签</span>" +
            "</div>" +
            "<div class='label-bar tag edit' data-order='' data-id=''>" +
            "<input type='text' class='edit-input' value='标签名称'>" +
            "<span class='icon icon-toleft'></span>" +
            "<div class='tag-content'>" +
            "<ul class='clear-fix'>" +
            "</ul>" +
            "</div>" +
            "<span class='icon icon-toright ban'></span>" +
            "<div class='btn-wrap'>" +
            "<i class='icon tag icon-save'></i>" +
            "<i class='icon tag icon-add-tag'></i>" +
            "<i class='icon-simulate-trashsm'></i>" +
            "</div>" +
            "</div>" +
            "</li>"
        $content.prepend(tagHtml);
    };

    /**
     * 添加属性方法
      */
    function addAttr(el) {
        var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        var entityId = $(".entity_attr", $page).attr("data-code");
        $CPF.showLoading();
		Ajax.ajax('admin/node/basicItemNode/getComm?entityId', {
			entityId: entityId
		}, function(data) {			
			var data = data.comm;
			var attrHtml = "<li class='add-attr clear-fix'>" +
            "<div class='icon-label attr' data-order='' data-id=''>" +
            "<i class='icon icon-attr'></i>" +
            "<span class='text'>属性</span>" +
            "</div>" +
            "<div class='label-bar attr edit' data-order='' data-id=''>" +
            "<input type='text' class='edit-input' value='属性名'>" +
            "<select class='abc-attr'>"            
            for(var i=0; i<data.length; i++) {
            	attrHtml += "<option data-id='"+data[i][0]+"' value='"+data[i][1]+"'>"+data[i][1]+"</option>";                
            }
			attrHtml += "</select>";
			attrHtml += "<select class='data-type'>";            
		    Ajax.ajax('admin/node/basicItemNode/getDataType', '', function(data){		    	
		    	var data = data.dataType;
		    	for(var i=0; i<data.length; i++) {
	            	attrHtml += "<option value='"+data[i]+"'>"+data[i]+"</option>";          
	            };
	            attrHtml += "</select>";
				attrHtml += "<select class='node-ops-type'>";
				Ajax.ajax('admin/node/basicItemNode/getNodeOpsType', '', function(data){		    	
			    	var data = data.nodeOpsType;
			    	for(var i=0; i<data.length; i++) {
		            	attrHtml += "<option value='"+data[i]+"'>"+data[i]+"</option>";          
		            };
		            attrHtml += "</select>";
		            attrHtml += "<div class='btn-wrap'>" +
		            "<i class='icon icon-save'></i>" +
		            "<i class='icon icon-trash-sm'></i>" +
		            "<i class='icon-simulate-trashsm'></i>" +
		            "</div>" +
		            "</div>" +
		            "</li>";
		            $content.prepend(attrHtml);
		            $CPF.closeLoading();
			    })
			    
		    })
	    });		                      
    };

    /**
     * 添加属性组方法
      */
    function addGroup(el) {
        var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        var attrGroupHtml = "<li class='attr-group'>" +
            "<div class='attr-group-title collapse-header' data-order='' data-id=''>" +
            "<div class='icon-label attr-group'>" +
            "<i class='icon icon-attr-group'></i>" +
            "<span class='text'>属性组</span>" +
            "</div>" +
            "<div class='label-bar attr-group edit'>" +
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
        var entityId = $(".entity_attr", $page).attr("data-code");
        $CPF.showLoading();
		Ajax.ajax('admin/node/basicItemNode/getComm?entityId', {
			entityId: entityId
		}, function(data) {			
			var data = data.comm;			
            var moreAttrHtml = "<li class='more-attr clear-fix'>" +
            "<div class='more-attr-title collapse-header' data-order='' data-id=''>" +
            "<div class='icon-label more-attr'>" +
            "<i class='icon icon-more-attr'></i>" +
            "<span class='text'>多值属性</span>" +
            "</div>" +
            "<div class='label-bar more-attr edit'>" +
            "<input type='text' class='edit-input' value='多值属性名称'>" +
            "<select class='abc-attr'>"
            for(var i=0; i<data.length; i++) {
            	moreAttrHtml += "<option data-id='"+data[i][0]+"' value='"+data[i][1]+"'>"+data[i][1]+"</option>";                
            }
            moreAttrHtml += "</select>";
            moreAttrHtml += "<select class='data-type'>";            
		    Ajax.ajax('admin/node/basicItemNode/getDataType', '', function(data){		    	
		    	var data = data.dataType;
		    	for(var i=0; i<data.length; i++) {
		    		moreAttrHtml += "<option value='"+data[i]+"'>"+data[i]+"</option>";          
	            };
	            moreAttrHtml += "</select>";
	            moreAttrHtml += "<select class='node-ops-type'>";
				Ajax.ajax('admin/node/basicItemNode/getNodeOpsType', '', function(data){		    	
			    	var data = data.nodeOpsType;
			    	for(var i=0; i<data.length; i++) {
			    		moreAttrHtml += "<option value='"+data[i]+"'>"+data[i]+"</option>";          
		            };
		            moreAttrHtml += "</select>";
		            moreAttrHtml += "<div class='btn-wrap'>" +
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
		            $CPF.closeLoading();
			    })
			    
		    })
	    });                                       
    };

    /**
     * 添加关系方法
      */
    function addRelative(el) {
        var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        var entityId = $(".entity_attr", $page).attr("data-code");
        $CPF.showLoading();
		Ajax.ajax('admin/node/basicItemNode/getComm?entityId', {
			entityId: entityId
		}, function(data) {			
			var data = data.comm;			            
            var relativeHtml = "<li class='attr-relative'>" +
            "<div class='attr-relative-title collapse-header' data-order='' data-id=''>" +
            "<div class='icon-label attr-relative'>" +
            "<i class='icon icon-attr-relative'></i><span class='text'>关系</span>" +
            "</div>" +
            "<div class='label-bar attr-relative edit'>" +
            "<input type='text' class='edit-input' value='关系名称'>" +
            "<select class='abc-attr'>"
            for(var i=0; i<data.length; i++) {
            	relativeHtml += "<option data-id='"+data[i][0]+"' value='"+data[i][1]+"'>"+data[i][1]+"</option>";                
            }
            relativeHtml += "</select>" +
            "<div class='btn-wrap'>" +
            "<i class='icon icon-save'></i>" +            
            "<i class='icon icon-arrow-sm'></i>" +
            "</div>" +
            "</div>" +
            "</div>" +            
            "<ul class='attr-relative-drag-wrap collapse-content collapse-content-active'>" +
            "</ul>" +
            "</li>";         
		    $content.prepend(relativeHtml);
		    $CPF.closeLoading();			    
	    });                                 
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

    //提醒有未保存的节点
    function judgeSave() {
        var editBar = $("#operate").find(".label-bar.edit");
        var editEntity = $("#operate").find(".entity-edit-wrap.edit");
        if(editBar.length > 0 || editEntity.length > 0) {
            alert("请先保存正在编辑的节点");
            return true;
        }
    }
    
    //根实体保存修改方法
    function entitySave(el) {    	
    	var $entityTitle = $(el).closest(".entity-title");    	
    	var type = 1;
    	var name = $entityTitle.children(".edit-input").val();
    	var abcattr = $(".entity_attr.active", $page).attr("data-cnName");
    	var abcattrCode = $(".entity_attr.active", $page).attr("data-code");
    	var order = $entityTitle.attr("data-order");
    	var id = $entityTitle.attr("data-id")
    	var dataType = "STRING";
    	var opt = 2; 
    	$CPF.showLoading();
		Ajax.ajax(' admin/node/basicItemNode/saveOrUpdate', {
			 type: type,
			 name: name,
			 abcattr: abcattr,
			 abcattrCode: abcattrCode,
			 dataType: dataType,
			 opt: opt,
			 order: order
		 }, function(data) {
			 var data = data.node;
			 //设置跟实体的order和id
			 var order = data.order;
			 var id = data.id;
			 $entityTitle.attr("data-order",order)
			 	.attr("data-id", id);
			 $CPF.closeLoading();
	    });
    }
    
    //标签保存修改方法
    function tagSave(el) {
    	var $tagBar = $(el).closest(".label-bar");
    	var type = 3;
    	var dataType = "STRING";
    	var opt = 2;
    	var name = $tagBar.children(".edit-input").val();    	
    	var order = $tagBar.attr("data-order");
    	var id = $tagBar.attr("data-id");
    	var parentId = $tagBar.closest(".collapse-content").prev(".collapse-header")
    						.attr("data-id"); 
    	var abcattr = [];
    	var abcattrCode = [];
    	var $tags = $tagBar.find(".tag-content").children("ul").children("li");
    	for(var i=0; i<$tags.length; i++) {
    		abcattrCode.push($($tags[i]).attr("data-id"));
    		abcattr.push($($tags[i]).attr("data-text"));
    	}
    	abcattr = abcattr.join(",");
    	abcattrCode = abcattrCode.join(",");
    	$CPF.showLoading();
    	Ajax.ajax(' admin/node/basicItemNode/saveOrUpdate', {
			 type: type,
			 name: name,
			 abcattr: abcattr,
			 abcattrCode: abcattrCode,
			 dataType: dataType,
			 opt: opt,
			 order: order,
			 parentId: parentId,
			 id: id
		 }, function(data) {
			 var data = data.node;
			 //设置当前节点order和id
			 var order = data.order;
			 var id = data.id;
			 $tagBar.attr("data-order",order)
			 	.attr("data-id", id);
			 $CPF.closeLoading();
		});
    };
    
    //属性保存修改方法
    function attrSave(el) {
    	var $attrBar = $(el).closest(".label-bar");
    	var type = 2;
    	var dataType = $attrBar.children(".data-type").find("option:selected").val();
    	var opt = $attrBar.children(".node-ops-type").find("option:selected").val();
    	var name = $attrBar.children(".edit-input").val();    	
    	var order = $attrBar.attr("data-order");
    	var id = $attrBar.attr("data-id");
    	var parentId = $attrBar.closest(".collapse-content").prev(".collapse-header")
    						.attr("data-id"); 
    	var abcattr = $attrBar.children(".abc-attr").find("option:selected").val();
    	var abcattrCode = $attrBar.children(".abc-attr").find("option:selected").attr("data-id");
    	switch (opt) {
	        case "读":
	            opt = 1;
	            break;
	        case "写":
	            opt = 2;
	            break;
	        case "补":
	            opt = 3;
	            break;
	        case "增":
	            opt = 4;
	            break;
	        case "并":
	            opt = 5;
	            break;
	        default:
	            break;
	    }
    	$CPF.showLoading();
    	Ajax.ajax('admin/node/basicItemNode/saveOrUpdate', {
			 type: type,
			 name: name,
			 abcattr: abcattr,
			 abcattrCode: abcattrCode,
			 dataType: dataType,
			 opt: opt,
			 order: order,
			 parentId: parentId,
			 id: id
		 }, function(data) {
			 var data = data.node;
			 //设置当前节点order和id
			 var order = data.order;
			 var id = data.id;
			 $attrBar.attr("data-order",order)
			 	.attr("data-id", id);
			 $CPF.closeLoading();
		});
    };
    
    //属性组保存修改方法
    function attrGroupSave(el) {
    	var $attrGroupBar = $(el).closest(".label-bar");
    	var type = 6;    	
    	var name = $attrGroupBar.children(".edit-input").val();    	
    	var order = $attrGroupBar.closest(".collapse-header").attr("data-order");
    	var opt = 2;
    	var id = $attrGroupBar.closest(".collapse-header").attr("data-id");
    	var parentId = $attrGroupBar.closest(".collapse-content").prev(".collapse-header")
    						.attr("data-id"); 
    	        	
    	$CPF.showLoading();
    	Ajax.ajax('admin/node/basicItemNode/saveOrUpdate', {
			 type: type,
			 name: name,			
			 order: order,
			 parentId: parentId,
			 dataType: "STRING",
			 abcattr: name,			 
			 opt: opt,
			 id: id
		 }, function(data) {
			 var data = data.node;
			 //设置当前节点order和id
			 var order = data.order;
			 var id = data.id;
			 $attrGroupBar.closest(".collapse-header")
			 	.attr("data-order",order)
			 	.attr("data-id", id);
			 $CPF.closeLoading();
		});
    };
    
    //多值属性保存修改方法
    function moreAttrSave(el) {
    	var $moreAttrBar = $(el).closest(".label-bar");
    	var type = 4;
    	var dataType = $moreAttrBar.children(".data-type").find("option:selected").val();
    	var opt = $moreAttrBar.children(".node-ops-type").find("option:selected").val();
    	var name = $moreAttrBar.children(".edit-input").val();    	
    	var order = $moreAttrBar.closest(".collapse-header").attr("data-order");
    	var id = $moreAttrBar.closest(".collapse-header").attr("data-id");
    	var parentId = $moreAttrBar.closest(".collapse-content").prev(".collapse-header")
    						.attr("data-id"); 
    	var abcattr = $moreAttrBar.children(".abc-attr").find("option:selected").val();
    	var abcattrCode = $moreAttrBar.children(".abc-attr").find("option:selected").attr("data-id");    	  
    	switch (opt) {
	        case "读":
	            opt = 1;
	            break;
	        case "写":
	            opt = 2;
	            break;
	        case "补":
	            opt = 3;
	            break;
	        case "增":
	            opt = 4;
	            break;
	        case "并":
	            opt = 5;
	            break;
	        default:
	            break;
	    }
    	$CPF.showLoading();
    	Ajax.ajax('admin/node/basicItemNode/saveOrUpdate', {
			 type: type,
			 name: name,
			 abcattr: abcattr,
			 abcattrCode: abcattrCode,
			 dataType: dataType,
			 opt: opt,
			 order: order,
			 parentId: parentId,
			 id: id
		 }, function(data) {
			 var data = data.node;
			 //设置当前节点order和id
			 var order = data.order;
			 var id = data.id;
			 $moreAttrBar.closest(".collapse-header")
			 	.attr("data-order",order)
			 	.attr("data-id", id);
			 $CPF.closeLoading();
		});
    };
    
    //关系保存修改方法
    function relativeSave(el) {
    	var $relativeBar = $(el).closest(".label-bar");
    	var type = 5;
    	var dataType = "STRING";
    	var opt = 2;
    	var name = $relativeBar.children(".edit-input").val();  
    	var abcattr = $relativeBar.children(".abc-attr").find("option:selected").val();
    	var abcattrCode = $relativeBar.children(".abc-attr").find("option:selected").attr("data-id");
    	var order = $relativeBar.closest(".collapse-header").attr("data-order");
    	var id = $relativeBar.closest(".collapse-header").attr("data-id");
    	var parentId = $relativeBar.closest(".collapse-content").prev(".collapse-header")
    						.attr("data-id");     	    	 
    	
    	$CPF.showLoading();
    	Ajax.ajax('admin/node/basicItemNode/saveOrUpdate', {
			 type: type,
			 name: name,
			 abcattr: abcattr,	
			 abcattrCode: abcattrCode,
			 dataType: dataType,
			 opt: opt,
			 order: order,
			 parentId: parentId,
			 id: id
		 }, function(data) {
			 var data = data.node;
			 //设置当前节点order和id
			 var order = data.order;
			 var id = data.id;
			 $relativeBar.closest(".collapse-header")
			 	.attr("data-order",order)
			 	.attr("data-id", id);
			 
			 //展现出关系下的标签和abc HTML			 
			 var html = "<li class='add-tag clear-fix'>" +
	            "<div class='icon-label tag'>" +
	            "<i class='icon icon-tag'></i>" +
	            "<span class='text'>标签</span>" +
	            "</div>" +
	            "<div class='label-bar tag edit' data-order='' data-id=''>" +
	            "<input type='text' class='edit-input' value='标签名称'>" +
	            "<span class='icon icon-toleft'></span>" +
	            "<div class='tag-content'>" +
	            "<ul class='clear-fix'>" +
	            "</ul>" +
	            "</div>" +
	            "<span class='icon icon-toright ban'></span>" +
	            "<div class='btn-wrap'>" +
	            "<i class='icon tag icon-save'></i>" +
	            "<i class='icon tag icon-add-tag-relative'></i>" +
	            "<i class='icon-simulate-trashsm'></i>" +
	            "</div>" +
	            "</div>" +
	            "</li>" +
	            "<li class='entity-ch-wrap'>" +
	            "<div class='attr-abc-title collapse-header' data-order='' data-id=''>" +
	            "<div class='icon-label abc'>" +
	            "<i class='icon icon-abc'></i><span class='text'>ABC</span>" +
	            "</div>" +
	            "<div class='label-bar abc edit'>" +
	            "<input class='edit-input' value='"+abcattr+"'>"+
	            "<span class='entity-only-title' data-abcattr-code='"+abcattrCode+"' data-abcattr='"+abcattr+"'>"+abcattr+"</span>"+
	            "<div class='btn-wrap'>" +
	            "<i class='icon icon-save'></i>" +
	            "<i class='icon icon-add-abc'></i>" +
	            "<i class='icon icon-trash-sm'></i>" +
	            "<i class='icon icon-arrow-sm'></i>" +
	            "</div>" +
	            "</div>" +
	            "</div>" +
	            "<ul class='drag-wrap-repeat collapse-content collapse-content-active'>" +
	            "</ul>" +
	            "</li>"				            
	          $relativeBar.parent(".collapse-header").next(".collapse-content").append(html);
			  $CPF.closeLoading();
		});
    };
    
    //abc属性保存修改方法
    function abcSave(el) {
    	var $abcBar = $(el).closest(".label-bar");
    	var type = 1;
    	var dataType = "STRING";
    	var opt = 2;
    	var name = $abcBar.children(".edit-input").val();    	
    	var order = $abcBar.closest(".collapse-header").attr("data-order");
    	var id = $abcBar.closest(".collapse-header").attr("data-id");
    	var parentId = $abcBar.closest(".collapse-content").prev(".collapse-header")
    						.attr("data-id"); 
    	var abcattr = $abcBar.children(".entity-only-title").attr("data-abcattr");    	
    	var abcattrCode = $abcBar.children(".entity-only-title").attr("data-abcattr-code");    	  
    	
    	$CPF.showLoading();
    	Ajax.ajax('admin/node/basicItemNode/saveOrUpdate', {
			 type: type,
			 name: name,
			 abcattr: abcattr,
			 abcattrCode: abcattrCode,
			 dataType: dataType,
			 opt: opt,
			 order: order,
			 parentId: parentId,
			 id: id
		 }, function(data) {
			 var data = data.node;
			 //设置当前节点order和id
			 var order = data.order;
			 var id = data.id;
			 $abcBar.closest(".collapse-header")
			 	.attr("data-order",order)
			 	.attr("data-id", id);
			 $CPF.closeLoading();
		});
    };
    
    //删除的请求方法
    function deleteAjax(id, boolean, callback) {
    	$CPF.showLoading();
    	Ajax.ajax('admin/node/basicItemNode/do_delete', {			
			 id: id,
			 isDelChil: boolean
		 }, function(data) {
			 console.log(data);
			 callback();
			 $CPF.closeLoading();
		});
    };
    
    //跟实体删除方法
    
    //跟实体删除方法
    function entityDelete(el) {
    	var $entityTitle = $(el).closest(".label-bar");
    	var id = $entityTitle.attr("data-id");
    	var isDelChil = true;
    	var callback = function() {
    		$entityTitle.next(".collapse-content").html();
    		$entityTitle.parent(".entity-edit-wrap")
    			.removeClass("active")
    			.addClass("edit");
    	};
    	deleteAjax(id, isDelChil, callback);
    }
    
    //属性删除方法
    function attrDelete(el) {
    	var $attrBar = $(el).closest(".label-bar");
    	var id = $attrBar.attr("data-id");
    	var isDelChil = false;
    	var callback = function() {
    		$attrBar.parent(".add-attr").remove();    		
    	};
    	deleteAjax(id, isDelChil, callback);
    }
    
    //属性组删除方法
    function attrDelete(el, isDelChil) {
    	var $attrGroupBar = $(el).closest(".label-bar");
    	var id = $attrGroupBar.closest(".collapse-header").attr("data-id");
    	var isDelChil = isDelChil;
    	var callback = function() {
    		$attrGroupBar.closest(".attr-group").remove();    		
    	};
    	deleteAjax(id, isDelChil, callback);
    };
    
    //多值属性删除方法
    function moreAttrDelete(el, isDelChil) {
    	var $moreAttrBar = $(el).closest(".label-bar");
    	var id = $attrGroupBar.closest(".collapse-header").attr("data-id");
    	var isDelChil = isDelChil;
    	var callback = function() {
    		$moreAttrBar.closest(".more-attr").remove();    		
    	};
    	deleteAjax(id, isDelChil, callback);
    };   
    
    //关系删除方法
    function relativeDelete(el) {
    	var $relativeBar = $(el).closest(".label-bar");
    	var id = $attrGroupBar.closest(".collapse-header").attr("data-id");
    	var isDelChil = isDelChil;
    	var callback = function() {
    		$moreAttrBar.closest(".more-attr").remove();    		
    	};
    	deleteAjax(id, isDelChil, callback);
    };   
        

    $page.on("click", function () {    	
        removePop();
    });
    

    //input事件绑定
    $(".edit-input").on("blur", function () {
        //传输给后台                  
    });

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
        var hasSave = judgeSave();
        if(hasSave){
            return;
        }
        removePop();
        pop(this);
        $(this).addClass("active")
    });

    //属性组 多值属性 添加事件绑定
    $("#operate").on("click", ".icon-add-sm", function (e) {
        e.stopPropagation();
        var hasSave = judgeSave();
        if(hasSave){
            return;
        }
        removePop();
        popsm(this);
        $(this).addClass("active")
    });

    //标签添加
    $("#operate").on("click", ".icon-add-tag", function (e) {
        e.stopPropagation();
        $(this).closest(".label-bar.tag").addClass("edit");
        removePop();
        popTag(this);
        $(this).addClass("active");
    });
    
    //关系下标签添加
    $("#operate").on("click", ".icon-add-tag-relative", function (e) {
        e.stopPropagation();
        $(this).closest(".label-bar.tag").addClass("edit");
        removePop();
        popRelativeTag(this);
        $(this).addClass("active");
    });


    //删除属性事件绑定
    $("#operate").on("click", ".icon-trash, .icon-trash-sm", function (e) {
        e.stopPropagation();
        removePop();
        var $header = $(this).closest(".attr-group");
        if ($header.length > 0) { //delete-list-c
            popGroupAttr(this);
        } else { //delete-list
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
        } else if ($("#operate").find(".icon-add-abc.active").length > 0) {
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
        var el = $("#operate").find(".icon-add-tag.active")[0];
        var text = $(this).val();
        var id = $(this).attr("data-id");
        var ul = $(el).closest(".label-bar.tag").find("ul");
        var $parent = $(this).parent(".tag-checkbox");
        if ($parent.hasClass("tag-checkbox-checked")) {
            $parent.removeClass("tag-checkbox-checked");
            tagRemoveTag(el, text);
        } else {
            $parent.addClass("tag-checkbox-checked");
            tagAddTag(el, text, id);
        };
        judegArrow(ul)
    });

    //标签向左移动事件绑定
    $("#operate").on("click", ".icon-toleft", function () {
        if ($(this).hasClass("ban")) {
            return;
        }
        var $ul = $(this).next(".tag-content").children("ul");
        var marginLeft = parseFloat($ul.css("marginLeft"));
        var ulWidth = parseFloat($ul.css("width"));
        var wrapWidth = parseFloat($ul.parent(".tag-content").css("width"));
        $("#operate").find(".icon-toright").removeClass("ban");
        if (ulWidth - wrapWidth + marginLeft < 80) {
            marginLeft = wrapWidth - ulWidth;
            $(this).addClass("ban");
        } else {
            marginLeft = marginLeft - 80;
        }
        $ul.css("marginLeft", marginLeft);
    })

    //标签向右移动事件绑定
    $("#operate").on("click", ".icon-toright", function () {
        if ($(this).hasClass("ban")) {
            return;
        }
        var $ul = $(this).prev(".tag-content").children("ul");
        var marginLeft = parseFloat($ul.css("marginLeft"));
        $("#operate").find(".icon-toleft").removeClass("ban");
        if (marginLeft > -80) {
            marginLeft = 0;
            $(this).addClass("ban");
        } else {
            marginLeft = marginLeft + 80;
        }
        $ul.css("marginLeft", marginLeft);
    })
    
    //标签删除图标点击事件绑定
    $("#operate").on("click", ".tag-content .icon-delete", function(){
    	$(this).parent("li").remove();
    })

    //删除数据弹出页中的事件绑定
    $("#operate").on("click", ".delete-list .opera, .delete-list-c .opera", function (e) {
        e.stopPropagation();
        if ($("#operate").find(".icon-trash-sm.active").length > 0) {
            var el = $("#operate").find(".icon-trash-sm.active")[0];
        } else if ($("#operate").find(".con-trash.active").length > 0) {
            var el = $("#operate").find(".icon-trash.active")[0];
        }
        if ($(this).hasClass("cancel")) {
            removePop();
        } else if ($(this).hasClass("confirm")) {
            var $header = $(el).closest(".collapse-header");
            if ($header.length > 0) {
                confirmDeleteAll(el);
            } else {
                confirmDeleteSingle(el);
            }
        } else if ($(this).hasClass("only-group")) {
            confirmDeleteOnly(el);
        } else {
            removePop();
        }

    })

    //双击编辑
    $("#operate").on("dblclick", ".label-bar", function(){
    	var hasSave = judgeSave();
    	if(hasSave){
    		return;
    	}
        $(this).addClass("edit");
    })

    //保存
    $("#operate").on("click", ".icon-save", function() {
        $(this).closest(".label-bar").removeClass("edit");
        $(this).closest(".entity-edit-wrap").removeClass("edit");
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
    
    //删除
    $("#operate").on("click", ".icon-trash, .icon-trash-sm", function() {        
        var entityTitle = $(this).closest(".entity-title");
        var labelBar = $(this).closest(".label-bar");
        if(entityTitle.length > 0) {
        	
        	return;
        }
        if(labelBar.hasClass("tag")) {   
        	
        }else if(labelBar.hasClass("attr")) {
        	
        	
        }else if(labelBar.hasClass("more-attr")) {
        	;
        }else if(labelBar.hasClass("attr-group")) {
        	
        }else if(labelBar.hasClass("attr-relative")) {
        	;
        }else if(labelBar.hasClass("abc")) {
        	
        }
    })
    
    //实体选择点击事件绑定
    $("#operate").on("click", ".entity_attr", function() {
    	var $attrArray = $(".entity_attr",$page);
    	for(var i=0; i<$attrArray.length; i++) {
    		if($($attrArray[i]).hasClass("active")) { //已经有选择过的了 就不能再点击选择了
    			return;
    		}
    	}    	
    	$(this).addClass("active");    		
    	getEntity(this);
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