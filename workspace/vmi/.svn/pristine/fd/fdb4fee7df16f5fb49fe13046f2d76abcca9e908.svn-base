Ext.define('DJ.test.productList', {
			extend : 'Ext.c.GridPanel',
			title : "产品列表界面",
			id : 'DJ.test.productList',
			pageSize : 50,
			selModel:Ext.create('Ext.selection.CheckboxModel'),
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetProductss.do',
			Delurl : "deleteProducts.do",
			EditUI : "DJ.test.productEdit",
			exporturl:"producttoexcel.do",
			Action_BeforeAddButtonClick : function(EditUI) {
				// 新增界面弹出前事件
			},
			Action_AfterAddButtonClick : function(EditUI) {
				// 新增界面弹出后事件
			},
			Action_BeforeEditButtonClick : function(EditUI) {	
			
			},
			Action_AfterEditButtonClick : function(EditUI) {
//				var editWidow=Ext.getCmp(EditUI);
//				alert(EditUI.infourl);
				
//				var record=Ext.getCmp("DJ.test.productList").getSelectionModel().getSelection();
//				for(var i=0;i<record.length;i++)
//					{
//				alert(record[i].get("fid"));
//					}	
//
//				var editWidow=Ext.getCmp(EditUI);
//				
//				Ext.Ajax.request({
//					url : "GetProduct.do",
//					params : {
//						fid : '46cd9f92-bf55-11e2-b585-60a44c5bbef3'
//					}, // 参数
//						success : function(response, option) {
//							var obj = Ext.decode(response.responseText);
//							if (obj.success == true) {
//								if(obj.data==undefined)
//									{
//										editWidow.hide();
//										Ext.MessageBox.alert('错误', '该记录可能已经不存在，请刷新后在操作');
//										return;
//									}else
//										{
//										var cform=editWidow.getform().getForm();
//										cform.setValues(obj.data[0]);			
//										cform.findField('fcustomerid').setRawValue(obj.data[0].cname);
//									//	cform.findField('fcreatetime').setValue('2012-02-10 11:11:11');
//									//	alert(obj.data[0].fcreatetime);
//										}
//							} else {
//								editWidow.hide();
//								Ext.MessageBox.alert('错误',obj.msg);
//								return;
//							}
//						}
//					});
			},
			Action_BeforeDelButtonClick : function(me, record) {
				//删除前事件
			},
			Action_AfterDelButtonClick : function(me, record) {
				//删除后事件
			},
//			custbar : [{
//						// id : 'DelButton',
//						text : '自定义按钮1',
//						height : 30
//					}],
			fields : [{
						name : 'fid'
					}, {
						name : 'fname',
						myfilterfield : 'd.fname',
						myfiltername : '名称',
						myfilterable : true
					}, {
						name : 'fnumber'
					}, {
						name : 'fcreatorid'
					}, {
						name : 'fcreatetime'
					}, {
						name : 'flastupdateuserid'
					}, {
						name : 'flastupdatetime'
					}, {
						name : 'u2_fname'
					}, {
						name : 'u1_fname'
					}, {
						name : 'fcharacter'
					}, {
						name : 'fboxmodelid'
					}, {
						name : 'feffect',
						convert:function(value,record)
						{
							if(value=='1')
							{	
								return true;
							}else{
								return false;
							}	
						}
							
					}, {
						name : 'fnewtype'
					}, {
						name : 'fversion'
					}, {
						name : 'fishistory',
						convert:function(value,record)
						{
							if(value=='1')
							{	
								return true;
							}else{
								return false;
							}	
						}
					}],
			columns : [{
						text : 'fid',
						dataIndex : 'fid',
						hidden : true,
						hideable : false,
						sortable : true
					}, {
						text : '编号',
						dataIndex : 'fnumber',
						sortable : true
					}, {
						text : '名称',
						dataIndex : 'fname',
						sortable : true
					}, {
						text : '版本号',
						dataIndex : 'fversion',
						sortable : true
					}, {
						text : '特征',
						dataIndex : 'fcharacter',
						sortable : true,
						width : 100
					}, {
						text: '箱型',
						dataIndex : 'fboxmodelid',
						sortable : true
						
					}, {
						text : '类型',
						dataIndex : 'fnewtype',
						sortable : true
					}, {
						text : '历史版本',
						dataIndex : 'fishistory',
						xtype : 'checkcolumn',
						processEvent : function() {
						},
						sortable : true
					}, {
						text : '禁用或启用',
						xtype : 'checkcolumn',
						dataIndex : 'feffect',
						processEvent : function() {
						},
						sortable : true
					},
					 {
						text : '修改人',
						dataIndex : 'u2_fname',
						sortable : true
					}, {
						text : '修改时间',
						dataIndex : 'flastupdatetime',
						width : 150,
						sortable : true
					
					}, {
						text : '创建人',
						dataIndex : 'u1_fname',
						sortable : true
					}, {
						text : '创建时间',
						dataIndex : 'fcreatetime',
						width : 150,
						sortable : true

					} ]
		})