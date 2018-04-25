; (function ($) {

    var initDefaults = {
        width: "100%",
        right: "0",
        lineList: false,                               //选项是否横向排列
        inforText: "--请选择1--",                        //选择提示
        callBack: function () { },                     //创建完成回调函数
        clickCallBack: function () {       			   //选项点击回调函数            
        },
        notMatchText: "暂无该海南",                        //搜索无果提示
        items: [                                       //选项数组 (  [{ value: "option1",name:"选项一" }] ),如果是select初始化则不需要

        ],
        selected: [],
        scrollEle: 'body'
    }

    $.fn.extend({
        //初始化
        Selection: function (options) {
            var options = $.extend({}, initDefaults, options);
            var self = this;            //实例化的父元素
            //移除
            self.remove = function () {
                var $selection,
                    $select,
                    soleId,
                    $drop;
                for (var i = 0; i < self.length; i++) {
                    $selection = $(self[i]).prev('.selection-container');
                    $select = $selection.next('select');    //找到对应的select
                    soleId = $selection.attr("soleId");
                    $drop = $('.selection-drop[soleId=' + soleId + ']');
                    $select.remove();
                    $selection.remove();
                    $drop.remove();
                }
            }
            
            //隐藏
            self.hidden = function() {
            	var $selection,
	                $select,
	                soleId,
	                $drop;
	            for (var i = 0; i < self.length; i++) {
	                $selection = $(self[i]).prev('.selection-container');
	                $select = $selection.next('select');    //找到对应的select
	                soleId = $selection.attr("soleId");
	                $drop = $('.selection-drop[soleId=' + soleId + ']');
	                
	                $selection.css("display","none")
	            }
            }
            //显示
            self.show = function() {
            	var $selection,
	                $select,
	                soleId,
	                $drop;
	            for (var i = 0; i < self.length; i++) {
	                $selection = $(self[i]).prev('.selection-container');
	                $select = $selection.next('select');    //找到对应的select
	                soleId = $selection.attr("soleId");
	                $drop = $('.selection-drop[soleId=' + soleId + ']');
	                
	                $selection.css("display","inline-block")	               
	            }
            }

            //重载(依据select重新加载选项,同时清空多选展示列表除标签,单选默认第一项)
            self.reload = function () {
                var optionHtml = "",
                    $selection,
                    $select,
                    $soleId,
                    $drop,
                    optionList;
                for (var i = 0; i < self.length; i++) {
                    $selection = $(self[i]).prev('.selection-container');
                    $select = $selection.next('select');    //找到对应的select
                    soleId = $selection.attr("soleId");
                    $drop = $('.selection-drop[soleId=' + soleId + ']');
                    optionList = $select.find('option');
                    for (var j = 0; j < optionList.length; j++) {
                        lineListClass = "";
                        var value = checkUndefined($(optionList[j]).prop("value")) ? "" : $(optionList[j]).prop("value");
                        var name = checkUndefined($(optionList[j]).text()) ? "该选项缺失" : $(optionList[j]).text();
                        optionHtml += "<li class='selection-option" + " " + lineListClass + "' data-value='" + value + "' data-name='" + name + "'>" + name + "</li>";
                    }
                    $drop.find('.selection-list ').html(optionHtml);
                    if( checkUndefined($select.attr('multiple')) ){  //单选
                        var text = $(optionList[0]).text();
                        $selection.find('.selection-button span').text(text);
                    }else {
                        $selection.find('.multiple-tag').remove();
                        $selection.find('.option-placeholder').removeClass('hide');
                    }
                }
            }

            //获取选中项数据(无意义 ，可以直接获取select的值就ok)

            //toggle禁用状态(暂时不写上去)
            self.disable = function () {

            }

            //  初始化分为单选和多选两种情况
            var render = function (options) {
                if (self.length === 0) {
                    console.error("没有该元素");
                    return;
                }
                if (self.find('.selection-container').length !== 0) {
                    console.error("该元素已经实例化");
                    return;
                }
                var infortext = options.inforText;
                var lineListClass = "";
                var callBack = options.callBack;
                var clickCallBack = options.clickCallBack;
                var width = options.width;
                var right = options.right;
                var notMatchText = options.notMatchText;
                var items = options.items;
                var domCount = self.length;                          //需要实例化的对象数量  
                var selected = options.selected;
                var scrollEle = options.scrollEle;

                if (options.lineList) {                              //是否横向排列
                    lineListClass = "linelist";
                } else {
                    lineListClass = ""
                }
                for (var i = 0; i < domCount; i++) {                 //遍历判断对象是否为 select
                    var initHtml = "";                               //创建的组件html模板
                    var optionHtml = "";
                    var dropHtml = "";
                    var selectOption = "";
                    var _thisELE = null;
                    var _thisDROP = null;

                    if (!$(self[i]).is('select')) {
                        console.error('触发元素需为select');
                        return;
                    }
                    $(self[i]).css("display", "none");
                    if (items.length === 0) {                  //页面select选项 驱动初始化
                        selected = [];                              //清空selected
                        var selectedList = $(self[i]).find("option:selected");
                        for (var j = 0; j < selectedList.length; j++) {
                            selected.push({
                                value: $(selectedList[j]).prop('value'),
                                name: $(selectedList[j]).text()
                            });
                        }
                        var optionList = $(self[i]).find('option');
                        for (var j = 0; j < optionList.length; j++) {
                            var value = checkUndefined($(optionList[j]).prop("value")) ? "" : $(optionList[j]).prop("value");
                            var name = checkUndefined($(optionList[j]).text()) ? "该选项缺失" : $(optionList[j]).text();
                            optionHtml += "<li class='selection-option" + " " + lineListClass + "' data-value='" + value + "' data-name='" + name + "'>" + name + "</li>";
                        }
                        if (checkUndefined($(self[i]).attr('multiple'))) {
                            initHtml = singleHTML(infortext, width, right);
                            _thisELE = $(initHtml).insertBefore($(self[i]));  //当前元素
                            dropHtml = dropHTML(lineListClass, optionHtml, notMatchText, "");
                            _thisDROP = $(dropHtml).appendTo('body');
                            if (selected.length !== 0) {
                                var name = selected[0].name;
                                singleAdd(_thisELE, _thisDROP, name)                              
                            }                           
                        } else {
                            initHtml = multipleHTML(infortext, width, right);
                            _thisELE = $(initHtml).insertBefore($(self[i]));
                            dropHtml = dropHTML(lineListClass, optionHtml, notMatchText, "multiple");
                            _thisDROP = $(dropHtml).appendTo('body');
                            var name = "";
                            if(selected.length !==0 ){
                                for (var k = 0; k < selected.length; k++) {
                                    name = selected[k].name;
                                    multipleAdd(_thisELE, _thisDROP, name)
                                }
                            }
                        }
                    } else {
                        for (var j = 0; j < items.length; j++) {        //页面select items选项驱动初始化
                            var value = checkUndefined(items[j].value) ? "" : items[j].value;
                            var name = checkUndefined(items[j].name) ? "" : items[j].name;
                            optionHtml += "<li class='selection-option" + " " + lineListClass + "' data-value='" + value + "' data-name='" + name + "'>" + name + "</li>";
                            selectOption += "<option value='" + value + "'>" + name + "</option>"
                        }
                        if (!checkUndefined($(self[i]).attr('multiple'))) {
                            initHtml = multipleHTML(infortext, width, right);
                            $(self[i]).append(selectOption);
                            _thisELE = $(initHtml).insertBefore($(self[i]));
                            dropHtml = dropHTML(lineListClass, optionHtml, notMatchText, "multiple");
                            _thisDROP = $(dropHtml).appendTo('body');
                            if (selected.length !== 0) {                //有初始化的值
                                var name = "";
                                for (var j = 0; j < selected.length; j++) {
                                    name = selected[j].name;
                                    multipleAdd(_thisELE, _thisDROP, name);
                                    amendSelect($(self[i]), name, "add")
                                }
                            }
                        } else {
                            initHtml = singleHTML(infortext, width, right);
                            $(self[i]).append(selectOption);
                            _thisELE = $(initHtml).insertBefore($(self[i]));
                            dropHtml = dropHTML(lineListClass, optionHtml, notMatchText, "");
                            _thisDROP = $(dropHtml).appendTo('body');
                            if (selected.length !== 0) {   //有初始化的值
                                var name = selected[0].name;
                                singleAdd(_thisELE, _thisDROP, name);
                                amendSelect($(self[i]), name, "add")
                            }
                        }
                    }
                    var soleId = Number(Math.random().toString().substr(2, 8) + Date.now()).toString(36);  //container和drop相同的一个属性值
                    _thisELE.attr("soleId", soleId);
                    _thisDROP.attr("soleId", soleId);
                    $(scrollEle).on('scroll',function(){                        
                        var offset = _thisELE.offset();
                        console.log(offset);
                        //判断生成的drop 是在触发元素的上方还是下方
                        if(_thisDROP.hasClass('bottom')){
                            _thisDROP.css({
                                top: offset.top + _thisELE.height()
                            })
                        }else {            
                            console.log(offset.top);
                            console.log(_thisELE.height());
                            _thisDROP.css({
                                top: offset.top - _thisDROP.height() -4,
                            })
                        }
                    })

                    //绑定事件
                    bindSingleEvent(_thisELE, _thisDROP, clickCallBack);

                    if (typeof (callBack) === "function") {
                        callBack();
                    }



                }
            };
            var place = function ($selection, $drop) {
                var windowHeight = $(window).height();
                var offset = $selection.offset();
                var bottomHeight = windowHeight - offset.top - $selection.height();  //触发元素距离底部的距离
                var eleHeight = $drop.height();
                var left = offset.left;
                var top;    
                if (bottomHeight > eleHeight) {
                    top = offset.top + $selection.height();
                    $drop.addClass('bottom').removeClass('top');
                } else {
                    top = offset.top - eleHeight-4;
                    $drop.addClass('top').removeClass('bottom');
                }
                $drop.css({
                    top: top,
                    left: left
                });
            }

            var width = function($selection,$drop) {
                var width = $selection.width();
                $drop.css("width",width);
            }
            // 单选HTML模板
            var singleHTML = function (infortext, width, right) {
                var singleHtml = "<div  class='selection-container  selection-container-single' style='width:" + width + ";margin-right:" + right + "'>"
                    + "<div class='selection-disabled'></div>"
                    + "<div class='selection-single selection-button'>"
                    + "<span>" + infortext + "</span>"
                    + "</div>"
                    + '</div> ';
                return singleHtml;
            }

            // 多选HTML模板
            var multipleHTML = function (infortext, width, right) {
                var multipleHtml = "<div class='selection-container selection-container-multiple' style='width:" + width + ";margin-right:" + right + "'>"
                    + "<div class='selection-disabled'></div>"
                    + "<div class='selection-multiple selection-button'>"
                    + "<span class='option-container'>"
                    + "<span class='option-placeholder'>" + infortext + "</span>"
                    + "</span>"
                    + "</div>"
                    + '</div> ';
                return multipleHtml;
            }

            // 下拉boxHTML模板
            var dropHTML = function (lineListClass, optionHtml, notMatchText, multipleClass) {
                var dropHtml = "<div class='selection-drop" + " " + multipleClass + "'>"
                    + "<ul class='selection-list" + " " + lineListClass + "'>"
                    + optionHtml
                    + "<li class='selection-search-empty-li'>" + notMatchText + "</li>"
                    + '</ul>'
                    + '</div>'
                return dropHtml;
            }

            //select模板
            var selectHTML = function () {
                var selectHtml = ""
            }

            // 事件添加
            var bindSingleEvent = function ($selection, $drop, clickCallBack) {
                $drop.on('click', '.selection-option', function (e) {
                    e.stopPropagation();
                    var $option = $(this);
                    var isSingle = $selection.hasClass('selection-container-single')  //判断是否是单选
                    if (isSingle) {
                        singleChose($selection, $option, clickCallBack);
                    } else {
                        multipleChose($selection, $option, clickCallBack);
                    }
                });

                $selection.find('.selection-disabled').on('click', function (e) {
                    e.stopPropagation();
                })
                $selection.find('.selection-button').on('click', function (e) {
                    e.stopPropagation();
                    dropMenu($selection, $drop);
                });

                $selection.find('.option-container').on('click', '.close-one-option', function (e) {
                    e.stopPropagation();  //阻止冒泡
                    var $target = $(this);
                    deleteOption($selection, $target, $drop);
                })
                // $(document).on('click', function (e) {
                //     close(e);
                // });
            }
            
            //body绑定事件 关闭所有展开的selection
            $("body").on('click',function(){
            	var allSelect = $('.selection-container');
                var allDrop = $('.selection-drop')
                allSelect.removeClass('drop');
                allDrop.removeClass('drop');          
            })
            

            // 单选选项点击
            var singleChose = function ($selection, $option, callBack) {
                var name = $option.text();
                var $select = $selection.next('select');
                var $drop = $option.closest('.selection-drop ');
                singleAdd($selection, $drop, name);
                amendSelect($select, name, "add")
                if (typeof (callBack) === "function") {
                    callBack();
                }
            }

            //多选 选项点击
            var multipleChose = function ($selection, $option, callBack) {
                var isChose = $option.hasClass('pitch-on');       //true 代表已选中 false代表还没选中
                var $select = $selection.next('select');
                var name = $option.attr('data-name');
                var $drop = $option.closest('.selection-drop ');
                //选中和取消选中
                if (isChose) {  // 取消选中

                    multipleDelete($selection, $drop, name);
                    amendSelect($select, name, "delete")

                } else {         //选中
                    multipleAdd($selection, $drop, name);
                    amendSelect($select, name, "add")
                }

                if (typeof (callBack) === "function") {
                    callBack();
                }
            }

            //多选 标签选项删除按钮点击
            var deleteOption = function ($selection, $target, $drop) {
                var $select = $selection.next('select')
                var name = $target.closest('.multiple-tag').attr('title');
                multipleDelete($selection, $drop, name);
                amendSelect($select, name, "delete");
            }

            //单选选择方法
            var singleAdd = function ($selection, $drop, name) {
                var $option = $drop.find('.selection-option[data-name="' + name + '"]');  //对应的选项
                var value = $option.attr('data-value');
                if ($option.hasClass('pitch-on')) {
                    $selection.removeClass('drop');
                    $drop.removeClass('drop');
                    return;
                }
                $option.addClass('pitch-on').siblings('.selection-option').removeClass('pitch-on');
                $drop.removeClass('drop');
                $selection.removeClass('drop').addClass('hasoption').find('.selection-button>span').text(name);
            }



            /**
             * 多选 删除选项方法
             * @param {*} $selection  当前下拉框
             * @param {*} name        选项文字
             */
            var multipleDelete = function ($selection, $drop, name) {

                var $option = $drop.find('.selection-option[data-name="' + name + '"]');  //对应的选项
                var $tag = $selection.find('.multiple-tag[title="' + name + '"]');  //对应的tag

                $option.removeClass('pitch-on');
                $tag.css({
                    "opacity": "0",
                    "transform": "scale(0,0)"
                });
                var removeTag = window.setTimeout(function () {
                    $tag.remove();
                    //如果没有tag则显示placeholder
                    if ($selection.find('.multiple-tag').length === 0) {
                        $selection.find('.option-placeholder').removeClass('hide');
                    }
                }, 300)
                removeTag = null;

            }


            /**
             * 多选 添加选项方法
             * @param {*} $selection  当前下拉框
             * @param {*} name        选项文字
             */
            var multipleAdd = function ($selection, $drop, name) {

                var $option = $drop.find('.selection-option[data-name="' + name + '"]');  //对应的选项

                $option.addClass('pitch-on');

                var optionHtml = "<span class='multiple-tag' title='" + name + "'>" +       //拼接需要展示在展示框内的html片段
                    "<em>" + name + "</em >" +
                    "<b class='close-one-option'></b>" +
                    "</span>";
                $selection.find('.option-container').append(optionHtml)     // 选中标签html片段插入
                    .find('.option-placeholder').addClass('hide');                                          // 使得placeholder隐藏
            }

            /**
             * 修改对应select选中项 
             * @param {*} $select     对应的原生select
             * @param {*} name        对应选项的text文本值
             * @param {*} status      选中还是取消选中，单选只有选中 "add","delete"
             */
            var amendSelect = function ($select, name, status) {
                var optionList = $select.find('option');
                var isSingle = $select.attr("multiple");
                if(typeof(isSingle) === "undefined"){ //单选
                	for (var i = 0; i < optionList.length; i++) {
                		$(optionList[i]).text() === name ?                       
                             $(optionList[i]).prop("selected", true).attr("selected",true):   //js节点和dom节点同时转换,避免视觉混淆
                        	 $(optionList[i]).prop("selected", false).attr("selected",false)

                    }
                	return;
                };
                for (var i = 0; i < optionList.length; i++) {
                    if ($(optionList[i]).text() === name) {
                        status == "add" ?
                            $(optionList[i]).prop("selected", true).attr("selected",true) :
                            $(optionList[i]).prop("selected", false).attr("selected",false)
                    }
                }
            }

            // 下拉框 点击展开收缩
            var dropMenu = function ($selection, $drop) {
                width($selection,$drop);
                place($selection,$drop);
                if ($selection.hasClass('drop')) {
                    $selection.removeClass('drop');
                    $drop.removeClass('drop');
                    return;
                }
                var allSelect = $('.selection-container');
                var allDrop = $('.selection-drop')
                allSelect.removeClass('drop');
                allDrop.removeClass('drop');                
                $selection.addClass('drop');
                $drop.addClass('drop');
            }

            // 检测变量是否为undefined
            var checkUndefined = function (string) {
                return (typeof (string) === "undefined");
            }



            //判断下拉框展开位置，输出"bottom"则为向下，输出“top”则为向上 输入参数为selectbutton Jquery对象
            var checkPostion = function ($selectionButton) {
                var $this = $selectionButton;
                var topDistance = $this.parent('.selection-container').offset().top;  //元素距离顶部的距离
                var scrollTop = $(window).scrollTop();  //网页被卷起高度
                var viewHeight = $(window).height(); //可视区域高度
                var selectionHeight = $this.height();
                var selectHeight = $this.parent('.selection-container').height();   //selection 整体高度
                var viewTop = topDistance - scrollTop;  //元素距离可视区域顶部距离
                var viewBottom = viewHeight - viewTop - selectHeight;    // selection距离可视区域底部距离
                var dropHeight = $this.parent('.selection-container').find('.selection-drop').height();
                if (dropHeight >= viewBottom) {
                    return "top";
                } else {
                    return "bottom";
                }
            }

            render(options);
            return self;
        }


    })

    // $("script").last().each(function () {
    //     var getRootPath = function () {
    //         var curWwwPath = window.document.location.href;
    //         var pathName = window.document.location.pathname;
    //         var pos = curWwwPath.indexOf(pathName);
    //         var localhostPaht = curWwwPath.substring(0, pos);
    //         var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    //         return localhostPaht + projectName;
    //     };
    //     var root = getRootPath() + "/";
    //     var path = $(this).attr("src");
    //     path = path.substr(0, path.lastIndexOf('/') + 1);
    //     $("<link>").attr({
    //         rel: "stylesheet",
    //         type: "text/css",
    //         href: root + path + "selection.css"
    //     }).insertAfter($(this));
    // });
})(jQuery);