Ext.define('DJ.System.CustAddressList', {
			extend : 'Ext.c.GridPanel',
			title : "客户地址",
			id : 'DJ.System.CustAddressList',
			iconCls : 'user',
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			pageSize : 100,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetUserAddressList.do',
			//Delurl : "DelUserAddress.do",
			EditUI : "DJ.System.CustAddressEdit",
			//exporturl:"suppliertoexcel.do",
			onload : function() {
				var win = this;	
				var btHideSs = ['button[text=删    除]', 'button[text=修  改]','button[text=导出Excel]'];
				Ext.each(btHideSs, function(ele, index, all) {
				   win.down(ele).hide();
				} );
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
			},
			Action_BeforeDelButtonClick : function(me, record) {
				// 删除前事件
			},
			Action_AfterDelButtonClick : function(me, record) {
				// 删除后事件
			},
			custbar : [],
			fields : [{
						name : 'fid'
					}, {
						name : 'cdid'
					},{
						name : 'custname'
					}, {
						name : 'dtaddress',
						myfilterfield : 'ad.FDETAILADDRESS',
						myfiltername : '详细地址',
						myfilterable : true
					}, {
						name : 'linkman'
					}, {
						name : 'phone'
					}],
					columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false
					},{
						'header' : 'cdid',
						'dataIndex' : 'cdid',
						 hidden : true,
						 hideable : false
					},{
						'header' : '客户名称',
						'dataIndex' : 'custname'
					}, {
						'header' : '详细地址',
						'dataIndex' : 'dtaddress',
						sortable : true,
						width : 262
					}, {
						'header' : '联系人',
						width : 70,
						'dataIndex' : 'linkman'
					}, {
						'header' : '联系方式',
						width : 70,
						'dataIndex' : 'phone'
					}]
		});
