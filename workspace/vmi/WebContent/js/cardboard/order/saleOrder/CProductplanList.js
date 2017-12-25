Ext.define('DJ.cardboard.order.saleOrder.CProductplanList', {
			extend : 'Ext.c.GridPanel',
			title : "纸板订单-制造商",
			id : 'DJ.cardboard.order.saleOrder.CProductplanList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetCProductPlanList.do',
			exporturl:"extportCProductPlanList.do",
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
				var me = this;
				var button = ['新  增','修  改','查   看','删    除'];//,'查    询'
				Ext.Array.forEach(button,function(item,index,allitems){
					me.down('[text='+item+']').hide();
				})
				
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
			custbar : [
			{
				text : '确认',
				height : 30,
				handler : //actionAudit(1)
					function() {
					var grid = Ext.getCmp("DJ.cardboard.order.saleOrder.CProductplanList");
					var record = grid.getSelectionModel().getSelection();
					if (record.length <= 0) {
						Ext.MessageBox.alert('提示', '请选中记录确认！');
						return;
					}
					var fid = "";
					for(var i=0;i<record.length;i++){
						if(record[i].get("faffirmed")==1){
							continue;
						}
						if(fid.length<=0){
							fid = record[i].get("fid");
						}else{
							fid = fid + ","+record[i].get("fid");
						}
					}
					if(fid == ''){
						Ext.Msg.alert('提示','请选择未确认的记录进行操作！');
						return;
					}
					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						url : "cardboardSupplierAffirm.do",
						params : {
							fidcls : fid
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								  djsuccessmsg( obj.msg);
								  grid.findCproductplan();//store.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
							el.unmask();
						}
					});
				}
			},{
				text : '取消确认',
				height : 30,
				handler : // actionAudit(1)
					function() {
					var grid = Ext.getCmp("DJ.cardboard.order.saleOrder.CProductplanList");
					var record = grid.getSelectionModel().getSelection();
					if (record.length <= 0) {
						Ext.MessageBox.alert('提示', '请选中记录取消确认！');
						return;
					}
					var fid = "";
					for(var i=0;i<record.length;i++){
						if(record[i].get("faffirmed")==0){
							continue;
						}
						if(fid.length<=0){
							fid = record[i].get("fid");
						}else{
							fid = fid + ","+record[i].get("fid");
						}
					}
						var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						url : "cardboardSupplierUnAffirm.do",
						params : {
							fidcls : fid
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								  djsuccessmsg( obj.msg);
								  grid.findCproductplan();//store.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
							el.unmask();
						}
					});
				}
			},{
				text : '入库',
				height : 30,
				handler : // actionAudit(1)
				function() {
					var grid = Ext.getCmp("DJ.cardboard.order.saleOrder.CProductplanList");
					var record = grid.getSelectionModel().getSelection();
					if (record.length != 1) {
						Ext.MessageBox.alert('提示', '请选中一条记录入库！');
						return;
					}
					if(record[0].get('fcloseed')==1){
						Ext.MessageBox.alert('提示', '已关闭不能入库！');
						return;
					}
					if (record[0].get("faffirmed") == 0
							|| record[0].get("faffirmed") == 'null') {
						Ext.MessageBox.alert('提示', '供应商未确认不能入库！');
						return;
					}
					var fid = record[0].get("fid");
					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						url : "GetproductplanToIn.do",
						params : {
							fid : fid
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								var datas = obj.data[0];
								var SaleOrderOIEdit = Ext
										.create('DJ.order.saleOrder.OISaleOrderEdit');
								var finqty = datas.famount - datas.fstockinqty;
								Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.famount").setValue(finqty);
								SaleOrderOIEdit.show();
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FProductID")
										.setValue(datas.fproductdefid);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.forderid")
										.setValue(datas.forderid);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.forderEntryid")
										.setValue(datas.fparentorderid);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fplanid")
										.setValue(fid);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FWarehouseID")
										.setmyvalue("\"fid\":\"" + datas.fwarehouseid
												+ "\",\"fname\":\"" + datas.wname
												+ "\"");
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FWarehouseID")
										.setReadOnly(true);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FWarehouseSiteID")
										.setReadOnly(true);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FWarehouseID")
										.setVisible(false);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FWarehouseSiteID")
										.setVisible(false);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FWarehouseSiteID")
										.setmyvalue("\"fid\":\""
												+ datas.fwarehousesiteid
												+ "\",\"fname\":\"" + datas.wsfname
												+ "\"");
								
								Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.ftype")
										.setValue("0");
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fordertype")
										.setValue(datas.fordertype);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fentryProductType")
										.setValue(datas.fentryProductType);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fassemble")
										.setValue(datas.fassemble);
								Ext
										.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fiscombinecrosssubs")
										.setValue(datas.fiscombinecrosssubs);
								
								SaleOrderOIEdit.down('button[text=确定]').setHandler(function(){


									if (!Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.famount").isValid()) {
										Ext.MessageBox.alert('提示', '输入项格式不正确，请修改后再提交！');
										return;
									}
									
									var urldo;
									if(Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.ftype").getValue()=="0"){
										urldo = "orderIn.do";
									}else{
										urldo = "orderOut.do";
									}
									var el = Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit").getEl();
									var  ftraitid = Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.ftraitid");
									if(ftraitid!=undefined){
										ftraitid = ftraitid.getValue();
									}else{
										ftraitid = '';
									}
									el.mask("系统处理中,请稍候……");
									Ext.Ajax.request({
										timeout: 600000,
										url : urldo,
										params : {
											FWarehouseID : Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FWarehouseID").getValue(),
											FWarehouseSiteID : Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FWarehouseSiteID").getValue(),
											fproductid : Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.FProductID").getValue(),
											forderid : Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.forderid").getValue(),
											forderEntryid : Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.forderEntryid").getValue(),
											famount : Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.famount").getValue(),
											fordertype : Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fordertype").getValue(),
											fentryProductType : Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fentryProductType").getValue(),
											fassemble : Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fassemble").getValue(),
											fiscombinecrosssubs : Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fiscombinecrosssubs").getValue(),
											fplanid : Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.fplanid").getValue(),
											ftraitid : ftraitid//Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit.ftraitid").getValue()
										}, // 参数
										success : function(response, option) {
											var obj = Ext.decode(response.responseText);
											if (obj.success == true) {
												  djsuccessmsg( obj.msg);
												  grid.findCproductplan();//.load();
												var windows = Ext.getCmp("DJ.order.saleOrder.OISaleOrderEdit");
												windows.close();
											} else {
												Ext.MessageBox.alert('错误', obj.msg);
											}
											el.unmask();
											
										}
									});
								
								})
								
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
							el.unmask();
						}
					});

				}

		/*	},{
				text : '导入接口',
				height : 30,
				handler : //AssageSupplier(1,fids)
				function() {
					var grid = Ext.getCmp("DJ.cardboard.order.saleOrder.CProductplanList");
					var record = grid.getSelectionModel().getSelection();
					var ids="";
					if(record.length<1){
						Ext.MessageBox.alert("信息","请选择至少一条订单分录导入！");
						return;
					}
					for ( var i = 0; i < record.length; i++) {
						ids +=  record[i].get("fid");
						if(record[i].get("sname")==null || record[i].get("sname")=='')
							{
							Ext.MessageBox.alert("信息","未分配制造商不能导入生产...或数据不同步请刷新！");
							return;
							}
						if(record[i].get("fimportEAS")==1)
						{
							Ext.MessageBox.alert("信息","订单不能重复导入生产...或数据不同步请刷新！");
							return;
						}
						if(record[i].get("fstate")==0)
						{
							Ext.MessageBox.alert("信息","订单未接收不能导入！");
							return;
						}
						if (i < record.length - 1) {
								ids = ids + ",";
							}
					}
					var el = grid.getEl();
							el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						timeout: 600000,
						url : "ImportEAS.do?",
						params : {
							fidcls:ids
						},
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								  djsuccessmsg( obj.msg);
								Ext.getCmp("DJ.cardboard.order.saleOrder.CProductplanList").store.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
							el.unmask();
						}
					});
					
				}*/
			},{
				xtype:'combobox',
				displayField: 'name',
				valueField: 'value',
				value:'d.fid,',
				editable:false,
				listeners:{
					select:function(me){
						me.up('grid').findCproductplan();
					}
				},
				store:Ext.create('Ext.data.Store',{
					fields: ['name', 'value'],
					data : [
					         {"name":"待确认", "value":"d.fstate,0"},
					         {"name":"已确认", "value":"d.fstate,1"},
					         {"name":"部分入库", "value":"d.fstate,2"},
					         {"name":"全部入库", "value":"d.fstate,3"},
//					         {"name":"未关闭", "value":"d.fcloseed,0"},
//					         {"name":"已关闭", "value":"d.fcloseed,1"},
					         {"name":"全部", "value":"d.fid,"}
					     ]
				})
			},{
				xtype:'textfield',
				emptyText :'回车搜索...',
				enableKeyEvents:true,
				listeners:{
					keypress:function(me,e,eOpts ){
						if(e.getKey()==13){
							me.up('grid').findCproductplan();
						}
					},
					render:function(me){
						Ext.tip.QuickTipManager.register({
						    target: me.id,
						    text: '可输入制造订单号、客户名称、制造商、纸箱规格、下料规格、成型方式、材料、订单来源'
						});
					}
				}
			},{
				
				xtype : 'mydatetimesearchbox',
				labelFtext : '创建时间',

				conditionDateField : 'd.fcreatetime',
				useDefaultfilter : true,
				// 默认过滤器之前的处理器
				beforeDefaultSearchAction : function(store) {

					return true;// false阻止执行
				}
				
			}],
			findCproductplan:function(){
				var combox = this.down('[xtype=combobox]').getValue();
				var text = this.down('[xtype=textfield]').getValue();
				var value = combox.split(',');
				var filter =[{
					myfilterfield : value[0],
					CompareType : "like",
					type : "string",
					value : value[1]
				},{
					myfilterfield : 'd.fnumber',
					CompareType : "like",
					type : "string",
					value : text
				},{
					myfilterfield : 'c.fname',
					CompareType : "like",
					type : "string",
					value : text
				},{
					myfilterfield : 's.fname',
					CompareType : "like",
					type : "string",
					value : text
				},{
					myfilterfield : "CONCAT_WS('x',da.fboxlength,da.fboxwidth,da.fboxheight)",
					CompareType : "like",
					type : "string",
					value : text.replace(/\*/g,'X')
				},{
					myfilterfield :"CONCAT_WS('x',da.fmateriallength,da.fmaterialwidth)",
					CompareType : "like",
					type : "string",
					value : text.replace(/\*/g,'X')
				},{
					myfilterfield : 'da.fseries',
					CompareType : "=",
					type : "string",
					value : text
				},{
					myfilterfield : 'f.fqueryname',
					CompareType : "like",
					type : "string",
					value : text
				},{
					myfilterfield : "CASE d.ftype  WHEN 1 THEN '客户订单' ELSE '平台订单' END",
					CompareType : "like",
					type : "string",
					value : text
				},{
					myfilterfield : "d.fcloseed",
					CompareType : "=",
					type : "int",
					value : 0
				}];
				var defaultmaskstring =" #0 and (#1 or #2 or #3 or #4 or #5 or #6 or #7 or #8) and #9";
				this.getStore().loadPage(1,{params:{defaultfilter:Ext.encode(filter),defaultmaskstring:defaultmaskstring}});
			},
			fields : [{
						name : 'fid'
					}, {
						name : 'fnumber',
						myfilterfield : 'd.fnumber',
						myfiltername : '制造订单号',
						myfilterable : true
					}, {
						name : 'cname',
						myfilterfield : 'c.fname',
						myfiltername : '客户名称',
						myfilterable : true
					}, {
						name : 'fname',
						myfilterfield : 'f.fname',
						myfiltername : '材料',
						myfilterable : true
					}, {
						name : 'ftype',
						myfilterfield : 'd.ftype',
						myfiltername : '订单来源(1客户订单、0平台订单)',
						myfilterable : true
					}, {
						name : 'sname'
					}, {
						name : 'fstate'
					}, {
						name : 'fboxmodel',
						myfilterfield : 'da.fboxmodel',
						myfiltername : '箱型(1普通、2全包、2半包、0其他)',
						myfilterable : true
					}, {
						name : 'fboxlength'
					}, {
						name : 'fboxheight'
					}, {
						name : 'fboxwidth'
					}, {
						name : 'fmateriallength'
					}, {
						name : 'fmaterialwidth'
					}, {
						name : 'fstavetype'
					}, {
						name : 'fvformula'
					},{
						name : 'fhformula'
					}, {
						name : 'fseries'
					},{
						name : 'famount'
					}, {
						name : 'famountpiece'
					}, {
						name : 'fstockinqty'
					}, {
						name : 'fstockoutqty'
					}, {
						name : 'farrivetime'
					}, {
						name : 'fimportEAS',
						myfilterfield : 'd.fimportEAS',
						myfiltername : '是否导入(1是、0否)',
						myfilterable : true
					},{
						name : 'fcreatetime'
					}, {
						name : 'fcreatorid'
					},{
						name: 'fdescription'
					}, {
						name : 'fdeliverapplyid'
					},'fcloseed','faffirmed','flastupdatetime','flastupdateuserid','faffirmtime','faffirmer'],
			columns : [Ext.create('DJ.Base.Grid.GridRowNum'),{
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true
					}, {
						'header' : '制造订单号',
						'dataIndex' : 'fnumber',
						sortable : true
					}, {
						'header' : '订单来源',
						'dataIndex' : 'ftype',
						sortable : true,
						 renderer: function(value){
						        if (value == 0) {
						            return '平台订单';
						        }else if(value==1){
						        	return '客户订单';}
					        	
						    }
					
					}, {
						'header' : '客户名称',
						'dataIndex' : 'cname',
						sortable : true
					},  {
						'header' : '订单状态',
						'dataIndex' : 'fstate',
						sortable : true,
						 renderer: function(value,items){
							 var val = '';
							 switch(value){
								 case '0' :val = '待确认';break;
								 case '1' :val = '已确认';break;
								 case '2' :val = '部分入库';break;
								 case '3' :val = '全部入库';break;
							 }
							 if(items.record.get('fcloseed')==1){
								 val = '已关闭';
							 }
							 return val;
						    }
					}, {
						'header' : '材料',
						'dataIndex' : 'fname',
						width:130,
						sortable : true
					}, {
						'header' : '箱型',
						width:50,
						'dataIndex' : 'fboxmodel',
						sortable : true,
						renderer : function(value) {
							if (value == 1) {
								return '普通';
							}else if(value == 2){
								return '全包';
							}else if(value == 3){
								return '半包';
						    }else {
								return '其他';
							}
						}
					}, {
						'header' : '纸板规格(CM)',
						draggable:false,
						columns:[{
							header : '长',
							draggable:false,
							align : 'center',
							'dataIndex' : 'fboxlength'
						},{
							header : '宽',
							draggable:false,
							align : 'center',
							'dataIndex' : 'fboxwidth'
						},{
							header : '高',
							draggable:false,
							align : 'center',
							'dataIndex' : 'fboxheight'
						}]
					}, {
						'header' : '下料规格(CM)',
						draggable:false,
						columns:[{
							header : '总长',
							draggable:false,
							align : 'center',
							'dataIndex' : 'fmateriallength'
						},{
							header : '总高',
							draggable:false,
							align : 'center',
							'dataIndex' : 'fmaterialwidth'
						}]
					}, {
						'header' : '压线方式',
						'dataIndex' : 'fstavetype',
						sortable : true
					},{
						'header' : '压线公式',
						draggable:false,
						columns:[{
							header : '纵向公式',
							draggable:false,
							align : 'center',
							'dataIndex' : 'fvformula'
						},{
							header : '横向公式',
							draggable:false,
							align : 'center',
							'dataIndex' : 'fhformula'
						}]
					}, {
						'header' : '成型方式',
						'dataIndex' : 'fseries',
						hidden : false,
						hideable : false,
						sortable : true
					},
						{
						'header' : '配送数量',
						draggable:false,
						columns:[{
							header : '只',
							draggable:false,
							align : 'center',
							'dataIndex' : 'famount'
						},{
							header : '片',
							draggable:false,
							align : 'center',
							'dataIndex' : 'famountpiece'
						}]
					}, {
						'header' : '入库数量/片',
						'dataIndex' : 'fstockinqty',
						sortable : true
					}, {
						'header' : '出库数量/片',
						'dataIndex' : 'fstockoutqty',
						sortable : true
					},{
						header: '特殊要求',
						dataIndex: 'fdescription',
						sortable: true
					}, {
							text : '配送时间',
							dataIndex : 'farrivetime',
							sortable : true,
							renderer:function(value){
								return value.substring(0,16);
							}
						}, {
							'header' : '制造商',
							'dataIndex' : 'sname',
							sortable : true
						}, {
							text : '创建时间',
							dataIndex : 'fcreatetime',
							width : 150,
							sortable : true,
							renderer:function(value){
								return value.substring(0,16);
							}
						},{
							text : '确认人',
							dataIndex : 'faffirmer',
							sortable : true
						},{
							text : '确认时间',
							dataIndex : 'faffirmtime',
							sortable : true,
							renderer:function(value){
								return value.substring(0,16);
							}
						}, {
							text : '导入接口',
							dataIndex : 'fimportEAS',
							sortable : true,
							renderer:function(val){
								return val=='1'?'是':'否'
							}
						}],
				
					selModel:Ext.create('Ext.selection.CheckboxModel')
		})

		
		
