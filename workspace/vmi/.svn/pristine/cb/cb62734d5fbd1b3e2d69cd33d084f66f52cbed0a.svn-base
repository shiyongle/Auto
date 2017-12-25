Ext.define('DJ.order.logistics.LogisticsaddressInfoList', {
			extend : 'DJ.myComponent.grid.MySimpleConciseGrid',
			id : 'DJ.order.logistics.LogisticsaddressInfoList',
			url : 'GetLogisticsAddressList.do',
			Delurl : "DelLogisticsAddress.do",
			EditUI : "DJ.order.logistics.LogisticsAddressEdit",
			// exporturl:"outWarehousetoexcel.do",
			stateful : true,
			pagingtoolbarDock : 'bottom',
			mixins : ['DJ.tools.grid.MySimpleGridMixer',
					'DJ.tools.grid.mixer.MyGridSearchMixer'],
			isFirststateShower : true,
			showSearchAllBtn : false,
			statics : {
			},
			cusTopBarItems : [{
						height : 30,
						text : "新  增",
						iconCls : "addnew",
						handler : function(btn) {
							var me = this.up("panel");
							me.doAddAction(me);
						}
					}, {
						height : 30,
						text : "修  改",
						iconCls : "edit",
						handler : function() {
							var me = this.up("panel");
							me.doEditAction(me);
						}
					}, {
						height : 30,
						text : "删    除",
						iconCls : "delete",
						handler : function() {
							var me = this.up("panel");
							me.doDeleteAction(me);
						}
					}, {

						xtype : 'tbspacer',
						flex : 1

					}, {

						xtype : 'mymixedsearchbox',
						condictionFields : ['flinkman', 'fphone',
								'fname'],
						tip : '请输入"发货人"、"发货电话"、"详细地址"',
						useDefaultfilter : true,
						filterMode : true
					}],
			onload : function() {
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
			},
			Action_BeforeDelButtonClick : function(me, record) {
				// 删除前事件

			},
			Action_AfterDelButtonClick : function(me, record) {
				// 删除后事件
			},

			fields : [{
						name : 'fid'
					}, {
						name : 'fname'
					}, {
						name : 'flinkman'
					}, {
						name : 'fphone'
					}],

			listeners : {},

			columns : [ {
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true
					}, {
						'header' : '发货人',
						align : 'center',
						dataIndex : 'flinkman',
						flex : 2,
						sortable : true
					}, {
						'header' : '发货人电话',
						align : 'center',
						dataIndex : 'fphone',
						flex : 2,
						sortable : true
					}, {
						'header' : '货物地址',
						align : 'center',
						dataIndex : 'fname',
						flex : 5,
						sortable : true,
						renderer : function(value) {
							return "<p>" + value + "</p>";
						}
					}

			],
			selModel : Ext.create('Ext.selection.CheckboxModel')
		});