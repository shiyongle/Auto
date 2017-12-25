Ext.define('DJ.order.saleOrder.SupplierStoreBalanceEdit', {
			extend : 'Ext.c.BaseEditUI',
			id : 'DJ.order.saleOrder.SupplierStoreBalanceEdit',
			//modal : true,
			title : "产品库存明细",
			width : 750,// 230, //Window宽度
			height : 420,// 137, //Window高度
			resizable : false,
			url : 'SaveSaledeliver.do',
			infourl : 'GetSaledeliverInfo.do',
			viewurl : 'GetSaledeliverInfo.do',
			closable : true,// 关闭按钮，默认为true
			onload : function() {
				Ext
						.getCmp("DJ.order.saleOrder.SupplierStoreBalanceEdit.closebutton")
						.setText("关闭");
			},
			items : {
				xtype : 'cTable',
				name : "SupplierStoreBalance",
				parentfield : "pnumber",
				url:"GetSupplierStoreBalanceEdit.do",
				fields : [{
							name : 'pnumber'
						}, {
							name : 'pspec'
						}, {
							name : 'pname'
						}, {
							name : 'ftype'
						}, {
							name : 'outIn'
						}, {
							name : 'uname'
						}, {
							name : 'pcreatetime'
						}],

				id : "DJ.traffic.SaleDeliver.SaleDeliverEdit.table",
				width : 740,
				height : 360,
				columns : [{
							xtype : "rownumberer",
							text : "No"
						}, {
							'header' : ' 制造订单号',
							width : 90,
							'dataIndex' : 'pnumber'
						}, {
							'header' : '包装物名称',
							width : 175,
							'dataIndex' : 'pname'
						}, {
							'header' : '规格',
							width : 100,
							'dataIndex' : 'pspec'
						}, {
							'header' : '类型',
							width : 60,
							'dataIndex' : 'ftype'
						}, {
							'header' : '数量',
							width : 60,
							'dataIndex' : 'outIn'
						}, {
							'header' : '操作人',
							width : 90,
							'dataIndex' : 'uname'
						}, {
							'header' : '操作时间',
							width : 140,
							'dataIndex' : 'pcreatetime'
						}]
			}
		});