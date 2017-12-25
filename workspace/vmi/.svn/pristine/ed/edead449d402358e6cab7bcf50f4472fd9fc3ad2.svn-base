Ext.define('appraise', {
    extend: 'Ext.data.Model',
    fields: [
        { name:'fid', type:'string' },
        { name:'fbasicappraise'},
        { name:'fdescription' },
        { name:'fappraiseman'},
        { name:'fappraisetime' },
        { name:'fschemedesignid'}
    ]
});
var imageTpl = new Ext.XTemplate(
    '<tpl for=".">',
        '<div style="margin-bottom: 10px;" class="thumb-wrap">',
          '<span>评价人:{fappraiseman}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>评价时间:{fappraisetime}</span><br/>',
//          '<span>基本描述:{fbasicappraise}</span><br/>',
          '<tpl if="fbasicappraise==0">',
          '<span>基本描述:非常满意</span><br/>',
          '<tpl elseif="fbasicappraise==1">',
          '<span>基本描述:满意</span><br/>',
          '<tpl else>',
          '<span>基本描述:不满意</span><br/>',
          '</tpl>',
          '<span>详细描述:{fdescription}</span><br/>',
          '<span>—————————————————————————————————————————————————————————————</span>',
        '</div>',
    '</tpl>',
    {
    	 disableFormats: true,
    	 isAppraise: function(fbasicappraise){
             if(fbasicappraise=0){
            	 return "非常满意"
             }else if(fbasicappraise==1){
            	 return "满意"
             }else{
            	 return "不满意"
             }
          }
    }
);
var store = Ext.create('Ext.data.Store', {
//    autoLoad: true,
    model:'appraise',
    proxy: {
        type: 'ajax',
        url: 'getAppraiseListByfid.do',
        reader: {
            type: 'json',
            root: 'data'
        }
    }
});
Ext.define('DJ.order.Deliver.AppraiseList',{
	title:'方案评价界面',
	id:'DJ.order.Deliver.AppraiseList',
	extend:'Ext.form.Panel',
	bodyPadding : '10 15',
	autoScroll:true,
//	viewurl:'getAppraiseListByfid.do',
	schemeDensignId:'',
	initComponent:function(){
		var me = this;
		Ext.applyIf(me,{
			items:[Ext.create('Ext.view.View', {
				
				deferEmptyText:false,
				emptyText:'<b>没有评价<b>',
				id:'DJ.order.Deliver.AppraiseList.view',
				minWidth:300,
				maxWidth:500,
			    store: store,
			    tpl: imageTpl
			})]
		})
		this.callParent(arguments);
	}
})


//{
//		layout:{
//			type:'hbox'
//		},
//		baseCls:'x-plain',
//		frame:true,
//		height:30,
//		items:[{
//			xtype:'displayfield',
//			fieldLabel:'评价人',
//			name:'fappraiseman'
//		},{
//			xtype:'displayfield',
//			fieldLabel:'评价时间',
//			name:'fappraisetime'
//			
//		}]
//	},{
//		xtype:'displayfield',
//		fieldLabel:'基本评价',
//		name:'fbasicappraise'
//	},{
//		xtype:'displayfield',
//		fieldLabel:'详细描述',
//		name:'fdescription',
//		width:250,
//		height:30
//	},{
//		xtype:'displayfield',
//		fieldLabel:'————————————————————————',
//		labelSeparator : ''
//	}