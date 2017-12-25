Ext.define('DJ.System.FrienduserRelationUserEdit', {
	extend : 'Ext.c.SelectFrameUI',
	id : 'DJ.System.FrienduserRelationUserEdit',
	modal : true,
	title : "关联好友页面",
	width : 850,// 230, //Window宽度
	height : 600,// 137, //Window高度
	resizable : false,
	closable : true,
	MyConfig:{
//		txtLable : "用户名",   //设置文本宽label值
		selectbtnname : "关联",  //设置选择按钮text
		delbtnname : "取消",      //设置取消按钮text
		selecturl : "addFrienduser.do",          //选择按钮调用接口
		delurl : "delFrienduser.do"              //删除按钮调用接口
	},
	RightConfig : {
		width : 800,// 下拉界面
		height : 200,// 下拉界面
		title : '关联好友',
		url : 'getFrieduserList.do', // 下拉数据来源
//		objectfilterfield:"e1.fsuperuserid",
		fields : [
			{
				name : 'fid'
			}, {
				name : 'friendname'
			}],
		columns : [
		     {
		    	 text:'用户名',
		    	 flex:1,
		    	 dataIndex:'friendname',
		    	 sortable:true,
		    	 filter:{
		    		 type:'string'
		    	 }
		     }
		]
	},
	LeftConfig : {
		width : 800,// 下拉界面
		height : 200,// 下拉界面
		title : '所有用户',
		url : 'getUsersList.do', // 下拉数据来源
		fields : [
			{
				name : 'fid'
			},{
				name : 'fname',
				myfilterfield : 'fname',
				myfiltername : '用户名称',
				myfilterable : true
			}
		],
		columns : [
			{
				text : '用户名称',
				dataIndex : 'fname',
				sortable : true,
				flex : 1,
				filter : {
					type : 'string'
				}
			}
		]
	}
});