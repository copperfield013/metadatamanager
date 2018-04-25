define(function(require, exports, module){
	function initPage($page, _param){
		var Utils = require('utils'), 
			Dialog = require('dialog'), 
			Bloodhound = require('bloodhound'), 
			Ajax = require('ajax');
		var param = $.extend({
			tmplData	: {}
		}, _param);
		
		var $groupContainer = $('.group-container', $page);
		var $tmplFieldGroup = $('#tmpl-field-group', $page),
		$tmplField = $('#tmpl-field', $page);
		var events = {
				afterRemoveGroup	: $.noop
		}
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
		 * 切换字段的显示长度
		 */
		function toggleFieldExpand($field, toExpand){
			var $i = $('.toggle-expand-field i', $field);
			Utils.switchClass($i, 'fa-expand', 'fa-compress', toExpand, function(compressed){
				$field.toggleClass('dbcol', !compressed);
			});
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
		
		var __fieldData = null, __compositeData = null, __fieldKeyData = {}, waitFnArray = null;
		/**
		 * 初始化字段数据后执行callback方法
		 */
		function afterLoadFieldData(callback){
			if(!__compositeData){
				if(waitFnArray){
					waitFnArray.push(callback);
					return;
				}else{
					waitFnArray = [callback];
				}
				Ajax.ajax('admin/peopledata/viewtmpl/field_json', {
					
				}, function(data){
					if(data){
						__compositeData = data;
						__fieldData = transferInfoToFields(__compositeData, __fieldKeyData);
						for(var i in waitFnArray){
							(waitFnArray[i] || $.noop)(__fieldData, __compositeData, __fieldKeyData);
						}
						waitFnArray = null;
					}
				});
			}else{
				(callback || $.noop)(__fieldData, __compositeData, __fieldKeyData);
			}
		}
		function getFieldData(fieldId, callback){
			afterLoadFieldData(function(fData, cData, fieldKeyData){
				var field = fieldKeyData['id_' + fieldId];
				if(field){
					(callback || $.noop)(field);
				}
			});
		}
		/**
		 * 将
		 */
		function transferInfoToFields(compositeData, fieldKeyData){
			var fieldData = [];
			for(var i in compositeData){
				var thisComposite = compositeData[i];
				for(var j in thisComposite.fields){
					var thisField = thisComposite.fields[j];
					fieldData.push(
							fieldKeyData['id_' + thisField.id] = {
									id		: thisField.id,
									name	: thisField.name,
									cname	: thisField.cname,
									type	: thisField.type,
									c_id	: thisComposite.id,
									c_name	: thisComposite.name,
									c_cname	: thisComposite.cname
							});
				}
			}
			return fieldData;
		}
		
		
		/**
		 * 初始化某个字段组的自动完成功能
		 */
		function initGroupFieldSearchAutocomplete($group){
			afterLoadFieldData(function(fieldData){
				//数据源
				var bloodhound = new Bloodhound({
					datumTokenizer 	: Bloodhound.tokenizers.obj.whitespace(['cname', 'c_cname']),
					queryTokenizer 	: Bloodhound.tokenizers.whitespace,
					local			: fieldData
				});
				var $search = $('.glyphicon-search-input', $group); 
				$search.typeahead({
				}, {
					display		: 'cname',
					source 		: bloodhound.ttAdapter(),
					templates	: {
						suggestion	: Handlebars.compile('<p><strong>{{cname}}</strong>-<i>{{c_cname}}</i></p>')
					}
				}).bind('typeahead:select', function(e, suggestion){
					if(appendFieldToGroup(suggestion.id, $group)){
						$search.typeahead('val', '');
					}
				});
			});
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
		
		function appendFieldToGroup(fieldId, $group, groupFieldData){
			groupFieldData = groupFieldData || {};
			//判断字段在页面中是否存在
			var $existsField = $page.find('.field-item[field-id="' + fieldId + '"]');
			if($existsField.length > 0){
				Dialog.notice('该字段已存在，不能重复添加', 'error');
				return false;
			}else{
				//构造新字段的内容
				getFieldData(fieldId, function(field){
					var fieldData = {
							id			: groupFieldData.id,
							title		: field.cname,
							name		: field.cname,
							fieldId		: field.id,
							dv			: groupFieldData.dv || 'XXXXX',
							colNum		: groupFieldData.colNum
					};
					//将字段插入到字段组中
					var $fieldContainer = getFieldContainer($group);
					var $field = $tmplField.tmpl(fieldData);
					$field.data('field-data', fieldData).appendTo($fieldContainer);
					adjustFieldTitle($field.find('.field-title'));
				});
				fieldpickerHandler(function($fieldpicker){
					var $toDisable = $('a.fieldpicker-field-item[data-id="' + fieldId + '"]', $fieldpicker);
					$toDisable.addClass('disabled');
				});
				return true;
			}
		}
		/**
		 * 切换字段选择器的显示状态
		 */
		function toggleFieldPicker($group, toShow){
			fieldpickerHandler(function($fieldPicker, free){
				if($group === false){
					$fieldPicker.hide();
				}else{
					if($fieldPicker.closest($group).length == 1){
						$fieldPicker.toggle(toShow);
					}else{
						var $fieldSearch = $group.find('.field-search');
						$fieldSearch.append($fieldPicker.show());
					}
				}
			});
		}
		
		var __$fieldPicker = null;
		/**
		 * 传入一个函数，获得字段选择器的dom，并对其进行处理
		 */
		function fieldpickerHandler(callback){
			afterLoadFieldData(function(fieldData, compositeData){
				if(!__$fieldPicker){
					__$fieldPicker = $('#tmpl-fieldpicker', $page).tmpl({
						composites	: compositeData
					});
					$('.fieldpicker-field-item', __$fieldPicker).click(function(){
						var $this = $(this);
						if(!$this.is('.disabled')){
							var fieldId = $this.attr('data-id');
							if(fieldId){
								appendFieldToGroup(fieldId, getLocateGroup(this));
							}
						}
					});
					$('ul.dropdown-menu>li>a', __$fieldPicker).click(function(){
						var $this = $(this);
						var $li = $this.closest('li.dropdown');
						$li.find('a.dropdown-toggle span').text($this.text());
					});
					(callback || $.noop)(__$fieldPicker, true);
				}else{
					(callback || $.noop)(__$fieldPicker, false);
				}
			});
		}
		
		function enableFieldSelectable(fieldId){
			fieldpickerHandler(function($fieldPicker){
				$fieldPicker.find('.fieldpicker-field-item.disabled[data-id="' + fieldId + '"]').removeClass('disabled');
			});
		}
		
		
		//初始化默认数据
		if(tmplData && tmplData.groups){
			for(var i in tmplData.groups){
				var $group = $tmplFieldGroup.tmpl(tmplData.groups[i]);
				var $fieldContainer = getFieldContainer($group);
				bindGroupFieldsDraggable($fieldContainer);
				for(var j in tmplData.groups[i].fields){
					var field = tmplData.groups[i].fields[j];
					appendFieldToGroup(field.fieldId, $group, field);
				}
				$group.data('group-data', tmplData.groups[i]).appendTo($groupContainer);
				initGroupFieldSearchAutocomplete($group);
			}
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
		 * 检查并整合页面中的模板数据
		 */
		function checkSaveData(callback){
			var saveData = {
					tmplId	: param.tmplId,
					//模板名
					name	: $('#tmplName', $page).val(),
					//字段组
					groups	: []
			};
			//遍历所有字段组
			$groupContainer.find('.field-group').each(function(){
				var $group = $(this);
				var group = {
						id		: $group.attr('data-id'),
						title	: $group.find('span.group-title').text(),
						fields	: []								
				};
				saveData.groups.push(group);
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
			});
			if(!saveData.name){
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
			Utils.scrollTo($page.closest('.cpf-page-content'));
			//触发字段组的标题修改功能
			$group.find('.group-title').trigger('dblclick');
		});
		//绑定点击保存按钮时的回调
		$('#save', $page).click(function(){
			checkSaveData(function(saveData){
				Ajax.postJson('admin/peopledata/viewtmpl/save', saveData, function(data){
					if(data.status === 'suc'){
						Dialog.notice('保存成功', 'success');
						$page.getLocatePage().close();
						var tpage = require('page').getPage('viewtmpl_list');
						if(tpage){
							tpage.refresh();
						}
					}else{
						Dialog.notice('保存失败', 'error');
					}
				});
			});
		});
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
			Dialog.confirm('确认在字段组[' + groupName + ']中删除字段[' + fieldTitle + ']？', function(yes){
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
			if(fieldData && fieldData.name){
				fieldName = fieldData.name;
			}
			Dialog.confirm('确认恢复字段[' + fieldTitle + ']为原始名称[' + fieldName + ']？', function(yes){
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
			Dialog.confirm('是否删除字段组[' + groupTitle + ']', function(yes){
				if(yes){
					//移除
					$group.remove();
					events.afterRemoveGroup([$group, $group.attr('data-id'), groupTitle]);
					$('.field-item[field-id]', $group).each(function(){
						enableFieldSelectable($(this).attr('field-id'));
					});
				}
			});
		});
		//切换字段选择框
		$page.on('mousedown', function(e){
			var $target = $(e.target);
			if($target.closest('.field-picker-button').length == 1){
				toggleFieldPicker(getLocateGroup($target));
				return false;
			}else if($target.closest('.fieldpicker-container').length === 0){
				toggleFieldPicker(false);
			}
		});
		//添加字段快捷键
		$page.on('keydown', ':text', function(e){
			if(e.altKey && e.keyCode == 78){
				$('#add-group', $page).trigger('click');
				return false;
			}
		});
		
		//双击编辑字段组标题
		bindDblClickEdit('span.group-title', 'group-title');
		//双击编辑字段标题
		bindDblClickEdit('label.field-title', 'field-title');
		bindDblClickEdit('span.field-view', 'field-view');
		//字段的标题初始化，需要延迟，等到页面加载完之后执行
		setTimeout(function(){
			$('.field-title', $page).each(function(){adjustFieldTitle($(this))});
			$('#tmplName', $page).focus().select();
		}, 50);
	}
	/**
	 * 动态调整字段标题的行高，使其垂直居中
	 */
	function adjustFieldTitle($titleLabel){
		var $titleSpan = $('<span class="field-title-d">').text($titleLabel.text());
		$titleLabel.empty().append($titleSpan);
		require('utils').removeStyle($titleLabel, 'font-size');
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
	}
	
	exports.init = initPage;
	exports.adjustFieldTitle = adjustFieldTitle;
});