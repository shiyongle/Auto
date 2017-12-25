/**
 * 
 * 通用零散工具
 * 
 * @author ZJZ（447338871@qq.com）
 * @since long ago
 * @version 0.2 2014-12-31 下午1:47:48
 * 
 */
Ext.define("DJ.tools.common.MyCommonToolsZ", {
	singleton : true,

	alternateClassName : "MyCommonToolsZ",

	constructor : function() {

		Ext.tip.QuickTipManager.init();

		return this;

	},

	/**
	 * @test 2014-12-31 下午1:56:50
	 * 
	 * @param {}
	 *            copyText
	 * @param {}
	 *            id
	 */
	copy : function(copyText, id) {

		this.insertScript("../other/zeroClipboard/ZeroClipboard.js");

		// var client = new ZeroClipboard( document.getElementById(id));

		var clip = new ZeroClipboard.Client();// 新建一个对象
		clip.setHandCursor(true); // 设置鼠标为手型
		clip.setText(copyText); // 设置要复制的文本。
		clip.glue(id); // 和上一句位置不可调换

		// client.on( "copy", function (event) {
		// var clipboard = event.clipboardData;
		// clipboard.setData( "text/plain", "Copy me!" );
		// clipboard.setData( "text/html", "<b>Copy me!</b>" );
		// clipboard.setData( "application/rtf", "{\\rtf1\\ansi\n{\\b Copy
		// me!}}" );
		// });

	},
	/*
	 * @deprecated at 2014-12-31 下午1:56:59 by ZJZ for 用ext自带的dom操纵类即可实习
	 */
	insertScript : function(url) {

		// 创建一个script节点
		var scriptBlock = document.createElement("script");
		// 将外部文件引入
		scriptBlock.src = url;
		// 将该文件加入的html文件的头部。
		document.getElementsByTagName("head")[0].appendChild(scriptBlock);
	},

	/*
	 * @deprecated at 2014-12-31 下午1:57:30 by ZJZ for 用ext自带的dom操纵类即可实习
	 */
	insertScriptInwin : function(url, win, tag) {

		win = win || window;

		tag = tag || "script";

		// 创建一个script节点
		var scriptBlock = win.document.createElement(tag);
		// 将外部文件引入
		scriptBlock.src = url;
		// 将该文件加入的html文件的头部。
		win.document.getElementsByTagName("head")[0].appendChild(scriptBlock);
	},

	/**
	 * 设置快速提示
	 * 
	 * @param {}
	 *            target，id可用
	 * @param {}
	 *            title
	 * @param {}
	 *            text
	 */
	setQuickTip : function(target, title, text) {

		Ext.tip.QuickTipManager.register({

			dismissDelay : 30000,
			maxWidth : 1000,
			target : target,
			title : title,
			text : text

		});

	},

	/**
	 * 设置快速提示,tooltip,建议使用
	 * 
	 * @param {}
	 *            target
	 * @param {}
	 *            html
	 */
	setToolTipZ : function(target, html) {

		Ext.create('Ext.tip.ToolTip', {
			maxWidth : 1000,
			dismissDelay : 30000,
			target : target,
			html : html
		});

	},

	/**
	 * 在插件初始化，设置提示等场合有用。渲染后处理
	 * 
	 * @param {}
	 *            comOut
	 * @param {}
	 *            fn
	 */
	setComAfterrender : function(comOut, fn) {

		// 内部组件处理技巧
		comOut.on("afterrender", function(com, eOpts) {

			fn(com, eOpts);

			comOut.un("afterrender", this);

		});

	},

	/**
	 * 一次性的事件处理器
	 * 
	 * @param {}
	 *            comOut
	 * @param {}
	 *            event
	 * @param {}
	 *            fn
	 */
	setComListernerOnce : function(comOut, event, fn) {

		// 内部组件处理技巧
		comOut.on(event, function(com, eOpts) {

			fn(com, eOpts);

			comOut.un(event, this);

		});

	},

	/**
	 * 构建url拼接参数
	 * 
	 * @deprecated 可以用Ext.Object.toQueryString
	 * @param {}
	 *            paramsObj
	 * @return {}
	 */
	buildGetParams : function(paramsObj) {

		// var paramsArray = [];
		//
		// // 解决特殊字符的编码问题
		//
		// var i = 0;
		//
		// for (var p in paramsObj) {
		//
		// if (i != 0) {
		//
		// paramsArray.push("&");
		//
		// } else {
		//
		// i = 1;
		// }
		//
		// paramsArray.push(p);
		// paramsArray.push("=" + paramsObj[p] + "");
		//
		// }

		return Ext.Object.toQueryString(paramsObj);

	},

	/**
	 * 
	 * @param {}
	 *            grid
	 * @param {}
	 *            limit,限制
	 * @return {},记录数组,-1为未选择
	 */
	pickSelectItems : function(grid, limit) {

		var records = grid.getSelectionModel().getSelection();

		if (records.length == 0) {

			Ext.Msg.alert("提示", "请选择条目");
			return -1;
		}

		if (limit) {

			if (records.length != limit) {

				Ext.Msg.alert("提示", Ext.String.format("只能选择{0}条", limit));
				return -1;

			}

		}

		return records;

	},

	// 判断字符是否有中文字符
	isHasChn : function(s) {
		var patrn = /[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi;
		if (!patrn.exec(s)) {
			return false;
		} else {
			return true;
		}
	},

	/**
	 * 
	 * @deprecated 可以用ext.isEmpty
	 * 
	 */
	isEmptyOrNull : function(value) {

		if (value == null || Ext.String.trim(value) == "") {

			return true;

		} else {

			return false;

		}

	},

	doSimpleAjaxAction : function(url, params,callback) {

		Ext.Ajax.request({
			timeout : 6000,

			params : params,

			url : url,
			success : function(response, option) {

				var obj = Ext.decode(response.responseText);
				if (obj.success == true) {
					
					callback();
					
				} else {
					Ext.MessageBox.alert('错误', obj.msg);
				}

			}
		});

	},
	
	/**
	 * 
	 * 后台用这两个参数接收 fids goalValue
	 * 
	 * 一次ajax请求，
	 * 
	 * @param {}
	 *            url
	 * @param {}
	 *            grid
	 * @param {}
	 *            idPropName
	 * @param {}
	 *            goalFieldPropValue，请求的值
	 */
	reqAction : function(url, grid, idPropName, goalFieldPropValue, inArrayP,autoLoad,callback) {

		var me = this;
		
		var fidsFName = 'fids';
		
		var goalValueFName = 'goalFieldPropValue';
		
		me.reqActionINCusFieldName(url, grid, idPropName, goalFieldPropValue,
			fidsFName, goalValueFName, inArrayP,autoLoad,callback);
		
	},

	/**
	 * 
	 * 
	 * @param {} url
	 * @param {} grid
	 * @param {} idPropName
	 * @param {} goalFieldPropValue
	 * @param {} fidsFName
	 * @param {} goalValueFName
	 * @param {} inArrayP
	 */
	reqActionINCusFieldName : function(url, grid, idPropName, goalFieldPropValue,
			fidsFName, goalValueFName, inArrayP,autoLoad,callback) {

		var idPropNameT = idPropName || 'fid';

		var goalFieldPropName = goalFieldPropName;

		var store = grid.getStore();

		var items = MyCommonToolsZ.pickSelectItems(grid);

		if (items == -1) {

			return;

		}

		var ids = [];

		Ext.each(items, function(ele, index, all) {

			ids.push(ele.get(idPropNameT));

		});

		var paramsObj = {};

		paramsObj[fidsFName] = inArrayP ? Ext.JSON.encode(ids) : ids.join(',');

		paramsObj[goalValueFName] = goalFieldPropValue;

		Ext.Ajax.request({
			timeout : 6000,

			params : paramsObj,

			url : url,
			success : function(response, option) {

				var obj = Ext.decode(response.responseText);
				if (obj.success == true) {

					
					if (autoLoad) {
					
						store.load();
						djsuccessmsg(obj.msg);
						
					}
					
					if (callback) {
					
						callback();
						
					}
					

				} else {
					Ext.MessageBox.alert('错误', obj.msg);
				}

			}
		});

	},
	
	
	showAllTip : function(tip) {

		Ext.create('widget.uxNotification', {
			// title : '提示',
			// width : '80%',
			// height : 150,
			position : 'tc',
			manager : 'demo1',
			iconCls : 'ux-notification-icon-information',
			autoCloseDelay : 3000,
			closable : false,
			resizable : false,
			spacing : 20,
			html : tip
		}).show();

	},
	
	/**
	 * 
	 * encode(str)
	 * 
	 */
	 base64 : (function() {

		// 运用闭包技术实现封装，so wonderful，漂亮

		// private property
		var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
		// private method for UTF-8 encoding
		function utf8Encode(string) {
			string = string.replace(/\r\n/g, "\n");
			var utftext = "";
			for (var n = 0; n < string.length; n++) {
				var c = string.charCodeAt(n);
				if (c < 128) {
					utftext += String.fromCharCode(c);
				} else if ((c > 127) && (c < 2048)) {
					utftext += String.fromCharCode((c >> 6) | 192);
					utftext += String.fromCharCode((c & 63) | 128);
				} else {
					utftext += String.fromCharCode((c >> 12) | 224);
					utftext += String.fromCharCode(((c >> 6) & 63) | 128);
					utftext += String.fromCharCode((c & 63) | 128);
				}
			}
			return utftext;
		}

		// public method for encoding
		return {
			// This was the original line, which tries to use Firefox's built in
			// Base64 encoder, but this kept throwing exceptions....
			// encode : (typeof btoa == 'function') ? function(input) { return
			// btoa(input); } : function (input) {

			encode : function(input) {
				var output = "";
				var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
				var i = 0;
				input = utf8Encode(input);
				while (i < input.length) {
					chr1 = input.charCodeAt(i++);
					chr2 = input.charCodeAt(i++);
					chr3 = input.charCodeAt(i++);
					enc1 = chr1 >> 2;
					enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
					enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
					enc4 = chr3 & 63;
					if (isNaN(chr2)) {
						enc3 = enc4 = 64;
					} else if (isNaN(chr3)) {
						enc4 = 64;
					}
					output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)
							+ keyStr.charAt(enc3) + keyStr.charAt(enc4);
				}
				return output;
			}
		};
	})()

// ,
		//	
		// /**
		// * 设置快速提示
		// * @param {} target，id可用
		// * @param {} title
		// * @param {} text
		// */
		// setQuickTip : function(target, title, text) {
		//	
		//		
		//	
		// Ext.create(Ext.tip.ToolTip, {
		//		
		// target : target,
		//			
		//			
		// });
		//	
		//		
		//		
		// }
		});