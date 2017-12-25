Ext.define('DJ.order.Deliver.FTUstatementList', {
	id : 'DJ.order.Deliver.FTUstatementList',
	extend : 'Ext.c.GridPanel',
	// title : "对账单",
	pageSize : 50,
	url : 'getFTUproductByCustomer.do',
	exporturl : "ExcelFTUstatementList.do",// 导出为EXCEL方法
	selModel : {selType:'checkboxmodel'},
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
		var bbar = this.down('toolbar[dock=bottom]');
		bbar.add(bbar.items.items.length - 1, {
					xtype : 'displayfield',
					fieldLabel : '总数量',
					labelWidth : 45
				}, {
					xtype : 'tbfill'
				}, {
					xtype : 'displayfield',
					fieldLabel : '总金额',
					labelWidth : 45
				}, {
					xtype : 'tbfill'
				});
		this.store.on('load', function(store, records, successful, eOpts) {
					if (store.data.items.length > 0) {
						var famount = 0, fprices = 0;
						Ext.each(records,function(record, index) {
									famount += eval(record.get('famount')==''?0:record.get('famount'));
									fprices += eval(record.get('fprices')==''?0:record.get('fprices'));
								})
						bbar.down('displayfield[fieldLabel=总数量]').setValue(Math
								.round(famount * 1000)
								/ 1000);
						bbar.down('displayfield[fieldLabel=总金额]').setValue(Math
								.round(fprices * 1000)
								/ 1000);
					}
				})
		this.store.on('beforeload', function(store, operation, eOpts) {
					if (Ext.isEmpty(me.down('combobox').getValue())) {
						return false;
					}
				})
		this.down('toolbar mydatetimesearchbox').on('afterrender',function(datafield){//双日历默认为空
			datafield.down("datefield").setValue(null);
			datafield.down("datefield").nextNode('datefield').setValue(null);
		})
	},
	custbar : [{
				xtype : 'tbspacer'
			}, {
				xtype : 'combobox',
				fieldLabel : '客户名称',
				labelWidth : 55,
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
								url : 'selectFtuCustomer.do',
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
								combo.fireEvent('select',combo,records,combo);
							}
						}})
					},
					select : function(combo, records, eOpts) {
						var grid = combo.up('grid');
						combo.nextNode("datefield").setValue(null);
						combo.nextNode("datefield").nextNode('datefield').setValue(null);
						grid.queryFtucustproduct();
					},
					change:function(combo,newValue,oldValue){
						if(Ext.isEmpty(newValue)){
							combo.store.load();
						}
					}
				}
			}, {
				xtype : 'mydatetimesearchbox',
				conditionDateField : "sd.fprinttime",
				labelFtext : "出库时间",
				useDefaultfilter : true,
				width : 450,
				beforeLoad : function(storeT, begainDate, endDate, myfilter) {
					if('1999-01-15 00:00:00'==begainDate){
						begainDate ='';
					}
					if('2099-01-15 23:59:59'==endDate){
						endDate = '';
					}
					this.up('grid').queryFtucustproduct(begainDate, endDate);
					return false;
				}
			}, '|', {
				text : '导出Excel',
				width : 70,
				height : 30,
				listeners:{
					click:function(button,e){
						var fcustomer = button.up('grid').down('combobox').rawValue;
						var header = '';
						if(Ext.isEmpty(fcustomer)){
							return false;
						}
						var startDate = Ext.Date.format(button.up('grid').down('datefield').getValue(),'Y-m-d');
						var endDate = Ext.Date.format(button.up('grid').down('datefield').next('datefield').getValue(),'Y-m-d');
						if(Ext.isEmpty(startDate)&&Ext.isEmpty(endDate)){
							header = fcustomer+"销售清单";
						}else{
							header = fcustomer+startDate+"到"+endDate+"销售清单";
						}
						button.up('grid').exporturl="ExcelFTUstatementList.do?header="+header;
					}
				},
				handler : function() {

				}
			}],
	queryFtucustproduct : function(startDate, endDate) {
		var me = this;
		var myfilter = [];
		// var startDate = me.down('datefield').getValue();
		// var endDate = me.down('datefield').next('datefield').getValue();
		myfilter.push({
					myfilterfield : "p.fcustomerid",
					CompareType : " like ",
					type : "string",
					value : me.down('combobox').getValue()
				},{
					myfilterfield : "sd.fstate",
					CompareType : " <> ",
					type : "string",
					value : 1
				});
		me.store.setDefaultmaskstring(" #0 and #1 ");
		if (!Ext.isEmpty(startDate) && !Ext.isEmpty(endDate)) {
			myfilter.push({
						myfilterfield : "sd.fprinttime",
						CompareType : " >= ",
						type : "datetime",
						value : startDate
					}, {
						myfilterfield : "sd.fprinttime",
						CompareType : " <= ",
						type : "datetime",
						value : endDate
					});
			me.store.setDefaultmaskstring(" #0 and #1 and (#2 and #3)");
		}
		me.store.setDefaultfilter(myfilter);

		me.store.loadPage(1);
	},
	fields : ['fid', 'fname', 'fspec','fprice', 'funit', 'fdescription',
			'famount','fprices', 'fnumber', 'fprinttime'],
	columns : {
		items : [{
			xtype : 'rownumberer',
			text : '序号',
			width : 35
				// hidden:true
			}, {
			text : '出库时间',
			dataIndex : 'fprinttime',
			flex : 1,
			renderer : function(val) {
				return val.substr(0, 16);
			}
		}, {
			text : '产品名称',
			dataIndex : 'fname',
			flex : 1
		}, {
			text : '规格',
			dataIndex : 'fspec',
			flex : 1
		}, {
			text : '数量',
			dataIndex : 'famount',
			width : 50
		}, {
			text : '单价',
			dataIndex : 'fprice',
			width : 70,
			renderer:function(v){
				return eval(v);
			}
		}, {
			text : '金额',
			dataIndex : 'fprices',
			width : 70,
			renderer:function(v){
				return eval(v);
			}
		}, {
			text : '出库单编号',
			dataIndex : 'fnumber',
			flex : 1
		},{
			text : '备注',
			dataIndex : 'fdescription',
			flex : 1
		}]
	}
})