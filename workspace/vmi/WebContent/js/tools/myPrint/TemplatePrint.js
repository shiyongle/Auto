Ext.define('appraise', {
    extend: 'Ext.data.Model',
    fields: [
        { name:'fid', type:'string' },
        { name:'filename'},
        { name:'sname'},
        { name:'fname'},
        { name:'famount'},
        { name:'fcreatetime'},
        { name:'farrivetime'},
        { name:'flinkman'},
        { name:'flinkphone'},
        { name:'fdescription'}
    ]
});
var imageTpl = new Ext.XTemplate(
    '<tpl for=".">',
        '<div style="{[xindex <= xcount-1 ? "margin-bottom: 10px;page-break-after: always;" : "margin-bottom: 10px"]}"  class="thumb-wrap">',// style="margin-bottom: 10px;page-break-after: always;"
        '<table  style="border-collapse:collapse;" width="90%" height="100%" border=1 align="center">',
        '<tr><td  height="50" colspan=2 align="center"><B>包装设计订单</B></td></tr>',
        '<tr><td  height="50" width="50%">&nbsp;制造商名称：{sname}</td><td>&nbsp;需求名称：{fname}</td></tr>',
        '<tr><td height="50" width="50%">&nbsp;订单数量：{famount}</td><td></td></tr>',
        '<tr><td height="50" width="50%">&nbsp;发布日期：{fcreatetime}</td><td>&nbsp;发货日期：{farrivetime}</td></tr>',
        '<tr><td height="50" width="50%">&nbsp;联系人：{flinkman}</td><td>&nbsp;联系电话：{flinkphone}</td></tr>',
        '<tr><td colspan=2 height="100" valign="top">&nbsp;附件名称：{filename}</td></tr>',
        '<tr><td colspan=2 height="200" valign="top">&nbsp;需求描述：{fdescription}</td></tr>',
        '</table>',
        '</div>',
    '</tpl>'
);
var store = Ext.create('Ext.data.Store', {
    model:'appraise',
    proxy: {
        type: 'ajax',
        url: 'productDemanPrint.do',
        reader: {
            type: 'json',
            root: 'data'
        }
    }
});
Ext.define('DJ.tools.myPrint.TemplatePrint',{
//	title:'模板打印界面',
	id:'DJ.tools.myPrint.TemplatePrint',
	extend:'Ext.c.BaseEditUI',
	bodyPadding : '10 15',
	autoScroll:true,
	frame:false,
	width : "100%",
	height : 700,
	myStore:store,
	myTpl: imageTpl,
	initComponent:function(){
		var me = this;
		Ext.applyIf(me,{
			items:[Ext.create('Ext.view.View', {
				deferEmptyText:false,
				id:'DJ.tools.myPrint.TemplatePrint.view',
			    store: me.myStore,
			    tpl: me.myTpl
			})]
		})
		this.callParent(arguments);
	}
})
