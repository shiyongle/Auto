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
	nodeParam : 'id',
	defaultRootId : '-1',
	root: {
		id      : '-1',
		text    : '所有制造商',		
		expanded: true,
		leaf: false
	}
});
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
				selectnodes.push(nodes[i].raw.text);
			}
				
		}
		trees.expandPath('/所有制造商/'+selectnodes[selectnodes.length-1],'text','/',function(bSucess,oLastNode){  
			selectnodes.pop();
	        trees.getSelectionModel().select(oLastNode);  
	        trees.fireEvent('itemclick', trees,oLastNode);
	      },this);
		el.unmask();
	}
	else{
		trees.expandPath('/所有制造商/'+selectnodes[selectnodes.length-1],'text','/',function(bSucess,oLastNode){ 
			selectnodes.pop();
	        trees.getSelectionModel().select(oLastNode);  
	        trees.fireEvent('itemclick', trees,oLastNode);
	        
	      },this);  
		}
	this.prev().focus();
	

}
Ext.define('DJ.cardboard.system.ProducePlanSupplierTree', {
	extend : 'Ext.tree.Panel',
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
					width :100,
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
						grid = this.up('panel').up('panel').down('grid');
						grid.doLoad(supplierid);
					}
				},
				load:function( view, node, records, successful, eOpts )
				{
					var trees=me;
					this.fireEvent('itemclick',this,this.getRootNode().getChildAt(0));
					if(selectpath!=null){
						trees.expandPath(selectpath,'text','/',function(bSucess,oLastNode){  
							trees.getSelectionModel().select(oLastNode);  
							trees.fireEvent('itemclick', trees,oLastNode);
						},this);  
					}else
					{
						trees.getSelectionModel().select(node); 
						trees.fireEvent('itemclick', trees,node);
					}
				}
			}
		});
		me.callParent();
	},
});
