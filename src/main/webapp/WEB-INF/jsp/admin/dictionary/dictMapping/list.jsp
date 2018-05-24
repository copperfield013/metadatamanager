<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="demo-list">
	<nav>
		<form class="form-inline" action="admin/dictionary/dictMapping/list">
			<div class="form-group">
				<label for="name">名称</label>
				<input type="text" class="form-control" name="name" value="${criteria.name }" />
				<input type="hidden" name="id" value="${criteria.id }">
			</div>
			<button type="submit" class="btn btn-default">查询</button>
			<a class="btn btn-primary tab" id="add" title="创建">创建</a>
		</form>
	</nav>
	<div class="row list-area">
		<table class="table">
			<thead>
				<tr>
					<th>序号</th>
					<th>名称</th>
					<th>描述</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="item" varStatus="i">
					<tr>
						<td>${i.index + 1 }</td>
						<td>${item.name }</td>
						<td>${item.describe }</td>
						<td>
							<a href="admin/dictionary/dictMappingAlias/list?mappingId=${item.id }" class="tab" target="dictMappingAlias_list" title="管理别名">管理别名</a>
							<a href="javascript:;" itemId="${item.id }" title="修改" id="edit">修改</a>
							<a href="admin/dictionary/dictMapping/do_delete/${item.id }" confirm="确认删除？">删除</a>
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
		
		$("form").on("click", "#add", function() {
            Dialog.openDialog("admin/dictionary/dictMapping/add" , "创建", undefined, {
                width :600,
                height : 300
           	});
       	});
		
		$("tbody").on("click", "#edit", function() {
            var itemId=$(this).attr("itemId");
            Dialog.openDialog("admin/dictionary/dictMapping/update/" +itemId , "修改", undefined, {
                width :600,
                height : 300
           		});
       	});
	});
</script>