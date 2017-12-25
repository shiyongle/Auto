Ext.define('DJ.order.Deliver.OrderAppraiseListPanel',{
	id:'DJ.order.Deliver.OrderAppraiseListPanel',
	extend:'Ext.panel.Panel',
	title:"订单评价表",
	autoScroll:false,
	border:false,
	layout:'border',
	closable:true,
//	width:1000,
//	height:500,
	initComponent:function(){
		Ext.apply(this,{
			items:[

				Ext.create('DJ.order.Deliver.OrderAppraiseListChart'),
				{
				region:'center',
				items:[Ext.create('DJ.order.Deliver.OrderAppraiseList')],
				layout:'fit'
				}
			]
		});
		this.callParent(arguments);
	}
});

Ext.ns("DJ.order.Deliver.OrderAppraiseListPanel");

DJ.order.Deliver.OrderAppraiseListPanel.forderappaiseStore = Ext.create('Ext.data.Store', {
	fields : [{name:'fsuppliername'},{name:'fsupplierid'},{name:'faveragedeliver', type : "float"},{name:'faveragequality', type : "float"},{name:'faverageservice', type : "float"},{name:'faveragemultiple', type : "float"}],
	remoteFilter : true,
	proxy : {
		type : 'ajax',
		url : 'GetAverageorderParisesOfCurMothData.do',
		reader : {
			type : 'json',
			root : "data"
		}
	
	}
//	,
//	autoLoad : true
});
Ext.define("DJ.order.Deliver.OrderAppraiseListChart",
		{
	extend:'Ext.panel.Panel',
	id:'DJ.order.Deliver.OrderAppraiseListChart',
//	xtype : 'panel',
	region : 'west',
	width : '35%',
	frame : true,
	title : '我对各制造商的订单评价情况',
	layout: 'fit',
	split : true,
//	collapsed : true,
	collapsible : true,
	items : [{
		xtype : 'chart',
		store :	DJ.order.Deliver.OrderAppraiseListPanel.forderappaiseStore,
		axes: [{
			type: 'Category',
			position: 'bottom',
//			minimum : 0,//数轴最小值
//			maximum : 6,//数轴最大值
			fields: ['fsuppliername'],//同时展示2个数据
			title: '制造商',
			label: {
//			 rotate: {
//                        degrees: 315//315度显示标签值
//                    }
                renderer: function(v) {
               var store=DJ.order.Deliver.OrderAppraiseListPanel.forderappaiseStore;
                	if(v.length>5&store.getCount()>3){
                    return Ext.String.ellipsis(v, 6, false);
                	}
                	return v
                	
                }
            }
			}, {
			type: 'Numeric',
			decimals : 1,
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
//			gutter : 5,//配置条形图矩形之间的间距
			groupGutter : 5,//配置条形图相邻两组矩形之间的距离是矩形宽度的10%
//			column : true,//配置条形图的模式（true垂直false水平）
//			xPadding : 5,//配置左右坐标轴距图形的空隙
//			yPadding : 0,//配置上下坐标轴距图形的空隙
			axis: 'left',
			xField: 'fsuppliername',//左侧坐标轴字段
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
			  width: 80,
			  height: 60,
			  renderer: function(storeItem, item) {
			    this.setTitle('制造商名称' );
			    this.update ( storeItem.get('fsuppliername'));
			  }	
		}
		,
		 listeners: {
                'itemclick': function(item) {
                     var store=Ext.getCmp("DJ.order.Deliver.OrderAppraiseList").getStore();
                     var filter=store.getDefaultfilter();
                     var currentfilter=new Array();
                     currentfilter.push({
                     		myfilterfield : "o.fsupplierid",
								CompareType : " = ",
								type : "string",
								value : item.storeItem.get("fsupplierid")
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
//                'itemmouseup': function() {
//                        Ext.Msg.alert('itemmouseup ');
//                }
        }
		
		}]
	}]

})