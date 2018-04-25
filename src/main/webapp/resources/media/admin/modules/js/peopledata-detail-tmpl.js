seajs.use(['utils'], function(Utils){
	var $page = $('#people-${peopleCode }');
	var hasRecord = '${people != null}';
	if(hasRecord != 'true'){
		Dialog.notice('数据不存在', 'warning');
		$('.header-title h1').text('数据不存在');
	}
	$('a.toggle-history', $page).click(function(){
		$(this).closest('.toggle-history').hide();
		$('.header-buttons', $page).show();
	});
	var timelineInited = false;
	$('a.toggle-timeline', $page).click(function(){
		$('#timeline-area', $page).show();
		if(!timelineInited){
			$('.show-more-history', $page).trigger('click');
		}
		return false;
	});
	$page.click(function(e){
		var $target = $(e.target);
		if($target.closest('#timeline-area').length == 0
			&& $target.closest('.toggle-timeline') == 0 ){
			$('#timeline-area', $page).hide();
		}
		return false; 
	});
	
	//下一页
	var curPageNo = 0;
	$('.show-more-history', $page).click(function(){
		var $this = $(this);
		if(!$this.is('.disabled')){
			$this.addClass('disabled').text('加载中');
			Ajax.ajax('admin/peopledata/paging_history/${peopleCode}', {
				pageNo	: curPageNo + 1
			}, function(data){
				if(data.status === 'suc'){
					appendHistory(data.history);
					curPageNo ++;
					timelineInited = true;
				}
				if(data.isLast){
					$this.text('没有更多了');					
				}else{
					$this.text('查看更多').removeClass('disabled');
				}
			});
		}
	});
	$page.on('click', '.circ', function(){
		var time = parseInt($(this).closest('dd').attr('data-time'));
		$page.getLocatePage().loadContent('admin/peopledata/detail/${peopleCode}', null, {timestamp:time});
		
	});
	console.log('1');
	var theTime = parseInt('${date.time}');
	function appendHistory(history){
		if(history.length > 0){
			var $dl = $('#timeline-area dl', $page);
			
			for(var i in history){
				var item = history[i];
				var $month = $('dt[data-month="' + item.monthKey + '"]', $dl);
				if($month.length == 0){
					var month = new Date(item.monthKey);
					$month = $('<dt data-month="' + item.monthKey + '">').text(Utils.formatDate(month, 'yyyy年MM月'));
					var inserted = false;
					$('dt', $dl).each(function(){
						var thisMonth = parseInt($(this).attr('data-month'));
						if(thisMonth <= month){
							$month.insertBefore(this);
							inserted = true;
							return false;
						}
					});
					if(!inserted){
						$('.show-more-history', $page).parent('dt').before($month);
						//$dl.append($month);
					}
				}
				$item = $(
						'<dd class="pos-right clearfix">' +
							'<div class="circ"></div>' + 
							'<div class="time"></div>' +
							'<div class="events">' + 
		 						'<div class="events-header"></div>' + 
								'<div class="events-body"></div>' + 
							'</div>' +
						'</dd>');
				$item.find('.time').text(Utils.formatDate(new Date(item.timeKey), 'yyyy-MM-dd hh:mm:ss'));
				$item.find('.events-header').text('操作人：' + item.userName);
				$item.find('.events-body').text('详情');
				$item.attr('data-id', item.id).attr('data-time', item.timeKey);
				var inserted = false;
				var $dds = $month.nextUntil('dt');
				if($dds.length > 0){
					$dds.each(function(){
						var $this = $(this);
						if($this.is('dd[data-time]')){
							var thisTimeKey = parseInt($this.attr('data-time'));
							if(thisTimeKey <= item.timeKey){
								$item.insertBefore(this);
								inserted = true;
								return false;
							}
						}
					});
					if(!inserted){
						$dds.last().after($item);
					}
				}else{
					$month.after($item);
				}
			}
			var $dd = $('dd', $dl);
			var checked = false;
			$dd.each(function(i){
				var $this = $(this);
				if(!checked){
					var thisTime = parseInt($this.attr('data-time'));
					if(theTime >= thisTime){
						$this.addClass('current');
						checked = true;
					}
				}
				Utils.switchClass($this, 'pos-right', 'pos-left', i % 2 == 0);
				
			});
			
		}
	}
	
	
	$('#datetime', $page).datetimepicker({
		language	: 'zh-CN',
		format		: 'yyyy-mm-dd hh:ii:ss',
		minuteStep	: 1,
		autoclose	: true,
		startView	: 'day'
	}).on('changeDate', function(e){
		$page.getLocatePage().loadContent('admin/peopledata/detail/${peopleCode }', undefined, {
			datetime	: $(this).val()
		});
	});
	var $errors = $('#errors', $page);
	$('#showErrors', $page).mouseenter(function(e){
		$errors.show();
	});
	$page.click(function(e){
		var $target = $(e.target);
		if(!$target.is('#showErrors') && $target.closest('#errors').length == 0){
			$errors.hide();
		}
	});
});