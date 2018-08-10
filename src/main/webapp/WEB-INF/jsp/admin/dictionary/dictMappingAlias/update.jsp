<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="demo-update">
	<div class="page-header">
		<div class="header-title">
			<h1>修改</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form class="bv-form form-horizontal validate-form" action="admin/dictionary/dictMappingAlias/do_update">
					<input type="hidden" name="id" value="${dictMappingAlias.id }" />
					<input type="hidden" name="basicItemId" value="${dictBasicItem.id }">
					<input type="hidden" name="mappingId" value="${criteria.mappingId }">
					
					 <div class="form-group">
						<label class="col-lg-2 control-label" for="basicItemId">字典名称</label>
						<div class="col-lg-5">
							${dictBasicItem.name }
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-lg-2 control-label" for="aliasName" >别名</label>
						<div class="col-lg-5">
							<input type="text" class="form-control" name="aliasName" value="${dictMappingAlias.aliasName }" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="priorityLevel">优先级</label>
						<div class="col-lg-5">
							<input type="number" placeholder="只能是数字" class="form-control" name="priorityLevel" value="${dictMappingAlias.priorityLevel }"/>
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