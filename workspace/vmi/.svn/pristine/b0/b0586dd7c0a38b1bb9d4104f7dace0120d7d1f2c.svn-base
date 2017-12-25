Ext.define('DJ.System.ExcelDataEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.System.ExcelDataEdit",
	modal : true,
	title : "数据导入设置编辑页面",
	width : 600,
	maxHeight : 600,
	resizable : false,
	ctype:'ExcelData',
	url : 'saveExcelData.do',
	infourl : 'getExcelDataInfo.do', 
	viewurl : 'getExcelDataInfo.do', 
	closable : true,
	bodyPadding:10,
	custbar:[{
		text:'设置默认模板',
		hidden:true,
		handler:function(){
			var field = this.up('window').getbaseform().findField('fcustomerid');
			field.setDisabled(true);
		}
	}],
	onload:function(){
		this.getbaseform().findField('ftarget').setmyvalue({ftableName:'t_ord_deliverapply',fname:'循环订单'});
		var cTable = this.down('cTable');
		cTable.down('toolbar').hide();
		cTable.nextSibling('cTable').down('toolbar').hide();
	},
	onloadfields:function(){
		var basicForm = this.getbaseform();
		basicForm.findField('ftarget').setmyvalue({ftableName:'t_ord_deliverapply',fname:'循环订单'});
		basicForm.findField('fcustomerid').setmyvalue({fid:this.editdata.fcustomerid_fid,fname:this.editdata.fcustomerid_fname});
	},
	cverifyinput:function(){
		var arr = [2,5,6,9];
		var store = this.down('cTable').getStore(),
			dataColumn,length,findex,fixedValue;
		store.each(function(record){
			dataColumn = record.get('fdatacolumn');
			fixedValue = record.get('ffixedvalue');
			if(Ext.Array.contains(arr,record.get('findex'))&&dataColumn==''&&record.get('ffixedcell')==''&&fixedValue==''){
				throw '请将上表格第'+record.get('findex')+'行数据填写完整！';
			}
			if(dataColumn&&dataColumn.indexOf('_')>-1){
				if(!length){
					length = dataColumn.split("_").length;
					findex = record.get('findex');
				}else if(length != dataColumn.split("_").length){
					throw '数据行'+record.get('findex')+'和数据行'+findex+'长度不一致，请更改！';
				}
			}
			if(record.get('ftargetfieldname')=='类型'){
				if(fixedValue!=''&&fixedValue!='0'&&fixedValue!='1'&&fixedValue!='要货'&&fixedValue!='备货'){
					throw '第9行“类型值”填写不正确，请填“固定值”为：0（要货）或1（备货）！';
				}
			}
		});
	},
	listeners:{
		show:function(){
			var me = this,
				store = me.down('cTable').getStore(),
				store1 = Ext.getCmp('DJ.System.ExcelDataTypeEntry').getStore();
			if(this.editstate=='add'){
				Ext.Ajax.request({
					url:'getMyCustomerList.do',
					success:function(res){
						var obj = Ext.decode(res.responseText);
						if(obj.success){
							if(obj.total==1){
								var field = me.getbaseform().findField('fcustomerid');
								field.setmyvalue(obj.data[0]);
								field.setReadOnly(true);
							}
						}
					}
				});
				//添加新类型后要更改后台ExcelDataSupport的静态代码块
				store.add({findex:1,ftargetfieldname:'采购订单号',ftargetfieldvalue:'fnumber'});
				store.add({findex:2,ftargetfieldname:"发放配送时间",ftargetfieldvalue:'farrivetime'});
				store.add({findex:3,ftargetfieldname:'联系人',ftargetfieldvalue:'flinkman'});
				store.add({findex:4,ftargetfieldname:'联系电话',ftargetfieldvalue:'flinkphone'});
				store.add({findex:5,ftargetfieldname:"发放配送数量",ftargetfieldvalue:'famount'});
				store.add({findex:6,ftargetfieldname:"发放产品名称",ftargetfieldvalue:'fmaktx'});
				store.add({findex:7,ftargetfieldname:'发放配送地址',ftargetfieldvalue:'freqaddress'});
				store.add({findex:8,ftargetfieldname:'发放制造商',ftargetfieldvalue:'fmaksupplier'});
				store.add({findex:9,ftargetfieldname:'类型',ftargetfieldvalue:'ftype'});
				store.add({findex:10,ftargetfieldname:'备注',ftargetfieldvalue:'fdescription'});
				//添加新类型后要更改后台transformTypeValue方法
				store1.add({findex:1,fname:'要货',ftype:0});
				store1.add({findex:2,fname:'备货',ftype:1});
			}
		}
	},
	initComponent:function(){
		Ext.apply(this,{
			items : [{
				xtype:'textfield',
				name:'fid',
				hidden:true
			},{
				xtype:'textfield',
				name:'fnumber',
				hidden:true
			},Ext.create('Ext.ux.form.DateTimeField', {
				name:'fcreatetime',
				format:'Y-m-d',
				value:new Date(),
				hidden:true
			}),{
				xtype:'textfield',
				name:'fcreatorid',
				hidden:true
			},{
				xtype:'container',
				anchor:'100%',
				layout:'hbox',
				defaultType:'container',
				items:[{
					layout:'anchor',
					flex:1,
					defaults:{
						anchor:'95%',
						allowBlank:false,
						labelWidth:65
					},
					items:[{
						xtype:'cCombobox',
						fieldLabel:'客户',
						name:'fcustomerid',
						valueField:'fid',
						displayField:'fname',
						blankText:'请填写客户',
						enableKeyEvents:true,
						listeners:{
							keydown:function(me,e){
								if(e.getKey()==13 && this.picker){
									var store = this.picker.getStore();
									if(store.count()==1){
										this.setmyvalue(store.getAt(0).data);
									}
								}
							}
						},
						MyConfig:{
							width:500,
							height:200,
							hiddenToolbar:true,
							url : 'getMyCustomerList.do',
							fields:[{
								name:'fid'
							},{
								name:'fname',
								myfilterfield : 'e1.fname',
								myfilterable : true
							},{
								name:'fnumber',
								myfilterfield : 'e1.fnumber',
								myfilterable : true
							}],
							columns:[{
								text:'客户名称',
								dataIndex:'fname',
								sortable:true,
								flex:1
							},{
								text:'编号',
								dataIndex:'fnumber',
								sortable:true,
								flex:1
							}]
						}
					},{
						xtype:'numberfield',
						fieldLabel:'起始行',
						name:'fstartrow',
						minValue:1
					}]
				},{
					flex:1,
					layout:'anchor',
					defaults:{
						anchor:'100%',
						labelWidth:70
					},
					items:[{
						xtype:'cCombobox',
						fieldLabel:'目标单据',
						name:'ftarget',
						valueField:'ftableName',
						displayField:'fname',
						value:'要货申请',
						editable:false,
						allowBlank:false,
						hideTrigger:true,
						MyConfig:{
							url:'',
							fields:[{
								name:'ftableName'
							},{
								name:'fname'
							}],
							columns:[]
						}
					},{
						xtype:'textfield',
						fieldLabel:'终止文本',
						name:'fendtext'
					}]
				}]
			},{
				xtype:'cTable',
				name:'ExcelDataEntry',
				pageSize:100,
				parentfield:'fparentid',
				url:'getExcelDataEntryList.do',
				plugins:[Ext.create("Ext.grid.plugin.CellEditing",{
					clicksToEdit:1,
					listeners:{
						edit:function(editor, e){
							var record = e.record,
								field = e.field;
							if(field=='fdatacolumn'){
								if(/^[A-Za-z]+(([_&])[A-Za-z]+)?(\2[A-Za-z]+)*$/.test(record.get('fdatacolumn'))){
									record.set('ffixedcell','');
									record.set('ffixedvalue','');
									record.set('fdatacolumn',record.get('fdatacolumn').toUpperCase());
								}else{
									record.set('fdatacolumn','');
								}
							}else if(field=='ffixedcell'){
								if(/^(?:[A-Za-z]+\d+([_&])([A-Za-z]+\d+\1)*[A-Za-z]+\d+|[A-Za-z]+\d+)$/.test(record.get('ffixedcell'))){
									record.set('fdatacolumn','');
									record.set('ffixedvalue','');
									record.set('ffixedcell',record.get('ffixedcell').toUpperCase());
								}else{
									record.set('ffixedcell','');
								}
							}else if(field=='ffixedvalue'){
								if(/\S+/.test(record.get('ffixedvalue'))){
									record.set('fdatacolumn','');
									record.set('ffixedcell','');
									record.set('ffixedvalue',Ext.String.trim(record.get('ffixedvalue')));
								}else{
									record.set('ffixedvalue','');
								}
							}
						}
					}
				})],
				fields:[
				        {name:'ftargetfieldname'},
				        {name:'ftargetfieldvalue'},
				        {name:'fdatacolumn'},
				        {name:'ffixedcell'},
				        {name:'ffixedvalue'},
				        {name:'findex',type:'number'}
				        ],
				        columns:[{
				        	dataIndex:'ftargetfieldname',
				        	text:'目标字段',
				        	sortable:true,
				        	flex:1
				        },{
				        	dataIndex:'fdatacolumn',
				        	text:'数据列',
				        	sortable:true,
				        	flex:1,
				        	editor:{
				        		xtype:'textfield',
				        		regex:/^[A-Za-z]+(([_&])[A-Za-z]+)?(\2[A-Za-z]+)*$/,
				        		regexText:'请填写英文字母'
				        	}
				        },{
				        	dataIndex:'ffixedcell',
				        	text:'固定单元',
				        	sortable:true,
				        	flex:1,
				        	editor:{
				        		xtype:'textfield',
				        		regex:/^(?:[A-Za-z]+\d+([_&])([A-Za-z]+\d+\1)*[A-Za-z]+\d+|[A-Za-z]+\d+)$/
				        	}
				        },{
				        	dataIndex:'ffixedvalue',
				        	text:'固定值',
				        	sortable:true,
				        	itemId:'abc',
				        	flex:1,
				        	editor:{
				        		xtype:'textfield'
				        	}
				        }]
			},{
				xtype:'cTable',
				name:'ExcelDataTypeEntry',
				id:'DJ.System.ExcelDataTypeEntry',
				parentfield:'fparentid',
				url:'getExcelDataTypeEntryList.do',
				margin:'10 0 0 0',
				plugins:[Ext.create("Ext.grid.plugin.CellEditing",{
					clicksToEdit:1
				})],
				fields:[
				    {name:'fname'},
				    {name:'fsetvalue'},
				    {name:'fsettext'},
				    {name:'ftype'},
				    {name:'findex'}
				],
				columns:[{
		        	dataIndex:'fname',
		        	text:'类型',
		        	sortable:true,
		        	flex:1
		        },{
		        	dataIndex:'fsetvalue',
		        	text:'判断逻辑',
		        	sortable:true,
		        	flex:1,
		        	editor:{
		        		xtype:'combo',
		        		editable:false,
		        		store: Ext.create('Ext.data.Store', {
		        		    fields: ['value', 'name'],
		        		    data : [
		        		        {value:"为空", name:"为空"},
		        		        {value:"不为空", name:"不为空"},
		        		        {value:"固定值", name:"固定值"}
		        		    ]
		        		}),
		        	    displayField: 'name',
		        	    valueField: 'value',
		        	    queryMode: 'local'
		        	}
		        },{
		        	dataIndex:'fsettext',
		        	text:'判断值',
		        	sortable:true,
		        	flex:1,
		        	editor:{
		        		xtype:'textfield'
		        	}
		        }]
			}]
		});
		this.callParent(arguments);
	}
});




