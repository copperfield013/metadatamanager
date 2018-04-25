/**
 * 人口编辑的编辑功能中，用于匹配生成适用于对应字段的表单
 */
define(function(require, exports, module){
	
	
	function FieldInput(_param){
		var defaultParam = {
			//字段类型
			//text			： 普通文本
			//textarea		： 长文本
			//select		： 单选下拉框
			//multiselect	: 多选下拉框
			//checkbox		: 多选
			//radio			: 单选
			//date			: 日期选择
			//time			: 时间选择
			//datetime		: 日期时间选择
			//daterange		: 日期范围选择
			//datetimerange	: 日期时间范围选择
			//autocomplete	: 自动完成框
			type		: 0,
			//表单的name（提交的字段名）
			name		: null,
			//表单的id
			id			: null,
			//默认显示的值
			value		: null,
			//要添加的class
			styleClass	: '',
			readonly	: false,
			//当类型是select、checkbox、radio时，可用的选项
			//数组的元素有三个属性（view/value/attrs)
			options		: null,
			//当没有传入options，但是传入optionKey时，
			//会自动去FieldInput.GLOBAL_OPTIONS中根据该key去获取options
			optionsKey	: null,
			//已经生成的表单元素，如果传入了该值，那么就不会根据其他参数再生成表单元素
			$dom		: null,
			//检测表单的函数，如果错误，返回错误信息(string)，否则检测成功
			validator	: $.noop
		};
		
		var param = $.extend({}, defaultParam, _param);
		
		if(!param.options){
			param.options = FieldInput.GLOBAL_OPTIONS[param.optionsKey];
		}
		/**
		 * 检查构造表单元素的参数是否正常
		 */
		function checkBuildParam(){
		}
		
		this.__setNormalAttrs = function($dom){
			if(param.name){
				$dom.attr('name', param.name);
			}
			if(param.id){
				$dom.attr('id', param.id);
			}
			if(param.readonly){
				$dom.attr('readonly', 'readonly');
			}
		};
		//普通文本框
		this.__buildText = function(){
			var $text = $('<input type="text" />');
			this.__setNormalAttrs($text);
			if(param.value){
				$text.val(param.value);
			}
			return $text;
			
		};
		
		//长文本输入
		this.__buildTextarea = function(){
			var $ta = $('<textarea></textarea>');
			this.__setNormalAttrs($ta);
			if(param.value){
				$ta.val(param.value);
			}
			return $ta;
		};
		//下拉选择框
		this.__buildSelect = function(){
			var $select = $('<select>');
			this.__setNormalAttrs($select);
			var $defOption = $('<option value="">--请选择---</option>');
			$select.append($defOption);
			$select.val('');
			if($.isArray(param.options)){
				for(var i in param.options){
					var option = param.options[i];
					if(option.view){
						//构造选项dom
						var $option = $('<option>');
						//显示内容
						$option.text(option.view);
						//设置属性
						if(typeof option.attrs === 'object'){
							$option.attr(option.attrs);
						}
						//设置选项值
						if(option.value){
							$option.attr('value', option.value);
						}
						$select.append($option);
					}
				}
				if(param.value !== undefined && param.value !== ''){
					if($select.find('option[value="' + param.value + '"]').length > 0){
						$select.val(param.value);
					}
				}
			}
			return $select;
		};
		
		this.__buildDatepicker = function(){
			var $text = this.__buildText();
			var value = param.value;
			var Utils = require('utils');
			if(typeof value === 'number'){
				value = Utils.formatDate(value, 'yyyy-mm-dd');
			}
			if(typeof value === 'string'){
				$text.val(value);
			}
			$text.attr('readonly', 'readonly');
			Utils.datepicker($text);
			return $text;
		};
		
		this.__buildCheckbox = function(){
			var Checkbox = require('checkbox');
			var $c = $('<span>');
			if($.isArray(param.options)){
				for(var i in param.options){
					var option = param.options[i];
					var $checkbox = $('<input type="checkbox" '
							+ 'name="' + param.name + '" '
							+ 'value="' + option.value + '" '
							+ 'data-text="' + option.view + '" />');
					$c.append($checkbox);
				}
				var group = Checkbox.bind($c.children(), param.value);
			}
			$c.val = function(val){
				if(val === undefined){
					return group.getValue().join();
				}else{
					group.setValue(val);
				}
			}
			return $c;
		}
		
		this.__buildDom = function(){
			checkBuildParam();
			var $dom = null;
			switch(param.type){
				case 'text':
					$dom = this.__buildText();
					break;
				case 'textarea':
					$dom = this.__buildTextarea();
					break;
				case 'select':
					$dom = this.__buildSelect();
					break;
				case 'date':
					$dom = this.__buildDatepicker();
					break;
				case 'checkbox':
					$dom = this.__buildCheckbox();
					break;
				default:
			}
			return $dom;
		};
		
		this.getType = function(){
			return param.type;
		}
		/**
		 * 根据参数生成表单元素（只生成一次）
		 */
		this.getDom = function(){
			if(param.$dom){
				return param.$dom;
			}else{
				return param.$dom = this.__buildDom();
			}
		};
		/**
		 * 获得当前表单的值
		 */
		this.getValue = function(){
			switch(param.type){
				case 'select':
				default:
					return this.getDom().val();
			}
		};
		
		/**
		 * 手动设置当前表单的值
		 */
		this.setValue = function(val){
			switch(param.type){
				case 'select':
				default: 
					this.getDom().val(val);
			}
		}
		
		/**
		 * 重置表单的值
		 */
		this.resetValue = function(){
			this.setValue(param.value);
		}
		
		/**
		 * 检测当前表单的合法性
		 */
		this.validate = function(){
			param.validator.apply(this, [this.getValue()]);
		}
		
		/**
		 * 
		 */
		this.getComparatorMap = function(callback){
			return FieldInput.getGlobalComparators(param.type, callback);
		}
	}
	
	$.extend(FieldInput, {
		appendTo		: function($doms, paramGetter){
			var def = $.Deferred();
			paramGetter = paramGetter || function($dom){
				function attr(attrName){
					return $dom.attr(attrName);
				}
				return {
					type		: attr('fInp-type'),
					name		: attr('fInp-name'),
					id			: attr('fInp-id'),
					value		: attr('fInp-value'),
					styleClass	: attr('fInp-class'),
					optionsKey	: attr('fInp-optkey'),
					readonly	: attr('fInp-readonly')
				};
			};
			$doms.each(function(){
				var $this = $(this);
				var fInp = new FieldInput(paramGetter($this));
				$this.append(fInp.getDom());
			});
			def.resolve();
			return def.promise();
		},
		globalOptionsCacheTimeLineMap	: {},
		globalOptionsCacheMap			: {},
		/**
		 * 加载全局的选项map
		 * loadGlobalOptions(url, reqParam)：从后台加载所有选项数据
		 */
		loadGlobalOptions	: function(url, reqParam){
			var deferred = $.Deferred();
			var originOptions = FieldInput.GLOBAL_OPTIONS;
			var TIMELINE = 30000;
			if(typeof url === 'string'){
				var timeline = this.globalOptionsCacheTimeLineMap[url];
				var now = (new Date()).getTime();
				if(!timeline || now - timeline > TIMELINE){
					require('ajax').ajax(url, reqParam, function(data){
						FieldInput.loadGlobalOptions(data).done(function(){
							FieldInput.globalOptionsCacheMap[url] = data;
							FieldInput.globalOptionsCacheTimeLineMap[url] = now;
							deferred.resolve([data, originOptions]);
						});
					});
				}else{
					deferred.resolve([FieldInput.globalOptionsCacheMap[url], originOptions]);
				}
			}else if(typeof url === 'object'){
				FieldInput.GLOBAL_OPTIONS = url;
				FieldInput.globalOptionsLoaded = true;
				deferred.resolve([url, originOptions]);
			}
			return deferred.promise();
		},
		globalOptionsLoaded		: false,
		//全局选项，
		GLOBAL_OPTIONS			: {},
		GLOBAL_COMPARATOR_MAP	: null,
		getGlobalComparators	: function(inputType, callback){
			function _callback(){
				(callback || $.noop)(FieldInput.GLOBAL_COMPARATOR_MAP[inputType].comparators);
			}
			if(FieldInput.GLOBAL_COMPARATOR_MAP == null){
				FieldInput.GLOBAL_COMPARATOR_MAP = $.Callbacks();
				FieldInput.GLOBAL_COMPARATOR_MAP.add(_callback);
				require('ajax')
					.loadResource('media/admin/field/json/comparator-map.json')
					.done(function(data){
						var callbacks = FieldInput.GLOBAL_COMPARATOR_MAP;
						FieldInput.GLOBAL_COMPARATOR_MAP = data;
						callbacks.fire();
					});
			}else if(typeof FieldInput.GLOBAL_COMPARATOR_MAP.fire === 'function'){
				FieldInput.GLOBAL_COMPARATOR_MAP.add(_callback);
			}else{
				_callback();
			}
		}
	});
	
	module.exports = FieldInput;
});