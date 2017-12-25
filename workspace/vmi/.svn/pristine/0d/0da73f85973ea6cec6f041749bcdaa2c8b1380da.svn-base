Ext.define('DJ.System.RoleAssgins', {
	extend : 'Ext.c.SelectFrameUI',
	id : 'DJ.System.RoleAssgins',
	modal : true,
	title : "分配用户",
	width : 850,// 230, //Window宽度
	height : 600,// 137, //Window高度
	resizable : false,
	closable : true,
	MyConfig:{
		txtLable:"角色名称",   //设置文本宽label值
		selectbtnname:"分配",  //设置选择按钮text
		delbtnname:"取消",      //设置取消按钮text
		selecturl:"AddRoleUser.do",          //选择按钮调用接口
		delurl:"DelRoleUser.do"              //删除按钮调用接口
	},
	RightConfig : {
		width : 800,// 下拉界面
		height : 200,// 下拉界面
		url : 'GetRoleUserList.do', // 下拉数据来源
		objectfilterfield:"l.froleid",
		fields : [{
			name : 'fid'
		}, {
			name : 'fname',
			myfilterfield : 'c.fname',
			myfiltername : '用户名称',
			myfilterable : true
		}, {
			name : 'fcustomername'
//		}, {
//			name : 'roleid'
		}],
	
	
	columns : [
		
		{
							text : 'fid',
							dataIndex : 'fid',
							hidden : true,
							hideable : false,
							sortable : true
	
		}, {
						'header' : '用户名称',
						'dataIndex' : 'fname',
						width : 150,
						sortable : true
			
					}, {
						'header' : '客户名称',
						'dataIndex' : 'fcustomername',
						width : 220,
						sortable : true
			
					}
	
	]
	},
	LeftConfig : {
		width : 800,// 下拉界面
		height : 200,// 下拉界面
		url : 'GetUserList.do', // 下拉数据来源
		fields : [{
			name : 'fid'
		}, {
			name : 'fname',
			myfilterfield : 'fname',
			myfiltername : '用户名称',
			myfilterable : true
		}, {
			name : 'fcustomername'
//		}, {
//			name : 'roleid'
		}],
		columns : [
		
		{
							text : 'fid',
							dataIndex : 'fid',
							hidden : true,
							hideable : false,
							sortable : true
	
		}, {
						'header' : '用户名称',
						'dataIndex' : 'fname',
						width : 150,
						sortable : true
			
					}, {
						'header' : '客户名称',
						'dataIndex' : 'fcustomername',
						width : 220,
						sortable : true
			
					}
	
	]
	}
});
