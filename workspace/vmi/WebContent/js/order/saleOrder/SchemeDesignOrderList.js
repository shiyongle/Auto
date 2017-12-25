Ext.define('DJ.order.saleOrder.SchemeDesignOrderList',{
	id:'DJ.order.saleOrder.SchemeDesignOrderList',
	extend : 'Ext.c.GridPanel',
	pageSize : 50,
	title : "一次性订单",
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'getSchemeDesignOrderList.do',
	Delurl : "",
	EditUI : "",
	exporturl : "",// 导出为EXCEL方法
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	onload : function() {
		Ext.getCmp('DJ.order.saleOrder.SchemeDesignOrderList.addbutton').setVisible(false);
		Ext.getCmp('DJ.order.saleOrder.SchemeDesignOrderList.editbutton').setVisible(false);
		Ext.getCmp('DJ.order.saleOrder.SchemeDesignOrderList.delbutton').setVisible(false);
		Ext.getCmp('DJ.order.saleOrder.SchemeDesignOrderList.viewbutton').setVisible(false);
		Ext.getCmp('DJ.order.saleOrder.SchemeDesignOrderList.exportbutton').setVisible(false);
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件
//		verification("edit");
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
//		Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.fnumber').setReadOnly(true);
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件
//		verification("del");
	},
		fields:[{name:'fid'},{name:'fdescription'},{name:'ffirstproductid'},
				{name:'fnumber',myfilterfield : 's.fnumber',myfiltername : '方案编号',myfilterable : true},
		        {name:'fname',myfilterfield : 's.fname',myfiltername : '方案名称',myfilterable : true},
		        {name:'fcustomer'},{name:'fsupplier'},{name:'fcreatid'},{name:'fordered'},
		        {name:'fcreator'},{name:'fcreatetime'},{name:'fconfirmed'}],
		columns:[{
			text:'fid',
			dataIndex:'fid',
			hidden:true
		},{
			text:'方案编号',
			dataIndex:'fnumber'
		},{
			text:'方案名称',
			dataIndex:'fname'
		},{
			text:'客户',
			dataIndex:'fcustomer',
			width : 160
		},{
			text:'制造商',
			dataIndex:'fsupplier'
		},{
			text:'创建人',
			dataIndex:'fcreator'
		},{
			text:'创建时间',
			dataIndex:'fcreatetime',
			width : 160
		},{
			text:'是否确定',
			dataIndex:'fconfirmed',
			renderer : function(value) {
				if (value == '1') {
					return '是';
				} else {
					return '否';
				}
			}
		},{
			text:'是否下单',
			dataIndex:'fordered',
			flex:1,
			renderer:function(val){
				if(val=='1'){
					return '是';
				}else{
					return '否';
				}
			}
		}]
	,custbar : [{
		text : '下单',
		height : 30,
		handler : function() {
			var me = Ext.getCmp("DJ.order.saleOrder.SchemeDesignOrderList");
			var record = me.getSelectionModel().getSelection();
			if (record.length != 1) {
				Ext.MessageBox.alert("信息", "请选择一条信息进行操作！");
				return;
			}
			if(record[0].get('fordered')==1){
				Ext.Msg.alert('提示','已下单方案不能重复下单！');
				return ;
			}
			var el = me.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				timeout : 600000,
				url : "SchemeDesignOrder.do",
				params : {
					fid : record[0].get("fid")
				}, // 参
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						djsuccessmsg(obj.msg);
						Ext.getCmp("DJ.order.saleOrder.SchemeDesignOrderList").store
								.load();

					} else {
						Ext.MessageBox.alert('错误', obj.msg);

					}
					el.unmask();
				}
			});

		}
	},{
		text:'取消下单',
		height:30,
		handler:function(){
			var me = Ext.getCmp("DJ.order.saleOrder.SchemeDesignOrderList");
			var record = me.getSelectionModel().getSelection();
			if (record.length != 1) {
				Ext.MessageBox.alert("提示", "请选择一条信息进行操作！");
				return;
			}
			if(record[0].get('fordered')!=1){
				Ext.Msg.alert('提示','请选择已下单方案进行取消！');
				return;
			}
			Ext.Ajax.request({
				url:'cancelSchemeDesignOrder.do',
				params : {fid:record[0].get('fid')},
				success:function(res){
					var obj = Ext.decode(res.responseText);
					if(obj.success){
						djsuccessmsg(obj.msg);
						Ext.getCmp("DJ.order.saleOrder.SchemeDesignOrderList").store.load();
					}else{
						Ext.Msg.alert('错误',obj.msg);
					}
				}
			})
		}
	},{
		text : '查看',
		height : 30,
		handler : function() {
			var me = Ext.getCmp("DJ.order.saleOrder.SchemeDesignOrderList");
			var record = me.getSelectionModel().getSelection();
			if (record.length < 1) {
				Ext.MessageBox.alert("信息", "请选择至少一条信息进行操作！");
				return;
			}
			var fid = record[0].get("fid");
			var SchemeDesignEdit = Ext.create('DJ.order.saleOrder.SchemeDesignEdit');
			SchemeDesignEdit.seteditstate("view");
			SchemeDesignEdit.loadfields(fid);
			SchemeDesignEdit.down("toolbar").add(
				Ext.create('Ext.Button', {
				    text: '方案设计下单',
				    handler: function() {
						Ext.Ajax.request({
							timeout : 600000,
							url : "SchemeDesignOrder.do",
							params : {
								fid : fid
							}, // 参
							success : function(response, option) {
								var obj = Ext.decode(response.responseText);
								if (obj.success == true) {
									djsuccessmsg(obj.msg);
									Ext.getCmp("DJ.order.saleOrder.SchemeDesignOrderList").store.load();
									
								} else {
									Ext.MessageBox.alert('错误', obj.msg);
								}
								Ext.getCmp("DJ.order.saleOrder.SchemeDesignEdit").close();
							}
						});
				    }
			}));
			SchemeDesignEdit.show();
		}
	}]
});
