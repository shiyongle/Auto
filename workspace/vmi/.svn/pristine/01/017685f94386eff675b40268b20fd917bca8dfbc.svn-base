/**
 * 混合模糊搜索框 usage: 加载，配置
 * 
 * 
 * @author ZJZ（447338871@qq.com）
 * 
 * @version 0.1 2014-10-29 下午2:17:23
 * @version 0.2 2014-12-18 上午10:22:07,日期进行特殊处理
 * 
 */
Ext.define('Ext.ux.form.MyMixedSearchBox', {
	extend : 'Ext.container.Container',

	alias : 'widget.mymixedsearchbox',

	requires : ['Ext.ux.form.FieldRecorder', 'DJ.tools.common.MyCommonToolsZ'],

	mixins : ['DJ.tools.fieldRel.mixer.MySearchFieldMixer'],

	height : 23,
	// width : 300,
	layout : {

		// align : 'stretch',
		type : 'hbox'
	},
	frame : false,

	filterMode : false,

	filters : [],

	conditionLinker : [],

	// 提示
	tip : null,

	// 如果不在gridpanel中运用，要自己传入store
	store : null,

	// 
	condictionFields : [],

	// condictionFields里的datefield。日期的必须配置，否则会报错
	condictionFieldsDateFields : [],

	// 只对默认的grid的store有效
	useDefaultfilter : false,

	// 自记忆,若设置true，id也必须设置，否则会出现功能混乱
	autoMemory : false,

	/**
	 * 一般在不是like条件符时使用
	 */
	beforeSearchActionForStore : function(defaultFilers, maskstrings,condictionValue) {

	},

	statics : {

		mixedSearch : function(condictionFields, condictionValue, com,
				condictionFieldsDateFields, store) {

			var callbackT = function(records, operation, success) {

				if (!success) {

					if (operation.response && operation.response.responseText) {

						Ext.Msg.alert("提示", operation.response.responseText);

					} else {

						Ext.Msg.alert("提示", '查询失败');
					}

				}

			};

			var gridT = com.up("grid");

			var storeT = store || gridT.getStore();

			var maskstrings = [];

			if (com.up("mymixedsearchbox").useDefaultfilter) {

				var myfilter = [];

				var dateFIsFirst = false;

				Ext.each(condictionFields, function(ele, index, all) {

					if (Ext.Array.contains(condictionFieldsDateFields, ele)) {

						if (MyCommonToolsZ.isHasChn(condictionValue)) {

							if (index == 0) {

								dateFIsFirst = true;

							}

							return;

						}

					}

					myfilter.push({
						myfilterfield : ele,
						CompareType : "like",
						type : "string",
						value : condictionValue
					});

					if (index == 0 || dateFIsFirst) {

						dateFIsFirst = false;

						maskstrings.push([" #", index, " "].join(""));
					} else {

						maskstrings.push([" or #", index, " "].join(""));
					}

				});

				com.up("mymixedsearchbox").beforeSearchActionForStore(myfilter,
						maskstrings, condictionValue);

				if (!com.up('mymixedsearchbox').filterMode) {

					storeT.setDefaultfilter(myfilter);
					storeT.setDefaultmaskstring(maskstrings.join(""));

					storeT.loadPage(1, {
						callback : callbackT
					});

				} else {

					var me = com.up('mymixedsearchbox');

					me.filters = myfilter;

					me.conditionLinker = [];

					Ext.each(condictionFields, function(ele, index, all) {

						if (index != 0) {

							me.conditionLinker.push(" or ");

						}

					});

					gridT.doGridSearchAction();

				}

			} else {

				storeT.loadPage(1, {

					params : {
						condictionFields : condictionFields.join(","),
						condictionValue : condictionValue
					},
					callback : callbackT

				});

			}

		}

	},

	initComponent : function() {
		var me = this;

		var searchAtion = function(com, value) {

			Ext.ux.form.MyMixedSearchBox.mixedSearch(me.condictionFields,
					value, com, me.condictionFieldsDateFields, me.store);

		};

		var textfieldF = {

			id : me.id + 'mymixedsearchboxtextfield',

			xtype : 'textfield',
			emptyText : '回车搜索',
			enableKeyEvents : true,
			remoteFilter : false,
			width : 150,

			listeners : {
				keyup : function(com, e) {

					if (Ext.EventObject.ENTER == e.getKey()) {

						searchAtion(com, com.getValue());

					}

				}
			}

		};

		if (me.autoMemory) {

			textfieldF.plugins = ['fieldrecorder'];
		}

		var spacerCfg = {

			xtype : "tbspacer",
			width : 5

		};

		var buttonF = {
			xtype : "button",
			text : '查询',
			handler : function(com, e) {

				searchAtion(com, com.previousNode("textfield").getValue());
			}
		};

		me.pushSelfToGridComsAF();

		// 内部组件处理技巧
		me.on("afterrender", function(com, eOpts) {

			// if (me.filterMode) {
			//
			// me.up("grid").filterComs.push(me);
			//
			// }

			var tipS = me._buildTip();

			if (tipS != -1) {

				MyCommonToolsZ.setQuickTip(com.down("textfield").id, "", tipS);

			}

			me.un("afterrender");

		});

		Ext.applyIf(me, {
			items : [textfieldF, spacerCfg, buttonF, {

				xtype : "tbspacer",
				width : 10

			}]
		});

		me.callParent(arguments);
	},
	_buildTip : function() {

		var me = this;

		var tip = ['可输入:'];

		if (me.tip != null) {

			return me.tip;

		} else {

			Ext.each(me.condictionFields, function(ele, index, all) {

				var keyField = ele.substring(ele.lastIndexOf(".")) == -1
						? ele
						: ele.substring(ele.lastIndexOf(".") + 1);

				var gridcolumnName = me.up("grid").down(Ext.String.format(
						"gridcolumn[dataIndex*={0}]", keyField)).text;

				if (gridcolumnName != undefined) {

					if (tip.length != 1) {

						tip.push(",");

					}

					tip.push(gridcolumnName);

				}

			});

			if (tip.length == 1) {

				return -1;

			} else {

				return tip.join("");
			}

		}

	}
});