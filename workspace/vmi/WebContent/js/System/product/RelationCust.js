Ext.define('DJ.System.product.RelationCust', {
	extend : 'Ext.c.SelectFrameUI',
	id : 'DJ.System.product.RelationCust',
	modal : true,
	title : "关联客户产品",
	width : 850,// 230, //Window宽度
	height : 600,// 137, //Window高度
	resizable : false,
	closable : true,
	MyConfig:{
		txtLable:"产品名称",   //设置文本宽label值
		selectbtnname:"关联",  //设置选择按钮text
		delbtnname:"取消",      //设置取消按钮text
		selecturl:"AddProductRelationCust.do",          //选择按钮调用接口
		delurl:"DelProductRelationCust.do"              //删除按钮调用接口
	},
	RightConfig : {
		width : 800,// 下拉界面
		height : 200,// 下拉界面
		url : 'GetProductRelationCustList.do', // 下拉数据来源
		objectfilterfield:"l.fproductdefid",
		fields : [{
			name : 'fid'
		}, {
			name : 'fname',
			myfilterfield : 'c.fname',
			myfiltername : '客户产品名称',
			myfilterable : true
		}, {
			name : 'fnumber',
			myfilterfield : 'c.fnumber',
			myfiltername : '编码',
			myfilterable : true
		}, {
		name : 'fspec'
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
			text : '编号',
			dataIndex : 'fnumber',
			sortable : true
		}, {
			text : '名称',
			dataIndex : 'fname',
			sortable : true
		}, {
			text : '规格',
			dataIndex : 'fspec',
			sortable : true

		}, {
			text : '备注',
			dataIndex : 'fdescription',
			sortable : true
		}
	]
	},
	LeftConfig : {
		width : 800,// 下拉界面
		height : 200,// 下拉界面
		url : 'GetCustproductList.do', // 下拉数据来源
		fields : [{
			name : 'fid'
		}, {
			name : 'fname',
			myfilterfield : 'fname',
			myfiltername : '客户产品名称',
			myfilterable : true
		}, {
			name : 'fnumber',
			myfilterfield : 'fnumber',
			myfiltername : '编码',
			myfilterable : true
	}, {
		name : 'fspec'
	}, {
		name : 'fname'
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
			text : '编号',
			dataIndex : 'fnumber',
			sortable : true
		}, {
			text : '名称',
			dataIndex : 'fname',
			sortable : true
		}, {
			text : '规格',
			dataIndex : 'fspec',
			sortable : true
		}, {
			text : '备注',
			dataIndex : 'fdescription',
			sortable : true
		}
	]
	}
});
