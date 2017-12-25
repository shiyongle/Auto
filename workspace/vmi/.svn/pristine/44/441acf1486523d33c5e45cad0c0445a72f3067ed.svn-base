var ltc = false;
window.onmouseup=function(){
	ltc = false;
	window.onmousemove = function(){};
}
//MouseDownToResize =function (obj,e){//允许拖动就不能设置列宽度
//	obj.mouseDownX= event.clientX;
//	ltc = true;
//	obj.pareneTdW=obj.parentElement.offsetWidth;
//	var table = obj.parentNode.parentNode.parentNode.parentNode;//表格
//	var i;
//	 Ext.each(table.rows,function(row){
//		 	Ext.each(row.cells,function(cell,index){
//		 		if(obj.parentElement==cell){
//		 			i = index;
//		 		}
//		 	})
//		 })
//	 window.onmousemove = function(){
//		 var newWidth=obj.pareneTdW*1+event.clientX*1-obj.mouseDownX;
//		 Ext.each(table.rows,function(row){
//		 	if(!Ext.isEmpty(i)){
//		 		if(!row.querySelector('table'))
//	 			row.cells[i].setAttribute('width',newWidth+'px')
//	 		}
//		 })
//	 }
//}
//
//MouseDownToLeftResize =function (obj,e){//允许拖动就不能设置列宽度
//	obj.mouseDownX= event.clientX;
//	ltc = true;
//	obj.pareneTdW=obj.parentElement.previousSibling.offsetWidth;
//	var table = obj.parentNode.parentNode.parentNode.parentNode;//表格
//	var i;
//	 Ext.each(table.rows,function(row){
//		 	Ext.each(row.cells,function(cell,index){
//		 		if(obj.parentElement==cell){
//		 			i = index;
//		 		}
//		 	})
//		 })
//	 window.onmousemove = function(){
//		 var newWidth=obj.pareneTdW*1+event.clientX*1-obj.mouseDownX;
//		 Ext.each(table.rows,function(row){
//		 	if(!Ext.isEmpty(i)){
//		 		if(!row.querySelector('table'))
//	 			row.cells[i-1].setAttribute('width',newWidth+'px')
//	 		}
//		 })
//	 }
//}
var newCellname = ['string1','string2','string3','string4','string5','string6','string7','string8','string9','string10']
Ext.define('DJ.order.Deliver.FtuPrintTemplate',{
	id:'DJ.order.Deliver.FtuPrintTemplate',
	extend:'Ext.Window',
	resizable:false,
	title:'打印模板设置',
	modal:true,
	bodyStyle : 'background:white',//white',
	dragging:function(){
		Ext.each(this.body.dom.querySelectorAll('span.resizeRightDivClass,span.resizeLeftDivClass'),function(r){//列移动改变宽度
			r.onmousedown = function(e){
				ltc = true;
				var pareneTdW=e.target.parentElement.offsetWidth;
				if(r.className=="resizeLeftDivClass"){
					pareneTdW = e.target.parentElement.previousSibling.offsetWidth;
				}
				var table = e.target.parentNode.parentNode.parentNode.parentNode;//表格
				var i;
				 Ext.each(table.rows,function(row){
					 	Ext.each(row.cells,function(cell,index){
					 		if(e.target.parentElement==cell){
					 			i = index;
					 		}
					 	})
					 })
				 window.onmousemove = function(event){
					 var newWidth=pareneTdW*1+event.clientX*1-e.clientX;
					 Ext.each(table.rows,function(row){
					 	if(!Ext.isEmpty(i)){
					 		if(!row.querySelector('table'))
					 		if(r.className=="resizeLeftDivClass"){
					 			row.cells[i-1].setAttribute('width',newWidth+'px')
					 		}else{
					 			row.cells[i].setAttribute('width',newWidth+'px')
					 		}
				 		}
					 })
				 }
	
			}
		})
	},
	listeners:{
		afterrender:function(me){
			Ext.Ajax.request({
				url:'getFtuConcat.do',
				success:function(response){
					var obj = Ext.decode(response.responseText);
					if(obj.success==true){
						var html = document.getElementById('printTemplate');
						html.querySelector('div').textContent = obj.data[0].fsuppliername;
					
						html.querySelector('td:nth-child(2)').textContent = html.querySelector('td:nth-child(2)').textContent.replace('{电话}',obj.data[0].fphone);
						html.querySelector('td:nth-child(4)').textContent = html.querySelector('td:nth-child(4)').textContent .replace('{传真}',obj.data[0].ffax);
						html.querySelector('td:nth-child(5)').textContent = html.querySelector('td:nth-child(5)').textContent .replace('{编号}','No.'+obj.data[0].fnumber);
						
						html.querySelector('table').rows[html.querySelector('table').rows.length-1].querySelector('td:nth-child(4)').textContent = Ext.Date.format(new Date(),'Y-m-d')
					}else{
						Ext.Msg.alert('提示',obj.msg);
					}
				}
			})
			this.dragging();
//			Ext.util.CSS.createStyleSheet(".contenteditableClass{" + "white-space:nowrap;overflow:hidden;background: #1DD453;border: 1px solid;" + "}");
//			Ext.each(me.body.dom.querySelectorAll('table td[contenteditable=true],div[contenteditable=true]'),function(td,index){
//				td.setAttribute('class','contenteditableClass');
//			})
		},
		show:function(me){
			var table = me.body.dom.querySelector('table table');
			table.oncontextmenu = function(e){//表格右击事件
					var item = [];
					if(e.target.parentNode.parentNode.parentNode==table.querySelector('table')){
						if(e.target.parentNode.parentNode.parentNode.querySelectorAll('tr').length == 2){
							if(e.target.parentNode == e.target.parentNode.parentNode.parentNode.querySelectorAll('tr')[1]){
//								item.push({
//									text:'删除行',
//									hidden:true,
//									handler:function(){
//										var i;
//										Ext.each(table.querySelector('table').rows,function(row,index){
//											if(row==e.target.parentNode){
//												i = index;
//											}
//											Ext.each(row.cells,function(cell){
//												cell.style.borderBottom = "";
//											})
//										})
//										table.querySelector('table').deleteRow(i);
//									}
//								})
//								var rightClick = new Ext.menu.Menu ({
//									items : item
//								}); 
//								rightClick.showAt(e.clientX,e.clientY); 
								return false;
							}else{
								return false;
							}
						}else{
							return false;
						}
						
					}
					if(Ext.isEmpty(e.target.parentNode.previousSibling)){//空就是列标题右击事情，否则就是表格行事件
						item.push({
							text : ' 新增列',
							handler:function(){
								Ext.create('Ext.Window',{
									modal:true,
									items:Ext.create('DJ.order.Deliver.FtuTemplateParameterEdit',{
										 id:'DJ.order.Deliver.AddFtuTemplateParameterEdit',
										 buttons : [{
											 text : '保存',
											 handler : function() {
											 	var columns = [];
												Ext.each(table.rows[0].cells,function(cell,index){
													Ext.each(newCellname,function(name){
														if(cell.getAttribute('name')==name){
															columns.push(cell.getAttribute('name'));
														}
													})
												})
												var newArray = Ext.Array.difference(newCellname,columns);
												if(Ext.isEmpty(newArray)){
													Ext.Msg.alert('提示','只能新增10列！');
													return false;
												}
											 	window.onkeydown = function(e){
													if(e.keyCode==8){
														return true;
													}
												}
											 	 var i,form = this.up('form');
												 var value =form.down('textfield[fieldLabel=名称]').getValue();
												 form.down('textfield[name=fname]').setValue(value);
												 var formVlaue = form.getValues();
												 formVlaue.fsaledeliverentry = newArray[0];
												 if(!form.isValid()){
												 	return false;
												 }
											 	Ext.Ajax.request({
											 		url:'saveOrupdateFtuParameters.do',
											 		params:{FtuParameter:Ext.encode(formVlaue)},
											 		success:function(response){
											 			var obj = Ext.decode(response.responseText);
											 			if(obj.success==true){
															Ext.each(table.rows,function(row,indexs){
																Ext.each(row.cells,function(cell,index){
																	if(cell==e.target){
																		i = index;
																	}
//																	if(!Ext.isEmpty(i)){
//																		cell.setAttribute('width',eval(cell.getAttribute('width'))-eval(50/table.rows[0].cells.length));
//																	}
																})
																
																if(!Ext.isEmpty(row.querySelector('table'))){
																	row.cells[0].setAttribute('colspan',eval(row.cells[0].getAttribute('colspan'))+1)
																}else{
//																	if(form.getValues().inserttype==1)
																	var c = row.insertCell(i+eval(form.getValues().inserttype));
																	if(indexs==0){//第一行
																		
																		c.setAttribute('name',newArray[0])
																	}
																	if(Ext.isEmpty(form.getValues().fisprint)){
																			c.setAttribute('noprint',true);
																	}else{
																		c.setAttribute('noprint','');
																	}
																	c.setAttribute('height','24');
																	c.setAttribute('width','50');
																	
																	if(table.rows[0].cells.length>0){
																		if(indexs==0){
																			c.innerHTML=value+'<span class="resizeLeftDivClass">&nbsp;</span><span class="resizeRightDivClass">&nbsp;</span>';
																		}else{
																			c.innerHTML='<span class="resizeLeftDivClass">&nbsp;</span><span class="resizeRightDivClass">&nbsp;</span>';
																		}
																	}
																}
															})
															me.dragging();
															form.up('window').close();
											 			}else{
											 				Ext.Msg.alert('提示',obj.msg);
											 			}
											 		}
											 	})
												
											 }
											 }]
								})
								}).show();
							}
						},{
							text : ' 修改列',
							handler:function(){
								Ext.create('Ext.Window',{
									modal:true,
									items:Ext.create('DJ.order.Deliver.FtuTemplateParameterEdit',{
									id:'DJ.order.Deliver.FtuTemplateParameterEdit',
									height:165,
									listeners:{
										afterrender:function(me){
											var filter = [];
											var name = Ext.String.trim(e.target.innerText||e.target.textContent);
											filter.push({
												myfilterfield:'falias',
												CompareType:'=',
												type:'string',
												value:name
											})
											Ext.Ajax.request({
												url:'getFtuParameter.do',
												params:{Defaultfilter:Ext.encode(filter),Defaultmaskstring:'#0'},
												success:function(response){
													var obj = Ext.decode(response.responseText);
													if(obj.success==true){
														if(obj.data){
															me.down('numberfield').setValue(obj.data[0].fdecimals);
															me.down('radiofield[inputValue='+obj.data[0].fieldtype+']').setValue(true);
															me.down('textfield[name=fcomputationalformula]').setValue(obj.data[0].fcomputationalformula);
															me.down('checkboxfield[name=fisprint]').setValue(obj.data[0].fisprint);
															if("fcusproductname,funit,famount,fdanjia,fprice".indexOf(e.target.getAttribute('name'))>-1){
																Ext.each(me.query('radiofield'),function(ra){
																	ra.setReadOnly(true);
																})
															}
														}
													}
												}
											})
											this.down('textfield[name=falias]').setValue(name);
											this.down('textfield[name=fname]').setValue(name);
											Ext.each(me.query("checkboxfield[boxLabel*=插入列]"),function(c){
												c.hide();
											})
										}
									},
									buttons : [{
											 text : '保存',
											 handler : function() {
											 	var form = this.up('form');
											 	var i;
												 var value =this.up('form').down('textfield[fieldLabel=名称]').getValue();
												 if(form.isValid()){
													 Ext.Ajax.request({
												 		url:'saveOrupdateFtuParameters.do',
												 		params:{FtuParameter:Ext.encode(form.getValues())},
												 		success:function(response){
												 			var obj = Ext.decode(response.responseText);
												 			if(obj.success==true){
																Ext.each(table.rows,function(row,indexs){
																	Ext.each(row.cells,function(cell,index){
																		if(cell==e.target){
																			i = index;
																			cell.innerHTML = value+'<span class="resizeLeftDivClass">&nbsp;</span><span class="resizeRightDivClass">&nbsp;</span>';
																		}
																		if(Ext.isEmpty(form.getValues().fisprint)){
																			if(index==i&&indexs!=table.rows.length-1){
																					cell.setAttribute('noprint',true);
																			}
																		}else{
																				cell.setAttribute('noprint','');
																			}
																	})
																	
																})
																me.dragging();
																form.up('window').close();
												 			}
												 		}
													 })
												 }
												
											 }
											 }]
								})
								}).show();
							}
						}, (function(){
							var names = ['fcusproductname','famount','fdanjia','fprice'];
							if(names.indexOf(e.target.getAttribute('name'))>-1){
								return {
									hidden:true
								}
							}else{
								return {
									text : ' 删除列',
									handler : function(){
										var i;
										Ext.Ajax.request({
											url:'delFtuParameter.do',
											params:{name:Ext.String.trim(e.target.innerText)},
											success:function(response){
												var obj = Ext.decode(response.responseText);
												if(obj.success==true){
													Ext.each(table.rows,function(row){
														Ext.each(row.cells,function(cell,index){
															if(cell==e.target){
																i = index;
															}
//															if(!Ext.isEmpty(i)){
//																	cell.setAttribute('width',eval(cell.getAttribute('width'))+eval(50/table.rows[0].cells.length));
//															}
														})
														if(Ext.isEmpty(row.querySelector('table'))){
															row.deleteCell(i);
														}else{
															row.cells[0].setAttribute('colspan',eval(row.cells[0].getAttribute('colspan'))-1)
														}
													})
												}else{
													Ext.Msg.alert('提示',obj.msg);
												}
											}
										})
										
									}
								}
							}
						})())
					}else {
						item.push({
							text : ' 新增行',
							hidden:true,
							handler : function(){
								var length= table.rows.length;
							   var tr=document.createElement("tr");
							   tr.setAttribute('height',table.rows[0].cells[0].height);
							   tr.setAttribute('align','center');
							   tr.id=length+1;
							   
							   for(var i=0;i<table.rows[0].cells.length;i++){
							   		var td = document.createElement("td");
									tr.appendChild(td);
							   }
							   function insertAfter(newEl, targetEl)
								{
								      var parentEl = targetEl.parentNode;
								
								      if(parentEl.lastChild == targetEl)
								      {
								           parentEl.appendChild(newEl);
								      }else
								      {
								           parentEl.insertBefore(newEl,targetEl.nextSibling);
								      }            
								}
							   insertAfter(tr,e.target.parentNode);
							}
						},{
							text : ' 删除行',
							hidden:true,
							handler : function(){
								var i;
								Ext.each(table.rows,function(row,index){
									if(row==e.target.parentNode){
										i = index;
									}
								})
								table.deleteRow(i);
							}
						})
					}
					try{
							if(Ext.isEmpty(e.target.parentNode.nextSibling.nextSibling)&&Ext.isEmpty(e.target.parentNode.previousSibling)){
							item.push({
								text : ' 新增行',
								hidden:true,
								handler : function(){
									var length= table.rows.length;
								   var tr=document.createElement("tr");
								   tr.setAttribute('height',table.rows[0].cells[0].height);
								   tr.setAttribute('align','center');
								   tr.id=length+1;
								   
								   for(var i=0;i<table.rows[0].cells.length;i++){
								   		var td = document.createElement("td");
										tr.appendChild(td);
								   }
								   function insertAfter(newEl, targetEl)
									{
									      var parentEl = targetEl.parentNode;
									
									      if(parentEl.lastChild == targetEl)
									      {
									           parentEl.appendChild(newEl);
									      }else
									      {
									           parentEl.insertBefore(newEl,targetEl.nextSibling);
									      }            
									}
								   insertAfter(tr,e.target.parentNode);
								}
							})
						}
					}catch(ee){
					}
					item.push({
						text:'行距',
						hidden:true,
						handler:function(){
							var win = Ext.Msg.prompt('提示', '请输入行的高度', function(btn, text){
							    if (btn == 'ok'){
							        Ext.each(table.rows,function(row,index){
//										if(row==e.target.parentNode){
							        		if(index!=0&&index!=table.rows.length-1){
									        	row.setAttribute('height',text);
							        			Ext.each(row.cells,function(cell,indexs){
													cell.setAttribute('height',text)
							        			})
							        		}
//										}
									})
							    }
							});
							win.down('textfield').setValue(e.target.getAttribute('height')||e.target.parentNode.getAttribute('height'));
							win.down('textfield').selectText();
						}
					})
					var n = 0;
					Ext.each(item,function(i){
						if(i.hidden){
							n++;
						}
					})
					var rightClick = new Ext.menu.Menu ({
						items : item
					}); 
					if(n!=item.length){
						rightClick.showAt(e.clientX,e.clientY); 
					}
				return false;//取消默认右击菜单 e.x,e.y
			}
		}
	},
	dockedItems: [{
	    xtype: 'toolbar',
	    dock: 'top',
//	    style : {
//				background : 'white'
//		},
	    items: [{
			text:'保存',
			height:30,
			handler:function(){
				var me = this;
				var html = this.up('window').body.dom.querySelector('#printTemplate').parentNode.innerHTML.replace(/\%/g,'ギ');
				Ext.Ajax.request({
					url:'savePrintTemplate.do',
					params:{html:html},
					success:function(response){
						var obj = Ext.decode(response.responseText);
						if(obj.success==true){
							djsuccessmsg(obj.msg);
							var grid;
							if(Ext.getCmp('DJ.order.Deliver.newFTUsaledeliverList')){
								grid = Ext.getCmp('DJ.order.Deliver.newFTUsaledeliverList');
							}else{
								grid = Ext.getCmp('DJ.System.SyscfgList');
							}
							grid.store.load();
							me.up('window').close();
						}else{
							Ext.Msg.alert('提示',obj.msg);
						}
					}
				})
			}
		},{
			text:'关闭',
			height:30,
			handler:function(){
				this.up('window').close();
			}
		},{
			text:'还原模板',
			height:30,
			handler:function(){
				var me = this;
				Ext.Msg.show({
					title:'提示', 
					msg:'还原模板后，将恢复成平台默认模板，已修改的模板无法使用，是否继续操作！',
					buttons: Ext.Msg.YESNO,
					fn:function(btn){
					    if (btn == 'yes'){
							Ext.Ajax.request({
								url:'resetPrintTemplate.do',
								success:function(response){
									var obj = Ext.decode(response.responseText);
									if(obj.success==true){
										djsuccessmsg(obj.msg);
										me.up('window').close();
										var grid;
										if(Ext.getCmp('DJ.order.Deliver.newFTUsaledeliverList')){
											grid = Ext.getCmp('DJ.order.Deliver.newFTUsaledeliverList');
										}else{
											grid = Ext.getCmp('DJ.System.SyscfgList');
										}
										grid.store.load();
										grid.down('button[text=打印模板修改]').handler();
										var p = [];
										p.push({'fname':'产品名称','fieldtype':0,'fsaledeliverentry':'fcusproductname'},{'fname':'规格','fieldtype':0,'fsaledeliverentry':'fspec'},{'fname':'单位','fieldtype':0,'fsaledeliverentry':'funit'},{'fname':'数量','fieldtype':1,'fdecimals':3,'fsaledeliverentry':'famount'},{'fname':'单价','fieldtype':1,'fdecimals':3,'fsaledeliverentry':'fdanjia'},{'fname':'金额','fieldtype':1,'fdecimals':2,'fcomputationalformula':'数量*单价','fsaledeliverentry':'fprice'},{'fname':'备注','fieldtype':0,'fsaledeliverentry':'fdescription'})
										Ext.Ajax.request({
											url:"saveAllFtuParams.do",
											params:{FtuParameter:Ext.encode(p)},
											success:function(response){
												var obj = Ext.decode(response.responseText);
												if(obj.success==true){
													
												}
											}
										})
									}else{
										Ext.Msg.alert('提示',obj.msg);
									}
								}
							})
					    }
				}
				});
			}
		},{
			text:'打印预览',
			height:30,
			handler:function(){
				var html = document.getElementById('printTemplate').parentNode;
				 Ext.each(html.querySelectorAll('.resizeLeftDivClass,.resizeRightDivClass,td[noprint=true]'),function(c){
				 	c.style.display='none';
				 })
				 var i = 0;
				 Ext.each(html.querySelector('table table').rows,function(row){
				 	Ext.each(row.cells,function(cell){
				 		if(cell.style.display == 'none'){
				 			i++
				 		}
				 	})
				 	if(row.querySelector('table')){//打印时隐藏不需要打印的列，里面table要改变colspan
				 		if(i>=html.querySelector('table table').rows.length-1){
				 			row.querySelector('td').setAttribute('colspan',row.querySelector('td').getAttribute('colspan')-eval((i/(html.querySelector('table table').rows.length-1))));
				 		}
				 	}
				 })
//				Ext.each(html.querySelector('table').querySelectorAll('td.printTd'),function(td){
//					td.setAttribute('width','15%');
//				})
				Ext.DomHelper.overwrite(Ext.get('panelPrint').dom.contentWindow.document.body,"<style>@media print {@page {margin: 2mm 10mm 0mm 10mm;}} table{table-layout:fixed;}</style>" + html.innerHTML);//this.up('window').body.dom.innerHTML);
				Ext.get('panelPrint').dom.contentWindow.focus();
				Ext.get('panelPrint').dom.contentWindow.print();
				Ext.each(html.querySelectorAll('.resizeLeftDivClass,.resizeRightDivClass,td[noprint=true]'),function(c){
				 	c.style.display='';
				 })
				 Ext.each(html.querySelector('table table').rows,function(row){
				 	Ext.each(row.cells,function(cell){
				 		if(cell.style.display == 'none'){
				 			i++
				 		}
				 	})
				 	if(row.querySelector('table')){//还原回来
				 		if(i>=html.querySelector('table table').rows.length-1){
				 			row.querySelector('td').setAttribute('colspan',eval(row.querySelector('td').getAttribute('colspan'))+eval((i/(html.querySelector('table table').rows.length-1))));
				 		}
				 	}
				 })
			}
		},{
			xtype:'tbfill'
		},{
			xtype:'button',
			text:'操作文档说明',
			height:30,
			handler:function(){
				window.open('downWordByFTU.do','操作文档说明');
//				Ext.Ajax.request({
//					url:'downWordByFTU.do',
//					success:function(response){
//						var obj = Ext.decode(response.responseText);
//						if(obj.success==true){
//							
//						}else{
//							Ext.Msg.alert('提示','请联系管理员！');
//						}
//					}
//				})
			}
		}]
	}]
})