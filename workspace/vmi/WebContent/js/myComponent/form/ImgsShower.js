/**
 * 图片展示器
 * 
 * 
 * @version 0.1 2015-7-10 下午3:13:28
 */
Ext.define('DJ.myComponent.form.ImgsShower', {
	extend : 'Ext.panel.Panel',

	alias : 'widget.imgsshower',

	// frameHeader : false,

	// requires : ['Ext.form.Panel', 'Ext.form.field.Text',
	// 'Ext.button.Button'],

	closable : false,

	autoScroll : true,

	// showDelButton : true,

	deleteUrl : '',

	remoteUrl : '',

	remoteParams : {},

	beforeGainImgsReq : function(remoteParams) {

	},

	gainImgsFromRomoteUrl : function() {

		var me = this;

		me.beforeGainImgsReq(me.remoteParams);

		var el = me.getEl();
		el.mask("系统处理中,请稍候……");

		Ext.Ajax.request({
			timeout : 6000,

			params : me.remoteParams,

			url : me.remoteUrl,
			success : function(response, option) {

				var obj = Ext.decode(response.responseText);
				if (obj.success == true) {

					me.setValue(obj.data);

				} else {
					Ext.MessageBox.alert('错误', obj.msg);
				}
				el.unmask();
			}
		});

	},

	imgCfg : {

		xtype : 'image',
		height : 100,

		listeners : {

			afterrender : function(com, eOpts) {

				var el = com.getEl();

				el.on('click', function() {

					open(com.src);

				});

			}

		},

		// autoEl : {
		//		
		// tag : 'a',
		// href : '',
		// targer : '_blank'
		//			
		//			
		// },

		minWidth : 200
	},

	height : 180,

	layout : {
		type : 'hbox',
		align : 'middle',
		defaultMargins : {
			top : 0,
			right : 10,
			bottom : 0,
			left : 10
		}
	},

	urlFidMap : new Ext.util.HashMap(),

	/**
	 * 重新设置图片
	 * 
	 * @param {}
	 *            imgUrlsP { fid ,imgUrl }
	 */
	setValue : function(imgUrlsP) {

		var me = this;

		var imgUrls = [];

		Ext.each(imgUrlsP, function(ele, index, all) {

			imgUrls.push(ele.imgUrl);
			me.urlFidMap.add(ele.imgUrl, ele.fid);

		});

		me.rebuildImgs(imgUrls);

	},

	/**
	 * 图片链接
	 * 
	 * @type {}
	 */
	imgUrls : [],

	/**
	 * 重新设置图片
	 * 
	 * @param {}
	 *            imgUrlsP
	 */
	rebuildImgs : function(imgUrlsP) {

		var me = this;

		if (imgUrlsP) {

			me.imgUrls = imgUrlsP;

		}

		var imgCfgS = me.buildImgsCfg();

		me.removeAll();

		me.add(imgCfgS);

	},

	buildImgsCfg : function() {

		var me = this;

		var imgCfgS = [];

		Ext.each(me.imgUrls, function(ele, index, all) {

			var objT = Ext.clone(me.imgCfg);

			objT.src = ele;

			// objT.autoEl.href = ele;

			if (!Ext.isEmpty(me.deleteUrl)) {

				var cont = {
					xtype : 'container',
					layout : {
						type : 'vbox',
						align : 'center',
						pack : 'center',

						defaultMargins : {
							top : 5,
							right : 0,
							bottom : 0,
							left : 0
						}
					},
					items : [objT, {
						xtype : 'button',
						text : '删除',
						handler : function() {
							var mee = this;

							var img = mee.previousNode('image');

							var srcT = img.src;

							Ext.Ajax.request({
								timeout : 6000,

								params : {
									fid : me.urlFidMap.get(srcT)
								},

								url : me.deleteUrl,
								success : function(response, option) {

									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {

										Ext.Array.remove(me.imgUrls, srcT);

										me.rebuildImgs();

									} else {
										Ext.MessageBox.alert('错误', obj.msg);
									}
									el.unmask();
								}
							});

						}
					}]
				};
				imgCfgS.push(cont);
			} else {

				imgCfgS.push(objT);

			}

		});

		return imgCfgS;

	},

	requires : ['Ext.Img'],

	initComponent : function() {
		var me = this;

		// MyCommonToolsZ.setComAfterrender(me, function() {
		//
		// if (!Ext.isEmpty(me.remoteUrl)) {
		//
		// me.gainImgsFromRomoteUrl();
		//
		// }
		//
		// });

		var imgCfgS = me.buildImgsCfg();

		Ext.applyIf(me, {
			items : imgCfgS
		});

		me.callParent(arguments);
	}

});