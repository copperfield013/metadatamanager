<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<link rel="stylesheet" href="media/admin/stat/css/list.css">

<script src="media/admin/plugins/beyond/js/select2/select2.js"></script>

<!-- 引入树结构 start -->
<link rel="stylesheet" href="media/admin/stat/statexpression/css/treeview.css">
<script src="media/admin/plugins/sortable/js/Sortable.js"></script>
<!-- 引入树结构 end -->
<script src="media/admin/stat/js/list.js"></script>
<div id="statEntity-wrap">
	<div class="statEntity-list">
	<!--  <div class="status-legend">	</div> -->
		<div class="entity">
			 <div class="statEntity_head">
				<img class="statEntity-list-img" src="media/admin/stat/images/entity_list_icon.png" />
				<span>统计实体<img alt="" class="status-legend">
				</span>
			</div>
			
			<div class="statEntity_list clear-fix">
				<c:forEach items="${statEList }" var="item" varStatus="i">
						<div title="code:${item[3] }, 中文名称:${empty item[4] ? '-': item[4]},sourceCode=${item[1] }, source名称:${empty  item[2] ? '-': item[2]}" class="entity_attr ${item[7] eq '2' ? 'stale':''}${item[7] eq '1' ?'inuse':''}${item[7] eq '0' ?'newadd':''}${item[7] eq '-1'?'inerror':'' }">				
						${item[4] }
						<ul class="entity_ul" bieCode="${item[0] }" entityId="${item[3] }" sourceCode='${item[1] }' status="${item[7] }">
							<li><a href="javascript:void(0)" class="get_entity_attr"><i class="icon entity-attr"></i>实体属性</a></li>
							<%-- <c:if test="${item[7] ne '2' }"><li><a href="javascript:void(0)" class="edit_entity"><i class="icon edit-entity"></i>编辑实体</a></li></c:if>							 --%>
							<c:if test="${item[7] ne '2' }"><li><a href="javascript:void(0)" class="change_status"><i class="icon stale-entity"></i>过期实体</a></li></c:if>
							<c:if test="${item[7] eq '2' }"><li><a href="javascript:void(0)" class="change_status"><i class="icon stale-entity"></i>解除过期</a></li></c:if>
							<li><a href="javascript:void(0)" class="delete_entity"><i class="icon edit-entity"></i>删除实体</a></li>
						</ul>
						<i class="icon status"></i>	
					</div>		
				</c:forEach>		
				<div class="entity_attr entity_attr_img" id="add_entity">
					<img alt="添加实体" src="media/admin/stat/addEntity_icon.png">
				</div>				
			</div>
			<div class="opera_entity">
				<img class="opera_entity_img" src="media/admin/stat/images/info.png">
				<span id="add_entity_mes"></span>
				<form id="entity_opera_form1" class="opera_entity_form">
					<input type="hidden" name="code" id="code"/>
					<input type="hidden" name="dataType" value="10">
					<div>
						<span class="opera_entity_label">中文名称<span style="color: red;">*</span></span>
						<input type="text" name="cnName" id="cnName"/>
					</div>
					<div>
						<span class="opera_entity_label">英文名称</span>
						<input type="text" name="enName" id="enName"/><br>
					</div>	
					<div>
						<span class="opera_entity_label">描述</span>
						<textarea name="description" id="description" rows="" cols=""></textarea>
					</div>	
					<div style="width: 92%;">
						<span class="opera_entity_label">请为标签选择字典<span style="color: red;">*</span></span>
						<select id="cascadedict" name="cascadedict">
						</select>
						<span class="opera_entity_label">来源实体<span style="color: red;">*</span></span>
						<select id="sourceCode" name="sourceCode">
						</select>
					</div>
					
				</form>
				<div class="opera_entity_btn">
					<span class="entity-btn-cancel" id="entity_but_cancel">取消</span>
					<span class="entity-btn-confirm" id="entity_but_confirm">确认</span>
				</div>
			</div>
		</div>
	
		<div class="common_proper properOperate entity" parentId="" sourcecode="">
		     <div class="entity_ch_head">		           
			      <img src="media/admin/stat/images/common.png">
			      <span>维度</span>	
		     </div>
			
			<div data-code-id="" class="new_add clear-fix">
			   <div class="comm_list clear-fix">
			    <div class="entity_attr entity_attr_img add_comm">
			     <img alt="添加普通属性" src="media/admin/dictionary/basicItem/addEntity_icon.png" />
			    </div>
			   </div>
			   <div class="opera_comm">
			    <img class="opera_entity_img" src="media/admin/dictionary/basicItem/images/info.png" />
			    <span id="add_comm_mes"></span>
			    <form groupname="" groupid="" id="comm_opera_form1" type="1" class="opera_entity_form">
			     <input type="hidden" name="id" id="id" />
			     
			    <input type="hidden" id="groupName" name="groupName" value="" />
			    <input type="hidden" id="comm_parent" name="parent" value="" />
			    <input type="hidden" id="edit_dataType" value="" />
			    <input type="hidden" id="edit_dictParentId" value="" />
			    <input type="hidden" name="code" id="code" />
			    <div>
			     <span class="opera_entity_label">中文名称<span style="color:" red;\"="">*</span></span>
			     <input type="text" name="cnName" id="cnName" />
			    </div>
			    <div>
			     <span class="opera_entity_label">英文名称</span>
			     <input type="text" name="enName" id="enName" />
			    </div>
			    <div class="select-wrap">
			     <span class="opera_entity_label" id="cn_dataType">数据类型<span style="color:" red;\"="">*</span></span>
			     <select id="dataType" class="enum_dataType_one" name="dataType"></select> 
			     <span style="display:none;" class="opera_entity_label" id="cn_needHistory">记录历史</span>
			     <select style="display:none;" id="needHistory" class="needHistory" name="needHistory"><option value="1" selected="selected">是</option><option value="0">否</option></select>
			    </div>
			    <div>
			     <span class="opera_entity_label" id="cn_dataRange">数据长度<span style="color:" red;\"="">*</span></span>
			     <input type="text" name="dataRange" id="dataRange" />
			    </div>
			    <div>
			     <span class="opera_entity_label">描述</span>
			     <textarea name="description" id="description"></textarea>
			    </div>
			     
			    <%--  tree   start --%>
			    <div id="tree_view_panel">
        <div class="entity-edit-wrap active">
            <!-- 实体标题:begin -->
            <div class="entity-title collapse-header al-save need-ajax edit" data-id="" data-parentId="">
	            <div class="icon-label-master">
	                <i class="icon-root icon"></i>
	                <span class="text">表达式</span>
	            </div>
	            <!-- <span class="entity-only-title"  style="padding:20px;" title="编号">xxx</span>
                <span class="entity-only-title"  style="padding:20px;" title="名称">xxx</span>
	            <span class="entity-only-title"  style="padding:20px;" title="英文名称">xxx</span>
	            <span class="entity-only-title"  style="padding:20px;" title="状态">xxx</span>
	            <span class="entity-only-title"  style="padding:20px;" title="排序">xxx</span> -->
	            
	            <!-- <input name="name" disabled type="text" class="edit-input" value="fwewwe"> -->
               <!--  <span class="entity-only-title">sdfwefwe</span> -->
                <div class="btn-wrap">
                	<!-- <i class="icon icon-save"></i> -->
                    <i class="icon icon-add"></i> 
                    <!-- <i class="icon icon-trash"></i> -->
                  <!--   <i class="icon icon-arrow"></i> -->
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
			     <%--  tree   end --%>
			    
			    </form>
			    <div class="opera_entity_btn">
			     <span class="entity-btn-cancel" id="comm_but_cancel">取消</span>
			     <span class="entity-btn-confirm" id="comm_but_confirm">确认</span>
			    </div>
			   </div>
			   <i class="icon status newadd"></i>
	  </div>
					
		</div>
		<div class="more_proper properOperate entity" parentId="" sourcecode="">
			<div class="entity_ch_head">		           
				<img src="media/admin/stat/images/more.png">
				<span>事实</span>		           	
				<!-- <div class="entity_ch_opera" id="add_more">
					 <img src="media/admin/stat/images/add-more.png">
					 <span>添加多值属性</span>
				</div> -->
			</div>	
			
			<div data-code-id="" class="new_add clear-fix">
			   <div class="comm_list clear-fix">
			    <div class="entity_attr entity_attr_img add_comm">
			     <img alt="添加普通属性" src="media/admin/dictionary/basicItem/addEntity_icon.png" />
			    </div>
			   </div>
			   <div class="opera_comm">
			    <img class="opera_entity_img" src="media/admin/dictionary/basicItem/images/info.png" />
			    <span id="add_comm_mes"></span>
			    <form groupname="" groupid="" id="comm_opera_form1" type="2" class="opera_entity_form">
			     <input type="hidden" name="id" id="id" />
			     
			    <input type="hidden" id="groupName" name="groupName" value="" />
			    <input type="hidden" id="comm_parent" name="parent" value="" />
			    <input type="hidden" id="edit_dataType" value="" />
			    <input type="hidden" id="edit_dictParentId" value="" />
			    <input type="hidden" name="code" id="code" />
			    <div>
			     <span class="opera_entity_label">中文名称<span style="color:" red;\"="">*</span></span>
			     <input type="text" name="cnName" id="cnName" />
			    </div>
			    <div>
			     <span class="opera_entity_label">英文名称</span>
			     <input type="text" name="enName" id="enName" />
			    </div>
			    <div class="select-wrap">
			     <span class="opera_entity_label" id="cn_dataType">数据类型<span style="color:" red;\"="">*</span></span>
			     <select id="dataType" class="enum_dataType_one" name="dataType"></select> 
			     <span style="display:none;" class="opera_entity_label" id="cn_needHistory">记录历史</span>
			     <select style="display:none;" id="needHistory" class="needHistory" name="needHistory"><option value="1" selected="selected">是</option><option value="0">否</option></select>
			    </div>
			    <div>
			     <span class="opera_entity_label" id="cn_dataRange">数据长度<span style="color:" red;\"="">*</span></span>
			     <input type="text" name="dataRange" id="dataRange" />
			    </div>
			    <div>
			     <span class="opera_entity_label">描述</span>
			     <textarea name="description" id="description"></textarea>
			    </div>
			     
			     <%--  tree   start --%>
			    <div id="tree_view_panel">
        <div class="entity-edit-wrap active">
            <!-- 实体标题:begin -->
            <div class="entity-title collapse-header al-save need-ajax edit" parentId="" sourcecode="">
	            <div class="icon-label-master">
	                <i class="icon-root icon"></i>
	                <span class="text">表达式</span>
	            </div>
	            <!-- <span class="entity-only-title"  style="padding:20px;" title="编号">xxx</span>
                <span class="entity-only-title"  style="padding:20px;" title="名称">xxx</span>
	            <span class="entity-only-title"  style="padding:20px;" title="英文名称">xxx</span>
	            <span class="entity-only-title"  style="padding:20px;" title="状态">xxx</span>
	            <span class="entity-only-title"  style="padding:20px;" title="排序">xxx</span> -->
	            
	            <!-- <input name="name" disabled type="text" class="edit-input" value="fwewwe"> -->
               <!--  <span class="entity-only-title">sdfwefwe</span> -->
                <div class="btn-wrap">
                	<!-- <i class="icon icon-save"></i> -->
                    <i class="icon icon-add"></i> 
                    <!-- <i class="icon icon-trash"></i> -->
                   <!--  <i class="icon icon-arrow"></i> -->
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
			     <%--  tree   end --%>
			     
			     
			    
			    </form>
			    <div class="opera_entity_btn">
			     <span class="entity-btn-cancel" id="comm_but_cancel">取消</span>
			     <span class="entity-btn-confirm" id="comm_but_confirm">确认</span>
			    </div>
			   </div>
			   <i class="icon status newadd"></i>
	  </div>    
		    
		</div>
		<div class="entity_relation properOperate entity" parentId="" sourcecode="">
		           <div class="entity_ch_head">
				        <img src="media/admin/stat/images/relative.png">
				        <span>filters管理</span>
				   </div>
				   
				   
				    <%--  tree   start --%>
			    <div id="tree_view_panel">
        <div class="entity-edit-wrap active">
            <!-- 实体标题:begin -->
            <div class="entity-title collapse-header al-save need-ajax head-filters" data-id="" data-parentId="">
	            <div class="icon-label-master">
	                <i class="icon-root icon"></i>
	                <span class="text">表达式</span>
	            </div>
	            <!-- <span class="entity-only-title"  style="padding:20px;" title="编号">xxx</span>
                <span class="entity-only-title"  style="padding:20px;" title="名称">xxx</span>
	            <span class="entity-only-title"  style="padding:20px;" title="英文名称">xxx</span>
	            <span class="entity-only-title"  style="padding:20px;" title="状态">xxx</span>
	            <span class="entity-only-title"  style="padding:20px;" title="排序">xxx</span> -->
	            
	            <!-- <input name="name" disabled type="text" class="edit-input" value="fwewwe"> -->
               <!--  <span class="entity-only-title">sdfwefwe</span> -->
                <div class="btn-wrap">
                	<!-- <i class="icon icon-save"></i> -->
                    <i class="icon icon-add"></i> 
                    <!-- <i class="icon icon-trash"></i> -->
                   <!--  <i class="icon icon-arrow"></i> -->
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
			     <%--  tree   end --%>
				   
				   
		          <!--  <div class="statEntity_list entity_relation_list clear-fix">
		           		<div class="entity_attr entity_attr_img add_entity_relation">
		           			<img alt="添加关系" src="media/admin/stat/addEntity_icon.png">		           			
		           		</div>	
		           </div> -->
		          <!-- <div class="opera_relation">
				    <img class="opera_entity_img" src="media/admin/stat/images/info.png">
				    <span id="add_relation_mes"></span>
				    <form id="entity_relation_opera_form" class="opera_entity_form">
				        <input type="hidden" name="leftRecordType" id="leftRecordType">
				         <input type="hidden" name="symmetry" id="symmetry">
				         
				         <div class="col-xs-4 opera_entity_label">
					    	<label>
					       		 <input id="add_rela_symmetry" class="checkbox-slider slider-icon colored-blue" type="checkbox">
					       		 <span class="text">添加对称关系</span>
					   		</label>
						</div>
				        <div class="select-wrap" id="rela_right">
				            <span class="opera_entity_label">选择右实体<span style="color: red;">*</span></span>
				            <select id="rightRecordType" name="rightRecordType" style="width:30%">
				            </select>
				        </div>
				          <input type="hidden" name="typeCode" id="typeCode" />
				          <input type="hidden" name="reverseCode" id="reverseCode" />
				        <div class="col-xs-4 opera_entity_label">
				            <span class="opera_entity_label">关系名称<span style="color: red;">*</span></span>
				            <input type="text" name="leftName" id="leftName" />
				        </div>
				         <div  class="select-wrap">
				            <span class="opera_entity_label">关系类型<span style="color: red;">*</span></span>
				           	<select id="leftRelationType" name="leftRelationType" style="width:30%">
				            </select>
				        </div>
				        <div id="rela_ni_name">
				            <span class="opera_entity_label">逆向关系名称<span style="color: red;">*</span></span>
				            <input type="text" name="rightName" id="rightName" />
				        </div>
				         <div  class="select-wrap">
				            <span class="opera_entity_label">逆向关系类型<span style="color: red;">*</span></span>
				           	<select id="rightRelationType" name="rightRelationType" style="width:30%">
				            </select>
				        </div>
				       
				    </form>
				    <div class="opera_entity_btn">
				        <span class="entity-btn-cancel" id="relation_but_cancel">取消</span>
				        <span class="entity-btn-confirm" id="relation_but_confirm">确认</span>
				    </div>
				</div> -->
				<!-- 编辑关系名称 -->
				<!-- <div class="opera_relation_edit" style="display: none;">
				    <img class="opera_entity_img" src="media/admin/stat/images/info.png">
				    <span id="add_relation_mes">编辑关系名称</span>
				    <form id="entity_relation_form_edit" class="opera_entity_form">
				          <input type="hidden" name="typeCode" id="typeCode" />
				        <div>
				            <span class="opera_entity_label">关系名称<span style="color: red;">*</span></span>
				            <input type="text" name="name" id="name" />
				        </div>
				         <div  class="select-wrap">
				            <span class="opera_entity_label">关系类型<span style="color: red;">*</span></span>
				           	<select id="relationType" name="relationType" style="width:30%">
				            </select>
				        </div>
				    </form>
				    <div class="opera_entity_btn">
				        <span class="entity-btn-cancel" id="relation_but_cancel_edit">取消</span>
				        <span class="entity-btn-confirm" id="relation_but_confirm_edit">确认</span>
				    </div>
				</div> -->
		 </div>
	</div>	    
</div>

