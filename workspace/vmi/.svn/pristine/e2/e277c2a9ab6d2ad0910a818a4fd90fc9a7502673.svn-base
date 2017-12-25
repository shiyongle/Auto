Ext.define('DJ.Inv.WarehouseSites.WarehouseSitesList', {
			extend : 'Ext.c.GridPanel',
			title : "库位管理",
			id : 'DJ.Inv.WarehouseSites.WarehouseSitesList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetWarehouseSites.do',
			Delurl : "DeleteWarehouseSites.do",
			EditUI : "DJ.Inv.WarehouseSites.WarehouseSitesEdit",
			exporturl:"warehouseSitetoexcel.do",
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
						name : 'fnumber'
					}, {
						name : 'fname',
						myfilterfield : 'w.fname',
						myfiltername : '仓库名称',
						myfilterable : true
					}, {
						name : 'wfname'
					}, {
						name : 'fparentid'
					}, {
						name : 'faddress'
					},{
						name:'fremark'
					}, {
						name : 'finstoreprice'
					}, {
						name : 'foutstoreprice'
					}, {
						name : 'farea'
					}, {
						name : 'flastupdatetime'
					}, {
						name : 'lfname'
					}, {
						name : 'fcreatetime'
					}, {
						name : 'cfname'
					}],
			columns : [Ext.create('DJ.Base.Grid.GridRowNum'),{
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true
					}, {
						'header' : '仓位编码',
						'dataIndex' : 'fnumber',
						sortable : true
					}, {
					'header' : '仓位名称',
					'dataIndex' : 'fname',
					sortable : true
					}, {
						'header' : '所属仓库',
						'dataIndex' : 'wfname',
						sortable : true
					}, {
						'header' : '容量(m2)',
						'dataIndex' : 'farea',
						sortable : true,
						xtype: 'numbercolumn',
						format:'0.0000'

					}, {
						'header' : '出库计件(元/m2)',
						dataIndex : 'foutstoreprice',
						sortable : true,
						xtype: 'numbercolumn',
						format:'0.0000'
					}, {
						'header' : '入库计件(元/m2)',
						dataIndex : 'finstoreprice',
						sortable : true,
						format:'0.0000',
						xtype: 'numbercolumn'
					}, {
						'header' : '地址',
						'dataIndex' : 'faddress',
						sortable : true
					}, {
						'header' : '备注',
						'dataIndex' : 'fremark',
						sortable : true
					},{
						'header' : '修改人',
							dataIndex : 'lfname',
							sortable : true
						}, {
							'header' : '修改时间',
							dataIndex : 'flastupdatetime',
							width : 150,
							sortable : true
						}, {
							'header' : '创建人',
							dataIndex : 'cfname',
							sortable : true
						}, {
							'header' : '创建时间',
							dataIndex : 'fcreatetime',
							width : 150,
							sortable : true
						
					}],
					selModel:Ext.create('Ext.selection.CheckboxModel')
		})