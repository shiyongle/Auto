

Ext.define('DJ.System.VmipdtParamEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.System.VmipdtParamEdit",
	modal : true,
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
	},
//	custbar : [{
//				// id : 'DelButton',
//				text : '自定义按钮1',
//				height : 30,
//				handler : function() {
//					var a = Ext.getCmp('DJ.test.testEdit');
//					a.seteditstate("edit"); // 设置界面可编辑"view"和""为不可编辑，其他都是可以编辑
//
//				}
//			}, {
//				// id : 'DelButton',
//				text : '自定义按钮2',
//				height : 30
//			}],
	title : "用户管理编辑界面",
	width : 360,// 230, //Window宽度
	height : 350,// 137, //Window高度
	resizable : false,
	url : 'SaveVmipdtParam.do',
	infourl : 'getVmipdtParamInfo.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	viewurl : 'getVmipdtParamInfo.do', // 查看状态数据源
	closable : true, // 关闭按钮，默认为true
	 initComponent : function() {
 		Ext.apply(this, {
	items : [{
				baseCls : 'x-plain',
				items : [
				         {
							id : 'DJ.System.VmipdtParamEdit.fid',
							xtype : 'textfield',
							name : 'fid',
							fieldLabel : 'FID',
							width : 300,
							hidden : true

						}, // combobox控件
						{
							valueField : 'fid', // 组件隐藏值
							id : "DJ.System.VmipdtParamEdit.fcustomerid",
							name : "fcustomerid",
							xtype : 'cCombobox',
							allowBlank : false,
							blankText : '请选择客户',
							displayField : 'fname',// 组件显示值
							fieldLabel : '客户名称 ',
//							 listeners:{
//								 select:function(_combo,_record, _opt)
//		    	        	    	{
//		    	        	    		var customerid = _combo.getValue();//Ext.getCmp("DJ.System.VmipdtParamEdit.fcustomerid").getValue();
//		    	        	    		Ext.getCmp("DJ.System.VmipdtParamEdit.fproductid").setDefaultfilter([{
//		    								myfilterfield : "t_pdt_productdef.fcustomerid",
//		    								CompareType : "like",
//		    								type : "string",
//		    								value : customerid
//		    							}]);
//		    							Ext.getCmp("DJ.System.VmipdtParamEdit.fproductid").setDefaultmaskstring(" #0 ");
//		    	        	    	}
//				  			    },
							MyConfig : {
								width : 800,// 下拉界面
								height : 200,// 下拉界面
								url : 'GetCustomerList.do', // 下拉数据来源
								fields : [
								          	{
												name : 'fid'
											}, {
												name : 'fname',
												myfilterfield : 't_bd_customer.fname', 	// 查找字段，发送到服务端
												myfiltername : '名称',						// 在过滤下拉框中显示的名称
												myfilterable : true							// 该字段是否查找字段
											}, {
												name : 'fnumber',
												myfilterfield : 't_bd_customer.fnumber', 	// 查找字段，发送到服务端
												myfiltername : '编码',						// 在过滤下拉框中显示的名称
												myfilterable : true							// 该字段是否查找字段
											}, {
												name : 'fdescription'
											}, {
												name : 'fcreatetime'
											}, {
												name : 'findustryid'
											}, {
												name : 'fartificialperson'
											}, {
												name : 'fbizregisterno'
											}, {
												name : 'fisinternalcompany'
											}, {
												name : 'ftxregisterno'
											}, {
												name : 'fmnemoniccode'
											}, {
												name : 'faddress'
											}, {
												name : 'fusedstatus'
											}
										],
								columns : [
											{
												'header' : 'fid',
												'dataIndex' : 'fid',
												hidden : true,
												hideable : false,
												sortable : true
	
											}, {
												'header' : '编码',
												'dataIndex' : 'fnumber',
												sortable : true
											}, {
												'header' : '客户名称',
												'dataIndex' : 'fname',
												sortable : true
											}, {
												'header' : '助记码',
												'dataIndex' : 'fmnemoniccode',
												sortable : true
											}, {
												'header' : '行业',
												'dataIndex' : 'findustryid',
												sortable : true
											}, {
												'header' : '地址',
												'dataIndex' : 'faddress',
												sortable : true,
												width : 250
											}, {
												'header' : '内部客户',
												'dataIndex' : 'fisinternalcompany',
												renderer : formatEffect,
												sortable : true
											}, {
												'header' : '税务登记号',
												'dataIndex' : 'ftxregisterno',
												sortable : true
											}, {
												'header' : '工商注册号',
												'dataIndex' : 'fbizregisterno',
												sortable : true
											}, {
												'header' : '法人代表',
												'dataIndex' : 'fartificialperson',
												sortable : true
											}, {
												'header' : '状态',
												'dataIndex' : 'fusedstatus',
												renderer : formatEffect,
												sortable : true
											}, {
												'header' : '创建时间',
												'dataIndex' : 'fcreatetime',
												width : 150,
												sortable : true
	
											}
										]
							}
						},// combobox控件
						{
							valueField : 'fid', // 组件隐藏值
							id : "DJ.System.VmipdtParamEdit.fsupplierId",
							name : "fsupplierId",
							xtype : 'cCombobox',
							displayField : 'fname',// 组件显示值
							fieldLabel : '制 造 商 ',
//							beforeExpand : function() {
//								var customerid = Ext.getCmp("DJ.System.VmipdtParamEdit.fcustomerid").getValue();//_combo.getValue();
//    	        	    		Ext.getCmp("DJ.System.VmipdtParamEdit.fproductid").setDefaultfilter([{
//    								myfilterfield : "t_pdt_productdef.fcustomerid",
//    								CompareType : "like",
//    								type : "string",
//    								value : customerid
//    							}]);
//    							Ext.getCmp("DJ.System.VmipdtParamEdit.fproductid").setDefaultmaskstring(" #0 ");
//							},
							allowBlank : false,
							blankText : '制造商不能为空',
							MyConfig : {
								width : 800,// 下拉界面
								height : 200,// 下拉界面
								url : 'GetSupplierList.do', // 下拉数据来源
								fields : [{
											name : 'fid'
										}, {
											name : 'fname',
											myfilterfield : 'fname',
											myfiltername : '名称',
											myfilterable : true
										}, {
											name : 'fnumber',
											myfilterfield : 'fnumber',
											myfiltername : '编码',
											myfilterable : true
										}, {
											name : 'fdescription'
										}, {
											name : 'femail'
										}, {
											name : 'ftel'
										}, {
											name : 'fcreatetime'
										}, {
											name : 'flastupdatetime'
										}, {
											name : 'fsimplename'
										}, {
											name : 'fartificialperson'
										}, {
											name : 'fbarcode'
										}, {
											name : 'fbusiexequatur'
										}, {
											name : 'fbizregisterno'
										}, {
											name : 'fbusilicence'
										}, {
											name : 'fgspauthentication'
										}, {
											name : 'ftaxregisterno'
										}, {
											name : 'fusedstatus'
										}, {
											name : 'fmnemoniccode'
										}, {
											name : 'fforeignname'
										}, {
											name : 'faddress'
										}, {
											name : 'fcreatorid'
										}, {
											name : 'flastupdateuserid'
										}, {
											name : 'fcountry'
										}, {
											name : 'fcity'
										}],
										columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
											'header' : 'fid',
											'dataIndex' : 'fid',
											hidden : true,
											hideable : false,
											sortable : true
										}, {
											'header' : '供应商名称',
											'dataIndex' : 'fname',
											sortable : true
										}, {
											'header' : '编码',
											'dataIndex' : 'fnumber',
											sortable : true
										}, {
											'header' : '简称',
											width : 70,
											'dataIndex' : 'fsimplename',
											sortable : true
										}, {
											'header' : '法人代表',
											width : 70,
											'dataIndex' : 'fartificialperson',
											sortable : true
										}, {
											'header' : '条形码',
											hidden : true,
											'dataIndex' : 'fbarcode',
											sortable : true
										}, {
											'header' : '经营许可证',
											'dataIndex' : 'fbusiexequatur',
											sortable : true
										}, {
											'header' : '工商注册号',
											'dataIndex' : 'fbizregisterno',
											sortable : true
										}, {
											'header' : '营业执照',
											'dataIndex' : 'fbusilicence',
											sortable : true
										}, {
											'header' : 'GSP认证',
											'dataIndex' : 'fgspauthentication',
											sortable : true
										}, {
											'header' : '税务登记号',
											'dataIndex' : 'ftaxregisterno',
											sortable : true
										}, {
											'header' : '状态',
											width : 50,
											'dataIndex' : 'fusedstatus',
											renderer : function formatfusedstatus(value) {
														return value == '1' ? '启用' : '禁用';
													},
											sortable : true
										}, {
											'header' : '助记码',
											'dataIndex' : 'fmnemoniccode',
											sortable : true
										}, {
											'header' : '外文名称',
											hidden : true,
											'dataIndex' : 'fforeignname',
											sortable : true
										}, {
											'header' : '地址',
											'dataIndex' : 'faddress',
											sortable : true
										}, {
											'header' : '手机',
											hidden : true,
											'dataIndex' : 'ftel',
											sortable : true
										}, {
											'header' : '邮箱',
											hidden : true,
											'dataIndex' : 'femail',
											sortable : true
										}, {
											'header' : '国家',
											hidden : true,
											width : 50,
											'dataIndex' : 'fcountry',
											sortable : true
										}, {
											'header' : '城市',
											hidden : true,
											width : 50,
											'dataIndex' : 'fcity',
											sortable : true
										}, {
											'header' : '修改时间',
											'dataIndex' : 'flastupdatetime',
											width : 140,
											sortable : true
										}, {
											'header' : '创建时间',
											'dataIndex' : 'fcreatetime',
											width : 140,
											sortable : true
										}, {
											'header' : '描述',
											hidden : true,
											'dataIndex' : 'fdescription',
											sortable : true
										}]
							}
						},// combobox控件
						{
							valueField : 'fid', // 组件隐藏值
							id : "DJ.System.VmipdtParamEdit.fproductid",
							name : "fproductid",
							xtype : 'cCombobox',
							displayField : 'fname',// 组件显示值
							fieldLabel : '产品名称 ',
							allowBlank : false,
							blankText : '请选择产品',
							beforeExpand : function() {
								var customerid = Ext.getCmp("DJ.System.VmipdtParamEdit.fcustomerid").getValue();//_combo.getValue();
    	        	    		Ext.getCmp("DJ.System.VmipdtParamEdit.fproductid").setDefaultfilter([{
    								myfilterfield : "t_pdt_productdef.fcustomerid",
    								CompareType : "like",
    								type : "string",
    								value : customerid
    							}]);
    							Ext.getCmp("DJ.System.VmipdtParamEdit.fproductid").setDefaultmaskstring(" #0 ");
							},
							MyConfig : {
								width : 800,// 下拉界面
								height : 200,// 下拉界面
								url : 'GetProductlist.do', // 下拉数据来源
								fields : [
											{
												name : 'fid'
											}, {
												name : 'fname',
												myfilterfield : 't_pdt_productdef.fname', 	// 查找字段，发送到服务端
												myfiltername : '名称',							// 在过滤下拉框中显示的名称
												myfilterable : true								// 该字段是否查找字段
											}, {
												name : 'fnumber',
												myfilterfield : 't_pdt_productdef.fnumber', 	// 查找字段，发送到服务端
												myfiltername : '编码',							// 在过滤下拉框中显示的名称
												myfilterable : true								// 该字段是否查找字段
											}, {
												name : 'fcreatorid'
											}, {
												name : 'fcreatetime'
											}, {
												name : 'flastupdateuserid'
											}, {
												name : 'flastupdatetime'
											}, {
												name : 'fcharacter'
											}, {
												name : 'fboxmodelid'
											}, {
												name : 'feffect'
											}, {
												name : 'fnewtype'
											}, {
												name : 'fversion'
											}, {
												name : 'fishistory'
											}
										],
								columns : [
								           	{
												'header' : 'fid',
												'dataIndex' : 'fid',
												hidden : true,
												hideable : false,
												sortable : true
											}, {
												'header' : '编号',
												'dataIndex' : 'fnumber',
												sortable : true
											}, {
												'header' : '名称',
												'dataIndex' : 'fname',
												sortable : true
											}, {
												'header' : '版本号',
												'dataIndex' : 'fversion',
												sortable : true
											}, {
												'header' : '特征',
												'dataIndex' : 'fcharacter',
												sortable : true,
												width : 100
											}, {
												'header': '箱型',
												'dataIndex' : 'fboxmodelid',
												sortable : true
												
											}, {
												'header' : '类型',
												'dataIndex' : 'fnewtype',
												sortable : true
											}, {
												'header' : '历史版本',
												'dataIndex' : 'fishistory',
												renderer:formatEffect,
												sortable : true
											}, {
												'header' : '禁用或启用',
												'dataIndex' : 'feffect',
												renderer:formatEffect,
												sortable : true
											},{
												'header' : '修改时间',
												'dataIndex' : 'flastupdatetime',
												width : 150,
												sortable : true
											
											}, {
												'header' : '创建时间',
												'dataIndex' : 'fcreatetime',
												width : 150,
												sortable : true
											}
										]
							}
						},{
							id : 'DJ.System.VmipdtParamEdit.ftype',
							name:'ftype',
		    	   			fieldLabel : '下单类型',
		    	   			xtype:'combo',
//		    	   			labelWidth : 70,
//		    	   			store: houseType,
		    	   			store:Ext.create('Ext.data.Store', {
							    fields: ['typevalue', 'typename'],
							    data : [
							        {"typevalue":"0", "typename":"通知"},
							        {"typevalue":"1", "typename":"订单"}
							    ]
							}),
		    	   			triggerAction: 'all',
		    	   			displayField: 'typename',
		    	   			valueField: 'typevalue',
		    	   			editable : false, // 可以编辑不
		    	   			value:'1'	
  		    	   		},{
							id : 'DJ.System.VmipdtParamEdit.fmaxstock',
							name : 'fmaxstock',
							value:0,
//							xtype : 'numberfield',
							xtype : 'textfield',
							fieldLabel : '最大库存量',
							allowBlank : false,
							blankText : '最大库存量不能为空',
							regex : /^(?!0)\d{0,10}$/,
							regexText : "请输入不超过10位大于0的数字"

						}, {
							id : 'DJ.System.VmipdtParamEdit.forderamount',
							name : 'forderamount',
							value:0,
//							xtype : 'numberfield',
							xtype : 'textfield',
							fieldLabel : '下单批量',
							allowBlank : false,
							blankText : '下单批量不能为空',
							regex : /^(?!0)\d{0,10}$/,
							regexText : "请输入不超过10位大于0的数字"

						}, {
							id : 'DJ.System.VmipdtParamEdit.fminstock',
							name : 'fminstock',
							value:0,
//							xtype : 'numberfield',
							xtype : 'textfield',
							fieldLabel : '最小库存量',
							allowBlank : false,
							blankText : '最小库存量不能为空',
							regex : /^\d{0,10}$/,
							regexText : "请输入不超过10位大于0的数字"
							,
							listeners : {
								change : function( newValue, oldValue, eOpts ){
									var ftype = Ext.getCmp("DJ.System.VmipdtParamEdit.ftype");
										if(newValue.value==0){
//											ftype.store.load;
											ftype.setValue('1');
											ftype.setRawValue("订单");
											
//											ftype.store.load;
											ftype.setReadOnly(true);
//											ftype.setEditable(true);
//											ftype.setDisabled(true); 
//											ftype.setActive(true); 

										}else{
											ftype.setReadOnly(false);
//											ftype.setEditable(false);
//											ftype.setDisabled(false); 
//											ftype.setActive(false); 
										}
								}
							}
						},
						 {
//							id : '',
							name : 'fcontrolunitid',
							xtype : 'textarea',
							fieldLabel : '描    述',
							maxLength:44,
							maxLengthText:'输入内容过长',
							height:50,
							regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
							regexText : "不能包含特殊字符"
						 },
						 {
							id : 'DJ.System.VmipdtParamEdit.fcreatetime',
							name : 'fcreatetime',
							xtype : 'textfield',
							hidden : true,
							width : 300

						}]
			}]
			}), this.callParent(arguments);
 	},
	bodyStyle : "padding-top:20px;padding-left:48px"
});

function formatEffect(value){        
    return value=='1'?'是':'否';  
}