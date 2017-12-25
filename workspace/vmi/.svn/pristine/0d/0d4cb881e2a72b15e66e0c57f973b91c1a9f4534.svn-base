Ext.define('DJ.Oqa.QualityCheck.QualityCheckList', {
			extend : 'Ext.c.GridPanel',
			title : "质量管理",
			id : 'DJ.Oqa.QualityCheck.QualityCheckList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetQualityCheck.do',
			Delurl : "DeleteQualityCheck.do",
			EditUI : "DJ.Oqa.QualityCheck.QualityCheckEdit",
			exporturl:"QualityChecktoexcel.do",
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
						name : 'fnumber',
						myfilterfield : 'o.fnumber',
						myfiltername : '编号',
						myfilterable : true
					}, {
						name : 'fsaleorderid'
					}, {
						name : 'salefnumber',
						myfilterfield : 's.fnumber',
						myfiltername : '订单编号',
						myfilterable : true
					}, {
						name : 'fdeliverid'
					}, {
						name : 'fdelivernumber',
						myfilterfield : 'd.fnumber',
						myfiltername : '要货计划',
						myfilterable : true
					}, {
						name : 'fcheckresult'
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
						'header' : '订单编号',
						'dataIndex' : 'salefnumber',
						sortable : true
					}, {
						'header' : '要货计划',
						'dataIndex' : 'fdelivernumber',
						sortable : true
					}, {
						'header' : '检验结果',
						'dataIndex' : 'fcheckresult',
						sortable : true,
						renderer: function(value){
					        if (value == 1) {
					            return '合格';
					        }
					        else{
					        	return '不合格';
					        }
					    }
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