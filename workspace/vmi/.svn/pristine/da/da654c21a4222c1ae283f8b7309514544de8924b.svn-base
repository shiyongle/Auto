/**
 * 
 * 按钮组过滤组件
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2015-6-2 下午7:14:21
 * 
 * 只对cgrid有效
 * 
 * 
 * 
 */
Ext.define('Ext.ux.form.MySimpleButtonGroupFilter', {
	extend : 'Ext.container.ButtonGroup',

	alias : 'widget.mysimplebuttongroupfilter',

	requires : ['DJ.tools.common.MyCommonToolsZ'],

	mixins : ['DJ.tools.fieldRel.mixer.MySearchFieldMixer'],

	filterMode : false,

	filters : [],

	conditionLinker : [],

	// 提示
	tipZ : false,

	showAllButton : true,

	conditionObjs : [],

	buttonWidth : false,

	/**
	 * 内部示例对象
	 * 
	 * @type {}
	 */
	_obj : {

		text : '',
		filterField : '',
		filterValue : ''

	},

	gainGrid : function() {

		return this.up('grid');

	},

	style : {
		background : 'white'
	},

	requires : ['Ext.button.Button'],

	doSearchAction : function(key, value) {

		var callbackT = function(records, operation, success) {

			if (!success) {

				if (operation.response && operation.response.responseText) {

					Ext.Msg.alert("提示", operation.response.responseText);

				} else {

					Ext.Msg.alert("提示", '查询失败');
				}

			}

		};

		var me = this;

		var gridT = me.gainGrid();

		var myfilter = [];

		var maskstring;

		var conditionLength;

		(function() {

			var val = value.split(',');

			conditionLength = val.length;

			for (var i = 0; i < val.length; i++) {
				myfilter.push({
					myfilterfield : key,
					CompareType : "like",
					type : "string",
					value : val[i]
						// me._hashMap.get(key)[1]
						});
				maskstring += '#' + i
				if (i < val.length - 1) {
					maskstring += ' or ';
				}
			}

		})();


		if (!me.filterMode) {

			var storeT = gridT.getStore();

			storeT.setDefaultfilter(myfilter);
			storeT.setDefaultmaskstring(maskstring);

			storeT.loadPage(1, {
				callback : callbackT
			});

		} else {

			me.filters = myfilter;

			(function() {

				me.conditionLinker = [];

				for (var i = 1; i <= conditionLength; i++) {

					me.conditionLinker.push('or');

				}

			})();

			gridT.doGridSearchAction();

		}

	},

	initComponent : function() {
		var me = this;

		me.pushSelfToGridComsAF();

		me.columns = me.showAllButton
				? (me.conditionObjs.length + 1)
				: me.conditionObjs.length;

		var buttonsCfg = [];

		var maxTextLength = 0;

		Ext.each(me.conditionObjs, function(ele, index, all) {

			if (ele.text.length > maxTextLength) {

				maxTextLength = ele.text.length;

			}

		});

		var buttonWidth = me.buttonWidth || (maxTextLength * 20);

		if (me.showAllButton) {

			buttonsCfg.push({

				width : buttonWidth,

				toggleGroup : me.id + 'toggleGroup',
				text : '全部',
				handler : function() {

					me.doSearchAction('1', '1');

				}
			});

		}

		Ext.each(me.conditionObjs, function(ele, index, all) {

			var buttoncfg = {

				width : buttonWidth,

				toggleGroup : me.id + 'toggleGroup',
				text : ele.text,
				filterField : ele.filterField,
				filterValue : ele.filterValue,

				handler : function() {

					var mee = this;

					me.doSearchAction(mee.filterField, mee.filterValue);

				}

			};

			buttonsCfg.push(buttoncfg);

		});

		Ext.applyIf(me, {
			items : buttonsCfg
		});

		me.callParent(arguments);
	}

});