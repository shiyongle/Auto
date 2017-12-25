
Ext.define('DJ.order.Deliver.ActualDeliverStatementList', {
	id : 'DJ.order.Deliver.ActualDeliverStatementList',
	extend : 'Ext.c.GridPanel',
	// title : "对账单",
	pageSize : 50,
	url : 'selectFoutedDeliverordersMVByCustomer.do',
	exporturl : "ExcelDeliverStatementList.do",// 导出为EXCEL方法
	selModel : {selType:'checkboxmodel'},
	require:["Ext.ux.form.DoubleDateField"],
	onload : function() {
		var me = this;

		var buttons = this.query('toolbar[dock=top] button[id*=' + me.id + ']');
		Ext.each(buttons,function(button){
			if (button.text == '导出Excel') {
				button.setText('导出');
			}
			button.hide();
		})

		MyGridTools.rebuildExcelAction(me);
		this.store.on('beforeload', function(store, operation, eOpts) {
					if (Ext.isEmpty(me.down('combobox').getValue())) {
						return false;
					}
				})
	},
	queryFtucustproduct : function(startDate, endDate) {
		var me = this;
		var myfilter = [];
		var fsuppliervalue=me.down('combobox').getValue();
		if(Ext.isEmpty(fsuppliervalue)) {Ext.MessageBox.alert("错误","请选择制造商进行查询"); return;}
//		myfilter.push({
//					myfilterfield : "st.fsupplierid",
//					CompareType : " = ",
//					type : "string",
//					value : fsuppliervalue
//				});
		me.store.getProxy().extraParams.fsupplierid =fsuppliervalue;
		me.store.setDefaultmaskstring("");
		var prittimeValues  = me.down('[itemId=fouttime]').getSubmitValues();
		if(prittimeValues){
					myfilter.push({
						myfilterfield : "fouttime",
						CompareType : ">=",
						type : "string",
						value : prittimeValues[0]
					},{
						myfilterfield : "fouttime",
						CompareType : "<=",
						type : "string",
						value : prittimeValues[1]
					});
				me.store.setDefaultmaskstring("  #0 and #1");
		}
		var farrivetimeValues = me.down('[itemId=farrivetime]').getSubmitValues();
		if(prittimeValues&&farrivetimeValues) {Ext.MessageBox.alert("错误","请选择出库时间或配送时间查询，不能同时查询");return;}
				if(farrivetimeValues){
					myfilter.push({
						myfilterfield : "farrivetime",
						CompareType : ">=",
						type : "string",
						value : farrivetimeValues[0]
					},{
						myfilterfield : "farrivetime",
						CompareType : "<=",
						type : "string",
						value : farrivetimeValues[1]
					});
				me.store.setDefaultmaskstring("#0 and #1");

				}
		me.store.setDefaultfilter(myfilter);

		me.store.loadPage(1);
	},
	custbar : [{
				xtype : 'tbspacer'
			}, {
				xtype : 'combobox',
				fieldLabel : '制造商名称',
				labelWidth : 70,
				displayField : 'fname',
				valueField : 'fid',
//				editable : false,
				queryParam:'fname',
				queryDelay:300,
				minChars:'2',
				store : Ext.create('Ext.data.Store', {
							fields : ['fid', 'fname'],
							proxy : {
								type : 'ajax',
								url : 'getSupplierForDeliverApply.do',
								reader : {
									type : 'json',
									root : 'data'
								}
							}
						}),
				listeners : {
					render:function(combo){
						combo.store.load({callback:function(records,operation,success ){
							if(records.length==1){
								combo.setValue(records[0].get('fid'));
//								combo.fireEvent('select',combo,records,combo);
							}
						}})
					},
					select : function(combo, records, eOpts) {
						var grid = combo.up('grid');
						grid.queryFtucustproduct();
					},
					change:function(combo,newValue,oldValue){
						if(Ext.isEmpty(newValue)){
							combo.store.load();
						}
					}
				}
			}, {
				xtype : 'doubledatefield',
				itemId: 'fouttime',
				fieldLabel: '出库时间',
				width: 240,
				labelWidth: 55,
				listeners: {
					change: function(me,val){
						var farrivetimedate=this.up('grid').down('[itemId=farrivetime]');
							farrivetimedate.suspendEvent('change');
							farrivetimedate.setValue("");
							farrivetimedate.resumeEvent('change');
						this.up('grid').queryFtucustproduct();
						
					},render:function(field,eOpts ){
					field.suspendEvent('change');
					field.setValue(Ext.Date.format(Ext.Date.subtract(new Date(), Ext.Date.DAY, new Date().getDate()-1),'Y-m-d') + field.separator+Ext.Date.format(new Date(),'Y-m-d'));
					field.resumeEvent('change');
					}
				}
			}, {
			xtype : 'tbspacer'
			}, {
			xtype : 'doubledatefield',
				itemId: 'farrivetime',
				fieldLabel: '配送时间',
				width: 240,
				labelWidth: 55,
				listeners: {
					change: function(me,val){
						
							var fouttimedate=this.up('grid').down('[itemId=fouttime]');
							fouttimedate.suspendEvent('change');
							fouttimedate.setValue("");
							fouttimedate.resumeEvent('change');
						this.up('grid').queryFtucustproduct();
					}
				}
			},{
				xtype:'container',
				items:[{
					text : '查询',
					xtype:'button',
						handler : function(){
							this.up('grid').queryFtucustproduct();
						}
				}]
			}, '|', {
				text : '导出Excel',
				width : 70,
				height : 30,
				listeners:{
					click:function(button,e){
						if(button.up('grid').store.getCount()===0){
							return false;
						}
						var fsupplierid=button.up('grid').down('combobox').getValue();
						var fsupplier = button.up('grid').down('combobox').rawValue;
						var header = '';
						if(Ext.isEmpty(fsupplierid)||Ext.isEmpty(fsupplier)){
							return false;
						}
						var exportdate= Ext.isEmpty(button.up('grid').down('[itemId=farrivetime]').getSubmitValues())? button.up('grid').down('[itemId=fouttime]').getSubmitValues():button.up('grid').down('[itemId=farrivetime]').getSubmitValues();
						if(Ext.isEmpty(exportdate)){
							header = fsupplier+"送货清单";
						}else{
							header = fsupplier+Ext.Date.format(new Date(exportdate[0]),'Y-m-d')+"到"+Ext.Date.format(new Date(exportdate[1]),'Y-m-d')+"送货清单";
						}
						button.up('grid').exporturl="ExcelDeliverStatementList.do?header="+header+"&fsupplierid="+fsupplierid;
					}
				},
				handler : function() {

				}
			}],
	fields : ['sdentryid', 'fsupplierid', 'fboxtype', 'saleorderNumber', 'cutpdtname', 'fboxspec',
			'fmaterialspec', 'famount', 'farrivetime', 'fouttime','fdescription','forderunit'],
	columns : {
		items : [{
			xtype : 'rownumberer',
			text : '序号',
			width : 35
				// hidden:true
			}, {
			text : '出库/配送时间',
			dataIndex : 'fouttime',
			flex : 1.3
//			,
//			renderer : function(val) {
//				return val.substr(0, 16);
//			}
		}, {
			text : '产品名称',
			dataIndex : 'cutpdtname',
			flex : 1
		}, {
			text : '纸箱规格',
			dataIndex : 'fboxspec',
			flex : 1
		}, {
			text : '下料规格',
			dataIndex : 'fmaterialspec',
			flex : 1
		}, {
			text : '收货数量',
			dataIndex : 'famount',
				width : 70,
			renderer:function(val){
			return val.substr(0,val.indexOf('.'));
		}
		}, {
			text : '单位',
			dataIndex : 'forderunit',
			width : 45
		}, {
			text : '订单类型',
			dataIndex : 'fboxtype',
			width : 70,
			renderer:function(val){
			return val==="0"?"纸箱订单":"纸板订单";
		}
		}, {
			text : '收货单号',
			dataIndex : 'saleorderNumber',
			flex : 1
		},{
			text : '备注',
			dataIndex : 'fdescription',
			flex : 0.5
		}]
	}
})