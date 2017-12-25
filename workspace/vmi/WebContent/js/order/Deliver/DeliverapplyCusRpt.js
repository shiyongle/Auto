Ext.define('DJ.order.Deliver.DeliverapplyCusRpt', {
	extend : 'Ext.c.GridPanel',
	title : "客户要货明细表",
	id : 'DJ.order.Deliver.DeliverapplyCusRpt',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'gainCusDeliverapplyRep.do',
	// Delurl : "DeleteOutWarehouse.do",
	// EditUI : "DJ.Inv.OutWarehouse.OutWarehouseEdit",
	 exporturl : "DeliverapplyCusRpttoExcel.do",

//	mixins : ['DJ.tools.grid.MySimpleGridMixer'],

	features : [{
	
		ftype : 'summary'
		
	}],
	
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等

		var me = this;
		
		var buttonH = ['新','修','查','删'];
		var grid = this;
		grid.store.on('beforeload',function( store, operation, eOpts ){
			store.getProxy().timeout=600000;
		})
		buttonH.forEach(function(ele){
			me.down('toolbar button[text*='+ele+']').hide();
		})
		
		MyGridTools.rebuildExcelAction(me); 
		//导出时配合金众前台传入的columns数据.header-checkbox和row-numberer不需要传入
		me.getStore().on('load',function(){
			me.columns[0].dataIndex = '';
			me.columns[1].dataIndex = '';
		})
//		me._hideButtonsCURD();
//		me._hideExportExcellButton();

	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件

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
	 
	 	xtype : 'myexcelexportercuscfgbutton',
	 	hidden:true
	 	
	 },{
		xtype : 'mydatetimesearchbox',

		useDefaultfilter : true,
		labelFtext : '创建时间',

		conditionDateField : 'dcreatetime'

	}],
	fields : [{
		name : 'fcustomerName',
		myfilterfield : 'fcustomerName',
		myfiltername : '客户名称',
		myfilterable : true

	}, {
		name : 'fcustomerNumber',

		myfilterfield : 'fcustomerNumber',
		myfiltername : '客户编号',
		myfilterable : true

	}, {name:'famount',type : 'int'}, {name:'fcount',type:'int'},'dcreatetime','fboxtype','fordesource','sfname'],
	columns : [
		Ext.create('DJ.Base.Grid.GridRowNum', {
						header:'序号',
						stateId : 'gridRowNum'
		}),{
			'header' : '制造商名称',
			'dataIndex' : 'sfname',
			width : 150,
			sortable : true
		},{
			'header' : '客户编码',
			'dataIndex' : 'fcustomerNumber',
			width : 150,
			sortable : true
		}, {
			'header' : '客户名称',
			dataIndex : 'fcustomerName',
			width : 200,
			sortable : true

		}, {
			text:'订单类型',
			dataIndex:'fboxtype',
			renderer:function(val){
				return val==1?'纸板订单':'纸箱订单';
			}
		}, {
			text:'来源订单类型',
			dataIndex:'fordesource',
			renderer:function(val){
				switch(val){
					case 'ios':return "苹果"
					case 'android':return "安卓"
					default:return "网页"
				}
			}
		},{
			'header' : '订单数量',
			dataIndex : 'fcount',
			width : 150,
			sortable : true,
			summaryType : 'sum',
			summaryRenderer : function(value) {
			
				return '订单数量总计：' + value;
				
			}

		}, {
			'header' : '订货数量',
			width : 150,
			dataIndex : 'famount',
			sortable : true,
			summaryType : 'sum',
			summaryRenderer : function(value) {
			
				return '订货数量总计：' + value;
				
			}
		}],
	selModel : Ext.create('Ext.selection.CheckboxModel')
})