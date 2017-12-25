Ext.define('DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel', {
	extend : 'Ext.Window',
	id : 'DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel',
	modal : true,
	title : "Excel文件上传",
	width : 450,// 230, //Window宽度
	resizable : false,
	closable : true, // 关闭按钮，默认为true
	items : [{
		xtype : 'form',
		id : 'DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel.form',
		baseCls : 'x-plain',
		fieldDefaults : {
			labelWidth : 400
		},
		width : 400,
		bodyPadding : 10,
		margin : '20 10 20 20',
		frame : true,
		items : [
			{
				id : 'DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel.form.fcustomerid',
				name:'fcustomerid',
				fieldLabel : '上传',
				labelWidth : 50,
				msgTarget : 'side',
				allowBlank : false,
				anchor : '83%',
        		fieldLabel : '客户',
        		xtype:'cCombobox',
        		displayField:'fname', // 这个是设置下拉框中显示的值
        	    valueField:'fid', // 这个可以传到后台的值
        	    blankText:'请选择客户',
        	    style: 'margin-bottom:20px',
        	    editable: false, // 可以编辑不
        	    MyConfig : {
 					width : 800,//下拉界面
 					height : 200,//下拉界面
 					url : 'GetCustomerListByUserId.do',  //下拉数据来源控制为当前用户关联过的客户;
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
						text : '编码',
						dataIndex : 'fnumber',
						sortable : true
					}, {
						text : '客户名称',
						dataIndex : 'fname',
						sortable : true
					}, {
						text : '行业',
						dataIndex : 'findustryid',
						sortable : true
					}, {
						text : '地址',
						dataIndex : 'faddress',
						sortable : true,
						width : 250
					}, {
						text : '内部客户',
						dataIndex : 'fisinternalcompany',
						xtype:'checkcolumn',
						processEvent : function() {
						},
						sortable : true
					}]
 				}
			},{
				xtype: 'component',
				html: '<span style="margin-right:27px">上传:</span><input id="abc" name="fileName" type="file" multiple/>'
			},
			{
			xtype : 'filefield',
			name : 'fileName',
			hidden: true,
			fieldLabel : '上传',
			labelWidth : 50,
			msgTarget : 'side',
//			allowBlank : false,
			anchor : '100%',
//			regex : /(.)+((\.xlsx)|(\.xls)(\w)?)$/i,
			regexText : "文件格式选择不正确",
			buttonText : '选择文件',
			extractFileInput: function(){
				return document.getElementById('abc');
			}
			/*afterrender: function(){
				//extractFileInput();
				console.log(document.getElementById('abc'));
				this.button.fileInputEl.dom = document.getElementById('abc');
			}*/
		}],
		buttons : [{
			text : '上传',
			handler : function() {
				var form = this.up('form').getForm();
//				form.hasUpload = function(){return true;};
				var fcustomerid = form.findField('fcustomerid').getValue();
//				return;
				if (form.isValid()) {
					form.submit({
//						url : 'uploadFile.do?action='+encodeURIComponent(fcustomerid),  custGenerateDeliversList
						url : 'saveUploadExcelData.do?action='+encodeURIComponent(fcustomerid),
						waitMsg : '正在保存文件...',
						success : function(fp, o) {
							Ext.Msg.show({
								title : '提示信息',
								msg : o.result.msg,
								minWidth : 200,
								modal : true,
								buttons : Ext.Msg.OK
							})
//							form.findField('fileName').setRawValue('');
							Ext.getCmp("DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel").close();
							Ext.getCmp("DJ.order.Deliver.custGenerateDeliversList").store.load();
						},
						failure : function(fp, o) {
							Ext.Msg.show({
								title : '提示信息',
								msg : o.result.msg,
								minWidth : 200,
								modal : true,
								buttons : Ext.Msg.OK
							})
							Ext.getCmp("DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel").close();
//							form.findField('fileName').setRawValue('');
						}
					});
				}
			}
		}]
	}]
});

Ext.define('DJ.order.Deliver.custGenerateDeliversList', {
	extend : 'Ext.c.GridPanel',
	title : "要货申请导入",
	id : 'DJ.order.Deliver.custGenerateDeliversList',
	pageSize : 200,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'GetGenerateDeliversList.do',
	Delurl : "",
	EditUI : "",
	exporturl : "",// 导出为EXCEL方法
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		// alert("DeliverList");
		Ext.getCmp('DJ.order.Deliver.custGenerateDeliversList.addbutton').setVisible(false);
		Ext.getCmp('DJ.order.Deliver.custGenerateDeliversList.editbutton').setVisible(false);
		Ext.getCmp('DJ.order.Deliver.custGenerateDeliversList.delbutton').setVisible(false);
		Ext.getCmp('DJ.order.Deliver.custGenerateDeliversList.exportbutton').setVisible(false);
		this.magnifier = Ext.create('Ext.ux.form.Magnifier');
	},
	listeners: {
		itemclick: function(me,record,item, index, e){
			if(record.get('fcusproductid')){
				this.magnifier.loadImages({
					fid: record.get('fcusproductid')
				});
				var coord = e.getXY();
				this.magnifier.showAt({
					left: coord[0] + 80,
					top: coord[1] + 5
				});
			}
		}
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
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	custbar : [
		/*{
		// id : 'DelButton',
		text : '导入正泰发放',
		height : 30,
		handler : function() {
			var grid = Ext.getCmp("DJ.order.Deliver.custGenerateDeliversList");
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				timeout : 600000,
				url : "ImportZTcusdelivers.do",
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						djsuccessmsg( obj.msg);
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});
		}
	}, {
		// id : 'DelButton',
		text : '修改交期',
		height : 30,
		handler : function() {
			var grid = Ext.getCmp("DJ.order.Deliver.custGenerateDeliversList");
			var record = grid.getSelectionModel().getSelection();
			var ids = "";
			for (var i = 0; i < record.length; i++) {
				var fid = record[i].get("fid");
				ids += "" + fid + "";
				if (i < record.length - 1) {
					ids = ids + ",";
				}
			}
			var edit=Ext.create('Ext.window.Window', {
				title : '修改发放交期',
				id:"DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel.changearrivetime",
				height : 200,
				width : 400,
				items : [{
					name : 'fid',
					id : "DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel.changearrivetime.id",
					xtype : 'textfield',
					labelWidth : 50,
					width : 260,
					hidden : true
				}, Ext.create('Ext.ux.form.DateTimeField', {
					id:"DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel.changearrivetime.arrivetime",
					fieldLabel : '配送时间',
					labelWidth : 70,
					width : 260,
					name : 'farrivetime',
					format : 'Y-m-d',
					allowBlank : false,
					blankText : '配送时间不能为空'
				})],
				buttons : [{
					text : '保存',
					handler : function() {
						
					
					if(!Ext
					.getCmp("DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel.changearrivetime.arrivetime")
					.validate())
					{
						Ext.MessageBox.alert('错误','配送时间不能为空' );
						return 
					}
						var myedit=Ext
					.getCmp("DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel.changearrivetime");
						var fidcls=Ext
					.getCmp("DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel.changearrivetime.id")
					.getValue();
					var arrivetime=Ext
					.getCmp("DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel.changearrivetime.arrivetime")
					.getValue();
					
						Ext.Ajax.request({
							timeout : 600000,
							url : "savegenerateDeliversarrivetime.do",
							params : {
								fidcls : fidcls,
								arrivetime : arrivetime
							}, // 参数
							success : function(response, option) {
								var obj = Ext.decode(response.responseText);
								if (obj.success == true) {
									Ext.MessageBox.alert('成功', obj.msg);
									Ext
											.getCmp("DJ.order.Deliver.custGenerateDeliversList").store
											.load();
								} else {
									Ext.MessageBox.alert('错误', obj.msg);
								}
								myedit.close();
								grid.store.load();
							}
						});
					}
				}]
			});
			edit.show();
			Ext
					.getCmp("DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel.changearrivetime.id")
					.setValue(ids);
		}
	}, {
		text : '生成要货申请',
		height : 30,
		handler : function() {
			var grid = Ext.getCmp("DJ.order.Deliver.custGenerateDeliversList");
			var record = grid.getSelectionModel().getSelection();
			if (record.length < 1) {
				Ext.MessageBox.alert("信息", "请选择至少一条记录导入EAS！");
				return;
			}
			var ids = "";
			for (var i = 0; i < record.length; i++) {
				var fid = record[i].get("fid");
				ids +=fid;
				if (i < record.length - 1) {
					ids = ids + ",";
				}
			}
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				timeout : 600000,
				url : "generateDelivers.do",
				params : {
					fids : ids
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						//Ext.MessageBox.alert('成功', obj.msg);
						Ext.getCmp("DJ.order.Deliver.custGenerateDeliversList").store
								.load();
						djsuccessmsg( obj.msg);
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});
		}
	}, */
		{
		// id : 'DelButton',
		text : 'Excel文件上传',
		height : 30,
		handler : function() {
			var loadupFormpanel = Ext.getCmp("DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel");
			if (loadupFormpanel == null) {
				loadupFormpanel = Ext.create("DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel");
			}
			var grid = Ext.getCmp("DJ.order.Deliver.custGenerateDeliversList");
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				timeout : 600000,
				url : "GetCustomerListByUserId.do",
//				params : {
//					fids : ids
//				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
//						Ext.MessageBox.alert('成功', obj.msg);
//						Ext.getCmp("DJ.order.Deliver.custGenerateDeliversList").store.load();
						if(obj.data.length==1){
							var cform = Ext
								.getCmp("DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel.form")
								.getForm();
						cform.findField('fcustomerid').setmyvalue("\"fid\":\""
								+ obj.data[0].fid + "\",\"fname\":\""
								+ obj.data[0].fname + "\"");
//								Ext.getCmp("DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel.form.fcustomerid").setdisabled(true);
//								cform.findField('fcustomerid').setdisabled(true);
						}
					} else {
//						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});
			loadupFormpanel.show();
		}
	},{
		text:'批量订单模板下载',
		handler : function() {
			window.open('downloadFile.do','下载模板')
			}
	}],
	fields : [{
		name : 'fid'
	}, {
		name : 'fmaktx'
	}, {
		name : 'freqaddress'
	}, {
		name : 'faddressid'
	}, {
		name : 'freqdate'
	}, {
		name : 'faddress',
		myfilterfield : 'a.fdetailaddress',
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
		name : 'fcustname',
		myfilterfield : 'c.fname',
		myfiltername : '客户名称',
		myfilterable : true
	}, {
		name : 'fsupplier',
		myfilterfield : 'sup.fname',
		myfiltername : '制造商',
		myfilterable : true
	}, {
		name : 'cutpdtname',
		myfilterfield : 'cpdt.fname',
		myfiltername : '包装物名称',
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
	}, {
		name : 'farrivetime',
		myfilterfield : 'd.farrivetime',
		myfiltername : '配送时间',
		myfilterable : true
	}, {
		name : 'fisread'
	}, {
		name : 'creator'
	}, {
		name : 'fcreatetime'
	},{
		name : 'ftype'
	},'fproductmatching'],
	columns : [Ext.create('DJ.Base.Grid.GridRowNum',{
		header:'序号'
	}),{
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true

	}, {
		'header' : 'fcustomerid',
		'dataIndex' : 'fcustomerid',
		hidden : true,
		hideable : false,
		sortable : true

	}, {
		'header' : 'faddressid',
		'dataIndex' : 'faddressid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : 'fcusproductid',
		'dataIndex' : 'fcusproductid',
		hidden : true,
		hideable : false,
		sortable : true

	}, {
		'header' : '客户名称',
		'dataIndex' : 'fcustname',
		width : 110,
		sortable : true
	}, {
		'header' : '制造商',
		'dataIndex' : 'fsupplier',
		width : 110,
		sortable : true
	},{
		'header' : '发放产品名称',
		width : 180,
		'dataIndex' : 'fmaktx',
		sortable : true
	}, {
		'header' : '发放地址',
		width : 120,
		'dataIndex' : 'freqaddress',
		sortable : true
	}, {
		'header' : '申请单号',
		'dataIndex' : 'fnumber',
		hidden : true,
		sortable : true
	},{
		text : '类型',
		dataIndex : 'ftype',
		sortable : true,
		renderer:function(val){
			var show;
			switch (val) {
			case '0':
				show = '要货';
				break;
			default:
				show = '备货';
				break;
			}
			return show;
		}
	}, {
		'header' : '导入时间',
		'dataIndex' : 'fcreatetime',
		width : 140,
		sortable : true
	}, {
		'header' : '导入人',
		'dataIndex' : 'creator',
		width : 50,
		sortable : true
	}, {
		'header' : '发放配送时间',
		'dataIndex' : 'freqdate',
		width : 140,
		sortable : true
	}, {
		'header' : '发放人',
		'dataIndex' : 'flinkman',
		width : 50,
		sortable : true
	}, {
		'header' : '联系电话',
		'dataIndex' : 'flinkphone',
		hidden : true,
		sortable : true
	}, {
		'header' : '发放数量',
		width : 60,
		'dataIndex' : 'famount',
		sortable : true
	}, {
		'header' : '配送时间',
		'dataIndex' : 'farrivetime',
		width : 140,
		sortable : true
	}, {
		'header' : '客户产品',
		'dataIndex' : 'cutpdtname',
		width : 180,
		editor : {
			valueField : 'fname', // 组件隐藏值
			cautoquery:false,
			id : "DJ.order.Deliver.custGenerateDeliversList.fcusproductid",
			name : "cutpdtname",
			xtype : 'cCombobox',
			displayField : 'fname',// 组件显示值
			// fieldLabel : '客户产品 ',
			width : 260,
			labelWidth : 70,
			onExpand : function() {
				this.getStore().loadPage(1);
				var c2 = this, c3 = c2.listKeyNav, c4 = c2.selectOnTab, c5 = c2
						.getPicker();
				c2.beforeExpand();
				if (c5 && c3) {
					c5.store.loadPage(0x1)
				};
				if (c3) {
					c3.enable()
				} else {
					c3 = c2.listKeyNav = new Ext.view.BoundListKeyNav(this.inputEl, {
								boundList : c5,
								forceKeyDown : true,
								tab : function(c6) {
									if (c4) {
										this.selectHighlighted(c6);
										c2.triggerBlur()
									};
									return true
								},
								enter : function(c6) {
									var c7 = c5.getSelectionModel(), c8 = c7.getCount();
									this.selectHighlighted(c6);
									if (!c2.multiSelect && c8 === c7.getCount()) {
										c2.collapse()
									}
								}
							})
				};
				if (c4) {
					c2.ignoreMonitorTab = true
				};
				Ext.defer(c3.enable, 0x1, c3);
				c2.inputEl.focus();
				c2.lastQuery = _$[86]
			},
			beforeExpand:function(){
				var grid = Ext.getCmp("DJ.order.Deliver.custGenerateDeliversList.fcusproductid");
				var fproductmatching = Ext.getCmp('DJ.order.Deliver.custGenerateDeliversList').getSelectionModel().getSelection()[0].data.fproductmatching;
				if(Ext.isEmpty(fproductmatching)){
					grid.setDefaultfilter([{
						myfilterfield : "fcustomerid",
						CompareType : "like",
						type : "string",
						value : Ext.getCmp('DJ.order.Deliver.custGenerateDeliversList').getSelectionModel().getSelection()[0].data.fcustomerid
					},{
						myfilterfield : "feffect",
						CompareType : "=",
						type : "int",
						value : 1
					}]);
					grid.setDefaultmaskstring(" #0 and #1");
				}else{
					grid.setDefaultfilter([{
						myfilterfield : "fcustomerid",
						CompareType : "like",
						type : "string",
						value : Ext.getCmp('DJ.order.Deliver.custGenerateDeliversList').getSelectionModel().getSelection()[0].data.fcustomerid
					},{
						myfilterfield : "feffect",
						CompareType : "=",
						type : "int",
						value : 1
					},{
						myfilterfield : "fproductmatching",
						CompareType : "=",
						type : "string",
						value : Ext.getCmp('DJ.order.Deliver.custGenerateDeliversList').getSelectionModel().getSelection()[0].data.fmaktx
					}]);
					grid.setDefaultmaskstring(" #0 and #1 and #2");
				}
			},
			MyDataChanged : function(com) {
				if(com==null){
					return;
				}
				var rows = Ext
							.getCmp("DJ.order.Deliver.custGenerateDeliversList")
							.getSelectionModel().getSelection();
//					rows[0].set("fcusproductid", com[0].data.fid);
//					rows[0].set("cutpdtname",com[0].data.fname);

					Ext.Ajax.request({
						timeout : 600000,
						url : "SaveGenerateDelivers.do",
						params : {
							fid : rows[0].get("fid"),
							cusproduct :com[0].data.fid
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								Ext.getCmp("DJ.order.Deliver.custGenerateDeliversList").chooseModel=com[0];
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
						}
					});
			},		
//			listeners : {
//				change : function(com) {
//					var rows = Ext
//							.getCmp("DJ.order.Deliver.custGenerateDeliversList")
//							.getSelectionModel().getSelection();
//					rows[0].set("fcusproductid", com.getValue());
//					rows[0].set("cutpdtname", com.getRawValue());
//					Ext.Ajax.request({
//						timeout : 600000,
//						url : "SaveGenerateDelivers.do",
//						params : {
//							fid : rows[0].get("fid"),
//							cutpdtname : rows[0].get("cutpdtname"),
//							faddress : rows[0].get("faddress")
//						}, // 参数
//						success : function(response, option) {
//							var obj = Ext.decode(response.responseText);
//							if (obj.success == true) {
//								Ext.MessageBox.alert('成功', obj.msg);
//								Ext
//										.getCmp("DJ.order.Deliver.custGenerateDeliversList").store
//										.load();
//								rows[0].set("cutpdtname", com.getRawValue());
//							} else {
//								Ext.MessageBox.alert('错误', obj.msg);
//							}
//						}
//					});
//				}
//			},
			MyConfig : {
				width : 800,// 下拉界面
				height : 200,// 下拉界面
				url : 'GetCustproductList.do', // 下拉数据来源
				fields : [{
					name : 'fid'
				}, {
					name : 'fname',
					myfilterfield : 't_bd_Custproduct.fname', // 查找字段，发送到服务端
					myfiltername : '名称', // 在过滤下拉框中显示的名称
					myfilterable : true
						// 该字段是否查找字段
						}, {
							name : 'fnumber',
							myfilterfield : 't_bd_Custproduct.fnumber', // 查找字段，发送到服务端
							myfiltername : '编码', // 在过滤下拉框中显示的名称
							myfilterable : true
						// 该字段是否查找字段
						}, {
							name : 'fspec'
						}, {
							name : 'forderunit'
						}, {
							name : 'fcustomerid'
						}, {
							name : 'fdescription'
						}, {
							name : 'fcreatorid'
						}, {
							name : 'fcreatetime'
						}, {
							name : 'flastupdateuserid'
						}, {
							name : 'flastupdatetime'
						}

				],
				columns : [{
					'header' : 'fid',
					'dataIndex' : 'fid',
					hidden : true,
					hideable : false,
					autoHeight : true,
					autoWidth : true,
					sortable : true
				}, {
					'header' : '产品名称',
					'dataIndex' : 'fname',
					sortable : true,
					filter : {
						type : 'string'
					}
				}, {
					'header' : '编码',
					'dataIndex' : 'fnumber',
					sortable : true,
					filter : {
						type : 'string'
					}
				}, {
					'header' : '规格',
					width : 70,
					'dataIndex' : 'fspec',
					sortable : true
				}, {
					'header' : '单位',
					width : 70,
					'dataIndex' : 'forderunit',
					sortable : true
				}, {
					'header' : '客户',
					hidden : true,
					'dataIndex' : 'fcustomerid',
					sortable : true
				}, {
					'header' : '修改时间',
					'dataIndex' : 'flastupdatetime',
					filter : {
						type : 'datetime',
						date : {
							format : 'Y-m-d'
						},
						time : {
							format : 'H:i:s A',
							increment : 1
						}
					},
					width : 140,
					sortable : true
				}, {
					'header' : '创建时间',
					'dataIndex' : 'fcreatetime',
					filter : {
						type : 'datetime',
						date : {
							format : 'Y-m-d'
						},
						time : {
							format : 'H:i:s A',
							increment : 1
						}
					},
					width : 140,
					sortable : true
				}, {
					'header' : '描述',
					hidden : true,
					'dataIndex' : 'fdescription',
					sortable : true
				}]
			}
		},
		sortable : true
	}, {
		'header' : '配送地址',
		'dataIndex' : 'faddress',
		width : 200,
		editor : {
//			valueField : 'fname', // 组件隐藏值
			valueField:'fdetailaddress',
			id:'DJ.order.Deliver.custGenerateDeliversList.faddress',
//			cautoquery:false,
			name : "faddress",
			xtype : 'cCombobox',
			width : 260,
			labelWidth : 70,
			displayField : 'fname',// 组件显示值
			beforeExpand:function(){
				var grid = Ext.getCmp("DJ.order.Deliver.custGenerateDeliversList.faddress");
//				grid.setValue='';
				grid.setDefaultfilter([{
					myfilterfield : "cd.fcustomerid",
					type : "string",
					CompareType : "like",
					value : Ext.getCmp('DJ.order.Deliver.custGenerateDeliversList').getSelectionModel().getSelection().data[0].fcustomerid
				}]);
				grid.setDefaultmaskstring(" #0 ");
			},
			MyDataChanged : function(com) {
				if(com==null){
					return;
				}
				var rows = Ext
							.getCmp("DJ.order.Deliver.custGenerateDeliversList")
							.getSelectionModel().getSelection();
//					rows[0].set("faddressid", com[0].data.fid);
//					rows[0].set("faddress",com[0].data.fdetailaddress);

					Ext.Ajax.request({
						timeout : 600000,
						url : "SaveGenerateDelivers.do",
						params : {
							fid : rows[0].get("fid"),
							faddress : com[0].data.fid
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								Ext.getCmp("DJ.order.Deliver.custGenerateDeliversList").chooseModel=com[0];
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
						}
					});	
			},																				
			MyConfig : {
				width : 800,// 下拉界面
				height : 200,// 下拉界面
				url : 'getUserToCustAddress.do', // 下拉数据来源
				fields:[ {
							name : 'fid'
						}, {
							name : 'fname',
							myfilterfield : 'ad.fname',
							myfiltername : '名称',
							myfilterable : true
						}, {
							name : 'fnumber',
							myfilterfield : 'ad.fnumber',
							myfiltername : '地址编号',
							myfilterable : true
						}, {
							name : 'flinkman',
							myfilterfield : 'ad.flinkman',
							myfiltername : '联系人',
							myfilterable : true
						}, {
							name : 'fphone',
							myfilterfield : 'ad.fphone', 
							myfiltername : '联系电话',
							myfilterable : true
						}
				  ],
				columns:[ {
									'header' : '地址名称',
									'dataIndex' : 'fname',
									width : 405,
									sortable : true
								}, {
									'header' : '地址编号',
									'dataIndex' : 'fnumber',
									width : 80,
									sortable : true
								}, {
									'header' : '联系人',
									'dataIndex' : 'flinkman',
									width : 80,
									sortable : true
								}, {
									'header' : '联系电话',
									'dataIndex' : 'fphone',
									width : 150,
									sortable : true
								}
					]
			}
		},
		width : 200,
		sortable : true
	}, {
		'header' : '备注',
		'dataIndex' : 'fdescription',
		width : 120,
		sortable : true
	}, {
		'header' : '读取',
		dataIndex : 'fisread',
		width : 40,
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '是';
			} else {
				return '否';
			}
		}
	}],
	fvalue:'',
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	plugins : [ Ext.create('Ext.grid.plugin.CellEditing', {
        clicksToEdit: 2,
        listeners:{
        	beforeedit:function( editor, e, eOpts ){
        		e.grid.fvalue= e.value;
        	},
        	edit:function(editor, e){
//        		e.grid.getStore().load();
        		var record = e.record;
        		if(e.column.dataIndex=='cutpdtname'){
        			
        			if(e.grid.chooseModel!=null){
        				record.set('cutpdtname',e.grid.chooseModel.get('fname'));
        				e.grid.chooseModel=null;
        			}else{
        				record.set('cutpdtname',e.grid.fvalue);
        			}
        			
        			
        		}
        		if(e.column.dataIndex=='faddress'){
        			if(e.grid.chooseModel!=null){
        				record.set('faddress',e.grid.chooseModel.get('fname'));
        				e.grid.chooseModel=null;
        			}else{
        				record.set('faddress',e.grid.fvalue);
        			}
        		}
        		}
        	}
    })]

});
