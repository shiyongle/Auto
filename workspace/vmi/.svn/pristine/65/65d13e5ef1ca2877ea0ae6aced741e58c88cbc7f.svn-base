Ext.require(['DJ.tools.fieldRel.MyFieldRelTools', 'Ext.ux.form.MySearchBox',
		'Ext.ux.form.MyDateTimeSearchBox',
		'DJ.myComponent.button.UploadAccessoryButton',
		'Ext.ux.grid.MySimpleGridContextMenu','Ext.ux.grid.MyGridItemDblClick',
		'DJ.tools.common.MyCommonToolsZ']);

Ext.ns("DJ.System.product");

Ext.define('DJ.System.product.CustproductCustTreeList.loadupFormpanel', {
	extend : 'Ext.Window',
	id : 'DJ.System.product.CustproductCustTreeList.loadupFormpanel',
	modal : true,
	title : "Excel文件上传",
	width : 450,// 230, //Window宽度
	height : 150,// 137, //Window高度
	resizable : false,
	closable : true, // 关闭按钮，默认为true
	// renderTo: 'upload',
	items : [{
		xtype : 'form',
		id : 'DJ.System.product.CustproductCustTreeList.loadupFormpanel.form',
		baseCls : 'x-plain',
		fieldDefaults : {
			labelWidth : 400
		},
		// items:[
		// {
		// title: '上传图片',
		width : 400,
		bodyPadding : 10,
		margin : '20 10 20 20',
		frame : true,

		items : [
			{
				id : 'DJ.System.product.CustproductCustTreeList.loadupFormpanel.form.fcustomerid',
				name:'fcustomerid',
				labelWidth : 50,
				msgTarget : 'side',
				allowBlank : false,
				anchor : '83%',
        		fieldLabel : '客户',
        		xtype:'cCombobox',
        		displayField:'fname', // 这个是设置下拉框中显示的值
        	    valueField:'fid', // 这个可以传到后台的值
        	    blankText:'请选择客户',
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
						text : '客户编号',
						dataIndex : 'fnumber',
						sortable : true
					}, {
						text : '客户名称',
						dataIndex : 'fname',
						sortable : true
					}, {
						text : '地址',
						dataIndex : 'faddress',
						sortable : true,
						width : 250
					}]
 				}
			},
			{
			xtype : 'filefield',
			name : 'fileName',
			fieldLabel : '上传',
			labelWidth : 50,
			msgTarget : 'side',
			allowBlank : false,
			anchor : '100%',
			regex : /(.)+((\.xlsx)|(\.xls)(\w)?)$/i,
			regexText : "文件格式选择不正确",
			buttonText : '选择文件'
		}],
		buttons : [{
			text : '上传',
			handler : function() {
				var form = this.up('form').getForm();
				var fcustomerid = form.findField('fcustomerid').getValue();
				if (form.isValid()) {
					form.submit({
//						url : 'uploadFile.do?action='+encodeURIComponent(fcustomerid),  custGenerateDeliversList
						url : 'saveUploadCustExcelData.do?action='+encodeURIComponent(fcustomerid),  
//						url : '',
//						params : fcustomerid, // 参数
//						timeout : 600000,
						// method:'POST',
						// type:'ajax',
						waitMsg : '正在保存文件...',
						success : function(fp, o) {
							// Ext.Msg.alert('提示信息',
							// '文件成功上传,文件名字为：'+o.result.file);
							Ext.Msg.show({
								title : '提示信息',
								msg : o.result.msg,
								minWidth : 200,
								modal : true,
								buttons : Ext.Msg.OK
							})
							form.findField('fileName').setRawValue('');
							Ext.getCmp("DJ.System.product.CustproductCustTreeList.loadupFormpanel").close();
							Ext.getCmp("DJ.System.product.CustproductCustTreeList").store.load();
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
	// }]
	}]
});

DJ.System.product.CustproductCustTreeListStoreOperateFields = function() {

	var fields = [{
		name : "id"
	}, {
		name : "fname"
	}, {
		name : "fspec"
	}, {
		name : "fnumber"
	}, {
		name : "leaf",
		convert : function(v, record) {
			return v == "1";
		}
	}, {
		name : "parentId"
	}, {
		name : "fcharactername"
	}, {
		name : "fmaterial"
	}, {
		name : "ftilemodel"
	}, {
		name : "forderunit"
	}, {
		name : "fdescription"
	}, {
		name : "fcreatetime"
	}, {
		name : "flastupdatetime"
	}];

	Ext.each(fields, function(ele, index, all) {

		Ext.applyIf(ele, {

			convert : MyFieldRelTools.convertToEmptyWhenNull
		})

	});

	return fields;

}

DJ.System.product.CustproductCustTreeListStore = Ext.create(
		"Ext.data.TreeStore", {
			autoLoad : false,
			nodeParam : "id",
			defaultRootId : "-1",
			root : {
				fname : "目录",
				id : "-1",
				expanded : true
			},
			fields : DJ.System.product
					.CustproductCustTreeListStoreOperateFields(),
			proxy : {
				type : "ajax",
				api : {
					read : "selectCustproductsInTreeWay.do",
					create : "updateAndCreateProductStructTree.do",
					destroy : "DelCustproductList.do",
					update : "updateAndCreateProductStructTree.do"
				},
				reader : {
					messageProperty : "Msg"
				},
				writer : {
					type : "json",
					encode : true,
					root : "data",
					allowSingle : false
				}
			}

			,
			listeners : {

				beforeload : function(store, operation, eOpts) {

					if (operation.params.pageNo == undefined) {

						if (Ext
								.getCmp("DJ.System.product.CustproductCustTreeList")) {
							Ext
									.getCmp("DJ.System.product.CustproductCustTreeList").pageNo = 0;

							Ext
									.getCmp("DJ.System.product.CustproductCustTreeList.pagingtoolbar").items.items[12]
									.setText(DJ.System.product.CustproductCustTreeList
											.buildPageText(Ext
													.getCmp("DJ.System.product.CustproductCustTreeList").pageNo
													+ 1));

						}

					}

					if (DJ.System.product.CustproductCustTreeListLoadMask) {
						DJ.System.product.CustproductCustTreeListLoadMask
								.show();
					}

					var pageingT = Ext
							.getCmp("DJ.System.product.CustproductCustTreeList.pagingtoolbar");

					if (operation.params.condictionValue == undefined
							&& operation.params.conditionDateField == undefined) {

						if (pageingT) {

							pageingT.items.items[1].enable();
							pageingT.items.items[7].enable();

						}

						// 模糊搜索
					} else {

						// if (operation.params.condictionValue) {
						// //不进行子节点查询
						// store.getProxy().setExtraParam("id", -2);
						// }

						// 前面的客户树不进行选择
						var custproductTree = Ext
								.getCmp("DJ.System.product.CustproductTree");

						if (custproductTree) {

							custproductTree.getSelectionModel().deselectAll();

						}

						if (pageingT) {

							pageingT.items.items[1].disable();
							pageingT.items.items[7].disable();

						}

					}

				},
				load : function(com, node, records, successful, eOpts) {

					if (DJ.System.product.CustproductCustTreeListLoadMask) {
						DJ.System.product.CustproductCustTreeListLoadMask
								.hide();
					}

					if (com.lastOptions.params.condictionValue) {

						Ext.each(records, function(ele, index, all) {

							if (!ele.data.leaf) {

								ele.expand();
							}
						});

					}

				}

			}

		}

);

Ext.ClassManager.setAlias(Ext.selection.CheckboxModel,
		"selection.checkboxmodel");

Ext.define("DJ.System.product.CustproductCustTreeList", {

	pageNo : 0,

	customerId : -1,

	extend : "Ext.tree.Panel",
	id : "DJ.System.product.CustproductCustTreeList",

	alias : "widget.custproductcusttreelist",

	title : '客户产品管理',

	statics : {

		buildPageText : function(page) {

			var aT = ['第', page, '页'];

			return aT.join('');
		}

	},

	selModel : {
		selType : 'checkboxmodel',
		simpleSelect : true,
		multiSelect : true
	},

	rootVisible : false,
	useArrows : true,
	autoScroll : true,
	border : false,
	store : DJ.System.product.CustproductCustTreeListStore,
	containerScroll : true,

	_updateAndView : function(com, model) {

		var rs = com.up("treepanel").getSelectionModel().getSelection();

		if (rs.length == 1) {

			if (model == 'edit') {

				var t = rs[0].get("fcharactername");

				if (t != null && Ext.String.trim(t) != "" && t != 'null') {

					Ext.Msg.alert("提示", "有特性的产品不能编辑");

					return;

				}

			}

			var editui = Ext.create('DJ.System.product.CustproductEdit');
			editui.seteditstate(model);
			editui.setparent('DJ.System.product.CustproductCustTreeList');
			editui.loadfields(rs[0].get("id"));
			if(!Ext.isIE){
				editui.getform().down("djmultiuploadPanel").setVisible( false );
			}
			editui.show();

		} else {

			Ext.Msg.alert("提示", "只能选择一条");

		}
	},

	dockedItems : [{
		xtype : 'toolbar',
		dock : 'top',
		enableOverflow : true,
		items : [{
			text : "增加",
			handler : function() {

				var addAtion = function(obj) {

					var editui = Ext
							.create('DJ.System.product.CustproductEdit');
					editui.seteditstate("add");
					editui
							.setparent('DJ.System.product.CustproductCustTreeList');

					// 新增界面弹出后事件
					var editform = editui.getform().getForm();
					var trees = Ext.getCmp("DJ.System.product.CustproductTree");
					var record = trees.getSelectionModel().getSelection();
					if (record.length > 0) {
						if (record[0].data.id != -1) {
							editform.findField('fcustomerid')
									.setmyvalue("\"fid\":\""
											+ record[0].data.id
											+ "\",\"fname\":\""
											+ record[0].data.text + "\"");
						}
					}

					editform.findField('isCreate').setValue(1);

					editform.findField('fid').setValue(obj.data[0].id);

					if(!Ext.isIE){
						editui.setHeight(509); 
						editui.getform().down("djmultiuploadPanel").url ="uploadCustProductImg.do?fid="+obj.data[0].id;
					}
					
					editui.show();

				};
				
				Ext.Ajax.request({
					timeout : 6000,

					url : "gainUUID.do",
					success : function(response, option) {

						var obj = Ext.decode(response.responseText);
						if (obj.success == true) {
							
							addAtion(obj);

						} else {
							Ext.MessageBox.alert('错误', obj.msg);

						}

					}
				});
				
			}
		}, {
			text : "修改",
			handler : function() {

				Ext.getCmp("DJ.System.product.CustproductCustTreeList")._updateAndView(this, "edit");

			}
		},
				// {
				// text : "删除",
				// tooltip : "试试Delete键",
				// handler : function() {
				//
				// var tree = this.up("treepanel");
				// var rs = tree.getSelectionModel().getSelection();
				// if (rs.length > 0) {
				//
				// Ext.each(rs, function(item, index, all) {
				// var rsT = item;
				// if (rsT.data.root) {
				// Ext.Msg.alert("删除节点", "根节点不允许删除！");
				// return;
				// }
				// if (rsT.isExpandable() || rsT.hasChildNodes()) {
				// Ext.Msg.alert("删除节点", "请先删除所有子节点，再删除该节点！");
				// return;
				// } else {
				// rsT.remove();
				//
				// }
				// }
				//
				// );
				//
				// }
				//
				// tree.getStore().sync({
				// success : function(opt) {
				// tree.getStore().load();
				//
				// },
				// failure : function(opt) {
				// var obj = tree.getStore().getProxy().getReader().rawData;
				// Ext.Msg.alert("发生错误", obj.msg);
				// tree.getStore().load();
				// }
				// });
				// }
				// },

				{
					text : "查看",
					handler : function() {

//						Ext.getCmp("DJ.System.product.CustproductCustTreeList")._updateAndView(this, "view");
						
						this.up("treepanel")._updateAndView(this, "view");
					}
				}, "|", {

					text : "刷新",
					handler : function(com) {

						com = com.up("treepanel");

						this.up("treepanel").pageNo = 0;
						this.up("treepanel").getStore().load({

							params : {
								fcustomerid : com.customerId

							}
						});
					}

				},Ext.create("DJ.myComponent.button.ExportExcelButton", {
				
					exportExcelUrl : "makeCustproducttoExcel.do"
					
				}), "|",{
					xtype : 'textfield',
					itemId : 'textfield',
					width:120,
					id : 'findByText',
					emptyText : '回车搜索',
					enableKeyEvents:true,
					
					
					
					listeners : {
						
						afterrender:function( com, eOpts ) {
							
							MyCommonToolsZ.setQuickTip(com.id,"" ,"可输入:包装物名称、包装物编号" );
							
						},
						
						keyup:function(me,e){
							if(e.getKey()==13){
								Ext.getCmp('DJ.System.product.CustproductCustTreeList').filterByName();

							}
						}
					}
								
				},"|",{
					text:'产品过滤',
					handler:function(){
						var edit = Ext.create('DJ.System.product.UserRelationCust');
						var txtname=Ext.getCmp('DJ.System.product.UserRelationCustTxtName');
						txtname.setVisible(false);
						edit.show();
					}
				},{
					xtype : "uploadaccessorybutton",
					fileUrl : "uploadCustProductImg.do",
					gridId : "DJ.System.product.CustproductCustTreeList",
					fileGridId : 'DJ.System.product.CustproductCustAccessoryList',
					filedName : "id"
				},{
					// id : 'DelButton',
					text : 'Excel文件上传',
					height : 30,
					handler : function() {
						var loadupFormpanel = Ext.getCmp("DJ.System.product.CustproductCustTreeList.loadupFormpanel");
						if (loadupFormpanel == null) {
							loadupFormpanel = Ext.create("DJ.System.product.CustproductCustTreeList.loadupFormpanel");
						}
						var grid = Ext.getCmp("DJ.System.product.CustproductCustTreeList");
						var el = grid.getEl();
						el.mask("系统处理中,请稍候……");
						Ext.Ajax.request({
							timeout : 600000,
							url : "GetCustomerListByUserId.do",
//							params : {
//								fids : ids
//							}, // 参数
							success : function(response, option) {
								var obj = Ext.decode(response.responseText);
								if (obj.success == true) {
									if(obj.data.length==1){
										var cform = Ext
											.getCmp("DJ.System.product.CustproductCustTreeList.loadupFormpanel.form")
											.getForm();
									cform.findField('fcustomerid').setmyvalue("\"fid\":\""
											+ obj.data[0].fid + "\",\"fname\":\""
											+ obj.data[0].fname + "\"");
									}
								} else {
								}
								el.unmask();
							}
						});
						loadupFormpanel.show();
					}
				},{
					text:'客户产品模板下载',
					handler : function() {
						window.open('downloadCustExcel.do','下载模板')
						}
				}, {

					xtype : "mydatetimesearchbox",
					conditionDateField : "fcreatetime",
					labelFtext : "创建时间",
					storeG : DJ.System.product.CustproductCustTreeListStore

				}
		]
	}, {
		id : "DJ.System.product.CustproductCustTreeList.pagingtoolbar",
		xtype : 'pagingtoolbar',
		dock : 'bottom',
		width : 360,
		displayInfo : true
	}

	],
	plugins : [{
	
		ptype : 'mysimplegridcontextmenu',
		useExistingButtons : ["button[text=上传附件]","button[text=修改]"]
		
	
	},{
	
		ptype : "mygriditemdblclick",
		dbClickHandler : "button[text=查看]"
		
	}],
	viewConfig : {
		toggleOnDblClick : false,
		listeners : {
			refresh : function() {
				// this.select(0);
				// this.focus(false);
			}
		}
	},
	columns : {
		items : [Ext.create('DJ.Base.Grid.GridRowNum'), {
			xtype : "treecolumn",
			dataIndex : "fname",
			text : "包装物名称",
			width : 200

		}, {

			dataIndex : "fnumber",
			text : "包装物编号",
			width : 150

		}, {

			dataIndex : "fcharactername",
			text : "特性",
			width : 70

		}, {

			dataIndex : "fspec",
			text : "规格",
			width : 70

		}, {

			dataIndex : "fmaterial",
			text : "材料"

		}, {

			dataIndex : "ftilemodel",
			text : "楞型"

		}, {

			dataIndex : "forderunit",
			text : "单位"

		}, {

			dataIndex : "fcreatetime",
			text : "创建时间"

		}, {

			dataIndex : "flastupdatetime",
			text : "修改时间"

		}

		],

		defaults : {
			xtype : "gridcolumn",
			renderer : MyFieldRelTools.showEmptyWhenNull
		}
	},
	actionT : function(com, add) {

		com = com.up("treepanel");

		if (add) {
			com.pageNo++;

		} else {
			com.pageNo--;

			if (com.pageNo < 0) {
				com.pageNo = 0;
			}
		}

		com.getStore().load({
			params : {
				fcustomerid : com.customerId,
				id : -1,
				pageNo : com.pageNo
			},
			callback : function(records, operation, success) {

				if (success) {

					Ext
							.getCmp("DJ.System.product.CustproductCustTreeList.pagingtoolbar").items.items[12]
							.setText(DJ.System.product.CustproductCustTreeList
									.buildPageText(com.pageNo + 1));

				}

			}
		});

	},

	listeners : {

		afterrender : function(com, eOpts) {
			if (!DJ.System.product.CustproductCustTreeListLoadMask) {
				DJ.System.product.CustproductCustTreeListLoadMask = new Ext.LoadMask(
						Ext.getCmp("DJ.System.product.CustproductCustTreeList"),
						{

							msg : '加载中……'

						});
			}

			var paging = Ext
					.getCmp("DJ.System.product.CustproductCustTreeList.pagingtoolbar");

			Ext.each(paging.items.items, function(ele, index, all) {

				if (index == 1) {
					ele.enable();
					ele.on('click', function(com) {

						com.up("treepanel").actionT(com, false);

					});

				} else if (index == 7) {
					ele.enable();
					ele.on('click', function(com) {

						com.up("treepanel").actionT(com, true);

					});

				} else if (index == 12) {
					ele.setText(DJ.System.product.CustproductCustTreeList
							.buildPageText(com.pageNo + 1));
				} else {
					ele.hide();
				}

			});
			this.magnifier = Ext.create('Ext.ux.form.Magnifier');
		},
		itemclick : function(com, record, item, index, e, eOpts) {
			var grid = Ext.getCmp("DJ.System.product.CustproductCustAccessoryList"),
				store = grid.getStore(),
				me = this;
			store.loadPage(1, {
						params : {

							fcusproductid : record.get("id")
						}
			});
			me.magnifier.loadImages({
				fid: record.get('id')
			});
			var coord = e.getXY();
			me.magnifier.showAt({
    			left: coord[0] + 80,
    			top: coord[1] + 5
    		});
		}
		
	}, 
	  filterByName : function(){
		  var store = DJ.System.product.CustproductCustTreeListStore;//Ext.getCmp('DJ.System.product.CustproductCustTreeList').getStore();
		  if(Ext.getCmp('DJ.System.product.CustproductTree').getSelectionModel().lastFocused!=null){
		  	var eastText = Ext.getCmp('DJ.System.product.CustproductTree').getSelectionModel().lastFocused.data.text;
		  }else{
			  var eastText = "";
		  }
		  
		  var textF = Ext.getCmp('findByText').getValue();
			store.load({params:{value:textF,'cname':eastText}});
		  
		
		  
	  }
});