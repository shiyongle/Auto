Ext
		.define(
				'DJ.other.DeliverapplyPaperboardDeleteLog.DeliverapplyPaperboardDeleteLogList',
				{
					extend : 'Ext.c.GridPanel',
					title : "纸板订单删除日志",
					id : 'DJ.other.DeliverapplyPaperboardDeleteLog.DeliverapplyPaperboardDeleteLogList',
					pageSize : 50,
					closable : true,// 是否现实关闭按钮,默认为false
					url : 'gainDeliverapplyPaperboardDeleteLogs.do',
					// Delurl : "DeleteOutWarehouse.do",
					// EditUI : "DJ.Inv.OutWarehouse.OutWarehouseEdit",
					// exporturl:"outWarehousetoexcel.do",

					mixins : ['DJ.tools.grid.MySimpleGridMixer'],

					onload : function() {
						// 加载后事件，可以设置按钮，控件值等
						var me = this;

						var btn = Ext
								.getCmp('DJ.other.DeliverapplyPaperboardDeleteLog.DeliverapplyPaperboardDeleteLogList.delbutton');

						MyCommonToolsZ.setComAfterrender(btn,function(){
						
							MyCommonToolsZ.setToolTipZ(btn.id,'删除1个月前数据');
							
						});
								
						btn.setHandler(function() {
							Ext.MessageBox.confirm('提示','是否确认删除？',function(btn){
								if(btn=='yes'){
									var el = me.getEl();
									el.mask("系统处理中,请稍候……");
		
									Ext.Ajax.request({
										timeout : 6000,
		
										params : {
		
										},
		
										url : "deleteDeliverapplyPaperboardDeleteLogs.do",
										success : function(response, option) {
		
											var obj = Ext.decode(response.responseText);
											if (obj.success == true) {
												djsuccessmsg(obj.msg);
		
											} else {
												Ext.MessageBox.alert('错误', obj.msg);
		
											}
											el.unmask();
										}
									});
								}
							})
						

						}, btn);

						me._hideExportExcellButton();
						me._hideButtonsCURDExclude(['delbutton']);
						me._hideSearchButton();

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
					custbar : [{}],
					fields : [{
						name : 'fid'
					}, {

						name : 'famount',
						type : 'int'

					}, 'fmateriallength', 'fdeletetime', 'fmaterialwidth',
							'fvline', 'fhline', 'faddress', 'fcreatetime',
							'cusName', 'material', 'fboxlength', 'fboxwidth',
							'fboxheight', 'fmaterialf', 'famountpiece'

					],
					columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true
					}, {
						'header' : '客户名称',
						'dataIndex' : 'cusName',
						sortable : true
					}, {
						'header' : '材料',
						dataIndex : 'fmaterialf',
						sortable : true

					}, {
						'header' : '纸箱规格(CM)',
						hideable : false,
						columns : [{
							'header' : '长',
							'dataIndex' : 'fboxlength',
							width : 50,
							sortable : true
						}, {
							'header' : '宽',
							'dataIndex' : 'fboxwidth',
							width : 50,
							sortable : true
						}, {
							'header' : '高',
							'dataIndex' : 'fboxheight',
							width : 50,
							sortable : true
						}]

					}, {
						'header' : '下料规格(CM)',
						hideable : false,
						columns : [{
							'header' : '总长',
							'dataIndex' : 'fmateriallength',
							width : 60,
							sortable : true
						}, {
							'header' : '总高',
							'dataIndex' : 'fmaterialwidth',
							width : 60,
							sortable : true
						}]
					}, {
						'header' : '压线公式',
						columns : [{
							'header' : '纵向公式',
							'dataIndex' : 'fvline',
							width : 100,
							sortable : true
						}, {
							'header' : '横向公式',
							'dataIndex' : 'fhline',
							width : 100,
							sortable : true
						}]
					}, {
						'header' : '配送数量',
						columns : [{
							'header' : '只',
							'dataIndex' : 'famount',
							width : 50,
							sortable : true
						}, {
							'header' : '片',
							'dataIndex' : 'famountpiece',
							width : 50,
							sortable : true
						}]
					}, {
						'header' : '配送地址',
						'dataIndex' : 'faddress',
						width : 300,
						sortable : true
					}, {
						'header' : '创建时间',
						'dataIndex' : 'fcreatetime',
						width : 140,
						sortable : true
					}, {
						'header' : '删除时间',
						'dataIndex' : 'fdeletetime',
						width : 140,
						sortable : true
					}

					],
					selModel : Ext.create('Ext.selection.CheckboxModel')
				})