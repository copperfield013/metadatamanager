<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>

<style>
	.entity {
		position:relative;
		padding: 10px 0px 10px 0px;
		left: 10%;
		right: 10%;
		top: 10%;
		width: 80%;
		margin-bottom: 40px;
	}
	
	.entity_head>span {
		font-size: 24px;
	}
	.entity_list{
		border: 1px solid #ebedf0;
		top: 24px;
		height: 250px;
	}
	 .entity_attr>ul>li>a {
        border: 1px solid #d9d9d9;
        padding: 10px 20px 10px 20px;
        margin: 21px 4px 0px 15px;
        color: #307b89;
        font-size:18px;
        float:left;
    }
    
	.entity_attr_img {
		border: 1px solid #d9d9d9;
	    padding: 10px 42px 10px 42px;
	    margin: 21px 4px 0px 15px;
	    word-break: keep-all;
	    color: #307b89;
	    font-size:18px;
		float:left;
	} 
	.opera_entity{
		border: 1px solid #d9d9d9;
		height: 134px;
		top: 24px;
		display:none;
	}
	
	.opera_relation {
		border: 1px solid #d9d9d9;
		height: 169px;
		top: 24px;
		display:none;
		margin-top:7px;
	}
	.opera_more{
		border: 1px solid #d9d9d9;
		height: 169px;
		top: 24px;
		display:none;
		margin-top:7px;
	}
	.opera_group{
		border: 1px solid #d9d9d9;
		height: 134px;
		top: 24px;
		display:none;
		margin-top:7px;
	}
	
	.opera_entity_img{
		position: relative;
		top:32px;
		left: 32px;
	}
	.entity_ul {
		display: none;
		position:relative;
        padding: 47px 10px 0px 20px;
        margin: 21px 4px 0px 15px;
        float:left;
	}
	
	.entity_ul>li>a {
	color: #126def;
	border: 1px solid #d9d9d9;
	padding: 0px 21px 0px 19px;
    margin: 0px -11px 0px -153px;
    color: #307b89;
    font-size:18px;
}
	.common_proper, .more_proper , .entity_relation{
		display: none;
	}
	.opera_comm, .opera_more_child {
		display: none;
	}
	ul,li {
	list-style-type:none;
	padding: 0;
	margin: 0;
	} 
</style>
<div id="demo-list">
	
	<div class="entity">
		 <div class="entity_head">
			<img src="media/admin/dictionary/basicItem/entity_list_icon.png" />
			<span>实体列表</span>
		</div>
		<div class="entity_list">
			<c:forEach items="${list }" var="item" varStatus="i">
				<div class="entity_attr">
					<ul>
						<li>
							<a href="javascript:void(0)">${item.cnName }</a>
							<ul class="entity_ul" entityId="${item.code }" status="${item.usingState }">
								<li><a href="javascript:void(0)" class="edit_entity">编辑实体</a></li>
								<li><a href="javascript:void(0)" class="change_status"><c:if test="${item.usingState eq '1' }">过期实体</c:if><c:if test="${item.usingState eq '2' }">解除过期</c:if></a></li>
								<li><a href="javascript:void(0)" class="get_entity_attr">实体属性</a></li>
							</ul>
						</li>
					</ul>
				</div>
			</c:forEach>
			<img class="entity_attr_img" id="add_entity" alt="添加实体" src="media/admin/dictionary/basicItem/addEntity_icon.png">
		</div>
		<div class="opera_entity">
			<img class="opera_entity_img" src="media/admin/dictionary/basicItem/opera_entity_icon.png">
			<span class="opera_entity_img" id="add_entity_mes"></span>
			<form id="entity_opera_form1" class="opera_entity_img">
				code: <input type="text" name="code" id="code"/>
				<input type="hidden" name="dataType" value="record">
				中文名称:<input type="text" name="cnName" id="cnName"/>
				英文名称:<input type="text" name="enName" id="enName"/><br>
				
				<input id="entity_but_cancel" type="button" value="取消">
				<input id="entity_but_confirm" type="button" value="确认">
			</form>
		</div>
	</div>
	<div class="common_proper entity" parentId="">
           <div style="position: relative; height: 20px;">
	           	<div style="width: 220px;float: left;">
	           		<img src="media/admin/dictionary/basicItem/common_proper_title_icon.png">普通属性
	           	</div>
               <div style="float: right;width: 220px;">
               	<img id="add_group"  src="media/admin/dictionary/basicItem/add_group_icon.png">添加分组
               </div>
           </div>
           <div class="opera_group">
				<img class="opera_entity_img" src="media/admin/dictionary/basicItem/opera_entity_icon.png">
				<span class="opera_entity_img" id="add_group_mes"></span>
				<form id="group_opera_form1" class="opera_entity_img">
					code: <input type="text" name="code" id="code"/>
					<input type="hidden" id="group_parent" name="parent" value="">
					<input type="hidden" name="dataType" value="group">
					中文名称:<input type="text" name="cnName" id="cnName"/>
					<input id="group_but_cancel" type="button" value="取消">
					<input id="group_but_confirm" type="button" value="确认">
				</form>
			</div>
     </div>
	<div class="more_proper entity" parentId="">
           <div style="position: relative; height: 20px;">
	           	<div style="width: 220px;float: left;">
	           		<img src="media/admin/dictionary/basicItem/common_proper_title_icon.png">多值属性
	           	</div>
               <div style="float: right;width: 220px;">
               	<img id="add_more" src="media/admin/dictionary/basicItem/add_group_icon.png">添加多值属性
               </div>
           </div>
           <div class="opera_more">
				<img class="opera_entity_img" src="media/admin/dictionary/basicItem/opera_entity_icon.png">
				<span class="opera_entity_img" id="add_more_mes"></span>
				<form id="more_opera_form1" class="opera_entity_img">
					code: <input type="text" name="code" id="code"/>
					<input type="hidden" id="more_parent" name="parent" value="">
					<input type="hidden" name="dataType" value="repeat">
					中文名称:<input type="text" name="cnName" id="cnName"/><br>
					英文名称:<input type="text" name="enName" id="enName"/>
					表描述:<textarea name="tableNameDescription" id="tableNameDescription" rows="" cols=""></textarea><br>
					<input id="more_but_cancel" type="button" value="取消">
					<input id="more_but_confirm" type="button" value="确认">
				</form>
			</div>
     </div>
	<div class="entity_relation entity" entityId="">
           <div style="position: relative; height: 20px;">
	           	<div style="width: 220px;float: left;">
	           		<img src="media/admin/dictionary/basicItem/common_proper_title_icon.png">实体关系管理
	           	</div>
           </div>
           <div class="entity_list entity_relation_list">
           	<img class="entity_attr_img" id="add_entity_relation" alt="添加关系" src="media/admin/dictionary/basicItem/addEntity_icon.png">
           </div>
          <div class="opera_relation">
			<img class="opera_entity_img" src="media/admin/dictionary/basicItem/opera_entity_icon.png">
			<span class="opera_entity_img" id="add_relation_mes"></span>
			<form id="entity_relation_opera_form" class="opera_entity_img">
				<input type="hidden" name="leftRecordType" id="leftRecordType">
				选择右实体：<select id="rightRecordType" name="rightRecordType">
				</select><br>	
			          左实体code:<input type="text" name="typeCode" id="typeCode"/>
				右实体code:<input type="text" name="reverseCode" id="reverseCode"/><br>
				左实体关系名称:<input type="text" name="leftName" id="leftName"/>
				右实体关系名称: <input type="text" name="rightName" id="rightName"/><br>
				
				<input id="relation_but_cancel" type="button" value="取消">
				<input id="relation_but_confirm" type="button" value="确认">
			</form>
		</div>
     </div>
     
</div>

<script>
seajs.use(['dialog', 'ajax'], function(Dialog, Ajax){
	
	//点击 添加实体 显示div
	$("#add_entity").click(function(){
		$("#add_entity_mes").html("");
		$("#add_entity_mes").html("添加实体信息");
		$(".opera_entity").show();
	})
	
	
	//点击 添加分组 显示div
	$("#add_group").click(function(){
		$("#add_group_mes").html("");
		$("#add_group_mes").html("添加分组信息");
		
		$(".opera_group").show();
	});
	
	//点击 添加 多值属性 自身显示div
	$("#add_more").click(function(){
		$("#add_more_mes").html("");
		$("#add_more_mes").html("添加多值属性信息");
		$(".opera_more").show();
	});
	
	
	//点击 添加普通属性加号 显示div
		$(".common_proper").on("click", "#add_comm", function() {
		$(".opera_comm").children("#comm_opera_form1").children("#dataType").children().remove();
		Ajax.ajax('admin/dictionary/basicItem/getDataType','' , function(data){
			var str = "";
			 for(var key in data){
				 str = str + " <option value =\""+key+"\">"+data[key]+"</option>";
		      } 
			 $(".opera_comm").children("#comm_opera_form1").children("#dataType").append(str);
		});	
		$("#add_comm_mes").html("");
		$("#add_comm_mes").html("添加属性");
		$(this).parent().parent().siblings(".opera_comm").show();
	});
	
	//点击 添加多值属性孩子加号 显示div
		$(".more_proper").on("click", "#add_more_child", function() {
		$(".opera_more_child").children("#more_child_opera_form1").children("#dataType").children().remove();
		Ajax.ajax('admin/dictionary/basicItem/getDataType','' , function(data){
			var str = "";
			 for(var key in data){
				 str = str + " <option value =\""+key+"\">"+data[key]+"</option>";
		      } 
			 $(".opera_more_child").children("#more_child_opera_form1").children("#dataType").append(str);
		});	
		$("#add_more_child_mes").html("");
		$("#add_more_child_mes").html("添加多值属性");
		$(this).parent().parent().siblings(".opera_more_child").show();
	});
	
		//点击 添加多值属性孩子加号 显示div
	$(".entity_relation").on("click", "#add_entity_relation", function() {
	
		Ajax.ajax('admin/dictionary/basicItem/entityList','' , function(data){
			var entity = data.entity;
			
			var str = "";
			for(var p in entity){//遍历json数组时，这么写p为索引，0,1
				str = str + "<option value=\""+entity[p].code+"\">"+ entity[p].cnName+"</option>"; 
			}
			
			$(".opera_relation").children("#entity_relation_opera_form").children("#rightRecordType").append(str);
		});	
		$("#add_relation_mes").html("");
		$("#add_relation_mes").html("添加关系");
		$(".opera_relation").show();
	});
		
	// 点击取消  取消添加关系
	$(".entity_relation").on("click", "#relation_but_cancel", function() {
		$(".opera_relation").hide();
	});
		
	//点击取消 ， 取消添加实体
	$("#entity_but_cancel").click(function(){
		$(".opera_entity").hide();
	});
	
	//点击取消 ， 取消添加分组
	$("#group_but_cancel").click(function(){
		$("#cnName").val("");
		$("#code").val("");
		$(".opera_group").hide();
	});
	
	//点击取消 ， 取消添加多值属性自身
	$("#more_but_cancel").click(function(){
		$("#cnName").val("");
		$("#code").val("");
		$(".opera_more").hide();
	});
	
	//点击取消 ， 取消添加普通属性
	$(".common_proper").on("click", "#comm_but_cancel", function() {
		$("#cnName").val("");
		$("#code").val("");
		$(this).parent().parent().hide();
	});
	
	//点击取消 ， 取消添加普通属性
	$(".more_proper").on("click", "#more_child_but_cancel", function() {
		$("#cnName").val("");
		$("#code").val("");
		$(this).parent().parent().hide();
	});
	
	//点击确认， 进行添加分组
	$("#group_but_confirm").click(function(){
		var fData = new FormData(document.querySelector("#group_opera_form1"));
		Ajax.ajax('admin/dictionary/basicItem/do_add',fData , function(data){
			
		});
		$(".opera_group").hide();
		var entityId = $(".common_proper").attr("parentId");
		$(".new_add").remove();
		enityAttr(entityId);
	});
	
	//点击确认， 进行添加多值属性自身
	$("#more_but_confirm").click(function(){
		var fData = new FormData(document.querySelector("#more_opera_form1"));
		Ajax.ajax('admin/dictionary/basicItem/do_add',fData , function(data){
			
		});
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
		var groupName = $(this).parent().attr("groupName");
		$(this).parent().children("#comm_parent").val(parentId);
		$(this).parent().children("#groupName").val(groupName);
		
		var formId = $(this).parent().attr("id");
	
		//var fData = new FormData(document.querySelector("#"+formId));
		
		var code = $(this).parent().children("#code").val();
		var cnName = $(this).parent().children("#cnName").val();
		var enName = $(this).parent().children("#enName").val();
		var dataType = $(this).parent().children("#dataType").val();
		var dataRange = $(this).parent().children("#dataRange").val();
		var dictParentId = $(this).parent().children("#dictParentId").val();
		var groupName = $(this).parent().children("#groupName").val();
		var parent = $(this).parent().children("#comm_parent").val();
		
		if (typeof(dictParentId) == "undefined"){ 
			dictParentId = "";
		}
		
		Ajax.ajax('admin/dictionary/basicItem/do_add',{
			code:code,        
			cnName:cnName,      
			enName:enName,      
			dataType:dataType,    
			dataRange:dataRange,   
			dictParentId:dictParentId,
			groupName:groupName,   
			parent:parent      
		} , function(data){
			
		});
		
		$(".opera_comm").hide();
		var entityId = $(".common_proper").attr("parentId");
		$(".new_add").remove();
		enityAttr(entityId);
	});
	
	//点击确认， 进行添加关系
	$(".entity_relation").on("click", "#relation_but_confirm", function() {
		//comm_opera_form1
		//设置隐藏元素的值
		var entityId = $(".entity_relation").attr("entityId");
		$(this).parent().children("#leftRecordType").val(entityId);
		
		var typeCode = $(this).parent().children("#typeCode").val();
		var reverseCode = $(this).parent().children("#reverseCode").val();
		var leftName = $(this).parent().children("#leftName").val();
		var rightName = $(this).parent().children("#rightName").val();
		var rightRecordType = $(this).parent().children("#rightRecordType").val();
		var leftRecordType = $(this).parent().children("#leftRecordType").val();
		
		Ajax.ajax('admin/dictionary/recordRelationType/doAdd',{
			typeCode:typeCode,        
			reverseCode:reverseCode,      
			leftName:leftName,      
			rightName:rightName,    
			rightRecordType:rightRecordType,   
			leftRecordType:leftRecordType,
		} , function(data){
			
		});
		
		$(".opera_relation").hide();
		var entityId = $(".entity_relation").attr("entityId");
		$(".new_add").remove();
		enityAttr(entityId);
	});
	
	//点击确认， 进行添加多值属性的孩子
	$(".more_proper").on("click", "#more_child_but_confirm", function() {
		//设置隐藏元素的值
		var groupName = $(this).parent().attr("groupName");
		var parentId = $(this).parent().attr("groupId");
		$(this).parent().children("#comm_parent").val(parentId);
		$(this).parent().children("#groupName").val(groupName);
		
		var formId = $(this).parent().attr("id");
	
		//var fData = new FormData(document.querySelector("#"+formId));
		
		var code = $(this).parent().children("#code").val();
		var cnName = $(this).parent().children("#cnName").val();
		var enName = $(this).parent().children("#enName").val();
		var dataType = $(this).parent().children("#dataType").val();
		var dataRange = $(this).parent().children("#dataRange").val();
		var dictParentId = $(this).parent().children("#dictParentId").val();
		var groupName = $(this).parent().children("#groupName").val();
		var parent = $(this).parent().children("#comm_parent").val();
		
		if (typeof(dictParentId) == "undefined"){ 
			dictParentId = "";
		}
		
		Ajax.ajax('admin/dictionary/basicItem/do_add',{
			code:code,        
			cnName:cnName,      
			enName:enName,      
			dataType:dataType,    
			dataRange:dataRange,   
			dictParentId:dictParentId,
			groupName:groupName,   
			parent:parent      
		} , function(data){
			
		});
		$(".opera_more_child").hide();
		var entityId = $(".more_proper").attr("parentId");
		$(".new_add").remove();
		enityAttr(entityId);
	});
	
	//点击确认， 进行添加操作
	$("#entity_but_confirm").click(function(){
		var fData = new FormData(document.querySelector("#entity_opera_form1"));
		Ajax.ajax('admin/dictionary/basicItem/do_add',fData , function(data){
			
		});
	});
	
	//给 li  注册鼠标点击事件  让自己的ul显示出来
	var $li = $(".entity_attr>ul>li");
	$li.click(function(){
		$(this).children("ul").toggle();
	});
	
	//普通属性显示 ul
	$(".common_proper").on("click", ".common_proper_li", function() {
		$(this).children("ul").toggle();
	});
	
	//多值属性显示 ul
	$(".more_proper").on("click", ".more_proper_li", function() {
		$(this).children("ul").toggle();
	});
	
	//实体关系   显示 ul
	$(".entity_relation").on("click", ".entity_relation_li", function() {
		$(this).children("ul").toggle();
	});
	/* $li.mouseout(function(){
		$(this).children("ul").hide();
	}); */
	
	//如果是枚举， 则显示下拉列表
	$(".common_proper").on("click", "#is_enum", function() {
		$(".opera_comm").children("#comm_opera_form1").children("#dictParentId").remove();
		$(".opera_comm").children("#comm_opera_form1").children("#span_enum").remove();
		if ($(this).is(':checked')) {
			//选中  则显示下拉列表
		Ajax.ajax('admin/dictionary/dictParentItem/getDictPitem','' , function(data){
			var dataArr = data.dictPitem;
			var str = "<span id=\"span_enum\">字典序：</span><select id=\"dictParentId\" name=\"dictParentId\">";
			for(var p in dataArr){//遍历json数组时，这么写p为索引，0,1
				str = str + "<option value=\""+dataArr[p].id+"\">"+ dataArr[p].name+"</option>"; 
			}
			str = str+"</select>";	
			$(".opera_comm").children("#comm_opera_form1").children("#dataType").after(str);
		});	
		} 
	});
	
	//如果是枚举， 则显示下拉列表  多值属性
	$(".more_proper").on("click", "#is_enum_more_child", function() {
		$(".opera_more_child").children("#more_child_opera_form1").children("#dictParentId").remove();
		$(".opera_more_child").children("#more_child_opera_form1").children("#span_enum").remove();
		if ($(this).is(':checked')) {
			//选中  则显示下拉列表
		Ajax.ajax('admin/dictionary/dictParentItem/getDictPitem','' , function(data){
			var dataArr = data.dictPitem;
			var str = "<span id=\"span_enum\">字典序：</span><select id=\"dictParentId\" name=\"dictParentId\">";
			for(var p in dataArr){//遍历json数组时，这么写p为索引，0,1
				str = str + "<option value=\""+dataArr[p].id+"\">"+ dataArr[p].name+"</option>"; 
			}
			str = str+"</select>";	
			$(".opera_more_child").children("#more_child_opera_form1").children("#dataType").after(str);
		});	
		} 
	});
	
	//编辑实体获取 id    
	$(".edit_entity").click(function(){
		var entityId = $(this).parent().parent().attr("entityId");
		Ajax.ajax('admin/dictionary/basicItem/getOne',{
			id : entityId
		} , function(jsonData){
			var $form1 = $("#entity_opera_form1");
			$form1.children("#code").val(jsonData.code);
			$form1.children("#code").attr("readonly", "readonly");
			$form1.children("#cnName").val(jsonData.cnName);
			$form1.children("#enName").val(jsonData.enName);
			
			$("#add_entity_mes").html("");
			$("#add_entity_mes").html("编辑实体信息");
			$(".opera_entity").show();
		});
	});
	
	//编辑普通属性获取 id    
		$(".common_proper").on("click", ".edit_common", function() {
			
		var entityId = $(this).parent().parent().attr("entityId");
		Ajax.ajax('admin/dictionary/basicItem/getOne',{
			id : entityId
		} , function(jsonData){
			      
			var $form1 = $(".opera_comm").children("#comm_opera_form1");
			$form1.children("#code").val(jsonData.code);
			$form1.children("#code").attr("readonly", "readonly");
			$form1.children("#cnName").val(jsonData.cnName);
			$form1.children("#enName").val(jsonData.enName);
			$form1.children("#dataRange").val(jsonData.dataRange);
			$form1.children("#edit_dataType").val(jsonData.dataType);
			$form1.children("#edit_dictParentId").val(jsonData.dictParentId);
		});
		
		$(".opera_comm").children("#comm_opera_form1").children("#dataType").children().remove();
		Ajax.ajax('admin/dictionary/basicItem/getDataType','' , function(data){
			var $form1 = $(".opera_comm").children("#comm_opera_form1");
			var dataType = $form1.children("#edit_dataType").val();
			var str = "";
			 for(var key in data){
				 if (dataType == data[key]) {
					 str = str + " <option selected=\"selected\" value =\""+key+"\">"+data[key]+"</option>";
				 } else {
					 str = str + " <option value =\""+key+"\">"+data[key]+"</option>";
				 }
		      } 
			 
			 $(".opera_comm").children("#comm_opera_form1").children("#dataType").append(str);
		});	
		
		//这里判读是否是枚举类型
		$(".opera_comm").children("#comm_opera_form1").children("#dictParentId").remove();
			$(".opera_comm").children("#comm_opera_form1").children("#span_enum").remove();
			
			
			
				Ajax.ajax('admin/dictionary/dictParentItem/getDictPitem','' , function(data){
					var dataArr = data.dictPitem;
					var $form1 = $(".opera_comm").children("#comm_opera_form1");
					var dictParentId = $form1.children("#edit_dictParentId").val();
					
					if(dictParentId!=""){
						var str = "<span id=\"span_enum\">字典序：</span><select id=\"dictParentId\" name=\"dictParentId\">";
						for(var p in dataArr){//遍历json数组时，这么写p为索引，0,1
							if (dictParentId ==dataArr[p].id) {
								str = str + "<option selected=\"selected\"  value=\""+dataArr[p].id+"\">"+ dataArr[p].name+"</option>"; 
							}else {
								str = str + "<option value=\""+dataArr[p].id+"\">"+ dataArr[p].name+"</option>"; 
							}
						}
						str = str+"</select>";	
						$(".opera_comm").children("#comm_opera_form1").children("#dataType").after(str);
					}
				});	

		$("#add_comm_mes").html("");
		$("#add_comm_mes").html("编辑属性");
		$(this).parent().parent().parent().parent().parent().parent().parent().siblings(".opera_comm").show();
	});
	
		
		//编辑多值属性的孩子  获取 id    
	$(".more_proper").on("click", ".edit_more_child", function() {
		var entityId = $(this).parent().parent().attr("entityId");
		Ajax.ajax('admin/dictionary/basicItem/getOne',{
			id : entityId
		} , function(jsonData){
			var $form1 = $(".opera_more_child").children("#more_child_opera_form1");
			$form1.children("#code").val(jsonData.code);
			$form1.children("#code").attr("readonly", "readonly");
			$form1.children("#cnName").val(jsonData.cnName);
			$form1.children("#enName").val(jsonData.enName);
			$form1.children("#dataRange").val(jsonData.dataRange);
			$form1.children("#edit_dataType").val(jsonData.dataType);
			$form1.children("#edit_dictParentId").val(jsonData.dictParentId);
		});
		
		$(".opera_more_child").children("#more_child_opera_form1").children("#dataType").children().remove();
		Ajax.ajax('admin/dictionary/basicItem/getDataType','' , function(data){
			var $form1 = $(".opera_more_child").children("#more_child_opera_form1");
			var dataType = $form1.children("#edit_dataType").val();
			var str = "";
			 for(var key in data){
				 if (dataType == data[key]) {
					 str = str + " <option selected=\"selected\" value =\""+key+"\">"+data[key]+"</option>";
				 } else {
					 str = str + " <option value =\""+key+"\">"+data[key]+"</option>";
				 }
		      } 
			 
			 $(".opera_more_child").children("#more_child_opera_form1").children("#dataType").append(str);
		});	
		
		//这里判读是否是枚举类型
		$(".opera_more_child").children("#more_child_opera_form1").children("#dictParentId").remove();
			$(".opera_more_child").children("#more_child_opera_form1").children("#span_enum").remove();
				Ajax.ajax('admin/dictionary/dictParentItem/getDictPitem','' , function(data){
					var dataArr = data.dictPitem;
					var $form1 = $(".opera_more_child").children("#more_child_opera_form1");
					var dictParentId = $form1.children("#edit_dictParentId").val();
					
					if(dictParentId!=""){
						var str = "<span id=\"span_enum\">字典序：</span><select id=\"dictParentId\" name=\"dictParentId\">";
						for(var p in dataArr){//遍历json数组时，这么写p为索引，0,1
							if (dictParentId ==dataArr[p].id) {
								str = str + "<option selected=\"selected\"  value=\""+dataArr[p].id+"\">"+ dataArr[p].name+"</option>"; 
							}else {
								str = str + "<option value=\""+dataArr[p].id+"\">"+ dataArr[p].name+"</option>"; 
							}
						}
						str = str+"</select>";	
						$(".opera_more_child").children("#more_child_opera_form1").children("#dataType").after(str);
					}
				});	

		$("#add_more_child_mes").html("");
		$("#add_more_child_mes").html("编辑属性");
		$(this).parent().parent().parent().parent().parent().parent().parent().siblings(".opera_more_child").show();
	});	
		
	
	//编辑分组 获取 id
	$(".common_proper").on("click", "#edit_group", function() {
	
		var groupId = $(this).attr("groupId");
		Ajax.ajax('admin/dictionary/basicItem/getOne',{
			id : groupId
		} , function(jsonData){
			var $form1 = $("#group_opera_form1");
			$form1.children("#code").val(jsonData.code);
			$form1.children("#code").attr("readonly", "readonly");
			$form1.children("#cnName").val(jsonData.cnName);
			$form1.children("#group_parent").val(jsonData.parent);
			
			$("#add_group_mes").html("");
			$("#add_group_mes").html("编辑分组信息");
			$(".opera_group").show();
		});
	});
	
	//编辑多值属性自身 获取id
	$(".more_proper").on("click", "#edit_more", function() {
		var groupId = $(this).attr("groupId");
		Ajax.ajax('admin/dictionary/basicItem/getOne',{
			id : groupId
		} , function(jsonData){
			var $form1 = $("#more_opera_form1");
			$form1.children("#code").val(jsonData.code);
			$form1.children("#code").attr("readonly", "readonly");
			$form1.children("#cnName").val(jsonData.cnName);
			$form1.children("#enName").val(jsonData.enName);
			$form1.children("#more_parent").val(jsonData.parent);
			$form1.children("#tableNameDescription").val(jsonData.tableNameDescription);
			
			$("#add_more_mes").html("");
			$("#add_more_mes").html("编辑多值属性信息");
			$(".opera_more").show();
		});
	});
	
	//过期实体获取 id    
	$(".change_status").click(function(){
		var entityId = $(this).parent().parent().attr("entityId");
		var status = $(this).parent().parent().attr("status");
		saveStatus(entityId, status);
	});
	
	//过期普通属性
	$(".common_proper").on("click", ".common_change_status", function() {
		var commId = $(this).parent().parent().attr("entityId");
		var status = $(this).parent().parent().attr("status");
		var entityId = $(".common_proper").attr("parentId");
		
		saveStatus(commId, status);
		$(".new_add").remove();
		enityAttr(entityId);
	});
	
	//过期 多值属性 属性
	$(".more_proper").on("click", ".more_child_change_status", function() {
		var commId = $(this).parent().parent().attr("entityId");
		var status = $(this).parent().parent().attr("status");
		var entityId = $(".common_proper").attr("parentId");
		
		saveStatus(commId, status);
		$(".new_add").remove();
		enityAttr(entityId);
	});
	
	//过期函数
	function saveStatus(entityId, status){
		//statusStr= normal//正常    
		//statusStr=	pastDue//过期
		if (status == '1') {
			status = 'pastDue';
		} else {
			status = 'normal';
		}
		
		Ajax.ajax('admin/dictionary/basicItem/saveStatus',{
			id : entityId,
			statusStr : status
		} , function(jsonData){
		});
	}
	
	//在实体列表页面右键点击   的时候
	$(".get_entity_attr").click(function(){
		
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
	
	//获取实体属性的ajax
	function enityAttr(entityId) {
		
		Ajax.ajax('admin/dictionary/basicItem/attrByPid',{
			parentId : entityId
		} , function(jsonData){
	        var commonArr = jsonData.commonProper;//普通属性
            for(var i=0;i<commonArr.length;i++) {
            	var str = "<div class=\"new_add\">"
            			+"<div style=\"position: relative;height: 300px; border: 1px solid;\">" 
            			+ "<div style=\"float: left;\">"+commonArr[i].cnName+"<img id=\"edit_group\" groupId=\""+commonArr[i].code+"\" src=\"media/admin/dictionary/basicItem/edit_icon.png\">"+"</div>"
                        +"<div>";
                        
				for(var j=0;j<commonArr[i].childList.length;j++) {
					str =str + "<div class=\"entity_attr\">"
						+"<ul>"
						+ "<li class=\"common_proper_li\">"
						+"<a href=\"javascript:void(0)\">"+commonArr[i].childList[j].cnName+"</a>"
						+ "<ul class=\"entity_ul\" entityId=\""+commonArr[i].childList[j].code+"\" status=\""+commonArr[i].childList[j].usingState+"\">"
						+ "<li><a href=\"javascript:void(0)\" class=\"edit_common\">编辑属性</a></li>"
						+"<li><a href=\"javascript:void(0)\" class=\"common_change_status\">"
						if (commonArr[i].childList[j].usingState == '1') {
							str =str + "过期实体"
						} else {
							str =str + "解除过期"
						}
						str =str +"</a></li>"
						+"</ul>"
						+"</li>"
						+"</ul>"
						+"</div>";    
	                 }
				 str =str +"<img class=\"entity_attr_img\" id=\"add_comm\" alt=\"添加普通属性\" src=\"media/admin/dictionary/basicItem/addEntity_icon.png\">"
                        + " </div>"      
                        + " </div>";
               
                 str=str + "<div class=\"opera_comm\" style=\"height: 200px;border: 1px solid;\">"
                 		+ "<img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/opera_entity_icon.png\">"
                 		+ "<div><div style=\"float: left;\"><span class=\"opera_entity_img\" id=\"add_comm_mes\"></span></div><div style=\"float: right;\">"
                 		+ "<span class=\"col-lg-6 col-md-6 col-xs-6\">"
	        			+ "<input id=\"is_enum\" class=\"checkbox-slider slider-icon colored-blue\" type=\"checkbox\">"	
	        			+ "<label class=\"text\">是否枚举</label>"	
        				+ "</span></div></div>"	
                        + "<form groupName=\""+commonArr[i].cnName+"\" groupId=\""+commonArr[i].code+"\" id=\"comm_opera_form1\" class=\"opera_entity_img\">"
                        + "<input type=\"hidden\" id=\"groupName\" name=\"groupName\" value=\"\">"
                        + "<input type=\"hidden\" id=\"comm_parent\" name=\"parent\" value=\"\">"
                        + "<input type=\"hidden\" id=\"edit_dataType\" value=\"\">"
                        + "<input type=\"hidden\" id=\"edit_dictParentId\" value=\"\">"
                        + "code: <input type=\"text\" name=\"code\" id=\"code\"/>"
                		+ "中文名称:<input type=\"text\" name=\"cnName\" id=\"cnName\"/>"
                		+ "英文名称:<input type=\"text\" name=\"enName\" id=\"enName\"/> <br>"
                		+ "数据长度:<input type=\"text\" name=\"dataRange\" id=\"dataRange\"/>"
                		+ "数据类型:<select id=\"dataType\" name=\"dataType\">"
                		+"<option value =\"\">--请选择--</option>"
                		+ "</select>  <br>"
        				+ "<input id=\"comm_but_cancel\" type=\"button\" value=\"取消\">"
                		+ "<input id=\"comm_but_confirm\" type=\"button\" value=\"确认\">"
                		+ "</form>"
                		+ "</div>";
                str=str + "</div>" ;   
				  $(".common_proper").append(str);
            }
	        
            var moreArr = jsonData.moreProper;//多值属性
            for(var i=0;i<moreArr.length;i++) {
            	var str = "<div class=\"new_add\">"
            			+"<div style=\"position: relative;height: 300px; border: 1px solid;\">" 
            			+ "<div style=\"float: left;\">"+moreArr[i].cnName+"<img id=\"edit_more\" groupId=\""+moreArr[i].code+"\" src=\"media/admin/dictionary/basicItem/edit_icon.png\">"+"</div>"
                        +"<div>";
                        
				for(var j=0;j<moreArr[i].childList.length;j++) {
					str =str + "<div class=\"entity_attr\">"
						+"<ul>"
						+ "<li class=\"more_proper_li\">"
						+"<a href=\"javascript:void(0)\">"+moreArr[i].childList[j].cnName+"</a>"
						+ "<ul class=\"entity_ul\" entityId=\""+moreArr[i].childList[j].code+"\" status=\""+moreArr[i].childList[j].usingState+"\">"
						+ "<li><a href=\"javascript:void(0)\" class=\"edit_more_child\">编辑属性</a></li>"
						+"<li><a href=\"javascript:void(0)\" class=\"more_child_change_status\">"
						if (moreArr[i].childList[j].usingState == '1') {
							str =str + "过期实体"
						} else {
							str =str + "解除过期"
						}
						str =str +"</a></li>"
						+"</ul>"
						+"</li>"
						+"</ul>"
						+"</div>";    
	                 }
				 str =str +"<img class=\"entity_attr_img\" id=\"add_more_child\" alt=\"添加多值属性\" src=\"media/admin/dictionary/basicItem/addEntity_icon.png\">"
                        + " </div>"      
                        + " </div>";
               
                 str=str + "<div class=\"opera_more_child\" style=\"height: 200px;border: 1px solid;\">"
                 		+ "<img class=\"opera_entity_img\" src=\"media/admin/dictionary/basicItem/opera_entity_icon.png\">"
                 		+ "<div><div style=\"float: left;\"><span class=\"opera_entity_img\" id=\"add_more_child_mes\"></span></div><div style=\"float: right;\">"
                 		+ "<span class=\"col-lg-6 col-md-6 col-xs-6\">"
	        			+ "<input id=\"is_enum_more_child\" class=\"checkbox-slider slider-icon colored-blue\" type=\"checkbox\">"	
	        			+ "<label class=\"text\">是否枚举</label>"	
        				+ "</span></div></div>"	
                        + "<form groupName=\""+moreArr[i].cnName+"\" groupId=\""+moreArr[i].code+"\" id=\"more_child_opera_form1\" class=\"opera_entity_img\">"
                        + "<input type=\"hidden\" id=\"groupName\" name=\"groupName\" value=\"\">"
                        + "<input type=\"hidden\" id=\"comm_parent\" name=\"parent\" value=\"\">"
                        + "<input type=\"hidden\" id=\"edit_dataType\" value=\"\">"
                        + "<input type=\"hidden\" id=\"edit_dictParentId\" value=\"\">"
                        + "code: <input type=\"text\" name=\"code\" id=\"code\"/>"
                		+ "中文名称:<input type=\"text\" name=\"cnName\" id=\"cnName\"/>"
                		+ "英文名称:<input type=\"text\" name=\"enName\" id=\"enName\"/> <br>"
                		+ "数据长度:<input type=\"text\" name=\"dataRange\" id=\"dataRange\"/>"
                		+ "数据类型:<select id=\"dataType\" name=\"dataType\">"
                		+"<option value =\"\">--请选择--</option>"
                		+ "</select>  <br>"
        				+ "<input id=\"more_child_but_cancel\" type=\"button\" value=\"取消\">"
                		+ "<input id=\"more_child_but_confirm\" type=\"button\" value=\"确认\">"
                		+ "</form>"
                		+ "</div>";
                str=str + "</div>" ;   
				  $(".more_proper").append(str);
            }
         
            //实体关系
            var entityRela = jsonData.entityRela;
            var str = "";
             for(var i=0;i<entityRela.length;i++) {
            	 str=str + "<div class=\"entity_attr new_add\">"
            	 	+ "<ul>"
            	 	+ "<li class=\"entity_relation_li\">"
            	 	+ "<a href=\"javascript:void(0)\">"+entityRela[i].name+"</a>"
            	 	/* + "<ul class=\"entity_ul\" typeCode=\""+entityRela[i].typeCode+"\">"
     				 + "<li><a href=\"javascript:void(0)\" class=\"edit_entity\">编辑关系</a></li>"
     				+ "<li><a href=\"javascript:void(0)\" class=\"change_status\">过期关系</a></li>"
     				+ "</ul>"
     				+ "</li>"
     				+ "</ul>" */
     				+ "</div>"
             }
             $(".entity_relation_list").prepend(str);
		});
	}
})
	
</script>