Ext.define('DJ.Inv.warehouse.WarehouseList', {
			extend : 'Ext.c.GridPanel',
			title : "仓库管理",
			id : 'DJ.Inv.warehouse.WarehouseList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetWarehouses.do',
			Delurl : "DeleteWarehouse.do",
			EditUI : "DJ.Inv.warehouse.WarehouseEdit",
			exporturl:"warehousetoexcel.do",
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
				 var i=0;
				 i=i+1;
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
						name : 'fsimplename'
					}, {
						name : 'ufname'
					}, {
						name : 'fcontrollerid'
					}, {
						name : 'cfaddresid'
					}, {
						name : 'dfname'
					},{
						name:'fdescription'
					}, {
						name : 'foutstorage'
					}, {
						name : 'finstorage'
					}, {
						name : 'cffreightprice'
					}, {
						name : 'fwarehousetype'
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
						'header' : '编码',
						'dataIndex' : 'fnumber',
						sortable : true
					}, {
					'header' : '名称',
					'dataIndex' : 'fname',
					sortable : true
					}, {
						'header' : '简称',
						'dataIndex' : 'fsimplename',
						sortable : true
					}, {
						'header' : '管理员',
						'dataIndex' : 'ufname',
						sortable : true
					}, {
						'header' : '送货地址',
						'dataIndex' : 'dfname',
						sortable : true
					}, {
						'header' : '描述',
						'dataIndex' : 'fdescription',
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
						}, {
							'header' : '出库计件(元/m2)',
							dataIndex : 'foutstorage',
							sortable : true
						}, {
							'header' : '入库计件(元/m2)',
							dataIndex : 'finstorage',
							sortable : true
						}, {
							'header' : '仓库类型',
							dataIndex : 'fwarehousetype',
							sortable : true,
							 renderer: function(value){
							        if (value == 1) {
							            return '成品仓库';
							        }else if(value==2)
							        	{
							        	return '半成品';}
							        else{
							        	return '未分类';
							        }
							    }
						}, {
							'header' : '运费单价',
							dataIndex : 'cffreightprice',
							sortable : true,
							xtype: 'numbercolumn',
							format:'0.0000'
					}],
					selModel:Ext.create('Ext.selection.CheckboxModel')
		})