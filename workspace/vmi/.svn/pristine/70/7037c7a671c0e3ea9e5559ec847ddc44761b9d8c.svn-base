Ext.define('DJ.order.Deliver.CommonMaterialOperation',{
	extend: 'Ext.Window',
	title: '常用材料',
	modal: true,
	width: 850,
	initComponent: function(){
		var me = this,
			left = me.left = grid('getSupplierCardboardList.do'),
			right = me.right = grid('getCommonMaterialList.do'),
			combo = me.combo = Ext.widget({
					xtype : 'combobox',
					fieldLabel: '制造商',
					name: 'fsupplierid',
					valueField : 'fid',
					displayField : 'fname',
					editable:false,
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
					listeners: {
						select: function(combo,records){
							var fsupplierid = this.getValue(),
								left = me.left,
								right = me.right;
							left.doLoad(fsupplierid,left.down('textfield[itemId=query]').getValue());
							right.doLoad(fsupplierid,right.down('textfield[itemId=query]').getValue());
						},
						afterrender: function(){
							var me = combo;
							combo.getStore().load({
								callback: function(records, operation, success){
									if(success){
										records.forEach(function(record){
											if(record.get('fid')=='39gW7X9mRcWoSwsNJhU12TfGffw='){
												me.setValue(record.get('fid'));
											}
										})
									}
								}
							});
						}
					}
			});
		Ext.apply(me,{
			items: [{
				xtype: 'panel',
				frame: true,
				padding: '10 20',
				border: 0,
				items: [combo],
				height: 50
			},{
				xtype: 'panel',
				layout: {
			        type: 'hbox',
			        align: 'stretch'
			    },
				height: 450,
				items: [left,{
					width: 40,
					defaultType: 'button',
					defaults: {
						margin: '100 auto'
					},
					items: [{
						text: '常用',
						handler: function(){
							var records = me.left.getSelectionModel().getSelection(),
								fids=[],btn = this;
							if(!records.length){
								return;
							}
							btn.setDisabled(true);
							records.forEach(function(item,index){
								fids.push(item.get('fid'));
							});
							Ext.Ajax.request({
								url: 'addCommonMaterial.do',
								params: {
									fids: fids.join(','),
									fsupplierid: me.combo.getValue()
								},
								success: function(res){
									var obj = Ext.decode(res.responseText);
									if(obj.success){
										djsuccessmsg('添加成功！');
										me.left.doLoad();
										me.right.doLoad();
									}else{
										Ext.Msg.alert('错误',obj.msg);
									}
									btn.setDisabled(false);
								},
								failure: function(){
									Ext.Msg.alert('错误','添加失败，请刷新页面重试...');
								}
							});
						}
					},{
						text: '取消',
						handler: function(){
							var records = me.right.getSelectionModel().getSelection(),
								fids=[];
							if(!records.length){
								return;
							}
							records.forEach(function(item,index){
								//rightPanel的fid为commonMaterial的fid,而不是材料的fid
								fids.push(item.get('fid'));
							});
							Ext.Ajax.request({
								url: 'removeCommonMaterial.do',
								params: {
									fids: fids.join(',')
								},
								success: function(res){
									var obj = Ext.decode(res.responseText);
									if(obj.success){
										djsuccessmsg('移除成功！');
										me.left.doLoad();
										me.right.doLoad();
									}else{
										Ext.Msg.alert('错误',obj.msg);
									}
								},
								failure: function(){
									Ext.Msg.alert('错误','移除失败，请刷新页面重试...');
								}
							});
						}
					}]
				},right],
				anchor: '100% 100%'
			}],
			listeners: {
				show: function(){
					this.left.doLoad();
					this.right.doLoad();
				}
			}
		});
		this.callParent(arguments);
		function grid(url){
			var store = Ext.create('Ext.data.Store',{
				fields: ['fid','fname','flayer','ftilemodelid'],
				proxy: {
					type: 'ajax',
					url: url,
					params: {
						a: 'abc'
					},
					reader: {
						type: 'json',
						root: 'data'
					}
				}
			});
			var grid = Ext.create('Ext.grid.Panel',{
				store: store,
				columns: [{
					text:'序号',
					xtype:'rownumberer',
					width:40
				},{
					text:'材料',
					dataIndex:'fname'
				},{
					text:'层数',
					dataIndex:'flayer'
				},{
					text:'楞型',
					dataIndex:'ftilemodelid',
					flex: 1
				}],
				doLoad: function(fsupplierid,query){
					if(fsupplierid===undefined){
						fsupplierid = this.up('window').combo.getValue();
						if(!fsupplierid){
							fsupplierid = '39gW7X9mRcWoSwsNJhU12TfGffw='; //默认为东经
						}
					}
					if(query===undefined){
						query = this.down('textfield[itemId=query]').getValue();
					}
					var store = this.getStore();
					Ext.apply(store.getProxy().extraParams,{
						fsupplierid: fsupplierid,
						query: encodeURIComponent(query || '').replace(/%/g,'_')
					});
					store.loadPage(1);
				},
				selModel: Ext.create('Ext.selection.CheckboxModel'),
				tbar: [{
					xtype: 'textfield',
					width : 200,
					itemId: 'query',
					emptyText: '按材料、层数、楞型查询...',
					enableKeyEvents: true,
					margin: 5,
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
						var query = this.getValue(),
							fsupplierid = this.up('window').combo.getValue();
						this.up('grid').doLoad(fsupplierid,query);
					}
				},{
					text : '查询',
					handler : function(){
						this.previousSibling().handleSelect();
					}
				}],
				dockedItems: [{
					xtype : 'pagingtoolbar',
					store : store,
					dock : 'bottom',
					displayInfo : true
				}],
				flex: 1
			});
			return grid;
		}
	}
});