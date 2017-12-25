Ext.define('DJ.order.Deliver.DeliversBoardSupCusProductGrid', {
	extend : 'Ext.grid.Panel',
	pageSize : 15,
	hideHeaders : true,
	columns : [
	        {
	        	xtype: 'rownum',
	        	width: 20
	        },
			{
				xtype : 'templatecolumn',
				tpl : ['<table width="150" height="150" border="0"><tr><td colspan="2"><div align="center"><img src="getFileSourceByParentId.do?fid={fid}" alt="product" name="proudct" width="100" height="100" id="proudct"/></div></td></tr></table>'],
				flex : 1,
				text : 'MyTemplateColumn2'
			},{
				xtype : 'templatecolumn',
				tpl : ['<table  style = "font-size:16pt;font-family:\'宋体\',\'隶书\'" width="100%" height="150" border="0"  cellspacing="10"><tr><td  valign="top" ><div align="center">{productName}</div></td></tr><tr><td valign="top"><div align="center">{productSpec}</div></td></tr></table>'],
				flex : 1,
				text : 'MyTemplateColumn3'
			},{
				xtype : 'templatecolumn',
				flex : 2,
				tpl : ['<table  style = "font-size:16pt;font-family:\'宋体\',\'隶书\'" width="100%" height="150" border="0"  cellspacing="10"><tr><td valign="top"><div align="center"  style = "width:80%;white-space:normal;word-wrap: break-word;line-height: 20px;">{fdescription}</div></td></tr></table>'],
				text : 'MyTemplateColumn4'
			}
	],
	initComponent: function(){
		this.selModel = Ext.create('Ext.selection.CheckboxModel');
		this.store = Ext.create('Ext.data.Store', {
		    fields : ['fid', 'productName', 'productSpec','fdescription','productid','fisboardcard'],
		    proxy: {
		        type: 'ajax',
		        url: 'getSupCusProductOfBoxBoard.do',
		        reader: {
		            type: 'json',
		            root: 'data'
		        }
		    }
		});
		this.callParent(arguments);
	}
});

Ext.define('DJ.order.Deliver.DeliversBoardSupCusProductEdit',{
	extend : 'Ext.Window',
	id:'DJ.order.Deliver.DeliversBoardSupCusProductEdit',
	title : "产品档案管理界面",
	requires: ['DJ.Base.Grid.GridRowNum'],
	closable : true,
	modal : true,
	resizable:false,
	layout:'fit',
	url:'',
	infourl: '',
	viewurl: '',
	initComponent: function(){
		this.createTbar();
		Ext.apply(this,{
			items: this.createGrid()
		});
		this.callParent(arguments);
	},
	loadGrid: function(){
		var win = this,customerid,queryVal,grid,store,myfilter,maskstring;
		customerid = win.down('combobox[itemId=customer]').getValue();
		queryVal = Ext.String.trim(win.down('textfield[itemId=queryVal]').getValue());
		grid = win.grid;
		store = grid.getStore();
		myfilter = [{
			myfilterfield : 'c.fcustomerid',
			CompareType : "=",
			type : "string",
			value : customerid
		}];
		maskstring = "#0";
		if(queryVal){
			myfilter.push({
				myfilterfield : 'c.fname',
				CompareType : "like",
				type : "string",
				value : queryVal
			});
			maskstring += " and #1";
		}
		store.proxy.extraParams = {
				Defaultfilter : Ext.encode(myfilter),
				Defaultmaskstring : maskstring
		};
		store.load();
	},
	doSave: function(fcustpdtid,fboardid){
		var win = this;
		Ext.Ajax.request({
			url: 'updateCustpdtByboard.do',
			params: {
				fcustpdtid: fcustpdtid,
				fboardid: fboardid
			},
			success: function(res){
				var obj = Ext.decode(res.responseText);
				if(obj.success){
					djsuccessmsg(obj.msg);
					win.close();
				}else{
					Ext.Msg.alert('错误',obj.msg);
				}
			},
			failure: function(){
				Ext.Msg.alert('错误','操作失败，请刷新页面重试！');
			}
		});
	},
	createGrid: function(){
		return this.grid = Ext.create('DJ.order.Deliver.DeliversBoardSupCusProductGrid',{
			width: 900,
			height: 400
		});
	},
	createTbar: function(){
		var win = this;
		win.tbar = [{
			text: '关联档案',
			height: 30,
			handler: function(){
				var grid = win.grid,
					records = grid.getSelectionModel().getSelection(),
					fcustpdtid,fboardid,fisboardcard;
				if (records.length != 1) {
					Ext.Msg.alert("提示", "请先选择一条进行操作");
					return;
				}
				fcustpdtid = records[0].get('fid');
				fboardid = win.fboardid;
				fisboardcard = records[0].get('fisboardcard');
				if(fisboardcard==1){
					Ext.Msg.confirm('提示','此产品已编辑纸箱信息，是否覆盖！',function(action){
						if(action == 'yes'){
							win.doSave(fcustpdtid,fboardid);
						}
					})
				}else{
					win.doSave(fcustpdtid,fboardid);
				}
			}
		},{
			text: '关&nbsp;闭',
			height: 30,
			handler: function(){
				win.close();
			}
		},{
			xtype: 'tbspacer',
			width: 40
		},{
			xtype : 'ncombobox',
			fieldLabel: '客户',
			itemId: 'customer',
			width : 250,
			labelWidth: 45,
			displayField : 'fname',
			valueField : 'fid',
			queryMode : 'local',
			forceSelection : true,
			listeners : {
				select : function() {
					win.loadGrid();
				}
			},
			store : Ext.create('Ext.data.Store', {
				fields : ['fid', 'fname'],
				proxy : {
					type : 'ajax',
					url : 'GetCustomersOfSupplier.do',
					reader : {
						type : 'json',
						root : 'data'
					},
					extraParams: {
						limit: 999
					}
				},
				autoLoad: true,
				listeners : {
					load : function(me, records) {
						if (records && records.length) {
							var com = win.down("combobox[itemId=customer]");
							com.setValue(records[0].get('fid'));
							win.loadGrid();
						}
					}
				}
			})
		},'|',{
			xtype: 'textfield',
			itemId: 'queryVal',
			listeners: {
				specialkey: function(field,e){
					if(e.getKey() == Ext.EventObject.ENTER){
						win.loadGrid();
					}
				}
			}
		},{
			text: '查询',
			handler: function(){
				win.loadGrid();
			}
		}]
	}
});