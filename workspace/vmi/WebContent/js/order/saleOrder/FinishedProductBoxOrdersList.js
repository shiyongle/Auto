




Ext
		.require(["Ext.ux.form.MyMixedSearchBox",
				"Ext.ux.grid.MySimpleGridContextMenu",
				'Ext.ux.grid.MyGridItemDblClick']);

Ext.define('DJ.order.saleOrder.FinishedProductBoxOrdersList', {
	extend : 'Ext.c.GridPanel',
	title : "我的业务",
	id : 'DJ.order.saleOrder.FinishedProductBoxOrdersList',
	alias : ['widget.finishedproductboxorderslist'],
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'GetFinishedProductBoxOrdersList.do?',
	Delurl : "",
	EditUI : "",
	exporturl : "AffirmedOrderProductplantoexect.do",
	requires : 'DJ.order.saleOrder.OrderAffirmList',
	
	showSearchAllBtn : false,
	
	mixins : ['DJ.tools.grid.MySimpleGridMixer',
			'DJ.tools.grid.mixer.MyGridSearchMixer'],
 
			//page-break-after:always; " media="print"
//	 printTplS : '<div style="page-break-after:always" align="center" height="290mm" width="210mm"><table width="100%" height="20mm" cellspacing="0" border="0" align="center"><tr><td width="33%">&nbsp;</td><td width="34%" align="center"><p style="font-size:14pt">{suppliername}</p></td><td width="33%">&nbsp;</td></tr><tr><td>&nbsp;</td><td width="34%" align="center"><p style="font-size:18pt">生产任务单</p></td><td width="33%">&nbsp;</td></tr><tr><td>客户：{customername}</td><td width="34%">&nbsp;</td><td width="33%" align="right">{fnumber}</td></tr></table><table width="100%" height="270mm" border="1" align="center" cellspacing="0" bordercolor="#000000"><tr><td width="15%" height="5%" align="center">产品名称</td><td height="5%" colspan="2" align="center">{productname}</td><td width="15%" height="5%" align="center">产品规格</td><td height="5%" colspan="2" align="center">{fcharacter}</td></tr><tr><td width="15%" align="center">数量</td><td width="15%" align="center">{famount}</td><td width="15%" align="center">交期</td><td width="15%" align="center">{farrivetime}</td><td width="15%" align="center">材料</td><td width="15%" align="center">{materialname}</td></tr><tr><td width="15%" align="center">来料供应商</td><td width="15%" align="center">{materialsuppliername}</td><td width="15%" align="center">来料时间</td><td width="15%" align="center">{materialfarrivetime}</td><td width="15%" align="center">箱片信息</td><td width="15%" align="center">{boxpie}</td></tr><tr><td width="15%" align="center">横向压线</td><td colspan="2" align="center">{fhformula}</td><td width="15%" align="center">纵向压线</td><td colspan="2" align="center">{fvformula}</td></tr><tr><td width="15%" align="center">印刷颜色</td><td colspan="5">{fprintcolor}</td></tr><tr height="30mm"><td width="15%" align="center">工艺路线</td><td colspan="5"><font style="line-height:18pt">{fworkproc}</font></td></tr><tr height="50mm"><td width="15%" align="center">特殊要求</td><td colspan="5"><font style="line-height:20pt">{fdescription}</font></td></tr><tr><td width="15%" align="center">工序记录</td><td width="15%" align="center">生产数</td><td width="15%" align="center">损耗数</td><td colspan="3" align="center">备注</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr height="100mm"><td colspan="6" align="center" valign="middle"><img src="{imgpath}" alt="" name="img" height="98%" style="max-width:98%" align="middle" id="img"></td></tr></table></div>',
	
//	 printTplS : '<div style="page-break-after:always" align="center" height="290mm" width="210mm"><table width="100%" height="20mm" cellspacing="0" border="0" align="center"><tr><td width="33%">&nbsp;</td><td width="34%" align="center"><p style="font-size:14pt">{suppliername}</p></td><td width="33%">&nbsp;</td></tr><tr><td>&nbsp;</td><td width="34%" align="center"><p style="font-size:18pt">生产任务单</p></td><td width="33%">&nbsp;</td></tr><tr><td>客户：{customername}</td><td width="34%">&nbsp;</td><td width="33%" align="right">{fnumber}</td></tr></table><table width="100%" height="270mm" border="1" align="center" cellspacing="0" bordercolor="#000000"><tr><td width="15%" height="5%" align="center">产品名称</td><td height="5%" colspan="2" align="center">{productname}</td><td width="15%" height="5%" align="center">产品规格</td><td height="5%" colspan="2" align="center">{fcharacter}</td></tr><tr><td width="15%" align="center">数量</td><td width="15%" align="center">{famount}</td><td width="15%" align="center">交期</td><td width="15%" align="center">{farrivetime}</td><td width="15%" align="center">材料</td><td width="15%" align="center">{materialname}</td></tr><tr><td width="15%" align="center">来料供应商</td><td width="15%" align="center">{materialsuppliername}</td><td width="15%" align="center">来料时间</td><td width="15%" align="center">{materialfarrivetime}</td><td width="15%" align="center">箱片信息</td><td width="15%" align="center">{boxpie}</td></tr><tr><td width="15%" align="center">横向压线</td><td colspan="2" align="center">{fhformula}</td><td width="15%" align="center">纵向压线</td><td colspan="2" align="center">{fvformula}</td></tr><tr><td width="15%" align="center">印刷颜色</td><td colspan="5">{fprintcolor}</td></tr><tr height="30mm"><td width="15%" align="center">工艺路线</td><td colspan="5"><font style="line-height:18pt">{fworkproc}</font></td></tr><tr height="50mm"><td width="15%" align="center">特殊要求</td><td colspan="5"><font style="line-height:20pt">{fdescription}</font></td></tr><tr><td width="15%" align="center">工序记录</td><td width="15%" align="center">生产数</td><td width="15%" align="center">损耗数</td><td colspan="3" align="center">备注</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr height="100mm"><td colspan="6" align="center" valign="middle"><img src="{imgpath}" alt="" name="img" height="98%" style="max-width:98%" align="middle"/></td></tr></table></div>',
	
 	printTplS : '<div style="page-break-after:always;"  align="center" height="300mm" width="210mm"><table width="100%" height="10"  cellspacing="0" border="0" align="center"><tr><td width="33%">&nbsp;</td><td width="34%" align="center"><p style="font-size: 14pt;">{suppliername}</p></td><td width="33%">&nbsp;</td></tr><tr><td>&nbsp;</td><td width="34%" align="center"><p style="font-size: 18pt;">生产任务单</p></td><td width="33%">&nbsp;</td></tr><tr><td>客户：{customername}</td><td width="34%">&nbsp;</td><td width="33%" align="right">{fnumber}</td></tr></table><table width="100%" height="850" border="1" align="center" cellspacing="0" bordercolor="#000000"><tr><td width="15%" height="5%" align="center">产品名称</td><td height="5%" colspan="2" align="center">{productname}</td><td width="15%" height="5%" align="center">产品规格</td><td height="5%" colspan="2" align="center">{fcharacter}</td></tr><tr><td width="15%" align="center">数量</td><td width="15%" align="center">{famount}</td><td width="15%" align="center">交期</td><td width="15%" align="center">{farrivetime}</td><td width="15%" align="center">材料</td><td width="15%" align="center">{materialname}</td></tr><tr><td width="15%" align="center">来料供应商</td><td width="15%" align="center">{materialsuppliername}</td><td width="15%" align="center">来料时间</td><td width="15%" align="center">{materialfarrivetime}</td><td width="15%" align="center">箱片信息</td><td width="15%" align="center">{boxpie}</td></tr><tr><td width="15%" align="center">横向压线</td><td colspan="2" align="center">{fhformula}</td><td width="15%" align="center">纵向压线</td><td colspan="2" align="center">{fvformula}</td></tr><tr><td width="15%" align="center">印刷颜色</td><td colspan="5">{fprintcolor}</td></tr><tr height="60" ><td width="15%" align="center">工艺路线</td><td colspan="5"><font style="line-height:18pt">{fworkproc}</font></td></tr><tr height="15%"><td width="15%" height="100" align="center">特殊要求</td><td colspan="5"><font style="line-height:20pt">{fdescription}</font></td></tr><tr><td width="15%" align="center">工序记录</td><td width="15%" align="center">生产数</td><td width="15%" align="center">损耗数</td><td colspan="3" align="center">备注</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr height="40%"><td height="320" colspan="6" align="center" valign="middle"><img src="{imgpath}" alt="" name="img" height="98%" style="max-width:98%;" align="middle"/></td></tr></table></div>',
		
	printTplSForIE : '<div style="page-break-after:always" align="center" height="270mm" width="210mm"><table width="100%" height="10" cellspacing="0" border="0" align="center"><tr><td width="33%">&nbsp;</td><td width="34%" align="center"><p style="font-size:14pt">{suppliername}</p></td><td width="33%">&nbsp;</td></tr><tr><td>&nbsp;</td><td width="34%" align="center"><p style="font-size:18pt">生产任务单</p></td><td width="33%">&nbsp;</td></tr><tr><td>客户：{customername}</td><td width="34%">&nbsp;</td><td width="33%" align="right">{fnumber}</td></tr></table><table width="100%" height="850" border="1" align="center" cellspacing="0" bordercolor="#000000"><tr><td width="16%" height="5%" align="center">产品名称</td><td height="5%" colspan="2" align="center">{productname}</td><td width="16%" height="5%" align="center">产品规格</td><td height="5%" colspan="2" align="center">{fcharacter}</td></tr><tr><td width="16%" align="center">数量</td><td width="16%" align="center">{famount}</td><td width="16%" align="center">交期</td><td width="16%" align="center">{farrivetime}</td><td width="16%" align="center">材料</td><td width="20%" align="center">{materialname}</td></tr><tr><td width="16%" align="center">来料供应商</td><td width="16%" align="center">{materialsuppliername}</td><td width="16%" align="center">来料时间</td><td width="16%" align="center">{materialfarrivetime}</td><td width="16%" align="center">箱片信息</td><td width="20%" align="center">{boxpie}</td></tr><tr><td width="16%" align="center">横向压线</td><td colspan="2" align="center">{fhformula}</td><td width="16%" align="center">纵向压线</td><td colspan="2" align="center">{fvformula}</td></tr><tr><td width="16%" align="center">印刷颜色</td><td colspan="5">{fprintcolor}</td></tr><tr height="60"><td width="16%" align="center">工艺路线</td><td colspan="5"><font style="line-height:18pt">{fworkproc}</font></td></tr><tr height="15%"><td width="16%" height="100" align="center">特殊要求</td><td colspan="5"><font style="line-height:20pt">{fdescription}</font></td></tr><tr><td width="16%" align="center">工序记录</td><td width="16%" align="center">生产数</td><td width="16%" align="center">损耗数</td><td colspan="3" align="center">备注</td></tr><tr><td width="16%">&nbsp;</td><td width="16%">&nbsp;</td><td width="16%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="16%">&nbsp;</td><td width="16%">&nbsp;</td><td width="16%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="16%">&nbsp;</td><td width="16%">&nbsp;</td><td width="16%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="16%">&nbsp;</td><td width="16%">&nbsp;</td><td width="16%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="16%">&nbsp;</td><td width="16%">&nbsp;</td><td width="16%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr height="40%"><td height="350" colspan="6" align="center" valign="middle"><img src="{imgpath}" alt="" name="img" height="98%" width="180mm" align="middle"></td></tr></table></div>',
	
	printTplSForWord : '<div style="page-break-after:always;"  align="center" height="300mm" width="210mm"><table width="100%" height="10"  cellspacing="0" border="0" align="center"><tr><td width="33%">&nbsp;</td><td width="34%" align="center"><p style="font-size: 14pt;">{suppliername}</p></td><td width="33%">&nbsp;</td></tr><tr><td>&nbsp;</td><td width="34%" align="center"><p style="font-size: 18pt;">生产任务单</p></td><td width="33%">&nbsp;</td></tr><tr><td>客户：{customername}</td><td width="34%">&nbsp;</td><td width="33%" align="right">{fnumber}</td></tr></table><table width="100%" height="850" border="1" align="center" cellspacing="0" bordercolor="#000000"><tr><td width="15%" height="5%" align="center">产品名称</td><td height="5%" colspan="2" align="center">{productname}</td><td width="15%" height="5%" align="center">产品规格</td><td height="5%" colspan="2" align="center">{fcharacter}</td></tr><tr><td width="15%" align="center">数量</td><td width="15%" align="center">{famount}</td><td width="15%" align="center">交期</td><td width="15%" align="center">{farrivetime}</td><td width="15%" align="center">材料</td><td width="15%" align="center">{materialname}</td></tr><tr><td width="15%" align="center">来料供应商</td><td width="15%" align="center">{materialsuppliername}</td><td width="15%" align="center">来料时间</td><td width="15%" align="center">{materialfarrivetime}</td><td width="15%" align="center">箱片信息</td><td width="15%" align="center">{boxpie}</td></tr><tr><td width="15%" align="center">横向压线</td><td colspan="2" align="center">{fhformula}</td><td width="15%" align="center">纵向压线</td><td colspan="2" align="center">{fvformula}</td></tr><tr><td width="15%" align="center">印刷颜色</td><td colspan="5">{fprintcolor}</td></tr><tr height="60" ><td width="15%" align="center">工艺路线</td><td colspan="5"><font style="line-height:18pt">{fworkproc}</font></td></tr><tr height="15%"><td width="15%" height="100" align="center">特殊要求</td><td colspan="5"><font style="line-height:20pt">{fdescription}</font></td></tr><tr><td width="15%" align="center">工序记录</td><td width="15%" align="center">生产数</td><td width="15%" align="center">损耗数</td><td colspan="3" align="center">备注</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr height="40%"><td height="320" colspan="6" align="center" valign="middle"><a href="{imgpath}">{imgpath}</a></td></tr></table></div>',
		
//	insertImgResizingStyle : function () {
//	
//		var me = this;
//		
//		
//		
//	},
	onload : function() {
		var me = this;
		
		MyGridTools.rebuildExcelAction(me);//导出设置
		
		me._hideButtons(['querybutton'/*, 'exportbutton'*/]);

		Ext.getCmp('DJ.order.saleOrder.FinishedProductBoxOrdersList.addbutton')
				.hide();
		Ext
				.getCmp('DJ.order.saleOrder.FinishedProductBoxOrdersList.editbutton')
				.hide();
		Ext.getCmp('DJ.order.saleOrder.FinishedProductBoxOrdersList.delbutton')
				.hide();
		Ext
				.getCmp('DJ.order.saleOrder.FinishedProductBoxOrdersList.viewbutton')
				.hide();
				
				
		MyCommonToolsZ.setComAfterrender(me, function() {

			if (Ext.isIE) {

//				me.down('button[text=导出word]').hide();

			}

		});		
		
		Ext.util.CSS.createStyleSheet(".fstate-true{" + "color:#0000CC" + "}",
				'fstate-true');

		Ext.util.CSS.createStyleSheet(".fstate-false{" + "color:black" + "}",
				'fstate-false');
		
	},
	// listeners:{
	// afterrender:function( com, eOpts ){
	// com.findProductPlan();
	// }
	// },
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件
		// Ext.getCmp("DJ.order.saleOrder.SaleOrderEdit").getform().getForm().findField('fnumber').hide();
	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
		// 可对编辑界面进行复制
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	viewConfig:{
	   getRowClass:function(record,rowIndex,p,ds){
		    return record.get("fcreateboard") == '2'
					? "fstate-true"
					: "fstate-false";
		}
	},
	plugins : [

	// {
	//
	// ptype : "mysimplegridcontextmenu",
	//
	// useExistingButtons : ["button[text=查看客户产品]", 'menuseparator',
	// "button[text=查看产品]", "button[text=查看图片]"]
	// // 选择器，menuseparator为分隔符。菜单链接到按钮，用这个就不用设置处理器了
	// // viewHandler : "button[text=查看客户产品]",//查看处理器或是选择符
	// // editHandler : null,//编辑
	// // deleteHandler : null,//删除
	// // 其他菜单项
	// // otherItems : [{
	// // text : "test submenu",
	// // menu : {
	// //
	// // xtype : 'menu',
	// // ignoreParentClicks : true,
	// // items : [{
	// // text : "apple",
	// // handler : function() {
	// //
	// // Ext.Msg.alert("apple","apple");
	// //
	// // }
	// // }, {
	// // text : "color picker",
	// // menu : {
	// // xtype : "colormenu"
	// // }
	// // }]
	// // }
	// //
	// // }]//其他菜单组件
	// },

	// {
	// ptype : "mygriditemdblclick",
	// dbClickHandler : "button[text=查看客户产品]"
	//
	// }

	],
	custbar : [/*{
				// id : 'DelButton',
				text : '接收',
				height : 30,
				handler : //actionAudit(1)
					function() {
					var grid = Ext.getCmp("DJ.order.saleOrder.FinishedProductBoxOrdersList");
					var record = grid.getSelectionModel().getSelection();
					if (record.length <= 0) {
						Ext.MessageBox.alert('提示', '请选中记录接收！');
						return;
					}
					var fid = "";
					for(var i=0;i<record.length;i++){
						if(record[i].get("faffirmed")==1){
							continue;
						}
						if(fid.length<=0){
							fid = record[i].get("fid");
						}else{
							fid = fid + ","+record[i].get("fid");
						}
					}
					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						url : "FinishedSupplierAffirm.do",
						params : {
							fidcls : fid
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								  djsuccessmsg( obj.msg);
								Ext.getCmp("DJ.order.saleOrder.FinishedProductBoxOrdersList").store.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
							el.unmask();
						}
					});
				}
	},*/

	{
		text : '取消接收',
		height : 30,
		handler : // actionAudit(1)
		function() {
			var grid = Ext
					.getCmp("DJ.order.saleOrder.FinishedProductBoxOrdersList");
			var record = grid.getSelectionModel().getSelection();
			if (record.length <= 0) {
				Ext.MessageBox.alert('提示', '请选中记录取消接收！');
				return;
			}
			var fid = "";
			for (var i = 0; i < record.length; i++) {
				if (record[i].get("faffirmed") == 0) {
					continue;
				}
				if (fid.length <= 0) {
					fid = record[i].get("fid");
				} else {
					fid = fid + "," + record[i].get("fid");
				}
			}
			Ext.Msg.show({
		     title:'提示',
		     msg: '取消接受将退回到客户订单，是否取消接受？',
		     buttons: Ext.Msg.YESNO,
		     icon: Ext.Msg.QUESTION,
		     fn:function(btn){
		     	if(btn=='yes'){
 					var el = grid.getEl();
					el.mask("系统处理中,请稍候……");
					Ext.Ajax.request({
						url : "FinishedSupplierUnAffirm.do",
						params : {
							fidcls : fid
						}, // 参数
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								djsuccessmsg("取消接收成功");
								Ext
										.getCmp("DJ.order.saleOrder.FinishedProductBoxOrdersList").store
										.load();
							} else {
								Ext.MessageBox.alert('错误', obj.msg);
							}
							el.unmask();
						}
					});
		     	}else{
		     		console.log('NO');
		     		return;
		     	}
		     }
		});

		}
	},
//	(function() {
//
//		if (!Ext.isIE) {
//
//			return Ext.create("Ext.ux.grid.print.MySimpleGridPrinterComponent");
//
//		} else {
//
//			return '|';
//
//		}
//
//	})(),
		{
				text:'采购纸板',
				height:30,
				handler:function(){
					var m = this;
					var record = this.up('grid').getSelectionModel().getSelection();
					var fid = '';
					if(record.length<1){
						Ext.Msg.alert('提示','请选择数据 ！');
						return;
					}
					for(var i = 0;i<record.length;i++){
						if(record[i].get("fstate")==0){
							Ext.Msg.alert('提示','未接收不能采购纸板 ！');
							return;
						}
						if(record[i].get('fcreateboard')=="true"){
//							Ext.Msg.alert('提示','已经生成纸板订单的不允许重复生成 ！');
//							return;
							continue;
						}
						fid += record[i].get('fid');
						if(i<record.length-1){
							fid += ',';	
						}
					}
					var toDeliversBoard  = function(address,emptyFun){
						Ext.Ajax.request({
							url:'productPlanToDeliversBoard.do',
							params:{'fid':fid,'Address':Ext.encode(address)},
							success:function(response){
								var obj = Ext.decode(response.responseText);
								if(obj.success==true){
									djsuccessmsg(obj.msg);
									emptyFun();
								}else{
									Ext.Msg.alert('提示',obj.msg);
									emptyFun();
								}
							}
						})
					}
					Ext.Ajax.request({
						url:'getUserToCustAddress.do',
						success:function(response){
							var obj = Ext.decode(response.responseText);
							if(obj.success==true){
								if(!Ext.isEmpty(obj.data)){
									if(obj.data.length==1){
										toDeliversBoard(obj.data[0],function(){
//															el.unmask();
															m.up('grid').store.load();
													});
									}else{
										Ext.create('Ext.Window',{
											title:'选择地址',
											height:120,width:400,
											modal:true,
											bodyPadding : '15 10',
											bbar:[{
												xtype:'tbfill'
											},{
												text:'确定',
												width:50,
												handler:function(){
													var me = this;
													if(!Ext.isEmpty(me.up('window').down('combobox').getValue())){
														var el = me.up('window').getEl();
														el.mask("系统处理中,请稍候……");
														toDeliversBoard(me.up('window').down('combobox').record,function(){
															el.unmask();
															me.up('window').close();
															m.up('grid').store.load();
														});
													}else{
														Ext.Msg.alert('提示','请选择地址！');
													}
												}
											},{
												text:'取消',
												width:50,
												handler:function(){
													this.up('window').close();
												}
											},{
												xtype:'tbfill'
											}
											],
											items:[{
												xtype:'combobox',
												fieldLabel: '地址',
												editable:false,
												labelWidth:45,
												width:350,
												displayField: 'fdetailaddress',
												valueField: 'fid',
												store:Ext.create('Ext.data.Store', {
														    fields: ['fdetailaddress', 'fid','flinkman','fphone'],
														    data : obj.data
														}),
												listeners:{
													select:function(combo, records, eOpts){
														combo.record = records[0].data;
													}
												}
											}]
										}).show();
									}
								}else{
									Ext.Msg.alert('提示','没有地址！');
								}
							}else{
								Ext.Msg.alert('提示',obj.msg);
							}
						}
					})
				}
			}, 
//				{
//				text : '打印',
//				handler : function() {
//
//					var grid = this.up('grid');
//
//					var recorders = MyCommonToolsZ.pickSelectItems(grid);
//
//					if (recorders == -1) {
//
//						return;
//
//					}
//
//					var fids = [];
//
//					Ext.each(recorders, function(ele, index, all) {
//
//						fids.push(ele.get('fid'));
//
//					});
//
//					var storeT = new Ext.data.JsonStore({
//						// store configs
//
//						proxy : {
//							type : 'ajax',
//							url : 'getPrintInfoWithProductplan.do',
//							reader : {
//								type : 'json',
//								root : 'data'
//							}
//						},
//
//						// alternatively, a Ext.data.Model name can be given
//						// (see
//						// Ext.data.Store for an example)
//						fields : ['suppliername', 'customername', 'fnumber',
//								'productname', 'fcharacter', 'famount',
//								'farrivetime', 'materialname',
//								'materialsuppliername', 'materialfarrivetime',
//								'boxpie', 'fhformula', 'fvformula',
//								'fprintcolor', 'fworkproc', 'fdescription',
//								'imgpath']
//					});
//
//					storeT.load({
//
//						params : {
//
//							fid : fids.join(',')
//
//						},
//
//						callback : function(records, operation, success) {
//
//							if (success) {
//
//								var htmlSs = [];
//
//								var xtl = new Ext.XTemplate(grid.printTplS);
//								
//								if (Ext.isIE) {
//
//									xtl = new Ext.XTemplate(grid.printTplSForIE);
//
//								}
//
//								Ext.each(records, function(ele, index, all) {
//
//									var goalH = xtl.apply(ele.data);
//
//									if (index == records.length - 1) {
//
//										goalH = goalH.replace(
//												'page-break-after:always;', '');
//
//									}
//
//									htmlSs.push(goalH);
//
//								});
//
//								var printerF = Ext
//										.get('mySimpleGridPrinterPrinterIFId');
//
//								Ext.DomHelper
//										.overwrite(
//												printerF.dom.contentWindow.document.body, 
//												htmlSs);
//
//								var el = grid.getEl();
//
//								el.mask("系统处理中,请稍候……");
//
//								
//								
//								setTimeout(function() {
//									el.unmask();
//
////									if (Ext.isIE) {
////
////										var newWin = window.open("", "printer");
////
////										Ext.DomHelper
////												.append(
////														newWin.document.head,
////														printerF.dom.contentWindow.document.head.outerHTML);
////
////										Ext.DomHelper
////												.append(
////														newWin.document.body,
////														printerF.dom.contentWindow.document.body.outerHTML);
////
////										newWin.focus();
////										newWin.print();
////										
////										newWin.close();
////
////									} else {
//
//										printerF.dom.contentWindow.focus();
//										printerF.dom.contentWindow.print();
//
////									}
//
//										// newWin.document.body
//
//								}, 1000);
//
//							}
//
//						}
//
//					});
//
//				}
//},
	
		{

	
				text : '导出生产任务单',
				handler : function() {

					var me = this;
					
					
					
					var grid = this.up('grid');

					var recorders = MyCommonToolsZ.pickSelectItems(grid);

					if (recorders == -1) {

						return;

					}

					var fids = [];

					Ext.each(recorders, function(ele, index, all) {

						fids.push(ele.get('fid'));

					});

					var storeT = new Ext.data.JsonStore({
						// store configs

						proxy : {
							type : 'ajax',
							url : 'getPrintInfoWithProductplan.do',
							reader : {
								type : 'json',
								root : 'data'
							}
						},

						// alternatively, a Ext.data.Model name can be given
						// (see
						// Ext.data.Store for an example)
						fields : ['suppliername', 'customername', 'fnumber',
								'productname', 'fcharacter', 'famount',
								'farrivetime', 'materialname',
								'materialsuppliername', 'materialfarrivetime',
								'boxpie', 'fhformula', 'fvformula',
								'fprintcolor', 'fworkproc', 'fdescription',
								'imgpath']
					});

					storeT.load({

						params : {

							fid : fids.join(',')

						},

						callback : function(records, operation, success) {

							if (success) {

								//添加绝对路径
								Ext.each(records, function(ele, index, all) {

									ele
											.set(
													'imgpath',
													!Ext.isEmpty(ele
															.get('imgpath'))
															? (('http://' + location.host) + ele
																	.get('imgpath'))
															: '');
											
											
//											ele.set('imgpath', 'http://www.wordlm.com/uploads/allimg/101219/1_101219222019_4.gif');

								});
								
								
								var htmlSs = [];

								var xtl = new Ext.XTemplate(grid.printTplSForWord);
								
								Ext.each(records, function(ele, index, all) {

									var goalH = xtl.apply(ele.data);

									if (index == records.length - 1) {

										goalH = goalH.replace(
												'page-break-after:always;', '');

									}

									htmlSs.push(goalH);

								});

								var printerF = Ext
										.get('mySimpleGridPrinterPrinterIFId');

								Ext.DomHelper
										.overwrite(
												printerF.dom.contentWindow.document.body, 
												htmlSs);

								var el = grid.getEl();

								el.mask("系统处理中,请稍候……");

								
								
								setTimeout(function() {
									el.unmask();

//									if (Ext.isIE) {
//
//										var newWin = window.open("", "printer");
//
//										Ext.DomHelper
//												.append(
//														newWin.document.head,
//														printerF.dom.contentWindow.document.head.outerHTML);
//
//										Ext.DomHelper
//												.append(
//														newWin.document.body,
//														printerF.dom.contentWindow.document.body.outerHTML);
//
//										newWin.focus();
//										
//										MySimpleHTMLToWord.gainPDFFromHTML(newWin.document.documentElement.outerHTML);
										
//										newWin.close();

//										
//										newWin.close();
//
//									} else {

									
									
									MySimpleHTMLToWord.gainWordFromHTML(printerF.dom.contentWindow.document.documentElement.outerHTML);


//									}

										// newWin.document.body

								}, 1000);

							}

						}

					});

				}

	
},{
				text:'查看产品档案',
				handler:function(){
								var grid = this.up('grid');
								var record = grid.getSelectionModel().getSelection();
								if (record.length != 1) {
									Ext.Msg.alert('提示','请先选择您要操作的行!');									
									return;
								};
								var winAdd = Ext.create('DJ.System.supplier.SupOfCustProductEdit');
						        winAdd.loadfields(record[0].get("productid"));
						        winAdd.seteditstate('edit');
						        winAdd.setTitle('产品档案');
//						        winAdd.fcustomerid = record[0].get('fcustomerid');
						        winAdd.parent = grid.id;
					        	if(winAdd){
									winAdd.down('toolbar[dock=top] button[text*=保]').hide();
									Ext.Array.each(winAdd.down('form').query('textfield'),function(item) {
										item.setReadOnly(true)
									});
								}
						        winAdd.show();					
				}
			},
			// {
			// text : '查看附件',
			// showInMenu : true,
			// handler : function() {
			// var grid = Ext
			// .getCmp("DJ.order.saleOrder.FinishedProductBoxOrdersList");
			// var record = grid.getSelectionModel().getSelection();
			// if (record.length != 1) {
			// Ext.MessageBox.alert('提示', '只能选中一条记录进行操作!');
			// return;
			// }
			// var win = Ext.create('Ext.Window', {
			// items : {
			// xtype : 'productfile',
			// url : 'GetProductfile.do?fid='
			// + record[0].get('fproductdefid')
			// }
			// });
			// win.show();
			// }
			// }, '|',

			{
				xtype : "mymixedsearchbox",
				tip : '按制造订单号、包装物名称、制造商名称、采购订单号、备注查询...',
				filterMode : true,
				useDefaultfilter : true,
				condictionFields : ['d.fnumber', 'f.fname', 's.fname',
						'd.fdescription', 'd.fpcmordernumber']
			},

			'|', {

				xtype : 'mysimplesearchercombobox',
				filterMode : true,

				editable : false,
				listeners:{
					render:function(me){
						me.store.removeAt(0);
					},
					select : function(combo, records, eOpts) {

					var value = combo.getValue();

					combo.self.doSearchAction(combo, value);

				},
				keypress : function(com, e, eOpts) {

					if (e.getKey() == Ext.EventObject.ENTER) {

						var value = com.getValue();

						com.self.doSearchAction(com, value);

					}

				}
				},
				fields : [{
					displayName : '全部',
					trueValue : '0,1,2,3,4',
					field : 'd.fstate'
				},
				{

					displayName : '未接收',
					trueValue : '0',
					field : 'd.fstate'

				}, {

					displayName : '已接收',
					trueValue : '1,2,3,4',
					field : 'd.fstate'
				}

				]

			},

			{

				xtype : 'mydatetimesearchbox',
				width:460,
				startDate: new Date(),
				filterMode : true,
				useDefaultfilter : true,
				labelFtext : '下单时间',
				conditionDateField : 'd.fcreatetime'

			}

	],
	fields : [{
		name : 'fid'
	}, {
		name : 'fnumber',
		myfilterfield : 'd.fnumber',
		myfiltername : '编号',
		myfilterable : true
	}, {
		name : 'cname',
		myfilterfield : 'c.fname',
		myfiltername : '客户名称',
		myfilterable : true
			// }, {
			// name : 'pname',
			// myfilterfield : 'p.fname',
			// myfiltername : '客户产品名称',
			// myfilterable : true
			}, {
				name : 'fname',
				myfilterfield : 'f.fname',
				myfiltername : '包装物名称',
				myfilterable : true
			}, {
				name : 'fproductdefid'
			}, {
				name : 'sname',
				myfilterfield : 's.fname',
				myfiltername : '制造商名称',
				myfilterable : true
			}, {
				name : 'fspec'
			}, {
				name : 'farrivetime'
			}, {
				name : 'fbizdate'
			}, {
				name : 'famount'
			}, {
				name : 'faudited'
			}, {
				name : 'fordertype'
			}, {
				name : 'fsuitProductID'
			}, {
				name : 'fparentOrderEntryId'
			}, {
				name : 'forderid'
			}, {
				name : 'fimportEAS',
				myfilterfield : 'd.fimportEAS',
				myfiltername : '导入',
				myfilterable : true
			}, {
				name : 'fstockoutqty'
			}, {
				name : 'fstockinqty'
			}, {
				name : 'fstoreqty'
			}, {
				name : 'faffirmed',
				myfilterfield : 'd.faffirmed',
				myfiltername : '确认',
				myfilterable : true
			}, {
				name : 'fassemble'
			}, {
				name : 'fiscombinecrosssubs'
			}, {
				name : 'fparentorderid'

			}, {
				name : 'ftype',
				myfilterfield : 'd.ftype',
				myfiltername : '订单类型',
				myfilterable : true
			}, {
				name : 'fboxlength'
			}, {
				name : 'fboxwidth'
			}, {
				name : 'fboxheight'
			}, {
				name : 'fschemename'
			}, {
				name : 'fdescription'
			}, {
				name : 'fstate'
			}, {
				name : 'faffirmtime'
			}, {
				name : 'faffirmer'
			}, {
				name : 'fcreatetime'
			}, {
				name : 'fcharacter'
			}, {
				name : 'fpcmordernumber'
			}, 'faddress', 'fordernumber','productid','fcreateboard','fcustomerid','fisupdate'],
	columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : 'fsuitProductID',
		'dataIndex' : 'fsuitProductID',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : 'fassemble',
		'dataIndex' : 'fassemble',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : 'fproductdefid',
		'dataIndex' : 'fproductdefid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : 'fiscombinecrosssubs',
		'dataIndex' : 'fiscombinecrosssubs',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : 'fparentOrderEntryId',
		'dataIndex' : 'fparentOrderEntryId',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : 'forderid',
		'dataIndex' : 'forderid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : 'fparentorderid',
		'dataIndex' : 'fparentorderid',
		hidden : true,
		hideable : false,
		sortable : true

	}, {
		'header' : '采购订单号',
		'dataIndex' : 'fordernumber',
		width : 100,
		sortable : true
	},

	{
		'header' : '制造订单号',
		'dataIndex' : 'fnumber',
		width : 100,
		sortable : true
	}, {
		header : '订单状态',
		dataIndex : 'fstate',
		renderer : function(val) {
			switch (val) {
				case '0' :
					return '未接收';
					break;
				case '1' :
					return '已接收';
					break;
				case '2' :
					return '已接收';
					break;
				case '3' :
					return '已接收';
					break;
				default :
					return '';
			}
			// if(val==0){
			// return '待确认';
			// }else if(val==1){
			// return '已确认';
			// }else if(val==2){
			// return '部分入库';
			// }else if(val==3){
			// return '全部入库';
			// }else{
			// return '';
			// }
		}
	}, {
		'header' : '客户名称',
		width : 100,
		'dataIndex' : 'cname',
		sortable : true
	},{
		'header' : '产品变更',
		width : 100,
		'dataIndex' : 'fisupdate',
		sortable : true,
		renderer:function(val){
			return val==1?'产品信息有变动':"";
		}
	}, {
		'header' : '产品名称',
		width : 180,
		'dataIndex' : 'fname',
		sortable : true
	}, {
		'header' : '规格',
		'dataIndex' : 'fspec',
		sortable : true
	},

	{
		'header' : '数量',
		width : 50,
		'dataIndex' : 'famount',
		sortable : true
	},

	{
		'header' : '交期',
		'dataIndex' : 'farrivetime',
		// xtype:'datecolumn',
		// format:'Y-m-d H:i',
		width : 130,
		sortable : true
	}, {

		header : '地址',
		dataIndex : 'faddress',

		width : 200,
		sortable : true

	}, {
		'header' : '下单时间',
		'dataIndex' : 'fcreatetime',
		// xtype:'datecolumn',
		// format:'Y-m-d H:i',
		width : 130,
		sortable : true
	}, {
		header : '备注',
		dataIndex : 'fdescription'
	},{
		header:'生成纸板',
		dataIndex:'fcreateboard',
		renderer:function(val){
			if(val==0){
				return "未采购";
			}else if(val==1){
				return "产品采购";
			}else if(val==2){
				return "线下采购";
			}
		}
	}

	],
	selModel : Ext.create('Ext.selection.CheckboxModel')
})