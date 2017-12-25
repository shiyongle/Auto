
/**
 * 主页消息工具类
 */
Ext.define("DJ.tools.mainPageRel.IndexMessageRel", {
	singleton : true,

	requires : ['DJ.tools.common.MyCommonToolsZ'],

	alternateClassName : "IndexMessageRel",

	doUA : function(fid) {

		var win = Ext.create('DJ.System.phoneLogin.AUCenter');

		win.goalFid = fid;
		
		win.show();

	},
	openUserCenter : function() {

		var win = Ext.create('DJ.System.phoneLogin.UserCenterWin');

		var userName = cookiemanager.get("username");

//		win.show();
		
		var el = Ext.getBody();
		el.mask("系统处理中,请稍候……");

		Ext.Ajax.request({
			timeout : 6000,

			params : {

			},

			url : "getUserManageUserinfo.do",
			success : function(response, option) {

				var obj = Ext.decode(response.responseText);
				if (obj.success == true) {

					var form = win.down('form').getForm();

					form.setValues(obj.data[0]);

					win.show();

				} else {
					Ext.MessageBox.alert('错误', obj.msg);
				}
				el.unmask();
			}
		});

	}

	,

	/**
	 * 项目基本相对路径
	 */
	projectBasePath : location.pathname.substring(0, location.pathname
			.lastIndexOf('/')
			+ 1),

	/**
	 * 项目基本url路径
	 * 
	 * @type {}
	 */
	baseAppUrl : 'http://'
			+ location.host
			+ location.pathname.substring(0, location.pathname.lastIndexOf('/')
					+ 1),

	gainIndexMessage : function() {

		Ext.Ajax.request({
			timeout : 60000,
			url : "selectSysTip.do",
			success : function(response, option) {

				var obj = Ext.decode(response.responseText);

				if (obj.success == true) {
					Ext.get("sysTipP").dom.innerHTML = obj.data[0].fvalue;
					var a = Ext.get("sysTipP").dom;
					var b = a.parentNode;
					if(!Ext.isEmpty(b)){
						b.setAttribute('style','margin-LEFT:100px;TEXT-align: center;POSITION: absolute;height:30px;width: 70%;color: red;')
					}
				}

			}
		});

	},

	myInitF : function() {

		var me = this;

		Ext.require('DJ.tools.css.MyCssHelper', function() {

			// 初始化设置要改toolbar按钮样式的grid
			MyCssHelper.setThoseDefaultGrids();

		});

		me.addDownLoadCommunicationTerPath();
		me.addCommunicationLinkPath();

	},

	addUserRelButtons : function() {

		Ext.Ajax.request({
			timeout : 6000,

			params : {

			},

			url : "getIsAuthenticationByCustomer.do",
			success : function(response, option) {

				var obj = Ext.decode(response.responseText);
				if (obj.success == true) {
					if (obj.total == 1) {

						Ext.DomHelper
								.insertFirst(
										Ext.get(Ext.query('#north table')[2])
												.down('tr'),
										'<td><div><button disabled="disabled" style="color:#FFFFFF;text-shadow:inherit;background:#99FF00;border-radius:25px;font-size:18px;">已认证</button></div></td><td>&nbsp;&nbsp;</td>');

					} else {

						var fid = obj.msg;
						
						Ext.DomHelper
								.insertFirst(
										Ext.get(Ext.query('#north table')[2])
												.down('tr'),
										'<td><div><button id = "doUAButton" style="color:#FFFFFF;text-shadow:inherit;background:#FF0000;border-radius:25px;font-size:18px;"  onclick="DJ.tools.mainPageRel.IndexMessageRel.doUA(\''
												+ fid
												+ '\')">V 立即申请认证</button></div></td><td>&nbsp;&nbsp;</td>');

					}

					Ext.DomHelper
							.insertFirst(
									Ext.get(Ext.query('#north table')[2])
											.down('tr'),
									'<td><div><a style="font-size:18px"  href="javascript:DJ.tools.mainPageRel.IndexMessageRel.openUserCenter()">用户中心</a></div></td><td>&nbsp;&nbsp;</td>');

				} else {
					Ext.MessageBox.alert('错误', obj.msg);
				}
//				el.unmask();
			}
		});

	},

	addDownLoadCommunicationTerPath : function() {

//		var href = 'IMsetup.exe';
//
//		Ext.DomHelper
//				.insertFirst(
//						Ext.get(Ext.query('#north table')[2]).down('tr'),
//						'<td><div> <a id="communicationTerPath" href="'
//								+ href
//								+ '" style="color: green;">下载在线沟通客户端</a></div> </td><td>&nbsp;&nbsp;</td>');

	}

	,

	/**
	 * 添加在线沟通链接
	 */
	addCommunicationLinkPath : function() {

		var me = this;
		me.addUserRelButtons();
//		var urlT = '';
//		Ext.Ajax.request({
//			url : 'OnlineConversation.do',
//			success : function(response, option) {
//				var obj = Ext.decode(response.responseText);
//				if (obj.success == true) {
//
//					urlT = "djbz:sendmsg?uid=" + obj.data.fimid + "&sessionId="
//							+ obj.data.sessionid;
//
//					Ext.DomHelper
//							.insertFirst(
//									Ext.get(Ext.query('#north table')[2])
//											.down('tr'),
//									'<td><div> <a href="'
//											+ urlT//在线沟通
//											+ '" style="color: green;"></a></div> </td><td>&nbsp;&nbsp;</td>');
//
//					me.addUserRelButtons();
//
//				} else {
//					Ext.MessageBox.alert('错误', obj.msg);
//				}
//			}
//		});
	},

	gainIndexMessageInterval : function(delay) {

		var bodyT = Ext.query("td",
				Ext.query("table[class=banner]>tbody>tr")[0])[0];

		// 放最上面有无法显示的问题，可能是被图片挡着了。
		// var bodyT = Ext.query("body")[0];

		var t = new Ext.Template('<div id = "systip" style="text-align: center;position: absolute;width: 100%;color: red;"> <p id = "sysTipP"></p></div>');

		t.compile();

		t.insertFirst(bodyT);

		var me = this;

		// 先获取一次，避免中空
		me.gainIndexMessage();

		return setInterval(function() {

			me.gainIndexMessage();

		}, delay);
	},

	/**
	 * 设置当前活动页面，并可以附带点击按钮。 与MainTabPanel耦合
	 * 
	 * @param {}
	 *            id
	 * @param {}
	 *            btnSelector,按钮 panle down查找器
	 */
	setActionItem : function(id, btnSelector) {

		var backC = Ext.getCmp(id);

		if (!backC) {

			backC = Ext.create(id);

		}

		if (!MainTabPanel.contains(backC)) {

			MainTabPanel.add(backC);

		}

		MainTabPanel.setActiveTab(backC); // 设置当前tab页
		MainTabPanel.doLayout(true);

		if (btnSelector) {

			var btn = backC.down(btnSelector);

			btn.handler.call(btn);

		}

	},

	/**
	 * 覆写类
	 */
	overrideClasses : function() {

		var me = this;

		// 设置c.grid本地状态持久化
		me._enableGridStateful();

		// me._setBaseEditwinConstrainHeader();
		// me._setCQueryUIEnterSearchAction();

	},

	/**
	 * 建议有需要的页面单独配置
	 */
	// _setBaseEditwinConstrainHeader : function() {
	//	
	// Ext.override(Ext.c.BaseEditUI, {
	//		
	//			
	// constrainHeader : true
	//			
	// });
	//		
	// },
	_setCQueryUIEnterSearchAction : function() {

		var myOnKeyDown = function(e) {
			var me = this, shift, focusables, first, last;

			// If tabbing off either end, wrap round.
			// See Ext.dom.Element.isFocusable
			// Certain browsers always report tabIndex zero in
			// the
			// absence of the tabIndex attribute.
			// Testing the specified property (Standards:
			// http://www.w3.org/TR/DOM-Level-2-Core/core.html#ID-862529273)
			// Should filter out these cases.
			// The exceptions are IE6 to IE8. In these browsers
			// all
			// elements will yield a tabIndex
			// and therefore all elements will appear to be
			// focusable.
			// This adversely affects modal Floating components.
			// These listen for the TAB key, and then test
			// whether
			// the event target === last focusable
			// or first focusable element, and forcibly to a
			// circular navigation.
			// We cannot know the true first or last focusable
			// element, so this problem still exists for IE6,7,8
			if (e.getKey() == Ext.EventObject.TAB) {
				shift = e.shiftKey;
				focusables = me.el.query(':focusable');
				first = focusables[0];
				last = focusables[focusables.length - 1];
				if (first && last && e.target === (shift ? first : last)) {
					e.stopEvent();
					(shift ? last : first).focus(false, true);
				}
			}

			if (e.getKey() == Ext.EventObject.ENTER) {

				var com = me.down('button[text=确    定]');

				com.handler.call(com);

			}
		}

		// onSpecialKey

		var myOnSplKey = function(ed, field, e) {
			var sm;

			if (e.getKey() === e.TAB) {
				e.stopEvent();

				if (ed) {
					// Allow the field to act on tabs before
					// onEditorTab, which ends
					// up calling completeEdit. This is useful for
					// picker type fields.
					ed.onEditorTab(e);
				}
				sm = ed.up('tablepanel').getSelectionModel();
				if (sm.onEditorTab) {
					if (ed.field.name == "fdescription")// 切换到下一行时，把材料赋值过去
					{
						var bstore = ed.up('tablepanel').getStore();
						var srecord = sm.getSelection()[0];
						var recordb = bstore.getAt(bstore.indexOf(srecord) + 1);
						if (recordb && Ext.isEmpty(recordb.get("fmaterialfid"))) {// 没有选过把材料赋值
							recordb.set('fmaterialfid', srecord
									.get("fmaterialfid"));
							recordb.set('fmaterialname', srecord
									.get("fmaterialname"));
							recordb.set('farrivetime', srecord
									.get("farrivetime"));
						}

					}

					return sm.onEditorTab(ed.editingPlugin, e);
				}
			}

			if (e.getKey() == Ext.EventObject.ENTER) {

				var com = me.down('button[text=确    定]');

				com.handler.call(com);

			}
		}

		Ext.override(Ext.c.QueryUI, {

			listeners : {

				afterrender : function(com, eOpts) {

					var grid = com.down('grid');

					grid.on('cellkeydown', function(me, td, cellIndex, record,
							tr, rowIndex, e, eOpts) {

						if (e.getKey() == Ext.EventObject.ENTER) {

							com.down('button[text=确    定]').handler.call(com
									.down('button[text=确    定]'))

						}

					});

					// MyCommonToolsZ.setComAfterrender(grid, function(com) {
					//
					// // com.plugins[0].onKeyDown = myOnKeyDown;
					//						
					// com.plugins[0].onSpecialKey = myOnSplKey;
					//
					// });

					grid.onKeyDown = myOnKeyDown;

				}

			},

			// Listen for TAB events and wrap round if tabbing of either end of
			// the Floater
			onKeyDown : myOnKeyDown

		});

	},

	/**
	 * 设置c.grid本地状态持久化
	 */
	_enableGridStateful : function() {

		var me = this;

		// Ext.override(Ext.c.GridPanel, {
		//
		// // stateful : true
		//
		// });

		// me._setStatefulByCondition();

		Ext.override(Ext.c.data.Store, {

			statefulFilters : true

		});

		Ext.override(Ext.grid.column.Column, {

			getStateId : function() {
				var me = this;

				// 表头的dataIndex可以作为唯一性标识
				return me.dataIndex || me.stateId
						|| (me.autoGenId ? null : me.id);
			}

		});

		/**
		 * ctable配置持久化
		 */

		Ext.override(Ext.c.Table, {

			statefulFilters : true

		});

	},

	_setStatefulByCondition : function() {
		/**
		 * 需要持久化的界面
		 */
		var ids = ['DJ.order.Deliver.FistproductdemandList',
				'DJ.System.product.CustproductCustTreeList',

				'DJ.order.Deliver.DeliversCustList',
				'DJ.order.Deliver.custGenerateDeliversList',
				'DJ.Inv.Storebalance.CustomerStorebalanceList',
				'DJ.order.Deliver.ActualDeliverRpt',
				'DJ.System.Customer.SubUserList',
				'DJ.System.Customer.SubRoleList',
				'DJ.order.Deliver.MystockListGrid',
				'DJ.order.Deliver.DeliversBoardList',

				'DJ.order.Deliver.DeliversList',
				'DJ.order.Deliver.DeliversOrderList',
				'DJ.order.Deliver.DeliverorderList',
//				'DJ.order.Deliver.FTUsaledeliverList',

				'DJ.order.saleOrder.OrderAffirmedList',
				'DJ.order.saleOrder.MyDeliveryList',
				'DJ.order.logistics.LogisticsOrderList',
				'DJ.order.logistics.LogisticsOrderManageList'];

		Ext.require(ids, function() {
			Ext.each(ids, function(ele, index, all) {

				Ext.override(Ext.ClassManager.get(ele), {

					stateful : true

				});

			});
		});

	},

	loadClasses : function() {

		var me = this;

		Ext.require(['Ext.ux.form.TextAreaTranslateBrAuto',
				'DJ.myComponent.button.MyExcelExporterCusCfgButton',
				'Ext.ux.grid.print.MySimpleGridPrinterComponent',
				'Ext.ux.form.MyMixedSearchBox',
				'Ext.ux.form.MySimpleSearcherCombobox',
				'DJ.myComponent.button.MySimpleUpLoadExcelWinButton',
				'Ext.ux.form.MyDateTimeSearchBox',
				'DJ.tools.common.MyCommonToolsZ',
				'DJ.myComponent.tree.LeftTree', 'DJ.tools.grid.MyGridTools',
				'Ext.ux.grid.MyGridShortcutKeyPlugin',
				'Ext.ux.form.MySimpleButtonGroupFilter',
				'Ext.ux.grid.column.MySimpleImageColumn',
				'Ext.ux.grid.column.MySimpleVMultiTextActionColumn',
				'Ext.ux.grid.column.MySimpleVMultiTextColumn',
				'DJ.tools.common.MySimpleHTMLToWord',
				'Ext.ux.grid.MyGridItemTipPlugin',
				'Ext.ux.form.OneClickFileFieldForMulitiChoice'], function() {

			// 需要在基本支持类全部加载后才能进行操作
			me._setStatefulByCondition();

			me._setCQueryUIEnterSearchAction();

				// me._setSpecClassForIE();

			});

	}
// ,
//	
// _setSpecClassForIE : function() {
//	
// var is
//		
// Ext.require()
//		
// }

// ,

// addCusVTypes : function () {
//	
// Ext.apply(Ext.form.field.VTypes, {
//		
// lineBallFormula : function (v) {
//			
// var numGT
//				
// }
//			
// });
//		
// }

});