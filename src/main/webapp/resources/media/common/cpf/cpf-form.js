/**
 * 表单提交模块
 * 表单提交有以下几种情况
 * 	1.查询
 * 		该情况下，表单提交之后依然会返回html代码，此时需要把html代码放到标签页或者弹出框中
 * 	2.修改
 * 		该情况下，表单提交之后会对数据库进行操作。处理结束之后只会返回处理的状态json。此时需要前后台统一该状态json，并让js直接根据状态信息处理回调
 * 	3.
 */
define(function(require, exports, module){
	var $CPF = require('$CPF'),
		Page = require('page'),
		Ajax = require('ajax')
		;
	
	$CPF.addDefaultParam({
		
	});
	
	$CPF.putPageInitSequeue(4, function($page){
		$('form', $page).not('.nform').submit(function(e){
			if(typeof CKEDITOR === 'object'){
				for (var key in CKEDITOR.instances){
					if($(CKEDITOR.instances[key].element.$).closest(this).length > 0){
						CKEDITOR.instances[key].updateElement();
					}
				}
			}
			var $this = $(this),
				page = $this.getLocatePage(),
				formData = new FormData(this)
			;
			if(!page){
				return;
			}
			var validator = $this.data('bootstrapValidator');
			if(validator && $this.is('.validate-form')){
				try{
					validator.validate();
				}catch(e){
					console.error(e);
					return false;
				}
				if(!validator.isValid()){
					return false;
				}
			}
			var url = $this.attr('action'),
				confirm = $this.attr('confirm'),
				Dialog = require('dialog'),
				_submit = function(){
				//构造提交事件
				var submitEvent = $.Event('cpf-submit');
				var canceled = false;
				submitEvent.doCancel = function(){canceled = true};
				var result = $this.trigger(submitEvent, [formData, $this, page]);
				try{
					if(!canceled){
						page.loadContent(url, undefined, formData);
						$this.trigger('cpf-submitting', [formData, $this, page]);
					}
				}catch(e){
					console.error(e);
				}finally{
					return false;
				}
			};
			if(confirm && Dialog){
				Dialog.confirm(confirm, function(yes){
					if(yes){
						_submit();
					}
				});
			}else{
				return _submit();
			}
		}).filter('.validate-form').each(function(){
			//初始化验证插件
			$(this).bootstrapValidator();
			//绑定重新校验表单事件
			$(':input', this).on('cpf-revalidate', function(){
				var $thisInput = $(this);
				var fieldName = $thisInput.attr('name');
				var bv = $thisInput.closest('form').data('bootstrapValidator');
				if(bv && fieldName){
					bv.updateStatus(fieldName, 'NOT_VALIDATED', null);
					bv.validateField(fieldName);
				}
			});
		}).end().submit(function(e){
			//阻止跳转
			  e.preventDefault();
		});
		//绑定在文本框的回车事件
		$('form :text', $page).keypress(function(e){
			if(e.keyCode === 13){
				$(this).closest('form').trigger('cpf-submit');
			}
		});
		/**
		 * 初始化下拉框的值
		 */
		$('form select', $page).each(function(){
			var val = $(this).attr('data-value');
			if(val){
				$(this).val(val);
			}
		});
		$('form :text.datepicker', $page).each(function(){
			$(this).datepicker();
		});
		/**
		 * 初始化页面的所有勾选框
		 * 勾选框为span.cpf-checkbox，勾选框对应内容直接放在元素内。元素支持以下几个属性
		 * 1.class包含checked，则勾选框默认将勾选上
		 * 2.input-id 勾选框的表单的id
		 * 3.checkbox-class， 勾选框实际展示元素的class（用于为勾选框添加其他样式）
		 * 4.name 勾选框表单的name
		 * 5.value 勾选框表单的value
		 * 事件
		 * 	cpf-toggle-checked事件，用于触发切换勾选框的勾选状态
		 * 	cpf-checked-change事件，勾选状态更改时触发，不可手动触发
		 * 		
		 */
		$('span.cpf-checkbox', $page).each(function(){
			var $this = $(this);
			var $span = $('<span>');
			var $checkbox = null;
			var checkboxClass = $this.attr('checkbox-class');
			$this.prepend($span.addClass(checkboxClass));
			if($this.attr('input') !== 'false'){
				var inputId = $this.attr('input-id');
				var name = $this.attr('name') || '';
				var value = $this.attr('value');
				$checkbox = $('<input type="checkbox" />')
									.attr('name', name)
									.val(value)
									.attr('id', inputId)
									.hide();
				$checkbox.prop('checked', $this.is('.checked'));
				$this.prepend($checkbox).removeClass('checked');
			}
			$this.on('cpf-toggle-checked', function(e, checked){
				var hasArg = typeof checked === 'boolean';
				var toCheckVal;
				if($checkbox){
					toCheckVal = hasArg? checked: !$checkbox.prop('checked');
					$checkbox.prop('checked', toCheckVal);
				}else{
					$this.toggleClass('checked', hasArg? checked: undefined);
					toCheckVal = $this.is('.checked');
				}
				$this.trigger('cpf-checked-change', [toCheckVal]);
			});
			$this.click(function(){$this.trigger('cpf-toggle-checked')});
		});
		/**
		 * 
		 */
		$($page).on('cpf-check-all-checkbox', function(e, checkboxGroup, checked){
			var $checkboxGroup;
			if(checkboxGroup){
				$checkboxGroup = $('span.cpf-checkbox[cpf-checkbox-group="' + checkboxGroup + '"]', $page);
			}else{
				$checkboxGroup = $('span.cpf-checkbox', $page);
			}
			$checkboxGroup.trigger('cpf-toggle-checked', [checked]);
		});
		/**
		 * 绑定批量勾选（取消）对应勾选框的勾选状态的点击事件
		 * 绑定对象有以下属性
		 * 
		 */
		$('.cpf-check-all', $page).click(function(){
			var $this = $(this);
			$page.trigger('cpf-check-all-checkbox', [$this.attr('cpf-checkbox-group'),  $this.attr('cpf-checked') !== 'false'])
		});
		
	});
});