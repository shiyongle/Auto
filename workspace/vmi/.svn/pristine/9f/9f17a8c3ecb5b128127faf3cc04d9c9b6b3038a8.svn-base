Ext.define('DJ.order.saleOrder.AddProductplanList',{
	id:'DJ.order.saleOrder.AddProductplanList',
	extend : 'Ext.c.GridPanel',
//	title:'快速增加出库产品',
	closeAction:'destroy',
	url : 'GetMyDeliveryListMV.do',
	selModel : {selType:'checkboxmodel'},
	onload:function(){
		var me = this;
		this.query('toolbar[dock=top] button').forEach(function(button){
			button.hide();
		});
		this.down('toolbar[dock=top]').add({
			text:'确定',
			handler:function(){
				var store = Ext.getCmp('DJ.order.saleOrder.BatchRealAmountEdit').store;
				var record = me.getSelectionModel().getSelection();
				if(record.length<1){
					Ext.Msg.alert('提示','请选择数据！');
					return;
				}
				record.forEach(function(re){
					store.data.items.forEach(function(s){
						if(re.get('fid')==s.get('fid')){
							record.pop(re);
						}
					})
				})
				store.loadRecords(record,{addRecords : true});
			}
		},{
			text:'取消',
			handler:function(){
				me.up('window').close();
			}
		},{
			xtype:'textfield',
			emptyText:'请输入按回车查询...',
			listeners:{
				render:function(me){
					Ext.tip.QuickTipManager.register({
					    target: me.id,
					    text: '允许输入包装物名称、纸箱规格、下料规格快速查询'
					});
				},
				specialkey:function(me, e, eOpts ){
					if(e.getKey()==13){
						var grid = me.up('grid');
						grid.store.setDefaultfilter([{
						myfilterfield : "_productname",
						CompareType : "like",
						type : "string",
						value : me.getValue()
					},{
						myfilterfield : "_spec",
						CompareType : "like",
						type : "string",
						value : me.getValue().replace(/\*/g,'X')
					},{
						myfilterfield : "_mspec",
						CompareType : "like",
						type : "string",
						value : me.getValue().replace(/\*/g,'X')
					},{
						myfilterfield : "fordernumber",
						CompareType : "like",
						type : "string",
						value : me.getValue()
					}]);
	    			grid.store.setDefaultmaskstring(" #0 or #1 or #2 or #3");
	    			grid.store.loadPage(1);
					}
				}
			}
		})
	},
	listeners:{
		itemdblclick:function(me, record, item, index, e, eOpts){
//			me.up('grid').down('button[text=确定]').handler();
		}
	},
	fields:[{
		name : 'fid'
	}, {
		name : 'fordernumber'
	}, {
		name : 'fnumber'
	}, {
		name : 'productname'
	}, {
		name : 'faddress'
	}, {
		name : 'fcreatorid'
	}, {
		name : 'fupdateuserid'
	}, {
		name : 'fcustomerid'
	}, {
		name : 'fcusproductid'
	}, {
		name : 'fcustname'
	}, {
		name : 'cutpdtname'
	},{
		name : 'ftype'
	}, {
		name : 'flinkman'
	}, {
		name : 'flinkphone'
	}, {
		name : 'famount'
	}, {
		name : 'fdescription'
	}, {
		name : 'farrivetime'
	}, {
		name : 'fcreator'
	}, {
		name : 'flastupdater'
	}, {
		name : 'fcreatetime'
	}, {
		name : 'fupdatetime'
	}, {
		name : 'fordered'
	}, {
		name : 'fordermanid'
	}, {
		name : 'fordertime'
	}, {
		name : 'forderman'
	}, {
		name : 'fsaleorderid'
	}, {
		name : 'forderentryid'
	}, {
		name : 'fimportEAS'
	}, {
		name : 'faudittime'
	}, {
		name : 'fauditor'
	},  {
		name : 'faudited'
	}, {
		name : 'fouted'
	}, {
		name : 'fmatched'
	}, {
		name : 'fassembleQty'
	}, {
		name : 'foutQty'
	},'fpcmordernumber','cfspec','pfnumber','fstate','fouttime','fdelivertype','fboxtype','fmaterial','fbalanceqty','sname'],
	columns:[  {
		'header' : '制造商订单号',
		width : 100,
		'dataIndex' : 'fordernumber',
		sortable : true,
		flex:1
	},
	{
		'header' : '包装物名称',
		width : 170,
		'dataIndex' : 'productname',
		sortable : true,
		flex:1
	}, {
		'header' : '纸箱规格',
		'dataIndex' : 'cfspec',
		sortable : true,
		flex:1
	}, {
		'header' : '下料规格',
		'dataIndex' : 'fmaterial',
		sortable : true,
		flex:1
	},{
		'header' : '库存数量',
		'dataIndex' : 'fbalanceqty',
		flex:1,
		hidden:true
	}
	]
})