Ext.define('DJ.order.logistics.LogisticsCarInfoList', {
			extend : 'DJ.myComponent.grid.MySimpleConciseGrid',
			id : 'DJ.order.logistics.LogisticsCarInfoList',
			url : 'GetLogisticsCarinfoList.do',
			Delurl : "DelLogisticsCarinfo.do",
			EditUI : "DJ.order.logistics.LogisticsCarEdit",
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
						condictionFields : ['fcargotype', 'fcartype'],
						tip : '请输入"货物类型"、"所需类型"',
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
						name : 'fcargotype'
					}, {
						name : 'fcartype'
					}],

			listeners : {},

			columns : [ {
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true
					}, {
						'header' : '货物类型',
						align : 'center',
						dataIndex : 'fcargotype',
						width:300,
						sortable : true,
						renderer : function(value) {
							return "<p>" + value + "</p>";
						}
					}, {
						'header' : '车辆类型',
						align : 'center',
						dataIndex : 'fcartype',
						width:300,
						sortable : true,
						renderer : function(value) {
							return "<p>" + value + "</p>";
						}
					}

			],
			selModel : Ext.create('Ext.selection.CheckboxModel')
		});