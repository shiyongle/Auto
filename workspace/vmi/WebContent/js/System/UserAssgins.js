Ext.require(['Ext.ux.grid.UserItemdblClick','Ext.ux.grid.FieldQueryFilter']);
Ext.define('DJ.System.UserAssgins', {
	extend : 'Ext.c.SelectFrameUI',
	id : 'DJ.System.UserAssgins',
	modal : true,
	title : "分配角色",
	width : 850,// 230, //Window宽度
	height : 600,// 137, //Window高度
	resizable : false,
	closable : true,
	MyConfig:{
		txtLable:"用户名称",   //设置文本宽label值
		selectbtnname:"分配",  //设置选择按钮text
		delbtnname:"取消",      //设置取消按钮text
		selecturl:"AddUserRole.do",          //选择按钮调用接口
		delurl:"DelUserRole.do"              //删除按钮调用接口
	},
	RightConfig : {
		width : 800,// 下拉界面
		height : 200,// 下拉界面
		url : 'GetUserRoleList.do', // 下拉数据来源
		objectfilterfield:"l.fuserid",
		plugins: [{
			ptype:'itemdblclick',
			callButton:'取消'
		},{
			ptype:'fieldqueryfilter'
		}],
		fields : [{
			name : 'fid'
		}, {
			name : 'fname',
			myfilterfield : 'c.fname',
			myfiltername : '角色名称',
			myfilterable : true
		}, {
			name : 'fnumber',
			myfilterfield : 'c.fnumber',
			myfiltername : '角色编码',
			myfilterable : true
		}, {
			name : 'fdescription'
		}],
	
	
	columns : [
		
		{
							text : 'fid',
							dataIndex : 'fid',
							hidden : true,
							hideable : false,
							sortable : true
	
		}, {
			'header' : '编码',
			'dataIndex' : 'fnumber',
			sortable : true
		}, {
			'header' : '角色名称',
			'dataIndex' : 'fname',
			sortable : true
		}, {
			'header' : '描述',
			'dataIndex' : 'fdescription',
			sortable : true
		}
	]
	},
	LeftConfig : {
		width : 800,// 下拉界面
		height : 200,// 下拉界面
		url : 'GetRoleList.do', // 下拉数据来源
		plugins: [{
			ptype:'itemdblclick',
			callButton:'分配'
		},{
			ptype:'fieldqueryfilter'
		}],
		fields : [{
			name : 'fid'
		}, {
			name : 'fname',
			myfilterfield : 'fname',
			myfiltername : '角色名称',
			myfilterable : true
		}, {
			name : 'fnumber',
			myfilterfield : 'fnumber',
			myfiltername : '角色编码',
			myfilterable : true
		}, {
			name : 'fdescription'
		}],
		columns : [
		
		{
							text : 'fid',
							dataIndex : 'fid',
							hidden : true,
							hideable : false,
							sortable : true
		}, {
			'header' : '编码',
			'dataIndex' : 'fnumber',
			sortable : true
		}, {
			'header' : '角色名称',
			'dataIndex' : 'fname',
			sortable : true
		}, {
			'header' : '描述',
			'dataIndex' : 'fdescription',
			sortable : true
		}
	]
	}
});
