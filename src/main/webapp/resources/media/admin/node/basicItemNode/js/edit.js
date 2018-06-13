
seajs.use(['dialog','utils', 'ajax', '$CPF'], function(Dialog, Utils, Ajax, $CPF){
	
	var $page = $("#operateEdit");	
	var nodeId = $(".entity-title", $page).attr("data-id");	
	function addUnfold(el) {
		if($(el).hasClass("icon-add") && $(el).siblings(".icon-arrow").hasClass("active")) {
        	$(el).siblings(".icon-arrow").trigger("click");
        }else if($(el).hasClass("icon-add-sm") && $(el).siblings(".icon-arrow-sm").hasClass("active")){
        	$(el).siblings(".icon-arrow-sm").trigger("click");
        }
	} 
	function saveSuccess(el) {
		 $(el).closest(".label-bar").removeClass("edit");
	     $(el).closest(".entity-edit-wrap").removeClass("edit");
	     $(el).closest(".label-bar").find(".edit-input").attr("disabled", "true");
	     $(el).closest(".label-bar").find("select").attr("disabled", "true");
	     $(el).closest(".label-bar").addClass("al-save");
	}
	
    //获取孩子的方法
	function getChild(nodeId, isRelative, bar) {
		$CPF.showLoading();
		Ajax.ajax('admin/node/basicItemNode/getChildNode', {
			nodeId: nodeId
		 }, function(data) {	
			 var data = data.childNode;			
			 var parentId = nodeId;
			 var parent = $(".collapse-header[data-id='"+nodeId+"']", $page).next(".collapse-content")[0];			 
			 $(parent).removeClass("need-ajax");			 
			 for(var i=0; i<data.length; i++) {					 
				 var abcattr = data[i].abcattr;
				 var subdomain = data[i].subdomain;
				 var abcattr_code = data[i].abcattrCode;
				 var dataType = data[i].dataType;
				 var id = data[i].id;
				 var name = data[i].name;
				 var opt = data[i].opt;
				 var order = data[i].order;		
				 if(data[i].type == 1) {
					 initAbc(abcattr, abcattr_code, id, name, order, parent);
				 }else if(data[i].type == 2) {				 
					 initAttr(abcattr,dataType,id,name,opt,order,parent);
				 }else if(data[i].type == 3) {					 
					 initTag(subdomain,id,name,order,parent);
				 }else if(data[i].type == 4) {
					 initMoreAttr(abcattr,dataType,id,name,opt,order,parent);
				 }else if(data[i].type == 5) {
					 initRelative(abcattr, id, name, order, parent);
				 }else if(data[i].type == 6) {
					 initGroup(id, name, order, parent);
				 }	
				 $("select", $page).css({"width":"15%","marginLeft":"16px"}).select2();
			 }	
			 if(data.length === 0 && isRelative === true) {
				 addRelativeChildren(bar);
			 }
			 $CPF.closeLoading();
	    }, {async: false});	 
	}
	
	function addRelativeChildren(bar) {
		 var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
		 var abcattr = $(bar).find("select.abc-attr").children("option:selected").attr("value");
		 var abcattrCode = $(bar).find("select.abc-attr").children("option:selected").attr("data-id");
		 var chLength = $(".entity-ch-wrap", $page).length;
		 var nest = "no-repeat";
		 if(chLength >= 2) {
			 nest = "repeat"
		 }
		 //展现出关系下的标签和abc HTML			 
		 var html = "<li class='add-tag clear-fix'>" +
           "<div class='icon-label tag'>" +
           "<i class='icon icon-tag'></i>" +
           "<span class='text'>标签</span>" +
           "</div>" +
           "<div class='label-bar tag edit' data-order='' data-id=''>" +
           "<input type='text' class='edit-input text' value='标签名称'>" +
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
           "<li class='entity-ch-wrap "+nest+"'>" +
           "<div class='attr-abc-title collapse-header' data-order='' data-id=''>" +
           "<div class='icon-label abc'>" +
           "<i class='icon icon-abc'></i><span class='text'>ABC</span>" +
           "</div>" +
           "<div class='label-bar abc edit'>" +
           "<input class='edit-input text' value='"+abcattr+"'>"+
           "<span class='entity-only-title' data-abcattr-code='"+abcattrCode+"' data-abcattr='"+abcattr+"'>"+abcattr+"</span>"+
           "<div class='btn-wrap'>" +
           "<i class='icon icon-save'></i>" +
           "<i class='icon icon-add-abc group'></i>" +	            
           "<i class='icon icon-arrow-sm'></i>" +
           "</div>" +
           "</div>" +
           "</div>" +
           "<ul class='drag-wrap-repeat dragEdit-wrap collapse-content collapse-content-active' id='dragEdit-"+dragWrapLen+"'>" +
           "</ul>" +
           "</li>"	      
           var $content = $(bar).closest(".collapse-header").next(".collapse-content");
           var $html = $(html).appendTo($content);
		   $html.find("select").css({"width":"15%","marginLeft":"16px"}).select2();
		   drag($(".dragEdit-wrap").length);
	};
    
	 //拖拽排序方法
    function drag(length) {
    	
    	var dragWrap = document.getElementById("dragEdit-"+length); 
		var name = "dragEdit-"+length;		
    	Sortable.create(dragWrap, {
	        group: {
	            name: name,
	            pull: false,
	            put: false
	        },
	        filter: ".no-dragger",	
	        handle: ".icon-label",
	        sort: true,
	        animation: 100,
	        onStart: function (evt) {
//	        	judgeSave();
	        },
	        onEnd: function (evt) {
	        	var current = $(evt.item);
	        	var before = $(evt.item).prev("li");
	        	var after = $(evt.item).next("li");
	        	var currentId = "";
	        	var beforeId = "";
	        	var afterId = "";	        	
	        	if(before.length == 0) {	        		
	        		beforeId = "";
	        	}else if(before.hasClass("attr-group") || before.hasClass("more-attr") || before.hasClass("attr-relative") || before.hasClass("entity-ch-wrap")){	        			        		
	        		beforeId = before.children(".collapse-header").attr("data-id");
	        	}else {	        		
	        		beforeId = before.children(".label-bar").attr("data-id");
	        	}
	        	
	        	if(after.length == 0) {	        		
	        		afterId = "";
	        	}else if(after.hasClass("attr-group") || after.hasClass("more-attr") || after.hasClass("attr-relative") || after.hasClass("entity-ch-wrap")){
	        		afterId = after.children(".collapse-header").attr("data-id");
	        	}else {
	        		afterId = after.children(".label-bar").attr("data-id");
	        	}
	        	
	        	if(current.hasClass("attr-group") || current.hasClass("more-attr") || current.hasClass("attr-relative") || current.hasClass("attr-abc")){	        			        		
	        		currentId = current.children(".collapse-header").attr("data-id");
	        	}else {	        		
	        		currentId = current.children(".label-bar").attr("data-id");
	        	}
	        	$CPF.showLoading();
	    		Ajax.ajax('admin/node/basicItemNode/nodeSort', {
	    			currentId: currentId,
	    			beforeId: beforeId,
	    			afterId: afterId
	    		 }, function(data) {
	    			 console.log(data);
	    			 $CPF.closeLoading();
	    	    });	        	
	        }
	    });     
    };
    
    drag($(".dragEdit-wrap", $page).length);       
    getChild(nodeId, false);  //直接执行
    $(".label-bar", $page).addClass("al-save");
    //abc初始化方法
    function initAbc(abcattr, abcattr_code, id, name, order, parent) {
    	var dragWrapLen = $(".dragEdit-wrap", $page).length + 1 ;
    	var chLength = $(".entity-ch-wrap", $page).length;
		 var nest = "no-repeat";
		 if(chLength >= 2) {
			 nest = "repeat"
		 }
    	var abcHtml = "<li class='entity-ch-wrap "+nest+"'>" +
			        "<div class='attr-abc-title collapse-header' data-order='"+order+"' data-id='"+id+"'>" +
			        "<div class='icon-label abc'>" +
			        "<i class='icon icon-abc'></i><span class='text'>ABC</span>" +
			        "</div>" +
			        "<div class='label-bar abc'>" +
			        "<input class='edit-input text' value='"+name+"'>"+
			        "<span class='entity-only-title' data-abcattr-code='"+abcattr_code+"' data-abcattr='"+abcattr+"'>"+abcattr+"</span>"+
			        "<div class='btn-wrap'>" +
			        "<i class='icon icon-save'></i>" +
			        "<i class='icon icon-add-abc group'></i>" +
			        "<i class='icon icon-trash-sm'></i>" +
			        "<i class='icon icon-arrow-sm'></i>" +
			        "</div>" +
			        "</div>" +
			        "</div>" +
			        "<ul class='drag-wrap-repeat dragEdit-wrap collapse-content collapse-content-active' id='dragEdit-"+dragWrapLen+"'>" +
			        "</ul>" +
			        "</li>"	
	    $(parent).prepend(abcHtml);
    }
    //普通属性初始化方法
    function initAttr(abcattr,dataType,id,name,opt,order,parent) {    	
    	var entityId = $(".entity_attr.active", $page).attr("data-code");        
    	Ajax.ajax('admin/node/basicItemNode/getComm?entityId', {
			entityId: entityId
		}, function(data) {			
			var data = data.comm;
			var attrHtml = "<li class='add-attr clear-fix'>" +
            "<div class='icon-label attr'>" +
            "<i class='icon icon-attr'></i>" +
            "<span class='text'>属性</span>" +
            "</div>" +
            "<div class='label-bar attr' data-order='"+order+"' data-id='"+id+"'>" +
            "<input type='text' disabled class='edit-input text' value='"+name+"'>" +
            "<select disabled class='abc-attr'>"            
            for(var i=0; i<data.length; i++) {
            	if(data[i][1] == abcattr) {
            		attrHtml += "<option data-id='"+data[i][0]+"' value='"+data[i][1]+"' selected>"+data[i][1]+"</option>";
            	}else {
            		attrHtml += "<option data-id='"+data[i][0]+"' value='"+data[i][1]+"'>"+data[i][1]+"</option>";
            	}
            	                
            }
			attrHtml += "</select>";
			attrHtml += "<select disabled class='data-type'>";            
		    Ajax.ajax('admin/node/basicItemNode/getDataType', '', function(data){		    	
		    	var data = data.dataType;
		    	for(var i=0; i<data.length; i++) {
		    		if(data[i] == dataType) {
		    			attrHtml += "<option value='"+data[i]+"' selected>"+data[i]+"</option>";
		    		}else {
		    			attrHtml += "<option value='"+data[i]+"'>"+data[i]+"</option>";
		    		}
	            	          
	            };
	            attrHtml += "</select>";
				attrHtml += "<select disabled class='node-ops-type'>";
				Ajax.ajax('admin/node/basicItemNode/getNodeOpsType', '', function(data){		    	
			    	var data = data.nodeOpsType;
			    	for(var i=0; i<data.length; i++) {
			    		if(data[i] == opt) {
			    			attrHtml += "<option value='"+data[i]+"' selected>"+data[i]+"</option>";
			    		}else {
			    			attrHtml += "<option value='"+data[i]+"'>"+data[i]+"</option>";
			    		}
		            	          
		            };
		            attrHtml += "</select>";
		            attrHtml += "<div class='btn-wrap'>" +
		            "<i class='icon icon-save'></i>" +
		            "<i class='icon icon-trash-sm'></i>" +
		            "<i class='icon-simulate-trashsm'></i>" +
		            "</div>" +
		            "</div>" +
		            "</li>";		           
		            $(parent).prepend(attrHtml);		            
			    }, {async: false})			    
		    }, {async: false})
	    }, {async: false});	
    }
	
    //标签初始化方法
    function initTag(subdomain, id,name,order,parent) {
    	console.log(subdomain);
    	subdomain = subdomain.split(",");    	
    	var tagHtml = "<li class='add-tag clear-fix'>" +
	        "<div class='icon-label tag'>" +
	        "<i class='icon icon-tag'></i>" +
	        "<span class='text'>标签</span>" +
	        "</div>" +
	        "<div class='label-bar tag' data-order='"+order+"' data-id='"+id+"'>" +
	        "<input type='text' disabled class='edit-input text' value='"+name+"'>" +
	        "<span class='icon icon-toleft'></span>" +
	        "<div class='tag-content'>" +
	        "<ul class='clear-fix'>" ;
	        for(var i=0; i<subdomain.length; i++) {
	        	if(subdomain[i] == "") {
	        		break;
	        	}
	        	tagHtml += "<li data-id='' data-text='" + subdomain[i] + "'>" +
	            "<span>" + subdomain[i] + "</span>" +
	            "<i class='icon icon-delete'></i>" +
	            "</li>"
	        }	        
	        tagHtml += "</ul>" +
	        "</div>" +
	        "<span class='icon icon-toright ban'></span>" +
	        "<div class='btn-wrap'>" +
	        "<i class='icon tag icon-save'></i>" +
	        "<i class='icon tag icon-add-tag'></i>" +
	        "<i class='icon icon-trash-sm'></i>" +
	        "</div>" +
	        "</div>" +
	        "</li>"
	    var Tag = $(tagHtml).prependTo($(parent));
	        console.log(Tag);
	    menuWidth(Tag.find(".tag-content").children("ul")[0]);
    }
    
    //属性组初始化方法
    function initGroup(id, name, order, parent) {
    	var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
        var attrGroupHtml = "<li class='attr-group'>" +
            "<div class='attr-group-title collapse-header' data-order='"+order+"' data-id='"+id+"'>" +
            "<div class='icon-label attr-group'>" +
            "<i class='icon icon-attr-group'></i>" +
            "<span class='text'>属性组</span>" +
            "</div>" +
            "<div class='label-bar attr-group'>" +
            "<input type='text' disabled class='edit-input text' value='"+name+"'>" +
            "<div class='btn-wrap'>" +
            "<i class='icon icon-save'></i>" +
            "<i class='icon icon-add-sm group'></i>" +
            "<i class='icon icon-trash-sm'></i>" +
            "<i class='icon icon-arrow-sm active'></i>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<ul class='attr-group-drag-wrap dragEdit-wrap collapse-content collapse-content-inactive need-ajax' id='dragEdit-"+dragWrapLen+"'>" +
            "</ul>" +
            "</li>"
        $(parent).prepend(attrGroupHtml);
        drag($(".dragEdit-wrap").length);
    }
    
    //多值属性初始化方法
    function initMoreAttr(abcattr,dataType,id,name,opt,order,parent) {    
    	var entityId = $(".entity_attr.active", $page).attr("data-code");     	
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;        
		Ajax.ajax('admin/node/basicItemNode/getComm?entityId', {
			entityId: entityId
		}, function(data) {			
			var data = data.comm;			
            var moreAttrHtml = "<li class='more-attr clear-fix'>" +
            "<div class='more-attr-title collapse-header' data-order='"+order+"' data-id='"+id+"'>" +
            "<div class='icon-label more-attr'>" +
            "<i class='icon icon-more-attr'></i>" +
            "<span class='text'>多值属性</span>" +
            "</div>" +
            "<div class='label-bar more-attr'>" +
            "<input type='text' disabled class='edit-input text' value='"+name+"'>" +
            "<select disabled class='abc-attr'>"
            console.log(data);
            for(var i=0; i<data.length; i++) {
            	if(data[i][1] == abcattr) {            		
            		moreAttrHtml += "<option data-id='"+data[i][0]+"' value='"+data[i][1]+"' selected>"+data[i][1]+"</option>"; 
            	}else {            		
            		moreAttrHtml += "<option data-id='"+data[i][0]+"' value='"+data[i][1]+"'>"+data[i][1]+"</option>"; 
            	}            	               
            }           
            moreAttrHtml += "</select>";
            moreAttrHtml += "<select disabled class='data-type'>";            
		    Ajax.ajax('admin/node/basicItemNode/getDataType', '', function(data){		    	
		    	var data = data.dataType;
		    	for(var i=0; i<data.length; i++) {
		    		if(data[i][1] == dataType) {            		
		    			moreAttrHtml += "<option value='"+data[i]+"' selected>"+data[i]+"</option>";       
	            	}else {            		
	            		moreAttrHtml += "<option value='"+data[i]+"'>"+data[i]+"</option>";       
	            	}		    		    
	            };
	            moreAttrHtml += "</select>";
	            moreAttrHtml += "<select disabled class='node-ops-type'>";
				Ajax.ajax('admin/node/basicItemNode/getNodeOpsType', '', function(data){		    	
			    	var data = data.nodeOpsType;
			    	for(var i=0; i<data.length; i++) {
			    		if(data[i][1] == opt) {            		
			    			moreAttrHtml += "<option value='"+data[i]+"' selected>"+data[i]+"</option>";			    			
		            	}else {            				            		
		            		moreAttrHtml += "<option value='"+data[i]+"'>"+data[i]+"</option>";
		            	}			    		         
		            };
		            moreAttrHtml += "</select>";
		            moreAttrHtml += "<div class='btn-wrap'>" +
		            "<i class='icon icon-save'></i>" +
		            "<i class='icon icon-add-sm group'></i>" +
		            "<i class='icon icon-trash-sm'></i>" +
		            "<i class='icon icon-arrow-sm active'></i>" +
		            "</div>" +
		            "</div>" +
		            "</div>" +
		            "<ul class='more-attr-drag-wrap dragEdit-wrap collapse-content collapse-content-inactive need-ajax' id='dragEdit-"+dragWrapLen+"'>" +
		            "</ul>" +
		            "</li>"
		            $(parent).prepend(moreAttrHtml);
		            drag($(".dragEdit-wrap").length);
			    }, {async: false})			    
		    }, {async: false})
	    }, {async: false});                                
    }

    //关系初始化方法
    function initRelative(abcattr, id, name, order, parent) {
    	var entityId = $(".entity_attr.active", $page).attr("data-code");
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;        
		Ajax.ajax('admin/node/basicItemNode/getComm?entityId', {
			entityId: entityId
		}, function(data) {			
			var data = data.comm;			            
            var relativeHtml = "<li class='attr-relative'>" +
            "<div class='attr-relative-title collapse-header' data-order='"+order+"' data-id='"+id+"'>" +
            "<div class='icon-label attr-relative'>" +
            "<i class='icon icon-attr-relative'></i><span class='text'>关系</span>" +
            "</div>" +
            "<div class='label-bar attr-relative'>" +
            "<input type='text' disabled class='edit-input text' value='"+name+"'>" +
            "<select disabled class='abc-attr'>"
            for(var i=0; i<data.length; i++) {
            	if(data[i][1] == abcattr) {            		            		
            		relativeHtml += "<option data-id='"+data[i][0]+"' value='"+data[i][1]+"' selected>"+data[i][1]+"</option>"; 
            	}else {            		            		
            		relativeHtml += "<option data-id='"+data[i][0]+"' value='"+data[i][1]+"'>"+data[i][1]+"</option>"; 
            	}               	              
            }
            relativeHtml += "</select>" +
            "<div class='btn-wrap'>" +
            "<i class='icon icon-save'></i>" + 
            "<i class='icon icon-trash-sm'></i>" +
            "<i class='icon icon-arrow-sm active'></i>" +
            "</div>" +
            "</div>" +
            "</div>" +            
            "<ul class='need-ajax attr-relative-drag-wrap dragEdit-wrap collapse-content collapse-content-inactive' id='dragEdit-"+dragWrapLen+"'>" +
            "</ul>" +
            "</li>";         
		    $(parent).prepend(relativeHtml);
		    drag($(".dragEdit-wrap").length);		    			    
	    }, {async: false});                                 
    }
	/**
     * 获取实体信息方法 示例     
     */
	function getEntity(entity) {		
		var cnName = $(entity).attr("data-cnname");		
		$("#operateEdit .entity-title>.edit-input").val(cnName);
		$("#operateEdit .entity-title>.entity-only-title").html(cnName);
		$("#operateEdit .entity-edit-wrap").addClass("active");
	}
	
    /**
     * 跟实体添加页面弹出方法
     * @param {当前点击元素,dom对象} el 
     */
    function pop(el) {
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
                "</li>"+
                "<li class='card-list add-relative'>" +
                "<i class='icon icon-card-relative'></i>" +
                "<span class='text'>添加关系</span>" +
                "</li>" +
                "</ul>";
        

        var wrap = $("#operateEdit");
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

        var wrap = $("#operateEdit");
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
			var wrap = $("#operateEdit");
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
			var wrap = $("#operateEdit");
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

        var wrap = $("#operateEdit");
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

        var wrap = $("#operateEdit");
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
                var ulWidth = parseFloat($tagUl.css("width"));
                var marginLeft = parseFloat($tagUl.css("margin-left"));
                var contentWidth = parseFloat($tagUl.parent(".tag-content").css("width"));
                
                if(ulWidth + marginLeft < contentWidth) {                	
                	var marginLeft = contentWidth - ulWidth;                	
                	if(marginLeft > 0) {
                		marginLeft = 0;
                	}
                	$tagUl.css("marginLeft",marginLeft);
                }
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
            "<input type='text' class='edit-input text' value='标签名称'>" +
            "<span class='icon icon-toleft'></span>" +
            "<div class='tag-content'>" +
            "<ul class='clear-fix'>" +
            "</ul>" +
            "</div>" +
            "<span class='icon icon-toright ban'></span>" +
            "<div class='btn-wrap'>" +
            "<i class='icon tag icon-save'></i>" +
            "<i class='icon tag icon-add-tag'></i>" +
            "<i class='icon icon-trash-sm'></i>" +
            "<i class='icon icon-simulate-trashsm'></i>" +            
            "</div>" +
            "</div>" +
            "</li>"
        $content.prepend(tagHtml);
        addUnfold(el)
    };

    /**
     * 添加属性方法
      */
    function addAttr(el) {
        var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        var entityId = $(".entity_attr.active", $page).attr("data-code");
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
            "<input type='text' class='edit-input text' value='属性名'>" +
            "<select class='abc-attr'>"            
            for(var i=0; i<data.length; i++) {
            	attrHtml += "<option data-id='"+data[i][0]+"' value='"+data[i][1]+"'>"+data[i][1]+"</option>";                
            }
			attrHtml += "</select>";
			attrHtml += "<select class='data-type'>";            
		    Ajax.ajax('admin/node/basicItemNode/getDataType', '', function(data){		    	
		    	var data = data.dataType;
		    	for(var i=0; i<data.length; i++) {
		    		if(data[i] === "STRING") {
		    			attrHtml += "<option value='"+data[i]+"' selected>"+data[i]+"</option>"; 	
		    		}else {
		    			attrHtml += "<option value='"+data[i]+"'>"+data[i]+"</option>"; 
		    		}	            	        
	            };
	            attrHtml += "</select>";
				attrHtml += "<select class='node-ops-type'>";
				Ajax.ajax('admin/node/basicItemNode/getNodeOpsType', '', function(data){		    	
			    	var data = data.nodeOpsType;
			    	for(var i=0; i<data.length; i++) {
			    		if(data[i] === "写") {
			    			attrHtml += "<option value='"+data[i]+"' selected>"+data[i]+"</option>";  	
			    		}else {
			    			attrHtml += "<option value='"+data[i]+"'>"+data[i]+"</option>"; 
			    		}
		            	         
		            };
		            attrHtml += "</select>";
		            attrHtml += "<div class='btn-wrap'>" +
		            "<i class='icon icon-save'></i>" +
		            "<i class='icon icon-trash-sm'></i>" +
		            "<i class='icon-simulate-trashsm'></i>" +
		            "</div>" +
		            "</div>" +
		            "</li>";
		            
		            var $html = $(attrHtml).prependTo($content);
		            $html.find("select").css({"width":"15%","marginLeft":"16px"}).select2();
		            addUnfold(el);
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
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
        var attrGroupHtml = "<li class='attr-group'>" +
            "<div class='attr-group-title collapse-header' data-order='' data-id=''>" +
            "<div class='icon-label attr-group'>" +
            "<i class='icon icon-attr-group'></i>" +
            "<span class='text'>属性组</span>" +
            "</div>" +
            "<div class='label-bar attr-group edit'>" +
            "<input type='text' class='edit-input text' value='属性组名称'>" +
            "<div class='btn-wrap'>" +
            "<i class='icon icon-save'></i>" +
            "<i class='icon icon-add-sm group'></i>" +
            "<i class='icon icon-trash-sm'></i>" +
            "<i class='icon icon-arrow-sm'></i>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<ul class='attr-group-drag-wrap dragEdit-wrap collapse-content collapse-content-active' id='dragEdit-"+dragWrapLen+"'>" +
            "</ul>" +
            "</li>"        
        var $html = $(attrGroupHtml).prependTo($content);  
        addUnfold(el)
        drag($(".dragEdit-wrap").length);
    };

    /**
     * 添加多值属性方法
      */
    function addMoreAttr(el) {
        var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        var entityId = $(".entity_attr.active", $page).attr("data-code");               
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
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
            "<input type='text' class='edit-input text' value='多值属性名称'>" +
            "<select class='abc-attr'>"
            for(var i=0; i<data.length; i++) {
            	moreAttrHtml += "<option data-id='"+data[i][0]+"' value='"+data[i][1]+"'>"+data[i][1]+"</option>";                
            }
            moreAttrHtml += "</select>";
            moreAttrHtml += "<select class='data-type'>";            
		    Ajax.ajax('admin/node/basicItemNode/getDataType', '', function(data){		    	
		    	var data = data.dataType;
		    	for(var i=0; i<data.length; i++) {
		    		if(data[i] === "STRING") {
		    			moreAttrHtml += "<option value='"+data[i]+"' selected>"+data[i]+"</option>";
		    		}else {
		    			moreAttrHtml += "<option value='"+data[i]+"'>"+data[i]+"</option>";
		    		}		    		         
	            };
	            moreAttrHtml += "</select>";
	            moreAttrHtml += "<select class='node-ops-type'>";
				Ajax.ajax('admin/node/basicItemNode/getNodeOpsType', '', function(data){		    	
			    	var data = data.nodeOpsType;
			    	for(var i=0; i<data.length; i++) {
			    		if(data[i] === "写"){
			    			moreAttrHtml += "<option value='"+data[i]+"' selected>"+data[i]+"</option>";
			    		}else {
			    			moreAttrHtml += "<option value='"+data[i]+"'>"+data[i]+"</option>";	
			    		}
			    		          
		            };
		            moreAttrHtml += "</select>";
		            moreAttrHtml += "<div class='btn-wrap'>" +
		            "<i class='icon icon-save'></i>" +
		            "<i class='icon icon-add-sm group'></i>" +
		            "<i class='icon icon-trash-sm'></i>" +
		            "<i class='icon icon-arrow-sm'></i>" +
		            "</div>" +
		            "</div>" +
		            "</div>" +
		            "<ul class='more-attr-drag-wrap dragEdit-wrap collapse-content collapse-content-active' id='dragEdit-"+dragWrapLen+"'>" +
		            "</ul>" +
		            "</li>"		            
		            var $html = $(moreAttrHtml).prependTo($content);
		            $html.find("select").css({"width":"15%","marginLeft":"16px"}).select2();
		            drag($(".dragEdit-wrap").length);
		            addUnfold(el);
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
        var entityId = $(".entity_attr.active", $page).attr("data-code");
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
        $CPF.showLoading();
		Ajax.ajax('admin/node/basicItemNode/entityList'," ", function(data) {			
			var data = data.entity;			            
            var relativeHtml = "<li class='attr-relative'>" +
            "<div class='attr-relative-title collapse-header' data-order='' data-id=''>" +
            "<div class='icon-label attr-relative'>" +
            "<i class='icon icon-attr-relative'></i><span class='text'>关系</span>" +
            "</div>" +
            "<div class='label-bar attr-relative edit'>" +
            "<input type='text' class='edit-input text' value='关系名称'>" +
            "<select class='abc-attr'>"
            for(var i=0; i<data.length; i++) {
            	relativeHtml += "<option data-id='"+data[i].code+"' value='"+data[i].cnName+"'>"+data[i].cnName+"</option>";                
            }
            relativeHtml += "</select>" +
            "<div class='btn-wrap'>" +
            "<i class='icon icon-save'></i>" +  
            "<i class='icon icon-trash-sm'></i>" +
            "<i class='icon icon-arrow-sm'></i>" +
            "</div>" +
            "</div>" +
            "</div>" +            
            "<ul class='attr-relative-drag-wrap dragEdit-wrap collapse-content collapse-content-active' id='dragEdit-"+dragWrapLen+"'>" +
            "</ul>" +
            "</li>";         		    
		    var $html = $(relativeHtml).prependTo($content);
            $html.find("select").css({"width":"15%","marginLeft":"16px"}).select2();
            addUnfold(el);
		    drag($(".dragEdit-wrap").length);
		    $CPF.closeLoading();			    
	    });                                 
    };    

    //提醒有未保存的节点
    function judgeSave() {
        var editBar = $("#operateEdit").find(".label-bar.edit");
        var editEntity = $("#operateEdit").find(".entity-edit-wrap.edit");
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
    	var id = $entityTitle.attr("data-id");
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
			 if(data.state == "fail") {
				 alert("属性名不能相同");
				 $CPF.closeLoading();
				 return;
			 }
			 var data = data.node;
			 //设置跟实体的order和id
			 var order = data.order;
			 var id = data.id;
			 $entityTitle.attr("data-order",order)
			 	.attr("data-id", id);
			 saveSuccess(el)
			 $CPF.closeLoading();
	    });
    }
    
    //标签保存修改方法
    function tagSave(el) {
    	var $tagBar = $(el).closest(".label-bar");
    	if($(el).next(".icon-add-tag-relative").length > 0) { //关系下的标签 
    		if($tagBar.children(".tag-content").children("ul").children("li").length == 0) {
    			alert("请至少选择一个关系");    			
    			$tagBar.addClass("edit");
    			return;
    		}
    	}else {
    		if($tagBar.children(".tag-content").children("ul").children("li").length == 0) {
    			alert("请至少选择一个标签");    			
    			$tagBar.addClass("edit");
    			return;
    		}
    	}
    	var type = 3;
    	var dataType = "STRING";
    	var opt = 2;
    	var name = $tagBar.children(".edit-input").val();    	
    	var order = $tagBar.attr("data-order");
    	var id = $tagBar.attr("data-id");
    	var parentId = $tagBar.closest(".collapse-content").prev(".collapse-header")
    						.attr("data-id"); 
    	var subdomain = []; 
    	var $tags = $tagBar.find(".tag-content").children("ul").children("li");
    	for(var i=0; i<$tags.length; i++) {
    		subdomain.push($($tags[i]).attr("data-text"));
    	}
    	subdomain = subdomain.join(",");
    	$CPF.showLoading();
    	Ajax.ajax(' admin/node/basicItemNode/saveOrUpdate', {
			 type: type,
			 name: name,
			 subdomain: subdomain,
			 abcattr: "",
			 dataType: dataType,
			 opt: opt,
			 order: order,
			 parentId: parentId,
			 id: id
		 }, function(data) {
			 if(data.state == "fail") {
				 alert("属性名不能相同");
				 $CPF.closeLoading();
				 return;
			 }
			 var data = data.node;
			 //设置当前节点order和id
			 var order = data.order;
			 var id = data.id;
			 $tagBar.attr("data-order",order)
			 	.attr("data-id", id);
			 saveSuccess(el)
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
			 if(data.state == "fail") {
				 alert("属性名不能相同");
				 $CPF.closeLoading();
				 return;
			 }
			 var data = data.node;
			 //设置当前节点order和id
			 var order = data.order;
			 var id = data.id;
			 $attrBar.attr("data-order",order)
			 	.attr("data-id", id);
			 saveSuccess(el)
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
			 if(data.state == "fail") {
				 alert("属性名不能相同");
				 $CPF.closeLoading();
				 return;
			 }
			 var data = data.node;
			 //设置当前节点order和id
			 var order = data.order;
			 var id = data.id;
			 $attrGroupBar.closest(".collapse-header")
			 	.attr("data-order",order)
			 	.attr("data-id", id);
			 saveSuccess(el)
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
			 if(data.state == "fail") {
				 alert("属性名不能相同");
				 $CPF.closeLoading();
				 return;
			 }
			 var data = data.node;
			 //设置当前节点order和id
			 var order = data.order;
			 var id = data.id;
			 $moreAttrBar.closest(".collapse-header")
			 	.attr("data-order",order)
			 	.attr("data-id", id);
			 saveSuccess(el)
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
    	var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
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
			 if(data.state == "fail") {
				 alert("属性名不能相同");
				 $CPF.closeLoading();
				 return;
			 }
			 var data = data.node;
			 //设置当前节点order和id
			 var order = data.order;
			 var id = data.id;
			 $relativeBar.closest(".collapse-header")
			 	.attr("data-order",order)
			 	.attr("data-id", id);
			 var chLength = $(".entity-ch-wrap", $page).length;
			 var nest = "no-repeat";
			 if(chLength >= 2) {
				 nest = "repeat"
			 }
			 //展现出关系下的标签和abc HTML			 
			 var html = "<li class='add-tag clear-fix'>" +
	            "<div class='icon-label tag'>" +
	            "<i class='icon icon-tag'></i>" +
	            "<span class='text'>标签</span>" +
	            "</div>" +
	            "<div class='label-bar tag edit' data-order='' data-id=''>" +
	            "<input type='text' class='edit-input text' value='标签名称'>" +
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
	            "<li class='entity-ch-wrap "+nest+"'>" +
	            "<div class='attr-abc-title collapse-header' data-order='' data-id=''>" +
	            "<div class='icon-label abc'>" +
	            "<i class='icon icon-abc'></i><span class='text'>ABC</span>" +
	            "</div>" +
	            "<div class='label-bar abc edit'>" +
	            "<input class='edit-input text' value='"+abcattr+"'>"+
	            "<span class='entity-only-title' data-abcattr-code='"+abcattrCode+"' data-abcattr='"+abcattr+"'>"+abcattr+"</span>"+
	            "<div class='btn-wrap'>" +
	            "<i class='icon icon-save'></i>" +
	            "<i class='icon icon-add-abc group'></i>" +	            
	            "<i class='icon icon-arrow-sm'></i>" +
	            "</div>" +
	            "</div>" +
	            "</div>" +
	            "<ul class='drag-wrap-repeat dragEdit-wrap collapse-content collapse-content-active' id='dragEdit-"+dragWrapLen+"'>" +
	            "</ul>" +
	            "</li>"	
	          var $content = $relativeBar.parent(".collapse-header").next(".collapse-content");		
			  if($content.hasClass("collapse-content-inactive")){
				  $relativeBar.find(".icon-arrow-sm").trigger("click");				  
				  if($content.children().length == 0) {					  
					  var $html = $(html).appendTo($content);
			          $html.find("select").css({"width":"15%","marginLeft":"16px"}).select2();
				  }
			  }
	          
			  drag($(".dragEdit-wrap").length);
			  saveSuccess(el)
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
			 if(data.state == "fail") {
				 alert("属性名不能相同");
				 $CPF.closeLoading();
				 return;
			 }
			 var data = data.node;
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
    
    //删除的请求方法
    function deleteAjax(id, boolean, callback) {    	
    	$CPF.showLoading();
    	Ajax.ajax('admin/node/basicItemNode/do_delete', {			
			 id: id,
			 isDelChil: boolean
		 }, function(data) {				 
			 callback();
			 removePop();
			 $CPF.closeLoading();
		});
    };
       
    
    //跟实体删除方法
    function entityDelete(el) {
    	var $entityTitle = $(el).closest(".entity-title");
    	var id = $entityTitle.attr("data-id");
    	var isDelChil = true;
    	var callback = function() {
    		$entityTitle.next(".collapse-content").html();
    		$entityTitle.parent(".entity-edit-wrap")
    			.removeClass("active")
    			.addClass("edit");
    	};
    	if($entityTitle.hasClass("al-save")) {
    		deleteAjax(id, isDelChil, callback);
    	}else {
    		callback();
    		removePop();
    	}
    }
    
    //属性删除方法
    function attrDelete(el) {    	
    	var $attrBar = $(el).closest(".label-bar");    	
    	var id = $attrBar.attr("data-id");
    	var isDelChil = false;
    	var callback = function() {
    		$attrBar.parent(".add-attr").remove();    		
    	};    	
    	if($attrBar.hasClass("al-save")) {
    		deleteAjax(id, isDelChil, callback);
    	}else {
    		callback();
    		removePop();
    	}    
    }
    
    //属性组删除方法
    function attrGroupDelete(el, isDelChil) {
    	var $attrGroupBar = $(el).closest(".label-bar");
    	var id = $attrGroupBar.closest(".collapse-header").attr("data-id");
    	var isDelChil = isDelChil;    	
    	if(isDelChil) {    		
    		var callback = function() {
        		$attrGroupBar.closest("li.attr-group").remove();    		
        	};
    	}else {    		
    		var callback = function() {
        		var html = $attrGroupBar.closest("li.attr-group")
        					.children(".collapse-content").html();
        		$attrGroupBar.closest("li.attr-group")
        			.after(html);
        		$attrGroupBar.closest("li.attr-group").remove();
        	};
    	}    	
    	if($attrGroupBar.hasClass(".al-save")) {
    		deleteAjax(id, isDelChil, callback);	
    	}else {
    		callback();
    		removePop();
    	}
    };
    
    //多值属性删除方法
    function moreAttrDelete(el, isDelChil) {
    	var $moreAttrBar = $(el).closest(".label-bar");
    	var id = $attrGroupBar.closest(".collapse-header").attr("data-id");
    	var isDelChil = isDelChil;
    	if(isDelChil) {
    		var callback = function() {
        		$moreAttrBar.closest("li.more-attr").remove();    		
        	};
    	}else {
    		var callback = function() {
        		var html = $moreAttrBar.closest("li.more-attr")
        					.children(".collapse-content").html();
        		$moreAttrBar.closest("li.more-attr")
        			.after(html);
        		$moreAttrBar.closest("li.more-attr").remove();
        	};
    	}    
    	if($moreAttrBar.hasClass("al-save")) {
    		deleteAjax(id, isDelChil, callback);	
    	}else {
    		callback();
    		removePop();
    	}  
    };   
    
    //关系删除方法
    function relativeDelete(el) {
    	var $relativeBar = $(el).closest(".label-bar");
    	var id = $relativeBar.closest(".collapse-header").attr("data-id");
    	var isDelChil = true;
    	var callback = function() {
    		$relativeBar.closest("li.attr-relative").remove();    		
    	};
    	if($relativeBar.hasClass("al-save")){
    		deleteAjax(id, isDelChil, callback);	
    	}else {
    		callback();
    		removePop();
    	}  
    };   
    
    //tag删除
    function tagDelete(el) {
    	var $tagBar = $(el).closest(".label-bar");
    	var id = $tagBar.attr("data-id");
    	var isDelChil = true;
    	var callback = function() {
    		$tagBar.closest("li.add-tag").remove();    		
    	};
    	if($tagBar.hasClass("al-save")){
    		deleteAjax(id, isDelChil, callback);	
    	}else {
    		callback();
    		removePop();
    	}  
    }
        

    $page.on("click", function () {    	
        removePop();
    });
      

    //收缩事件绑定
    $("#operateEdit").on("click", ".icon-arrow, .icon-arrow-sm", function (e) {
    	e.stopPropagation();
    	var bar = $(this).closest(".label-bar")[0];
        var $content = $(this).closest(".collapse-header")
            .siblings(".collapse-content");
        var isRelative = $(this).closest(".label-bar").hasClass("attr-relative");        
        var needAjax = $content.hasClass("need-ajax");  //判断是否需要ajax获取数据            
        var nodeId = $(this).closest(".collapse-header").attr("data-id");
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
        	getChild(nodeId, isRelative, bar);
        }
    })

    //跟实体添加事件绑定
    $("#operateEdit").on("click", ".icon-add, .icon-add-abc", function (e) {
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
    $("#operateEdit").on("click", ".icon-add-sm", function (e) {
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
    $("#operateEdit").on("click", ".icon-add-tag", function (e) {
        e.stopPropagation();
        $(this).closest(".label-bar.tag").addClass("edit");
        removePop();
        popTag(this);
        $(this).addClass("active");
    });
    
    //关系下标签添加
    $("#operateEdit").on("click", ".icon-add-tag-relative", function (e) {
        e.stopPropagation();
        $(this).closest(".label-bar.tag").addClass("edit");
        removePop();
        popRelativeTag(this);
        $(this).addClass("active");
    });


    //删除属性事件绑定
    $("#operateEdit").on("click", ".icon-trash, .icon-trash-sm", function (e) {
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
    $("#operateEdit").on("click", ".card>li.card-list", function (e) {
        e.stopPropagation();
        if ($("#operateEdit").find(".icon-add.active").length > 0) {
            var el = $("#operateEdit").find(".icon-add.active")[0];
        } else if ($("#operateEdit").find(".icon-add-sm.active").length > 0) {
            var el = $("#operateEdit").find(".icon-add-sm.active")[0];
        } else if ($("#operateEdit").find(".icon-add-abc.active").length > 0) {
            var el = $("#operateEdit").find(".icon-add-abc.active")[0];
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
    $("#operateEdit").on("click", ".tag-checkbox-input", function (e) {    	
        e.stopPropagation();
        var el = $("#operateEdit").find(".icon-add-tag.active");
        if(el.length == 0) {
        	el = $("#operateEdit").find(".icon-add-tag-relative.active")[0];
        }else {
        	el = $("#operateEdit").find(".icon-add-tag.active");
        }   
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
    $("#operateEdit").on("click", ".icon-toleft", function () {
        if ($(this).hasClass("ban")) {
            return;
        }
        var $ul = $(this).next(".tag-content").children("ul");
        var marginLeft = parseFloat($ul.css("marginLeft"));
        var ulWidth = parseFloat($ul.css("width"));
        var wrapWidth = parseFloat($ul.parent(".tag-content").css("width"));
        $("#operateEdit").find(".icon-toright").removeClass("ban");
        if (ulWidth - wrapWidth + marginLeft < 80) {
            marginLeft = wrapWidth - ulWidth;
            $(this).addClass("ban");
        } else {
            marginLeft = marginLeft - 80;
        }
        $ul.css("marginLeft", marginLeft);
    })

    //标签向右移动事件绑定
    $("#operateEdit").on("click", ".icon-toright", function () {
        if ($(this).hasClass("ban")) {
            return;
        }
        var $ul = $(this).prev(".tag-content").children("ul");
        var marginLeft = parseFloat($ul.css("marginLeft"));
        $("#operateEdit").find(".icon-toleft").removeClass("ban");
        if (marginLeft > -80) {
            marginLeft = 0;
            $(this).addClass("ban");
        } else {
            marginLeft = marginLeft + 80;
        }
        $ul.css("marginLeft", marginLeft);
    })
    
    //标签删除图标点击事件绑定
    $("#operateEdit").on("click", ".tag-content .icon-delete", function(){
    	var $tagUl = $(this).closest(".tag-content")
        .children("ul");     	
    	$(this).parent("li").remove();
    	menuWidth($tagUl[0]);
        judegArrow($tagUl);
        var ulWidth = parseFloat($tagUl.css("width"));
        var marginLeft = parseFloat($tagUl.css("margin-left"));
        var contentWidth = parseFloat($tagUl.parent(".tag-content").css("width"));
        
        if(ulWidth + marginLeft < contentWidth) {        	
        	var marginLeft = contentWidth - ulWidth;        	        	        	
        	if(marginLeft > 0) {
        		marginLeft = 0;
        	}
        	$tagUl.css("marginLeft",marginLeft);
        }
    })    

    //双击编辑
    $("#operateEdit").on("dblclick", ".label-bar", function(){
//    	var hasSave = judgeSave();
//    	if(hasSave){
//    		return;
//    	}
    	if(!$(this).hasClass("attr-relative")){
    		$(this).find(".edit-input").removeAttr("disabled");
        	$(this).find("select").removeAttr("disabled");
    	}    	
        $(this).addClass("edit");
    })

    //保存
    $("#operateEdit").on("click", ".icon-save", function() {        
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
    $("#operateEdit").on("click", ".opera.confirm", function(e) { 
    	e.stopPropagation();    	
        var entityTitle = $(".icon-trash.active").closest(".entity-title");
        var labelBar = $(".icon-trash-sm.active").closest(".label-bar");
        if(entityTitle.length > 0) {
        	var el = $(".icon-trash.active")[0];
        	entityDelete(el);
        	return;
        }
        var el = $(".icon-trash-sm.active")[0];
        if(labelBar.hasClass("attr")) {         	
        	attrDelete(el);        	
        }else if(labelBar.hasClass("more-attr")) {
        	moreAttrDelete(el, true);
        }else if(labelBar.hasClass("attr-group")) {
        	attrGroupDelete(el, true);
        }else if(labelBar.hasClass("attr-relative")) {
        	relativeDelete(el);
        }else if(labelBar.hasClass("tag")) {
        	tagDelete(el);
        }
    })
    
    //删除-仅组
    $("#operateEdit").on("click", ".opera.only-group", function(e) { 
    	e.stopPropagation();
    	var el = $(".icon-trash-sm.active")[0];       
        var labelBar = $(".icon-trash-sm.active").closest(".label-bar");        
        if(labelBar.hasClass("more-attr")) {
        	moreAttrDelete(el, false);
        }else if(labelBar.hasClass("attr-group")) {
        	attrGroupDelete(el, false);
        }
    })
    
    //实体选择点击事件绑定
    $("#operateEdit").on("click", ".entity_attr", function() {
    	var $attrArray = $(".entity_attr",$page);
    	for(var i=0; i<$attrArray.length; i++) {
    		if($($attrArray[i]).hasClass("active")) { //已经有选择过的了 就不能再点击选择了
    			return;
    		}
    	}    	
    	$(this).addClass("active");    		
    	getEntity(this);
    }) 
    
    
    
})