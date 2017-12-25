Ext.define('DJ.order.Deliver.TraitidAllotList', {
	id : 'DJ.order.Deliver.TraitidAllotList',
	extend : 'Ext.c.GridPanel',
	pageSize : 50,
	title : "一次性配送信息",
	// closable : true,// 是否现实关闭按钮,默认为false
	url : 'getunallotTraitidList.do',
	Delurl : "",
	EditUI : "",
	exporturl : "",// 导出为EXCEL方法
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	onload : function() {
				// 加载后事件，可以设置按钮，控件值等
		Ext.getCmp('DJ.order.Deliver.TraitidAllotList.viewbutton')
				.setVisible(false);
		Ext.getCmp('DJ.order.Deliver.TraitidAllotList.addbutton')
				.setVisible(false);
		Ext.getCmp('DJ.order.Deliver.TraitidAllotList.editbutton')
				.setVisible(false);
		Ext.getCmp('DJ.order.Deliver.TraitidAllotList.delbutton')
				.setVisible(false);
		Ext.getCmp('DJ.order.Deliver.TraitidAllotList.exportbutton')
				.setVisible(false);
	var instore= this.store; 
			 	var myfilter = [];
						myfilter.push({
							myfilterfield : "e.fallot",
							CompareType : " = ",
							type : "int",
							value : 0
						}
						);
						instore.setDefaultfilter(myfilter);
						instore.setDefaultmaskstring(" #0 ");
				instore.load();
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
	},
	Action_BeforeEditButtonClick : function(EditUI) {
	},
	Action_AfterEditButtonClick : function(EditUI) {
	},
	Action_BeforeDelButtonClick : function(me, record) {
	},
	custbar : [{
	text : '生成要货',
		height : 30,
		handler : function() {
			var grid = Ext.getCmp("DJ.order.Deliver.TraitidAllotList");
			var record = grid.getSelectionModel().getSelection();
			if(record.length<1){
				Ext.MessageBox.alert("信息","请选择至少一条记录操作！");
				return;
			}
			var ids="";
			for ( var i = 0; i < record.length; i++) {
				var fid = record[i].get("fid");
				ids+=fid;
				if (i < record.length - 1) {
					ids = ids + ",";
				}
			}
			
			if(record.length == 1){
				if(record[0].get("fallot")=="1"){
					Ext.MessageBox.alert('错误', "该记录已经分配！");
					return;
				}
				var fid = record[0].get("fid");
				var amount = parseInt(record[0].get("fentryamount"));
				var frealamount = record[0].get("frealamount")=="null"?0:parseInt(record[0].get("frealamount"));
				var TraitidAmountEdit = Ext.create('DJ.order.Deliver.TraitidAmountEdit');
				TraitidAmountEdit.show();
				Ext.getCmp("DJ.order.Deliver.TraitidAmountEdit.traitid").setValue(fid);
				Ext.getCmp("DJ.order.Deliver.TraitidAmountEdit.fentryamount").setValue(amount-frealamount);
				Ext.getCmp("DJ.order.Deliver.TraitidAmountEdit.frealamount").setValue(amount-frealamount);
			}else {
				var el = grid.getEl();
				el.mask("系统处理中,请稍候……");
				Ext.Ajax.request({
					timeout : 600000,
					url : "allotTraits.do",
					params : {
						fidcls : ids,
						ftype:0,
						frealamount:0
					}, // 参数
					success : function(response, option) {
						var obj = Ext.decode(response.responseText);
						if (obj.success == true) {
						  	djsuccessmsg( obj.msg);
							Ext.getCmp("DJ.order.Deliver.TraitidAllotList").store
									.load();
						} else {
							Ext.MessageBox.alert('错误', obj.msg);
						}
						el.unmask();
					}
				});
			}
		}
	},{
	text : '显示已生成',
	height : 30,
	handler : function() {
		var state=1;
		if(this.getText()=='显示已生成')
		{
			this.setText("显示未生成");
		}else
		{
			state=0;
			this.setText("显示已生成");
		}
		var store = Ext.getCmp('DJ.order.Deliver.TraitidAllotList')
					.getStore();
			store.setDefaultfilter([{
				myfilterfield : "e.fallot",
				CompareType : " = ",
				type : "string",
				value :state
			}]);
			store.setDefaultmaskstring(" #0 ");
			store.load();

	}
	},{
	text : '取消生成管理',
	height : 30,
	handler : function() {
		var tgrid = Ext.getCmp("DJ.order.Deliver.TraitidAllotList");
		var trecord = tgrid.getSelectionModel().getSelection();
		if(trecord.length<1){
				Ext.MessageBox.alert("信息","请选择至少一条特性记录操作！");
				return;
		}
		var grid = Ext.getCmp("DJ.order.Deliver.TraitidMainList.DeliversOrderList");
		var record = grid.getSelectionModel().getSelection();
			if(record.length<1){
				Ext.MessageBox.alert("信息","请选择至少一条要货记录操作！");
				return;
			}
		var ids="";
		for (var i = 0; i < record.length; i++) {
				var fid = record[i].get("fid");
				ids += fid;
				if (i < record.length - 1) {
					ids = ids + ",";
				}
			}
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				timeout : 600000,
				url : "unallotTraits.do",
				params : {
					fidcls : ids
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						djsuccessmsg(obj.msg);
					Ext.getCmp("DJ.order.Deliver.TraitidAllotList").store
								.load();
					Ext.getCmp("DJ.order.Deliver.TraitidMainList.DeliversOrderList").store
								.load();
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});
			
	
	}
	}],
	fields : [{
		name : 'fid'
	}, {
		name : 'fcharacter',
		myfilterfield : 'e.fcharacter',
		myfiltername : '特性',
		myfilterable : true
	}, {
		name : 'fentryamount'
	}, {
		name : 'frealamount'
	}, {
		name : 'fallot',
		myfilterfield : 'e.fallot',
		myfiltername : '是否生成',
		myfilterable : true
	}, {
		name : 'dname'
	}, {
		name : 'fname'
	}],
	columns : [{
		text : 'fid',
		dataIndex : 'fid',
		hidden : true
	}, {
		text : '需求名称',
		dataIndex : 'fname'
	}, {
		text : '方案名称',
		dataIndex : 'dname'
	}, {
		text : '特性',
		dataIndex : 'fcharacter'
	}, {
		text : '数量',
		dataIndex : 'fentryamount'
	}, {
		text : '要货数量',
		dataIndex : 'frealamount'
	}, {
		text : '是否生成',
		dataIndex : 'fallot',
		renderer : function(value) {
			if (value == "1") {
				return "是";
			} else {
				return "否";
			}
		}
	}],
	listeners : {
		itemclick : function(view, record, item, index) {
			var store = Ext.getCmp('DJ.order.Deliver.TraitidMainList.DeliversOrderList')
					.getStore();
			store.setDefaultfilter([{
				myfilterfield : "d.ftraitid",
				CompareType : " = ",
				type : "string",
				value : record.get("fid")
			}]);
			store.setDefaultmaskstring(" #0 ");
			store.load();
		}
	}
})


Ext.define('DJ.order.Deliver.TraitidAmountEdit', {
			extend : 'Ext.Window',
			id : 'DJ.order.Deliver.TraitidAmountEdit',
			modal : true,
			title : "实际数量",
			width : 400,
			height : 180,
			resizable : false,
			layout : 'form',
//			labelAlign : "left",
//			defaults : {
//				xtype : 'textfield',
//				msgTarget : 'side'
//			},
			closable : true,
			initComponent : function() {
						Ext.apply(this, {
			items : [	
						{
		    	   			id : 'DJ.order.Deliver.TraitidAmountEdit.traitid',
		    	   			xtype : 'textfield',
		    	   			fieldLabel : 'traitid',
							hidden : true
		    	   		},{
		    	   			id : 'DJ.order.Deliver.TraitidAmountEdit.fentryamount',
		    	   			fieldLabel : 'fentryamount',
							xtype : 'textfield',
							fieldLabel : '可生成数量',
							hidden : true
		    	   		},{
		    	   			id : 'DJ.order.Deliver.TraitidAmountEdit.frealamount',
		    	   			fieldLabel : 'famount',
							xtype : 'textfield',
							fieldLabel : '生成数量',
							width : 260,
							labelWidth : 70,
							allowBlank : false,
							blankText : '数量不能为空',
							regex : /^(?!0)\d{0,10}$/,
							regexText : "请输入不超过10位大于0的数字"
		    	   		}
		    	   		,Ext.create('Ext.ux.form.DateTimeField', {
		    	   		id:'DJ.order.Deliver.TraitidAmountEdit.farrivetime',
						fieldLabel : '配送时间',
						labelWidth : 70,
						width : 260,
//						name : 'farrivetime',
						format : 'Y-m-d',
						allowBlank : false,
						value:Ext.Date.add(new Date(new Date().setHours(17,0,0,0)),Ext.Date.DAY,1),
						blankText : '配送时间不能为空'
//						 ,onExpand : function() {
//		var a = this.getValue();
//		this.picker.setValue(Ext.isDate(a) ? a : new Date(new Date().setHours(17,0,0,0)));
//	}
	})
		    	   		]}), this.callParent(arguments);
		    	   		},
			buttons : [{
						xtype : "button",
						text : "确定",
						pressed : false,
						handler : function() {

							if (!Ext.getCmp("DJ.order.Deliver.TraitidAmountEdit.frealamount").isValid()) {
								Ext.MessageBox.alert('提示', '输入项格式不正确，请修改后再提交！');
								return;
							}
							if (!Ext.getCmp("DJ.order.Deliver.TraitidAmountEdit.farrivetime").isValid()) {
								Ext.MessageBox.alert('提示', '时间输入项格式不正确，请修改后再提交！');
								return;
							}
							var fids = Ext.getCmp("DJ.order.Deliver.TraitidAmountEdit.traitid").getValue();
							var famount = Ext.getCmp("DJ.order.Deliver.TraitidAmountEdit.fentryamount").getValue();
							var frealamount = Ext.getCmp("DJ.order.Deliver.TraitidAmountEdit.frealamount").getValue();
							var farrivetime=Ext.getCmp("DJ.order.Deliver.TraitidAmountEdit.farrivetime").getValue();
							if(frealamount*1>famount*1){
								Ext.MessageBox.alert('错误', "生成数量之不能大于实际数量！");
								return;
							}
							
							var el = Ext.getCmp("DJ.order.Deliver.TraitidAmountEdit").getEl();
							el.mask("系统处理中,请稍候……");
							
							Ext.Ajax.request({
								timeout : 600000,
								url : "allotTraits.do",
								params : {
									fidcls : fids ,
									frealamount : frealamount ,
									ftype : 1,
									farrivetime:farrivetime
								}, // 参数
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										  djsuccessmsg( obj.msg);
										  if(Ext.getCmp("DJ.order.Deliver.TraitidAllotList")== undefined){
										  	Ext.getCmp("DJ.order.Deliver.TraitidAllotList").store.load();
										  }else{
										  	Ext.getCmp("DJ.order.Deliver.TraitidAllotList").store.load();
										  }
										var windows = Ext.getCmp("DJ.order.Deliver.TraitidAmountEdit");
										windows.close();
										
//										 if(Ext.getCmp("DJ.order.Deliver.TraitidAllotList")!= undefined){
//										 	Ext.getCmp("DJ.traffic.Truckassemble.TruckassembleList").store.load();
//										 }
										
									} else {
										Ext.MessageBox.alert('错误', obj.msg);
									}
									el.unmask();
								}
							});
						
						}
					}
					, {
						xtype : "button",
						text : "取消",
						handler : function() {
							var windows = Ext.getCmp("DJ.order.Deliver.TraitidAmountEdit");
							if (windows != null) {
								windows.close();
							}
						}
					}],
			buttonAlign : "center",
			bodyStyle : "padding-top:15px ;padding-left:30px;padding-right:30px"
		});
		
