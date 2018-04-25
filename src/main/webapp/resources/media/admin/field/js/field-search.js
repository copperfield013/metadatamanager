/**
 * 字段选择器
 */
define(function(require, exports, module){
	
	function FieldSearch(_param){
		var defaultParam = {
			//是否单选.未完成
			single			: false,
			$container		: $(),
			reqDataURL		: 'admin/field/json/people',
			reqDataParam	: {},
			disablePicked	: true,
			afterPicked		: $.noop,
			//选择后，是否在自动完成文本框中同步该选项
			textPicked		: false,
			//是否显示数组字段
			showArrayComposite	: true
		};
		
		var param = $.extend({}, defaultParam, _param);
		var _afterPicked = param.afterPicked;
		var _this = this;
		
		param.afterPicked = function(){
			param.afterChoose.apply(_this, arguments);
			_afterPicked.apply(_this, arguments);
		};
		
		var loadFieldDataDeferred = null;
		function afterLoadFieldData(callback){
			if(loadFieldDataDeferred == null){
				loadFieldDataDeferred = $.Deferred();
				require('ajax').ajax(param.reqDataURL, param.reqDataParam, function(__compositeData){
					var __fieldKeyData = {};
					var __fieldData = transferInfoToFields(__compositeData, __fieldKeyData);
					loadFieldDataDeferred.resolve(__fieldData, __compositeData, __fieldKeyData);
				});
			}
			loadFieldDataDeferred.done(callback);
		}
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
									c_id	: thisComposite.c_id,
									c_name	: thisComposite.name,
									c_cname	: thisComposite.cname,
									title	: thisField.cname,
									c_title	: thisComposite.cname,
									composite	: thisComposite
							});
				}
			}
			return fieldData;
		}
		
		function whenPickField(fieldId){
			_this.enableField(fieldId, param.disablePicked == false).done(function(field){
				if(param.textPicked){
					try{
						setTextsValue(field.cname);
					}catch(e){}
				}
				param.afterPicked.apply(_this, [field]);
			});
		}
		
		var __$fieldPicker = null;
		var __fieldPickerDeferred = $.Deferred(),
			__loadingFieldPicker = false;
		
		function filterTmplData(composites){
			var result = [];
			if($.isArray(composites)){
				for(var i in composites){
					var composite = composites[i];
					if(!param.showArrayComposite && composite.isArray == 1){
						continue;
					}
					result.push(composite);
				}
			}
			return result;
		}
		
		/**
		 * 传入一个函数，获得字段选择器的dom，并对其进行处理
		 */
		function fieldpickerHandler(callback){
			afterLoadFieldData(function(fieldData, compositeData){
				
				if(!__loadingFieldPicker){
					__loadingFieldPicker = true;
					require('tmpl').load('media/admin/field/tmpl/tmpl-fieldsearch.tmpl').done(function(tmpl){
						var _compositeData = filterTmplData(compositeData);
						__$fieldPicker = tmpl.tmpl({
							composites	: _compositeData,
							pickerKey	: require('utils').uuid(5, 62)
						});
						$('.fieldpicker-field-item', __$fieldPicker).click(function(){
							var $this = $(this);
							if(!$this.is('.disabled')){
								var fieldId = $this.attr('data-id');
								if(fieldId){
									whenPickField(fieldId);
								}
							}
						});
						$('ul.dropdown-menu>li>a', __$fieldPicker).click(function(){
							var $this = $(this);
							var $li = $this.closest('li.dropdown');
							$li.find('a.dropdown-toggle span').text($this.text());
						});
						(callback || $.noop)(__$fieldPicker, true);
						__fieldPickerDeferred.resolve();
					});
				}else{
					__fieldPickerDeferred.done(function(){
						(callback || $.noop)(__$fieldPicker, false);
					});
				}
			});
		}
		var bindedSearchTexts = new Set();
		function setTextsValue(fieldTitle){
			bindedSearchTexts.forEach(function(t){
				$(t).typeahead('val', fieldTitle);
			})
		}
		/**
		 * 绑定自动完成的功能到某个文本输入框
		 */
		this.bindTypeahead = function($search, __param){
			var thisParam = $.extend({}, {
				afterSelected	: $.noop
			}, __param);
			var _afterSelected = thisParam.afterSelected;
			thisParam.afterSelected = function(){
				__param.afterChoose.apply(_this, arguments);
				_afterSelected.apply(this, arguments);
			}
			if($search && $search.length > 0){
				afterLoadFieldData(function(fieldData){
					var Bloodhound = require('bloodhound');
					//数据源
					var bloodhound = new Bloodhound({
						datumTokenizer 	: Bloodhound.tokenizers.obj.whitespace(['cname', 'c_cname']),
						queryTokenizer 	: Bloodhound.tokenizers.whitespace,
						local			: fieldData
					});
					$search.typeahead({
					}, {
						display		: 'cname',
						source 		: bloodhound.ttAdapter(),
						templates	: {
							suggestion	: Handlebars.compile('<p><strong>{{cname}}</strong>-<i>{{c_cname}}</i></p>')
						}
					}).bind('typeahead:select', function(e, suggestion){
						var $this = $(this);
						var fieldId = suggestion.id;
						if(param.disablePicked){
							_this.isFieldDisabled(fieldId).done(function(disabled){
								if(disabled){
									require('dialog').notice('不能选择该字段', 'error');
								}else{
									_this.enableField(suggestion.id, false).done(function(field){
										thisParam.afterSelected.apply(_this, [field, $this]);
									});
								}
							});
						}else{
							_this.getFieldData(fieldId).done(function(field){
								thisParam.afterSelected.apply(_this, [field, $this]);
							});
						}
					}).each(function(){
						bindedSearchTexts.add(this);
					});
				});
			}
		};
		
		/**
		 * 切换字段选择框的显示状态
		 */
		this.togglePicker = function($container, toShow){
			var deferred = $.Deferred();
			fieldpickerHandler(function($fieldPicker, free){
				if($container === false){
					$fieldPicker.hide();
				}else{
					if($fieldPicker.closest($container).length == 1){
						$fieldPicker.toggle(toShow);
					}else{
						$container.append($fieldPicker.show());
					}
				}
				deferred.resolve($fieldPicker);
			});
			return deferred.promise();
		};
		
		/**
		 * 获得字段数据，该方法是异步操作，返回Deferred
		 */
		this.getFieldData = function(fieldId, callback){
			var def = $.Deferred();
			afterLoadFieldData(function(fData, cData, fieldKeyData){
				var field = fieldKeyData['id_' + fieldId];
				if(field){
					(callback || $.noop)(field);
				}
				def.resolve(field);
			});
			return def.promise();
		};
		
		/**
		 * 获得绑定的页面元素
		 */
		this.getContainer = function(){
			return param.$container;
		};
		
		
		var disabledFieldSet = new Set();
		/**
		 * 启用字段（参数toEnable为false时为禁用，其他情况均为启用）
		 * 该方法是异步操作，返回一个Deferred对象
		 */
		this.enableField = function(fieldId, toEnable){
			var def = $.Deferred();
			fieldpickerHandler(function($fieldpicker){
				var $toDisable = $('a.fieldpicker-field-item[data-id="' + fieldId + '"]', $fieldpicker);
				$toDisable.toggleClass('disabled', toEnable === false);
				_this.getFieldData(fieldId).done(function(field){
					if(toEnable === false){
						if(field.composite.isArray){
							//选择的字段是一个数组字段，锁定当前选择器的标签页
							_this.lockTabByCompositeId(field.composite.c_id);
						}else{
							hideArrayComposites();
						}
						disabledFieldSet.add(fieldId.toString());
					}else{
						disabledFieldSet['delete'](fieldId.toString());
						if(disabledFieldSet.size === 0){
							if(field.composite.isArray){
								_this.lockTabByCompositeId(field.composite.c_id, false);
							}else{
								hideArrayComposites(false);
							}
						}
					}
					def.resolve(field, $fieldpicker);
				});
			});
			return def.promise();
		}
		/**
		 * 判断字段是否被禁用
		 */
		this.isFieldDisabled = function(fieldId){
			var def = $.Deferred();
			isDisabledFieldWhenLocked(fieldId).done(function(isDisabledField){
				def.resolve(disabledFieldSet.has(fieldId.toString()) || isDisabledField);
			});
			return def.promise();
		}
		/**
		 * 选择某个字段
		 * @param fieldId 字段的id
		 */
		this.select = function(fieldId){
			this.getFieldData(fieldId).done(function(field){
				if(field){
					setTextsValue(field.cname);
					whenPickField(fieldId);
				}
			});
		};
		var locked = false, lockedFields = new Set();
		/**
		 * 判断字段是否在锁定模式下，是禁用的字段
		 */
		function isDisabledFieldWhenLocked(fieldId){
			var def = $.Deferred();
			if(locked){
				_this.getFieldData(fieldId).done(function(field){
					if(locked && field.composite.isArray){
						_this.getLockedCompositeId().done(function(compositeId){
							def.resolve(field.composite.c_id.toString() != compositeId.toString());
						});
					}else{
						def.resolve(false);
					}
				});
			}else{
				def.resolve(false);
			}
			return def.promise();
		}
		function hideArrayComposites(toHide){
			var def = $.Deferred();
			fieldpickerHandler(function($fieldPicker){
				$('ul.nav-tabs li>a[data-toggle="tab"]', $fieldPicker).each(function(){
					var $li = $(this).closest('li');
					if($li.is('.array-field')){
						$li.toggle(toHide === false);
					}
				});
				def.resolve();
			});
			return def.promise();
		}
		this.getLockedCompositeId = function(){
			var def = $.Deferred();
			if(locked){
				fieldpickerHandler(function($fieldPicker){
					def.resolve($('ul.nav-tabs li.active[data-id]', $fieldPicker).attr('data-id'));
				});
			}else{
				def.resolve();
			}
			return def.promise();
		}
		this.lockTabByCompositeId = function(compositeId, toLock){
			var def = $.Deferred();
			fieldpickerHandler(function($fieldPicker){
				var tabIndex = $('ul.nav-tabs li[data-id]', $fieldPicker).index($('ul.nav-tabs li[data-id="' + compositeId + '"]', $fieldPicker));
				_this.lockTab(tabIndex, toLock);
			});
			return def.promise();
		}
		/**
		 * 锁定选择器的标签页，
		 * 并且限制自动完成的字段只能选择该标签页下的字段
		 * @param tabIndex 标签页索引，如果不传入，则锁定当前打开的标签页
		 */
		this.lockTab = function(tabIndex, toLock){
			var def = $.Deferred();
			fieldpickerHandler(function($fieldPicker){
				if(typeof tabIndex === 'boolean'){
					toLock = tabIndex;
					tabIndex = undefined;
				}
				if(tabIndex === undefined){
					tabIndex = -1;
					$('ul.nav-tabs li>a[data-toggle="tab"]', $fieldPicker).each(function(i, a){
						if($(a).parent().is('.active')){
							tabIndex = i;
							return false;
						}
					});
					
					_this.lockTab(tabIndex, toLock).done(function(){
						def.resolve();
					});
				}else{
					_this.activeTab(tabIndex).done(function($tabTitle){
						var toHideTab = toLock === false;
						$('ul.nav-tabs li>a[data-toggle="tab"]', $fieldPicker).each(function(){
							var $li = $(this).closest('li');
							if($li.not($tabTitle)){
								$li.toggle(toHideTab);
							}
						});
						$tabTitle.show();
						locked = !toHideTab;
						def.resolve();
					});
				}
			});
			return def.promise();
		}
		
		/**
		 * 激活指定索引的标签页
		 */
		this.activeTab = function(tabIndex){
			var def = $.Deferred();
			fieldpickerHandler(function($fieldPicker){
				$('.tab-pane', $fieldPicker).hide();
				var $tabTitle = $('ul.nav-tabs li[data-id]>a', $fieldPicker).eq(tabIndex).trigger('click');
				var $li = $tabTitle.closest('li');
				$('.tab-pane' + $tabTitle.attr('href'), $fieldPicker).show();
				def.resolve($li);
			});
			return def.promise();
		}
		
		
		var released = false;
		this.release = function(){
			param = undefined;
			__$fieldPicker = null;
			__fieldPickerDeferred = undefined;
			__loadingFieldPicker = undefined;
			_afterPicked = param.afterPicked;
			released = true;
		};
		this.isReleased = function(){
			return released;
		}
	}
	
	var $model = $('<div class="input-icon field-search">'
			+ '<span class="search-input-wrapper">'
			+ '<input type="text" class="search-text-input form-control input-xs glyphicon-search-input" autocomplete="off" placeholder="输入添加的字段名" />'
			+ '</span>'
			+ '<i class="glyphicon glyphicon-search blue"></i>'
			+ '<i title="选择字段" class="glyphicon glyphicon-th blue field-picker-button"></i>'
			+ '</div>');
	$.extend(FieldSearch, {
		build	: function(param){
			var $search = $model.clone();
			return exports.bind($search);
		},
		bind	: function($search, param){
			var $textInput = $search.find('.search-text-input');
			var reqDataURL = undefined;
			if(!param.module){
				param.module = 'people';
			}
			reqDataURL = 'admin/field/json/' + param.module;
			var search = new FieldSearch($.extend({}, {
				$container		: $search,
				afterChoose		: undefined,
				afterPicked		: undefined,
				afterSelected	: undefined,
				reqDataURL		: reqDataURL
			}, param));
			(param.textInputHandler || $.noop).apply(search, [$textInput]);
			search.bindTypeahead($textInput, param);
			var $button = $search.find('.field-picker-button');
			$button.click(function(){
				search.togglePicker($search);
			});
			$($search.getLocatePage().getContent()).on('click', function(e){
				if($(e.target).closest($search).length === 0){
					search.togglePicker(false);
				}
			});
			return search;
		}
	});
	
	module.exports = FieldSearch;
});