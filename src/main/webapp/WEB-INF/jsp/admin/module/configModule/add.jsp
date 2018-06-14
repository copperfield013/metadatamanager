<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="module_add">
	<div class="page-header">
		<div class="header-title">
			<h1>添加</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form class="bv-form form-horizontal validate-form" action="admin/module/configModule/do_add">
					<div class="form-group">
						
						<label class="col-lg-2 control-label" for="moduleName">模块名</label>
						<div class="col-lg-5">
							<input type="text" class="form-control" name="moduleName" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="moduleTitle">模块标题</label>
						<div class="col-lg-5">
							<input type="text" class="form-control" id="moduleTitle" name="moduleTitle" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="mappingName">配置名称</label>
						<div class="col-lg-5">
							<select id="mappingName" name="mappingName">
								<c:forEach items="${abcList }" var="item">
									<option value="${item.id }">${item.name }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="codeName">编码字段</label>
						<div class="col-lg-5">
							<select id="codeName" name="codeName">
								<c:forEach items="${childNodeList }" var="item">
									<option>${item.name }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="titleName">名称字段</label>
						<div class="col-lg-5">
							<select id="titleName" name="titleName">
								<c:forEach items="${childNodeList }" var="item">
									<option>${item.name }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
			        	<div class="col-lg-offset-3 col-lg-3">
			        		<input class="btn btn-block btn-darkorange" type="submit" value="提交"  />
				        </div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script>
	seajs.use(['dialog','utils', 'ajax', '$CPF'], function(Dialog, Utils, Ajax, $CPF){
		var $page = $('#module_add');
		
		$(".page-body", $page).on("click", "#mappingName", function (e) {
			 $("#mappingName", $page).css({"width":"30%","marginLeft":"16px"}).select2();
	    }); 
	    
	    $(".page-body", $page).on("change", "#mappingName", function() {
	    	var $this = $(this);
	    	var parentId = $this.val();
	    	 Ajax.ajax('admin/module/configModule/childNodeList', {
	    		 parentId:parentId
	    	 }, function(data) {
	    		 var child = data.childNodeList;
	    		 var str = "";
	    		 for (var p in child) { //遍历json数组时，这么写p为索引，0,1
                     str = str + "<option value=\"" + child[p].id + "\">" + child[p].name + "</option>"; 
                 }
	    		 
	    		 $("#codeName").empty().append(str);
	    		 $("#titleName").empty().append(str);
	    		 
	    	 }) 
	    });
	});
</script>