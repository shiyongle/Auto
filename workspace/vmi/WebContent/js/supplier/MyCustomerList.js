//var clip = new ZeroClipboard.Client(); 
Ext.define('DJ.supplier.MyCustomerList.AddressEdit',{
	id:'DJ.supplier.MyCustomerList.AddressEdit',
	extend:'Ext.c.BaseEditUI',
	bodyPadding:'15 20',
	width:730,
//	height:200,
	onload:function(){
		this.down('toolbar[dock=top]').hide();
	},
	dockedItems : [ {
					xtype : 'toolbar',
					dock : 'bottom',
					ui: 'footer',
					layout: {
					    pack: 'end'
					},
					items:[{
						text:'保存',
						minWidth:60,
						minHeight:20,
						handler:function(){
							var me = this;
							var cTable = this.up('window').down('cTable');
							var customerid = this.up('window').down('textfield[name=customerid]').getValue();
							try{
								var Address = [];
								Ext.each(cTable.store.data.items,function(item){
									if(Ext.isEmpty(item.data.fname)&&Ext.isEmpty(item.data.flinkman)&&Ext.isEmpty(item.data.fphone)){
														//都为空 不保存
									}else{
										if(Ext.isEmpty(item.data.fname)||Ext.isEmpty(item.data.flinkman)||Ext.isEmpty(item.data.fphone)){
											if(item.dirty){
												if(!Ext.isEmpty(item.data.fname)||!Ext.isEmpty(item.data.flinkman)||!Ext.isEmpty(item.data.fphone)){
													throw "请完善地址信息！";
												}
											}else{
												Address.push(item.data);
											}
										}else{
											Address.push(item.data);
										}
								}
								})
						        Ext.Ajax.request({
		   							url:'saveOrUpdateAddress.do',
		   							params:{'Address':Ext.encode(Address),'customerid':customerid},
		   							success:function(response){
		   								var obj = Ext.decode(response.responseText);
		   								if(obj.success){
		   									djsuccessmsg(obj.msg);
		   									Ext.getCmp(me.up('window').parent).store.load();
		   									me.up('window').close();
		   								}else{
		   									Ext.Msg.alert('错误',obj.msg);
		   								}
		   							}
		   						});
							}catch(e){
								Ext.Msg.alert('提示',e);
							}
						}
						},{
						text:'关闭',
						minWidth:60,
						minHeight:20,
						handler:function(){
							this.up('window').close();
						}
					}]
	}],
	items:[{
		name:'customerid',
		xtype:'textfield',
		hidden:true
	},{
				xtype : 'cTable',
				name : "Address",
				width:'100%',
				height : 50,
				width:660,
				pageSize : 100,
				columnLines:true,
				margin:'0',
				url : "GetsupplierscustAddressList.do",
//				parentfield : "e.fid",
				plugins: [Ext.create('Ext.grid.plugin.CellEditing', {
					clicksToEdit: 1
				})],
				onload:function(){
					var me = this;
					this.store.on('load',function( store, records, index, eOpts ){
						Ext.each(records,function(){
							me.setHeight(me.height+22);
						})
					})
					this.down('toolbar').hide();
					this.store.add(0);
				},
				fields : [{
					name : "fid"
				}, {
					name : "fname"
				}, {
					name : "flinkman"
				},{
					name:"fphone"
				}],
				columns : [{
					dataIndex : "fname",
					text : "地址",
					width:300,
					editor:{
					}
				}, {
					dataIndex : "flinkman",
					text : "收货人",
					flex:1,
					editor:{
					}
				}, {
					dataIndex : "fphone",
					text : "电话",
					flex:1,
					editor:{
						xtype:'numberfield',
						hideTrigger :true
					}
				},{
					xtype:'actioncolumn',
					width:100,
					align:'center',
					text:'操作',
					flex:1,
					id:'DJ.order.Deliver.productdemandfile.productActions',
			        items: [
				        {
				            icon: 'images/delete.gif',
				            tooltip: '删除',
				            handler: function(grid, rowIndex, colIndex) {
				            	grid.store.removeAt(rowIndex);
					            grid.up().setHeight(grid.up().height-22);
				            	if(grid.store.getCount()==0){
				            		grid.up().next().handler();
				            	}
				            }
				        }]
				}]
	},{
		xtype:'button',
		text:'<b>添加地址</b>',
		handler:function(){
			var ctable = this.prev('cTable');
			ctable.setHeight(ctable.height+22);
			ctable.store.add(0);
		}
	}]
})
Ext.define('DJ.supplier.MyCustomerList', {
	extend : 'DJ.myComponent.grid.MySimpleConciseGrid',
	 title : "我的客户",
	id : 'DJ.supplier.MyCustomerList',
	 pageSize : 50,

	closable : true,// 是否现实关闭按钮,默认为false
	url : 'getMyCustomersList.do',
	
	
	Delurl : "DelMyCustomer.do",
	EditUI : "DJ.supplier.mainCustomerEdit",

	pagingtoolbarDock : 'bottom',

	mixins : ['DJ.tools.grid.MySimpleGridMixer',
			'DJ.tools.grid.mixer.MyGridSearchMixer'],

	isFirststateShower : true,
	showSearchAllBtn : false,
	statics : {
		editAction:function(){
			var me = Ext.getCmp('DJ.supplier.MyCustomerList');
			var record = me.getSelectionModel().getSelection();
			if(record.length!=1){
				Ext.Msg.alert('提示','请选择一条数据！');
				return;
			}
			if (me.EditUI == null || Ext.util.Format.trim(me.EditUI) == "") {
					Ext.MessageBox.alert("错误", "没有指定编辑界面");
					return;
			}
			var editui = Ext.create(me.EditUI,{
				editstate:"edit",
				parent:me.id,
				modal:true
			});
			var form = editui.down('form').getForm();
			form.setValues({
				fid:record[0].get('fid'),
				fname:record[0].get('fname'),
				fphone:record[0].get('fphone'),
				ffax:record[0].get('ffax'),
				flinkman:record[0].get('flinkman'),
				fartificialpersonphone:record[0].get('fartificialpersonphone'),
				fdescription:record[0].get('fdescription'),
				fcreatetime:record[0].get('fcreatetime'),
				fcreatorid:record[0].get('fcreatorid')
			})
			var ctable = editui.down('cTable');
			ctable.store.setDefaultfilter([{
						myfilterfield : "r.fcustomerid",
						CompareType : "=",
						type : "string",
						value : record[0].get('fid')
					}]);
			ctable.store.setDefaultmaskstring('#0');
			ctable.store.loadPage(1);
			editui.show();
		},
		delAction:function(){
			var me = Ext.getCmp('DJ.supplier.MyCustomerList');
			var record = me.getSelectionModel().getSelection();
			if (me.Delurl == null || Ext.util.Format.trim(me.Delurl) == "") {
					Ext.MessageBox.alert("错误", "没有指定删除路径");
					return;
				}
			if(record.length ==0){
				Ext.Msg.alert('提示','请选择数据！');
				return;
			}
			Ext.MessageBox.confirm("提示", "是否确认删除选中的内容?", function(btn) {
					if (btn == "yes") {
						try {
							me.Action_BeforeDelButtonClick(me, record);
							me.Action_DelButtonClick(me, record);
							me.Action_AfterDelButtonClick(me, record);
						} catch (e) {
							Ext.MessageBox.alert("提示", e);
						}
					}
			});
		},
		addressAction:function(title){
			var me = Ext.getCmp('DJ.supplier.MyCustomerList');
			var record = me.getSelectionModel().getSelection();
			var win = Ext.create('DJ.supplier.MyCustomerList.AddressEdit',{
				'title':title,
				parent:me.id
			});
			if (record.length != 1) {
				Ext.MessageBox.alert("提示", "请选择一条数据!");
				return;
			}
			win.show();
			if(title=='管理地址'){
				var fcustomerid = record[0].get('fid');
				win.down('textfield[name=customerid]').setValue(fcustomerid);
				var cTable = win.down('cTable');
				cTable.store.setDefaultfilter([{
							myfilterfield : 'tba.fcustomerid',
							CompareType : "=",
							type : "string",
							value : fcustomerid
				}]);
				cTable.store.setDefaultmaskstring("#0");
				cTable.store.loadPage(1);
			}
		}
	},
	showInviteWayWindow: function(grid,fidcls){
		Ext.create('Ext.Window',{
			modal: true,
			resizable: false,
			title: '选择登录方式',
			width: 150,
			autoShow: true,
			layout: {
		        type: 'vbox',
		        align: 'stretch'
		    },
			sendInvite: function(way){
				var win = this;
				win.getEl().mask('正在处理中...');
				Ext.Ajax.request({
					url: 'sendMessageForCustomers.do',
					params: {
						fidcls: fidcls,
						way: way || ''
					},
					success: function(res){
						var obj = Ext.decode(res.responseText);
						win.close();
						if(obj.success){
							djsuccessmsg(obj.msg);
							grid.store.load();
						}else{
							Ext.Msg.alert('错误',obj.msg);
						}
					},
					failure: function(){
						win.close();
						Ext.Msg.alert('错误','保存失败，请检查网络状态或刷新页面重试！');
					}
				});
			},
			items: [{
				xtype: 'button',
				text: '网　页',
				height: 30,
				handler: function(){
					var win = this.up('window');
					win.sendInvite(1);
				}
			},{
				xtype: 'button',
				text: '安　卓',
				height: 30,
				handler: function(){
					var win = this.up('window');
					win.sendInvite(2);
				}
			},{
				xtype: 'button',
				text: '苹　果',
				height: 30,
				handler: function(){
					var win = this.up('window');
					win.sendInvite(3);
				}
			}]
		});
	},
	cusTopBarItems : [{
		text:'新增',
		handler:function(){
			var me = this.up('grid');
			if (me.EditUI == null || Ext.util.Format.trim(me.EditUI) == "") {
					Ext.MessageBox.alert("错误", "没有指定编辑界面");
					return;
			}
			var editui = Ext.create(me.EditUI,{
				editstate:"add",
				parent:me.id,
				modal:true
			});
			editui.show();
		}
	},{
		text:'修改',
		hidden:true,
		handler:function(){
			DJ.supplier.MyCustomerList.editAction();
		}
	},{
		text:'删除',
		hidden:true,
		handler:function(){
			var me = this.up('grid');
			if (me.Delurl == null || Ext.util.Format.trim(me.Delurl) == "") {
					Ext.MessageBox.alert("错误", "没有指定删除路径");
					return;
				}
				var record = me.getSelectionModel().getSelection();
				if (record.length == 0) {
					Ext.MessageBox.alert("提示", "请先选择您要操作的行!");
					return;
				}
				
			Ext.MessageBox.confirm("提示", "是否确认删除选中的内容?", function(btn) {
					if (btn == "yes") {
						try {
						me.Action_BeforeDelButtonClick(me, record);
						me.Action_DelButtonClick(me, record);
						me.Action_AfterDelButtonClick(me, record);
						} catch (e) {
							Ext.MessageBox.alert("提示", e);
						}
					}
			});

		}
	},{
		text:'新增地址',
		handler:function(){
			DJ.supplier.MyCustomerList.addressAction('新增地址');
		}
	},{
		text:'邀请',
		handler:function(){
			var grid = this.up('grid'),
				records = grid.getSelectionModel().getSelection(),
				fids=[];
			if(records.length===0){
				Ext.Msg.alert('提示','请先选择客户再操作！');
				return;
			}
			var fisinvited=false;
			Ext.each(records,function(record,index){
				if(record.get("fisinvited")==1){
					fisinvited=true;
				}
				fids.push(record.get('fid'));
			});
			if(fisinvited){
				Ext.MessageBox.show({title:'提示',msg: '已邀请，是否继续邀请？', buttons: Ext.MessageBox.YESNO,buttonText:{  yes: "是",   no: "否" },fn:function(btn, text){
								if(btn=="yes"){
									grid.showInviteWayWindow(grid,fids.join(','));
								}
							}
				})
			}else{
				grid.showInviteWayWindow(grid,fids.join(','));
			}
			
		}
	},{
		text:'邀请信息',
		handler:function(){
			
			var grid = this.up('grid'),
				records = grid.getSelectionModel().getSelection();
			if(records.length!==1){
				Ext.Msg.alert('提示','请选择一位客户再操作！');
				return;
			}
			Ext.Ajax.request({
				url: 'sendMessageForCustomers.do',
				params: {
					fidcls: records[0].get('fid')
				},
				success: function(res){
					var obj = Ext.decode(res.responseText);
					if(obj.success){
						grid.store.load();
						if(obj.msg){
							var info = obj.msg.split('_');
							Ext.create('Ext.Window',{
								modal: true,
								width: 300,
								minHeight: 50,
								layout: 'fit',
								autoShow: true,
								items: [{
									xtype: 'displayfield',
									value: '您好！我司已升级了接单系统，请登录 www.olcps.com ，您的帐号:'+info[0]+'，密码：8888，欢迎使用接单系统下单。<br/>' +
											'安卓版点击http://www.olcps.com/android/indexcps.html<br/>' +
											'苹果版点击http://t.cn/R2nUgF1【'+info[1]+'】'
								}]
							})
						}
					}else{
						Ext.Msg.alert('错误',obj.msg);
					}
				},
				failure: function(){
					Ext.Msg.alert('错误','保存失败，请检查网络状态或刷新页面重试！');
				}
			});
		
		}
	}, {

		xtype : 'tbspacer',
		flex : 1

	}, {

		xtype : 'mymixedsearchbox',
		condictionFields : ['c.fname'],
		tip : '输入客户名称',
		useDefaultfilter : true,
		filterMode : true

	}

	],

	onload : function() {
		
		this.magnifier = Ext.create('Ext.ux.form.Magnifier');
		var me = this;
//		var task = new Ext.util.DelayedTask(function(){
//			// 新建一个对象
//			clip.setHandCursor(true); // 设置鼠标为手型
//			var localUrl = window.location.href;
//			clip.setText("");
//			clip.glue(me.down('button[text=邀请信息]').id,me.down('button[text=邀请信息]').id);
//			
//			clip.addEventListener("mousedown",function(){
//				var grid = me,
//					records = grid.getSelectionModel().getSelection();
//				if(records.length!==1){
//					Ext.Msg.alert('提示','请选择一位客户再操作！');
//					return;
//				}
//				Ext.Ajax.request({
//					url: 'sendMessageForCustomers.do',
//					params: {
//						fidcls: records[0].get('fid')
//					},
//					success: function(res){
//						var obj = Ext.decode(res.responseText);
//						if(obj.success){
//							grid.store.load();
//							if(obj.msg){
//								var info = obj.msg.split('_');
//								clip.setText('您好！我司已升级了接单系统，请登录 www.olcps.com ，您的帐号:'+info[0]+'，密码：8888，欢迎使用接单系统下单。【'+info[1]+'】');
////									clip.addEventListener( "complete", function(){
//									Ext.Msg.alert('提示','邀请信息已复制剪贴板，请自己粘贴！');
////									});
//							}
//						}else{
//							Ext.Msg.alert('错误',obj.msg);
//						}
//					},
//					failure: function(){
//						Ext.Msg.alert('错误','保存失败，请检查网络状态或刷新页面重试！');
//					}
//				});
//			})
//			
//	});
//		task.delay(600);
		
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

	fields : [{
		name : 'fid'
	}, {
		name : 'fname'
	}, {
		name : 'flinkman'
	}, {
		name : 'fphone'
	}, {
		name : 'fartificialpersonphone'
	}, {
		name : 'fcreatetime'

	}, 'fisinvited', 'fdescription', 'addressname','ffax'

	],


	columns : [{
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true
	},{
		xtype:'templatecolumn',
		text:'操作',
//		dateIndex:'',
		align:'center',
		width:250,
		tpl:["<a href='javaScript:DJ.supplier.MyCustomerList.editAction();'><span style=\"background:url('images/edit.gif')\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span dataqtip=''>修改</span></a>","&nbsp;&nbsp;","<a href='javaScript:DJ.supplier.MyCustomerList.delAction()'><span style=\"background:url('images/delete.png')\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span dataqtip=''>删除</span></a>","&nbsp;&nbsp;","<a href='javaScript:DJ.supplier.MyCustomerList.addressAction(\"管理地址\")'><span style=\"background:url('images/delete.png')\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span dataqtip=''>管理地址</span></a>"]
	},{
		header : '客户名称',
		align : 'center',
		dataIndex : 'fname',
		flex:1
	}, {
		header : '联系人',
		align : 'center',
		dataIndex : 'flinkman',
		flex:1
	}, {
		header : '手机号',
		align : 'center',
		dataIndex : 'fphone',
		flex:1
	}, {
		header : '座机号',
		align : 'center',
		dataIndex : 'fartificialpersonphone',
		flex:1
	}, {

		header : '传真',
		align : 'center',
		dataIndex : 'ffax',
		flex:1
	}, {

		header : '地址',
		align : 'center',
		dataIndex : 'addressname',
		width:200,
		renderer:function(val,grid){
			grid.style="height:30px";
			if(val.length>=10){
				return val.substr(0,10)+"<br/>"+val.substr(10,val.length-1);
			}else{
				return val;
			}
		}
	}, {

		header : '是否邀请',
		align : 'center',
		dataIndex : 'fisinvited',
		flex:1,
		renderer:function(val){
			if(val==0){
				return '否';
			}else if(val==1){
				return '是';
			}
		}
	}, {
		header : '建档时间',
		align : 'center',
		dataIndex : 'fcreatetime',
		flex:1,
		renderer:function(val){
			return val.substr(0,16);
		}
	},{
		header : '备注',
		align : 'center',
		dataIndex : 'fdescription',
		flex:1
	}

	],
	selModel : Ext.create('Ext.selection.CheckboxModel')
});