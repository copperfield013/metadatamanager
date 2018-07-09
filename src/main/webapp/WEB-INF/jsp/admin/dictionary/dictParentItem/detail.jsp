<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="dictBasicItem-list">
	<nav>
		<form class="form-inline" action="admin/dictionary/dictBasicItem/list">
			<div class="form-group">
				<label for="name">名称</label>
				<input type="text" class="form-control" name="name" value="${criteria.name }" />
				<input type="hidden" class="form-control" name="parentId" value="${criteria.parentId }" />
			</div>
			<!-- <div class="form-group">
				<label class="form-control-title" for="date">日期</label>
				<input type="text" class="form-control" id="date" name="date" readonly="readonly" css-cursor="text"  />
			</div> -->
			<button type="submit" class="btn btn-default">查询</button>
			<a class="btn btn-primary tab" id="add" pId="${criteria.parentId }">创建</a>
		</form>
	</nav>
	
	<div class="row list-area">
		<table class="table">
			<thead>
				<tr>
					<th>序号</th>
					<th>父名称</th>
					<th>名称</th>
					<th>code</th>
					<th>英文名称</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="item" varStatus="i">
					<tr>
						<td>${i.index + 1 }</td>
						<td>${item.parentName }</td>
						<td>${item.name }</td>
						<td>${item.code }</td>
						<td>${item.enName }</td>
						<td>${item.status }</td>
						<td>
							<a href="javascript:;" title="修改" itemId="${item.id }" id="edit">修改</a>
							<a href="admin/dictionary/dictBasicItem/do_delete/${item.id }" confirm="确认删除？">删除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>
	</div>
<script>
	seajs.use(['dialog','utils'], function(Dialog, Utils){
		var $page = $('#dictBasicItem-list');
		Utils.datepicker($('#date', $page));
		
		$("form", $page).on("click", "#add", function() {
            var pId=$(this).attr("pId");
            Dialog.openDialog("admin/dictionary/dictBasicItem/add?parentId="+pId, "创建", undefined, {
                width :600,
                height : 300
            });
        });
		
		$("tbody", $page).on("click", "#edit", function() {
            var itemId=$(this).attr("itemId");
            Dialog.openDialog("admin/dictionary/dictBasicItem/update/"+itemId, "修改", undefined, {
                width :600,
                height : 300
            });
        });
		
	});
</script>