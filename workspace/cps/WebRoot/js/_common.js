window.document.title="智能服务平台";
function getRootPath(){
	var pathName = window.document.location.pathname;
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return projectName;
}



function getBasePath() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	//测试环境
	return (localhostPaht + projectName);  //http://192.168.1.10:8080/cps-vmi/
	//正式环境
	//return localhostPaht
	//http://192.168.1.10:8080/
}
    document.write('<script type="text/javascript" src="'+getBasePath()+'/js/jquery-1.8.3.min.js"></script>');
	document.write('<link rel="stylesheet" type="text/css" href="'+getBasePath()+'/css/jNotify.jquery.css" />');
	document.write('<script type="text/javascript" src="'+getBasePath()+'/js/jNotify.jquery.js"></script>');
	document.write('<script type="text/javascript" src="'+getBasePath()+'/js/layer/layer.js"></script>');



function onclickImg(){
	layer.msg('开发中......', {
	  	icon: 5 ,
	    offset: 'auto',
	    shift: 6,
	    time: 1000//1秒自动关闭
	});
}

function goToSmallLogin(){
	var i = layer.open({
	    title:'',move:false,
	    type: 2,
	    anim:true,
	    area: ['350px', '310px'],
	    content:  window.getRootPath()+"/pages/smallLogin/smallLogin.jsp"
	});
	layer.style(i,{
		'border-radius':'25px',
		'border':'2px solid #a1a1a1'
	});
}
//设为常用
function isCommon(obj){
	$.ajax({
		url:"${ctx}/custproduct/isCommon.net",
		type:"post",
		dataType:"text",
		data:{fid:obj},
		success:function(data){
			if(data ="success"){
				layer.alert('操作成功！', function(index){
					window.gridCustproductTable(1);
					layer.close(index);
					});
			}else{
				layer.alert('操作失败！', function(index){layer.close(index);});
			}
		},
		error:function (){
				layer.alert('操作失败！', function(index){layer.close(index);});
		}
	});
}
//取消常用
function cancelCommon(obj){
	$.ajax({
		url:"${ctx}/custproduct/cancelCommon.net",
		type:"post",
		dataType:"text",
		data:{fid:obj},
		success:function(data){
			if(data ="success"){
				layer.alert('操作成功！', function(index){
					window.gridCustproductTable(1);
					layer.close(index);
					});
			}else{
				layer.alert('操作失败！', function(index){layer.close(index);});
			}
		},
		error:function (){
				layer.alert('操作失败！', function(index){layer.close(index);});
		}
	});
}
/**
 * 日期格式化	
 * @param date	日期对象
 * @param hasTime	是否含时间
 * @returns {String}   yyyy-MM-dd HH:mm:ss 或 yyyy-MM-dd
 */
function formatDate(date,hasTime){
	if(date == null){
		date = false;
	}
	if(typeof date == 'boolean'){
		date = new Date();
		hasTime = date;
	}
	var ret = '';
    var year = date.getFullYear();
    var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
    var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    ret = year + "-" + month + "-" + day;
    if(hasTime){
    	var hour = date.getHours()< 10 ? "0" + date.getHours() : date.getHours();
    	var minute = date.getMinutes()< 10 ? "0" + date.getMinutes() : date.getMinutes();
    	var second = date.getSeconds()< 10 ? "0" + date.getSeconds() : date.getSeconds();
    	ret = ret + " "+hour+":"+minute+":"+second;
    }
    return ret;
}
//兼容ie8
String.prototype.trim = String.prototype.trim || function(){
	return this.replace(/^\s+|\s+$/g,'');
};
//解决toFixed浏览器之间的差异
Number.prototype.toFixed = function (precision) {
	var num = this.toString(), zeros = '00000000000000000000', newNum, decLength, factor;
	if (0 > precision || precision > 20) {
		throw new RangeError("toFixed() digits argument must be between 0 and 20");
	}
	if (Math.abs(num) === Infinity || Math.abs(num) >= 1e21) {
		return num;
	}
	precision = parseInt(precision, 10) || 0;
	newNum = num = isNaN(parseInt(num, 10)) ? '0' : num;
	if (/\./.test(num)) {
		decLength = num.split('.')[1].length;
		if (decLength < precision) {
			newNum += zeros.substring(0, precision - decLength);
		} else {
			factor = Math.pow(10, precision);
			newNum = Math.round(num * factor) / factor;
		}
	} else {
		if (precision) {
			newNum += '.' + zeros.substring(0, precision);
		}
	}
	return newNum;
};
// 兼容ie低版本
if(window.JSON == undefined){
	window.JSON = {};
}
JSON.stringify = JSON.stringify || function(o) {
	charToReplace = /[\\\"\x00-\x1f\x7f-\uffff]/g;
	m = {
        "\b": '\\b',
        "\t": '\\t',
        "\n": '\\n',
        "\f": '\\f',
        "\r": '\\r',
        '"': '\\"',
        "\\": '\\\\',
        '\x0b': '\\u000b' //ie doesn't handle \v
    };
	function pad(n){
		return n < 10 ? "0" + n : n;
	}
	function encodeDate(o){
		 return '"' + o.getFullYear() + "-"
	        + pad(o.getMonth() + 1) + "-"
	        + pad(o.getDate()) + " "
	        + pad(o.getHours()) + ":"
	        + pad(o.getMinutes()) + ":"
	        + pad(o.getSeconds()) + '"';
	};
	function encodeString(s){
        return '"' + s.replace(charToReplace, function(a) {
            var c = m[a];
            return typeof c === 'string' ? c : '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
        }) + '"';
    }
	encodeArray = function(o) {
        var a = ["[", ""],
            len = o.length,
            i;
        for (i = 0; i < len; i += 1) {
            a.push(JSON.stringify(o[i]), ',');
        }
        a[a.length - 1] = ']';
        return a.join("");
    };
    function encodeObject(o) {
        var a = ["{", ""],
            i, val;
        for (i in o) {
            val = o[i];
            if (o.hasOwnProperty(i)) {
                if (typeof val === 'function' || val === undefined) {
                    continue;
                }
                a.push(JSON.stringify(i), ":", JSON.stringify(val), ',');
            }
        }
        a[a.length - 1] = '}';
        return a.join("");
    };
    if (o === null || o === undefined) {
        return "null";
    } else if ($.type(o) == 'date') {
        return encodeDate(o);
    } else if ($.type(o) == 'string') {
        return encodeString(o);
    } else if (typeof o == "number") {
        return isFinite(o) ? String(o) : "null";
    } else if (typeof o == 'boolean') {
        return String(o);
    }else if ($.type(o) == 'array') {
        return encodeArray(o);
    } else if ($.type(o) == 'object') {
        return encodeObject(o);
    } else if (typeof o === "function") {
        return "null";
    }
    return 'undefined';
};

//2015-10-29   只允许整数
function addNumOnlyEvent(obj){
	$(obj).each(function(){
		 $(this).keyup(function(){
			 $(this).val($(this).val().replace(/\D|^0/g,''));
		 }).bind("paste",function(){
			 $(this).val($(this).val().replace(/\D|^0/g,''));
		 }).css("ime-mode", "disabled");
	});
};

//2015-1-23   只允许整数
function addNumOnlyEvent2(obj){
	var field = $(obj);
	if(!field.length){
		return;
	}
	field.on("keypress", function(event) { 
		//控制只能输入的值  
		if (event.which && (event.which < 48 || event.which > 57) && event.which != 8 ) {  
		    event.preventDefault();  
		     return;  
		    }  
	});
	
	//失去焦点是触发  
	field.on("change", function(event) {
		var value = $(this).val(),reg = /^\d*\.{0,1}\d*$/;
		if (!reg.test(value)) {  
			$(this).val("");  
		}
	}).on("mousewheel",function(event){
		if(this.readOnly || !$(this).is(':focus')){
			return;
		}
		//2015-12-01 如果使用jQuery来进行事件绑定，在事件回调的参数中e是被jQuery重新封装的，所以我们必须使用e.originalEvent来指向原始的事件对象，就是这样任性。
		//取消滚动滑轮数字变化
		//ScrollText(this,event.originalEvent.wheelDelta);
		event.stopPropagation();	
		window.event.preventDefault();	
	}).blur(function(){
		$(this).trigger('change');
	});
};


function getFrameWin(){
	return window.top.document.getElementById('iframepage').contentWindow;
}
//2015-11-30 只允许数字小数点
//function addfloatOnlyEvent(obj){
//	$(obj).each(function(){
//		 $(this).keyup(function(){
//			 $(this).val($(this).val().replace(/[^0-9.]/g,'')); 
//		 }).bind("paste",function(){
//			 $(this).val($(this).val().replace(/[^0-9.]/g,'')); 
//		 }).css("ime-mode", "disabled");
//	});
//};
//敲击按键时触发  
function addfloatOnlyEvent(obj,precision) {
	var field = $(obj);
	if(!field.length){
		return;
	}
	field.on("keypress", function(event) {  
			var event= event || window.event;  
			var getValue = $(this).val();  
			//控制第一个不能输入小数点"."  
			if (getValue.length == 0 && event.which == 46) {  
			    event.preventDefault();  
			    return;  
			}  
			//控制只能输入一个小数点"."  
			if (getValue.indexOf('.') != -1 && event.which == 46) {  
			    event.preventDefault();  
			    return;  
			}  
			//控制只能输入的值  
			if (event.which && (event.which < 48 || event.which > 57) && event.which != 8 && event.which != 46) {  
			    event.preventDefault();  
			     return;  
			    }  
		});  
		//失去焦点是触发  
		field.on("change", function(event) {
			var value = $(this).val(),reg = /^\d*\.{0,1}\d*$/;  
			if (!reg.test(value)) {  
				$(this).val("");  
			}else if(precision){
				$(this).val(parseFloat(parseFloat(value).toFixed(precision)));
			}
		}).on("mousewheel",function(event){
			if(this.readOnly || !$(this).is(':focus')){
				return;
			}
			//2015-12-01 如果使用jQuery来进行事件绑定，在事件回调的参数中e是被jQuery重新封装的，所以我们必须使用e.originalEvent来指向原始的事件对象，就是这样任性。
			ScrollText(this,event.originalEvent.wheelDelta);
			event.stopPropagation();	
			window.event.preventDefault();	
		}).blur(function(){
			$(this).trigger('change');
		});
}
//需求包移除滚轮的增加数字事件
function addfloatOnlyEvent2(obj,precision) {
	var field = $(obj);
	if(!field.length){
		return;
	}
	field.on("keypress", function(event) {  
			var event= event || window.event;  
			var getValue = $(this).val();  
			//控制第一个不能输入小数点"."  
			if (getValue.length == 0 && event.which == 46) {  
			    event.preventDefault();  
			    return;  
			}  
			//控制只能输入一个小数点"."  
			if (getValue.indexOf('.') != -1 && event.which == 46) {  
			    event.preventDefault();  
			    return;  
			}  
			//控制只能输入的值  
			if (event.which && (event.which < 48 || event.which > 57) && event.which != 8 && event.which != 46) {  
			    event.preventDefault();  
			     return;  
			    }  
		});  
		//失去焦点是触发  
		field.on("change", function(event) {
			var value = $(this).val(),reg = /^\d*\.{0,1}\d*$/;  
			if (!reg.test(value)) {  
				$(this).val("");  
			}else if(precision){
				$(this).val(parseFloat(parseFloat(value).toFixed(precision)));
			}
		}).on("mousewheel",function(event){
			if(this.readOnly || !$(this).is(':focus')){
				return;
			}
			//2015-12-01 如果使用jQuery来进行事件绑定，在事件回调的参数中e是被jQuery重新封装的，所以我们必须使用e.originalEvent来指向原始的事件对象，就是这样任性。
			//ScrollText(this,event.originalEvent.wheelDelta);
			event.stopPropagation();	
			window.event.preventDefault();	
		}).blur(function(){
			$(this).trigger('change');
		});
}

function ScrollText(oTxt,arg){
	oTxt.focus();
	if(oTxt.value=="" || isNaN(oTxt.value)){
		oTxt.value=1;
	}
	var _value=parseFloat(oTxt.value);
	if(arg>0){
		_value++;
	}else{
		if(_value==0){
			return;
		}
		_value--;
	}
	oTxt.value=_value;
	oTxt.select();//选取效果
}

//子页面打开后设置iframe高度为当前屏幕高度
function getHtmlLoadingBeforeHeight(){
	var iframe  = parent.document.getElementById('iframepage');
	iframe.height =  Math.max(window.screen.height-423,550);
}
//子页面打开后设置iframe高度为当前body高度-非动态拼接html调用
function getHtmlBodyHeight(){
	var iframe  = parent.document.getElementById('iframepage');
	iframe.height =  document.body.scrollHeight;
}
//子页面动态拼接html后设置iframe高度-动态拼接代码后-调用
function getHtmlLoadingAfterHeight(){
	var iframe  = parent.document.getElementById('iframepage');
	var bHeight = iframe.contentWindow.document.body.scrollHeight;
	var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
	var height = Math.max(bHeight, dHeight);
	iframe.height =  height;
} 
//监听给定obj元素改变事件，动态改变iframe的高度。*****IE不兼容*****
function setIframeHeight(obj){
	$(obj).bind('DOMNodeInserted', function(e) {
		parent.$('#iframepage').attr('height',$(obj).height());
	}).bind('DOMNodeRemoved',function(e){
		parent.$('#iframepage').attr('height',$(obj).height());
	});
}
//多选--共用的获取选中复选框后input的值--列表中复选框后紧跟一个影藏的input 设置fid
function getCommonIds() {
	var paramStr = '';
	$('input:checkbox[name=product]:checked').next().each(function(i){
		if (i == 0) {
			paramStr += 'ids=' + $(this).val();
		}else{
			paramStr += "&ids=" + $(this).val();
		}
	});
	return paramStr;
}
//单选--共用的获取选中复选框后input的值--列表中复选框后紧跟一个影藏的input 设置fid
function getCommonId() {
	var paramStr = '';
	$('input:checkbox[name=product]:checked').next().each(function(i){
		if (i == 0) {
			paramStr += 'id=' + $(this).val();
		}
	});
	return paramStr;
}
/**
 * 
 * @param $allCheck	全选复选框的jquery对象
 * @param $checks	所有单选框的jquery对象
 * @param $checkparent 包含单选框的对象（点击容器，单选选择）
 */
function addAllCheckEvent($allCheck,$checks,$checkparent){
	$checks.change(function(){
		var len = $checks.not(':checked').length;
		var allChecked = $allCheck.prop('checked');
		if(len>0 && allChecked){
			$allCheck.prop('checked',false);
		}else if(len==0 && !allChecked){
			$allCheck.prop('checked',true);
		}
	}).click(function(e){
		e.stopPropagation();
	});
	$allCheck.change(function(){
		$checks.prop('checked',$(this).prop('checked'));
	});
	if($checkparent)
	{
		 $checkparent.click(function(i) {
		 var t = $(this).find($checks);
		  t.prop("checked",!t.attr("checked"));
			t.change();   
         });
	}
}
/**
 * 生成html代码，见 cardOrder.js
 * @param data	数据（array）
 * @param template	模板（array或string）
 * @param transFn	转换函数
 * @returns	
 */
function getHtml(data,template,transFn){
	var ret = [];
	if($.isEmptyObject(data)){
		return;
	}
	if($.type(template) == 'array'){
		template = template.join('').replace(/\t+/g,'');
	}
	if(!transFn){
		transFn = {};
	}
	$.each(data,function(index,item){
		ret.push(template.replace(/{{(\w+)}}/g,function(_,name){
			if($.type(transFn[name]) == 'function'){
				return transFn[name](item[name],item);
			}else{
				if(item[name]===null){
					item[name] = '';
				}
				return item[name];
			}
		}));
	});
	return ret.join('');
}
/**
 * 生成分页代码
 * @param response	响应数据
 * @param id		分页元素的id
 * @param callback	查询方法
 */
function getPagination(response,id,callback,isClone,parentid){
	var kkpager = isClone? new KKPager() : window.kkpager;
	$('#'+id).html('');
	kkpager.pno =response.pageNo;
	kkpager.total =Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
	kkpager.totalRecords =response.totalRecords;
	kkpager.generPageHtml({
		pagerid : id, //divID
		gopageWrapId: id+'_gopage_wrap',
		gopageButtonId: id+'_btn_go',
		gopageTextboxId: id+'_btn_go_input',
		click : function(n){
			callback(n,parentid||'');
			this.selectPage(n);
		},
		pno : response.pageNo,//当前页码
		total : Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize)),//总页码
		totalRecords : response.totalRecords,//总数据条数
		lang : {
			prePageText : '上一页',
			nextPageText : '下一页',
			totalPageBeforeText : '共',
			totalPageAfterText : '页',
			totalRecordsAfterText : '条数据',
			gopageBeforeText : '转到',
			gopageButtonOkText : 'GO',
			gopageAfterText : '页',
			buttonTipBeforeText : '第',
			buttonTipAfterText : '页'
		}
    });
}
