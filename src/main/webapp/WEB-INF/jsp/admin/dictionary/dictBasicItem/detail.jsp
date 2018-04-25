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
					<input type="hidden" name="id" value="${dictBasicItem.id }" />
					<div class="form-group">
						<label class="col-lg-2 control-label" for="name" >名称</label>
						<div class="col-lg-5">
							<input type="text" class="form-control" name="name" value="${dictBasicItem.name }" />
						</div>
					</div>
					
				</form>
			</div>
		</div>
	</div>
</div>