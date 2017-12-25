var addressConfig = {
	
					beforeExpand : function() {	
							//传值技巧，
//							this.setDefaultfilter([{
//								myfilterfield : "1",
//								CompareType : "=",
//								type : "string",
//								value : 1
//							},{
//								myfilterfield : "cd.fcustomerid",
//								CompareType : "like",
//								type : "string",
//								value : "-1"
//							}]);
//							this.setDefaultmaskstring(" #0 or #1 ");
						
							var grid =this.up('window').down('cTable');

									var records = grid.getSelectionModel().getSelection();

									if (records.length !== 1) {

										return;

									}
								Ext.getCmp("DJ.order.Deliver.batchdeliversEdit.faddressComb").setDefaultfilter([{
									myfilterfield : "cd.fcustomerid",
									CompareType : "like",
									type : "string",
									value : records[0].get("fcustomerid")
								}]);
					        	Ext.getCmp("DJ.order.Deliver.batchdeliversEdit.faddressComb").setDefaultmaskstring(" #0 ");	
								
						},
	
		MyConfig:{
			width:750,
			height:200,
//			id:'DJ.order.Deliver.batchdeliversEdit.faddress',
			url : 'getDjCustAddress.do',
			hiddenToolbar : true,
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
		},
		listeners:{
			select:function(combo,record){
				Ext.getCmp('DJ.order.BatDeliver.Deliverapplys').chooseRecord.faddressComb = record[0];
			}
			
		}
};

Ext.define('DJ.order.Deliver.batchdeliversEdit',{
	extend : 'Ext.c.BaseEditUI',
	id:'DJ.order.Deliver.batchdeliversEdit',
	title : "要货申请批量新增界面",
	width : 1120,
	minHeight : 350,
	closable : true,
	modal : true,
	resizable:false,
	myobj: {},
	layout:'fit',
	url:'SavebatchDeliverapplyLu.do',
	requires:'Ext.ux.form.DateTimeField',
	IndexCust: '',
	onload:function(){
		var grid = Ext.getCmp('DJ.order.BatDeliver.Deliverapplys');
		grid.removeDocked(grid.down('toolbar'));
		var me = this;
		Ext.Ajax.request({
			timeout : 60000,
			url : "GetAddressListByuser.do",
			success : function(response, option) {

				var obj = Ext.decode(response.responseText);
				if (obj.success == true) {
					if (obj.data != undefined) {
//						customerFiled.setmyvalue("\"fid\":\""+ obj.data[0].fid + "\",\"fname\":\""+ obj.data[0].fname + "\"");
						me.myobj.faddressid = obj.data[0].fid;
						me.myobj.faddress = obj.data[0].fname;
						me.myobj.flinkphone = obj.data[0].fphone;
						me.myobj.flinkman = obj.data[0].flinkman;
						me.myobj.fcharactername = obj.data[0].fcharactername;
						me.myobj.clearAddress = 0;
					}
				} else {
//					Ext.MessageBox.alert('错误', obj.msg);
//					me.myobj.faddressid = "";
//					me.myobj.faddress = "";
//					me.myobj.flinkphone = "";
//					me.myobj.flinkman = "";
//					me.myobj.clearAddress = 1;
					me.myobj={clearAddress:1};
				}
				
				var store = Ext.getCmp('DJ.order.BatDeliver.Deliverapplys').getStore(),
				obj = me.myobj ,
				arr = [] ;
				for(i=0;i<5;i++){
					arr.push({
						farrivetime:Ext.Date.add(new Date(new Date().setHours(14,0,0,0)),Ext.Date.DAY,5),
						famount:0,
						faddressid:obj.faddressid,
						faddress:obj.faddress,
						flinkphone:obj.flinkphone,
						flinkman:obj.flinkman
					});
				}
				store.add.apply(store,arr);
				me.down('toolbar combobox[fieldLabel=制造商]').store.load();
			}
		});
	},
	Action_BeforeSubmit:function(){
		var store = this.down('grid').getStore(),
			records = [];
		store.each(function(record){
			//批量黏贴时，是没有id的
			if(record.get('fcusproductid')=='' && Ext.isEmpty(record.get("cutpdtname"))){
				records.push(record);
			}else if(record.get('famount')==''){
				throw '配送数量不能为空！';
			}
			if(Ext.isEmpty(record.get('fsuppliername'))){
				record.set('fsupplierid','');
				record.set('fsuppliername','');
			}
		});
		//
		Ext.Array.each(records,function(item){
			store.remove(item);
		})
		
		
		if(store.getCount()<1){
			throw '请先添加记录再保存！';
		}
		if(Ext.getCmp('DJ.order.Deliver.batchdeliversEdit.cutpdtnameComb')!=undefined){
			Ext.getCmp('DJ.order.Deliver.batchdeliversEdit.cutpdtnameComb').inputEl.dom.value='0';
		}
		if(Ext.getCmp('DJ.order.Deliver.batchdeliversEdit.faddressComb')!=undefined){
			Ext.getCmp('DJ.order.Deliver.batchdeliversEdit.faddressComb').inputEl.dom.value='0';
		}
	},
	custbar:['','-','',{
		xtype:'textfield',
		height:20,
		width:20,
		emptyText:'行数',
		value:5,
		id:'DJ.order.Deliver.batchCustDeliverapplyEdit.RowCount'
	},{
		text:'新增行',
		height:30,
		handler:function(){
			var store = this.up('window').down('grid').getStore(),
				rowCount = this.previousSibling().getValue(),
				arr = [],i;
			var obj = this.up('window').myobj ;
			
			for(i=0;i<rowCount;i++){
				arr.push({
					farrivetime:Ext.Date.add(new Date(new Date().setHours(14,0,0,0)),Ext.Date.DAY,5),
					famount:0,
					faddressid:obj.faddressid,
					faddress:obj.faddress,
					flinkphone:obj.flinkphone,
					flinkman:obj.flinkman,
					fsupplierid:obj.fsupplierid,
					fsuppliername:obj.fsuppliername
				});
			}
			store.add.apply(store,arr);
		}
	},'','-','',{
		text:'包装物',
		height:30,
		handler:function(){
			var win = Ext.create('DJ.order.Deliver.CustProductChooseEdit',{myobjparam:{myobject:this.up('window').myobj,mystore:this.up('window').down('cTable').getStore()}});
			win.show();
		}
	},'','-','',{
		text:'删除行',
		height:30,
		handler:function(){
			var grid = this.up('window').down('grid');
			grid.getStore().remove(grid.getSelectionModel().getSelection());
		}
	}/*,{xtype:'tbspacer',width:30}*/,{
		xtype:'datetimefield',
		fieldLabel:'配送时间',
		format:'Y-m-d H:i',
		initComponent: function() {
			this.callParent();
		},
		itemId:'time',
		id :'timeForArrivetime',
		editable:false,
		labelWidth:60,
		listeners:{
			select:function(field,val){
				var store = this.up('window').down('cTable').getStore();
				store.each(function(record){
					record.set('farrivetime',val);
				})
			}
		}
	},{
			xtype : 'combobox',
			fieldLabel : '制造商',
			id:'fsupplierCombobox',
			labelWidth : 50,
			width:150,
			valueField : 'fid',
			displayField : 'fname',
//			editable:false,
			queryMode : 'local',
			typeAhead : true,
			forceSelection : true,
			store:Ext.create('Ext.data.Store',{
				fields: ['fid', 'fname'],
				proxy:{
					type:'ajax',
					url: 'getSupplierForDeliverApply.do',
			         reader: {
			             type: 'json',
			             root: 'data'
			         }
				},
			   listeners:{
		         	load:function(store, records, successful, eOpts){
		         			var me = Ext.getCmp('fsupplierCombobox');
							if (records.length=== 1) {
								me.setValue(records[0].get('fid'));
							
								me.up('window').down('cTable').getStore()
										.each(function(model) {
											model.set('fsuppliername',
													records[0].data.fname);
											model.set('fsupplierid',me.getValue());
											model.commit();
										})
								me.up('window').myobj.fsuppliername=records[0].data.fname;
								me.up('window').myobj.fsupplierid=me.getValue();
							}
							// 2015-06-04 by lu  关联东经，默认东经
							else if(records.length>1){
								var newArry = Ext.Array.filter(records,function(item,index,records){ 
									  if(item.get('fname')=='东经')
										  return true;
								  });
								if(newArry.length==1){
									me.setValue(newArry[0].get('fid'));
									me.up('window').down('cTable').getStore()
											.each(function(model) {
												model.set('fsuppliername',
														newArry[0].data.fname);
												model.set('fsupplierid',me.getValue());
												model.commit();
											})
									me.up('window').myobj.fsuppliername=newArry[0].data.fname;
									me.up('window').myobj.fsupplierid=me.getValue();
								}
							}
						
		         	}
		         }
			}),
			listeners:{
					render : function(me) {
					me.getStore().load({
							callback : function(records, operation, success) {
								if (records.length=== 1) {
									me.setValue(records[0].get('fid'));
									me.up('window').down('cTable').getStore()
											.each(function(model) {
												model.set('fsuppliername',
														records[0].data.fname);
												model.set('fsupplierid',me.getValue());
												model.commit();
											})
									me.up('window').myobj.fsuppliername=records[0].data.fname;
									me.up('window').myobj.fsupplierid=me.getValue();
								}
								// 2015-06-04 by lu  关联东经，默认东经
								else if(records.length>1){
									var newArry = Ext.Array.filter(records,function(item,index,records){ 
										  if(item.get('fname')=='东经')
											  return true;
									  });
									if(newArry.length==1){
										me.setValue(newArry[0].get('fid'));
										me.up('window').down('cTable').getStore()
												.each(function(model) {
													model.set('fsuppliername',
															newArry[0].data.fname);
													model.set('fsupplierid',me.getValue());
													model.commit();
												})
										me.up('window').myobj.fsuppliername=newArry[0].data.fname;
										me.up('window').myobj.fsupplierid=me.getValue();
									}
								}
							}
						})
					},
				select:function(combo,records){
					var record = records[0],
						store = this.up('window').down('cTable').getStore();
					store.each(function(model){
						model.set('fsuppliername',record.data.fname);
						model.set('fsupplierid',record.data.fid);
					})
					combo.up('window').myobj.fsuppliername=record.data.fname;
					combo.up('window').myobj.fsupplierid=record.data.fid;
				}
				
			},
			onItemClick: function(picker, record){
       		 var me = this,
           	 selection = me.picker.getSelectionModel().getSelection(),
            valueField = me.valueField;
       		 if (!me.multiSelect && selection.length) {
            if (record.get(valueField) === selection[0].get(valueField)) {
              me.fireEvent('select', me,selection);
              me.collapse();
            }
        }
    }
		},/*{xtype:'tbspacer',width:20},*/{
			xtype:'textfield',
			fieldLabel : '采购订单号',
			labelWidth : 70,
			emptyText:'按回车键结束',
			enableKeyEvents:true,
			listeners:{
				blur:function( me, The, eOpts ){
					var store = this.up('window').down('cTable').getStore();
					store.each(function(model){
						if(model.data.fordernumber==''|| model.data.mystock==''){
							model.set('fordernumber',me.value);
						}
					})
				},
				keypress:function( me, e, eOpts ){
					if(e.getKey()==13){
						var store = this.up('window').down('cTable').getStore();
						store.each(function(model){
							if(model.data.fordernumber=='' || model.data.mystock==''){
								model.set('fordernumber',me.value);
							}
						})
					}
				}
			}
		}
	],
	initComponent:function(){
		Ext.apply(this,{
			items:[{
				xtype:'cTable',
				name:'Deliverapply',
				height:300,
				pageSize:100,
				url:'',
				id:'DJ.order.BatDeliver.Deliverapplys',
				selModel : Ext.create('Ext.selection.CheckboxModel'),
				
				bbar : [Ext.create(
						"DJ.myComponent.button.ExcelPasterButton", {
							
							condition : ['cutpdtname', 'famount', 'farrivetime',
							'faddress', 'flinkman', 'flinkphone',
							'fsuppliername', 'fdescription'],
							
					onAfterPasteToGrid : function(com, dataItemsMC) {

						var grid = com.up("grid");

						var store = grid.getStore();

						var fids = [];

						var myfilter = [];

						var maskS = [];

						Ext.each(dataItemsMC, function(ele, index, all) {

							var fid = ele.get("fcusproductid");

							if (!Ext.Array.contains(fids, fid)) {

								fids.push(fid);

								myfilter.push({
									myfilterfield : "fid",
									CompareType : "=",
									type : "string",
									value : fid
								});

								if (index != 0) {

									maskS.push(" or ");

								}

								maskS.push("#" + index + " ");

							}
							
							

						});

						var el = grid.getEl();
						el.mask("系统处理中,请稍候……");

						Ext.Ajax.request({
							timeout : 60000,

							params : {
								Defaultfilter : Ext.JSON.encode(myfilter),
								Cusfilter : [],
								Defaultmaskstring :maskS.join(""),
								Maskstring : "",
								Merge : "",
								page : 1,
								start : 0,
								limit : 100

							},

							url : "GetCustproductLists.do",
							success : function(response, option) {

								var obj = Ext.decode(response.responseText);
								if (obj.success == true) {

									var map = new Ext.util.HashMap();

									Ext.each(obj.data,
											function(ele, index, all) {

												map.add(ele.fid, ele);

											});

									Ext.each(dataItemsMC, function(ele, index,
											all) {

										var mixMC = store.data;

										var itemT = mixMC.get(ele.internalId);

										itemT.set("cutpdtnumber", map.get(ele
												.get("fcusproductid")).fnumber);

										itemT.set("fspec", map.get(ele
												.get("fcusproductid")).fspec);
										itemT
												.set(
														"fcharacter",
														map
																.get(ele
																		.get("fcusproductid")).fcharactername);
										itemT.set("balance", map.get(ele
												.get("fcusproductid")).balance);

									});

								} else {
									Ext.MessageBox.alert('错误', obj.msg);

								}
								el.unmask();
							}
						});

					},
					removeItemsAfterSync : true,
					// gainSaveButton : function (){
					//							
					// return
					// Ext.getCmp("DJ.order.Deliver.batchCustDeliverapplyEdit.savebutton");
					//								
					// }
					otherFieldRemoteCfgs : [{
						beanName : 'Custproduct',// hql里面的bean名称
						sourceField : 'cutpdtname',// 源域，用于获取错误的中文提示
						sourceFieldInBean : 'fname',// 在bean中的名称，用于hql取值
						goalField : 'fid',// 目标域，在bean中的名称，用于hql取值
						goalDataIndex : 'fcusproductid'// 目标域，dataIndex，用于返回后的赋值
					}, {
						beanName : 'Address',// hql里面的bean名称
						sourceField : 'faddress',// 源域，用于获取错误的中文提示
						sourceFieldInBean : 'fname',// 在bean中的名称，用于hql取值
						goalField : 'fid',// 目标域，在bean中的名称，用于hql取值
						goalDataIndex : 'faddressid'// 目标域，dataIndex，用于返回后的赋值
					}, {
						beanName : 'Supplier',// hql里面的bean名称
						sourceField : 'fsuppliername',// 源域，用于获取错误的中文提示
						sourceFieldInBean : 'fname',// 在bean中的名称，用于hql取值
						goalField : 'fid',// 目标域，在bean中的名称，用于hql取值
						goalDataIndex : 'fsupplierid'// 目标域，dataIndex，用于返回后的赋值
					}]
						// ,
						// gainGrid : function() {
						//
						// return Ext
						// .getCmp("DJ.order.BatDeliver.Deliverapplys");
						//
						//							}

				}),{
					text: '下单方式切换',
					handler: function(){
						var owin = this.up('window');
						owin.close();
						var nwin = Ext.create('DJ.order.Deliver.DeliversEdit');
						nwin.setparent('DJ.order.Deliver.DeliversList');
						nwin.seteditstate('add');
						nwin.show();
					}
				}],
				
				plugins : [Ext.create("Ext.ux.grid.MySimpleGridContextMenu", {
					useCopyItem : true
				}), Ext.create("Ext.ux.grid.MyGridShortcutKeyPlugin", {

					enableCopyPaste : false
					,bindings:[{
			gainComAndTip : function(comGrid) {
				return {

					com : comGrid.up("window").down("button[text=删除行]"),
					tip : "delete"

				};
			},
			key : Ext.EventObject.DELETE,
			handler : function(keyCode, e) {

				var grid = e.grid;

				var button = grid.up("window").down("button[text=删除行]");
				
				button.handler.call(button);
			}

		}]

				}),Ext.create("Ext.grid.plugin.CellEditing",{
					clicksToEdit:1,
					listeners:{
						beforeedit:function(editor, e){
		            		if(e.record.get('fordernumber')!=''&& e.colIdx==1 && e.record.get('mystock')!=''){
		            			return false;
		            		}
		            	},
						edit:function(editor, e){
							var record = e.record,
							cRecord = e.grid.chooseRecord;
							if(e.column.dataIndex=='cutpdtname' && cRecord.cutpdtnameComb!=''){	//第一列（包装物名称）编辑后
								record.set('balance',cRecord.cutpdtnameComb.data.balance);
								record.set('fcusproductid',cRecord.cutpdtnameComb.data.fid);
								record.set('cutpdtname',cRecord.cutpdtnameComb.data.fname);
								record.set('cutpdtnumber',cRecord.cutpdtnameComb.data.fnumber);
								record.set('fcharacter',cRecord.cutpdtnameComb.data.fcharactername);
								record.set('ftraitid',cRecord.cutpdtnameComb.data.fcharacterid);
								record.set('fspec',cRecord.cutpdtnameComb.data.fspec);
								
//								e.grid.chooseRecord.cutpdtnameComb = '';
								Ext.getCmp('DJ.order.Deliver.batchdeliversEdit.cutpdtnameComb').inputEl.dom.value='';
								
//								if(Ext.getCmp('DJ.order.Deliver.batchCustDeliverapplyEdit').myobj.clearAddress=="1"){
//									var rows = Ext.getCmp("DJ.order.BatDeliver.Deliverapplys").getSelectionModel().getSelection();
//									rows[0].set("faddressid", "");
//									rows[0].set("faddress", "");
//									rows[0].set("flinkman", "");
//									rows[0].set("flinkphone", "");
//								}
							}else if(e.column.dataIndex=='faddress' && cRecord.faddressComb!=''){
								record.set('faddressid',cRecord.faddressComb.data.fid);
								record.set('faddress',cRecord.faddressComb.data.fname);
								record.set('flinkman',cRecord.faddressComb.data.flinkman);
								record.set('flinkphone',cRecord.faddressComb.data.fphone);
//								e.grid.chooseRecord.faddressComb = '';
								Ext.getCmp('DJ.order.Deliver.batchdeliversEdit.faddressComb').inputEl.dom.value='';
							}else if(e.column.dataIndex=='fsuppliername' && cRecord.fsupplierRecord!=null){
//								console.log(cRecord.fsupplierRecord);
								record.set('fsupplierid',cRecord.fsupplierRecord.data.fid);
								record.set('fsuppliername',cRecord.fsupplierRecord.data.fname);
								cRecord.fsupplierRecord=null;
							}
							else if(e.column.dataIndex=='fcustname' && cRecord.custnameComb!=''){	//第一列（包装物名称）编辑后
								record.set('fcustomerid',cRecord.custnameComb.data.fid);
								record.set('fcustname',cRecord.custnameComb.data.fname);
								cRecord.custnameComb = null;
			            		//2015-06-06 客户选完之后，产品需要根据客户过滤
			            		Ext.getCmp('DJ.order.Deliver.batchdeliversEdit').IndexCust = e.record.get('fcustomerid');			            
							}
						}
					}
				})],
				chooseRecord:{},
				fields:[{
				    		name : "fid"
				    	},{
				    		name : "fcustomerid"
				    	},{
				    		name : "fcustname"
				    	} ,{
				    		name : "cutpdtname"
				    	}, {
				    		name : "fcusproductid"
				    	}, {
				    		name : "cutpdtnumber"
				    	}, {
				    		name : "fcharacter"
				    	}, {
				    		name : "famount"
				    	},{
				    		name : "farrivetime"
				    	},
				    	{
				    		name : "faddress"
				    	},
				    	{
				    		name : "faddressid"
				    	}, {
				    		name : "flinkman"
				    	}, {
				    		name : "flinkphone"
				    	},{
				    		name : "fdescription"
				    	},{
				    		name : "ftraitid"
				    	},{
				    		name:'balance'
				    	},{
				    		name:'fspec'
				    	},{
				    		name:'fsupplierid'
				    	},{
				    		name:'fsuppliername'
				    	},{
				    		name:'fordernumber'
				    	},{
				    		name:'mystock'
				    	}
				    	],
				columns:[{
							dataIndex:'fordernumber',
							text:'采购订单号',
//							flex:2,
							editor:{
								
							}
						},{
							dataIndex:'fcustname',
							text:'客户',
							sortable:true,
							width : 190,
							editor:{
								xtype:'cCombobox',
								displayField:'fname',
								valueField:'fcustomerid',
								id:'DJ.order.Deliver.batchdeliversEdit.custComb',
								allowBlank:false,
								blankText:'请选择客户',
								MyConfig : {
									width : 800,// 下拉界面
									height : 200,// 下拉界面
									url : 'GetCustomerList.do', // 下拉数据来源
									fields : [{
										name : 'fid'
									}, {
										name : 'fname',
										myfilterfield : 't_bd_customer.fname', // 查找字段，发送到服务端
										myfiltername : '名称', // 在过滤下拉框中显示的名称
										myfilterable : true
											// 该字段是否查找字段
											}, {
												name : 'fnumber',
												myfilterfield : 't_bd_customer.fnumber', // 查找字段，发送到服务端
												myfiltername : '编码', // 在过滤下拉框中显示的名称
												myfilterable : true
											// 该字段是否查找字段
											}, {
												name : 'findustryid'
											}, {
												name : 'fmnemoniccode'
											}, {
												name : 'faddress'
											}],
									columns : [{
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
										'header' : '客户名称',
										'dataIndex' : 'fname',
										sortable : true
									}, {
										'header' : '助记码',
										'dataIndex' : 'fmnemoniccode',
										sortable : true
									}, {
										'header' : '行业',
										'dataIndex' : 'findustryid',
										sortable : true
									}, {
										'header' : '地址',
										'dataIndex' : 'faddress',
										sortable : true,
										width : 250
									}]
								},
								listeners:{
									select:function(combo,record){
										Ext.getCmp('DJ.order.BatDeliver.Deliverapplys').chooseRecord.custnameComb = record[0];
									}
								}
							}
						},{
							dataIndex:'cutpdtname',
							text:'包装物名称',
							sortable:true,
							width : 190,
//							flex:0.5,
							renderer:function(val,m){
								var me = this;
								if(!Ext.isEmpty(val)){
//									var record = me.chooseRecord.cutpdtnameComb;
//									return "<span data-qtip='包装物名称："+val+", 包装物编码："+record.get('fnumber')+", 规格："+record.get('fspec')+", 特性："+record.get('fcharactername')+"'>"+val+"</span>";
									return "<span data-qtip='包装物名称："+val+", 包装物编码："+m.record.get('cutpdtnumber')+", 规格："+m.record.get('fspec')+", 特性："+m.record.get('fcharactername')+"'>"+val+"</span>";
								}
							},
							editor:{
								xtype:'cCombobox',
								displayField:'fname',
								valueField:'fid',
								id:'DJ.order.Deliver.batchdeliversEdit.cutpdtnameComb',
								allowBlank:false,
								blankText:'请选择包装物',
//								multiSelect:true,
								MyConfig:{
									width:600,
									height:200,
									hiddenToolbar:true,
									id:'DJ.order.Deliver.batchdeliversEdit.CustProductList',
									url : 'GetCustproductLists.do',
									ShowImg: true,
									fields:[{
										name:'fid'
									},{
										name : 'fname',
										myfilterfield : 't_bd_Custproduct.fname',
										myfiltername : '名称',
										myfilterable : true
									},{
										name:'fnumber',
										myfilterfield : 't_bd_Custproduct.fnumber',
										myfiltername : '编号',
										myfilterable : true
									},{
										name:'fspec',
										myfilterfield : 't_bd_Custproduct.fspec',
										myfiltername : '规格',
										myfilterable : true
									},{
										name:'fcharactername',
										myfilterfield : 't_bd_Custproduct.fcharactername',
										myfilterable : true
									},{
										name:'fcharacterid'
									},{
										name:'fcustomerid'
									},{
										name:'balance'
									}],
									columns:[{
										dataIndex:'fname',
										header:'包装物名称',
										sortable:true,
//										flex:1
										width : 255
									},{
										dataIndex:'fnumber',
										header:'包装物编号',
										sortable:true,
//										flex:1
										width : 137
									},{
										dataIndex:'fspec',
										header:'规格',
										sortable:true,
										flex:1
									},{
										dataIndex:'fcharactername',
										header:'特性',
										sortable:true,
										flex:1
									}]
								},
								listeners:{
									select:function(combo,record){
										Ext.getCmp('DJ.order.BatDeliver.Deliverapplys').chooseRecord.cutpdtnameComb = record[0];
										//var customerid = Ext.getCmp("DJ.order.Deliver.DeliversCustEdit.fcustomerid").getValue();
										
										/*Ext.Ajax.request({
											timeout : 60000,
											url:"countBalanceqtyByCusproductandCustomer.do",
											params : {
												cusproduct : record[0].get("fid"),
												customerid :record[0].get("fcustomerid"),
												ftraitid:record[0].get("fcharacterid")
											},
											success : function(response, option) {

												var obj = Ext
														.decode(response.responseText);
												if (obj.success == true) {

													Ext.getCmp('DJ.order.BatDeliver.Deliverapplys').chooseRecord.balance = obj.data[0].balanceqty;

												} else {
													Ext.MessageBox.alert('错误', obj.msg);

												}
											}
										})*/
										
//										balanceFiled.setValue(record[0].balanceqty);
									}
								},
								beforeExpand : function() {
									var grid =this.up('window').down('cTable');

									var records = grid.getSelectionModel().getSelection();

									if (records.length !== 1) {

										return;

									}
									Ext.getCmp("DJ.order.Deliver.batchdeliversEdit.cutpdtnameComb").setDefaultfilter([{
											myfilterfield : "fcustomerid",
											CompareType : "like",
											type : "string",
											value : records[0].get('fcustomerid')
										}]);
							        	Ext.getCmp("DJ.order.Deliver.batchdeliversEdit.cutpdtnameComb").setDefaultmaskstring(" #0 ");							
								}
							}
						}, {
								'header' : '包装物编号',
								width : 137,
								'dataIndex' : 'cutpdtnumber',
								sortable : true,
								allowBlank : false,
								hideable : false,
								hidden:true
							}, {
								'header' : '规格',
								width : 120,
								'dataIndex' : 'fspec',
								sortable : true,
								allowBlank : false,
								hidden:true,
								hideable : false
							}, {
								header : '特性',
								width : 90,
								dataIndex : 'fcharacter',
								sortable : true,
								hidden:true,
								hideable : false
							}, {
								'header' : '配送数量',
								width : 60,
								'dataIndex' : 'famount',
								sortable : true,
								allowBlank : false,
								editor : {
									xtype : 'numberfield'
								}
							},{
								'header' : '可用库存',
								width : 60,
//								id:'DJ.order.BatDeliver.Deliverapplys.balance',
								'dataIndex' : 'balance',
								sortable : true,
								allowBlank : false
							}, {
								'header' : '配送时间',
								'dataIndex' : 'farrivetime',
								xtype : "datecolumn",
								format : "Y-m-d H:i",
								width : 120,
								sortable : true,
								editor : {
									xtype : 'datetimefield',
									allowBlank : false,
									format : 'Y-m-d H:i'
								}
							}, {
								dataIndex:'faddress',
								text:'送货地址',
								sortable:true,
								width : 200,
								renderer:function(val){
//									var me = this;
									if(!Ext.isEmpty(val)){
										return "<div><span data-qtip='送货地址："+val+"'>"+val+"</span></div>";
									}
								},
								editor:Ext.apply({
									xtype:'cCombobox',
									displayField:'fname',
									valueField:'fid',
									id:'DJ.order.Deliver.batchdeliversEdit.faddressComb',
									allowBlank:false,
									blankText:'请选择送货地址'
									
								},addressConfig)
						
							}, {
								'header' : '联系人',
								'dataIndex' : 'flinkman',
								width : 50,
								sortable : true,
								editor : {
									
								}
							}, {
								'header' : '联系电话',
								'dataIndex' : 'flinkphone',
								sortable : true,
								editor : {
									
								}
							},{
								header : '制造商',
								dataIndex : 'fsuppliername',
								sortable : true,
								editor:{
									xtype : 'combobox',
									valueField : 'fid',
									displayField : 'fname',
//									editable:false,
									store:Ext.create('Ext.data.Store',{
										fields: ['fid', 'fname'],
										proxy:{
											type:'ajax',
											url: 'getSupplierForDeliverApply.do',
									         reader: {
									             type: 'json',
									             root: 'data'
									         }
										}
									}),
									listeners:{
										select:function(combo,record){
											Ext.getCmp('DJ.order.BatDeliver.Deliverapplys').chooseRecord.fsupplierRecord = record[0];
										},
										focus:function( com, The, eOpts ){
											com.store.load();
											com.expand();
										},
										change:function(com,newValue){
											com.queryMode = "local";
											com.minChars =  0;
											com.doRawQuery();
										}
									}
								}
							}, {
								'header' : '备注',
								'dataIndex' : 'fdescription',
								sortable : true,
								renderer:function(val){
									var me = this;
									if(!Ext.isEmpty(val)){
										return "<span data-qtip='备注："+val+"'>"+val+"</span>";
									}
								},
								editor : {
									
								}
							},{
								dataIndex:'mystock',
								header:'备货ID',
								sortable:true,
								hidden:true
							}]
			}]
		});
		this.callParent(arguments);
	}
});