/**
 * 
 * grid搜索混入类
 * 
 * @author ZJZ（447338871@qq.com）
 * 
 * 
 */
Ext.define("DJ.tools.grid.mixer.MyGridSearchMixer", {

	isFirstDoSearchAction : true,

	buttonSearchAll : null,

	showSearchAllBtn : true,

	// filterComs : [],

	// _removeEmptyComs : function() {
	//
	// var me = this;
	//
	// var arrayT = Ext.Array.filter(me.filterComs, function(ele, index, all) {
	//			
	// //剔除被销毁的
	// return !ele.isDestroyed;
	// });
	//		
	// me.filterComs = arrayT;
	//
	// },

	/**
	 * 拦截器，使得混入器搜索解决方案更加成熟，使得额外的非常规过滤组件也能进行操作
	 * me.doBeforeGridSearchAction(filters, maskA, nextIndex);
	 * 
	 */
	doBeforeGridSearchAction : Ext.emptyFn,

	doGridSearchAction : function() {

		var me = this;

		// me._removeEmptyComs();

		var filters = [];

		var maskA = [];

		var indexAll = 0;

		var isFirst = true;

		Ext.each(me.filterComs, function(ele, index, all) {

			// 如果过滤组件里面没有过滤条件就直接返回，不添加
			if (ele.filters.length == 0) {

				return;

			}

			if (!isFirst) {

				maskA.push(" and ");

			}

			maskA.push('(');

			Ext.each(ele.filters, function(ele2, index2, all) {

				filters.push(ele2);

				if (index2 != 0) {

					maskA.push(Ext.String.format(" {0} ",
							ele.conditionLinker[index2 - 1]));

				}

				maskA.push(" #" + indexAll++ + " ");

			});

			maskA.push(")");

			isFirst = false;

		});

		var storeT = me.getStore();

		me.doBeforeGridSearchAction(filters, maskA, indexAll);

		storeT.setDefaultfilter(filters);
		storeT.setDefaultmaskstring(maskA.join(''));

		storeT.loadPage(1, {
			callback : function(records, operation, success) {

				if (!success) {

					if (operation.error) {
						Ext.Msg.alert("提示", operation.error);
					} else

					if (operation.response && operation.response.responseText) {

						Ext.Msg.alert("提示", operation.response.responseText);

					} else {

						Ext.Msg.alert("提示", '查询失败');
					}

				}

			}
		});

		var dockTop = me.getDockedItems('toolbar[dock="top"]')[0];

		if (!me.showSearchAllBtn) {

			return;

		}

		if (me.isFirstDoSearchAction) {

			dockTop.add(

					{

						text : '展示全部',
						// hidden : true,
						handler : function() {

							// var me = this;

							// this.getEl().frame("#CFFCE1", 1, {
							// duration : 500
							// });

							this.getEl().fadeOut();

							storeT.setDefaultfilter([]);
							storeT.setDefaultmaskstring("");

							storeT.loadPage(1, {
								callback : function(records, operation, success) {

									if (!success) {

										Ext.Msg
												.alert(
														"提示",
														operation.response.responseText);
									}
								}
							});

						}

					}

			);

			me.buttonSearchAll = dockTop.down("button[text=展示全部]");

			me.isFirstDoSearchAction = false;
		}

		if (me.buttonSearchAll && me.buttonSearchAll.getEl) {

			if (me.buttonSearchAll.getEl()) {

				me.buttonSearchAll.getEl().fadeIn();
			}

		}

	}

});