
function print(imageTpl,fids,size,key){
//	var key = 3; 
	if(Ext.isEmpty(imageTpl)){
//			var key = 2; 
			var imageTpl = new Ext.XTemplate(
				'<tpl for=".">',//page-break-after: always;
				      '<div align="center" style="{[xindex <= xcount-1 ? "font-family:黑体;height:120mm; width:180mm;page-break-after: always;" : "font-family:黑体;height:120mm; width:180mm;"]}">',
						'<span align="center" style="font-family:黑体;font-size:24px;">{fcustname}(代合同)</span></br>',
						'<span align="center" style="font-family:黑体;font-size:22px;">送 货 凭 证</span></br>',
							'<table width="100%" height="10%">',
								'<tr align="left"><td width="28%">接单电话：{ftel}</td><td width="28%">接单传真：{ffax}</td><td width="17%"></td><td align="right" width="27%">No.{number}</td></tr>',
								'<tr align="left"><td>客户名称：{fcustomer}</td><td>联系电话：{fphone}</td><td colspan="2" style="text-overflow:ellipsis;white-space:nowrap;overflow:hidden;">送货地址：{fcustAddress}</td></tr>',
								'<tr><td colspan="4">' ,
									'<table  style="border-collapse:collapse;" width="100%" height="50%" border=1>',
							        '<tr align="center"><td height="25" width="30%">产品名称</td>',
							        '<td height="30" width="20%">规格</td>',
							        '<td height="30" width="7%">单位</td>',
							        '<td height="30" width="9%">数量</td>',
							        '<td height="30" width="11%">单价</td>',
							        '<td height="30" width="12%">金额</td>',
							        '<td height="30" width="21%">备注</td>',
							        '</tr>',
							         '<tpl foreach="product">',
							      	 '<tr align="center"><td style="text-overflow:ellipsis;white-space:nowrap;overflow:hidden;font-family:黑体;font-size:14px;" height="25">{fname}</td>',
									'<td height="20" style="font-family:黑体;font-size:14px;">{fspec}</td>',
									'<td height="20" style="font-family:黑体;font-size:14px;">{funit}</td>',
									'<tpl if="this.isZero(famount)">',
						            '<td height="20"></td>',
						        	'<tpl else>',
						            '<td height="20" style="font-family:黑体;font-size:14px;">{famount}</td>',
						        	'</tpl>',
						        	'<tpl if="this.isZero(fprice)">',
									'<td height="20"></td>',
									'<tpl else>',
									'<td height="20" style="font-family:黑体;font-size:14px;">{fprice}</td>',
									'</tpl>',
//									'<td height="20">{fprice}</td>',
									'<tpl if="this.isZero(fprices)">',
									'<td height="20"></td>',
									'<tpl else>',
									'<td height="20" style="font-family:黑体;font-size:14px;">{fprices}</td>',
									'</tpl>',
									'<td height="20" style="white-space:nowrap;overflow:hidden;font-family:黑体;font-size:14px;">{fdescription}</td>',
									'</tr>',
									'</tpl>',
									'<tpl if="psize==1">',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tpl elseif="psize==2">',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							           '<tpl elseif="psize==3">',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							           '<tpl elseif="psize==4">',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							           '<tpl elseif="psize==5">',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							           '<tpl elseif="psize==6">',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							           '<tpl elseif="psize==7">',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							           '<tpl elseif="psize==8">',
							         '<tr align="center"><td height="25">{fname}</td><td height="20">{fspec}</td><td height="20">{funit}</td><td height="20">{famount}</td><td height="20">{fprice}</td><td height="20"></td><td height="20">{fdescription}</td></tr>',
							        '</tpl>',
									'<tr><td  colspan="3" height="25" style="font-family:黑体;font-size:14px;">合计金额(大写)&nbsp;{chinesefprices}</td>',
									'<td colspan="2" style="font-family:黑体;font-size:16px;">总数量:{sum}</td>',
									'<td  colspan="2">&nbsp;&yen;:&nbsp;{fsumprices}</td>',
									'</tr>',
									'</tr>',
									'<tr align="left">',
									 '<td colspan="7" height="25" style="font-family:黑体;font-size:16px;"><p style="text-indent:-2em;padding:0 0 0 2em;">注：产品质量、数量以客户签字栏签字确认验收为准。</p></td>',
									'</tr>',
							  		'</table>' ,
						  		'</td></tr>',
						  '<tr align="left"><td>开单员：{fclerk}</td><td>时间：{fcreatetime}</td><td>司机：{fdriver}</td><td>客户签字：</td></tr>',
						  '<tr></tr>',
//						  '<tr align="left"><td colspan="3">注：第一联存根（白）  第二联客户（红）  第三联财务（黄）</td></tr>',
						   '</table>',
						  '<div align="left">注：第一联存根（白）  第二联客户（红）  第三联财务（黄）</div>',
						'</div>',
				    '</tpl>',
				    {
				    	 isZero: function(params){
				           return params== 0;
				        }
				    }
				);
	}
				Ext.define('appraise', {
				    extend: 'Ext.data.Model',
				    fields: [//p.fid,p.fname,p.fspec,p.fprice,p.funit,p.fdescription,sa.`famount`,sa.`fprice` fprices
				        { name:'fid', type:'string' },{ name:'fname'},{ name:'fspec'},{ name:'fprice'},{ name:'funit'},{ name:'fdescription'},
				        { name:'chinesefprices'},{name:'product'},{ name:'famount'},'fcustname','fcustomerid','fnumber','fcreatetime','fcreator','ffax','ftel','fdriver','fclerk','fcustomer','fphone','psize','fsumprices','sum','number','fcustAddress']
				});
				var store = Ext.create('Ext.data.Store', {
				    model:'appraise',
				    proxy: {
				        type: 'ajax',
				        url: 'getFTUList.do',
				        reader: {
				            type: 'json',
				            root: 'data'
				        }
				    }
				});
				
	                			var win = Ext.create('DJ.tools.myPrint.TemplatePrint',{
						  			myStore:store,
						  			myTpl:imageTpl,
						  			width:840,
						  			height:500
						  		});
								win.show().hide();
								var AppraiseLisStore = Ext.getCmp('DJ.tools.myPrint.TemplatePrint.view').getStore();
								AppraiseLisStore.on('datachanged',function(store, records, successful, eOpts){
									Ext.each(store.data.items,function(items){
										Ext.each(items.data.product,function(product){
											product.famount = eval(product.famount);
											product.fprice = eval(product.fprice);
											product.fprices = eval(product.fprices);
										})
									})
								})
	                			AppraiseLisStore.load({params:{'saledeliverid':fids,'size':size}, callback: function(records, operation, success) {
	                				var product = records[0].get('product');
	                				product[0].famount = eval(product[0].famount);// table table td{border:1px solid #000000;}
									Ext.DomHelper.overwrite(Ext.get('panelPrint').dom.contentWindow.document.body,"<style>@media print {@page {margin: 5mm 10mm 0mm 10mm;}} table{table-layout:fixed;}</style>" + Ext.getCmp('DJ.tools.myPrint.TemplatePrint.view').el.dom.innerHTML);
									Ext.get('panelPrint').dom.contentWindow.focus();
									Ext.get('panelPrint').dom.contentWindow.print();
									
									win.close();
									Ext.getCmp('DJ.order.Deliver.FTUsaledeliverList').store.loadPage(1);
									Ext.Ajax.request({
										url:'updateSyscfgsByKeyAndValue.do',
										params:{'key':'thePrintTP','value':key},
										success:function(response){
											var obj = Ext.decode(response.responseText);
											if(obj.success==true){
													
											}else{
											}
										}
									})
								}});
}
Ext.require(["Ext.ux.form.MyDateTimeSearchBox","DJ.tools.grid.MergeGrid"]);
Ext.define('DJ.order.Deliver.FTUsaledeliverList', {
	extend : 'Ext.c.GridPanel',
	title : "送货凭证",
	id : 'DJ.order.Deliver.FTUsaledeliverList',
	pageSize : 50,
	closable:true,
	url:'getFTUsaledeliver.do',
	Delurl : "delFTUsaledeliver.do",
	exporturl : "ExcelFTUsaledeliverList.do",// 导出为EXCEL方法
	mixins : ['DJ.tools.grid.mixer.MyGridSearchMixer'],
	onload : function() {
		var me = this;
		this.down('button[text*=新]').hide();
		this.down('button[text*=修]').hide();
		this.down('button[text*=查]').hide();
		this.down('button[text*=导]').setText('导出').hide();
		
		MyGridTools.rebuildExcelAction(me);//导出设置
		
		this.down('toolbar').add(0,{
			text:'新增',
			height:30,
			iconCls : 'addnew',
			handler:function(){
				Ext.create('Ext.Window',{
					width:800,
					modal : true,
					title:'送货凭证编辑界面',
					items:[Ext.create('DJ.order.Deliver.FTUsaledeliverEdit',{
						closable:false,
						parent:me.id,
						editstate:'add'
					})]
				}).show();
			}
		})
		Ext.util.CSS.createStyleSheet(".fstate-true{" + "color:#0000CC" + "}",
				'fstate-true');

		Ext.util.CSS.createStyleSheet(".fstate-false{" + "color:black" + "}",
				'fstate-false');
		
		
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件
	},
	Action_BeforeEditButtonClick : function(EditUI) {
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
	},
	Action_BeforeDelButtonClick : function(me, record) {
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	plugins:[{
		ptype:'mergegrid',
		key:'fid',
		selectMerge:true,
		isNoMerge:true,
		cols:[6,7,9,10,11]
	}],
	viewConfig:{
	   getRowClass:function(record,rowIndex,p,ds){
		    return record.get("fstate") == '2'
					? "fstate-true"
					: "fstate-false";
		}
	},
	custbar:[{
		text:'修改',
		height:30,
		iconCls : 'edit',
		handler:function(){
			
			this.nextNode().handler('修改');
			var editwin = Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit');
			editwin.on('afterrender',function(){
				Ext.each(editwin.query('toolbar button'),function(button){
					if(button.hidden){
						button.show();
					}
				})
				Ext.each(editwin.query('combobox,textfield'),function(ele){
					if(ele.disabled){
						ele.enable();
					}
				})
//				editwin.query('toolbar button').forEach(function(button){
//					if(button.hidden){
//						button.show();
//					}
//				})
//				editwin.query('combobox,textfield').forEach(function(ele){
//					if(ele.disabled){
//						ele.enable();
//					}
//				})
			})
			
		}
	},{
		text:'查看',
		height:30,
		iconCls : 'view',
		handler:function(btn){
			var win = Ext.create('Ext.Window',{
					width:800,
					modal : true,
					title:'送货凭证编辑界面',
					items:[Ext.create('DJ.order.Deliver.FTUsaledeliverEdit',{
						parent:this.up('grid').id,
						editstate:'view'
					})]
				});
			var record = this.up('grid').getSelectionModel().getSelection();
			var editwin = Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit');
			if(record.length>0){
				var el = this.up('grid').getEl();
				el.mask("系统处理中,请稍候……");
				editwin.down('combobox[name=fcustomer]').setValue(record[record.length-1].get('fcustoermid')).setDisabled(true);
				editwin.down('combobox[name=fcustomer]').setRawValue(record[record.length-1].get('fcustomer'));
				editwin.down('textfield[name=fid]').setValue(record[record.length-1].get('fid')).setDisabled(true);
				editwin.down('textfield[name=fsuppliername]').setValue(record[record.length-1].get('fsuppliername')).setDisabled(true);
				editwin.down('textfield[name=fsupplierid]').setValue(record[record.length-1].get('fsupplierid')).setDisabled(true);
				editwin.down('combobox[name=fclerk]').setValue(record[record.length-1].get('fclerk')).setDisabled(true);
				editwin.down('combobox[name=fdriver]').setValue(record[record.length-1].get('fdriver')).setDisabled(true);
				editwin.down('textfield[name=fphone]').setValue(record[record.length-1].get('fphone')).setDisabled(true);
				editwin.down('textfield[name=ffax]').setValue(record[record.length-1].get('ffax'));
				editwin.down('textfield[name=ftel]').setValue(record[record.length-1].get('ftel'));
				editwin.down('textfield[name=busNumber]').setValue(record[record.length-1].get('fbusNumber')).setDisabled(true);
				editwin.down('textfield[name=fcustAddress]').setValue(record[record.length-1].get('fcustAddress')).setDisabled(true);
				Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit.fnumber').setText('No.'+record[record.length-1].get('fnumber'));
				editwin.down('textfield[name=fnumber]').setValue(record[record.length-1].get('fnumber'));
				var fcustname = Ext.toArray(record[record.length-1].get('fcustname')+"(代合同)");
				Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit.fcustname').setText(fcustname.join(' '));
				Ext.Ajax.request({
					url:'getFTUproduct.do',
					params:{'saledeliverid':record[record.length-1].get('fid')},
					success:function(response){
						var obj = Ext.decode(response.responseText);
						var sumfprice = 0;
						var famount = 0;
						if(obj.success==true){
							editwin.down('button[text=保存并打印]').hide();
							editwin.down('button[text=保存]').hide();
							win.show();
							if(btn!='修改'){
								editwin.down('cTable').plugins[0].destroy();
							}
							if(obj.data){
								editwin.down('cTable').getStore().loadData(obj.data);	
//								obj.data.forEach(function(records){
//									sumfprice  += eval(records.fprices);
//									famount +=eval(records.famount);
//								})
								Ext.each(obj.data,function(records){
									sumfprice  += eval(records.fprices);
									famount +=eval(records.famount);
								})
								
								sumfprice = Math.round(sumfprice * 1000) / 1000;
								Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit.RMB').setValue(sumfprice);
								Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit.SUM').setValue(famount);
								var sum = Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit.upperSum');
									sum.generateItems(sumfprice);
							}
							
						}
						el.unmask();
					}
				})
//				editwin.down('cTable').setDisabled(true);
				
			}else{
				Ext.Msg.alert('提示','请选择一条记录！')
			}
			
		}
	},{
		text:'导出Excel',
		height : 30,
		iconCls : 'excel',
		listeners:{
			click:function(btn){
				if(btn.up('grid').store.getCount()==0){
					return false;
				}
			}
		},
		handler:function(){
		}
	},{
		text:'复制',
		handler:function(){
			this.previousSibling('button[text=修改]').handler();
			Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit').down('textfield[name=fid]').setValue('');
			Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit').editstate = 'add';
		}
	},{
	  		text:'打印',
	  		height:30,
	  		handler:function(btn,e,callback,fid){
	  			var me = this;
	  			var record = this.up('grid').getSelectionModel().getSelection();
	  			var fids = '';
	  			for(var i = 0;i<record.length;i++){
	  				if(fids.indexOf(record[i].get('fid'))<0){
	  					fids += record[i].get('fid');
	  					if(i<record.length-1){
	  						fids += ',';
	  					}
	  				}
	  			}
	  			if(Ext.isEmpty(fid)){
	  				if(record.length==0){
		  				Ext.Msg.alert('提示','请选择数据！');
		  				return;
	  				}
	  			}else{
	  				fids = fid;
	  			}
	  			
	  			var imageTpl = new Ext.XTemplate(
									'<tpl for=".">',//page-break-after: always;
									      '<div contenteditable="true" align="center" style="{[xindex <= xcount-1 ? "font-family:黑体;height:93mm; width:210mm;page-break-after: always;overflow:hidden;" : "font-family:黑体;height:93mm; width:210mm;overflow:hidden;"]}">',
											'<span style="display:block;float:left;">{title}</span><span  align="center" style="font-family:黑体;font-size:22px;">{fcustname}(代合同)</span><br/>',
											'<span  align="center" style="font-family:黑体;font-size:18px;">送 货 凭 证</span>',
												'<table width="100%" height="10%">',
													'<tr align="left"><td height="20"width="28%">接单电话：{ftel}</td><td width="25%">接单传真：{ffax}</td><td></td><td  align="right">No.{number}</td></tr>',
													'<tr align="left"><td height="20">客户名称：{fcustomer}</td><td>联系电话：{fphone}</td><td colspan="2" style="text-overflow:ellipsis;white-space:nowrap;overflow:hidden;">送货地址：{fcustAddress}</td></tr>',
													'<tr><td colspan="4">' ,//border=1
														'<table  style="border-collapse:collapse;border:1px solid #000000;" width="100%" height="100%" border=1>',
												        '<tr align="center"><td  height="24" width="24%">产品名称</td>',
												        '<td  width="18%">规格</td>',
												        '<td  width="6%">单位</td>',
												        '<td  width="10%">数量</td>',
												        '<td  width="12%">单价</td>',
												        '<td  width="12%">金额</td>',
												        '<td  width="18%">备注</td>',
												        '</tr>',
												         '<tpl foreach="product">',
												      	 '<tr align="center"><td style="text-overflow:ellipsis;white-space:nowrap;overflow:hidden;" height="24">{fname}</td>',
														'<td height="20">{fspec}</td>',
														'<td height="20">{funit}</td>',
														'<tpl if="this.isZero(famount)">',
											            '<td height="20"></td>',
											        	'<tpl else>',
											            '<td height="20">{famount}</td>',
											        	'</tpl>',
														'<tpl if="this.isZero(fprice)">',
											            '<td height="20"></td>',
											        	'<tpl else>',
											            '<td height="20">{fprice}</td>',
											        	'</tpl>',
														'<tpl if="this.isZero(fprices)">',
											            '<td height="20"></td>',
											        	'<tpl else>',
											            '<td height="20">{fprices}</td>',
											        	'</tpl>',
														'<td height="20" style="white-space:nowrap;overflow:hidden;">{fdescription}</td>',
														'</tr>',
														'</tpl>',
														'<tpl if="psize==1">',
												         '<tr align="center"><td height="24">{fname}</td><td >{fspec}</td><td >{funit}</td><td >{famount}</td><td >{fprice}</td><td ></td><td >{fdescription}</td></tr>',
												         '<tr align="center"><td height="24">{fname}</td><td >{fspec}</td><td >{funit}</td><td >{famount}</td><td >{fprice}</td><td ></td><td >{fdescription}</td></tr>',
												         '<tr align="center"><td height="24">{fname}</td><td >{fspec}</td><td >{funit}</td><td >{famount}</td><td >{fprice}</td><td ></td><td >{fdescription}</td></tr>',
												         '<tr align="center"><td height="24">{fname}</td><td >{fspec}</td><td >{funit}</td><td >{famount}</td><td >{fprice}</td><td ></td><td >{fdescription}</td></tr>',
												         '<tr align="center"><td height="24">{fname}</td><td >{fspec}</td><td >{funit}</td><td >{famount}</td><td >{fprice}</td><td ></td><td >{fdescription}</td></tr>',
												         '<tpl elseif="psize==2">',
												         '<tr align="center"><td height="24">{fname}</td><td >{fspec}</td><td >{funit}</td><td >{famount}</td><td >{fprice}</td><td ></td><td >{fdescription}</td></tr>',
												         '<tr align="center"><td height="24">{fname}</td><td >{fspec}</td><td >{funit}</td><td >{famount}</td><td >{fprice}</td><td ></td><td >{fdescription}</td></tr>',
												         '<tr align="center"><td height="24">{fname}</td><td >{fspec}</td><td >{funit}</td><td >{famount}</td><td >{fprice}</td><td ></td><td >{fdescription}</td></tr>',
												         '<tr align="center"><td height="24">{fname}</td><td >{fspec}</td><td >{funit}</td><td >{famount}</td><td >{fprice}</td><td ></td><td >{fdescription}</td></tr>',
												         '<tpl elseif="psize==3">',
												         '<tr align="center"><td height="24">{fname}</td><td >{fspec}</td><td >{funit}</td><td >{famount}</td><td >{fprice}</td><td ></td><td >{fdescription}</td></tr>',
												         '<tr align="center"><td height="24">{fname}</td><td >{fspec}</td><td >{funit}</td><td >{famount}</td><td >{fprice}</td><td ></td><td >{fdescription}</td></tr>',
												         '<tr align="center"><td height="24">{fname}</td><td >{fspec}</td><td >{funit}</td><td >{famount}</td><td >{fprice}</td><td ></td><td >{fdescription}</td></tr>',
												         '<tpl elseif="psize==4">',
												         '<tr align="center"><td height="24">{fname}</td><td >{fspec}</td><td >{funit}</td><td >{famount}</td><td >{fprice}</td><td ></td><td >{fdescription}</td></tr>',
												         '<tr align="center"><td height="24">{fname}</td><td >{fspec}</td><td >{funit}</td><td >{famount}</td><td >{fprice}</td><td ></td><td >{fdescription}</td></tr>',
												         '<tpl elseif="psize==5">',
												         '<tr align="center"><td height="24">{fname}</td><td >{fspec}</td><td >{funit}</td><td >{famount}</td><td >{fprice}</td><td ></td><td >{fdescription}</td></tr>',
												        '</tpl>',
												       
											  		'<tr><td colspan="7"><table style="border-collapse:collapse;border:0 solid" width="100%" height="100%">',
											  		'<tr><td  colspan="3" width="60%" height="24" style="border-right:1px solid;border-bottom:1px solid;font-family:黑体;font-size:15px;">合计金额(大写)&nbsp;{chinesefprices}</td>',
														'<td colspan="2" width="20%" style="border-right:1px solid;border-bottom:1px solid;font-family:黑体;font-size:15px;">&nbsp;总数量：{sum}</td>',
														'<td  colspan="2" width="20%" style="border-bottom:1px solid;font-family:黑体;font-size:15px;">&nbsp;&yen;：{fsumprices}</td>',
														'</tr>',
														'<tr>',
														 '<td colspan="7" height="24" style="font-family:黑体;font-size:15px;">注：产品质量、数量以客户签字栏签字确认验收为准。</td>',
														'</tr>',
														'</table></td></tr>',
														
												  		'</table>' ,
											  		'</td>',
											  		 '</tr>',
											  '<tr align="left"><td height="20">开单员：{fclerk}</td><td>开单时间：{fcreatetime}</td><td>司机：{fdriver}</td><td>客户签字：</td></tr>',
											   '</table>',
											'</div>',
									    '</tpl>',
									    {
									    	 isZero: function(params){
									           return params== 0;
									        }
									    }
									);
						var div = imageTpl.html.substring(imageTpl.html.indexOf('<div'),imageTpl.html.lastIndexOf('</div>')).replace('style="{[xindex <= xcount-1 ? "font-family:黑体;height:93mm; width:210mm;page-break-after: always;overflow:hidden;" : "font-family:黑体;height:93mm; width:210mm;overflow:hidden;"]}"','style="{[xindex <= xcount-1 ? "font-family:黑体;height:100mm; width:200mm;overflow:hidden;" : "font-family:黑体;height:100mm; width:200mm;overflow:hidden;"]}"');
						var tpl = '';
						for(var i = 0;i<3;i++){
							
							if( i == 2){
								div = div.replace('"font-family:黑体;height:100mm; width:200mm;overflow:hidden;" :','"font-family:黑体;height:100mm; width:200mm;page-break-after: always;overflow:hidden;" :');
								tpl += div+'</div>';
							}else{
								tpl += div+'</br><div style="BORDER-TOP: #00686b 1px dashed; OVERFLOW: hidden; HEIGHT: 1px"></div></div>';
							}
						}
					Ext.Ajax.request({
						url:'gainCfgByFkey.do',
						params:{'key':'thePrintTP'},
						success:function(response){
							var obj = Ext.decode(response.responseText);
							if(obj.success==true){
								if(obj.data[0].fvalue=='2'){
									print('',fids, 9,2);
								}else if(obj.data[0].fvalue=='3'){
									print(imageTpl,fids, 6,3);
								}else if(obj.data[0].fvalue=='4'){
									print('<tpl for=".">'+tpl+'</tpl>',fids, 6,4);
								}else{
											Ext.Msg.confirm({
												title : '提示',
												icon : this.QUESTION,
												msg : '请选择打印模板大小',
												buttons : Ext.Msg.YESNO,
												callback : function(btn) {
													if (btn == 'yes') {
														print('',fids, 6,2);
													} else if (btn == 'no') {
														print(imageTpl,fids, 6,3);
													} else if(btn='cancel'){
//														console.log(imageTpl.html);
//														var div = imageTpl.html.substring(imageTpl.html.indexOf('<div'),imageTpl.html.lastIndexOf('</tpl>')).replace('style="{[xindex <= xcount-1 ? "font-family:黑体;height:93mm; width:210mm;" : "font-family:黑体;height:93mm; width:210mm;"]}"','style="{[xindex <= xcount-1 ? "font-family:黑体;height:100mm; width:200mm;" : "font-family:黑体;height:100mm; width:200mm;"]}"');
//														var tpl = '';
//														for(var i = 0;i<3;i++){
//															if( i == 2){
//																div = div.replace('"font-family:黑体;height:100mm; width:200mm;" :','"font-family:黑体;height:100mm; width:200mm;page-break-after: always;" :');
//															}
//															tpl += div;
//															
//														}
														print('<tpl for=".">'+tpl+'</tpl>',fids, 6,4);
													}
												},
												buttonText : {
													yes : '二等分',
													no : '三等分',
													cancel: 'A4打印'
												},
												scope : this
											})
								}
							}else{
								Ext.Msg.alert('提示','请联系管理员！');
								me.up('panel').close();
							}
						}
					})
						
	  		}
	  	},{
	  		xtype:'textfield',
	  		listeners:{
	  			render:function(me){
	  				Ext.tip.QuickTipManager.register({
					    target: me.id,
					    text: '可输入发货单位、出库单编号、客户名称、产品名称、规格进行模糊查询'
					});
	  			},
	  			specialkey:function( me, e, eOpts){
	  				var begintime = me.up().down('datefield').getValue();
	  				var endtime = me.up().down('datefield').next('datefield').getValue();
	  				if(e.getKey()==13){
	  					if(!Ext.isEmpty(begintime)&&!Ext.isEmpty(endtime)){
	  						begintime = Ext.Date.format(begintime,'Y-m-d')+" 00:00:00";
	  						endtime = Ext.Date.format(endtime,'Y-m-d')+" 23:59:59";
	  					}
	  					
	  					me.up('grid').filterFTU(me,begintime,endtime);
	  				}
	  			}
	  		}
	  	},{
	  		text:'<font color="red">切换版本(新)</font>',
	  		handler:function(){
	  			var me = this;
	  			Ext.Ajax.request({
	  				url:'saveUIEditionChange.do',
	  				params:{FoldUI:me.up('grid').id,FnewUI:'DJ.order.Deliver.newFTUsaledeliverList'},
	  				success:function(response){
	  					var obj = Ext.decode(response.responseText);
	  					if(obj.success==true){
							var treepanel = me.up('grid').up('tabpanel').previousSibling();
							Ext.each(treepanel.query('treepanel'),function(tree){
								Ext.each(tree.items.items[0].store.data.items,function(item,index){
									if(item.data.furl=='DJ.order.Deliver.FTUsaledeliverList'){
										me.up('grid').destroy();
										item.data.furl = 'DJ.order.Deliver.newFTUsaledeliverList';
										tree.getSelectionModel().select(index);
										tree.fireEvent('itemclick','',item);
										return true;
									}
								})
							})
	  					}
	  				}
	  			})
	  		}
	  	},'-',{
	  		width:450,
			xtype : "mydatetimesearchbox",
			conditionDateField : "sa.fcreatetime",
			labelFtext : "开单时间",
//			additionalCondition : true,
			useDefaultfilter : true,
			tip: '',
//			filterMode : false
			beforeLoad:function(storeT, begainDate, endDate, myfilter){
				this.up('grid').filterFTU(this.up('toolbar').down('textfield'),begainDate,endDate);
				return false;
			}
	  	},{
	  		text:'单据回收',
//	  		hidden:true,
	  		handler:function(){
	  			var me = this,fnumber='';
	  			var record = this.up('grid').getSelectionModel().getSelection();
	  			if(record.length==0){
	  				Ext.Msg.alert('提示','请选择数据！');
	  				return;
	  			}
	  			for(var i =0;i<record.length;i++){
	  				fnumber += record[i].get('fnumber');
	  				if(i<record.length-1){
	  					fnumber += ',';
	  				}
	  			}
	  			Ext.Ajax.request({
	  				url:'updateFTUstate.do',
	  				params:{'fnumber':fnumber},
	  				success:function(response){
	  					var obj = Ext.decode(response.responseText);
	  					if(obj.success==true){
//	  						record.forEach(function(rec){
//				  				var recid = me.up('grid').getView().getRowId(rec);
//				  				var r = document.getElementById(recid);
//				  				r.setAttribute('style','color:red');
//				  			})
	  						me.up('grid').store.loadPage(1);
	  						djsuccessmsg(obj.msg);
	  					}else{
	  						Ext.Msg.alert('提示',obj.msg);
	  					}
	  				}
	  			})
	  		}
	  	},{
	  		text:'报表',
	  		handler:function(){
	  			Ext.create('Ext.Window',{modal:true,layout:'fit',title : "对账单",width:800,height:400,items:Ext.create('DJ.order.Deliver.FTUstatementList')}).show();
	  		}
	  	}],
	 filterFTU:function(me,begintime,endtime){
	 		var myfilter = [];
	  					var store = me.up('grid').store;
		  					myfilter.push({
							myfilterfield : "sa.fnumber",
							CompareType : "like",
							type : "string",
							value : me.getValue()
						},{
							myfilterfield : "c.fname",
							CompareType : "like",
							type : "string",
							value : me.getValue()
						},{
							myfilterfield : "p.fname",
							CompareType : "like",
							type : "string",
							value : me.getValue()
						},{
							myfilterfield : "p.fspec",
							CompareType : "like",
							type : "string",
							value : me.getValue().replace(/\*/g,'X')
						},{
							myfilterfield : "sa.fsuppliername",
							CompareType : "like",
							type : "string",
							value : me.getValue()
						});
						store.setDefaultmaskstring("#0 or #1 or #2 or #3 or #4");
						if(!Ext.isEmpty(begintime)&&!Ext.isEmpty(endtime)){
							myfilter.push({
								myfilterfield : "sa.fcreatetime",
								CompareType : ">=",
								type : "datetime",
								value : begintime//Ext.Date.format(begintime,'Y-m-d')+" 00:00:00"
							},{
								myfilterfield : "sa.fcreatetime",
								CompareType : "<=",
								type : "datetime",
								value : endtime//Ext.Date.format(endtime,'Y-m-d')+" 23:59:59"
							})
							store.setDefaultmaskstring(" (#0 or #1 or #2 or #3 or #4) and( #5 and #6)");
						}
						store.setDefaultfilter(myfilter);
						
						store.loadPage(1);
	 },
	fields : [{
		name : 'fid'
	}, {
		name : 'fsuppliername',
		 myfilterfield : 'sa.fsuppliername',
		 myfiltername : '发货单位',
		 myfilterable : true
	}, {
		name : 'fsupplierid'
//		myfilterfield : 'd.fordernumber',
//		myfiltername : '订单编号',
//		myfilterable : true
	}, {
		name : 'fnumber',
		myfilterfield : 'sa.fnumber',
		myfiltername : '出库单编号',
		myfilterable : true
	}, {
		name : 'fcreatetime'
	}, {
		name : 'fcreator'
	},{
		name:'ffax'
	}, {
		name : 'ftel'
	}, {
		name : 'fdriver'
	}, {
		name : 'fclerk'
	}, {
		name : 'fcustomer',
		myfilterfield : 'c.fname',
		myfiltername : '客户名称',
		myfilterable : true
	},{
		name:'fphone'
	},{
		name:'fname',
		myfilterfield : 'p.fname',
		myfiltername : '产品名称',
		myfilterable : true
	},'fspec','funit',{
		name:'famount'
//		,
//		convert : function(v, record) {
//			return eval(v);
//		}
	},{
		name:'fdanjia',
		myfilterfield : 'p.fprice',
		myfiltername : '单价',
		myfilterable : true
//		,
//		convert : function(v, record) {
//			return eval(v);
//		}
	},{
		name:'fprice',
		convert : function(v, record) {
			return eval(v);
		}
	},'fdescription','fdriver','fclerk','fprinttime','fprintcount','fcustname','fstate','fbusNumber','fcustAddress','fcustoermid','fproductid','fsupplierid','fsuppliername','string1','string2','string3','string4','string5','string6','string7','string8','string9','string10'],
columns : {
	defaults: { // defaults are applied to items, not the container
	    sortable: false
	},
	items:[{
		xtype: 'rownumberer',
		text:'序号',
		hidden:true
	},{
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true
	},{
		'header' : '发货单位',
		dataIndex : 'fsuppliername',
		width : 90,
		flex : 1,
		align : 'center'
	}, {
		'header' : '客户名称',
		width : 200,
		'dataIndex' : 'fcustomer',
		width:80,
		align : 'center'
	}, {
		'header' : '出库单编号',
		'dataIndex' : 'fnumber',
		width:135,
		align : 'center'
	},{
		'header' : '产品名称',
		'dataIndex' : 'fname',
		width:130,
		align : 'center'
	},{
		'header' : '规格',
		'dataIndex' : 'fspec',
		width:130,
		align : 'center'
	},{
		'header' : '单位',
		'dataIndex' : 'funit',
		width:50,
		align : 'center' 
	},{
		'header' : '数量',
		'dataIndex' : 'famount',
		flex : 1,
		align : 'center'
	},{
		'header' : '单价',
		'dataIndex' : 'fdanjia',
		flex : 1,
		align : 'center',
		renderer:function(val){
			if(eval(val)!=0){
				return val;
			}
		}
	},{
		'header' : '金额',
		'dataIndex' : 'fprice',
		flex : 1,
		align : 'center',
		renderer:function(val){
			if(eval(val)!=0){
				return val;
			}
		}
	}, {
		'header' : '开单时间',
		'dataIndex' : 'fcreatetime',
		flex : 1,
		align : 'center', 
		renderer:function(val){
			return val.substr(0,10);
		}
	},{
		'header' : '开单员',
		'dataIndex' : 'fclerk',
		width:70,
		align : 'center' 
	},{
		'header' : '司机',
		'dataIndex' : 'fdriver',
		width:70,                                                                                                                                                                                                                                                                                                                                                                                                                                
		align : 'center' 
	},{
		'header' : '出库时间',
		'dataIndex' : 'fprinttime',
		flex : 1,
		align : 'center', 
		renderer:function(val){
			return val.substr(0,16);
		}
	},{
		'header' : '打印次数',
		'dataIndex' : 'fprintcount',
		flex : 1,
		align : 'center' 
	},{
		'header' : '备注',
		'dataIndex' : 'fdescription',
		flex : 1,
		align : 'center' 
	},{
		header:'string1',
		dataIndex:'string1',
		align : 'center',
		hidden :true,
		hideable:false
	},{
		header:'string2',
		dataIndex:'string2',
		align : 'center',
		hidden :true,
		hideable:false
	},{
		header:'string3',
		dataIndex:'string3',
		align : 'center',
		hidden :true,
		hideable:false
	},{
		header:'string4',
		dataIndex:'string4',
		align : 'center',
		hidden :true,
		hideable:false
	},{
		header:'string5',
		dataIndex:'string5',
		align : 'center',
		hidden :true,
		hideable:false
	},{
		header:'string6',
		dataIndex:'string6',
		align : 'center',
		hidden :true,
		hideable:false
	},{
		header:'string7',
		dataIndex:'string7',
		align : 'center',
		hidden :true,
		hideable:false
	},{
		header:'string8',
		dataIndex:'string8',
		align : 'center',
		hidden :true,
		hideable:false
	},{
		header:'string9',
		dataIndex:'string9',
		align : 'center',
		hidden :true,
		hideable:false
	},{
		header:'string10',
		dataIndex:'string10',
		align : 'center',
		hidden :true,
		hideable:false
	}
	]},
	
	selModel : {selType:'checkboxmodel'}
})