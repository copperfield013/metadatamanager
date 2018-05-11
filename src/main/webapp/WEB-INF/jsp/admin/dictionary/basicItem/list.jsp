<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<link rel="stylesheet" href="media/admin/dictionary/basicItem/css/list.css">
<script src="media/admin/dictionary/basicItem/js/list.js"></script>

<div id="entity-wrap">
	<div class="entity-list">
		<div class="entity">
			 <div class="entity_head">
				<img class="entity-list-img" src="media/admin/dictionary/basicItem/images/entity_list_icon.png" />
				<span>实体列表</span>
				<div id="createTab">
					<img alt="更新" src="media/admin/dictionary/basicItem/images/refresh.png">
					<span>更新实体存储</span>
				</div>
			</div>
			
			<div class="entity_list clear-fix">
				<c:forEach items="${list }" var="item" varStatus="i">
						<c:if test="${item.usingState eq '1' }"><div title="code:${item.code }, 中文名称:${empty item.cnName ? '-': item.cnName},  英文名称:${empty  item.enName ? '-': item.enName}" class="entity_attr"></c:if>
						<c:if test="${item.usingState eq '2' }"><div title="code:${item.code }, 中文名称:${empty item.cnName ? '-': item.cnName},  英文名称:${empty  item.enName ? '-': item.enName}" class="entity_attr stale"></c:if>				
						${item.cnName }
						<ul class="entity_ul" entityId="${item.code }" status="${item.usingState }">
							<li><a href="javascript:void(0)" class="edit_entity"><i class="icon edit-entity"></i>编辑实体</a></li>
							<li><a href="javascript:void(0)" class="change_status"><i class="icon stale-entity"></i><c:if test="${item.usingState eq '1' }">过期实体</c:if><c:if test="${item.usingState eq '2' }">解除过期</c:if></a></li>
							<li><a href="javascript:void(0)" class="get_entity_attr"><i class="icon entity-attr"></i>实体属性</a></li>
						</ul>
						<i class="icon delete"></i>	
					</div>			
				</c:forEach>		
				<div class="entity_attr entity_attr_img" id="add_entity">
					<img alt="添加实体" src="media/admin/dictionary/basicItem/addEntity_icon.png">
				</div>				
			</div>
			<div class="opera_entity">
				<img class="opera_entity_img" src="media/admin/dictionary/basicItem/opera_entity_icon.png">
				<span id="add_entity_mes"></span>
				<form id="entity_opera_form1" class="opera_entity_form">
						<input type="hidden" name="code" id="code"/>
					<input type="hidden" name="dataType" value="record">
					<div>
						<span class="opera_entity_label">中文名称</span>
						<input type="text" name="cnName" id="cnName"/>
					</div>
					<div>
						<span class="opera_entity_label">英文名称</span>
						<input type="text" name="enName" id="enName"/><br>
					</div>						
				</form>
				<div class="opera_entity_btn">
					<span class="entity-btn-cancel" id="entity_but_cancel">取消</span>
					<span class="entity-btn-confirm" id="entity_but_confirm">确认</span>
				</div>
			</div>
		</div>
	
		<div class="common_proper entity" parentId="">
		     <div class="entity_ch_head">		           
			      <img src="media/admin/dictionary/basicItem/common_proper_title_icon.png">
			      <span>普通属性</span>		           	
		          <div class="entity_ch_opera">
		               <img id="add_group"  src="media/admin/dictionary/basicItem/add_group_icon.png">
		               <span>添加分组</span>
		          </div>
		     </div>
		     <div class="opera_group">
				<img class="opera_entity_img" src="media/admin/dictionary/basicItem/opera_entity_icon.png">
				<span class="opera_entity_img" id="add_group_mes"></span>
				<form id="group_opera_form1" class="opera_entity_form">
						<input type="hidden" name="code" id="code"/>
					<input type="hidden" id="group_parent" name="parent" value="">
					<input type="hidden" name="dataType" value="group">
					<div>
						<span class="opera_entity_label">中文名称</span>
						<input type="text" name="cnName" id="cnName"/>
					</div>						
				</form>
				<div class="opera_entity_btn">
					<span class="entity-btn-cancel" id="group_but_cancel">取消</span>
					<span class="entity-btn-confirm" id="group_but_confirm">确认</span>
				</div>
			</div>			
		</div>
		<div class="more_proper entity" parentId="">
			<div class="entity_ch_head">		           
				<img src="media/admin/dictionary/basicItem/common_proper_title_icon.png">
				<span>多值属性</span>		           	
				<div class="entity_ch_opera">
					 <img id="add_more"  src="media/admin/dictionary/basicItem/add_group_icon.png">
					 <span>添加多值属性</span>
				</div>
			</div>		          
		    <div class="opera_more">
				<img class="opera_entity_img" src="media/admin/dictionary/basicItem/opera_entity_icon.png">
				<span class="opera_entity_img" id="add_more_mes"></span>
				<form id="more_opera_form1" class="opera_entity_form">
						<input type="hidden" name="code" id="code"/>
					<input type="hidden" id="more_parent" name="parent" value="">
					<input type="hidden" name="dataType" value="repeat">
					<div>
						<span class="opera_entity_label">中文名称</span>
						<input type="text" name="cnName" id="cnName"/><br>
					</div>
					<div>
						<span class="opera_entity_label">英文名称</span>
						<input type="text" name="enName" id="enName"/>
					</div>
					<div>
						<span class="opera_entity_label">表描述</span>
						<textarea name="tableNameDescription" id="tableNameDescription" rows="" cols=""></textarea><br>
					</div>
				</form>
				<div class="opera_entity_btn">
					<span class="entity-btn-cancel" id="more_but_cancel">取消</span>
					<span class="entity-btn-confirm" id="more_but_confirm">确认</span>
				</div>
			</div>
		</div>
		<div class="entity_relation entity" entityId="">
		           <div class="entity_ch_head">
				        <img src="media/admin/dictionary/basicItem/common_proper_title_icon.png">
				        <span>实体关系管理</span>
				   </div>
		           <div class="entity_list entity_relation_list clear-fix">
		           		<div class="entity_attr entity_attr_img add_entity_relation">
		           			<img alt="添加关系" src="media/admin/dictionary/basicItem/addEntity_icon.png">		           			
		           		</div>	
		           </div>
		          <div class="opera_relation">
				    <img class="opera_entity_img" src="media/admin/dictionary/basicItem/opera_entity_icon.png">
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
				            <span class="opera_entity_label">选择右实体</span>
				            <select id="rightRecordType" name="rightRecordType">
				            </select>
				        </div>
				          <input type="hidden" name="typeCode" id="typeCode" />
				          <input type="hidden" name="reverseCode" id="reverseCode" />
				        <div>
				            <span class="opera_entity_label">关系名称</span>
				            <input type="text" name="leftName" id="leftName" />
				        </div>
				        <div id="rela_ni_name">
				            <span class="opera_entity_label">逆向关系名称</span>
				            <input type="text" name="rightName" id="rightName" />
				        </div>
				    </form>
				    <div class="opera_entity_btn">
				        <span class="entity-btn-cancel" id="relation_but_cancel">取消</span>
				        <span class="entity-btn-confirm" id="relation_but_confirm">确认</span>
				    </div>
				</div>
		 </div>
	</div>	    
</div>

