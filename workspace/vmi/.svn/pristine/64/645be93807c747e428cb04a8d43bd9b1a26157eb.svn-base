var cTreeStore=Ext.create("DJ.System.Customer.CustomerTreeStore");
var selectpath=null;
var selectnodes=new Array();//查询条件值存放地
function sarchNode(btn) {
	var filterValue=Ext.getCmp("DJ.System.product.CustproductTreeBaseInfo.Input").getValue();
	var trees=Ext.getCmp("DJ.System.product.CustproductTreeBaseInfo");
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
		trees.getSelectionModel().select(selectnodes[selectnodes.length-1]);  
	        trees.fireEvent('itemclick', trees,trees.getSelectionModel().getSelection()[0]);
	        selectnodes.pop();
		el.unmask();
	}
	else{
			trees.getSelectionModel().select(selectnodes[selectnodes.length-1]);  
	       trees.fireEvent('itemclick',trees,trees.getSelectionModel().getSelection()[0]);
	       selectnodes.pop();
		}
}

Ext.define('DJ.System.product.CustproductTreeBaseInfo', {
	extend : 'Ext.tree.Panel',
	id : 'DJ.System.product.CustproductTreeBaseInfo',
	rootVisible : true,
	useArrows : true,
	autoScroll : true,
	border : false,
	store : cTreeStore,
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		items : [{
			text : '刷新',
		
			handler : function(btn) {
				
				var trees=Ext.getCmp("DJ.System.product.CustproductTreeBaseInfo");
				
			var record=trees.getSelectionModel().getSelection();
			if(record.length == 0)
			{
				selectpath=null;
			}
			else
				{
			 selectpath=record[0].getPath('text');
				}
			cTreeStore.load();
		}
		},{
			xtype : 'textfield',emptyText : '根据客户名称查找...',id:'DJ.System.product.CustproductTreeBaseInfo.Input',width :100,
			listeners:
				{
					change:function( fild, newValue, oldValue, eOpts )
					{
						selectnodes=new Array();
					}
				}
		},{
			text : '查找',
			handler : sarchNode
		}
		]
		}],
		  listeners:{
			  itemclick:function(view, record, item, index, e, eOpts )
			  {
				  var customerid='';
				  if(record.data.id!='-1')
				  {
					  customerid=record.data.id;
				  }
				 
				  var pstore;
				  if(Ext.getCmp("DJ.System.product.CustproductList")==undefined){
				  	pstore=Ext.getCmp("DJ.System.product.CustproductCustList").getStore();
				  }else{
				  	pstore=Ext.getCmp("DJ.System.product.CustproductList").getStore();
				  }
				  
//				  pstore.on("beforeload", function(store, options) {
//	    				Ext.apply(store.proxy.extraParams, 
//	    				{
//	    					fcustomerid:customerid});
//				  	});
				 // pstore.load(); 
				  var myfilter = [];
				  if(customerid==""){
				  	customerid = "1";
			  		myfilter.push({
						myfilterfield : "1",
						CompareType : " like ",
						type : "string",
						value : customerid
					});
				  }else{
					  
						myfilter.push({
							myfilterfield : "fcustomerid",
							CompareType : " like ",
							type : "string",
							value : customerid
						});
				  }
						pstore.setDefaultfilter(myfilter);
						pstore.setDefaultmaskstring(" #0 ");
						pstore.loadPage(1);
//				  pstore.load(); 
			  },
			  load:function( view, node, records, successful, eOpts )
				{
				var trees=Ext.getCmp("DJ.System.product.CustproductTreeBaseInfo");
				
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


