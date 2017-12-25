Ext.define('DJ.order.Deliver.ProductdemandOfallotList', {
	extend : 'Ext.c.GridPanel',
	id : 'DJ.order.Deliver.ProductdemandOfallotList',
	pageSize : 50,
	title : "产品需求分配",
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'getproductdemandOfallotList.do',
	Delurl : "",
	EditUI : "",
	exporturl : "",// 导出为EXCEL方法
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件
		verification("edit");
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件
	},
	fields : [{
		name : 'fid'
	}, {
		name : 'fnumber',
		myfilterfield : 'f.fnumber',
		myfiltername : '需求编号',
		myfilterable : true
	}, {
		name : 'fdescription'
	}, {
		name : 'fboxlength'
	}, {
		name : 'fboxwidth'
	}, {
		name : 'fboxheight'
	}, {
		name : 'fboardlength'
	}, {
		name : 'fboardwidth'
	}, {
		name : 'fcreatid'
	}, {
		name : 'fcreatetime'
	}, {
		name : 'fupdateuserid'
	}, {
		name : 'fupdatetime'
	}, {
		name : 'fauditorid'
	}, {
		name : 'fauditortime'
	}, {
		name : 'isfauditor'
	}, {
		name : 'fcustomerid'
	}, {
		name : 'fsupplierid'
	}, {
		name : 'falloted'
	}, {
		name : 'fallotor'
	}, {
		name : 'fallottime'
	}, {
		name : 'freceived'
	}, {
		name : 'freceiver'
	}, {
		name : 'freceivetime'
	}, {
		name : 'fname',
		myfilterfield : 'f.fname',
		myfiltername : '需求名称',
		myfilterable : true
	}, {
		name : 'ftype'
	}, {
		name : 'fcostneed'
	}, {
		name : 'fiszhiyang'
	}, {
		name : 'famount'
	}, {
		name : 'foverdate'
	}, {
		name : 'farrivetime'
	}, {
		name : 'fboxpileid'
	}, {
		name : 'fmaterial'
	}, {
		name : 'fcorrugated'
	}, {
		name : 'fprintcolor'
	}, {
		name : 'fprintbarcode'
	}, {
		name : 'funitestyle'
	}, {
		name : 'fprintstyle'
	}, {
		name : 'fsurfacetreatment'
	}, {
		name : 'fpackstyle'
	}, {
		name : 'fpackdescription'
	}, {
		name : 'fisclean'
	}, {
		name : 'fispackage'
	}, {
		name : 'fpackagedescription'
	}, {
		name : 'fislettering'
	}, {
		name : 'fletteringescription'
	}, {
		name : 'fsuppliername'
	},{
		name : 'custname'
	}],
	columns : [{
		text : 'fid',
		dataIndex : 'fid',
		hidden : true
	}, {
		text : '编号',
		dataIndex : 'fnumber'
	}, {
		text : '名称',
		dataIndex : 'fname'
	},{
		text : '客户名称',
		dataIndex : 'custname'
	}, {
		text : '创建人',
		dataIndex : 'fcreatid'
	}, {
		text : '创建时间',
		dataIndex : 'fcreatetime'
	}, {
		text : '接收人',
		dataIndex : 'freceiver'
	}, {
		text : '接收时间',
		dataIndex : 'freceivetime'
	}, {
		text : '确认人',
		dataIndex : 'fauditorid'
	}, {
		text : '确认时间',
		dataIndex : 'fauditortime'
	}, {
		text : '方案入库日期',
		dataIndex : 'foverdate',
		width:120
	}, {
		text : '成本要求',
		dataIndex : 'fcostneed'
	}, {
		text : '是否制样',
		dataIndex : 'fiszhiyang',
		renderer : function(value) {
			if (value == 'true') {
				return '是';
			} else {
				return '否';
			}
		}
	}, {
		text : '备注',
		dataIndex : 'fdescription'
	}, {
		text : '供应商',
		dataIndex : 'fsuppliername'
	}, {
		text : '是否发布',
		dataIndex : 'isfauditor',
		renderer : function(value) {
			if (value == 'true') {
				return '是';
			} else {
				return '否';
			}
		}
	}, {
		text : '是否分配',
		dataIndex : 'falloted',
		renderer : function(value) {
			if (value == 'true') {
				return '是';
			} else {
				return '否';
			}
		}
	}],
	custbar : [{
		text : '分配',
		height : 30,
		handler : function() {
			 var allotgrid=Ext.getCmp("DJ.order.Deliver.ProductdemandOfallotList");
			 var allotrecord = allotgrid.getSelectionModel().getSelection();
			 if(allotrecord.length<1){
			 Ext.MessageBox.alert("错误","请在需求列表中选择一条记录进行分配!");
			 return;
			 }	
			var fid = "";
//			for(var i=0;i<allotrecord.length;i++){
//				if(allotrecord[i].get("falloted")=="true"){
//					continue;
//				}
//				fid += allotrecord[i].get("fid");
//				if(i<allotrecord.length-1){
//					fid += ",";
//				}
//			}
			if(allotrecord[0].get("falloted")=="true"){
				Ext.MessageBox.alert("错误","该需求已分配!");
				return;
			}
			fid += allotrecord[0].get("fid");
				
			 var el = allotgrid.getEl();
			 el.mask("系统处理中,请稍候……");
			 
			 Ext.Ajax.request({
			 url:'allotruletoproductdemand.do',
			 params:{
			 fidcls:fid
			 },
			 success : function(response, option) {
			 var obj = Ext.decode(response.responseText);
			 if (obj.success == true) {
			 djsuccessmsg(obj.msg);
			 Ext.getCmp("DJ.order.Deliver.ProductdemandOfallotList").store.load();
			 } else {
				 if(obj.msg='多用户'){
					 var win = Ext.create('DJ.order.Deliver.PdtAllotChooseEdit');
					 win.down("combo").getStore().getProxy().setExtraParam( "productdemandid", fid );
					 Ext.getCmp('DJ.order.Deliver.PdtAllotChooseEdit.fProductdemandid').setValue(fid);
					 win.show();
				 }else{
					 Ext.MessageBox.alert('错误', obj.msg);
				 }
			 }
			 el.unmask();
			 }
			 })
						
		}
	}, {
		text : '取消分配',
		height : 30,
		handler : function() {

			 var allotgrid=Ext.getCmp("DJ.order.Deliver.ProductdemandOfallotList");
	
			 var allotrecord = allotgrid.getSelectionModel().getSelection();
			 if(allotrecord.length<1){
			 Ext.MessageBox.alert("错误","请在需求列表中选择一条记录进行取消分配!");
			 return;
			 }
			var fid = "";
			for(var i=0;i<allotrecord.length;i++){
				if(allotrecord[i].get("falloted")=="false"){
					continue;
				}
				fid += allotrecord[i].get("fid");
				if(i<allotrecord.length-1){
					fid += ",";
				}
			}
			 var el = allotgrid.getEl();
			 el.mask("系统处理中,请稍候……");
			 Ext.Ajax.request({
			 url:'unallotruletoproductdemand.do',
			 params:{
			 fidcls:fid
			 },
			 success : function(response, option) {
			 var obj = Ext.decode(response.responseText);
			 if (obj.success == true) {
			 djsuccessmsg(obj.msg);
			 Ext.getCmp("DJ.order.Deliver.ProductdemandOfallotList").store.load();
			} else {
				Ext.MessageBox.alert('错误', obj.msg);
			
			}
				el.unmask();
			}
//			 ,failure: function(response, opts) {
//		    }
			})
		}
	}]
})

//Ext.define('DJ.order.Deliver.ProductdemandOfallotList', {
//	extend : 'Ext.panel.Panel',
//	id : 'DJ.order.Deliver.ProductdemandOfallotList',
//	autoScroll : true,
//	border : false,
//	layout : 'border',
//	title:'产品需求分配',
//	closable:true,
//	items:
//			[{
//				region : 'center',
//				items : [ Ext.create("DJ.order.Deliver.FistproductdemandList",{
//					id:'DJ.order.Deliver.AllotproductdemandList',
//					url : 'getFirstproductdemandList.do',
//					selModel : Ext.create('Ext.selection.CheckboxModel'),
//					listeners:{
//						render:function(){
//							this.down('button[text=分配]').show();
//							this.down('button[text=取消分配]').show();
//							this.down('button[text=审核]').hide();
//							this.down('button[text=反审核]').hide();
//							this.down('button[text=接收]').hide();
//							this.down('button[text=取消接收]').hide();
//							Ext.getCmp('DJ.order.Deliver.AllotproductdemandList.addbutton').setVisible(false);
//							Ext.getCmp('DJ.order.Deliver.AllotproductdemandList.editbutton').setVisible(false);
//							Ext.getCmp('DJ.order.Deliver.AllotproductdemandList.viewbutton').setVisible(false);
//							Ext.getCmp('DJ.order.Deliver.AllotproductdemandList.delbutton').setVisible(false);
////							Ext.getCmp('DJ.order.Deliver.AllotproductdemandList.refreshbutton').setVisible(false);
//							Ext.getCmp('DJ.order.Deliver.AllotproductdemandList.querybutton').setVisible(false);
//							Ext.getCmp('DJ.order.Deliver.AllotproductdemandList.exportbutton').setVisible(false);
//							Ext.getCmp('DJ.order.Deliver.AllotproductdemandList.exportbutton').setVisible(false);
//						}
//					}
//				})],
//				layout:'fit'
//			},
//			{
//				region : 'south',
//				width : 220,
//				height:300,
//				layout:'fit',
//				items : [ Ext.create("DJ.System.product.ProductreqallocationrulesList", {
//					id:'DJ.System.product.AllotProductreqallocationrulesList',
//					url : 'selectProductreqallocationruleses.do',
//					selModel : Ext.create('Ext.selection.CheckboxModel'),
//					listeners :{
//						render:function(){
//							Ext.getCmp('DJ.System.product.AllotProductreqallocationrulesList.addbutton').setVisible(false);
//							Ext.getCmp('DJ.System.product.AllotProductreqallocationrulesList.editbutton').setVisible(false);
//							Ext.getCmp('DJ.System.product.AllotProductreqallocationrulesList.viewbutton').setVisible(false);
//							Ext.getCmp('DJ.System.product.AllotProductreqallocationrulesList.delbutton').setVisible(false);
////							Ext.getCmp('DJ.System.product.AllotProductreqallocationrulesList.refreshbutton').setVisible(false);
//							Ext.getCmp('DJ.System.product.AllotProductreqallocationrulesList.querybutton').setVisible(false);
//							Ext.getCmp('DJ.System.product.AllotProductreqallocationrulesList.exportbutton').setVisible(false);
//							Ext.getCmp('DJ.System.product.AllotProductreqallocationrulesList.exportbutton').setVisible(false);
//						}
//					}})]
//				
//			}]
//	       
//});
