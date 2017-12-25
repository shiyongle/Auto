Ext.define('DJ.order.saleOrder.PreProductDemandSouth',{
	extend : 'Ext.c.GridPanel',
	alias : 'widget.preproductdemandsouth',
	pageSize : 9999,
	url : 'getPreProductDemandList.do',
	Delurl : 'delPreProductDemand.do',
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	showData: function(){
		var west = Ext.getCmp('DJ.order.saleOrder.PreProductDemandWest'),
			east = Ext.getCmp('DJ.order.saleOrder.PreProductDemandEast'),
			westRecords = west.getSelectionModel().getSelection(),
			eastRecords = east.getSelectionModel().getSelection(),
			store = this.getStore(),
			westRecord,eastRecord,config=[],count=0;
		if(westRecords.length>0 && westRecords[0].get('fid')){
			config.push({
				myfilterfield : "e.fstructureid",
				CompareType : " like ",
				type : "string",
				value : westRecords[0].get('fid')
			});
			count++;
		}
		if(eastRecords.length>0){
			config.push({
				myfilterfield : "e.fplanid",
				CompareType : " like ",
				type : "string",
				value : eastRecords[0].get('fid')
			});
			count++;
		}
		store.setDefaultfilter(config);
		if(count==1){
			store.setDefaultmaskstring(" #0 ");
		}else if(count==2){
			store.setDefaultmaskstring(" #0 and #1");
		}else{	//都不选
			store.setDefaultfilter([{
				myfilterfield : "1",
				CompareType : " = ",
				type : "string",
				value : "-1"
			}]);
			store.setDefaultmaskstring(" #0 ");
		}
		store.loadPage(1);
	},
	custbar : [{
		text : '生成新需求',
		iconCls : 'app',
		height : 30,
		handler : function(){
			var south = this.up('grid'),records,record;
			if((records=south.getSelectionModel().getSelection()).length!=1){
				Ext.Msg.alert('提示','请选择一条记录进行操作！');
				return;
			}
			record = records[0];
			var win = Ext.create('DJ.order.saleOrder.PreProductDemandEdit'),
				form = win.getbaseform(),
				store = win.filePanel.getStore();
			form.findField("fname").setValue(record.get('fcpname'));
			form.findField("fcustomerid").setValue(record.get('fcustomerid'));
			form.findField("fstructureid").setValue(record.get('fstructureid'));
			form.findField("fplanid").setValue(record.get('fplanid'));
			form.findField("fdescription").setValue(record.get('fdescription'));
			form.findField("fpreproductdemandid").setValue(record.get('fid'));
			store.add({fname:record.get('fstructurefilename'),fid:record.get('fstructurefileid'),fparentid:0});
			store.add({fname:record.get('fplanfilename'),fid:record.get('fplanfileid'),fparentid:1});
			win.seteditstate('add');
			win.show();
		}
	}],
	plugins: [
	 	 Ext.create('Ext.grid.plugin.CellEditing', {
	 		 clicksToEdit : 1,
	 		 listeners : {
	 			 edit : function(editor,e){
	 				 if(e.originalValue===e.value){
	 					 return;
	 				 }
	 				 Ext.Ajax.request({
	 					 url: 'addPreProductDemandDescription.do',
	 					 params: {
	 						 fid: e.record.get('fid'),
	 						 fdescription: e.value
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
	 			 }
	 		 }
	 	 })
	],
	onload : function(){
		//按钮隐藏
		var id = this.id;
		Ext.getCmp(id+'.addbutton').hide();
		Ext.getCmp(id+'.editbutton').hide();
		Ext.getCmp(id+'.viewbutton').hide();
		Ext.getCmp(id+'.querybutton').hide();
		Ext.getCmp(id+'.exportbutton').hide();
		//客户下拉隐藏
		Ext.Ajax.request({
			url:'getMyCustomerList.do',
			success:function(res){
				var obj = Ext.decode(res.responseText);
				if(obj.success){
					if(obj.total>1){
						Ext.getCmp('DJ.order.saleOrder.PreProductDemand.fcustomerid').show();
					}else if(obj.total==1){
						Ext.getCmp('DJ.order.saleOrder.PreProductDemand.fcustomerid').setmyvalue(obj.data[0]);
					}
				}
			}
		});
		//隐藏数据
		var store = this.store;
		store.setDefaultfilter([{
			myfilterfield : "1",
			CompareType : " = ",
			type : "string",
			value : "-1"
		}]);
		store.setDefaultmaskstring(" #0 ");
		store.loadPage(1);
	},
	fields : [
	       {name : 'fid'},{name : 'fcpname'},{name : 'fspec'},{name : 'fcustpage'},{name : 'fcustomerid'},
	       {name : 'fstructurefilename'},{name : 'fplanfilename'},{name : 'fdescription'},{name : 'fplanfileid'},
	       {name : 'fstructurefileid'},{name : 'fstructureid'},{name : 'fplanid'}
	],
	columns : [{
		text : '序号',
		xtype : 'rownumberer',
		width : 35
	},{
		text : '包装物名称',
		dataIndex : 'fcpname',
		width : 120
	},{
		text : '规 格',
		dataIndex : 'fspec'
	},{
		text : '客户版面',
		dataIndex : 'fcustpage',
		flex : 1
	},{
		text : '参考结构图纸',
		dataIndex : 'fstructurefilename',
		flex : 1
	},{
		text : '参考平面图纸',
		dataIndex : 'fplanfilename',
		flex : 1
	},{
		text : '备 注',
		dataIndex : 'fdescription',
		width : 200,
		editor : {
			xtype : 'textfield'
		}
	}]
});