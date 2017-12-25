Ext.define('DJ.System.MainTreePanel',{
		extend: 'Ext.panel.Panel',
		layout: 'border',
		closable: true,
		grid: '',						// grid是必配项
		childItemConfig: [],			// 主面板添加其它面板，如 [{xtype:'panel',region:'east'}]
		westPanelConfig: {},			// 对树面板添加额外配置，如{width:200}
		treeTitle: '客户',					// 树列表名称，如制造商，客户等
		treeUrl: 'GetCustomerTree.do',	// 菜单树的url
		initComponent: function(){
			var me = this,
				centerPanel,westPanel;
			centerPanel = me.createCenterPanel();
			westPanel = me.createWestPanel();
			Ext.apply(me,{
				items: [centerPanel,westPanel].concat(me.childItemConfig)
			});
			this.callParent(arguments);
		},
		createWestPanel: function(){
			var me = this;
			var treeStore = Ext.create('Ext.data.TreeStore',{
				fields : [{
					name: 'id'
				},{
					name: 'text'
				},{
					name: 'isleaf'
				},{
					name: 'leaf',
					convert: function(v, record){
					    return record.data.isleaf=='1' ? true : false;
					}}
				],
				proxy: {
					type: 'ajax',
					url: me.treeUrl,
					timeout: 60000,
					extraParams: {
						fname: '----1'
					}
				},
				reader: {
					type : 'json'
				},
				autoLoad: false,
				nodeParam: 'id',
				defaultRootId : '-1',
				root: {
					id: '-1',
					text: '所有'+me.treeTitle,		
					expanded: true,
					leaf: false
				}
			});
			
			me.westPanel = Ext.create('Ext.tree.Panel',Ext.apply({
				region: 'west',
				title: me.treeTitle+'列表',
				store: treeStore,
				width: 200,
				collapsible: true,
				rootVisible: true,
				useArrows: true,
				listeners: {
					itemclick: function(tree,record,item,index,e){
						var store = me.centerPanel.getStore();
						var fcustomerid = record.get('id');
						if(fcustomerid==-1){
							store.getProxy().extraParams.fcustomerid = '';
						}else{
							store.getProxy().extraParams.fcustomerid = fcustomerid;
						}
						store.loadPage(1);
						// me.onItemClick();
					}
				},
				tbar: ['',{
					xtype: 'textfield',
					width: 120,
					emptyText: '请输入至少两个字符...',
					enableKeyEvents: true,
					listeners: {
						keyup: function(field,e){
							if (e.getKey() == e.ENTER) {
		                        field.nextSibling().handler();
		                    }
						}
					}
				},{
					text: '查询',
					handler: function(){
						var val = Ext.String.trim(this.prev().getValue());
						if(val && val.length>1){
							var store = me.westPanel.getStore();
							store.getProxy().extraParams = {
								fname: Ext.encodeUnicodeString(val)
							};
							store.load();
						}
					}
				}]
			},me.westPanelConfig));
			return me.westPanel;
		},
		createCenterPanel: function(){
			var me = this;
			me.centerPanel = Ext.create(me.grid,{
				region: 'center'
			});
			return me.centerPanel;
		}
	});