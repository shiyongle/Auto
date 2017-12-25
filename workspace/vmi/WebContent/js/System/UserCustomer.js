Ext.require(['Ext.grid.*', 'Ext.toolbar.Paging', 'Ext.util.*', 'Ext.data.*',
		'Ext.ux.form.SearchField', 'Ext.selection.CheckboxModel']);

function formatEffect(value) {
	return value == '1' ? '是' : '否';
}
function formatStatus(value) {
	return value == '1' ? '核准' : (value == '0' ? '未核准' : '禁用');
}
var recordIds = new Array();
var deSelection = function(sm, rec) {
	recordIds.pop(rec.data.fid);

};
var selModel = Ext.create('Ext.selection.CheckboxModel', {
			checkOnly : true,
			listeners : {
				select : function(sm, selection, num, opts) {
					if (Ext.Array.contains(recordIds, selection.data.fid)) {

					} else {
						recordIds.push(selection.data.fid);
					}

				},
				deselect : deSelection
			}
		});

var rostore = Ext.create('Ext.data.Store', {
	fields : [ {
		name : 'fid'
	}, {
		name : 'fname'
	}, {
		name : 'fnumber'
	}, {
		name : 'fdescription'
	}, {
		name : 'fcreatetime'
	}, {
		name : 'findustryid'
	}, {
		name : 'fisinternalcompany'
	},{
		name : 'faddress'
	}, {
		name : 'fusedstatus'
	},{
		name:'ftype'
	} ],
			pageSize : 15,
			proxy : {
				type : 'ajax',
				url : 'GetUserCustomer.do',
				reader : {
					type : 'json',
					root : 'data',
					totalProperty : 'total'
				}
			},
			autoLoad : false,
			listeners : {
				load : function(store, records, succ, eOpts) {
					selModel.removeListener('deselect');
					var selections = new Array();
					store.each(function(record) {
								if (recordIds.length > 0) {
									for (i = 0; i < recordIds.length; i++) {
										var id = recordIds[i];
										if (record.data.fid == id) {
											selections.push(record);
											break;
										}
									}
								}
								if (record.data.ftype == '1') {
									selections.push(record);
								}

							});
					selModel.select(selections, true);
					selModel.addListener('deselect', deSelection);
				}
			}
		});
Ext.define('DJ.System.UserCustomer.GridList', {
			extend : 'Ext.grid.Panel',
			id : 'DJ.System.UserCustomer.GridList',
			initComponent : function() {
				Ext.apply(this, {
							selModel : selModel,
							columnLines : true,
							disableSelection : false,// 值为TRUE，表示禁止选择行
							width : 650,
							height : 390,
							emptyMsg : "没有数据显示",
							dockedItems : [{
										xtype : 'pagingtoolbar',
										store : rostore,
										dock : 'bottom',
										displayInfo : true
									}],
							autoExpandColumn : "fdescription",
							store : rostore,
							columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
								text : 'fid',
								dataIndex : 'fid',
								hidden : true,
								hideable : false,
								sortable : true

							}, {
								text : '编码',
								dataIndex : 'fnumber',
								sortable : true
							
							}, {
								text : '客户名称',
								dataIndex : 'fname',
								sortable : true
							}, {
								text : '行业',
								dataIndex : 'findustryid',
								sortable : true
							}, {
								text : '地址',
								dataIndex : 'faddress',
								sortable : true,
								width : 250
							}, {
								text : '内部客户',
								dataIndex : 'fisinternalcompany',
								renderer : formatEffect,
								sortable : true
							}, {
								text : '状态',
								dataIndex : 'fusedstatus',
								renderer : formatStatus,
								sortable : true
							}, {
								text : '创建时间',
								dataIndex : 'fcreatetime',
								width : 150,
								sortable : true

							}]
						}), this.callParent(arguments);
			}
		});
function SaveUserCustomer(btn) {

//	if (recordIds.length == 0) {
//		Ext.MessageBox.alert('提示', '请先选择您要分配的用户!');
//		return;
//	}
	var ids = "";
	var rfid = Ext.getCmp("DJ.System.UserCustomer.UserFID").getValue();
	var ftype = Ext.getCmp("DJ.System.UserCustomer.Ftype").getValue();
	for (var i = 0; i < recordIds.length; i++) {
		ids += recordIds[i];
		if (i < recordIds.length - 1) {
			ids = ids + ",";
		}
	}
	var me=Ext.getCmp("DJ.System.UserCustomer");
var el = me.getEl();
el.mask("系统处理中,请稍候……");
	Ext.Ajax.request({
				url : "SaveUserCustomer.do",
				params : {
					ftype : ftype,
					fuserid : rfid,
					fcustid : ids
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						Ext.MessageBox.alert('成功', obj.msg);
						Ext.getCmp("DJ.System.UserCustomer").hide();
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});

}

Ext.define('DJ.System.UserCustomer', {
			extend : 'Ext.Window',
			id : 'DJ.System.UserCustomer',
			modal : true,
			closeAction : 'hide',
			title : "关联客户",
			width : 800,// 230, //Window宽度
			height : 520,// 137, //Window高度
			resizable : false,
			defaults : {
				xtype : 'textfield'
			},
			closable : true, // 关闭按钮，默认为true
			items : [{
						id : 'DJ.System.UserCustomer.UserFID',
						fieldLabel : 'FID',
						hidden : true
					}, {
						id : 'DJ.System.UserCustomer.UserName',
						fieldLabel : '用户',
						readOnly : true,
						width : 200,
						labelWidth : 45
					},{
						id : 'DJ.System.UserCustomer.Ftype',
						hidden : true
					}, Ext.create("DJ.System.UserCustomer.GridList")],
			buttons : [{
						id : 'DJ.System.UserCustomer.SaveButton',
						xtype : "button",
						text : "确定",
						pressed : false,
						handler : SaveUserCustomer
					}, {
						id : 'DJ.System.UserCustomer.ColseButton',
						xtype : "button",
						text : "取消",
						handler : function() {
							var windows = Ext.getCmp("DJ.System.UserCustomer");
							if (windows != null) {
								windows.hide();
							}
						}

					}],
			listeners : {
				'hide' : function() {
					recordIds = new Array();
				}
			},
			buttonAlign : "center",
			bodyStyle : "padding-top:5px ;padding-left:30px;padding-right:30px"

		});