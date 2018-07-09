<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="dictParentItem-list">
	<nav>
		<form class="form-inline" action="admin/dictionary/dictParentItem/list">
			<div class="form-group">
				<label for="name">名称</label>
				<input type="text" class="form-control" name="name" value="${criteria.name }" />
			</div>
			
			<button type="submit" class="btn btn-default">查询</button>
			<a id="add" class="btn btn-primary tab" title="创建">创建</a>
		</form>
	</nav>
	<div class="row list-area">
		<table class="table">
			<thead>
				<tr>
					<th>序号</th>
					<th>名称</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="item" varStatus="i">
					<tr>
						<td>${i.index + 1 }</td>
						<td>${item.name }</td>
						<td>
							<a class="tab" href="admin/dictionary/dictBasicItem/list?parentId=${item.id }" target="dictBasicItem_list" title="查看子数据">查看子数据</a>
							<a href="javascript:;" itemId="${item.id }" title="修改" id="edit">修改</a>
							<a href="admin/dictionary/dictParentItem/do_delete/${item.id }" confirm="确认删除？">删除</a>
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
		var $page = $('#dictParentItem-list');
		Utils.datepicker($('#date', $page));
		
		$("form", $page).on("click", "#add", function() {
            Dialog.openDialog("admin/dictionary/dictParentItem/add", "创建", "", {
                width :600,
                height : 300
            });
        });
		
		$("tbody", $page).on("click", "#edit", function() {
            var itemId=$(this).attr("itemId");
            Dialog.openDialog("admin/dictionary/dictParentItem/update/"+itemId, "修改", undefined, {
                width :600,
                height : 300
            });
        });
		
	});
</script>