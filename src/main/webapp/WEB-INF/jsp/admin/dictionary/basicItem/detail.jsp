<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="demo-update">
	<div class="page-header">
		<div class="header-title">
			<h1>详情</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form class="bv-form form-horizontal validate-form">
					<input type="hidden" name="code" value="${basicItem.code }" />
					<div class="form-group">
						<label class="col-lg-2 control-label" for="cnName" >名称</label>
						<div class="col-lg-5">
							<input type="text" class="form-control" name="cnName" value="${basicItem.cnName }" />
						</div>
					</div>
					
				</form>
			</div>
		</div>
	</div>
</div>