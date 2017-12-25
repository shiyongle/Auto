Ext.define('DJ.System.ExcelDataList',{
	id:'DJ.System.ExcelDataList',
	extend : 'Ext.c.GridPanel',
	pageSize : 50,
	title : "数据导入设置",
	closable : true,
	url : 'getExcelDataList.do',
	Delurl : "delExcelDataInfo.do",
	EditUI : "DJ.System.ExcelDataEdit",
	exporturl : "",// 导出为EXCEL方法
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	onload:function(){
		Ext.getCmp('DJ.System.ExcelDataList.exportbutton').hide();
	},
	Action_AfterAddButtonClick : function(me) {
		if(me.getStore().getAt(0).get('fcustomername')){
			var win = Ext.getCmp(me.EditUI);
			win.down('button[text=设置默认模板]').show();
		}
	},
	Action_AfterEditButtonClick : function(me, record) {
		if(this.getSelectionModel().getSelection()[0].get('fcustomername')==''){
			var win = Ext.getCmp(me.EditUI);
			win.down('textfield[name=fcustomerid]').setDisabled(true);
		}
	},
	fields:[
	    {name:'fid'},
	    {name:'fnumber'},
	    {name:'fcustomername'},
	    {name:'ftarget'},
	    {name:'fendtext'},
	    {name:'fcreatetime'},
	    {name:'fcreatorid'}
	],
	columns:[{
		dataIndex:'fnumber',
		text:'采购订单号',
		width:200,
		sortable:true
	},{
		dataIndex:'fcustomername',
		text:'客户',
		width:200,
		sortable:true
	},{
		dataIndex:'fendtext',
		text:'终止文本',
		width:200,
		sortable:true
	},{
		dataIndex:'fcreatetime',
		text:'创建时间',
		width:200,
		sortable:true
	},{
		dataIndex:'fcreatorid',
		text:'创建人',
		sortable:true,
		flex:1
	}]

});
