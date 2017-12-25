
			ws = null;
		    function startServer() {
		        var url = "ws://192.168.2.13:8080/vmi/echo.ws";
		        if ('WebSocket' in window) {
		            ws = new WebSocket(url);
		        } else if ('MozWebSocket' in window) {
		            ws = new MozWebSocket(url);
		        } else {
		            alert('Your browser Unsupported WebSocket!');
		            return;
		        }
		
		        ws.onopen = function() {
		            document.getElementById("content").innerHTML += 'websocket open! Welcome!<br />';
		        };
		        ws.onmessage = function(event) {
		            document.getElementById("content").innerHTML += event.data + '<br />';
		        };
		        ws.onclose = function() {
		            document.getElementById("content").innerHTML += 'websocket closed! Byebye!<br />';
		        };
		    }

requires : ['Ext.ux.form.MyMixedSearchBox'],
Ext.define('DJ.order.Deliver.MystockList',
		{
			extend : 'Ext.panel.Panel',
			id:'DJ.order.Deliver.MystockList',
//			requires : [ 'Ext.grid.Panel', 'Ext.grid.column.Number',
//					'Ext.grid.column.Date', 'Ext.grid.column.Boolean',
//					'Ext.grid.View' ],

			// height: 385,
			// width: 496,
			layout : 'border',
			title : '我的备货',
			closable : true,
			autoScroll : false,
			listeners:{
				afterrender:function(){
					Ext.getCmp('DJ.order.Deliver.MystockList').findMystock();
					startServer();
				}
			},
			onload:function(){
//				Ext.getCmp('DJ.order.Deliver.MystockList').findMystock();
			},
			switchOrderType: function(orderType){
				var me = this,bt;
				switch (orderType) {
					case "single" :
						var record = Ext.getCmp('DJ.order.Deliver.MystockListGrid').getSelectionModel().getSelection();
						if(record.length<1){
							Ext.MessageBox.alert('错误','请选择一条数据!');
							return;
						}
						bt = Ext.create("DJ.order.Deliver.DeliversCustEdit");
						bt.seteditstate('edit');
						bt._relatedPanel = me;
						bt.down('textfield[name=ftraitid]').setValue(record[0].get('fcharacterid'));
						bt.down('textfield[name=fordernumber]').setValue(record[0].get('fpcmordernumber'));
						bt.down('textfield[name=fordernumber]').setReadOnly(true);
						bt.down('cCombobox[name=fcusproductid]').setmyvalue("\"fid\":\""
								+ record[0].get('fcustproductid') + "\",\"fname\":\""
								+ record[0].get('fcustproductname') + "\"");
						bt.down('cCombobox[name=fcusproductid]').setReadOnly(true);
							bt.down('cCombobox[name=fcustomerid]').setmyvalue("\"fid\":\""
								+ record[0].get('fcustomerid') + "\",\"fname\":\""
								+ record[0].get('fname') + "\"");
							bt.down('combobox[name=fsupplierid]').setValue(record[0].get('fsupplierid'));
	//						bt.down('combobox[name=fsupplierid]').setReadOnly(true);
							bt.down('textfield[name=famount]').setValue(record[0].get('fplanamount'));
							bt.down('textfield[fieldLabel=可用库存]').setValue(record[0].get('fplanamount'));
							bt.down('textfield[fieldLabel=可用库存]').setReadOnly(true);
							bt.down('textfield[name=fcharacter]').setValue(record[0].get('fcharactername'));
							bt.down('textfield[name=fcharacter]').setReadOnly(true);
							bt.down('textfield[name=fdescription]').setValue(record[0].get('fremark'));
							bt.down('textfield[name=fdescription]').setReadOnly(true);
						bt.show();
						break;
	
					case "multi" :
						me.CustDeliverapplyEditWin();
						break;
	
				}
			},
			initComponent : function() {
				var me = this;

				Ext.applyIf(me, {
					items : [ 
					          {
						          region:'center',
						          xtype:'panel',
						          layout:'border',
						          height:'100%',
//						          split : true,
						          items:[
						                 Ext.create('DJ.order.Deliver.MystockListGrid'),
						            Ext.create("Ext.c.GridPanel", {
//						            	title:'配送信息',
//										xtype : 'gridpanel',
										id:'DJ.order.Deliver.MystockList.deliverorder',
										region : 'south',
										autoScroll:true,
										height:'40%',
//										width : '30%',
										emptyText:'<center><h1>没有数据</h1></center>',
//										split : true,
										url:'getDeliverOrderList.do',
										onload:function(){
											this.down("toolbar").setVisible(false);
										},
										selModel : Ext.create('Ext.selection.CheckboxModel'),
//										store : DJ.order.Deliver.myStore,
										// title: 'My Grid Panel',
										fields : [{name:'fid'},{name:'famount'},{name:'farrivetime',property:'farrivetime' ,direction : 'desc'}],
//								          remoteFilter : true,
										columns : [ {
											dataIndex : 'fid',
											text:'fid',
											hidden:true,
											hideable:false
										},{
											dataIndex : 'famount',
											text : '配送数量',
											align:"center",
											flex:1
										}, {
											dataIndex : 'farrivetime',
											text : '配送时间',
											flex:1
										} ]
						          })
						          ]
						          
					          }
					          ,Ext.create('DJ.order.Deliver.MystockListForm') //左边的form界面
//					          Ext.create('DJ.order.Deliver.MystockListPanel',{//中间的panel
//					        	  title:'本月与上月备货总量比较'
//					          }),
//					          Ext.create('DJ.order.Deliver.MystockListChart'), //右边的柱形图界面
					          ],
					dockedItems : [ {
						xtype : 'toolbar',
						dock : 'top',
						// ui: 'footer',
						layout : {
							pack : 'left'
						},
						items : [ '&nbsp',{
							text : '新增',
							iconCls : 'addnew',
							handler:function(){
								Ext.getCmp('DJ.order.Deliver.MystockListForm').setDisabled(false);
								Ext.getCmp('DJ.order.Deliver.MystockList.funit').setReadOnly(false);
								Ext.getCmp('DJ.order.Deliver.MystockList.fnumber').setReadOnly(true);
								var forms = Ext.getCmp('DJ.order.Deliver.MystockListForm').getForm().reset();
								Ext.getCmp('DJ.order.Deliver.MystockListGrid').getSelectionModel().deselectAll();
								Ext.getCmp('DJ.order.Deliver.MystockList.fcustproductid').setmyvalue("\"fid\":\""
										+ '' + "\",\"fname\":\""
										+ '' + "\"");
								Ext.getCmp("DJ.order.Deliver.MystockList.button7").toggle(true);
								Ext.getCmp("DJ.order.Deliver.MystockList.week").toggle(true);
							}
						}, {
							text : '删除',
							iconCls : 'delete',
							handler:function(){
								Ext.MessageBox.confirm('提示', '是否确认删除选中的内容?',function(btn, text){
									if(btn=="yes"){
										var form = Ext.getCmp('DJ.order.Deliver.MystockListForm').getForm();
										var record = Ext.getCmp('DJ.order.Deliver.MystockListGrid').getSelectionModel().getSelection();
										if(record.length<1){
											Ext.MessageBox.alert("错误","请选择一条记录！");
											return;
										}
										if(form.getValues().fordered==1){
											Ext.Msg.alert('提示','已下单的不能修改!');
											return;
										}
									
										var fids = "('";
										for(var i =0;i<record.length;i++){
											if(record[i].get('fisconsumed')==1){
												Ext.Msg.alert('提示','已消耗完毕的不能修改!');
												return;
											}
											if(record[i].get('fnumber')==null||record[i].get('fnumber')==""){
												Ext.Msg.alert('提示',"特性订单不能删除!");
												return;
											}
											fids += record[i].get("fid");
											if(i<record.length-1){
												fids += "','";
											}
										}
										fids +="')";
										Ext.Ajax.request({
											url:'delMystock.do',
											params:{
												fids:fids
											},
										  	success:function(res){
						                		var obj = Ext.decode(res.responseText);
						                		if(obj.success){
//						                			stockMount();
						                			djsuccessmsg(obj.msg);
//						                			Ext.getCmp('DJ.order.Deliver.MystockListGrid').getStore().load();
						                			var text = Ext.getCmp('DJ.order.Deliver.MystockList.text').getValue();
						                			document.getElementById('DJ.order.Deliver.MystockList.button').click();
						                		}else{
						                			Ext.Msg.alert('提示',obj.msg);
						                		}
						                		//Ext.getCmp('DJ.System.MaigaoEntryGrids').getStore().reload();
						                	}
										})
									}
								});
								
								
							}

						}, {
							text : '保存',
							iconCls : 'save',
							handler:function(){
								var form = Ext.getCmp('DJ.order.Deliver.MystockListForm').getForm();
								var record = Ext.getCmp('DJ.order.Deliver.MystockListGrid').getSelectionModel().getSelection();
								var mystock = Ext.encode(form.getValues());
								if(mystock=='{}'){
									Ext.Msg.alert('提示','已下单的不能修改!');
									return;
								}
								if(record.length>0){
									if(record[0].get('fisconsumed')==1){
										Ext.Msg.alert('提示','已消耗完毕的不能修改!');
										return;
									}
									if(record[0].get('fnumber')==null||record[0].get('fnumber')==""){
										Ext.Msg.alert('提示',"特性订单不能修改!");
										return;
									}
								}
								if(form.getValues().fordered==1){
									Ext.Msg.alert('提示','已下单的不能修改!');
									return;
								}
								if(form.getValues().fsupplierid==''){
									Ext.MessageBox.confirm('提示', '制造商未选择，是否继续保存?',function(btn, text){
										if(btn=="yes"){
											if(form.isValid()){
												
												Ext.Ajax.request({
													url:'saveOrUpdateMystock.do',
													params:{
														Mystock:mystock
													},//form.getValues(),
														//form._record.data,
												  	success:function(res){
								                		var obj = Ext.decode(res.responseText);
								                		if(obj.success){
//								                			stockMount();
								                			djsuccessmsg(obj.msg);
//								                			Ext.getCmp('DJ.order.Deliver.MystockListGrid').getStore().load();
								                			var text = Ext.getCmp('DJ.order.Deliver.MystockList.text').getValue();
								                			document.getElementById('DJ.order.Deliver.MystockList.button').click();
								                		}else{
								                			Ext.Msg.alert('提示',obj.msg);
								                		}
								                		//Ext.getCmp('DJ.System.MaigaoEntryGrids').getStore().reload();
								                	}
												})
											}else{
												Ext.Msg.alert("提示","请填写完整的数据");
											}
										}
									})
								}else{
									if(form.isValid()){
										
										Ext.Ajax.request({
											url:'saveOrUpdateMystock.do',
											params:{
												Mystock:mystock
											},//form.getValues(),
												//form._record.data,
										  	success:function(res){
						                		var obj = Ext.decode(res.responseText);
						                		if(obj.success){
//						                			stockMount();
						                			djsuccessmsg(obj.msg);
//						                			Ext.getCmp('DJ.order.Deliver.MystockListGrid').getStore().load();
						                			var text = Ext.getCmp('DJ.order.Deliver.MystockList.text').getValue();
						                			document.getElementById('DJ.order.Deliver.MystockList.button').click();
						                		}else{
						                			Ext.Msg.alert('提示',obj.msg);
						                		}
						                		//Ext.getCmp('DJ.System.MaigaoEntryGrids').getStore().reload();
						                	}
										})
									}else{
										Ext.Msg.alert("提示","请填写完整的数据");
									}
								}
								
							}

						},{
							text : '刷新',
							iconCls : 'refresh',
							handler:function(){
								var forms = Ext.getCmp('DJ.order.Deliver.MystockListForm').getForm().reset();
								Ext.getCmp('DJ.order.Deliver.MystockList.fcustproductid').setmyvalue("\"fid\":\""
										+ '' + "\",\"fname\":\""
										+ '' + "\"");
								Ext.getCmp('DJ.order.Deliver.MystockListGrid').getStore().load();
//								DJ.order.Deliver.productStore.load();
								
								Ext.getCmp("DJ.order.Deliver.MystockList.button7").toggle(true);
								Ext.getCmp("DJ.order.Deliver.MystockList.week").toggle(true);
								
							}

						},
						{
							text : '导出Excel',		//导出
							iconCls : 'excel',
							height : 30,
//							id : c1.id + '.exportbutton',
							handler : function() {
								var g = Ext.getCmp('DJ.order.Deliver.MystockListGrid');
								g.down('button[text=导出Excel]').handler();
//								window.open('exportMystockList.do','导出EXCEL')
								}
						}
						,{
							text : '批量新增',
							handler:function(){
								var win = Ext.create('DJ.order.Deliver.MyStockAddEdit');
								win.setparent('DJ.order.Deliver.MystockListGrid');
								win.seteditstate('edit');
								win.show();
							}

						},{
							text:'下单',
							handler:function(){
//								var faddressid,faddress,flinkphone,flinkman,fcharactername,clearAddress;
//									var win = Ext.create('DJ.order.Deliver.batchCustDeliverapplyEdit',{	onload:function(){
//									var grid = Ext.getCmp('DJ.order.Deliver.Deliverapplys');
//									grid.removeDocked(grid.down('toolbar'));
//									var me = this;
//									Ext.Ajax.request({
//										timeout : 60000,
//										url : "GetAddressListByuser.do",
//										success : function(response, option) {
//
//											var obj = Ext.decode(response.responseText);
//											if (obj.success == true) {
//												if (obj.data != undefined) {
////													customerFiled.setmyvalue("\"fid\":\""+ obj.data[0].fid + "\",\"fname\":\""+ obj.data[0].fname + "\"");
//													me.myobj.faddressid = obj.data[0].fid;
//													me.myobj.faddress = obj.data[0].fname;
//													me.myobj.flinkphone = obj.data[0].fphone;
//													me.myobj.flinkman = obj.data[0].flinkman;
//													me.myobj.fcharactername = obj.data[0].fcharactername;
//													me.myobj.clearAddress = 0;
//												}
//											} else {
////												Ext.MessageBox.alert('错误', obj.msg);
////												me.myobj.faddressid = "";
////												me.myobj.faddress = "";
////												me.myobj.flinkphone = "";
////												me.myobj.flinkman = "";
////												me.myobj.clearAddress = 1;
//												me.myobj={clearAddress:1};
//											}
//											
//											var store = Ext.getCmp('DJ.order.Deliver.Deliverapplys').getStore(),
//											obj = me.myobj ,
//											arr = [] ;
//											for(i=0;i<5;i++){
//												arr.push({
//													farrivetime:Ext.Date.add(new Date(new Date().setHours(14,0,0,0)),Ext.Date.DAY,5),
//													famount:0,
//													faddressid:obj.faddressid,
//													faddress:obj.faddress,
//													flinkphone:obj.flinkphone,
//													flinkman:obj.flinkman
//												});
//											}
////											store.add.apply(store,arr);
//										}
//									});
//									
//								},title:'生成配送编辑界面',selModel : Ext.create('Ext.selection.CheckboxModel')});
//									
//								
//								win.setparent('DJ.order.Deliver.MystockListGrid');
//								win.seteditstate('edit');
//								var record = Ext.getCmp('DJ.order.Deliver.MystockListGrid').getSelectionModel().getSelection();
//								var store = Ext.getCmp('DJ.order.Deliver.Deliverapplys').getStore();
//								if(record.length==0){
//									Ext.Msg.alert('提示',"请选择条数据");
//									return;
//								}
//								Ext.Ajax.request({
//									timeout : 60000,
//									url : "GetAddressListByuser.do",
//									success : function(response, option) {
//
//										var obj = Ext.decode(response.responseText);
//										if (obj.success == true) {
//											if (obj.data != undefined) {
////												customerFiled.setmyvalue("\"fid\":\""+ obj.data[0].fid + "\",\"fname\":\""+ obj.data[0].fname + "\"");
//												faddressid = obj.data[0].fid;
//												faddress = obj.data[0].fname;
//												flinkphone = obj.data[0].fphone;
//												flinkman = obj.data[0].flinkman;
//												fcharactername = obj.data[0].fcharactername;
//											}
//											for(var i =0;i<record.length;i++){
//											
//												store.add({fcusproductid:record[i].get('fcustproductid'),
//															cutpdtname:record[i].get('fcustproductname'),
//															cutpdtnumber:record[i].get('cfnumber'),
//															ftraitid:record[i].get('fcharacterid'),
//															fcharacter:record[i].get('fcharactername'),
//															fordernumber:record[i].get('fpcmordernumber'),
////															famount:0,
//															farrivetime:new Date(),
//															famount:record[i].get('fbalanceqty'),
//															faddressid:faddressid,
//															faddress:faddress,
//															flinkphone:flinkphone,
//															flinkman:flinkman,
//															clearAddress : 0,
//															balance : record[i].get('fbalanceqty'),
//															mystock:record[i].get('fid')
//															});
//											}
//											
//											win.show();
//										}else{
//											for(var i =0;i<record.length;i++){
//												store.add({fcusproductid:record[i].get('fcustproductid'),
//															cutpdtname:record[i].get('fcustproductname'),
//															cutpdtnumber:record[i].get('cfnumber'),
//															ftraitid:record[i].get('fcharacterid'),
//															fcharacter:record[i].get('fcharactername'),
//															fordernumber:record[i].get('fpcmordernumber'),
////															famount:0,
//															farrivetime:new Date(),
//															famount:record[i].get('fbalanceqty'),
//															faddressid:faddressid,
//															faddress:faddress,
//															flinkphone:flinkphone,
//															flinkman:flinkman,
//															clearAddress : 0,
//															balance : record[i].get('fbalanceqty'),
//															mystock:record[i].get('fid')
//															});
//											}
//											
//											win.show();
//										}
//									}
//								})
							


							var button = this;
							
							Ext.Ajax.request({
								timeout : 6000,
								
								url : "gainOrderWayCfg.do",
								success : function(response, option) {

									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {

										var orderType = obj.data[0].fvalue;

										
										
										Ext.getCmp('DJ.order.Deliver.MystockList').switchOrderType(orderType);
										

									} else {
										Ext.MessageBox.alert('错误', obj.msg);
									}

								}
							});

						
							}
						},'->','->',
//						{
//							xtype : 'combobox',
//							editable:false,
//							id:'DJ.order.Deliver.MystockList.name',
//							store : Ext.create('Ext.data.Store', {
//								fields : [ 'name', 'value' ],
//								data : [ {
//									"name" : "包装物名称",
//									"value" : "a.fcustproductname"
//								}, {
//									"name" : "包装物编码",
//									"value" : "a.cfnumber"
//								} ]
//							}),
//							value : 'a.fcustproductname',
//							displayField : 'name',
//							valueField : 'value'
//						}, 
						{
							xtype : 'textfield',
							id:'DJ.order.Deliver.MystockList.text',
							emptyText:'按回车键查询...',
							itemId:'text',
							enableKeyEvents:true,
							listeners:{
								keypress:function(me,e){
											if(e.getKey()==13){
												document.getElementById('DJ.order.Deliver.MystockList.button').click();
											}
									},
									render:function(){Ext.tip.QuickTipManager.register({
										 target: 'DJ.order.Deliver.MystockList.text',
										    text: '可输入:客户名称，包装物名称，包装物编码',
//										    width: 100,
										    dismissDelay: 10000 // Hide after 10 seconds hover
										});
									}
									   
							}
						}, {
							text : '查询',
							id:'DJ.order.Deliver.MystockList.button',
							handler:function(){
//								var name = Ext.getCmp('DJ.order.Deliver.MystockList.name').getValue();
								var text = Ext.getCmp('DJ.order.Deliver.MystockList.text').getValue();
								var store = Ext.getCmp('DJ.order.Deliver.MystockListGrid').getStore();
//								store.loadPage(1,{params:{name:name,value:text}});
								store.setDefaultfilter([{
									myfilterfield : 'a.fcustproductname',
									CompareType : "like",
									type : "string",
									value : text
								},{
									myfilterfield : 'a.cfnumber',
									CompareType : "like",
									type : "string",
									value : text
								},{
									myfilterfield : 'a.cname',
									CompareType : "like",
									type : "string",
									value : text
								}]);
								store.setDefaultmaskstring(" #0 or #1 or #2");
								store.loadPage(1);
								
							}

						}
						,'->', {
							xtype : 'combobox',
							editable:false,
							id:'DJ.order.Deliver.MystockList.fisconsumed',
							store : Ext.create('Ext.data.Store', {
								fields : [ 'name', 'value' ],
								data : [ {
									"name" : "已创建",
									"value" : "0"
								}, {
									"name" : "已安排生产",
									"value" : "1"
								}, {
									"name" : "已结束备货",
									"value" : "2"
								}, {
									"name" : "显示全部",
									"value" : '3'
								} ]
							}),
							value : '1',
							displayField : 'name',
							valueField : 'value',
							listeners:{
								select:function(com,record,item,index, e, eOpts ){
									Ext.getCmp('DJ.order.Deliver.MystockList').findMystock();
//	  								store.loadPage(1,{params:{name:'m.fisconsumed',value:name}});
									
								}
							}
						},'-' ]
					} ],CustDeliverapplyEditWin:function(){
						var faddressid,faddress,flinkphone,flinkman,fcharactername,clearAddress;
						var win = Ext.create('DJ.order.Deliver.batchCustDeliverapplyEdit',{	onload:function(){
						var grid = Ext.getCmp('DJ.order.Deliver.Deliverapplys');
						grid.removeDocked(grid.down('toolbar'));
						var me = this;
						Ext.Ajax.request({
							timeout : 60000,
							url : "GetAddressListByuser.do",
							success : function(response, option) {

								var obj = Ext.decode(response.responseText);
								if (obj.success == true) {
									if (obj.data != undefined) {
//										customerFiled.setmyvalue("\"fid\":\""+ obj.data[0].fid + "\",\"fname\":\""+ obj.data[0].fname + "\"");
										me.myobj.faddressid = obj.data[0].fid;
										me.myobj.faddress = obj.data[0].fname;
										me.myobj.flinkphone = obj.data[0].fphone;
										me.myobj.flinkman = obj.data[0].flinkman;
										me.myobj.fcharactername = obj.data[0].fcharactername;
										me.myobj.clearAddress = 0;
									}
								} else {
//									Ext.MessageBox.alert('错误', obj.msg);
//									me.myobj.faddressid = "";
//									me.myobj.faddress = "";
//									me.myobj.flinkphone = "";
//									me.myobj.flinkman = "";
//									me.myobj.clearAddress = 1;
									me.myobj={clearAddress:1};
								}
								
								var store = Ext.getCmp('DJ.order.Deliver.Deliverapplys').getStore(),
								obj = me.myobj ,
								arr = [] ;
								for(i=0;i<5;i++){
									arr.push({
										farrivetime:Ext.Date.add(new Date(new Date().setHours(14,0,0,0)),Ext.Date.DAY,5),
										famount:0,
										faddressid:obj.faddressid,
										faddress:obj.faddress,
										flinkphone:obj.flinkphone,
										flinkman:obj.flinkman
									});
								}
//								store.add.apply(store,arr);
							}
						});
						
					},title:'生成配送编辑界面',selModel : Ext.create('Ext.selection.CheckboxModel')});
						
					win._relatedPanel = me;
					win.setparent('DJ.order.Deliver.MystockListGrid');
					win.seteditstate('edit');
					var record = Ext.getCmp('DJ.order.Deliver.MystockListGrid').getSelectionModel().getSelection();
					var store = Ext.getCmp('DJ.order.Deliver.Deliverapplys').getStore();
					if(record.length==0){
						Ext.Msg.alert('提示',"请选择条数据");
						return;
					}
					Ext.Ajax.request({
						timeout : 60000,
						url : "GetAddressListByuser.do",
						success : function(response, option) {

							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
								if (obj.data != undefined) {
//									customerFiled.setmyvalue("\"fid\":\""+ obj.data[0].fid + "\",\"fname\":\""+ obj.data[0].fname + "\"");
									faddressid = obj.data[0].fid;
									faddress = obj.data[0].fname;
									flinkphone = obj.data[0].fphone;
									flinkman = obj.data[0].flinkman;
									fcharactername = obj.data[0].fcharactername;
								}
								for(var i =0;i<record.length;i++){
								
									store.add({fcusproductid:record[i].get('fcustproductid'),
												cutpdtname:record[i].get('fcustproductname'),
												cutpdtnumber:record[i].get('cfnumber'),
												ftraitid:record[i].get('fcharacterid'),
												fcharacter:record[i].get('fcharactername'),
												fordernumber:record[i].get('fpcmordernumber'),
//												famount:0,
												farrivetime:new Date(),
												famount:record[i].get('fbalanceqty'),
												faddressid:faddressid,
												faddress:faddress,
												flinkphone:flinkphone,
												flinkman:flinkman,
												clearAddress : 0,
												balance : record[i].get('fbalanceqty'),
												mystock:record[i].get('fid'),
												fdescription:record[i].get('fremark')
												});
								}
								
								win.show();
							}else{
								for(var i =0;i<record.length;i++){
									store.add({fcusproductid:record[i].get('fcustproductid'),
												cutpdtname:record[i].get('fcustproductname'),
												cutpdtnumber:record[i].get('cfnumber'),
												ftraitid:record[i].get('fcharacterid'),
												fcharacter:record[i].get('fcharactername'),
												fordernumber:record[i].get('fpcmordernumber'),
//												famount:0,
												farrivetime:new Date(),
												famount:record[i].get('fbalanceqty'),
												faddressid:faddressid,
												faddress:faddress,
												flinkphone:flinkphone,
												flinkman:flinkman,
												clearAddress : 0,
												balance : record[i].get('fbalanceqty'),
												mystock:record[i].get('fid'),
												fdescription:record[i].get('fremark')
												});
								}
								
								win.show();
							}
						}
					})
					},
					findMystock:function(){
						var name = Ext.getCmp('DJ.order.Deliver.MystockList.fisconsumed').getValue();
						var store = Ext.getCmp('DJ.order.Deliver.MystockListGrid').getStore();
						if(name!=3){
							store.setDefaultfilter([{
								myfilterfield : 'a.fstate',
								CompareType : "=",
								type : "int",
								value : name
							}]);
						}else{
							store.setDefaultfilter([{
								myfilterfield : '1',
								CompareType : "=",
								type : "string",
								value : '1'
							}]);
						}
						
						store.setDefaultmaskstring(" #0 ");
						store.loadPage(1);
					}
				});

				me.callParent(arguments);
			}

		});