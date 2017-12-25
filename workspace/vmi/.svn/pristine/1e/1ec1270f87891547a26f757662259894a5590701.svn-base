Ext.ns("DJ.order.Deliver");

DJ.order.Deliver.productStore = Ext.create('Ext.data.Store', {
	fields : [{name:'fcreatetime'},{name:'fplanamount', type : "int"},{name:'fcreatetimes'}],
	          remoteFilter : true,
	proxy : {
		type : 'ajax',
		url : 'getPlanamountBycustproduct.do',
		reader : {
			type : 'json',
			root : "data"
		}
	}
});
Ext.define("DJ.order.Deliver.MystockListChart",
		{
	extend:'Ext.panel.Panel',
	id:'DJ.order.Deliver.MystockListChart',
//	xtype : 'panel',
	region : 'east',
	width : '48%',
	frame : true,
	title : '最近7次备货量变化',
	layout: 'fit',
	split : true,
	items : [{
		xtype : 'chart',
		store : DJ.order.Deliver.productStore,
		axes: [{
			type: 'Category',
			position: 'bottom',
//			minimum : 0,//数轴最小值
//			maximum : 60,//数轴最大值
			fields: ['fcreatetime'],//同时展示2个数据
			title: '时间'
		}, {
			type: 'Numeric',
			decimals : 0,
//			minimum : 0,//数轴最小值
//			maximum : ,//数轴最大值
			position: 'left',
			fields: 'fplanamount',
			title: '计划数量'
		}],
//		legend : {
//			position : 'bottom' //图例位置
//		},
		series : [{
		    type: 'bar',
//			gutter : 5,//配置条形图矩形之间的间距
//			groupGutter : 5,//配置条形图相邻两组矩形之间的距离是矩形宽度的10%
			column : true,//配置条形图的模式（true垂直false水平）
//			xPadding : 5,//配置左右坐标轴距图形的空隙
			yPadding : 0,//配置上下坐标轴距图形的空隙
			axis: 'left',
			xField: 'fcreatetime',//左侧坐标轴字段
			yField: 'fplanamount',//底部坐标轴字段
//			title : ['人员比例','增长速度'],//配置图例字段说明
			label : {
				field : ['fplanamount'],//标签字段名
				display : 'outside'
					,//标签显现方式
//				font : '18px "Lucida Grande"'
//					,//字体
				renderer : function(v){//自定义标签渲染函数
					return v;
				}
			}
		,tips : {
			
			trackMouse: true,
			  width: 80,
			  height: 60,
			  renderer: function(storeItem, item) {
			    this.setTitle(storeItem.get('fplanamount') );
			    this.update ( storeItem.get('fcreatetimes'));
			  }
			
		}}]
	}]

})