Ext.define('DJ.System.FrienduserList',{
	extend : 'Ext.c.GridPanel',
	id : "DJ.System.FrienduserList",
	title : "好友设置",
	pageSize : 100,
	url : 'getFrieduserList.do',
	Delurl : "delFrienduser.do",
	EditUI : "",
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		var me = this;
		var indexs = [0,1,2,6];
		indexs.forEach(function(item,index,allitems){
			me.query('button')[indexs[index]].hide();
		})
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件
	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	custbar : [{
	text : '关联好友',
	height : 30,
	handler : function() {
//		win.settxtid(record[0].get("fid"));
//		win.settxtvalue(record[0].get("ufname"));
		var me = this;
		var win = Ext.create('DJ.System.FrienduserRelationUserEdit');
		Ext.getCmp('DJ.System.FrienduserRelationUserEditTxtName').hide();
		win.query('grid')[1].getStore().on('load',function(){
			me.up('grid').getStore().load();
		})
		win.show();
	}
	}],
	fields : ['fid','userid','frienduserid','ufname','fcustomername','femail','ftel',{
		name : 'friendname',
		myfilterfield : 'u2.fname',
		myfiltername : '好友名称',
		myfilterable : true
		}],
	columns : [Ext.create('DJ.Base.Grid.GridRowNum'),{
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true

	},{
		'header' : '好友名称',
		'dataIndex' : 'friendname'
	},{
		'header' : '客户名称',
		'dataIndex' : 'fcustomername'
	},{
		'header' : '邮箱',
		'dataIndex' : 'femail'
	},{
		'header' : '手机',
		'dataIndex' : 'ftel'
	}]
	
})