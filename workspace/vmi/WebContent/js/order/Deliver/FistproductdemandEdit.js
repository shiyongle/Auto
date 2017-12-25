
Ext.define('DJ.order.Deliver.productdemandfile',{
//	id:'DJ.order.Deliver.Fistproductdemand',
	ctype:'Productdemandfile',
	extend : 'Ext.c.GridPanel',
	pageSize : 50,
	alias:'widget.productdemandfile',
	closable : false,// 是否现实关闭按钮,默认为false
	url:'',
	exporturl : "",// 导出为EXCEL方法
	height:200,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	onload:function(){
	
	},

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
			xtype:'actioncolumn',
			width:100,
//			hidden:true,
//			hideable:false,
			align:'center',
			text:'操作',
			id:'DJ.order.Deliver.productdemandfile.productActions',
        items: [
//                {
//            icon: 'images/image_add.png',  
//            tooltip: '上传附件',
//            handler: function(grid, rowIndex, colIndex) {
//            	var filename = grid.getStore().getAt(rowIndex).get('fname');
//            	if(filename!=null&&filename!=""){
//            		Ext.Msg.alert('提示','请先删除附件！');
//                	return ;
//            	}
//            	var fid = grid.getStore().getAt(rowIndex).get('fid');
//            	var id = Ext.getCmp('DJ.order.Deliver.FistproductdemandEdit.fid').getValue();
//            	Ext.getCmp('DJ.order.Deliver.FistproductdemandEdit.fparentid').setValue(id);
//            	Ext.getCmp('DJ.order.Deliver.FistproductdemandEdit.state').setValue(fid);//上传唛稿分录附件
//            	document.getElementById('DJ.order.Deliver.FistproductdemandEdit.file-button-fileInputEl').click();
//            }
//        },
        {
            icon: 'images/delete.gif',
            tooltip: '删除附件',
            handler: function(grid, rowIndex, colIndex) {
                var fid = grid.getStore().getAt(rowIndex).get('fid');
                var fpath = grid.getStore().getAt(rowIndex).get('fpath');
                var fparentid = grid.getStore().getAt(rowIndex).get('fparentid');
                if(grid.getStore().getAt(rowIndex).get('fname')==""){
                	Ext.Msg.alert('提示','此分录未上传附件！');
                	return ;
                }
                Ext.Ajax.request({
                	url:'delProductdemandfile.do',
                	params:{fid:fid,fpath:fpath,fparentid:fparentid},
                	success:function(res){
                		var obj = Ext.decode(res.responseText);
                		if(obj.success){
                			djsuccessmsg(obj.msg);
                			Ext.getCmp('DJ.order.Deliver.productdemandfile').getStore().load();
                		}else{
                			Ext.Msg.alert('提示',obj.msg);
                		}
                		//Ext.getCmp('DJ.System.MaigaoEntryGrids').getStore().reload();
                	},
                	failure:function(){
                		Ext.Msg.alert('错误','系统故障，请检查网络状况或联系管理员！');
                	}
                });
            }
        }]
		},{
			xtype:'templatecolumn',
			text:'附件名称',
			dataIndex:'fname',
			tpl : '<a href="downloadProductdemandFile.do?fid={fid}" target="_blank" style="text-decoration:none;">{fname}</a>',
			flex:1
		},{
			dataIndex:'fparentid',
			hidden:true,
			hideable:false
		}]
})
	

Ext.QuickTips.init();
Ext.define('DJ.order.Deliver.FistproductdemandEdit',{
	extend : 'Ext.c.BaseEditUI',
	id:'DJ.order.Deliver.FistproductdemandEdit',
	title : "产品需求编辑界面",
	resizable : false,
	width : 830,
	height : 600,
	ctype:'Firstproductdemand',
	bodyPadding:'20 15',
	autoScroll:true,
	resizable : false,
	closable : true,
	modal : true,
	url:'SaveOrupdateFirstproductdemand.do',
	viewurl:'getFirstproductdemandInfo.do',
	onload:function(){
	},
	listeners:{
		close:function(panel,eOpts){
			if(panel.editstate=="add"){
				var fparentid = Ext.getCmp('DJ.order.Deliver.FistproductdemandEdit.fid').getValue();
//				var fpath = Ext.getCmp('DJ.order.Deliver.productdemandfile.fpath').getValue();
				Ext.Ajax.request({
						url:'getFileByfid.do',
						params:{
							fparentid:fparentid
							},
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
//								djsuccessmsg(obj.msg);
//								Ext.getCmp("DJ.order.Deliver.FistproductdemandList").store
//												.load();
								} else {
									Ext.MessageBox.alert('错误', obj.msg);

								}
								}
							})
			}
		},
		show:function(){
			var editstate = Ext.getCmp('DJ.order.Deliver.FistproductdemandEdit').editstate;
			if(editstate=="edit"||editstate=="view"){
				var record = Ext.getCmp('DJ.order.Deliver.FistproductdemandList').getSelectionModel().getSelection();

				var grid = Ext.getCmp('DJ.order.Deliver.productdemandfile');
				var myfilter = [];
				myfilter.push({
					myfilterfield : "fparentid",
					CompareType : " = ",
					type : "string",
					value : record[0].get('fid')
				});
				var store = grid.getStore();
				store.setDefaultfilter(myfilter);
				store.setDefaultmaskstring(" #0 ");
				store.load();
				grid.setDisabled(false);
			}
			if(editstate=="view"){
				Ext.getCmp('DJ.order.Deliver.productdemandfile.addfile').hide();
				Ext.getCmp('DJ.order.Deliver.productdemandfile.productActions').hide();
				Ext.getCmp('DJ.order.Deliver.FistproductdemandEdit.fieldcontainer').setDisabled(false);
				Ext.getCmp('DJ.order.Deliver.FistproductdemandEdit.fboard').setDisabled(false);
				Ext.getCmp('DJ.order.Deliver.FistproductdemandEdit.fbox').setDisabled(false);
			}else if(editstate=="edit"){
				Ext.getCmp('DJ.order.Deliver.fauditortime').hide();
			}
		}
	},
	initComponent:function(){
		Ext.apply(this,{
			items:[
			  {name:'isfauditor',xtype:'textfield',id:'DJ.order.Deliver.FistproductdemandEdit.isfauditor',hidden:true},
			  {name:'fid',id:'DJ.order.Deliver.FistproductdemandEdit.fid',xtype:'textfield',hidden:true},
			  {name:'fcreatid',xtype:'textfield',hidden:true},
			  {name:'fcreatetime',xtype:'textfield',hidden:true,value:'2014-05-27 00:00:00.000'},
			  {name:'fupdateuserid',xtype:'textfield',hidden:true},
			  {name:'fcustomerid',xtype:'textfield',hidden:true},
			  {name:'fsupplierid',xtype:'textfield',hidden:true},
			  {name:'falloted',xtype:'textfield',hidden:true},
			  {name:'freceived',xtype:'textfield',hidden:true},
			  {name:'freceiver',xtype:'textfield',hidden:true},
//			  {name:'freceivetime',xtype:'textfield',hidden:true,value:'2014-05-27 00:00:00.000'},
			  {
				layout:{
					type:'table',
					columns:2
				},
				defaultType:'textfield',
				baseCls:'x-plain',
				defaults:{
					width:350,
					labelWidth:75,
					bodyStyle:'padding:20;'
				},
				items:[{
					fieldLabel:'需求编号',id:'DJ.order.Deliver.FistproductdemandEdit.fnumber',name:'fnumber',labelWidth:130,width:380
				},{
					fieldLabel:'需求名称',name:'ftheme',labelWidth:130,width:380,labelStyle:'padding-left:30px;'
				},{
					fieldLabel:'需求类别',xtype:'combobox',editable : false,value:'0',
					store: Ext.create('Ext.data.Store', {
					    fields: ['name', 'value'],
					    data : [
					        {"name":"新产品包装设计", "value":"0"},
					        {"name":"老产品包装技改", "value":"1"},
					    ]
					}),
					displayField:'name',valueField:'value',name:'ftype',labelWidth:130,width:380
				},{
					fieldLabel:'成本要求',name:'fcostneed',labelWidth:130,width:380,labelStyle:'padding-left:30px;'
				},{
					fieldLabel:'是否制样',xtype:'combobox',name:'fiszhiyang',editable : false,value:'true',
					store: Ext.create('Ext.data.Store', {
					    fields: ['name', 'value'],
					    data : [
					        {"name":"是", "value":"true"},
					        {"name":"否", "value":"false"},
					    ]
					}),
					displayField:'name',valueField:'value',
					labelWidth:130,width:380
				},{
					fieldLabel:'首单数量(含制样)',xtype:'numberfield',name:'famount',labelWidth:130,width:380,labelStyle:'padding-left:30px;'
				},    Ext.create('Ext.ux.form.DateTimeField', {
					fieldLabel : '方案入库日期',
					labelWidth:130,
					width:380,
					format:'Y-m-d',
					value:new Date(),
					name : 'foverdate',
					allowBlank : false,
					blankText : '方案入库日期不能为空'
				}),Ext.create('Ext.ux.form.DateTimeField', {
					fieldLabel : '发货日期',
					labelWidth:130,
					width:380,
					format:'Y-m-d',
					name : 'farrivetime',
					allowBlank : false,
					blankText : '配送日期不能为空',
					labelStyle:'padding-left:30px;'
				})]
			},{
				xtype:'displayfield',
				value:'附件分录'
			},
			{
				xtype:'productdemandfile',
				id:'DJ.order.Deliver.productdemandfile',
				url:'getProductdemandfileList.do',
				onload:function(){
					Ext.getCmp('DJ.order.Deliver.productdemandfile.addbutton').setVisible(false);
					Ext.getCmp('DJ.order.Deliver.productdemandfile.editbutton').setVisible(false);
					Ext.getCmp('DJ.order.Deliver.productdemandfile.viewbutton').setVisible(false);
					Ext.getCmp('DJ.order.Deliver.productdemandfile.delbutton').setVisible(false);
					Ext.getCmp('DJ.order.Deliver.productdemandfile.refreshbutton').setVisible(false);
					Ext.getCmp('DJ.order.Deliver.productdemandfile.querybutton').setVisible(false);
					Ext.getCmp('DJ.order.Deliver.productdemandfile.exportbutton').setVisible(false);
					Ext.getCmp('DJ.order.Deliver.productdemandfile.exportbutton').setVisible(false);
				 },
				custbar:[{
					text:'上传附件',
					height : 30,
					id:'DJ.order.Deliver.productdemandfile.addfile',
					handler : function(){
						var grid = Ext.getCmp('DJ.order.Deliver.productdemandfile');
		            	var id = Ext.getCmp('DJ.order.Deliver.FistproductdemandEdit.fid').getValue();
		            	Ext.getCmp('DJ.order.Deliver.FistproductdemandEdit.fparentid').setValue(id);
//		            	Ext.getCmp('DJ.order.Deliver.FistproductdemandEdit.state').setValue("0");//上传唛稿分录附件
		            	document.getElementById('DJ.order.Deliver.FistproductdemandEdit.file-button-fileInputEl').click();
					}
					}]
				}			   		
			,{
				xtype:'displayfield',
				value:'<br/>需求描述:(当填写包装要求、特殊要求、现状问题，填写内容准确、具体，设计效率越高.)'
			},{
				layout:{
					type:'table',
					columns:2
				},
				baseCls:'x-plain',
				defaults:{
					width:350,
					labelWidth:75,
					bodyStyle:'padding:20;'
				},
				items:[{name:'fdescription',xtype:'textarea',colspan:2,allowBlank:true,width:763,maxLength:255}]
			},{
//				xtype:'fieldset',
//				title:'详细信息',
//				collapsible: false,
				baseCls:'x-plain',
				items:[{
					layout:'column',
					baseCls:'x-plain',
		        	items:[{
		        		baseCls:'x-plain',
		        		items:[{
		        			fieldLabel:'箱型',name:'fboxpileid',xtype:'cCombobox',labelWidth:90,width:370,
		        			displayField:'fname', // 这个是设置下拉框中显示的值
	    	        	    valueField:'fid', // 这个可以传到后台的值
	    	        	    MyConfig : {
	    	 					width : 800,// 下拉界面
	    	 					height : 200,// 下拉界面
	    	 					url : 'getBoxpileList.do', 
	    	 					fields:[{name:'fid'},{name:'fname'},{name:'fnumber'}],
	    	 					columns:[{
	    	 						dataIndex:'fid',
	    	 						hidden:true
	    	 					},{
	    	 						text:'名称',
	    	 						dataIndex:'fname'
	    	 					},{
	    	 						text:'编号',
	    	 						dataIndex:'fnumber'
	    	 					}]
	    	        	    }
		        		},{
		    	        	 xtype:'fieldcontainer',
		    	        	 fieldLabel : '纸箱规格',
		    	        	 layout: 'hbox',
		    	        	 labelWidth:90,width:370,
		    	        	 id:'DJ.order.Deliver.FistproductdemandEdit.fbox',
				    	     items:[{
				    	        		        	 xtype:'numberfield',
								    	        	 name:'fboxlength',
								    	        		 hideLabel: true,
								    	        		 value:0,
								    	        		 minValue:0,
								    	        		 width:64,
								    	        		 negativeText :'不能为负数'
				    	        		        },
				    	        		        {
				    	        		        	 xtype:'displayfield',
								    	        	value:'mm X'
				    	        		        },
				    	        		        {
				    	        		        	 xtype:'numberfield',
								    	        	 name:'fboxwidth',
								    	        	 value:0,
								    	        	 minValue:0,
								    	        	 width:64,
								    	        	 negativeText :'不能为负数'
				    	        		        },
				    	        		        {
				    	        		        	 xtype:'displayfield',
				    	        		        	 value:'mm X'
				    	        		        },
				    	        		        {
				    	        		        	 xtype:'numberfield',
								    	        	 name:'fboxheight',
								    	        	 value:0,
								    	        	 minValue:0,
								    	        	 width:64,
								    	        	 negativeText :'不能为负数'
				    	        		        },{
				    	        		        	 xtype:'displayfield',
				    	        		        	 value:'mm'
				    	        		        }]
						},{
							fieldLabel:'楞型',
							xtype:'combobox',
							labelWidth:90,width:370,editable : false,
							store: Ext.create('Ext.data.Store', {
							    fields: ['name', 'value'],
							    data : [
							        {"name":"A", "value":"0"},
							        {"name":"B", "value":"1"},
							        {"name":"C", "value":"2"},
							        {"name":"E", "value":"3"},
							        {"name":"BE", "value":"4"},
							        {"name":"BC", "value":"5"},
							        {"name":"EBC", "value":"6"},
							        {"name":"BBC", "value":"7"},
							    ]
							}),
							displayField:'name',valueField:'value',
//							store:["A","B","C","BC","BBC"],
							name:'fcorrugated'
						},{
							fieldLabel:'印刷条形码',
							xtype:'textfield',
							labelWidth:90,width:370,
							name:'fprintbarcode'
						}]
		        	},{
		        		baseCls:'x-plain',
		        		items:[{
		        			xtype:'fieldcontainer',
		    	        	 fieldLabel : '纸板规格',
		    	        	 labelWidth: 86,
		    	        	 layout: 'hbox',
		    	        	 labelWidth:90,width:370,
		    	        	 labelStyle:'padding-left:20px;',
		    	        	 id:'DJ.order.Deliver.FistproductdemandEdit.fboard',
		    	        	 items:[{
		    		        	 xtype:'numberfield',
			    	        	 name:'fboardlength',
			    	        		 hideLabel: true,
			    	        		 value:0,
				    	        	 minValue:0,
			    	        		 width:100,
			    	        		 negativeText :'不能为负数'
		    		        },
		    		        {
		    		        	 xtype:'displayfield',
			    	        	value:'mm X'
		    		        },
		    		        {
		    		        	 xtype:'numberfield',
			    	        	 name:'fboardwidth',
			    	        	 value:0,
			    	        	 minValue:0,
			    	        	 width:100,
			    	        	 negativeText :'不能为负数'
			    	        	 
		    		        },
		    		        {
		    		        	 xtype:'displayfield',
		    		        	 value:'mm'
		    		        }]
		        		},{
							fieldLabel:'材料',
							xtype:'textfield',
							name:'fmaterial',
							labelWidth:90,width:370,
		    	        	labelStyle:'padding-left:20px;'
						},{
							fieldLabel:'印刷颜色',
							xtype:'textfield',
							labelWidth:90,width:370,
							labelStyle:'padding-left:20px;',
							name:'fprintcolor'
						}]
	    	        	 
					}] 
					
				}]
			},{
//				xtype:'fieldset',
//				title:'工艺要求',
//				collapsible: false,
				baseCls:'x-plain',
				items:[{
					layout:'column',
					baseCls:'x-plain',
		        	items:[{
		        		baseCls:'x-plain',
		        		items:[{
		        			fieldLabel:'结合方式',
							xtype:'combobox',
							editable : false,
							value:'0',
							store: Ext.create('Ext.data.Store', {
							    fields: ['name', 'value'],
							    data : [
							        {"name":"钉针", "value":"0"},
							        {"name":"胶粘", "value":"1"},
							        {"name":"不粘不钉", "value":"2"},
							    ]
							}),
							displayField:'name',valueField:'value',
//							store:["钉针","胶粘","不粘不钉"],
							labelWidth:90,width:370,
							name:'funitestyle'
		        		},{
		        			fieldLabel:'表面处理',
							xtype:'combobox',
							editable : false,
							value:'0',
//							store:["无","覆光膜","覆哑膜","上光","上哑曲"],
							store: Ext.create('Ext.data.Store', {
							    fields: ['name', 'value'],
							    data : [
							        {"name":"无", "value":"0"},
							        {"name":"覆光膜", "value":"1"},
							        {"name":"覆哑膜", "value":"2"},
							        {"name":"上光", "value":"3"},
							        {"name":"上哑曲", "value":"4"},
							    ]
							}),
							displayField:'name',valueField:'value',
							labelWidth:90,width:370,
							name:'fsurfacetreatment'
		        		},{
		        			fieldLabel:'打包方式',
							xtype:'fieldcontainer',
							layout: 'hbox',
							id:'DJ.order.Deliver.FistproductdemandEdit.fieldcontainer',
							labelWidth:90,width:370,
								items:[{
									xtype:'textfield',
									width:235,
									name:'fpackstyle'
								},{
									xtype:'displayfield',
			    		        	 value:'(片)/包'
								}]
		        		},{
		        			fieldLabel:'是否组装',
							xtype:'combobox',
							editable : false,
							value:'false',
//							store:["是","否"],
							store: Ext.create('Ext.data.Store', {
							    fields: ['name', 'value'],
							    data : [
							        {"name":"是", "value":"true"},
							        {"name":"否", "value":"false"},
							    ]
							}),
							displayField:'name',valueField:'value',
							labelWidth:90,width:370,
							name:'fispackage'
		        		},{
		        			fieldLabel:'是否刻字',
							xtype:'combobox',
							editable : false,
							value:'false',
//							store:["是","否"],
							store: Ext.create('Ext.data.Store', {
							    fields: ['name', 'value'],
							    data : [
							        {"name":"是", "value":"true"},
							        {"name":"否", "value":"false"},
							    ]
							}),
							displayField:'name',valueField:'value',
							labelWidth:90,width:370,
							name:'fislettering'
		        		}]
		        	},{
		        		baseCls:'x-plain',
		        		items:[{
		        			fieldLabel:'印刷方式',
							xtype:'combobox',
							editable : false,
							value:'0',
//							store:["水印","胶印","空白"],
							store: Ext.create('Ext.data.Store', {
							    fields: ['name', 'value'],
							    data : [
							        {"name":"水印", "value":"0"},
							        {"name":"胶印", "value":"1"},
							        {"name":"空白", "value":"2"},
							    ]
							}),
							displayField:'name',valueField:'value',
							labelWidth:90,width:370,
							labelStyle:'padding-left:20px;',
							name:'fprintstyle'
		        		},{
		        			fieldLabel:'是否清废',
							xtype:'combobox',
							editable : false,
							value:'true',
//							store:["是","否"],
							store: Ext.create('Ext.data.Store', {
							    fields: ['name', 'value'],
							    data : [
							        {"name":"是", "value":"true"},
							        {"name":"否", "value":"false"},
							    ]
							}),
							displayField:'name',valueField:'value',
							labelWidth:90,width:370,
							labelStyle:'padding-left:20px;',
							name:'fisclean'
						},{
							fieldLabel:'打包描述',
							xtype:'textfield',
							labelWidth:90,width:370,
							labelStyle:'padding-left:20px;',
							name:'fpackdescription'
		        		},{
							fieldLabel:'组装描述',
							xtype:'textfield',
							labelWidth:90,width:370,
							labelStyle:'padding-left:20px;',
							name:'fpackagedescription'
		        		},{
							fieldLabel:'刻字描述',
							xtype:'textfield',
							labelWidth:90,width:370,
							labelStyle:'padding-left:20px;',
							name:'fletteringescription'
		        		}]
		        	}]
				}]
			},{

		   		layout:{
					type:'table',
					columns:2
				},
				baseCls:'x-plain',
				defaults:{
					width:350,
					labelWidth:75,
					bodyStyle:'padding:20;'
				},
				defaultType:'displayfield',
				items:[
					{fieldLabel:'创建人',name:'fcreatids'},
					{fieldLabel:'创建时间',name:'fcreatetime',labelStyle:'padding-left:30px;',labelWidth:140,width:415},
					{fieldLabel:'最后修改人',name:'fupdateuserid',id:'DJ.System.MaigaoEdit.flastupdateusername'},
					{fieldLabel:'最后修改时间',name:'fupdatetime',labelStyle:'padding-left:30px;',labelWidth:140,width:415},
					{fieldLabel:'审核人',name:'fauditorid'},
					{fieldLabel:'审核时间',id:'DJ.order.Deliver.fauditortime',name:'fauditortime',labelStyle:'padding-left:30px;',labelWidth:140,width:415}
				]
		   	
			}]
		});
		this.callParent(arguments);
				}
	
//	custbar:[{
//		text:'附件信息',
//		height:30,
//		
//		handler:function(){
//			var win = Ext.create('DJ.System.MaigaoAttachmentWin');
//			var grid = Ext.getCmp('DJ.System.MaigaoAttachmentGrid');
//			if(Ext.getCmp('DJ.order.Deliver.FistproductdemandEdit.isfauditor').getValue()=='true'){
//				Ext.getCmp('DJ.System.MaigaoAttachmentGrid.delbutton').hide();
//				Ext.getCmp('DJ.System.MaigaoAttachmentGrid.UploadButton').hide();
//			}
//			var store = grid.getStore();
//			store.setDefaultfilter([{
//				myfilterfield : "fparentid",
//				CompareType : " = ",
//				type : "string",
//				value : Ext.getCmp('DJ.order.Deliver.FistproductdemandEdit.fid').getValue()
//			}]);
//			store.setDefaultmaskstring(" #0 ");
//			win.show();
//		}
//	}]
})
Ext.create('Ext.form.Panel',{
   		id:'DJ.order.Deliver.FistproductdemandEdit.Maogao',
   		renderTo:document.body,
   		defaultType:'textfield',
   		items:[{
   			name:'state',
   			id:'DJ.order.Deliver.FistproductdemandEdit.state'
   		},{
   			name:'parentid',
   			id:'DJ.order.Deliver.FistproductdemandEdit.fparentid'
   		},{
   			xtype:'filefield',
   			name:'fileName',
   			id:'DJ.order.Deliver.FistproductdemandEdit.file',
   			listeners:{
   				change:function(){
   					var form = Ext.getCmp('DJ.order.Deliver.FistproductdemandEdit.Maogao').getForm();
   					var parentid = Ext.getCmp('DJ.order.Deliver.FistproductdemandEdit.fparentid').getValue();
   					if(form.isValid()){
   						form.submit({
   							url:'uploadProductFile.do?parentid='+parentid,
   							success:function(me,action){
   								var obj = Ext.decode(action.response.responseText);
   								if(obj.success){
   									djsuccessmsg(obj.msg);
   										var store = Ext.getCmp('DJ.order.Deliver.productdemandfile').getStore();
   										store.filter([
   										           Ext.create('Ext.util.Filter', {property: "fparentid", value: parentid, root: 'data'})
   										       ]);
   										store.reload();
   									}
   								else{
   									Ext.Msg.alert('错误',obj.msg);
   								}
   							}
   						})
   					}
   				}
   			}
   		}]
})