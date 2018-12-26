seajs.use(['dialog', 'ajax', '$CPF'], function(Dialog, Ajax, $CPF) {
	var $page = $("#entity-wrap");
	//验证 不能为空
	function notNull(data) {
    	var reg = /^\s*$/g;
    	return reg.test(data);
    }
	
	function checkEntityAndMore($form) {
		var $cnName = $form.find("#cnName");
        var $enName = $form.find("#enName");
        var $dataRange = $form.find("#dataRange");
        //中文名
         if (notNull($cnName.val())) {
        	 $cnName.siblings("#req").remove();
        	 $cnName.after(" <span id=\"req\" style=\"color: red;\">必填项</span>");
        	 return false;
         } else {
        	 $cnName.siblings("#req").remove();
         }
         
         //英文名
         /*if (notNull($enName.val())) {
        	 $enName.siblings("#req").remove();
        	 $enName.after(" <span id=\"req\" style=\"color: red;\">必填项</span>");
        	 return false;
         } else {
        	 $enName.siblings("#req").remove();
         }*/
         
         //数据长度
         if (notNull($dataRange.val())) {
        	 $dataRange.siblings("#req").remove();
        	 $dataRange.after(" <span id=\"req\" style=\"color: red;\">必填项</span>");
        	 return false;
         } else {
        	 $dataRange.siblings("#req").remove();
         }
		
		return true;
	}
	
	//验证表单为必填项
	 function checkForm($form) {
	    	var $cnName = $form.find("#cnName");
	        var $enName = $form.find("#enName");
	        var $dataRange = $form.find("#dataRange");
	        //中文名
	         if (notNull($cnName.val())) {
	        	 $cnName.siblings("#req").remove();
	        	 $cnName.after(" <span id=\"req\" style=\"color: red;\">必填项</span>");
	        	 return false;
	         } else {
	        	 $cnName.siblings("#req").remove();
	         }
	         
	         //数据长度
	         if (notNull($dataRange.val())) {
	        	 $dataRange.siblings("#req").remove();
	        	 $dataRange.after(" <span id=\"req\" style=\"color: red;\">必填项</span>");
	        	 return false;
	         } else {
	        	 $dataRange.siblings("#req").remove();
	         }
	         
	         return true;
	    }
	 
	 //验证二级属性不为空
	 function checkTwoForm($form) {
		//二级属性
	        var $dictionaryAttr = $form.find("#dictionaryAttr");
	        var $valueAttr =  $form.find("#valueAttr");
      //验证二级属性 必填
	      if ($dictionaryAttr.val()== null) {
	     	 $dictionaryAttr.siblings("#req").remove();
	     	 $dictionaryAttr.after(" <span id=\"req\" style=\"color: red;\">必填项,请在多值属性中添加枚举类型</span>");
	     	 return false; 
	      } else {
	     	 $dictionaryAttr.siblings("#req").remove();
	     	
	      }
	      if ($valueAttr.val() == null) {
	     	$valueAttr.siblings("#req").remove();
	     	 $valueAttr.after(" <span id=\"req\" style=\"color: red;\">必填项,请在多值属性中添加普通类型</span>");
	     	 return false; 
	      } else {
	    	  $valueAttr.siblings("#req").remove();
	      }
	      return true;
	 }
	 
	 //验证二级属性的孩子
	 function twoChildCheck($form) {
		 var $name = $form.find("#name");
	     var $dictionaryCode =  $form.find("#dictionaryCode");
	   //中文名
         if (notNull($name.val())) {
        	 $name.siblings("#req").remove();
        	 $name.after(" <span id=\"req\" style=\"color: red;\">必填项</span>");
        	 return false;
         } else {
        	 $name.siblings("#req").remove();
         }
	     
         if ($dictionaryCode.val() == null) {
        	 $dictionaryCode.siblings("#req").remove();
        	 $dictionaryCode.after(" <span id=\"req\" style=\"color: red;\">必填项,请在多值属性中添加普通类型</span>");
 	     	 return false; 
 	      } else {
 	    	 $dictionaryCode.siblings("#req").remove();
 	      }
		 
		 return true;
	 }
	
	 //验证关系
	 function checkRela($form) {
		var $rightRecordType = $form.find("#rightRecordType");
		var $leftName = $form.find("#leftName");
		var $rightName = $form.find("#rightName");
		
		 //中文名
         if (notNull($leftName.val())) {
        	 $leftName.siblings("#req").remove();
        	 $leftName.after(" <span id=\"req\" style=\"color: red;\">必填项</span>");
        	 return false;
         } else {
        	 $leftName.siblings("#req").remove();
         }
         
         //中文名
         if (notNull($rightName.val())) {
        	 $rightName.siblings("#req").remove();
        	 $rightName.after(" <span id=\"req\" style=\"color: red;\">必填项</span>");
        	 return false;
         } else {
        	 $rightName.siblings("#req").remove();
         }
		 
         if ($rightRecordType.val() == null) {
        	 $rightRecordType.siblings("#req").remove();
        	 $rightRecordType.after(" <span id=\"req\" style=\"color: red;\">必填项</span>");
 	     	 return false; 
 	      } else {
 	    	 $rightRecordType.siblings("#req").remove();
 	      }
         
		 return true;
	 }
	 
	 
    //创建表
   $("#createTab", $page).click(function() {
    	Dialog.confirm("点击是， 则生成数据库表，字段", function(isYes) {
    		if (isYes) {
    			$CPF.showLoading();
    			 Ajax.ajax('admin/dictionary/basicItem/createTab', '', function(data) {
    				 $CPF.closeLoading();
    			 });
    			 
    		}
    	});
    });
   
   //删除实体 
   $(".entity-list", $page).on("click", ".delete_entity", function() {
	   var entityid = $(this).parent().parent().attr("entityId");
	   Dialog.confirm("删除可能会引起数据错误， 是否确认删除", function(isYes) {
	   		if (isYes) {
	   			Ajax.ajax('admin/dictionary/basicItem/delete', {
	   			   id:entityid
	   		   }, function(data) {
	   	           
	   	       });
	   		}
	   });
   });
   
 //删除分组
   $(".common_proper", $page).on("click", "#group_but_del", function() {
      var $form = $(this).closest(".opera_group").find("#group_opera_form1");
      var groupId = $form.find("#code").val();
      var entityId = $(".common_proper").attr("parentid");
       Dialog.confirm("删除可能会引起数据错误， 是否确认删除", function(isYes) {
            if (isYes) {
                Ajax.ajax('admin/dictionary/basicItem/delete', {
                   id:groupId
               }, function(data) {
            	   $form.parent().parent().remove();    
               });
            }
       });
   });
   
 //删除多值属性本身
   $(".more_proper", $page).on("click", "#more_but_del", function() {
       var entityId = $(".more_proper").attr("parentId");
       var $form = $(this).closest(".opera_more").find("#more_opera_form1");
      var groupId = $form.find("#code").val();
      Dialog.confirm("删除可能会引起数据错误， 是否确认删除", function(isYes) {
            if (isYes) {
                Ajax.ajax('admin/dictionary/basicItem/delete', {
                   id:groupId
               }, function(data) {
            	   $form.parent().parent().remove();    
               });
            }
       });
   });
   
   //删除属性
   $(".entity-list", $page).on("click", ".delete_attr", function() {
	   var entityid = $(this).parent().parent().attr("entityId");
	   var attrNode =  $(this).parent().parent().parent();
	   var patentId = $(this).attr("patentId");
	   Dialog.confirm("删除可能会引起数据错误， 是否确认删除", function(isYes) {
	   		if (isYes) {
	   			Ajax.ajax('admin/dictionary/basicItem/delete', {
	   			   id:entityid
	   		   }, function(data) {
	   			attrNode.remove();
	   	       });
	   		}
	   });
   });
   
   //删除二级属性本身
   $(".more_proper", $page).on("click", "#twoLevelAttr_but_del", function() {
	   var $form = $(this).closest('.twoLevelAttr_show').find('form');
       var twoId = $form.find("#id").val();
       var $divno = $(this).parent().parent();
      var $nodetwo =  $divno.siblings(".twoLevelAttr_child");
      var entityId = $(".common_proper").attr("parentid");
       Dialog.confirm("删除可能会引起数据错误， 是否确认删除", function(isYes) {
            if (isYes) {
                Ajax.ajax('admin/dictionary/basicItem/twoattr_delete', {
                   id:twoId
               }, function(data) {
            	  /* $divno.hide();
            	   $nodetwo.remove();*/
            	   
            	   $(".new_add").remove();
     	   	      enityAttr(entityId);
               });
            }
       });
   });
   
 //删除二级属性的孩子
   $(".more_proper", $page).on("click", ".delete_twoLevelAttr_chil", function() {
	   var $nodepa = $(this).parent().parent().parent();
	   var twoId = $(this).attr("twoId");
      var entityId = $(".common_proper").attr("parentid");
       Dialog.confirm("删除可能会引起数据错误， 是否确认删除", function(isYes) {
            if (isYes) {
                Ajax.ajax('admin/dictionary/basicItem/twoattr_chil_delete', {
                   code:twoId
               }, function(data) {
            	   $nodepa.remove();
               });
            }
       });
   });
   
   //删除关系
   $(".entity-list", $page).on("click", ".delete_rela", function() {
	   var typeCode = $(this).parent().parent().attr("typeCode");
	   
	   var patentId = $(this).attr("patentId");
	   
	   Dialog.confirm("删除可能会引起数据错误， 是否确认删除", function(isYes) {
	   		if (isYes) {
	   			Ajax.ajax('admin/dictionary/recordRelationType/delete', {
	   			   id:typeCode
	   		   }, function(data) {
	   			relaAttr(patentId);
	   	       });
	   		}
	   });
   });
   
   
   //编辑关系名称
   $(".entity-list", $page).on("click", ".edit_rela", function() {
	   var typeCode = $(this).parent().parent().attr("typeCode");
	   
	   var patentId = $(this).attr("patentId");
	  
	  Ajax.ajax('admin/dictionary/recordRelationType/edit', {
		  typeCode:typeCode
	  }, function(data) {
		  
		  var strl = "";
		  Ajax.ajax('admin/dictionary/recordRelationType/getRelationType', '', function(data2) {
          	var reTypeMap = data2.reTypeMap;
              for (var h in reTypeMap) { //遍历json数组时，这么写p为索引，0,1
             	 
             	 if (reTypeMap[h] == data.recordRelationType.relationType) {
             		strl = strl + "<option selected value=\"" + h + "\">" + reTypeMap[h] + "</option>"; 
             	 } else {
             		strl = strl + "<option value=\"" + h + "\">" + reTypeMap[h] + "</option>"; 
             	 }
             	 
              }
         	 	
              $(".opera_relation_edit").children("#entity_relation_form_edit").find("#relationType").html("");
              $(".opera_relation_edit").children("#entity_relation_form_edit").find("#relationType").css("width","30%").select2().append(strl).trigger("change");
          });
		  
		  $(".opera_relation_edit").children("#entity_relation_form_edit").find("#name").val(data.recordRelationType.name);
		  $(".opera_relation_edit").children("#entity_relation_form_edit").find("#typeCode").val(data.recordRelationType.typeCode);
		  $(".opera_relation_edit").show();
	  });
   });
   
   
   // 点击取消  取消编辑关系名称
   $(".entity_relation", $page).on("click", "#relation_but_cancel_edit", function() {
       $(".opera_relation_edit").hide();
   });
   
   // 点击确认  保存编辑后的关系名称
   $(".entity_relation", $page).on("click", "#relation_but_confirm_edit", function() {
	  var $ore =  $(this).closest(".opera_relation_edit");
	  var $form =  $ore.children("#entity_relation_form_edit");
	  var name =  $form.find("#name").val();
	  var typeCode = $form.find("#typeCode").val();
	  var relationType = $form.find("#relationType").val();
	  
	   Ajax.ajax('admin/dictionary/recordRelationType/do_edit', {
			  typeCode:typeCode,
			  name:name,
			  relationType,relationType
		  }, function(data) {
			  if (data.code == 200) {
				  var recordRelationType = data.recordRelationType;
				  relaAttr(recordRelationType.leftRecordType);
			  } else {
				  Dialog.notice(msg, "error");
			  }
			  
			  $ore.hide();
		  });
   });
   
    //点击 添加实体 显示div
    $("#add_entity", $page).click(function() {
    	$("#entity_opera_form1").find("#code").val("");
    	$("#entity_opera_form1").find("#cnName").val("");
    	$("#entity_opera_form1").find("#enName").val("");
    	$("#entity_opera_form1").find("#description").val("");
    	$CPF.showLoading();
    	Ajax.ajax('admin/dictionary/basicItem/getDictPitem', '', function(data) {
            var dataArr = data.dictPitem;
            if (data.code == 200) {
            	var str = "";
            	for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
            		str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
            	}
            	$("#entity_opera_form1").find("#cascadedict").append(str);
            	$("#entity_opera_form1").find("#cascadedict").css("width","20%").select2();
            } else {
            	Dialog.notice("标签字典加载失败", "error");
            }
            $CPF.closeLoading();
        });          
    	  $(".common_proper").hide();
          $(".more_proper").hide();
          $(".entity_relation").hide();
        $("#add_entity_mes").html("");
        $("#add_entity_mes").html("添加实体信息");
        $(".opera_entity").show();
    });
    //点击 添加分组 显示div
    $("#add_group", $page).click(function() {
    	$opera = $(this).closest('.entity_ch_head').siblings('.opera_group');
    	$opera.find("#add_group_mes").html("");
    	$opera.find("#add_group_mes").html("添加分组信息");
    	$opera.find("#group_opera_form1").find("#cnName").val("");
    	$opera.show();
    });
    //点击 添加 多值属性 自身显示div
    $("#add_more", $page).click(function() {
    	$opera = $(this).closest('.entity_ch_head').siblings('.opera_more');
    	$opera.find("#add_more_mes").html("");
    	$opera.find("#add_more_mes").html("添加多值属性信息");
    	var $form1 = $opera.find("#more_opera_form1");
    	$form1.find("#code").removeAttr("readonly");
        $form1.find("#cnName").val("");
        $form1.find("#code").val("");
        $form1.find("#enName").val("");
        $form1.find("#tableNameDescription").val("");
        $opera.show();
    });
    
    //选中数据类型为  枚举   触发事件   普通属性
    $(".common_proper", $page).on("change", ".enum_dataType_one", function() {  
    	var $this = $(this);
    	var options=$(".enum_dataType_one option:selected");  //获取选中的项
    	var $form = $this.closest(".opera_comm").find("#comm_opera_form1");
    	$form.find("#cn_needHistory").hide();
		$form.find("#needHistory").hide();
		$form.find("#needHistory").find("option[value='1']").attr("selected",true);
		$form.find("#needHistory").find("option[value='0']").removeAttr("selected");
		
    	if ("14" == $this.val()) {   
    		 //如果是枚举， 则显示下拉列表
	    	$CPF.showLoading();
	        $form.find("#dictParentId").remove();
	        $form.find("#s2id_dictParentId").remove();
	        $form.find("#span_enum").remove();
	        $form.find("#refType").remove();
	        $form.find("#refType_enum").remove(); 
	        $form.find("#s2id_refType").remove();
            //选中  则显示下拉列表       
            Ajax.ajax('admin/dictionary/basicItem/getDictPitem', '', function(data) {
            	var dictParentId = $form.find("#edit_dictParentId").val();
                var dataArr = data.dictPitem;
                var str = "<span id=\"span_enum\">字典序：</span><select id=\"dictParentId\" name=\"dictParentId\">";
                if (dictParentId == "") {
                	for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                        str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                    }
                }else {
                	for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                        if (dictParentId == dataArr[p].id) {
                            str = str + "<option selected=\"selected\"  value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                        } else {
                            str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                        }
                    }
                }
                
                str = str + "</select>";               
                $form.find("#dataType").after(str);                
                $form.find("#dictParentId").css("width","30%").select2();
                $form.find("#dataRange").val("ENUMTYPE").hide();
                $form.find("#cn_dataRange").hide();                  
                $CPF.closeLoading();
            });            
    	}if ("1401" == $this.val()) {   //枚举类型多选
    		 //如果是枚举类型多选， 则显示下拉列表
	    	$CPF.showLoading();
	        $form.find("#dictParentId").remove();
	        $form.find("#s2id_dictParentId").remove();
	        $form.find("#span_enum").remove();
	        $form.find("#refType").remove();
	        $form.find("#refType_enum").remove(); 
	        $form.find("#s2id_refType").remove();
            //选中  则显示下拉列表       
            Ajax.ajax('admin/dictionary/basicItem/getDictPitem', '', function(data) {
            	var dictParentId = $form.find("#edit_dictParentId").val();
                var dataArr = data.dictPitem;
                var str = "<span id=\"span_enum\">字典序：</span><select id=\"dictParentId\" name=\"dictParentId\">";
                if (dictParentId == "") {
                	for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                        str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                    }
                }else {
                	for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                        if (dictParentId == dataArr[p].id) {
                            str = str + "<option selected=\"selected\"  value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                        } else {
                            str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                        }
                    }
                }
                
                str = str + "</select>";               
                $form.find("#dataType").after(str);                
                $form.find("#dictParentId").css("width","30%").select2();
                $form.find("#dataRange").val("ENUMTYPE_MULTI").hide();
                $form.find("#cn_dataRange").hide();                  
                $CPF.closeLoading();
            });            
    	} else if("11" == $this.val()) {//如果是引用类型
    		 //如果是引用类型， 则显示下拉列表
	    	$CPF.showLoading();
	        $form.find("#dictParentId").remove();
	        $form.find("#s2id_dictParentId").remove();
	        $form.find("#span_enum").remove(); 	
	        
	        $form.find("#refType").remove();
	        $form.find("#refType_enum").remove(); 
	        $form.find("#s2id_refType").remove();
            //选中  则显示下拉列表       
            Ajax.ajax('admin/dictionary/basicItem/referenceTypeEntityList', '', function(data) {
            	
                var entityList = data.entity;
                var str = "<span id=\"refType_enum\">引用实体：</span><select id=\"refType\" name=\"refType\">";
            	for (var p in entityList) { //遍历json数组时，这么写p为索引，0,1
                    str = str + "<option value=\"" + entityList[p].code + "\">" + entityList[p].cnName + "</option>"; 
                }
                
                str = str + "</select>";               
                $form.find("#dataType").after(str);                
                $form.find("#refType").css("width","30%").select2();
                $form.find("#dataRange").val("32").hide();
                $form.find("#cn_dataRange").hide();                 
                $CPF.closeLoading();
            });            
    		 
    	} else if ("17" == $this.val()) {//级联类型
    		$CPF.showLoading();
	        $form.find("#dictParentId").remove();
	        $form.find("#s2id_dictParentId").remove();
	        $form.find("#span_enum").remove();
	        $form.find("#refType").remove();
	        $form.find("#refType_enum").remove(); 
	        $form.find("#s2id_refType").remove();
            //选中  则显示下拉列表       
            Ajax.ajax('admin/dictionary/basicItem/getCascaseDictPitem', '', function(data) {
            	var dictParentId = $form.find("#edit_dictParentId").val();
                var dataArr = data.dictPitem;
                var str = "<span id=\"span_enum\">字典序：</span><select id=\"dictParentId\" name=\"dictParentId\">";
                if (dictParentId == "") {
                	for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                        str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                    }
                }else {
                	for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                        if (dictParentId == dataArr[p].id) {
                            str = str + "<option selected=\"selected\"  value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                        } else {
                            str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                        }
                    }
                }
                
                str = str + "</select>";               
                $form.find("#dataType").after(str);                
                $form.find("#dictParentId").css("width","30%").select2();
                $form.find("#dataRange").val("32").show();
               $form.find("#cn_dataRange").show();               
                $CPF.closeLoading();
            }); 
    	} else if ("52" == $this.val()) {   //字符型预设
    		 //如果是字符型预设， 则显示下拉列表
	    	$CPF.showLoading();
	        $form.find("#dictParentId").remove();
	        $form.find("#s2id_dictParentId").remove();
	        $form.find("#span_enum").remove();
	        $form.find("#refType").remove();
	        $form.find("#refType_enum").remove(); 
	        $form.find("#s2id_refType").remove();
            //选中  则显示下拉列表       
            Ajax.ajax('admin/dictionary/basicItem/getDictPitem', '', function(data) {
            	var dictParentId = $form.find("#edit_dictParentId").val();
                var dataArr = data.dictPitem;
                var str = "<span id=\"span_enum\">字典序：</span><select id=\"dictParentId\" name=\"dictParentId\">";
                if (dictParentId == "") {
                	for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                        str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                    }
                }else {
                	for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                        if (dictParentId == dataArr[p].id) {
                            str = str + "<option selected=\"selected\"  value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                        } else {
                            str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                        }
                    }
                }
                
                str = str + "</select>";               
                $form.find("#dataType").after(str);                
                $form.find("#dictParentId").css("width","30%").select2();
                $form.find("#dataRange").val("32").show();
                $form.find("#cn_dataRange").show();        
                $CPF.closeLoading();
            });            
    	}else {    		
    		$form.find("#cn_dataRange").show();
    		 $form.find("#dataRange").show();
    		 $form.find("#dataRange").val('32');
    		if ("1" == $this.val()) {//数字型
    			$form.find("#dataRange").val('11');
    		} else if ("15" == $this.val()) {//数字型小数
    			$form.find("#dataRange").val('10,2');
    		} else if ("6"== $this.val() ) {// 日期
    			$form.find("#dataRange").val("DATE").hide();
    			$form.find("#cn_dataRange").hide();
    		} else if ( "7" == $this.val()) {//时间
    			$form.find("#dataRange").val("DATETIME").hide();
    			$form.find("#cn_dataRange").hide();
    		} else if ("5"== $this.val() ) { //字符型
    			$form.find("#dataRange").val('32');
    		} else if ("8" == $this.val()) {
    			$form.find("#cn_needHistory").show();
    			$form.find("#needHistory").show();
    			$form.find("#needHistory").find("option[value='1']").removeAttr("selected");
    			$form.find("#needHistory").find("option[value='0']").attr("selected",true);
    			$form.find("#dataRange").val("BYTES").hide();
    			$form.find("#cn_dataRange").hide();
    		}
	        
    		 $form.find("#refType").remove();
 	        $form.find("#refType_enum").remove(); 
 	        $form.find("#s2id_refType").remove();
	        $form.find("#dictParentId").remove();
	        $form.find("#s2id_dictParentId").remove();
	        $form.find("#span_enum").remove(); 	        
	        $CPF.closeLoading();
        }
    	
    });
    
  //选中数据类型为  枚举   触发事件
    $(".more_proper", $page).on("change", ".enum_daType_two", function() {
    	var options=$(this).find("option:selected");    	
    	var $form  = $(this).parent().parent();
    	var entityId = $form.attr("entityId");
    	
    	$form.find("#cn_needHistory").hide();
		$form.find("#needHistory").hide();
		$form.find("#needHistory").find("option[value='1']").attr("selected",true);
		$form.find("#needHistory").find("option[value='0']").removeAttr("selected");
    	
    	if ("14" == options.val()) {    		
    		 //如果是枚举， 则显示下拉列表
	    	$CPF.showLoading();
	        $form.find("#dictParentId").remove();
	        $form.find("#s2id_dictParentId").remove();
	        $form.find("#span_enum").remove();  
	        
	        $form.find("#refType").remove();
	        $form.find("#refType_enum").remove(); 
	        $form.find("#s2id_refType").remove();
            //选中  则显示下拉列表       	        
            Ajax.ajax('admin/dictionary/basicItem/getDictPitem', '', function(data) {
                var dataArr = data.dictPitem;
                var dictParentId = $form.find("#edit_dictParentId").val();
                var str = "<span id=\"span_enum\">字典序：</span><select id=\"dictParentId\" name=\"dictParentId\">";
                if(dictParentId == "") {
                	for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                        str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                    }                	                                        
                }else {
                	for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                        if (dictParentId == dataArr[p].id) {
                            str = str + "<option selected=\"selected\"  value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                        } else {
                            str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                        }
                    }
                }
                
                str = str + "</select>";
                $form.find("#dataType").after(str);
                $form.find("#dictParentId").css("width","30%").select2();
                $form.find("#dataRange").val("ENUMTYPE").hide();
                $form.find("#cn_dataRange").hide();
                $CPF.closeLoading();
            });           
    	}if ("1401" == options.val()) {   //枚举类型多选 		
    		 //如果是枚举， 则显示下拉列表
	    	$CPF.showLoading();
	        $form.find("#dictParentId").remove();
	        $form.find("#s2id_dictParentId").remove();
	        $form.find("#span_enum").remove();  
	        
	        $form.find("#refType").remove();
	        $form.find("#refType_enum").remove(); 
	        $form.find("#s2id_refType").remove();
            //选中  则显示下拉列表       	        
            Ajax.ajax('admin/dictionary/basicItem/getDictPitem', '', function(data) {
                var dataArr = data.dictPitem;
                var dictParentId = $form.find("#edit_dictParentId").val();
                var str = "<span id=\"span_enum\">字典序：</span><select id=\"dictParentId\" name=\"dictParentId\">";
                if(dictParentId == "") {
                	for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                        str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                    }                	                                        
                }else {
                	for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                        if (dictParentId == dataArr[p].id) {
                            str = str + "<option selected=\"selected\"  value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                        } else {
                            str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                        }
                    }
                }
                
                str = str + "</select>";
                $form.find("#dataType").after(str);
                $form.find("#dictParentId").css("width","30%").select2();
                $form.find("#dataRange").val("ENUMTYPE_MULTI").hide();
                $form.find("#cn_dataRange").hide();
                $CPF.closeLoading();
            });           
    	} else if("11" == options.val()) {//如果是引用类型
    		 //如果是引用类型， 则显示下拉列表
	        $CPF.showLoading();
	        $form.find("#dictParentId").remove();
	        $form.find("#s2id_dictParentId").remove();
	        $form.find("#span_enum").remove(); 
	        
	        $form.find("#refType").remove();
	        $form.find("#refType_enum").remove(); 
	        $form.find("#s2id_refType").remove();
            //选中  则显示下拉列表       
            Ajax.ajax('admin/dictionary/basicItem/referenceTypeEntityList', '', function(data) {
            	
                var entityList = data.entity;
                var str = "<span id=\"refType_enum\">引用实体：</span><select id=\"refType\" name=\"refType\">";
            	for (var p in entityList) { //遍历json数组时，这么写p为索引，0,1
                    str = str + "<option value=\"" + entityList[p].code + "\">" + entityList[p].cnName + "</option>"; 
                }
                
                str = str + "</select>";               
                $form.find("#dataType").after(str);                
                $form.find("#refType").css("width","30%").select2();
                $form.find("#dataRange").val("32").hide();
                $form.find("#cn_dataRange").hide();                 
                $CPF.closeLoading();
            });            
    		 
    	} else if ("17" == options.val()) {//级联类型
    		$CPF.showLoading();
	        $form.find("#dictParentId").remove();
	        $form.find("#s2id_dictParentId").remove();
	        $form.find("#span_enum").remove();
	        $form.find("#refType").remove();
	        $form.find("#refType_enum").remove(); 
	        $form.find("#s2id_refType").remove();
            //选中  则显示下拉列表       
            Ajax.ajax('admin/dictionary/basicItem/getCascaseDictPitem', '', function(data) {
            	var dictParentId = $form.find("#edit_dictParentId").val();
                var dataArr = data.dictPitem;
                var str = "<span id=\"span_enum\">字典序：</span><select id=\"dictParentId\" name=\"dictParentId\">";
                if (dictParentId == "") {
                	for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                        str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                    }
                }else {
                	for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                        if (dictParentId == dataArr[p].id) {
                            str = str + "<option selected=\"selected\"  value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                        } else {
                            str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                        }
                    }
                }
                
                str = str + "</select>";               
                $form.find("#dataType").after(str);                
                $form.find("#dictParentId").css("width","30%").select2();
                $form.find("#dataRange").val("32").show();
               $form.find("#cn_dataRange").show();               
                $CPF.closeLoading();
            }); 
    	} if ("52" == options.val()) {    	
    		 //如果是字符型预设， 则显示下拉列表
	    	$CPF.showLoading();
	        $form.find("#dictParentId").remove();
	        $form.find("#s2id_dictParentId").remove();
	        $form.find("#span_enum").remove();  
	        
	        $form.find("#refType").remove();
	        $form.find("#refType_enum").remove(); 
	        $form.find("#s2id_refType").remove();
            //选中  则显示下拉列表       	        
            Ajax.ajax('admin/dictionary/basicItem/getDictPitem', '', function(data) {
                var dataArr = data.dictPitem;
                var dictParentId = $form.find("#edit_dictParentId").val();
                var str = "<span id=\"span_enum\">字典序：</span><select id=\"dictParentId\" name=\"dictParentId\">";
                if(dictParentId == "") {
                	for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                        str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                    }                	                                        
                }else {
                	for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                        if (dictParentId == dataArr[p].id) {
                            str = str + "<option selected=\"selected\"  value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                        } else {
                            str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                        }
                    }
                }
                
                str = str + "</select>";
                $form.find("#dataType").after(str);
                $form.find("#dictParentId").css("width","30%").select2();
                $form.find("#dataRange").val("32").show();
                $form.find("#cn_dataRange").show();
                $CPF.closeLoading();
            });           
    	}else {    		
    		var isAddBtn = $form.closest(".opera_more_child").prev("div").find(".pitch").hasClass("add_more_child");    		
    		 $form.find("#dataRange").show();
 	         $form.find("#cn_dataRange").show();
    		if ("1" == options.val()) {//数字型
    			$form.find("#dataRange").val('11');
    		} else if ("15" == options.val()) {//数字型小数
    			$form.find("#dataRange").val('10,2');
    		} else if ("6"== options.val()) {// 日期
    			$form.find("#dataRange").val("DATE").hide();
    			$form.find("#cn_dataRange").hide();
    		} else if ("7" == options.val()) {// 时间
    			$form.find("#dataRange").val("DATETIME").hide();
    			$form.find("#cn_dataRange").hide();
    		} else if ("5"== options.val() ) { //字符型
    			$form.find("#dataRange").val('32');
    		} else if ("8" == options.val()) {
    			$form.find("#cn_needHistory").show();
    			$form.find("#needHistory").show();
    			$form.find("#needHistory").find("option[value='1']").removeAttr("selected");
    			$form.find("#needHistory").find("option[value='0']").attr("selected",true);
    			$form.find("#dataRange").val("BYTES").hide();
    			$form.find("#cn_dataRange").hide();
    		}
    		
	        $form.find("#dictParentId").remove();
	        $form.find("#s2id_dictParentId").remove();
	        $form.find("#span_enum").remove();   
	        
	        $form.find("#refType").remove();
	        $form.find("#refType_enum").remove(); 
	        $form.find("#s2id_refType").remove();
	        $CPF.closeLoading();
        }
    });
    
    //点击 添加普通属性加号 显示div
    $(".common_proper", $page).on("click", ".add_comm", function() {
    	var $this = $(this);
    	var $newAdd = $this.closest(".new_add");
    	var $form = $newAdd.find(".opera_comm").find("#comm_opera_form1");
    	var $select =  $form.find("#dataType");
    	$this.siblings('.entity_attr').removeClass('pitch');
        $select.html("");
        Ajax.ajax('admin/dictionary/basicItem/getDataType', '', function(data) {
            var str = "";
            for (var key in data) {
                if ("5" == key) {
                	 str = str + " <option selected=\"selected\" value =\"" + key + "\">" + data[key] + "</option>";
                } else {
                	 str = str + " <option value =\"" + key + "\">" + data[key] + "</option>";
                }
                
            }    
            
            $select.append(str).css("width","30%").select2();
        });
        $form.find("#dictParentId").remove();
        $form.find("#s2id_dictParentId").remove();
        $form.find("#span_enum").remove();
        
        $form.find("#refType").remove();
        $form.find("#s2id_refType").remove();
        $form.find("#refType_enum").remove();
        
        $newAdd.find("#add_comm_mes").html("");
        $newAdd.find("#add_comm_mes").html("添加属性");
        $newAdd.find(".opera_comm").find('input').val("");
        $newAdd.find(".opera_comm").show();
        
        $form.find("#dataRange").show();
        $form.find("#cn_dataRange").show();
        $form.find("#dataRange").val("32");
        $form.find("#description").val("");
        
        $form.find("#needHistory").hide();
        $form.find("#cn_needHistory").hide();
		$form.find("#needHistory").find("option[value='1']").attr("selected",true);
        $form.find("#needHistory").find("option[value='0']").removeAttr("selected");
    });
    //点击 添加多值属性孩子加号 显示div
    $(".more_proper", $page).on("click", ".add_more_child", function() {
    	var $this = $(this);
    	var $newAdd = $this.closest('.new_add');
    	$this.siblings('.entity_attr').removeClass('pitch');
    	var  $form = $newAdd.find(".opera_more_child").find("#more_child_opera_form1");
    	
    	$form.find("#dictParentId").attr("disabled", false);
    	 $form.find("#dataType").attr("disabled", false);
    	$form.find("#dataType").html("");
    	
    	$form.find("#needHistory").hide();
    	$form.find("#cn_needHistory").hide();
    	$form.find("#needHistory").find("option[value='1']").attr("selected",true);
        $form.find("#needHistory").find("option[value='0']").removeAttr("selected");

        Ajax.ajax('admin/dictionary/basicItem/getDataType', '', function(data) {
            var str = "";
            for (var key in data) {
            	 if ("5" == key) {
                	 str = str + " <option selected=\"selected\" value =\"" + key + "\">" + data[key] + "</option>";
                } else {
                	 str = str + " <option value =\"" + key + "\">" + data[key] + "</option>";
                }
            }
            $(".opera_more_child").find("#more_child_opera_form1").find("#dataType").append(str).css("width","30%").select2();            
        });
        $newAdd.find("#add_more_child_mes").html("");
        $newAdd.find("#add_more_child_mes").html("添加多值属性");
        $newAdd.find(".opera_more_child").find('input').val("");
        $newAdd.find(".opera_more_child").show();
        
        $form.find("#dataRange").show();
        $form.find("#dataRange").val("32");
        $form.find("#description").val("");
        $form.find("#cn_dataRange").show();
        $form.find("#dictParentId").remove();
        $form.find("#s2id_dictParentId").remove();
        $form.find("#span_enum").remove(); 
        
        $form.find("#refType").remove();
        $form.find("#refType_enum").remove(); 
        $form.find("#s2id_refType").remove();
    });
    //点击确认， 添加一条二级属性
    $(".more_proper", $page).on("click", "#twoLevelAttr_but_confirm", function() {
        var $form = $(this).closest('.twoLevelAttr_show').find('form');
        var id = $form.find("#id").val();
        var name = $form.find("#name").val();
        var relatedMultiattribute = $form.find("#relatedMultiattribute").val();
        var dictionaryAttr = $form.find("#dictionaryAttr").val();
        var valueAttr = $form.find("#valueAttr").val();
        var entityId = $(".common_proper").attr("parentId");
     
        if (checkTwoForm($form)) {
        	 $CPF.showLoading();
             Ajax.ajax('admin/dictionary/basicItem/saveTwoLevelAttr', {
                 id: id,
                 name: name,
                 relatedMultiattribute: relatedMultiattribute,
                 dictionaryAttr: dictionaryAttr,
                 valueAttr: valueAttr
             }, function(data) {
             	 $(".twoLevelAttr_show").hide();
            	/*$form.parent().hide();*/
             	$(".new_add").remove();
                enityAttr(entityId);
             	 
             	 
                  $CPF.closeLoading();
             });
        }
    });
    
  //点击 查看多值级联属性的孩子  显示div
    $(".more_proper", $page).on("click", ".more_show_cascadeAttr_child", function() {
       var code = $(this).closest('.entity_ul ').attr("entityid");
      var $casecadeNode = $(this).closest('.more_list').siblings(".more_cascadeAttr_child");
       Ajax.ajax('admin/dictionary/basicItem/getCascadeAttrChild', {
    	   code:code
        }, function(data) {
        	if (data.code == 200) {
        	 var cascadeAttr = data.cascadeAttrChild;
        	 $casecadeNode.find(".new_add_cascade_child").remove();
        	 var str = '';
        	 for (var key in cascadeAttr) {   
                if(cascadeAttr[key][4] == '0'){                       
                    str = str + "<div title=\"id:"+cascadeAttr[key][1]+", 名称："+cascadeAttr[key][2]+", 等级:"+cascadeAttr[key][3]+"\"  class='entity_attr new_add_cascade_child newadd'>"+cascadeAttr[key][2]+"<i class=\"icon status\"></i><ul class='entity_ul' ><li><a href='javascript:void(0)' code="+cascadeAttr[key][0]+" casCode="+cascadeAttr[key][1]+" class='more_delete_cascadeAttr_child'><i class='icon edit-entity'></i>删除属性</a></li></ul></div>"
                } else if(cascadeAttr[key][4] == '2'){                        
                    str = str + "<div title=\"id:"+cascadeAttr[key][1]+", 名称："+cascadeAttr[key][2]+", 等级:"+cascadeAttr[key][3]+"\"  class='entity_attr new_add_cascade_child stale'>"+cascadeAttr[key][2]+"<i class=\"icon status\"></i><ul class='entity_ul' ><li><a href='javascript:void(0)' code="+cascadeAttr[key][0]+" casCode="+cascadeAttr[key][1]+" class='more_delete_cascadeAttr_child'><i class='icon edit-entity'></i>删除属性</a></li></ul></div>"
                } else if(cascadeAttr[key][4] == '-1'){                       
                    str = str + "<div title=\"id:"+cascadeAttr[key][1]+", 名称："+cascadeAttr[key][2]+", 等级:"+cascadeAttr[key][3]+"\"  class='entity_attr new_add_cascade_child inerror'>"+cascadeAttr[key][2]+"<i class=\"icon status\"></i><ul class='entity_ul'  ><li><a href='javascript:void(0)' code="+cascadeAttr[key][0]+" casCode="+cascadeAttr[key][1]+" class='more_delete_cascadeAttr_child'><i class='icon edit-entity'></i>删除属性</a></li></ul></div>"
                } else if(cascadeAttr[key][4] == '1'){                        
                    str = str + "<div title=\"id:"+cascadeAttr[key][1]+", 名称："+cascadeAttr[key][2]+", 等级:"+cascadeAttr[key][3]+"\" class='entity_attr new_add_cascade_child inuse'>"+cascadeAttr[key][2]+"<i class=\"icon status\"></i><ul class='entity_ul'><li><a href='javascript:void(0)' code="+cascadeAttr[key][0]+" casCode="+cascadeAttr[key][1]+" class='more_delete_cascadeAttr_child'><i class='icon edit-entity'></i>删除属性</a></li></ul></div>"
                }
            }
        	  str = str + "<div cascadeCode='"+code+"' class=\"entity_attr new_add_cascade_child entity_attr_img  more_add_cascadeAttr_child\"><img cascadeCode=\"" + code+ "\" alt=\"添加级联属性\" src=\"media/admin/dictionary/basicItem/addEntity_icon.png\"></div>"	  
        	  $casecadeNode.append(str).show();	  
        	} else {
        		Dialog.notice("数据加载错误，重新点击！", "error");
        	}
        });
      
        $(this).closest('.more_list').siblings(".more_cascadeAttr_child").show();
    });
    
  //点击 添加多值级联属性的孩子  显示div
    $(".more_proper", $page).on("click", ".more_add_cascadeAttr_child", function() {
    	var $cascadeChilde = $(this).closest('.more_cascadeAttr_child').siblings(".more_cascadeAttr_child_show");
    	var $form = $cascadeChilde.find("#cascadeAttr_form1");
         var cascadeCode =  $(this).attr("cascadeCode");
         $form.attr("cascadeCode", cascadeCode);
         $form.find("#cnName").val("");
         $form.find("#description").val("");
       /* Ajax.ajax('admin/dictionary/basicItem/getMoreAttrByPid', {
        	cascadeCode:cascadeCode
        }, function(data) {
        	if (data.code == 200) {
        		  var commAttr = data.commAttr;
        		  var dictattr_enum='';
                  for (var i = 0;i < commAttr.length;i++){
                    dictattr_enum = dictattr_enum + " <option value =\"" + commAttr[i].code + "\">" + commAttr[i].cnName + "</option>";
                  }
                  $form.find("#casCode").append(dictattr_enum).css("width","30%").select2();
        	} else {
        		Dialog.notice("数据加载错误，重新点击！", "error");
        	}
        });*/
      
        $cascadeChilde.show();
    });
    
    //取消     隐藏多值级联属性div添加孩子
    $(".more_proper", $page).on("click", "#more_cascadeAttr_child_but_cancel", function() {
    	$(this).closest(".more_cascadeAttr_child_show").hide();
    });
    
  //点击   删除多值级联属性的孩子
    $(".more_proper", $page).on("click", ".more_delete_cascadeAttr_child", function() {
    	var code = $(this).attr("code");
    	 var $morecase = $(this).closest('.more_cascadeAttr_child').siblings(".more_list");
    	var casCode = $(this).attr("cascode");
        Ajax.ajax('admin/dictionary/basicItem/delCascaseAttrChild', {
        	code:code,
        	casCode:casCode
        }, function(data) {
        	if (data.code == 200) {
        		 Dialog.notice("删除成功！", "success");
        		 $morecase.find("[data-code-id='"+code+"']").find('.more_show_cascadeAttr_child').trigger("click");
        		  $casecadediv.hide();
        	} else {
        		Dialog.notice("删除失败，重新点击！", "error");
        	}
        });
    });
    
  //点击   确认  ，添加多值级联属性的孩子
    $(".more_proper", $page).on("click", "#more_cascadeAttr_child_but_confirm", function() {
    	var $casecadediv = $(this).closest(".more_cascadeAttr_child_show");
    	var $form = $(this).closest('.more_cascadeAttr_child_show').find("#cascadeAttr_form1");
    	var cascadecode = $form.attr("cascadecode");
    	var cascadecode = $form.attr("cascadecode");
    	var cnName = $form.find("#cnName").val();
    	var description = $form.find("#description").val();
    	
        Ajax.ajax('admin/dictionary/basicItem/saveCascaseAttrChild', {
        	code:cascadecode,
        	cnName:cnName,
        	description:description
        }, function(data) {
        	if (data.code == 200) {
        		  var cascadeAttr = data.cascadeAttr;
        		  $casecadediv.siblings(".more_list").find("[data-code-id='"+cascadecode+"']").find('.more_show_cascadeAttr_child').trigger("click");
        		  $casecadediv.hide();
        		  Dialog.notice("添加成功！", "success");
        	} else {
        		Dialog.notice(data.msg, "error");
        	}
        });
    });
    
    //点击 查看分组级联属性的孩子  显示div
    $(".common_proper", $page).on("click", ".show_cascadeAttr_child", function() {
       var code = $(this).closest('.entity_ul ').attr("entityid");
      var $casecadeNode = $(this).closest('.comm_list').siblings(".cascadeAttr_child");
       Ajax.ajax('admin/dictionary/basicItem/getCascadeAttrChild', {
    	   code:code
        }, function(data) {
        	if (data.code == 200) {
        	 var cascadeAttr = data.cascadeAttrChild;
        	 $casecadeNode.find(".new_add_cascade_child").remove();
        	 var str = '';
        	 for (var key in cascadeAttr) {   
                if(cascadeAttr[key][4] == '0'){                       
                    str = str + "<div title=\"id:"+cascadeAttr[key][1]+", 名称："+cascadeAttr[key][2]+", 等级:"+cascadeAttr[key][3]+"\"  class='entity_attr new_add_cascade_child newadd'>"+cascadeAttr[key][2]+"<i class=\"icon status\"></i><ul class='entity_ul' ><li><a href='javascript:void(0)' code="+cascadeAttr[key][0]+" casCode="+cascadeAttr[key][1]+" class='delete_cascadeAttr_child'><i class='icon edit-entity'></i>删除属性</a></li></ul></div>"
                } else if(cascadeAttr[key][4] == '2'){                        
                    str = str + "<div title=\"id:"+cascadeAttr[key][1]+", 名称："+cascadeAttr[key][2]+", 等级:"+cascadeAttr[key][3]+"\"  class='entity_attr new_add_cascade_child stale'>"+cascadeAttr[key][2]+"<i class=\"icon status\"></i><ul class='entity_ul' ><li><a href='javascript:void(0)' code="+cascadeAttr[key][0]+" casCode="+cascadeAttr[key][1]+" class='delete_cascadeAttr_child'><i class='icon edit-entity'></i>删除属性</a></li></ul></div>"
                } else if(cascadeAttr[key][4] == '-1'){                       
                    str = str + "<div title=\"id:"+cascadeAttr[key][1]+", 名称："+cascadeAttr[key][2]+", 等级:"+cascadeAttr[key][3]+"\"  class='entity_attr new_add_cascade_child inerror'>"+cascadeAttr[key][2]+"<i class=\"icon status\"></i><ul class='entity_ul'  ><li><a href='javascript:void(0)' code="+cascadeAttr[key][0]+" casCode="+cascadeAttr[key][1]+" class='delete_cascadeAttr_child'><i class='icon edit-entity'></i>删除属性</a></li></ul></div>"
                } else if(cascadeAttr[key][4] == '1'){                        
                    str = str + "<div title=\"id:"+cascadeAttr[key][1]+", 名称："+cascadeAttr[key][2]+", 等级:"+cascadeAttr[key][3]+"\" class='entity_attr new_add_cascade_child inuse'>"+cascadeAttr[key][2]+"<i class=\"icon status\"></i><ul class='entity_ul'><li><a href='javascript:void(0)' code="+cascadeAttr[key][0]+" casCode="+cascadeAttr[key][1]+" class='delete_cascadeAttr_child'><i class='icon edit-entity'></i>删除属性</a></li></ul></div>"
                }
            }
        	  str = str + "<div cascadeCode='"+code+"' class=\"entity_attr new_add_cascade_child entity_attr_img  add_cascadeAttr_child\"><img cascadeCode=\"" + code+ "\" alt=\"添加级联属性\" src=\"media/admin/dictionary/basicItem/addEntity_icon.png\"></div>"	  
        	  $casecadeNode.append(str).show();	  
        	} else {
        		Dialog.notice("数据加载错误，重新点击！", "error");
        	}
        });
      
        $(this).closest('.comm_list ').siblings(".cascadeAttr_show").show();
    });
    
    //点击 添加分组级联属性的孩子  显示div
    $(".common_proper", $page).on("click", ".add_cascadeAttr_child", function() {
    	var $cascadeChilde = $(this).closest('.cascadeAttr_child').siblings(".cascadeAttr_child_show");
    	var $form = $cascadeChilde.find("#cascadeAttr_form1");
         var cascadeCode =  $(this).attr("cascadeCode");
         $form.attr("cascadeCode", cascadeCode);
         $form.find("#cnName").val("");
         $form.find("#description").val("");
        /* $form.find("#casCode").empty();
        Ajax.ajax('admin/dictionary/basicItem/getAttrByPidGroupName', {
        	cascadeCode:cascadeCode
        }, function(data) {
        	if (data.code == 200) {
        		  var commAttr = data.commAttr;
        		  var dictattr_enum='';
                  for (var i = 0;i < commAttr.length;i++){
                    dictattr_enum = dictattr_enum + " <option value =\"" + commAttr[i].code + "\">" + commAttr[i].cnName + "</option>";
                  }
                  $form.find("#casCode").append(dictattr_enum).css("width","30%").select2();
        	} else {
        		Dialog.notice("数据加载错误，重新点击！", "error");
        	}
        });*/
      
        $cascadeChilde.show();
    });
    
    //取消     隐藏普通级联属性div添加孩子
    $(".common_proper", $page).on("click", "#cascadeAttr_child_but_cancel", function() {
    	$(this).closest(".cascadeAttr_child_show").hide();
    });
    
    
  //点击   删除分组级联属性的孩子
    $(".common_proper", $page).on("click", ".delete_cascadeAttr_child", function() {
    	var code = $(this).attr("code");
    	var $cascade = $(this).closest('.cascadeAttr_child').siblings(".comm_list");
    	var casCode = $(this).attr("cascode");
        Ajax.ajax('admin/dictionary/basicItem/delCascaseAttrChild', {
        	code:code,
        	casCode:casCode
        }, function(data) {
        	if (data.code == 200) {
        		 Dialog.notice("删除成功！", "success");
        		 $cascade.find("[data-code-id='"+code+"']").find('.show_cascadeAttr_child').trigger("click");
        		  $casecadediv.hide();
        	} else {
        		Dialog.notice("删除失败，重新点击！", "error");
        	}
        });
    });
    
  //点击   确认  ，添加分组级联属性的孩子
    $(".common_proper", $page).on("click", "#cascadeAttr_child_but_confirm", function() {
    	var $casecadediv = $(this).closest(".cascadeAttr_child_show");
    	var $form = $(this).closest('.cascadeAttr_child_show').find("#cascadeAttr_form1");
    	var cascadecode = $form.attr("cascadecode");
    	var cnName = $form.find("#cnName").val();
    	var description = $form.find("#description").val();
        Ajax.ajax('admin/dictionary/basicItem/saveCascaseAttrChild', {
        	code:cascadecode,
        	cnName:cnName,
        	description:description
        }, function(data) {
        	if (data.code == 200) {
        		  var cascadeAttr = data.cascadeAttr;
        		  $casecadediv.siblings(".comm_list").find("[data-code-id='"+cascadecode+"']").find(".show_cascadeAttr_child").trigger("click");
        		  $casecadediv.hide();
        		  Dialog.notice("添加成功！", "success");
        	} else {
        		Dialog.notice(data.msg, "error");
        	}
        });
    });
    
    //点击 添加二级属性  显示div
    $(".more_proper", $page).on("click", "#add_twoLevelAttr", function() {
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
               
                if (datachild[key].oneLevelItem.dataType == '14') {
                    dictattr_enum = dictattr_enum + " <option value =\"" + datachild[key].code + "\">" + datachild[key].cnName + "</option>";
                } else {
                	 valuestr = valuestr + " <option value =\"" + datachild[key].code + "\">" + datachild[key].cnName + "</option>";
                }
            }
            $form.find("#dictionaryAttr").append(dictattr_enum).css("width","30%").select2();
            $form.find("#valueAttr").append(valuestr).css("width","30%").select2();
        });
        $("#twoLevelAttr_mes").html("");
        $("#twoLevelAttr_mes").html("添加二级属性");
        $(this).closest('.new_add').find(".twoLevelAttr_show").show();
    });
    //点击  编辑二级属性  显示div
    $(".more_proper", $page).on("click", "#edit_twoLevelAttr", function() {
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
                    
                    if (datachild[key].oneLevelItem.dataType == '14') {
                        if (datachild[key].code == dictionarystr) {
                            dictattr_enum = dictattr_enum + " <option selected=\"selected\" value =\"" + datachild[key].code + "\">" + datachild[key].cnName + "</option>";
                        } else {
                            dictattr_enum = dictattr_enum + " <option value =\"" + datachild[key].code + "\">" + datachild[key].cnName + "</option>";
                        }
                    } else {
                    	if (datachild[key].code == valueattr) {
                            valuestr = valuestr + " <option selected=\"selected\" value =\"" + datachild[key].code + "\">" + datachild[key].cnName + "</option>";
                        } else {
                            valuestr = valuestr + " <option value =\"" + datachild[key].code + "\">" + datachild[key].cnName + "</option>";
                        }
                    }
                }
                $form.find("#dictionaryAttr").append(dictattr_enum).css("width","30%").select2();
                $form.find("#valueAttr").append(valuestr).css("width","30%").select2();
            });
        });
        $("#twoLevelAttr_mes").html("");
        $("#twoLevelAttr_mes").html("编辑二级属性");
        $(this).closest('.new_add').find(".twoLevelAttr_show").show();
    });
    //点击 查看二级属性  显示div
    $(".more_proper", $page).on("click", "#twoLevelAttr", function() {    	
        var twoLevelId = $(this).attr("twoLevelId");
        var $twochile = $(this).closest(".new_add").find(".twoLevelAttr_child");
        if($(this).hasClass("active")) {
        	$(this).removeClass("active").children("a").text("查看二级属性");
        	$twochile.hide();
        }else {
        	$(this).addClass("active").children("a").text("收起二级属性");        	
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
                    if(child[key][2] == '0'){                    	
                    	str = str + "<div title=\"id:"+child[key][0]+", 名称："+child[key][1]+", 对应字典id:"+child[key][3]+", 对应字典名称:"+child[key][4]+"\" twoLevel_chil_Id=" + child[key][0] +" class='entity_attr new_add_twolc newadd'>"+child[key][1]+"<i class=\"icon status\"></i><ul class='entity_ul' entityId='${item.code}' status='${item.usingState}'><li><a href='javascript:void(0)' twoId="+child[key][0]+" class='delete_twoLevelAttr_chil'><i class='icon edit-entity'></i>删除属性</a></li></ul></div>"
                    } else if(child[key][2] == '2'){                    	
                    	str = str + "<div title=\"id:"+child[key][0]+", 名称："+child[key][1]+", 对应字典id:"+child[key][3]+", 对应字典名称:"+child[key][4]+"\" twoLevel_chil_Id=" + child[key][0] +" class='entity_attr new_add_twolc stale'>"+child[key][1]+"<i class=\"icon status\"></i><ul class='entity_ul' entityId='${item.code}' status='${item.usingState}'><li><a href='javascript:void(0)' twoId="+child[key][0]+"  class='delete_twoLevelAttr_chil'><i class='icon edit-entity'></i>删除属性</a></li></ul></div>"
                    } else if(child[key][2] == '-1'){                    	
                    	str = str + "<div title=\"id:"+child[key][0]+", 名称："+child[key][1]+", 对应字典id:"+child[key][3]+", 对应字典名称:"+child[key][4]+"\" twoLevel_chil_Id=" + child[key][0] +" class='entity_attr new_add_twolc inerror'>"+child[key][1]+"<i class=\"icon status\"></i><ul class='entity_ul' entityId='${item.code}' status='${item.usingState}'><li><a href='javascript:void(0)' twoId="+child[key][0]+"  class='delete_twoLevelAttr_chil'><i class='icon edit-entity'></i>删除属性</a></li></ul></div>"
                    } else if(child[key][2] == '1'){                    	
                    	str = str + "<div title=\"id:"+child[key][0]+", 名称："+child[key][1]+", 对应字典id:"+child[key][3]+", 对应字典名称:"+child[key][4]+"\" twoLevel_chil_Id=" + child[key][0] +" class='entity_attr new_add_twolc inuse'>"+child[key][1]+"<i class=\"icon status\"></i><ul class='entity_ul' entityId='${item.code}' status='${item.usingState}'><li><a href='javascript:void(0)' twoId="+child[key][0]+"  class='delete_twoLevelAttr_chil'><i class='icon edit-entity'></i>删除属性</a></li></ul></div>"
                    }
                }
                
                Ajax.ajax('admin/dictionary/basicItem/getMoreParent', {
                	code: datatmm.relatedMultiattribute
                }, function(data) {
                	if (data != 2) {
                		str = str + "<div class=\"entity_attr new_add_twolc entity_attr_img add_comm add_twoLevelAttr_children\"><img mappingId=\"" + datatmm.id + "\" alt=\"添加二级属性\" src=\"media/admin/dictionary/basicItem/addEntity_icon.png\"></div>"
                	}
                	
                	$twochile.append(str);
                });
            });
        }        
    });
    //点击 确认  二级属性的孩子 
    $(".more_proper", $page).on("click", "#twoLevelAttr_child_but_confirm", function() {
        var $form = $(this).closest(".twoLevelAttr_child_show").find('form');   
        var $divform = $form.parent();
        var name = $form.find("#name").val();
        var mappingId = $form.find("#mappingId").val();
        var dictionaryCode = $form.find("#dictionaryCode").val();
        var entityId = $(".common_proper").attr("parentid");
        if (twoChildCheck($form)) {
        	 $CPF.showLoading();
        	 
        	 Ajax.ajax('admin/dictionary/basicItem/getTwoSameCount', {
                 name: name,
                 entityId: entityId,
             }, function(data) {
             	if (data == true) {
             		 Ajax.ajax('admin/dictionary/basicItem/saveTwoLevelAttrChild', {
                         name: name,
                         mappingId: mappingId,
                         dictionaryCode: dictionaryCode
                     }, function(datajson) {
                    	 $divform.hide();
                    	 var str = "<div title=\"id:"+datajson.code+", 名称："+datajson.basicItem.cnName+"\" twoLevel_chil_Id=" + datajson.code +" class='entity_attr new_add_twolc inuse'>"+datajson.basicItem.cnName+"<i class=\"icon status\"></i><ul class='entity_ul' entityId='${item.code}' status='${item.usingState}'><li><a href='javascript:void(0)' twoId="+datajson.code+" class='delete_twoLevelAttr_chil'><i class='icon edit-entity'></i>删除属性</a></li></ul></div>"
                    	 $divform.siblings(".twoLevelAttr_child").children(".add_twoLevelAttr_children ").before(str);
                     	 $CPF.closeLoading();
                     });
             	} else {
             		var $name = $form.find("#name");
             		$name.siblings("#req").remove();
             		$name.after(" <span id=\"req\" style=\"color: red;\">和普通属性名称不能相同</span>");
             		$CPF.closeLoading();
             	}
             });
        }
        
       
    });
    //点击 添加 二级属性的孩子 显示div
    $(".more_proper", $page).on("click", ".add_twoLevelAttr_children", function() {
    	
    	$(this).closest('.new_add').find(".twoLevelAttr_child_show").children("form").find("#name").val("");
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
                str = str + " <option value =\"" + dictList[key].id + "\">" + dictList[key].name + "</option>";
            }
            $form.find("#dictionaryCode").append(str).css("width","30%").select2();
        });
        $(this).closest('.new_add').find(".twoLevelAttr_child_show").show();
    });
    //点击 添加关系加号 显示div
    $(".entity_relation", $page).on("click", ".add_entity_relation", function() {
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
            var strl = "";
            Ajax.ajax('admin/dictionary/recordRelationType/getRelationType', '', function(data2) {
            	var reTypeMap = data2.reTypeMap;
                for (var h in reTypeMap) { //遍历json数组时，这么写p为索引，0,1
               	 strl = strl + "<option value=\"" + h + "\">" + reTypeMap[h] + "</option>"; 
                }
                $(".opera_relation").find("#entity_relation_opera_form").find("#leftRelationType").html("");
           	 	$(".opera_relation").find("#entity_relation_opera_form").find("#leftRelationType").css("width","30%").select2().append(strl).trigger("change");
           	 	
           	 	$(".opera_relation").find("#entity_relation_opera_form").find("#rightRelationType").html("");
        	 	$(".opera_relation").find("#entity_relation_opera_form").find("#rightRelationType").css("width","30%").select2().append(strl).trigger("change");
            });
            
            $(".opera_relation").find("#entity_relation_opera_form").find("#rightRecordType").html("");
            $(".opera_relation").find("#entity_relation_opera_form").find("#rightRecordType").css("width","30%").select2().append(str).trigger("change");            
            $("#add_relation_mes").html("");
            $("#add_relation_mes").html("添加关系");
            $(".opera_relation").find("form").find("input").val("");
            $(".opera_relation").show();
            
            $CPF.closeLoading();
        });
        
        
    });
    // 点击取消  取消添加关系
    $(".entity_relation", $page).on("click", "#relation_but_cancel", function() {
        $(".opera_relation").hide();
        $(".entity_relation_list").children(".add_entity_relation").removeClass("pitch");
    });
    //点击取消 ， 取消添加实体
    $("#entity_but_cancel", $page).click(function() {
        var $form1 = $(this).closest(".opera_entity").find("form");
        $("#add_entity").closest(".entity_attr").removeClass("pitch").siblings().removeClass("pitch");
        $form1.find("#code").removeAttr("readonly");
        $form1.find("#cnName").val("");
        $form1.find("#code").val("");
        $form1.find("#enName").val("");
        $(this).closest(".opera_entity").hide();
    });
    //点击取消 ， 取消添加分组
    $(".common_proper", $page).on("click", "#group_but_cancel", function() {    	
        var $form1 = $(this).closest(".opera_group").find('form');
        $form1.find("#code").removeAttr("readonly");
        $form1.find("#cnName").val("");
        $form1.find("#code").val("");
        $(this).closest(".opera_group").hide();
    });
    //点击取消 ， 取消添加多值属性自身
    $(".more_proper", $page).on("click", "#more_but_cancel", function() {
        var $form1 = $(this).closest(".opera_more").find("form");
        $form1.find("#code").removeAttr("readonly");
        $form1.find("#cnName").val("");
        $form1.find("#code").val("");
        $form1.find("#enName").val("");
        $form1.find("#tableNameDescription").val("");
        $(this).closest(".opera_more").hide();
    });
    //点击取消 ， 取消添加普通属性
    $(".common_proper", $page).on("click", "#comm_but_cancel", function() {
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
    $(".more_proper", $page).on("click", "#more_child_but_cancel", function() {
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
    $(".more_proper", $page).on("click", "#twoLevelAttr_but_cancel", function() {
    	$(".add_twoLevelAttr_children").removeClass("pitch");
        var $form1 = $(this).parent();
        $form1.find("#id").val("");
        $form1.find("#dictionaryAttr").val("");
        $form1.find("#valueAttr").val("");
        $(this).parent().parent().hide();
    });
    //点击确认， 进行添加分组和修改分組
    $(".common_proper", $page).on("click", "#group_but_confirm", function() {
    	var $form = $(this).closest(".opera_group").find("#group_opera_form1");
    	var formdom = $(this).closest(".opera_group").find("#group_opera_form1")[0];
    	var code = $form.children("#code").val();
        var fData = new FormData(formdom);
        var entityId = $(".common_proper").attr("parentid");
        var $form = $(this).closest(".opera_group").find("#group_opera_form1");
        //验证表单不能为空
        if(checkForm($form)) {
        	 $CPF.showLoading();
        	 Ajax.ajax('admin/dictionary/basicItem/do_add', fData, function(data) {
             	$(".opera_group").hide();
             	//------------------------
             	if ("" == code) {
             		 var str = "<div data-code-id=\""+data.code+"\" class=\"new_add clear-fix\">" + 
         			"<div class=\"new_add_title\">" + "<span title=\"code:"+data.code+",中文名称："+data.cnName+"\">" +data.cnName+ "</span>"
						str = str + "<img id=\"edit_group\" groupId=\"" + data.code + "\" src=\"media/admin/dictionary/basicItem/images/edit_ch.png\">"
						str = str + "</div><div class=\"comm_list clear-fix\">";                		    
			     	str = str + "<div class=\"entity_attr entity_attr_img add_comm\">" +
							    "<img alt=\"添加普通属性\" src=\"media/admin/dictionary/basicItem/addEntity_icon.png\">" +					"</div>"
						str = str + "</div>"	
						
						//查看级联属性的孩子start
						str = str + "<div style='display: none;' class=\"entity_list cascadeAttr_child clear-fix\"><div class=\"new_add_title\"><span>级联属性孩子</span>" 
		                str = str +"</div><div class=\"clear-fix\"></div>"
		                str = str +"</div>"
						//end
		                
		              //添加编辑级联属性的孩子  div		
		 			    str = str + "<div style='padding-left: 1.8em;display: none;' class=\"cascadeAttr_child_show\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/opera_entity_icon.png\"><span class=\"opera_entity_img\">级联属性孩子</span><form  id=\"cascadeAttr_form1\" class=\"opera_entity_form\"><input type=\"hidden\" id=\"id\" name=\"id\" value=\"\"><div><span class=\"opera_entity_label\">中文名称<span style=\"color: red;\">*</span></span><input type=\"text\" name=\"cnName\" id=\"cnName\" /></div><div><span class=\"opera_entity_label\">描述</span><textarea name=\"description\" id=\"description\"></textarea></div><div class=\"select-wrap\"></div></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"cascadeAttr_child_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"cascadeAttr_child_but_confirm\">确认</span></div></div>";
						
						
						str = str + "<div class=\"opera_comm\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/images/info.png\"><span id=\"add_comm_mes\"></span><form groupName="+data.cnName+"  groupId="+data.code+"   id=\"comm_opera_form1\" class=\"opera_entity_form\"><input type=\"hidden\" id=\"groupName\" name=\"groupName\" value=\"\"><input type=\"hidden\" id=\"comm_parent\" name=\"parent\" value=\"\"><input type=\"hidden\" id=\"edit_dataType\" value=\"\"><input type=\"hidden\" id=\"edit_dictParentId\" value=\"\"><input type=\"hidden\" name=\"code\" id=\"code\" /><div><span class=\"opera_entity_label\">中文名称<span style=\"color: red;\">*</span></span><input type=\"text\" name=\"cnName\" id=\"cnName\" /></div><div><span class=\"opera_entity_label\">英文名称</span><input type=\"text\" name=\"enName\" id=\"enName\" /></div><div class=\"select-wrap\"><span class=\"opera_entity_label\" id=\"cn_dataType\">数据类型<span style=\"color: red;\">*</span></span><select id=\"dataType\" class=\"enum_dataType_one\" name=\"dataType\"></select><span style='display:none;' class=\"opera_entity_label\" id=\"cn_needHistory\">记录历史</span><select style='width: 30%; display: none;' id=\"needHistory\" class=\"needHistory\" name=\"needHistory\"><option value='1' selected='selected'>是</option><option value='0'>否</option></select></div><div><span class=\"opera_entity_label\" id=\"cn_dataRange\">数据长度<span style=\"color: red;\">*</span></span><input type=\"text\" name=\"dataRange\" id=\"dataRange\"></div><div><span class=\"opera_entity_label\">描述</span><textarea name=\"description\" id=\"description\"></textarea></div></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"comm_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"comm_but_confirm\">确认</span></div></div>";
			         str = str + "<div class=\"opera_group\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/images/info.png\"><span class=\"opera_entity_img\" id=\"add_group_mes\"></span><form id=\"group_opera_form1\" class=\"opera_entity_form\"><input type=\"hidden\" name=\"code\" id=\"code\" /><input type=\"hidden\" id=\"group_parent\" name=\"parent\" value=\"\"><input type=\"hidden\" name=\"dataType\" value=\"16\"><div><span class=\"opera_entity_label\">中文名称<span style=\"color: red;\">*</span></span><input type=\"text\" name=\"cnName\" id=\"cnName\" /></div><div><span class=\"opera_entity_label\">描述</span><textarea name=\"description\" id=\"description\"></textarea></div></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"group_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"group_but_confirm\">确认</span><span class=\"entity-btn-confirm\" id=\"group_but_del\">删除</span></div></div>";
			         str = str + "<i class=\"icon status newadd\"></div>";
			         $(".common_proper").append(str);
             	} else {
             		var $sp = $(".common_proper").children("[data-code-id='"+code+"']").children(".new_add_title").find("span");
             		var titlestr="code:"+data.code+",中文名称："+data.cnName+"";
             		$sp.attr("title", titlestr);
             		$sp.text(data.cnName);
             	}
                 
                 $CPF.closeLoading();
             });
        }
    });
    //点击确认， 进行添加多值属性自身
    $(".more_proper", $page).on("click", "#more_but_confirm", function() {
    	var formdom = $(this).closest(".opera_more").find("#more_opera_form1")[0];
        var fData = new FormData(formdom);
        var entityId = $(".more_proper").attr("parentId");
        var $form = $(this).closest(".opera_more").find("#more_opera_form1");
        var code = $form.children("#code").val();
        if (checkEntityAndMore($form)) {
        	 $CPF.showLoading();
             Ajax.ajax('admin/dictionary/basicItem/do_add', fData, function(data) {
             	 $(".opera_more").hide();
             	 
             	 //-----------------------------------
             if ("" == code) {
            	 var str = "<div data-code-id=\""+data.code+"\" class=\"new_add clear-fix\">" + 
      			"<div  class=\"new_add_title\">" + "<span title=\"code:"+data.code+", 中文名称："+data.cnName+", 英文名称："+data.enName+", 表描述:"+data.oneLevelItem.tableNameDescription+"\">" + data.cnName+ "</span>"
				 str = str + "<img id=\"edit_more\" groupId=\"" + data.code + "\" src=\"media/admin/dictionary/basicItem/images/edit_ch.png\">"                			
			      str = str + "<div class=\"two-level-attr\" id=\"add_twoLevelAttr\" ><a href=\"javascript:void(0)\"\">添加二级属性</a></div>"
			      str = str + "</div><div class=\"clear-fix more_list\">";
			      str = str + "<div class=\"entity_attr entity_attr_img add_more_child\">" +
								    "<img alt=\"添加多值属性\" src=\"media/admin/dictionary/basicItem/addEntity_icon.png\">" +
								"</div>"
					str = str + "</div>"
			      str = str + "<div class=\"opera_more_child\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/images/info.png\"><span id=\"add_more_child_mes\"></span><form groupName=\"" + data.cnName + "\" groupId=\"" + data.code + "\" id=\"more_child_opera_form1\" class=\"opera_entity_form\"><input type=\"hidden\" id=\"groupName\" name=\"groupName\" value=\"\"><input type=\"hidden\" id=\"comm_parent\" name=\"parent\" value=\"\"><input type=\"hidden\" id=\"edit_dataType\" value=\"\"><input type=\"hidden\" id=\"edit_dictParentId\" value=\"\"><input type=\"hidden\" name=\"code\" id=\"code\"/><div><span class=\"opera_entity_label\">中文名称<span style=\"color: red;\">*</span></span><input type=\"text\" name=\"cnName\" id=\"cnName\"/></div><div><span class=\"opera_entity_label\">英文名称</span><input type=\"text\" name=\"enName\" id=\"enName\"/></div><div class=\"select-wrap\"><span class=\"opera_entity_label\" id=\"cn_dataType\">数据类型<span style=\"color: red;\">*</span></span><select id=\"dataType\" class=\"enum_daType_two\" name=\"dataType\"></select><span style='display:none;'  class=\"opera_entity_label\" id=\"cn_needHistory\">记录历史记录</span><select style='width: 30%;display:none;' id=\"needHistory\" name=\"needHistory\"><option value='1' selected='selected'>是</option><option value='0'>否</option></select></div><div><span class=\"opera_entity_label\" id=\"cn_dataRange\">数据长度<span style=\"color: red;\">*</span></span><input type=\"text\" name=\"dataRange\" id=\"dataRange\" /></div><div><span class=\"opera_entity_label\">描述</span><textarea name=\"description\" id=\"description\"></textarea></div></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"more_child_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"more_child_but_confirm\">确认</span></div></div>";                
			      //这里是显示二级信息以及二级的孩子数据 start
			      str = str + "<div class=\"entity_list twoLevelAttr_child clear-fix\"><div class=\"new_add_title\"><span id=\"twoLevelAttr_name\"></span>" 
			      	str = str + "<img twoLevelId=\"\" id=\"edit_twoLevelAttr\" src=\"media/admin/dictionary/basicItem/images/edit_ch.png\">"
			      str = str +"</div><div class=\"clear-fix\"></div>"
			      str = str +"</div>"
			      //end
			      //添加编辑二级属性 div		
			      str = str + "<div class=\"twoLevelAttr_show\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/opera_entity_icon.png\"><span class=\"opera_entity_img\">二级属性</span><form groupName=\"" + data.cnName + "\" groupId=\"" + data.code + "\" id=\"twoLevelAttr_form1\" class=\"opera_entity_form\"><input type=\"hidden\" id=\"id\" name=\"id\" value=\"\"><div><span class=\"opera_entity_label\">名称<span style=\"color: red;\">*</span></span><input type=\"text\" readonly=\"readonly\" id=\"name\" name=\"name\" value=\"\"></div><div><span class=\"opera_entity_label\">对应多值id<span style=\"color: red;\">*</span></span><input type=\"text\" readonly=\"readonly\" id=\"relatedMultiattribute\" name=\"relatedMultiattribute\" value=\"\"></div><div class=\"select-wrap\"><span class=\"opera_entity_label\">字典枚举<span style=\"color: red;\">*</span></span><select id=\"dictionaryAttr\" name=\"dictionaryAttr\"></select></div><div class=\"select-wrap\"><span class=\"opera_entity_label\">普通属性值<span style=\"color: red;\">*</span></span><select id=\"valueAttr\" name=\"valueAttr\"></select></div></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"twoLevelAttr_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"twoLevelAttr_but_confirm\">确认</span><span class=\"entity-btn-confirm\" id=\"twoLevelAttr_but_del\">删除</span></div></div>";
			      //添加编辑二级属性的孩子 div		
			      str = str + "<div class=\"twoLevelAttr_child_show\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/opera_entity_icon.png\"><span class=\"opera_entity_img\">添加二级属性</span><form groupName=\"" + data.cnName + "\" groupId=\"" + data.code + "\" id=\"twoLevelAttr_child_form1\" class=\"opera_entity_form\"><div><span class=\"opera_entity_label\">名称<span style=\"color: red;\">*</span></span><input type=\"text\" id=\"name\" name=\"name\" value=\"\"></div><div class=\"select-wrap\"><span class=\"opera_entity_label\">字典名称<span style=\"color: red;\">*</span></span><select id=\"dictionaryCode\" name=\"dictionaryCode\"></select></div><input type=\"hidden\" id=\"mappingId\" name=\"mappingId\" value=\"\"></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"twoLevelAttr_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"twoLevelAttr_child_but_confirm\">确认</span></div></div>";
			      str = str + "<div class=\"opera_more\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/opera_entity_icon.png\"><span class=\"opera_entity_img\" id=\"add_more_mes\"></span><form id=\"more_opera_form1\" class=\"opera_entity_form\"><input type=\"hidden\" name=\"code\" id=\"code\"/><input type=\"hidden\" id=\"more_parent\" name=\"parent\" value=\"\"><input type=\"hidden\" name=\"dataType\" value=\"9\"><div><span class=\"opera_entity_label\">中文名称<span style=\"color: red;\">*</span></span><input type=\"text\" name=\"cnName\" id=\"cnName\"/><br></div><div><span class=\"opera_entity_label\">英文名称<span style=\"color: red;\">*</span></span><input type=\"text\" name=\"enName\" id=\"enName\"/></div><div><span class=\"opera_entity_label\">表描述</span><textarea name=\"tableNameDescription\" id=\"tableNameDescription\" rows=\"\" cols=\"\"></textarea><br></div><div><span class=\"opera_entity_label\">描述</span><textarea name=\"description\" id=\"description\"></textarea></div></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"more_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"more_but_confirm\">确认</span><span class=\"entity-btn-confirm\" id=\"more_but_del\">删除</span></div></div>";
			      str = str + "<i class=\"icon status newadd\"></div>";
		        $(".more_proper").append(str);
             } else {
            	var titlestr="code:"+data.code+", 中文名称："+data.cnName+", 英文名称："+data.enName+", 表描述:"+data.oneLevelItem.tableNameDescription+""
            	 
            	 var $moreva = $(".more_proper").children("[data-code-id='"+code+"']");
            	$moreva.children(".new_add_title").children("span").attr("title", titlestr).text(data.cnName); 
            	
            	$moreva.children(".opera_more_child").children("form").attr("groupname", data.cnName);
            	$moreva.children(".twoLevelAttr_show").children("form").attr("groupname", data.cnName);
            	$moreva.children(".twoLevelAttr_child_show").children("form").attr("groupname", data.cnName);
             }
                 
             	 //------------------------------------
                  $CPF.closeLoading();
             });
        }
    });
    //点击确认， 进行添加普通属性
    $(".common_proper", $page).on("click", "#comm_but_confirm", function() {
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
        var description = $(this).closest('.opera_comm').find("#description").val();
        var refType = $(this).closest('.opera_comm').find("#refType").val();
        if (typeof(dictParentId) == "undefined") {
            dictParentId = "";
        }      
        
        var needHistory = $(this).closest('.opera_comm').find("#needHistory").val();
        var entityId = $(".common_proper").attr("parentId");
        var $form = $(this).closest(".opera_comm").find("#comm_opera_form1");
        
        var $commlist = $form.parent().siblings(".comm_list ");//普通属性列表
        
        //验证表单
        if (checkForm($form)) {
        	$CPF.showLoading();
        	
            	Ajax.ajax('admin/dictionary/basicItem/do_add', {
                    code: code,
                    cnName: cnName,
                    enName: enName,
                    dataType: dataType,
                    dataRange: dataRange,
                    dictParentId: dictParentId,
                    groupName: groupName,
                    parent: parent,
                    description:description,
                    refType:refType,
                    needHistory:needHistory
                }, function(data) {
                	$(this).closest('.opera_comm').hide();
                	
                	 var cnNameStr =data.cnName;
                	 
                	 if (data.oneLevelItem.dataType == "17") {
                		 cnNameStr = "<font color='red'>CM </font>" + cnNameStr;
                	 }
                	
                	if ("" == code) {//新增一个属性
                		 var str = "<div data-code-id=\""+data.code+"\" title=\"code:"+data.code+", 中文名称:"+data.cnName+",  英文名称:"+data.enName+",数据类型："+data.oneLevelItem.dataTypeCName+", 数据长度："+data.oneLevelItem.dataRange+", 字典序："+ data.oneLevelItem.dictParentId  +"  \" class=\"entity_attr newadd\">" + cnNameStr 
     	                str = str +"<ul class=\"entity_ul\" entityId=\"" + data.code + "\" status=\"" +data.usingState + "\">" + "<li><a href=\"javascript:void(0)\" class=\"edit_common\"><i class=\"icon edit-entity\"></i>编辑属性</a></li>" + "<li><a href=\"javascript:void(0)\" class=\"common_change_status\"><i class=\"icon stale-entity\"></i>过期实体</a></li>" 
                         str = str+"<li><a href=\"javascript:void(0)\" patentId=\""+data.parent+"\" class=\"delete_attr\"><i class=\"icon edit-entity\"></i>删除属性</a></li>" 
                		 
                		 if (data.oneLevelItem.dataType == "17") {
                			 str = str+"<li><a href=\"javascript:void(0)\" class=\"show_cascadeAttr_child\"><i class=\"icon edit-entity\"></i>查看级联属性孩子</a></li>";
                		 }
                		 str = str + "</ul><i class=\"icon status\"></i>" +"</div>";
                         $commlist.children(".add_comm").before(str);
                	} else {
                		var titlestr = "code:"+data.code+", 中文名称:"+data.cnName+",  英文名称:"+data.enName+",数据类型："+data.oneLevelItem.dataTypeCName+", 数据长度："+data.oneLevelItem.dataRange+", 字典序："+ data.oneLevelItem.dictParentId  +""
                		 var str =cnNameStr 
      	                str = str +"<ul class=\"entity_ul\" entityId=\"" + data.code + "\" status=\"" +data.usingState + "\">" + "<li><a href=\"javascript:void(0)\" class=\"edit_common\"><i class=\"icon edit-entity\"></i>编辑属性</a></li>" + "<li><a href=\"javascript:void(0)\" class=\"common_change_status\"><i class=\"icon stale-entity\"></i>过期实体</a></li>" 
                        str = str+"<li><a href=\"javascript:void(0)\" patentId=\""+data.parent+"\" class=\"delete_attr\"><i class=\"icon edit-entity\"></i>删除属性</a></li>"
                      	
                        if (data.oneLevelItem.dataType == "17") {
               			 str = str+"<li><a href=\"javascript:void(0)\" class=\"show_cascadeAttr_child\"><i class=\"icon edit-entity\"></i>查看级联属性孩子</a></li>";
               		 	}
                        
                        str = str + "</ul><i class=\"icon status\"></i>";
                		$commlist.children("[data-code-id='"+code+"']").attr("title", titlestr);
                		$commlist.children("[data-code-id='"+code+"']").attr("class", "entity_attr newadd");
                		$commlist.children("[data-code-id='"+code+"']").html(str);
                	}
                	
                     $form.parent().hide();
                	 $CPF.closeLoading();
                });
            	
            	 $CPF.closeLoading();
        }
    });
    
  //添加对称关系
    $(".entity_relation", $page).on("click", "#add_rela_symmetry", function() {
    	if ($(this).is(':checked')) {
    		$(this).parent().parent().siblings('#rela_right').hide();
    		$(this).parent().parent().siblings('#rela_ni_code').hide();
    		$(this).parent().parent().siblings('#rela_ni_name').find("#rightName").val("1");
    		$(this).parent().parent().siblings('#rela_ni_name').hide();
    		$(this).parent().parent().siblings('#symmetry').val('symmetry');
    	} else {
    		$(this).parent().parent().siblings('#rela_right').show();
    		$(this).parent().parent().siblings('#rela_ni_code').show();
    		$(this).parent().parent().siblings('#rela_ni_name').find("#rightName").val("");
    		$(this).parent().parent().siblings('#rela_ni_name').show();
    		$(this).parent().parent().siblings('#symmetry').val("");
    	}
    	
    });
    
    //点击确认， 进行添加关系
    $(".entity_relation", $page).on("click", "#relation_but_confirm", function() {
        //comm_opera_form1
    	var $form = $(this).closest(".entity_relation").find('form');
    	
        //设置隐藏元素的值
        var entityId =$(this).closest(".entity_relation").attr("entityId");
        $form.find("#leftRecordType").val(entityId);
        var leftName = $form.find("#leftName").val();
        var rightName = $form.find("#rightName").val();
        var rightRecordType = $form.find("#rightRecordType").val();
        var leftRecordType = $form.find("#leftRecordType").val();
        var symmetry = $form.find("#symmetry").val();
        var leftRelationType =  $form.find("#leftRelationType").val();
        var rightRelationType =  $form.find("#rightRelationType").val();
        
        if (checkRela($form)) {
        	 $CPF.showLoading();
             Ajax.ajax('admin/dictionary/recordRelationType/doAdd', {
                 leftName: leftName,
                 rightName: rightName,
                 rightRecordType: rightRecordType,
                 leftRecordType: leftRecordType,
                 symmetry:symmetry,
                 leftRelationType,leftRelationType,
                 rightRelationType,rightRelationType
             }, function(data) {
            	 
            	 if (data.code == 200) {
            		 $(".opera_relation").hide();
                     $(this).closest('.entity_relation').find(".entity_attr").not(".entity_attr_img").remove();
                     relaAttr(entityId);
                     Dialog.notice(data.msg, "success");
            	 } else {
            		 Dialog.notice(data.msg, "error");
            	 }
                 $CPF.closeLoading();
             });
        }
       
    });
    //点击确认， 进行添加多值属性的孩子
    $(".more_proper", $page).on("click", "#more_child_but_confirm", function() {
    	
    	
    	var $more_list = $(this).closest('.opera_more_child').siblings(".more_list");
    	
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
        var description = $form.find("#description").val();
        var refType = $form.find("#refType").val();
        if (typeof(dictParentId) == "undefined") {
            dictParentId = "";
        }
        var needHistory = $form.find("#needHistory").val();
        if (checkForm($form)) {
        	$CPF.showLoading();
            var entityId = $(".more_proper").attr("parentId");
            Ajax.ajax('admin/dictionary/basicItem/do_add', {
                code: code,
                cnName: cnName,
                enName: enName,
                dataType: dataType,
                dataRange: dataRange,
                dictParentId: dictParentId,
                groupName: groupName,
                parent: parent,
                description:description,
                refType:refType,
                needHistory:needHistory
            }, function(data) {
            	
            	 $(".opera_more_child").hide();
            	 //---------------------------
            	 
            	 
            	 var cnNameStr = data.cnName;
            	 if (data.oneLevelItem.dataType == '17') {
            		 cnNameStr = "<font color='red'>CM </font>" + cnNameStr;
            	 }
            	 
                 if ("" == code) {//新增一个属性
                	 var str = "<div data-code-id=\""+data.code+"\" title=\"code:"+data.code+", 中文名称:"+data.cnName+",  英文名称:"+data.enName+",数据类型："+data.oneLevelItem.dataTypeCName+", 数据长度："+data.oneLevelItem.dataRange+", 字典序："+ data.oneLevelItem.dictParentId  +"  \" class=\"entity_attr newadd\">"  + cnNameStr 
                     str = str+ "<ul class=\"entity_ul\" entityId=\"" + data.code + "\" status=\"" + data.usingState + "\">" + "<li><a href=\"javascript:void(0)\" class=\"edit_more_child\"><i class=\"icon edit-entity\"></i>编辑属性</a></li>" + "<li><a href=\"javascript:void(0)\" class=\"more_child_change_status\"><i class=\"icon stale-entity\"></i>过期实体</a></li>"
                     str = str + "<li><a href=\"javascript:void(0)\" patentId=\""+data.parent+"\" class=\"delete_attr\"><i class=\"icon edit-entity\"></i>删除属性</a></li>"
                     
                     if (data.oneLevelItem.dataType == '17') {
                         str = str+"<li><a href=\"javascript:void(0)\"  class=\"more_show_cascadeAttr_child\"><i class=\"icon edit-entity\"></i>查看级联属性孩子</a></li>";
                      }
                     
                     str = str+ "</ul> <i class=\"icon status\"></i>"+ "</div>";
                	 $more_list.children(".add_more_child").before(str);
            	} else {
            	   var titlestr="code:"+data.code+", 中文名称:"+data.cnName+",  英文名称:"+data.enName+",数据类型："+data.oneLevelItem.dataType+", 数据长度："+data.oneLevelItem.dataRange+", 字典序："+ data.oneLevelItem.dictParentId  +""
            		var str = cnNameStr 
                    str = str+ "<ul class=\"entity_ul\" entityId=\"" + data.code + "\" status=\"" + data.usingState + "\">" + "<li><a href=\"javascript:void(0)\" class=\"edit_more_child\"><i class=\"icon edit-entity\"></i>编辑属性</a></li>" + "<li><a href=\"javascript:void(0)\" class=\"more_child_change_status\"><i class=\"icon stale-entity\"></i>过期实体</a></li>"
                    str = str + "<li><a href=\"javascript:void(0)\" patentId=\""+data.parent+"\" class=\"delete_attr\"><i class=\"icon edit-entity\"></i>删除属性</a></li>"
                    if (data.oneLevelItem.dataType == '17') {
                        str = str+"<li><a href=\"javascript:void(0)\"  class=\"more_show_cascadeAttr_child\"><i class=\"icon edit-entity\"></i>查看级联属性孩子</a></li>";
                     }
                    str = str+ "</ul><i class=\"icon status\"></i>";
            	   $more_list.children("[data-code-id='"+code+"']").attr("title", titlestr);
            	   $more_list.children("[data-code-id='"+code+"']").attr("class", "entity_attr newadd");
            	   $more_list.children("[data-code-id='"+code+"']").html(str);
            	}
            	 
                 $(this).closest('.opera_more_child').hide();
            	$form.parent().hide();
                 $CPF.closeLoading();
            });
        }
    });
    //点击确认， 进行添加操作
    $("#entity_but_confirm", $page).click(function() {
    	var formdom = $(this).closest(".opera_entity").find("#entity_opera_form1")[0];
        var fData = new FormData(formdom);  
        
        var $form = $(this).closest(".opera_entity").find("#entity_opera_form1");
        
        if (checkEntityAndMore($form)) {
        	Ajax.ajax('admin/dictionary/basicItem/do_add', fData, function(data) {});
        }
    });
    
    //给 实体列表  注册鼠标点击事件  让自己的ul显示出来    
    var $list = $(".entity_list", $page);
    $list.on('click', '.entity_attr', function() {
    	var $this = $(this).closest(".entity_attr");    	
    	if($this.hasClass("entity_attr_img")){
    		$this.addClass('pitch').siblings()
    		.removeClass('pitch')
    		.removeClass('active');
    	}else {
    		if($this.hasClass('active')) {
        		$this.removeClass('active');
        	}else {        		
        		$this.addClass('active')
            	.siblings().removeClass('active');
        	}
    	}
    	
    })
    //普通属性显示 ul
    $(".common_proper", $page).on("click", ".entity_attr", function() {
    	var $this = $(this).closest(".entity_attr");
    	if($this.hasClass("entity_attr_img")){
    		$this.addClass('pitch').siblings()
    		.removeClass('pitch')
    		.removeClass('active');
    	}else {
    		if($this.hasClass('active')) {
        		$this.removeClass('active');
        	}else {
        		$this.addClass('active')
            	.siblings().removeClass('active');
        	}
    	}    	
    });
    //多值属性显示 ul
    $(".more_proper", $page).on("click", ".entity_attr", function() {
    	var $this = $(this).closest(".entity_attr");
    	if($this.hasClass("entity_attr_img")){
    		$this.addClass('pitch').siblings()
    		.removeClass('pitch')
    		.removeClass('active');
    	}else  {
    		if($this.hasClass('active')) {
        		$this.removeClass('active');
        	}else {
        		$this.addClass('active')
            	.siblings().removeClass('active');
        	}
    	}    	
    });
    /* 	//二级属性的孩子显示 ul
	$(".more_proper").on("click", ".more_proper_li", function() {
		$(this).find("ul").toggle();
	}); */
    //实体关系   显示 ul
    $(".entity_relation", $page).on("click", ".entity_relation_li", function() {
        $(this).find("ul").toggle();
    });
  
    //编辑实体获取 id    
    $(".edit_entity", $page).click(function() {
    	$('.entity_attr').removeClass('active').removeClass('pitch');
    	$(this).closest('.entity_attr').addClass('pitch');
        var entityId = $(this).parent().parent().attr("entityId");
        $(".common_proper").hide();
        $(".more_proper").hide();
        $(".entity_relation").hide();
        
        //$(".opera_entity").show();
        Ajax.ajax('admin/dictionary/basicItem/getOne', {
            id: entityId
        }, function(jsonData) {
        	
        	Ajax.ajax('admin/dictionary/basicItem/getLableObj', {
                code: jsonData.code
            }, function(data) {
            	var lableObj = data.lableObj;

                if (data.code == 200) {
                	 if (lableObj == undefined) {
                		 Dialog.notice("当前实体没有选择标签", "warning");
                		 Ajax.ajax('admin/dictionary/basicItem/getDictPitem', '', function(data) {
                             var dataArr = data.dictPitem;
                             if (data.code == 200) {
                                 var str = "";
                                 for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                                	 str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                                 }
                                 $("#entity_opera_form1").find("#cascadedict").empty().append(str);
                                 $("#entity_opera_form1").find("#cascadedict").css("width","20%").select2();
                             } else {
                                 Dialog.notice("标签字典加载失败", "error");
                             }
                             $CPF.closeLoading();
                         });  
                	 } else {
                		 
                		 var lableObj = data.lableObj.oneLevelItem;
                		 Ajax.ajax('admin/dictionary/basicItem/getDictPitem', '', function(data) {
                             var dataArr = data.dictPitem;
                             if (data.code == 200) {
                                 var str = "";
                                 for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                                	 if (lableObj.dictParentId == dataArr[p].id) {
                                		 str = str + "<option selected='selected' value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                                	 } else {
                                		 str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                                	 }
                                 }
                                 $("#entity_opera_form1").find("#cascadedict").empty().append(str);
                                 $("#entity_opera_form1").find("#cascadedict").css("width","20%").select2();
                             } else {
                                 Dialog.notice("标签字典加载失败", "error");
                             }
                             $CPF.closeLoading();
                         });  
                	 }
                } else {
                	Dialog.notice("加载失败", "error");
                }
            });
        	
        	
            var $form1 = $(".opera_entity").children("form");
            $form1.find("#code").val(jsonData.code);
            $form1.find("#code").attr("readonly", "readonly");
            $form1.find("#cnName").val(jsonData.cnName);
            $form1.find("#enName").val(jsonData.enName);
            $form1.find("#description").val(jsonData.description);
            $("#add_entity_mes").html("");
            $("#add_entity_mes").html("编辑实体信息");
            $(".opera_entity").show();
        });
    });
    //编辑普通属性获取 id    
    $(".common_proper", $page).on("click", ".edit_common", function() {
    	var $this = $(this); 
    	var $newAdd = $this.closest(".new_add");
    	var $form1 =$newAdd.find("form");
        var entityId = $this.closest('.entity_ul').attr("entityid");
        var ischecked = false;
        $this.closest(".entity_attr").addClass('pitch').siblings(".entity_attr").removeClass('pitch');
        
        //总控制
        $form1.find("#dataType").attr("disabled", true);
        
        Ajax.ajax('admin/dictionary/basicItem/getOne', {
            id: entityId
        }, function(jsonData) {  
        	if(jsonData.oneLevelItem.dataType == "14") {
        		ischecked = true;
        	};         	
            $form1.find("#code").val(jsonData.code);
            $form1.find("#code").attr("readonly", "readonly");
            $form1.find("#cnName").val(jsonData.cnName);
            $form1.find("#description").val(jsonData.description);
            $form1.find("#enName").val(jsonData.enName);
            $form1.find("#dataRange").val(jsonData.oneLevelItem.dataRange);
            $form1.find("#edit_dataType").val(jsonData.oneLevelItem.dataType);
            $form1.find("#edit_dictParentId").val(jsonData.oneLevelItem.dictParentId);
            $form1.find("#dataType").html("");
            
           /* $form1.find("#dataType").attr("disabled", false);*/
            
            $form1.find("#needHistory").empty();
            var str="";
            if (1 == jsonData.oneLevelItem.needHistory) {
                str = str + "<option selected=\"selected\"  value=\"1\">是</option><option value=\"0\">否</option>"; 
            } else {
            	 str = str + "<option   value=\"1\">是</option><option selected=\"selected\" value=\"0\">否</option>";
            }
            $form1.find("#needHistory").append(str);
            
            $form1.find("#needHistory").hide();
    		$form1.find("#cn_needHistory").hide();
            if(ischecked) {
    			Ajax.ajax('admin/dictionary/basicItem/getDictPitem', '', function(data) {
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
    	                $form1.find("#s2id_dictParentId").remove(); 
    	                $form1.find("#refType").remove();
    	                $form1.find("#refType_enum").remove();
    	                $form1.find("#s2id_refType").remove();
    	                $form1.find("#dictParentId").css("width","30%").select2();	
    	            }
    	        });
            }if("1401" == jsonData.oneLevelItem.dataType) {//枚举类型多选
    			Ajax.ajax('admin/dictionary/basicItem/getDictPitem', '', function(data) {
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
    	                $form1.find("#dataType").attr("disabled", true);
    	                $form1.find("#s2id_dictParentId").remove(); 
    	                $form1.find("#refType").remove();
    	                $form1.find("#refType_enum").remove();
    	                $form1.find("#s2id_refType").remove();
    	                $form1.find("#dictParentId").css("width","30%").select2();	
    	            }
    	        });
            } else if ("11" == jsonData.oneLevelItem.dataType) {//如果是引用类型
            	
            	 $form1.find("#refType_enum").remove();
	                $form1.find("#refType").remove();
	                $form1.find("#s2id_refType").remove(); 
       		 //如果是引用类型， 则显示下拉列表
                Ajax.ajax('admin/dictionary/basicItem/referenceTypeEntityList', '', function(data) {
                	
                    var entityList = data.entity;
                    var str = "<span id=\"refType_enum\">引用实体：</span><select id=\"refType\" name=\"refType\">";
                	for (var p in entityList) { //遍历json数组时，这么写p为索引，0,1
                   
                		if (jsonData.oneLevelItem.refType == entityList[p].code) {
                			str = str + "<option selected=\"selected\" value=\"" + entityList[p].code + "\">" + entityList[p].cnName + "</option>"; 
	                    } else {
	                    	str = str + "<option value=\"" + entityList[p].code + "\">" + entityList[p].cnName + "</option>"; 
	                    }
                    }
                    
                	 str = str + "</select>";
 	                $form1.find("#span_enum").remove();
 	                $form1.find("#dictParentId").remove();
 	                $form1.find("#dataRange").hide();
 	                $form1.find("#cn_dataRange").hide();
 	                $form1.find("#dataType").after(str);
 	                $form1.find("#s2id_dictParentId").remove(); 
 	              
 	                $form1.find("#refType").css("width","30%").select2();
                    $CPF.closeLoading();
                });            
        		 
        	} else if ("17" == jsonData.oneLevelItem.dataType) {
        		Ajax.ajax('admin/dictionary/basicItem/getCascaseDictPitem', '', function(data) {
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
    	                $form1.find("#dataRange").show();
    	                $form1.find("#cn_dataRange").show();
    	                $form1.find("#dataType").after(str);
    	                $form1.find("#s2id_dictParentId").remove(); 
    	                $form1.find("#refType").remove();
    	                $form1.find("#refType_enum").remove();
    	                $form1.find("#s2id_refType").remove();
    	                $form1.find("#dictParentId").css("width","30%").select2();	
    	            }
    	        });
        	} if("52" == jsonData.oneLevelItem.dataType) {
    			Ajax.ajax('admin/dictionary/basicItem/getDictPitem', '', function(data) {
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
    	                $form1.find("#dataRange").show();
    	                $form1.find("#cn_dataRange").show();
    	                $form1.find("#dataType").after(str);
    	                $form1.find("#s2id_dictParentId").remove(); 
    	                $form1.find("#refType").remove();
    	                $form1.find("#refType_enum").remove();
    	                $form1.find("#s2id_refType").remove();
    	                $form1.find("#dictParentId").css("width","30%").select2();	
    	            }
    	        });
            }else {
            	$form1.find("#span_enum").remove();
                $form1.find("#dictParentId").remove();	
                $form1.find("#dataRange").show();
                $form1.find("#cn_dataRange").show();
                $form1.find("#s2id_dictParentId").remove(); 
                $form1.find("#refType").remove();
                $form1.find("#refType_enum").remove();
                $form1.find("#s2id_refType").remove();
            	if ("6" == jsonData.oneLevelItem.dataType) {
            		$form1.find("#dataRange").val("DATE").hide();
	                $form1.find("#cn_dataRange").hide();
            	} 
            	if ("7" == jsonData.oneLevelItem.dataType) {
            		$form1.find("#dataRange").val("DATETIME").hide();
	                $form1.find("#cn_dataRange").hide();
            	} 
            	
            	if ("8" == jsonData.oneLevelItem.dataType) {
            		$form1.find("#needHistory").show();
            		$form1.find("#cn_needHistory").show();
            		
            		$form1.find("#dataRange").val("BYTES").hide();
	                $form1.find("#cn_dataRange").hide();
            	} 
            	
            	if ("14" == jsonData.oneLevelItem.dataType) {
            		$form1.find("#dataRange").hide();
	                $form1.find("#cn_dataRange").hide();
            	}
            	
            }
            
            Ajax.ajax('admin/dictionary/basicItem/getDataType', '', function(data) {    	            
	            var dataType = $form1.find("#edit_dataType").val();
	            var str = "";
	            for (var key in data) {
	            	if (ischecked) {
	            		 if ("枚举类型" == data[key]) {
		 	                    str = str + " <option selected=\"selected\" value =\"" + key + "\">" + data[key] + "</option>";
		 	                } else {
		 	                    str = str + " <option value =\"" + key + "\">" + data[key] + "</option>";
		 	                }
	            	} else {
	            		if (dataType == "14") {
	 	                    str = str + " <option selected=\"selected\" value =\"" + key + "\">" + data[key] + "</option>";
	 	                } else {
	 	                	 if (dataType == key) {
	 	 	                    str = str + " <option selected=\"selected\" value =\"" + key + "\">" + data[key] + "</option>";
	 	 	                } else {
	 	 	                    str = str + " <option value =\"" + key + "\">" + data[key] + "</option>";
	 	 	                }
	 	                }
	            		
	            		
	            	}
	            	
	               
	            }
	            
	            $form1.find("#dataType").append(str).css("width","30%").select2();
	        });
            
            $newAdd.find("#add_comm_mes").html("");
            $newAdd.find("#add_comm_mes").html("编辑属性");        
            $newAdd.find(".opera_comm").show();
        });                                    
    });
    //编辑多值属性的孩子  获取 id    
    $(".more_proper", $page).on("click", ".edit_more_child", function() {
    	var $this = $(this);  
    	var $newAdd = $this.closest('.new_add');
    	$this.closest(".entity_attr").addClass('pitch').siblings(".entity_attr").removeClass('pitch');
    	var $form1 = $newAdd.find('form');
    	
    	//总控制
    	$form1.find("#dataType").attr("disabled", true);
    	
        var entityId = $(this).closest('.entity_ul').attr("entityid");
        $form1.attr("entityId", entityId);
        var ischecked = false;
        Ajax.ajax('admin/dictionary/basicItem/getOne', {
            id: entityId
        }, function(jsonData) {
        	if(jsonData.oneLevelItem.dataType == "14") {
        		ischecked = true;
        	}        	            
            $form1.find("#code").val(jsonData.code);
            $form1.find("#code").attr("readonly", "readonly");
            $form1.find("#cnName").val(jsonData.cnName);
            $form1.find("#description").val(jsonData.description);
            $form1.find("#enName").val(jsonData.enName);
            $form1.find("#dataRange").val(jsonData.oneLevelItem.dataRange);
            $form1.find("#edit_dataType").val(jsonData.oneLevelItem.dataType);
            $form1.find("#edit_dictParentId").val(jsonData.oneLevelItem.dictParentId);
            $form1.find("#dataType").html("");  
            
            $form1.find("#needHistory").empty();
            var str="";
            if (1 == jsonData.oneLevelItem.needHistory) {
                str = str + "<option selected=\"selected\"  value=\"1\">是</option><option value=\"0\">否</option>"; 
            } else {
            	 str = str + "<option   value=\"1\">是</option><option selected=\"selected\" value=\"0\">否</option>";
            }
            $form1.find("#needHistory").append(str);
            $form1.find("#needHistory").hide();
            $form1.find("#cn_needHistory").hide();
            
    		if(ischecked) {
    			Ajax.ajax('admin/dictionary/basicItem/getDictPitem', '', function(data) {
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
    	                    	                
    	    	        $form1.find("#s2id_dictParentId").remove();    	    	            	    	        
    	                $form1.find("#dataRange").hide();
    	                $form1.find("#cn_dataRange").hide();  
    	                $form1.find("#span_enum").remove();
    	                $form1.find("#dictParentId").remove();
    	                $form1.find("#dataType").after(str);
    	                $form1.find("#refType").remove();
    	                $form1.find("#refType_enum").remove();
    	                $form1.find("#s2id_refType").remove();
    	                $form1.find("#dictParentId").css("width","30%").select2();	
    	            }
    	        });
    		}if("1401" == jsonData.oneLevelItem.dataType) {
    			Ajax.ajax('admin/dictionary/basicItem/getDictPitem', '', function(data) {
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
    	                    	                
    	    	        $form1.find("#s2id_dictParentId").remove();    	    	            	    	        
    	                $form1.find("#dataRange").hide();
    	                $form1.find("#cn_dataRange").hide();  
    	                $form1.find("#span_enum").remove();
    	                $form1.find("#dictParentId").remove();
    	                $form1.find("#dataType").after(str);
    	                $form1.find("#refType").remove();
    	                $form1.find("#refType_enum").remove();
    	                $form1.find("#s2id_refType").remove();
    	                $form1.find("#dictParentId").css("width","30%").select2();	
    	            }
    	        });
    		}else if ("11" == jsonData.oneLevelItem.dataType) {//如果是引用类型
            	
           	 $form1.find("#refType_enum").remove();
	                $form1.find("#refType").remove();
	                $form1.find("#s2id_refType").remove(); 
      		 //如果是引用类型， 则显示下拉列表
               Ajax.ajax('admin/dictionary/basicItem/referenceTypeEntityList', '', function(data) {
               	
                   var entityList = data.entity;
                   var str = "<span id=\"refType_enum\">引用实体：</span><select id=\"refType\" name=\"refType\">";
               	for (var p in entityList) { //遍历json数组时，这么写p为索引，0,1
                  
               		if (jsonData.oneLevelItem.refType == entityList[p].code) {
               			str = str + "<option selected=\"selected\" value=\"" + entityList[p].code + "\">" + entityList[p].cnName + "</option>"; 
	                    } else {
	                    	str = str + "<option value=\"" + entityList[p].code + "\">" + entityList[p].cnName + "</option>"; 
	                    }
                   }
                   
               	 str = str + "</select>";
	                $form1.find("#span_enum").remove();
	                $form1.find("#dictParentId").remove();
	                $form1.find("#dataRange").hide();
	                $form1.find("#cn_dataRange").hide();
	                $form1.find("#dataType").after(str);
	                $form1.find("#s2id_dictParentId").remove(); 
	              
	                $form1.find("#refType").css("width","30%").select2();
                   $CPF.closeLoading();
               });            
       		 
       	}else if ("17" == jsonData.oneLevelItem.dataType) {//级联类型
    		$CPF.showLoading();
    		$form1.find("#dictParentId").remove();
    		$form1.find("#s2id_dictParentId").remove();
    		$form1.find("#span_enum").remove();
    		$form1.find("#refType").remove();
    		$form1.find("#refType_enum").remove(); 
    		$form1.find("#s2id_refType").remove();
            //选中  则显示下拉列表       
            Ajax.ajax('admin/dictionary/basicItem/getCascaseDictPitem', '', function(data) {
            	var dictParentId = $form1.find("#edit_dictParentId").val();
                var dataArr = data.dictPitem;
                var str = "<span id=\"span_enum\">字典序：</span><select id=\"dictParentId\" name=\"dictParentId\">";
                if (dictParentId == "") {
                	for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                        str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                    }
                }else {
                	for (var p in dataArr) { //遍历json数组时，这么写p为索引，0,1
                        if (dictParentId == dataArr[p].id) {
                            str = str + "<option selected=\"selected\"  value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                        } else {
                            str = str + "<option value=\"" + dataArr[p].id + "\">" + dataArr[p].name + "</option>"; 
                        }
                    }
                }
                
                str = str + "</select>";               
                $form1.find("#dataType").after(str);                
                $form1.find("#dictParentId").css("width","30%").select2();
                $form1.find("#dataRange").val("32").show();
                $form1.find("#cn_dataRange").show();               
                $CPF.closeLoading();
            }); 
    	} if("52" == jsonData.oneLevelItem.dataType) {//字符型预设
			Ajax.ajax('admin/dictionary/basicItem/getDictPitem', '', function(data) {
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
	                    	                
	    	        $form1.find("#s2id_dictParentId").remove();    	    	            	    	        
	                $form1.find("#dataRange").show();
	                $form1.find("#cn_dataRange").show();  
	                $form1.find("#span_enum").remove();
	                $form1.find("#dictParentId").remove();
	                $form1.find("#dataType").after(str);
	                $form1.find("#refType").remove();
	                $form1.find("#refType_enum").remove();
	                $form1.find("#s2id_refType").remove();
	                $form1.find("#dictParentId").css("width","30%").select2();	
	            }
	        });
		}else {
    			 $form1.find("#span_enum").remove();
                 $form1.find("#dictParentId").remove();
                 $form1.find("#dataRange").show();
 	            $form1.find("#cn_dataRange").show();
 	           $form1.find("#s2id_dictParentId").remove(); 	
 	          $form1.find("#refType").remove();
              $form1.find("#refType_enum").remove();
              $form1.find("#s2id_refType").remove();
 	           if ("6" == jsonData.oneLevelItem.dataType) {
 	        	   	$form1.find("#dataRange").val("date").hide();
	                $form1.find("#cn_dataRange").hide();
 	           } 
 	           
 	          if ("7" == jsonData.oneLevelItem.dataType) {
	        	   	$form1.find("#dataRange").val("datetime").hide();
	                $form1.find("#cn_dataRange").hide();
	           } 
 	          
 	         if ("8" == jsonData.oneLevelItem.dataType) {
 	        	 
 	        	 $form1.find("#needHistory").show();
 	        	 $form1.find("#cn_needHistory").show();
	        	   	$form1.find("#dataRange").hide();
	                $form1.find("#cn_dataRange").hide();
	           } 
 	        if ("11" == jsonData.oneLevelItem.dataType) {
        	   	$form1.find("#dataRange").hide();
                $form1.find("#cn_dataRange").hide();
           } 
    		}   
    		
    		Ajax.ajax('admin/dictionary/basicItem/getDataType', '', function(data) {    	           
	            var dataType = $form1.find("#edit_dataType").val();
	            var str = "";
	            for (var key in data) {
	            	
	            	if (ischecked) {
	            		 if ("14" == key) {
	 	                    str = str + " <option selected=\"selected\" value =\"" + key + "\">" + data[key] + "</option>";
	 	                } else {
	 	                    str = str + " <option value =\"" + key + "\">" + data[key] + "</option>";
	 	                }
	            	} else {
	            			if (dataType == "8" && "8" == key) {
		 	                    str = str + " <option selected=\"selected\" value =\"" + key + "\">" + data[key] + "</option>";
		 	                } else {
		 	                	 if (dataType == key) {
		 	 	                    str = str + " <option selected=\"selected\" value =\"" + key + "\">" + data[key] + "</option>";
		 	 	                } else {
		 	 	                    str = str + " <option value =\"" + key + "\">" + data[key] + "</option>";
		 	 	                }
		 	                }
	            			
	            		
	            	}
	            }
	           
	            $form1.find("#dataType").append(str).css("width","30%").select2();	            
	        }); 
    		
    		 /*Ajax.ajax('admin/dictionary/basicItem/isTwoattr', {
    	  			id:entityId
    	  		}, function(data) {  
    	  			
    	  			
    	  			if ("1401"==jsonData.oneLevelItem.dataType) {	 
    	  			$form1.find("#dataType").attr("disabled", true);
    	  		  }
    	  			
    	            if (true == data) {	 
    	            	$form1.find("#dictParentId").attr("disabled", true);
    	            	$form1.find("#dataType").attr("disabled", true);
    	            } else if ("1401"!=jsonData.oneLevelItem.dataType) {
    	            	$form1.find("#dictParentId").attr("disabled", false);
    	            	$form1.find("#dataType").attr("disabled", false);
    	            }
    		     });*/
    		
            $("#add_more_child_mes").html("");
            $("#add_more_child_mes").html("编辑属性");
            $this.closest('.new_add').find(".opera_more_child").show();
        });        
    });
    //编辑分组 获取 id
    $(".common_proper", $page).on("click", "#edit_group", function() {
    	
        var groupId = $(this).attr("groupId");
        var $this = $(this);
        var $form1 = $this.closest('.new_add').find("#group_opera_form1");
        Ajax.ajax('admin/dictionary/basicItem/getOne', {
            id: groupId
        }, function(jsonData) {            
            $form1.find("#description").val(jsonData.description);
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
    $(".more_proper", $page).on("click", "#edit_more", function() {
    	var $this = $(this);
    	var $newAdd = $this.closest(".new_add ");
        var groupId = $(this).attr("groupId");
        var $form1 = $newAdd.find("#more_opera_form1");
        Ajax.ajax('admin/dictionary/basicItem/getOne', {
            id: groupId
        }, function(jsonData) {            
            $form1.find("#code").val(jsonData.code);
            $form1.find("#code").attr("readonly", "readonly");
            $form1.find("#description").val(jsonData.description);
            $form1.find("#cnName").val(jsonData.cnName);
            $form1.find("#enName").val(jsonData.enName);
            $form1.find("#more_parent").val(jsonData.parent);
            $form1.find("#tableNameDescription").val(jsonData.oneLevelItem.tableNameDescription);
            $newAdd.find("#add_more_mes").html("");
            $newAdd.find("#add_more_mes").html("编辑多值属性信息");
            $newAdd.find(".opera_more").show();
        });
    });
    //过期实体获取 id    
    $(".change_status", $page).click(function() {
        var entityId = $(this).parent().parent().attr("entityid");
        var status = $(this).parent().parent().attr("status");
        saveStatus(entityId, status, entityId);
    });
    //过期普通属性
    $(".common_proper", $page).on("click", ".common_change_status", function() {
        var commId = $(this).closest('.entity_ul').attr("entityid");        
        var status = $(this).closest('.entity_ul').attr("status");        
        var entityId = $(".common_proper").attr("parentid");
        saveStatus(commId, status, entityId, this);
    });
    //过期 多值属性 属性
    $(".more_proper", $page).on("click", ".more_child_change_status", function() {
        var commId = $(this).parent().parent().attr("entityid");
        var status = $(this).parent().parent().attr("status");        
        var entityId = $(".common_proper").attr("parentid");
        saveStatus(commId, status, entityId, this);
    });
    //过期函数
    function saveStatus(entityId, status, parentId, th) { 
    	var node = $(th).parent().parent().parent();
    	var nodeul = $(th).parent().parent();
        Ajax.ajax('admin/dictionary/basicItem/saveStatus', {
            id: entityId,
            statusStr: status
        }, function(jsonData) {
        	if ("2" != status) {
        		node.removeClass('inuse')
        		.removeClass('newadd')
        		.removeClass('inerror')
        		.addClass('stale');
        		nodeul.attr("status", 2);
        		$(th).html("<i class=\"icon stale-entity\"></i>解除过期");
        	} else {
        		node.removeClass('stale');
        		if ("1" == jsonData.usingState) {
        			node.addClass('inuse');
        		} else if("0" == jsonData.usingState) {
        			node.addClass('newadd');
        		} else if ("-1" == jsonData.usingState){
        			node.addClass('inerror');
        		}
        		nodeul.attr("status", jsonData.usingState);
        		$(th).html("<i class=\"icon stale-entity\"></i>过期实体");
        	}
        	
        	
        });
    }
    
    //过期关系
    $(".entity_relation", $page).on("click", ".chang_status_rela", function() {
        var isPastDue = $(this).hasClass("rela_past");
        var typeCode = $(this).parent().parent().attr("typeCode")
        var entityId = $(this).parent().parent().attr("patentId");
        
        Ajax.ajax('admin/dictionary/recordRelationType/saveStatus', {
        	typeCode: typeCode,
        	isPastDue: isPastDue
        }, function(jsonData) {
        	var code = jsonData.code;
        	relaAttr(entityId);
        });
    });
    
    //在实体列表页面右键点击   的时候
    $(".get_entity_attr", $page).click(function() {
    	$('.entity_attr').removeClass('active').removeClass('pitch');
    	$(this).closest('.entity_attr').addClass('pitch');
        var entityId = $(this).parent().parent().attr("entityId");
        var status = $(this).parent().parent().attr("status");
        $(".opera_entity").hide();
        $(".common_proper").show();
        $(".more_proper").show();
        $(".entity_relation").show();
        $(".common_proper").attr("parentId", entityId);
        $(".more_proper").attr("parentId", entityId);
        $(".entity_relation").attr("entityId", entityId);
        $("#group_parent").val(entityId);
        $("#more_parent").val(entityId);
        $(".new_add").remove();
        enityAttr(entityId, status);//初始化所有数据
        
        if (status == 2) {
        	$("#add_group").hide();
        	$("#add_more").hide();
        	$(".add_entity_relation").hide();
        }
        
    });
    //document 绑定事件
    $page.on('click', function(e) {    	
    	var $target = $(e.target);
    	if($target.hasClass('entity_attr') || $target.hasClass('status')) {
    		return;
    	}else {
    		$('.entity_attr').removeClass('active');
    	}
    });
    
    //获取普通属性的ajax, entityId  是当前实体的id
    function relaAttr(entityId) {
        Ajax.ajax('admin/dictionary/basicItem/attrByPid', {
            parentId: entityId
        }, function(jsonData) {
        	 //实体关系
            $(".entity_relation_list").find(".entity_attr").not(".entity_attr_img").remove(); 
            var entityRelation = jsonData.entityRela; 
            
            var str = "";
            for (var i = 0; i < entityRelation.length; i++) {
            	
            	var typeCode = entityRelation[i].typeCode;
                var index =  typeCode.indexOf("R");
               var parentId = typeCode.substring(0, index);
            	
               str = str + "<div title=\"typeCode:"+entityRelation[i].typeCode+", 名称:"+entityRelation[i].name+", 左实体:"+entityRelation[i].leftRecordType+", <"+entityRelation[i].relationType+">, 右实体:"+entityRelation[i].rightRecordType+" , 逆向关系："+entityRelation[i].reverseCode+"\" class=\"entity_attr ";
           	
           	if (entityRelation[i].usingState == 2) {
           		str = str + "stale";
           	}else if (entityRelation[i].usingState ==1) {
           		str = str + "inuse";
           	}else if (entityRelation[i].usingState ==0) {
           		str = str + "newadd";
           	}else if (entityRelation[i].usingState ==-1) {
           		str = str + "inerror";
           	}
           	
           	str = str+"\">" + entityRelation[i].name
           	+"<ul class=\"entity_ul\" patentId=\""+parentId+"\" typeCode=\""+entityRelation[i].typeCode+"\">" 
           	+"<li><a href=\"javascript:void(0)\" patentId=\""+parentId+"\" class=\"edit_rela\"><i class=\"icon edit-entity\"></i>编辑名称</a></li>"
        	if (entityRelation[i].usingState != 2) {
				str = str+"<li><a href=\"javascript:void(0)\"  class=\"chang_status_rela rela_past\"><i class=\"icon edit-entity\"></i>过期关系</a></li>"
			}
        	if (entityRelation[i].usingState == 2) {
        		str = str+"<li><a href=\"javascript:void(0)\" class=\"chang_status_rela\"><i class=\"icon edit-entity\"></i>解除过期</a></li>"
        	}
        	str = str+"<li><a href=\"javascript:void(0)\" patentId=\""+parentId+"\" class=\"delete_rela\"><i class=\"icon edit-entity\"></i>删除关系</a></li>";
			
           	str = str+"</ul>"
				+"<i class=\"icon delete\"></i> <i class=\"icon status\"></i>"
				+"</div>"  
            }
            $(".entity_relation_list").prepend(str);
            $(".select-wrap").css({
            	"width": "100%",
            	"margin-right": "0"
            });
            $("select", $page).css("width","30%");
        });
    }

    //获取实体属性的ajax
    function enityAttr(entityId, statusStr) {
    	var uuid = "dcr" + guid();
        Ajax.ajax('admin/dictionary/basicItem/attrByPid?uuid='+uuid, {
            parentId: entityId
        }, function(jsonData) {
            var commonArr = jsonData.commonProper; //普通属性
            
            for (var i = 0; i < commonArr.length; i++) {
                var str = "<div data-code-id=\""+commonArr[i].code+"\" class=\"new_add clear-fix\">" + 
                			"<div class=\"new_add_title\">" + "<span title=\"code:"+commonArr[i].code+",中文名称："+commonArr[i].cnName+"\">" +commonArr[i].cnName+ "</span>"
                			if (statusStr != 2) {
                				str = str + "<img id=\"edit_group\" groupId=\"" + commonArr[i].code + "\" src=\"media/admin/dictionary/basicItem/images/edit_ch.png\">"
                				/*str = str + "<div class=\"two-level-attr\" id=\"add_cascade_attr\" ><a href=\"javascript:void(0)\"\">添加级联属性</a></div>"
                			*/}
			            		
                			str = str + "</div><div class=\"comm_list clear-fix\">";                		    
                for (var j = 0; j < commonArr[i].childList.length; j++) {   
                	
                	var cnNameStr = commonArr[i].childList[j].cnName;
                	
                	if (commonArr[i].childList[j].oneLevelItem.dataType == '17') {
                		cnNameStr = "<font color='red'>CM </font>" + cnNameStr;
                	}
                	
                    if (commonArr[i].childList[j].usingState == '0') {
                    	
                        str = str + "<div data-code-id=\""+commonArr[i].childList[j].code+"\" title=\"code:"+commonArr[i].childList[j].code+", 中文名称:"+commonArr[i].childList[j].cnName+",  英文名称:"+commonArr[i].childList[j].enName+",数据类型："+commonArr[i].childList[j].oneLevelItem.dataTypeCName+", 数据长度："+commonArr[i].childList[j].oneLevelItem.dataRange+", 字典序："+ commonArr[i].childList[j].oneLevelItem.dictParentId  +"  \" class=\"entity_attr newadd\">" + cnNameStr
	                        if (statusStr != 2) {
	                        	str = str +"<ul class=\"entity_ul\" entityId=\"" + commonArr[i].childList[j].code + "\" status=\"" + commonArr[i].childList[j].usingState + "\">" + "<li><a href=\"javascript:void(0)\" class=\"edit_common\"><i class=\"icon edit-entity\"></i>编辑属性</a></li>" + "<li><a href=\"javascript:void(0)\" class=\"common_change_status\"><i class=\"icon stale-entity\"></i>过期实体</a></li>" 
	                        }
                        } else if (commonArr[i].childList[j].usingState == '2') {
                        str = str + "<div data-code-id=\""+commonArr[i].childList[j].code+"\" title=\"code:"+commonArr[i].childList[j].code+", 中文名称:"+commonArr[i].childList[j].cnName+",  英文名称:"+commonArr[i].childList[j].enName+",数据类型："+commonArr[i].childList[j].oneLevelItem.dataTypeCName+", 数据长度："+commonArr[i].childList[j].oneLevelItem.dataRange+", 字典序："+ commonArr[i].childList[j].oneLevelItem.dictParentId  +"  \" class=\"entity_attr stale\">" + cnNameStr
	                        if (statusStr != 2) {
	                        	str = str +"<ul class=\"entity_ul\" entityId=\"" + commonArr[i].childList[j].code + "\" status=\"" + commonArr[i].childList[j].usingState + "\">" 
	                        	if (commonArr[i].childList[j].usingState != '2') {
	                        		str = str + "<li><a href=\"javascript:void(0)\" class=\"edit_common\"><i class=\"icon edit-entity\"></i>编辑属性</a></li>" 
	                        	}
	                        	str = str + "<li><a href=\"javascript:void(0)\" class=\"common_change_status\"><i class=\"icon stale-entity\"></i>解除过期</a></li>" 
	                        }
                        } else if (commonArr[i].childList[j].usingState == '-1') {
	                        str = str + "<div data-code-id=\""+commonArr[i].childList[j].code+"\" title=\"code:"+commonArr[i].childList[j].code+", 中文名称:"+commonArr[i].childList[j].cnName+",  英文名称:"+commonArr[i].childList[j].enName+",数据类型："+commonArr[i].childList[j].oneLevelItem.dataTypeCName+", 数据长度："+commonArr[i].childList[j].oneLevelItem.dataRange+", 字典序："+ commonArr[i].childList[j].oneLevelItem.dictParentId  +"  \" class=\"entity_attr inerror\">" + cnNameStr 
	                        if (statusStr != 2) {
	                        	str = str +"<ul class=\"entity_ul\" entityId=\"" + commonArr[i].childList[j].code + "\" status=\"" + commonArr[i].childList[j].usingState + "\">" + "<li><a href=\"javascript:void(0)\" class=\"edit_common\"><i class=\"icon edit-entity\"></i>编辑属性</a></li>" + "<li><a href=\"javascript:void(0)\" class=\"common_change_status\"><i class=\"icon stale-entity\"></i>过期实体</a></li>" 
	                        }
                       } else if (commonArr[i].childList[j].usingState == '1') {
	                        str = str + "<div data-code-id=\""+commonArr[i].childList[j].code+"\" title=\"code:"+commonArr[i].childList[j].code+", 中文名称:"+commonArr[i].childList[j].cnName+",  英文名称:"+commonArr[i].childList[j].enName+",数据类型："+commonArr[i].childList[j].oneLevelItem.dataTypeCName+", 数据长度："+commonArr[i].childList[j].oneLevelItem.dataRange+", 字典序："+ commonArr[i].childList[j].oneLevelItem.dictParentId  +"  \" class=\"entity_attr inuse\">" + cnNameStr
	                        
	                        if (statusStr != 2) {
	                        	str = str +"<ul class=\"entity_ul\" entityId=\"" + commonArr[i].childList[j].code + "\" status=\"" + commonArr[i].childList[j].usingState + "\">" + "<li><a href=\"javascript:void(0)\" class=\"edit_common\"><i class=\"icon edit-entity\"></i>编辑属性</a></li>" + "<li><a href=\"javascript:void(0)\" class=\"common_change_status\"><i class=\"icon stale-entity\"></i>过期实体</a></li>" 
	                        }
                        }
	                    if (statusStr != 2) {
	                    str = str+"<li><a href=\"javascript:void(0)\" patentId=\""+commonArr[i].childList[j].parent+"\" class=\"delete_attr\"><i class=\"icon edit-entity\"></i>删除属性</a></li>" 
	                    }
	                    
	                    if (statusStr != 2 && commonArr[i].childList[j].oneLevelItem.dataType == '17') {
		                    str = str+"<li><a href=\"javascript:void(0)\" patentId=\""+commonArr[i].childList[j].parent+"\" class=\"show_cascadeAttr_child\"><i class=\"icon edit-entity\"></i>查看级联属性孩子</a></li>";
		                 }
	                    if (statusStr != 2) {
		                    str = str+  "</ul>"; 
		                   }
	                    
                    	str = str + "<i class=\"icon status\"></i>" +"</div>";
                  
                }
                if (statusStr != 2) {
                	str = str + "<div class=\"entity_attr entity_attr_img add_comm\">" +
						    "<img alt=\"添加普通属性\" src=\"media/admin/dictionary/basicItem/addEntity_icon.png\">" +
						"</div>"
                }	
				str = str + "</div>"	
				
				//查看级联属性的孩子start
				str = str + "<div style='display: none;' class=\"entity_list cascadeAttr_child clear-fix\"><div class=\"new_add_title\"><span>级联属性孩子</span>" 
                str = str +"</div><div class=\"clear-fix\"></div>"
                str = str +"</div>"
				//end
                
              //添加编辑级联属性的孩子  div		
 			    str = str + "<div style='padding-left: 1.8em;display: none;' class=\"cascadeAttr_child_show\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/opera_entity_icon.png\"><span class=\"opera_entity_img\">级联属性孩子</span><form  id=\"cascadeAttr_form1\" class=\"opera_entity_form\"><input type=\"hidden\" id=\"id\" name=\"id\" value=\"\"><div><span class=\"opera_entity_label\">中文名称<span style=\"color: red;\">*</span></span><input type=\"text\" name=\"cnName\" id=\"cnName\" /></div><div><span class=\"opera_entity_label\">描述</span><textarea name=\"description\" id=\"description\"></textarea></div><div class=\"select-wrap\"></div></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"cascadeAttr_child_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"cascadeAttr_child_but_confirm\">确认</span></div></div>";
				
				str = str + "<div class=\"opera_comm\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/images/info.png\"><span id=\"add_comm_mes\"></span><form groupName="+commonArr[i].cnName+"  groupId="+commonArr[i].code+"   id=\"comm_opera_form1\" class=\"opera_entity_form\"><input type=\"hidden\" id=\"groupName\" name=\"groupName\" value=\"\"><input type=\"hidden\" id=\"comm_parent\" name=\"parent\" value=\"\"><input type=\"hidden\" id=\"edit_dataType\" value=\"\"><input type=\"hidden\" id=\"edit_dictParentId\" value=\"\"><input type=\"hidden\" name=\"code\" id=\"code\" /><div><span class=\"opera_entity_label\">中文名称<span style=\"color: red;\">*</span></span><input type=\"text\" name=\"cnName\" id=\"cnName\" /></div><div><span class=\"opera_entity_label\">英文名称</span><input type=\"text\" name=\"enName\" id=\"enName\" /></div><div class=\"select-wrap\"><span class=\"opera_entity_label\" id=\"cn_dataType\">数据类型<span style=\"color: red;\">*</span></span><select id=\"dataType\" class=\"enum_dataType_one\" name=\"dataType\"></select> <span style='display:none;' class=\"opera_entity_label\" id=\"cn_needHistory\">记录历史</span><select style='display:none;' id=\"needHistory\" class=\"needHistory\" name=\"needHistory\"><option value='1' selected='selected'>是</option><option value='0'>否</option></select></div><div><span class=\"opera_entity_label\" id=\"cn_dataRange\">数据长度<span style=\"color: red;\">*</span></span><input type=\"text\" name=\"dataRange\" id=\"dataRange\"></div><div><span class=\"opera_entity_label\">描述</span><textarea name=\"description\" id=\"description\"></textarea></div></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"comm_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"comm_but_confirm\">确认</span></div></div>";
                str = str + "<div class=\"opera_group\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/images/info.png\"><span class=\"opera_entity_img\" id=\"add_group_mes\"></span><form id=\"group_opera_form1\" class=\"opera_entity_form\"><input type=\"hidden\" name=\"code\" id=\"code\" /><input type=\"hidden\" id=\"group_parent\" name=\"parent\" value=\"\"><input type=\"hidden\" name=\"dataType\" value=\"16\"><div><span class=\"opera_entity_label\">中文名称<span style=\"color: red;\">*</span></span><input type=\"text\" name=\"cnName\" id=\"cnName\" /></div><div><span class=\"opera_entity_label\">描述</span><textarea name=\"description\" id=\"description\"></textarea></div></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"group_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"group_but_confirm\">确认</span><span class=\"entity-btn-confirm\" id=\"group_but_del\">删除</span></div></div>";
                if(commonArr[i].usingState == '0'){
                	str = str + "<i class=\"icon status newadd\"></div>";
                } else if(commonArr[i].usingState == '2'){
                	str = str + "<i class=\"icon status stale\"></div>";
                } else if(commonArr[i].usingState == '-1'){
                	str = str + "<i class=\"icon status inerror\"></div>";
                } else if(commonArr[i].usingState == '1'){
                	str = str + "<i class=\"icon status inuse\"></div>";
                }
            
                
                $(".common_proper").append(str);
            }
            var moreArr = jsonData.moreProper; //多值属性
            for (var i = 0; i < moreArr.length; i++) {
                var str = "<div data-code-id=\""+moreArr[i].code+"\" class=\"new_add clear-fix\">" + 
                			"<div  class=\"new_add_title\">" + "<span title=\"code:"+moreArr[i].code+", 中文名称："+moreArr[i].cnName+", 英文名称："+moreArr[i].enName+", 表描述:"+moreArr[i].oneLevelItem.tableNameDescription+"\">" + moreArr[i].cnName+ "</span>"
                			
    			if (statusStr != 2) {
    				str = str + "<img id=\"edit_more\" groupId=\"" + moreArr[i].code + "\" src=\"media/admin/dictionary/basicItem/images/edit_ch.png\">"                			
    			}
                
                if (moreArr[i].twoLevelAttr == null) {
                	if (statusStr != 2) {
                		str = str + "<div class=\"two-level-attr\" id=\"add_twoLevelAttr\" ><a href=\"javascript:void(0)\"\">添加二级属性</a></div>"
                	}
                } else {
                    str = str + "<div class=\"two-level-attr\" id=\"twoLevelAttr\" twoLevelId=\"" + moreArr[i].twoLevelAttr + "\"><a href=\"javascript:void(0)\"\">查看二级属性</a></div>"
                }  
                str = str + "</div><div class=\"clear-fix more_list\">";
                for (var j = 0; j < moreArr[i].childList.length; j++) {   
                	
                	var cnNameStr = moreArr[i].childList[j].cnName;
                	
                	if (moreArr[i].childList[j].oneLevelItem.dataType == '17') {
                		cnNameStr = "<font color='red'>CM </font>" + cnNameStr;
                	}
                	
                    if (moreArr[i].childList[j].usingState == '0') {
                        str = str + "<div data-code-id=\""+moreArr[i].childList[j].code+"\" title=\"code:"+moreArr[i].childList[j].code+", 中文名称:"+moreArr[i].childList[j].cnName+",  英文名称:"+moreArr[i].childList[j].enName+",数据类型："+moreArr[i].childList[j].oneLevelItem.dataTypeCName+", 数据长度："+moreArr[i].childList[j].oneLevelItem.dataRange+", 字典序："+ moreArr[i].childList[j].oneLevelItem.dictParentId  +"  \" class=\"entity_attr newadd\">"  + cnNameStr 
                        if (statusStr != 2) {
                        	str = str+ "<ul class=\"entity_ul\" entityId=\"" + moreArr[i].childList[j].code + "\" status=\"" + moreArr[i].childList[j].usingState + "\">" + "<li><a href=\"javascript:void(0)\" class=\"edit_more_child\"><i class=\"icon edit-entity\"></i>编辑属性</a></li>" + "<li><a href=\"javascript:void(0)\" class=\"more_child_change_status\"><i class=\"icon stale-entity\"></i>过期实体</a></li>"
                        }
                    } else if (moreArr[i].childList[j].usingState == '2') {
                        str = str + "<div data-code-id=\""+moreArr[i].childList[j].code+"\" title=\"code:"+moreArr[i].childList[j].code+", 中文名称:"+moreArr[i].childList[j].cnName+",  英文名称:"+moreArr[i].childList[j].enName+",数据类型："+moreArr[i].childList[j].oneLevelItem.dataTypeCName+", 数据长度："+moreArr[i].childList[j].oneLevelItem.dataRange+", 字典序："+ moreArr[i].childList[j].oneLevelItem.dictParentId  +"  \" class=\"entity_attr stale\">" + cnNameStr 
                        if (statusStr != 2) {
                        str = str + "<ul class=\"entity_ul\" entityId=\"" + moreArr[i].childList[j].code + "\" status=\"" + moreArr[i].childList[j].usingState + "\">" 
                        if (moreArr[i].childList[j].usingState != '2') {
                        	str = str + "<li><a href=\"javascript:void(0)\" class=\"edit_more_child\"><i class=\"icon edit-entity\"></i>编辑属性</a></li>" 
                        }  
                        str = str + "<li><a href=\"javascript:void(0)\" class=\"more_child_change_status\"><i class=\"icon stale-entity\"></i>解除过期</a></li>"
                        }
                    } else if (moreArr[i].childList[j].usingState == '-1') {
                        str = str + "<div data-code-id=\""+moreArr[i].childList[j].code+"\" title=\"code:"+moreArr[i].childList[j].code+", 中文名称:"+moreArr[i].childList[j].cnName+",  英文名称:"+moreArr[i].childList[j].enName+",数据类型："+moreArr[i].childList[j].oneLevelItem.dataTypeCName+", 数据长度："+moreArr[i].childList[j].oneLevelItem.dataRange+", 字典序："+ moreArr[i].childList[j].oneLevelItem.dictParentId  +"  \" class=\"entity_attr inerror\">" + cnNameStr 
                        if (statusStr != 2) {  
                        str = str + "<ul class=\"entity_ul\" entityId=\"" + moreArr[i].childList[j].code + "\" status=\"" + moreArr[i].childList[j].usingState + "\">" + "<li><a href=\"javascript:void(0)\" class=\"edit_more_child\"><i class=\"icon edit-entity\"></i>编辑属性</a></li>" + "<li><a href=\"javascript:void(0)\" class=\"more_child_change_status\"><i class=\"icon stale-entity\"></i>过期实体</a></li>"
                        }
                    } else if (moreArr[i].childList[j].usingState == '1') {
                        str = str + "<div data-code-id=\""+moreArr[i].childList[j].code+"\" title=\"code:"+moreArr[i].childList[j].code+", 中文名称:"+moreArr[i].childList[j].cnName+",  英文名称:"+moreArr[i].childList[j].enName+",数据类型："+moreArr[i].childList[j].oneLevelItem.dataTypeCName+", 数据长度："+moreArr[i].childList[j].oneLevelItem.dataRange+", 字典序："+ moreArr[i].childList[j].oneLevelItem.dictParentId  +"  \" class=\"entity_attr inuse\">" + cnNameStr 
                        if (statusStr != 2) {  
                        str = str + "<ul class=\"entity_ul\" entityId=\"" + moreArr[i].childList[j].code + "\" status=\"" + moreArr[i].childList[j].usingState + "\">" + "<li><a href=\"javascript:void(0)\" class=\"edit_more_child\"><i class=\"icon edit-entity\"></i>编辑属性</a></li>" + "<li><a href=\"javascript:void(0)\" class=\"more_child_change_status\"><i class=\"icon stale-entity\"></i>过期实体</a></li>"
                        }
                    }
                    if (statusStr != 2) {
                    	str = str + "<li><a href=\"javascript:void(0)\" patentId=\""+moreArr[i].parent+"\"  class=\"delete_attr\"><i class=\"icon edit-entity\"></i>删除属性</a></li>"
                    }
                    
                    if (statusStr != 2 && moreArr[i].childList[j].oneLevelItem.dataType == '17') {
	                    str = str+"<li><a href=\"javascript:void(0)\"  class=\"more_show_cascadeAttr_child\"><i class=\"icon edit-entity\"></i>查看级联属性孩子</a></li>";
	                 }
                    
                    if (statusStr != 2) {
                    	str = str + "</ul>";
                    }
                    str = str+ "<i class=\"icon status\"></i>"+ "</div>";
                }
                
                if (statusStr != 2) {
                str = str + "<div class=\"entity_attr entity_attr_img add_more_child\">" +
							    "<img alt=\"添加多值属性\" src=\"media/admin/dictionary/basicItem/addEntity_icon.png\">" +
							"</div>"
                }			    
				str = str + "</div>"
				
				//查看级联属性的孩子start
				str = str + "<div style='display: none;' class=\"entity_list more_cascadeAttr_child clear-fix\"><div class=\"new_add_title\"><span>级联属性孩子</span>" 
                str = str +"</div><div class=\"clear-fix\"></div>"
                str = str +"</div>"
				//end
                
              //添加编辑级联属性的孩子  div		
 			    str = str + "<div style='padding-left: 1.8em;display: none;' class=\"more_cascadeAttr_child_show\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/opera_entity_icon.png\"><span class=\"opera_entity_img\">级联属性孩子</span><form  id=\"cascadeAttr_form1\" class=\"opera_entity_form\"><input type=\"hidden\" id=\"id\" name=\"id\" value=\"\"><div><span class=\"opera_entity_label\">中文名称<span style=\"color: red;\">*</span></span><input type=\"text\" name=\"cnName\" id=\"cnName\" /></div><div><span class=\"opera_entity_label\">描述</span><textarea name=\"description\" id=\"description\"></textarea></div></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"more_cascadeAttr_child_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"more_cascadeAttr_child_but_confirm\">确认</span></div></div>";
				
				
                str = str + "<div class=\"opera_more_child\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/images/info.png\"><span id=\"add_more_child_mes\"></span><form groupName=\"" + moreArr[i].cnName + "\" groupId=\"" + moreArr[i].code + "\" id=\"more_child_opera_form1\" class=\"opera_entity_form\"><input type=\"hidden\" id=\"groupName\" name=\"groupName\" value=\"\"><input type=\"hidden\" id=\"comm_parent\" name=\"parent\" value=\"\"><input type=\"hidden\" id=\"edit_dataType\" value=\"\"><input type=\"hidden\" id=\"edit_dictParentId\" value=\"\"><input type=\"hidden\" name=\"code\" id=\"code\"/><div><span class=\"opera_entity_label\">中文名称<span style=\"color: red;\">*</span></span><input type=\"text\" name=\"cnName\" id=\"cnName\"/></div><div><span class=\"opera_entity_label\">英文名称</span><input type=\"text\" name=\"enName\" id=\"enName\"/></div><div class=\"select-wrap\"><span class=\"opera_entity_label\" id=\"cn_dataType\">数据类型<span style=\"color: red;\">*</span></span><select id=\"dataType\" class=\"enum_daType_two\" name=\"dataType\"></select><span style='display:none;'  class=\"opera_entity_label\" id=\"cn_needHistory\">记录历史记录</span><select style='display:none;' id=\"needHistory\" name=\"needHistory\"><option value='1' selected='selected'>是</option><option value='0'>否</option></select></div><div><span class=\"opera_entity_label\" id=\"cn_dataRange\">数据长度<span style=\"color: red;\">*</span></span><input type=\"text\" name=\"dataRange\" id=\"dataRange\" /></div><div><span class=\"opera_entity_label\">描述</span><textarea name=\"description\" id=\"description\"></textarea></div></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"more_child_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"more_child_but_confirm\">确认</span></div></div>";                
                //这里是显示二级信息以及二级的孩子数据 start
                str = str + "<div class=\"entity_list twoLevelAttr_child clear-fix\"><div class=\"new_add_title\"><span id=\"twoLevelAttr_name\"></span>" 
                if (statusStr != 2) {		
                	str = str + "<img twoLevelId=\"\" id=\"edit_twoLevelAttr\" src=\"media/admin/dictionary/basicItem/images/edit_ch.png\">"
                }		
                str = str +"</div><div class=\"clear-fix\"></div>"
                str = str +"</div>"
                //end
                //添加编辑二级属性 div		
                str = str + "<div class=\"twoLevelAttr_show\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/opera_entity_icon.png\"><span class=\"opera_entity_img\">二级属性</span><form groupName=\"" + moreArr[i].cnName + "\" groupId=\"" + moreArr[i].code + "\" id=\"twoLevelAttr_form1\" class=\"opera_entity_form\"><input type=\"hidden\" id=\"id\" name=\"id\" value=\"\"><div><span class=\"opera_entity_label\">名称<span style=\"color: red;\">*</span></span><input type=\"text\" readonly=\"readonly\" id=\"name\" name=\"name\" value=\"\"></div><div><span class=\"opera_entity_label\">对应多值id<span style=\"color: red;\">*</span></span><input type=\"text\" readonly=\"readonly\" id=\"relatedMultiattribute\" name=\"relatedMultiattribute\" value=\"\"></div><div class=\"select-wrap\"><span class=\"opera_entity_label\">字典枚举<span style=\"color: red;\">*</span></span><select id=\"dictionaryAttr\" name=\"dictionaryAttr\"></select></div><div class=\"select-wrap\"><span class=\"opera_entity_label\">普通属性值<span style=\"color: red;\">*</span></span><select id=\"valueAttr\" name=\"valueAttr\"></select></div></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"twoLevelAttr_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"twoLevelAttr_but_confirm\">确认</span><span class=\"entity-btn-confirm\" id=\"twoLevelAttr_but_del\">删除</span></div></div>";
                //添加编辑二级属性的孩子 div		
                str = str + "<div class=\"twoLevelAttr_child_show\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/opera_entity_icon.png\"><span class=\"opera_entity_img\">添加二级属性</span><form groupName=\"" + moreArr[i].cnName + "\" groupId=\"" + moreArr[i].code + "\" id=\"twoLevelAttr_child_form1\" class=\"opera_entity_form\"><div><span class=\"opera_entity_label\">名称<span style=\"color: red;\">*</span></span><input type=\"text\" id=\"name\" name=\"name\" value=\"\"></div><div class=\"select-wrap\"><span class=\"opera_entity_label\">字典名称<span style=\"color: red;\">*</span></span><select id=\"dictionaryCode\" name=\"dictionaryCode\"></select></div><input type=\"hidden\" id=\"mappingId\" name=\"mappingId\" value=\"\"></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"twoLevelAttr_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"twoLevelAttr_child_but_confirm\">确认</span></div></div>";
                str = str + "<div class=\"opera_more\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/opera_entity_icon.png\"><span class=\"opera_entity_img\" id=\"add_more_mes\"></span><form id=\"more_opera_form1\" class=\"opera_entity_form\"><input type=\"hidden\" name=\"code\" id=\"code\"/><input type=\"hidden\" id=\"more_parent\" name=\"parent\" value=\"\"><input type=\"hidden\" name=\"dataType\" value=\"9\"><div><span class=\"opera_entity_label\">中文名称<span style=\"color: red;\">*</span></span><input type=\"text\" name=\"cnName\" id=\"cnName\"/><br></div><div><span class=\"opera_entity_label\">英文名称<span style=\"color: red;\">*</span></span><input type=\"text\" name=\"enName\" id=\"enName\"/></div><div><span class=\"opera_entity_label\">表描述</span><textarea name=\"tableNameDescription\" id=\"tableNameDescription\" rows=\"\" cols=\"\"></textarea><br></div><div><span class=\"opera_entity_label\">描述</span><textarea name=\"description\" id=\"description\"></textarea></div></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"more_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"more_but_confirm\">确认</span><span class=\"entity-btn-confirm\" id=\"more_but_del\">删除</span></div></div>";
                if(moreArr[i].usingState == '0'){
                	str = str + "<i class=\"icon status newadd\"></div>";
                } else if(moreArr[i].usingState == '2'){
                	str = str + "<i class=\"icon status stale\"></div>";
                } else if(moreArr[i].usingState == '-1'){
                	str = str + "<i class=\"icon status inerror\"></div>";
                } else if(moreArr[i].usingState == '1'){
                	str = str + "<i class=\"icon status inuse\"></div>";
                }
                $(".more_proper").append(str);
            }
            //实体关系
            $(".entity_relation_list").find(".entity_attr").not(".entity_attr_img").remove(); 
            var entityRelation = jsonData.entityRela; 
            
            var str = "";
            for (var i = 0; i < entityRelation.length; i++) {
            	
            	var typeCode = entityRelation[i].typeCode;
                var index =  typeCode.indexOf("R");
               var parentId = typeCode.substring(0, index);
            	
            	str = str + "<div title=\"typeCode:"+entityRelation[i].typeCode+", 名称:"+entityRelation[i].name+", 左实体:"+entityRelation[i].leftRecordType+", <"+entityRelation[i].relationType+">  右实体:"+entityRelation[i].rightRecordType+" , 逆向关系："+entityRelation[i].reverseCode+"\" class=\"entity_attr ";
            	
            	if (entityRelation[i].usingState == 2) {
            		str = str + "stale";
            	}else if (entityRelation[i].usingState ==1) {
            		str = str + "inuse";
            	}else if (entityRelation[i].usingState ==0) {
            		str = str + "newadd";
            	}else if (entityRelation[i].usingState ==-1) {
            		str = str + "inerror";
            	}
            	
            	str = str+"\">" + entityRelation[i].name
            	+"<ul class=\"entity_ul\" patentId=\""+parentId+"\" typeCode=\""+entityRelation[i].typeCode+"\">" 
            	+"<li><a href=\"javascript:void(0)\" patentId=\""+parentId+"\" class=\"edit_rela\"><i class=\"icon edit-entity\"></i>编辑名称</a></li>"
            	if (entityRelation[i].usingState != 2) {
					str = str+"<li><a href=\"javascript:void(0)\"  class=\"chang_status_rela rela_past\"><i class=\"icon edit-entity\"></i>过期关系</a></li>"
				}
            	if (entityRelation[i].usingState == 2) {
            		str = str+"<li><a href=\"javascript:void(0)\" class=\"chang_status_rela\"><i class=\"icon edit-entity\"></i>解除过期</a></li>"
            	}
            	str = str+"<li><a href=\"javascript:void(0)\" patentId=\""+parentId+"\" class=\"delete_rela\"><i class=\"icon edit-entity\"></i>删除关系</a></li>";
				
            	str = str+"</ul>"
				+"<i class=\"icon delete\"></i> <i class=\"icon status\"></i>"
				+"</div>"  
            }
            $(".entity_relation_list").prepend(str);
            $(".select-wrap").css({
            	"width": "100%",
            	"margin-right": "0"
            });
            $("select", $page).css("width","30%");          
        });
    }
    
   //用于生成uuid
    function S4() {
        return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
    }
    function guid() {
        return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
    }
})