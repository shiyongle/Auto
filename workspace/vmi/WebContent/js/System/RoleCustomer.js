function formatfusedstatus(value) {
	return value == '1' ? '启用' : '禁用';
}
Ext.define('DJ.System.RoleCustomer', {
	extend : 'Ext.c.SelectFrameUI',
	id : 'DJ.System.RoleCustomer',
	modal : true,
	title : "关联客户",
	width : 850,// 230, //Window宽度
	height : 600,// 137, //Window高度
	resizable : false,
	closable : true,
	MyConfig:{
		txtLable:"角色名称",   //设置文本宽label值
		selectbtnname:"分配",  //设置选择按钮text
		delbtnname:"取消",      //设置取消按钮text
		selecturl:"AddRoleCustomer.do",          //选择按钮调用接口
		delurl:"DelRoleCustomer.do"              //删除按钮调用接口
	},
	RightConfig : {
		width : 800,// 下拉界面
		height : 200,// 下拉界面
		url : 'GetRoleCustomerList.do', // 下拉数据来源
		objectfilterfield:"l.froleid",
		fields : [{
			name : 'fid'
		}, {
			name : 'fnumber',
			myfilterfield : 'c.fnumber',
			myfiltername : '客户编码',
			myfilterable : true
		}, {
			name : 'fname',
			myfilterfield : 'c.fname',
			myfiltername : '客户名称',
			myfilterable : true
		}, {
			
			name : 'fcreatetime'
		}, {
			name :'faddress'
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
			'header' : '客户名称',
			'dataIndex' : 'fname',
			sortable : true			
		}, {
			text : '地址',
			dataIndex : 'faddress',
			sortable : true
		}, {
			'header' : '创建时间',
			'dataIndex' : 'fcreatetime'
		}
		
	
	]
	},
	LeftConfig : {
		width : 800,// 下拉界面
		height : 200,// 下拉界面
		url : 'GetCustomerList.do', // 下拉数据来源
		fields : [{
			name : 'fid'
		}, {
			name : 'fname',
			myfilterfield : 'fname',
			myfiltername : '客户名称',
			myfilterable : true
		}, {
			name : 'fnumber',
			myfilterfield : 'fnumber',
			myfiltername : '客户编码',
			myfilterable : true
		}, {
			
			name : 'fcreatetime'
		}, {
			name :'faddress'
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
			'header' : '客户名称',
			'dataIndex' : 'fname',
			sortable : true		
		}, {
			text : '地址',
			dataIndex : 'faddress',
			sortable : true
		}, {
			'header' : '创建时间',
			'dataIndex' : 'fcreatetime'
		}
	
	]
	}
});
