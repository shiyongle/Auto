Ext.define('Ext.ux.panel.GridAndLinePanel', {
	extend : 'Ext.panel.Panel',

	alias : 'widget.gridandlinepanel',

	requires : ['Ext.ux.chart.MySimpleLine'],

	layout : {
		type : 'border'
	},
	title : 'default',

	defaults : {
		collapsible : true,
		split : true
	},
	
	closable : true,// 是否现实关闭按钮,默认为false

	initComponent : function() {
		var me = this;

		var linePanelCfg = {
			flex : 1,
			collapsed : true,
			region : 'east',
			padding : '0 20 0 0',
			xtype : 'panel',
			title : "default",
			layout : 'fit',
			items : [{
				xtype : 'mysimpleline',
				mysimplelineStoreCfg : {
					fields : [{
						name : "famount"
					}, {
						name : "fcreatetime"
					}],
					proxy : {
						url : "selectFoutedDeliverorderQtyAndDate.do"
					}
				},
				lineCfg : {
					axes : [{
						title : '库存数量'
					}, {
						title : '时间'
					}],
					series : [{
						title : '时间'// 配置图例字段说明
					}]
				}
			}]

		}

		Ext.apply(linePanelCfg, me.linePanelCfg);

		var gridCfg = {
			
			xtype : 'actualdeliverrpt',
			region : 'center',
//			width : 550,
			collapsible : false,
			split : false
		}

		Ext.apply(gridCfg, me.gridCfg);

		Ext.applyIf(me, {
			items : [linePanelCfg, gridCfg]
		});

		me.callParent(arguments);
	}

});