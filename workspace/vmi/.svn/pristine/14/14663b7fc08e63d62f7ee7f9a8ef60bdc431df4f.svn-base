ltc = function(file){//this.parentNode.submit()
	console.log(file.value);
}
Ext.define('DJ.supplier.mainCustomerEdit', {
	id : 'DJ.supplier.mainCustomerEdit',
	width : 730,
	height : 500,
	editstate:'',
	parent:'',
	title:'我的客户编辑界面',
	extend : 'Ext.Window',
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [ Ext.create('Ext.tab.Panel', {
				id : 'DJ.supplier.mainCustomerEdit.panel',
				autoScroll:true,
				activeTab : 0,
				items : [
				          Ext.create('DJ.supplier.MyCustomerEdit',{
								closable : false,
								frame:true,
								autoScroll:true,
								height:420
							})
						,{
							xtype:'panel',
							title:'导入客户',
							autoScroll:true,
							height:520,
							bodyPadding:'15 20',
							layout:{
								type:'vbox'
							},
							items:[{
								xtype:'displayfield',//line-height:5px
								value:'<div style="font-size:18px">一、请按照标准模板格式出入内容<a href="downloadMyCustomerExcel.do"><b>【下载模板】</b></a><br/>' +
										'<p style="text-indent:2em;line-height:5px">注意事项</p>' +
										'<p style="text-indent:2em;">1、模板中的表头名称不可更改，表头行不能删除</p>' +
										'<p style="text-indent:2em;">2、客户名称为必填项</p>' +
										'<p style="text-indent:2em;">3、客户信息重复时，不导入</p><div><br/>'
							},{
								xtype:'displayfield',//line-height:5px
								value:'<div style="font-size:18px">二、上传填好的文件模板<br/>' 
							},{
								xtype:'cTable',
								name : "Address",
								width:'100%',
								height : 100,
								pageSize : 100,
								url : "getMyCustomerFile.do",
								border:false,
								hideHeaders :true,
								onload:function(){
									Ext.each(this.query('toolbar button[id*='+this.id+']'),function(button){
										this.up().hide();
									})
									var me = this;
									this.store.on('load',function( store, records, index, eOpts ){
										Ext.each(records,function(){
											me.setHeight(me.height+22);
										})
									})
									this.store.load();
								},
								dockedItems: [{
							        xtype: 'toolbar',
							        dock: 'top',
							        style : {
										background : 'white'
									},
							        items: [{
							            text: '选择文件',
							            height : 30,
							            preventDefault:false,
							            style : {
											background : '#ced9e7'
										}
							        }]
							    }],
								fields:[{
									name:'fid'
								},{
									name:'fname'
								},{
									name:'fpath'
								},{
									name:'fparentid'
								}],
								columns:[{
									dataIndex:'fid',
									hidden:true,
									hideable:false
								},{
									text:'路径',
									dataIndex:'fpath',
									id:'DJ.order.Deliver.productdemandfile.fpath',
									hidden:true,
									hideable:false
								},{
									xtype:'templatecolumn',
									text:'附件名称',
									dataIndex:'fname',
									tpl : '<a href="downloadProductdemandFile.do?fid={fid}" target="_blank" style="text-decoration:none;">{fname}</a>',
									flex:1
								},{
									xtype:'actioncolumn',
									width:100,
									align:'center',
									text:'操作',
									id:'DJ.order.Deliver.productdemandfile.productActions',
							        items: [
								        {
							            icon: 'images/delete.gif',
							            tooltip: '删除附件',
							            handler: function(grid, rowIndex, colIndex) {
							            	
							                var fid = grid.getStore().getAt(rowIndex).get('fid');
							                var fpath = grid.getStore().getAt(rowIndex).get('fpath');
							                var fparentid = grid.getStore().getAt(rowIndex).get('fparentid');
							                Ext.Ajax.request({
							                	url:'delProductdemandfile.do',
							                	params:{
							                		fid:fid,
							                		fpath:fpath,
							                		fparentid:fparentid
							                		},
							                	success:function(res){
							                		var obj = Ext.decode(res.responseText);
							                		if(obj.success){
							                			djsuccessmsg(obj.msg);
							                			grid.getStore().load({params:{fparentid:fparentid}});
							                		}else{
							                			Ext.Msg.alert('提示',obj.msg);
							                		}
							                	},
							                	failure:function(){
							                		Ext.Msg.alert('错误','系统故障，请检查网络状况或联系管理员！');
							                	}
							                })
	            
							            }
							        }]
								},{
									dataIndex:'fparentid',
									hidden:true,
									hideable:false
								}]
							}]
						}],
						listeners:{
							tabchange:function( tabPanel, newCard, oldCard, eOpts ){
								if(newCard.title=='手动新增'){
									var button = tabPanel.up().down('toolbar[dock=bottom] button[text=导入]');
									if(!Ext.isEmpty(button)){
										button.setText('保存');
									}
								}else if(newCard.title='导入客户'){
									var task = new Ext.util.DelayedTask(function(){
										var b = newCard.down('button[text=选择文件]');
										var btn = document.getElementById(b.id);
										var div = document.createElement('div');
										var frag = document.createDocumentFragment();
										div.innerHTML = '<form action="MyCustomerUploadFile.do" method="post" enctype="multipart/form-data" target="hideFileTarget"><input type="file" multiple="multiple" name="file" onchange="this.parentNode.submit()" style="height:40px;outline:medium none;position:absolute;display:block;top:0;right:0;opacity:0;-moz-opacity:0;filter:alpha(opacity=0);cursor:pointer;" ></input></form><iframe id="ProductDemand.hideFileTarget" name="hideFileTarget" style="display:none;" />';
										while(div.firstChild){
											frag.appendChild(div.firstChild);
										}
										if(Ext.isEmpty(btn.querySelector('form'))){
											btn.appendChild(frag);
										}
										var iframe = document.getElementById('ProductDemand.hideFileTarget');
										 if (iframe.attachEvent){	//ie
						                     iframe.attachEvent('onload',function(){
						                    	 newCard.down('cTable').getStore().load();
						                     })
							             }else{
							                 iframe.onload = function() { 
							                	 newCard.down('cTable').getStore().load(); 
							                } 
							             } 
										
									})
									task.delay(600);
									var button = tabPanel.up().down('toolbar[dock=bottom] button[text=保存]');
									if(!Ext.isEmpty(button)){
										button.setText('导入');
									}
								}
							}
						}
			})],
		dockedItems : [ {
							xtype : 'toolbar',
							dock : 'bottom',
							ui: 'footer',
							layout: {
							    pack: 'end'
							},
							items:[{
								text:'保存',
								minWidth:60,
								minHeight:20,
								handler:function(){
									var me = this;
									var text = this.text;
									var form = this.up('window').down('form');//.getForm();
									var cTable = this.up('window').down('form').down('cTable');
									var Customer = {};// = form.getValues();
									Customer.fid = form.down('textfield[name=fid]').getValue();
									Customer.fname = form.down('textfield[name=fname]').getValue();//Customer.fname[0]; 
									Customer.fphone =form.down('textfield[name=fphone]').getValue();//Customer.fphone[0]; 
									Customer.flinkman =form.down('textfield[name=flinkman]').getValue();//Customer.flinkman[0]; 
									Customer.ffax =form.down('textfield[name=ffax]').getValue();//Customer.flinkman[0]; 
									Customer.fartificialpersonphone =form.down('textfield[name=fartificialpersonphone]').getValue();//Customer.flinkman[0]; 
									Customer.fdescription =form.down('textfield[name=fdescription]').getValue();//Customer.flinkman[0]; 
									Customer.fcreatetime =form.down('textfield[name=fcreatetime]').getValue();//Customer.flinkman[0]; 
									Customer.fcreatorid =form.down('textfield[name=fcreatorid]').getValue();//Customer.flinkman[0]; 
									if(text=='保存'){//新增界面保存
										if (form.isValid()) {
											try{
												var Address = [];
												Ext.each(cTable.store.data.items,function(item){
													if(Ext.isEmpty(item.data.fname)&&Ext.isEmpty(item.data.flinkman)&&Ext.isEmpty(item.data.fphone)){
														//都为空 不保存
													}else{
														if(Ext.isEmpty(item.data.fname)||Ext.isEmpty(item.data.flinkman)||Ext.isEmpty(item.data.fphone)){
															if(item.dirty){//数据库中不存在的要求验证
																if(!Ext.isEmpty(item.data.fname)||!Ext.isEmpty(item.data.flinkman)||!Ext.isEmpty(item.data.fphone)){
																	throw "请完善地址信息！";
																}
															}else{
																Address.push(item.data);
															}
														}else{
															Address.push(item.data);
														}
													}
												})
										        form.submit({
						   							url:'saveOrUpdateCustomer.do',
						   							params:{'Customer':Ext.encode(Customer),'Address':Ext.encode(Address)},
						   							success:function(m,action){
						   								var obj = Ext.decode(action.response.responseText);
						   								if(obj.success){
						   									djsuccessmsg(obj.msg);
						   									Ext.getCmp(me.up('window').parent).store.load();
						   									me.up('window').close();
						   								}else{
						   									Ext.Msg.alert('错误',obj.msg);
						   								}
						   							}
						   						});
											}catch(e){
												Ext.Msg.alert('提示',e);
											}
									    }
									}else if(text=='导入'){//导入客户
										
										  Ext.Ajax.request({
						   							url:'saveUploadMyCustomerExcelData.do',
						   							success:function(response){
						   								var obj = Ext.decode(response.responseText);
						   								if(obj.success){
//						   									djsuccessmsg(obj.msg);
						   									Ext.getCmp(me.up('window').parent).store.load();
						   									me.up('window').close();
						   									Ext.Msg.alert('提示',obj.msg);
						   								}else{
						   									Ext.Msg.alert('错误',obj.msg);
						   								}
						   							}
						   					});
									}
								}
								},{
								text:'关闭',
								minWidth:60,
								minHeight:20,
								handler:function(){
									this.up('window').close();
								}
							}]
						}]
		});
		me.callParent(arguments);
	}
})
