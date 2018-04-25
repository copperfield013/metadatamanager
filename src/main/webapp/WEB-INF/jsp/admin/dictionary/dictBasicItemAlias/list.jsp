<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="demo-list">
	<nav>
		<form class="form-inline" action="admin/dictionary/dictBasicItemAlias/list">
			<div class="form-group">
				<label for="name">别名</label>
				<input type="text" class="form-control" name="aliasName" value="${criteria.aliasName }" />
				<input type="hidden" name="basicItemId" value="${criteria.basicItemId }">
			</div>
			<!-- <div class="form-group">
				<label class="form-control-title" for="date">日期</label>
				<input type="text" class="form-control" id="date" name="date" readonly="readonly" css-cursor="text"  />
			</div> -->
			<button type="submit" class="btn btn-default">查询</button>
			<a class="btn btn-primary tab" href="admin/dictionary/dictBasicItemAlias/add?basicItemId=${criteria.basicItemId }" title="创建" target="dictBasicItemAlias_add" >创建</a>
		</form>
	</nav>
	<div class="row list-area">
		<table class="table">
			<thead>
				<tr>
					<th>序号</th>
					<th>别名</th>
					<th>优先级</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="item" varStatus="i">
					<tr>
						<td>${i.index + 1 }</td>
						<td>${item.aliasName }</td>
						<td>${item.priorityLevel }</td>
						<td>
							<a href="admin/dictionary/dictBasicItemAlias/update/${item.id }" class="tab" target="dictBasicItemAlias_update_${item.id }" title="修改">修改</a>
							<a href="admin/dictionary/dictBasicItemAlias/do_delete/${item.id }" confirm="确认删除？">删除</a>
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