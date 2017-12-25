//客户TreeStore
Ext.define('DJ.System.DJCustTreeStore', {
	extend : 'Ext.data.TreeStore',
	fields : [ {
		name : 'id'
	}, {
		name : 'text'
	},{
		name : 'isleaf'
	},
		{ name : 'leaf',
		  convert:function(v,record){
			  return record.data.isleaf=='1'?true:false;
		  }
		}
	],
	proxy : {
		type : 'ajax',
		url : 'GetCustomerTree.do'
	},
	reader : {
		type : 'json'
	},
	autoLoad : false,
	nodeParam : 'id',
	defaultRootId : '-1',
	root: {
		id      : '-1',
		text    : '所有客户',		
		expanded: true,
		leaf: false
	}
});
//客户树列表
Ext.define('DJ.System.DJCustTree',{
	extend:'Ext.tree.Panel',
	rootVisible:true,
	useArrows:true,
	autoScroll:true,
	border:false,
	store:Ext.create('DJ.System.DJCustTreeStore'),
	dockedItems:{
		xtype:'toolbar',
		dock:'top',
		items:[{
			text:'刷新',
			handler:function(){
				this.up('treepanel').getStore().load();
			}
		},{
			xtype:'textfield',
			width:100,
			emptyText:'请填写客户名称...'
		},{
			text:'查找',
			handler:function(){
				var val = Ext.String.trim(this.prev().getValue()),
					panel = this.up('treepanel'),
					record = panel.getRootNode().findChildBy(function(item){
						if(item.data.text.indexOf(val)>-1){
							return true;
						}
					});
				panel.fireEvent('itemClick',this,record);
			}
		}]
	},
	listeners:{
		itemClick:function(view,record){
			var store = this.up('panel').up('panel').mainView.getStore();
			if(record.data.id!=-1){
				myfilter=[{
					myfilterfield : "v.fcustomerid",
					CompareType : " like ",
					type : "string",
					value : record.data.id
				}];
			}else{//不走缓存
				myfilter=[{
					myfilterfield : "v.fcustomerid",
					CompareType : " != ",
					type : "string",
					value : -1
				}];
			}
			store.setDefaultfilter(myfilter);
			store.setDefaultmaskstring(" #0 ");
			store.loadPage(1);
		}
	}
});
//panel
Ext.define('DJ.System.BindCustTreePanel',{
	extend:'Ext.panel.Panel',
	autoScroll:true,
	border:false,
	layout:'border',
	closable:true,
	width:1000,
	height:500,
	initComponent:function(){
		Ext.apply(this,{
			items:[{
				region:'center',
				items:this.createMainView(),
				layout:'fit'
			},{
				title:'客户列表',
				region:'west',
				collapsible:true,
				width:180,
				layout:'fit',
				items:[Ext.create('DJ.System.DJCustTree')]
			}]
		});
		this.callParent(arguments);
	},
	createMainView:function(){
		return this.mainView = Ext.create(this.mainView);
	}
});