//Ext.showLuImgPreview= function(ev) {
//			var grid = Ext.getCmp("DJ.order.saleOrder.MyProductBoxOrdersList");
//			var records = MyCommonToolsZ.pickSelectItems(grid);
//			if (records == -1) {
//				return;
//			}
//			var fid = records[0].data.cpid;
//			grid.magnifier.loadImages({
//				fid : fid
//			});
//			// var coord = e.getXY();
//			ev = ev || window.event;
//			var mousePosition = function(ev) {
//				if (ev.pageX || ev.pageY) {
//					return {
//						x : ev.pageX,
//						y : ev.pageY
//					};
//				}
//				return {
//					x : ev.clientX + document.body.scrollLeft
//							- document.body.clientLeft,
//					y : ev.clientY + document.body.scrollTop
//							- document.body.clientTop
//				};
//			}
//
//			var mousePos = mousePosition(ev);
//			var topLength = mousePos.y + 5;
//			if (topLength + 300 > document.body.clientHeight) {
//				topLength = document.body.clientHeight - 300;
//			}
//			grid.magnifier.showAt({
//				left : mousePos.x + 80,
//				top : topLength
//			});
//
//		};
var tplAA = new Ext.XTemplate(
			'<table width="150" height="100" border="0" cellspacing="10">',
			'<tr><td valign="center"><tpl if="amount != 0">{amount}只</tpl></td></tr>',
			'<tr><td valign="center">{[this.renderTime(values.arrivetime)]}</td></tr></table>' ,
			{renderTime : function(val){
				return val.replace("AM","上午").replace("PM","下午");
			}
			}
		);
		
var tplLL = new Ext.XTemplate(
			'<table width="150" height="100" border="0" cellspacing="10">',
			'<tr><td valign="center">{supname}</td></tr>',
			'<tr><td valign="center">{materiaspec}</td></tr>',
			'<tr><td valign="center">{mpdtname}</td></tr></table>'
		);		
		
var tplSC = new Ext.XTemplate(
			'<table width="150" height="100" border="0" cellspacing="10">',
			'<tr><td valign="center"><tpl if="!Ext.isEmpty(pdtseries)">{pdtseries}/{pdtcolor}</tpl></td></tr>',			
			'<tr><td valign="center">{pdtworkproc}</td></tr></table>'
		);				
var tplCP = new Ext.XTemplate(
			'<table width="150" height="100" border="0" cellspacing="10">',
//			'<tr><td rowspan = 3><img onclick="Ext.showLuImgPreview(event)" src="getFileSourceByParentId.do?fid={cpid}" width="150" height="150" /></td><td valign="center">{pdtname}</td></tr>',
			'<tr><td rowspan = 3><img src="getFileSourceByParentId.do?fid={cpid}" width="150" height="150" /></td><td valign="center">{pdtname}</td></tr>',
			'<tr><td valign="center" style = "text-align: left">{pdtspec}</td></tr>',
			'<tr><td valign="center" style = "text-align: left">{materialinfo}</td></tr></table>'
		);					
Ext.define('DJ.order.saleOrder.MyProductBoxOrdersList', {
	extend : 'DJ.myComponent.grid.MySimpleConciseGrid',
	id : 'DJ.order.saleOrder.MyProductBoxOrdersList',
	url : 'GetMyProductBoxOrdersList.do',
	title : '我的业务',
	closable : true,
//	Delurl : " ",
//	EditUI : " ",
	pagingtoolbarDock : 'bottom',
	showSearchAllBtn : false,
	printTplSForWord : '<div style="page-break-after:always;"  align="center" height="300mm" width="210mm"><table width="100%" height="10"  cellspacing="0" border="0" align="center"><tr><td width="33%">&nbsp;</td><td width="34%" align="center"><p style="font-size: 14pt;">{suppliername}</p></td><td width="33%">&nbsp;</td></tr><tr><td>&nbsp;</td><td width="34%" align="center"><p style="font-size: 18pt;">生产任务单</p></td><td width="33%">&nbsp;</td></tr><tr><td>客户：{customername}</td><td width="34%">&nbsp;</td><td width="33%" align="right">{fnumber}</td></tr></table><table width="100%" height="850" border="1" align="center" cellspacing="0" bordercolor="#000000"><tr><td width="15%" height="5%" align="center">产品名称</td><td height="5%" colspan="2" align="center">{productname}</td><td width="15%" height="5%" align="center">产品规格</td><td height="5%" colspan="2" align="center">{fcharacter}</td></tr><tr><td width="15%" align="center">数量</td><td width="15%" align="center">{famount}</td><td width="15%" align="center">交期</td><td width="15%" align="center">{farrivetime}</td><td width="15%" align="center">材料</td><td width="15%" align="center">{materialname}</td></tr><tr><td width="15%" align="center">来料供应商</td><td width="15%" align="center">{materialsuppliername}</td><td width="15%" align="center">来料时间</td><td width="15%" align="center">{materialfarrivetime}</td><td width="15%" align="center">箱片信息</td><td width="15%" align="center">{boxpie}</td></tr><tr><td width="15%" align="center">横向压线</td><td colspan="2" align="center">{fhformula}</td><td width="15%" align="center">纵向压线</td><td colspan="2" align="center">{fvformula}</td></tr><tr><td width="15%" align="center">印刷颜色</td><td colspan="5">{fprintcolor}</td></tr><tr height="60" ><td width="15%" align="center">工艺路线</td><td colspan="5"><font style="line-height:18pt">{fworkproc}</font></td></tr><tr height="15%"><td width="15%" height="100" align="center">特殊要求</td><td colspan="5"><font style="line-height:20pt">{fdescription}</font></td></tr><tr><td width="15%" align="center">工序记录</td><td width="15%" align="center">生产数</td><td width="15%" align="center">损耗数</td><td colspan="3" align="center">备注</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td width="15%">&nbsp;</td><td colspan="3">&nbsp;</td></tr><tr height="40%"><td height="320" colspan="6" align="center" valign="middle"><a href="{imgpath}">{imgpath}</a></td></tr></table></div>',
	mixins : ['DJ.tools.grid.MySimpleGridMixer','DJ.tools.grid.mixer.MyGridSearchMixer'],
	viewConfig:{
		getRowClass:function(record,rowIndex,p,ds){
	    	if(record.get("createboard") == '1'){
	    		return "cb-true";
	    	}
		}		
	},
	dockedItems: [{		
    xtype: 'toolbar',
    dock: 'top',
    items: [{
	text : '今日',
	handler : function(){
		this.up('grid').getStore().	setDefaultfilter([]);
		this.up('grid').getStore().	setDefaultmaskstring();
		this.up('grid').getStore().loadPage(1);				
	}
	},'|',{
 	text : '全部',
	handler : function(){
		this.up('grid').getStore().	setDefaultfilter("[]");
		this.up('grid').getStore().	setDefaultmaskstring();
		this.up('grid').getStore().loadPage(1);				
	}       	
	}]
	},{
		xtype: 'toolbar',
        dock: 'top',
	     items: [{
		text : '取消接收',
		handler : function() {
			var grid = Ext.getCmp("DJ.order.saleOrder.MyProductBoxOrdersList");
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
								Ext.getCmp("DJ.order.saleOrder.MyProductBoxOrdersList").store.load();
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
	},{
	    text : '采购纸板',
		handler : function() {
			var m = this;
			var grid = this.up('grid');
			var recorders = MyCommonToolsZ.pickSelectItems(grid);
			if (recorders.length != 1) {
				Ext.Msg.alert('提示','请选择一条数据 ！');
				return;				
			}			
			if(recorders[0].get('fstate') == 0){
				Ext.Msg.alert('提示','未接收不能采购纸板 ！');
				return;						
			}
			if(recorders[0].get('createboard') == 1){
				Ext.Msg.alert('提示','已经生成纸板订单的不允许重复生成 ！');
				return;						
			}
			var fid = recorders[0].get('fid');
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
				url : 'Getpdtboardcard.do',
				params : {
					pdtid : recorders[0].get('pdtid')
				},
				success : function(response, option) {
					var obj = Ext.decode(response.responseText);
					if (obj.success == true) {
						if(obj.total>0){
							if(obj.data[0].fisboardcard == "1"){
							    	Ext.Ajax.request({
										url:'getUserToCustAddress.do',
										success:function(response){
											var obj = Ext.decode(response.responseText);
											if(obj.success==true){
												if(!Ext.isEmpty(obj.data)){
													if(obj.data.length==1){
														toDeliversBoard(obj.data[0],function(){
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
							if(obj.data[0].fisboardcard== "0"){
								Ext.MessageBox.alert('提示','内盒暂时不支持采购！');
							}
							if(obj.data[0].fisboardcard== "-1"){
								el = grid.getEl();
							    el.mask("请先维护此产品档案");
							    var task = new Ext.util.DelayedTask(function(){
							    var winpdt = Ext.create('DJ.System.supplier.SupOfCustProductEdit');
							 	winpdt.loadfields(recorders[0].get("pdtid"));
							  	winpdt.seteditstate('edit');
							    winpdt.setTitle('产品档案');
							  	winpdt.show();	
							  	winpdt.query("button[itemId='内盒信息']")[0].hide()
							  	winpdt.fisboardcard.setValue(1);
							   el.unmask();									   
//							   winpdt.query('button[id*=savebutton]')[0].getEl().on('click', function(){ 							   	    
//							   		m.handler();
//							   });
							    })
							    task.delay(2000);
							}
						}
						else{
							Ext.MessageBox.alert('错误','客户产品已经删除！');
						}
					}
					else{
						Ext.MessageBox.alert('错误', obj.msg);
					}
				}
			})
		}
	},{
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
				proxy : {
					type : 'ajax',
					url : 'getPrintInfoWithProductplan.do',
					reader : {
						type : 'json',
						root : 'data'
					}
				},
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
						});																
						var htmlSs = [];
						var xtl = new Ext.XTemplate(grid.printTplSForWord);						
						Ext.each(records, function(ele, index, all) {
							var goalH = xtl.apply(ele.data);
							if (index == records.length - 1) {
								goalH = goalH.replace('page-break-after:always;', '');
							}
							htmlSs.push(goalH);
						});
						var printerF = Ext.get('mySimpleGridPrinterPrinterIFId');
						Ext.DomHelper.overwrite(printerF.dom.contentWindow.document.body, htmlSs);
						var el = grid.getEl();
						el.mask("系统处理中,请稍候……");															
						setTimeout(function() {
							el.unmask();
							MySimpleHTMLToWord.gainWordFromHTML(printerF.dom.contentWindow.document.documentElement.outerHTML);
						}, 1000);
					}
				}
			});
		
		}
	},{
		text : '查看修改档案',
		handler : function() {
			var grid = this.up('grid');
			var record = grid.getSelectionModel().getSelection();
			if (record.length != 1) {
				Ext.Msg.alert('提示','请先选择您要操作的行!');									
				return;
			};
			var winAdd = Ext.create('DJ.System.supplier.SupOfCustProductEdit');		
			winAdd.loadfields(record[0].get("pdtid"));
			winAdd.seteditstate('edit');
			winAdd.setTitle('产品档案');
			winAdd.parent = grid.id;
			//winAdd.query("button[itemId='内盒信息']")[0].hide()
			winAdd.show();
		}
	},{
		text : '刷新',
		handler : function() {
			this.up('grid').getStore().	setDefaultfilter([]);
			this.up('grid').getStore().	setDefaultmaskstring();
			this.up('grid').getStore().loadPage(1);			
		}
	},{
			xtype : 'tbspacer',
			flex : 1
	},{
			xtype : 'mymixedsearchbox',
			condictionFields : ['cust.fname', 'pdt.fname','cpdt.fspec', 'pdt.fmaterialinfo',
					'mpdt.fname', 'sup.fname', 'pdt.fprintcolor','pdt.fworkproc'],
			tip : '请输入采购方、产品名称、规格、材料、制造商、印刷颜色、工艺',
			beforeSearchActionForStore : function(myfilter, maskstrings,condictionValue) {
				Ext.each(myfilter,function(ele){
					if(ele.myfilterfield=='cpdt.fspec'){
						ele.value = ele.value.replace(/\*/g,'_').replace(/\X/g,'_').replace(/\x/g,'_');
					}
				})			
			},
			useDefaultfilter : true,
			filterMode : true
	}, {
			xtype : 'button',
			text : '筛选条件',
			menu : {
				xtype : 'menu',
				items : [{
	
					xtype : 'mydatetimesearchbox',
					filterMode : true,
					useDefaultfilter : true,
					labelFtext : '下单时间',
					conditionDateField : 'pp.fcreatetime',
					startDate : Ext.Date.subtract(new Date(), Ext.Date.MONTH, 2)
				}, {
	
					xtype : 'mydatetimesearchbox',
					filterMode : true,
					useDefaultfilter : true,
					labelFtext : '交        期',
					conditionDateField : 'pp.farrivetime'
				}]
			}
	}]
	}],
	onload : function() {
		var me = this;
		me.magnifier = Ext.create('Ext.ux.form.Magnifier');
		Ext.util.CSS.createStyleSheet(".cb-true{" + "color:#0000CC" + "}",
				'cb-true');
		this.getStore().on('load',function(){
			Ext.defer(function(){
				var images = me.getEl().query('img'),
				now = new Date().getTime(),url,index;
				Ext.Array.each(images,function(img){
					url = img.src;
					index = url.indexOf('&');
					img.src = url.substring(0,index==-1?url.length:index) + '&_dc=' + now;
					Ext.fly(img).on('click',function(e){
						var magnifier = me.magnifier;
						magnifier.loadImages({
							fid : me.getSelectionModel().getSelection()[0].get('cpid')
						});
						var coord = e.getXY();
						var top = coord[1] + 5;
						if (top + 300 > document.body.clientHeight) {
							top = document.body.clientHeight - 300;
						}
						magnifier.showAt({
							left : coord[0] + 80,
							top : top
						});
					});
				});
			},50);
		})				
	},
	fields : [{
		name : 'fid'
	}, {
		name : 'ppnumber'
	}, {
		name : 'ppcreatetime'
	}, {
		name : 'arrivetime'
	},{
		name : 'fstate'		
	},{
		name : 'custname'
	}, {
		name : 'pdtname'
	},{
		name : 'cpid'
	},{
		name : 'pdtid'
	},{
		name : 'pdtspec'
	},{
		name : 'materialinfo'
	},{
		name : 'amount',
		type : 'int'
	}, {
		name : 'supname'
	},{
		name : 'mpdtname'
	},{
		name : 'materiaspec'
	},{
		name : 'pdtseries'
	},{
		name : 'pdtcolor'
	},{
		name : 'pdtworkproc'
	},{
		name : 'pdtdesc'
	},{
		name : 'createboard'
	},{
		name : 'fisboardcard'
	}
	],
	
	columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false
	}, {
		'header' : 'fisboardcard',
		'dataIndex' : 'fisboardcard',
		hidden : true,
		hideable : false
	},{
		'header' : '制造订单号',
		'dataIndex' : 'ppnumber',
		align : 'center',
		sortable : true
	}, {
		'header' : '下单时间',
		'dataIndex' : 'ppcreatetime',
		hidden : true,
		hideable : true	
	},{
		'header' : '采购订单号',
		'dataIndex' : 'ppcreatetime',
		hidden : true,
		hideable : true	
	},{
		'header' : '采购方',
		'dataIndex' : 'custname',
		align : 'center',
		sortable : true
	}, {
		xtype : 'templatecolumn',
		align : 'center',
		'text' : '产品信息',
		tpl : tplCP,
		width : 320,
		sortable : true
	}, {
		xtype : 'templatecolumn',
		width : 150,
		'text' : '数量 / 交期',
		align : 'center',	
		tpl : tplAA,
		sortable : true
	}, {
		xtype : 'templatecolumn',
		width : 150,
		'text' : '来料信息',
		align : 'center',
		tpl : tplLL,
		sortable : true
	}, {
		xtype : 'templatecolumn',
		width : 200,
		'text' : '生产信息',
		align : 'center',
		tpl : tplSC,
		sortable : true
	}, {
		'header' : '采购纸板',
		'dataIndex' : 'createboard',
		renderer:function(val){
			if(val==0){
				return "未采购";
			}else if(val==1){
				return "产品采购";
			}else if(val==2){
				return "线下采购";
			}
		}
	}, {
		'header' : '备注',
		'dataIndex' : 'pdtdesc',
		align : 'center',
		sortable : true,
		flex : 1
	}]
});