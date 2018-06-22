<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/base_empty.jsp"%>
<script src="media/admin/plugins/beyond/js/select2/select2.js"></script>
<div id="module_edit">
	<div class="page-header">
		<div class="header-title">
			<h1>编辑</h1>
		</div>
	</div>
	<div class="page-body">
		<div class="row">
			<div class="col-lg-12">
				<form class="bv-form form-horizontal validate-form" action="admin/module/configModule/do_edit">
					<div class="form-group">
						
						<input type="hidden" name="moduleName" value="${module.name }">
						<input type="hidden" name="moduleTitle" value="${module.title }">
						<input type="hidden" name="mappingName" value="${module.mappingName }">
						
						<label class="col-lg-2 control-label" for="moduleName">模块名</label>
						<div class="col-lg-5">
							${module.name }
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="moduleTitle">模块标题</label>
						
						<div class="col-lg-7">
							${module.title }
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="mappingName">配置名称</label>
						<div class="col-lg-5">
							${module.mappingName }
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="codeName">编码字段</label>
						<div class="col-lg-5">
							<select style="width: 30%;" id="codeName" class="ser-list" name="codeName">
								<option selected="selected" value="">(默认)code</option>
								<c:forEach items="${childNode }" var="item">
									<option value="${item.name }" <c:if test="${item.name eq module.codeName }">selected='selected'</c:if> >${item.name }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label" for="titleName">名称字段</label>
						<div class="col-lg-5">
							<select style="width: 30%;" id="titleName" class="ser-list" name="titleName">
								<option selected="selected" value="">(默认)姓名</option>
								<c:forEach items="${childNode }" var="item">
									<option value="${item.name }" <c:if test="${item.name eq module.titleName }">selected='selected'</c:if> >${item.name }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
			        	<div class="col-lg-offset-3 col-lg-3">
			        		<input class="btn btn-block btn-darkorange" type="submit" value="提交"  />
				        </div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script>
	seajs.use(['dialog','utils', 'ajax', '$CPF'], function(Dialog, Utils, Ajax, $CPF){
		var $page = $('#module_edit');
		
		/* $(".page-body", $page).on("click", ".ser-list", function (e) {
			 $(".ser-list", $page).css({"width":"30%"}).select2();
	    });  */
		
		 $(function(){
	    	 $(".ser-list", $page).css({"width":"30%"}).select2();
	    })
	    
	    $(".page-body", $page).on("change", "#mappingName", function() {
	    	var $this = $(this);
	    	var options=$("#mappingName option:selected"); //获取选中的项
	    	var parentId = options.attr("data-id");
	    	
	    	 Ajax.ajax('admin/module/configModule/childNodeList', {
	    		 parentId:parentId
	    	 }, function(data) {
	    		 var child = data.childNode;
	    		 var str = "<option selected=\"selected\" value=\"\">(默认)code</option>";
	    		 var str1 = "<option selected=\"selected\" value=\"\">(默认)姓名</option>";
	    		 for (var p in child) { //遍历json数组时，这么写p为索引，0,1
                     str = str + "<option value=\"" + child[p].name + "\">" + child[p].name + "</option>";
                     str1 = str1 + "<option value=\"" + child[p].name + "\">" + child[p].name + "</option>"; 
                 }
	    		 
	    		 $("#codeName").empty().append(str);
	    		 $("#titleName").empty().append(str1);
	    		 $("#codeName").options.selectedIndex = 0; //回到初始状态
	    		 $("#titleName").options.selectedIndex = 0; //回到初始状态
	    	 }) 
	    });
	});
</script>