<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<link rel="stylesheet" href="media/admin/node/basicItemNode/css/operate.css">
<script src="media/admin/node/basicItemNode/js/Sortable.min.js"></script>
<script src="media/admin/node/basicItemNode/js/operate.js"></script>

<div id="operate">
		
		<div class="entity_list clear-fix">		
			<c:forEach items="${list }" var="item">
				<div title="code:${item.code }, 中文名称:${item.cnName },  英文名称:${item.enName }" 
					 data-cnName="${item.cnName }"  
					 data-code="${item.code }" class="entity_attr">				
						${item.cnName }				
				</div>
			</c:forEach>
		</div>
		
        <div class="entity-edit-wrap edit">
            <!-- 实体标题:begin -->
            <div class="entity-title collapse-header" data-order="" data-id="">
                <div class="icon-label-master">
                    <i class="icon-root icon"></i>
                    <span class="text">ABC</span>
                </div>
                <input name="name" type="text" class="edit-input" value="">
                <span class="entity-only-title"></span>
                <div class="btn-wrap">
                	<i class="icon icon-save"></i>
                    <i class="icon icon-add"></i>
                    <i class="icon icon-trash"></i>
                    <i class="icon icon-arrow"></i>
                </div>
            </div>
            <!-- 实体标题:end -->
            <!-- 标签 不能拖拽 始终在第一个-->
           
            <!-- 拖拽排序wrap -->
            <ul class="drag-wrap collapse-content  collapse-content-active" id="drag-1">
                <!-- 属性-->
               
                <!-- 属性组 -->
               
                <!-- 多值属性 -->
               
                <!-- 关系 -->

            </ul>

        </div>
    </div>


<script>
	
</script>