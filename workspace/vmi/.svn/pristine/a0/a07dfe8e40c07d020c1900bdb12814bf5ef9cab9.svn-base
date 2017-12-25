
Ext.define('DJ.test.SelectFrameUi', {
	extend : 'Ext.c.SelectFrameUI',
	id : 'DJ.test.SelectFrameUi',
	modal : true,
	title : "用户管理编辑界面",
	width : 800,// 230, //Window宽度
	height : 600,// 137, //Window高度
	resizable : false,
	url : 'SaveProductdef.do',
	infourl : 'GetProductInfo.do',
	viewurl : 'GetProductInfo.do',
	closable : true,
	MyConfig:{
		txtLable:"陈朝测试",   //设置文本宽label值
		selectbtnname:"选择",  //设置选择按钮text
		delbtnname:"取消",      //设置取消按钮text
		selecturl:"GetQueryConfiginfo.do",          //选择按钮调用接口
		delurl:""              //删除按钮调用接口
	},
	RightConfig : {
		width : 800,// 下拉界面
		height : 200,// 下拉界面
		url : 'GetCustproductList.do', // 下拉数据来源
		objectfilterfield:"fnumber",
		fields : [ {
			name : 'fid'
		}, {
			name : 'fname',
			myfilterfield : 't_bd_Custproduct.fname', // 查找字段，发送到服务端
			myfiltername : '名称', // 在过滤下拉框中显示的名称
			myfilterable : true
		// 该字段是否查找字段
		}, {
			name : 'fnumber',
			myfilterfield : 't_bd_Custproduct.fnumber', // 查找字段，发送到服务端
			myfiltername : '编码', // 在过滤下拉框中显示的名称
			myfilterable : true
		// 该字段是否查找字段
		}, {
			name : 'fspec'
		}, {
			name : 'forderunit'
		}, {
			name : 'fcustomerid'
		}, {
			name : 'fdescription'
		}, {
			name : 'fcreatorid'
		}, {
			name : 'fcreatetime'
		}, {
			name : 'flastupdateuserid'
		}, {
			name : 'flastupdatetime'
		}

		],
		columns : [ {
			'header' : 'fid',
			'dataIndex' : 'fid',
			hidden : true,
			hideable : false,
			autoHeight : true,
			autoWidth : true,
			sortable : true
		}, {
			'header' : '产品名称',
			'dataIndex' : 'fname',
			sortable : true,
			filter : {
				type : 'string'
			}
		}, {
			'header' : '编码',
			'dataIndex' : 'fnumber',
			sortable : true,
			filter : {
				type : 'string'
			}
		}, {
			'header' : '规格',
			width : 70,
			'dataIndex' : 'fspec',
			sortable : true
		}, {
			'header' : '单位',
			width : 70,
			'dataIndex' : 'forderunit',
			sortable : true
		}, {
			'header' : '客户',
			hidden : true,
			'dataIndex' : 'fcustomerid',
			sortable : true
		}, {
			'header' : '修改时间',
			'dataIndex' : 'flastupdatetime',
			filter : {
				type : 'datetime',
				date : {
					format : 'Y-m-d'
				},
				time : {
					format : 'H:i:s A',
					increment : 1
				}
			},
			width : 140,
			sortable : true
		}, {
			'header' : '创建时间',
			'dataIndex' : 'fcreatetime',
			filter : {
				type : 'datetime',
				date : {
					format : 'Y-m-d'
				},
				time : {
					format : 'H:i:s A',
					increment : 1
				}
			},
			width : 140,
			sortable : true
		}, {
			'header' : '描述',
			hidden : true,
			'dataIndex' : 'fdescription',
			sortable : true
		} ]
	},
	LeftConfig : {
		width : 800,// 下拉界面
		height : 200,// 下拉界面
		url : 'GetCustproductList.do', // 下拉数据来源
		fields : [ {
			name : 'fid'
		}, {
			name : 'fname',
			myfilterfield : 't_bd_Custproduct.fname', // 查找字段，发送到服务端
			myfiltername : '名称', // 在过滤下拉框中显示的名称
			myfilterable : true
		// 该字段是否查找字段
		}, {
			name : 'fnumber',
			myfilterfield : 't_bd_Custproduct.fnumber', // 查找字段，发送到服务端
			myfiltername : '编码', // 在过滤下拉框中显示的名称
			myfilterable : true
		// 该字段是否查找字段
		}, {
			name : 'fspec'
		}, {
			name : 'forderunit'
		}, {
			name : 'fcustomerid'
		}, {
			name : 'fdescription'
		}, {
			name : 'fcreatorid'
		}, {
			name : 'fcreatetime'
		}, {
			name : 'flastupdateuserid'
		}, {
			name : 'flastupdatetime'
		}

		],
		columns : [ {
			'header' : 'fid',
			'dataIndex' : 'fid',
			hidden : true,
			hideable : false,
			autoHeight : true,
			autoWidth : true,
			sortable : true
		}, {
			'header' : '产品名称',
			'dataIndex' : 'fname',
			sortable : true,
			filter : {
				type : 'string'
			}
		}, {
			'header' : '编码',
			'dataIndex' : 'fnumber',
			sortable : true,
			filter : {
				type : 'string'
			}
		}, {
			'header' : '规格',
			width : 70,
			'dataIndex' : 'fspec',
			sortable : true
		}, {
			'header' : '单位',
			width : 70,
			'dataIndex' : 'forderunit',
			sortable : true
		}, {
			'header' : '客户',
			hidden : true,
			'dataIndex' : 'fcustomerid',
			sortable : true
		}, {
			'header' : '修改时间',
			'dataIndex' : 'flastupdatetime',
			filter : {
				type : 'datetime',
				date : {
					format : 'Y-m-d'
				},
				time : {
					format : 'H:i:s A',
					increment : 1
				}
			},
			width : 140,
			sortable : true
		}, {
			'header' : '创建时间',
			'dataIndex' : 'fcreatetime',
			filter : {
				type : 'datetime',
				date : {
					format : 'Y-m-d'
				},
				time : {
					format : 'H:i:s A',
					increment : 1
				}
			},
			width : 140,
			sortable : true
		}, {
			'header' : '描述',
			hidden : true,
			'dataIndex' : 'fdescription',
			sortable : true
		} ]
	}
});