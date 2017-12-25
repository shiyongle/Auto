Ext.define('DJ.order.Deliver.FTUsaledeliverEdit',{
	extend : 'Ext.form.Panel',
	id : 'DJ.order.Deliver.FTUsaledeliverEdit',
//	title : '送货凭证编辑界面',
	closable : false,
//	minHeight: 600,
	autoScroll:true,
	height:550,
	border: false,
	frame:true,
	listeners:{
		render:function(){
					 var me = this;
					 var cTable = me.down('cTable');
					 for(var i = 0 ;i<10;i++){
					 	cTable.getStore().add(i);
					 }
					cTable.down('toolbar').hide();//隐藏cTable的toolbar
					
					
					var combobox  =  me.query('combobox');
//						combobox.forEach(function(com,index){//为每个下拉框增加个'focus'事件,获取焦点时展开
//								document.getElementById(com.id).addEventListener('click',function(){
//									com.getStore().load();
//									com.setValue(com.getValue());
//									com.expand();
//								})
//								
//						})
					Ext.each(combobox,function(com,index){
						if(Ext.isIE){
							document.getElementById(com.id).attachEvent('onclick',function(){
									com.getStore().load();
									com.setValue(com.getValue());
									com.expand();
								})
						}else{
							document.getElementById(com.id).addEventListener('click',function(){
									com.getStore().load();
									com.setValue(com.getValue());
									com.expand();
								})
						}
					})
					if(me.editstate=='add'){
						Ext.Ajax.request({
							url:'getFtuConcat.do',
							success:function(response, opts){
								var obj = Ext.decode(response.responseText);
								if(obj.success==true){
									var  fcustnameArray = Ext.toArray(obj.data[0].fcustname+"(代合同)");
									
									Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit.fnumber').setText('No.'+obj.data[0].fnumber);
									Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit.fcustname').setText(fcustnameArray.join(' '));
									me.down('textfield[name=ftel]').setValue(obj.data[0].fphone);
									me.down('textfield[name=ffax]').setValue(obj.data[0].ffax);
									me.down('textfield[name=fsupplierid]').setValue(obj.data[0].fsupplierid);
									me.down('textfield[name=fsuppliername]').setValue(obj.data[0].fsuppliername);
									me.down('textfield[name=fnumber]').setValue(obj.data[0].fnumber);
									me.userid = obj.data[0].fuserid;
								}else{
									Ext.Msg.alert('提示',obj.msg);
									me.up().close();
								}
							}
						})
					
					}
//					Ext.DomHelper.overwrite(//为ifram添加Ext样式
//							Ext.get('panelPrint').dom.contentWindow.document.head,Ext.String.format('<link rel="stylesheet" type="text/css" href="{0}extlib/resources/css/ext-all.css">',location.pathname.substring(0,location.pathname.lastIndexOf('/')+1))
//					);
		},
		afterrender:function(me){
			me.verifyParameter();
		}
	},
	tbar: [{
			xtype: 'button', 
			text: '保存',
//			hidden:true,
			width:40,
			height:30,
			handler:function(btn,e,callback){
					btn.setDisabled(true);
					var me = this.up().up();
					var form = this.up().up().getForm().getValues();
					var itmes = this.up().up().down('cTable').store.data.items;
					var custproduct = [];
					
					if(Ext.isEmpty(form.ftel)&&Ext.isEmpty(form.ffax)){
						Ext.Msg.alert('提示','电话、传真请维护基础资料',function(){
							var userWin = Ext.create('DJ.System.UserPrintEdit');
							userWin.loadfields(me.userid);
							userWin.seteditstate("edit");
							userWin.url='SaveCustomerUser.do';
							var pass= userWin.down('[name=fpassword]');
							var pass1= pass.ownerCt.getComponent('password1');
							userWin.down('[name=fname]').setReadOnly(true);
							pass1.setDisabled(true);
							var checkbox = userWin.query('checkboxfield');
//							checkbox.forEach(function(box){
//								box.hide();
//							})
							Ext.each(checkbox,function(box){
								box.hide();
							})
							var combobox = userWin.query('combobox');
//							combobox.forEach(function(com){
//								com.hide();
//							})
							Ext.each(combobox,function(com){
								com.hide();
							})
							userWin.show();
							userWin.on('close',function(m){
								btn.setDisabled(false);
								me.down('textfield[name=ftel]').setValue(m.down('textfield[name=fphone]').getValue());
								me.down('textfield[name=ffax]').setValue(m.down('textfield[name=ffax]').getValue());
							},this,'single: true')
						})
						return;
					}
					
//					itmes.forEach(function(ele,index){
//						if(ele.get('fname')||ele.get('fspec')){
//							if(ele.get('famount')==null){
//								ele.set('famount','');
//							}
//							custproduct.push(ele.data);
//						}
//					})
					Ext.each(itmes,function(ele,index){
						if(ele.get('fname')||ele.get('fspec')){
							if(ele.get('famount')==null){
								ele.set('famount','');
							}
							custproduct.push(ele.data);
						}
					})
						Ext.Ajax.request({
							url:'saveFtuSaleDeliver.do',
							params:{
								'Custproduct':Ext.encode(custproduct),
//								'FTUSaledeliverEntry':Ext.encode(product),
								'fphone':form.fphone,
								'ffax':form.ffax,
								'fnumber':form.fnumber,
								'fcustomer':form.fcustomer,
								'ftel':form.ftel,
								'fclerk':form.fclerk,
								'fdriver':form.fdriver,
								'fsupplierid':form.fsupplierid,
								'fsuppliername':form.fsuppliername,
								'fcustAddress':form.fcustAddress,
								'busNumber':form.busNumber,
								'fid':form.fid
							},
							success:function(response, opts){
								var obj = Ext.decode(response.responseText);
								if(obj.success==true){
									btn.setDisabled(false);
									djsuccessmsg("保存成功！");
									Ext.getCmp('DJ.order.Deliver.FTUsaledeliverList').getStore().load();
									
									if(Ext.isFunction(callback)){
										callback(obj.msg);
									}
									me.up().close();
								}else{
								//
									btn.setDisabled(false);
									djsuccessmsg(obj.msg);
								}
							}
						})
						
	  		}
	  	},{
			xtype: 'button', 
			text: '关闭',
			width:40,
			height:30,
			handler:function(){
//				this.up().up().getForm().reset()
				this.up('window').close();
			}
	  	},{
	  		text:'保存并打印',
			height:30,
	  		handler:function(){
	  			var mebtn = this;
	  			this.previousSibling('[text=保存]').handler(this,"",
	  				function(fid) {
	  				
	  					Ext.getCmp(mebtn.up('panel').parent).down('button[text=打印]').handler("","","",fid);
	  					
	  				}
	  			
	  			);
	  			
	  		}
	  	}
	],
	verifyParameter:function(){
		var me = this;
		Ext.Ajax.request({
			url:'getFtuParameter.do',
			success:function(response){
				var obj = Ext.decode(response.responseText);
				var type;
				if(obj.success==true){
					Ext.each(obj.data,function(d){
						Ext.each(me.down('cTable').columns,function(column){
							if(column.text==d.fname){//getEditor()
									var a = Ext.isEmpty(column.editor)?column.editor:column.editor;
									if(a.xtype=="combobox"){//下拉框的不做特殊处理
										column.disabled = d.fedit==1?true:false;
										column.setText(d.falias);
										column.Edisabled = d.fedit==1?true:false;
										a.disabled= d.fedit==1?false:true;
										
									}else{
										type = d.fieldtype==1?'numbercolumn':'gridcolumn';
										column.xtype = type;
										column.format = Ext.Number.toFixed(0,d.fdecimals);
										column.fcomputationalformula = d.fcomputationalformula;
										column.decimalPrecision = d.fdecimals;
										column.Edisabled = d.fedit==1?true:false;
										column.editor = {
											xtype:d.fieldtype==1?'numberfield':'textfield',
											decimalPrecision:d.fdecimals,
											disabled:d.fedit==1?false:true,
											allowDecimals:d.fdecimals==0?false:true
										}
									}
//								}
							}
						})
					})
				}else{
					
				}
			}
		})
	},
	initComponent : function() {
			var me = this;
			Ext.applyIf(me, {
//				defaultType:'container',
				defaults: {
						border:false
				},
				items : [{
					layout:'absolute',
					defaultType: 'textfield',
					baseCls : "x-plain",
					items:[{
						x: '40%',
				        y: 10,
				        id:'DJ.order.Deliver.FTUsaledeliverEdit.fcustname',
				        xtype:'label',
				        text: '_____(代合同)'
					},{
						hidden:true,
						name:'fsupplierid',
						fieldLabel: '制造商ID',
						labelWidth:55
					},{
						hidden:true,
						name:'fsuppliername',
						fieldLabel: '制造商名称',
						labelWidth:55
					},{
					    x: '45%',
				        y: 30,
				        xtype:'label',
				        text: '送 货 凭 证'
					},{
						x: 10,
						y: 50,
						xtype:'displayfield',
						id:'DJ.order.Deliver.FTUsaledeliverEdit.ftel',
						labelWidth:55,
						fieldLabel:'接单电话',
						value:''
						
					},{
						xtype:'displayfield',
						x: '35%',
						y: 50,
						id:'DJ.order.Deliver.FTUsaledeliverEdit.ffax',
						labelWidth:55,
						fieldLabel:'接单传真',
						value:''
					},{
						x: 10,
						y: 50,
						hidden:true,
						name:'ftel',
						fieldLabel: '订单电话',
						labelWidth:55,
						listeners:{
							change:function(me, newValue, oldValue, eOpts){
								me.previousSibling('displayfield[fieldLabel=接单电话]').setValue(newValue);
							}
						}
					},{
						x: 10,
						y: 50,
						hidden:true,
						name:'ffax',
						fieldLabel: '传真',
						labelWidth:55,
						listeners:{
							change:function(me, newValue, oldValue, eOpts){
								me.previousSibling('displayfield[fieldLabel=接单传真]').setValue(newValue);
							}
						}
					},{
						x: 0,
						y: 0,
						hidden:true,
						name:'fnumber',
						fieldLabel: '编码',
						labelWidth:55
					},{
						x: 0,
						y: 0,
						hidden:true,
						name:'fid',
						labelWidth:55
					},{
						x: '85%',
						y: 50,
						xtype:'label',
						id:'DJ.order.Deliver.FTUsaledeliverEdit.fnumber',
						text:'No.CPS150430-001'
					},
						Ext.create('Ext.form.ComboBox', {
								y: 80,
//								hidden:true,
							    fieldLabel: '客户名称',
							    id:'DJ.order.Deliver.FTUsaledeliverEdit.fcustomer',
							    name:'fcustomer',
							    hideTrigger:true,
							    labelWidth:55,
							    queryParam:'fname',
								queryDelay:300,
								minChars:'2',
							    store: Ext.create('Ext.data.Store', {
									    fields: ['fid', 'fname','ftel'],
									    autoLoad:true,
									   	proxy:{
											type:'ajax',
											url: 'selectFtuCustomer.do',
									         reader: {
									             type: 'json',
									             root: 'data'
									         }
										}
								}),
							    displayField: 'fname',
							    valueField: 'fid',
							    listeners:{
							    	render:function(combo){
//							    		combo.store.load({callback:function(records, operation, success){
//							    			if(records.length==1){
//							    				combo.setValue(records[0].get('fid'));
//							    				combo.nextSibling().setValue(records[0].data.ftel);
//							    				combo.nextSibling('combobox').store.load({callback:function( store, records, successful, eOpts){
//													if(store.length==1){
//														Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit.fcustAddress').setValue(store[0].get('fid'));
//													}else if(store.length==0){
//														Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit.fcustAddress').setValue('');
//													}
//									    			}
//												});
//							    			}
//							    		}})
							    	},
							    	select:function(combo, records, eOpts){
							    		combo.nextSibling().setValue(records[0].data.ftel);
							    		combo.nextSibling('combobox').store.load({callback:function( store, records, successful, eOpts){
							    			Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit.fcustAddress').setValue('');
											if(store.length==1){
												Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit.fcustAddress').setValue(store[0].get('fid'));
											}/*else if(store.length==0){
												Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit.fcustAddress').setValue('');
											}*/
							    			}
										});
//							    		combo.nextSibling().nextSibling().setValue(records[0].data.fid);
							    	},
							    	change:function(combo,newValue,oldValue){//当输入下拉框没有的客户名称时，删除上一次存的Value
							    		if(newValue==''){
							    			combo.store.load();
							    		}
							    		Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit.fcustAddress').setValue('');
							    		combo.setValue(newValue);
							    	}
							    },
							    margin : '0 10'
						})			
					,{
						x: '35%',
						y: 80,
//						hidden:true,
						fieldLabel: '联系电话',
						name:'fphone',
						labelWidth:55
					},{
						x: '40%',
						y: 80,
						hidden:true,
						fieldLabel: '客户ID',
						name:'fcustomerid'
					},Ext.create('Ext.form.ComboBox', {
						x: '65%',
						y:80,
						labelWidth:55, 
						width:245,
						fieldLabel: '送货地址',
						id:"DJ.order.Deliver.FTUsaledeliverEdit.fcustAddress",
						name:'fcustAddress',
						hideTrigger:true,
						queryParam:'fnames',
						queryDelay:300,
						enforceMaxLength:true,
						maxLength:15,
						minChars:'99',
						forceSelection:false,
						store: Ext.create('Ext.data.Store', {
								fields: ['fid', 'fname'],
//								autoLoad:true,
								proxy:{
									type:'ajax',
									url: 'getCustAddress.do',
									reader: {
									       type: 'json',
									       root: 'data'
									       }
										},
								listeners:{
									beforeload:function(store, operation, eOpts){
										var fcustomer = Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit.fcustomer').getValue();
										if (Ext.isEmpty(fcustomer)) {// 没有选择客户时，清除所有数据
												store.removeAll();
												return false;
											} else {
												store.getProxy().setExtraParam(
														'fcustomerid', fcustomer);
											}
									}
							}
						}),
						displayField: 'fname',
						valueField: 'fid',
						listeners:{
							change:function(combo,newValue,oldValue){//当输入下拉框没有的客户名称时，删除上一次存的Value
							    		if(newValue==''){
							    			combo.store.load();
							    		}
							    		var value = combo.rawValue.replace(/<br\/>/g,'\r\n');
							    		combo.rawValue = value;
							    		combo.setValue(value);
							    	}
						}
					})]},{
						xtype:'cTable',
						margin:'10 5 0 0',
						name:'product',
						pageSize:100,
						height:250,
						url:'',
						border: 5,
						columnLines :true,
						bodyStyle: {
						   'border-width': '2px 0 0 0'
						},
						onload:function(me){
							this.on('afterrender',function(me){
								Ext.each(me.columns,function(column,index){
									if(!column.hidden){
										document.getElementById(column.id).oncontextmenu  = function(e){
//											var c = Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit').down('cTable');
											e.preventDefault();
											var rightClick = new Ext.menu.Menu ({
												items : [{ 
													text : ' 删除列',
													handler : function(){
//														if()
														column.hide();
													}
												}, { 
													handler : function(){} , 
													text : ' 新增列' 
												}] 
											}); 
											rightClick.showAt(e.x,e.y); 
										}
									}
								})
							})
						},
						plugins:[ Ext.create('Ext.grid.plugin.CellEditing', {
							  showEditor: function(ed, context, value) {
							        var me = this,
							            record = context.record,
							            columnHeader = context.column,
							            sm = me.grid.getSelectionModel(),
							            selection = sm.getCurrentPosition(),
							            otherView = selection && selection.view;
							
							        // Selection is for another view.
							        // This can happen in a lockable grid where there are two grids, each with a separate Editing plugin
							        if (otherView && otherView !== me.view) {
							            return me.lockingPartner.showEditor(ed, me.lockingPartner.getEditingContext(selection.record, selection.columnHeader), value);
							        }
							
							        me.setEditingContext(context);
							        me.setActiveEditor(ed);
							        me.setActiveRecord(record);
							        me.setActiveColumn(columnHeader);
							
							        // Select cell on edit only if it's not the currently selected cell
							        if (sm.selectByPosition && (!selection || selection.column !== context.colIdx || selection.row !== context.rowIdx)) {
							            sm.selectByPosition({
							                row: context.rowIdx,
							                column: context.colIdx,
							                view: me.view
							            });
							        }
							
							        ed.startEdit(me.getCell(record, columnHeader), value, context);
							        //开始编辑时 选中文本内容
							        var input = Ext.DomQuery.selectNode('input[name='+context.field+']');
							        if(input){
							        	input.select();
							        }
							        me.editing = true;
							        me.scroll = me.view.el.getScroll();
							    },
							onSpecialKey : function(ed, field, e) {
								var sm;
		
								if (/*e.getKey() === e.TAB || */e.getKey() === e.ENTER) {//回车键下一行 Tab键有点问题
									e.stopEvent();
		
									if (ed) {
										ed.onEditorTab(e);
									}
									sm = ed.up('tablepanel').getSelectionModel();
									if (sm.onEditorTab) {
										return sm.onEditorTab(ed.editingPlugin, e);
									}
								}
							},
				            clicksToEdit: 1,
				            listeners:{
				            	beforeedit:function(edit,e){
				            		var count = e.grid.store.data.items.length-1;
				            		if(e.rowIdx==count){
				            			e.grid.store.add(count+1);//点击最后一行时，自动新增一行
				            		}
				            	},
				            	edit:function(edit,e){
				            		var me = e.grid;
			            			var fcount = e.record.get('famount'),fprice = e.record.get('fprice');
			            			var upperSum = Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit.upperSum');
			            			var RMB = Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit.RMB');
			            			var SUM = Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit.SUM');
			            			var fprices = 0,famount=0;
				            		if(e.field=='famount'||e.field=='fprice'){
				            			if(fcount&&fprice){
				            				e.record.set('fprices',Ext.util.Format.round(Math.round((fcount*fprice) * 1000) / 1000,eval(e.grid.columns[5].getEditor().decimalPrecision)));
//				            				e.grid.store.data.items.forEach(function(store,index){
//				            					if(store.get('fprices')){
//				            						fprices = eval(fprices)+eval(store.get('fprices'));
//				            					}
//				            				})
				            				Ext.each(e.grid.store.data.items,function(store,index){
				            					if(store.get('fprices')){
				            						fprices = eval(fprices)+eval(store.get('fprices'));
				            					}
				            				})
				            				prices = Math.round(fprices * 1000) / 1000;
				            				RMB.setValue(prices);
				            				upperSum.generateItems(prices);
				            			}else{
				            				e.record.set('fprices','');
//				            				e.grid.store.data.items.forEach(function(store,index){
//				            					if(store.get('fprices')){
//				            						fprices = eval(fprices)+eval(store.get('fprices'));
//				            					}
//				            				})
				            				Ext.each(e.grid.store.data.items,function(store,index){
				            					if(store.get('fprices')){
				            						fprices = eval(fprices)+eval(store.get('fprices'));
				            					}
				            				})
				            				prices = Math.round(fprices * 1000) / 1000;
				            				RMB.setValue(prices);
				            				upperSum.generateItems(prices);
				            			}
//				            			e.grid.store.data.items.forEach(function(store,index){
//				            					if(store.get('famount')){
//				            						famount += 0+eval(store.get('famount'));
//				            					}
//				            				})
				            			Ext.each(e.grid.store.data.items,function(store,index){
				            				if(store.get('famount')){
				            						famount += 0+eval(store.get('famount'));
				            				}
				            			})
				            			SUM.setValue(famount);
				            		}
				            		if(e.field=='fprices'&&e.record.data.fprices!=null){
				            			try{
				            				var a = 0;
					            			Ext.each(e.grid.store.data.items,function(store,index){
					            					if(store.get('fprices')){
					            						fprices = eval(a)+eval(store.get('fprices'));
					            					}
					            				})
				            				prices = Math.round(fprices * 1000) / 1000;
				            				RMB.setValue(prices);
				            				upperSum.generateItems(prices);
				            			}catch(ee){
				            				return true;
				            			}
				            			
				            		}
				            		if(e.field=='fname'){
				            			if(me.record){
				            				var records = me.record[0];
				            				e.record.set('fname',records.data.fname);
//				            				console.log(e.record.get('fspec'));
//				            				if(Ext.isEmpty(e.record.get('fspec'))){
					            				if(records.data.fspec!=undefined){//解决火狐赋空值时，会出现代码。
					            					e.record.set('fspec',records.data.fspec);
					            				}else{
					            					e.record.set('fspec','');
					            				}
//				            				}
//				            				if(Ext.isEmpty(e.record.get('fprice'))){
					            				e.record.set('fprice',records.data.fprice);
//				            				}
//				            				if(Ext.isEmpty(e.record.get('funit'))){
					            				e.record.set('funit',records.data.funit);
//				            				}
//				            				if(Ext.isEmpty(e.record.get('famount'))){
					            				e.record.set('famount',records.data.famount);
//				            				}
//				            				if(Ext.isEmpty(e.record.get('fprices'))){
					            				e.record.set('fprices',records.data.fprices);
//				            				}
				            				e.record.set('fid',records.data.fid);
				            				e.record.set('fproductid',records.data.fproductid);
			            					e.record.set('fcreatetime',records.data.fcreatetime);
				            				me.record = '';
				            			}
				            		}
				            		e.record.commit();
				            	}
				            }
				        })],
						fields:[{
							name:'fid'
						},{
							name:'fname'
						},{
							name:'fspec'
						},{
							name:'fprice'
						},{
							name:'funit'
						},{
							name:'fdescription'
						},{
							name:'fcustomerid'
						},'fftuproductid','famount','fprices','fproductid','fcreatetime'],
						columns:[{
							dataIndex:'fname',
							text:'产品名称',
							width:180,
							sortable:false,
							menuDisabled:true,
							listeners:{
								headerclick:function( ct, column, e, t, eOpts ){
									Ext.Msg.prompt('请输入列名称','',function(btn, text){
									    if (btn == 'ok'){
									        // process text value and close...
									    	column.setText(text);
									    }
									});
								}
							},
							editor:{
								xtype:'combobox',
								displayField:'fviewname',
								valueField:'fid',
								queryParam:'fname',
								queryDelay:300,
								minChars:'2',
								forceSelection:false,
								doRawQuery : function() {
									if(Ext.isIE){
										this.doQuery(this.getRawValue(), false, true);	
									}else{
										this.doQuery(this.getRawValue().replace(/'/g,'').trim(), false, true);	
									}
									
								},
//								listConfig:{
//									emptyText:'未找到匹配产品'
//								},
								store:Ext.create('Ext.data.Store', {
								    fields:[{
										name:'fid'
									},{
										name : 'fname',
										myfilterfield : 'fname',
										myfiltername : '名称',
										myfilterable : true
									},{
										name:'fspec',
										myfilterfield : 'fspec',
										myfiltername : '规格',
										myfilterable : true
									},{
										name:'funit'
									},{
										name:'fprice'
									},{
										name:'famount'
									},{
										name:'fprices'
									},'fdescription','fviewname','fproductid','fcreatetime'],
								    proxy:{
											type:'ajax',
											url: 'selectProductsByCustomer.do',
									         reader: {
									             type: 'json',
									             root: 'data'
									         }
										}
										,
										listeners:{
											beforeload:function(store, operation, eOpts){
												var fcustomerid = Ext.getCmp('DJ.order.Deliver.FTUsaledeliverEdit.fcustomer').getValue();
												if(Ext.isEmpty(fcustomerid)){//没有选择客户时，清除所有数据
													store.removeAll();
													return false;
												}else{
													store.getProxy().setExtraParam('fcustomerid',fcustomerid);
												}
											}
										}
								}),    
								onExpand: function() {
										this.getStore().loadPage(1);
									        var me = this,
									            keyNav = me.listKeyNav,
									            selectOnTab = me.selectOnTab,
									            picker = me.getPicker();
									
									        if (keyNav) {
									            keyNav.enable();
									        } else {
									            keyNav = me.listKeyNav = new Ext.view.BoundListKeyNav(this.inputEl, {
									                boundList: picker,
									                forceKeyDown: true,
									                tab: function(e) {
									                    if (selectOnTab) {
									                        this.selectHighlighted(e);
									                        me.triggerBlur();
									                    }
									                    return true;
									                },
									                enter: function(e){
									                    var selModel = picker.getSelectionModel(),
									                        count = selModel.getCount();
									                        
									                    this.selectHighlighted(e);
									                    
									                    if (!me.multiSelect && count === selModel.getCount()) {
									                        me.collapse();
									                    }
									                }
									            });
									        }
									
									        if (selectOnTab) {
									            me.ignoreMonitorTab = true;
									        }
									
									        Ext.defer(keyNav.enable, 1, keyNav); //wait a bit so it doesn't react to the down arrow opening the picker
									        me.inputEl.focus();
									    },
									listeners:{
									select:function(combo, records, eOpts){
										var panel = combo.up('panel');
										panel.record = records;
									},
									change:function( me, newValue, oldValue, eOpts){
										var panel = me.up('panel');
										me.setValue(newValue);//没有时，第一次匹配时会清空数据
									}
								}
								}
							
						},{
							dataIndex:'fspec',
							text:'规格',
							sortable:false,
							menuDisabled:true,
							width:140,
							editor:{
								
							}
						},{
							dataIndex:'funit',
							text:'单位',
							sortable:false,
							menuDisabled:true,
//							flex:0.5,
							width:60,
							renderer:function(val,me){
								if(Ext.isEmpty(val)){
									me.record.set('funit','只');
									me.record.commit();
									return '只';
								}else{
									return val;
								}
							},
							editor:{
								xtype:'combobox',
								displayField:'name',
								valueField:'value',
//								editable:false,
								store:Ext.create('Ext.data.Store', {
								    fields:[{
										name : 'name'
									},{
										name:'value'
									}],
									data:[{
										name:'只',value:'只'
									},{
										name:'套',value:'套'
									},{
										name:'片',value:'片'
									},{
										name:'平方米',value:'平方米'
									},{
										name:'平方厘米',value:'平方厘米'
									}]
								})
								}
						},{
							dataIndex:'famount',
							text:'数量',
							sortable:false,
							menuDisabled:true,
							width:80,
							xtype:'numbercolumn',
							format:'0',
							editor:{
								xtype:'numberfield',
								allowDecimals:false	,
								decimalPrecision:0,
								minValue:0,
								value:0
							}
						},{
							dataIndex:'fprice',
							text:'单价',
							sortable:false,
							menuDisabled:true,
							xtype:'numbercolumn',
							format:'0.000',
							width:80,
//							renderer:function(val){
//								return val.substr(0,val.indexOf('.')+3);
//							},
							editor:{
								xtype:'numberfield',
								decimalPrecision:3,
								minValue:0
							}
						},{
							dataIndex:'fprices',
							width:80,
							sortable:false,
							menuDisabled:true,
//							xtype:'numbercolumn',
//							format:'0.000',
							text:'金额',
							editor : {
								disabled:true,
								decimalPrecision:3
							}
						},{
							dataIndex:'fdescription',
							text:'备注',
							sortable:false,
							menuDisabled:true,
							width:130,
							editor:{
								
							}
						},{
							dataIndex:'fid',
							text:'客户产品ID',
							hidden:true//,
//							hideable:false
						},{
							dataIndex:'fproductid',
							text:'产品ID',
							hidden:true//,
//							hideable:false
						},{
							dataIndex:'fcreatetime',
							text:'创建时间',
							hidden:true//,
//							hideable:false
						}]
					},{
						xtype:'button',
						text:'+新增行',
						width:60,
						margin : '2 5 0 5',
						handler:function(){
							var ctable = this.prev('cTable');
							ctable.store.add(ctable.store.getCount());
						}
					},{
						layout:'hbox',
						baseCls : "x-plain",
						bodyPadding : '0 5',
						items:[{
							xtype:'fieldcontainer',
							fieldLabel:'<b>合计金额(大写)</b>',
							id:'DJ.order.Deliver.FTUsaledeliverEdit.upperSum',
							generateItems: function(price){
								var me = this,
									chinaNum = '零壹贰叁肆伍陆柒捌玖'.split(''),
									arr = ['分整','角','元','拾','佰','仟','万'],
									subArr = ['','拾','佰','仟'],
									texts = [],
									wTexts = [],
									i = 7,
									text = '',
									num;
								price *= 100;
								while(price && --i){
									num = price % 10;
									texts.push(chinaNum[num]);
									price = Math.floor(price/10);
								}
								if(price){
									if(price<=0){
										return;
									}
									if(price>10000){
										Ext.Msg.alert('提示','金额过大，不允许操作！');
										return;
									}
									while(price){
										num = price % 10;
										wTexts.push(chinaNum[num]);
										price = Math.floor(price/10);
									}
									Ext.each(wTexts,function(item,index){
//									wTexts.forEach(function(item,index){
										text = item + subArr[index] + text;
									});
									texts[texts.length] = text;
								}
								me.removeAll();
								var array = [];
									Ext.each(texts,function(item,index){
//							        texts.forEach(function(item,index){
							         array.unshift({
							          text: arr[index],
							          style: {
							           fontWeight: 'bold',
							           fontSize: '14px'
							          }
							         });
							         array.unshift({
							          text: item
							         });
							         /*me.add(0,{
							          text: arr[index],
							          style: {
							           fontWeight: 'bold',
							           fontSize: '14px'
							          }
							         });
							         me.add(0,{
							          text: item
							         });*/
							        });
							        me.add(array);
								return me.getEl().dom.innerHTML;
							},
							width:'60%',
							defaultType: 'label',
							defaults: {
								margin: '0 5 0 0'
							},
							items: [{
								text: '万',
								style: {
									fontWeight: 'bold'
								}
							},{
								text: '仟',
								style: {
									fontWeight: 'bold'
								}
							},{
								text: '佰',
								style: {
									fontWeight: 'bold'
								}
							},{
								text: '拾',
								style: {
									fontWeight: 'bold'
								}
							},{
								text: '元',
								style: {
									fontWeight: 'bold'
								}
							},{
								text: '角',
								style: {
									fontWeight: 'bold'
								}
							},{
								text: '分 整',
								style: {
									fontWeight: 'bold'
								}
							}]
						},{
							xtype:'displayfield',
							id:'DJ.order.Deliver.FTUsaledeliverEdit.SUM',
							width:'20%',
							labelWidth:50,
							fieldLabel:'<b>总数量</b>',
							value:''
						},{
							xtype:'displayfield',
							width:'20%',
							id:'DJ.order.Deliver.FTUsaledeliverEdit.RMB',
							labelWidth:20,
							fieldLabel:'<b>￥</b>',
							value:''
						}]
					},{
						layout:'hbox',
						defaults: {
							border:1
						},
						baseCls : "x-plain",
						bodyPadding : '10 10',
						items:[{
							xtype:'textarea',
							labelWidth:10,
							width:'60%',
							height:100,
							readOnly:true,
							value:'注:1.产品质量,数量以需方经办人在供方"送货凭证"上验收为准。' +
//									'2.合同履行地为供方所在地，双方一致同意“送货凭证”代替书面合同、如有争议，双方友好协商，协商不成，则由供方所在地法院处理。' +
									'2.需方应在收到货后在双方达成付款期限内付清贷款。' +
//									'4.没有我公司合同授权，需方单位不得以任何形式支付贷款给我司业务员，否则，本公司概不承担任何责任。' +
									'3.本送货凭证由需方在客户签字栏签字确认。' 
						},{
							xtype:'textarea',
							width:'39%',
							style: {
					            marginBottom: '10px'
					        },
							height:100,
							readOnly:true,
							value:'客户签字:'
						}]
					},{
						layout:'hbox',
						defaults: {
							border:1
						},
						bodyPadding : '0 10',
						baseCls : "x-plain",
						items:[
							Ext.create('Ext.form.ComboBox', {
							    fieldLabel: '开单员',
							    hideTrigger:true,
							    labelWidth:40,
							    name:'fclerk',
							    store: Ext.create('Ext.data.Store', {
									    fields: ['fid', 'fname'],
//									    autoLoad:true,
									   	proxy:{
											type:'ajax',
											url: 'selectFtuClerk.do',
									         reader: {
									             type: 'json',
									             root: 'data'
									         }
										}
								}),
							    displayField: 'fname',
							    valueField: 'fname',
							    listeners:{
							    	render:function(combo){
							    		combo.store.load({callback:function(records, operation, success){
							    			if(records.length==1){
							    				combo.setValue(records[0].get('fname'));
							    			}
							    		}})
							    	}
							    }
							}),
								Ext.create('Ext.form.ComboBox', {
							    fieldLabel: '司机',
							    hideTrigger:true,
							    labelWidth:30,
							    name:'fdriver',
							    store: Ext.create('Ext.data.Store', {
									    fields: ['fid', 'fname'],
//									    autoLoad:true,
									   	proxy:{
											type:'ajax',
											url: 'selectFtuDriver.do',
									         reader: {
									             type: 'json',
									             root: 'data'
									         }
										}
								}),
							    displayField: 'fname',
							    valueField: 'fname',
							    margin : '0 75',
							    listeners:{
									render:function( combo, eOpts ){
										combo.store.load({callback:function(records, operation, success){
							    			if(records.length==1){
							    				combo.setValue(records[0].get('fname'));
							    			}
							    		}})
									}
								}
							}),Ext.create('Ext.form.ComboBox', {
							    fieldLabel: '车牌号码',
							    hideTrigger:true,
							    labelWidth:55,
							    name:'busNumber',
							    store: Ext.create('Ext.data.Store', {
									    fields: ['fid', 'fname'],
//									    autoLoad:true,
									   	proxy:{
											type:'ajax',
											url: 'selectFtuBusNumber.do',
									         reader: {
									             type: 'json',
									             root: 'data'
									         }
										}
								}),
							    displayField: 'fname',
							    valueField: 'fname',
//							    margin : '0 75',
							    listeners:{
									render:function( combo, eOpts ){
										combo.store.load({callback:function(records, operation, success){
							    			if(records.length==1){
							    				combo.setValue(records[0].get('fname'));
							    			}
							    		}})
									}
								}
							})
						]
					}
				]
			});
			me.callParent(arguments);
	}
})