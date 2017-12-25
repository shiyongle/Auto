Ext.require("DJ.myComponent.form.MainSchemeDesignListDeliverButton");

Ext.define('DJ.order.saleOrder.mainSchemeDesignList', {
	extend : 'Ext.panel.Panel',
	id : 'DJ.order.saleOrder.mainSchemeDesignList',
	autoScroll : true,
	border : false,
	layout : 'border',
	title:'方案设计',
	closable:true,
	
	items:
			[{
				region : 'center',
				items : [ Ext.create("DJ.order.Deliver.FistproductdemandList",{
					id:'DJ.order.saleOrder.SchemeDesignID',
					url:'getAllotedFirstproductList.do',
					exporturl : "exportProductDemand.do",
					selModel : Ext.create('Ext.selection.CheckboxModel'),
					fields:[{name:'fid'},{name:'cname',myfilterfield : 'a.cname',myfiltername : '客户名称',myfilterable : true},
					        {name:'fname',myfilterfield : 'a.fname',myfiltername : '需求名称',myfilterable : true},{name:'fnumber',myfilterfield : 'a.fnumber',myfiltername : '需求编号',myfilterable : true},{name:'fdescription'},{name:'fboxlength'},
					        {name:'fboxwidth'},{name:'fboxheight'},{name:'fboardlength'},{name:'fboardwidth'},
					        {name:'fsuppliername'},{name:'fsupplierid'},
					        {name:'fcreatid'},{name:'fcreatetime'},{name:'fupdateuserid'},{name:'fupdatetime'},
					        {name:'fauditorid'},{name:'fauditortime',myfilterfield : 'a.fauditortime',myfiltername : '发布时间',myfilterable : true},{name:'isfauditor'},{name:'fcustomerid'},
					        {name:'fsupplierid'},{name:'falloted'},{name:'fallotor'},{name:'fallottime'},{name:'freceived'},
					        {name:'freceiver',myfilterfield : 'a.freceiver',myfiltername : '设计师',myfilterable : true},{name:'freceiverTel'},{name:'freceivetime'},{name:'fname'},{name:'ftype'},{name:'fcostneed'},{name:'fiszhiyang'},{name:'famount'}
					        ,{name:'foverdate',myfilterfield : 'a.foverdate',myfiltername : '方案入库日期',myfilterable : true},{name:'farrivetime'},{name:'fboxpileid'},{name:'fmaterial'},{name:'fcorrugated'},{name:'fprintcolor'},{name:'fprintbarcode'},{name:'funitestyle'}
					        ,{name:'fprintstyle'},{name:'fsurfacetreatment'},{name:'fpackstyle'},{name:'fpackdescription'},{name:'fisclean'},{name:'fispackage'},{name:'fpackagedescription'},{name:'fislettering'},{name:'fletteringescription'},
					        {name:'fstate',myfilterfield : 'a.fstate',myfiltername : '需求状态',myfilterable : true},
					        {name:'flinkman'},{name:'flinkphone'},{name:'fisaccessory',myfilterfield : 'a.fisaccessory',myfiltername : '是否有附件(1是、0否)',myfilterable : true},{name:'fgroupid'},{name:'fqq'},{name:'fisdemandpackage'}],
						onload : function() {
						var state=this.down('toolbar').getComponent('selectcombo');
							state.fireEvent('select', state,state.setValue("-1"));
						},
					custbar : [
					           {
									text : '设计师指定',
									height : 30,
									handler : function() {
										var grid = Ext.getCmp('DJ.order.saleOrder.SchemeDesignID');
										var records = grid.getSelectionModel().getSelection();
										if(records.length<1){
											Ext.MessageBox.alert("错误","请选择一条记录！");
											return;
										}
										var result = "('";
										for(var i=0;i<records.length;i++){
											if(records[i].get('freceived')=='true'){
												Ext.Msg.alert('提示','已接收方案不能重复接收！');
												return ;
											}
											result += records[i].get("fid");
											if(i<records.length-1){
												result += "','";
											}
										}
										result+="')";
										Ext.Ajax.request({
										url:'getFirstdemandSupplierid.do',
										params:{
												fids:result
										},
										success : function(response, option) {
											var obj = Ext.decode(response.responseText);
											if (obj.success == true) {
															var designerEdit = Ext.create("DJ.order.saleOrder.designerEdit");
															designerEdit.show();
															Ext.getCmp("DJ.order.saleOrder.designerEdit.FresultID").setValue(result);
															Ext.getCmp("DJ.order.saleOrder.designerEdit.fsupplierid").setmyvalue("\"fid\":\""+ obj.data[0].fsupplierid+ "\",\"fname\":\""+  obj.data[0].fsuppliername+ "\"");
														} else {
															Ext.MessageBox.alert('错误', obj.msg);

														}
													}
												})
									}
								},{
									text : '取消指定',
									height : 30,
									handler : function() {
										var grid = Ext.getCmp('DJ.order.saleOrder.SchemeDesignID');
										var records = grid.getSelectionModel().getSelection();
										if(records.length<1){
											Ext.MessageBox.alert("提示","请选择一条记录！");
											return;
										}
										var result = [];
										for(var i=0;i<records.length;i++){
											if(records[i].get('freceived')=='true'){
												result.push("'"+records[i].get('fid')+"'");
											}
										}
										if(result.length==0){
											Ext.Msg.alert('提示','请选择一条已接收记录进行操作！');
											return;
										} 
										result='('+result.join()+')';
										Ext.MessageBox.confirm('提示', '是否取消指定设计师?方案未确认则直接删除方案！',function(btn, text){
											if(btn=="yes"){
												var el = grid.getEl();
												el.mask("系统处理中,请稍候……");
												Ext.Ajax.request({
													url:'UnReceiveProductdemand.do',
													params:{
														fids:result
													},
													success : function(response, option) {
														var obj = Ext.decode(response.responseText);
														if (obj.success == true) {
															djsuccessmsg(obj.msg);
															Ext.getCmp("DJ.order.saleOrder.SchemeDesignID").store
																	.load();

														} else {
															Ext.MessageBox.alert('错误', obj.msg);

														}
														el.unmask();
													}
												})
											}
										})
									
									}
								},
								{
									text : '新增方案',
									height : 30,
									handler : function() {
										var grid = Ext.getCmp('DJ.order.saleOrder.SchemeDesignID');
										grid.addClick = true;
										var records = grid.getSelectionModel().getSelection();
										if(records.length<1){
											Ext.MessageBox.alert("错误","请选择一条产品需求后再新增方案！");
											return;
										}
										if(records[0].data.freceived=="null" || records[0].data.freceived == "false"){
											Ext.MessageBox.alert("错误","请先接收后再新增方案！");
											return;
										}
										if("关闭"==records[0].get("fstate")){
											Ext.MessageBox.alert("错误","已关闭的不能新增方案！");
											return;
										}
										Ext.Ajax.request({
											url:'getSDFidFnumber.do',
											success:function(response){
												var obj = Ext.decode(response.responseText);
												if(obj.success){
													var win = Ext.create('DJ.order.saleOrder.SchemeDesignEdit');
													win.seteditstate("add");
													win.setparent("DJ.order.saleOrder.SchemeDesignList");
													Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.fid').setValue(obj.data[0].fid);
													Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.fnumber').setValue(obj.data[0].fnumber);
													
													win.down("textfield[name=fcustomerid]").setValue(records[0].data.fcustomerid);
													win.down("textfield[name=fsupplierid]").setValue(records[0].data.fsupplierid);
													win.down("textfield[name=ffirstproductid]").setValue(records[0].data.fid);
													win.down("textfield[name=fname]").setValue(records[0].data.fname);
													win.show();
													Ext.getCmp('DJ.order.saleOrder.SchemeDesignEdit.fnumber').setReadOnly(true);
												}else{
													Ext.Msg.alert('错误',obj.msg);
												}
												grid.addClick = false;
											}
										});
									}
									},{
											text:'编辑产品',
											handler:function(){
												var grid = Ext.getCmp('DJ.order.saleOrder.SchemeDesignID');
												var records = grid.getSelectionModel().getSelection();
												if(records.length==0){
													Ext.MessageBox.alert("错误","请选择一条记录！");
													return;
												}
												//方案已经确认才可新增产品
												Ext.Ajax.request({
													url:'getFirstProductisEdit.do',
													params:{
														fid:records[0].get("fid")
													},
													success : function(response, option) {
														var obj = Ext.decode(response.responseText);
														if (obj.success == true) {
														var win = Ext.create('DJ.order.saleOrder.FistDemandProductEdit');
														win.seteditstate(obj.msg);
														win.loadfields(records[0].get("fid"));
														win.setparent("DJ.order.saleOrder.SchemeDesignID");
														win.show();

//														
														} else {
															Ext.MessageBox.alert('错误', obj.msg);

														}
													}
												})
												
											}
									},{
									text : '完成',
									height : 30,
									handler : function() {
										var grid = Ext.getCmp('DJ.order.saleOrder.SchemeDesignID');
										var records = grid.getSelectionModel().getSelection();
										if(records.length<1){
											Ext.MessageBox.alert("提示","请选择一条记录！");
											return;
										}
//										if("关闭"==records[0].get("fstate")){
//											Ext.MessageBox.alert("错误","已关闭的不能完成！");
//											return;
//										}
										var result = [];
										for(var i=0;i<records.length;i++){
		
											result.push(records[i].get('fid'));
											
										}
										if(result.length==0){
											Ext.Msg.alert('提示','请选择一条记录进行操作！');
											return;
										}
										
										result=result.join();
										
										var el = grid.getEl();
										el.mask("系统处理中,请稍候……");
										Ext.Ajax.request({
											url:'updateStateBySupplierOver.do',
											params:{
												fids:result,
												ftype:0
											},
											success : function(response, option) {
												var obj = Ext.decode(response.responseText);
												if (obj.success == true) {
													djsuccessmsg(obj.msg);
													Ext.getCmp("DJ.order.saleOrder.SchemeDesignID").store
															.load();
													el.unmask();
												} else {
													el.unmask();
													if(obj.msg=="该需求有未确认的方案，是否继续操作。"){
													Ext.MessageBox.confirm('错误', obj.msg,function(id)
													{
															if(id=="yes"){
															el.mask("系统处理中,请稍候……");
															Ext.Ajax.request({
															url:'updateStateBySupplierOver.do',
															params:{
																fids:result,
																ftype:1
															},
															success : function(response, option) {
																var obj = Ext.decode(response.responseText);
																if (obj.success == true) {
																	djsuccessmsg(obj.msg);
																	Ext.getCmp("DJ.order.saleOrder.SchemeDesignID").store
																			.load();
																	
																} else {
																	
																	Ext.MessageBox.alert('错误',obj.msg);
				
																}
																el.unmask();
															}
														})
															}
															});
													}else
													{
														Ext.MessageBox.alert('错误',obj.msg);
													}

												}
												
											}
										})
									}
									},{
									text : '取消完成',
									height : 30,
									handler : function() {
										var grid = Ext.getCmp('DJ.order.saleOrder.SchemeDesignID');
										var records = grid.getSelectionModel().getSelection();
										if(records.length<1){
											Ext.MessageBox.alert("提示","请选择一条或多条记录进行操作！");
											return;
										}
										var result = [];
										for(var i=0;i<records.length;i++){
											
												result.push(records[i].get('fid'));

										}
										if(result.length==0){
											Ext.MessageBox.alert("提示","请选择一条或多条记录进行操作！");
											return;
										} 
										result=result.join();
										var el = grid.getEl();
										el.mask("系统处理中,请稍候……");
										Ext.Ajax.request({
											url:'updateStateBySupplierUnOver.do',
											params:{
												fids:result
											},
											success : function(response, option) {
												var obj = Ext.decode(response.responseText);
												if (obj.success == true) {
													djsuccessmsg(obj.msg);
													Ext.getCmp("DJ.order.saleOrder.SchemeDesignID").store
															.load();

												} else {
													Ext.MessageBox.alert('错误', obj.msg);

												}
												el.unmask();
											}
										})
									}
									},'-',{
									xtype:'textfield',
//									id:"DJ.order.saleOrder.SchemeDesignID.filtername",
									width:120,
									emptyText:'请输入需求名称...',
									itemId:'text',
									enableKeyEvents:true,
									listeners:{
										keypress:function(me,e){
											if(e.getKey()==13){
												this.up('grid').filterByName();
											}
										}
									}
									},{
									
										xtype:'combo',
										itemId:'selectcombo',
//										displayField:'state',
//										valueField:'value',
										queryMode:'local',
										store:[["-1","全部"],["已发布","已发布"],["已分配","已分配"],["已接收","已接收"],["已设计","已设计"],["已完成","已完成"],["关闭","已关闭"]],
										listeners:{
											select:function(){
												this.up('grid').filterByName();
											}
										}
										},{
									text:'查找',
									itemId:'search',
									handler:function(){
										this.up('grid').filterByName();
									}
								}],
					listeners:{
						render:function(){
							
							Ext.getCmp('DJ.order.saleOrder.SchemeDesignID.viewbutton').setVisible(true);
							Ext.getCmp('DJ.order.saleOrder.SchemeDesignID.refreshbutton').setVisible(true);
							Ext.getCmp('DJ.order.saleOrder.SchemeDesignID.querybutton').setVisible(true);
							Ext.getCmp('DJ.order.saleOrder.SchemeDesignID.addbutton').setVisible(false);
							Ext.getCmp('DJ.order.saleOrder.SchemeDesignID.editbutton').setVisible(false);
							Ext.getCmp('DJ.order.saleOrder.SchemeDesignID.delbutton').setVisible(false);
							Ext.getCmp('DJ.order.saleOrder.SchemeDesignID.exportbutton').setVisible(true);
//							this.down('#fstate').hide();
							this.down('#fiszhiyang').hide();
//							this.down('#fcreatid').hide();
//							this.down('#fcreatetime').hide();
							this.down('#fauditorid').hide();
//							this.down('#fauditortime').hide();
							this.down('#flinkman').show();
							this.down('#flinkphone').show();
							this.down('#fisaccessory').show();
							this.down('#freceivetime').hide();
							this.down('#fsuppliername').hide();
						},
						itemclick:function(view,record,item,index){
							var store = Ext.getCmp('DJ.order.saleOrder.SchemeDesignList').getStore();
							store.setDefaultfilter([{
								myfilterfield : "s.ffirstproductid",
								CompareType : " = ",
								type : "string",
								value : record.get("fid")
							}]);
							store.setDefaultmaskstring(" #0 ");
							store.load();
						},
						itemdblclick: function(){
							Ext.getCmp('DJ.order.saleOrder.SchemeDesignID.viewbutton').handler();
						}
					},
					filterByName:function(){
						var store = this.getStore();
//						store.setDefaultfilter([{
//							myfilterfield : "f.fname",
//							CompareType : " like ",
//							type : "string",
//							value : this.down('toolbar').getComponent('text').getValue()
//						}]);
//						store.setDefaultmaskstring(" #0 ");
						var compare="=",closevalue="关闭",closecompare="=",vvalue=this.down('toolbar').getComponent('selectcombo').getValue();
						var flag=false;
												if(vvalue=="-1")
												{
													flag=true;
												   compare="<>";
												   vvalue="已完成";
												}
												
												if(vvalue!="关闭")
												{
													closecompare="<>";
												}
									
												if(vvalue!="关闭"){
													if(flag){
														store.setDefaultfilter([{
															myfilterfield : "fstate",
															CompareType : compare,
															type : "string",
															value : vvalue
															},{
																myfilterfield : "fstate",
																CompareType : closecompare,
																type : "string",
																value : closevalue
																},{
															myfilterfield : "fname",
															CompareType : " like ",
															type : "string",
															value : this.down('toolbar').getComponent('text').getValue()
															},{
																myfilterfield : "cname",
																CompareType : " like ",
																type : "string",
																value : this.down('toolbar').getComponent('text').getValue()
															},{
																myfilterfield : "count",
																CompareType : " = ",
																type : "string",
																value : "0"
															}]);
													}else{
													store.setDefaultfilter([{
														myfilterfield : "fstate",
														CompareType : compare,
														type : "string",
														value : vvalue
														},{
															myfilterfield : "fstate",
															CompareType : closecompare,
															type : "string",
															value : closevalue
															},{
														myfilterfield : "fname",
														CompareType : " like ",
														type : "string",
														value : this.down('toolbar').getComponent('text').getValue()
														},{
															myfilterfield : "cname",
															CompareType : " like ",
															type : "string",
															value : this.down('toolbar').getComponent('text').getValue()
														}]);}
												}else{
													store.setDefaultfilter([{
														myfilterfield : "fstate",
														CompareType : compare,
														type : "string",
														value : vvalue
														},{
														myfilterfield : "fname",
														CompareType : " like ",
														type : "string",
														value : this.down('toolbar').getComponent('text').getValue()
														},{
															myfilterfield : "cname",
															CompareType : " like ",
															type : "string",
															value : this.down('toolbar').getComponent('text').getValue()
														}]);
												}
												if(vvalue!="关闭"){
													if(flag){
														store.setDefaultmaskstring(" #0 and #1 and (#2 or #3) and #4 ");
													}else{
													store.setDefaultmaskstring(" #0 and #1 and (#2 or #3)");}
												}else{
													store.setDefaultmaskstring(" #0 and (#1 or #2)");
												}
												store.loadPage(1);
					}
				})],
				layout:'fit'
			},
			{
				region : 'south',
				split : true,
				width : 220,
				height:250,
				layout:'fit',
				items : [ Ext.create("DJ.order.saleOrder.SchemeDesignList", {
//					onload:function(){
//						Ext.getCmp('DJ.order.saleOrder.SchemeDesignList.viewbutton').hide();
//					},
				
					id:'DJ.order.saleOrder.SchemeDesignList',
					selModel : Ext.create('Ext.selection.CheckboxModel'),
					fields:[{name:'fid'},{name:'fdescription'},{name:'ffirstproductid'},
					        {name:'fname',myfilterfield : 's.fname',myfiltername : '方案名称',myfilterable : true},
					        {name:'fnumber',myfilterfield : 's.fnumber',myfiltername : '方案编码',myfilterable : true},
					        {name:'fcustomer',myfilterfield : 'c.fname',myfiltername : '客户名称',myfilterable : true},{name:'fsupplier'},{name:'fcreatid'},
					        {name:'fcreator',myfilterfield : 'u1.fname',myfiltername : '创建人',myfilterable : true},
					        {name:'fcreatetime',myfilterfield : 's.fcreatetime',myfiltername : '创建时间',myfilterable : true},
					        {name:'fconfirmed',myfilterfield : 's.fconfirmed',myfiltername : '是否确认（1是 0否）',myfilterable : true},'coname','fconfirmtime',{name:'utype'},
					        {name:'fgroupid'},'sfid','fauditorid','faudited','fqq'],
					custbar:['-',{
						xtype:'textfield',
						width:120,
						emptyText:'请输入方案名称...',
						itemId:'text',
						enableKeyEvents:true,
						listeners:{
							keypress:function(me,e){
										if(e.getKey()==13){
											this.up('grid').filterByName();
										}
								}
						}
					},{
						text:'查找',
						itemId:'search',
						handler:function(){
							this.up('grid').filterByName();
						}
					},{
						text:'审核',
						handler:function(){
							var grid = this.up('grid');
							var records = grid.getSelectionModel().getSelection();
							if(records.length!=1){
								Ext.MessageBox.alert("错误","请选择一条记录！");
								return;
							}
							if(records[0].get('fconfirmed')=='1'){
								Ext.MessageBox.alert("错误","已确认的不能再审核！");
								return;
							}
							if(records[0].get('faudited')=='1'){
								Ext.MessageBox.alert("错误","已审核！");
								return;
							}
							if(Ext.isEmpty(records[0].get('sfid'))){
								Ext.MessageBox.alert("错误","无需审核！");
								return;
							}
							this.prev('button[text*=修]').handler();
							var editwin = Ext.getCmp(grid.EditUI);
							Ext.each(editwin.query('toolbar button'),function(button){
								button.hide();
							})
							editwin.url = "SaveOrupdateSchemeDesignAudit.do";
							editwin.down('button[text*=保]').setText('审核').show();
						}
					},{
						text : '确认',
						height : 30,
						tooltip:'有特性的方案(唛稿类方案)确认后已直接下单',
						handler : function() {
							var grid = Ext.getCmp('DJ.order.saleOrder.SchemeDesignList');
							var records = grid.getSelectionModel().getSelection();
							if(records.length==0){
								Ext.MessageBox.alert("错误","请选择一条记录！");
								return;
							}
							var result = "'";
							for(var i=0;i<records.length;i++){
								if(!Ext.isEmpty(records[i].get('sfid'))){
									if(records[i].get('faudited')=='0'){
										Ext.MessageBox.alert("错误","请审核后再确认！");
										return;
									}
								}
								
								result += records[i].get("fid");
								if(i<records.length-1){
									result += "','";
								}
							}
							result+="'";
//							var el = grid.getEl();
//							el.mask("系统处理中,请稍候……");
//							var productdemand = Ext.getCmp('DJ.order.Deliver.FistproductdemandList');
//							var productrecord = productdemand.getSelectionModel().getSelection();
//							if(productrecord.length==0){
//								Ext.MessageBox.alert("错误","请选择一条包装需求！");
//								return;
//							}
							Ext.Ajax.request({
								url:"AffirmSchemeDesign.do",
								params:{
									fids:result
								},
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										djsuccessmsg(obj.msg);
									if(obj.total==true){
										Ext.MessageBox.confirm('提示', '是否全部生成配送信息!',function(btn, text){
											if(btn=="yes"){
												var fid ="";
												for(var i=0;i<records.length;i++){
													fid += records[i].get("fid");
													if(i<records.length-1){
														fid += ",";
													}
												}
												//Ext.MessageBox.alert('错误', "未完.待续.....");
												Ext.Ajax.request({
													timeout : 6000,
													url : 'generateDelivery.do',
													params : {
														fids : fid
													},
													success : function(response, option) {

														var obj = Ext.decode(response.responseText);
														if (obj.success == true) {
															Ext.MessageBox.alert('成功', obj.msg);

														} else {
															Ext.MessageBox.alert('错误', obj.msg);

														}
													}
												});
											}
										}
										)
									}
										Ext.getCmp('DJ.order.saleOrder.SchemeDesignList').getStore().load();
//										productdemand.getStore().load({
//											
//											 callback: function(records, operation, success) {
//												 productdemand.getSelectionModel().select(productrecord[0].index);
//											    }
//											
//										});
										
										
									} else {
										Ext.MessageBox.alert('错误', obj.msg);

									}
//									el.unmask();
								}
							})
						}
					},{
						text : '取消确认',
						height : 30,
						handler : function() {

							var grid = Ext.getCmp('DJ.order.saleOrder.SchemeDesignList');
							var records = grid.getSelectionModel().getSelection();
							if(records.length==0){
								Ext.MessageBox.alert("错误","请选择一条记录！");
								return;
							}
							var result = "'";
							for(var i=0;i<records.length;i++){
								result += records[i].get("fid");
								if(i<records.length-1){
									result += "','";
								}
							}
							result+="'";
//							var el = grid.getEl();
//							el.mask("系统处理中,请稍候……");
//							var productdemand = Ext.getCmp('DJ.order.Deliver.FistproductdemandList');
//							var productrecord = productdemand.getSelectionModel().getSelection();
//							if(productrecord.length==0){
//								Ext.MessageBox.alert("错误","请选择一条包装需求！");
//								return;
//							}
							Ext.Ajax.request({
								url:"UnAffirmSchemeDesign.do",
								params:{
									fids:result
								},
								success : function(response, option) {
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										djsuccessmsg(obj.msg);
//										var record = Ext.getCmp('DJ.order.Deliver.FistproductdemandList').getSelectionModel().getSelection();
										Ext.getCmp('DJ.order.saleOrder.SchemeDesignList').store
												.load();
//										productdemand.getStore().load({
//											
//											 callback: function(records, operation, success) {
//												 productdemand.getSelectionModel().select(productrecord[0].index);
//											    }
//											
//										});
											
									} else {
										Ext.getCmp('DJ.order.saleOrder.SchemeDesignList').store
										.load();
										Ext.MessageBox.alert('错误', obj.msg);

									}
//									el.unmask();
								}
							})
						
						}
					}, {
				xtype : "mainschemedesignlistdeliverbutton"

			}, {

				xtype : "mainschemedesignlistdeliverbutton",
				isCreateDeliver : false

			},{
				text:'查看',
				handler:function(){
					var grid = Ext.getCmp('DJ.order.saleOrder.SchemeDesignList');
					var records = grid.getSelectionModel().getSelection();
					if(records.length==0){
						Ext.MessageBox.alert("错误","请选择一条记录！");
						return;
					}
					var panel = Ext.create('DJ.order.saleOrder.SchemeDesignTabPanel',{
						schemeDesignId:records[0].get('fid')
					});
					panel.parentid = 'DJ.order.saleOrder.SchemeDesignList';
					panel.show();
				}
			}],
					listeners :{
						
						render:function(){
							Ext.getCmp('DJ.order.saleOrder.SchemeDesignList.addbutton').setVisible(false);
							Ext.getCmp('DJ.order.saleOrder.SchemeDesignList.viewbutton').hide();
						},
						itemdblclick: function(){
							Ext.getCmp('DJ.order.saleOrder.SchemeDesignList.editbutton').handler();
						}
					},
					filterByName:function(){
						var store = this.getStore();
						store.setDefaultfilter([{
							myfilterfield : "s.fname",
							CompareType : " like ",
							type : "string",
							value : this.down('toolbar').getComponent('text').getValue()
						}]);
						store.setDefaultmaskstring(" #0 ");
						store.loadPage(1); 
					}
				})]
				
			}]
	       
});
