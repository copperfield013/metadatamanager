seajs.use(['dialog', 'ajax', '$CPF'], function(Dialog, Ajax, $CPF) {
	var $page = $("#statEntity-wrap");
	
	
	
	 $(function(){
		  //FILTERS
		    Ajax.ajax('admin/node/binFilterBody/getFiltersOpt', {
		    	opsCode:10
		    }, function(data){		    	
		    	var data = data.optMap;
		    	optFILTERS = data;
		    	$CPF.closeLoading();
		    }, {async: true})
	    })
	
	
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
   
   
 //删除分组
   $(".properOperate", $page).on("click", "#group_but_del", function() {
      var $form = $(this).closest(".opera_group").find("#group_opera_form1");
      var groupId = $form.find("#code").val();
      var entityId = $(".properOperate").attr("parentid");
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
   
   //删除实体 
   $(".statEntity-list", $page).on("click", ".delete_entity", function() {//新添加的
	   var bieCode = $(this).parent().parent().attr("bieCode");
	   var entityId = $(this).parent().parent().attr("entityId");
	   Dialog.confirm("删除可能会引起数据错误， 是否确认删除", function(isYes) {
	   		if (isYes) {
	   			Ajax.ajax('admin/stat/state/do_delete', {
	   				bieCode:bieCode,
	   				entityId:entityId
	   		   }, function(data) {
	   	           
	   	       });
	   		}
	   });
   });
   
   //删除属性
   $(".entity-list", $page).on("click", ".delete_attr", function() {
	   var statdfid = $(this).closest(".entity_ul").attr("statdfid");
	   
	   Dialog.confirm("删除可能会引起数据错误， 是否确认删除", function(isYes) {
	   		if (isYes) {
	   			Ajax.ajax('admin/stat/statDf/do_delete', {
	   				statdfid:statdfid
	   		   }, function(data) {
	   	           
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
      var entityId = $(".properOperate").attr("parentid");
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
      var entityId = $(".properOperate").attr("parentid");
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
    	  $(".properOperate").hide();
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
    $(".properOperate", $page).on("change", ".enum_dataType_one", function() {  
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
    $(".more_proper", $page).on("change", ".enum_daType_two", function() {/*
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
    */});
	
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
	
 //删除分组
   $(".properOperate", $page).on("click", "#group_but_del", function() {
      var $form = $(this).closest(".opera_group").find("#group_opera_form1");
      var groupId = $form.find("#code").val();
      var entityId = $(".properOperate").attr("parentid");
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
   
   //删除statDf
   $(".statEntity-list", $page).on("click", ".delete_attr", function() {
	   var statdfid = $(this).parent().parent().parent().attr("statdfid");
	   var attrNode =  $(this).parent().parent().parent();
	   Dialog.confirm("删除可能会引起数据错误， 是否确认删除", function(isYes) {
	   		if (isYes) {
	   			Ajax.ajax('admin/stat/statDf/do_delete', {
	   				statdfid:statdfid
	   		   }, function(data) {
	   			   if (data.code == 200) {
	   				 attrNode.remove();
	   			   } else {
	   				 Dialog.notice(msg, "error");
	   			   }
	   	       });
	   		}
	   });
   });
   
    //点击 添加实体 显示div    
    $("#add_entity", $page).click(function() {//新添加的
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
    	Ajax.ajax('admin/stat/state/getEntity', '', function(data) {
            if (data.code == 200) {
            	var allEntity = data.allEntity;
            	var str = "";
            	for (var p in allEntity) { //遍历json数组时，这么写p为索引，0,1
            		var entity = allEntity[p];
            			str = str + "<option value=\"" + entity[0] + "\">" +entity[1]+ "</option>"; 
            	}
            	
            	$("#entity_opera_form1").find("#sourceCode").append(str);
            	$("#entity_opera_form1").find("#sourceCode").css("width","20%").select2();
            } else {
            	Dialog.notice(msg, "error");
            }
            $CPF.closeLoading();
        });          
    	  $(".properOperate").hide();
          $(".more_proper").hide();
          $(".entity_relation").hide();
        $("#add_entity_mes").html("");
        $("#add_entity_mes").html("添加统计实体");
        $(".opera_entity").show();
    });
    
    //点击 添加维度加号 显示div
  //点击 添加普通属性加号 显示div
    $(".properOperate", $page).on("click", ".add_comm", function() {
    	var $this = $(this);
    	var $newAdd = $this.closest(".new_add");
    	var $form = $newAdd.find(".opera_comm").find("#comm_opera_form1");
    	
    	$form.find("#dataType").attr("disabled", false);
    	
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
        
        //清除树结构

        var parent = $form.find(".entity-title").next(".collapse-content")[0];
          $(parent).html(""); //清空子节点
        
    });
    
  //点击 添加普通属性加号 显示div
    $(".more_proper", $page).on("click", ".add_comm", function() {
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
        var entityId = $(".properOperate").attr("parentId");
     
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
    
    //点击取消 ， 取消添加普通属性
    $(".properOperate", $page).on("click", "#comm_but_cancel", function() {//新添加的
        var $form1 = $(this).closest(".opera_comm").find("form");        
        $(this).closest('.new_add').find('.entity_attr').removeClass('pitch');
        $form1.find("#code").removeAttr("readonly");
        $form1.find("#cnName").val("");
        $form1.find("#code").val("");
        $form1.find("#enName").val("");
        $form1.find("#dataRange").val("");
        $(this).closest(".opera_comm").hide();
        
        //清除树节点的孩子
        var parent = $form1.find(".entity-title").next(".collapse-content")[0];
        $(parent).html(""); //清空子节点
    });
    
    //点击取消 ， 取消添加普通属性
    $(".more_proper", $page).on("click", "#comm_but_cancel", function() {//新添加的
        var $form1 = $(this).closest(".opera_comm").find("form");        
        $(this).closest('.new_add').find('.entity_attr').removeClass('pitch');
        $form1.find("#code").removeAttr("readonly");
        $form1.find("#cnName").val("");
        $form1.find("#code").val("");
        $form1.find("#enName").val("");
        $form1.find("#dataRange").val("");
        $(this).closest(".opera_comm").hide();
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
    
    //点击确认， 添加statDf 这里获取维度的form表单
    $(".properOperate", $page).on("click", "#comm_but_confirm", function() {//新添加的
        //设置隐藏元素的值
    	
    	  var $form = $(this).closest(".opera_comm").find("#comm_opera_form1");
    	
    	var parentId = $(".properOperate").attr("parentId");
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
        var entityId = $(".properOperate").attr("parentId");
        
        var $commlist = $form.parent().siblings(".comm_list ");//普通属性列表
      
        var id =  $(this).closest('.opera_comm').find("#id").val();
        var type = $form.attr("type");
        
        var expressionId;
        
        $form.find("#tree_view_panel").children(".entity-edit-wrap").children("ul").children("li").each(function(){
        	expressionId =  $(this).attr("data-id");
		  });
        
        if (!checkForm($form)) {
        	return;
        }
        
        var $commlist = $form.parent().siblings(".comm_list ");//普通属性列表
        	$CPF.showLoading();
            	Ajax.ajax('admin/stat/statDf/do_add', {
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
                     needHistory:needHistory,
            		id: id,
            		type: type,
            		expressionId: expressionId
                }, function(data) {
                	var statDf = data.statDf;
                	var biCode = statDf.biCode;
                	$(this).closest('.opera_comm').hide();
                	
                	if("" == id) {
                		 var str = "<div statDfId='"+statDf.id+"' class=\"entity_attr newadd\">" + cnName 
      	                str = str +"<ul class=\"entity_ul\" statDfId='"+statDf.id+"'>" + "<li><a href=\"javascript:void(0)\" class=\"edit_common\"><i class=\"icon edit-entity\"></i>编辑属性</a></li>"+"<li><a href=\"javascript:void(0)\" class=\"common_change_status\"><i class=\"icon stale-entity\"></i>过期实体</a></li>"
                          str = str+"<li><a href=\"javascript:void(0)\" patentId='' class=\"delete_attr\"><i class=\"icon edit-entity\"></i>删除属性</a></li>" 
                 		 str = str + "</ul><i class=\"icon status\"></i>" +"</div>";
                          $commlist.children(".add_comm").before(str);
                	} else {
                		
                		var titlestr  = "code:"+code+", 中文名称:"+cnName+",  英文名称:"+enName+",数据类型："+dataType+", 数据长度："+dataRange+", 字典序："+ dictParentId;
                		var str = cnName +"<ul class=\"entity_ul\" statDfId='"+statDf.id+"'>" + "<li><a href=\"javascript:void(0)\" class=\"edit_common\"><i class=\"icon edit-entity\"></i>编辑属性</a></li>"+"<li><a href=\"javascript:void(0)\" class=\"common_change_status\"><i class=\"icon stale-entity\"></i>过期实体</a></li>"
                        str = str+"<li><a href=\"javascript:void(0)\" patentId='' class=\"delete_attr\"><i class=\"icon edit-entity\"></i>删除属性</a></li>" 
               		 str = str + "</ul><i class=\"icon status\"></i>" 
               		 
               		 $commlist.children("[data-code-id='"+id+"']").attr("title", titlestr);
                		$commlist.children("[statdfid='"+id+"']").attr("class", "entity_attr newadd");
                		$commlist.children("[statdfid='"+id+"']").html(str);
                	}
                	
            		
                     $form.parent().hide();
                	 $CPF.closeLoading();
                });
            	
            	 $CPF.closeLoading();
    });
    
  //点击确认， 添加statDf 这里获取事实的form表单
    
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
    $("#entity_but_confirm", $page).click(function() {//信添加的
    	var formdom = $(this).closest(".opera_entity").find("#entity_opera_form1")[0];
        var fData = new FormData(formdom);  
       // if (checkEntityAndMore($form)) {
        	 Ajax.ajax('admin/stat/state/do_add', fData, function(data) {
             	
             });
      //  }
       
    });
    
    //给 实体列表  注册鼠标点击事件  让自己的ul显示出来    
    var $list = $(".statEntity_list", $page);
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
        $(".properOperate").hide();
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
    $(".properOperate", $page).on("click", ".edit_common", function() {
    	var $this = $(this); 
    	var $newAdd = $this.closest(".new_add");
    	var $form1 =$newAdd.find("form");
        var entityId = $this.closest('.entity_ul').attr("entityid");
        var statdfid = $this.closest('.entity_ul').attr("statdfid");
        
        var ischecked = false;
        $this.closest(".entity_attr").addClass('pitch').siblings(".entity_attr").removeClass('pitch');
        
        //总控制
        $form1.find("#dataType").attr("disabled", true);
        $form1.find("#id").val(statdfid);
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
            
            //回显树结构
            Ajax.ajax('admin/stat/statDf/getStatDf', {
            	statdfid:statdfid
            }, function(data) {  
            	
            	if (data.code == 200) {
            		var statDf = data.statDf;
            		var expressionId = statDf.expressionId;
            		 Ajax.ajax('admin/stat/statExpression/getStatExpression', {
            			 expressionId:expressionId
                      }, function(data) {  
                      	
                    	  initNode(data.statExpression, $form1);//值初始化第一个节点
                      	
                      })
            	} else {
            		
            	}
            	
            	
            	
            })
            
            
        });                                    
    });
    
    
 function getFUNCATIONchild(expressionId,parent) {
    	
    	//获取所有的孩子
    	Ajax.ajax("admin/stat/statExpression/getStatExpChild", {
    		expressionId:expressionId
		},function(data){
			
			if (data.code == 200) {
				var statExpList = data.statExpList;
				
				$(parent).html(""); //清空子节点
				
				for (var key in statExpList) {
					if (statExpList[key].type == 1) {
			    		initFUNCTION(statExpList[key], parent);
			    	} else if (statExpList[key].type == 2) {
			    		initCONSTANT(statExpList[key], parent);
			    	} else if (statExpList[key].type == 3) {
			    		initATTRVAR(statExpList[key], parent);
			    	}
				}
				
			} else {
				Dialog.notice("数据加载错误！", "error");
			}
			
			
		})
    }
    
    /**
     * 加载本身节点
     */
    function initNode(statExpression, $form1){
    	$CPF.showLoading();
    	var parent = $form1.find(".entity-title").next(".collapse-content")[0];
    	$(parent).html(""); //清空子节点
    	
    	if (statExpression.type == 1) {
    		initFUNCTION(statExpression, parent);
    	} else if (statExpression.type == 2) {
    		initCONSTANT(statExpression, parent);
    	} else if (statExpression.type == 3) {
    		initATTRVAR(statExpression, parent);
    	}
    	
    	$CPF.closeLoading();
    }
    
    
    /**
     * 初始化
      */
    function initFUNCTION(statExpression, parent) {
    	
    	/*var $content = $(el).closest(".collapse-header").siblings(".collapse-content");*/
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
    	
       var p1 =  statExpression.parameter1;
       var p2 =  statExpression.parameter2;
       var p3 =  statExpression.parameter3;
       
       if (p1 == undefined) {
    	  p1=""; 
       }  
       if (p2 == undefined) {
    	  p2=""; 
       } 
       if (p3 == undefined) {
    	  p3=""; 
       }
    	$CPF.showLoading();
    	Ajax.ajax("admin/stat/statExpression/getSQLFunctionType", {
		},function(data){
			var sqlFunctionType = data.sqlFunctionType;
			if (data.code==200 && sqlFunctionType.length>0) {
				
		        var nodeHtml = "<li class='attr-relative' data-id='"+statExpression.id+"'>" + 
					"<div class='attr-relative-title attr-relative collapse-header' data-id='"+statExpression.id+"'  data-parentId=''>" + 
						"<div class='icon-label attr-relative-dict attr-relative'>" + 
							"<i class='icon icon-attr-relative'></i><span class='text'>FUNCTION</span>" +
						"</div>" + 
						"<div class='label-bar attr-relative  FUNCTION-save  al-save'>"+
								"<span id='bianhaospan' style='color: #363636;padding-right: 1em;' title='编号'>"+statExpression.id+"</span>"+
								"<select disabled name='functionType' class='abc-attr functionType'>";
								 for (var key in sqlFunctionType){
									 
									 if (sqlFunctionType[key][1] == statExpression.functionType) {
										 nodeHtml+="<option  title='"+sqlFunctionType[key][1]+"' paraSize='"+sqlFunctionType[key][2]+"' value='"+sqlFunctionType[key][1]+"' selected>"+sqlFunctionType[key][0]+"</option>";
									 } else {
										 nodeHtml+="<option  title='"+sqlFunctionType[key][1]+"' paraSize='"+sqlFunctionType[key][2]+"' value='"+sqlFunctionType[key][1]+"'>"+sqlFunctionType[key][0]+"</option>";
									 }
									 
								 }
								 nodeHtml +="</select>"+
								
				            	"<input type='text' class='edit-input text parameter1' name='parameter1' placeholder='parameter1'  title='parameter1' value='"+p1+"'>"+
				            	"<input type='text'  class='edit-input text parameter2' name='parameter2' placeholder='parameter2'  title='parameter2' value='"+p2+"'>"+
				            	"<input type='text' class='edit-input text parameter3' name='parameter3' placeholder='parameter3'  title='parameter3' value='"+p3+"'>"+
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
				    var $html = $(nodeHtml).prependTo($(parent));
		            $html.find("select").css({"width":"12%","marginLeft":"16px"}).select2();
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
     * 初始化
      */
    function initCONSTANT(statExpression, parent) {
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
        $CPF.showLoading();
        var nodeHtml = "<li class='attr-relative' data-id='"+statExpression.id+"'>" + 
			"<div class='attr-relative-title attr-relative collapse-header' data-id='"+statExpression.id+"'  data-parentId=''>" + 
				"<div class='icon-label attr-relative-sub attr-relative'>" + 
					"<i class='icon icon-attr-group'></i><span class='text'>CONSTANT</span>" +
				"</div>" + 
				"<div class='label-bar attr-relative CONSTANT-save  al-save'>"+
						"<span id='bianhaospan' style='color: #363636;padding-right: 1em;' title='编号'>"+statExpression.id+"</span>"+
						"<input disabled type='text'  class='edit-input text parameter0' name='parameter0' title='parameter0' placeholder='parameter0' value='"+statExpression.parameter0+"'>" +
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
		    var $html = $(nodeHtml).prependTo($(parent));
            $html.find("select").css({"width":"12%","marginLeft":"16px"}).select2();
		    $CPF.closeLoading();	
    }
    
    
  //初始化
    function initATTRVAR(statExpression, parent){
    	$CPF.showLoading();
    	var sourcecode = $(parent).closest(".properOperate").attr("sourcecode");
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
        $CPF.showLoading();
        var nodeHtml = "<li class='attr-relative' data-id='"+statExpression.id+"'>" + 
			"<div class='attr-relative-title attr-relative-dict attr-relative collapse-header' data-id='"+statExpression.id+"'  data-parentId=''>" + 
				"<div class='icon-label attr-relative-dict attr-relative'>" + 
					"<i class='icon icon-attr-relative'></i><span class='text'>ATTRVAR</span>" +
				"</div>" + 
				"<div class='label-bar attr-relative ATTRVAR-save al-save'>"+
				"<span id='bianhaospan' style='color: #363636;padding-right: 1em;' title='编号'>"+statExpression.id+"</span>";
						
				/*
				 * 获取来源实体的普通孩子
				 * */
				Ajax.ajax("admin/dictionary/basicItem/getComm", {
					entityId:sourcecode
				},function(data){
					var commList = data.commList;
					if (data.code==200) {
						nodeHtml +="<select disabled name='parameter0' title='parameter0' class='abc-attr parameter0'>";
						for(var key in commList) {
							if (statExpression.parameter0 == commList[key][0]) {
								nodeHtml +="<option value='"+commList[key][0]+"' selected>"+commList[key][1]+"</option>";
							} else {
								nodeHtml +="<option value='"+commList[key][0]+"'>"+commList[key][1]+"</option>";
							}
							
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
					    var $html = $(nodeHtml).prependTo($(parent));
			            $html.find("select").css({"width":"15%","marginLeft":"16px"}).select2();
					    $CPF.closeLoading();
					} else {
						Dialog.notice("来源实体普通属性加载错误！", "error");
					}
	})
    }
    
    
    //编辑普通属性获取 id    
   /* $(".properOperate", $page).on("click", ".edit_common", function() {//新添加的
    	var $this = $(this); 
    	var $newAdd = $this.closest(".new_add");
    	var $form1 =$newAdd.find("form");
        var statdfid = $this.closest('.entity_ul').attr("statdfid");
        var ischecked = false;
        $this.closest(".entity_attr").addClass('pitch').siblings(".entity_attr").removeClass('pitch');
        
        //总控制
        $form1.find("#id").val(statdfid);
        
        $newAdd.find("#add_comm_mes").html("");
        $newAdd.find("#add_comm_mes").html("编辑属性");        
        $newAdd.find(".opera_comm").show();
    });*/
    
    //编辑普通属性获取 id    
   /* $(".more_proper", $page).on("click", ".edit_common", function() {//新添加的
    	var $this = $(this); 
    	var $newAdd = $this.closest(".new_add");
    	var $form1 =$newAdd.find("form");
        var statdfid = $this.closest('.entity_ul').attr("statdfid");
        var ischecked = false;
        $this.closest(".entity_attr").addClass('pitch').siblings(".entity_attr").removeClass('pitch');
        
        //总控制
        $form1.find("#id").val(statdfid);
        
        $newAdd.find("#add_comm_mes").html("");
        $newAdd.find("#add_comm_mes").html("编辑属性");        
        $newAdd.find(".opera_comm").show();
    });*/
    //编辑多值属性的孩子  获取 id    
   /* $(".more_proper", $page).on("click", ".edit_more_child", function() {
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
    		
    		 Ajax.ajax('admin/dictionary/basicItem/isTwoattr', {
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
    		     });
    		
            $("#add_more_child_mes").html("");
            $("#add_more_child_mes").html("编辑属性");
            $this.closest('.new_add').find(".opera_more_child").show();
        });        
    });
    //编辑分组 获取 id
    $(".properOperate", $page).on("click", "#edit_group", function() {
    	
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
    });*/
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
        saveStatus(entityId, status, entityId, this);
    });
    //过期普通属性
    $(".properOperate", $page).on("click", ".common_change_status", function() {
        var commId = $(this).closest('.entity_ul').attr("entityid");        
        var status = $(this).closest('.entity_ul').attr("status");        
        var entityId = $(".properOperate").attr("parentid");
        
        saveStatus(commId, status, entityId, this);
    });
    //过期函数
    function saveStatus(entityId, status, parentId, th) { 
    	var node = $(th).closest(".entity_attr ");
    	var nodeul = $(th).closest(".entity_ul");
        Ajax.ajax('admin/stat/state/saveStatus', {
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
        		
        		if ("1" == jsonData.basicItem.usingState) {
        			node.addClass('inuse');
        		} else if("0" == jsonData.basicItem.usingState) {
        			node.addClass('newadd');
        		} else if ("-1" == jsonData.basicItem.usingState){
        			node.addClass('inerror');
        		}
        		nodeul.attr("status", jsonData.basicItem.usingState);
        		$(th).html("<i class=\"icon stale-entity\"></i>过期实体");
        	}
        	
        	
        });
    }
    
    //过期关系
   /* $(".entity_relation", $page).on("click", ".chang_status_rela", function() {
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
    });*/
    
    //在实体列表页面右键点击   的时候
    $(".get_entity_attr", $page).click(function() {
    	$('.entity_attr').removeClass('active').removeClass('pitch');
    	$(this).closest('.entity_attr').addClass('pitch');
        var bieCode = $(this).parent().parent().attr("bieCode");
        var sourcecode = $(this).parent().parent().attr("sourcecode");
        
        var status = $(this).parent().parent().attr("status");
        $(".opera_entity").hide();
        $(".properOperate").show();
        $(".more_proper").show();
        $(".entity_relation").show();
        $(".properOperate").attr("parentId", bieCode);
        $(".properOperate").attr("sourcecode", sourcecode);
        
        $(".entity_relation").attr("entityId", bieCode);
       // $(".new_add").remove();
      // enityAttr(bieCode);//初始化所有数据
       initStat(bieCode);//初始化所有数据
       
       initStatFilter(bieCode);
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
    function initStat(bieCode) {
    	
    	var uuid = "dcr" + guid();
        Ajax.ajax('admin/stat/statDf/getStatDfList?uuid='+uuid, {
        	bieCode: bieCode
        }, function(jsonData) {
        	
        	if (jsonData.code == 200) {
        		var statDfList = jsonData.statDfList;
        		//移除指定孩子之外的
        		$(".properOperate").find(".comm_list").children(".newadd").remove();
        		
        		var dimensionStr = "";
    			var factStr = "";
        		for (var key in statDfList) {
        			var statDf = statDfList[key];
        			//按照类型构建维度和事实
        			if (statDf[1] == 1) {
        				dimensionStr = "<div statDfId='"+statDf[0]+"'  title=\"code:"+statDf[7]+", 中文名称:"+statDf[3]+",  英文名称:"+statDf[4]+",数据类型："+statDf[8]+", 数据长度："+statDf[9]+", 字典序："+ statDf[12] +"  \" class=\"entity_attr newadd\">" + statDf[3] 
        				dimensionStr = dimensionStr +"<ul entityid='"+statDf[7]+"' parentid='"+statDf[5]+"' status='"+statDf[6]+"' class=\"entity_ul\" statDfId='"+statDf[0]+"'>" + "<li><a href=\"javascript:void(0)\" class=\"edit_common\"><i class=\"icon edit-entity\"></i>编辑属性</a></li>"+ "<li><a href=\"javascript:void(0)\" class=\"common_change_status\"><i class=\"icon stale-entity\"></i>过期实体</a></li>"
        				dimensionStr = dimensionStr+"<li><a href=\"javascript:void(0)\" patentId='' class=\"delete_attr\"><i class=\"icon edit-entity\"></i>删除属性</a></li>" 
        				dimensionStr = dimensionStr + "</ul><i class=\"icon status\"></i>" +"</div>";
        				$(".common_proper").find(".comm_list").children(".add_comm").before(dimensionStr);
        			} else {
        				factStr = "<div statDfId='"+statDf[0]+"' title=\"code:"+statDf[7]+", 中文名称:"+statDf[3]+",  英文名称:"+statDf[4]+",数据类型："+statDf[8]+", 数据长度："+statDf[9]+", 字典序："+ statDf[12] +"  \" class=\"entity_attr newadd\">" + statDf[3] 
        				factStr = factStr +"<ul entityid='"+statDf[7]+"' parentid='"+statDf[5]+"' status='"+statDf[6]+"' class=\"entity_ul\" statDfId='"+statDf[0]+"'>" + "<li><a href=\"javascript:void(0)\" class=\"edit_common\"><i class=\"icon edit-entity\"></i>编辑属性</a></li>"+ "<li><a href=\"javascript:void(0)\" class=\"common_change_status\"><i class=\"icon stale-entity\"></i>过期实体</a></li>"
        				factStr = factStr+"<li><a href=\"javascript:void(0)\" patentId='' class=\"delete_attr\"><i class=\"icon edit-entity\"></i>删除属性</a></li>" 
        				factStr = factStr + "</ul><i class=\"icon status\"></i>" +"</div>";
        				$(".more_proper").find(".comm_list").children(".add_comm").before(factStr);
        			}
        		}
        	} else {
        		Dialog.notice(jsonData.msg, "error");
        	}
        	
        })
        
    }
    
    //初始化filters
    function initStatFilter(bieCode) {
    	
    	var parent = $(".entity_relation", $page).find(".collapse-content")[0];
       	$(parent).html("");
    	 //这里加载filters
		Ajax.ajax('admin/node/binFilterBody/getStatFilters', {
			bieCode: bieCode
		}, function(data) {			
			if (data.code == "400") {
				 Dialog.notice("filters加载失败", "error");
				 $CPF.closeLoading();
			} else{
				
				if (data.binFilterBody != undefined) {
					var source = "sourceEntity";
					initFilters(bieCode, data.statFilter, data.binFilterBody, source);
					$CPF.closeLoading();
				}
			}
			
			$CPF.closeLoading();
		});
    }
    
    function initFilters(entityId, statFilter, binFilterBody, source) {
    	
   	 var parent = $(".entity_relation", $page).find(".collapse-content")[0];
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
				str = str + "<div style='display: none;' class=\"statEntity_list cascadeAttr_child clear-fix\"><div class=\"new_add_title\"><span>级联属性孩子</span>" 
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
            
                
                $(".properOperate").append(str);
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
				str = str + "<div style='display: none;' class=\"statEntity_list more_cascadeAttr_child clear-fix\"><div class=\"new_add_title\"><span>级联属性孩子</span>" 
                str = str +"</div><div class=\"clear-fix\"></div>"
                str = str +"</div>"
				//end
                
              //添加编辑级联属性的孩子  div		
 			    str = str + "<div style='padding-left: 1.8em;display: none;' class=\"more_cascadeAttr_child_show\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/opera_entity_icon.png\"><span class=\"opera_entity_img\">级联属性孩子</span><form  id=\"cascadeAttr_form1\" class=\"opera_entity_form\"><input type=\"hidden\" id=\"id\" name=\"id\" value=\"\"><div><span class=\"opera_entity_label\">中文名称<span style=\"color: red;\">*</span></span><input type=\"text\" name=\"cnName\" id=\"cnName\" /></div><div><span class=\"opera_entity_label\">描述</span><textarea name=\"description\" id=\"description\"></textarea></div></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"more_cascadeAttr_child_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"more_cascadeAttr_child_but_confirm\">确认</span></div></div>";
				
				
                str = str + "<div class=\"opera_more_child\"><img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/images/info.png\"><span id=\"add_more_child_mes\"></span><form groupName=\"" + moreArr[i].cnName + "\" groupId=\"" + moreArr[i].code + "\" id=\"more_child_opera_form1\" class=\"opera_entity_form\"><input type=\"hidden\" id=\"groupName\" name=\"groupName\" value=\"\"><input type=\"hidden\" id=\"comm_parent\" name=\"parent\" value=\"\"><input type=\"hidden\" id=\"edit_dataType\" value=\"\"><input type=\"hidden\" id=\"edit_dictParentId\" value=\"\"><input type=\"hidden\" name=\"code\" id=\"code\"/><div><span class=\"opera_entity_label\">中文名称<span style=\"color: red;\">*</span></span><input type=\"text\" name=\"cnName\" id=\"cnName\"/></div><div><span class=\"opera_entity_label\">英文名称</span><input type=\"text\" name=\"enName\" id=\"enName\"/></div><div class=\"select-wrap\"><span class=\"opera_entity_label\" id=\"cn_dataType\">数据类型<span style=\"color: red;\">*</span></span><select id=\"dataType\" class=\"enum_daType_two\" name=\"dataType\"></select><span style='display:none;'  class=\"opera_entity_label\" id=\"cn_needHistory\">记录历史记录</span><select style='display:none;' id=\"needHistory\" name=\"needHistory\"><option value='1' selected='selected'>是</option><option value='0'>否</option></select></div><div><span class=\"opera_entity_label\" id=\"cn_dataRange\">数据长度<span style=\"color: red;\">*</span></span><input type=\"text\" name=\"dataRange\" id=\"dataRange\" /></div><div><span class=\"opera_entity_label\">描述</span><textarea name=\"description\" id=\"description\"></textarea></div></form><div class=\"opera_entity_btn\"><span class=\"entity-btn-cancel\" id=\"more_child_but_cancel\">取消</span><span class=\"entity-btn-confirm\" id=\"more_child_but_confirm\">确认</span></div></div>";                
                //这里是显示二级信息以及二级的孩子数据 start
                str = str + "<div class=\"statEntity_list twoLevelAttr_child clear-fix\"><div class=\"new_add_title\"><span id=\"twoLevelAttr_name\"></span>" 
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
    $(".properOperate", $page).on("click", ".card>li.card-list", function (e) {
    	e.stopPropagation();
    	
        if ($(".properOperate", $page).find(".icon-add.active").length > 0) {
            var el = $(".properOperate", $page).find(".icon-add.active")[0];
        } else if ($(".properOperate", $page).find(".icon-add-abc.active").length > 0) {
            var el = $(".properOperate", $page).find(".icon-add-abc.active")[0];
        }else if ($(".properOperate", $page).find(".icon-add-filterGroup.active").length > 0) {
            var el = $(".properOperate", $page).find(".icon-add-filterGroup.active")[0];
        }else if ($(".properOperate", $page).find(".icon-add-filter.active").length > 0) {
            var el = $(".properOperate", $page).find(".icon-add-filter.active")[0];
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
        var entityId = $(el).closest(".properOperate").attr("parentId");
       /* if($(el).closest(".properOperate").hasClass("entity-title")){
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
        var $content = $(el).closest(".collapse-header").siblings(".collapse-content");
        
        var entityId = $(el).closest(".collapse-header").attr("entityId");
        var source = $(el).closest(".collapse-header").attr("source");
        
        var dragWrapLen = $(".dragEdit-wrap").length + 1 ;
        $CPF.showLoading();
            var relativeHtml = "<li class='attr-relative'>" +
            "<div class='attr-relative-title collapse-header' source='"+source+"' entityId='"+entityId+"' data-order='' data-id=''>" +
            "<div class='icon-label attr-relative-dict attr-relative'>" +
            "<i class='icon icon-attr-relative'></i><span class='text'>filter</span>" +
            "</div>" +
            "<div class='label-bar filter edit'>" +
            "<input type='text' class='edit-input filterName text' value='filter名称'>";
            
            relativeHtml += "<select class='abcattrCodeData'>";	
            //getCriteriaSymbol
    	    Ajax.ajax('admin/node/basicItemNode/getComm', {
    	    	entityId:entityId
    	    }, function(data){		
    	    	var entityData = data.comm;
    	    	 Ajax.ajax('admin/node/basicItemNode/getRepeatChild', {
    	    		 repeatId:entityId
    	    	    }, function(data){		
    	    	    	var repeatData = data.repeatChild;
    	    	if (source == "moreAttr") {
    	    		 for(var key in repeatData) {
    	 		    	relativeHtml += "<option value='"+repeatData[key].code+"'>"+repeatData[key].cnName+"</option>"; 
    	 	         };
    	    	} else {
    	    		 for(var key in entityData) {
    	 		    	relativeHtml += "<option value='"+entityData[key][0]+"'>"+entityData[key][1]+"</option>"; 
    	 	         };
    	    	}
		   
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
    	    })
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
        var editBar = $(".properOperate", $page).find(".label-bar.edit");
        var editEntity = $(".properOperate", $page).find(".entity-edit-wrap.edit");
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
    $(".properOperate", $page).on("click", ".icon-arrow, .icon-arrow-sm", function (e) {
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
         
            Ajax.ajax('admin/node/basicItemNode/getComm', {
    	    	entityId:enID
    	    }, function(data){		
    	    	var entityData = data.comm;
    	    	 Ajax.ajax('admin/node/basicItemNode/getRepeatChild', {
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
    	    	} else {
    	    		 for(var key in entityData) {
    	    			 if (filterBodyList.abcattrCode == entityData[key].code) {
    	    				 relativeHtml += "<option selected value='"+entityData[key][0]+"'>"+entityData[key][1]+"</option>"; 
    	    			 } else {
    	    				 relativeHtml += "<option value='"+entityData[key][0]+"'>"+entityData[key][1]+"</option>"; 
    	    			 }
    	    		 }
    	    	}
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
            
    	    })
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
    $(".properOperate", $page).on("click", ".icon-add, .icon-add-abc", function (e) {
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
    $(".properOperate", $page).on("click", ".icon-add-filterGroup", function (e) {
       
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
    $(".properOperate", $page).on("click", ".icon-add-filter", function (e) {
       
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
    	$(".properOperate", $page).on("click", ".icon-trash, .icon-trash-sm", function (e) {
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
    $(".properOperate", $page).on("dblclick", ".label-bar", function(){
		$(this).find(".edit-input").removeAttr("disabled");
    	$(this).find("select").removeAttr("disabled");
        $(this).addClass("edit");
    })
    
    //双击编辑
   $(".properOperate", $page).on("dblclick", ".entity-title", function(){   
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
        	var bieCode = $abcBar.closest(".collapse-header").attr("entityid");
        	
        	$CPF.showLoading();
        	Ajax.ajax('admin/node/binFilterBody/saveOrUpdate', {
    			 name: name,
    			 parentId: parentId,
    			 id: id,
    			 isFilters: true,
    			 dataType: 10,
    			 opt:opt,
    			 bieCode:bieCode,
    			 isStat: true
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
    				 	.attr("entityid", rightRecordType);
    			 
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
    $(".properOperate", $page).on("click", ".opera.confirm", function(e) {  
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