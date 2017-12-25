Ext.define('DJ.order.logistics.LogisticsOrderList',{
	id:'DJ.order.logistics.LogisticsOrderList',
	extend:'DJ.myComponent.grid.MySimpleConciseGrid',
	title:'我的订单',
	url:'getLogisticsOrderList.do',
	EditUI:'DJ.order.logistics.LogisticsOrderWinEdit',
	Delurl:'delLogisticsOrderList.do',
	pageSize : 15,
	closable:true,
	mixins : ['DJ.tools.grid.MySimpleGridMixer',
		'DJ.tools.grid.mixer.MyGridSearchMixer'],
	pagingtoolbarDock : 'bottom',
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	onload:function(){
		var grid = this;
		grid.down('button[text=全部]').toggle();
		grid.store.on('load',function(){
			grid.down('button[text=筛选条件]').menu.hide();
		})
	},
	statics:{
		editAction:function(){
			var me = Ext.getCmp('DJ.order.logistics.LogisticsOrderList');
			if (me.EditUI == null || Ext.util.Format.trim(me.EditUI) == "") {
					Ext.MessageBox.alert("错误", "没有指定编辑界面");
					return;
				}
				try {
					me.Action_BeforeEditButtonClick(me);
					me.Action_EditButtonClick(me);
					me.Action_AfterEditButtonClick(me);
				} catch (e) {

					Ext.MessageBox.alert("提示", e);

				}
		},
		viewAction:function(){
			var me = Ext.getCmp('DJ.order.logistics.LogisticsOrderList');
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
		},
		delAction:function(){
			var me = Ext.getCmp('DJ.order.logistics.LogisticsOrderList');
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
	},
	cusTopBarItems : [{

		xtype : 'mysimplebuttongroupfilter',

		filterMode : true,

		conditionObjs : [{

			text : '未接收',
			filterField : 'fstate',
			filterValue : '0'

		}, {

			text : '已指定车辆',
			filterField : 'fstate',
			filterValue : '1'

		}]

	}, {

		xtype : 'tbspacer',
		flex : 1

	},{
		xtype : 'mymixedsearchbox',
		condictionFields : ['l.fnumber', 'l.frecipientsname', 'l.frecipientsphone',
				 'l.frecipientsaddress'],
		tip : '请输入订单编号、收货人、收货电话、收货地址',
		useDefaultfilter : true,
		filterMode : true
	
	},{
		xtype : 'button',
		text : '筛选条件',
		menu : {
			xtype : 'menu',
			items : [{

				xtype : 'mydatetimesearchbox',
				filterMode : true,
				useDefaultfilter : true,
				labelFtext : '申请时间',
				conditionDateField : 'l.fcreatetime'

//				startDate : new Date()

			},{

				xtype : 'mydatetimesearchbox',
				filterMode : true,
				useDefaultfilter : true,
				labelFtext : '送达时间',
				conditionDateField : 'l.farrivedate'

//				startDate : new Date()

			}]
		}
	}],
	fields : ['flinkman','fphone','fcreatetime','fcargotype','fcartype','farrivetime','fconveyingstate','fcarnumber','fcreater','fnumber','frecipientsaddress','fid','fcustomerid','fshipper','fshipperphone','fcargoname','fcargoaddress','frecipientsname','frecipientsphone','fdriver','fdriverphone','farrivedate','frealityarrivetime','fcost',{name:'fstate',type:'int'}],
	columns:[{
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
		dataIndex:'fconveyingstate',
		align:'center',
		hidden:true,
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
		text:'送达时间/地点',
		flex:1,
		xtype:'templatecolumn',
		align:'center',
		tpl:'',
		renderer:function(val,metaData,record,rowIndex,colIndex,store,view){
			metaData.style='white-space:normal;';// 自动换行
			var time = record.get('farrivetime')==0?'上午':'下午';
			return record.get('farrivedate').substr(0,10)+" "+time+'<br/>'+record.get('frecipientsaddress')
		}
	},{
		text:'订单编号',
		align:'center',
		dataIndex:'fnumber',
		flex:1
	},{
		text:'发货人',
		dataIndex:'flinkman',
		align:'center',
		hidden:true
	},{
		text:'发货人电话',
		dataIndex:'fphone',
		align:'center',
		hidden:true
	},{
		text:'货物地址',
		dataIndex:'fcargoaddress',
		align:'center',
		hidden:true
	},{
		text:'收货人',
		dataIndex:'frecipientsname',
		align:'center',
		hidden:true
	},{
		text:'收货人电话',
		dataIndex:'frecipientsphone',
		align:'center',
		hidden:true
	},{
		text:'收货人地址',
		dataIndex:'frecipientsaddress',
		align:'center',
		hidden:true
	},{
		text:'申请时间',
		dataIndex:'fcreatetime',
		align:'center',
		hidden:true
	},{
		text:'备注',
		dataIndex:'fdescription',
		align:'center',
		hidden:true
	},{
		stateId : 'actions',
//		flex:1,
		width:100,
		header : '操作',
		xtype : 'templatecolumn',
		align : 'center',
		tpl:"",
		renderer:function(val,metaData,record,rowIndex,colIndex,store,view){
			if(record.get('fstate')==0){
				return "<div><a href='javaScript:DJ.order.logistics.LogisticsOrderList.viewAction()'>查看详情</a><br/><br/><a href='javaScript:DJ.order.logistics.LogisticsOrderList.editAction()'>修改</a><br/><br/><a href='javaScript:DJ.order.logistics.LogisticsOrderList.delAction()'>删除</a></div>"
			}else{
				return "<div><br/><br/><a href='javaScript:DJ.order.logistics.LogisticsOrderList.viewAction()'>查看详情</a><br/><br/><br/></div>"
			}
		}
	}]
})