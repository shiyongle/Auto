Ext.define('DJ.System.log.SysLogList',{
	id:'DJ.System.log.SysLogList',
	extend : 'Ext.c.GridPanel',
	pageSize : 50,
	title : "用户操作",
	closable : true,
	url : 'getSysLogList.do',
	Delurl : "",
	EditUI : "",
	exporturl : "",// 导出为EXCEL方法
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	onload:function(){
		var id = this.id;
		Ext.getCmp(id+'.exportbutton').hide();
		Ext.getCmp(id+'.delbutton').hide();
		Ext.getCmp(id+'.editbutton').hide();
	},
	fields:[
	    {name:'fid'},
	    {
	    	name:'fsource',
	    	myfilterfield : 'fsource',
	    	myfiltername : '来源',
	    	myfilterable : true
	    },
	    {name:'fip'},
	    {
	    	name:'fuser',
	    	myfilterfield : 'fuser',
	    	myfiltername : '用户',
	    	myfilterable : true
	    },
	    {name:'ftime'},
	    {
	    	name:'faction',
	    	myfilterfield : 'faction',
	    	myfiltername : '操作',
	    	myfilterable : true
	    },
	    {name:'fdata'},
	    {name:'fsuccess'},
	    {name:'fmessage'}
	],
	initComponent: function(){
		var columns = [{
			dataIndex:'faction',
			text:'操作',
			width:110,
			sortable:true
		},{
			dataIndex:'fdata',
			text:'数据',
			width:200,
			sortable:true
		},{
			dataIndex:'ftime',
			text:'时间',
			width:200,
			sortable:true
		},{
			dataIndex:'fsource',
			text:'来源',
			sortable:true,
			width:120
		}];
		if(Ext.userIsFilter){
			var adds = [{
				dataIndex:'fsuccess',
				text:'是否成功',
				sortable:true,
				renderer: function(val){
					if(val === '0'){
						return '失败';
					}else{
						return '成功';
					}
				}
			},{
				dataIndex:'fuser',
				text:'用户',
				width:50,
				sortable:true
			},{
				dataIndex:'fip',
				text:'IP',
				sortable:true
			},{
				dataIndex:'fmessage',
				text:'错误信息',
				sortable:true,
				flex: 1
			}];
			columns = columns.concat(adds);
		};
		Ext.apply(this,{
			columns: columns
		});
		this.callParent(arguments);
	}

});
