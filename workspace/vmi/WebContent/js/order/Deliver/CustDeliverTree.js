var cTreeStore=Ext.create("DJ.System.Customer.CustomerTreeStore");
var selectpath=null;
var selectnodes=new Array();//查询条件值存放地
function sarchNode(btn) {
	var filterValue=Ext.getCmp("DJ.order.Deliver.CustDeliverTree.Input").getValue();
	var trees=Ext.getCmp("DJ.order.Deliver.CustDeliverTree");
	var cstore=trees.getStore();
	if( Ext.String.trim(filterValue).length==0)
	{return; }
	if (selectnodes.length==0)
	{
		
		var el = trees.getEl();
		el.mask("系统处理中,请稍候……");
		Ext.Ajax.request({
			url : "SearchCustomerNode.do",
			params : {
				fname : filterValue
			}, 
			success : function(response, option) {
				var obj = Ext.decode(response.responseText);
				if (obj.success == true) {
					for ( var i = 0; i < obj.total; i++) {
						selectnodes.push(obj.data[i].id);
					}
					var node1=cstore.getNodeById(selectnodes[selectnodes.length-1]).getPath('text');
					trees.expandPath(node1,'text','/',function(bSucess,oLastNode){  
						selectnodes.pop();
				        trees.getSelectionModel().select(oLastNode);  
				        trees.fireEvent('itemclick', trees,oLastNode);
				      },this);  
				
				}else
				{
					Ext.MessageBox.alert('提示',obj.msg);
					
				}
				el.unmask();
			}
		});
	}
	else{
			var node2=cstore.getNodeById(selectnodes[selectnodes.length-1]).getPath('text');
			trees.expandPath(node2,'text','/',function(bSucess,oLastNode){ 
				selectnodes.pop();
		        trees.getSelectionModel().select(oLastNode);  
		        trees.fireEvent('itemclick', trees,oLastNode);
		        
		      },this);  
		}
}

Ext.define('DJ.order.Deliver.CustDeliverTree', {
	extend : 'Ext.tree.Panel',
	id : 'DJ.order.Deliver.CustDeliverTree',
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
				
				var trees=Ext.getCmp("DJ.order.Deliver.CustDeliverTree");
				
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
			xtype : 'textfield',emptyText : '根据客户名称查找...',id:'DJ.order.Deliver.CustDeliverTree.Input',width :100,
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
				 
				  var pstore=Ext.getCmp("DJ.order.Deliver.DeliversList").getStore();
				 
				  
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
							myfilterfield : "mv.fcustomerid",
							CompareType : " like ",
							type : "string",
							value : customerid
						});
						
				  }
				  	pstore.setDefaultfilter(myfilter);
					pstore.setDefaultmaskstring(" #0 ");
						pstore.loadPage(1);
				  //pstore.load(); 
			  },
			  load:function( view, node, records, successful, eOpts )
				{
				var trees=Ext.getCmp("DJ.order.Deliver.CustDeliverTree");
				
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


