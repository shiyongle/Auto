Ext.apply(Ext.QuickTips.getQuickTip(),{
	dismissDelay : 0
}); 

Ext.require("DJ.tools.common.MyCommonToolsZ");
Ext.require("Ext.ux.form.MyDateTimeSearchBox");
Ext.require(["DJ.tools.fieldRel.MyFieldRelTools",
		"Ext.ux.grid.MySimpleGridContextMenu","Ext.ux.form.DoubleDateField"]);



Ext.define('DJ.order.Deliver.DeliversBoardList', {
			extend : 'Ext.c.GridPanel',
			
			alias : 'widget.deliversboardlist',
			
			title : "纸板订单",
			id : 'DJ.order.Deliver.DeliversBoardList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'selectBoardDeliverapplyCustsMV.do',
			Delurl : "DelBoardDeliverapplyList.do",
			EditUI : "DJ.order.Deliver.singleBoardDeliverapplyEdit",
			exporturl:"deliverapplytoExcel.do",//导出为EXCEL方法
			statics : {
				DELIVERS_STATE : ["已创建", "已接收",  "已分配", "已排产","部分发货", "全部发货"]
			},
			mixins : ['DJ.tools.grid.mixer.MyGridSearchMixer'],
			
		/*	listeners: {
				afterrender: function(){
					document.getElementById(this.id+'-body').style.overflow = 'hidden';
				}
			},*/
			
			onload : function() {
				
				var me = this;
				
				
				Ext.getCmp('DJ.order.Deliver.DeliversBoardList.addbutton').setText("下单").hide();
				Ext.getCmp('DJ.order.Deliver.DeliversBoardList.viewbutton').hide();
				Ext.getCmp('DJ.order.Deliver.DeliversBoardList.querybutton').hide();
				
				Ext.getCmp('DJ.order.Deliver.DeliversBoardList.exportbutton').hide();

				this.down('toolbar').down('combo').setValue('');
				me.down('toolbar').add(0,{iconCls : 'addnew',text:'下单',height:30,handler:function(){
					Ext.create('DJ.order.Deliver.MainDeliversBoardEdit').show();
					constrainedWin.parent = me.id;
					constrainedWin.editstate="add";
					constrainedWin.show();
					var mask = Ext.query('div[class=x-mask]');
					mask[mask.length-1].setAttribute('style','display:none');
//					mask[mask.length-1].remove();//阴影部分隐藏
				}})
				
				},
			Action_BeforeAddButtonClick : function(EditUI) {
				// 新增界面弹出前事件
			},
			Action_AfterAddButtonClick : function(EditUI) {
			},
			Action_BeforeEditButtonClick : function(EditUI) {
				// 修改界面弹出前事件
				var grid = Ext.getCmp("DJ.order.Deliver.DeliversBoardList");
				var record = grid.getSelectionModel().getSelection(),
					state;
				for ( var i = 0; i < record.length; i++) {
					state = record[i].get("fstate");
					if(state !='0'){
						 throw "已生成订单，不允许修改!";
					}
				}
			},
			doBeforeGridSearchAction: function(filters, maskA,indexAll){
				//查询值
				var me = this,
					store = me.getStore(),
					query = Ext.String.trim(me.down('[itemId=query]').getValue());
				if(/^\d+\.?\d*\*\d+\.?\d*(\*\d+\.?\d*)?$/.test(query)){
					query = query.replace(/\*/g,'___=');
				}
				query = Ext.encode(query);
				store.getProxy().extraParams.query = query.substring(1,query.length-1);
				//配送时间
				var dateValues = me.down('[itemId=farrivetime]').getSubmitValues();
				if(dateValues){
					filters.push({
						myfilterfield : "farrivetime",
						CompareType : ">=",
						type : "string",
						value : dateValues[0]
					});
					if(indexAll !=0){
						maskA.push(" and ");
					}
					maskA.push(" #" + indexAll++ + " ");
					filters.push({
						myfilterfield : "farrivetime",
						CompareType : "<=",
						type : "string",
						value : dateValues[1]
					});
					maskA.push(" and #" + indexAll++ + " ");
				}
				//创建时间
				var dateValues = me.down('[itemId=fcreatetime]').getSubmitValues();
				if(dateValues){
					filters.push({
						myfilterfield : "fcreatetime",
						CompareType : ">=",
						type : "string",
						value : dateValues[0]
					});
					if(indexAll !=0){
						maskA.push(" and ");
					}
					maskA.push(" #" + indexAll++ + " ");
					filters.push({
						myfilterfield : "fcreatetime",
						CompareType : "<=",
						type : "string",
						value : dateValues[1]
					});
					maskA.push(" and #" + indexAll++ + " ");
				}
			},
			/*Action_EditButtonClick : function(c1) {
				var c2 = c1.getSelectionModel().getSelection();
				if (c2.length == 0) {
					throw '请先选择您要操作的行!';
				};
				var c3 = Ext.create(c1.EditUI);
				c3.generateHformula();
				c3.generateVformula();
				c3.loadfields(c2[0].get('fid'));
				c3.seteditstate('edit');
				c3.setparent(c1.id);
				c3.show()
			},*/
			/*Action_EditButtonClick : function(c1) {
				var c2 = c1.getSelectionModel().getSelection();
				if (c2.length == 0) {
					throw "请选中记录进行操作!";
				};
				
				var fidcls ="";
				for (var i = 0; i < c2.length; i++) {
					fidcls += c2[i].get("fid");
					if (i < c2.length - 1) {
						fidcls = fidcls + ","
					}
				};
				var c3 = Ext.create(c1.EditUI);
				c3.seteditstate("add");
				c3.setparent(c1.id);
				c3.show()
				var cctable=Ext.ComponentQuery.query("cTable", c3);
				  cctable[0].store.on("beforeload", function(store, options) {
	    				Ext.apply(store.proxy.extraParams, 
	    				{
	    					fidcls:fidcls});
				  	});
				  cctable[0].store.load();
			},*/
			Action_AfterEditButtonClick : function(EditUI) {
				// 修改界面弹出后事件
//				Ext.getCmp("DJ.order.Deliver.DeliversCustEdit.FNUMBER").setReadOnly(true);
//				Ext.getCmp("DJ.order.Deliver.DeliversCustEdit.fcharacter").setReadOnly(true);

			},
			Action_BeforeDelButtonClick : function(me, record) {
				// 删除前事件
				var grid = Ext.getCmp("DJ.order.Deliver.DeliversBoardList");
				var record = grid.getSelectionModel().getSelection();
				for ( var i = 0; i < record.length; i++) {
					if(record[i].get("fstate")!='0'){					
						 throw "已生成订单,不允许删除!";
					}
				}
			},
			Action_AfterDelButtonClick : function(me, record) {
				// 删除后事件
			}
			,
			custbar : [{

				xtype : 'mysimplesearchercombobox',
				width: 90,
				filterMode : true,
				fields : [

				{

					displayName : '已下单',//已创建
					trueValue : '0',
					field : 'fstate'

				}, {

					displayName : '已接收',//已排产  2:订单已确认 3已分配
					trueValue : '2,3',
					field : 'fstate'

				}, {

					displayName : '已入库',
					trueValue : '4',
					field : 'fstate'
				}, {

					displayName : '已发货',//部分发货+全部发货
					trueValue : '5,6',
					field : 'fstate'

				}, {

					displayName : '发货差数',//部分发货
					trueValue : '5',
					field : 'fstate'
				},{
					displayName : '作废',
					trueValue : '8',
					field : 'fstate'
				}

				]

			},'|',{
						xtype: 'textfield',
						width : 150,
						itemId: 'query',
						enableKeyEvents: true,
						margin: 5,
						listeners: {
							render:function(me){
								Ext.tip.QuickTipManager.register({
								    target : me.id,
								    text : '可根据客户标签、申请单号、材料、制造商名称、数量、规格或特殊要求查询',
								    dismissDelay : 10000 // Hide after 10 seconds hover
								});
							},
							keyup: function(me,e){
								if(e.getKey()==Ext.EventObject.ENTER){
									this.handleSelect();
								}
							},
							change : function(me,newValue,oldValue){
								if(newValue === '' || newValue === null){
									this.handleSelect();
								}
							}
						},
						handleSelect: function(){
							this.up('grid').doGridSearchAction();
						}
					},{
						text : '查询',
						handler : function(){
							this.previousSibling().handleSelect();
						}
					},"|",{
						xtype: 'doubledatefield',
						fieldLabel: '创建时间',
						itemId: 'fcreatetime',
						width: 250,
						labelWidth: 55,
						listeners: {
							change: function(me,val){
								this.up('grid').doGridSearchAction();
							}
						}
					},"|",{
				text:'设为常用',
//				hidden:true,
				handler:function(ftype){
					var type = this.up('').ftype==undefined?1:this.up('').ftype;
					this.up('').ftype  = undefined;
					var fids ='',me = this;
					var record = me.up('grid').getSelectionModel().getSelection();
					if(type!=0){
						if(record.length!=1){
							Ext.Msg.alert('提示','请选择1条数据！');
							return;
						}
					}
					for(var i = 0;i<record.length;i++){
						fids += record[i].get('fid');
						if(i<record.length-1){
							fids += ',';
						}
					}
					Ext.Ajax.request({
						url:'setCommonorder.do',
						params:{type:type,fids:fids},
						success: function(response, opts) {
					        var obj = Ext.decode(response.responseText);
					        if(obj.success===true){
					        	me.up('grid').getStore().loadPage(1);
					        	if(type!=0){//设置常用订单时显示
						        	var messgebox = Ext.Msg.prompt('<center>常用订单名称命名</center>', '', function(btn, text){
									    if (btn == 'ok'){
									    	Ext.Ajax.request({
									    		url:'setCommonBoardOrderName.do',
									    		params:{fid:fids,name:text},
									    		success:function(response, opts){
									    			var o = Ext.decode(response.responseText);
									    			  if(o.success===true){
									    			  		djsuccessmsg(o.msg);
									    			  }else{
									    			  		Ext.Msg.alert('提示',obj.msg);
									    			  }
									    		}
									    	})
									    }
									});
									var table = Ext.getDom(messgebox.down('textfield').id);
									var lable = table.getElementsByTagName('td')[0];//获取lable的td
									lable.setAttribute('style','');//去除td 的隐藏样式
									lable.setAttribute('width',50);
									lable.textContent='名称：';
					        	}
					        }else{
					        	Ext.Msg.alert('提示',obj.msg);
					        }
					        
					    }
					})
				}
			},{
				text:'取消常用',
//				hidden:true,
				handler:function(){
					this.up().ftype = 0;
					this.previousSibling('button[text=设为常用]').handler();
				}
			},{
				
				xtype : 'myexcelexportercuscfgbutton',
				condition : ["flabel","fsuppliername","fnumber", "fmaterialname", "fboxmodel", "fboxlength", "fboxwidth", "fboxheight", "fmateriallength", "fmaterialwidth", "fstavetype", "fvline", "fhline", "famountpiece", "fseries", "farrivetime","fdescription"],
				mode : 3
				
			},{
				text: '复制',
//				height: 30,
				handler: function(){
					var me = this;
					var grid = me.up('grid'),
						records = grid.getSelectionModel().getSelection();
					if(records.length != 1){
						Ext.Msg.alert('提示','请选择一行进行操作');
						return;
					}
					var win = Ext.create(grid.EditUI);
					win.loadfields(records[0].get('fid'));
					win.seteditstate('copy');
					win.setparent(grid.id);
					win.show();
				}
			},{
				text: '产品档案',
				handler: function(){
					var me = this,
						grid = me.up('grid'),
						records = grid.getSelectionModel().getSelection();
					if(records.length != 1){
						Ext.Msg.alert('提示','请选择一行进行操作');
						return;
					}
					Ext.Msg.show({
						title: '产品档案',
//		                msg: '请选择您的操作',
		                buttons: Ext.Msg.YESNO,
		                buttonText: {yes: '新增档案', no: '管理档案'},
		                callback: function(action){
		                	var pWin;
		                	if(action == 'yes'){
		                		pWin = Ext.create('DJ.System.supplier.BoardRelateCustPdt');
		                		pWin.show();
		                		pWin.down("textfield[name=fboardid]").setValue(records[0].get('fid'));
							}else{
								pWin = Ext.create('DJ.order.Deliver.DeliversBoardSupCusProductEdit');
								pWin.fboardid = records[0].get('fid');
								pWin.show();
							}
						}
					});
				}
			},
			
			(function() {

		if (!Ext.isIE) {

			return Ext.create("Ext.ux.grid.print.MySimpleGridPrinterComponent");

		} else {

			return '|';

		}

	})(),
			
		{
				xtype: 'doubledatefield',
				fieldLabel: '配送时间',
				itemId: 'farrivetime',
				width: 250,
				labelWidth: 55,
				listeners: {
					change: function(me,val){
						this.up('grid').doGridSearchAction();
					}
				}
			}],
			relevanceByName : function(){
			var store = this.getStore();
			var valstate = this.down('toolbar').getComponent('statecombo').getValue();
			var valString = this.down('toolbar').getComponent('textfield').getValue();
			store.setDefaultfilter([{
				myfilterfield : "fstate",
				CompareType : " like ",
				type : "string",
				value : valstate
			},{
				myfilterfield : "fnumber",
				CompareType : " like ",
				type : "string",
				value : valString
			},{
				myfilterfield : "_materialname",
				CompareType : " like ",
				type : "string",
				value : valString
			},{
				myfilterfield : "_suppliername",
				CompareType : " like ",
				type : "string",
				value : valString
			},{
				myfilterfield : "fstavetype",
				CompareType : " like ",
				type : "string",
				value : valString
				},{
				myfilterfield : "fseries",
				CompareType : " like ",
				type : "string",
				value : valString
				},{
				myfilterfield : "flinkman",
				CompareType : " like ",
				type : "string",
				value : valString
			},{
				myfilterfield : "fdescription",
				CompareType : " like ",
				type : "string",
				value : valString
			}             
			]);
			store.setDefaultmaskstring("#0 and (#1 or #2 or #3 or #4 or #5 or #6 or #7 )");
////			store.loadPage(1);
		}
			,fields : [{
						name : 'fid'
					}, {
						name : 'faddress',
						myfilterfield : 'faddress',
						myfiltername : '配送地址',
						myfilterable : true
					}, {
						name : 'fnumber',
						myfilterfield : 'fnumber',
						myfiltername : '申请单号',
						myfilterable : true,
						type : "String",
						sortDir : "DESC"
					}, {
						name : 'fcustomerid'
					}, {
						name : 'fcustname',
						myfilterfield : '_custname',
						myfiltername : '客户名称',
						myfilterable : true
					}, {
						name : 'flinkman',
						myfilterfield : 'flinkman',
						myfiltername : '联系人',
						myfilterable : true
					}, {
						name : 'flinkphone',
						myfilterfield : 'flinkphone',
						myfiltername : '联系电话',
						myfilterable : true
					}, {
						name : 'famount'
					}, {
						name : 'farrivetime',
						myfilterfield : 'farrivetime',
						myfiltername : '配送时间',
						myfilterable : true
					},{
						name: 'flabel'
					}, {
						name : 'fcreator'
					}, {
						name : 'fcreatetime'
					}, {
						name : 'fstate'
					},{
						name : 'fsuppliername'
					},{
						name: 'fstockinqty'
					},{
						name : 'stateinfo'
					},'fwerkname','fiscommonorder','fcreatetime','foutQty','fmaterialname','fboxmodel','fboxlength','fboxwidth','fboxheight','fmateriallength','fmaterialwidth','fstavetype','fvline','fhline','famountpiece','foutQtypiece','fseries','fouttime','fdescription','fordesource'
					
					],
			columns : {items:[Ext.create('DJ.Base.Grid.GridRowNum',{
						header:'序号'
					}),{
						header: '客户标签',
						width: 100,
						dataIndex: 'flabel',
						sortable: true
					},{
						'header' : '常用',
						width : 30,
						'dataIndex' : 'fiscommonorder',
//						hidden : true,
//						hideable : false,
						sortable : true,
						renderer:function(value){
							return value=='true'?'是':'否';
						}
					},{
						'header' : '申请单号',
						width : 100,
						'dataIndex' : 'fnumber',
						sortable : true
					}, 
						{
						'header' : '客户名称',
						width : 140,
						'dataIndex' : 'fcustname',
						sortable : true,
						hidden: true
					},{
						'header' : '制造商',
						width : 80,
						'dataIndex' : 'fsuppliername',
						sortable : true,
						hidden: true
					},  {
						'header' : '订单状态',
						'dataIndex' : 'fstate',
						width : 60,
						sortable : true
						,
						renderer : function(value) {
							if (value == 0) {
								return '已下单';//已创建
							}else if(value == 1 || value ==2 || value == 3){
								return '已接收';//已排产
							}else if(value == 4){
								return '已入库';
							}else if(value == 5){//部分发货
								return '发货差数';
							}else if(value == 6){
								return '已发货';
						    }else if(value == 7){
								return '暂存';
						    }else if(value==8){
						    	return '作废';
						    }else {
								return '';
							}
						}
					},{
						'header' : '材料',
						width : 120,
						'dataIndex' : 'fmaterialname',
						sortable : true
					},{
						'header' : '箱型',
						width : 40,
						'dataIndex' : 'fboxmodel',
						draggable : false,
						sortable : true,
						renderer : function(value) {
							if (value == 1) {
								return '普通';
							}else if(value == 2){
								return '全包';
							}else if(value == 3){
								return '半包';
						    }else if(value == 4){
								return '有底无盖';
						    }else if(value == 5){
								return '有盖无底';
						    }else if(value == 6){
								return '围框';
						    }else if(value == 7){
								return '天地盖';
						    }else if(value == 8){
								return '立体箱';
						    }else if(value == 0){
						    	return '其它';
						    }
						}
					},{
						'header' : '纸箱规格(CM)',
						hideable: false,
						draggable : false,
						
						stateId : 'cartonSize',
						
						 columns: [{
						 'header' : '长',
						'dataIndex' : 'fboxlength',
						hideable: false,
						draggable : false,
						width : 50,
						sortable : true
						 },{
						 'header' : '宽',
						'dataIndex' : 'fboxwidth',
						width : 50,
						hideable: false,
						draggable : false,
						sortable : true
						 },{
						 'header' : '高',
						'dataIndex' : 'fboxheight',
						hideable: false,
						draggable : false,
						width : 50,
						sortable : true
						 }]
						 	
					},{
						'header' : '下料规格(CM)',
						hideable: false,
						draggable : false,
						
						stateId : 'theMaterialSpecifications',
						
						 columns: [{
						 'header' : '总长',
						'dataIndex' : 'fmateriallength',
						width : 60,
						hideable: false,
						draggable : false,
						sortable : true
						 },{
						 'header' : '总高',
						 'dataIndex' : 'fmaterialwidth',
						 width : 60,
						 hideable: false,
						draggable : false,
						 sortable : true
						 }]
					},{
						'header' : '配送数量',
						hideable: false,
						draggable : false,
						
						
						stateId : 'quantityofthestore',
						
						 columns: [{
						 'header' : '只',
						'dataIndex' : 'famount',
						hideable: false,
						draggable : false,
						width : 50,
						sortable : true
						 },{
						 'header' : '片',
						 'dataIndex' : 'famountpiece',
						 hideable: false,
						draggable : false,
						 width : 50,
						 sortable : true
						 }]
					},{
						'header' : '实际配送数量(片)',
//						 columns: [{
//						 'header' : '只',
						width : 120,
						'dataIndex' : 'foutQty',
						sortable : true
//						 },{
//						 'header' : '片',
//						 'dataIndex' : 'foutQtypiece',
//						 width : 50,
//						 sortable : true
//						 }]
					},{
						'header' : '压线方式',
						'dataIndex' : 'fstavetype',
						width : 60,
						sortable : true
//						renderer : function(value) {
//							if (value == 1) {
//								return '纵向内压';
//							}else if(value == 2){
//								return '纵向平压';
//							}else if(value == 3){
//								return '纵向外压';
//							}else if(value == 4){
//								return '横压';
//						    }else {
//								return '不压线';
//							}
//						}
					},{
						'header' : '压线公式',
						hideable: false,
						draggable : false,
						
						stateId : 'linePressingFormula',
						
						 columns: [{
						 'header' : '纵向公式',
						'dataIndex' : 'fvline',
						hideable: false,
						draggable : false,
						width : 100,
						sortable : true
						 },{
						 'header' : '横向公式',
						 'dataIndex' : 'fhline',
						 hideable: false,
						draggable : false,
						 width : 100,
						 sortable : true
						 }]
					},{
						'header' : '成型方式',
						'dataIndex' : 'fseries',
						width : 80,
						sortable : true
//						renderer : function(value) {
//							if (value == 1) {
//								return '一片';
//							}else if(value == 2){
//								return '二片';
//							}else if(value == 4){
//								return '四片';
//						    }else {
//								return '';
//							}
//						}
					},{
						'header' : '配送时间',
						'dataIndex' : 'farrivetime',
						width : 120,
						sortable : true
					},{
						'header' : '实际配送时间',
						'dataIndex' : 'fouttime',
						width : 120,
						sortable : true
					},{
						'header' : '配送地址',
						'dataIndex' : 'faddress',
						width : 300,
						sortable : true
					}, {
						'header' : '联系人',
						'dataIndex' : 'flinkman',
						width : 60,
						sortable : true
					}, {
						'header' : '联系电话',
						'dataIndex' : 'flinkphone',
						sortable : true
					},{
						'header' : '创建时间',
						'dataIndex' : 'fcreatetime',
						width : 140,
						sortable : true
						},{
							'header' : '入库数量/片',
							'dataIndex' : 'fstockinqty',
							width : 140,
							sortable : true
						},{
						'header' : '特殊要求',
						'dataIndex' : 'fdescription',
						width : 140,
						sortable : true
					},{
						'header' : '来源订单类型',
						'dataIndex' : 'fordesource',
						renderer:function(val){
							switch(val){
								case 'ios':return "苹果"
								case 'android':return "安卓"
								default:return "网页"
							}
						},
						sortable : true
					},{
						text:'备注',
						dataIndex:'fwerkname',
						width : 140,
						sortable : true
					}
					
					],

	defaults: {
		renderer : DJ.tools.fieldRel.MyFieldRelTools.showEmptyWhenNullNoZERO
    }
					},
					selModel:Ext.create('Ext.selection.CheckboxModel',{
					
						stateId : 'CheckboxModel'
						
					})
		})
		
		


		