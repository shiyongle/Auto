/**
 * info： 搜索框，combox有自记忆功能。一般是放在grid里面用。
 * 
 * usage: 加载，配置
 * 
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2014-9-10 上午10:52:31
 * 
 */
Ext.define('Ext.ux.form.MySearchBox', {
	extend : 'Ext.container.Container',

	alias : 'widget.mysearchbox',

	requires : ['Ext.ux.form.FieldRecorder'],

	height : 23,
	width : 300,
	layout : {

		// align : 'stretch',
		type : 'hbox'
	},
	frame : false,

	// 只对默认的grid的store有效
	useDefaultfilter : false,

	storeG : null,

	paramsZ : {},

	//自记忆,若设置true，id也必须设置，否则会出现混乱功能
	autoMemory : false,
	
	initComponent : function() {
		var me = this;

		var comboxF = {
			id : me.id + "combobox",
			xtype : 'combobox',
			queryMode : "local",

			forceSelection : true,
			typeAhead : true

			// height : 20,
			
		}

		if (me.autoMemory) {
		
			comboxF.plugins = ['fieldrecorder'];
			
		}
		
		Ext.apply(comboxF, this.comboCfg);

		var textfieldF = {

			id : me.id + "textfield",

			xtype : 'textfield',
			emptyText : '回车搜索',
			enableKeyEvents : true,

			remoteFilter : false,
			height : me.height,
			width : 150,

			_doFilter : function(isEnterKey) {

				if (this.remoteFilter) {
					if (isEnterKey) {

						this._doRemoteFilter();

					}

				} else {

					this._myFilter();

				}

			},

			_myFilter : function() {

				var storeT = me.storeG || this.up("grid").getStore();

				if (Ext.String.trim(this.getValue()) == "") {

					storeT.clearFilter();

				} else {

					storeT.filter(this.previousNode("combobox").getValue(),
							this.getValue());
				}

			},
			_doRemoteFilter : function() {

				var paramsT = {
					condictionField : this.previousNode("combobox").getValue(),
					condictionValue : this.getValue()
				}

				Ext.applyIf(paramsT, me.paramsZ);

				var callbackT = function(records, operation, success) {

					if (!success) {

						Ext.Msg.alert("提示", operation.response.responseText);
					}

				};

				if (me.storeG) {

					me.storeG.load({

						params : paramsT,
						callback : callbackT

					});

				} else {

					var storeT = this.up("grid").getStore();

					// 每次过滤前清空前面过滤器
					storeT.setDefaultfilter([]);
						
					if (me.useDefaultfilter) {

						var myfilter = [];
						myfilter.push({
							myfilterfield : paramsT.condictionField,
							CompareType : "like",
							type : "string",
							value : paramsT.condictionValue
						});

						storeT.setDefaultfilter(myfilter);
						storeT.setDefaultmaskstring(" #0 ");

						storeT.loadPage(1);

					} else {

						storeT.loadPage(1, {

							params : paramsT,
							callback : callbackT

						});

					}

				}

			},
			listeners : {
				keyup : function(com, e) {

					if (Ext.EventObject.ENTER == e.getKey()) {

						com._doFilter(true);
					}

				},
				blur : function(com) {

					com._doFilter(false);
				}
			}

		}

		Ext.apply(textfieldF, this.textfieldCfg);

		var spacerCfg = {

			xtype : "tbspacer",
			width : 5

		}

		Ext.apply(spacerCfg, this.spacerCfg);

		Ext.applyIf(me, {
			items : [comboxF, spacerCfg, textfieldF]
		});

		me.callParent(arguments);
	}

});