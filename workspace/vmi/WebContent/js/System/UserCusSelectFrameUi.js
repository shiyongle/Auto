Ext.require(['Ext.ux.grid.UserItemdblClick','Ext.ux.grid.FieldQueryFilter']);

Ext.define('DJ.System.UserCusSelectFrameUi', {
	extend : 'Ext.c.SelectFrameUI',
	id : 'DJ.System.UserCusSelectFrameUi',
	modal : true,
	title : "客户权限分配",
	width : 850,// 230, //Window宽度
	height : 600,// 137, //Window高度
	resizable : false,
	closable : true,
	MyConfig:{
		txtLable:"用户名",   //设置文本宽label值
		selectbtnname:"分配",  //设置选择按钮text
		delbtnname:"取消",      //设置取消按钮text
		selecturl:"AddUserCustomer.do",          //选择按钮调用接口
		delurl:"DelUserCustomer.do"              //删除按钮调用接口
	},
	RightConfig : {
		width : 800,// 下拉界面
		height : 200,// 下拉界面
		url : 'GetUserCustomerList.do', // 下拉数据来源
		objectfilterfield:"l.FUSERID",
		plugins: [{
			ptype:'itemdblclick',
			callButton:'取消'
		},{
			ptype:'fieldqueryfilter'
		}],
		fields : [ 
	{
		name : 'useid'
	}
	,	
	{
		name : 'fid'
	}, {
		name : 'fname'
		
	}, {
		name : 'fnumber'
	}],
	
	
	columns : [
		
		{
							text : 'fid',
							dataIndex : 'fid',
							hidden : true,
							hideable : false,
							sortable : true
	
						}, {
							text : '编码',
							dataIndex : 'fnumber',
							sortable : true,
							filter : {
								type : 'string'
							}
						}, {
							text : '客户名称',
							dataIndex : 'fname',
							sortable : true,
							filter : {
								type : 'string'
							}
						}
	
	]
	},
	LeftConfig : {
		width : 800,// 下拉界面
		height : 200,// 下拉界面
		url : 'GetCustomerList.do', // 下拉数据来源
		plugins: [{
			ptype:'itemdblclick',
			callButton:'分配'
		},{
			ptype:'fieldqueryfilter'
		}],
		fields : [ 
			{
				name : 'useid'
			}
			,	
			{
				name : 'fid'
			}, {
				name : 'fname',
				myfilterfield : 'fname',
				myfiltername : '客户名称',
				myfilterable : true
			}, {
				name : 'fnumber',
				myfilterfield : 'fnumber',
				myfiltername : '编码',
				myfilterable : true
			}
		],
		columns : [
		
		{
							text : 'fid',
							dataIndex : 'fid',
							hidden : true,
							hideable : false,
							sortable : true
	
						}, {
							text : '编码',
							dataIndex : 'fnumber',
							sortable : true,
							filter : {
								type : 'string'
							}
						}, {
							text : '客户名称',
							dataIndex : 'fname',
							sortable : true,
							filter : {
								type : 'string'
							}
						}
	
	]
	}
});