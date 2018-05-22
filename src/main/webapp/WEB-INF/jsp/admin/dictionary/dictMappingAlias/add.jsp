<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="demo-add">
	<div class="page-header">
		<div class="header-title">
			<h1>添加</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form class="bv-form form-horizontal validate-form" action="admin/dictionary/dictMappingAlias/do_add">
					
					<div class="form-group">
						<label class="col-lg-2 control-label" for="mappingId">字典名称</label>
							<select name="basicItemId">
									<c:forEach items="${dictBItemList }" var="item">
										<option value="${item.id }">${item.name }</option>
									</c:forEach>
							</select>
							<select name="basicItemId">
									<c:forEach items="${dictBItemList }" var="item">
										<option value="${item.id }">${item.name }</option>
									</c:forEach>
							</select>
					</div>
					
					<div class="form-group">
						<input type="hidden" name="mappingId" value="${mappingId }">
						<label class="col-lg-2 control-label" for="aliasName">别名</label>
						<div class="col-lg-5">
							<input type="text" class="form-control" name="aliasName" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="priorityLevel">优先级</label>
						<div class="col-lg-5">
							<input type="text" class="form-control" id="priorityLevel" name="priorityLevel" />
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
