define(function(require, exports, module){
	"use strict";
	 function adjustFieldTitle($titleLabel){
		var $titleSpan = $('<span class="field-title-d">').text($titleLabel.text());
		$titleLabel.empty().append($titleSpan);
		require('utils').removeStyle($titleLabel, 'font-size');
		require('utils').removeStyle($titleLabel, 'line-height');
		var thisWidth = $titleSpan.width(),
		thisHeight = $titleSpan.height(),
		parentWidth = $titleLabel.width(),
		parentHeight = $titleLabel.height(),
		parentFontsize = parseFloat($titleLabel.css('font-size'));
		;
		var row = Math.ceil(thisWidth / parentWidth);
		var parentLineheight = parentHeight / row;
		if(parentFontsize >= parentLineheight){
			$titleLabel.css('font-size', (parentLineheight - 2) + 'px');
		}
		$titleLabel.css('line-height', (parentLineheight - 1) + 'px');
		
		$titleLabel.text($titleSpan.text());
	};
	exports.adjustFieldTitle = adjustFieldTitle;
	
	exports.init = function($page, _param){
		var FieldSearch = require('field/js/field-search.js');
		//初始化参数
		var param = $.extend({
			tmplData	: {}
		}, _param);
		
		//字段组容器
		var $groupContainer = $('.group-container', $page);
		//字段模板
		var $tmplFieldGroup = $('#tmpl-field-group', $page);
		var $tmplField = $('#tmpl-field', $page);
		
		var tmplData = param.tmplData;
		
		/**
		 * 初始化某个字段组内的字段容器的拖拽事件
		 */
		function bindGroupFieldsDraggable($fieldContainer){
			$fieldContainer.sortable({
				helper 		: "clone",
				cursor 		: "move",// 移动时候鼠标样式
				opacity		: 0.5, // 拖拽过程中透明度
				placeholder	: function(curr){
					return curr.is('.dbcol')? 
							'field-item-placeholder dbcol'
							: 'field-item-placeholder'  
				},
				tolerance 	: 'pointer'
			});
		}
		
		/**
		 * 获得某个元素所在的字段最顶层dom
		 */
		function getLocateField($dom){
			return $($dom).closest('.field-item');
		}
		
		/**
		 * 获得某个元素所在的字段组最顶层dom
		 */
		function getLocateGroup($dom){
			return $($dom).closest('.field-group');
		}
		
		/**
		 * 获得字段组的字段容器dom
		 */
		function getFieldContainer($group){
			return $('.field-container', $group);
		}
		
		/**
		 * 初始化某个字段组的自动完成功能
		 */
		function initGroupFieldSearchAutocomplete($group){
			var $fieldSearch = $('.field-search', $group);
			var fieldSearch = FieldSearch.bind($fieldSearch, {
				single			: true,
				textPicked		: true,
				module			: param.module,
				afterChoose		: function(field){
					if(field.composite.isArray){
						//选择的字段是一个数组字段，锁定当前选择器的标签页
						fieldSearch.lockTab();
						//TODO: 将字段添加到数组当中
					}
					appendFieldToGroup({
						title		: field.title,
						fieldId		: field.id,
						colNum		: 1
					}, $group, field.composite.isArray == 1);
				}
			});
			/*disabledFieldIdSet.forEach(function(disabledFieldId){
				fieldSearch.enableField(disabledFieldId, false);
			});*/
			$group.data('fieldSearch', fieldSearch);
		}
		/**
		 * 添加一个字段到指定的字段组中
		 * @param groupFieldData 字段数据，必须包含fieldId属性
		 */
		function appendFieldToGroup(groupFieldData, $group, isArrayField){
			//判断字段在页面中是否存在
			//var $existsField = $page.find('.field-item[field-id="' + groupFieldData.fieldId + '"]');
			/*if($existsField.length > 0){
				require('dialog').notice('该字段已存在，不能重复添加', 'error');
				return false;
			}else{*/
			//构造新字段的内容
			var fieldData = {
					id			: groupFieldData.id,
					title		: groupFieldData.title,
					fieldId		: groupFieldData.fieldId,
					dv			: groupFieldData.dv || 'XXXXX',
					colNum		: groupFieldData.colNum
			};
			//将字段插入到字段组中
			var $fieldContainer = getFieldContainer($group);
			if(isArrayField){
				var $arrayTable = $('.field-array-table', $fieldContainer);
				if($arrayTable.length == 0){
					$arrayTable = $('#tmpl-field-array-table', $page).tmpl();
					$arrayTable.appendTo($fieldContainer);
				}
				var $titleCell = $('#tmpl-field-array-title', $page).tmpl(fieldData);
				$titleCell.data('field-data', fieldData);
				$arrayTable.find('.title-row').append($titleCell);
				$arrayTable.find('.value-row').append($('#tmpl-field-array-value', $page).tmpl(fieldData));
			}else{
				var $field = $tmplField.tmpl(fieldData);
				$field.data('field-data', fieldData).appendTo($fieldContainer);
				//enableFieldSelectable(groupFieldData.fieldId, false);
				adjustFieldTitle($field.find('.field-title'));
			}
			var fieldSearch = $group.data('fieldSearch');
			fieldSearch.enableField(fieldData.fieldId, false).done(function(field){
				if(field.composite.isArray){
					$group.attr('composite-id', field.composite.c_id);
				}
			});
			return true;
		}
		
		/**
		 * 绑定双击时，编辑该文本的事件
		 */
		function bindDblClickEdit(selector, inputClass){
			$page.on('dblclick', selector, function(e){
				require('utils').toEditContent(e.target, inputClass).bind('confirmed', function(text, $this){
					if($this.is('.field-title')){
						adjustFieldTitle($this);
					}else if($this.is('.group-title')){
						//焦点放到字段搜索框中
						getLocateGroup($this).find('.search-text-input').select();
					}
				});
			});
		}
		
		/**
		 * 切换字段的显示长度
		 */
		function toggleFieldExpand($field, toExpand){
			var $i = $('.toggle-expand-field i', $field);
			require('utils').switchClass($i, 'fa-expand', 'fa-compress', toExpand, function(compressed){
				$field.toggleClass('dbcol', !compressed);
			});
		}
		
		/**
		 * 绑定全局事件
		 */
		function bindPageEvent(event, selector, callback){
			$page.bind(event, selector, function(e){
				var $target = $(e.target);
				console.log($target);
				if($target.is(selector)){
					try{
						callback.apply($target, [e]);
					}catch(e){}
					return false;
				}
			});
		}
		/**
		 * 检查并整合页面中的模板数据
		 */
		function checkSaveData(callback){
			var Dialog = require('dialog');
			var saveData = {
					tmplId	: param.tmplId,
					//模板名
					title	: $('#tmplName', $page).val(),
					//字段组
					groups	: [],
					//模板模块
					module	: param.module
			};
			//遍历所有字段组
			getAllGroups().each(function(){
				var $group = $(this);
				var $arrayTable = $group.find('.field-array-table');
				var isArray = $arrayTable.length > 0;
				
				var group = {
						id		: $group.attr('data-id'),
						title	: $group.find('span.group-title').text(),
						isArray	: isArray,
						fields	: []							
				};
				saveData.groups.push(group);
				if(isArray){
					group.compositeId = $group.attr('composite-id');
					$arrayTable.find('.title-row>th[field-id]').each(function(){
						var $th = $(this);
						var field = $th.data('field-data');
						group.fields.push({
							id		: $th.attr('data-id'),
							fieldId	: $th.attr('field-id'),
							title	: $th.text(),
							viewVal	: 'XXX'
						});
					});
				}else{
					//遍历所有字段
					$group.find('.field-item').each(function(){
						var $field = $(this);
						var field = {
								id		: $field.attr('data-id'),
								fieldId	: $field.attr('field-id'),
								title	: $field.find('label.field-title').text(),
								viewVal	: $field.find('span.field-view').text(),
								dbcol	: $field.is('.dbcol')
						};
						group.fields.push(field);
					});
				}
			});
			if(!saveData.title){
				Dialog.notice('请填写模板名', 'error');
			}else if(saveData.groups.length == 0){
				Dialog.notice('请至少添加一个字段组', 'error');
			}else{
				Dialog.confirm('确认保存模板？', function(yes){
					if(yes){
						(callback || $.noop)(saveData);
					}
				});
			}
		}
		
		function getAllGroups(){
			return $groupContainer.find('.field-group');
		}
		
		var disabledFieldIdSet = new Set();
		function enableFieldSelectable(fieldId, toEnable){
			/*var $groups = getAllGroups();
			$groups.each(function(){
				var fieldSearch = $(this).data('fieldSearch');
				fieldSearch.enableField(fieldId, toEnable);
				if(toEnable == false){
					disabledFieldIdSet.add(fieldId.toString());
				}else{
					disabledFieldIdSet['delete'](fieldId.toString());
				}
			});*/
		}
		
		//初始化字段组容器的拖拽事件
		$groupContainer.sortable({
			helper		: 'clone',
			cursor		: 'move',
			opacity		: 0.5,
			placeholder	: 'group-item-placeholder',
			handle		: '.group-title',
			tolerance 	: 'pointer'
		});
		
		//绑定点击添加字段组按钮的事件
		$('#add-group', $page).click(function(){
			var $group = $tmplFieldGroup.tmpl({
				title	: '新字段组'
			}).appendTo($groupContainer);
			//绑定字段组内字段的拖动动作
			bindGroupFieldsDraggable(getFieldContainer($group));
			//初始化字段组的字段搜索自动完成功能
			initGroupFieldSearchAutocomplete($group);
			//页面滚动到底部
			require('utils').scrollTo($page.closest('.cpf-page-content'));
			//触发字段组的标题修改功能
			$group.find('.group-title').trigger('dblclick');
		});
		
		//绑定点击保存按钮时的回调
		$('#save', $page).click(function(){
			checkSaveData(function(saveData){
				require('ajax').postJson('admin/tmpl/dtmpl/save', saveData, function(data){
					if(data.status === 'suc'){
						require('dialog').notice('保存成功', 'success');
						$page.getLocatePage().close();
						var tpage = require('page').getPage(param.module + '_dtmpl_list');
						if(tpage){
							tpage.refresh();
						}
					}else{
						require('dialog').notice('保存失败', 'error');
					}
				});
			});
		});
		
		//绑定模板名称回车时，添加一个字段组
		$('#tmplName', $page).keypress(function(e){
			if(e.keyCode === 13){
				if($groupContainer.children('.field-group').length === 0){
					$('#add-group', $page).trigger('click');
				}
			}
		});
		
		//切换字段的显示长度
		bindPageEvent('click', '.toggle-expand-field i', function(e){
			var $field = getLocateField(this);
			toggleFieldExpand($field);
		});
		
		//删除字段
		bindPageEvent('click', '.remove-field i', function(e){
			var $field = getLocateField(e.target),
			$group = getLocateGroup(e.target),
			fieldTitle = $field.find('.field-title').text(),
			groupName = $group.find('.group-title').text();
			require('dialog').confirm('确认在字段组[' + groupName + ']中删除字段[' + fieldTitle + ']？', function(yes){
				if(yes){
					enableFieldSelectable($field.attr('field-id'));
					$field.remove();
				}
			});
		});
		//恢复字段名称
		bindPageEvent('click', '.recover-field i', function(e){
			var $field = getLocateField(e.target),
			$fieldTitle = $field.find('.field-title'),
			fieldTitle = $fieldTitle.text(),
			fieldData = $field.data('field-data'),
			fieldName = fieldTitle;
			if(fieldData && fieldData.title){
				fieldName = fieldData.title;
			}
			require('dialog').confirm('确认恢复字段[' + fieldTitle + ']为原始名称[' + fieldName + ']？', function(yes){
				if(yes){
					$fieldTitle.text(fieldName);
					adjustFieldTitle($fieldTitle);
				}
			});
		});
		
		//删除字段组
		bindPageEvent('click', '.remove-group i', function(e){
			var $group = getLocateGroup(e.target);
			var groupTitle = $group.find('.group-title').text();
			require('dialog').confirm('是否删除字段组[' + groupTitle + ']', function(yes){
				if(yes){
					//移除
					$group.remove();
					//释放字段组的字段选择器
					var fieldSearch = $group.data('fieldSearch');
					if(fieldSearch){
						fieldSearch.release();
					}
					//events.afterRemoveGroup([$group, $group.attr('data-id'), groupTitle]);
					$('.field-item[field-id]', $group).each(function(){
						enableFieldSelectable($(this).attr('field-id'));
					});
				}
			});
		});
		//双击编辑字段组标题
		bindDblClickEdit('span.group-title', 'group-title');
		//双击编辑字段标题
		bindDblClickEdit('label.field-title', 'field-title');
		bindDblClickEdit('span.field-view', 'field-view');
		
		//初始化默认数据
		if(tmplData && tmplData.groups){
			for(var i in tmplData.groups){
				var group = tmplData.groups[i];
				var $group = $tmplFieldGroup.tmpl(group).appendTo($groupContainer);
				if(!group.isArray){
					//绑定字段组内字段的拖动动作
					bindGroupFieldsDraggable(getFieldContainer($group));
				}
				//初始化字段组的字段搜索自动完成功能
				initGroupFieldSearchAutocomplete($group);
				for(var j in group.fields){
					var field = group.fields[j];
					appendFieldToGroup(field, $group, group.isArray == 1);
				}
			}
		}
		//字段的标题初始化，需要延迟，等到页面加载完之后执行
		setTimeout(function(){
			$('.field-title', $page).each(function(){adjustFieldTitle($(this))});
			$('#tmplName', $page).focus().select();
		}, 50);
		
		
	}
});