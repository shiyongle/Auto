Ext.define('DJ.order.Deliver.FtuTemplateEdit',{
	id:'DJ.order.Deliver.FtuTemplateEdit',
	extend:'Ext.Window',
	resizable:false,
	title:'送货凭证编辑界面',
	modal:true,
//	bodyStyle : 'background:white',
	autoScroll:true,
	onload:function(){
	},
	createCombo: function(config,parentDom){
		var url = config.url||'';
		Ext.widget('combobox',Ext.apply({
			store: Ext.create('Ext.data.Store', {
			    fields: ['fid', 'fname','ftel'],
			    proxy:{
			         type: 'ajax',
			         url: url,
			         reader: {
			             type: 'json',
			             root: 'data'
			         }
			    },
			    autoLoad: true
			}),
			width: parentDom.clientWidth,
		    displayField: 'fname',
		    valueField: 'fname',
		    renderTo: parentDom,
		    queryDelay:300,
			minChars:'1',
		    queryMode:'remote',
		    queryParam:'fname'
		},config));
	},
	toRMB : function(Num) 
	{  Num = Num + "";
	      Num.replace(/[,\s]/g,'');
	      Num = Num.replace("￥","")//替换掉可能出现的￥字符 
	        if(isNaN(Num))    
	        { 
	     //验证输入的字符是否为数字 
	     alert("请检查小写金额是否正确"); 
	     return; 
	        } 
	        //---字符处理完毕，开始转换，转换采用前后两部分分别转换---// 
	        part = String(Num).split("."); 
	        newchar = "";    
	        //小数点前进行转化 
	        for(i=part[0].length-1;i>=0;i--) 
	        { 
	         if(part[0].length > 10){ alert("位数过大，无法计算");return "";}//若数量超过拾亿单位，提示 
	     tmpnewchar = "" 
	     perchar = part[0].charAt(i); 
	     switch(perchar){ 
	     case "0": tmpnewchar="零" + tmpnewchar ;break; 
	     case "1": tmpnewchar="壹" + tmpnewchar ;break; 
	     case "2": tmpnewchar="贰" + tmpnewchar ;break; 
	     case "3": tmpnewchar="叁" + tmpnewchar ;break; 
	     case "4": tmpnewchar="肆" + tmpnewchar ;break; 
	     case "5": tmpnewchar="伍" + tmpnewchar ;break; 
	     case "6": tmpnewchar="陆" + tmpnewchar ;break; 
	     case "7": tmpnewchar="柒" + tmpnewchar ;break; 
	     case "8": tmpnewchar="捌" + tmpnewchar ;break; 
	     case "9": tmpnewchar="玖" + tmpnewchar ;break; 
	         } 
	         switch(part[0].length-i-1) 
	    { 
	     case 0: tmpnewchar = tmpnewchar +"元" ;break; 
	     case 1: if(perchar!=0)tmpnewchar= tmpnewchar +"拾" ;break; 
	     case 2: if(perchar!=0)tmpnewchar= tmpnewchar +"佰" ;break; 
	     case 3: if(perchar!=0)tmpnewchar= tmpnewchar +"仟" ;break;    
	     case 4: tmpnewchar= tmpnewchar +"万" ;break; 
	     case 5: if(perchar!=0)tmpnewchar= tmpnewchar +"拾" ;break; 
	     case 6: if(perchar!=0)tmpnewchar= tmpnewchar +"佰" ;break; 
	     case 7: if(perchar!=0)tmpnewchar= tmpnewchar +"仟" ;break; 
	     case 8: tmpnewchar= tmpnewchar +"亿" ;break; 
	     case 9: tmpnewchar= tmpnewchar +"拾" ;break; 
	         } 
	         newchar = tmpnewchar + newchar; 
	        } 
	        //小数点之后进行转化 
	        if(Num.indexOf(".")!=-1) 
	        { 
	         if(part[1].length > 2) 
	         { 
	        alert("小数点之后只能保留两位,系统将自动截段"); 
	        part[1] = part[1].substr(0,2) 
	     } 
	         for(i=0;i<part[1].length;i++) 
	         { 
	        tmpnewchar = "" 
	        perchar = part[1].charAt(i) 
	        switch(perchar){ 
	        case "0": tmpnewchar="零" + tmpnewchar ;break; 
	        case "1": tmpnewchar="壹" + tmpnewchar ;break; 
	        case "2": tmpnewchar="贰" + tmpnewchar ;break; 
	        case "3": tmpnewchar="叁" + tmpnewchar ;break; 
	        case "4": tmpnewchar="肆" + tmpnewchar ;break; 
	        case "5": tmpnewchar="伍" + tmpnewchar ;break; 
	        case "6": tmpnewchar="陆" + tmpnewchar ;break; 
	        case "7": tmpnewchar="柒" + tmpnewchar ;break; 
	        case "8": tmpnewchar="捌" + tmpnewchar ;break; 
	        case "9": tmpnewchar="玖" + tmpnewchar ;break; 
	     } 
	     if(i==0)tmpnewchar =tmpnewchar + "角"; 
	     if(i==1)tmpnewchar = tmpnewchar + "分"; 
	     newchar = newchar + tmpnewchar; 
	         } 
	        } 
	        //替换所有无用汉字 
	        while(newchar.search("零零") != -1) 
	            newchar = newchar.replace("零零", "零"); 
	        newchar = newchar.replace("零亿", "亿"); 
	        newchar = newchar.replace("亿万", "亿"); 
	        newchar = newchar.replace("零万", "万");    
	        newchar = newchar.replace("零元", "元"); 
	        newchar = newchar.replace("零角", ""); 
	        newchar = newchar.replace("零分", ""); 
	    
	        if (newchar.charAt(newchar.length-1) == "元" || newchar.charAt(newchar.length-1) == "角") 
	         newchar = newchar+"整" 
	        return newchar; 
	},
	listeners:{
		show:function(me){
			if(me.editstate=='edit'||me.editstate=='add'){
				var tables = me.body.dom.querySelector('table');
				var table = me.body.dom.querySelector('table table[contenteditable=false]');
				me.createCombo({
					url: 'selectFtuCustomer.do',
					id:'printTemplate.customer',
				 	displayField: 'fname',
		    		valueField: 'fid',
					listeners: {
				    	focus:function(){
				    		this.expand();
					    },
						select: function(combo,records){
							if(!Ext.isEmpty(tables.rows[1].cells[2].textContent)){
								tables.rows[1].cells[3].textContent = records[0].get('ftel');
								Ext.getCmp('printTemplate.faddress').store.load();
								Ext.getCmp('printTemplate.faddress').store.on('load',function(mm, records, successful, eOpts ){
									if(records.length==1){
										Ext.getCmp('printTemplate.faddress').setValue(records[0])
									}
								},this,{single: true})
							}
						}
				}
				},tables.rows[1].querySelector('td:nth-child(2)'));
				if(!Ext.isEmpty(tables.rows[1].querySelector('td:nth-child(6)').previousSibling.textContent)){
					me.createCombo({
						id: 'printTemplate.faddress',
						store: Ext.create('Ext.data.Store', {
						    fields: ['fid', 'fname'],
						    proxy:{
						         type: 'ajax',
						         url: 'getFTUCustAddress.do',
						         reader: {
						             type: 'json',
						             root: 'data'
						         }
						    },
						    listeners:{
						    	beforeload:function(store, operation, eOpts){
									var fcustomer = Ext.getCmp('printTemplate.customer').getValue();
									if(Ext.isEmpty(fcustomer)){//没有选择客户时，清除所有数据
										store.removeAll();
										return false;
									}else{
										store.getProxy().setExtraParam('fcustomer',fcustomer);
									}
								}
						    }
						}),
						listeners:{
							focus:function(){
					    		this.expand();
						    },
							expand:function(m){
								var v = m.getValue();
								m.store.load();
								m.setValue(v);
							}
//							,
//							render:function(m){
//								this.store.on('load',function(mm, records, successful, eOpts ){
//									if(records.length==1){
//										m.setValue(records[0])
//									}
//								})
//							}
						}
					},tables.rows[1].querySelector('td:nth-child(6)'));
				}
				if(!Ext.isEmpty(tables.rows[tables.rows.length-1].querySelector('td:nth-child(2)').previousSibling.textContent)){
					me.createCombo({
						url: 'selectFtuClerk.do',
						hideTrigger :true,
						listeners:{
							focus:function(){
					    		this.expand();
						    },
							render:function(m){
								this.store.on('load',function(mm, records, successful, eOpts ){
									if(records.length==1){
										m.setValue(records[0])
									}
								},this,{single: true})
							}
						}
					},tables.rows[tables.rows.length-1].querySelector('td:nth-child(2)'));
				}
				if(!Ext.isEmpty(tables.rows[tables.rows.length-1].querySelector('td:nth-child(6)').previousSibling.textContent)){
					me.createCombo({
						url: 'selectFtuDriver.do',
						hideTrigger :true,
						listeners:{
							focus:function(){
					    		this.expand();
						    },
							render:function(m){
								this.store.on('load',function(mm, records, successful, eOpts ){
									if(records.length==1){
										m.setValue(records[0])
									}
								},this,{single: true})
							}
						}
					},tables.rows[tables.rows.length-1].querySelector('td:nth-child(6)'));
				}
				var cusindex,unitindex;
				function addtableCom(){//表格内部批量添加下拉框
					Ext.each(table.rows,function(row,indexs){
						Ext.each(row.cells,function(cell,index){
							if(indexs==0){
								if(cell.getAttribute('name')=='fcusproductname'){
									cusindex = index;
								}
								if(cell.getAttribute('name')=='funit'){
									unitindex = index
								}
							}
							if(indexs>0&&!cell.querySelector('table')){
								if(index==cusindex){
									me.createCombo({
										url: 'selectProductsByCustomers.do',
										displayField: 'fviewname',
		   			 					valueField: 'fname',
										store: Ext.create('Ext.data.Store', {
										    fields: ['fid', 'fname','ftel','funit',{
										    			name:'fdanjia',
										    			convert : function(v, record) {
																	return eval(v);
																}
													},'fspec','fviewname'],
										    proxy:{
										         type: 'ajax',
										         url: 'selectProductsByCustomers.do',
										         reader: {
										             type: 'json',
										             root: 'data'
										         }
										    },
//										    autoLoad: true,
										    listeners:{
										    	beforeload:function(store, operation, eOpts){
													var fcustomer = Ext.getCmp('printTemplate.customer').getValue();
													if(Ext.isEmpty(fcustomer)){//没有选择客户时，清除所有数据
														store.removeAll();
														return false;
													}else{
														store.getProxy().setExtraParam('fcustomer',fcustomer);
													}
												}
										    }
										}),
										listeners:{
											select:function(combo,records){
												combo.viewname = records[0].get('fname');
												var m,com = document.getElementById(combo.id);
												while(com.parentNode){
													m = com.parentNode;
													if(m.getAttribute('contenteditable')=="true"){
														while(m.nextSibling){
															m = m.nextSibling;
															var tr = m.parentNode;
															Ext.each(tr.cells,function(cell,index){
																if(cell==m){
																	if(m.querySelector('input[type=text]')){
																		Ext.getCmp(cell.querySelector('input[type=text]').id.replace('-inputEl','')).setValue(records[0].get(table.rows[0].cells[index].getAttribute('name')))
																	}else{
																		m.textContent = records[0].get(table.rows[0].cells[index].getAttribute('name'))
																	}
																}
															})
														}
														break;
													}
												}
											},
											focus:function(){
									    		this.expand();
										    },
											expand:function(m){
												var v = m.getValue();
												m.store.load();
												m.setRawValue(v);
											},
											blur:function(m){
												if(!Ext.isEmpty(m.viewname)){
													m.setRawValue(m.viewname);
												}
											},
											change:function(combo, newValue, oldValue, eOpts){
												if(newValue!=oldValue){
													combo.viewname = "";
												}
											}
										}
									},cell);
								}
								if(index==unitindex){
									me.createCombo({
										url: 'getFTUunit.do',
										listeners: {
									    	focus:function(){
									    		this.expand();
										    }
										}
									},cell);
								}
							}
						})
					})
				}
				addtableCom();
				function contextmenu(){
					Ext.each(tables.rows,function(row,index){
						Ext.each(row.cells,function(cell,indexs){
							if(cell.querySelector('table[contenteditable=false]')){
								return true;
							}
							cell.onclick = function(e){//点击的时候重新获取焦点，针对焦点不能正确获取的情况
								e.target.focus();
							}
							if(cell.getAttribute('contenteditable')){
								cell.onkeydown = function(e){//回车换行
								var keycode = e.which||e.keyCode;
								if(keycode==13){//回车键
									if(e.target.tagName=="INPUT"){
										var m = e.target;
										while(m.parentNode){
											m = m.parentNode;
											if(m.getAttribute('contenteditable')=="true"){
												var nextnode = m.nextSibling;
												if(nextnode==null){
														var t = m.parentNode.nextSibling.querySelector('table');
														if(t){
															if(t.rows[1].cells[0].querySelector('input[type=text]')){
																t.rows[1].cells[0].querySelector('input[type=text]').focus();
															}else{
																t.rows[1].cells[0].focus();
															}
															break;
														}
													}
												while(nextnode){
													if(nextnode.getAttribute('contenteditable')=="true"){
														nextnode.focus();
														break;
													}
													nextnode = nextnode.nextSibling;
													if(nextnode==null){
														var mbox = Ext.Msg.show({
														     title:'提示',
														     msg: '是否保存？',
														     buttons: Ext.Msg.YESNO,
														     icon: Ext.Msg.QUESTION,
														     fn:function(btn){
														     	if(btn=='yes'){
														     		me.down('button[text=保存]').handler();
														     	}
														     },scope:me
														});
													}
												}
												break;
											}
										}
									}else{
										if(e.target.nextSibling){
											if(!e.target.nextSibling.querySelector('input[type=text]')){
												var nextnode = e.target;
												while(nextnode){
													nextnode = nextnode.nextSibling;
													if(nextnode==null){
														if(e.target.parentNode.nextSibling.cells[0].querySelector('input[type=text]')){
															e.target.parentNode.nextSibling.cells[0].querySelector('input[type=text]').focus();
															break;
														}else{
															var nextTr = e.target.parentNode.nextSibling.cells[0];
															while(nextTr){
																if(nextTr.getAttribute('contenteditable')=="true"){
																	if(nextTr.querySelector('input[type=text]')){
																		nextTr.querySelector('input[type=text]').focus();
																	}else{
																		nextTr.focus();
																	}
																	break;
																}
																nextTr = nextTr.nextSibling;
															}
															
															break;
														}
													}
													if(nextnode.getAttribute('contenteditable')=="true"){
														if(nextnode.querySelector('input[type=text]')){
															nextnode.querySelector('input[type=text]').focus();
														}else{
															nextnode.focus();
														}
														break;
													}
												}
											}
										}
									}
									return false;
								}
							}
							}
						})
					})
					Ext.each(table.rows,function(row,index){
						Ext.each(row.cells,function(cell,indexs){
							cell.onclick = function(e){//点击的时候重新获取焦点，针对焦点不能正确获取的情况
								e.target.focus();
							}
							cell.onkeydown = function(e){//回车换行
								var keycode = e.which||e.keyCode;
								if(keycode==13){//回车键
									if(e.target.tagName=="INPUT"){
										var m = e.target;
										while(m.parentNode){
											m = m.parentNode;
											if(m.getAttribute('contenteditable')=="true"){
												var t = m.nextSibling.querySelector('input[type=text]');
												if(t){
													m.nextSibling.querySelector('input[type=text]').focus();
													break;
												}else{
													m.nextSibling.focus();
													break;
												}
											}
										}
									}else{
										if(e.target.nextSibling){
											if(!e.target.nextSibling.querySelector('input[type=text]')){
												e.target.nextSibling.focus();
											}else{
												e.target.nextSibling.querySelector('input[type=text]').focus();
											}
										}else{
											if(e.target.parentNode.nextSibling.cells[0].querySelector('input[type=text]')){
												e.target.parentNode.nextSibling.cells[0].querySelector('input[type=text]').focus();
											}else{
												var nexttd = e.target.parentNode.nextSibling;
												if(nexttd.cells[0].getAttribute('contenteditable')=="true"){
													nexttd.cells[0].focus();
												}else{
													tables.rows[tables.rows.length-1].cells[1].querySelector('input[type=text]').focus();
												}
												
											}
										}
									}
									return false;
								}
							}
						})
						row.oncontextmenu = function(e){//右击菜单，新增行，删除行
							if(index==table.rows.length-1||index==0){//row.querySelector('table')||
								return false;
							}
							var item = [];
							item.push({
								text : ' 新增行',
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
								   Ext.each(tr.cells,function(cell,index){
//										cell.setAttribute('style',"white-space:nowrap;overflow:hidden;");
										cell.setAttribute('style','white-space:nowrap;overflow:hidden;background: #ffffff;border: 1px solid;');
										cell.setAttribute('contenteditable','true');
								   })
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
								   insertAfter(tr,row);
//								   me.setHeight(me.getHeight()+eval(table.rows[0].cells[0].height));
//								   console.log(me.getHeight())
								   contextmenu();//新增行右击事件
								   countMethod();//新增行统计总数方法
								   addtableCom();//新增行添加下拉框
								   verifyTD();//新增行控制数字输入
								}
							},{
								text : ' 删除行',
								handler : function(){
									var i;
									Ext.each(table.rows,function(row,index){
										if(row==e.target.parentNode){
											i = index;
										}
									})
									table.deleteRow(i);
//									me.setHeight(me.getHeight()-table.rows[0].cells[0].height);
									 contextmenu();
								}
							})
	 					var rightClick = new Ext.menu.Menu ({
							items : item,
							listeners:{
								hide:function(){
									this.close();
								}
							}
						}); 
						rightClick.showAt(e.clientX,e.clientY); 
						return false;//取消默认右击菜单 e.x,e.y
						}
					})
				}
				contextmenu();
				function countMethod(){//计算金额
					Ext.Ajax.request({
						url:'getFtuParameter.do',
						success:function(response){
							var obj = Ext.decode(response.responseText);
							var object = new Object();
							if(obj.success==true){
								Ext.each(obj.data,function(dd,l){
									if(dd.fcomputationalformula){//获取计算金额公式
										var fcomputationalformula = dd.fcomputationalformula;//obj.data[0].fcomputationalformula;
										if(fcomputationalformula){
											var fprice = fcomputationalformula.replace(/[\+\-\/Xx()（）]/g,'*').split("*");
										}
										var table = me.body.dom.querySelector('table table[contenteditable=false]');//产品表格
										Ext.each(table.rows,function(row,index){
											Ext.each(row.cells,function(cell,indexs){
												if(index==0){
													for(var i = 0;i<fprice.length;i++){
//														if(cell.textContent.indexOf(fprice[i])>-1){
														if(cell.textContent.trim()==fprice[i].trim()){
															object[fprice[i]] = indexs;
														}
														if(cell.getAttribute('name')==dd.fsaledeliverentry){//'fprice'){
															object[dd.fsaledeliverentry] = indexs;
															cell.setAttribute('fcomputationalformula',fcomputationalformula);
														}
														if(cell.getAttribute('name')=='famount'){
															object.famount = indexs;
														}
														if(cell.getAttribute('name')=='fprice'){
															object.fpindex = indexs;
														}
													}
												}
												if(index>0){
													if(object.fpindex ==indexs){
														function onblur(e){
															if(!Number(cell.textContent)){
																cell.textContent = '';
															}else{
																var sumFprice = 0;
																Ext.each(table.rows,function(row,rindex){
																	Ext.each(row.cells,function(cell,cindexs){
																		if(rindex>0){
																			if(object.fpindex ==cindexs){
																				sumFprice +=eval(Ext.isEmpty(cell.textContent)?0:cell.textContent.trim());
																			}
																		}
																	})
																})
																table.rows[table.rows.length-1].querySelector('tr').cells[2].innerHTML = table.rows[table.rows.length-1].querySelector('tr').cells[2].textContent.split(':')[0]+":"+(Math.round(sumFprice * 100) / 100==0?'':Math.round(sumFprice * 100) / 100);
																table.rows[table.rows.length-1].querySelector('tr').cells[0].innerHTML = table.rows[table.rows.length-1].querySelector('tr').cells[0].textContent.split(':')[0]+":"+me.toRMB((Math.round(sumFprice * 100) / 100==0?'':Math.round(sumFprice * 100) / 100));
															}
															return;
														}
														cell.removeEventListener('blur',onblur);
														cell.addEventListener('blur',onblur);
													}
													if(fcomputationalformula.indexOf(table.rows[0].cells[indexs].textContent.trim())>-1){
														function blur(e){//失去焦点时，计算金额
															if(!Number(cell.textContent)){
																cell.textContent = '';
																return;
															}
															var fprices = table.rows[0].cells[object[obj.data[l].fsaledeliverentry]].getAttribute('fcomputationalformula');//dd.fcomputationalformula;
															fprice = fprices.replace(/[\+\-\/Xx()（）]/g,'*').split("*");
															var sumFamount = 0,sumFprice = 0;
															try{
																for(var i =0;i<fprice.length;i++){
																	if(object[fprice[i]]){
																		fprices = fprices.replace(fprice[i],Ext.isEmpty(e.target.parentNode.cells[object[fprice[i]]].textContent)?fprice[i]:e.target.parentNode.cells[object[fprice[i]]].textContent.trim())
																	}
																}
																var value= Math.round(eval(fprices.replace(/[\Xx]/g,'*').replace(/[\（]/g,'(').replace(/[\）]/g,')')) * 1000) / 1000;
																e.target.parentNode.cells[object[dd.fsaledeliverentry]].textContent = value.toFixed(dd.fdecimals);//按照设置，取结果小数位数
																
																Ext.each(table.rows,function(row,indexs){
																	if(indexs>0){
																		Ext.each(row.cells,function(cell,index){
																			if(index==object.famount){
																				sumFamount +=eval(Ext.isEmpty(cell.textContent)?0:cell.textContent.trim());
																			}
																			if(index==object.fprice){//计算总金额
																				sumFprice +=eval(Ext.isEmpty(cell.textContent)?0:cell.textContent.trim());
																			}
																		})
																	}
																})
																table.rows[table.rows.length-1].querySelector('tr').cells[1].innerHTML = table.rows[table.rows.length-1].querySelector('tr').cells[1].textContent.split(':')[0]+":"+(Math.round(sumFamount * 1000) / 1000==0?'':Math.round(sumFamount * 1000) / 1000);
																table.rows[table.rows.length-1].querySelector('tr').cells[2].innerHTML = "&nbsp;&yen;:"+(Math.round(sumFprice * 100) / 100==0?'':Math.round(sumFprice * 100) / 100);
																table.rows[table.rows.length-1].querySelector('tr').cells[0].innerHTML = table.rows[table.rows.length-1].querySelector('tr').cells[0].textContent.split(':')[0]+":"+me.toRMB((Math.round(sumFprice * 100) / 100==0?'':Math.round(sumFprice * 100) / 100));
															}catch(ee){
																e.target.parentNode.cells[object.fprice].textContent = '';
																Ext.each(table.rows,function(row,indexs){
																	if(indexs>0){
																		Ext.each(row.cells,function(cell,index){
																			if(index==object.famount){
																				sumFamount +=eval(Ext.isEmpty(cell.textContent)?0:cell.textContent.trim());
																			}
																		})
																	}
																})
																table.rows[table.rows.length-1].querySelector('tr').cells[1].innerHTML = table.rows[table.rows.length-1].querySelector('tr').cells[1].textContent.split(':')[0]+":"+(sumFamount==0?'':sumFamount);
															}
														}
														cell.removeEventListener('blur',blur);
														cell.addEventListener('blur',blur);
													}
												}
											})
										})
									}
								})
							
							}else{
							}
						}
				})
				}
				countMethod();
				function verifyTD(){//检验数字列
					Ext.Ajax.request({
						url:'getNumberParams.do',
						success:function(response){
							var obj = Ext.decode(response.responseText);
							me.verifytd = obj.data;
							if(obj.success==true){
									Ext.each(table.rows,function(row,indexs){
										Ext.each(row.cells,function(cell,indexss){
											if(indexs==0){
												Ext.each(obj.data,function(d,index){
													if(cell.getAttribute('name')==d.fsaledeliverentry){
														d[cell.getAttribute('name')]= indexss;	
													}
												})
											}else{
												Ext.each(obj.data,function(d,indexsss){
													if(d[d.fsaledeliverentry]==indexss){
														cell.onclick = function(e){//点击的时候重新获取焦点，针对焦点不能正确获取的情况
															e.target.focus();
														}
														cell.onkeypress = function(e){
															 var keycode = e.keyCode||e.which;
															 var realkey = String.fromCharCode(keycode);
															 if(!Number(realkey)&&keycode!=48&&keycode!=46&&keycode!=8){
															 	return false;
															 }else{//小数位数控制
															 	if(keycode==8||keycode==13){//火狐删除
															 		return true;
															 	}
															 	if(cell.textContent){
															 		if(keycode==46){
															 			if(d.fdecimals==0){
															 				return false;
															 			}
															 		}
															 		if(cell.textContent.split('.')[1]){
															 			if(cell.textContent.split('.')[1].trim().length>=d.fdecimals){
															 				
															 				 if(document.getSelection){//非IE浏览器,在.前面还能输入数字
																                var selectText  = document.getSelection();
																                if(selectText.focusOffset<=cell.textContent.indexOf('.')){
																                	return true;
																                }
																                
																            }
															 				return false;
															 			}
															 		}
															 	}
															 }
														}
													}
												})
											}
										})
									})
							}
						}
					})
				}
				verifyTD();
			}
			if(me.editstate!='add'&&me.editstate!='print'){
				document.getElementById('printTemplate').querySelector('div:nth-child(2)').textContent = me.data[0].fid;
				var table = me.body.dom.querySelector('table table[contenteditable=false]');
				for(var j =table.rows.length-2;j< me.data.length;j++){//查看、修改数据超出模板行时，自动新增行
					var length= table.rows.length;
				   var tr=document.createElement("tr");
				   tr.setAttribute('height',24);
				   tr.setAttribute('align','center');
				   tr.id=length+1;
				   for(var i=0;i<table.rows[0].cells.length;i++){
				   		var td = document.createElement("td");
						tr.appendChild(td);
				   }
				   Ext.each(tr.cells,function(cell,index){
					   	if(me.editstate=='view'){
							cell.setAttribute('style','white-space:nowrap;overflow:hidden;border: 1px solid;');
					   	}else{
					   		cell.setAttribute('style','white-space:nowrap;overflow:hidden;background: #ffffff;border: 1px solid;');
					   	}
						cell.setAttribute('contenteditable','true');
				   })
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
				   insertAfter(tr,table.rows[0]);
				   me.setHeight(eval(me.getHeight())+24);
				}
				if(me.editstate!='view'){
				   contextmenu();//新增行右击事件
				   countMethod();//新增行统计总数方法
				   addtableCom();//新增行添加下拉框
				   verifyTD();//新增行控制数字输入
				}
				Ext.each(me.body.dom.querySelector('table table[contenteditable=false]').rows,function(row,index){//为编辑界面赋数据
					if(index>0){
						Ext.each(row.cells,function(cell,indexs){
							if(me.data[index-1]){
//								if(index!=me.body.dom.querySelector('table table[contenteditable=false]').rows.length-1){
									if(!cell.querySelector('input[type=text]')){
										cell.textContent = me.data[index-1][me.body.dom.querySelector('table table[contenteditable=false]').rows[0].cells[indexs].getAttribute('name')]
									}else{
										Ext.getCmp(cell.querySelector('input[type=text]').id.replace('-inputEl','')).setValue(me.data[index-1][me.body.dom.querySelector('table table[contenteditable=false]').rows[0].cells[indexs].getAttribute('name')]);
									}
								}
//							}
						})
						if(row.querySelector('table')){
							var sumFamount = 0,sumFprice = 0;
							for(var i =0;i<me.data.length;i++){
								sumFamount += eval(Ext.isEmpty(me.data[i].famount)?0:me.data[i].famount);
								sumFprice += eval(Ext.isEmpty(me.data[i].fprice)?0:me.data[i].fprice);
							}
							if(index==me.body.dom.querySelector('table table[contenteditable=false]').rows.length-1){
								row.querySelector('tr').cells[0].innerHTML = "合计金额(大写):"+me.toRMB(Math.round(sumFprice * 100) / 100);
								row.querySelector('tr').cells[1].innerHTML = "&nbsp;总数量:"+(Math.round(sumFamount * 1000) / 1000==0?'':Math.round(sumFamount * 1000) / 1000);
								row.querySelector('tr').cells[2].innerHTML = "&nbsp;&yen;:"+(Math.round(sumFprice * 100) / 100==0?'':Math.round(sumFprice * 100) / 100);
							}
						}		
					}
				})
				Ext.each(me.body.dom.querySelector('table').rows,function(row,index){
					Ext.each(row.cells,function(cell,indexs){
						if(!cell.querySelector('table')){
							if(cell.getAttribute('name')){
								cell.textContent = Ext.isEmpty(me.data[0][cell.getAttribute('name')])?'':me.data[0][cell.getAttribute('name')];
							}
						}else{
							if(me.editstate=='edit'){
							 	if(cell.querySelector('input[type=text]')&&index!=2){
									Ext.getCmp(cell.querySelector('input[type=text]').id.replace('-inputEl','')).setValue(Ext.isEmpty(me.data[0][cell.getAttribute('name')])?cell.textContent:me.data[0][cell.getAttribute('name')]);
									if(cell.getAttribute('name')=='fcustomername'){//待优化
										Ext.getCmp(cell.querySelector('input[type=text]').id.replace('-inputEl','')).setValue(Ext.data.Record.create({},{},{},{fname:Ext.isEmpty(me.data[0].cname)?cell.textContent:me.data[0].cname,fid:Ext.isEmpty(me.data[0][cell.getAttribute('name')])?cell.textContent:me.data[0][cell.getAttribute('name')]}));
									}
							 	}
							}
						}
					})
				})
				if(me.editstate=='view'){//查看界面不能编辑数据
					me.body.dom.querySelector('#printTemplate').style.overflow = '';
					document.getElementById('printTemplate').querySelector('div').textContent = document.getElementById('printTemplate').querySelector('div').textContent.replace('{制造商名称}',me.data[0].fsuppliername);
					me.down('button[text=保存]').hide();
					me.down('button[text=保存并打印]').hide();
					me.down('button[text=修改]').show();
					Ext.each(me.body.dom.querySelectorAll('td[contenteditable=true],tr[contenteditable=true],div[contenteditable=true]'),function(td){
						td.setAttribute('contenteditable',false);
					})
				}
			}
		},
		afterrender:function(me){
			me.setWidth(eval(me.getWidth())+18);
			me.onload();
			if(me.editstate!='print'&&me.editstate!='view'){//新增时赋制造商名称，编号，开单时间等值
				this.body.dom.querySelector('#printTemplate').style.overflow = '';
				Ext.Ajax.request({
					url:'getFtuConcat.do',
					success:function(response){
						var obj = Ext.decode(response.responseText),table;
						if(obj.success==true){
							var html = document.getElementById('printTemplate');
							table = html.querySelector('table');
							html.querySelector('div').textContent = html.querySelector('div').textContent.replace('{制造商名称}',obj.data[0].fsuppliername);
							if(me.editstate=='add'){
								html.querySelector('td:nth-child(2)').textContent = html.querySelector('td:nth-child(2)').textContent.replace('{电话}',obj.data[0].fphone);
								html.querySelector('td:nth-child(4)').textContent = html.querySelector('td:nth-child(4)').textContent .replace('{传真}',obj.data[0].ffax);
								html.querySelector('td:nth-child(5)').textContent = 'No.'+obj.data[0].fnumber;
								table.rows[table.rows.length-1].querySelector('td:nth-child(4)').textContent = Ext.Date.format(new Date(),'Y-m-d');
							}
							if(me.copy){
								html.querySelector('td:nth-child(5)').textContent = 'No.'+obj.data[0].fnumber;
								table.rows[table.rows.length-1].querySelector('td:nth-child(4)').textContent = Ext.Date.format(new Date(),'Y-m-d')
							}
						}else{
							Ext.Msg.alert('提示',obj.msg);
						}
					}
				})
				Ext.each(this.body.dom.querySelectorAll('span.resizeRightDivClass,span.resizeLeftDivClass'),function(r){
	//				r.setAttribute('class','');
					r.setAttribute('style','display:none');
				})
				Ext.each(me.body.dom.querySelector('table table').rows,function(row,index){
					Ext.each(row.cells,function(cell){
						if(index>0){
							cell.setAttribute('style',"white-space:nowrap;overflow:hidden;");
							if(index>0&&index<me.body.dom.querySelector('table table').rows.length-1){
								cell.setAttribute('style','white-space:nowrap;overflow:hidden;background: #ffffff;border: 1px solid;');
								cell.setAttribute('contenteditable',true);
							}
						}
					})
				})
				me.body.dom.querySelector('table table table td[contenteditable=true]').setAttribute('contenteditable',false);
				Ext.each(me.body.dom.querySelectorAll('div[contenteditable=true]'),function(div){
					div.setAttribute('contenteditable',false);
				})
				Ext.each(me.body.dom.querySelector('table').rows,function(row,indexs){//编辑不能编辑
					Ext.each(row.cells,function(cell,index){
						if(cell.getAttribute('contenteditable')){
							cell.setAttribute('contenteditable',false);
						}else{
							if(index%2 !=0){
								cell.setAttribute('contenteditable',true);
							}
						}
					})
				})
				Ext.each(me.body.dom.querySelectorAll('table td[contenteditable=true]'),function(td,index){
					td.setAttribute('style','white-space:nowrap;overflow:hidden;background: #ffffff;border: 1px solid;');
				})
			}
		}
	},
	dockedItems: [{
	    xtype: 'toolbar',
	    dock: 'top',
	    items: [{
			text:'保存',
			height:30,
			handler:function(){
				var me = this;
				var verifytd = me.up('window').verifytd;
				var newDate = [];
				var name = [];
				var bool = false,tds = '';
				var table = me.up('window').body.dom.querySelector('table table[contenteditable=false]');
				Ext.each(table.rows,function(row,indexs){
					if(indexs==0){
						Ext.each(row.cells,function(cell,index){
							name.push(cell.getAttribute('name')); 
						})
					}
					if(indexs>0&&indexs<table.rows.length-1){
						var Proobject = new Object();
						Ext.each(row.cells,function(cell,index){
							if(Ext.isEmpty(cell.querySelector('table'))){
								if(!Ext.isEmpty(cell.textContent.trim())){
									Ext.each(verifytd,function(td){
										if(td[td.fsaledeliverentry]==index){
											if(!Number(cell.textContent)&&cell.textContent!=0){
												tds = td.falias;
												bool = true;
											}
										}
									})
									Proobject[name[index]] = cell.textContent.replace(/\No./g,'');
								}
							}else{
								if(cell.querySelector('input[type=text]').id){
									if(!Ext.isEmpty(Ext.getCmp(cell.querySelector('input[type=text]').id.replace('-inputEl','')).rawValue.trim())){
										Proobject[name[index]] = Ext.getCmp(cell.querySelector('input[type=text]').id.replace('-inputEl','')).rawValue;
									}
								}
							}
						
						})
						if(!Ext.Object.isEmpty(Proobject)){
							newDate.push(Proobject);
						}
					}
				})
				if(Ext.isEmpty(newDate)){
					Ext.Msg.alert('提示','请填写产品信息！');
					return false;
				}
				var FTUobject = new Object();//送货凭证头信息
				Ext.each(me.up('window').body.dom.querySelectorAll('table td.overflowHiddenClass,div:nth-child(2)'),function(td,indexs){
					if(Ext.isEmpty(td.querySelector('table'))){
						FTUobject[td.getAttribute('name')] = td.textContent.replace(/\No./g,'');
					}else{
						if(td.querySelector('input[type=text]').id)
						FTUobject[td.getAttribute('name')] = Ext.getCmp(td.querySelector('input[type=text]').id.replace('-inputEl','')).rawValue;
					}
				})
				if(Ext.isEmpty(FTUobject.fcustomername)){
					Ext.Msg.alert('提示','请填写客户名称！');
					return false;
				}
				if(bool){
					Ext.Msg.alert('提示','<b>'+tds+"</b>只能输入数字！");
					return false;
				}
				return Ext.Ajax.request({
					url:'saveOrUpdateFtusaledeliver.do',
					params:{FTUSaledeliverEntry:Ext.encode(newDate),FTUSaledeliver:Ext.encode(FTUobject)},
					success:function(response){
						var obj = Ext.decode(response.responseText);
						if(obj.success==true){
							djsuccessmsg('保存成功');
							me.up('window').close();
							Ext.getCmp('DJ.order.Deliver.newFTUsaledeliverList').store.load();
						}else{
							Ext.Msg.alert('提示',obj.msg)
						}
					}
				})
			}
		},{
			text:'保存并打印',
			height:30,
			handler:function(me){
				var saledilverfid = this.prev().handler();
				saledilverfid.options.success=function(response){
						var obj = Ext.decode(response.responseText);
						if(obj.success==true){
							djsuccessmsg('保存成功');
							me.up('window').close();
							Ext.getCmp('DJ.order.Deliver.newFTUsaledeliverList').store.load();
							Ext.getCmp('DJ.order.Deliver.newFTUsaledeliverList').down('button[text=新打印]').handler('','',obj.msg);
						}else{
							Ext.Msg.alert('提示',obj.msg)
						}
					}
				
			}
		},{
			text:'关闭',
			height:30,
			handler:function(){
				this.up('window').close();
			}
		},{
			text:'数据获取',
			hidden:true,
			height:30,
			handler:function(me){
				var newDate = [];
				var name = [];
				var table = me.up('window').body.dom.querySelector('table table[contenteditable=false]');
				Ext.each(table.rows,function(row,indexs){
					if(indexs==0){
						Ext.each(row.cells,function(cell,index){
							name.push(cell.getAttribute('name')); 
						})
					}
					if(indexs>0&&indexs<table.rows.length-1){
						var Proobject = new Object();
						Ext.each(row.cells,function(cell,index){
							if(Ext.isEmpty(cell.querySelector('table'))){
								if(!Ext.isEmpty(cell.textContent.trim())){
									Proobject[name[index]] = cell.textContent.replace(/\No./g,'');
								}
							}else{
								if(cell.querySelector('input[type=text]').id){
									if(!Ext.isEmpty(Ext.getCmp(cell.querySelector('input[type=text]').id.replace('-inputEl','')).rawValue.trim())){
										Proobject[name[index]] = Ext.getCmp(cell.querySelector('input[type=text]').id.replace('-inputEl','')).rawValue;
									}
								}
							}
						
						})
						if(!Ext.Object.isEmpty(Proobject)){
							newDate.push(Proobject);
						}
					}
					
				})
				console.log(newDate);
				//				var newDate = [];
//				var data = [];
//				var name = [],me = this;
//				var params = '';
//				Ext.each(me.up('window').body.dom.querySelector('table table[contenteditable=false]').rows,function(row,indexs){
//					Ext.each(row.cells,function(cell,index){
//						if(indexs==0){
//							name.push(cell.getAttribute('name')); 
//						}else{
//							if(!Ext.isEmpty(cell.textContent!=null?cell.textContent.trim():null)&&indexs!=me.up('window').body.dom.querySelector('table table[contenteditable=false]').rows.length-1){
//								params +=  "'"+name[index]+"':'"+cell.textContent.trim()+"'";
//								if(index<row.cells.length-1){
//									params += ',';
//								}
//							}
//						}
//					})
//					if(!Ext.isEmpty(params)){
//						data.push("{"+params+"}")
//						params = '';
//					}
//				})
//				Ext.each(data,function(d){
//					newDate.push(eval('(' + d+ ')'))
//				})
			}
		},{
			text:'打印预览',
			hidden:true,
			handler:function(){
				var html = document.getElementById('printTemplate').parentNode;
				 Ext.each(html.querySelectorAll('.resizeLeftDivClass,.resizeRightDivClass,td[noprint=true]'),function(c){
				 	c.style.display='none';
				 })
				 Ext.each(html.querySelectorAll('table table'),function(table){//循环所有的table
					 var i = 0;
					 Ext.each(table.rows,function(row){
					 	Ext.each(row.cells,function(cell){
					 		if(cell.style.display == 'none'){
					 			i++
					 		}
					 	})
					 	if(row.querySelector('table')){//打印时隐藏不需要打印的列，里面table要改变colspan
					 		if(i>=table.rows.length-1){
					 			row.querySelector('td').setAttribute('colspan',row.querySelector('td').getAttribute('colspan')-eval((i/(table.rows.length-1))));
					 		}
					 	}
					 })
				 })
				Ext.DomHelper.overwrite(Ext.get('panelPrint').dom.contentWindow.document.body,"<style>@media print {@page {margin: 2mm 10mm 0mm 10mm;}} table{table-layout:fixed;}</style>" + html.innerHTML);//this.up('window').body.dom.innerHTML);
				Ext.get('panelPrint').dom.contentWindow.focus();
				Ext.get('panelPrint').dom.contentWindow.print();
				this.up('window').close();
//				Ext.each(html.querySelectorAll('.resizeLeftDivClass,.resizeRightDivClass,td[noprint=true]'),function(c){
//				 	c.style.display='';
//				 })
//				 Ext.each(html.querySelector('table table').rows,function(row){
//				 	Ext.each(row.cells,function(cell){
//				 		if(cell.style.display == 'none'){
//				 			i++
//				 		}
//				 	})
//				 	if(row.querySelector('table')){//还原回来
//				 		if(i>=html.querySelector('table table').rows.length-1){
//				 			row.querySelector('td').setAttribute('colspan',eval(row.querySelector('td').getAttribute('colspan'))+eval((i/(html.querySelector('table table').rows.length-1))));
//				 		}
//				 	}
//				 })
			}
		},{
			text:'修改',
			height:30,
			hidden:true,
			handler:function(){
				this.up('window').close();
				Ext.getCmp('DJ.order.Deliver.newFTUsaledeliverList').down('button[text=新编辑]').handler();
			}
		}]
	}]
})