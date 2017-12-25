Ext.define('DJ.order.Deliver.OrderAppraiseSupplierListPanel',{
	id:'DJ.order.Deliver.OrderAppraiseSupplierListPanel',
	extend:'Ext.panel.Panel',
	title:"订单评价表",
	autoScroll:false,
	border:false,
	layout:'border',
	closable:true,
	initComponent:function(){
		Ext.apply(this,{
			items:[
				Ext.getCmp('DJ.order.Deliver.OrderAppraiseSupplierListChart')||Ext.create('DJ.order.Deliver.OrderAppraiseSupplierListChart'),
				{
				region:'center',
				items:[Ext.getCmp('DJ.order.Deliver.OrderAppraiseSupplierList') || Ext.create('DJ.order.Deliver.OrderAppraiseSupplierList')],
				layout:'fit'
			}
			]
		});
		this.callParent(arguments);
	}
});

Ext.ns("DJ.order.Deliver.OrderAppraiseSupplierListPanel");

DJ.order.Deliver.OrderAppraiseSupplierListPanel.forderappaiseStore = Ext.create('Ext.data.Store', {
	fields : [{name:'fcustomername'
//	,convert: function(v,record){if(v.length>7){return v.substr(0,7)+"<br\/>"+v.substr(7);}return  v}
	},{name:'fcustomerid'},{name:'faveragedeliver', type : "float"},{name:'faveragequality', type : "float"},{name:'faverageservice', type : "float"},{name:'faveragemultiple', type : "float"}],
	remoteFilter : true,
	proxy : {
		type : 'ajax',
		url : 'GetAverageorderParisesSupplierOfCurMothData.do',
		reader : {
			type : 'json',
			root : "data"
		}
	}

//	,autoLoad : true
});
Ext.define("DJ.order.Deliver.OrderAppraiseSupplierListChart",
		{
	extend:'Ext.panel.Panel',
	id:'DJ.order.Deliver.OrderAppraiseSupplierListChart',
//	xtype : 'panel',
	region : 'west',
	width : '38%',
	frame : true,
	title : '客户对我的评价情况',
	layout: 'fit',
	split : true,
	collapsible : true,
	items : [{
		xtype : 'chart',
		store :	DJ.order.Deliver.OrderAppraiseSupplierListPanel.forderappaiseStore,
		axes: [{
			type: 'Category',
			position: 'bottom',
//			minimum : 0,//数轴最小值
//			maximum : 6,//数轴最大值
			fields: ['fcustomername'],//同时展示2个数据
			title: '客户',
			label: {
//			 rotate: {
//                        degrees: 315//315度显示标签值
//                    }
                renderer: function(v) {
               var store=DJ.order.Deliver.OrderAppraiseSupplierListPanel.forderappaiseStore;
                	if(v.length>5&store.getCount()>3){
                    return Ext.String.ellipsis(v, 6, false);
                	}
                	return v
                	
                }
            }
			}, {
			type: 'Numeric',
			decimals:1,
			minimum : 0,//数轴最小值
			maximum :6 ,//数轴最大值
			position: 'left',
			fields: ['faveragedeliver','faveragequality','faverageservice','faveragemultiple'],
			title: '平均分值',
			grid:{opacity:0
			}
		}],
		legend : {
			position : 'bottom' //图例位置
		},
		series : [{
		    type: 'column',
//			gutter : 50,//配置条形图矩形之间的间距
			groupGutter : 5,//配置条形图相邻两组矩形之间的距离是矩形宽度的10%
//			column : true,//配置条形图的模式（true垂直false水平）
//			xPadding : 5,//配置左右坐标轴距图形的空隙
//			yPadding : 0,//配置上下坐标轴距图形的空隙
			axis: 'left',
			xField: 'fcustomername',//左侧坐标轴字段
			yField:  ['faveragedeliver','faveragequality','faverageservice','faveragemultiple'],//底部坐标轴字段
			title : ['交期满意度','质量满意度','服务满意度','数量准确度']
			,//配置图例字段说明
			label : {
				field :  ['faveragedeliver','faveragequality','faverageservice','faveragemultiple'],//标签字段名
				display : 'outside',//标签显现方式
////				font : '18px "Lucida Grande"'
////					,//字体
				renderer : function(v){//自定义标签渲染函数
					if(v==""|v==null)
					{
						return v;
					}
					if(v.toString().indexOf(".")>0){
					return Ext.Number.toFixed(v,1);
					} 
					return v;
				}
			}
		,tips : {
			
			trackMouse: true,
			  width: 100,
			  height: 60,
			  renderer: function(storeItem, item) {
			    this.setTitle('客户名称' );
			    this.update ( storeItem.get('fcustomername'));
			  }	
		}
		,
		 listeners: {
                'itemclick': function(item) {
                     var store=Ext.getCmp("DJ.order.Deliver.OrderAppraiseSupplierList").getStore();
                      var filter=store.getDefaultfilter();
                     var currentfilter=new Array();
                     currentfilter.push({
                     		myfilterfield : "o.fcustomerid",
								CompareType : " like ",
								type : "string",
								value : item.storeItem.get("fcustomerid")
                     });
                     var maskString=" #0 ";
                    if(!Ext.isEmpty(filter)&&filter.length>1){
         				currentfilter.push(filter[filter.length-2]);
         				currentfilter.push(filter[filter.length-1]);
         				maskString=maskString+" and #1 and #2 "
                    }
                    store.setDefaultfilter(currentfilter);
					store.setDefaultmaskstring(maskString);
					store.load();
                }
        }
		
		}]
	}]

})