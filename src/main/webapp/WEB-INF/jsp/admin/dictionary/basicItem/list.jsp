<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<link rel="stylesheet" href="media/admin/dictionary/basicItem/css/list.css">
<script src="media/admin/dictionary/basicItem/js/list.js"></script>
<script src="media/admin/plugins/beyond/js/select2/select2.js"></script>

<div id="entity-wrap">
	<div class="entity-list">
	<!--  <div class="status-legend">	</div> -->
		<div class="entity">
			 <div class="entity_head">
				<img class="entity-list-img" src="media/admin/dictionary/basicItem/images/entity_list_icon.png" />
				<span>实体列表<img alt="" class="status-legend" src="media/admin/dictionary/basicItem/images/status-legend.png">
				</span>
				<div id="createTab">
					<img alt="更新" src="media/admin/dictionary/basicItem/images/refresh.png">
					<span>更新实体存储</span>
				</div>
			</div>
			
			<div class="entity_list clear-fix">
				<c:forEach items="${list }" var="item" varStatus="i">
						<div title="code:${item.code }, 中文名称:${empty item.cnName ? '-': item.cnName},  英文名称:${empty  item.enName ? '-': item.enName}" class="entity_attr ${item.usingState eq '2' ? 'stale':''}${item.usingState eq '1' ?'inuse':''}${item.usingState eq '0' ?'newadd':''}${item.usingState eq '-1'?'inerror':'' }">				
						${item.cnName }
						<ul class="entity_ul" entityId="${item.code }" status="${item.usingState }">
							<li><a href="javascript:void(0)" class="get_entity_attr"><i class="icon entity-attr"></i>实体属性</a></li>
							<c:if test="${item.usingState ne '2' }"><li><a href="javascript:void(0)" class="edit_entity"><i class="icon edit-entity"></i>编辑实体</a></li></c:if>							
							<c:if test="${item.usingState ne '2' }"><li><a href="javascript:void(0)" class="change_status"><i class="icon stale-entity"></i>过期实体</a></li></c:if>
							<c:if test="${item.usingState eq '2' }"><li><a href="javascript:void(0)" class="change_status"><i class="icon stale-entity"></i>解除过期</a></li></c:if>
							<li><a href="javascript:void(0)" class="delete_entity"><i class="icon edit-entity"></i>删除实体</a></li>
						</ul>
						<i class="icon status"></i>	
					</div>			
				</c:forEach>		
				<div class="entity_attr entity_attr_img" id="add_entity">
					<img alt="添加实体" src="media/admin/dictionary/basicItem/addEntity_icon.png">
				</div>				
			</div>
			<div class="opera_entity">
				<img class="opera_entity_img" src="media/admin/dictionary/basicItem/images/info.png">
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
					<div>
						<span >选择标签<span style="color: red;">*</span></span>
						<select id="cascadedict" name="cascadedict">
						</select>
						<span >记录历史</span>
						<select id="needHistory" name="needHistory">
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
						<span>缓存</span>
						<select id="cached" name="cached">
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
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
			      <img src="media/admin/dictionary/basicItem/images/common.png">
			      <span>普通属性</span>		           	
		          <div class="entity_ch_opera" id="add_group" >
		               <img src="media/admin/dictionary/basicItem/images/add-common.png">
		               <span>添加分组</span>
		          </div>
		     </div>
		     <div class="opera_group">
				<img class="opera_entity_img" src="media/admin/dictionary/basicItem/images/info.png">
				<span class="opera_entity_img" id="add_group_mes"></span>
				<form id="group_opera_form1" class="opera_entity_form">
						<input type="hidden" name="code" id="code"/>
					<input type="hidden" id="group_parent" name="parent" value="">
					<input type="hidden" id="dataType" name="dataType" value="16">
					<input type="hidden" id="dataRange" name="dataRange" value="group">
					<div>
						<span class="opera_entity_label">中文名称<span style="color: red;">*</span></span>
						<input type="text" name="cnName" id="cnName"/>
					</div>	
					<div>
						<span class="opera_entity_label">描述</span>
						<textarea name="description" id="description" rows="" cols=""></textarea>
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
				<img src="media/admin/dictionary/basicItem/images/more.png">
				<span>多值属性</span>		           	
				<div class="entity_ch_opera" id="add_more">
					 <img src="media/admin/dictionary/basicItem/images/add-more.png">
					 <span>添加多值属性</span>
				</div>
			</div>		          
		    <div class="opera_more">
				<img class="opera_entity_img" src="media/admin/dictionary/basicItem/images/info.png">
				<span class="opera_entity_img" id="add_more_mes"></span>
				<form id="more_opera_form1" class="opera_entity_form">
						<input type="hidden" name="code" id="code"/>
					<input type="hidden" id="more_parent" name="parent" value="">
					<input type="hidden" id="dataType" name="dataType" value="9">
					<div>
						<span class="opera_entity_label">中文名称<span style="color: red;">*</span></span>
						<input type="text" name="cnName" id="cnName"/><br>
					</div>
					<div>
						<span class="opera_entity_label">英文名称<span style="color: red;">*</span></span>
						<input type="text" name="enName" id="enName"/>
					</div>
					<div>
						<span class="opera_entity_label">表描述</span>
						<textarea name="tableNameDescription" id="tableNameDescription" rows="" cols=""></textarea><br>
					</div>
					<div>
						<span class="opera_entity_label">描述</span>
						<textarea name="description" id="description" rows="" cols=""></textarea>
					</div>	
				</form>
				<div class="opera_entity_btn">
					<span class="entity-btn-cancel" id="more_but_cancel">取消</span>
					<span class="entity-btn-confirm" id="more_but_confirm">确认</span>
				</div>
			</div>
		</div>
		<!-- 聚合属性 start -->
		
		<div class="aggregate_proper entity" parentId="">
		     <div class="entity_ch_head">		           
			      <img src="media/admin/dictionary/basicItem/images/common.png">
			      <span>聚合属性</span>		           	
		          <div class="entity_ch_opera" id="add_aggregate_group" >
		               <img src="media/admin/dictionary/basicItem/images/add-common.png">
		               <span>添加聚合分组</span>
		          </div>
		     </div>
		     <div class="opera_group">
				<img class="opera_entity_img" src="media/admin/dictionary/basicItem/images/info.png">
				<span class="opera_entity_img" id="add_group_mes"></span>
				<form id="group_opera_form1" class="opera_entity_form">
					<input type="hidden" name="code" id="code"/>
					<input type="hidden" id="group_parent" name="parent" value="">
					<input type="hidden" id="dataType" name="dataType" value="16">
					<input type="hidden" id="dataRange" name="dataRange" value="aggregate">
					<div>
						<span class="opera_entity_label">中文名称<span style="color: red;">*</span></span>
						<input type="text" name="cnName" id="cnName"/>
					</div>	
					<div>
						<span class="opera_entity_label">描述</span>
						<textarea name="description" id="description" rows="" cols=""></textarea>
					</div>						
				</form>
				<div class="opera_entity_btn">
					<span class="entity-btn-cancel" id="group_but_cancel">取消</span>
					<span class="entity-btn-confirm" id="group_but_confirm">确认</span>
				</div>
			</div>			
		</div>
		
		<!-- 聚合属性  end -->
		
		<div class="entity_relation entity" entityId="">
		           <div class="entity_ch_head">
				        <img src="media/admin/dictionary/basicItem/images/relative.png">
				        <span>实体关系管理</span>
				   </div>
		           <div class="entity_list entity_relation_list clear-fix">
		           		<div class="entity_attr entity_attr_img add_entity_relation">
		           			<img alt="添加关系" src="media/admin/dictionary/basicItem/addEntity_icon.png">		           			
		           		</div>	
		           </div>
		          <div class="opera_relation">
				    <img class="opera_entity_img" src="media/admin/dictionary/basicItem/images/info.png">
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
				        <div  class="select-wrap">
				            <span class="opera_entity_label">巨型关系<span style="color: red;">*</span></span>
				           	<select id="leftgiant" name="leftgiant" style="width:30%">
				           		<option value="0" selected="selected">否</option>
				           		<option value="1">是</option>
				            </select>
				        </div>
				        
				        
				         <div class="select-wrap" id="rela_right">
				            <span class="opera_entity_label">选择逆向实体<span style="color: red;">*</span></span>
				            <select id="rightRecordType" name="rightRecordType" style="width:30%">
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
				         <div  class="select-wrap">
				            <span class="opera_entity_label">逆向巨型关系<span style="color: red;">*</span></span>
				           	<select id="rightgiant" name="rightgiant" style="width:30%">
				           		<option value="0" selected="selected">否</option>
				           		<option value="1">是</option>
				            </select>
				        </div>
				    </form>
				    <div class="opera_entity_btn">
				        <span class="entity-btn-cancel" id="relation_but_cancel">取消</span>
				        <span class="entity-btn-confirm" id="relation_but_confirm">确认</span>
				    </div>
				</div>
				<!-- 编辑关系名称 -->
				<div class="opera_relation_edit" style="display: none;">
				    <img class="opera_entity_img" src="media/admin/dictionary/basicItem/images/info.png">
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
				         <div  class="select-wrap">
				            <span class="opera_entity_label">巨型关系<span style="color: red;">*</span></span>
				           	<select id="giant" name="giant" style="width:30%">
				            </select>
				        </div>
				    </form>
				    <div class="opera_entity_btn">
				        <span class="entity-btn-cancel" id="relation_but_cancel_edit">取消</span>
				        <span class="entity-btn-confirm" id="relation_but_confirm_edit">确认</span>
				    </div>
				</div>
		 </div>
	</div>	    
</div>

