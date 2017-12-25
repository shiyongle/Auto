Ext.require(['Ext.ux.grid.UserItemdblClick','Ext.ux.grid.FieldQueryFilter']);
Ext.define('DJ.System.UserToUserSelectFrameEdit', {
	extend : 'Ext.c.SelectFrameUI',
	id : 'DJ.System.UserToUserSelectFrameEdit',
	modal : true,
	title : "关联用户页面",
	width : 850,// 230, //Window宽度
	height : 600,// 137, //Window高度
	resizable : false,
	closable : true,
	MyConfig:{
		txtLable:"用户名",   //设置文本宽label值
		selectbtnname:"分配",  //设置选择按钮text
		delbtnname:"取消",      //设置取消按钮text
		selecturl:"addUserToUser.do",          //选择按钮调用接口
		delurl:"delUserToUser.do"              //删除按钮调用接口
	},
	RightConfig : {
		width : 800,// 下拉界面
		height : 200,// 下拉界面
		url : 'getUserRightList.do', // 下拉数据来源
		objectfilterfield:"e1.fsuperuserid",
		plugins: [{
			ptype:'itemdblclick',
			callButton:'取消'
		},{
			ptype:'fieldqueryfilter'
		}],
		fields : [
			{
				name : 'fid'
			}, {
				name : 'fname'
			}],
		columns : [
		     {
		    	 text:'用户名',
		    	 flex:1,
		    	 dataIndex:'fname',
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
		url : 'getUserLeftList.do', // 下拉数据来源
		plugins: [{
			ptype:'itemdblclick',
			callButton:'分配'
		},{
			ptype:'fieldqueryfilter'
		}],
		fields : [
			{
				name : 'fid'
			},{
				name : 'fname',
				myfilterfield : 'fname',
				myfiltername : '客户名称',
				myfilterable : true
			}
		],
		columns : [
			{
				text : '客户名称',
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