Ext.define('DJ.myComponent.win.UpLoadDeliverApplyWin', {
	extend : 'Ext.Window',
	id : 'DJ.myComponent.win.UpLoadDeliverApplyWin',
	modal : true,
	title : "Excel文件上传",
	width : 450,// 230, //Window宽度
	height : 150,// 137, //Window高度
	resizable : false,
	closable : true, // 关闭按钮，默认为true
	// renderTo: 'upload',
	items : [{
		xtype : 'form',
		//		id : 'DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel.form',
		baseCls : 'x-plain',
		fieldDefaults : {
			labelWidth : 400
		},
		// items:[
		// {
		// title: '上传图片',
		width : 400,
		bodyPadding : 10,
		margin : '20 10 20 20',
		frame : true,

		items : [{
			//				id : 'DJ.order.Deliver.custGenerateDeliversList.loadupFormpanel.form.fcustomerid',
			name : 'fcustomerid',
			fieldLabel : '上传',
			labelWidth : 50,
			msgTarget : 'side',
			allowBlank : false,
			anchor : '83%',
			fieldLabel : '客户',
			xtype : 'cCombobox',
			displayField : 'fname', // 这个是设置下拉框中显示的值
			valueField : 'fid', // 这个可以传到后台的值
			blankText : '请选择客户',
			editable : false, // 可以编辑不
			MyConfig : {
				width : 800,//下拉界面
				height : 200,//下拉界面
				url : 'GetCustomerListByUserId.do', //下拉数据来源控制为当前用户关联过的客户;
				fields : [{
					name : 'fid'
				}, {
					name : 'fname',
					myfilterfield : 't_bd_customer.fname', //查找字段，发送到服务端
					myfiltername : '客户名称',//在过滤下拉框中显示的名称
					myfilterable : true
						//该字段是否查找字段
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
			xtype : 'filefield',
			name : 'fileName',
			fieldLabel : '上传',
			labelWidth : 50,
			msgTarget : 'side',
			allowBlank : false,
			anchor : '100%',
			regex : /(.)+((\.xlsx)|(\.xls)(\w)?)$/i,
			regexText : "文件格式选择不正确",
			buttonText : '选择文件'
		}],
		buttons : [{
			text : '上传',
			handler : function() {

				var me = this;

				var form = this.up('form').getForm();
				var fcustomerid = form.findField('fcustomerid').getValue();
				if (form.isValid()) {
					form.submit({
						url : 'saveUploadExcelData.do?action='
								+ encodeURIComponent(fcustomerid),
						waitMsg : '正在保存文件...',
						success : function(fp, o) {
							Ext.Msg.show({
								title : '提示信息',
								msg : o.result.msg,
								minWidth : 200,
								modal : true,
								buttons : Ext.Msg.OK
							})
							form.findField('fileName').setRawValue('');
							this.up("window").close();
						},
						failure : function(fp, o) {
							Ext.Msg.show({
								title : '提示信息',
								msg : o.result.msg,
								minWidth : 200,
								modal : true,
								buttons : Ext.Msg.OK
							})
							form.findField('fileName').setRawValue('');
						}
					});
				}
			}
		}]
	// }]
	}]
});