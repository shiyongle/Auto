Ext.define('DJ.order.saleOrder.SchemeDesignList',{
//	id:'DJ.order.saleOrder.SchemeDesignList',
	extend : 'Ext.c.GridPanel',
	pageSize : 50,
	title : "方案设计",
//	closable : true,// 是否现实关闭按钮,默认为false
	url : 'getSchemeDesignList.do',
	Delurl : "DelSchemeDesignList.do",
	EditUI : "DJ.order.saleOrder.SchemeDesignEdit",
	exporturl : "",// 导出为EXCEL方法
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	onload:function(){
		var store = this.store;
		store.setDefaultfilter([{
			myfilterfield : "s.ffirstproductid",
			CompareType : " = ",
			type : "string",
			value : "-1"
		}]);
		store.setDefaultmaskstring(" #0 ");
		store.loadPage(1);
		this.down('button[text=导出Excel]').hide();
	
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件
//		verification("edit");
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
		Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.fnumber').setReadOnly(true);
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件
		if(me.getSelectionModel().getSelection().length>1){
			throw "只能选择一条记录进行删除！";
		}
	},
		fields:[{name:'fid'},{name:'fdescription'},{name:'ffirstproductid'},
		        {name:'fname',myfilterfield : 'fname',myfiltername : '方案名称',myfilterable : true},
		        {name:'fnumber',myfilterfield : 'fnumber',myfiltername : '方案编号',myfilterable : true},
		        {name:'fcustomer',myfilterfield : 'c.fname',myfiltername : '客户名称',myfilterable : true},{name:'fsupplier'},{name:'fcreatid'},
		        {name:'fcreator'},{name:'fcreatetime'},{name:'fconfirmed'},'coname','fconfirmtime','utype',{name:'fgroupid'},'sfid','fauditorid','fqq'],
		columns:[
			{
			xtype: 'rownumberer',
			text:'序号',
			width:30
		},{
			text:'fgroupid',
			dataIndex:'fgroupid',
			hidden:true
		},{
			text:'fid',
			dataIndex:'fid',
			hidden:true
		},{
			text:'方案编号',
			dataIndex:'fnumber'
		},{
			text:'方案名称',
			dataIndex:'fname'
		},{
			text:'客户名称',
			dataIndex:'fcustomer',
			width : 160
		},{
			text:'制造商',
			dataIndex:'fsupplier'
		},{
			text:'创建人',
			dataIndex:'fcreator'
		},{
			text:'创建时间',
			dataIndex:'fcreatetime',
			width : 160,
			renderer:function(val){
				return val.substring(0,16);
			}
		},{
			text:'审核人',
			dataIndex:'fauditorid',
			width : 160,
			renderer:function(val,metDate,record){
				if(Ext.isEmpty(record.get('sfid'))){
					return '一 一';
				}else{
					return val;
				}
			}
		},{
			text:'是否确认',
			dataIndex:'fconfirmed',
			flex:1,
			renderer : function(value) {
				if (value == '1') {
					return '是';
				} else {
					return '否';
				}
			}
		},{
			text:'确认人',
			flex:1,
			dataIndex:'coname',
			itemId:'coname',
			renderer:function(val,record){
				
				var type = '';
				switch(record.record.get('utype')){
					case '0' : type ='客户';break
					case '1' : type ='制造商';break
					case '2' : type ='平台';break
				}
				if(Ext.isEmpty(val)){
					return '';
				}
				return type+'-'+val;
			}
		},{
			text:'确认时间',
			flex:1,
			dataIndex:'fconfirmtime',
			renderer:function(val){
				return val.substring(0,16);
			}
		},{
				text:'在线沟通',
				dataIndex:'fqq',
				renderer:function(val){
					if(!Ext.isEmpty(val)){
					return '<a  onMouseDown="javascript:window.open(&quot;http://wpa.qq.com/msgrd?v=3&uin='+val+'&site=qq&menu=yes&quot;,&quot;_blank&quot;);"><img border="0" src="http://wpa.qq.com/pa?p=2:'+val+':51" alt="点击这里给我发消息" title="点击这里给我发消息"/></a>';
					}
				}
//		},{
//			text:'在线沟通',
//			flex:1,
//			dataIndex:'fgroupid',
//			listeners : {
//				focus : function(){
//					
//				var urlT='';
//				var val = this.getValue();
//					Ext.Ajax.request({
//					async: false,
//					url : 'OnlineConversation.do',
//					success : function(response, option) {
//						var obj = Ext.decode(response.responseText);
//						if (obj.success == true) {
//							if(val!=null && val!=''){
//								urlT = "djbz:sendmsg?uid=" + obj.data.fimid+'&g_target='+val
//									+ "&sessionId=" + obj.data.sessionid;
//							}else{
//								urlT = "djbz:sendmsg?uid=" + obj.data.fimid
//									+ "&sessionId=" + obj.data.sessionid;
//							}
//						} else {
//							Ext.MessageBox.alert('错误', obj.msg);
//						}
//					}
//				});
//				return '<a href="'+urlT+'">在线沟通</a>';
//			
//				}
//			},
//			renderer:function(val){
//				var urlT='';
//				
//					Ext.Ajax.request({
//					async: false,
//					url : 'OnlineConversation.do',
//					success : function(response, option) {
//						var obj = Ext.decode(response.responseText);
//						if (obj.success == true) {
//							if(val!=null && val!=''){
//								urlT = "djbz:sendmsg?uid=" + obj.data.fimid+'&g_target='+val
//									+ "&sessionId=" + obj.data.sessionid;
//							}else{
//								urlT = "djbz:sendmsg?uid=" + obj.data.fimid
//									+ "&sessionId=" + obj.data.sessionid;
//							}
//						} else {
//							Ext.MessageBox.alert('错误', obj.msg);
//						}
//					}
//				});
//				return '<a href="'+urlT+'">在线沟通</a>';
//			}
		}]
//	,custbar : [
//	           {
//				text : '审核',
//				height : 30,
//		//		id : "DJ.order.saleOrder.SchemeDesignList.Audit",
//				handler : function() {
//					var grid = Ext.getCmp('DJ.order.saleOrder.SchemeDesignList');
//					var records = grid.getSelectionModel().getSelection();
//					if(records.length<1){
//						Ext.MessageBox.alert("错误","请选择一条记录！");
//						return;
//					}
//					var result = "('";
//					for(var i=0;i<records.length;i++){
//						result += records[i].get("fid");
//						if(i<records.length-1){
//							result += "','";
//						}
//					}
//					result+="')";
//					var el = grid.getEl();
//					el.mask("系统处理中,请稍候……");
//					Ext.Ajax.request({
//						url:'fauditorFproductdemand.do',
//						params:{
//							fids:result
//						},
//						success : function(response, option) {
//							var obj = Ext.decode(response.responseText);
//							if (obj.success == true) {
//								djsuccessmsg(obj.msg);
//								Ext.getCmp("DJ.order.saleOrder.SchemeDesignList").store
//										.load();
//		
//							} else {
//								Ext.MessageBox.alert('错误', obj.msg);
//		
//							}
//							el.unmask();
//						}
//					})
//				}
//			},{
//				text : '反审核',
//				height : 30,
//		//		id : "DJ.order.saleOrder.SchemeDesignList.UnAudit",
//				handler : function() {
//					var grid = Ext.getCmp('DJ.order.saleOrder.SchemeDesignList');
//					var records = grid.getSelectionModel().getSelection();
//					if(records.length<1){
//						Ext.MessageBox.alert("错误","请选择一条记录！");
//						return;
//					}
//					var result = "('";
//					for(var i=0;i<records.length;i++){
//						result += records[i].get("fid");
//						if(i<records.length-1){
//							result += "','";
//						}
//					}
//					result+="')";
//					var el = grid.getEl();
//					el.mask("系统处理中,请稍候……");
//					Ext.Ajax.request({
//						url:'unfauditorFproductdemand.do',
//						params:{
//							fids:result
//						},
//						success : function(response, option) {
//							var obj = Ext.decode(response.responseText);
//							if (obj.success == true) {
//								djsuccessmsg(obj.msg);
//								Ext.getCmp("DJ.order.saleOrder.SchemeDesignList").store
//										.load();
//		
//							} else {
//								Ext.MessageBox.alert('错误', obj.msg);
//		
//							}
//							el.unmask();
//						}
//					})
//				}
//			},{
//				text:'接收',
//				height : 30,
//				handler:function(){
//					
//				}
//			},{
//				text:'取消接收',
//				height : 30,
//				handler:function(){
//					
//				}
//			},{
//				text:'分配',
//				height : 30,
//				hidden:true,
//				handler:function(){
//				var allotgrid=Ext.getCmp("DJ.order.Deliver.ProductdemandOfallotList");
//				 var demandgrid= Ext.getCmp("DJ.order.Deliver.AllotproductdemandList");
//				 var rulegrid = Ext.getCmp("DJ.System.product.AllotProductreqallocationrulesList");
//					var demandrecord = demandgrid.getSelectionModel().getSelection();
//					var rulegridrecord = rulegrid.getSelectionModel().getSelection();
//					if(demandrecord.length!=1){
//					   Ext.MessageBox.alert("错误","请在需求列表中选择一条记录进行分配!");
//					   return;
//					}
//					if (demandrecord[0].get("falloted")== "true") {
//						Ext.MessageBox.alert("错误","当前记录已分配，不能再次分配!");
//						 return;
//					}
//					if(rulegridrecord.length!=1){
//						Ext.MessageBox.alert("错误","请在规则列表中选择一条记录进行分配");
//						 return;
//					}
//					
//					var el = allotgrid.getEl();
//					el.mask("系统处理中,请稍候……");
//					Ext.Ajax.request({
//						url:'allotruletoproductdemand.do',
//						params:{
//							fid:demandrecord[0].get("fid"),
//							rfid:rulegridrecord[0].get("fid")
//						},
//						success : function(response, option) {
//							var obj = Ext.decode(response.responseText);
//							if (obj.success == true) {
//								djsuccessmsg(obj.msg);
//								Ext.getCmp("DJ.order.Deliver.AllotproductdemandList").store
//										.load();
//		//						Ext.getCmp("DJ.System.product.AllotProductreqallocationrulesList").store
//		//								.load();
//		
//							} else {
//								Ext.MessageBox.alert('错误', obj.msg);
//		
//							}
//							el.unmask();
//						}
//					})
//					
//				}
//			},{
//				text:'取消分配',
//				height : 30,
//				hidden:true,
//				handler:function(){
//					
//					var allotgrid=Ext.getCmp("DJ.order.Deliver.ProductdemandOfallotList");
//				    var demandgrid= Ext.getCmp("DJ.order.Deliver.AllotproductdemandList");
//					var demandrecord = demandgrid.getSelectionModel().getSelection();
//					if(demandrecord.length!=1){
//					   Ext.MessageBox.alert("错误","请在需求列表中选择一条记录进行取消分配!");
//					   return;
//					}
//					if (demandrecord[0].get("falloted")== "false") {
//						Ext.MessageBox.alert("错误","当前记录未分配!");
//						 return;
//					}
//					var el = allotgrid.getEl();
//					el.mask("系统处理中,请稍候……");
//					Ext.Ajax.request({
//						url:'unallotruletoproductdemand.do',
//						params:{
//							fid:demandrecord[0].get("fid")
//						},
//						success : function(response, option) {
//							var obj = Ext.decode(response.responseText);
//							if (obj.success == true) {
//								djsuccessmsg(obj.msg);
//								Ext.getCmp("DJ.order.Deliver.AllotproductdemandList").store
//										.load();
//								
//		
//							} else {
//								Ext.MessageBox.alert('错误', obj.msg);
//		
//							}
//							el.unmask();
//						}
//					})
//				}
//			}
//		]
});

//function verification(object){
//	var grid = Ext.getCmp("DJ.order.saleOrder.SchemeDesignList");
//	var record = grid.getSelectionModel().getSelection();
//	if(object=="edit"){
//		if(record.length!=1){
//			throw "请选择一条记录!";
//		}
//	}else if(object=="del"){
//		if(record.length<1){
//			throw "请选择一条记录!";
//		}
//	}
//	if (record[0].get("isfauditor")== "true") {
//		throw "当前记录已审核，不能修改和删除!";
//	}
//}