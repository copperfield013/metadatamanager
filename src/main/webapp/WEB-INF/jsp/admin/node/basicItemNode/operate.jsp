<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<link rel="stylesheet" href="media/admin/node/basicItemNode/css/operate.css">
<script src="media/admin/node/basicItemNode/js/Sortable.min.js"></script>
<script src="media/admin/node/basicItemNode/js/operate.js"></script>

<div id="operate">
		
		<div class="entity_list clear-fix">				
			<div title="code:IBTE001, 中文名称:人口信息4,  英文名称:people" class="entity_attr">				
				人口信息4						
			</div>			
				
			<div title="code:IBTE002, 中文名称:测试1,  英文名称:test1" class="entity_attr">				
				测试1						
			</div>			
				
			<div title="code:IBTE003, 中文名称:新增1,  英文名称:newadd1" class="entity_attr">				
				新增1					
			</div>												
		</div>
		
        <div class="entity-edit-wrap">
            <!-- 实体标题:begin -->
            <div class="entity-title collapse-header">
                <div class="icon-label-master">
                    <i class="icon-root icon"></i>
                    <span class="text">ABC</span>
                </div>
                <input name="name" type="text" class="edit-input" value="人口信息">
                <select name="entityName" id="">
                    <option value="人口1">人口1</option>
                    <option value="人口2">人口2</option>
                    <option value="人口3">人口3</option>
                    <option value="人口4">人口4</option>
                </select>
                <div class="btn-wrap">
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