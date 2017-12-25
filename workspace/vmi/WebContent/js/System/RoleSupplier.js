function formatfusedstatus(value) {
	return value == '1' ? '启用' : '禁用';
}
Ext.define('DJ.System.RoleSupplier', {
	extend : 'Ext.c.SelectFrameUI',
	id : 'DJ.System.RoleSupplier',
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
		selecturl:"AddRoleSupplier.do",          //选择按钮调用接口
		delurl:"DelRoleSupplier.do"              //删除按钮调用接口
	},
	RightConfig : {
		width : 800,// 下拉界面
		height : 200,// 下拉界面
		url : 'GetRoleSupplierList.do', // 下拉数据来源
		objectfilterfield:"l.froleid",
		fields : [{
			name : 'fid'
		}, {
			name : 'fnumber',
			myfilterfield : 'c.fnumber',
			myfiltername : '供应商编码',
			myfilterable : true
		}, {
			name : 'fname',
			myfilterfield : 'c.fname',
			myfiltername : '供应商名称',
			myfilterable : true
		}, {
			
			name : 'fcreatetime'
		}, {
			name :'fusedstatus'
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
			'header' : '供应商名称',
			'dataIndex' : 'fname',
			sortable : true			
		}, {
			'header' : '状态',
			width : 50,
			'dataIndex' : 'fusedstatus',
			renderer : formatfusedstatus,
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
		url : 'GetSupplierList.do', // 下拉数据来源
		fields : [{
			name : 'fid'
		}, {
			name : 'fname',
			myfilterfield : 'fname',
			myfiltername : '供应商名称',
			myfilterable : true
		}, {
			name : 'fnumber',
			myfilterfield : 'fnumber',
			myfiltername : '供应商编码',
			myfilterable : true
		}, {
			
			name : 'fcreatetime'
		}, {
			name :'fusedstatus'
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
			'header' : '供应商名称',
			'dataIndex' : 'fname',
			sortable : true		
		}, {
			'header' : '状态',
			width : 50,
			'dataIndex' : 'fusedstatus',
			renderer : formatfusedstatus,
			sortable : true
		}, {
			'header' : '创建时间',
			'dataIndex' : 'fcreatetime'
		}
	
	]
	}
});
