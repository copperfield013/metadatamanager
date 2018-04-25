'use strict';
;(function (window, document) {

    var SWinput;
    var VERSION = '1.0.0';
    var swinputMap = {}; //保存所有SWinput对象

    SWinput = function (pt, options) {
        //pt  容器元素
        var me = this;

        me.wrapper = typeof pt === 'string' ? document.querySelector(pt) : pt;

        this._init(me.wrapper, options);
    };

    SWinput.version = VERSION;

    SWinput.prototype = {
        //初始化
        _init: function _init(wrapper, options) {

            var me = this;

            //实例化的包裹层
            me.warpper = wrapper;

            //创建ID
            me.id = options && options.id || Number(Math.random().toString().substr(2, 8) + Date.now()).toString(36);

            //默认选项
            me.options = {
                type: "1",
                title_en: "text",
                title: "defaultBox"

            };

            //参数和并
            for (var i in options) {
                me.options[i] = options[i];
            }

            //document事件监听
            //类型筛选
            switch (me.options.type) {
                case "1":
                    me.inputCallback(me.wrapper, me.options, me.id);
                    break;           
                case "3":
                	me.selectCallback(me.wrapper, me.options, me.id);                   
                    break;
                case "7":
                	me.textareaCallback(me.wrapper, me.options, me.id);
                    break;
                default:
                    break;
            }
        },

        //input类型render 并且添加事件监听
        inputCallback: function inputCallback(wrapper, options, id) {
            var me = this;
            var regular = options.check_rule;
            var title = options.title;
            var SWid = id;
            var _thisELE = null; //当前元素
            var _thisINPUT = null; //
            var value = options.a_value ? options.a_value : "";
            var name = options.title_en;
           
            var html = '<div class="datacenter-swinput datacenter-input" sw_id = ' + SWid + '>\n  <label>' + title + '</label>\n<input class="basic-slide " type="text" data-type="' + regular + '" autocomplete="off" name="'+name+'">\n</div>';

            var checkInput = function checkInput(target) {
                var target = target;
                var hasValue = target.value !== ""; //当前input是否有值
                var value = ""; //input的值
                var vaild = false; //值是否合法有效

                if (!hasValue) {
                    target.previousElementSibling.classList.remove("active");
                    target.classList.remove("vaild");
                    target.classList.remove("invaild");
                } else {
                    target.previousElementSibling.classList.add("active");
                    value = target.value;
                    vaild = me.regular(regular, value);
                    if (vaild) {
                        target.classList.remove("invaild");
                        target.classList.add("vaild");
                    } else {
                        target.classList.remove("vaild");
                        target.classList.add("invaild");
                    }
                }
            };

            wrapper.insertAdjacentHTML('beforeend', html); //后期加入错误处理机制

            _thisELE = document.querySelector('div[sw_id=\'' + SWid + '\']');
            _thisINPUT = document.querySelector('div[sw_id=\'' + SWid + '\']>input');

            _thisINPUT.setAttribute("value", value); //初始化放值
            checkInput(_thisINPUT);

            //添加事件监听
            _thisINPUT.addEventListener('focus', function (evt) {
                var target = evt.target;
                target.previousElementSibling.classList.add("active");
            }, false);

            _thisINPUT.addEventListener('blur', function (evt) {
                var target = evt.target;
                checkInput(target);
            }, false);

            //保存SWinput对象；
            me.swinput = _thisELE;
            swinputMap[me.id] = me;
        },

        //正则验证
        regular: function regular(_regular, value) {

            function checkEmail(email) {
                // 邮箱验证方法
                console.log('reg email');
                var reg = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
                var re = new RegExp(reg);
                var result = false;
                re.test(email) ? result = true : result = false;
                return result;
            }

            function checkIdentity(identity) {
                //  身份证号码验证
                console.log('reg identity');
                // 15位数身份证正则表达式
                var reg1 = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
                // 18位数身份证正则表达式
                var reg2 = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Z])$/;
                if (identity.match(reg1) == null && identity.match(reg2) == null) {
                    return false;
                } else {
                    return true;
                }
            }

            function checkMobiles(mobiles) {
                //  手机验证
                console.log('reg mobiles');
                var reg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
                var re = new RegExp(reg);
                var result = false;
                re.test(mobiles) ? result = true : result = false;
                return result;
            }

            switch (_regular) {//验证规则选定
                case "1":
                    return checkEmail(value);
                case "2":
                    return checkIdentity(value);
                case "3":
                    return checkMobiles(value);
                default:
                    return true;
            }
        },

        //textarea类型render 同时添加事件监听 没有正则验证  支持 高度自适应内容
        textareaCallback: function textareaCallback(wrapper, options, id) {
            var me = this;
            var title = options.title;
            var SWid = id;
            var _thisELE = null; //当前元素
            var _thisTEXTAREA = null; //当前插入的元素中的textarea元素
            var hiddenDiv = null; //对应的div
            var html = '<div class="datacenter-swinput datacenter-input" sw_id = ' + SWid + '>\n                            <textarea class="basic-slide"></textarea>\n                            <label>' + title + '</label>\n                        </div>';
            var hiddenHtml = '<div class="hiddendiv" sw_id = ' + SWid + ' ></div>';

            wrapper.insertAdjacentHTML('beforeend', html); //后期需要加入错误处理机制
            document.body.insertAdjacentHTML('beforeend', hiddenHtml);

            _thisELE = document.querySelector('div[sw_id=\'' + SWid + '\']');
            _thisTEXTAREA = document.querySelector('div[sw_id=\'' + SWid + '\']>textarea');

            //设置hiddenDIV
            hiddenDiv = document.querySelector('div.hiddendiv[sw_id=\'' + SWid + '\']');
            hiddenDiv.style.width = window.getComputedStyle(_thisELE.querySelector('textarea')).width;

            //添加事件监听
            _thisTEXTAREA.addEventListener('focus', function (evt) {
                var target = evt.target;
                target.nextElementSibling.classList.add("active");
            }, false);

            _thisTEXTAREA.addEventListener('blur', function (evt) {
                var target = evt.target;
                var hasValue = target.value !== ""; //当前input是否有值
                var value = ""; //input的值

                if (!hasValue) {
                    target.nextElementSibling.classList.remove("active");
                }
            }, false);

            _thisTEXTAREA.addEventListener('input', function (evt) {
                var target = evt.target;
                var height = parseFloat(window.getComputedStyle(target).height); //textarea现有高度  
                var minHeight = parseFloat(window.getComputedStyle(target).minHeight); //textarea最小高度
                var targetValue = target.value;
                //内容放置到对应的div
                hiddenDiv.textContent = targetValue;
                var hiddenHeight = parseFloat(window.getComputedStyle(hiddenDiv).height);

                if (hiddenHeight > height || hiddenHeight < height && height > minHeight) {
                    target.style.height = hiddenHeight + "px";
                }
            }, false);

            //保存SWinput对象；
            me.swinput = _thisELE;
            swinputMap[me.id] = me;
        },

        //select类型render 添加相应的事件监听 无正则验证 只提供单选  多选请用checkbox
        selectCallback: function selectCallback(wrapper, options, id) {
            var me = this;
            var option = options.fileList;
            var SWid = id;
            var _thisELE = null;
            var _thisINPUT = null;
            var _thisSelect = null;
            var _thisList = null;
            var optionHtml = "";
            var selectHtml = "";
            var title = "";
            var value = "";
            var name = options.name;
            var checked = options.checked;
            for (var i = 0; i < option.length; i++) {
                title = option[i].title;
                value = option[i].value;
                optionHtml += '<li class="" data-index = ' + (i + 1) + ' data-value= "'+value+'">' + title + '</li>';

                selectHtml += '<option value="' + value + '">' + title + '</option>';
            }
            var html = '<div class="datacenter-select" sw_id="' + SWid + '">\n                            <span class="down-icon">▼</span>\n                            <input type="text" class="select-dropdown" readonly="true" value="选择一个选项">\n                            <ul class="dropdown-content">\n                                <li class="disabled active">选择一个选项</li>  \n                                ' + optionHtml + '                              \n                            </ul>\n                            <select class="initialized" name="'+name+'">\n                                <option value="" disabled selected>选择一个选项</option>\n                                ' + selectHtml + '\n                            </select>\n                        </div>';

            wrapper.insertAdjacentHTML('beforeend', html); //后期需要加入错误处理机制

            _thisELE = document.querySelector('div[sw_id=\'' + SWid + '\']');
            _thisINPUT = document.querySelector('div[sw_id=\'' + SWid + '\']>input');
            _thisList = document.querySelector('div[sw_id=\'' + SWid + '\']>ul');
            _thisSelect = document.querySelector('div[sw_id=\'' + SWid + '\']>select');

            //事件监听
            _thisINPUT.addEventListener("click", function (evt) {
                //展开，同时判断向上展开还是向下展开
                var target = evt.target;
                // var topDistance = $(this).parent('.selection-container').offset().top;  //元素距离顶部的距离
                // var scrollTop = $(window).scrollTop();  //网页被卷起高度
                // var viewHeight = $(window).height(); //可视区域高度
                // var selectionHeight = $(this).height();
                // var selectHeight = $(this).parent('.selection-container').height();   //selection 整体高度
                // var viewTop = topDistance - scrollTop;  //元素距离可视区域顶部距离
                // var viewBottom = viewHeight - viewTop - selectHeight;    // selection距离可视区域底部距离
                // var dropHeight = $(this).parent('.selection-container').find('.selection-drop').height();
                _thisList.classList.add("active");
            }, false);

            _thisList.addEventListener("click", function (evt) {
                var target = evt.target;
                var allOption = target.parentNode.children;
                var index = 1;
                var text = "";
                if (target.classList.contains('disabled')) {
                    _thisList.classList.remove("active");
                    return;
                }
                for (var i = 0; i < allOption.length; i++) {
                    allOption[i].classList.remove("active");
                }
                _thisList.classList.remove("active");
                target.classList.add('active');

                //设置inputvalue
                text = target.textContent;
                _thisINPUT.setAttribute('value', text);

                //设置select对应选项
                index = target.getAttribute('data-index');
                _thisSelect.children[index].selected = true;
            }, false);
            //如果有值
            if(checked !== ""){
            	var index = 0;
                var listLength = _thisList.children.length;
            	for( var i=0; i < listLength; i++){
            		if(_thisList.children[i].getAttribute('data-value') === checked){            			
						index = _thisList.children[i].getAttribute('data-index');
						console.log(index);
            		}
            	}
            	_thisINPUT.setAttribute('value', checked);
            	_thisSelect.children[index].selected = true;
            }
        },

        //radio类型render
        radioCallback: function radioCallback(wrapper, options, id) {
            var me = this;
            var title = options.title;
            var name = options.name;
            var SWid = id;
            var value = options.value;
            var checked = "";
            options.check ? checked = "checked" : "";
            var _thisELE = null; //当前元素 radio
            var html = '<input ' + checked + ' class="datacenter-radio" id="' + SWid + '" name="' + name + '" type="radio" value="' + value + '">\n                        <label for="' + SWid + '">' + title + '</label>';

            wrapper.insertAdjacentHTML('beforeend', html); //后期加入错误处理机制
            _thisELE = document.querySelector('div[id=\'' + SWid + '\']');

            //保存SWinput对象；
            me.swinput = _thisELE;
            swinputMap[me.id] = me;
        },

        //checkbox类型render
        checkBoxCallback: function checkBoxCallback(wrapper, options, id) {
            var me = this;
            var title = options.title;
            var name = options.name;
            var SWid = id;
            var value = options.value;
            var _thisELE = null; //当前元素 radio
            var checked = "";
            options.check ? checked = "checked" : "";
            var html = '<input ' + checked + ' class="datacenter-checkbox" id="' + SWid + '" name="' + name + '" type="checkbox" value="' + value + '">\n                        <label for="' + SWid + '">' + title + '</label>';

            wrapper.insertAdjacentHTML('beforeend', html); //后期加入错误处理机制
            _thisELE = document.querySelector('input[id=\'' + SWid + '\']');

            //保存SWinput对象；
            me.swinput = _thisELE;
            swinputMap[me.id] = me;
        }

    };

    window.SWinput = SWinput;
})(window, document);