<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="demo-list">
	<nav>
		<form class="form-inline" action="admin/module/configModule/list">
			 <div class="form-group">
				<label for="name">模块</label>
				<input type="text" class="form-control" name="moduleName" value="${criteria.moduleName }" />
			</div>
			<button type="submit" class="btn btn-default">查询</button>
			<a class="btn btn-primary tab" href="admin/module/configModule/add" title="创建" target="configModule_add" >创建</a>
		</form>
	</nav>
	<div class="row list-area">
		<table class="table">
			<thead>
				<tr>
					<th>序号</th>
					<th>模块</th>
					<th>标题</th>
					<th>配置名称</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="item" varStatus="i">
					<tr>
						<td>${i.index + 1 }</td>
						<td>${item.name }</td>
						<td>${item.title }</td>
						<td>${item.mappingName }</td>
						<td>
						
							<c:if test="${item.disabled eq false}">
							<a href="admin/module/configModule/disabled?name=${item.name }&endisabled=true" confirm="确认禁用？">禁用</a>
							</c:if>
							<c:if test="${item.disabled eq true }">
							<a href="admin/module/configModule/disabled?name=${item.name }&endisabled=false" confirm="确认启用？">启用</a>
							</c:if>
							<a href="admin/module/configModule/do_delete?name=${item.name }" confirm="确认移除？">移除</a>
							<a href="admin/module/configModule/refresh?name=${item.name }" confirm="确认刷新？">刷新</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<%-- <div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div> --%>
	</div>
</div>
<script>
	seajs.use(['utils'], function(Utils){
		var $page = $('#demo-list');
		Utils.datepicker($('#date', $page));
	});
</script>