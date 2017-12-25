Ext.define('DJ.System.BoxpileList', {
	extend : 'Ext.c.GridPanel',
	title:'箱型',
	id:'DJ.System.BoxpileList',
	url : 'getBoxpileList.do',
	Delurl : "delBoxpile.do",
	EditUI : "DJ.System.BoxpileEdit",
	closable : true,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	fields : [{
		name:'fid'
	},{
		name:'fname'
	},{
		name:'fnumber'
	}],
	columns:[{
		text:'fid',
		dataIndex:'fid',
		hidden:true
	},{
		text:'箱型名称',
		dataIndex:'fname'
	},{
		text:'编号',
		dataIndex:'fnumber',
		flex:1
	}]
})