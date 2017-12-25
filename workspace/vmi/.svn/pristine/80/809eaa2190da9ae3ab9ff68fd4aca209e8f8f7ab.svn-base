function converInteger(v, record){
    return record.data.isleaf=='1'?true : false;
    
}
var store = Ext.create('Ext.data.TreeStore', {
//	id : 'DJ.cardboard.system.SupplierTreeStor',
	fields : [ {
		name : 'id'
	}, {
		name : 'text'
	},{
		name : 'isleaf'
	},
		{ name : 'leaf',convert:converInteger}
	],
	proxy : {
		type : 'ajax',
		url : 'getSupplierTree.do'
	},
	reader : {
		type : 'json'
	},
//	autoLoad : true,
	nodeParam : 'id',
	defaultRootId : '-1',
	root: {
		id      : '-1',
		text    : '所有制造商',		
		expanded: true,
		leaf: false
	}
});
//var cTreeStore=Ext.create("DJ.cardboard.system.SupplierTreeStore");
var selectpath=null;//选择节点路径
var selectnodes=new Array();//查询条件值存放地
function sarchNode(btn) {
	var filterValue=this.prev().getValue();
	var trees=this.up('treepanel');
	var cstore=trees.getStore();
	if( Ext.String.trim(filterValue).length==0)
	{return; }
	if (selectnodes.length==0)
	{
		var el = trees.getEl();
		el.mask("系统处理中,请稍候……");
		var nodes = trees.getStore().tree.root.childNodes;
		for ( var i = nodes.length-1; i >= 0; i--) {
			if(new RegExp(filterValue).test(nodes[i].raw.text)){
				selectnodes.push(i+1);//nodes[i].raw.text);
			}
				
		}
//		trees.expandPath('/所有制造商/'+selectnodes[selectnodes.length-1],'text','/',function(bSucess,oLastNode){  
//			selectnodes.pop();
//	        trees.getSelectionModel().select(oLastNode);  
//	        trees.fireEvent('itemclick', trees,oLastNode);
//	      },this);
			
	        trees.getSelectionModel().select(selectnodes[selectnodes.length-1]);  
	        trees.fireEvent('itemclick', trees,trees.getSelectionModel().getSelection()[0]);
	        selectnodes.pop();
		el.unmask();
	}
	else{
//		trees.expandPath('/所有制造商/'+selectnodes[selectnodes.length-1],'text','/',function(bSucess,oLastNode){ 
//			selectnodes.pop();
//	        trees.getSelectionModel().select(oLastNode);  
//	        trees.fireEvent('itemclick', trees,oLastNode);
//	        
//	      },this);  
		  trees.getSelectionModel().select(selectnodes[selectnodes.length-1]);  
	       trees.fireEvent('itemclick',trees,trees.getSelectionModel().getSelection()[0]);
	       selectnodes.pop();
		}
	this.prev().focus();
	

}
Ext.define('DJ.cardboard.system.SupplierTree', {
	extend : 'Ext.tree.Panel',
//	id : 'DJ.cardboard.system.SupplierTree',
	rootVisible : true,
	useArrows : true,
	autoScroll : true,
	border : false,
	containerScroll:true,
	store : store,
	initComponent: function(){
		var me = this;
		Ext.apply(me,{
			dockedItems : [ {
				xtype : 'toolbar',
				dock : 'top',
				items : [{
					text : '刷新',
					handler : function(btn) {			
						var trees=me;	
						var record=trees.getSelectionModel().getSelection();
						if(record.length == 0)
						{
							selectpath=null;
						}
						else
						{
							selectpath=record[0].getPath('text');
						}
						trees.getStore().load();
					}
				},
				{
					xtype : 'textfield',
					emptyText : '根据制造商名称查找...',
					id: me.id+'.Input',
					width :140,
					enableKeyEvents : true,
					listeners:
					{
						change:function( fild, newValue, oldValue, eOpts )
						{
							selectnodes=new Array();
						},
						keypress:function( me, e, eOpts ){
							if(e.getKey()==13){
								me.nextSibling('[text=查找]').handler();
							}
						}
					}
				
				},{
					text : '查找',
					handler : sarchNode
				}]
			}],
			listeners:{
				itemclick:function(view, record, item, index, e, eOpts )
				{
					var supplierid='';
					if(record.data.id!='-1')
					{
						supplierid=record.data.id;
					}
					var pstore = this.up('panel').up('panel').down('grid').getStore();
//					var pstore=Ext.getCmp("DJ.cardboard.system.CardboardList").getStore();
					pstore.setDefaultfilter([{
						myfilterfield : "fsupplierid",
						CompareType : "like",
						type : "string",
						value : supplierid
					}]);
					pstore.setDefaultmaskstring(" #0 ");
					pstore.loadPage(1);
				}
		,
				load:function( view, node, records, successful, eOpts )
				{
					var trees=me;
					
					if(selectpath!=null){
						trees.expandPath(selectpath,'text','/',function(bSucess,oLastNode){  
							trees.getSelectionModel().select(oLastNode);  
//							trees.fireEvent('itemclick', trees,oLastNode);
						},this);  
					}else
					{
						trees.getSelectionModel().select(node); 
						if(records.length==1){
							trees.getSelectionModel().select(records[0]);//只有一个制造商时默认选中
						}
//						trees.fireEvent('itemclick', trees,node);
					}
				}  
			}
		});
		me.callParent();
	},
});
