Ext.define('DJ.Inv.Storebalance.CustomerStoreBalanceRptPanel', {

	extend : 'Ext.ux.panel.GridAndLinePanel',

	id : 'DJ.Inv.Storebalance.CustomerStoreBalanceRptPanel',

	requires : ['DJ.Inv.Storebalance.CustomerStorebalanceList'],

	title : '我的库存',

	closable : true,// 是否现实关闭按钮,默认为false

	linePanelCfg : {//折线图配置
		title : "最近7天内库存变动曲线",
		items : [{
			xtype : 'mysimpleline',
			mysimplelineStoreCfg : {
				fields : [{
					name : "fstoreqty"
				}, {
					name : "fcreatetime"
				}],
				proxy : {
					url : "selectProductHistoryQty.do"
				}
			},
			lineCfg : {
				debug : false, //测试用 
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
	},
	gridCfg : {

		xtype : 'customerstorebalancelist'

	}

});