<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<link rel="stylesheet" href="media/admin/buildproject/css/list.css">
<div id="buildproject-list">
	<nav>
		<form class="form-inline" action="admin/module/configModule/list">
		</form>
	</nav>
	
	<!-- 实体列表 -->
	<div class="entityItems">
		
	</div>
	
	<div class="buildproject">
		<a id="downloadItemFile" href="javascript:;" >下载Item文件</a>
		<a id="downloadEnumFile" href="javascript:;" >下载枚举文件</a>
		<a id="downloadRelationFile" href="javascript:;" >下载关系文件</a>
		<a id="downloadProject" href="javascript:;" >下载项目</a>
	</div>
</div>
<script>
	seajs.use(['dialog','utils', 'ajax', '$CPF'], function(Dialog, Utils, Ajax, $CPF){
		var $page = $('#buildproject-list');
		
		
		 $(function(){
		    $CPF.showLoading();
		    
		    var $div = $(".entityItems");
		    //  获取实体列表
		    Ajax.ajax('admin/dictionary/basicItem/entityList', {
		    }, function(data){		    	
		    	var entityList = data.entity;
		    	var str = "";
		    	for(var key in entityList) {
		    		var cnName = entityList[key].cnName;
		    		var code = entityList[key].code;
		    		str+="<div entityId='"+code+"' class='entity_attr' >" + cnName;
		    		str +="</div>";
		    	}
		    	$(str).prependTo($div);
		    	
		    	$CPF.closeLoading();
		    }, {async: true})
		    
		    $CPF.closeLoading();
		    })
		    
		    //选中实体
		    $(".entityItems", $page).on("click", ".entity_attr", function (e) {
		    	var $this = $(this);
		    	if ($this.hasClass("inse")) {
		    		$this.removeClass("inse");
		    	} else {
		    		$this.addClass("inse");
		    	}
		    	
	  	    }); 
		
		
		 //下载
		$(".buildproject", $page).on("click", "#downloadProject", function (e) {
			
			var $childs = $(".entityItems").children(".inse");
			var length = $childs.length;
			Dialog.confirm('选中实体数量为: "'+length+'", 是否确认下载项目？', function(yes){
				var entityCodes = "";
				$childs.each(function(){
				    var entityCode = $(this).attr("entityId");
				    entityCodes+=entityCode + ",";
				  });
				
				entityCodes = entityCodes.substr(0, entityCodes.length-1);
				
	        	if(yes){
	        		var url="admin/buildproject/downloadProject?entityCodes=" +entityCodes ;
	        		Ajax.download(url);
	        	}
			});
	  	 }); 
		
		$(".buildproject", $page).on("click", "#downloadEnumFile", function (e) {
			Dialog.confirm('确认下载枚举文件？', function(yes){
	        	if(yes){
	        		var url="admin/buildproject/downloadEnumFile";
	        		Ajax.download(url);
	        	}
			});
			
	  	 }); 
		
		$(".buildproject", $page).on("click", "#downloadRelationFile", function (e) {
			Dialog.confirm('确认下载关系文件？', function(yes){
	        	if(yes){
	        		var url="admin/buildproject/downloadRelationFile";
	        		Ajax.download(url);
	        	}
			});
			
	  	 }); 
		
		$(".buildproject", $page).on("click", "#downloadItemFile", function (e) {
			
			var $childs = $(".entityItems").children(".inse");
			var length = $childs.length;
			
			if (length === 0) {
				Dialog.notice("没有选中实体！", "warning");
				return;
			}
			
			Dialog.confirm('选中实体数量为: "'+length+'", 是否确认下载？', function(yes){
	        	if(yes){
	        		$childs.each(function(){
					    var entityCode = $(this).attr("entityId");
					    var url="admin/buildproject/downloadItemFile?entityCode=" + entityCode;
		        		Ajax.download(url);
					  });
	        	}
			});
			
	  	 });
		
		
		
		
	});
</script>