<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="demo-list">
	<nav>
		<form class="form-inline" action="admin/dictionary/dictMappingAlias/list">
			<div class="form-group">
				<label for="name">父名称</label>
				<input type="text" class="form-control" name="btItemParentName" value="${criteria.btItemParentName }" />
				
				<label for="name">字典名称</label>
				<input type="hidden" class="form-control" name="mappingId" value="${criteria.mappingId }" />
				<input type="text" class="form-control" name="basicItemName" value="${criteria.basicItemName }">
				<label for="name">别名</label>
				<input type="text" class="form-control" name="aliasName" value="${criteria.aliasName }" />
			</div>
			<button type="submit" class="btn btn-default">查询</button>
		</form>
	</nav>
	<div class="row list-area">
		<table class="table">
			<thead>
				<tr>
					<th>序号</th>
					<th>父名称</th>
					<th>字典名称</th>
					<th>别名</th>
					<th>优先级</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="item" varStatus="i">
					<tr>
						<td>${i.index + 1 }</td>
						<td>${item[11] }</td> 
						<td>${item[3] }</td>
						<td>${item[9] }</td>
						<td>${item[10] }</td>
						<td>
							<a href="javascript:;" title="修改" itemId="${item[6]}" basicItemId="${item[0]}" mappingId=${criteria.mappingId } id="edit">修改</a>
							<a href="admin/dictionary/dictMappingAlias/do_delete/${item[6] }" confirm="确认删除？">删除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>
	</div>
</div>
<script>
	seajs.use(['dialog','utils'], function(Dialog, Utils){
		var $page = $('#demo-list');
		Utils.datepicker($('#date', $page));
		
		$("tbody").on("click", "#edit", function() {
			var itemId=$(this).attr("itemId");
			var basicItemId=$(this).attr("basicItemId");
			var mappingId=$(this).attr("mappingId");
			Dialog.openDialog("admin/dictionary/dictMappingAlias/update?id="+itemId+"&basicItemId="+basicItemId+"&mappingId="+mappingId, "修改", undefined, {
				width :600,
				height : 300
			});
		});
		
		
	});
</script>