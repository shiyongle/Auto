Ext.define('Ext.order.Deliver.CustProductStore', {
	extend:'Ext.data.Store',
    fields:['fid','fname','fnumber','fspec','fcharactername','fcharacterid','fcustomerid','balance'],
    proxy: {
        type: 'ajax',
        url:'GetCustproductLists.do',
        reader: {
            type: 'json',
            root: 'data'
        }
    }
});
Ext.define('DJ.order.Deliver.CustProductChooseEdit',{
	extend : 'Ext.Window',
	id:'DJ.order.Deliver.CustProductChooseEdit',
	title : "客户产品批量选择",
	width : 600,
	height : 350,
	closable : true,
	myobjparam:'',
	modal : true,
	layout:'fit',
	dockedItems: [{
        xtype: 'toolbar',
        dock: 'top',
        items: [{
            text: '确定',
            height:30,
            handler:function(){
            	var win = this.up('window'),
            		items = win.down('grid').cItems,
            		myobject=win.myobjparam.myobject;
            		store=win.myobjparam.mystore;
//            		editwind=Ext.getCmp('DJ.order.Deliver.batchCustDeliverapplyEdit').myobj;
//            		store = Ext.getCmp('DJ.order.Deliver.Deliverapplys').getStore(),
            		arr = [],records=[];
            	Ext.each(items,function(record){
            		arr.push({
						fcusproductid : record.get('fid'),
						cutpdtname : record.get('fname'),
						cutpdtnumber : record.get('fnumber'),
						fcharacter : record.get('fcharactername'),
						ftraitid : record.get('fcharacterid'),
						fspec : record.get('fspec'),
						balance : record.get('balance'),
						famount : 0,
						farrivetime : Ext.Date.add(new Date(new Date().setHours(14,0,0,0)),Ext.Date.DAY,5),
						fsupplierid:(Ext.isEmpty(myobject)||Ext.isEmpty(myobject.fsupplierid)?"":myobject.fsupplierid),
						fsuppliername:(Ext.isEmpty(myobject)||Ext.isEmpty(myobject.fsuppliername)?"":myobject.fsuppliername)
            		});
            	});
            	store.each(function(record){
            		if(record.get('fcusproductid')==''){
            			records.push(record);
            		}
            	});
            	Ext.Array.each(records,function(item){
        			store.remove(item);
        		})
            	store.add(arr);
            	console.log(store.getAt(0));
            	console.log(store.getAt(0).get('fspec'));
            	win.close();
            }
        },{
        	text:'取消',
        	height:30,
        	handler:function(){
        		this.up('window').close();
        	}
        },'',{
        	xtype:'textfield',
        	width:120,
        	emptyText:'请输入按回车查询...',
        	listeners : {
        		change : function(tf, e) {
        			var grid = this.up('window').down('grid');
        			var value = Ext.String.trim(this.getValue());
        			grid.getStore().load({
        				params:{
        					Cusfilter : '[{"type":"string","myfilterfield":"t_bd_Custproduct.fname","CompareType":"like","value":'+Ext.encode(value)+'},{"type":"string","myfilterfield":"t_bd_Custproduct.fnumber","CompareType":"like","value":'+Ext.encode(value)+'},{"type":"string","myfilterfield":"t_bd_Custproduct.fspec","CompareType":"like","value":'+Ext.encode(value)+'},{"type":"string","myfilterfield":"t_bd_Custproduct.fcharactername","CompareType":"like","value":'+Ext.encode(value)+'}]',
        					Defaultfilter : '[]',
        					Defaultmaskstring : '',
        					Merge : '',
        					Maskstring : '#0 OR #1 OR #2 OR #3'
        				}
        			})
        		}
        	} 
        }]
    }],
	initComponent:function(){
		Ext.apply(this,{
			items:[{
				xtype:'grid',
				selModel : Ext.create('Ext.selection.CheckboxModel'),
				id : 'DJ.order.Deliver.CustProductChooseGrid',
				cItems:[],
				listeners:{
					select:function(me,record){
						var cItems = this.cItems;
						var contain = false;
						Ext.Array.each(cItems,function(item){
							if(item.get('fid')==record.get('fid')){
								contain = true;
								return false;
							}
						});
						if(contain){
							return;
						}
						cItems.push(record);
					},
					deselect:function(me,record){
						var cItems = this.cItems;
						Ext.Array.each(cItems,function(item){
							if(item.get('fid')==record.get('fid')){
								Ext.Array.remove(cItems,item);
								return false;
							}
						})
					}
				},
				store:Ext.create('Ext.order.Deliver.CustProductStore',{
					autoLoad:true,
					listeners:{
						load:function(){
							var grid = Ext.getCmp('DJ.order.Deliver.CustProductChooseGrid'),
								store = grid.getStore(),
								model = grid.getSelectionModel(),
								ids = Ext.Array.pluck(Ext.Array.pluck(grid.cItems, "data"),'fid'),
								records = [];
							store.each(function(record){
								if(Ext.Array.contains(ids,record.get('fid'))){
									records.push(record);
								}
							});
							model.select(records);
						}
					}
				}),
				columns:[{
					dataIndex:'fname',
					header:'包装物名称',
					sortable:true,
					flex:1
				},{
					dataIndex:'fnumber',
					header:'包装物编号',
					sortable:true,
					flex:1
				},{
					dataIndex:'fspec',
					header:'规格',
					sortable:true,
					flex:1
				},{
					dataIndex:'fcharactername',
					header:'特性',
					sortable:true,
					flex:1
				}]
			}]
		});
		this.callParent(arguments);
	}
});
