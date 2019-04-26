seajs.use(['dialog', 'ajax', '$CPF'], function(Dialog, Ajax, $CPF) {
	var $page = $("#filters-tree");
	
	 $(function(){
		  //FILTERS
		    Ajax.ajax('admin/node/binFilterBody/getFiltersOpt', {
		    	opsCode:10
		    }, function(data){		    	
		    	var data = data.optMap;
		    	optFILTERS = data;
		    	$CPF.closeLoading();
		    }, {async: true})
		    
		    debugger;
		    var filtersId =  $page.find(".tree_view_panel").attr("filtersId");
		    var entityId =  $page.find(".tree_view_panel").attr("entityId");
		    
		    var parent = $page.find(".entity-title").next(".collapse-content")[0];
		    debugger;
		    //回显第一个节点
		    if (filtersId) {
		    	 Ajax.ajax('admin/node/binFilterBody/getBinFilterBody', {
		    		 filtersId:filtersId
                  }, function(data) {  
                	  
                	  debugger;
                	  var source = "sourceEntity";
                	  initFilters(entityId, data.binFilterBody, source,parent);//值初始化第一个节点
                  	
         })
		    }
		    
	    })
	    
	    
	    function initFilters(entityId, binFilterBody, source, parent) {
    	debugger;
   	 	$(parent).html("");
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
        $CPF.showLoading();
            var relativeHtml = "<li class='attr-relative'>" +
            "<div class='attr-relative-title collapse-header' source='"+source+"' entityId='"+entityId+"' data-order='' data-id='"+binFilterBody.id+"'>" +
            "<div class='icon-label attr-relative-dict attr-relative'>" +
            "<i class='icon icon-attr-relative'></i><span class='text'>filters</span>" +
            "</div>" +
            "<div class='label-bar filters al-save'>" +
            "<input type='text' class='edit-input text' value='"+binFilterBody.name+"'>";
            relativeHtml += "<select disabled class='node-ops-type'>";	
            var nodePosType = optFILTERS;
            for(var key in nodePosType) {
		    	if(binFilterBody.opt == key) {
		    		relativeHtml += "<option value='"+key+"' selected>"+nodePosType[key]+"</option>";  	
		    	}else {
		    		relativeHtml += "<option value='"+key+"'>"+nodePosType[key]+"</option>"; 
		    	}
	         }
	        relativeHtml += "</select>";
	        relativeHtml += "<div class='btn-wrap'>" +
            "<i class='icon icon-save'></i>" +  
            "<i class='icon icon-add-filterGroup group'></i>" +
            "<i class='icon icon-trash-sm'></i>" +
            "<i class='icon icon-arrow-sm active'></i>" +
            "</div>" +
            "</div>" +
            "</div>" +            
            "<ul class='attr-relative-drag-wrap dragEdit-wrap collapse-content collapse-content-inactive need-ajax' id='dragEdit-"+dragWrapLen+"'>" +
            "</ul>" +
            "</li>";         		    
		    var $html = $(relativeHtml).prependTo($(parent));
		    $html.find("select").css({"width":"15%","marginLeft":"16px"}).select2();
		    $CPF.closeLoading();
   }
	    
	    
    ///////////////////////////下面是树节点
    function addUnfold(el) {		
		if($(el).hasClass("icon-add") && $(el).siblings(".icon-arrow").hasClass("active")) {
        	$(el).siblings(".icon-arrow").trigger("click");        	
        }else if($(el).hasClass("icon-add-sm") && $(el).siblings(".icon-arrow-sm").hasClass("active")){
        	$(el).siblings(".icon-arrow-sm").trigger("click");
        }else if($(el).hasClass("icon-add-abc") && $(el).siblings(".icon-arrow-sm").hasClass("active")){
        	$(el).siblings(".icon-arrow-sm").trigger("click");
        } else if($(el).hasClass("icon-add-filterGroup") && $(el).siblings(".icon-arrow-sm").hasClass("active")){
        	$(el).siblings(".icon-arrow-sm").trigger("click");
        } else if($(el).hasClass("icon-add-filter") && $(el).siblings(".icon-arrow-sm").hasClass("active")){
        	$(el).siblings(".icon-arrow-sm").trigger("click");
        }
	} 	
	function saveSuccess(el) {
		 $(el).closest(".label-bar").removeClass("edit");
		 $(el).closest(".entity-title").removeClass("edit");
	     $(el).closest(".tree_view_panel").removeClass("edit");
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
    	
    	 var labelBar = $(el).closest(".head-filters");
         if (labelBar.hasClass("head-filters")) {
        	 html = "<ul class='card'>";        
             html += "<li class='card-list add-filters' source='sourceEntity'>" +
             "<i class='icon icon-card-relative'></i>" +
             "<span class='text'>添加过滤条件</span>" +
             "</li>" +
                 "</ul>";
         }
            
        var wrap = $(el).closest("#tree_view_panel");
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
    $(".tree_view_panel", $page).on("click", ".card>li.card-list", function (e) {
    	e.stopPropagation();
    	
        if ($(".tree_view_panel", $page).find(".icon-add.active").length > 0) {
            var el = $(".tree_view_panel", $page).find(".icon-add.active")[0];
        } else if ($(".tree_view_panel", $page).find(".icon-add-abc.active").length > 0) {
            var el = $(".tree_view_panel", $page).find(".icon-add-abc.active")[0];
        }else if ($(".tree_view_panel", $page).find(".icon-add-filterGroup.active").length > 0) {
            var el = $(".tree_view_panel", $page).find(".icon-add-filterGroup.active")[0];
        }else if ($(".tree_view_panel", $page).find(".icon-add-filter.active").length > 0) {
            var el = $(".tree_view_panel", $page).find(".icon-add-filter.active")[0];
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
        }else if ($(this).hasClass("add-filters")) {
       	/* var filtersBar = $(el).closest(".label-bar").closest(".collapse-header").parent().find(".label-bar.filters");
    	 if(filtersBar.length > 0) {
             Dialog.notice("只能添加一个filters", "warning");
             return true;
         }*/
    	var source =  $(this).attr("source");
    	addFilters(el, source);
    }else if ($(this).hasClass("add-filterGroup")) {
    	addFilterGroup(el);
    } else if ($(this).hasClass("add-filter")) {
    	addFilter(el);
    } else if ($(this).hasClass("add-rFilter")) {
    	addrFilter(el);
    } 
        removePop();
        $(el).removeClass("active");
    });
    
    
    /**
     * 添加过滤条件
      */
    function addFilters(el, source) {
        var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        var entityId = $(el).closest(".tree_view_panel").attr("entityId");
       /* if($(el).closest(".tree_view_panel").hasClass("entity-title")){
        	entityId = $(el).closest(".collapse-header").attr("data-abcattrcode");
        }else if ($(el).closest(".collapse-header").hasClass("more-attr-title")) {
        	entityId = $(el).closest(".collapse-header").attr("data-abcattrcode");
        }else {
        	entityId = $(el).closest(".collapse-header").find(".label-bar")
        					.find(".entity-only-title").attr("data-abcattrcode");
        }*/
        
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
        $CPF.showLoading();
            var relativeHtml = "<li class='attr-relative'>" +
            "<div class='attr-relative-title collapse-header' source='"+source+"' entityId='"+entityId+"' data-order='' data-id=''>" +
            "<div class='icon-label attr-relative-dict attr-relative'>" +
            "<i class='icon icon-attr-relative'></i><span class='text'>filters</span>" +
            "</div>" +
            "<div class='label-bar filters edit'>" +
            "<input type='text' class='edit-input text' value='filters名称'>";
            relativeHtml += "<select class='node-ops-type'>";	
            var nodePosType = optFILTERS;
            for(var key in nodePosType) {
 		    	relativeHtml += "<option value='"+key+"'>"+nodePosType[key]+"</option>"; 
 	         }
	        relativeHtml += "</select>";
	        relativeHtml += "<div class='btn-wrap'>" +
            "<i class='icon icon-save'></i>" +  
            "<i class='icon icon-add-filterGroup group'></i>" +
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
		   /* drag($(".dragEdit-wrap").length);*/
		    $CPF.closeLoading();			    
    }; 
    
    
    /**
     * 添加过滤条件 filterGroup
      */
    function addFilterGroup(el) {
    	
        var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        
       var entityId = $(el).closest(".collapse-header").attr("entityId");
       var source = $(el).closest(".collapse-header").attr("source");
        
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
        $CPF.showLoading();
            var relativeHtml = "<li class='attr-relative'>" +
            "<div class='attr-relative-title collapse-header' source='"+source+"' entityId='"+entityId+"' data-order='' data-id=''>" +
            "<div class='icon-label attr-relative-dict attr-relative'>" +
            "<i class='icon icon-attr-relative'></i><span class='text'>Group</span>" +
            "</div>" +
            "<div class='label-bar filterGroup edit'>" +
            "<input type='text' class='edit-input text' value='filterGroup名称'>";
            relativeHtml += "<select class='node-ops-type'>";	
            var nodePosType = optFILTERS;
            for(var key in nodePosType) {
 		    	relativeHtml += "<option value='"+key+"'>"+nodePosType[key]+"</option>"; 
 	         }
	        relativeHtml += "</select>";
	        relativeHtml += "<div class='btn-wrap'>" +
            "<i class='icon icon-save'></i>" +  
            "<i class='icon icon-add-filter group'></i>" +
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
		    $CPF.closeLoading();			    
    }; 
    
    
    /**
     * 添加过滤条件 filter
      */
    function addFilter(el) {
    	$CPF.showLoading();
        var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        
        
        
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
        
            var relativeHtml = "<li class='attr-relative'>" +
            "<div class='attr-relative-title collapse-header' source='"+source+"' entityId='"+entityId+"' data-order='' data-id=''>" +
            "<div class='icon-label attr-relative-dict attr-relative'>" +
            "<i class='icon icon-attr-relative'></i><span class='text'>filter</span>" +
            "</div>" +
            "<div class='label-bar filter edit'>" +
            "<input type='text' class='edit-input filterName text' value='filter名称'>";
            
            	
            //getCriteriaSymbol
            
            var entityId = $(el).closest(".collapse-header").attr("entityId");
            var source = $(el).closest(".collapse-header").attr("source");
            relativeHtml += "<select class='abcattrCodeData'>";
    	    Ajax.ajax('admin/dictionary/basicItem/getComm', {
    	    	entityId:entityId
    	    }, function(data){		
    	    	var entityData = data.commList;
    	    	
    	    	
    	    	 /*Ajax.ajax('admin/dictionary/basicItem/getRepeatChild', {
    	    		 repeatId:entityId
    	    	    }, function(data){	
    	    	    	
    	    	    	var repeatData = data.repeatChild;*/
    	    	    	
    	    	    	
    	    	/*if (source == "moreAttr") {
    	    		 for(var key in repeatData) {
    	 		    	relativeHtml += "<option value='"+repeatData[key].code+"'>"+repeatData[key].cnName+"</option>"; 
    	 	         };
    	    	} else {*/
    	    		 for(var key in entityData) {
    	 		    	relativeHtml += "<option value='"+entityData[key][0]+"'>"+entityData[key][1]+"</option>"; 
    	 	         };
    	    	/*}*/
		   
	        relativeHtml += "</select>";
            
            relativeHtml += "<select class='node-Symbol-type'>";	
            //getCriteriaSymbol
    	    Ajax.ajax('admin/node/binFilterBody/getCriteriaSymbol', {
    	    	dataType:12
    	    }, function(data){		
    	    	var nodeSymbolType = data.symbolMap;
		    for(var key in nodeSymbolType) {
		    	relativeHtml += "<option value='"+key+"'>"+nodeSymbolType[key]+"</option>"; 
	         };
	        relativeHtml += "</select>"+
	        "<input type='text' class='edit-input valueStr text' value='value'>";
            
            relativeHtml += "<select class='node-ops-type'>";	
            var nodePosType = optFILTERS;
            for(var key in nodePosType) {
 		    	relativeHtml += "<option value='"+key+"'>"+nodePosType[key]+"</option>"; 
 	         }
	        relativeHtml += "</select>";
	        relativeHtml += "<div class='btn-wrap'>" +
            "<i class='icon icon-save'></i>" +  
            /*"<i class='icon icon-add-filter group'></i>" +*/
            "<i class='icon icon-trash-sm'></i>" +
           /* "<i class='icon icon-arrow-sm'></i>" +*/
            "</div>" +
            "</div>" +
            "</div>" +            
            "<ul class='attr-relative-drag-wrap dragEdit-wrap collapse-content collapse-content-active' id='dragEdit-"+dragWrapLen+"'>" +
            "</ul>" +
            "</li>";         		    
		    var $html = $(relativeHtml).prependTo($content);
            $html.find("select").css({"width":"13%","marginLeft":"16px"}).select2();
            $html.find("select.node-ops-type").css({"width":"8%","marginLeft":"16px"}).select2();
            
            addUnfold(el);
		   /* drag($(".dragEdit-wrap").length);*/
		    $CPF.closeLoading();	
    	   /* })*/
    	    })
    	    })
    }; 
    
    
    /**
     * 添加过滤条件 rFilter
      */
    function addrFilter(el) {
        var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        var entityId = $(el).closest(".collapse-header").attr("entityId");
        var source = $(el).closest(".collapse-header").attr("source");
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
        $CPF.showLoading();
        
        Ajax.ajax('admin/dictionary/recordRelationType/getRelation', {
			leftRecordType: entityId,
			relationType:''
		}, function(data) {			
			var relationList = data.relationList;
        Ajax.ajax('admin/node/binFilterBody/getCriteriaSymbol', {
	    	dataType:13
	    }, function(data){	
	    	
            var relativeHtml = "<li class='attr-relative'>" +
            "<div class='attr-relative-title collapse-header' source='"+source+"' entityId='' data-order='' data-id=''>" +
            "<div class='icon-label attr-relative-dict attr-relative'>" +
            "<i class='icon icon-attr-relative'></i><span class='text'>rFilter</span>" +
            "</div>" +
            "<div class='label-bar rFilter edit'>" +
            "<input type='text' class='edit-input text' value='rFilter名称'>";
            
            relativeHtml += "<select class='relationData'>";	
		    for(var key in relationList) {
		    	relativeHtml += "<option rightRecordType='"+relationList[key].rightRecordType+"' value='"+relationList[key].typeCode+"'>"+relationList[key].name+"</option>"; 
	         };
	         relativeHtml += "</select>";
            
            relativeHtml += "<select class='node-Symbol-type'>";	
    	    	
    	    	var nodeSymbolType = data.symbolMap;
		    for(var key in nodeSymbolType) {
		    	relativeHtml += "<option value='"+key+"'>"+nodeSymbolType[key]+"</option>"; 
	         };
	         relativeHtml += "</select>";

	         relativeHtml += "<select class='node-ops-type'>";	
            var nodePosType = optFILTERS;
            for(var key in nodePosType) {
 		    	relativeHtml += "<option value='"+key+"'>"+nodePosType[key]+"</option>"; 
 	         }
	         relativeHtml += "</select>";
	         relativeHtml += "<div class='btn-wrap'>" +
            "<i class='icon icon-save'></i>" +  
            "<i class='icon icon-add-filterGroup group'></i>" +
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
		    /*drag($(".dragEdit-wrap").length);*/
		    $CPF.closeLoading();	
    	    })
		 })
    }; 
    
    
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
    	
    	var sourcecode = $(el).closest(".tree_view_panel").attr("sourcecode");
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
		$("#tree_view_panel .tree_view_panel").addClass("active");
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
        
        
        var wrap = $(el).closest("#tree_view_panel");

       /* var wrap = $("#tree_view_panel");*/
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
        var editBar = $(".tree_view_panel", $page).find(".label-bar.edit");
        var editEntity = $(".tree_view_panel", $page).find(".tree_view_panel.edit");
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
			 		debugger;
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
    		$entityTitle.parent(".tree_view_panel")
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
    $(".tree_view_panel", $page).on("click", ".icon-arrow, .icon-arrow-sm", function (e) {
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
    	   var filters =  $(this).closest(".label-bar").hasClass("filters");
    	   var filterGroup =  $(this).closest(".label-bar").hasClass("filterGroup");
    	   var rFilter =  $(this).closest(".label-bar").hasClass("rFilter");
     	  if (filters || filterGroup || rFilter) {//这里是加载Filters的孩子
     		 var nodeId = $(this).closest(".label-bar").closest(".collapse-header").attr("data-id");
     		 var enID = $(this).closest(".label-bar").closest(".collapse-header").attr("entityId");
     		 getFiltersChild(nodeId, enID, bar);
     		  return;
     	  }
     	 var FUNCTIONSave =  $(this).closest(".label-bar").hasClass("FUNCTION-save");
     	var expressionId = $(this).closest(".label-bar").closest(".collapse-header").attr("data-id"); 
     	var parent= $(this).closest(".label-bar").closest(".collapse-header").next(".collapse-content");
	   	  if (FUNCTIONSave) {//这里是加载subChild
	   		getFUNCATIONchild(expressionId,parent);
	   	  }
    	   
    	   $content.removeClass("need-ajax");
        } else {
        	$content.addClass("need-ajax");
        }
    })
    
    function getFiltersChild(nodeId, enID, bar) {
		$CPF.showLoading();
		Ajax.ajax('admin/node/binFilterBody/getFiltersChild', {
			parentId: nodeId
		}, function(data) {			
			 if(data.code == "400") {
		            Dialog.notice(data.msg, "error");
		            return true;
		       }
			 
			 var parent = $(".collapse-header[data-id='" + nodeId + "']", $page).next(".collapse-content")[0];
			 
			 $(parent).html("");
			 var source = $(bar).closest(".collapse-header").attr("source");
			 $(parent).removeClass("need-ajax");
			 var filterBodyList = data.filterBodyChild;
			 if (data.code==200 && filterBodyList.length>0) {
				 for(var i=0; i<filterBodyList.length; i++) {
					 var dataType = filterBodyList[i].dataType;
					 
					 if (dataType == 11) {
						 initFilterGroup(filterBodyList[i],nodeId, enID,parent, source);
					 } else if (dataType == 12) {
						 initFilter(filterBodyList[i],nodeId, enID,parent,  source);
					 }else if (dataType == 13) {
						 initRFilter(filterBodyList[i],nodeId, enID,parent, source);
					 }
				 }
				} else if (data.code==200 && filterBodyList.length==0) {
					Dialog.notice("当前没有孩子！", "warning");
				} else {
					Dialog.notice("孩子数据加载错误！", "error");
				}
			 
			 $CPF.closeLoading();
		});
		
	}
	
	
	function initFilterGroup(filterBodyList,nodeId, enID, parent, source) {
		
	        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
	        $CPF.showLoading();
	            var relativeHtml = "<li class='attr-relative'>" +
	            "<div class='attr-relative-title collapse-header' source='"+source+"'  entityId='"+enID+"' data-order='' data-id='"+filterBodyList.id+"'>" +
	            "<div class='icon-label attr-relative-dict attr-relative'>" +
	            "<i class='icon icon-attr-relative'></i><span class='text'>Group</span>" +
	            "</div>" +
	            "<div class='label-bar filterGroup al-save'>" +
	            "<input type='text' class='edit-input text' value='"+filterBodyList.name+"'>";
	            relativeHtml += "<select disabled  class='node-ops-type'>";	
	            var nodePosType = optFILTERS;
	            for(var key in nodePosType) {
			    	if(filterBodyList.opt == key) {
			    		relativeHtml += "<option value='"+key+"' selected>"+nodePosType[key]+"</option>";  	
			    	}else {
			    		relativeHtml += "<option value='"+key+"'>"+nodePosType[key]+"</option>"; 
			    	}
		         }
		        relativeHtml += "</select>";
		        relativeHtml += "<div class='btn-wrap'>" +
	            "<i class='icon icon-save'></i>" +  
	            "<i class='icon icon-add-filter group'></i>" +
	            "<i class='icon icon-trash-sm'></i>" +
	            "<i class='icon icon-arrow-sm active'></i>" +
	            "</div>" +
	            "</div>" +
	            "</div>" +            
	            "<ul class='attr-relative-drag-wrap dragEdit-wrap collapse-content collapse-content-inactive need-ajax' id='dragEdit-"+dragWrapLen+"'>" +
	            "</ul>" +
	            "</li>";         		    
			    var $html = $(relativeHtml).prependTo($(parent));
	           $html.find("select").css({"width":"15%","marginLeft":"16px"}).select2();
	}
	function initFilter(filterBodyList,nodeId, enID, parent, source) {
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
        $CPF.showLoading();
            var relativeHtml = "<li class='attr-relative'>" +
            "<div class='attr-relative-title collapse-header' source='"+source+"' entityId='"+enID+"' data-order='' data-id='"+filterBodyList.id+"'>" +
            "<div class='icon-label attr-relative-dict attr-relative'>" +
            "<i class='icon icon-attr-relative'></i><span class='text'>filter</span>" +
            "</div>" +
            "<div class='label-bar filter al-save'>" +
            "<input type='text' class='edit-input filterName text' value='"+filterBodyList.name+"'>";
            
            relativeHtml += "<select disabled class='abcattrCodeData'>";	
         
            Ajax.ajax('admin/dictionary/basicItem/getComm', {
    	    	entityId:enID
    	    }, function(data){		
    	    	var entityData = data.commList;
    	    	 /*Ajax.ajax('admin/dictionary/basicItem/getRepeatChild', {
    	    		 repeatId:enID
    	    	    }, function(data){		
    	    	    	var repeatData = data.repeatChild;
    	    	if (source == "moreAttr") {
    	    		 for(var key in repeatData) {
    	    			 if (filterBodyList.abcattrCode == repeatData[key].code) {
    	    				 relativeHtml += "<option selected value='"+repeatData[key].code+"'>"+repeatData[key].cnName+"</option>";
    	    			 } else {
    	    				 relativeHtml += "<option value='"+repeatData[key].code+"'>"+repeatData[key].cnName+"</option>";
    	    			 }
    	 	         }
    	    	} else {*/
    	    		 for(var key in entityData) {
    	    			 if (filterBodyList.abcattrCode == entityData[key].code) {
    	    				 relativeHtml += "<option selected value='"+entityData[key][0]+"'>"+entityData[key][1]+"</option>"; 
    	    			 } else {
    	    				 relativeHtml += "<option value='"+entityData[key][0]+"'>"+entityData[key][1]+"</option>"; 
    	    			 }
    	    		 }
    	    	/*}*/
	        relativeHtml += "</select>";
            
            relativeHtml += "<select disabled class='node-Symbol-type'>";	
            //getCriteriaSymbol
    	    Ajax.ajax('admin/node/binFilterBody/getCriteriaSymbol', {
    	    	dataType:12
    	    }, function(data){		
    	    	var nodeSymbolType = data.symbolMap;
		    for(var key in nodeSymbolType) {
		    	if (key == filterBodyList.filterType) {
		    		relativeHtml += "<option value='"+key+"' selected>"+nodeSymbolType[key]+"</option>"; 
		    	} else {
		    		relativeHtml += "<option value='"+key+"'>"+nodeSymbolType[key]+"</option>"; 
		    	}
	         }
	        relativeHtml += "</select>"+
	        "<input type='text' class='edit-input valueStr text' value='"+filterBodyList.value+"'>";
            
            relativeHtml += "<select disabled class='node-ops-type'>";	
            var nodePosType = optFILTERS;
		    for(var key in nodePosType) {
		    	if(filterBodyList.opt == key) {
		    		relativeHtml += "<option value='"+key+"' selected>"+nodePosType[key]+"</option>";  	
		    	}else {
		    		relativeHtml += "<option value='"+key+"'>"+nodePosType[key]+"</option>"; 
		    	}
	         }
	        relativeHtml += "</select>";
	        relativeHtml += "<div class='btn-wrap'>" +
            "<i class='icon icon-save'></i>" +  
            /*"<i class='icon icon-add-filter group'></i>" +*/
            "<i class='icon icon-trash-sm'></i>" +
           /* "<i class='icon icon-arrow-sm active'></i>" +*/
            "</div>" +
            "</div>" +
            "</div>" +            
            "<ul class='attr-relative-drag-wrap dragEdit-wrap collapse-content collapse-content-inactive need-ajax' id='dragEdit-"+dragWrapLen+"'>" +
            "</ul>" +
            "</li>";         		    
		    var $html = $(relativeHtml).prependTo($(parent));
            $html.find("select").css({"width":"13%","marginLeft":"16px"}).select2();
            $html.find("select.node-ops-type").css({"width":"8%","marginLeft":"16px"}).select2();
            
    	    /*})*/
    	     })
    	    })
	}
	function initRFilter(filterBodyList,nodeId, enID, parent, source) {
        var entityId = enID;
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
        $CPF.showLoading();
        
        Ajax.ajax('admin/dictionary/recordRelationType/getRelation', {
			leftRecordType: entityId,
			relationType:''
		}, function(data) {			
			var relationList = data.relationList;
        Ajax.ajax('admin/node/binFilterBody/getCriteriaSymbol', {
	    	dataType:13
	    }, function(data){	
	    	
            var relativeHtml = "<li class='attr-relative'>" +
            "<div class='attr-relative-title collapse-header' source='"+source+"' entityId='' data-order='' data-id='"+filterBodyList.id+"'>" +
            "<div class='icon-label attr-relative-dict attr-relative'>" +
            "<i class='icon icon-attr-relative'></i><span class='text'>rFilter</span>" +
            "</div>" +
            "<div class='label-bar rFilter al-save'>" +
            "<input type='text' class='edit-input text' value='"+filterBodyList.name+"'>";
            
            relativeHtml += "<select disabled class='relationData'>";	
		    for(var key in relationList) {
		    	if (relationList[key].typeCode == filterBodyList.subdomain) {
		    		relativeHtml += "<option selected rightRecordType='"+relationList[key].rightRecordType+"' value='"+relationList[key].typeCode+"'>"+relationList[key].name+"</option>";
		    	} else {
		    		relativeHtml += "<option rightRecordType='"+relationList[key].rightRecordType+"' value='"+relationList[key].typeCode+"'>"+relationList[key].name+"</option>";
		    	}
	         }
	         relativeHtml += "</select>";
            
            relativeHtml += "<select disabled class='node-Symbol-type'>";	
    	    	var nodeSymbolType = data.symbolMap;
		    for(var key in nodeSymbolType) {
		    	if (key == filterBodyList.filterType) {
		    		relativeHtml += "<option value='"+key+"' selected>"+nodeSymbolType[key]+"</option>"; 
		    	} else {
		    		relativeHtml += "<option value='"+key+"'>"+nodeSymbolType[key]+"</option>"; 
		    	}
	         };
	         relativeHtml += "</select>";

	         relativeHtml += "<select disabled class='node-ops-type'>";	
            var nodePosType = optFILTERS;
            for(var key in nodePosType) {
		    	if(filterBodyList.opt == key) {
		    		relativeHtml += "<option value='"+key+"' selected>"+nodePosType[key]+"</option>";  	
		    	}else {
		    		relativeHtml += "<option value='"+key+"'>"+nodePosType[key]+"</option>"; 
		    	}
	         }
	         relativeHtml += "</select>";
	         relativeHtml += "<div class='btn-wrap'>" +
            "<i class='icon icon-save'></i>" +  
            "<i class='icon icon-add-filterGroup group'></i>" +
            "<i class='icon icon-trash-sm'></i>" +
            "<i class='icon icon-arrow-sm active'></i>" +
            "</div>" +
            "</div>" +
            "</div>" +            
            "<ul class='attr-relative-drag-wrap dragEdit-wrap collapse-content collapse-content-inactive need-ajax' id='dragEdit-"+dragWrapLen+"'>" +
            "</ul>" +
            "</li>";         		    
		    var $html = $(relativeHtml).prependTo($(parent));
            $html.find("select").css({"width":"15%","marginLeft":"16px"}).select2();
    	    })
		 })
	}

    //跟实体添加事件绑定
    $(".tree_view_panel", $page).on("click", ".icon-add, .icon-add-abc", function (e) {
        e.stopPropagation();
        
        var hasSave = judgeSave();
        if(hasSave){
            return;
        }
        removePop();
        pop(this);
        $(this).addClass("active")
    });
    
    //filterGroup 事件绑定
    $(".tree_view_panel", $page).on("click", ".icon-add-filterGroup", function (e) {
       
    	e.stopPropagation();
        var hasSave = judgeSave();
        if(hasSave){
            return;
        }
        removePop();
        popFilterGroup(this);
        $(this).addClass("active")
    });
    
  //filter 事件绑定
    $(".tree_view_panel", $page).on("click", ".icon-add-filter", function (e) {
       
    	e.stopPropagation();
        var hasSave = judgeSave();
        if(hasSave){
            return;
        }
        removePop();
        popFilter(this);
        $(this).addClass("active")
    });
    
    
    /**
     * filterGroup 页面弹出方法
    */
    function popFilterGroup(el) {
    	
        var html = "<ul class='card'>";
            html += "<li class='card-list add-filterGroup'>" +
                "<i class='icon icon-card-attr'></i>" +
                "<span class='text'>添加filterGroup</span>" +
                "</li>" +
                "</ul>";
       /* var wrap = $("#operateEdit");*/
            var wrap = $(el).closest("#tree_view_panel");
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
     * filter 页面弹出方法
    */
    function popFilter(el) {
    	
    	var source = $(el).closest(".collapse-header").attr("source");
    	
        var html = "<ul class='card'>";
            html += "<li class='card-list add-filter'>" +
                "<i class='icon icon-card-attr'></i>" +
                "<span class='text'>添加filter</span>" +
                "</li>";
            
            if (source != "moreAttr") {
            	html +="<li class='card-list add-rFilter'>" +
                "<i class='icon icon-card-attr'></i>" +
                "<span class='text'>添加rfilter</span>" +
                "</li>";
            }
               html += "</ul>";
       /* var wrap = $("#operateEdit");*/
               var wrap = $(el).closest("#tree_view_panel");
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
    

    //删除属性事件绑定
    	$(".tree_view_panel", $page).on("click", ".icon-trash, .icon-trash-sm", function (e) {
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

    //双击编辑
    $(".tree_view_panel", $page).on("dblclick", ".label-bar", function(){
		$(this).find(".edit-input").removeAttr("disabled");
    	$(this).find("select").removeAttr("disabled");
        $(this).addClass("edit");
    })
    
    //双击编辑
   $(".tree_view_panel", $page).on("dblclick", ".entity-title", function(){   
    	$(this).find(".edit-input").removeAttr("disabled");
    	$(this).find("select").removeAttr("disabled");
        $(this).addClass("edit");
    })
    
    //保存
    $(".tree_view_panel", $page).on("click", ".icon-save", function() {  
    	
    	debugger;
        var entityTitle = $(this).closest(".entity-title");
        var labelBar = $(this).closest(".label-bar");
        if(entityTitle.length > 0) {
        	//表达式回调
        	var filtersId = entityTitle.siblings(".collapse-content").find(".collapse-header").attr("data-id");
        	if(filtersId){
			Dialog.notice('保存成功', 'success');
			var page = $page.getLocatePage();
			if(page.getPageObj() instanceof Dialog){
				var afterSave = page.getPageObj().getEventCallbacks('afterSave');
				if(typeof afterSave == 'function'){
					afterSave.apply(page, [filtersId]);
				}
			}
			$page.getLocatePage().close();
        	}else{
				Dialog.notice('保存失败', 'error');
			}
        	
        	return;
        }
        
      if(labelBar.hasClass("FUNCTION-save")) {
        	FUNCTIONSave(this);
        }else if(labelBar.hasClass("ATTRVAR-save")) {
        	ATTRVARSave(this);
        }else if(labelBar.hasClass("CONSTANT-save")) {
        	CONSTANTSave(this);
        }else if(labelBar.hasClass("filters")) {
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
        	var $abcBar = $(el).closest(".label-bar");
        	var name = $abcBar.children(".edit-input").val();    	
        	var id = $abcBar.closest(".collapse-header").attr("data-id");
        	var parentId = $abcBar.closest(".collapse-content").prev(".collapse-header")
        						.attr("data-id"); 
        	var opt = $abcBar.children(".node-ops-type").find("option:selected").val();
        	//var bieCode = $abcBar.closest(".collapse-header").attr("entityid");
        	$CPF.showLoading();
        	Ajax.ajax('admin/node/binFilterBody/saveOrUpdate', {
    			 name: name,
    			 parentId: parentId,
    			 id: id,
    			 isFilters: true,
    			 dataType: 10,
    			 opt:opt,
    			 signFilter: "aggregateAttrFilter"
    		 }, function(data) {
    			if(data.code == "400") {
    				 Dialog.notice(data.msg, "warning");
    				 $CPF.closeLoading();
    				 return;
    			 }
    			
    			if (data.binFilterBody!= undefined) {
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
    				 	.attr("entityId", rightRecordType);
    			 
    			 saveSuccess(el)
    			 $CPF.closeLoading();
    		});
        };
        
        
      //删除Filters的请求方法
        function deleteAjaxFilters(id, callback) {
        	$CPF.showLoading();
        	Ajax.ajax('admin/node/binFilterBody/doDelete', {			
    			 id: id
    		 }, function(data) {
    			 
    			 if(data.code == "400") {
    				 Dialog.notice(data.msg, "error");
    				 $CPF.closeLoading();
    				 return;
    			 }
    			 
    			 callback();
    			 removePop();
    			 $CPF.closeLoading();
    		});
        };
        
        //filters删除方法
        function filtersDelete(el) {
        	
        	var $relativeBar = $(el).closest(".label-bar");
        	var id = $relativeBar.closest(".collapse-header").attr("data-id");
        	
        	var callback = function() {
        		$relativeBar.closest("li.attr-relative").remove();    		
        	};
        	if($relativeBar.hasClass("al-save")){
        		deleteAjaxFilters(id, callback);	
        	}else {
        		callback();
        		removePop();
        	}  
        };
        
    
    //删除-全部
    $(".tree_view_panel", $page).on("click", ".opera.confirm", function(e) {  
    	e.stopPropagation();    
        var entityTitle = $(".icon-trash.active").closest(".entity-title");
        var el = $(".icon-trash.active")[0];
        var labelBar = $(".icon-trash.active").closest(".label-bar");
        if (el == undefined) {
        	 el = $(".icon-trash-sm.active")[0];     
        	 labelBar = $(".icon-trash-sm.active").closest(".label-bar");
        }
        
        if(entityTitle.length > 0) {
        	entityDelete(el);
        	return;
        } 
       
        
        if (labelBar.hasClass("FUNCTION-save") || labelBar.hasClass("ATTRVAR-save")|| labelBar.hasClass("CONSTANT-save")) {
        	statExpressionDel(el);
        } else if (labelBar.hasClass("filters")) {
        	filtersDelete(el);
        } else if (labelBar.hasClass("filterGroup")) {
        	filtersDelete(el);
        } else if (labelBar.hasClass("rFilter")) {
        	filtersDelete(el);
        } else if (labelBar.hasClass("filter")) {
        	filtersDelete(el);
        } 
        
        /*if (labelBar.hasClass("attr-subselection-child")) {
        	subChildDel(el);
        	return;
        }*/
        
       /* relativeDelete(el);*/
    })
})