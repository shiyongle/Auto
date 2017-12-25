Ext.ns("DJ.System.product");

DJ.System.product.CustPdtMainCustListColumnStore = Ext.create(Ext.data.Store, {
			fields : [{
				name : 'createcount',
				type : "int"
			}, {
				name : 'updatecount',
				type : "int"
			},{
				name : 'xaxisTip',
				type : "string"
					}],

			proxy : {
				type : "ajax",
				url : 'selectCreateAndUpdateCountForColumn.do',
				reader : {
					type : "json",
					root : "data"
				}
			},
			autoLoad : true,

			listeners : {

				load : function(store, records, successful, eOpts) {}
				
			}		
});

DJ.System.product.CustPdtMainCustListColumn = Ext.create(Ext.chart.Chart,{
	store : DJ.System.product.CustPdtMainCustListColumnStore,
	axes: [{
					type: 'Numeric',
					position: 'left',
					fields: ['createcount','updatecount'],//同时展示2个数据
					title: '数量',
					minimum : 0//数轴最小值
					,
					decimals : 0
				}, {
					type: 'Category',
					position: 'bottom',
					fields: ['xaxisTip']
//					,
//					title: '项目'
				}],legend : {
					position : 'left' //图例位置
				},
				series : [{
				    type: 'column',
					axis: 'left',
					xField: 'xaxisTip',//x轴字段
					yField: ['createcount','updatecount'],//y轴字段
					title : ['产品创建数量','产品修改数量'],//配置图例字段说明
					label : {
						field : ['createcount','updatecount'],//标签字段名
						display : 'insideEnd',//标签显现方式
						font : '18px "Lucida Grande"'
						
					}
				}]
});


Ext.define('DJ.System.product.CustPdtMainCustList', {
	extend : 'Ext.panel.Panel',
	id : 'DJ.System.product.CustPdtMainCustList',
	
	requires : ['DJ.System.product.CustproductCustTreeList','DJ.System.product.CustproductCustAccessoryList'],
	
	autoScroll : true,
	border : false,
	layout : {type : 'border',
		regionWeights : {
		
			north : -20,
			south : -10,
			center : 0,
			west : 10,
			east : 20
					
		}
		
	
	},
	title:'我的产品',
	closable:true,
//	width:1000,
//	height:500,
	
//	layout:'fit',
	
	defaults : {
		collapsible : true,
		split : true
	},
	items:
			[{
				split : false,
				region : 'center',
				collapsible : false,
				items : [ {xtype : "custproductcusttreelist"
//				,
//					padding : '0 15 0 0'
				}],
				layout:'fit'
			},
			{
				title : '客户列表',
				region : 'west',
				collapsed: true,
				width : 180,
				layout:'fit',
				items : [ Ext.create("DJ.System.product.CustproductTree")]
			},
			{
			
				region : 'east',
				collapsible : false,
				items : [ {xtype : "custproductcustaccessorylist",
					padding : '0 15 0 0',
					title : ' 产品附件'
				}],
				layout:'fit',
				width : 180
				
			}
/*
 * 后期还要用到，暂时注释
 * 2014年12月23日 09:24:50
 			,{
				region : 'south',
				height: 200,
				items : [DJ.System.product.CustPdtMainCustListColumn]
				,layout:'fit',
					title : ' 最近30天内产品变动情况',
					collapsed : true
				
			}*/
			]
	       
});