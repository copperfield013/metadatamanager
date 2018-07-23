<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<link rel="stylesheet" href="media/admin/node/basicItemNode/css/edit.css">
<script src="media/admin/plugins/beyond/js/select2/select2.js"></script>
<script src="media/admin/node/basicItemNode/js/Sortable.js"></script>
<script src="media/admin/node/basicItemNode/js/edit.js"></script>

<div id="operateEdit" data-btnode="${btNode}">
		
		<div class="entity_list clear-fix">					
				<div title="code:${basicItem.code }, 中文名称:${basicItem.cnName },  英文名称:${basicItem.enName }" 
					 data-cnName="${basicItem.cnName }"  
					 data-code="${basicItem.code }" class="entity_attr active">				
						${basicItem.cnName }				
				</div>			
		</div>
		
        <div class="entity-edit-wrap active">
            <!-- 实体标题:begin -->
            <div class="entity-title collapse-header al-save need-ajax" data-abcattrCode="${btNode.abcattrCode }" data-order="${btNode.order}" data-id="${btNode.id}">
                <div class="icon-label-master">
                    <i class="icon-root icon"></i>
                    <span class="text">ABC</span>
                </div>
                <input name="name" disabled type="text" class="edit-input" value="${btNode.name}">
                <span class="entity-only-title">${btNode.abcattr}</span>
                <select disabled class='node-ops-type' data-val='${btNode.opt}'>
                	
                </select>
                <div class="btn-wrap">
                	<i class="icon icon-save"></i>
                    <i class="icon icon-add"></i>
                    <i class="icon icon-trash"></i>
                    <a title="预览" style="font-size: 14px;position:absolute;right:98px;" nodeId="${item.id }" id="preview" href="javascript:;">预览</a>
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
