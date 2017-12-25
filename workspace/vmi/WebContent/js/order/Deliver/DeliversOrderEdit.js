Ext.define('DJ.order.Deliver.DeliversOrderEdit', {
			extend : 'Ext.Window',
			id : 'DJ.order.Deliver.DeliversOrderEdit',
			modal : true,
			title : "订单出入库",
			width : 400,
			height : 150,
			resizable : false,
			layout : 'form',
			labelAlign : "left",
			defaults : {
				xtype : 'textfield',
				msgTarget : 'side'
			},
			closable : true,
			items : [{
	   			id : 'DJ.order.Deliver.DeliversOrderEdit.FWarehouseID',
	    	   		name:'FWarehouseID',
	        		fieldLabel : '仓库',
	        		valueField : 'fid', // 组件隐藏值
				xtype : 'cCombobox',
				labelWidth : 70,
				displayField : 'fname',// 组件显示值
				MyConfig : {
					width : 800,// 下拉界面
					height : 200,// 下拉界面
					url : 'GetWarehouses.do', // 下拉数据来源
					fields : [{
								name : 'fid'
							}, {
								name : 'fname',
								myfilterfield : 'w.fname', // 查找字段，发送到服务端
								myfiltername : '仓库名称',// 在过滤下拉框中显示的名称
								myfilterable : true
								// 该字段是否查找字段
							}, {
								name : 'fsimplename'
							}, {
								name : 'ufname'
							}, {
								name : 'fcontrollerid'
							}, {
								name : 'cfaddresid'
							}, {
								name : 'dfname'
							},{
								name:'fdescription'
							}, {
								name : 'foutstorage'
							}, {
								name : 'finstorage'
							}, {
								name : 'cffreightprice'
							}, {
								name : 'fwarehousetype'
							}, {
								name : 'flastupdatetime'
							}, {
								name : 'lfname'
							}, {
								name : 'fcreatetime'
							}, {
								name : 'cfname'
							}],
					columns : [Ext.create('DJ.Base.Grid.GridRowNum'),{
								'header' : 'fid',
								'dataIndex' : 'fid',
								hidden : true,
								hideable : false,
								sortable : true
							}, {
								'header' : '编码',
								'dataIndex' : 'fnumber',
								sortable : true
							}, {
							'header' : '名称',
							'dataIndex' : 'fname',
							sortable : true
							}, {
								'header' : '简称',
								'dataIndex' : 'fsimplename',
								sortable : true
							}, {
								'header' : '管理员',
								'dataIndex' : 'ufname',
								sortable : true
							}, {
								'header' : '送货地址',
								'dataIndex' : 'dfname',
								sortable : true
							}, {
								'header' : '描述',
								'dataIndex' : 'fdescription',
								sortable : true
							},{
								'header' : '修改人',
									dataIndex : 'lfname',
									sortable : true
								}, {
									'header' : '修改时间',
									dataIndex : 'flastupdatetime',
									width : 150,
									sortable : true
								}, {
									'header' : '创建人',
									dataIndex : 'cfname',
									sortable : true
								}, {
									'header' : '创建时间',
									dataIndex : 'fcreatetime',
									width : 150,
									sortable : true
								}, {
									'header' : '出库计件(元/m2)',
									dataIndex : 'foutstorage',
									sortable : true
								}, {
									'header' : '入库计件(元/m2)',
									dataIndex : 'finstorage',
									sortable : true
								}, {
									'header' : '仓库类型',
									dataIndex : 'fwarehousetype',
									sortable : true,
									 renderer: function(value){
									        if (value == 1) {
									            return '成品仓库';
									        }else if(value==2)
									        	{
									        	return '半成品';}
									        else{
									        	return '未分类';
									        }
									    }
								}, {
									'header' : '运费单价',
									dataIndex : 'cffreightprice',
									sortable : true,
									xtype: 'numbercolumn',
									format:'0.0000'
							}]
					}
	    	   		},{
	    	   			id : 'DJ.order.Deliver.DeliversOrderEdit.FWarehouseSiteID',
		    	   		name:'FWarehouseSiteID',
  	        		fieldLabel : '库位',
  	        		valueField : 'fid', // 组件隐藏值
					xtype : 'cCombobox',
					labelWidth : 70,
					displayField : 'fname',// 组件显示值
					beforeExpand : function() {
						var warehouseid = Ext.getCmp("DJ.order.Deliver.DeliversOrderEdit.FWarehouseID").getValue();//_combo.getValue();
        	    		Ext.getCmp("DJ.order.Deliver.DeliversOrderEdit.FWarehouseSiteID").setDefaultfilter([{
							myfilterfield : "s.fparentid",
							CompareType : "like",
							type : "string",
							value : warehouseid
						}]);
						Ext.getCmp("DJ.order.Deliver.DeliversOrderEdit.FWarehouseSiteID").setDefaultmaskstring(" #0 ");
					},
					MyConfig : {
						width : 800,// 下拉界面
						height : 200,// 下拉界面
						url : 'GetWarehousesiteList.do', // 下拉数据来源
						fields : [
						          		{
											name : 'fid'
										}, {
											name : 'fnumber'
										}, {
											name : 'fname',
											myfilterfield : 'w.fname',
											myfiltername : '库位名称',
											myfilterable : true
										}, {
											name : 'wfname'
										}, {
											name : 'fparentid'
										}, {
											name : 'faddress'
										},{
											name:'fremark'
										}, {
											name : 'finstoreprice'
										}, {
											name : 'foutstoreprice'
										}, {
											name : 'farea'
										}, {
											name : 'flastupdatetime'
										}, {
											name : 'lfname'
										}, {
											name : 'fcreatetime'
										}, {
											name : 'cfname'
										}
									],
						columns : [Ext.create('DJ.Base.Grid.GridRowNum'),
						           {
										'header' : 'fid',
										'dataIndex' : 'fid',
										hidden : true,
										hideable : false,
										sortable : true
									}, {
										'header' : '仓位编码',
										'dataIndex' : 'fnumber',
										sortable : true
									}, {
									'header' : '仓位名称',
									'dataIndex' : 'fname',
									sortable : true
									}, {
										'header' : '所属仓库',
										'dataIndex' : 'wfname',
										sortable : true
									}, {
										'header' : '容量(m2)',
										'dataIndex' : 'farea',
										sortable : true,
										xtype: 'numbercolumn',
										format:'0.0000'

									}, {
										'header' : '出库计件(元/m2)',
										dataIndex : 'foutstoreprice',
										sortable : true,
										xtype: 'numbercolumn',
										format:'0.0000'
									}, {
										'header' : '入库计件(元/m2)',
										dataIndex : 'finstoreprice',
										sortable : true,
										format:'0.0000',
										xtype: 'numbercolumn'
									}, {
										'header' : '地址',
										'dataIndex' : 'faddress',
										sortable : true
									}, {
										'header' : '备注',
										'dataIndex' : 'fremark',
										sortable : true
									},{
										'header' : '修改人',
											dataIndex : 'lfname',
											sortable : true
										}, {
											'header' : '修改时间',
											dataIndex : 'flastupdatetime',
											width : 150,
											sortable : true
										}, {
											'header' : '创建人',
											dataIndex : 'cfname',
											sortable : true
										}, {
											'header' : '创建时间',
											dataIndex : 'fcreatetime',
											width : 150,
											sortable : true
									}
								]
							}
		    	   		},{
		    	   			id : 'DJ.order.Deliver.DeliversOrderEdit.famount',
		    	   			fieldLabel : 'famount',
							hidden : true
		    	   		},{
		    	   			id : 'DJ.order.Deliver.DeliversOrderEdit.forderid',
		    	   			fieldLabel : 'forderid',
							hidden : true
		    	   		},{
		    	   			id : 'DJ.order.Deliver.DeliversOrderEdit.forderEntryid',
		    	   			fieldLabel : 'forderEntryid',
							hidden : true
		    	   		},{
		    	   			id : 'DJ.order.Deliver.DeliversOrderEdit.fdeliverid',
		    	   			fieldLabel : 'fdeliverid',
							hidden : true
		    	   		}],
			buttons : [{
						id : 'DJ.order.Deliver.DeliversOrderEdit.SaveButton',
						xtype : "button",
						text : "确定",
						pressed : false,
						handler : function() {

							if (!Ext.getCmp("DJ.order.Deliver.DeliversOrderEdit.FWarehouseID").isValid()
									|| !Ext.getCmp("DJ.order.Deliver.DeliversOrderEdit.FWarehouseID").isValid()) {
								Ext.MessageBox.alert('提示', '输入项格式不正确，请修改后再提交！');
								return;
							}
							if (!Ext.getCmp("DJ.order.Deliver.DeliversOrderEdit.FWarehouseSiteID").isValid()) {
								Ext.MessageBox.alert('提示', '描述格式不对或字输入的内容太长，请修改后再提交！');
								return;
							}
							var me=Ext.getCmp("DJ.order.Deliver.DeliversOrderEdit");
							var el = me.getEl();
							el.mask("系统处理中,请稍候……");

							Ext.Ajax.request({
								timeout: 600000,
								url : "deliversOut.do",
								params : {
									FWarehouseID : Ext.getCmp("DJ.order.Deliver.DeliversOrderEdit.FWarehouseID").getValue(),
									FWarehouseSiteID : Ext.getCmp("DJ.order.Deliver.DeliversOrderEdit.FWarehouseSiteID").getValue(),
									forderid : Ext.getCmp("DJ.order.Deliver.DeliversOrderEdit.forderid").getValue(),
									forderEntryid : Ext.getCmp("DJ.order.Deliver.DeliversOrderEdit.forderEntryid").getValue(),
									fdeliverid : Ext.getCmp("DJ.order.Deliver.DeliversOrderEdit.fdeliverid").getValue(),
									famount : Ext.getCmp("DJ.order.Deliver.DeliversOrderEdit.famount").getValue()
								}, // 参数
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										  djsuccessmsg( obj.msg);
										Ext.getCmp("DJ.order.Deliver.DeliversOrderList").store.load();
									} else {
										Ext.MessageBox.alert('错误', obj.msg);
									}
									el.unmask();
									var windows = Ext.getCmp("DJ.order.Deliver.DeliversOrderEdit");
									windows.close();
								}
							});
							
						}
					}
					, {
						id : 'DJ.order.Deliver.DeliversOrderEdit.ColseButton',
						xtype : "button",
						text : "取消",
						handler : function() {
							var windows = Ext.getCmp("DJ.order.Deliver.DeliversOrderEdit");
							if (windows != null) {
								windows.close();
							}
						}
					}
					],
			buttonAlign : "center",
			bodyStyle : "padding-top:15px ;padding-left:30px;padding-right:30px"
		});