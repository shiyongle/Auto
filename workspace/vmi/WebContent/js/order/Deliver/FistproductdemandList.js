Ext.define('DJ.order.Deliver.FistproductdemandList',{
	id:'DJ.order.Deliver.FistproductdemandList',
	extend : 'Ext.c.GridPanel',
	pageSize : 50,
	title : "包装需求",
//	closable : true,// 是否现实关闭按钮,默认为false
	url : 'getFirstproductdemandList.do',
	Delurl : "DelFirstproductdemandList.do",
	EditUI : "DJ.order.Deliver.ProductDemandEdit",
	//exporturl : "exportProductDemand.do",// 导出为EXCEL方法
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	
		
	},
//	onload:function(){
//Ext.getCmp('DJ.order.Deliver.FistproductdemandList.exportbutton').setVisible(false);
//	},
	Action_AfterAddButtonClick : function(EditUI) {

		Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.fnumber').setReadOnly(true);
		
		Ext.Ajax.request({
			url:'getCreateidAndFnumber.do',
			success:function(response){
				var obj = Ext.decode(response.responseText);
				if(obj.success){
					Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.fid').setValue(obj.data[0].fid);
					Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.fnumber').setValue(obj.data[0].fnumber);
					Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.flinkman').setValue(obj.data[0].username);
					Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.flinkphone').setValue(obj.data[0].tel);
				
				}else{
					Ext.Msg.alert('错误',obj.msg);
				}
			}
		});
		var field = Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.fsupplierid');
		field.getPicker().getStore().load();
	
	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件
		
		verification("edit");
		
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
//		Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.true').setValue("true");
	Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.fnumber').setReadOnly(true);
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件
		verification("del");
	},
	
		fields:[{name:'fid'},{name:'fnumber',myfilterfield : 'f.fnumber',myfiltername : '需求编号',myfilterable : true},
		        {name:'fname',myfilterfield : 'f.fname',myfiltername : '需求名称',myfilterable : true},{name:'fdescription'},{name:'fboxlength'},
		        {name:'fboxwidth'},{name:'fboxheight'},{name:'fboardlength'},{name:'fboardwidth'},
		        {name:'fsuppliername',myfilterfield : 'sp.fname',myfiltername : '制造商',myfilterable : true},{name:'fsupplierid'},
		        {name:'fcreatid'},{name:'fcreatetime'},{name:'fupdateuserid'},{name:'fupdatetime'},
		        {name:'fauditorid'},{name:'fauditortime',myfilterfield : 'f.fauditortime',myfiltername : '发布时间',myfilterable : true},{name:'isfauditor'},{name:'fcustomerid'},
		        {name:'fsupplierid'},{name:'falloted'},{name:'fallotor'},{name:'fallottime'},{name:'freceived'},
		        {name:'freceiver',myfilterfield : 'u2.fname',myfiltername : '设计师',myfilterable : true},{name:'freceiverTel'},
		        {name:'freceivetime',myfilterfield : 'f.freceivetime',myfiltername : '接收时间',myfilterable : true},{name:'fname'},{name:'ftype'},{name:'fcostneed'},{name:'fiszhiyang'},{name:'famount'}
		        ,{name:'foverdate'},{name:'farrivetime'},{name:'fboxpileid'},{name:'fmaterial'},{name:'fcorrugated'},{name:'fprintcolor'},{name:'fprintbarcode'},{name:'funitestyle'}
		        ,{name:'fprintstyle'},{name:'fsurfacetreatment'},{name:'fpackstyle'},{name:'fpackdescription'},{name:'fisclean'},{name:'fispackage'},{name:'fpackagedescription'},{name:'fislettering'},{name:'fletteringescription'},
		        {name:'fstate'},{name:'fisaccessory'},{name:'flinkman'},{name:'flinkphone'},{name:'cname',myfilterfield : 'c.fname',myfiltername : '客户名称',myfilterable : true},{name:'fgroupid'},{name:'fqq'},{name:'fisdemandpackage'}],
		columns:[{
			xtype: 'rownumberer',
			text:'序号',
			width:30
		},{
			text:'fid',
			dataIndex:'fid',
			hidden:true,
			hideable : false
		},{
			text:'需求编号',
			dataIndex:'fnumber'
		},{
			text:'客户名称',
			dataIndex:'cname',
			width:200
		},{
			text:'需求名称',
			dataIndex:'fname',
			width:230
		},{
			text:'需求状态',
			dataIndex:'fstate',
			itemId:'fstate'
		},{
			text:'类型',
			dataIndex:'fisdemandpackage',
			renderer:function(value){
				if(value==="0"){
					return "高端设计";
				}else if(value==="1") {
					return "需求包";
				}
				return ""
				
			}
		},{
			text:'制造商',
			dataIndex:'fsuppliername',
			itemId:'fsuppliername',
			editor:{
				xtype:'combo',
				displayField:'fname',
				valueField:'fid',
				editable:false,
				store:Ext.create('Ext.data.Store',{
					fields: ['fid', 'fname'],
					proxy:{
						type:'ajax',
						url: 'getSupplierListOfCustomer.do',
				         reader: {
				             type: 'json',
				             root: 'data'
				         }
					}
				}),
				listeners:{
					select:function(combo,records){
						var grid = combo.up('grid');
						grid.chooseSupplierModel = records[0];
					}
				}
			}
		},{
			text:'是否制样',
			dataIndex:'fiszhiyang',
			itemId:'fiszhiyang',
			renderer:function(value){
				if(value=='true'){
					return "是";
				}else{
					return "否";
				}
			}
		},{
			text:'方案入库日期',
			dataIndex:'foverdate',
			width:120
		},{
			text:'需求描述',
			dataIndex:'fdescription',
			renderer:function(value){
				if(value.length>10){
					return value.substring(0,5)+'...';
				}else{
					return value;
				}
			}
//		},{
//			text:'创建人',
//			dataIndex:'fcreatid',
//			itemId:'fcreatid'
//		},{
//			text:'创建时间',
//			dataIndex:'fcreatetime',
//			itemId:'fcreatetime'
		},{
			text:'发布人',
			dataIndex:'fauditorid',
			itemId:'fauditorid'
		},{
			text:'发布时间',
			dataIndex:'fauditortime',
			itemId:'fauditortime',
			flex:1
		},{
			text:'接收时间',
			dataIndex:'freceivetime',
			itemId:'freceivetime',
			flex:1
		},{
			text:'联系人',
			dataIndex:'flinkman',
			itemId:'flinkman',
			hidden:true
		},{
			text:'联系电话',
			dataIndex:'flinkphone',
			itemId:'flinkphone',
			hidden:true
		},{
			text:'是否有附件',
			dataIndex:'fisaccessory',
			itemId:'fisaccessory',
			renderer:function(val){
				if(val=='true'){
					return '有';
				}else{
					return '没有';
				}
			},
			hidden:true
		},{
			text:'是否接收',
			dataIndex:'freceived',
			itemId:'freceived',
			flex:1,
			renderer:function(val){
				if(val=='true'){
					return '已接收';
				}else{
					return '未接收';
				}
			},
			hidden:true,
			hideable : false
		},{
			text:'设计师',
			dataIndex:'freceiver',
			itemId:'freceiver',
			renderer : function (v) {
			
				if (v == null || v == "" || Ext.String.trim(v) == "") {
				
					return "设计咨询"
					
				} else {
				
					
					return v;
				
				}
				
			}
		},{
			text:'设计师电话',
			dataIndex:'freceiverTel',
			itemId:'freceiverTel',
			renderer : function (v) {
			
				if (v == null || v == "" || Ext.String.trim(v) == "") {
				
					return "0577-55575290"
					
				} else {
				
					return v;
					
				}
				
				
			}
			},{
				text:'fgroupid',
				dataIndex:'fgroupid',
				hidden:true
			},{
				text:'在线沟通',
				dataIndex:'fqq',
				renderer:function(val){
					if(!Ext.isEmpty(val)){
					return '<a  onMouseDown="javascript:window.open(&quot;http://wpa.qq.com/msgrd?v=3&uin='+val+'&site=qq&menu=yes&quot;,&quot;_blank&quot;);" ><img border="0" src="http://wpa.qq.com/pa?p=2:'+val+':51" alt="点击这里给我发消息" title="点击这里给我发消息"/></a>';
					}
				}
//			},{
//				text:'在线沟通',
//				dataIndex:'fgroupid',
//				renderer:function(val){
//					if(!Ext.isEmpty(val)){
//						var urlT='';
//						Ext.Ajax.request({
//							async: false,
//							url : 'OnlineConversation.do',
//							success : function(response, option) {
//								var obj = Ext.decode(response.responseText);
//								if (obj.success == true) {
//									if(val != '' && val != null){			
//										urlT = "djbz:sendmsg?uid=" + obj.data.fimid+'&g_target='+val
//										+ "&sessionId=" + obj.data.sessionid;
//									}else{
//										urlT = "djbz:sendmsg?uid=" + obj.data.fimid
//										+ "&sessionId=" + obj.data.sessionid;
//									}
//				
//								} else {
//									Ext.MessageBox.alert('错误', obj.msg);
//								}
//							}
//						});
//						return '<a href="'+urlT+'">在线沟通</a>';
//					}
//				}
			}]
})

function verification(object){
	var grid = Ext.getCmp("DJ.order.Deliver.FistproductdemandList");
	if(grid==undefined){
		grid = Ext.getCmp("DJ.order.Deliver.SaleFistproductdemandList");
	}
	var record = grid.getSelectionModel().getSelection();
	if(object=="edit"){
		if(record.length!=1){
			throw "请选择一条记录!";
		}
	}else if(object=="del"){
		if(record.length<1){
			throw "请选择一条记录!";
		}
	}
	for(var i= 0;i<record.length;i++){
		if(record[i].get("fstate")=="关闭"){
			throw "已关闭的产品需求不能操作!";
		}
		if(record[i].get("fstate")=="已接收"){
			throw "需求已接收，不能操作!";
		}
		
		if (record[i].get("isfauditor")== "true") {
			throw "当前记录已发布，不能修改和删除!";
		}
	}
	
	
}