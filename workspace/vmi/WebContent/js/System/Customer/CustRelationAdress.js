Ext.define('DJ.System.Customer.CustRelationAdress', {
	
	extend : 'Ext.c.SelectFrameUI',
	id : 'DJ.System.Customer.CustRelationAdress',
	modal : true,
	title : "关联地址",
	width : 900,// 230, //Window宽度
	height : 600,// 137, //Window高度
	resizable : false,
	closable : true,
	MyConfig:{
		txtLable:"客户名称",   //设置文本宽label值
		selectbtnname:"关联",  //设置选择按钮text
		delbtnname:"取消",      //设置取消按钮text
		selecturl:"AddCustRelationAdress.do",          //选择按钮调用接口
		delurl:"DelCustRelationAdress.do"              //删除按钮调用接口
	},
	LeftConfig : {
		width : 800,// 下拉界面
		height : 200,// 下拉界面
		id : 'DJ.System.Customer.CustRelationAdress.fcustomerid',
		url : 'GetAddressLists.do', // 下拉数据来源
		fields : [{
			name : 'fid'
		}, {
			name : 'fname',
			myfilterfield : 'tba.fname',
			myfiltername : '名称',
			myfilterable : true
		}, {
			name : 'fnumber',
			myfilterfield : 'tba.fnumber',
			myfiltername : '编码',
			myfilterable : true
		}, {
			name : 'fcreatorid'
		}, {
			name : 'flastupdateuserid'
		}, {
			name : 'fcontrolunitid'
		}, {
			name : 'fdetailaddress'
		}, {
			name : 'fcountryid'
		}, {
			name : 'fcityidid'
		}, {
			name : 'femail'
		}, {
			name : 'flinkman'
		}, {
			name : 'fphone'
		}, {
			name : 'fprovinceid'
		}, {
			name : 'fdistrictidid'
		}, {
			name : 'fpostalcode'
		}, {
			name : 'ffax'
		}, {
			name : 'fcreatetime'
		}, {
			name : 'flastupdatetime'
		}, {
			name : 'fcustomerid'
		}, {
			name : 'customerName'
		}
		
		],
columns : [{
			'header' : 'fid',
			'dataIndex' : 'fid',
			hidden : true,
			hideable : false,
			sortable : true

		},{
			'header' : 'fcreatorid',
			'dataIndex' : 'fcreatorid',
			hidden : true,
			hideable : false,
			sortable : true

		}, {
			'header' : 'flastupdateuserid',
			'dataIndex' : 'flastupdateuserid',
			hidden : true,
			hideable : false,
			sortable : true

		}, {
			'header' : 'fprovinceid',
			'dataIndex' : 'fprovinceid',
			hidden : true,
			hideable : false,
			sortable : true

		}, {
			'header' : 'fcountryid',
			'dataIndex' : 'fcountryid',
			hidden : true,
			hideable : false,
			sortable : true

		}, {
			'header' : 'fcityidid',
			'dataIndex' : 'fcityidid',
			hidden : true,
			hideable : false,
			sortable : true

		}, {
			'header' : 'fdistrictidid',
			'dataIndex' : 'fdistrictidid',
			hidden : true,
			hideable : false,
			sortable : true

		}, {
			'header' : 'fcontrolunitid',
			'dataIndex' : 'fcontrolunitid',
			hidden : true,
			hideable : false,
			sortable : true

		}, {
			'header' : '地址名称',
			'dataIndex' : 'fname',
			sortable : true
		}, {
			'header' : '客户',
			'dataIndex' : 'customerName',
			sortable : true
		}, {
			'header' : '地址编码',
			'dataIndex' : 'fnumber',
			sortable : true
		}, {
			'header' : '邮政编码',
			'dataIndex' : 'fpostalcode',
			sortable : true
		}, {
			'header' : '邮箱',
			'dataIndex' : 'femail',
			sortable : true
		}, {
			'header' : '联系人',
			'dataIndex' : 'flinkman',
			sortable : true
		},{
			'header' : '电话',
			'dataIndex' : 'fphone',
			sortable : true
		},{
			'header' : '传真',
			'dataIndex' : 'ffax',
			sortable : true
		},{
			'header' : '详细地址',
			'dataIndex' : 'fdetailaddress',
			sortable : true
		}, {
			'header' : '创建时间',
			'dataIndex' : 'fcreatetime',
			width : 200,
			sortable : true
		}, {
			'header' : '修改时间',
			'dataIndex' : 'flastupdatetime',
			width : 200,
			sortable : true
		}]
	},
	RightConfig : {
		width : 800,// 下拉界面
		height : 200,// 下拉界面
		url : 'getCustRelationAdressList.do', // 下拉数据来源
		objectfilterfield:"ca.fcustomerid",
		fields : [{
			name : 'fid'
		}, {
			name : 'fname',
			myfilterfield : 'tba.fname',
			myfiltername : '名称',
			myfilterable : true
		}, {
			name : 'fnumber',
			myfilterfield : 'tba.fnumber',
			myfiltername : '编码',
			myfilterable : true
		}, {
			name : 'fcreatorid'
		}, {
			name : 'flastupdateuserid'
		}, {
			name : 'fcontrolunitid'
		}, {
			name : 'fdetailaddress'
		}, {
			name : 'fcountryid'
		}, {
			name : 'fcityidid'
		}, {
			name : 'femail'
		}, {
			name : 'flinkman'
		}, {
			name : 'fphone'
		}, {
			name : 'fprovinceid'
		}, {
			name : 'fdistrictidid'
		}, {
			name : 'fpostalcode'
		}, {
			name : 'ffax'
		}, {
			name : 'fcreatetime'
		}, {
			name : 'flastupdatetime'
		}, {
			name : 'fcustomerid'
		}, {
			name : 'customerName'
		}
		],
columns : [{
			'header' : 'fid',
			'dataIndex' : 'fid',
			hidden : true,
			hideable : false,
			sortable : true

		},{
			'header' : 'fcreatorid',
			'dataIndex' : 'fcreatorid',
			hidden : true,
			hideable : false,
			sortable : true

		}, {
			'header' : 'flastupdateuserid',
			'dataIndex' : 'flastupdateuserid',
			hidden : true,
			hideable : false,
			sortable : true

		}, {
			'header' : 'fprovinceid',
			'dataIndex' : 'fprovinceid',
			hidden : true,
			hideable : false,
			sortable : true

		}, {
			'header' : 'fcountryid',
			'dataIndex' : 'fcountryid',
			hidden : true,
			hideable : false,
			sortable : true

		}, {
			'header' : 'fcityidid',
			'dataIndex' : 'fcityidid',
			hidden : true,
			hideable : false,
			sortable : true

		}, {
			'header' : 'fdistrictidid',
			'dataIndex' : 'fdistrictidid',
			hidden : true,
			hideable : false,
			sortable : true

		}, {
			'header' : 'fcontrolunitid',
			'dataIndex' : 'fcontrolunitid',
			hidden : true,
			hideable : false,
			sortable : true

		}, {
			'header' : '地址名称',
			'dataIndex' : 'fname',
			sortable : true
		}, {
			'header' : '客户',
			'dataIndex' : 'customerName',
			sortable : true
		}, {
			'header' : '地址编码',
			'dataIndex' : 'fnumber',
			sortable : true
		}, {
			'header' : '邮政编码',
			'dataIndex' : 'fpostalcode',
			sortable : true
		}, {
			'header' : '邮箱',
			'dataIndex' : 'femail',
			sortable : true
		}, {
			'header' : '联系人',
			'dataIndex' : 'flinkman',
			sortable : true
		},{
			'header' : '电话',
			'dataIndex' : 'fphone',
			sortable : true
		},{
			'header' : '传真',
			'dataIndex' : 'ffax',
			sortable : true
		},{
			'header' : '详细地址',
			'dataIndex' : 'fdetailaddress',
			sortable : true
		}, {
			'header' : '创建时间',
			'dataIndex' : 'fcreatetime',
			width : 200,
			sortable : true
		}, {
			'header' : '修改时间',
			'dataIndex' : 'flastupdatetime',
			width : 200,
			sortable : true
		}]
	}
});
