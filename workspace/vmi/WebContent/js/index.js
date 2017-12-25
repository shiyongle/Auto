//以下是处理IE中indexOf不兼容的定义方法
if(!Array.indexOf){
	Array.prototype.indexOf = function(obj){
		for(var i=0; i<this.length; i++){
			if(this[i]==obj){
				return i;
			}
		}
		return -1;
	}
}
//以下是处理IE中map不兼容的定义方法
if(!Array.map){
	Array.prototype.map = function(fn, scope){
            var results = [];
            for (var i=0; i < this.length; i++) {
                results[i] = fn.call(scope, this[i], i,this);
            }
            return results;
	}
}
//tofixed方法浏览器统一
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
//请求时间
Ext.Ajax.timeout = 600000;
// 修复Ext的combobox不支持anyMatch的bug
Ext.define('Ext.form.field.SupportAnyMatchComboBox',{
	extend: 'Ext.form.field.ComboBox',
	alias: 'widget.ncombobox',
	initComponent: function(){
		this.callParent(arguments);
		this.queryFilter.anyMatch = true;
	}
});
// 转字符串生成unicode码
Ext.encodeUnicodeString = function(str){
	var str = Ext.encode(str);
	return str.substring(1,str.length-1);
}
//处理列表界面的改变状态操作
Ext.djRequest = function(url,comp,config,field){
	var grid = (comp.xtype=='button')?comp.up('grid'):comp,
		records = grid.getSelectionModel().getSelection(),
		fids = [],i;
	if(records.length===0){
		Ext.Msg.alert('提示','请先选择记录再操作！');
		return;
	}
	if(config){
		records.forEach(function(record){
			var add = true;
			Ext.Object.each(config,function(key,value){
				if(record.get(key) !== value){
					add = false;
				}
			});
			if(add){
				fids.push(record.get(field || 'fid'));
			}
		});
	}else{
		records.forEach(function(record){
			fids.push(record.get(field || 'fid'));
		});
	}
	if(fids.length===0){
		Ext.Msg.alert('提示','没有可操作的记录！');
		return;
	}
	Ext.Ajax.request({
		url: url,
		params: {
			fidcls: fids.join(',')
		},
		success: function(res){
			var obj = Ext.decode(res.responseText);
			if(obj.success){
				djsuccessmsg(obj.msg);
				grid.getStore().loadPage(1);
			}else{
				Ext.Msg.alert('错误',obj.msg);
			}
		},
		failure: function(){
			Ext.Msg.alert('错误','保存失败，请检查网络状态或刷新页面重试！');
		}
	});
};
var MainTabPanel, cookiemanager = new Ext.state.CookieProvider();
Ext.QuickTips.init();

Ext.Ajax.request({
		url : "getusernamebysession.do",
		success : function(response, option) {
            var obj = Ext.decode(response.responseText);
            if (obj.success == true) {
						cookiemanager.set("username",obj.username);
				}
		}
	});
Ext.Ajax.request({
	url : "getUserFilter.do",
	success : function(response) {
        var obj = Ext.decode(response.responseText);
        Ext.userIsFilter = obj.success;
	}
});

//设置状态存储代理
if (Ext.supports.LocalStorage) {

	Ext.state.Manager.setProvider(new Ext.state.LocalStorageProvider());

} else {

	Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider', {
		expires : new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 30))
			// 30 days
			}));

}


function showMsgView(type) {

	MainTabPanel.setActiveTab("mainSimpleMessage");

	var fT = function() {

		Ext.getCmp("DJ.System.SimplemessagePanel").down("treepanel")
				.getSelectionModel().select(type - 1);

		Ext.getCmp("DJ.System.SimplemessagePanel.SimplemessageList")
				.down("combo[name=readedState]").setValue(0);

	};

	var myAction = 0, typeIn = -1;

	switch (type) {
		case 1 :
//			myAction = 0;

			break;

		case 2 :
			typeIn = 1;

			break;
		case 3 :

			typeIn = 2;

			break;
	}

	setTimeout(function() {

		fT();
		DJ.System.SimplemessagePanel.loadDate(myAction, typeIn);

	}, 200);
}

function indexForm() {

//	Ext.apply(Ext.grid.Panel,{stateful : true});
//	
//	Ext.apply(Ext.c.GridPanel,{stateful : true});
	
//	Ext.override(Ext.grid.Panel,{stateful : true});
//	
//	//持久化grid配置
//	Ext.override(Ext.c.GridPanel,{stateful : true});
	
	
	
	//set the user's name to title
	document.title = Ext.String.format("智能服务平台({0})", cookiemanager.get("username"));
	
	
	
	/*document.getElementById('rTel').innerHTML = "<font size=\"2px\"  color=\"blue\">服务热线：0577-55575296 QQ号：1718733793</font> ";*/
	Ext.get('rTel').on('mouseover',function(){
		var style = document.getElementById('vmi_concat').style;
		style.display = 'block';
		style.left = this.getX()+'px';
	}).on('mouseout',function(){
		document.getElementById('vmi_concat').style.display = 'none';
	});
	/**
	 * 用于主页远程获取winCfg的Model
	 */
	Ext.define('CusWinCfg', {
		extend : 'Ext.data.Model',

		idProperty : 'fid',

		fields : [{
			name : 'fid'
		}, {
			name : 'userName'
		}, {
			name : 'fPositionx',
			type : 'int'
		}, {
			name : 'fPositiony',
			type : 'int'
		}, {
			name : 'ftitle'
		}, {
			name : 'fwidth',
			type : 'float'
		}, {
			name : 'fheight',
			type : 'float'
		}, {
			name : 'ftype'
		}, {
			name : 'fcode'
				// ,
				// convert: function (v, record) {
				//								
				// var r;
				//									
				// r = Ext.String.htmlDecode(v);
				//									
				// return r;
				//								
				// }
				}]

	});

	/**
	 * 用于主页远程获取的Store
	 */
	Ext.app.storeCusWinCfg = new Ext.data.Store({
		storeId : 'storeCusWinCfgId',

		model : "CusWinCfg",
		proxy : {
			type : 'ajax',// 使用Ext.data.proxy.LocalStorage代理
			api : {
				create : undefined,
				read : 'getWinCfgsByUser.do',
				update : 'updateCusWinCfgs.do',
				destroy : undefined
			},
			reader : {
				type : 'json',
				root : 'data',
				messageProperty : 'msg'
			},
			writer : {
				type : 'json',
				encode : true,
				root : 'data',
				allowSingle : false
			}
		}
	});

	// 设置类名对应的路径
	Ext.Loader.setConfig({
		enabled : true,
		paths : {
			'DJ' : 'js',
			'Ext.ux' : 'extlib/ux',
			'Ext.app' : 'js/tools/myPrint',
			'MyApp.tools' : 'js/tools/myPrint'

		}
	});

	Ext.require("DJ.tools.mainPageRel.IndexMessageRel", function() {

		// 定时获取系统提示。
		if(!Ext.isIE){
			DJ.tools.mainPageRel.IndexMessageRel.gainIndexMessageInterval(60000);
		}
		
		IndexMessageRel.loadClasses();
		
		IndexMessageRel.overrideClasses();
		
		//统一初始化，以后初始化操作建议写在这里
		IndexMessageRel.myInitF();
		//添加表格可复制特性
//		IndexMessageRel.setUpTheCopyFunctionOfGrid();
	});

	Ext.define('MenuModel', {
		extend : 'Ext.data.Model',
		fields : [{
			name : 'fid',
			type : 'string'
		}, {
			name : 'text',
			type : 'string'
		}, {
			name : 'furl',
			type : 'string'
		}, {
			name : 'fisleaf',
			type : 'int'
		}, {
			name : 'fpath',
			type : 'string'
		}]
	});
	var MainMenuPanel = new Ext.Panel({
		width : 100,
		height : 300,
		x : 0,
		y : 0,
		layout : 'accordion',
		defaults : {
			bodyStyle : 'padding:15px'
		},
		layoutConfig : {
			titleCollapse : false,
			animate : true,
			activeOnTop : true
		}
	});

	var mainSimpleMessage = Ext.create("DJ.System.SimplemessagePanel");

	Ext.Ajax.request({
		url : "MainMenuPanel.do",
		success : function(response, option) {
			// if (!Ext.isString(response.responseText)) {
			// window.location.href = 'login.do';
			// return;
			// };
			var obj;
			try {
				obj = Ext.decode(response.responseText);
			} catch (e) {
				window.location.href = 'login.do';
				return;
			}

			if (obj.success == true) {
				var m = obj.menus;
				for (var i = 0; i < m.length; i++) {

					var store = Ext.create('Ext.data.TreeStore', {
						model : 'MenuModel',
						proxy : {
							type : 'ajax',
							url : 'MainMenuTree.do'
						},
						reader : {
							type : 'json'
						},
						writer : {
							type : "json"
						},

						root : {
							fid : m[i].fid,
							furl : m[i].furl,
							expanded : true,
							text : m[i].text,
							iconCls : m[i].iconCls
						// ,
						},
						listeners : {
							'beforeload' : function(_store, record) {
								var new_params = {
									fid : record.node.data.fid
								};
								Ext.apply(_store.proxy.extraParams, new_params);
							}
						}
					});

					var MainMenuTreePanel = Ext.create('Ext.tree.Panel', {
						iconCls : m[i].iconCls,
						title : m[i].text,
						useArrows : true,
						rootVisible : false,
						store : store,
						plugins : [
							//反馈菜单
							Ext.create("Ext.ux.grid.MySimpleGridContextMenu",{
								
								otherItems : [{
									
									text:'反馈',
									handlerN : function(com, record, item, index, e, eOpts) {

									var me = this;

									var pageId = 'DJ.other.news.indexForTest';
									
									var backC = Ext
											.getCmp(pageId);

									if (!backC) {
									
										backC = Ext
											.create(pageId);
										
									}
											
									if (!MainTabPanel.contains(backC)) {
									
										MainTabPanel.add(backC);
										
									}
									
									MainTabPanel.setActiveTab(backC); // 设置当前tab页
									MainTabPanel.doLayout(true);

//									var btn = backC.down("button[id*=addbutton]");
//									
//									btn.handler.call(btn);
//									
//									Ext.defer(function(){
//									
//										var textT = record.get("text");
//										
//										var noteEditT = Ext.getCmp("DJ.System.note.NoteEdit");
//										
//										noteEditT.down("textarea").setValue(Ext.String.format(" {0}-{1} 这个界面有地方要改进，",record.parentNode.get('text') ,textT));
//										
//									},200);
									
								}
									
								}]
								
							})
						
						],
						listeners : {
							load:function(me,store,records,successful, eOpts ){
								if(store.get('text')=='客户平台'){
									for(var i=0;i<records.length;i++){
										switch(records[i].get('text')){
											case '我的需求':
												records[i].set('qtip','发布、查看、确认包装设计需求');
												break;
											case '我的产品':
												records[i].set('qtip','查看维护包装物产品档案');
												break;
											case '我的订单':
												records[i].set('qtip','下单和查看订单状态');
												break;
											case '我的备货':
												records[i].set('qtip','下达消耗备货产品');
												break;
											case '要货申请导入':
												records[i].set('qtip','批量下达采购计划和订单');
												break;
											case '我的库存':
												records[i].set('qtip','查看产品库存信息');
												break;
											case '我的收货':
												records[i].set('qtip','查看产品收货记录');
												break;
											case '子用户管理':
												records[i].set('qtip','管理平台帐号权限');
												break;
											case '客户角色管理':
												records[i].set('qtip','通过角色管理帐号权限');
												break;
											default:
												break;
										}
										
										
									}
								}
								
							},
							itemclick : function(view, record, item, index, e) {// 用了select这个事件不会触发。
								if (record.data.leaf == false)
									return;
									if(record.data.furl == "DJ.order.Deliver.FistproductList" || record.data.furl=="DJ.quickOrder.quickOrderList"){
//									if(false){
										var url;
										if(record.data.furl=="DJ.quickOrder.quickOrderList"){
											url = "/menuTree/center.net?menu=c3d28df961a3c9ebfc7994361031186c";
//											url = "/cps-vmi/menuTree/center.net?menu=c3d28df961a3c9ebfc7994361031186c";
										}else if(record.data.furl=="DJ.order.Deliver.FistproductList"){
											url = "/menuTree/center.net?menu=64d6d70f6f80f70bbe73b62d9cf9b004";
//											url = "/cps-vmi/menuTree/center.net?menu=64d6d70f6f80f70bbe73b62d9cf9b004";
										}
										Ext.Ajax.request({
												url : "logonNewAction.do",
												params: {
													url: url
												},
												timeout: 60000,
												success: function(response){
													var obj = Ext.decode(response.responseText);
														if(!Ext.isEmpty(obj.data.redirectUrl)){
															window.location.href = obj.data.redirectUrl;
														}
												},
												failure : function(response, option){
													Ext.MessageBox.alert('错误', '无法连接服务器!');
												}
											});
											
									}else{
										var subTabPanel = Ext.getCmp(record.data.furl) || Ext.create(record.data.furl);
										if (!MainTabPanel.contains(subTabPanel)) {
											MainTabPanel.add(subTabPanel);
										}
										MainTabPanel.setActiveTab(subTabPanel);
									}
								
							}
						}

					});

					MainMenuPanel.add(MainMenuTreePanel);

				}
			}
		}
	});

	/**
	 * tab
	 */
	Ext.define('MainTab',{
		extend: 'Ext.TabPanel',
		xtype: 'maintab',
		width : 500,
		height : 300,
		autoDestroy : false,
		activeTab : 0,
		defaults : {
		//	autoScroll : true,
			autoHeight : true
		},
		initComponent: function(){
			var me = this;
			Ext.apply(me,{
				plugins : Ext.create("Ext.ux.TabCloseMenu"),
				listeners : {
					afterrender:function(tab){
						var tabBar=tab.down("tabbar");
						tab.mon(tabBar.el,{
							scope:this,
							dblclick:function(){
								if(tab.getActiveTab().closable){
									tab.remove(tab.getActiveTab());
								}
							},
							delegate:'div.x-tab'
						});
					}
				},
				items : [{
					xtype : 'panel',
					id : 'mianPagePanel',
					// xtype:'Ext.Viewport',
					title : "主页",
					closable : false,// 是否现实关闭按钮,默认为false
					tabTip : "mormal",
//					layout : "absolute",
					layout : "fit",
					items : [Ext.create("DJ.other.news.NewsList")],
					
					iconCls : 'house'
				}, {
					xtype : 'panel',
					id : 'mainSimpleMessage',
					// xtype:'Ext.Viewport',
					title : "消息中心",
					closable : false,// 是否现实关闭按钮,默认为false
					tabTip : "mormal",
					layout : "fit",
					items : [mainSimpleMessage]
				}],
				enableTabScroll : true
			});
			me.callParent(arguments);
		}
	});
/*	MainTabPanelCopy = new Ext.TabPanel({
		id : "mainTab",
		width : 500,
		height : 300,
		autoDestroy : false,
		plugins : Ext.create("Ext.ux.TabCloseMenu"),
		listeners : {
			afterrender:function(tab){
				var tabBar=tab.down("tabbar");
//				var tabs = Ext.getCmp('mainTab');
				tab.mon(tabBar.el,{
					scope:this,
					dblclick:function(){
						if(tab.getActiveTab().closable){
							tab.remove(tab.getActiveTab());
						}
						},
					delegate:'div.x-tab'
					});
			},
			remove : function(tp, c) {
				c.hide()
			}
		},
		activeTab : 0,
		defaults : {
			autoScroll : true,
			autoHeight : true
		},
		items : [{
			xtype : 'panel',
			id : 'mianPagePanel',
			// xtype:'Ext.Viewport',
			title : "主页",
			closable : false,// 是否现实关闭按钮,默认为false
			tabTip : "mormal",
//			layout : "absolute",
			layout : "fit",
			items : [Ext.create("DJ.other.news.NewsList")],
			
			iconCls : 'house'
		}, {
			xtype : 'panel',
			id : 'mainSimpleMessage',
			// xtype:'Ext.Viewport',
			title : "消息中心",
			closable : false,// 是否现实关闭按钮,默认为false
			tabTip : "mormal",
			layout : "fit",
			items : [mainSimpleMessage]
		}],
		enableTabScroll : true
	});*/

	// 可能下面没用
	var winItemIds = [];

//	var mainPageBgCfg = {xtype: "image",src : 'images/mainPageBg.jpg', style: {
//            height : '100%',
//            width : '100%'
//            
//        }};
	
	// 读取数据
//	Ext.app.storeCusWinCfg.load({
//		scope : this,
//		callback : function(records, operation, success) {
//
//			if (!success) {
//
//				return;
//
//			}
//
//			var winItems = [mainPageBgCfg];
//
//			Ext.each(Ext.app.storeCusWinCfg.data.items, function(item, index,
//					all) {
//
//				item = item.data;
//
//				var obj = {
//					xtype : 'panel',
//
//					// constrainHeader : true,
//
//					resizable : false,
//
//					id : item.fid,
//					title : item.ftitle,
//					draggable : false,
//					width : item.fwidth,
//					height : item.fheight,
//					x : item.fPositionx,
//					y : item.fPositiony,
//					html : item.fcode
//				};
//
//				winItemIds.push(item.fid);
//
//				winItems.push(obj);
//			});
//
//			Ext.getCmp("mianPagePanel").add(winItems);
//
//		}
//	});

	Ext.define('DJ.MainList', {
		extend : 'Ext.Viewport',
		layout : 'border',
		// bodyStyle : "text-align:center;",
		items : [{
			region : 'north',
			xtype : 'panel',
			contentEl : 'north',
			height : 60,
			split : false,
			margins : ' 5 0 0 0'
		}, {
			id : 'statepanel',
			region : 'south',
			xtype : 'panel',
			height : 20,
			split : false,
			html : cookiemanager.get("username") + ',欢迎您登录！',
			margins : '0 5 5 5'
		}, {
			// bodyStyle : "text-align:center;",
			title : '系统菜单',
			region : 'west',
			xtype : 'panel',
			items : [MainMenuPanel],
			margins : '0 0 5 0',
			width : 170,
			collapsible : true,
			id : 'west-region-container',
			layout : 'fit'
		},{
			xtype: 'maintab',
			id: 'mainTab',
			region: 'center'
		}/*, {

			region : 'center',
			xtype : 'panel',
			autoScroll: false,
			items : [MainTabPanel],
			layout : 'fit',
			margins : ' 5 5 0 0',
			html : '在Extjs4中,center区域必须指定，否者会报错！'
		}*/],
		renderTo : Ext.get("mydiv"),
		listeners: {
			afterrender: function(){
				window.MainTabPanel = this.down('maintab');
			}
		}
	});
	function activateHandler(tab) {
		mytabpanel.remove("test1111");
		Ext.MessageBox.alert("提示", tab.title);
	};
	var lockwindow = Ext.create("Ext.Window", {
		id : "lockwindow",
		iconCls : "lock",
		title : "锁定系统",
		width : 210,
		modal : true,
		height : 110,
		collapsible : false,
		titleCollapse : false,
		maximizable : false,
		draggable : false,
		closable : false,
		resizable : false,
		items : [{
			xtype : 'form',
			id : "lockform",
			width : 210,
			height : 50,
			defaultType : 'textfield',
			labelSeparator : '：',
			// bodyStyle : 'padding:10 0 0 10',
			layout : "absolute",
			items : [{
				fieldLabel : '帐&nbsp;号',
				labelWidth : 30,
				hidden : true,
				name : 'username',
				id : 'lockwindow.username',
				// fieldCls : 'user', // cls
				blankText : '帐号不能为空,请输入!',
				maxLength : 30,
				maxLengthText : '账号的最大长度为30个字符',
				allowBlank : false,
				listeners : {
					specialkey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {
							Ext.getCmp('password').focus();
						}
					}
				}
			}, {
				fieldLabel : '密&nbsp;码',
				labelWidth : 30,
				x : 10,
				y : 10,
				name : 'password',
				id : 'lockwindow.password',
				// cls : 'key',
				inputType : 'password',
				blankText : '密码不能为空,请输入!',
				allowBlank : false,
				listeners : {
					specialkey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {
							login();
						}
					}
				}
			}]

		}],
		buttons : [{
			text : '&nbsp;解   锁',
			iconCls : 'key',
			handler : function() {
				// Ext.MessageBox.alert('提示',
				// Ext.getCmp("username").getValue());
				login();
			}
		}]

	});

	/**
	 * 值为，"editable"或"saveable"
	 * 
	 * @type String
	 */
	Ext.app.mainMenuOperationTheCfgState = "editable";

	/**
	 * 设置可编辑性的函数
	 * 
	 * @param {}
	 *            draggable，可编辑性
	 * @return {}
	 */
	var setCfgObg = function(draggable) {

		var winCfgt = [mainPageBgCfg];

		Ext.each(Ext.app.storeCusWinCfg.data.items, function(item, index, all) {

			item = item.data;

			var obj = {
				xtype : 'panel',

				// constrainHeader : true,

				resizable : draggable,

				id : item.fid,
				title : item.ftitle,
				draggable : draggable,
				width : item.fwidth,
				height : item.fheight,
				x : item.fPositionx,
				y : item.fPositiony,
				html : item.fcode
			};

			winCfgt.push(obj);
		});
		
		return winCfgt;

	}

	// 编辑函数
	var editWindowsCfg = function() {

		Ext.getCmp("mianPagePanel").removeAll();
		Ext.getCmp("mianPagePanel").add(setCfgObg(true));
	}

	// 保存函数
	var saveWindowsCfg = function() {

		var xOffset = -Ext.getCmp("mianPagePanel").getX();
		var yOffset = -Ext.getCmp("mianPagePanel").getY();

		// 修改store到最新值
		Ext.each(Ext.app.storeCusWinCfg.data.items, function(item, index, all) {

			
				var com = Ext.getCmp(item.data.fid);
	
				var modlet = Ext.app.storeCusWinCfg
						.findRecord("fid", item.data.fid);
	
				modlet.set("fPositionx", (com.getX() + xOffset) <= 0 ? 0 : (com
						.getX() + xOffset));
				modlet.set("fPositiony", (com.getY() + yOffset) <= 0 ? 0 : (com
						.getY() + yOffset));
	
				modlet.set("fwidth", com.getWidth());
				modlet.set("fheight", com.getHeight());
				
	

		});

		Ext.app.storeCusWinCfg.sync({
			callback : function(optional) {
				var r = "成功";

				if (!optional.operations[0].resultSet.success) {
					r = "失败";
					Ext.Msg.alert("提示", r + " "
						+ optional.operations[0].resultSet.message);
				}

				Ext.getCmp("mianPagePanel").removeAll();
				Ext.getCmp("mianPagePanel").add(setCfgObg(false));

				
			}
		});

	}

	var mainMenu = new Ext.menu.Menu({
		id : 'mainMenu',
		items : [
//			{
//			
//			id : "mainMenuOperationTheCfg",
//			text : '编辑主页',
//			iconCls : 'house',
//			handler : function() {
//
//				// saveWindowsCfg();
//
//				// editWindowsCfg();
//
//				if (Ext.app.mainMenuOperationTheCfgState == "editable") {
//					Ext.app.mainMenuOperationTheCfgState = "saveable";
//
//					Ext.getCmp("mainMenuOperationTheCfg").setText("保存主页");
//
//					editWindowsCfg();
//				} else {
//					Ext.app.mainMenuOperationTheCfgState = "editable";
//
//					Ext.getCmp("mainMenuOperationTheCfg").setText("编辑主页");
//
//					saveWindowsCfg();
//				}
//
//				// Ext.MessageBox.alert('提示',
//				// +Ext.getCmp("testpanel1").x
//				// + "," + Ext.getCmp("testpanel1").y);
//			}
//		},
		{
			text : '系统锁定',
			iconCls : 'lock',
			handler : function() {
				lockwindow.show();
				cookiemanager.set("locked", "1");
				Ext.getCmp("lockwindow.username").setValue(cookiemanager
						.get("username"));
			}
		}/*,{
			text: '纸板订单暂存',
			checked: false,
			isCheckHandler: true,
			setCheckNoHandler: function(bool){
				var me = this;
				me.isCheckHandler = false;
				me.setChecked(bool);
				me.isCheckHandler = true;
			},
			checkHandler: function(item,checked){
				var s = checked?'暂存':'已创建';
				if(!item.isCheckHandler){
					return;
				}
				Ext.Ajax.request({
					url: 'saveBoardOrderCfg.do',
					params: {
						add: checked
					},
					success: function(res){
						var obj = Ext.decode(res.responseText);
						if (!obj.success) {
							item.isCheckHandler = false;
							item.setCheckNoHandler(!checked);
						}else{
							djsuccessmsg('已将纸板订单下单的状态改为<span style="color:#f00;">'+s+'</span>状态');
							var sendBtn = Ext.getCmp('DJ.order.Deliver.DeliversBoardList.Send');
							if(sendBtn){
								sendBtn.setVisible(checked);
							}
						}
					},
					failure: function(){
						Ext.Msg.alert('错误','请刷新页面重试！');
						item.setCheckNoHandler(!checked);
					}
				});
			},
			listeners: {
				afterrender: function(){
					var me = this;
					Ext.Ajax.request({
						url: 'getBoardOrderCfg.do',
						success: function(res){
							var obj = Ext.decode(res.responseText);
							if (obj.success && obj.msg==='1') {
								me.setCheckNoHandler(true);
							}
						}
					});
				}
			}
		}*/]
	});

	var configButton = new Ext.Button({
		// text : '首选项',
		// iconCls : 'config2Icon',
		// iconAlign : 'left',
		iconCls : "config",
		scale : 'medium',
		width : 35,
		height : 25,
		tooltip : '<span style="font-size:12px">首选项设置</span>',
		pressed : true,
		renderTo : 'configDiv',
		menu : mainMenu
	});

	// Ext.create('Ext.Button', {
	// text : '锁 定',
	// iconAlign : 'left',
	// // scale : 'medium',
	// width : 50,
	// // tooltip : '<span style="font-size:12px">切换用户,安全退出系统</span>',
	// pressed : true,
	// arrowAlign : 'right',
	// renderTo : 'LockDiv',
	// handler : function() {
	// // logout();
	// lockwindow.show();
	// cookiemanager.set("locked", "1");
	// Ext.getCmp("lockwindow.username").setValue(cookiemanager
	// .get("username"));
	// }
	// });

	function login() {
		var form = Ext.getCmp('lockform');
		var Password = Ext.getCmp("lockwindow.password").getValue();
		if (Ext.util.Format.trim(Password) == "") {
			Ext.MessageBox.alert("警告", "密码不能够为空！");
			return;
		}
		Ext.getCmp("lockwindow.password").setValue(MD5(Password));
		var unlockparams = form.getValues();
		unlockparams["type"] = 'unlock';
		form.submit({
			waitMsg : '正在验证,请稍候...',
			url : "logonAction.do",
			mthod : 'POST',
			params : unlockparams,
			success : function(response, option, action) {
				if (option.result.success == true) {
					lockwindow.hide();
					cookiemanager.set("locked", "0");
					Ext.getCmp("lockwindow.password").setValue("");
				} else {
					Ext.MessageBox.alert("登录失败", option.result.msg); // obj.msg
					Ext.getCmp("lockwindow.password").setValue(Password);
				}
			},
			failure : function(response, option) {
				if (option.failureType == 'server') {
					Ext.MessageBox.alert('友情提示', option.result.msg);
				} else if (option.failureType == 'connect') {
					Ext.Msg.alert('连接错误', '无法连接服务器!');
				} else if (option.failureType == 'client') {
					Ext.Msg.alert('提示', '数据错误，非法提交');
				} else {
					Ext.MessageBox.alert('警告', '服务器数据传输失败：'
							+ option.response.responseText);
				}
				Ext.getCmp("lockwindow.password").setValue(Password);
			}
		});
	};
	// Ext.create('Ext.Button', {
	// text : '保存首页',
	// iconAlign : 'left',
	// // scale : 'medium',
	// width : 80,
	// // tooltip : '<span style="font-size:12px">切换用户,安全退出系统</span>',
	// pressed : true,
	// arrowAlign : 'right',
	// renderTo : 'SaveIndexDiv',
	// handler : function() {
	// Ext.MessageBox.alert('提示', +Ext.getCmp("testpanel1").x
	// + "," + Ext.getCmp("testpanel1").y);
	// }
	// });
	Ext.create('Ext.Button', {
		// text : '退 出',
		// iconAlign : 'left',
		iconCls : "logout",
		// scale : 'medium',
		width : 25,
		height : 25,
		// tooltip : '<span style="font-size:12px">切换用户,安全退出系统</span>',
		pressed : true,
		arrowAlign : 'right',
		renderTo : 'closeDiv',
		handler : function() {
			logout();
		}
	});
	function logout() {
		Ext.MessageBox.show({
			title : '提示',
			msg : '确认要注销系统,退出登录吗?',
			width : 250,
			buttons : Ext.MessageBox.YESNO,
			animEl : Ext.getBody(),
			icon : Ext.MessageBox.QUESTION,
			fn : function(btn) {
				if (btn == 'yes') {
					Ext.MessageBox.show({
						title : '请等待',
						msg : '正在注销...',
						width : 300,
						wait : true,
						waitConfig : {
							interval : 50
						}
					});
					window.location.href = 'login.do?reqCode=logout';
				}
			}
		});
	}
	Ext.create('DJ.MainList');

	
	
	// 获取未读取信息
	var reqCfg = {
		url : "gainMsgTipCount.do",
		callback : function(options, success, response) {
			if (success) {
				var msg = Ext.JSON.decode(response.responseText);
//				msg = Ext.JSON.decode(msg.msg);
				Ext.create('widget.uxNotification', {
					title : '提示',
					width : 260,
					height : 150,
					position : 'br',
					manager : 'demo1',
					iconCls : 'ux-notification-icon-information',
					autoCloseDelay : 10000,
					spacing : 20,
					html : '您有<a href = "javascript:showMsgView(1);">' + msg.unReadedCount + '<\a>条未读消息;<br\>' +
							'您有<a href = "javascript:showMsgView(2);">'
							+ msg.unReceivingCount + '<\a>条待收货消息;<br\>' +
									'您有<a href = "javascript:showMsgView(3);">'
							+ msg.projectEvaluation + '<\a>个方案评价消息;'
				}).show();
			}
			// var unreadCount = -1;
			//
			// if (success) {
			// unreadCount = Ext.JSON.decode(response.responseText).total;
			// }
			//
			// Ext.create('widget.uxNotification', {
			// title : '提示',
			// width : 260,
			// height : 150,
			// position : 'br',
			// manager : 'demo1',
			// iconCls : 'ux-notification-icon-information',
			// autoCloseDelay : 590000,
			// spacing : 20,
			// html : '您 有 [ '
			// + unreadCount
			// + ' ] 条未读 消 息;<br\> 您 有 [ 0 ] 个 待 处 理 订 单<br\ >您 有 [ 0 ] 个 待 处 理
			// 任 务 '
			// }).show();

		}
	};
	
	

	if (cookiemanager.get("locked") === "1") {
		lockwindow.show();
		Ext.getCmp("lockwindow.username").setValue(cookiemanager
				.get("username"));
	};
	 /**
	 * 消息提醒
	 */
//	 function showmessage() {
//	 Ext.Ajax.request(reqCfg);
//	 }
//	 // Ext.Ajax.request(reqCfg);
//	 setInterval("showmessage()", 600000);
	if(!Ext.isIE){
		Ext.TaskManager.start({
			run : function() {
				Ext.Ajax.request(reqCfg);
			},
			interval : 600000
		});
	}
}

Ext.onReady(indexForm);
/**
 * 显示系统时钟
 */
function showTime() {
	var date = new Date();
	var now = "";
	if (date.getHours() > 0 && date.getHours() <= 12) {
		now = "早上好:" + cookiemanager.get("username");
	} else if (date.getHours() > 12 && date.getHours() <= 18) {
		now = "下午好:" + cookiemanager.get("username");
	} else {
		now = "晚上好:" + cookiemanager.get("username");
	}
	now = now + ";今天是:"
	now = now + date.getFullYear() + "-"; // 读英文就行了
	now = now + (date.getMonth() + 1) + "-";// 取月的时候取的是当前月-1如果想取当前月+1就可以了
	now = now + date.getDate() + " ";
	now = now + date.getHours() + ":";
	now = now + date.getMinutes() + ":";
	if (date.getSeconds() > 9) {
		now = now + date.getSeconds() + "";
	} else {
		now = now + "0" + date.getSeconds() + "";
	}
	document.getElementById('rTime').innerHTML = now;
};

Ext.app.refreshById = function(id) {

	var storet = Ext.data.StoreManager.lookup('storeCusWinCfgId');

	storet.load({
		scope : this,
		callback : function(records, operation, success) {

			if (!success) {

				return;

			}

			var recordt = storet.findRecord("fid", id);

			var obj = {
				xtype : 'panel',

				// constrainHeader : true,

				resizable : false,

				id : recordt.get("fid"),
				title : recordt.get("ftitle"),
				draggable : false,
				width : recordt.get("fwidth"),
				height : recordt.get("fheight"),
				x : recordt.get("fPositinx"),
				y : recordt.get("fPositiony"),
				html : recordt.get("fcode")
			};

			Ext.getCmp("mianPagePanel").remove(id);

			Ext.getCmp("mianPagePanel").add(winItems);

		}
	})

}
Ext.Function.defer(function(){
	if(localStorage._IndexMsg2){
		return;
	}
	Ext.create('Ext.Window',{
		draggable: false,
		modal: true,
		autoShow: true,
		resizable: false,
		closable: false,
		layout: 'fit',
		items: [{
			xtype: 'component',
			html: '<img hidefocus="true" class="x-img x-fit-item x-window-item x-img-default" id="image-1132" src="images/indexmsg2.jpg" style="margin: 0px; width: 710px; height: 350px;">'
		}],
		buttons: [/*{
			text: '去体验...',
			width: '50%',
			handler: function(){
				this.up('window').close();
				localStorage._IndexMsg2="1";
				var subTabPanel = Ext.create('DJ.order.Deliver.FTUsaledeliverList');
				MainTabPanel.add(subTabPanel);
				MainTabPanel.setActiveTab(subTabPanel); // 设置当前tab页
				MainTabPanel.doLayout(true);
			}
		},*/'->',{
			text: '知道了...',
			width: '100px',
			handler: function(){
				this.up('window').close();
				localStorage._IndexMsg2="1";
			}
		},'->']
	});
    
}, 200);
window.onload = function() {
	setInterval("showTime()", 1000);
		// setInterval("showmessage()", 600000);

};
