var countryStore = Ext.create('DJ.System.Customer.CountryStore');
var proivinceStore = Ext.create('DJ.System.Customer.ProvinceStore');
var cityStore = Ext.create('DJ.System.Customer.CityStore');
var regionStore = Ext.create('DJ.System.Customer.RegionStore');

Ext.require('DJ.myComponent.form.ImgsShower');

Ext.define('DJ.System.Customer.CustomerEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.System.Customer.CustomerEdit",
	modal : true,

	// listeners : {},

	needFid : false,
	
	onloadfields : function() {
	
		var me = this, c0=this;	
		
		

				// 修改或者查看的时候要请求图片

				var imgShowers = me.query('imgsshower');

				Ext.each(imgShowers, function(ele, index, all) {

					var shower = ele;

					shower.remoteParams.fid = me.down('hidden[name=fid]')
							.getValue();

					shower.gainImgsFromRomoteUrl();

				});

			
			

		// });

	},
	
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		var c0 = this, me = this;

		// var imgShowers =

		this.down('button[text=保  存]').setHandler(

				function() {
					try {
						if (c0.editstate == 'edit') {
							Ext.Ajax.request({
								url : 'IsSupplier.do',
								params : {
									fids : c0.down('[name=fid]').getValue()
								},
								success : function(response) {
									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										try {
											c0.Action_BeforeSubmit(c0);
											c0.Action_Submit(c0);
											c0.Action_AfterSubmit(c0)
										} catch (e) {
											// TODO: handle exception
											Ext.MessageBox.alert('提示', e);
										}
									} else {
										Ext.MessageBox.confirm('提示',
												'修改后制造商资料对应属性值同时被修改，是否继续保存?',
												function(btn, text) {
													if (btn == "yes") {
														try {
															c0
																	.Action_BeforeSubmit(c0);
															c0
																	.Action_Submit(c0);
															c0
																	.Action_AfterSubmit(c0)
														} catch (e) {
															// TODO: handle
															// exception
															Ext.Msg.show({
																title : '提示',
																msg : e,
																buttonText : {
																	ok : '确定'
																},
																animateTarget : c0
															});
														}
													} else {
														return;
													}
												})
									}
								}
							})

						} else {
							c0.Action_BeforeSubmit(c0);
							c0.Action_Submit(c0);
							c0.Action_AfterSubmit(c0)
						}

					} catch (e) {
						Ext.MessageBox.alert('提示', e);
					}
				})
	},
	title : "客户管理编辑界面",
	width : 700,// 230, //Window宽度
	// height : 500,// 137, //Window高度
	resizable : false,
	url : 'SaveNewCustomer.do',//SaveCustomer
	infourl : 'getNewCustomer.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	viewurl : 'getNewCustomer.do', // 查看状态数据源getCustomer
	closable : true, // 关闭按钮，默认为true
	items : [{
		xtype : 'hidden',
		name : 'fid'
	}, {
		layout : 'column',
		baseCls : 'x-plain',
		items : [{
			bodyStyle : 'padding-top:5px;padding-left:5px;padding-right:5px',
			baseCls : 'x-plain',
			columnWidth : .33,
			layout : 'form',
			items : [{
				xtype : 'textfield',
				name : 'fnumber',
				fieldLabel : '编码',
				allowBlank : false,
				blankText : '编码不能为空',
				labelWidth : 70,
				regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
				regexText : "不能包含特殊字符"

			}]
		}, {
			bodyStyle : 'padding-top:5px;padding-left:5px;padding-right:5px',
			baseCls : 'x-plain',
			columnWidth : .66,
			layout : 'form',
			items : [{
				// labelWidth : 70, old
				labelWidth : 100,
				xtype : 'textfield',
				name : 'fname',
				fieldLabel : '名称',
				allowBlank : false,
				blankText : '名称不能为空',
				regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
				regexText : "不能包含特殊字符"
			}]
		}]
	}, {
		layout : 'column',
		baseCls : 'x-plain',
		items : [{
			bodyStyle : 'padding:5px;',
			baseCls : 'x-plain',
			columnWidth : .33,
			layout : 'form',
			defaults : {
				xtype : 'textfield'
			},
			items : [
					// {
					// labelWidth : 70,
					// name : 'fsimplename',
					// fieldLabel : '简称',
					// regex : /^([\u4E00-\u9FA5]|\w)*$/,//
					// /^[^,\!@#$%^&*()_+}]*$/,
					// regexText : "不能包含特殊字符"
					//
					// },
					// {
					// labelWidth : 70,
					// name : 'fbizregisterno',
					// fieldLabel : '工商注册号',
					// regex : /^([\u4E00-\u9FA5]|\w)*$/,//
					// /^[^,\!@#$%^&*()_+}]*$/,
					// regexText : "不能包含特殊字符"
					//
					// },

					{
						labelWidth : 70,
						fieldLabel : '注册号',
						regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
						name : 'ftxregisterno',
						regexText : "不能包含特殊字符"

					},

					// {
					// labelWidth : 70,
					// name : 'fgspauthentication',
					// fieldLabel : 'GSP认证',
					// regex : /^([\u4E00-\u9FA5]|\w)*$/,//
					// /^[^,\!@#$%^&*()_+}]*$/,
					// regexText : "不能包含特殊字符"
					//
					// },
					{
						labelWidth : 70,
						name : 'findustryid',
						fieldLabel : '行业',
						regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
						regexText : "不能包含特殊字符"

					}
			// ,
			// {
			// labelWidth : 70,
			// name : 'fprovince',
			// fieldLabel : '省份',
			// store : proivinceStore,
			// triggerAction : "all",
			// xtype : 'combo',
			// displayField : 'fname', // 这个是设置下拉框中显示的值
			// valueField : 'fid', // 这个可以传到后台的值
			// editable : false, // 可以编辑不
			// forceSelection : true,
			// mode : 'local',
			// listeners : {
			// select : function(_combo, _record, _opt) {
			//
			// var fprovinceidd = _combo.getValue();
			// var cforms = Ext
			// .getCmp("DJ.System.Customer.CustomerEdit")
			// .getform().getForm();
			// cforms.findField('fregionid').clearValue();
			// cforms.findField('fcityid').clearValue();
			// cityStore.on("beforeload", function(store, options) {
			// Ext.apply(store.proxy.extraParams, {
			// fprovinceid : fprovinceidd
			// });
			// });
			// cityStore.load();
			//
			// regionStore.on("beforeload", function(store, options) {
			// Ext.apply(store.proxy.extraParams, {
			// fcityid : 'aa'
			// });
			// });
			// regionStore.load();
			// //
			// }
			// }
			//
			// }
			]
		},
				// {
				//
				// bodyStyle : 'padding: 5px;',
				// baseCls : 'x-plain',
				// columnWidth : .33,
				// layout : 'form',
				// defaults : {
				// xtype : 'textfield'
				// },
				// items : [
				// // {
				// // labelWidth : 70,
				// // name : 'fforeignname',
				// // fieldLabel : '外文名称',
				// // regex : /^([\u4E00-\u9FA5]|\w)*$/,//
				// /^[^,\!@#$%^&*()_+}]*$/,
				// // regexText : "不能包含特殊字符"
				// //
				// // },
				// // {
				// // labelWidth : 70,
				// // name : 'fbusilicence',
				// // fieldLabel : '营业执照',
				// // regex : /^([\u4E00-\u9FA5]|\w)*$/,//
				// /^[^,\!@#$%^&*()_+}]*$/,
				// // regexText : "不能包含特殊字符"
				// //
				// // }, {
				// // labelWidth : 70,
				// // name : 'ftxregisterno',
				// // fieldLabel : '税务登记号',
				// // regex : /^([\u4E00-\u9FA5]|\w)*$/,//
				// /^[^,\!@#$%^&*()_+}]*$/,
				// // regexText : "不能包含特殊字符"
				// //
				// // },
				// // {
				// // labelWidth : 70,
				// // name : 'fbarcode',
				// // fieldLabel : '条形码',
				// // regex : /^([\u4E00-\u9FA5]|\w)*$/,//
				// /^[^,\!@#$%^&*()_+}]*$/,
				// // regexText : "不能包含特殊字符"
				// //
				// // },
				// // {
				// // labelWidth : 70,
				// // name : 'fcityid',
				// // fieldLabel : '城市',
				// // store : cityStore,
				// // triggerAction : "all",
				// // xtype : 'combo',
				// // displayField : 'fname', // 这个是设置下拉框中显示的值
				// // valueField : 'fid', // 这个可以传到后台的值
				// // editable : false, // 可以编辑不
				// // forceSelection : true,
				// // mode : 'local',
				// // listeners : {
				// // select : function(_combo, _record, _opt) {
				// //
				// // var fcityidd = _combo.getValue();
				// // var cforms = Ext
				// // .getCmp("DJ.System.Customer.CustomerEdit")
				// // .getform().getForm();
				// // cforms.findField('fregionid').clearValue();
				// //
				// // regionStore.on("beforeload", function(store, options) {
				// // Ext.apply(store.proxy.extraParams, {
				// // fcityid : fcityidd
				// // });
				// // });
				// // regionStore.load();
				// // }
				// //
				// // }
				// //
				// // }
				// ]
				// },
				{

					bodyStyle : 'padding: 5px;',
					baseCls : 'x-plain',
					columnWidth : .33,
					layout : 'form',
					defaults : {
						xtype : 'textfield'
					},
					items : [
					// {
					// labelWidth : 100,
					// name : 'fmnemoniccode',
					// fieldLabel : '助记码',
					// regex : /^([\u4E00-\u9FA5]|\w)*$/,//
					// /^[^,\!@#$%^&*()_+}]*$/,
					// regexText : "不能包含特殊字符"
					//
					// },
					// {
					// labelWidth : 100,
					// name : 'fbusiexequatur',
					// fieldLabel : '生产/经营许可证',
					// regex : /^([\u4E00-\u9FA5]|\w)*$/,//
					// /^[^,\!@#$%^&*()_+}]*$/,
					// regexText : "不能包含特殊字符"
					//
					// },

					{
						labelWidth : 100,
						name : 'fartificialperson',
						fieldLabel : '法人代表',
						regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
						regexText : "不能包含特殊字符"

					}
					// ,
					// {
					// labelWidth : 100,
					// name : 'fcountryid',
					// fieldLabel : '国家',
					// store : countryStore,
					// triggerAction : "all",
					// xtype : 'combo',
					// displayField : 'fname', // 这个是设置下拉框中显示的值
					// valueField : 'fid', // 这个可以传到后台的值
					// editable : false, // 可以编辑不
					// forceSelection : true,
					// mode : 'local',
					// listeners : {
					// select : function(_combo, _record, _opt) {
					// var fcountryidd = _combo.getValue();
					// var cforms = Ext
					// .getCmp("DJ.System.Customer.CustomerEdit")
					// .getform().getForm();
					// cforms.findField('fregionid').clearValue();
					// cforms.findField('fcityid').clearValue();
					// cforms.findField('fprovince').clearValue();
					// proivinceStore.on("beforeload",
					// function(store, options) {
					// Ext.apply(store.proxy.extraParams, {
					// fcountryid : fcountryidd
					// });
					// });
					// proivinceStore.load();
					//
					// cityStore.on("beforeload", function(store, options) {
					// Ext.apply(store.proxy.extraParams, {
					// fprovinceid : 'aa'
					// });
					// });
					// cityStore.load();
					//
					// regionStore.on("beforeload", function(store, options) {
					// Ext.apply(store.proxy.extraParams, {
					// fcityid : 'aa'
					// });
					// });
					// regionStore.load();
					//											    	        	    		 
					// }
					// }

					// }, {
					// labelWidth : 100,
					// name : 'fregionid',
					// fieldLabel : '区县',
					// store : regionStore,
					// triggerAction : "all",
					// xtype : 'combo',
					// displayField : 'fname', // 这个是设置下拉框中显示的值
					// valueField : 'fid', // 这个可以传到后台的值
					// editable : false, // 可以编辑不
					// forceSelection : true,
					// mode : 'local'
					// }
					]
				}]
	}, {
		layout : 'form',
		bodyStyle : 'padding-left:5px;padding-right:10px',
		baseCls : 'x-plain',
		items : [

		{

			labelWidth : 70,
			xtype : 'textfield',
			name : 'fbarcode',
			fieldLabel : '身份证号',
			regex : /(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
			regexText : '身份证输入有误,请核对更改'

		},

		{
			labelWidth : 70,
			xtype : 'textfield',
			name : 'faddress',
			fieldLabel : '地址',
			regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
			regexText : "不能包含特殊字符"

		}, {
			labelWidth : 70,
			xtype : 'textfield',
			name : 'fdescription',
			fieldLabel : '备注',
			regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
			regexText : "不能包含特殊字符"

		},

		{
			labelWidth : 70,
			xtype : 'oneclickfilefieldformulitichoice',
			url : 'uploadCustomerAuthenticationFile.do',
			itemId : 'idf1',

			fieldLabel : '营业执照',

			noStore : true,

			params : {
				type : 1
			}

			// ,
			// buttonOnly : false
			,

			beforeChangeAction : function() {

				var me = this;

				var fid = me.up("window").down('hidden[name=fid]').getValue();

				me.params.fparentid = fid;

			},

			afterSuccessCallback : function(datas) {

				var me = this;

				var shower = me.up("window")
						.down('imgsshower[itemId=imgsshowerBusiness]');
				shower.remoteParams.fid = me.up("window")
						.down('hidden[name=fid]').getValue();
				shower.gainImgsFromRomoteUrl();

			}

		},

		{
			labelWidth : 70,
			xtype : 'oneclickfilefieldformulitichoice',
			url : 'uploadCustomerAuthenticationFile.do',
			params : {
				type : 0
			},

			itemId : 'idf',

			fieldLabel : '身份证',
			// buttonOnly : false,
			noStore : true,

			beforeChangeAction : function() {

				var me = this;

				var fid = me.up("window").down('hidden[name=fid]').getValue();

				me.params.fparentid = fid;
			},

			afterSuccessCallback : function(datas) {

				var me = this;

				var shower = me.up("window")
						.down('imgsshower[itemId=imgsshowerForId]');

				// shower.setValue(datas);

				shower.remoteParams.fid = me.up("window")
						.down('hidden[name=fid]').getValue();

				shower.gainImgsFromRomoteUrl();

			}

		},

		, {
			layout : 'column',
			baseCls : 'x-plain',
			items : [
					// {
					// columnWidth : .2,
					// xtype : 'checkbox',
					// name : 'fisinternalcompany',
					// boxLabel : '内部客户'
					// },
					{
						columnWidth : .2,
						xtype : 'checkbox',
						name : 'fschemedesign',
						boxLabel : '确认方案',
						 inputValue:'1',
			    	     uncheckedValue :'0'
					}, {

						columnWidth : .2,
						xtype : 'checkbox',
						name : 'fisinternalcompany',
						 inputValue:'1',
			    	     uncheckedValue :'0',
						boxLabel : '认证'

					}

			]
		},

		{

			xtype : 'imgsshower',
			itemId : 'imgsshowerBusiness',
			deleteUrl : 'deleteCustomerFileByfilefid.do',
			title : '营业执照',

			remoteUrl : 'getCustomerIdentityFileByfid.do',

			remoteParams : {
				type : 1
			},

			beforeGainImgsReq : function(cfg) {

				var me = this;

				cfg.fid = me.up("window").down('hidden[name=fid]').getValue();

			},

			// width : 600,
			imgUrls : [],

			listeners : {

				disable : function(com, eOpts) {

					com.setDisabled(false);

				}

			}

		}, {

			itemId : 'imgsshowerForId',
			xtype : 'imgsshower',
			deleteUrl : 'deleteCustomerFileByfilefid.do',

			remoteUrl : 'getCustomerIdentityFileByfid.do',

			remoteParams : {
				type : 0
			},

			title : '身份证',

			beforeGainImgsReq : function(cfg) {

				var me = this;

				cfg.fid = me.up("window").down('hidden[name=fid]').getValue();

			},

			listeners : {

				disable : function(com, eOpts) {

					com.setDisabled(false);

				}

			}

		}

		]
	}],
	bodyStyle : "padding-top:5px;padding-left:15px",
	listeners : {
		// 'beforeshow' : function(win) {
		// if (win.editstate == "edit") {
		// var form = win.getform().getForm();
		// var combox1 = form.findField('fcountryid');
		// var combox3 = form.findField('fcityid');
		// var combox2 = form.findField('fprovince');
		// var combox4 = form.findField('fregionid');
		// combox2.store.on("beforeload", function(store, options) {
		// Ext.apply(store.proxy.extraParams, {
		// fcountryid :combox1.getValue()
		// });
		// });
		// combox2.store.load();
		// combox3.store.on("beforeload", function(store, options) {
		// Ext.apply(store.proxy.extraParams, {
		// fprovinceid : combox2.getValue()
		// });
		// });
		// combox3.store.load();
		// combox4.store.on("beforeload", function(store, options) {
		// Ext.apply(store.proxy.extraParams, {
		// fcityid : combox3.getValue()
		// });
		// });
		// combox4.store.load();
		// }
		// },
		// 'close':function (win,eOpts)
		// {
		// proivinceStore.on("beforeload", function(store, options) {
		// Ext.apply(store.proxy.extraParams, {
		// fcountryid : ''
		// });
		// });
		// proivinceStore.load();
		// cityStore.on("beforeload", function(store, options) {
		// Ext.apply(store.proxy.extraParams, {
		// fprovinceid : ''
		// });
		// });
		// cityStore.load();
		// regionStore.on("beforeload", function(store, options) {
		// Ext.apply(store.proxy.extraParams, {
		// fcityid : ''
		// });
		// });
		// regionStore.load();
		// },

		beforeclose : function(win, eOpts) {

			Ext.Ajax.request({
				timeout : 6000,

				params : {
					fid : win.down("hidden[name=fid]").getValue()
				},

				url : "deleteCustomerFileByAddfid.do",
				success : function(response, option) {

					// var obj = Ext.decode(response.responseText);
					// if (obj.success == true) {
					// Ext.MessageBox.alert('成功', obj.msg);
					//
					// } else {
					// Ext.MessageBox.alert('错误', obj.msg);
					// }
					// el.unmask();
				}
			});

		}

	}

});
