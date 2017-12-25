var cTreeStoreCustproductTree=Ext.create("DJ.System.Customer.CustomerTreeStore");
var selectpathCustproductTree=null;
var selectnodesCustproductTree=new Array();//查询条件值存放地


var selectpath=null;//选择节点路径
var selectnodes=new Array();//查询条件值存放地
function sarchNodeCustproductTree(btn) {
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
			if(selectnodes.length!=0){
				trees.getSelectionModel().select(selectnodes[selectnodes.length-1]);  
		        trees.fireEvent('itemclick', trees,trees.getSelectionModel().getSelection()[0]);
		        selectnodes.pop();
			}else{
				Ext.Msg.alert('提示','没找到该客户！');
			}
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

Ext.define('DJ.System.product.CustproductTree', {
	extend : 'Ext.tree.Panel',
	id : 'DJ.System.product.CustproductTree',
	rootVisible : true,
	useArrows : true,
	autoScroll : true,
	border : false,
	store : cTreeStoreCustproductTree,
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		items : [{
			text : '刷新',
		
			handler : function(btn) {
				
				var trees=Ext.getCmp("DJ.System.product.CustproductTree");
				
			var record=trees.getSelectionModel().getSelection();
			if(record.length == 0)
			{
				selectpathCustproductTree=null;
			}
			else
				{
			 selectpathCustproductTree=record[0].getPath('text');
				}
			cTreeStoreCustproductTree.load();
		}
		},{
			xtype : 'textfield',emptyText : '根据客户名称查找...',id:'DJ.System.product.CustproductTree.Input',width :100,enableKeyEvents : true,
			listeners:
				{
					change:function( fild, newValue, oldValue, eOpts )
					{
						selectnodesCustproductTree=new Array();
					},
					keypress:function( me, e, eOpts ){
							if(e.getKey()==13){
								me.nextSibling('[text=查找]').handler();
							}
						}
				}
		},{
			text : '查找',
			handler : sarchNodeCustproductTree
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
				  
				  pstore=Ext.getCmp("DJ.System.product.CustproductCustTreeList").getStore();
				  
//				  if (Ext.getCmp("DJ.System.product.CustproductList") == undefined) {
				// pstore=Ext.getCmp("DJ.System.product.CustproductCustTreeList").getStore();
				pstore = Ext
						.getCmp("DJ.System.product.CustproductCustTreeList")
						.getStore();

				Ext.getCmp("DJ.System.product.CustproductCustTreeList").customerId = customerid;
				Ext.getCmp("DJ.System.product.CustproductCustTreeList").pageNo = 0;
				// pstore.setDefaultmaskstring(" #0 ");
				pstore.load({

					params : {
						fcustomerid : customerid,
						id : -1
					}
				});

//			} 
			
//			else {
//				pstore = Ext.getCmp("DJ.System.product.CustproductList")
//						.getStore();
//
//				var myfilter = [];
//				if (customerid == "") {
//					customerid = "1";
//					myfilter.push({
//						myfilterfield : "1",
//						CompareType : " like ",
//						type : "string",
//						value : customerid
//					});
//				} else {
//
//					myfilter.push({
//						myfilterfield : "fcustomerid",
//						CompareType : " like ",
//						type : "string",
//						value : customerid
//					});
//				}
//				
//				
//				 pstore.loadPage(1);
//			}
				  
// pstore.on("beforeload", function(store, options) {
//	    				Ext.apply(store.proxy.extraParams, 
//	    				{
//	    					fcustomerid:customerid});
//				  	});
				 // pstore.load(); 
//				
				  

				  if (customerid == "") {
				customerid = "-1";
			}


// pstore.load();
			  },
			  load:function( view, node, records, successful, eOpts )
				{
					
//				var trees=Ext.getCmp("DJ.System.product.CustproductTree");
//				
//				if(selectpathCustproductTree!=null){
//				trees.expandPath(selectpathCustproductTree,'text','/',function(bSucess,oLastNode){
//		            trees.getSelectionModel().select(oLastNode);  
//		            trees.fireEvent('itemclick', trees,oLastNode);
//		          },this);  
//				}else
//				{
//					  trees.getSelectionModel().select(node); 
//					  trees.fireEvent('itemclick', trees,node);
//				}
  }  
		  }
});


