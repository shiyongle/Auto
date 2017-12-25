/**
 * 搜索组件约定混入器
 * 
 * @author ZJZ（447338871@qq.com）
 * @since long age
 * 
 * @version 0.1 2015-3-3 上午9:15:53，备注
 * 
 */
Ext.define("DJ.tools.fieldRel.mixer.MySearchFieldMixer", {

	requires : ['DJ.tools.common.MyCommonToolsZ'],

	// 这个设为true的话
	filterMode : false,
	
//	remerberPreDate : true,
	
	// 过滤条件
	filters : [],
	// 连接条件
	conditionLinker : [],

	gainGrid : function() {

		return this.up('grid');

	},
	// 把自己添加到grid里面的容器
	pushSelfToGridComsA : function() {

		var me = this;

		var filterComs = me.gainGrid().filterComs;

		if (Ext.isEmpty(filterComs)) {
					
			filterComs = [];
			
		}
		
		var aT = Ext.Array.filter(filterComs, function(ele, index, all) {

			return me._removePrev(ele, index, all);

		}, me);

		me.gainGrid().filterComs = aT;

		me.gainGrid().filterComs.push(me);

	},
	// 删除之前添加的
	_removePrev : function(ele, index, all) {

		return true;
		
	},

	// // 覆写，判断是否已经在grid coms内
	// _hasBeenAdded : function(item, index, all) {
	//
	// return true;
	//
	// }
	//
	// ,

	// 把自己添加到grid里面的容器，门面
	pushSelfToGridComsAF : function() {

		var me = this;

		MyCommonToolsZ.setComAfterrender(me, function() {

			if (me.filterMode) {

				me.pushSelfToGridComsA();

			}

		});

	}

});