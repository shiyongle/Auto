Ext.require("DJ.tools.fieldRel.MyFieldRelTools");

Ext.require("Ext.ux.form.MyMixedSearchBox");
Ext.require("Ext.ux.form.MyDateTimeSearchBox");
Ext.require("Ext.ux.form.DoubleDateField");

Ext.define('DJ.order.Deliver.ActualDeliverRpt', {
	extend : 'Ext.c.GridPanel',
	alias : 'widget.actualdeliverrpt',
	title : "我的收货",
	id : 'DJ.order.Deliver.ActualDeliverRpt',
	pageSize : 50,
	closable : false,// 是否现实关闭按钮,默认为false
	url : 'selectFoutedDeliverordersMV.do',
	Delurl : "",
	EditUI : "",
	exporturl : "",// 导出为EXCEL方法
	mixins : ['DJ.tools.grid.mixer.MyGridSearchMixer'],
	
	
	viewConfig : {
		getRowClass : function(record, rowIndex, rowParams, store) {

			return record.get("faffirmed") == 1
					? "fstate-true"
					: "fstate-false";
		}
	},
	
	onload : function() {
		var me = this;
		var grid = this;
		grid.store.on('beforeload',function( store, operation, eOpts ){
			store.getProxy().timeout=600000;
			if(store.autoLoad==true){
				store.autoLoad=false;
				return false;
			}
		})
		Ext.util.CSS.createStyleSheet(".fstate-true{" + "color:#0000CC" + "}",
				'fstate-true');

		Ext.util.CSS.createStyleSheet(".fstate-false{" + "color:black" + "}",
				'fstate-false');
		// //同时也刷新图表
		me.getStore().on('load', function() {
			me.previousNode("chart").getStore().load();
		});
		Ext.getCmp('DJ.order.Deliver.ActualDeliverRpt.querybutton').hide();
		Ext.getCmp('DJ.order.Deliver.ActualDeliverRpt.addbutton').hide();
		Ext.getCmp('DJ.order.Deliver.ActualDeliverRpt.editbutton').hide();
		Ext.getCmp('DJ.order.Deliver.ActualDeliverRpt.delbutton').hide();
		Ext.getCmp('DJ.order.Deliver.ActualDeliverRpt.viewbutton').hide();
		Ext.getCmp('DJ.order.Deliver.ActualDeliverRpt.exportbutton').hide();
		Ext.getCmp('DJ.order.Deliver.ActualDeliverRpt.exportbutton').next().hide();
		
	},
	doBeforeGridSearchAction: function(filters, maskA,indexAll){
				//查询值
				var me = this;
				//出库时间
				var dateValues = me.down('[itemId=farrivetime]').getSubmitValues();
				if(dateValues){
					filters.push({
						myfilterfield : "farrivetime",
						CompareType : ">=",
						type : "string",
						value : dateValues[0]
					});
					if(indexAll !=0){
						maskA.push(" and ");
					}
					maskA.push(" #" + indexAll++ + " ");
					filters.push({
						myfilterfield : "farrivetime",
						CompareType : "<=",
						type : "string",
						value : dateValues[1]
					});
					maskA.push(" and #" + indexAll++ + " ");
				}
			},
	custbar :[Ext.create("DJ.myComponent.button.ExportExcelButton"),{
		xtype : 'mymixedsearchbox',
		condictionFields:['cutpdtname','fboxspec','saleorderNumber','fsuppliername'],
		tip:'可输入:产品名称、纸箱规格、收货单号、制造商',
		useDefaultfilter : true,
		width : 200,
		filterMode : true,
		beforeSearchActionForStore:function(myfilter,maskstrings){
						Ext.each(myfilter,function(ele){
							if(ele.myfilterfield=='fboxspec'){
								ele.value = ele.value.replace(/\*/g,'_').replace(/\X/g,'_').replace(/\x/g,'_');
							}
						})		
					}
	},"|",{
		xtype : 'doubledatefield',
				itemId: 'farrivetime',
				fieldLabel: '出库时间',
				width: 240,
				labelWidth: 55,
				listeners: {
					change: function(me,val){
					this.up('grid').doGridSearchAction();
				},render:function(field){
					field.setValue(Ext.Date.format(Ext.Date.subtract(new Date(), Ext.Date.MONTH, 3),'Y-m-d') + field.separator+Ext.Date.format(new Date(),'Y-m-d'));
					}
		}
	},{
				xtype:'container',
				items:[{
					text : '查询',
					xtype:'button',
						handler : function(){
							this.up('grid').doGridSearchAction();
						}
				}]
	/*},{
		text : '逻辑描述',
		height : 30,
		handler : function() {
			Ext.MessageBox.alert('描述','<p><h4 align="center">"实际发货情况报表"----已经发货的配送信息</h4></p>');
		}*/
	},{
		text : '评价',
		height : 30,
		handler:function(){
							var grid = Ext.getCmp('DJ.order.Deliver.ActualDeliverRpt');
							var records = grid.getSelectionModel().getSelection();
							if(records.length!=1){
								Ext.Msg.alert('提示','请选择一条数据进行评价！')
								return;
							}			
						Ext.Ajax.request({
						url : 'getDeliverorderByfidForappraise.do',
						params : {
							fid : records[0].get('fid')
						},
						success : function(res) {
							var obj = Ext.decode(res.responseText);
							if (obj.success) {
								if(obj.data!=undefined){
									var rinfo=obj.data;
									var win = Ext.create('DJ.order.Deliver.OrderappraiseEdit');
									win.setparent('DJ.order.Deliver.ActualDeliverRpt');
									win.seteditstate('edit');
									var form =win.getform().form;
									form.findField("fordernumber").setValue(rinfo[0].fdeliverorderid_fnumber);
									form.findField("fordertype").setValue(rinfo[0].fordertype);
									form.findField("ftype").setValue(rinfo[0].ftype);
									Ext.getCmp("DJ.order.Deliver.OrderappraiseEdit.fsupplierId").setReadOnly(true);
									Ext.getCmp("DJ.order.Deliver.OrderappraiseEdit.fdeliverorderid").setReadOnly(true);
									Ext.getCmp("DJ.order.Deliver.OrderappraiseEdit.fcustomerid").setValue( rinfo[0].fcustomerid);
									Ext.getCmp("DJ.order.Deliver.OrderappraiseEdit.fsupplierId").setmyvalue("\"fid\":\""+ rinfo[0].fsupplierid_fid + "\",\"fname\":\""+ rinfo[0].fsupplierid_fname + "\"");
									Ext.getCmp("DJ.order.Deliver.OrderappraiseEdit.fdeliverorderid").setmyvalue("\"fid\":\""+ rinfo[0].fdeliverorderid_fid + "\",\"fnumber\":\""+ rinfo[0].fdeliverorderid_fnumber + "\"");

							win.show();
								}
							
							} else {
								Ext.Msg.alert('提示', obj.msg);
							}
						}
					})
							
		}
	},
	
		
		{

		text : '确认',
		handler : function() {

			var grid = this.up('grid');

			MyCommonToolsZ.reqAction("updateFoutedDeliverordersFaffirmed.do", grid, "sdentryid", "1", true,true);

		}},{
		text:'报表',
	  		handler:function(){
	  			Ext.create('Ext.Window',{modal:true,layout:'fit',title : "对账单",width:860,height:400,items:Ext.create('DJ.order.Deliver.ActualDeliverStatementList')}).show();
	  		}
		}],
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
		myfiltername : '申请单号',
		myfilterable : true
	}, {
		name : 'fcustomerid'
	}, {
		name : 'fcusproductid'
	}, {
		name : 'fsuppliername'
	}, {
		name : 'fcustname'
	},{
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
	},{
		name : 'farrivetime',
		myfilterfield : 'd.farrivetime',
		myfiltername : '配送时间',
		myfilterable : true
	},{
		name : 'fordered'
	}, {
		name : 'fordermanid'
	}, {
		name : 'fordertime'
	}, {
		name : 'forderman'
	}, {
		name : 'fsaleorderid'
	}, {
		name : 'fordernumber'
	}, {
		name : 'forderentryid'
	}, {
		name : 'fouted'
	},
	"pfnumber","saleorderNumber","forderunit",'fpcmordernumber','fboxtype','fboxspec','fmaterialspec',"sdentryid","faffirmed"
	],
	columns : {items : [Ext.create('DJ.Base.Grid.GridRowNum',{
		header:'序号'
	}),{
		'header' : '制造商',
		'dataIndex' : 'fsuppliername',
		sortable : true
	}, {
		'header' : '订单类型',
		'dataIndex' : 'fboxtype',
		width:80,
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '纸板订单';
			}else {
				return '纸箱订单';
			}
		}
	},{
		'header' : '收货单号',
		'dataIndex' : 'saleorderNumber',
//		width : 100,
		sortable : true
	},{
		'header' : '采购订单号',
		'dataIndex' : 'fpcmordernumber',
		hidden:true,
//		width : 100,
		sortable : true
	}, {
		'header' : '产品名称',
		'dataIndex' : 'cutpdtname',
//		width : 125,
		sortable : true
	},{
		'header' : '纸箱规格',
		'dataIndex' : 'fboxspec',
		sortable : true,
		width:120,
		renderer : function(value) {
			var vT = DJ.tools.fieldRel.MyFieldRelTools.showEmptyWhenNull(value);
			if (vT == 'null×null×null') {
				vT = '';
			}
			return vT;
		}
	},{
		'header' : '下料规格',
		'dataIndex' : 'fmaterialspec',
		width:120,
		sortable : true
	},{
		'header' : '出库数量',
		'dataIndex' : 'famount',
		sortable : true,
		width : 73,
		renderer:function(val){
			return val.substr(0,val.indexOf('.'));
		}
	},{
		'header' : '单位',
		'dataIndex' : 'forderunit',
		sortable : true,
		width : 48
	},{
		'header' : '出库时间',
		'dataIndex' : 'farrivetime',
		sortable : true
	},{
		'header' : '收货地址',
		'dataIndex' : 'faddress',
		width : 200,
		sortable : true
	},{
		'header' : '备注',
		'dataIndex' : 'fdescription',
//		width : 50,
		sortable : true
		
	}],
	defaults: {
		renderer : DJ.tools.fieldRel.MyFieldRelTools.showEmptyWhenNull,
		width : 150
    }
	},
	selModel : Ext.create('Ext.selection.CheckboxModel')
})