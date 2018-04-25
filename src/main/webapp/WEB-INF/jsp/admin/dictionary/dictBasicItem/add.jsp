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
				<form class="bv-form form-horizontal validate-form" action="admin/dictionary/dictBasicItem/do_add">
					<div class="form-group">
						<input type="hidden" name="parentId" value="${parentId }">
						<label class="col-lg-2 control-label" for="name">名称</label>
						<div class="col-lg-5">
							<input type="text" class="form-control" name="name" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="enName">英文名称</label>
						<div class="col-lg-5">
							<input type="text" class="form-control" name="enName" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="status">状态</label>
						<div class="col-lg-5">
							<input name="status" type="radio" value="启用" style="opacity:1;position: static;height:13px;" checked/> 
							<label for="启用">启用</label> 
							<input name="status" type="radio" value="启用" style="opacity:1;position: static;height:13px;"/> 
							<label for="废弃">废弃</label>
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