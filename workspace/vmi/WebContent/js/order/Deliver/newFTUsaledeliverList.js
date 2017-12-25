
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
								win.show();//.hide();
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
									
//									win.close();
									Ext.getCmp('DJ.order.Deliver.newFTUsaledeliverList').store.loadPage(1);
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
Ext.define('DJ.order.Deliver.newFTUsaledeliverList', {
	extend : 'Ext.c.GridPanel',
	title : "送货凭证",
	id : 'DJ.order.Deliver.newFTUsaledeliverList',
	pageSize : 50,
	closable:true,
	url:'getFTUsaledeliver.do',
	Delurl : "delFTUsaledeliver.do",
	exporturl : "ExcelFTUsaledeliverList.do",// 导出为EXCEL方法
	mixins : ['DJ.tools.grid.mixer.MyGridSearchMixer'],
	listeners:{
		afterrender:function(me){
			var p = [];
			p.push({'fname':'产品名称','fieldtype':0,'fsaledeliverentry':'fcusproductname'},{'fname':'规格','fieldtype':0,'fsaledeliverentry':'fspec'},{'fname':'单位','fieldtype':0,'fsaledeliverentry':'funit'},{'fname':'数量','fieldtype':3,'fdecimals':0,'fsaledeliverentry':'famount'},{'fname':'单价','fieldtype':1,'fdecimals':3,'fsaledeliverentry':'fdanjia'},{'fname':'金额','fieldtype':1,'fdecimals':2,'fcomputationalformula':'数量*单价','fsaledeliverentry':'fprice'},{'fname':'备注','fieldtype':0,'fsaledeliverentry':'fdescription'})
			Ext.Ajax.request({
				url:"saveAllFtuParams.do",
				params:{FtuParameter:Ext.encode(p)},
				success:function(response){
					var obj = Ext.decode(response.responseText);
					if(obj.success==true){
						
					}
				}
			})
			me.store.on('load',function(){
				Ext.Ajax.request({
					url:'getFtuParameter.do',
					success:function(response){
						var obj = Ext.decode(response.responseText);
						var fparamsName  = [];
						var columnsName  = [];
						if(obj.success==true){
							Ext.each(me.columns,function(column,index){
								columnsName.push(column.dataIndex);
								Ext.each(obj.data,function(d){
									if(index==0){
										fparamsName.push(d.fsaledeliverentry);
									}
									if(column.text==d.fname){
										column.setText(d.falias)
										column.show();
									}
									if(column.dataIndex==d.fsaledeliverentry){
										column.setText(d.falias)
										column.hideable = true;
										column.show();
									}
								})
							})
							var newArray = Ext.Array.difference(columnsName,fparamsName);//待优化
							Ext.each(newArray,function(a){
								Ext.each(p,function(param){
									if(a==param.fsaledeliverentry){
										Ext.each(me.columns,function(column,index){
											if(column.dataIndex==a){
												column.hide();
											}
										})
									}else{
										if(a.indexOf('string')>-1){
											Ext.each(me.columns,function(column,index){
												if(column.dataIndex==a){
													column.hide();
												}
											})
										}
									}
								})
							})
						}
					}
				})
				
			})
		},
		itemdblclick:function( me, record, item, index, e, eOpts ){
			me.up().down('button[text=新查看]').handler();
		}
	},
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
				me.down('button[text=新新增]').handler();
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
			this.up().down('button[text=新编辑]').handler(this);
		}
	},{
		text:'查看',
		height:30,
		iconCls : 'view',
		handler:function(btn){
			this.up().down('button[text=新查看]').handler(this);
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
		handler:function(me){
	  			var records = me.up('grid').getSelectionModel().getSelection();
	  			if(records.length==0){
	  				Ext.Msg.alert('提示','请选择一条数据!');
	  				return;
	  			}
	  			var fid = records[0].data.fid;
	  			function req(param){
	  				Ext.Ajax.request({
	  				url:'getFtusaledeliver.do',
	  				params:{fid:fid},
	  				success:function(response){
	  					var obj = Ext.decode(response.responseText);
	  					if(obj.success==true){
	  						Ext.Ajax.request({
								url:'getFtuPrintTemplate.do',
								params:{key:param},
								success:function(response, opts){//3联的
									Ext.create('DJ.order.Deliver.FtuTemplateEdit',{
										data:obj.data,
										editstate:'edit',
										copy:true,
										html:response.responseText
									}).show();
									Ext.getCmp('DJ.order.Deliver.FtuTemplateEdit').body.dom.querySelector('div[name=fid]').textContent='';
								},
								failure: function(response, opts) {
							        Ext.Msg.alert('提示','请联系管理员！')
							    }
							})
	  					}
	  				}
	  			})
	  			}
	  			
	  			Ext.Ajax.request({
				url:'gainCfgByFkey.do',
				params:{'key':'thePrintTP'},
				success:function(response){
					var obj = Ext.decode(response.responseText);
					if(obj.success==true){
						if(obj.data[0].fvalue=='2'){//2等分
							req(2);
						}else if(obj.data[0].fvalue=='3'){//3等分
							req(3);
						}else{
							Ext.Msg.alert('提示','请先去设置模板');
						}
					}
				}
  				})
		}
	},{
	  		text:'打印',
	  		height:30,
	  		handler:function(btn,e,callback,fid){
	  			this.up().down('button[text=新打印]').handler();
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
	  	},{
	  		text:'打印模板修改',
	  		hidden:true,
	  		handler:function(me,e,fn){
	  			 function req(param){
		  			Ext.Ajax.request({
						url:'getFtuPrintTemplate.do',
						params:{key:param},
						success:function(response, opts){//3联的
							var win =	Ext.create('DJ.order.Deliver.FtuPrintTemplate',{
									html:response.responseText
								}).show();
							if(Ext.isFunction(fn)){
								win.on('close',function(){
									fn();
								})
							}
						},
						failure: function(response, opts) {
					        Ext.Msg.alert('提示','请联系管理员！')
					    }
					})
	  			}
  				Ext.Ajax.request({
					url:'gainCfgByFkey.do',
					params:{'key':'thePrintTP'},
					success:function(response){
						var obj = Ext.decode(response.responseText);
						if(obj.success==true){
							if(obj.data[0].fvalue=='2'){//2等分
								req(2);
							}else if(obj.data[0].fvalue=='3'){//3等分
								req(3);
							}else{
								Ext.Msg.confirm({
									title : '提示',
									icon : this.QUESTION,
									msg : '请选择打印模板大小',
									buttons : Ext.Msg.YESNO,
									callback : function(btn) {
										if (btn == 'yes') {
											Ext.Ajax.request({
												url:'updateSyscfgsByKeyAndValue.do',
												params:{'key':'thePrintTP','value':2},
												success:function(response){
													var obj = Ext.decode(response.responseText);
													if(obj.success==true){
														req(2);
													}
												}
											})
										} else if (btn == 'no') {
											Ext.Ajax.request({
												url:'updateSyscfgsByKeyAndValue.do',
												params:{'key':'thePrintTP','value':3},
												success:function(response){
													var obj = Ext.decode(response.responseText);
													if(obj.success==true){
														req(3);
													}
												}
											})
										} 
									},
									buttonText : {
										yes : '二等分',
										no : '三等分'
									},
									scope : this
								})
							
							}
						}
					}
  				})
	  		}
	  	},{
	  		text:'新新增',
	  		hidden:true,
	  		handler:function(me){
	  			var me = this;
	  			function req(param){
	  				Ext.Ajax.request({
					url:'getFtuPrintTemplate.do',
					params:{key:param},
					success:function(response, opts){//3联的
						Ext.create('DJ.order.Deliver.FtuTemplateEdit',{
							editstate:'add',
							html:response.responseText
						}).show();
					},
					failure: function(response, opts) {
				        Ext.Msg.alert('提示','请联系管理员！')
				    }
					})
	  			}
  				Ext.Ajax.request({
				url:'gainCfgByFkey.do',
				params:{'key':'thePrintTP'},
				success:function(response){
					var obj = Ext.decode(response.responseText);
					if(obj.success==true){
						if(obj.data[0].fvalue=='2'){//2等分
							req(2);
						}else if(obj.data[0].fvalue=='3'){//3等分
							req(3);
						}else{
							me.up('grid').down('button[text=打印模板修改][hidden=true]').handler(me.up('grid').down('button[text=打印模板修改][hidden=true]'),'',function(){
								me.handler();
							});
						}
					}
				}
  				})
	  			
	  			
	  		
	  		}
	  	},{
	  		text:'新编辑',
	  		hidden:true,
	  		handler:function(me){
	  			var me = this;
	  			var records = me.up('grid').getSelectionModel().getSelection();
	  			if(records.length==0){
	  				Ext.Msg.alert('提示','请选择一条数据!');
	  				return;
	  			}
	  			var fid = records[0].data.fid;
	  			function req(param){
	  				Ext.Ajax.request({
	  				url:'getFtusaledeliver.do',
	  				params:{fid:fid},
	  				success:function(response){
	  					var obj = Ext.decode(response.responseText);
	  					if(obj.success==true){
	  						Ext.Ajax.request({
								url:'getFtuPrintTemplate.do',
								params:{key:param},
								success:function(response, opts){//3联的
									Ext.create('DJ.order.Deliver.FtuTemplateEdit',{
										data:obj.data,
										editstate:'edit',
										html:response.responseText
									}).show();
								},
								failure: function(response, opts) {
							        Ext.Msg.alert('提示','请联系管理员！')
							    }
							})
	  					}
	  				}
	  			})
	  			}
	  			
	  			Ext.Ajax.request({
				url:'gainCfgByFkey.do',
				params:{'key':'thePrintTP'},
				success:function(response){
					var obj = Ext.decode(response.responseText);
					if(obj.success==true){
						if(obj.data[0].fvalue=='2'){//2等分
							req(2);
						}else if(obj.data[0].fvalue=='3'){//3等分
							req(3);
						}else{
							me.up('grid').down('button[text=打印模板修改][hidden=true]').handler(me.up('grid').down('button[text=打印模板修改][hidden=true]'),'',function(){
								me.handler();
							});
						}
					}
				}
  				})
	  			
	  		}
	  	},{
	  		text:'新查看',
	  		hidden:true,
			handler:function(me){
				var me = this;
	  			var records = me.up('grid').getSelectionModel().getSelection();
	  			if(records.length==0){
	  				Ext.Msg.alert('提示','请选择一条数据!');
	  				return;
	  			}
	  			var fid = records[0].data.fid;
	  			function req(param){
	  				Ext.Ajax.request({
	  				url:'getFtusaledelivers.do',
	  				params:{fid:fid},
	  				success:function(response){
	  					var obj = Ext.decode(response.responseText);
	  					if(obj.success==true){
	  						Ext.Ajax.request({
								url:'getFtuPrintTemplate.do',
								params:{key:param},
								success:function(response, opts){//3联的
									Ext.create('DJ.order.Deliver.FtuTemplateEdit',{
										data:obj.data,
										editstate:'view',
										html:response.responseText
									}).show();
								},
								failure: function(response, opts) {
							        Ext.Msg.alert('提示','请联系管理员！')
							    }
							})
	  					}
	  				}
	  			})
	  			}
  				Ext.Ajax.request({
				url:'gainCfgByFkey.do',
				params:{'key':'thePrintTP'},
				success:function(response){
					var obj = Ext.decode(response.responseText);
					if(obj.success==true){
						if(obj.data[0].fvalue=='2'){//2等分
							req(2);
						}else if(obj.data[0].fvalue=='3'){//3等分
							req(3);
						}else{
							me.up('grid').down('button[text=打印模板修改][hidden=true]').handler(me.up('grid').down('button[text=打印模板修改][hidden=true]'),'',function(){
								me.handler();
							});
						}
					}
				}
  				})
	  			
	  			
			}
	  	},{
	  		text:'新打印',
	  		hidden:true,
	  		handler:function(me,e,fid){
	  			var fids =[];
	  			var m = this;
	  			var records = this.up('grid').getSelectionModel().getSelection();
	  			if(Ext.isEmpty(fid)){
		  			if(records.length==0){
		  				Ext.Msg.alert('提示','请选择数据！');
		  				return;
		  			}
	  			}
  				for(var i = 0;i<records.length;i++){
	  				if(fids.indexOf(records[i].get('fid'))<0){
	  					fids.push(records[i].get('fid'));
	  				}
	  			}
	  			fids = Ext.isEmpty(fid)?fids.join(","):fid;
	  			function req(param){
		  			Ext.Ajax.request({
		  				url:'getFtuPrintTemplate.do',
		  				params:{key:param},
		  				success:function(response){
		  						var win = Ext.create('Ext.Window',{
		  							html:response.responseText
		  						}).show().hide();
		  						Ext.Ajax.request({
									url:'getPrintFTUList.do',
									params:{fids:fids,size:win.body.dom.querySelector('table table').rows.length-2},//-2为去掉列标题和去掉尾部
									success:function(responses, opts){//3联的
										win.close();
										var obj = Ext.decode(responses.responseText);
										try{
											Ext.each(obj.data,function(d){
												Ext.each(d.product,function(product){
													product.famount = eval(product.famount)==0?'':eval(product.famount);
													product.fprice = eval(product.fprice)==0?'':eval(product.fprice);
													product.fdanjia = eval(product.fdanjia)==0?'':eval(product.fdanjia);
												})
												d.sum = eval(d.sum)==0?'':eval(d.sum);
//												d.fsumprices = eval(d.fsumprices)==0?'':eval(d.fsumprices);
											})
										}catch(ee){
											
										}
										
										if(obj.success==true){
											var wins = Ext.create('DJ.order.Deliver.FtuTemplateEdit',{
												data:obj.data,
												onload:function(){
													var me = this;
													var data = this.data;
										  			var dom;
										  			var printTemplate = me.body.dom.querySelector('#printTemplate');
													for(i =0;i<data.length;i++){
														if(i==0){
															continue;
														}
														dom = Ext.clone(printTemplate);
														dom.setAttribute('id',dom.getAttribute('id')+i);
	//													insertAfter(dom,printTemplate);
														printTemplate.parentNode.appendChild(dom)
													}
													for(i =0;i<data.length;i++){
														var div = me.body.dom.querySelector('#printTemplate'.concat(i==0?'':i));
														div.querySelector('div').textContent = div.querySelector('div').textContent.replace('{制造商名称}',data[i].fsuppliername);
														Ext.each(div.querySelector('table table').rows,function(row,index){//表格内容
															if(index>0){
																Ext.each(row.cells,function(cell,indexs){
																	if(data[i].product[index-1]){
																		if(!cell.querySelector('table')){
																			cell.textContent = data[i].product[index-1][div.querySelector('table table').rows[0].cells[indexs].getAttribute('name')]
																		}
																	}
																	if(cell.querySelector('table')){
																		Ext.each(cell.querySelector('table').rows,function(row){
																			Ext.each(row.cells,function(cell){
																				if(cell.getAttribute('name')){
																					cell.textContent = cell.textContent.replace(cell.textContent,cell.textContent+data[i][cell.getAttribute('name')])
																				}
																			})
																		})
																	}
																})
															}
														})
														Ext.each(div.querySelector('table').rows,function(row,index){//表格标题
															Ext.each(row.cells,function(cell,indexs){
																if(!cell.querySelector('table')){
																	if(cell.getAttribute('name')){
																		cell.textContent = Ext.isEmpty(me.data[i][cell.getAttribute('name')])?'':me.data[i][cell.getAttribute('name')];
																	}
																}
															})
														})
	//													me.close();
													}
												},
												editstate:'print',
												html:response.responseText
											}).show().hide();
											wins.down('button[text=打印预览]').handler();
										}
									},
									failure: function(response, opts) {
								        Ext.Msg.alert('提示','请联系管理员！')
								    }
								})
		  				}
		  			})
	  			}
	  			Ext.Ajax.request({
				url:'gainCfgByFkey.do',
				params:{'key':'thePrintTP'},
				success:function(response){
					var obj = Ext.decode(response.responseText);
					if(obj.success==true){
						if(obj.data[0].fvalue=='2'){//2等分
							req(2);
						}else if(obj.data[0].fvalue=='3'){//3等分
							req(3);
						}else{
//							Ext.Msg.alert('提示','请先去设置模板');
							m.up('grid').down('button[text=打印模板修改][hidden=true]').handler(m.up('grid').down('button[text=打印模板修改][hidden=true]'),'',function(){
								m.handler();
							});
						}
					}
				}
  				})
	  		}
	  	},{
	  		text:'切换版本(旧)',
	  		handler:function(){
	  			var me = this;
	  			Ext.Ajax.request({
	  				url:'saveUIEditionChange.do',
	  				params:{FoldUI:'DJ.order.Deliver.FTUsaledeliverList',FnewUI:'DJ.order.Deliver.newFTUsaledeliverList'},
	  				success:function(response){
	  					var obj = Ext.decode(response.responseText);
	  					if(obj.success==true){
							var treepanel = me.up('grid').up('tabpanel').previousSibling();
							Ext.each(treepanel.query('treepanel'),function(tree){
								Ext.each(tree.items.items[0].store.data.items,function(item,index){
									if(item.data.furl=='DJ.order.Deliver.newFTUsaledeliverList'){
										me.up('grid').destroy();
										item.data.furl = 'DJ.order.Deliver.FTUsaledeliverList';
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
		'header' : '发货单位',
		dataIndex : 'fsuppliername',
		width : 90,
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