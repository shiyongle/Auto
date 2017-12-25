/**
 * 
 * 左侧tree过滤组件，是通用默认的解决方案
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2015-2-3 下午2:34:41
 * 
 */
Ext.define('DJ.myComponent.tree.LeftTree', {
	extend : 'Ext.tree.Panel',

	// title : '客户',

	alias : 'widget.lefttree',

	mixins : ['DJ.tools.tree.TreeFilterMixer'],

	rootVisible : true,
	useArrows : true,
	autoScroll : true,
	border : false,
	containerScroll : true,

	filterMode : false,

	filters : [],

	conditionLinker : [],

	gainGrid : function() {

		return this.nextNode('grid');

	},

	searchBoxEmptyText : '根据客户名称查找...',

	fieldName : 'fcustomerid',

	textFieldName : 'text',

	treeStoreCfg : {

		fields : [{
			name : 'id'
		}, {
			name : 'text'
		}, {
			name : 'isleaf'
		}, {
			name : 'leaf',
			convert : function(v, record) {
				return record.data.isleaf == '1' ? true : false;

			}
		}],
		dataUrl : 'GetCustomerTree.do',
		rootText : '所有客户'
	},

	statics : {

		_buildTreeStore : function(treeStoreCfg) {

			var treeStore = Ext.create('Ext.data.TreeStore', {

				fields : treeStoreCfg.fields,
				proxy : {
					type : 'ajax',
					url : treeStoreCfg.dataUrl
				},
				reader : {
					type : 'json'
				},
				autoLoad : true,
				nodeParam : 'id',
				defaultRootId : '-1',
				root : {
					id : '-1',
					text : treeStoreCfg.rootText,
					expanded : true,
					leaf : false
				}

			});

			return treeStore;

		},

		searchAction : function(grid, fieldName, value, me) {

			var storeT = grid.getStore();

			var filterS = [];

			if (value != -1) {

				filterS.push({
					myfilterfield : fieldName,
					CompareType : "like",
					type : "string",
					value : value
				});

			}

			if (me.filterMode) {

				me.filters = filterS;

				grid.doGridSearchAction();

			} else {

				storeT.setDefaultfilter(myfilter);
				storeT.setDefaultmaskstring("");

				storeT.loadPage(1, {
					callback : function(records, operation, success) {

						if (!success) {

							Ext.Msg
									.alert("提示",
											operation.response.responseText);
						}

					}
				});

			}
		}

	},

	pushSelfToGoalCom : function() {

		var me = this;

		if (me.gainGrid()) {

			me.gainGrid().filterComs.push(me);

		}

	},

	initComponent : function() {

		var me = this;

		MyCommonToolsZ.setComAfterrender(me, function() {

			if (me.filterMode) {

				me.pushSelfToGoalCom();

			}

		});

		me.store = me.self._buildTreeStore(me.treeStoreCfg, me.defaultFields);

		Ext.apply(me, {

			dockedItems : [{
				xtype : 'toolbar',
				dock : 'top',
				items : [{
					text : '刷新',
					handler : function(btn) {
						me.getStore().load();
						me.clearFilter();
					}
				}, {
					xtype : 'textfield',
					emptyText : me.searchBoxEmptyText,
					// width : 100,
					listeners : {
						change : function(fild, newValue, oldValue, eOpts) {

							if (Ext.isEmpty(newValue)) {
							
								me.clearFilter();
								
								return ;
								
							}
							
							me.filterByText(newValue);

						}
					}

				}]
			}],
			listeners : {
				itemclick : function(view, record, item, index, e, eOpts) {

					me.self.searchAction(me.gainGrid(), me.fieldName, record
							.get('id'), me);

				}

			}

		});

		this.callParent(arguments);
	}

});
