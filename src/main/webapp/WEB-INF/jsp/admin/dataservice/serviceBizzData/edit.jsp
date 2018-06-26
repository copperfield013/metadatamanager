<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<script src="media/admin/plugins/beyond/js/select2/select2.js"></script>
<div id="dataService_add">
	<div class="page-header">
		<div class="header-title">
			<h1>编辑</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form class="bv-form form-horizontal validate-form" action="admin/dataservice/serviceBizzData/do_edit">
					
					<div class="form-group">
						<label class="col-lg-2 control-label" for="ip">ip</label>
						<div class="col-lg-5">
							<input type="text"  data-bv-notempty="true" data-bv-notempty-message="ip必填"  class="form-control" id="ip" name="ip" value="${serviceBizzData.ip }"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="port">端口</label>
						<div class="col-lg-5">
							<input type="text" data-bv-notempty="true" data-bv-notempty-message="端口必填"  class="form-control" id="port" name="port" value="${serviceBizzData.port }"/>
						</div>
					</div>
					<div class="form-group">
						<input type="hidden" name="id" value="${serviceBizzData.id }">
						<label class="col-lg-2 control-label" for="name">服务名</label>
						<div class="col-lg-5">
							<input type="text" data-bv-notempty="true" data-bv-notempty-message="服务名必填" class="form-control" name="name" value="${serviceBizzData.name }" />
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-lg-2 control-label" for="describe">描述</label>
						<div class="col-lg-5">
						<textarea rows="" cols="62" name="describe">${serviceBizzData.describe }</textarea>
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
		var $page = $('#dataService_add');
		
	    
	    
	});
</script>