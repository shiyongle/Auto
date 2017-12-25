Ext.define('DJ.cardboard.system.ProducePlanList',{
	extend : 'Ext.grid.Panel',
	title : "纸板材料排产计划",
	pageSize : 50,
	closable : false,// 是否现实关闭按钮,默认为false
	requires: ['Ext.ux.form.TipComboBox','DJ.tools.common.MyCommonToolsZ'],
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	sortableColumns: false,
	initComponent: function(){
		var me = this;
		Ext.apply(me,{
			tbar:[{
				text: '修改',
				iconCls: 'edit',
				handler: function(){
					var records = me.getSelectionModel().getSelection(),
						fisweek,fweek,fday,fnoday,weeks,days,nodays;
					if(records.length!=1){
						Ext.Msg.alert('提示','请选择一条记录进行操作！');
						return;
					}
					var fproduceplanid = records[0].get('fproduceplanid');
					if(!fproduceplanid){
						Ext.Msg.alert('提示','此材料目前没有排产计划！');
						return;
					}
					fweek = records[0].get('fweek');
					fday = records[0].get('fday');
					fnoday = records[0].get('fnoday');
					fisweek = records[0].get('fisweek');
					var win = Ext.create('DJ.cardboard.system.ProducePlanEdit',{
						weeks:[],
						nonMonthes: [],
						monthes: []
					});
					win.parentGrid = me;
					win.fsupplierid = me.fsupplierid;
					win.fproductid = records[0].get('fproductid');
					win.fcreatetime = records[0].get('fcreatetime');
					win.fproduceplanid = records[0].get('fproduceplanid');
					if(fisweek == '1'){
						var weekPanel = win.down('panel[title=按周排产]').query('panel');
						if(fweek){
							weeks = fweek.split('');
							weeks.forEach(function(item,index){
								win.saveData(1,weekPanel[0].down('selectbox[value='+item+']'));
							});
						}
						if(fnoday){
							nodays = fnoday.split('_');
							nodays.forEach(function(item,index){
								win.saveData(2,weekPanel[1].down('selectbox[value='+item+']'));
							});
						}
					}else{
						var dayPanel = win.down('panel[title=按天排产]');
						days = fday.split('_');
						days.forEach(function(item,index){
							win.saveData(3,dayPanel.down('selectbox[value='+item+']'));
						});
					}
					win.show();
					
				}
			},{
				text: '刷新',
				iconCls: 'refresh',
				handler: function(){
					me.doLoad(me.fsupplierid);
				}
			},{
				text: '删除',
				iconCls: 'delete',
				handler: function(){
					var records = me.getSelectionModel().getSelection();
					if(records.length==0){
						Ext.Msg.alert('提示','请选择一条记录进行操作！');
						return;
					}
					var fids = [],fid;
					records.forEach(function(item,index){
						fid = item.get('fproduceplanid');
						if(fid){
							fids.push(fid);
						}
					});
					if(fids.length>0){
						Ext.Ajax.request({
							url: 'deleteProducePlan.do',
							params: {
								fidcls: fids.join()
							},
							success: function(res){
								var obj = Ext.decode(res.responseText);
								if(obj.success){
									djsuccessmsg(obj.msg);
									me.doLoad(me.fsupplierid);
								}else{
									Ext.Msg.alert('提示',obj.msg);
								}
							}
						});
					}else{
						Ext.Msg.alert('提示','此材料目前没有排产计划！');
					}
				}
			},{
				text: '生成排产计划',
				iconCls: 'addnew',
				handler: function(){
					if(!me.fsupplierid){
						Ext.Msg.alert('提示','请先选择制造商！');
						return;
					}
					var records = me.getSelectionModel().getSelection();
					if(records.length!=1){
						Ext.Msg.alert('提示','请选择一条记录进行操作！');
						return;
					}
					if(records[0].get('fproduceplanid')){
						Ext.Msg.alert('提示','此材料已生成排产计划，无法再次生成！');
						return;
					}
					var win = Ext.create('DJ.cardboard.system.ProducePlanEdit',{
						weeks:[],		//开机星期
						nonMonthes: [],	//不开机日期
						monthes: []		//开机日期
					});
					win.parentGrid = me;
					win.fsupplierid = me.fsupplierid;
					win.fproductid = records[0].get('fproductid');
					win.fcreatetime = records[0].get('fcreatetime');
					win.fproduceplanid = records[0].get('fproduceplanid');
					win.show();
				}
			},{
				xtype: 'textfield',
				margin : '0 0 0 10',
				width : 120,
				emptyText: '按材料名称,楞型查询...',
				enableKeyEvents: true,
				listeners: {
					keydown: function(me,e){
						if(e.getKey()==Ext.EventObject.ENTER){
							this.handleSelect();
						}
					},
					change : function(me,newValue,oldValue){
						if(newValue === '' || newValue === null){
							this.handleSelect();
						}
					}
				},
				handleSelect: function(){
					var value = this.getValue();
					me.doLoad(me.fsupplierid,{
						prouductName: value
					});
				}
			},{
				text : '查询',
				handler : function(){
					this.previousSibling().handleSelect();
				}
			},{
				xtype:'checkbox',
				margin : '0 0 0 10',
				boxLabel :'白班开机',
				inputValue:'0',
				uncheckedValue:false,
				listeners: {
					change:function(checkbox,newValue,oldValue)
					{
						
						if(newValue===true)
						{
							me.doSelectState(checkbox,checkbox.getSubmitValue());
						}
					}
				}
			},{
				xtype:'checkbox',
				margin : '0 0 0 10',
				boxLabel :'夜班开机',
				inputValue:'1',
				uncheckedValue:false,
				listeners: {
					
					change:function(checkbox,newValue,oldValue)
					{
						
						if(newValue===true)
						{
							me.doSelectState(checkbox,checkbox.getSubmitValue());
						}
					}
				}
				
			},'',{
				xtype:'timefield',
				 fieldLabel: '最晚下单时间',
				 labelWidth:80,
				 width:180,
				 increment: 30,
				 format:'H:i',
//				 autoSelect:false,
				 submitFormat:'Y-m-d H:i:s',
				  listeners: {	
				  	select:function(tfield)
				  	{
				  		//if(!Ext.isEmpty(tfield.getRawValue()))
				  		me.doSetLastTimeConfig(tfield.getSubmitData());
				  	},
				  	
				  	specialkey:function( tfield, e )
				  	{
//				  		if(e.getKey()==e.BACKSPACE){
//				  			tfield.clearValue();
//				  			me.doSetLastTimeConfig("");
//				  		}
				  		if(e.getKey()==e.ENTER){
				  			if(tfield.getStore().getCount()==0){
				  			me.doSetLastTimeConfig(tfield.getSubmitData());
				  			}
				  		}
				  	}
				  }
				// value:'19:00'
			}]
		});
		this.createColumns();
		this.createStore();
		this.callParent();
	},
	createStore: function(){
		this.store = Ext.create('Ext.data.Store', {
			storeId:'ProducePlanStore',
			fields:['fproduceplanid','fproductid','fproduct','ftilemodelid','fcreatetime','fupdatetime','fstate','d0','d1','d2','d3','d4','d5','d6','d7','d8','d9','d10','d11','d12','d13','d14','fisweek','fweek','fday','fnoday']
		});
	},
	doLoad: function(fsupplierid,params){
		params = params || {};
		var me = this;
		var dates = me.dates;
		me.fsupplierid = fsupplierid || '';
		Ext.Ajax.request({
			url: 'getProducePlanList.do',
			params: Ext.apply(params,{
				fsupplierid: me.fsupplierid
			}),
			success: function(res){
				var obj = Ext.decode(res.responseText),supplierconfig,
					productData,datas = [];
				if(obj.success){
					me.store.removeAll();
					 supplierconfig=obj.total;
					 if(supplierconfig)
					 {
					 	 me.down("timefield").setRawValue(supplierconfig.timeconfig.substr(0,5));
					 }
					productData = obj.data;
					if(!productData){
						return;
					}
					productData.forEach(function(item,index){
						item.fproduct = item.fname;
						item.fproductid = item.fid;
						dates.forEach(function(dItem,dIndex){
							item['d'+dIndex] = me.isProduceDay(dItem,item)? '√' : '';
						});
						datas.push(item);
					});
					datas.sort(function(item1,item2){
						for(var i=0;i<15;i++){
							if(item1['d'+i]!= item2['d'+i]){
								return item1['d'+i]!= '' ? -1 : 1; 
							}
						}
						return 0;
					});
					me.store.add(datas);
				}else{
					Ext.Msg.alert('错误','获取数据失败！');
				}
			}
		});
	},
	doSelectState:function(checkbox,newValue){
		var me=this;
		var record = me.getSelectionModel().getSelection();
		var fid = '';
		for (var i = 0; i < record.length; i++) {
			fid += record[i].get('fproductid');
			if (i < record.length - 1) {
				fid += ',';
			}
		}
		var searchbutton=this.down("button[text=查询]");
		Ext.Ajax.request({
					url : "saveMaterialState.do",
					params : {
						ftype : newValue,
						fid : fid
					}, // 参数
					success : function(response, option) {
						var obj = Ext.decode(response.responseText);
						if(obj.success){
							djsuccessmsg(obj.msg);
							 checkbox.previousSibling("textfield").handleSelect();
							}else{
								Ext.Msg.alert('提示',obj.msg);
							}
							 checkbox.setValue(false);
							 
					}
				});
	},
	doSetLastTimeConfig:function(timevalue)
	{
		var me = this;
		if(Ext.isEmpty(me.fsupplierid))
		{
			Ext.Msg.alert('提示','请选择制造商设置时间');
		}
		Ext.Ajax.request({
					url : "saveLastTimeConfig.do",
					params : {
						timeValue : timevalue,
						fsupplierid :  me.fsupplierid
					}, // 参数
					success : function(response, option) {
						var obj = Ext.decode(response.responseText);
						if(obj.success){
							djsuccessmsg(obj.msg);
							}else{
								Ext.Msg.alert('提示',obj.msg);
							}							 
					}
				});
	},
	isProduceDay: function(date,data){
		var isWeek = data.fisweek,
			week = data.fweek,
			day = data.fday,
			noDay = data.fnoday;
		return (isWeek=='1') ? ( week.indexOf(date.getDay())>-1 && ('_'+noDay+'_').indexOf('_'+date.getDate()+'_') == -1 )
					: ('_'+day+'_').indexOf('_'+date.getDate()+'_') > -1;
	},
	createColumns: function(){
		var columns = this.columns = [];
		var dates = this.dates = [];
		columns.push({
			text:'序号',
			xtype:'rownumberer',
			width:40
		});
		columns.push({
			text: '材料',
			dataIndex: 'fproduct',
			width: 200
		});
		columns.push({
			text: '楞型',
			dataIndex: 'ftilemodelid',
			width: 50
		});
		var weeks = ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
			date,i;
		for(i=0,date = new Date();i<15;i++){
			dates.push(date);
			columns.push({
				text: formatDate(date),
				dataIndex: 'd'+i,
				width: 70,
				align: 'center'
			});
			date = Ext.Date.add(date,Ext.Date.DAY,1);
		}
		columns.push({
			text: '开机状态',
			dataIndex: 'fstate',
			renderer:function(value){
			return value==="0"?"白班":(value==="1"?"夜班":"");
			}
		});
		columns.push({
			text: '创建时间',
			dataIndex: 'fcreatetime'
		});
		columns.push({
			text: '修改时间',
			dataIndex: 'fupdatetime'
		});
		function formatDate(date){
			return Ext.String.leftPad(date.getMonth()+1,2,'0')+'-'+Ext.String.leftPad(date.getDate(),2,'0')+'<br/>'+weeks[date.getDay()];
		}
	},
	 listeners: {
	 	render:function(){
	 		var me=this;
			MyCommonToolsZ.setComAfterrender(me, function(com) {

					MyCommonToolsZ.setQuickTip(me.down("timefield").id, "", "按回车enter可修改设置");

				});
	 	}
	 }
});
