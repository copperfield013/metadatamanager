<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="demo-list">
	<nav>
		<form class="form-inline" action="admin/node/basicItemNode/list">
			<div class="form-group">
				<label for="name">name</label>
				<input type="text" class="form-control" name="name" value="${criteria.name }" />
				<label for="name">abcattr</label>
				<input type="text" class="form-control" name="abcattr" value="${criteria.abcattr }" />
			</div>
			<button type="submit" class="btn btn-default">查询</button>
			<a class="btn btn-primary tab" href="admin/node/basicItemNode/operate" title="创建" target="dictMapping_add" >创建</a>
		</form>
	</nav>
	<div class="row list-area">
		<table class="table">
			<thead>
				<tr>
					<th>序号</th>
					<th>name</th>
					<th>abcattr</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="item" varStatus="i">
					<tr>
						<td>${i.index + 1 }</td>
						<td>${item.name }</td>
						<td>${item.abcattr }</td>
						<td>
							<a href="javascript:;" itemId="${item.id }" title="修改" id="edit">修改</a>
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
		
	/* 	$("tbody").on("click", "#edit", function() {
            var itemId=$(this).attr("itemId");
            Dialog.openDialog("admin/dictionary/dictMapping/update/" +itemId , "修改", undefined, {
                width :600,
                height : 300
           		});
       		 }); */
	});
</script>