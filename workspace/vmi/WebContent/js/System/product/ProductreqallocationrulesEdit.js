Ext.define('DJ.System.product.ProductreqallocationrulesEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : 'DJ.System.product.ProductreqallocationrulesEdit',
	modal : true,
	title : "产品需求分配规则编辑界面",
	ctype : "Productreqallocationrules",
	width : 350,// 230, //Window宽度
	height : 250,// 137, //Window高度
	resizable : false,
	url : 'saveProductreqallocationrules.do',
	infourl : 'selectProductreqallocationrulesById.do',
	viewurl : 'selectProductreqallocationrulesById.do',
	closable : true, // 关闭按钮，默认为true
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
	},
	layout : {
		type : 'vbox',
		align : 'center',
		pack : 'center'
	},
	initComponent : function() {
		Ext.apply(this, {
			items : [{
				labelWidth : 80,
				width : 280,
				name : 'fcustomerid',
				fieldLabel : '客户',
				xtype : 'cCombobox',
				displayField : 'fname', // 这个是设置下拉框中显示的值
				valueField : 'fid', // 这个可以传到后台的值
				allowBlank : false,
				blankText : '请选择客户',
//				editable : false, // 可以编辑不
				MyConfig : {
					width : 800,// 下拉界面
					height : 200,// 下拉界面
					url : 'GetCustomerList.do', // 下拉数据来源
					hiddenToolbar:true,
					fields : [{
						name : 'fid'
					}, {
						name : 'fname',
						myfilterfield : 't_bd_customer.fname', // 查找字段，发送到服务端
						myfiltername : '客户名称',// 在过滤下拉框中显示的名称
						myfilterable : true
							// 该字段是否查找字段
							}, {
								name : 'fnumber'
							}, {
								name : 'findustryid'
							}, {
								name : 'faddress'
							}, {
								name : 'fisinternalcompany',
								convert : function(value, record) {
									if (value == '1') {
										return true;
									} else {
										return false;
									}
								}
							}],
					columns : [{
						text : 'fid',
						dataIndex : 'fid',
						hidden : true,
						sortable : true
					}, {
						text : '编码',
						dataIndex : 'fnumber',
						sortable : true
					}, {
						text : '客户名称',
						dataIndex : 'fname',
						sortable : true
					}, {
						text : '行业',
						dataIndex : 'findustryid',
						sortable : true
					}, {
						text : '地址',
						dataIndex : 'faddress',
						sortable : true,
						width : 250
					}, {
						text : '内部客户',
						dataIndex : 'fisinternalcompany',
						xtype : 'checkcolumn',
						processEvent : function() {
						},
						sortable : true
					}]
				}

			}, {
				name : 'fsupplierid',
				fieldLabel : '供应商名称',
				labelWidth : 80,
				width : 280,
				allowBlank : false,
				blankText : '请选择供应商',
//				editable : false, // 可以编辑不
				xtype : 'cCombobox',
				displayField : 'fname', // 这个是设置下拉框中显示的值
				valueField : 'fid', // 这个可以传到后台的值
				MyConfig : {
					width : 800,// 下拉界面
					height : 200,// 下拉界面
					url : 'GetSupplierList.do', // 下拉数据来源
						hiddenToolbar:true,
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
						name : 'fusedstatus',
						convert : function(value, record) {
							if (value == '1') {
								return true;
							} else {
								return false;
							}
						}
					}, {
						name : 'fmnemoniccode'
					}, {
						name : 'faddress'

					}],
					columns : [{
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						autoHeight : true,
						autoWidth : true,
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
						'header' : '状态',
						width : 50,
						'dataIndex' : 'fusedstatus',
						xtype : 'checkcolumn',
						processEvent : function() {
						},
						sortable : true
					}, {
						'header' : '助记码',
						'dataIndex' : 'fmnemoniccode',
						sortable : true
					}, {
						'header' : '地址',
						'dataIndex' : 'faddress',
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
					}]
				}

			}, 
			{
				xtype : 'textfield',
				fieldLabel : '创建人',
				labelWidth : 80,
				width : 280,
				name : 'fcreatorname',
				readOnly : true
			}
			,{
				xtype : 'textfield',
				fieldLabel : '创建时间',
				labelWidth : 80,
				width : 280,
				name : 'fcreatetime',
				format : 'Y-m-d',
				readOnly : true
   			},{
			    xtype:'checkbox',
			    name:'fisstock',
			    fieldLabel : '是否快速下单',
			    inputValue:'1',
			    labelWidth : 80,
			    uncheckedValue :'0',
			    checked:true
			},{
			    xtype:'checkbox',
			    name:'fbacthstock',
			    fieldLabel : '批量备货',
			    inputValue:'1',
			    labelWidth : 80,
			    uncheckedValue :'0',
			    checked:true
			},{
				xtype:'displayfield',hideLabel:true,value:'注：勾选批量备货，快速下单的购物车支持备货下单' 
			    }, {
				name : 'fid',
//				xtype : 'textfield',
				xtype : 'hidden'
			}, {
				name : 'fcreatorid',
//				xtype : 'textfield',
				xtype : 'hidden'
			}]

		}), this.callParent(arguments);
	}

});