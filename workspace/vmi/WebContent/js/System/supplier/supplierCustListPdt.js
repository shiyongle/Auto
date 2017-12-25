Ext.define("Ext.c.GridPanelT", {
	extend : "Ext.grid.Panel",

	xtype : "cGridPanelT",
	initComponent : function() {
		var me = this;
		if (me.id == "") {
			me.id = Ext.id();
			
			
		}
		me.store = Ext.create("Ext.c.data.Store", {
			fields : me.fields,
			pageSize : me.pageSize,
			url : me.url,
			autoLoad : false,
			remoteSort : me.remoteSort ? me.remoteSort : false,
			listeners : {
				exception : function(dataProxy, response, action, options) {
					var o = Ext.decode(response.responseText)
					if (!o.success) {
						Ext.Msg.alert('错误提示', o.msg);
					}
				}
			}
				// 设置属性进行请求后台排序
				});

		me.dockedItems.push({
			xtype : "pagingtoolbar",
			store : me.store, 
			dock : "top",
			displayInfo : true,
			afterPageText : '/{0}',
//			style : {
//				background : 'white'
//			},
			listeners : {

				afterrender : function(com, eOpts) {

					var paging = com;

					Ext.each(paging.items.items, function(ele, index, all) {

						if (index == 7 || index == 4 || index == 15
								|| index == 14 || index == 13 || index == 12
								|| index == 10) {
						} else if (index != 5 && index !=1) {
							ele.hide();
						}

					});
		        	com.add(0,
							{
								xtype : 'tbspacer',
								flex : 1
							});
				}
			}
				});

		this.callParent(arguments);
	}
});

Ext.define('DJ.System.supplier.SupOfCustPdtList', {
	extend : 'Ext.c.GridPanelT',
	id : 'DJ.System.supplier.SupOfCustPdtList',
	closable : true,
	alias : 'widget.supofcustpdtlist',
	pageSize : 15,
	emptyText : '<h1><center>请先选择客户，再操作</center></h1>',
	mixins : ['DJ.tools.grid.MySimpleGridMixer',
			'DJ.tools.grid.mixer.MyGridSearchMixer'],

	url : 'GetQuickProductforSupplierList.do',	

	style : {
		gridRowCellFontSize : '30pt'

	},

	//hideHeaders : true,

	closable : false,// 是否现实关闭按钮,默认为false

	showSearchAllBtn : false,

	doBeforeGridSearchAction : function(filters, maskA, nextIndex) {

		var me = this;

		var suplierID = me.down('combobox[name=fcustomerid]').getValue();

		filters.push({

			myfilterfield : "c.fcustomerid",
			CompareType : "=",
			type : "string",
			value : suplierID

		});


		maskA.push(Ext.String.format("and (#{0})",nextIndex));

	},

	magnifier: Ext.create('Ext.ux.form.Magnifier'),

	statics : {
		
		editImg : function() {

			var gridMain = Ext.getCmp("DJ.System.supplier.SupOfCustPdtList");

			var record = gridMain.getSelectionModel().getSelection();

			if (record.length != 1) {

				Ext.Msg.alert("提示", "只能选择一条进行操作");

				return;

			}

			var grid = Ext.create("DJ.System.supplier.SupOfPdtImgShower"), store = grid
					.getStore(), me = this;
			var fid = record[0].get("fid");
			store.loadPage(1, {
				params : {

					fid : fid
				}
			});

			var widthT = 500;

			Ext.create('Ext.window.Window', {
				// title: '',
				modal : true,
				resizable : false,
				height : widthT / 16 * 9,
				width : widthT,
				layout : 'fit',
				items : [grid],

				listeners : {

					close : function(panel, eOpts) {

//						gridMain.getStore().load();
						document.getElementById('SupOfCustPdtImg_'+fid).src = 'getFileSourceByParentId.do?fid='+fid+'&_dc='+new Date().getTime();

					}
				}

			}).show();

		}

	},
	arriveTimeSetWin: function(){
		Ext.create('Ext.Window',{
			title: '交期设置',
			width: 400,
			layout: 'absolute',
			listeners: {
				show: function(){
					var win = this;
					Ext.Ajax.request({
						url: 'getSupplierDeliverTimeConfig.do',
						success: function(res){
							var obj = Ext.decode(res.responseText);
							if(obj.success){
								win.fsupplierid = obj.fsupplierid;
								if(obj.fid){
									win.fid = obj.fid;
									win.down('textfield[name=fdays]').setValue(obj.fdays);
									win.down('textfield[name=fdefaultdays]').setValue(obj.fdefaultdays);
								}
							}else{
								win.close();
								Ext.Msg.alert('提示',obj.msg);
							}
						}
					})
				}
			},
			items: [{
				x: 80,
				y: 20,
				xtype: 'numberfield',
				hideTrigger: true,
				name: 'fdays',
				minValue: 0,
				value: 0,
				width: 30,
				fieldStyle: 'text-align:center;font-weight:bold;'
			},{
				x: 120,
				y: 23,
				xtype: 'label',
				text: '日内可发货（最早发货交期）'
			},{
				x: 80,
				y: 60,
				xtype: 'numberfield',
				hideTrigger: true,
				name: 'fdefaultdays',
				minValue: 0,
				value: 5,
				width: 30,
				fieldStyle: 'text-align:center;font-weight:bold;'
			},{
				x: 120,
				y: 63,
				xtype: 'label',
				text: '日内可预计发货（默认交期）'
			},{
				x: 160,
				y: 120,
				xtype: 'label',
				text: '注：客户交期的范围根据此设置进行选择。',
				style: 'margin-bottom:10px;color:#444;'
			}],
			bbar: ['->',{
				text: '保存',
				height: 30,
				handler: function(){
					var win = this.up('window'),
						daysField = win.down('textfield[name=fdays]'),
						defaultdaysField = win.down('textfield[name=fdefaultdays]'),
						data = {};
					data.fid = win.fid || '';
					data.fsupplierid = win.fsupplierid;
					if(!daysField.validate() || !defaultdaysField.validate()){
						return;
					}
					data.fdays = daysField.getValue();
					data.fdefaultdays = defaultdaysField.getValue();
					if(parseInt(data.fdays)>parseInt(data.fdefaultdays)){
						Ext.Msg.alert('提示','"最早发货交期"不能大于"默认交期",请更改！');
						return;
					}
					Ext.Ajax.request({
						url: 'saveSupplierDeliverTimeConfig.do',
						params: {
							SupplierDeliverTime: Ext.encode(data)
						},
						success: function(res){
							var obj = Ext.decode(res.responseText);
							if(obj.success){
								djsuccessmsg(obj.msg);
							}else{
								Ext.Msg.alert('提示',obj.msg);
							}
						}
					});
					win.close();
				}
			},{
				text: '关闭',
				height: 30,
				margin: '0 5 0 10',
				handler: function(){
					this.up('window').close();
				}
			}]
		}).show();
	},
	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			dockedItems : [{
				xtype : 'toolbar',
				dock : 'top',
				style : {
					background : 'white'

				},
				border : 0,
				items : [{
					height : 30,
					text : "新  增",
					iconCls:'addnew',
					handler:function(){
						this.up().down('button[text=产品管理] menuitem[text=增加]').handler();
					}
				},{
					height : 30,
					text : "修  改",
					iconCls : "edit",
					handler:function(){
						this.up().down('button[text=产品管理] menuitem[text=修改]').handler();
					}
				},{
					height : 30,
					text : "查   看",
					iconCls : "view",
					handler:function(){
						this.up().down('button[text=产品管理] menuitem[text=修改]').handler();
						var winAdd = Ext.getCmp('DJ.System.supplier.SupOfCustProductEdit');
						if(winAdd){
							winAdd.down('toolbar[dock=top] button[text*=保]').hide();
							Ext.Array.each(winAdd.down('form').query('textfield'),function(item) {
										item.setReadOnly(true)
									});
						}
					}
				},{
					text : "删    除",
					iconCls : "delete",
					height : 30,
					handler:function(){
						var me = this;
						var record = me.up('grid').getSelectionModel().getSelection();
						if(record.length==0){
							Ext.Msg.alert('提示','请选择数据！');
							return;
						}
						Ext.Msg.show({
						     title:'提示',
						     msg: '是否确认删除选中的内容？',
						     buttons: Ext.Msg.YESNO,
						     icon: Ext.Msg.QUESTION,
						     fn:function(btn){
						     	if(btn=='yes'){
									me.up().down('button[text=产品管理] menuitem[text=删除]').handler();
						     	}
						     }
						});
					}
				},{
					text:'导入产品',
					height:30,
					handler:function(){
						this.up().down('button[text=产品管理] menuitem[text=导入产品]').handler();
					}
				},{
					text: '交期设置',
					height: 30,
					handler: function(){
						me.arriveTimeSetWin();
					}
				},{
					xtype : 'button',
					text : '产品管理',
					hidden:true,
					width : 100,
					menu : {
						xtype : 'menu',
						items : [{
							xtype : 'menuitem',
							text : '增加',
							handler : function(item, e) {
								var grid = Ext.getCmp("DJ.System.supplier.SupOfCustPdtList");
								var winAdd = Ext.create('DJ.System.supplier.SupOfCustProductEdit');
								winAdd.seteditstate('add');
								winAdd.parent = grid.id;
								winAdd.show();
							}

						},{
							xtype : 'menuitem',
							text : '修改',
							handler : function() {
								var grid = Ext.getCmp("DJ.System.supplier.SupOfCustPdtList");
								var record = grid.getSelectionModel().getSelection();
								if (record.length != 1) {
									Ext.Msg.alert('提示','请先选择您要操作的行!');									
									return;
								};
								var winAdd = Ext.create('DJ.System.supplier.SupOfCustProductEdit');
						        winAdd.loadfields(record[0].get("productid"));
						        winAdd.seteditstate('edit');
						        winAdd.parent = grid.id;
						        winAdd.show();					
							}
						},{
									xtype : 'menuitem',
									text : '删除',
									handler : function() {

										var grid = Ext
												.getCmp("DJ.System.supplier.SupOfCustPdtList");

										MyCommonToolsZ.reqAction(
												'forbiddenCusproduct.do', grid,
												null, 1, false, true);

									}
								},{
									xtype : 'menuitem',
									text:'导入产品',
									handler:function(){
										var m = this;
										Ext.create('Ext.Window',{
											height:150,
											modal:true,
											resizable :false,
											width : 400,
											layout:'form',
											bodyPadding : 15,
											title:'产品上传',
											listeners:{
												afterrender:function(me){
													var val = {
														'fid':m.up('grid').down('combobox[name=fcustomerid]').getValue(),
														'fname':m.up('grid').down('combobox[name=fcustomerid]').rawValue
													};
													me.down('combobox[name=fcustomerid]').setmyvalue(val);
												}
											},
											items:[{
												xtype : 'form',
												baseCls : 'x-plain',
												items:[{
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
												},
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
												text:'产品模板下载',
												handler:function(){
													window.open('downloadCustExcel.do','下载模板');
												}
											},{
												xtype:'tbfill'
											},{
												text : '上传',
												handler : function() {
													var me = this;
													var form = this.up('window').down('form').getForm();
													var fcustomerid = form.findField('fcustomerid').getValue();
													if (form.isValid()) {
														form.submit({
															url : 'saveUploadCustExcelDataToJxlOrPoi.do?action='+encodeURIComponent(fcustomerid),  
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
								}
						]
					}
				},  {
					hideEmptyLabel: false,
					name : 'fcustomerid',
					id: 'DJ.System.supplier.SupOfCustPdtList.fcustomerid',
					//labelWidth:130,
					emptyText : '请选择客户',
					width : 300,
					xtype : 'ncombobox',
					displayField : 'fname',
					valueField : 'fid',
					editable : true,
//					pageSize : 50,
					queryMode : 'local',
					typeAhead : true,
					forceSelection : true,
					doRawQuery : function() {
						if(Ext.isIE){
							this.doQuery(this.getRawValue(), false, true);	
						}else{
							this.doQuery(this.getRawValue().trim(), false, true);	
						}
						
					},
					changeAction : function(comb, value) {

						var grid = comb.up("grid");

						var storeT = grid.getStore();

						var myfilter = [];
						myfilter.push({
							myfilterfield : 'c.fcustomerid',
							CompareType : "=",
							type : "string",
							value : value
						});

						maskstring = "#0";

						storeT.setDefaultfilter(myfilter);
						storeT.setDefaultmaskstring(maskstring);

						storeT.loadPage(1);

					},

					listeners : {

						select : function(comb, records) {

							comb.changeAction(comb,this.getValue());

						},
						expand:function( field, eOpts ){
							field.store.load();
						}

					},

					store : Ext.create('Ext.data.Store', {
						fields : ['fid', 'fname'],
						proxy : {							
							type : 'ajax',
							url : 'GetStanderCustomersOfSupplier.do',
							reader : {
								type : 'json',
								root : 'data'
							}
						},

						autoLoad : true,

						listeners : {
							load : function(me, records) {
								if (records && records.length && records.length > 0) {
									var com = Ext.getCmp('DJ.System.supplier.supplierCustListPdt').down("combobox[name=fcustomerid]");
									if(Ext.isEmpty(com.getValue())){
										com.setValue('');//records[0].get('fid')
										com.fireEvent('change', com,com.getValue(),null );
									}
								}
							}
						}
					})
				}, {
					xtype : 'tbspacer',
					flex : 1
				}, {
					xtype : 'mymixedsearchbox',
					condictionFields : ['c.fname','c.fspec'],
					tip : '请输入产品名称',
					useDefaultfilter : true,
					filterMode : true,
					//2015-06-30 转化为_之后，mysql中就支持中间任意字符查询
					beforeSearchActionForStore : function(myfilter, maskstrings,condictionValue) {
					Ext.each(myfilter,function(ele){
						if(ele.myfilterfield=='c.fspec'){
							ele.value = ele.value.replace(/[\*xX]/g,'_');
						}
					})
					}
				}]
			}],
			
			plugins : [
	
	{
	ptype : 'mygriditemtipplugin',
	separator : '<br/>',
	showingFields : ['productName','productSpec']
	
	}],
			
			fields : [{
				name : 'fid'
			}, 'fcreatetime','productName', 'productSpec','fdescription','productid','fmaterial'],
			columns : [

					// Ext.create('DJ.Base.Grid.GridRowNum'),

					{
						xtype : 'templatecolumn',
						tpl : ['<table width="150" height="150" border="0"><tr><td colspan="2"><div align="center"><img id="SupOfCustPdtImg_{fid}" src="getFileSourceByParentId.do?fid={fid}" alt="product" name="proudct" width="100" height="100" id="proudct"/></div></td></tr><tr style = "font-size:12pt;font-family:\'宋体\',\'隶书\';"><td><div align="center"><a href="javascript:DJ.System.supplier.SupOfCustPdtList.editImg();">编辑图片</a></div></td></tr></table>'],
						flex : 1,
						//width : 150,
						text : '产品图片',
						align : 'center'
					}, {
						xtype : 'templatecolumn',
						tpl : ['<table  style = "font-size:12pt;font-family:\'宋体\',\'隶书\'" width="100%" height="150" border="0"  cellspacing="10"><tr><td  valign="top"><div align="center">{productName}</div></td></tr><tr><td valign="top"><div align="center">{productSpec}</div></td></tr><tr><td valign="top"><div align="center">{fmaterial}</div></td></tr></table>'],
						// width : 200,
						
						minWidth : 300,
						
						flex : 1,
						text : '产品名称',
						align : 'center'
					},{
						xtype : 'templatecolumn',
						flex : 2,
						tpl : ['<table  style = "font-size:16pt;font-family:\'宋体\',\'隶书\'" width="100%" height="150" border="0"  cellspacing="10"><tr><td valign="top"><div align="center"  style = "width:80%;white-space:normal;word-wrap: break-word;line-height: 20px;">{fdescription}</div></td></tr></table>'],
						text : '备注',
						align : 'center'
					},{
						'header' : '创建时间',
						'dataIndex' : 'fcreatetime',
						 width : 140
					}
					

			],
			selModel : Ext.create('Ext.selection.CheckboxModel')
		});

		me.callParent(arguments);
		
		me.getStore().on('load',function(){
			Ext.defer(function(){
				var images = me.getEl().query('img'),
				now = new Date().getTime(),url,index;
				Ext.Array.each(images,function(img){
					url = img.src;
					index = url.indexOf('&');
					img.src = url.substring(0,index==-1?url.length:index) + '&_dc=' + now;
					Ext.fly(img).on('click',function(e){
						var magnifier = me.magnifier;
						magnifier.loadImages({
							fid : me.getSelectionModel().getSelection()[0].get('fid')
						});
						var coord = e.getXY();
						var top = coord[1] + 5;
						if (top + 300 > document.body.clientHeight) {
							top = document.body.clientHeight - 300;
						}
						magnifier.showAt({
							left : coord[0] + 80,
							top : top
						});
					});
				});
			},50);
		});
	}
});

Ext.define('DJ.System.supplier.supplierCustListPdt', {
	extend : 'Ext.panel.Panel',

	id : 'DJ.System.supplier.supplierCustListPdt',

	title : '产品档案',

	layout : {
		type : 'vbox',
		align : 'stretch'
	},

	closable : true,

	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [
			{
				xtype : 'supofcustpdtlist',
				flex : 1
			}]
		});

		me.callParent(arguments);
	}

});

