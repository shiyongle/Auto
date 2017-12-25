var clip = new ZeroClipboard.Client(); 
Ext.define('DJ.supplier.LinkedCustomerList', {
	extend : 'Ext.c.GridPanel',
	title : "客户管理",
	id : 'DJ.supplier.LinkedCustomerList',
	pageSize : 50,
	closable : true,
	url : 'getCustomerListBySupplier.do',
	Delurl : "deleteForCustomersBysupplier.do",
	EditUI : "DJ.supplier.LinkedCustomerEdit",
	exporturl : "",// 导出为EXCEL方法
	onload : function() {
		var id = this.id;
		var me = this;
		Ext.getCmp(id+'.viewbutton').setVisible(false);
//		Ext.getCmp(id+'.delbutton').setVisible(false);
		Ext.getCmp(id+'.exportbutton').setVisible(false);
//		var task = new Ext.util.DelayedTask(function(){
//				// 新建一个对象
//				clip.setHandCursor(true); // 设置鼠标为手型
//				var localUrl = window.location.href;
//				clip.setText("");
//				clip.glue(me.down('button[text=邀请信息]').id,me.down('button[text=邀请信息]').id);
//				
//				clip.addEventListener("mousedown",function(){
//					var grid = me,
//						records = grid.getSelectionModel().getSelection();
//					if(records.length!==1){
//						Ext.Msg.alert('提示','请选择一位客户再操作！');
//						return;
//					}
//					Ext.Ajax.request({
//						url: 'sendMessageForCustomers.do',
//						params: {
//							fidcls: records[0].get('fid')
//						},
//						success: function(res){
//							var obj = Ext.decode(res.responseText);
//							if(obj.success){
//								grid.store.load();
//								if(obj.msg){
//									var info = obj.msg.split('_');
//									clip.setText('您好！我司已升级了接单系统，请登录 www.olcps.com ，您的帐号:'+info[0]+'，密码：8888，欢迎使用接单系统下单。【'+info[1]+'】');
////									clip.addEventListener( "complete", function(){
//										Ext.Msg.alert('提示','邀请信息已复制剪贴板，请自己粘贴！');
////									});
//								}
//							}else{
//								Ext.Msg.alert('错误',obj.msg);
//							}
//						},
//						failure: function(){
//							Ext.Msg.alert('错误','保存失败，请检查网络状态或刷新页面重试！');
//						}
//					});
//				})
//				
//		});
//		task.delay(600);
	},
	Action_BeforeEditButtonClick: function(a,b){
		var records = this.getSelectionModel().getSelection();
		if(records.length!=1){
			throw '请选择一条记录进行操作！';
		}
//		if(!records[0].get('fphone')){
//			throw '此客户信息不是您创建，无法更改！';
//		}
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
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	custbar: [{
		text: '邀    请',
		height: 30,
		handler: function(){
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
		text: '邀请信息',
		handler: function(){
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
								width: 350,
								minHeight: 100,
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
	},{
//					xtype : 'menuitem',
					text:'导入客户',
					handler:function(){
						var m = this;
						Ext.create('Ext.Window',{
							height:150,
							modal:true,
							resizable :false,
							width : 400,
							layout:'form',
							bodyPadding : 15,
							title:'客户上传',
							/*listeners:{
								afterrender:function(me){
									var val = {
										'fid':m.up('grid').down('combobox[name=fcustomerid]').getValue(),
										'fname':m.up('grid').down('combobox[name=fcustomerid]').rawValue
									};
									me.down('combobox[name=fcustomerid]').setmyvalue(val);
								}
							},*/
							items:[{
								xtype : 'form',
								baseCls : 'x-plain',
								items:[/*{
									name:'fcustomerid',
									labelWidth : 50,
									width:350,
									msgTarget : 'side',
									allowBlank : false,
					        		fieldLabel : '客户',
					        		xtype:'cCombobox',
					        		displayField:'fname', // 这个是设置下拉框中显示的值
					        	    valueField:'fid', // 这个可以传到后台的值
					        	    blankText:'请选择客户',
					        	    editable: false, // 可以编辑不
					        	    MyConfig : {
					 					width : 800,//下拉界面
					 					height : 200,//下拉界面
					 					url : 'getCustomerBysupplier.do',  //下拉数据来源控制为当前用户关联过的客户;
					 					fields : [ {
					 						name : 'fid'
					 					}, {
					 						name : 'fname',
					 						myfilterfield : 'c.fname', //查找字段，发送到服务端
					 						myfiltername : '客户名称',//在过滤下拉框中显示的名称
					 						myfilterable : true//该字段是否查找字段
					 					}, {
					 						name : 'fnumber'
					 					}, {
					 						name : 'findustryid'
					 					}, {
					 						name : 'faddress'
					 					}, {
					 						name : 'fisinternalcompany',
					 						convert:function(value,record)
											{
												if(value=='1')
												{	
													return true;
												}else{
													return false;
												}	
											}
					 					} ],
					 					columns : [ {
					 						text : 'fid',
											dataIndex : 'fid',
											hidden : true,
											sortable : true
										}, {
											text : '客户编号',
											dataIndex : 'fnumber',
											sortable : true,
											flex:1
										}, {
											text : '客户名称',
											dataIndex : 'fname',
											sortable : true,
											flex:1
										}, {
											text : '客户地址',
											dataIndex : 'faddress',
											sortable : true,
											width : 250,
											flex:1
										}]
					 				}
								},*/
								{
								xtype : 'filefield',
								name : 'fileName',
								fieldLabel : '上传',
								labelWidth : 50,
								width:350,
								msgTarget : 'side',
								allowBlank : false,
								anchor : '100%',
								regex : /(.)+((\.xlsx)|(\.xls)(\w)?)$/i,
								regexText : "文件格式选择不正确",
								buttonText : '选择文件'
							}]
							}],
							buttons : [{
								text : '上传',
								handler : function() {
									var me = this;
									var form = this.up('window').down('form').getForm();
//									var fcustomerid = form.findField('fcustomerid').getValue();
									if (form.isValid()) {
										form.submit({
											url : 'saveUploadCustomerExcelData.do',  
											waitMsg : '正在保存文件...',
											success : function(fp, o) {
												Ext.Msg.show({
													title : '提示信息',
													msg : o.result.msg,
													minWidth : 200,
													modal : true,
													buttons : Ext.Msg.OK
												})
												form.findField('fileName').setRawValue('');
												me.up('window').close();
												m.up('grid').store.load();
											},
											failure : function(fp, o) {
												Ext.Msg.show({
													title : '提示信息',
													msg : o.result.msg,
													minWidth : 200,
													modal : true,
													buttons : Ext.Msg.OK
												})
												form.findField('fileName').setRawValue('');
											}
										});
									}
								}
							}]
						}).show();
					}
				},{
					text:'客户导入模板下载',
					handler : function() {
						window.open('downloadCustomerExcel.do','下载模板')
						}
				},{
					text:'新增地址',
					handler:function(){
						var record = this.up('grid').getSelectionModel().getSelection();
						if(record.length!=1){
							Ext.Msg.alert('提示','请选择一条数据！');
							return;
						}
						var winAdd = Ext.create('DJ.System.supplier.AddressManageEdit',{
							fcustomerid: record[0].get('fid')
						});
//				        winAdd.loadfields(record[0].get("productid"));
				        winAdd.seteditstate('add');
				        winAdd.show();
					}
				}],
	fields: [{
		name: 'fid'
	},{
		name: 'fcustname',
		myfilterfield : 'e.fname',
		myfiltername : '客户名称',
		myfilterable : true
	},{
		name: 'fphone'
	},{
		name: 'fdescription'
	},'findustryid','ftxregisterno','fbizregisterno','fartificialperson','faddress','fcreatetime','flastupdateuserid','flastupdatetime','fdescription','flastupdateusername','fpackbuyamount','fpacktotal','fisinvited'],
	columns: [{
		text: '客户名称',
		dataIndex: 'fcustname',
		width: 250,
		sortable: true
	},{
		text: '下单手机号',
		dataIndex: 'fphone',
		width: 200,
		sortable: true
	},{
		text: '是否邀请',
		dataIndex: 'fisinvited',
		renderer:function(val){
			return val==="1"?"是":"否";
		},
		sortable: true
	},{
		text:'行业',
		dataIndex:'findustryid'
	},{
		text:'税务登记号',
		dataIndex:'ftxregisterno'
	},{
		text:'工商注册号',
		dataIndex:'fbizregisterno'
	},{
		text:'法人代表',
		dataIndex:'fartificialperson'
	},{
		text:'包装总用量',
		dataIndex:'fpacktotal'
	},{
		text:'包装采购量',
		dataIndex:'fpackbuyamount'
	}/*,{
		text:'地址',
		dataIndex:'faddress'
	}*/,{
		text:'建档时间',
		dataIndex:'fcreatetime',
		renderer:function(val){
			return val.substr(0,16);
		}
	},{
		text:'修改人',
		dataIndex:'flastupdateusername'
	},{
		text:'修改时间',
		dataIndex:'flastupdatetime',
		renderer:function(val){
			return val.substr(0,16);
		}
	},{
		text: '备注',
		dataIndex: 'fdescription',
		sortable: true,
		flex: 1
	}]
});