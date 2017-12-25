Ext.define('DJ.System.product.RelationProduct', {
	extend : 'Ext.c.SelectFrameUI',
	id : 'DJ.System.product.RelationProduct',
	modal : true,
	title : "关联产品",
	width : 850,// 230, //Window宽度
	height : 600,// 137, //Window高度
	resizable : false,
	closable : true,
	MyConfig:{
		txtLable:"产品名称",   //设置文本宽label值
		selectbtnname:"关联",  //设置选择按钮text
		delbtnname:"取消",      //设置取消按钮text
		selecturl:"AddCustRelationProduct.do",          //选择按钮调用接口
		delurl:"DelCustRelationProduct.do"              //删除按钮调用接口
	},
	RightConfig : {
		width : 800,// 下拉界面
		height : 200,// 下拉界面
		url : 'GetCustRelationProductList.do', // 下拉数据来源
		objectfilterfield:"l.fcustid",
		fields : [{
			name : 'fid'
		}, {
			name : 'fname',
			myfilterfield : 'c.fname',
			myfiltername : '产品名称',
			myfilterable : true
		}, {
			name : 'fnumber',
			myfilterfield : 'c.fnumber',
			myfiltername : '编码',
			myfilterable : true
				}, {
		name : 'fversion'
			}, {
		name : 'fcharacter'
			}, {
		name : 'fnewtype'
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
			text : '版本',
			dataIndex : 'fversion',
			sortable : true
		}, {
			text : '特性',
			dataIndex : 'fcharacter',
			sortable : true
			}, {
			text : '产品类型',
			dataIndex : 'fnewtype',
			sortable : true
		}
	]
	},
	LeftConfig : {
		width : 800,// 下拉界面
		height : 200,// 下拉界面
		url : 'GetProductss.do', // 下拉数据来源
		fields : [{
			name : 'fid'
		}, {
			name : 'fname',
			myfilterfield : 'd.fname',
			myfiltername : '产品名称',
			myfilterable : true
		}, {
			name : 'fnumber',
			myfilterfield : 'd.fnumber',
			myfiltername : '编码',
			myfilterable : true
		},{
	name : 'fversion'
			}, {
		name : 'fcharacter'
			}, {
		name : 'fnewtype'
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
			text : '版本',
			dataIndex : 'fversion',
			sortable : true
		}, {
			text : '特性',
			dataIndex : 'fcharacter',
			sortable : true
			}, {
			text : '产品类型',
			dataIndex : 'fnewtype',
			sortable : true
		}
	]
	}
});