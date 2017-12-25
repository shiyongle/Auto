/**
 * combo查询组件
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2015-1-31 上午10:03:43,只能在默认的grid里面使用
 * 
 * 
 */
Ext.define('Ext.ux.form.MySimpleSearcherCombobox', {
	extend : 'Ext.form.field.ComboBox',

	alias : 'widget.mysimplesearchercombobox',

	requires : ['DJ.tools.common.MyCommonToolsZ'],

	mixins : ['DJ.tools.fieldRel.mixer.MySearchFieldMixer'],
	
	filterMode : false,

	filters : [],

	conditionLinker : [],
	
	//提示
	tip: false,
	
	// 配置查询内容
	fields : [],

	// 不要使用，上面配置的单个对象
	_singleField : {

		displayName : '',
		field : '',
		trueValue : ''

	},

	gainGrid : function() {

		return this.up('grid');

	},

	beforeSearchReq : function(field, value){
	
		
		return true;
	},
	

	queryMode : 'local',
	triggerAction : 'all',
	queryMode : 'local',

	enableKeyEvents : true,

	forceSelection : true,
	typeAhead : true,
	value : 0,

	_hashMap : new Ext.util.HashMap(),

	statics : {

		doSearchAction : function(me, key) {

			var callbackT = function(records, operation, success) {

				if (!success) {

					Ext.Msg.alert("提示", operation.response.responseText);
				}

			};

			var gridT = me.gainGrid();
			
			var maskstring = '';

			var myfilter = [];
			
			var conditionLength;
			
			if (key != 0) {
				
				var val = me._hashMap.get(key)[1].split(',');
				
				conditionLength = val.length;
				
				for(var i = 0;i<val.length;i++){
					myfilter.push({
					myfilterfield : me._hashMap.get(key)[0],
					CompareType : "like",
					type : "string",
					value : val[i]//me._hashMap.get(key)[1]
				});
				maskstring += '#'+i
				if(i<val.length-1){
					maskstring	+= ' or ';
				}
//				maskstring  = ' #0 ';
				}

			}

			if (!me.beforeSearchReq(me._hashMap.get(key)[0], me._hashMap.get(key)[1])) {
			
				return ;
				
			}
			
			if (!me.filterMode) {

				var storeT = gridT.getStore();

				storeT.setDefaultfilter(myfilter);
				storeT.setDefaultmaskstring(maskstring);

				storeT.loadPage(1, {
					callback : callbackT
				});

			} else {

				me.filters = myfilter;
				
				(function(){
				
					me.conditionLinker = [];
					
					for (var i = 1; i <= conditionLength; i++){
					
						me.conditionLinker.push('or');
						
					}
					
				})();
				
				gridT.doGridSearchAction();

			}

		}

	},

	initComponent : function() {
		var me = this, tipS = ['全部'];

		me.pushSelfToGridComsAF();
	
		var combStoreT = [[0, '全部']];

		me._hashMap.add(0, ['全部', '全部']);
		
		Ext.each(me.fields, function(ele, index, all) {

			combStoreT.push([index + 1, ele.displayName]);
			
			tipS.push(ele.displayName);

			me._hashMap.add(index + 1, [ele.field, ele.trueValue]);
		});

		MyCommonToolsZ.setComAfterrender(me, function() {

//			if (me.filterMode) {
//
//				me.gainGrid().filterComs.push(me);
//
//			}
			
			//设置提示信息
			var tipT = me.tipT ||  tipS.join('、');
					
			
			MyCommonToolsZ.setToolTipZ(me.id, tipT);
						

		});

		Ext.applyIf(me, {

			store : combStoreT,

			listeners : {

				select : function(combo, records, eOpts) {

					var value = combo.getValue();

					me.self.doSearchAction(me, value);

				},
				keypress : function(com, e, eOpts) {

					if (e.getKey() == Ext.EventObject.ENTER) {

						var value = combo.getValue();

						me.self.doSearchAction(me, value);

					}

				}

			}

		});

		me.callParent(arguments);
	}
});