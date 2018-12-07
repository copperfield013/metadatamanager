<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<script src="media/admin/plugins/beyond/js/select2/select2.js"></script>
<div id="module_edit">
	<div class="page-header">
		<div class="header-title">
			<h1>编辑</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form class="bv-form form-horizontal validate-form" action="admin/module/configModule/do_edit">
					<div class="form-group">
						<label class="col-lg-2 control-label" for="moduleTitle">模块名称<font color="red">*</font></label>
						<div class="col-lg-5">
							<input type="text" data-bv-notempty="true" data-bv-notempty-message="模块名称必填" value="${module.title }" class="form-control" id="moduleTitle" name="moduleTitle" />
						</div>
					</div>
					<div class="form-group">
						
						<input type="hidden" name="moduleName" value="${module.name }">
						<input type="hidden" name="mappingId" value="${module.mappingId }">
						
						<label class="col-lg-2 control-label" for="moduleName">模块标识</label>
						<div class="col-lg-5">
							${module.name }
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-lg-2 control-label" for="mappingId">配置名称<font color="red">*</font></label>
						<div class="col-lg-5">
							${abc.name }
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="codeName">编码字段</label>
						<div class="col-lg-5">
							<select style="width: 30%;" id="codeName" class="ser-list" name="codeName">
								<option selected="selected" value="">唯一编码</option>
								<%-- <c:forEach items="${childNode }" var="item">
									<option value="${item.name }" ${item.name eq module.codeName ? 'selected="selected"' : '' }>${item.name }</option>
								</c:forEach> --%>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="titleName">名称字段</label>
						<div class="col-lg-5">
							<select style="width: 30%;" id="titleName" class="ser-list" name="titleName">
								<option selected="selected" value=""></option>
								<c:forEach items="${childNode }" var="item">
									<option value="${item.name }" ${item.name eq module.titleName ? 'selected="selected"' : ''}>${item.name }</option>
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
		var $page = $('#module_edit');
		
		/* $(".page-body", $page).on("click", ".ser-list", function (e) {
			 $(".ser-list", $page).css({"width":"30%"}).select2();
	    });  */
		
		 $(function(){
	    	 $(".ser-list", $page).css({"width":"30%"}).select2();
	    })
	    
	});
</script>