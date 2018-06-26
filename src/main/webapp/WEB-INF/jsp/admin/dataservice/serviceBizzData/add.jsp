<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<script src="media/admin/plugins/beyond/js/select2/select2.js"></script>
<div id="dataService_add">
	<div class="page-header">
		<div class="header-title">
			<h1>添加</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form class="bv-form form-horizontal validate-form" action="admin/dataservice/serviceBizzData/do_add">
					
					<div class="form-group">
						<label class="col-lg-2 control-label" for="ip">ip<font color="red">*</font></label>
						<div class="col-lg-5">
							<input type="text" data-bv-notempty="true" data-bv-notempty-message="ip必填" class="form-control" id="ip" name="ip" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="port">端口<font color="red">*</font></label>
						<div class="col-lg-5">
							<input type="text" data-bv-notempty="true" data-bv-notempty-message="端口必填" class="form-control" id="port" name="port" />
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-lg-2 control-label" for="name">服务名<font color="red">*</font></label>
						<div class="col-lg-5">
							<input type="text" data-bv-notempty="true" data-bv-notempty-message="服务名必填" class="form-control" name="name" />
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-lg-2 control-label" for="describe">描述</label>
						<div class="col-lg-5">
							<textarea rows="" cols="62" name="describe"></textarea>
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