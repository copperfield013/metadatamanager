<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="demo-list">
	<nav>
		<form class="form-inline" action="admin/dictionary/dictMappingAlias/list">
			<div class="form-group">
				<label for="name">别名</label>
				<input type="text" class="form-control" name="aliasName" value="${criteria.aliasName }" />
				
				<label for="name">映射id</label>
				<input type="text" class="form-control" name="mappingId" value="${criteria.mappingId }" />
				<input type="hidden" name="basicItemId" value="${criteria.basicItemId }">
			</div>
			<button type="submit" class="btn btn-default">查询</button>
			<a class="btn btn-primary tab" href="admin/dictionary/dictMappingAlias/add?basicItemId=${criteria.basicItemId }" title="创建" target="dictMappingAlias_add" >创建</a>
		</form>
	</nav>
	<div class="row list-area">
		<table class="table">
			<thead>
				<tr>
					<th>序号</th>
					<th>映射id</th>
					<th>别名</th>
					<th>优先级</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="item" varStatus="i">
					<tr>
						<td>${i.index + 1 }</td>
						<td>${item.mappingId }</td>
						<td>${item.aliasName }</td>
						<td>${item.priorityLevel }</td>
						<td>
							<a href="admin/dictionary/dictMappingAlias/update/${item.id }" class="tab" target="dictMappingAlias_update" title="修改">修改</a>
							<a href="admin/dictionary/dictMappingAlias/do_delete/${item.id }" confirm="确认删除？">删除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>
	</div>
</div>
<script>
	seajs.use(['utils'], function(Utils){
		var $page = $('#demo-list');
		Utils.datepicker($('#date', $page));
	});
</script>