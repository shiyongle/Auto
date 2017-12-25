Ext.define('DJ.System.AddressList', {
			extend : 'Ext.c.GridPanel',
			title : "地址簿",
			id : 'DJ.System.AddressList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetAddressList.do',
			Delurl : "DelAddressList.do",
			EditUI : "DJ.System.AddressEdit",
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
			},
			Action_BeforeAddButtonClick : function(EditUI) {
				// 新增界面弹出前事件
			},
			Action_AfterAddButtonClick : function(EditUI) {
				// 新增界面弹出后事件
			},
			Action_BeforeEditButtonClick : function(EditUI) {
				// 修改界面弹出前事件
			},
			Action_AfterEditButtonClick : function(EditUI) {
				// 修改界面弹出后事件
				// 可对编辑界面进行复制
				
//				var cforms = Ext.getCmp("DJ.System.AddressEdit").getform().getForm();
//				var store1 = cforms.findField('fdistrictidid').store;
//				var store2 = cforms.findField('fcityidid').store;
//				var store3 = cforms.findField('fprovinceid').store;
//				cforms.findField('fcountryid').store.load();
//
//				store3.on("beforeload", function(store, options) {
//							Ext.apply(store.proxy.extraParams, {
//										fcountryid : Ext.getCmp("DJ.System.AddressEdit").editdata.fcountryid//obj.data[0].fcountryid
//									});
//						});
//				store3.load();
//				store2.on("beforeload", function(store, options) {
//							Ext.apply(store.proxy.extraParams, {
//										fprovinceid : Ext.getCmp("DJ.System.AddressEdit").editdata.fprovinceid//obj.data[0].fprovinceid
//									});
//						});
//				store2.load();
//				store1.on("beforeload", function(store, options) {
//							Ext.apply(store.proxy.extraParams, {
//										fcityid : Ext.getCmp("DJ.System.AddressEdit").editdata.fcityidid//obj.data[0].fcityidid
//									});
//						});
//				store1.load();

			},
			Action_BeforeDelButtonClick : function(me, record) {
				// 删除前事件
			},
			Action_AfterDelButtonClick : function(me, record) {
				// 删除后事件
			}
//			,custbar : [{
//						// id : 'DelButton',
//						text : '自定义按钮1',
//						height : 30,
//						handler : function() {
//							var me = this;
//						}
//					}, {
//						// id : 'DelButton',
//						text : '自定义按钮2',
//						height : 30
//					}]
			,fields : [{
						name : 'fid'
					}, {
						name : 'fname',
						myfilterfield : 'tba.fname',
						myfiltername : '名称',
						myfilterable : true
					}, {
						name : 'fnumber',
						myfilterfield : 'tba.fnumber',
						myfiltername : '编码',
						myfilterable : true
					}, {
						name : 'fcreatorid'
					}, {
						name : 'flastupdateuserid'
					}, {
						name : 'fcontrolunitid'
					}, {
						name : 'fdetailaddress'
					}, {
						name : 'fcountryid'
					}, {
						name : 'fcityidid'
					}, {
						name : 'femail'
					}, {
						name : 'flinkman'
					}, {
						name : 'fphone'
					}, {
						name : 'fprovinceid'
					}, {
						name : 'fdistrictidid'
					}, {
						name : 'fpostalcode'
					}, {
						name : 'ffax'
					}, {
						name : 'fcreatetime'
					}, {
						name : 'flastupdatetime'
					}, {
						name : 'fcustomerid'
					}, {
						name : 'customerName'
					}
					
					],
			columns : [{
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true

					},{
						'header' : 'fcreatorid',
						'dataIndex' : 'fcreatorid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : 'flastupdateuserid',
						'dataIndex' : 'flastupdateuserid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : 'fprovinceid',
						'dataIndex' : 'fprovinceid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : 'fcountryid',
						'dataIndex' : 'fcountryid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : 'fcityidid',
						'dataIndex' : 'fcityidid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : 'fdistrictidid',
						'dataIndex' : 'fdistrictidid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : 'fcontrolunitid',
						'dataIndex' : 'fcontrolunitid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : '地址名称',
						'dataIndex' : 'fname',
						sortable : true
					}, {
						'header' : '客户',
						'dataIndex' : 'customerName',
						sortable : true
					}, {
						'header' : '地址编码',
						'dataIndex' : 'fnumber',
						sortable : true
					}, {
						'header' : '邮政编码',
						'dataIndex' : 'fpostalcode',
						sortable : true
					}, {
						'header' : '邮箱',
						'dataIndex' : 'femail',
						sortable : true
					}, {
						'header' : '联系人',
						'dataIndex' : 'flinkman',
						sortable : true
					},{
						'header' : '电话',
						'dataIndex' : 'fphone',
						sortable : true
					},{
						'header' : '传真',
						'dataIndex' : 'ffax',
						sortable : true
					},{
						'header' : '详细地址',
						'dataIndex' : 'fdetailaddress',
						sortable : true
					}, {
						'header' : '创建时间',
						'dataIndex' : 'fcreatetime',
						width : 200,
						sortable : true
					}, {
						'header' : '修改时间',
						'dataIndex' : 'flastupdatetime',
						width : 200,
						sortable : true
					}]
		})