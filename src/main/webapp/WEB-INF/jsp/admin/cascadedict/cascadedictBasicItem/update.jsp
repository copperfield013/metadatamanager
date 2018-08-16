<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="cascadedictBasicItem-update">
	<div class="page-header">
		<div class="header-title">
			<h1>修改</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form class="bv-form form-horizontal validate-form" action="admin/cascadedict/cascadedictBasicItem/do_update">
					<input type="hidden" name="id" value="${dictBasicItem.id }" />
					<input type="hidden" name="parentId" value="${dictBasicItem.parentId }" />
					<input type="hidden" name="casPid" value="${dictBasicItem.casPid }" />
					<div class="form-group">
						<label class="col-lg-2 control-label" for="name" >名称</label>
						<div class="col-lg-5">
							<input type="text" placeholder="名字不能为空"  data-bv-notempty="true" data-bv-notempty-message="名字不能为空"  class="form-control" name="name" value="${dictBasicItem.name }" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="address">英文名称</label>
						<div class="col-lg-5">
							<input type="text" class="form-control" name="enName" value="${dictBasicItem.enName }"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="order">排序</label>
						<div class="col-lg-5">
							<input type="number" placeholder="只能输入数字" class="form-control" name="order" value="${dictBasicItem.order }" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="status">状态</label>
						<div class="col-lg-5">
							<input name="status"  type="radio" value="启用" ${dictBasicItem.status eq '启用'? 'checked="checked"':'' } style="opacity:1;position: static;height:13px;" /> 
							<label for="启用">启用</label> 
							<input name="status" type="radio" value="废弃" ${dictBasicItem.status eq '废弃'? 'checked="checked"':'' } style="opacity:1;position: static;height:13px;"/> 
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