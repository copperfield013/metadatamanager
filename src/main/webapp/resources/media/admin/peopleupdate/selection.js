; (function ($) {

    var initDefaults = {
        checkbox: false,                               //默认单选
        width: "100%",
        right: "0",
        lineList: false,                               //选项是否横向排列(checkbox形式)
        inforText: "--请选择--",                       //选择提示
        callBack: function () { },                    //创建完成回调函数
        clickCallBack: function () {       //选项点击回调函数            
        },
        notMatchText: "暂无该海南",                    //搜索无果提示
        items: [                                      //选项数组 (  [{ value: "option1",name:"选项一" }] ),如果是select初始化则不需要

        ],
        selected: []
    }
    var refreshDefaults = {
        callBack: function () { },      //刷新完成回调函数
    }

    $.fn.extend({
        //传参初始化
        Selection: function (options) {
            var options = $.extend({}, initDefaults, options);
            var self = this;            //实例化的父元素
            //移除
            self.remove = function () {
                var selection = this.find('.selection-container');
                selection.remove();
            }

            //刷新
            self.refresh = function (option) {
                if (self.length === 0) {
                    console.error("没有该元素");
                    return;
                }
                var selectionWrap = this.find('.selection-list');
                var lineListClass = "";
                if (this.find('.selection-list.linelist').length !== 0) {
                    lineListClass = "linelist";
                }
                var infortext = options.inforText;
                this.find('.option-close')
                    .removeClass('active').prev('span').text(infortext)
                    .parent('.selection-single').removeClass('hasoption');
                var option = $.extend({}, refreshDefaults, option);
                var url = option.url;
                var data = option.data;
                var callBack = option.callBack;
                var optionHtml = "";

                if (url === "" && data === "") {
                    return;
                }
                $.ajax({
                    url: url,
                    data: data,
                    dataType: 'jsonp',
                    type: "POST",
                    success: function (data) {
                        if (typeof (data) !== "undefined") {
                            var result = data.result;
                            try {
                                for (var i = 0; i < result.length; i++) {
                                    var id = checkUndefined(result[i].id) ? "" : result[i].id;
                                    var name = checkUndefined(result[i].name) ? "" : result[i].name;
                                    var parentid = checkUndefined(result[i].parentid) ? "" : result[i].parentid;
                                    var parentname = checkUndefined(result[i].parentname) ? "" : result[i].parentname;
                                    var areacode = checkUndefined(result[i].areacode) ? "" : result[i].areacode;

                                    optionHtml += "<li class='selection-option" + " " + lineListClass + "' data-id='" + id + "' data-name='" + name + "' data-parentid='" + parentid + "' data-parentname='" + parentname + "' data-areacode='" + areacode + "'>" + name + "</li>";
                                }
                            } catch (error) {
                                console.error(error);
                            }
                        }
                        selectionWrap.html(optionHtml);
                        callBack();
                    },
                    error(data) {
                        console.error(data);
                    }
                })


            }

            //获取选中项数据
            self.getData = function () {
                var selected = this.find(".selection-option.pitch-on");
                var id = selected.attr('data-id');
                var name = selected.attr('data-name');
                var parentid = selected.attr('data-parentid');
                var parentname = selected.attr('data-parentname');
                var areacode = selected.attr('data-areacode');
                return {
                    id: id,
                    name: name,
                    parentid: parentid,
                    parentname: parentname,
                    areacode: areacode
                };
            }

            //获取选中项文本
            self.getText = function () {
                var selected = this.find(".selection-option.pitch-on");
                var text = selected.text();
                if (checkUndefined(text)) {
                    text = "";
                }
                return text;
            }

            //清空选项列表
            self.empty = function () {
                if (self.length === 0) {
                    console.error("没有该元素");
                    return;
                }
                var infortext = options.inforText;
                this.find('.selection-list').html("")
                    .closest('.selection-container').find('.option-close')
                    .removeClass('active').prev('span').text(infortext)
                    .parent('.selection-single').removeClass('hasoption');
            }

            //禁用
            self.disable = function () {
                console.log(this);
                console.log(this.find('.selection-container'));
                this.css("cursor", "not-allowed");
                this.find('.selection-container').css("cursor", "not-allowed");
                this.find('.selection-button').css("backgroundColor", "");
            }

            /**
             * 下拉框打开关闭
             * option，关闭还是打开，默认关闭("close"),("open")
             */

            self.drop = function (option) {
                var _self = this;
                var selectionButton = _self.find('.selection-button');
                var selectionHeight = selectionButton.height();  //selection不包含下拉框的高度
                var selectHeight = selectionButton.parent('.selection-container').height();   //selection 整体高度
                var position = checkPostion(selectionButton);

                if (option === "open") {
                    if (position === "top") {
                        _self.find('.selection-container').addClass('drop-top').addClass('drop')
                            .find('.selection-drop').css({ 'bottom': selectHeight, 'top': "" });
                        return;
                    }
                    _self.find('.selection-container').removeClass('drop-top').addClass('drop')
                        .find('.selection-drop').css({ 'top': selectionHeight, 'bottom': "" });
                } else {            //其他参数也默认为关闭
                    if (position === "top") {
                        _self.find('.selection-container').addClass('drop-top').removeClass('drop')
                            .find('.selection-drop').css({ 'bottom': selectHeight, 'top': "" });
                        return;
                    }
                    _self.find('.selection-container').removeClass('drop-top').removeClass('drop')
                        .find('.selection-drop').css({ 'top': selectionHeight, 'bottom': "" });
                }
            }

            //值获取 
            self.getData = function($selection){
                var selectList = $selection.find('.selection-option.pitch-on');
                var result = [];
                for(var i=0; i<selectList.length; i++){
                    result.push(
                        {
                            value: $(selectList[i]).attr('data-value'),
                            name: $(selectList[i]).attr('data-name')
                        }
                    )
                }
                return result;
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
                var checkbox = options.checkbox;
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

                if (options.lineList) {                              //是否横向排列
                    lineListClass = "linelist";
                } else {
                    lineListClass = ""
                }
                for (var i = 0; i < domCount; i++) {                 //遍历判断对象是否为 select
                    var initHtml = "";                              //创建的组件html模板
                    var optionHtml = "";
                    var _thisELE = null;
                    if ($(self[i]).is('select')) {                  //页面select驱动初始化
                        $(self[i]).css("display","none");
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
                            initHtml = singleHTML(infortext, lineListClass, optionHtml, notMatchText, width, right);
                            $(self[i]).before(initHtml);
                            var name = selected[0].name;
                            var $selection = $(self[i]).prev();
                            singleAdd($selection, name)
                        } else {
                            initHtml = multipleHTML(infortext, lineListClass, optionHtml, notMatchText, width, right);
                            $(self[i]).before(initHtml);
                            var $selection = $(self[i]).prev();
                            var name = "";
                            for (var k = 0; k < selected.length; k++) {
                                name = selected[k].name;
                                multipleAdd($selection, name)
                            }
                        }

                        _thisELE = $(self[i]).prev('.selection-container');

                    } else {
                        for (var j = 0; j < items.length; j++) {        //单对象初始化(父元素)
                            var value = checkUndefined(items[j].value) ? "" : items[j].value;
                            var name = checkUndefined(items[j].name) ? "" : items[j].name;
                            optionHtml += "<li class='selection-option" + " " + lineListClass + "' data-value='" + value + "' data-name='" + name + "'>" + name + "</li>";
                        }
                        if (checkbox) {
                            initHtml = multipleHTML(infortext, lineListClass, optionHtml, notMatchText, width, right);
                            $(self[i]).append(initHtml);
                            if (selected.length !== 0) {                //有初始化的值
                                var $selection = $(self[i]).find('.selection-container');
                                var name = "";
                                for (var j = 0; j < selected.length; j++) {
                                    name = selected[j].name;
                                    multipleAdd($selection, name)
                                }
                            }
                        } else {
                            initHtml = singleHTML(infortext, lineListClass, optionHtml, notMatchText, width, right);
                            $(self[i]).append(initHtml);
                            if (selected.length !== 0) {   //有初始化的值
                                var $selection = $(self[i]).find('.selection-container');
                                var name = selected[0].name;
                                singleAdd($selection, name);
                            }
                        }
                        _thisELE = $(self[i]).find('.selection-container');
                    }

                    //绑定事件
                    bindSingleEvent(_thisELE, clickCallBack);

                    if (typeof (callBack) === "function") {
                        callBack();
                    }



                }
            };


            // 单选HTML模板
            var singleHTML = function (infortext, lineListClass, optionHtml, notMatchText, width, right) {
                var singleHtml = "<div  class='selection-container  selection-container-single' style='width:" + width + ";margin-right:" + right + "'>"
                    + "<div class='selection-disabled'></div>"
                    + "<div class='selection-single selection-button'>"
                    + "<span>" + infortext + "</span>"
                    + "</div>"
                    + "<div class='selection-drop'>"
                    + "<ul class='selection-list" + " " + lineListClass + "'>"
                    + optionHtml
                    + "<li class='selection-search-empty-li'>" + notMatchText + "</li>"
                    + '</ul>'
                    + '</div>'
                    + '</div> ';
                return singleHtml;
            }

            // 多选HTML模板
            var multipleHTML = function (infortext, lineListClass, optionHtml, notMatchText, width, right) {
                var multipleHtml = "<div class='selection-container selection-container-multiple' style='width:" + width + ";margin-right:" + right + "'>"
                    + "<div class='selection-disabled'></div>"
                    + "<div class='selection-multiple selection-button'>"
                    + "<span class='option-container'>"
                    + "<span class='option-placeholder'>" + infortext + "</span>"
                    + "</span>"
                    + "</div>"
                    + "<div class='selection-drop'>"
                    + "<ul class='selection-list" + " " + lineListClass + "'>"
                    + optionHtml
                    + "<li class='selection-search-empty-li'>" + notMatchText + "</li>"
                    + '</ul>'
                    + '</div>'
                    + '</div> ';
                return multipleHtml;
            }

            // 事件添加
            var bindSingleEvent = function (selection, clickCallBack) {

                $(selection).on('click', '.selection-option', function (e) {
                    e.stopPropagation();
                    var _thisELE = this;
                    var isSingle = $(_thisELE).closest('.selection-container').hasClass('selection-container-single')  //判断是否是单选
                    if (isSingle) {
                        singleChose(_thisELE, clickCallBack);
                    } else {
                        multipleChose(_thisELE, clickCallBack);
                    }
                });

                $(selection).find('.selection-disabled').on('click', function (e) {
                    e.stopPropagation();
                })
                $(selection).find('.selection-button').on('click', dropMenu);

                $(selection).find('.option-container').on('click', '.close-one-option', function (e) {
                    e.stopPropagation();  //阻止冒泡
                    var _thisELE = this;
                    deleteOption(_thisELE);
                })
                // $(document).on('click', function (e) {
                //     close(e);
                // });
            }


            // 单选选项点击
            var singleChose = function (target, callBack) {
                var name = $(target).text();
                var $selection = $(target).closest('.selection-container');
                var $select = $selection.next('select');
                singleAdd($selection, name);
                amendSelect($select, name, "add")
                if (typeof (callBack) === "function") {
                    callBack();
                }
            }

            //多选 选项点击
            var multipleChose = function (target, callBack) {
                var isChose = $(target).hasClass('pitch-on');       //true 代表已选中 false代表还没选中
                var $selection = $(target).closest('.selection-container');
                var $select = $selection.next('select');
                var name = $(target).attr('data-name');
                //选中和取消选中
                if (isChose) {  // 取消选中

                    multipleDelete($selection, name);
                    amendSelect($select, name, "delete")

                } else {         //选中
                    multipleAdd($selection, name);
                    amendSelect($select, name, "add")
                }

                if (typeof (callBack) === "function") {
                    callBack();
                }
            }

            //多选 标签选项删除按钮点击
            var deleteOption = function (target) {
                var $selection = $(target).closest('.selection-container');
                var $select = $selection.next('select')
                var name = $(target).closest('.multiple-tag').attr('title');
                multipleDelete($selection, name);
                amendSelect($select, name, "delete")
            }

            //单选选择方法
            var singleAdd = function ($selection, name) {
                var $option = $selection.find('.selection-option[data-name="' + name + '"]');  //对应的选项
                var value = $option.attr('data-value');
                if ($option.hasClass('pitch-on')) {
                    $option.closest('.selection-container').removeClass('drop');
                    return;
                }
                $option.addClass('pitch-on').siblings('.selection-option').removeClass('pitch-on')
                    .closest('.selection-container').removeClass('drop').find('.selection-button>span').text(name)
                    .closest('.selection-single').addClass('hasoption');
            }

            

            /**
             * 多选 删除选项方法
             * @param {*} $selection  当前下拉框
             * @param {*} name        选项文字
             */
            var multipleDelete = function ($selection, name) {

                var $option = $selection.find('.selection-option[data-name="' + name + '"]');  //对应的选项
                var $tag = $selection.find('.multiple-tag[title="' + name + '"]');  //对应的tag

                $option.removeClass('pitch-on');
                $tag.css({
                    "opacity": "0",
                    "transform": "scale(0,0)"
                });
                var removeTag = window.setTimeout(function () {
                    $tag.remove();
                    //如果没有tag则显示placeholder
                    if ($selection.closest('.selection-container').find('.multiple-tag').length === 0) {
                        console.log(1);
                        $selection.closest('.selection-container').find('.option-placeholder').removeClass('hide');
                    }
                }, 300)
                removeTag = null;

            }


            /**
             * 多选 添加选项方法
             * @param {*} $selection  当前下拉框
             * @param {*} name        选项文字
             */
            var multipleAdd = function ($selection, name) {

                var $option = $selection.find('.selection-option[data-name="' + name + '"]');  //对应的选项

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
                for (var i = 0; i < optionList.length; i++) {
                    if ($(optionList[i]).text() === name) {
                        status == "add" ?
                            $(optionList[i]).prop("selected", true) :
                            $(optionList[i]).prop("selected", false)

                    }
                }
                // $select.find('option[text="'+name+'"]').attr("selected",true)
            }


            // // 下拉框关闭
            // var close = function (e, index) {
            //     if ($(e.target).closest('.selection-container').length !== 0) {
            //         return;
            //     };
            //     var allSelect = $('.selection-container');
            //     for (var i = 0; i < allSelect.length; i++) {
            //         $(allSelect[i]).removeClass('drop');
            //     }

            // }

            // 下拉框 点击展开收缩
            var dropMenu = function (e) {		            	
                e.stopPropagation();
                var windowHeight = $(window).height(); 
                var selectionHeight = $(this).height();                               //selection不包含下拉框的高度
                var selectHeight = $(this).parent('.selection-container').height();   //selection 整体高度
                var position = checkPostion($(this));
               /* var offset = $(this).parent('.selection-container').offset();
                var bottomHeight = windowHeight - offset.top - selectionHeight;  //触发元素距离底部的距离
                var	selectionLeft = offset.left;
    			var selectionTop;
    			if( bottomHeight > selectHeight ){
    				selectionTop = offset.top + selectionHeight;	
    				$(this).parent('.selection-container').removeClass('bottom');
    			}else {
    				pickerTop = offset.top - selectionHeight;
    				$(this).parent('.selection-container').addClass('bottom');
    			}
    			this.picker.css({
    				top: pickerTop,
    				left: pickerLeft
    			});*/
                if ($(this).parent('.selection-container').hasClass('drop')) {
                    $(this).parent('.selection-container').removeClass('drop');
                    return;
                }
                var allSelect = $('.selection-container');
                for (var i = 0; i < allSelect.length; i++) {
                    $(allSelect[i]).removeClass('drop');
                }
                $(this).parent('.selection-container').addClass('drop');
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