Ext.require("DJ.myComponent.form.MainSchemeDesignListDeliverButton");
Ext.define('DJ.order.saleOrder.SchemeDesignNumsRpt',{
	id:'DJ.order.saleOrder.SchemeDesignNumsRpt',
	extend : 'Ext.c.GridPanel',
	title:'未生成配送特性方案报表',
	url : 'getSchemeDesignNumsRpt.do',
	EditUI : "DJ.order.saleOrder.SchemeDesignEdit",
	Delurl : "",
	exporturl : "",// 导出为EXCEL方法
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	closable:true,
	onload:function(){
//		this.down('toolbar').hide();
		Ext.getCmp('DJ.order.saleOrder.SchemeDesignNumsRpt.addbutton').hide();
		Ext.getCmp('DJ.order.saleOrder.SchemeDesignNumsRpt.editbutton').hide();
		Ext.getCmp('DJ.order.saleOrder.SchemeDesignNumsRpt.delbutton').hide();
//		Ext.getCmp('DJ.order.saleOrder.SchemeDesignNumsRpt.viewbutton').hide();
//		Ext.getCmp('DJ.order.saleOrder.SchemeDesignNumsRpt.refreshbutton').hide();
//		Ext.getCmp('DJ.order.saleOrder.SchemeDesignNumsRpt.querybutton').hide();
		Ext.getCmp('DJ.order.saleOrder.SchemeDesignNumsRpt.exportbutton').hide();
	},
	Action_AfterViewButtonClick : function(EditUI) {
		var grid = Ext.getCmp(EditUI.EditUI);
		grid.down('button[text=确认]').setVisible(false);
		grid.down('button[text=取消确认]').setVisible(false);
	
	},
	custbar:[ {
		xtype : "mainschemedesignlistdeliverbutton"
	}],
	fields:[{name:'fid'},{name:'fconfirmer'},{name:'sname'},{name:'cname'},{name:'fname',myfilterfield : 'a.fname',myfiltername : '方案名称',myfilterable : true},{name:'fnumber',myfilterfield : 'a.fnumber',myfiltername : '方案编号',myfilterable : true},
	        {name:'uname'},{name:'fcreatetime'},{name:'fconfirmed'},{name:'fconfirmtime'}],
	columns:[{
		text:"fid",
		dataIndex:'fid',
		hidden:true
	},{
		text:'编号',
		dataIndex:'fnumber'
	},{
		text:'名称',
		dataIndex:'fname'
	},{
		text:'客户',
		dataIndex:'cname'
	},{
		text:'制造商',
		dataIndex:'sname'
	},{
		text:'创建人',
		dataIndex:'uname'
	},{
		text:'创建时间',
		dataIndex:'fcreatetime'
	},{
		text:'是否确认',
		dataIndex:'fconfirmed',
		renderer:function(value){
			if(value==1){
				return '是';
			}else{
				return '否';
			}
		}
	},{
		text:'确认人',
		dataIndex:'fconfirmer'
	},{
		text:'确认时间',
		dataIndex:'fconfirmtime'
	}]
})