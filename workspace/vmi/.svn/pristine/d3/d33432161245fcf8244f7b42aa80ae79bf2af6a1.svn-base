Ext.define('DJ.order.saleOrder.PreProductDemandWest',{
	extend : 'Ext.c.GridPanel',
	alias : 'widget.preproductdemandwest',
	pageSize : 30,
	url : 'getPreProductDemandStructureList.do',
	Delurl : 'delPreProductDemandStructure.do',
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	requires : ['Ext.ux.form.OneClickFileField','Ext.ux.form.TipComboBox'],
	plugins: [
	     Ext.create('Ext.grid.plugin.RowEditing', {
	         saveBtnText : "保存",
			 cancelBtnText : "取消",
			 pluginId : 'PreProductDemandWestRowEditing',
			 lockingPartner : true,
			 listeners:{
				 edit : function(editor,e){
					 var customerid = Ext.getCmp('DJ.order.saleOrder.PreProductDemand.fcustomerid').getValue();
					 if(!customerid){
						 Ext.Msg.alert('提示','客户ID不存在，保存失败！');
						 return;
					 }
					 e.record.data.fcustomerid = customerid;
					 Ext.Ajax.request({
						 url : 'savePreProductDemandStructure.do',
						 params : {PreProductDemandStructure : Ext.encode(e.record.data)},
						 success : function(res){
							 var obj = Ext.decode(res.responseText);
							 if(obj.success){
								 djsuccessmsg(obj.msg);
							 }else{
								 Ext.Msg.alert('警告',obj.msg);
							 }
							 e.store.loadPage(1);
						 }
					 });
				 }
			 }
	     })
	],
	custbar : [{
		xtype : 'ocfile',
		disabled : true,
		url : 'uploadStructureFile.do',
		fileType : 'jpg',
		buttonConfig :{
			iconCls : 'upload'
		},
		doChange : function(){
			var me = this,
				grid = me.up('grid'),
				records = grid.getSelectionModel().getSelection();
			if(records.length != 1){
				return false;
			}
			me.params.fid = records[0].get('fid');
		}
	},{
		xtype: 'tipcombo',
		storageId: 'PreProductDemandWest',
		supportFields: ["e.fcpname","e.fspec","e1.fname"],
		margin : '0 0 0 10',
		width : 120
	},{
		text : '查询',
		handler : function(){
			this.previousSibling().handleSelect();
		}
	}],
	doEdit : function(record){
		var plugin = this.getPlugin('PreProductDemandWestRowEditing');
		plugin.lockingPartner = false;
		plugin.startEdit(record,this.down('#fcpname'));
		plugin.lockingPartner = true;
	},
	onload : function(){
		var me = this,
			id = me.id,
			store = me.getStore();
		Ext.getCmp(id+'.querybutton').hide();
		Ext.getCmp(id+'.viewbutton').hide();
		Ext.getCmp(id+'.exportbutton').hide();
		Ext.getCmp(id+'.addbutton').setHandler(function(){
			var customerField = Ext.getCmp('DJ.order.saleOrder.PreProductDemand.fcustomerid'),
				customerid = customerField.getValue();
			if(!customerid){
				Ext.Msg.alert('提示','请先选择客户再新增！');
				return;
			}else{
				store.add({});
				me.doEdit(store.last());
			}
		});
		//编辑按钮
		Ext.getCmp(id+'.editbutton').setHandler(function(){
			var records = me.getSelectionModel().getSelection();
			if(records.length!=1){
				Ext.Msg.alert('提示','请选择一条数据进行操作！');
				return ;
			}
			Ext.Ajax.request({
				url : 'demandIsGenerated.do',
				params : {fid : records[0].get('fid'),index : '0'},
				success : function(res){
					var obj = Ext.decode(res.responseText);
					if(obj.success){
						me.doEdit(records[0]);
					}else{
						Ext.Msg.alert('提示',obj.msg);
					}
				}
			})
		});
		//删除按钮
		var delHandler = function(me,selectedRecord){
			if(!selectedRecord[0].get('fid')){
				store.remove(selectedRecord[0]);
				store.loadPage(1);
				return false;
			}
		};
		me.Action_DelButtonClick = Ext.Function.createInterceptor(me.Action_DelButtonClick,delHandler);
		//store添加事件
		store.addListener('load',function(){
			me.down('filefield[buttonText=上传]').disable();
		});
	},
	listeners:{
		select : function(records){
			var selModel = this.getSelectionModel(),
				selected = selModel.getLastSelected();
			selModel.getSelection().forEach(function(item){
				if(item !== selected){
					selModel.deselect(item);
					return;
				}
			});
			if(selected.get('fid')){
				this.down('filefield[buttonText=上传]').enable();
			}
			Ext.getCmp('DJ.order.saleOrder.PreProductDemandSouth').showData();
			
			var westImg = Ext.getCmp('DJ.order.saleOrder.PreProductDemand.StructureDrawing').viewImg = document.getElementById('structure_drawing');
			westImg.src = "getDrawingById.do?fid="+(selected.get('fsfileid')||selected.get('ffileid'))+'&_dc=' + Ext.Date.now();
		},
		deselect : function(me,record){
			this.down('filefield[buttonText=上传]').disable();
			Ext.getCmp('DJ.order.saleOrder.PreProductDemandSouth').showData();
			Ext.getCmp('DJ.order.saleOrder.PreProductDemand.StructureDrawing').viewImg.src='';
		}
	},
	fields : [
	       {name : 'fid'},{name : 'fcpname'},{name : 'fspec'},{name : 'fcustomerid'},
	       {name : 'ffilename'},{name : 'ffileid'},{name : 'fsfileid'}
	],
	columns : [{
		text : '序号',
		xtype : 'rownumberer',
		width : 35
	},{
		text : '包装物名称',
		dataIndex : 'fcpname',
		flex : 1,
		itemId : 'fcpname',
		editor : {
			xtype : 'textfield',
			allowBlank : false,
			blankText : "包装物名称不能为空！"
		}
	},{
		text : '规格',
		dataIndex : 'fspec',
		width : 80,
		editor : {
			xtype : 'textfield'
		}
	},{
		text : '结构图纸名称',
		dataIndex : 'ffilename',
		flex : 1
	}]
});