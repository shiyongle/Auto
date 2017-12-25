var saveExcel = Ext.create('Ext.Window', {
	modal : true,
	title : "Excel文件上传",
	width : 450,// 230, //Window宽度
	height : 150,// 137, //Window高度
	resizable : false,
	closable : true, // 关闭按钮，默认为true
	closeAction : 'hide',
	listeners:{
		show:function(me){
			var record = Ext.getCmp('DJ.cardboard.system.CardboardList').up('panel').up('panel').down('treepanel').getSelectionModel().getSelection()
			if(record.length==1&&record[0].get('id')!=-1){
				me.down('combobox[name=fsupplierid]').setValue(record[0].get('id'));
			}
		}
	},
	items : [{
		xtype : 'form',
		baseCls : 'x-plain',
		fieldDefaults : {
			labelWidth : 400
		},
		width : 400,
		bodyPadding : 10,
		margin : '20 10 20 20',
		frame : true,

		items : [
			{
//				id : 'DJ.System.product.CustproductCustTreeList.loadupFormpanel.form.fcustomerid',
				name:'fsupplierid',
				fieldLabel : '上传',
				labelWidth : 50,
				msgTarget : 'side',
				allowBlank : false,
				anchor : '83%',
        		fieldLabel : '制造商',
        		xtype:'combobox',
        		displayField:'text', // 这个是设置下拉框中显示的值
        	    valueField:'id', // 这个可以传到后台的值
        	    blankText:'请选择制造商',
        	    editable: false, // 可以编辑不
        	    store:Ext.create('Ext.data.Store',{
        	    	fields:['id','text'],
        	    	  proxy: {
        	    	         type: 'ajax',
        	    	         url: 'getSupplierTree.do',
        	    	         reader: {
        	    	             type: 'json',
        	    	             root: 'children'
        	    	         }
        	    	     },
        	    	     autoLoad: true
        	    })
			},
			{
			xtype : 'filefield',
			name : 'fileName',
			fieldLabel : '上传',
			labelWidth : 50,
			msgTarget : 'side',
			allowBlank : false,
			anchor : '100%',
			regex : /(.)+((\.xlsx)|(\.xls)(\w)?)$/i,
			regexText : "文件格式选择不正确",
			buttonText : '选择文件'
		}],
		buttons : [{
			text : '上传',
			handler : function() {
				var m = this;
				var form = this.up('form').getForm();
				var fcustomerid = form.findField('fsupplierid').getValue();
				if (form.isValid()) {
					form.submit({
						url : 'saveCustExcel.do?action='+encodeURIComponent(fcustomerid),  
						waitMsg : '正在保存文件...',
						success : function(fp, o) {
							Ext.Msg.show({
								title : '提示信息',
								msg : o.result.msg,
								minWidth : 200,
								modal : true,
								buttons : Ext.Msg.OK
							})
							form.findField('fileName').setRawValue('');
							m.up('window').hide();
							Ext.getCmp("DJ.cardboard.system.CardboardList").store.load();
						},
						failure : function(fp, o) {
							Ext.Msg.show({
								title : '提示信息',
								msg : o.result.msg,
								minWidth : 200,
								modal : true,
								buttons : Ext.Msg.OK
							})
							form.findField('fileName').setRawValue('');
						}
					});
				}
			}
		}]
	}]
});
Ext.define('DJ.cardboard.system.CardboardList',{
	extend : 'Ext.c.GridPanel',
	title : "纸板材料",
	id : 'DJ.cardboard.system.CardboardList',
	pageSize : 50,
	closable : false,// 是否现实关闭按钮,默认为false
	url : 'getCardboardList.do',
	Delurl : "delCardboard.do",
	EditUI : "DJ.cardboard.system.CardboardEdit",
	exporturl : "exportCardboardList.do",// 导出为EXCEL方法
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	onload:function(){
	},
	listeners:{
		afterrender:function(){
			this.findCardboard();
		}
	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 新增界面弹出前事件
//		var record = this.getSelectionModel().getSelection();
//		if(record.length==1){
//			var fid = record[0].get('fid');
//			Ext.Ajax.request({
//				url:'isGenerateDeliverapply.do',
//				params:{fid:fid},
//				async:false,
//				success:function(response){
//					var obj = Ext.decode(response.responseText);
//					if(obj.success==false){
//						throw '已生成订单不允许删除、修改！';
//					}
//				}
//			})
//		}else{
//			throw '请先选择您要操作的行!';
//		}
	},
	Action_BeforeDelButtonClick : function(EditUI) {
		// 删除界面弹出前事件
		var record = this.getSelectionModel().getSelection();
		var fid = '';
		for(var i =0;i<record.length;i++){
			fid += record[i].get('fid');
			if(i<record.length-1){
				fid += ',';
			}
		}
		Ext.Ajax.request({
			url:'isGenerateDeliverapply.do',
			params:{fid:fid},
			async:false,
			success:function(response){
				var obj = Ext.decode(response.responseText);
				if(obj.success==false){
					throw '已生成订单不允许删除、修改！';
				}
			}
		})
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改弹出后事件
		this.clickButton();
	},
	Action_AfterViewButtonClick : function(EditUI) {
		// 查看弹出后事件
		this.clickButton();
	},
	isGenerateDeliverapply:function(){

	},
	clickButton:function(){
		var record = this.getSelectionModel().getSelection();
		if(record.length==1){
			var ftilemodelidButton = Ext.getCmp('DJ.cardboard.system.CardboardEdit.ftilemodelid');
			var flayerButton = Ext.getCmp('DJ.cardboard.system.CardboardEdit.flayer');
			var ftilemodelid = record[0].get('ftilemodelid');//Ext.getCmp(EditUI.EditUI).down('[name=ftilemodelid]').getValue();
			var flayer = record[0].get('flayer');//Ext.getCmp(EditUI.EditUI).down('[name=flayer]').getValue();
			if(ftilemodelid!='BC'){
				if(ftilemodelidButton.down('button[text='+ftilemodelid+']')){
					ftilemodelidButton.down('button[text='+ftilemodelid+']').toggle();
				}
			}
			if(flayer!='1'){
				if(flayerButton.down('button[text='+flayer+']')){
					flayerButton.down('button[text='+flayer+']').toggle();
				}
			}
		}
	},
	custbar:[{
		text:'启用',
		handler:function(){
			onEffectClick();
		}
	},{
		text:'禁用',
		handler:function(){
			onForbiddenClick();
		}
	},{
		text:'EXCEL文件上传',
		handler:function(){
			saveExcel.show();
		}
	},{
		text:'导入模板下载',
		handler:function(){
			window.open('downloadCardboard.do','导入模板下载');
		}
	},{
		xtype:'combobox',
		displayField: 'name',
		valueField: 'value',
		value : 1,
		editable :false,
		store:Ext.create('Ext.data.Store',{
			fields:['name','value'],
			data:[{
				name:'全部',
				value:''
			},{
				name:'禁用',
				value:0
			},{
				name:'启用',
				value:1
			}]
		}),
		listeners:{
			select:function(me){
				me.up('grid').findCardboard();
			}
		}
	},{
		xtype:'textfield',
		emptyText:'回车搜索',
		enableKeyEvents :true,
		id:'DJ.cardboard.system.CardboardList.textfield',
		listeners:{
			render:function(){
				Ext.tip.QuickTipManager.register({
				    target: 'DJ.cardboard.system.CardboardList.textfield',
				    text: '可输入名称、代码、层数、楞型进行查询'
				});
			},
			keypress:function( me, e, eOpts ){
				if(e.getKey()==13){
					me.up('grid').findCardboard();
				}
				
			}
		}
	}],
	findCardboard:function(){
		var combo = this.down('[xtype=combobox]').getValue();
		var text = this.down('[xtype=textfield]').getValue();
		var record = this.up('panel').up('panel').down('treepanel').getSelectionModel().getSelection();
		var  fsupplierid = '';
		if(record.length!=0){
			fsupplierid = record[0].get('id')==-1?'':record[0].get('id');
		}
		var store = this.getStore();
		store.setDefaultfilter([{
			myfilterfield : "feffect",
			CompareType : "like",
			type : "string",
			value : combo
		},{
			myfilterfield : "fname",
			CompareType : "like",
			type : "string",
			value : text
		},{
			myfilterfield : "fnumber",
			CompareType : "like",
			type : "string",
			value : text
		},{
			myfilterfield : "flayer",
			CompareType : "like",
			type : "string",
			value : text
		},{
			myfilterfield : "ftilemodelid",
			CompareType : "like",
			type : "string",
			value : text
		},{
			myfilterfield : "fsupplierid",
			CompareType : "like",
			type : "string",
			value : fsupplierid
		}]);
		 
		store.setDefaultmaskstring(" #0 and (#1 or #2 or #3 or #4) and #5");
		store.loadPage(1);
	},
	fields : [ {
		name : 'fid'
	}, {
		name : 'fname',
		myfilterfield : 'fname',
		myfiltername : '材料',
		myfilterable : true
	}, {
		name : 'fnumber',
		myfilterfield : 'fnumber',
		myfiltername : '代码',
		myfilterable : true
	}, {
		name : 'flayer',
		myfilterfield : 'flayer',
		myfiltername : '层数',
		myfilterable : true
	}, {
		name : 'ftilemodelid',
		myfilterfield : 'ftilemodelid',
		myfiltername : '楞型',
		myfilterable : true
	}, {
		name : 'fnewtype'
	}, {
		name : 'feffect',
		myfilterfield : 'feffect',
		myfiltername : '是否启用(1是,0否)',
		myfilterable : true
	}, {
		name : 'fcreatetime'
	}, {
		name : 'flastupdatetime'
	} ],
	columns:[{
		text:'序号',
		xtype:'rownumberer',
		width:40
	},{
		dataIndex:'fid',
		hidden : true
	},{
		text:'材料',
		dataIndex:'fname'
	},{
		text:'代码',
		dataIndex:'fnumber'
	},{
		text:'层数',
		dataIndex:'flayer'
	},{
		text:'楞型',
		dataIndex:'ftilemodelid'
	},{
		text:'类型',
		dataIndex:'fnewtype',
		renderer:function(value){
			return value == '2' ? '裱胶' : '普通';
		}
	},{
		text:'状态',
		dataIndex:'feffect',
		renderer:function(value){
			return value == '1' ? '启用' : '禁用';
		}
	},{
		text:'创建时间',
		dataIndex:'fcreatetime',
		flex:1,
		renderer:function(val){
			return val.substring(0,16);
		}
	},{
		text:'修改时间',
		dataIndex:'flastupdatetime',
		flex:1,
		renderer:function(val){
			return val.substring(0,16);
		}
		
	}]
})
function onEffectClick(btn) {
	var grid = Ext.getCmp("DJ.cardboard.system.CardboardList");// Ext.getCmp("DJ.System.UserListPanel")
	var record = grid.getSelectionModel().getSelection();
	if (record.length == 0) {
		Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
		return;
	}
	Ext.MessageBox.confirm("提示", "是否确认启用选中的产品?", function(btn) {
		if (btn == 'yes') {
			var ids = "";
			for (var i = 0; i < record.length; i++) {
				if (record[i].get("feffect") == "1") {
					Ext.MessageBox.alert('提示', '所选中的产品已经启用!');
					return;
				}
				var fid = record[i].get("fid");
				ids += fid;
				if (i < record.length - 1) {
					ids = ids + ",";
				}
			}
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				url : "effectProducts.do",
				params : {
					fidcls : ids
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						// Ext.MessageBox.alert('成功', obj.msg);
						grid.store.load();
						djsuccessmsg(obj.msg);
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});
		}
	});
}

function onForbiddenClick(btn) {
	var grid = Ext.getCmp("DJ.cardboard.system.CardboardList");// Ext.getCmp("DJ.System.UserListPanel")
	var record = grid.getSelectionModel().getSelection();
	if (record.length == 0) {
		Ext.MessageBox.alert('提示', '请先选择您要操作的行!');
		return;
	}
	Ext.MessageBox.confirm("提示", "是否确认禁用选中的产品?", function(btn) {
		if (btn == 'yes') {
			var ids = "";
			for (var i = 0; i < record.length; i++) {
				if (record[i].get("feffect") == "0") {
					Ext.MessageBox.alert('提示', '所选中的产品未启用!');
					return;
				}
				var fid = record[i].get("fid");
				ids += fid;
				if (i < record.length - 1) {
					ids = ids + ",";
				}
			}
			var el = grid.getEl();
			el.mask("系统处理中,请稍候……");
			Ext.Ajax.request({
				url : "forbiddenProduct.do",
				params : {
					fidcls : ids
				}, // 参数
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						// Ext.MessageBox.alert('成功', obj.msg);
						grid.store.load();
						djsuccessmsg(obj.msg);
					} else {
						Ext.MessageBox.alert('错误', obj.msg);
					}
					el.unmask();
				}
			});
		}
	});
}

