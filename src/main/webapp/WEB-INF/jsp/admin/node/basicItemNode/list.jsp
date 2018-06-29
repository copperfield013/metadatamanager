<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="basicItemNode-list">
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
							<a class="dialog" href="admin/node/basicItemNode/preview?nodeId=${item.id }" title="预览" target="basicItemNode_preview" >预览</a>
							<a id="download" nodeId="${item.id }" href="javascript:;" >下载</a>
							<a class="tab" href="admin/node/basicItemNode/edit?nodeId=${item.id }" title="修改" target="basicItemNode_edit" >修改</a>
							<a href="admin/node/basicItemNode/do_delete?id=${item.id }&isDelChil=false" confirm="确认删除？">删除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="cpf-paginator" pageNo="${pageInfo.pageNo }" pageSize="${pageInfo.pageSize }" count="${pageInfo.count }"></div>
	</div>
</div>
<script>
	seajs.use(['dialog','utils', 'ajax'], function(Dialog, Utils, Ajax){
		var $page = $('#basicItemNode-list');
	$("tbody", $page).on("click", "#download", function (e) {
		var nodeId = $(this).attr("nodeId");
		var url="admin/node/basicItemNode/download?nodeId=" + nodeId;
		Ajax.download(url);
  	 });  
	});
</script>