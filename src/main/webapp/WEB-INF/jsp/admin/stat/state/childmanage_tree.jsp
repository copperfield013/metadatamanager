<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<link rel="stylesheet" href="media/admin/stat/statexpression/css/treeview.css">
<script src="media/admin/plugins/beyond/js/select2/select2.js"></script>
<script src="media/admin/plugins/sortable/js/Sortable.js"></script>
<script src="media/admin/stat/statexpression/js/tree_view.js"></script>

<div id="tree_view_panel">
        <div class="entity-edit-wrap active">
            <!-- 实体标题:begin -->
            <div class="entity-title collapse-header al-save need-ajax" data-id="${basicItem.id }" data-parentId="${basicItem.parentId }">
	            <div class="icon-label-master">
	                <i class="icon-root icon"></i>
	                <span class="text">级联字典</span>
	            </div>
	            <span class="entity-only-title"  style="padding:20px;" title="编号">${basicItem.id}</span>
                <span class="entity-only-title"  style="padding:20px;" title="名称">${basicItem.name}</span>
	            <span class="entity-only-title"  style="padding:20px;" title="英文名称">${basicItem.enName}</span>
	            <span class="entity-only-title"  style="padding:20px;" title="状态">${basicItem.status}</span>
	            <span class="entity-only-title"  style="padding:20px;" title="排序">${basicItem.order}</span>
               
                <div class="btn-wrap">
                	<!-- <i class="icon icon-save"></i> -->
                    <i class="icon icon-add"></i> 
                    <i class="icon icon-trash"></i>
                    <i class="icon icon-arrow"></i>
                </div>
            </div>
            <!-- 实体标题:end -->
            <!-- 标签 不能拖拽 始终在第一个-->
           
            <!-- 拖拽排序wrap -->
            <ul class="dragEdit-wrap dragEdit-wrap-1 collapse-content  collapse-content-active" id="dragEdit-1">
                <!-- 属性-->
               
                <!-- 属性组 -->
               
                <!-- 多值属性 -->
               
                <!-- 关系 -->

            </ul>

        </div>
    </div>
