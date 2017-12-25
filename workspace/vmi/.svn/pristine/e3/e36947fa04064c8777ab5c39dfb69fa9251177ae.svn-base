Ext.define('DJ.order.saleOrder.PreProductDemandEast',{
	extend : 'Ext.c.GridPanel',
	alias : 'widget.preproductdemandeast',
	pageSize : 30,
	url : 'getPreProductDemandPlanList.do',
	Delurl : 'delPreProductDemandPlan.do',
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	custbar : [{
		text : '上传',
		height : 30,
		iconCls : 'upload',
		handler : function(){
			var customerid = Ext.getCmp('DJ.order.saleOrder.PreProductDemand.fcustomerid').getValue();
			if(!customerid){
				 Ext.Msg.alert('提示','请先选择客户！');
				 return;
			}
			var win = Ext.create('DJ.order.saleOrder.PreProductDemandEastEdit');
			win.setparent('DJ.order.saleOrder.PreProductDemandEast');
			win.seteditstate("edit");
			win.getbaseform().findField('fcustomerid').setValue(customerid);
			win.show();
		}
	},{
		xtype: 'tipcombo',
		storageId: 'PreProductDemandEast',
		supportFields: ["e.fcustpage","e1.fname"],
		margin : '0 0 0 10',
		width : 120
	},{
		text : '查询',
		handler : function(){
			this.previousSibling().handleSelect();
		}
	}],
	onload : function(){
		var id = this.id;
		Ext.getCmp(id+'.addbutton').hide();
		Ext.getCmp(id+'.editbutton').hide();
		Ext.getCmp(id+'.viewbutton').hide();
		Ext.getCmp(id+'.querybutton').hide();
		Ext.getCmp(id+'.exportbutton').hide();
	},
	listeners:{
		select : function(records){
			var selModel = this.getSelectionModel(),
				selected = selModel.getLastSelected();
			selModel.getSelection().forEach(function(item){
				if(item !== selected){
					selModel.deselect(item);
					return;
				}
			});
			Ext.getCmp('DJ.order.saleOrder.PreProductDemandSouth').showData();
			var eastImg = Ext.getCmp('DJ.order.saleOrder.PreProductDemand.PlanDrawing').viewImg = document.getElementById('plan_drawing');
			eastImg.src = "getDrawingById.do?fid="+(selected.get('fsfileid')||selected.get('ffileid'))+'&_dc=' + Ext.Date.now();
		},
		deselect: function(){
			Ext.getCmp('DJ.order.saleOrder.PreProductDemandSouth').showData();
			Ext.getCmp('DJ.order.saleOrder.PreProductDemand.PlanDrawing').viewImg.src='';
		}
	},
	fields : [
	       {name : 'fid'},{name : 'fcustpage'},{name : 'ffilename'},{name : 'fcustomerid'},{name: 'ffileid'},{name: 'fsfileid'}
	],
	columns : [{
		text : '序号',
		xtype : 'rownumberer',
		width : 35
	},{
		text : '客户版面',
		dataIndex : 'fcustpage',
		flex : 1
	},{
		text : '平面图纸名称',
		dataIndex : 'ffilename',
		flex : 1
	}]
});