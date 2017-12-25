var checkResultType = Ext.create('Ext.data.Store', {
	fields : [ 'typevalue', 'typename' ],
	data : [ {
		"typevalue" : "1",
		"typename" : "合格"
	}, {
		"typevalue" : "2",
		"typename" : "不合格"
	} ]
});
Ext.define('DJ.Oqa.QualityCheck.QualityCheckEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : 'DJ.Oqa.QualityCheck.QualityCheckEdit',
	modal : true,
	title : "质量管理界面",
	width : 400,// 230, //Window宽度
	height : 250,// 137, //Window高度
	resizable : false,
	url : 'SaveQualityCheck.do',
	infourl : 'GetQualityCheckInfo.do',
	viewurl : 'GetQualityCheckInfo.do',
	closable : true, // 关闭按钮，默认为true
	showLock : false,
	onload : function() {
	},
	layout : {
		type : 'vbox',
		align : 'stretch',
		pack : 'center',
		defaultMargins : {
			top : 0,
			right : 0,
			bottom : 0,
			left : 40
		}
	},

	initComponent : function() {
		Ext.apply(this, {
			items : [ {
				name : 'fnumber',
				fieldLabel : '编号',
				xtype : 'textfield',
				labelWidth : 80,
				width:300
			}, {
				width:300,
					 name:'fdeliverid',
					 fieldLabel : '要货计划',
					 allowBlank : false,
 	        	    blankText:'请选择要货计划',
					 valueField : 'fid', // 组件隐藏值
					 xtype : 'cCombobox',
					 labelWidth : 80,
					 editable: false,
					 displayField : 'fnumber',// 组件显示值
					 MyConfig : {
					 width : 800,// 下拉界面
					 height : 200,// 下拉界面
					 url : 'GetDeliversList.do', // 下拉数据来源
					 fields : [{
							name : 'fid'
						}, {
							name : 'faddress',
							myfilterfield : 'd.faddress',
							myfiltername : '配送地址',
							myfilterable : true
						}, {
							name : 'fnumber',
							myfilterfield : 'd.fnumber',
							myfiltername : '采购订单号',
							myfilterable : true
						}, {
							name : 'fcreatorid'
						}, {
							name : 'fupdateuserid'
						}, {
							name : 'fcustomerid'
						}, {
							name : 'fcusproductid'
						}, {
							name : 'fcustname',
							myfilterfield : 'c.fname',
							myfiltername : '客户名称',
							myfilterable : true
						}, {
							name : 'cutpdtname',
							myfilterfield : 'cpdt.fname',
							myfiltername : '客户产品',
							myfilterable : true
						}, {
							name : 'flinkman',
							myfilterfield : 'd.flinkman',
							myfiltername : '联系人',
							myfilterable : true
						}, {
							name : 'flinkphone',
							myfilterfield : 'd.flinkphone',
							myfiltername : '联系电话',
							myfilterable : true
						}, {
							name : 'famount'
						}, {
							name : 'fdescription'
						}, {
							name : 'farrivetime',
							myfilterfield : 'd.farrivetime',
							myfiltername : '配送时间',
							myfilterable : true
						}, {
							name : 'fcreator'
						}, {
							name : 'flastupdater'
						}, {
							name : 'fcreatetime'
						}, {
							name : 'fupdatetime'
						}],
				columns : [{
							'header' : 'fid',
							'dataIndex' : 'fid',
							hidden : true,
							hideable : false,
							sortable : true

						},{
							'header' : 'fcreatorid',
							'dataIndex' : 'fcreatorid',
							hidden : true,
							hideable : false,
							sortable : true

						}, {
							'header' : 'fupdateuserid',
							'dataIndex' : 'fupdateuserid',
							hidden : true,
							hideable : false,
							sortable : true

						}, {
							'header' : 'fcustomerid',
							'dataIndex' : 'fcustomerid',
							hidden : true,
							hideable : false,
							sortable : true

						}, {
							'header' : 'fcusproductid',
							'dataIndex' : 'fcusproductid',
							hidden : true,
							hideable : false,
							sortable : true

						}, {
							'header' : '客户名称',
							'dataIndex' : 'fcustname',
							sortable : true
						}, {
							'header' : '采购订单号',
							'dataIndex' : 'fnumber',
							sortable : true
						}, {
							'header' : '配送时间',
							'dataIndex' : 'farrivetime',
							width : 150,
							sortable : true
						}, {
							'header' : '联系人',
							'dataIndex' : 'flinkman',
							sortable : true
						}, {
							'header' : '联系电话',
							'dataIndex' : 'flinkphone',
							sortable : true
						}, {
							'header' : '配送数量',
							'dataIndex' : 'famount',
							sortable : true
						},{
							'header' : '配送地址',
							'dataIndex' : 'faddress',
							width : 200,
							sortable : true
						},{
							'header' : '客户产品',
							'dataIndex' : 'cutpdtname',
							sortable : true
						},{
							'header' : '备注',
							'dataIndex' : 'fdescription',
							sortable : true
						},{
							'header' : '创建人',
							'dataIndex' : 'fcreator',
							sortable : true
						}, {
							'header' : '创建时间',
							'dataIndex' : 'fcreatetime',
							width : 150,
							sortable : true
						},{
							'header' : '修改人',
							'dataIndex' : 'flastupdater',
							sortable : true
						}, {
							'header' : '修改时间',
							'dataIndex' : 'fupdatetime',
							width : 150,
							sortable : true
						}]
					 }
			},{
				allowBlank : false,
        	    blankText:'请选择销售订单', 
				name:'fsaleorderid',
				 fieldLabel : '销售订单',
				 valueField : 'fid', // 组件隐藏值
				 xtype : 'cCombobox',
				 labelWidth : 80,
				 width:300,
				 editable: false,
				 displayField : 'fnumber',// 组件显示值
				 MyConfig : {
				 width : 800,// 下拉界面
				 height : 200,// 下拉界面
				 url : 'GetSaleOrders.do', // 下拉数据来源
				 fields : [
				 {
				 name : 'fid'
				 }, {
				 name : 'fnumber'
				 }, {
				 name : 'cname',
				 myfilterfield : 'c.fname',
				 myfiltername : '客户名称',
				 myfilterable : true
				 }, {
				 name : 'pname',
				 myfilterfield : 'p.fname',
				 myfiltername : '客户产品名称',
				 myfilterable : true
				 }, {
				 name : 'fspec'
				 }, {
				 name : 'farrivetime'
				 }, {
				 name : 'fbizdate'
				 }, {
				 name : 'famount'
				 }, {
				 name : 'flastupdatetime'
				 }, {
				 name : 'u2_fname'
				 }, {
				 name : 'fcreatetime'
				 }, {
				 name : 'u1_fname'
				 }
				 ],
				 columns : [Ext.create('DJ.Base.Grid.GridRowNum'),
				 {
				 'header' : 'fid',
				 'dataIndex' : 'fid',
				 hidden : true,
				 hideable : false,
				 sortable : true
				 }, {
				 'header' : '订单编号',
				 'dataIndex' : 'fnumber',
				 sortable : true
				 }, {
				 'header' : '客户名称',
				 'dataIndex' : 'cname',
				 sortable : true
				 }, {
				 'header' : '客户产品名称',
				 'dataIndex' : 'pname',
				 sortable : true
				 }, {
				 'header' : '规格',
				 'dataIndex' : 'fspec',
				 sortable : true
				 }, {
				 'header' : '数量',
				 'dataIndex' : 'famount',
				 sortable : true
				 }, {
				 'header' : '交期',
				 'dataIndex' : 'farrivetime',
				 sortable : true
				 }, {
				 'header' : '业务时间',
				 'dataIndex' : 'fbizdate',
				 sortable : true
				 },{
				 text : '修改人',
				 dataIndex : 'u2_fname',
				 sortable : true
				 }, {
				 text : '修改时间',
				 dataIndex : 'flastupdatetime',
				 width : 150,
				 sortable : true
				 }, {
				 text : '创建人',
				 dataIndex : 'u1_fname',
				 sortable : true
				 }, {
				 text : '创建时间',
				 dataIndex : 'fcreatetime',
				 width : 150,
				 sortable : true
				 }
				 ]
				 }
		},{
			name:'fcheckresult',
			xtype:'combo',
			store:checkResultType,
   			fieldLabel : '检验结果',
   			labelWidth : 80,
   			triggerAction: 'all',
   			displayField: 'typename',
   			valueField: 'typevalue',
   			editable : false, // 可以编辑不
   			value:"1",
   			width:300
		},{
			name:'fquestdescription',
   			fieldLabel : '问题描述',
   			xtype:'textarea',
   			labelWidth : 80,
   			width:300,
   			height: 60
			},{
				name : 'fid',
				xtype : 'hidden'
			} ]

		}), this.callParent(arguments);
	},
	listeners : {
		'beforeshow' : function(win) {
			var cform=win.getform().getForm();
 			if(win.editstate=='add')
 			{
 				cform.findField('fnumber').hide();
 			}
 			cform.findField('fnumber').setReadOnly(true);
 		
		}
	},


	bodyStyle : "padding-left:5px;"
});