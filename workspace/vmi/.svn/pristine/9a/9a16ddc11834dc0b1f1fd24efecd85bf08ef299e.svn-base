function formatfusedstatus(value) {
	return value == '1' ? '启用' : '禁用';
}
function onEffectButtonClick(btn) {
	var grid = Ext.getCmp("DJ.System.VmipdtParamView");// DJ.System.VmipdtParamList
	var feffect = 0;
	if (Ext.util.Format.trim(btn.text) == "启用") {
		feffect = 1;
	}
	var record = grid.getSelectionModel().getSelection();
	if (record.length == 0) {
		Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
		return;
	}
	Ext.MessageBox.confirm("提示", "是否确认"+Ext.util.Format.trim(btn.text)+"选中的用户?", function(btn) {
				if (btn == 'yes') {
					var ids = "";
					for (var i = 0; i < record.length; i++) {
						var fid = record[i].get("fid");
						ids +=fid ;
						if (i < record.length - 1) {
							ids = ids + ",";
						}
					}
					
					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
								url : "EffectVmipdtparamList.do",
								params : {
									feffect : feffect,
									fidcls : ids
								}, // 参数
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
//										Ext.MessageBox.alert('成功', obj.msg);
										djsuccessmsg( obj.msg);
										grid.store.load();
									} else {
										Ext.MessageBox.alert('错误', obj.msg);
									}
									el.unmask();
								}
							});
				}
			});
}
//列表
Ext.define('DJ.System.VmipdtParamView', {
			extend : 'Ext.c.GridPanel',
			title : "产品安全库存",
			id : 'DJ.System.VmipdtParamView',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetVmipdtParamList.do',
			Delurl : "DelVmipdtParamList.do",
			EditUI : "DJ.System.VmipdtParamEdit",
			exporturl:"vmitoexcel.do",
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
			},
			Action_BeforeAddButtonClick : function(EditUI) {
				// 新增界面弹出前事件
//				alert("新增前");
			},
			Action_AfterAddButtonClick : function(EditUI) {
				// 新增界面弹出后事件
//				alert("新增后");
			},
			Action_BeforeEditButtonClick : function(EditUI) {
				// 修改界面弹出前事件
			},
			Action_AfterEditButtonClick : function(EditUI) {
				// 修改界面弹出后事件
				// 可对编辑界面进行复制
			},
			Action_BeforeDelButtonClick : function(me, record) {
				// 删除前事件
			},
			Action_AfterDelButtonClick : function(me, record) {
				// 删除后事件
			},
			custbar : [{
						// id : 'DelButton',
						text : '启用',
						height : 30,
						handler : onEffectButtonClick
					}, {
						// id : 'DelButton',
						text : '禁用',
						height : 30,
						handler : onEffectButtonClick
					}],
			fields : [
			          	{
							name : 'fid'
						}, {
							name : 'fcreatorid'
						},{
							name : 'fcreator'
						}, {
							name : 'fcreatetime'
						}, {
							name : 'flastupdateuserid'
						}, {
							name : 'flastupdater'
						}, {
							name : 'flastupdatetime'
						}, {
							name : 'fcustomerid'
						}, {
							name : 'fcustname',
							myfilterfield : 'c.fname',
							myfiltername : '客户名称',
							myfilterable : true
						}, {
							name : 'fproductid'
						}, {
							name : 'fpdtname',
							myfilterfield : 'd.fname',
							myfiltername : '产品名称',
							myfilterable : true
						}, {
							name : 'fmaxstock'
						}, {
							name : 'forderamount'
						}, {
							name : 'fminstock'
						}, {
							name : 'fbalanceqty'
						}, {
							name : 'fproducedqty'
						}, {
							name : 'fdescription'
						}, {
							name : 'fcontrolunitid'
						}, {
							name : 'fefected'
						}, {
							name : 'fsupplier',
							myfilterfield : 'sp.fname',
							myfiltername : '制造商',
							myfilterable : true
						}, {
							name : 'fsupplierId'
						}, {
							name : 'ftype'
						}
					],
			columns : [
						{
							'dataIndex' : 'fid',
							'header' : 'fid',
							hidden : true,
							hideable : false,
							sortable : true
						}, {
							'dataIndex' : 'fcreatorid',
							'header' : 'fcreatorid',
							hidden : true,
							hideable : false,
							sortable : true
						}, {
							'dataIndex' : 'fcustomerid',
							'header' : 'fcustomerid',
							hidden : true,
							hideable : false,
							sortable : true
						}, {
							'dataIndex' : 'fcustname',
							'header' : '客户名称',
							sortable : true
						}, {
							'dataIndex' : 'fproductid',
							'header' : 'fproductid',
							hidden : true,
							hideable : false,
							sortable : true
						}, {
							'dataIndex' : 'fpdtname',
							'header' : '产品名称',
							sortable : true
						}, {
							'dataIndex' : 'fsupplier',
							'header' : '制造商',
							sortable : true
						}, {
							'dataIndex' : 'fsupplierId',
							'header' : '制造商',
							hidden : true,
							sortable : true
						},{
							'header' : '启用',
							dataIndex : 'fefected',
							width : 40,
							sortable : true,
							renderer: formatfusedstatus
						}, {
							'dataIndex' : 'fmaxstock',
							'header' : '最大库存量',
							sortable : true
						}, {
							'dataIndex' : 'forderamount',
							'header' : '下单批量',
							sortable : true
						}, {
							'dataIndex' : 'fminstock',
							'header' : '最小库存量',
							sortable : true
						}, {
							'dataIndex' : 'ftype',
							'header' : '下单类型',
							sortable : true,
							renderer : function(value) {
								if (value == 1) {
									return '订单';
								} else {
									return '通知';
								}
							}
						}, {
							'dataIndex' : 'fbalanceqty',
							'header' : '库存数量',
							hidden : true,
							sortable : true
						}, {
							'dataIndex' : 'fproducedqty',
							'header' : '生产数量',
							hidden : true,
							sortable : true
						}, {
							'dataIndex' : 'fdescription',
							'header' : '备注',
							sortable : true
						},{
							'dataIndex' : 'fcreator',
							'header' : '创建人',
							sortable : true
						}, {
							'dataIndex' : 'fcreatetime',
							'header' : '创建时间',
							width : 200,
							sortable : true
						}, {
							'dataIndex' : 'flastupdateuserid',
							'header' : 'flastupdateuserid',
							hidden : true,
							hideable : false,
							sortable : true
						}, {
							'dataIndex' : 'flastupdater',
							'header' : '最后修改人',
							sortable : true
						}, {
							'dataIndex' : 'flastupdatetime',
							'header' : '最后修改时间',
							width : 200,
							sortable : true
						}, {
							'dataIndex' : 'fcontrolunitid',
							'header' : '描述',
							width : 200,
							sortable : true
						}
					],
					selModel:Ext.create('Ext.selection.CheckboxModel')
		});

Ext.define('DJ.System.VmipdtParamList',{
	extend:'DJ.System.MainTreePanel',
	id:'DJ.System.VmipdtParamList',
	title:'产品安全库存',
	grid:'DJ.System.VmipdtParamView'
});