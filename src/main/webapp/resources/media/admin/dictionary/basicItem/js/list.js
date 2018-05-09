seajs.use(['dialog', 'ajax', '$CPF'], function(Dialog, Ajax, $CPF) {
    //创建表
   $("#createTab").click(function() {
    	Dialog.confirm("点击是， 则生成数据库表，字段", function(isYes) {
    		if (isYes) {
    			 Ajax.ajax('admin/dictionary/basicItem/createTab', '', function(data) {});
    		}
    	});
    });
    //点击 添加实体 显示div
    $("#add_entity").click(function() {
        $("#add_entity_mes").html("");
        $("#add_entity_mes").html("添加实体信息");
        $(".opera_entity").show();
    });
    //点击 添加分组 显示div
    $("#add_group").click(function() {
    	$opera = $(this).closest('.entity_ch_head').siblings('.opera_group');
    	$opera.find("#add_group_mes").html("");
    	$opera.find("#add_group_mes").html("添加分组信息");
    	$opera.show();
    });
    //点击 添加 多值属性 自身显示div
    $("#add_more").click(function() {
    	$opera = $(this).closest('.entity_ch_head').siblings('.opera_more');
    	$opera.find("#add_more_mes").html("");
    	$opera.find("#add_more_mes").html("添加多值属性信息");
        $opera.show();
    });
    
    //选中数据类型为  枚举   触发事件   普通属性
    $(".common_proper").on("change", ".enum_dataType_one", function() {
    	var options=$(".enum_dataType_one option:selected");  //获取选中的项
    	var $form = $(".opera_comm").find("#comm_opera_form1");
    	if ("枚举" == options.val()) {
    		 //如果是枚举， 则显示下拉列表
	    	$CPF.showLoading();
	        $form.find("#dictParentId").remove();
	        $form.find("#span_enum").remove();        
            //选中  则显示下拉列表       
            Ajax.ajax('admin/dictionary/dictParentItem/getDictPitem', '', function(data) {
                var dataArr = data.dictPitem;
                var str = "<span id=\"span_enum\">字典序：</span><select id=\"dictParentId\" name=\"dictParentId\">";
                for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                    str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                }
                str = str + "</select>";
                $form.find("#dataType").after(str);
                $form.find("#dataRange").val("枚举").hide();
                $form.find("#cn_dataRange").hide();
                $CPF.closeLoading();
            });            
    	} else {
    		$form.find("#cn_dataRange").show();
    		 $form.find("#dataRange").show();
    		 
    		if ("digital" == options.val()) {//数字型
    			$form.find("#dataRange").val('11');
    		} else if ("digitalDecimal" == options.val()) {//数字型小数
    			$form.find("#dataRange").val('10,2');
    		} else if ("date"== options.val() || "dateTime" == options.val()) {// 日期和时间
    			$form.find("#dataRange").val("").hide();
    			$form.find("#cn_dataRange").hide();
    		} else if ("char"== options.val() ) { //字符型
    			$form.find("#dataRange").val('32');
    		}
	        
	        $form.find("#dictParentId").remove();
	        $form.find("#span_enum").remove();    
	        $CPF.closeLoading();
        }
    	
    });
    
  //选中数据类型为  枚举   触发事件
    $(".more_proper").on("change", ".enum_daType_two", function() {
    	var options=$(this).find("option:selected");
    	//var $form = $(".more_proper").find("#more_child_opera_form1");
    	var $form  = $(this).parent().parent();
    	if ("枚举" == options.val()) {
    		 //如果是枚举， 则显示下拉列表
	    	$CPF.showLoading();
	        $form.find("#dictParentId").remove();
	        $form.find("#span_enum").remove();        
            //选中  则显示下拉列表       
            Ajax.ajax('admin/dictionary/dictParentItem/getDictPitem', '', function(data) {
                var dataArr = data.dictPitem;
                var str = "<span id=\"span_enum\">字典序：</span><select id=\"dictParentId\" name=\"dictParentId\">";
                for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                    str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                }
                str = str + "</select>";
                $form.find("#dataType").after(str);
                $form.find("#dataRange").val("枚举").hide();
                $form.find("#cn_dataRange").hide();
                $CPF.closeLoading();
            });           
    	} else {
    		 $form.find("#dataRange").show();
 	        $form.find("#cn_dataRange").show();
 	        
    		if ("digital" == options.val()) {//数字型
    			$form.find("#dataRange").val('11');
    		} else if ("digitalDecimal" == options.val()) {//数字型小数
    			$form.find("#dataRange").val('10,2');
    		} else if ("date"== options.val() || "dateTime" == options.val()) {// 日期和时间
    			$form.find("#dataRange").val("").hide();
    			$form.find("#cn_dataRange").hide();
    		} else if ("char"== options.val() ) { //字符型
    			$form.find("#dataRange").val('32');
    		}
    		
	        $form.find("#dictParentId").remove();
	        $form.find("#span_enum").remove();    
	        $CPF.closeLoading();
        }
    });
    
    
    //点击 添加普通属性加号 显示div
    $(".common_proper").on("click", ".add_comm", function() {
    	var $this = $(this);
    	var $newAdd = $this.closest(".new_add");
    	var $form = $newAdd.find(".opera_comm").find("#comm_opera_form1");
    	var $select =  $form.find("#dataType");
    	$this.siblings('.entity_attr').removeClass('pitch');
        $select.html("");
        Ajax.ajax('admin/dictionary/basicItem/getDataType', '', function(data) {
            var str = "";
            for (var key in data) {
                if ("char" == key) {
                	 str = str + " <option selected=\"selected\" value =\"" + key + "\">" + data[key] + "</option>";
                } else {
                	 str = str + " <option value =\"" + key + "\">" + data[key] + "</option>";
                }
                
            }    
            
            $select.append(str);
        });
        $form.find("#dictParentId").remove();
        $form.find("#span_enum").remove();
        
        $newAdd.find("#add_comm_mes").html("");
        $newAdd.find("#add_comm_mes").html("添加属性");
        $newAdd.find(".opera_comm").find('input').val("");
        $newAdd.find(".opera_comm").show();
        
        $form.find("#dataRange").show();
        $form.find("#cn_dataRange").show();
        $form.find("#dataRange").val("32");
    });
    //点击 添加多值属性孩子加号 显示div
    $(".more_proper").on("click", ".add_more_child", function() {
    	var $this = $(this);
    	var $newAdd = $this.closest('.new_add');
    	$this.siblings('.entity_attr').removeClass('pitch');
    	var  $form = $newAdd.find(".opera_more_child").find("#more_child_opera_form1");
    	$form.find("#dataType").html("");
        Ajax.ajax('admin/dictionary/basicItem/getDataType', '', function(data) {
            var str = "";
            for (var key in data) {
            	 if ("char" == key) {
                	 str = str + " <option selected=\"selected\" value =\"" + key + "\">" + data[key] + "</option>";
                } else {
                	 str = str + " <option value =\"" + key + "\">" + data[key] + "</option>";
                }
            }
            $(".opera_more_child").find("#more_child_opera_form1").find("#dataType").append(str);
        });
        $newAdd.find("#add_more_child_mes").html("");
        $newAdd.find("#add_more_child_mes").html("添加多值属性");
        $newAdd.find(".opera_more_child").find('input').val("");
        $newAdd.find(".opera_more_child").show();
        
        $form.find("#dataRange").show();
        $form.find("#dataRange").val("32");
        $form.find("#cn_dataRange").show();
        $form.find("#dictParentId").remove();
        $form.find("#span_enum").remove(); 
    });
    //点击确认， 添加一条二级属性
    $(".more_proper").on("click", "#twoLevelAttr_but_confirm", function() {
        var $form = $(this).closest('.twoLevelAttr_show').find('form');
        var id = $form.find("#id").val();
        var name = $form.find("#name").val();
        var relatedMultiattribute = $form.find("#relatedMultiattribute").val();
        var dictionaryAttr = $form.find("#dictionaryAttr").val();
        var valueAttr = $form.find("#valueAttr").val();
        Ajax.ajax('admin/dictionary/basicItem/saveTwoLevelAttr', {
            id: id,
            name: name,
            relatedMultiattribute: relatedMultiattribute,
            dictionaryAttr: dictionaryAttr,
            valueAttr: valueAttr
        }, function(data) {});
        $(".twoLevelAttr_show").hide();
        var entityId = $(".common_proper").attr("parentId");
        $(".new_add").remove();
        enityAttr(entityId);
    });
    //点击 添加二级属性  显示div
    $(".more_proper").on("click", "#add_twoLevelAttr", function() {
        var $form = $(this).closest('.new_add').find(".twoLevelAttr_show").find("#twoLevelAttr_form1");
        $form.find("#dictionaryAttr").empty();
        $form.find("#valueAttr").empty();
        var groupName = $form.attr("groupName");
        var groupId = $form.attr("groupId");
        $form.find("#name").val(groupName);
        $form.find("#relatedMultiattribute").val(groupId);
        Ajax.ajax('admin/dictionary/basicItem/getDataByPid', {
            id: groupId
        }, function(data) {
            var datachild = data.child;
            var dictattr_enum = "";
            var valuestr = "";
            for (var key in datachild) {
                valuestr = valuestr + " <option value =\"" + datachild[key].code + "\">" + datachild[key].cnName + "</option>";
                if (datachild[key].dataRange == '枚举') {
                    dictattr_enum = dictattr_enum + " <option value =\"" + datachild[key].code + "\">" + datachild[key].cnName + "</option>";
                }
            }
            $form.find("#dictionaryAttr").append(dictattr_enum);
            $form.find("#valueAttr").append(valuestr);
        });
        $("#twoLevelAttr_mes").html("");
        $("#twoLevelAttr_mes").html("添加二级属性");
        $(this).closest('.new_add').find(".twoLevelAttr_show").show();
    });
    //点击  编辑二级属性  显示div
    $(".more_proper").on("click", "#edit_twoLevelAttr", function() {
        var $form = $(this).closest('.new_add').find(".twoLevelAttr_show").find("#twoLevelAttr_form1");
        var twoLevelId = $(this).attr("twoLevelId");
        Ajax.ajax('admin/dictionary/basicItem/getTwoLevelAttr', {
            id: twoLevelId
        }, function(data) {
            var datatmm = data.tmm;
            $form.find("#id").val(datatmm.id);
            $form.find("#name").val(datatmm.name);
            $form.find("#relatedMultiattribute").val(datatmm.relatedMultiattribute);
            $form.find("#valueAttr").html("");
            $form.find("#dictionaryAttr").html("");
            var moreId = datatmm.relatedMultiattribute
            var dictionarystr = datatmm.dictionaryAttr;
            var valueattr = datatmm.valueAttr;
            Ajax.ajax('admin/dictionary/basicItem/getDataByPid', {
                id: moreId
            }, function(data) {
                var datachild = data.child;
                var dictattr_enum = "";
                var valuestr = "";
                for (var key in datachild) {
                    if (datachild[key].code == valueattr) {
                        valuestr = valuestr + " <option selected=\"selected\" value =\"" + datachild[key].code + "\">" + datachild[key].cnName + "</option>";
                    } else {
                        valuestr = valuestr + " <option value =\"" + datachild[key].code + "\">" + datachild[key].cnName + "</option>";
                    }
                    if (datachild[key].dataRange == '枚举') {
                        if (datachild[key].code == dictionarystr) {
                            dictattr_enum = dictattr_enum + " <option selected=\"selected\" value =\"" + datachild[key].code + "\">" + datachild[key].cnName + "</option>";
                        } else {
                            dictattr_enum = dictattr_enum + " <option value =\"" + datachild[key].code + "\">" + datachild[key].cnName + "</option>";
                        }
                    }
                }
                /*   for(var key in datachild){
							 valuestr = valuestr + " <option value =\""+datachild[key].code+"\">"+datachild[key].cnName+"</option>";
							if (datachild[key].dataRange == '枚举') {
								dictattr_enum = dictattr_enum + " <option value =\""+datachild[key].code+"\">"+datachild[key].cnName+"</option>";
							} 
					      }  */
                $form.find("#dictionaryAttr").append(dictattr_enum);
                $form.find("#valueAttr").append(valuestr);
            });
        });
        $("#twoLevelAttr_mes").html("");
        $("#twoLevelAttr_mes").html("编辑二级属性");
        $(this).closest('.new_add').find(".twoLevelAttr_show").show();
    });
    //点击 查看二级属性  显示div
    $(".more_proper").on("click", "#twoLevelAttr", function() {
        var twoLevelId = $(this).attr("twoLevelId");
        var $twochile = $(this).closest(".new_add").find(".twoLevelAttr_child");
        Ajax.ajax('admin/dictionary/basicItem/getTwoLevelAttr', {
            id: twoLevelId
        }, function(data) {
            var datatmm = data.tmm;
            var child = datatmm.childList;
            $twochile.show();
            $twochile.find("#twoLevelAttr_name").html("");
            $twochile.find("#twoLevelAttr_name").html(datatmm.name);
            $twochile.find("#edit_twoLevelAttr").attr("twoLevelId", datatmm.id);
            $twochile.find(".new_add_twolc").remove();
            var str = "";
            for (var key in child) {            	
                str = str + "<div twoLevel_chil_Id=" + child[key].id +" class='entity_attr new_add_twolc'>"+child[key].name+"<ul class='entity_ul_hide' entityId='${item.code}' status='${item.usingState}'><li><a href='javascript:void(0)' class='edit_entity'><i class='icon edit-entity'></i>编辑实体</a></li></ul><i class='icon delete'></i></div>";
            }
            str = str + "<div class=\"entity_attr new_add_twolc entity_attr_img add_comm add_twoLevelAttr_children\"><img mappingId=\"" + datatmm.id + "\" alt=\"添加二级属性\" src=\"media/admin/dictionary/basicItem/addEntity_icon.png\"></div>"
            $twochile.append(str);
        });
    });
    //点击 确认  二级属性的孩子 
    $(".more_proper").on("click", "#twoLevelAttr_child_but_confirm", function() {
        var $form = $(this).closest(".twoLevelAttr_child_show").find('form');
        var name = $form.find("#name").val();
        var mappingId = $form.find("#mappingId").val();
        var dictionaryCode = $form.find("#dictionaryCode").val();
        Ajax.ajax('admin/dictionary/basicItem/saveTwoLevelAttrChild', {
            name: name,
            mappingId: mappingId,
            dictionaryCode: dictionaryCode
        }, function(data) {});
        var entityId = $(".common_proper").attr("parentid");
        $(".new_add").remove();
        enityAttr(entityId);
    });
    //点击 添加 二级属性的孩子 显示div
    $(".more_proper").on("click", ".add_twoLevelAttr_children", function() {
        var mappingId = $(this).find('img').attr("mappingid");           
        var $form = $(".twoLevelAttr_child_show").find("#twoLevelAttr_child_form1");
        $form.find("#mappingId").val(mappingId);
        $form.find("#dictionaryCode").empty();
        Ajax.ajax('admin/dictionary/basicItem/getDictCode', {
            id: mappingId
        }, function(data) {
            var dictList = data.dictList;
            var str = "";
            for (var key in dictList) {
                str = str + " <option value =\"" + dictList[key].code + "\">" + dictList[key].name + "</option>";
            }
            $form.find("#dictionaryCode").append(str);
        });
        $(this).closest('.new_add').find(".twoLevelAttr_child_show").show();
    });
    //点击 添加关系加号 显示div
    $(".entity_relation").on("click", ".add_entity_relation", function() {
    	var $form = $(".opera_relation").find("#entity_relation_opera_form");
    	
    	$form.find('#rela_right').show();
    	$form.find('#rela_ni_code').show();
    	$form.find('#rela_ni_name').show();
    	$form.find('#symmetry').val("");
    	$form.find("#add_rela_symmetry").attr("checked",false);
    	
        Ajax.ajax('admin/dictionary/basicItem/entityList', '', function(data) {
            var entity = data.entity;
            var str = "";
            for (var p in entity) { //遍历json数组时，这么写p为索引，0,1
                str = str + "<option value=\"" + entity[p].code + "\">" + entity[p].cnName + "</option>"; 
            }
            $(".opera_relation").find("#entity_relation_opera_form").find("#rightRecordType").html("");
            $(".opera_relation").find("#entity_relation_opera_form").find("#rightRecordType").append(str);
        });
        $("#add_relation_mes").html("");
        $("#add_relation_mes").html("添加关系");
        $(".opera_relation").find("form").find("input").val("");
        $(".opera_relation").show();
    });
    // 点击取消  取消添加关系
    $(".entity_relation").on("click", "#relation_but_cancel", function() {
        $(".opera_relation").hide();
    });
    //点击取消 ， 取消添加实体
    $("#entity_but_cancel").click(function() {
        var $form1 = $(this).closest(".opera_entity").find("form");
        $form1.find("#code").removeAttr("readonly");
        $form1.find("#cnName").val("");
        $form1.find("#code").val("");
        $form1.find("#enName").val("");
        $(this).closest(".opera_entity").hide();
    });
    //点击取消 ， 取消添加分组
    $(".common_proper").on("click", "#group_but_cancel", function() {    	
        var $form1 = $(this).closest(".opera_group").find('form');
        $form1.find("#code").removeAttr("readonly");
        $form1.find("#cnName").val("");
        $form1.find("#code").val("");
        $(this).closest(".opera_group").hide();
    });
    //点击取消 ， 取消添加多值属性自身
    $(".more_proper").on("click", "#more_but_cancel", function() {
        var $form1 = $(this).closest(".opera_more").find("form");
        $form1.find("#code").removeAttr("readonly");
        $form1.find("#cnName").val("");
        $form1.find("#code").val("");
        $form1.find("#enName").val("");
        $form1.find("#tableNameDescription").val("");
        $(this).closest(".opera_more").hide();
    });
    //点击取消 ， 取消添加普通属性
    $(".common_proper").on("click", "#comm_but_cancel", function() {
        var $form1 = $(this).closest(".opera_comm").find("form");        
        $(this).closest('.new_add').find('.entity_attr').removeClass('pitch');
        $form1.find("#code").removeAttr("readonly");
        $form1.find("#cnName").val("");
        $form1.find("#code").val("");
        $form1.find("#enName").val("");
        $form1.find("#dataRange").val("");
        $(this).closest(".opera_comm").hide();
    });
    //点击取消 ， 取消添加多值属性的孩子
    $(".more_proper").on("click", "#more_child_but_cancel", function() {
        var $form1 = $(this).parent();
        $(this).closest('.new_add').find('.entity_attr').removeClass('pitch');
        $form1.find("#code").removeAttr("readonly");
        $form1.find("#cnName").val("");
        $form1.find("#code").val("");
        $form1.find("#enName").val("");
        $form1.find("#dataRange").val("");
        $(this).parent().parent().hide();
    });
    //点击取消 ， 取消添加多值属性的孩子
    $(".more_proper").on("click", "#twoLevelAttr_but_cancel", function() {
        var $form1 = $(this).parent();
        $form1.find("#id").val("");
        $form1.find("#dictionaryAttr").val("");
        $form1.find("#valueAttr").val("");
        $(this).parent().parent().hide();
    });
    //点击确认， 进行添加分组
    $(".common_proper").on("click", "#group_but_confirm", function() {
    	var formdom = $(this).closest(".opera_group").find("#group_opera_form1")[0];
        var fData = new FormData(formdom);
        Ajax.ajax('admin/dictionary/basicItem/do_add', fData, function(data) {});
        $(".opera_group").hide();
        var entityId = $(".common_proper").attr("parentid");
        $(".new_add").remove();        
        enityAttr(entityId);
    });
    //点击确认， 进行添加多值属性自身
    $(".more_proper").on("click", "#more_but_confirm", function() {
    	var formdom = $(this).closest(".opera_more").find("#more_opera_form1")[0];
        var fData = new FormData(formdom);
        Ajax.ajax('admin/dictionary/basicItem/do_add', fData, function(data) {});
        $(".opera_more").hide();
        var entityId = $(".more_proper").attr("parentId");
        $(".new_add").remove();
        enityAttr(entityId);
    });
    //点击确认， 进行添加普通属性
    $(".common_proper").on("click", "#comm_but_confirm", function() {
        //comm_opera_form1
        //设置隐藏元素的值
        var parentId = $(".common_proper").attr("parentId");
        var groupId = $(this).closest('.opera_comm').find('.opera_entity_form').attr("groupId");
        $(this).closest('.opera_comm').find("#comm_parent").val(parentId);
        $(this).closest('.opera_comm').find("#groupName").val(groupId);
        var formId = $(this).parent().attr("id");
        //var fData = new FormData(document.querySelector("#"+formId));
        var code = $(this).closest('.opera_comm').find("#code").val();
        var cnName = $(this).closest('.opera_comm').find("#cnName").val();
        var enName = $(this).closest('.opera_comm').find("#enName").val();
        var dataType = $(this).closest('.opera_comm').find("#dataType").val();
        var dataRange = $(this).closest('.opera_comm').find("#dataRange").val();
        var dictParentId = $(this).closest('.opera_comm').find("#dictParentId").val();
        var groupName = $(this).closest('.opera_comm').find("#groupName").val();
        var parent = $(this).closest('.opera_comm').find("#comm_parent").val();
        if (typeof(dictParentId) == "undefined") {
            dictParentId = "";
        }        
        Ajax.ajax('admin/dictionary/basicItem/do_add', {
            code: code,
            cnName: cnName,
            enName: enName,
            dataType: dataType,
            dataRange: dataRange,
            dictParentId: dictParentId,
            groupName: groupName,
            parent: parent
        }, function(data) {});
        $(this).closest('.opera_comm').hide();
        var entityId = $(".common_proper").attr("parentId");
        $(".new_add").remove();
        enityAttr(entityId);
    });
    
  //添加对称关系
    $(".entity_relation").on("click", "#add_rela_symmetry", function() {
    	if ($(this).is(':checked')) {
    		$(this).parent().parent().siblings('#rela_right').hide();
    		$(this).parent().parent().siblings('#rela_ni_code').hide();
    		$(this).parent().parent().siblings('#rela_ni_name').hide();
    		$(this).parent().parent().siblings('#symmetry').val('symmetry');
    	} else {
    		$(this).parent().parent().siblings('#rela_right').show();
    		$(this).parent().parent().siblings('#rela_ni_code').show();
    		$(this).parent().parent().siblings('#rela_ni_name').show();
    		$(this).parent().parent().siblings('#symmetry').val("");
    	}
    	
    });
    
    //点击确认， 进行添加关系
    $(".entity_relation").on("click", "#relation_but_confirm", function() {
        //comm_opera_form1
    	var $form = $(this).closest(".entity_relation").find('form')
        //设置隐藏元素的值
        var entityId =$(this).closest(".entity_relation").attr("entityId");
        $form.find("#leftRecordType").val(entityId);
        var leftName = $form.find("#leftName").val();
        var rightName = $form.find("#rightName").val();
        var rightRecordType = $form.find("#rightRecordType").val();
        var leftRecordType = $form.find("#leftRecordType").val();
        var symmetry = $form.find("#symmetry").val();
        Ajax.ajax('admin/dictionary/recordRelationType/doAdd', {
            leftName: leftName,
            rightName: rightName,
            rightRecordType: rightRecordType,
            leftRecordType: leftRecordType,
            symmetry:symmetry,
        }, function(data) {});
        $(".opera_relation").hide();
        var entityId = $(this).closest(".entity_relation").attr("entityid");
        $(this).closest('.entity_relation').find(".entity_attr").not(".entity_attr_img").remove();
        $(".new_add").remove();
        enityAttr(entityId);
    });
    //点击确认， 进行添加多值属性的孩子
    $(".more_proper").on("click", "#more_child_but_confirm", function() {
        //设置隐藏元素的值
    	var $form = $(this).closest('.opera_more_child').find('form');
        var groupName = $form.attr("groupName");
        var parentId = $form.attr("groupId");
        $form.find("#comm_parent").val(parentId);
        $form.find("#groupName").val(groupName);
        var formId = $form.attr("id");
        //var fData = new FormData(document.querySelector("#"+formId));
        var code = $form.find("#code").val();
        var cnName = $form.find("#cnName").val();
        var enName = $form.find("#enName").val();
        var dataType = $form.find("#dataType").val();
        var dataRange = $form.find("#dataRange").val();
        var dictParentId = $form.find("#dictParentId").val();
        var groupName = $form.find("#groupName").val();
        var parent = $form.find("#comm_parent").val();
        if (typeof(dictParentId) == "undefined") {
            dictParentId = "";
        }
        Ajax.ajax('admin/dictionary/basicItem/do_add', {
            code: code,
            cnName: cnName,
            enName: enName,
            dataType: dataType,
            dataRange: dataRange,
            dictParentId: dictParentId,
            groupName: groupName,
            parent: parent
        }, function(data) {});
        $(".opera_more_child").hide();
        var entityId = $(".more_proper").attr("parentId");
        $(".new_add").remove();
        enityAttr(entityId);
    });
    //点击确认， 进行添加操作
    $("#entity_but_confirm").click(function() {
    	var formdom = $(this).closest(".opera_entity").find("#entity_opera_form1")[0];
    	console.log(formdom);
    	console.log(new FormData(formdom));
        var fData = new FormData(formdom);        
        Ajax.ajax('admin/dictionary/basicItem/do_add', fData, function(data) {});
    });
    //给 实体列表  注册鼠标点击事件  让自己的ul显示出来    
    var $list = $(".entity_list");
    $list.on('click', '.entity_attr', function() {
    	if($(this).hasClass('active')) {
    		$(this).removeClass('active');
    	}else {
    		$(this).addClass('active')
        	.siblings().removeClass('active');
    	}
    })
    //普通属性显示 ul
    $(".common_proper").on("click", ".entity_attr", function() {
    	if($(this).hasClass('active')) {
    		$(this).removeClass('active');
    	}else {
    		$(this).addClass('active')
        	.siblings().removeClass('active');
    	}
    });
    //多值属性显示 ul
    $(".more_proper").on("click", ".entity_attr", function() {
    	if($(this).hasClass('active')) {
    		$(this).removeClass('active');
    	}else {
    		$(this).addClass('active')
        	.siblings().removeClass('active');
    	}
    });
    /* 	//二级属性的孩子显示 ul
	$(".more_proper").on("click", ".more_proper_li", function() {
		$(this).find("ul").toggle();
	}); */
    //实体关系   显示 ul
    $(".entity_relation").on("click", ".entity_relation_li", function() {
        $(this).find("ul").toggle();
    });
  
    //编辑实体获取 id    
    $(".edit_entity").click(function() {
        var entityId = $(this).parent().parent().attr("entityId");
        Ajax.ajax('admin/dictionary/basicItem/getOne', {
            id: entityId
        }, function(jsonData) {
            var $form1 = $("#entity_opera_form1");
            $form1.find("#code").val(jsonData.code);
            $form1.find("#code").attr("readonly", "readonly");
            $form1.find("#cnName").val(jsonData.cnName);
            $form1.find("#enName").val(jsonData.enName);
            $("#add_entity_mes").html("");
            $("#add_entity_mes").html("编辑实体信息");
            $(".opera_entity").show();
        });
    });
    //编辑普通属性获取 id    
    $(".common_proper").on("click", ".edit_common", function() {
    	var $this = $(this);
    	var $newAdd = $this.closest(".new_add");
    	var $form1 =$newAdd.find("form");
        var entityId = $this.closest('.entity_ul').attr("entityid");
        var ischecked = false;
        $this.closest(".entity_attr").addClass('pitch').siblings(".entity_attr").removeClass('pitch');
        Ajax.ajax('admin/dictionary/basicItem/getOne', {
            id: entityId
        }, function(jsonData) {  
        	if(jsonData.dataRange == "枚举") {
        		ischecked = true;
        	};         	
            $form1.find("#code").val(jsonData.code);
            $form1.find("#code").attr("readonly", "readonly");
            $form1.find("#cnName").val(jsonData.cnName);
            $form1.find("#enName").val(jsonData.enName);
            $form1.find("#dataRange").val(jsonData.dataRange);
            $form1.find("#edit_dataType").val(jsonData.dataType);
            $form1.find("#edit_dictParentId").val(jsonData.dictParentId);
            $form1.find("#dataType").html("");
            if(ischecked) {
    			Ajax.ajax('admin/dictionary/dictParentItem/getDictPitem', '', function(data) {
    	            var dataArr = data.dictPitem;    	            
    	            var dictParentId = $form1.find("#edit_dictParentId").val();
    	            if (dictParentId != "") {
    	                var str = "<span id=\"span_enum\">字典序：</span><select id=\"dictParentId\" name=\"dictParentId\">";
    	                for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
    	                    if (dictParentId == dataArr[p].id) {
    	                        str = str + "<option selected=\"selected\"  value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
    	                    } else {
    	                        str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
    	                    }
    	                }
    	                str = str + "</select>";
    	                $form1.find("#span_enum").remove();
    	                $form1.find("#dictParentId").remove();
    	                $form1.find("#dataRange").hide();
    	                $form1.find("#cn_dataRange").hide();
    	                $form1.find("#dataType").after(str);
    	            }
    	        });   	
            }else {
            	$form1.find("#span_enum").remove();
                $form1.find("#dictParentId").remove();	
                $form1.find("#dataRange").show();
                $form1.find("#cn_dataRange").show();
                
            	if ("日期型" == jsonData.dataType || "时间型" == jsonData.dataType) {
            		$form1.find("#dataRange").val("").hide();
	                $form1.find("#cn_dataRange").hide();
            	} 
            	
            }
            
            Ajax.ajax('admin/dictionary/basicItem/getDataType', '', function(data) {    	            
	            var dataType = $form1.find("#edit_dataType").val();
	            var str = "";
	            for (var key in data) {
	            	
	            	if (ischecked) {
	            		 if ("枚举" == data[key]) {
		 	                    str = str + " <option selected=\"selected\" value =\"" + key + "\">" + data[key] + "</option>";
		 	                } else {
		 	                    str = str + " <option value =\"" + key + "\">" + data[key] + "</option>";
		 	                }
	            	} else {
	            		 if (dataType == data[key]) {
	 	                    str = str + " <option selected=\"selected\" value =\"" + key + "\">" + data[key] + "</option>";
	 	                } else {
	 	                    str = str + " <option value =\"" + key + "\">" + data[key] + "</option>";
	 	                }
	            	}
	            	
	               
	            }
	            
	            $form1.find("#dataType").append(str);
	        });
            
            $newAdd.find("#add_comm_mes").html("");
            $newAdd.find("#add_comm_mes").html("编辑属性");        
            $newAdd.find(".opera_comm").show();
        });                                    
    });
    //编辑多值属性的孩子  获取 id    
    $(".more_proper").on("click", ".edit_more_child", function() {
    	var $this = $(this);  
    	var $newAdd = $this.closest('.new_add');
    	$this.closest(".entity_attr").addClass('pitch').siblings(".entity_attr").removeClass('pitch');
    	var $form1 = $newAdd.find('form');
        var entityId = $(this).closest('.entity_ul').attr("entityid");
        var ischecked = false;
        Ajax.ajax('admin/dictionary/basicItem/getOne', {
            id: entityId
        }, function(jsonData) {
        	if(jsonData.dataRange == "枚举") {
        		ischecked = true;
        	}        	            
            $form1.find("#code").val(jsonData.code);
            $form1.find("#code").attr("readonly", "readonly");
            $form1.find("#cnName").val(jsonData.cnName);
            $form1.find("#enName").val(jsonData.enName);
            $form1.find("#dataRange").val(jsonData.dataRange);
            $form1.find("#edit_dataType").val(jsonData.dataType);
            $form1.find("#edit_dictParentId").val(jsonData.dictParentId);
            $form1.find("#dataType").html("");  
    		if(ischecked) {
    			Ajax.ajax('admin/dictionary/dictParentItem/getDictPitem', '', function(data) {
    	            var dataArr = data.dictPitem;    	            
    	            var dictParentId = $form1.find("#edit_dictParentId").val();
    	            if (dictParentId != "") {
    	                var str = "<span id=\"span_enum\">字典序：</span><select id=\"dictParentId\" name=\"dictParentId\">";
    	                for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
    	                    if (dictParentId == dataArr[p].id) {
    	                        str = str + "<option selected=\"selected\"  value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
    	                    } else {
    	                        str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
    	                    }
    	                }
    	                str = str + "</select>";
    	                $form1.find("#dataRange").hide();
    	                $form1.find("#cn_dataRange").hide();  
    	                $form1.find("#span_enum").remove();
    	                $form1.find("#dictParentId").remove();
    	                $form1.find("#dataType").after(str);
    	            }
    	        });   	
    		}else {
    			 $form1.find("#span_enum").remove();
                 $form1.find("#dictParentId").remove();
                 $form1.find("#dataRange").show();
 	            $form1.find("#cn_dataRange").show();
 	            
 	           if ("日期型" == jsonData.dataType || "时间型" == jsonData.dataType) {
           		$form1.find("#dataRange").val("").hide();
	                $form1.find("#cn_dataRange").hide();
           	} 
    		}   
    		
    		Ajax.ajax('admin/dictionary/basicItem/getDataType', '', function(data) {    	           
	            var dataType = $form1.find("#edit_dataType").val();
	            var str = "";
	            for (var key in data) {
	            	
	            	if (ischecked) {
	            		 if ("枚举" == data[key]) {
	 	                    str = str + " <option selected=\"selected\" value =\"" + key + "\">" + data[key] + "</option>";
	 	                } else {
	 	                    str = str + " <option value =\"" + key + "\">" + data[key] + "</option>";
	 	                }
	            	} else {
	            		 if (dataType == data[key]) {
	 	                    str = str + " <option selected=\"selected\" value =\"" + key + "\">" + data[key] + "</option>";
	 	                } else {
	 	                    str = str + " <option value =\"" + key + "\">" + data[key] + "</option>";
	 	                }
	            	}
	            }
	           
	            $form1.find("#dataType").append(str);
	        }); 	
    		
            $("#add_more_child_mes").html("");
            $("#add_more_child_mes").html("编辑属性");
            $this.closest('.new_add').find(".opera_more_child").show();
        });        
    });
    //编辑分组 获取 id
    $(".common_proper").on("click", "#edit_group", function() {
    	
        var groupId = $(this).attr("groupId");
        var $this = $(this);
        var $form1 = $this.closest('.new_add').find("#group_opera_form1");
        Ajax.ajax('admin/dictionary/basicItem/getOne', {
            id: groupId
        }, function(jsonData) {            
            $form1.find("#code").val(jsonData.code);
            $form1.find("#code").attr("readonly", "readonly");
            $form1.find("#cnName").val(jsonData.cnName);
            $form1.find("#group_parent").val(jsonData.parent);
            $this.closest('.new_add').find("#add_group_mes").html("");
            $this.closest('.new_add').find("#add_group_mes").html("编辑分组信息");
            $this.closest('.new_add').find(".opera_group").show();
        });
    });
    //编辑多值属性自身 获取id
    $(".more_proper").on("click", "#edit_more", function() {
    	var $this = $(this);
    	var $newAdd = $this.closest(".new_add ");
        var groupId = $(this).attr("groupId");
        var $form1 = $newAdd.find("#more_opera_form1");
        Ajax.ajax('admin/dictionary/basicItem/getOne', {
            id: groupId
        }, function(jsonData) {            
            $form1.find("#code").val(jsonData.code);
            $form1.find("#code").attr("readonly", "readonly");
            $form1.find("#cnName").val(jsonData.cnName);
            $form1.find("#enName").val(jsonData.enName);
            $form1.find("#more_parent").val(jsonData.parent);
            $form1.find("#tableNameDescription").val(jsonData.tableNameDescription);
            $newAdd.find("#add_more_mes").html("");
            $newAdd.find("#add_more_mes").html("编辑多值属性信息");
            $newAdd.find(".opera_more").show();
        });
    });
    //过期实体获取 id    
    $(".change_status").click(function() {
        var entityId = $(this).parent().parent().attr("entityid");
        var status = $(this).parent().parent().attr("status");
        saveStatus(entityId, status);
    });
    //过期普通属性
    $(".common_proper").on("click", ".common_change_status", function() {
        var commId = $(this).closest('.entity_ul').attr("entityid");        
        var status = $(this).closest('.entity_ul').attr("status");        
        var entityId = $(".common_proper").attr("parentid");
        saveStatus(commId, status);
        $(".new_add").remove();
        enityAttr(entityId);
    });
    //过期 多值属性 属性
    $(".more_proper").on("click", ".more_child_change_status", function() {
        var commId = $(this).parent().parent().attr("entityid");
        var status = $(this).parent().parent().attr("status");        
        var entityId = $(".common_proper").attr("parentid");
        saveStatus(commId, status);
        $(".new_add").remove();
        enityAttr(entityId);
    });
    //过期函数
    function saveStatus(entityId, status) {    	
        //statusStr= normal//正常    
        //statusStr=	pastDue//过期
        if (status == '1') {
            status = 'pastDue';
        } else {
            status = 'normal';
        }
        console.log(entityId);
		console.log(status);
        Ajax.ajax('admin/dictionary/basicItem/saveStatus', {
            id: entityId,
            statusStr: status
        }, function(jsonData) {});
    }
    //在实体列表页面右键点击   的时候
    $(".get_entity_attr").click(function() {
    	$('.entity_attr').removeClass('active').removeClass('pitch');
    	$(this).closest('.entity_attr').addClass('pitch');
        var entityId = $(this).parent().parent().attr("entityId");
        $(".common_proper").show();
        $(".more_proper").show();
        $(".entity_relation").show();
        $(".common_proper").attr("parentId", entityId);
        $(".more_proper").attr("parentId", entityId);
        $(".entity_relation").attr("entityId", entityId);
        $("#group_parent").val(entityId);
        $("#more_parent").val(entityId);
        $(".new_add").remove();
        enityAttr(entityId);
    });
    //document 绑定事件
    $(document).on('click', function(e) {    	
    	var $target = $(e.target);
    	if($target.hasClass('entity_attr')) {
    		return;
    	}else {
    		$('.entity_attr').removeClass('active');
    	}
    })
    //获取实体属性的ajax
    function enityAttr(entityId) {
        Ajax.ajax('admin/dictionary/basicItem/attrByPid', {
            parentId: entityId
        }, function(jsonData) {
            var commonArr = jsonData.commonProper; //普通属性
            
            for (var i = 0; i < commonArr.length; i++) {
                var str = "<div class=\"new_add clear-fix\">" + 
                			"<div title=\"code:"+commonArr[i].code+",中文名称："+commonArr[i].cnName+"\" class=\"new_add_title\">" + "<span>" +commonArr[i].cnName+ "</span>"+ 
                			"<img id=\"edit_group\" groupId=\"" + commonArr[i].code + "\" src=\"media/admin/dictionary/basicItem/images/edit_ch.png\">" + 
                			"</div><div class=\"clear-fix\">";                		    
                for (var j = 0; j < commonArr[i].childList.length; j++) {                    
                    if (commonArr[i].childList[j].usingState == '1') {
                        str = str + "<div title=\"code:"+commonArr[i].childList[j].code+", 中文名称:"+commonArr[i].childList[j].cnName+",  英文名称:"+commonArr[i].childList[j].enName+",数据类型："+commonArr[i].childList[j].dataType+", 数据长度："+commonArr[i].childList[j].dataRange+", 字典序："+ commonArr[i].childList[j].dictParentId  +"  \" class=\"entity_attr\">" + commonArr[i].childList[j].cnName + "<ul class=\"entity_ul\" entityId=\"" + commonArr[i].childList[j].code + "\" status=\"" + commonArr[i].childList[j].usingState + "\">" + "<li><a href=\"javascript:void(0)\" class=\"edit_common\"><i class=\"icon edit-entity\"></i>编辑属性</a></li>" + "<li><a href=\"javascript:void(0)\" class=\"common_change_status\"><i class=\"icon stale-entity\"></i>过期实体"
                    } else {
                        str = str + "<div title=\"code:"+commonArr[i].childList[j].code+", 中文名称:"+commonArr[i].childList[j].cnName+",  英文名称:"+commonArr[i].childList[j].enName+",数据类型："+commonArr[i].childList[j].dataType+", 数据长度："+commonArr[i].childList[j].dataRange+", 字典序："+ commonArr[i].childList[j].dictParentId  +"  \" class=\"entity_attr stale\">" + commonArr[i].childList[j].cnName + "<ul class=\"entity_ul\" entityId=\"" + commonArr[i].childList[j].code + "\" status=\"" + commonArr[i].childList[j].usingState + "\">" + "<li><a href=\"javascript:void(0)\" class=\"edit_common\"><i class=\"icon edit-entity\"></i>编辑属性</a></li>" + "<li><a href=\"javascript:void(0)\" class=\"common_change_status\"><i class=\"icon stale-entity\"></i>解除过期"
                    }
                    str = str + "</a>" + "</li>" + "</ul>" + "<i class=\"icon delete\"></i>" + "</div>";
                }
                str = str + "<div class=\"entity_attr entity_attr_img add_comm\">" +
							    "<img alt=\"添加普通属性\" src=\"media/admin/dictionary/basicItem/addEntity_icon.png\">" +
							"</div></div>"	
				str = str + "<div class=\"opera_comm\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/opera_entity_icon.png\"><span id=\"add_comm_mes\"></span><form groupName="+commonArr[i].cnName+"  groupId="+commonArr[i].code+"   id=\"comm_opera_form1\" class=\"opera_entity_form\"><input type=\"hidden\" id=\"groupName\" name=\"groupName\" value=\"\"><input type=\"hidden\" id=\"comm_parent\" name=\"parent\" value=\"\"><input type=\"hidden\" id=\"edit_dataType\" value=\"\"><input type=\"hidden\" id=\"edit_dictParentId\" value=\"\"><input type=\"hidden\" name=\"code\" id=\"code\" /><div><span class=\"opera_entity_label\">中文名称</span><input type=\"text\" name=\"cnName\" id=\"cnName\" /></div><div><span class=\"opera_entity_label\">英文名称</span><input type=\"text\" name=\"enName\" id=\"enName\" /></div><div><span class=\"opera_entity_label\" id=\"cn_dataType\">数据类型</span><select id=\"dataType\" class=\"enum_dataType_one\" name=\"dataType\"></select></div><div><span class=\"opera_entity_label\" id=\"cn_dataRange\">数据长度</span><input type=\"text\" name=\"dataRange\" id=\"dataRange\"></div></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"comm_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"comm_but_confirm\">确认</span></div></div>";
                str = str + "<div class=\"opera_group\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/opera_entity_icon.png\"><span class=\"opera_entity_img\" id=\"add_group_mes\"></span><form id=\"group_opera_form1\" class=\"opera_entity_form\"><input type=\"hidden\" name=\"code\" id=\"code\" /><input type=\"hidden\" id=\"group_parent\" name=\"parent\" value=\"\"><input type=\"hidden\" name=\"dataType\" value=\"group\"><div><span class=\"opera_entity_label\">中文名称</span><input type=\"text\" name=\"cnName\" id=\"cnName\" /></div></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"group_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"group_but_confirm\">确认</span></div></div>";
                str = str + "</div>";                
                $(".common_proper").append(str);
            }
            var moreArr = jsonData.moreProper; //多值属性
            for (var i = 0; i < moreArr.length; i++) {
                var str = "<div class=\"new_add clear-fix\">" + 
                			"<div title=\"code:"+moreArr[i].code+", 中文名称："+moreArr[i].cnName+", 英文名称："+moreArr[i].enName+", 表描述:"+moreArr[i].tableNameDescription+"\" class=\"new_add_title\">" + "<span>" + moreArr[i].cnName+ "</span>"+
                			"<img id=\"edit_more\" groupId=\"" + moreArr[i].code + "\" src=\"media/admin/dictionary/basicItem/images/edit_ch.png\">"                			
                if (moreArr[i].twoLevelAttr == null) {
                    str = str + "<div class=\"two-level-attr\" id=\"add_twoLevelAttr\" ><a href=\"javascript:void(0)\"\">添加二级属性</a></div>"
                } else {
                    str = str + "<div class=\"two-level-attr\" id=\"twoLevelAttr\" twoLevelId=\"" + moreArr[i].twoLevelAttr + "\"><a href=\"javascript:void(0)\"\">查看二级属性</a></div>"
                }  
                str = str + "</div><div class=\"clear-fix\">";
                for (var j = 0; j < moreArr[i].childList.length; j++) {                    
                    if (moreArr[i].childList[j].usingState == '1') {
                        str = str + "<div title=\"code:"+moreArr[i].childList[j].code+", 中文名称:"+moreArr[i].childList[j].cnName+",  英文名称:"+moreArr[i].childList[j].enName+",数据类型："+moreArr[i].childList[j].dataType+", 数据长度："+moreArr[i].childList[j].dataRange+", 字典序："+ moreArr[i].childList[j].dictParentId  +"  \" class=\"entity_attr\">"  + moreArr[i].childList[j].cnName + "<ul class=\"entity_ul\" entityId=\"" + moreArr[i].childList[j].code + "\" status=\"" + moreArr[i].childList[j].usingState + "\">" + "<li><a href=\"javascript:void(0)\" class=\"edit_more_child\"><i class=\"icon edit-entity\"></i>编辑属性</a></li>" + "<li><a href=\"javascript:void(0)\" class=\"more_child_change_status\"><i class=\"icon stale-entity\"></i>过期实体"
                    } else {
                        str = str + "<div title=\"code:"+moreArr[i].childList[j].code+", 中文名称:"+moreArr[i].childList[j].cnName+",  英文名称:"+moreArr[i].childList[j].enName+",数据类型："+moreArr[i].childList[j].dataType+", 数据长度："+moreArr[i].childList[j].dataRange+", 字典序："+ moreArr[i].childList[j].dictParentId  +"  \" class=\"entity_attr stale\">" + moreArr[i].childList[j].cnName + "<ul class=\"entity_ul\" entityId=\"" + moreArr[i].childList[j].code + "\" status=\"" + moreArr[i].childList[j].usingState + "\">" + "<li><a href=\"javascript:void(0)\" class=\"edit_more_child\"><i class=\"icon edit-entity\"></i>编辑属性</a></li>" + "<li><a href=\"javascript:void(0)\" class=\"more_child_change_status\"><i class=\"icon stale-entity\"></i>解除过期"
                    }
                    str = str + "</a></li>" + "</ul>" + "<i class=\"icon delete\"></i>"+ "</div>";
                }
                str = str + "<div class=\"entity_attr entity_attr_img add_more_child\">" +
							    "<img alt=\"添加多值属性\" src=\"media/admin/dictionary/basicItem/addEntity_icon.png\">" +
							"</div></div>"
                str = str + "<div class=\"opera_more_child\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/opera_entity_icon.png\"><span id=\"add_more_child_mes\"></span><form groupName=\"" + moreArr[i].cnName + "\" groupId=\"" + moreArr[i].code + "\" id=\"more_child_opera_form1\" class=\"opera_entity_form\"><input type=\"hidden\" id=\"groupName\" name=\"groupName\" value=\"\"><input type=\"hidden\" id=\"comm_parent\" name=\"parent\" value=\"\"><input type=\"hidden\" id=\"edit_dataType\" value=\"\"><input type=\"hidden\" id=\"edit_dictParentId\" value=\"\"><input type=\"hidden\" name=\"code\" id=\"code\"/><div><span class=\"opera_entity_label\">中文名称</span><input type=\"text\" name=\"cnName\" id=\"cnName\"/></div><div><span class=\"opera_entity_label\">英文名称</span><input type=\"text\" name=\"enName\" id=\"enName\"/></div><div><span class=\"opera_entity_label\" id=\"cn_dataType\">数据类型</span><select id=\"dataType\" class=\"enum_daType_two\" name=\"dataType\"></select></div><div><span class=\"opera_entity_label\" id=\"cn_dataRange\">数据长度</span><input type=\"text\" name=\"dataRange\" id=\"dataRange\" /></div></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"more_child_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"more_child_but_confirm\">确认</span></div></div>";                
                //这里是显示二级信息以及二级的孩子数据 start
                str = str + "<div class=\"entity_list twoLevelAttr_child clear-fix\"><div class=\"new_add_title\"><span id=\"twoLevelAttr_name\"></span><img twoLevelId=\"\" id=\"edit_twoLevelAttr\" src=\"media/admin/dictionary/basicItem/images/edit_ch.png\"></div><div class=\"clear-fix\"></div></div>";
                //end
                //添加编辑二级属性 div		
                str = str + "<div class=\"twoLevelAttr_show\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/opera_entity_icon.png\"><span class=\"opera_entity_img\">二级属性</span><form groupName=\"" + moreArr[i].cnName + "\" groupId=\"" + moreArr[i].code + "\" id=\"twoLevelAttr_form1\" class=\"opera_entity_form\"><input type=\"hidden\" id=\"id\" name=\"id\" value=\"\"><div><span class=\"opera_entity_label\">名称</span><input type=\"text\" readonly=\"readonly\" id=\"name\" name=\"name\" value=\"\"></div><div><span class=\"opera_entity_label\">对应多值id</span><input type=\"text\" readonly=\"readonly\" id=\"relatedMultiattribute\" name=\"relatedMultiattribute\" value=\"\"></div><div><span class=\"opera_entity_label\">字典枚举</span><select id=\"dictionaryAttr\" name=\"dictionaryAttr\"></select></div><div><span class=\"opera_entity_label\">普通属性值</span><select id=\"valueAttr\" name=\"valueAttr\"></select></div></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"twoLevelAttr_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"twoLevelAttr_but_confirm\">确认</span></div></div>";
                //添加编辑二级属性的孩子 div		
                str = str + "<div class=\"twoLevelAttr_child_show\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/opera_entity_icon.png\"><span class=\"opera_entity_img\">添加二级属性</span><form groupName=\"" + moreArr[i].cnName + "\" groupId=\"" + moreArr[i].code + "\" id=\"twoLevelAttr_child_form1\" class=\"opera_entity_form\"><div><span class=\"opera_entity_label\">名称</span><input type=\"text\" id=\"name\" name=\"name\" value=\"\"></div><div><span class=\"opera_entity_label\">字典名称</span><select id=\"dictionaryCode\" name=\"dictionaryCode\"></select></div><input type=\"hidden\" id=\"mappingId\" name=\"mappingId\" value=\"\"></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"twoLevelAttr_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"twoLevelAttr_child_but_confirm\">确认</span></div></div>";
                str = str + "<div class=\"opera_more\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/opera_entity_icon.png\"><span class=\"opera_entity_img\" id=\"add_more_mes\"></span><form id=\"more_opera_form1\" class=\"opera_entity_form\"><input type=\"hidden\" name=\"code\" id=\"code\"/><input type=\"hidden\" id=\"more_parent\" name=\"parent\" value=\"\"><input type=\"hidden\" name=\"dataType\" value=\"repeat\"><div><span class=\"opera_entity_label\">中文名称</span><input type=\"text\" name=\"cnName\" id=\"cnName\"/><br></div><div><span class=\"opera_entity_label\">英文名称</span><input type=\"text\" name=\"enName\" id=\"enName\"/></div><div><span class=\"opera_entity_label\">表描述</span><textarea name=\"tableNameDescription\" id=\"tableNameDescription\" rows=\"\" cols=\"\"></textarea><br></div></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"more_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"more_but_confirm\">确认</span></div></div>";
                str = str + "</div>";
                $(".more_proper").append(str);
            }
            //实体关系
            var entityRela = jsonData.entityRela;
            var str = "";
            for (var i = 0; i < entityRela.length; i++) {
            	str = str + "<div class=\"entity_attr\">" + entityRela[i].name + "</div>"               
            }
            $(".entity_relation_list").find(".entity_attr").not(".entity_attr_img").remove();
            $(".entity_relation_list").prepend(str);
        });
    }
})