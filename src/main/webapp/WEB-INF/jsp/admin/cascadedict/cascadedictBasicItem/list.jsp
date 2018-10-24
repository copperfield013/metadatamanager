<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<div id="cascadedictBasicItem-list">
	<nav>
		<form class="form-inline" action="admin/cascadedict/cascadedictBasicItem/list">
			<div class="form-group">
				<input type="hidden" class="form-control" name="parentId" value="${criteria.parentId }" />
				<label for="name">名称</label>
				<input type="text" class="form-control" name="name" value="${criteria.name }" />
			</div>
			<button type="submit" class="btn btn-default">查询</button>
			<a id="add" parentId="${criteria.parentId }" class="btn btn-primary tab" title="创建">创建</a>
		</form>
	</nav>
	<div class="row list-area">
		<table class="table">
			<thead>
				<tr>
					<th>序号</th>
					<th>编号</th>
					<th>名称</th>
					<th>英文名称</th>
					<th>状态</th>
					<th>排序</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="item" varStatus="i">
					<tr>
						<td>${i.index + 1 }</td>
						<td>${item.id }</td>
						<td>${item.name }</td>
						<td>${item.enName }</td>
						<td>${item.status }</td>
						<td>${item.order }</td>
						<td>
							<a class="tab" href="admin/cascadedict/cascadedictBasicItem/getOne?id=${item.id }" target="cascadedictBasicItem_child" title="管理子数据">管理子数据</a>
							<%-- <a class="tab" href="admin/cascadedict/cascadedictSubsection/getOne?id=${item.id }" target="cascadedictSubsection_tree" title="字典再分">字典再分</a> --%>
							<a href="javascript:;" itemId="${item.id }" title="修改" id="edit">修改</a>
							<a href="admin/cascadedict/cascadedictBasicItem/do_delete/${item.id }" confirm="确认删除？">删除</a>
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
		var $page = $('#cascadedictBasicItem-list');
		Utils.datepicker($('#date', $page));
		
		$("form", $page).on("click", "#add", function() {
			var parentId = $(this).attr("parentId");
            Dialog.openDialog("admin/cascadedict/cascadedictBasicItem/add?parentId="+parentId, "创建", "", {
                width :600,
                height : 300
            });
        });
		
		$("tbody", $page).on("click", "#edit", function() {
            var itemId=$(this).attr("itemId");
            Dialog.openDialog("admin/cascadedict/cascadedictBasicItem/update/"+itemId, "修改", "", {
                width :600,
                height : 300
            });
        });
		
		//管理子数据
		$("tbody", $page).on("click", "#managerChild", function() {
            var itemId=$(this).attr("itemId");
            Dialog.openDialog("admin/cascadedict/cascadedictBasicItem/getOne?id="+itemId, "管理子数据", "cascadedictBasicItem_child", {
                
            });
        });
		
	});
</script>