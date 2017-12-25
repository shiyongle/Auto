Ext.define('DJ.order.logistics.LogisticsOrderManageList',{
	id:'DJ.order.logistics.LogisticsOrderManageList',
	extend:'DJ.myComponent.grid.MySimpleConciseGrid',
	title:'订单管理',
	url:'getLogisticsOrderManageList.do',
	EditUI:'DJ.order.logistics.LogisticsOrderWinEdit',
	Delurl:'',
	pageSize : 15,
	closable:true,
	mixins : ['DJ.tools.grid.MySimpleGridMixer',
		'DJ.tools.grid.mixer.MyGridSearchMixer'],
	pagingtoolbarDock : 'bottom',
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	onload:function(){
		var grid = this;
		var fitlerstate=grid.down("button[text=未接收]");
		fitlerstate.toggle();
		grid.getStore().on('load',function(store, records, successful, eOpts){
			if(Ext.isEmpty(store.Defaultmaskstring)){
			store.filter("fstate", 0)
			}else{
				store.clearFilter();
			}
			grid.down('button[text=筛选条件]').menu.hide();
		});	
	},
	
	statics:{
		//取消派送
	cancellSend : function() {
			var me = Ext
					.getCmp("DJ.order.logistics.LogisticsOrderManageList");

			var record = me.getSelectionModel().getSelection();
			if (record.length == 0) {
				Ext.MessageBox.alert('提示', '请选择记录');
				return
			};
			try {
				Ext.MessageBox.confirm('确定', '确定取消派送？', function(c4) {
					if (c4 == 'yes') {

						var ids = "";
						for (var i = 0; i < record.length; i++) {
							var fid = record[i].get("fid");
							ids += fid;
							if (i < record.length - 1) {
								ids = ids + ",";
							}
						}
						var el = me.getEl();
						el.mask("系统处理中,请稍候……");
						Ext.Ajax.request({
									url : 'cancellSendOrders.do',
									params : {
										fidcls : ids
									}, // 参数
									success : function(response, option) {
										var obj = Ext
												.decode(response.responseText);
										if (obj.success == true) {
											// Ext.MessageBox.alert("成功",
											// obj.msg);
											djsuccessmsg(obj.msg);
											el.unmask();
											me.store.load();
										} else {
											Ext.MessageBox.alert("错误", obj.msg);
											el.unmask();
											me.store.load();
										}
									}
								});

					}
				})
			} catch (e) {
				Ext.MessageBox.alert('失败', e)
			}

		},
		viewAction:function(){
			var me = Ext.getCmp('DJ.order.logistics.LogisticsOrderManageList');
			if (me.EditUI == null || Ext.util.Format.trim(me.EditUI) == "") {
					Ext.MessageBox.alert("错误", "没有指定编辑界面");
					return;
				}
				try {
					var record = me.getSelectionModel().getSelection();
					if (record.length == 0) {
						throw "请先选择您要操作的行!";
					};
					me.Action_BeforeViewButtonClick(me);
					var editui = Ext.create(me.EditUI);
					editui.loadfields(record[0].get("fid"));
					// me.loadfields(editui, record[0].get("fid"));
//					editui.seteditstate("view");
					editui.setparent(me.id);
					me.Action_AfterViewButtonClick(me);
					editui.editstate='view';
					editui.show();
					Ext.each(editui.query('field'),function(e){
						e.setReadOnly(true);
					})
				} catch (e) {

					Ext.MessageBox.alert("提示", e);

				}
		}
	},
	
	dockedItems : [{
				xtype : 'toolbar',
				dock : 'top',
				style : {
					background : 'white'
				},
				border : 0,
				items : [{
							xtype : 'mysimplebuttongroupfilter',

							filterMode : true,

							conditionObjs : [{

										text : '未接收',
										filterField : 'l.fstate',
										filterValue : '0'
							},{

										text : '已指定车辆',
										filterField : 'l.fstate',
										filterValue : '1'

		}]
	}, {

		xtype : 'tbspacer',
							flex : 1
						}]
			}, {
				xtype : 'toolbar',
				dock : 'top',
				style : {
					background : 'white'

				},
				items : [{ xtype:'container',
					items:[{
							xtype : 'button',
							text : '派车',
							width: 80,
							handler :function(btn){	
								var me = this.up("gridpanel");
								var record = me.getSelectionModel().getSelection();
								if (record.length == 0) {
									Ext.MessageBox.alert('提示', '请先选择您要操作的行');
									return;
								};
								if (record.length >1) {
									Ext.MessageBox.alert('提示', '请选择一条记录进行操作');
									return;
								};
								if(!Ext.isEmpty(record[0].get("fstate"))&record[0].get("fstate")===1)
								{
									Ext.MessageBox.alert('提示', '该订单已派车');
									return;
								}
								var editui = Ext.create("DJ.order.logistics.LogisticsSendEdit");
								editui.seteditstate("add");
								editui.setparent(me.id);
								editui.down("textfield[hidden=true]").setValue(record[0].get("fid"));
								editui.show();
								}
						}]
						}, { xtype: 'tbspacer' ,width:20
						}, {
							 xtype:'container',
							items:[{
							xtype : 'button',
							text : '取消派车',
							width: 80,	
							handler :function(btn){
							DJ.order.logistics.LogisticsOrderManageList.cancellSend();
							}
							}]
						}, {

							xtype : 'tbspacer',
							flex : 1

						}, {
							xtype : 'mymixedsearchbox',
							condictionFields : ['c.fname', 'l.fnumber',
									'fcargotype', 'l.flinkman',
									'l.frecipientsname', 'l.fdriver',
									'l.fcarnumber'],
							tip : '请输入客户名称、订单编号、货物类型、发货人、收货人、司机、车牌号',
							useDefaultfilter : true,
							filterMode : true

						}, {
							xtype : 'button',
							text : '筛选条件',
							menu : {
								xtype : 'menu',
								items : [{

									xtype : 'mydatetimesearchbox',
									filterMode : true,
									useDefaultfilter : true,
									labelFtext : '申请时间',
									conditionDateField : 'l.fcreatetime',
									startDate : Ext.Date.getFirstDateOfMonth(new Date())
								}, {
									xtype : 'mydatetimesearchbox',
									filterMode : true,
									useDefaultfilter : true,
									labelFtext : '送达时间',
									conditionDateField : 'l.farrivedate',
									startDate : Ext.Date.getFirstDateOfMonth(new Date())
								}]
							}
						}]
			}],
	fields : ['fdescription','fcreatetime','fcreater','cname','fcartype','fcargotype','farrivetime','fconveyingstate','fcarnumber','fcreater','fnumber','frecipientsaddress','fid','fcustomerid','fphone','flinkman','fcargoaddress','frecipientsname','frecipientsphone','fdriver','fdriverphone','farrivedate','frealityarrivetime','fcost',{name:'fstate',type:'int'}],
	columns:[{
		text:'客户名称',
		align:'center',
		dataIndex:'cname',
		flex:1
	},{
		text:'订单状态',
		align:'center',
		dataIndex:'fstate',
		flex:1,
		renderer:function(val){
			if(val=='0'){
				return '未接收';
			}else if(val=='1'){
				return '已指定车辆';
			}else if(val=='2'){
			
			}
		}
	},{
		text:'运输状态',
		hidden:true,
		dataIndex:'fconveyingstate',
		align:'center',
		flex:1,
		renderer:function(val){
			if(val==0){
				return "待运";
			}else if(val==1){
				return "在途";
			}
		}
	},{
		text:'运费',
		align:'center',
		dataIndex:'fcost',
		flex:1,
		renderer:function(val){
			if(!Ext.isEmpty(val)){
				return "<b>¥"+val+"</b>";
			}
		}
	},{
		text:'司机',
		align:'center',
		dataIndex:'fdriver',
		flex:1,
		renderer:function(val,metaData,record,rowIndex,colIndex,store,view){
			return val+"<br/>"+record.get('fdriverphone')
		}
	},{
		text:'车牌号',
		dataIndex:'fcarnumber',
		align:'center',
		flex:1
	},{
		text:'货物类型',
		dataIndex:'fcargotype',
		align:'center',
		flex:1
	},{
		text:'所需车型',
		dataIndex:'fcartype',
		align:'center',
		flex:1
	},{
		text:'发货人',
		dataIndex:'flinkman',
		align:'center',
		flex:1,
		renderer:function(val,metaData,record,rowIndex,colIndex,store,view){
			return val+"<br/>"+record.get('fphone')
		}
	},{
		text:'货物位置',
		dataIndex:'fcargoaddress',
		align:'center',
		flex:1,
		align:'center',
			renderer:function(val,metaData,record,rowIndex,colIndex,store,view){
			metaData.style='white-space:normal;';// 自动换行
			return val;
		}
	},{
		text:'收货人',
		dataIndex:'frecipientsname',
		align:'center',
		flex:1,
		renderer:function(val,metaData,record,rowIndex,colIndex,store,view){
			return val+"<br/>"+record.get('frecipientsphone')
		}
	},{
		text:'送达时间',
		flex:1,
		xtype:'templatecolumn',
		align:'center',
		tpl:'',
		renderer:function(val,metaData,record,rowIndex,colIndex,store,view){
			var time = record.get('farrivetime')==0?'上午':'下午';
			return record.get('farrivedate').substr(0,10)+"</br>"+time;
		}
	},{
		text:'收货地址',
		flex:1,
		dataIndex:'frecipientsaddress',
		align:'center',
			renderer:function(val,metaData,record,rowIndex,colIndex,store,view){
			metaData.style='white-space:normal;';// 自动换行
			return val;
		}
	},{
		text:'订单编号',
		align:'center',
		dataIndex:'fnumber',
		flex:1
	},{
		text:'备注',
		align:'center',
		dataIndex:'fdescription',
		hidden:true,
		flex:1
	},{
		text:'申请时间',
		align:'center',
		dataIndex:'fcreatetime',
		hidden:true,
		flex:1
	},{
		text:'申请人',
		align:'center',
		dataIndex:'fcreater',
		hidden:true,
		flex:1
	},{
		stateId : 'actions',
		width:100,
		header : '操作',
		xtype : 'templatecolumn',
		align : 'center',
		tpl:"",
		renderer:function(val,metaData,record,rowIndex,colIndex,store,view){
			return "<div><br/><br/><a href='javaScript:DJ.order.logistics.LogisticsOrderManageList.viewAction()'>查看详情</a><br/><br/><br/></div>"
		}
	}]
	
})