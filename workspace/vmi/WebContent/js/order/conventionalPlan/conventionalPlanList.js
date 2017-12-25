Ext.define('DJ.order.conventionalPlan.conventionalPlanList', {
			extend : 'Ext.c.GridPanel',
			title : "常规计划",
			id : 'DJ.order.conventionalPlan.conventionalPlanList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetConventionalplanList.do',
			Delurl : "DelConventionalplanList.do",
			EditUI : "DJ.order.conventionalPlan.conventionalPlanEdit",
			exporturl:"ConventionalplantoExcel.do",//导出为EXCEL方法
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
//				alert("DeliverList");
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
						name : 'fcustPdtName',
						myfilterfield : 'p.fname',
						myfiltername : '客户产品名称',
						myfilterable : true
					}, {
						name : 'fcustPdtNumer',
						myfilterfield : 'p.fnumber',
						myfiltername : '客户产品编码',
						myfilterable : true
					}, {
						name : 'fcreatorid'
					}, {
						name : 'fupdateuserid'
					}, {
						name : 'fcustProductid'
					}, {
						name : 'fcustPdtNumer'
					}, {
						name : 'fcustPdtName'
					}, {
						name : 'fplanQty'
					}, {
						name : 'fplanCycle'
					}, {
						name : 'fplanDelivery'
					}, {
						name : 'fplanAmount'
					}, {
						name : 'fcreator'
					}, {
						name : 'flastupdater'
					}, {
						name : 'fcreatetime'
					}, {
						name : 'fupdatetime'
					}, {
						name : 'fcustomerid'
					}, {
						name : 'custname'
					}, {
						name : 'fcustproductName'
					}, {
						name : 'fcustomerName'
					}, {
						name : 'fplanbegintime'
					}, {
						name : 'fplanendtime'
					}, {
						name : 'fdescription'
					}],
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
						'header' : 'fupdateuserid',
						'dataIndex' : 'fupdateuserid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : 'fcustProductid',
						'dataIndex' : 'fcustProductid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : 'fcustomerid',
						'dataIndex' : 'fcustomerid',
						hidden : true,
						hideable : false,
						sortable : true

					},{
						'header' : '客户',
						'dataIndex' : 'custname',
						sortable : true
					},{
						'header' : '客户产品',
						'dataIndex' : 'fcustPdtName',
						sortable : true
					},{
						'header' : '客户产品编码',
						'dataIndex' : 'fcustPdtNumer',
						sortable : true
					},{
						'header' : '导入的客户产品',
						'dataIndex' : 'fcustproductName',
						sortable : true
					},{
						'header' : '导入的客户',
						'dataIndex' : 'fcustomerName',
						sortable : true
					}, {
						'header' : '计划产量',
						'dataIndex' : 'fplanQty',
						width : 80 ,
						sortable : true
					}, {
						'header' : '预计交期',
						'dataIndex' : 'fplanDelivery',
						width : 120 ,
						sortable : true
					}, {
						'header' : '计划周期',
						'dataIndex' : 'fplanCycle',
						width : 80 ,
						sortable : true
					}, {
						'header' : '计划开始时间',
						'dataIndex' : 'fplanbegintime',
						width : 120 ,
						sortable : true
					}, {
						'header' : '计划结束时间',
						'dataIndex' : 'fplanendtime',
						width : 120 ,
						sortable : true
					}, {
						'header' : '计划数量',
						'dataIndex' : 'fplanAmount',
						width : 80 ,
						sortable : true
					},{
						'header' : '创建人',
						'dataIndex' : 'fcreator',
						sortable : true
					}, {
						'header' : '创建时间',
						'dataIndex' : 'fcreatetime',
						width : 120,
						sortable : true
					},{
						'header' : '修改人',
						'dataIndex' : 'flastupdater',
						sortable : true
					}, {
						'header' : '修改时间',
						'dataIndex' : 'fupdatetime',
						width : 120,
						sortable : true
					}, {
						'header' : '备注',
						'dataIndex' : 'fdescription',
						width : 120,
						sortable : true
					}]
		})