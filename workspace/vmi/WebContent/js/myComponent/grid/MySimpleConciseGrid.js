/**
 * 
 * 简明表格 简明分页组件
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2015-6-13 下午6:37:43
 * 
 */
Ext.define("DJ.myComponent.grid.MySimpleConciseGrid", {
	extend : "Ext.grid.Panel",

	pagingtoolbarDock : 'top',

	cusPagingBar : [],

	custbar : [],

	pageSize : 15,

	mixins : ['DJ.tools.grid.MySimpleGridMixer',
			'DJ.tools.grid.mixer.MyGridSearchMixer'],

	xtype : "widget.mysimpleconcisegrid",
	// EditUI:"",
	initComponent : function() {
		var me = this;
		if (me.id == "") {
			me.id = Ext.id();
		}

		Ext.applyIf(me, {
			dockedItems : [{
				xtype : 'toolbar',
				dock : 'top',
				style : {
					background : 'white'

				},
				border : 0,
				items : me.cusTopBarItems
			}]
		});

		me.store = Ext.create("Ext.c.data.Store", {
			fields : me.fields,
			pageSize : me.pageSize,
			url : me.url,
			autoLoad : true,
			remoteSort : me.remoteSort ? me.remoteSort : false,
			listeners : {
				exception : function(dataProxy, response, action, options) {
					var o = Ext.decode(response.responseText)
					if (!o.success) {
						Ext.Msg.alert('错误提示', o.msg);
					}
				}
			}
				// 设置属性进行请求后台排序
				});

		// me.pageSize = 200;
		// createmGridPanelstore(me);

		me.dockedItems.push({
			xtype : "pagingtoolbar",
			store : me.store, // same store GridPanel
			// is using
			dock : me.pagingtoolbarDock,
			displayInfo : true,
			afterPageText : '/{0}',

			style : {
				background : 'white'
			},

			// border : 0,

			// ui : '',

			listeners : {

				afterrender : function(com, eOpts) {

					var paging = com;

					// console.log(paging.items.items);

					Ext.each(paging.items.items, function(ele, index, all) {

						if (index == 1) {
						} else if (index == 7 || index == 4 || index == 15
								|| index == 14 || index == 13 || index == 12
								|| index == 10) {
						} else if (index == 5) {

						} else {
							ele.hide();
						}

					});

					var cusPagingBar = me.cusPagingBar;

					cusPagingBar.push({
						xtype : 'tbspacer',
						flex : 1
					});

					com.add(0, cusPagingBar);

				}

			}

		});

		this.callParent(arguments);
		me.onload();
	},

	
	doAddAction : function(c1) {

		if (c1.EditUI == null || Ext.util.Format.trim(c1.EditUI) == _$[142]) {
			// Ext.MessageBox.alert(_$[143], _$[144]);
			return
		};
		try {
			c1.Action_BeforeAddButtonClick(c1);
			c1.Action_AddButtonClick(c1);
			c1.Action_AfterAddButtonClick(c1)
		} catch (e) {
			Ext.MessageBox.alert('错误', e)
		}

	},
	doEditAction : function(c1) {

		if (c1.EditUI == null || Ext.util.Format.trim(c1.EditUI) == _$[142]) {
			// Ext.MessageBox.alert(_$[143], _$[144]);
			return
		};
		try {
			c1.Action_BeforeEditButtonClick(c1);
			c1.Action_EditButtonClick(c1);
			c1.Action_AfterEditButtonClick(c1)
		} catch (e) {
			Ext.MessageBox.alert('错误', e)
		}

	},

	doDeleteAction : function(c1) {

		if (c1.Delurl == null || Ext.util.Format.trim(c1.Delurl) == '') {
			// Ext.MessageBox.alert('', _$[164]);
			return
		};
		var c3 = c1.getSelectionModel().getSelection();
		if (c3.length == 0) {
			Ext.MessageBox.alert('提示', '请选择');
			return
		};
		try {
			Ext.MessageBox.confirm('确定', '确定删除？', function(c4) {
				if (c4 == 'yes') {
					c1.Action_BeforeDelButtonClick(c1, c3);
					c1.Action_DelButtonClick(c1, c3);
					c1.Action_AfterDelButtonClick(c1, c3)
				}
			})
		} catch (e) {
			Ext.MessageBox.alert('失败', e)
		}

	},

	onload : function() {
		var me = this;
	},
	Action_BeforeAddButtonClick : function(me) {
	},
	Action_AfterAddButtonClick : function(me) {
	},
	Action_AddButtonClick : function(me) {
		// Ext.MessageBox.alert('提示', EditUI);
		// onAddButtonClick();
		// Ext.MessageBox.alert('提示', '子类事件');
		var editui = Ext.create(me.EditUI);
		editui.seteditstate("add");
		editui.setparent(me.id);
		editui.show();

	},
	Action_BeforeEditButtonClick : function(me) {
	},
	Action_AfterEditButtonClick : function(me) {
	},
	Action_BeforeViewButtonClick : function(me) {
	},
	Action_AfterViewButtonClick : function(me) {
	},
	loadfields : function(editui, id) {
		var url = "";
		if (editui.editstate === "view" || editui.editstate === "") {
			url = editui.viewurl;
		} else {
			url = editui.infourl;
		}

		if (url == null || Ext.util.Format.trim(url) == "") {
			throw "没有指定数据获取路径";
		};
		Ext.Ajax.request({
			url : url,
			params : {
				fid : id
			}, // 参数
			success : function(response, option) {
				var obj = Ext.decode(response.responseText);
				if (obj.success == true) {
					if (obj.data instanceof Array) {
						editui.getform().setValues(obj.data[0], id);
						editui.editdata = obj.data[0];
					} else {
						editui.getform().setValues(obj.data, id);
						editui.editdata = obj.data;
					}
					editui.onloadfields();
					editui.show();
				} else {
					Ext.MessageBox.alert("成功", obj.msg);
					editui.close();
				}
			},
			failure : function(response, option) {

				if (option.failureType == "server") {
					Ext.MessageBox.alert("成功", option.result.msg);
					editui.close();
				} else if (option.failureType == "connect") {
					Ext.MessageBox.alert("成功", "无法连接服务器");
					editui.close();
				} else if (option.failureType == "client") {
					Ext.MessageBox.alert("成功", "数据错误，非法提交");
					editui.close();
				} else {
					// 其它类型的错误
					Ext.MessageBox.alert("成功", "服务器数据传输失败:"
							+ option.response.responseText);
					editui.close();
				}
			}
		});
	},
	Action_EditButtonClick : function(me) {
		// Ext.MessageBox.alert('提示', EditUI);
		// onAddButtonClick();
		// Ext.MessageBox.alert('提示', '子类事件');
		var record = me.getSelectionModel().getSelection();
		if (record.length == 0) {
			throw "请先选择您要操作的行!";
		};
		var editui = Ext.create(me.EditUI);
		editui.loadfields(record[0].get("fid"));
		editui.seteditstate("edit");
		editui.setparent(me.id);
		me.loadfields(editui, record[0].get("fid"));
		editui.show();
	},
	Action_BeforeDelButtonClick : function(me, record) {
	},
	Action_AfterDelButtonClick : function(me, record) {
	},
	Action_DelButtonClick : function(me, record) {
		var ids = "";
		for (var i = 0; i < record.length; i++) {
			var fid = record[i].get("fid");
			ids += fid;
			if (i < record.length - 1) {
				ids = ids + ",";
			}
		}
		var el = me.getEl();
		el.mask("系统处理中,请稍候……");
		Ext.Ajax.request({
			url : me.Delurl,
			params : {
				fidcls : ids
			}, // 参数
			success : function(response, option) {
				var obj = Ext.decode(response.responseText);
				if (obj.success == true) {
					// Ext.MessageBox.alert("成功", obj.msg);
					djsuccessmsg(obj.msg);
					el.unmask();
					me.store.load();
				} else {
					Ext.MessageBox.alert("错误", obj.msg);
					el.unmask();
					me.store.load();
				}
			}
		});

	}

	,
	selModel : Ext.create('Ext.selection.CheckboxModel')

});
