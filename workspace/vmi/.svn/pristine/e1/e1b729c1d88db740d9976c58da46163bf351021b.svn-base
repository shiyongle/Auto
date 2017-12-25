/**
 * grid黏贴工具
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2014-12-31 上午11:13:16
 * @version 0.2 2015-1-5
 *          上午11:22:56，大幅升级功能：支持隐藏域附带赋值；低侵入式直调原方法保存；部分列黏贴；黏贴后的回调函数支持；
 * 
 * 
 */
Ext.define("DJ.tools.grid.ExcelPaster", {
	singleton : true,

	alternateClassName : "ExcelPaster",

	requires : ['DJ.tools.grid.MyGridTools'],

	COLUMNS_IS_NOT_SUIT : "列不符合！",

	_confirmTheProLength : function(propertyS, columns) {
		if (propertyS.length != columns.length) {

			Ext.Msg.alert("提示", ExcelPaster.COLUMNS_IS_NOT_SUIT);

			throw ExcelPaster.COLUMNS_IS_NOT_SUIT;

		}
	},
	_gainPropertys : function(objS) {
		// 特殊的分割符
		var propertyS = objS.split(String.fromCharCode(9));
		return propertyS;
	},
	_buildObjs : function(content, columns, items) {
		var me = this;

		var itemSs = content.split("\n");

		Ext.each(itemSs, function(ele, index, all) {
			// 跳过空行
			if (ele.trim() == "") {

				return;

			}

			var objS = ele;

			var propertyS = me._gainPropertys(objS);

			me._confirmTheProLength(propertyS, columns);

			var obj = {};

			Ext.each(columns, function(ele, index, all) {

				obj[ele.dataIndex] = propertyS[index];

			});

			items.push(obj);

		});

	},

	_buildObjForRemote : function(content, columns, otherFieldRemoteCfgs) {

		var me = this;

		var items = [];

		// 二维数组
		var remoteCfgss = [];

		var itemSs = content.split("\n");

		Ext.each(itemSs, function(ele, index, all) {
			// 跳过空行
			if (ele.trim() == "") {

				return;

			}

			var objS = ele;

			var propertyS = me._gainPropertys(objS);

			me._confirmTheProLength(propertyS, columns);

			var obj = {};

			Ext.each(columns, function(ele, index, all) {

				obj[ele.dataIndex] = propertyS[index];

			});

			items.push(obj);

			var remoteCfgs = [];

			Ext.each(otherFieldRemoteCfgs, function(ele, index, all) {

				if (!Ext.isEmpty(obj[ele.sourceField])) {

					var remoteCfg = {
						beanName : ele.beanName,
						sourceFieldValue : obj[ele.sourceField],
						goalField : ele.goalField,
						goalDataIndex : ele.goalDataIndex,
						sourceFieldInBean : ele.sourceFieldInBean,
						tip : Ext.Array.findBy(columns, function(item, index) {

							return item.dataIndex == ele.sourceField;

						}).text
					};
					remoteCfgs.push(remoteCfg);
				}

			});

			remoteCfgss.push(remoteCfgs);

		});
		return {
			items : items,
			remoteCfgss : remoteCfgss
		};
	},

	_gainAndSetOtherField : function(grid, remoteCfgss, items, storeT, callBack) {
		var el = grid.getEl();
		el.mask("系统处理中,请稍候……");

		Ext.Ajax.request({
			timeout : 10000,

			params : {

				remoteCfgss : Ext.JSON.encode(remoteCfgss)

			},

			url : "gainValuesByOtherFieldRemoteCfgs.do",
			success : function(response, option) {

				var obj = Ext.decode(response.responseText);
				if (obj.success == true) {

					Ext.each(obj.data, function(ele, index, all) {

						Ext.each(ele, function(ele2, index2, all2) {

							items[index][ele2.dataIndex] = ele2.value;

						});

					});

					storeT.loadData(items, true);

					var modelsMc = storeT.data;

					var itemsM = modelsMc.getRange(modelsMc.getCount()
							- items.length);

					// 运用回调技术
					callBack(itemsM);

				} else {
					Ext.MessageBox.alert('错误', obj.msg);

				}
				el.unmask();
			}
		});
	},
	_validateContentIsNotEmpty : function(content) {
		if (content.trim() == "") {
			Ext.Msg.alert("提示", "内容为空");
			throw "content is empty";

		}
	},
	/**
	 * 黏贴
	 * 
	 * @param {}
	 *            content，内容，有特定格式
	 * @param {}
	 *            grid
	 * @param {}
	 *            condition，需要黏贴的列，按需传入
	 * @return {}，模型数组
	 */
	loadDataToStore : function(content, grid, condition) {

		this._validateContentIsNotEmpty(content);

		var storeT = grid.getStore();

		var columns = MyGridTools.buildColumns(grid, condition);

		var items = [];

		this._buildObjs(content, columns, items);

		storeT.loadData(items, true);

		var modelsMc = storeT.data;

		var itemsM = modelsMc.getRange(modelsMc.getCount() - items.length);

		return itemsM;

	},
	/**
	 * 黏贴Remote
	 * 
	 * @param {}
	 *            content，内容，有特定格式
	 * @param {}
	 *            grid
	 * @param {}
	 *            condition，需要黏贴的列，按需传入
	 * @param {}
	 *            otherFieldRemoteCfgs, { beanName : '', sourceField : '',
	 *            sourceFieldInBean : '', goalField : '', goalDataIndex :'' };
	 * @param {}
	 *            callBack,回调
	 */
	loadDataToStoreRemote : function(content, grid, condition,
			otherFieldRemoteCfgs, callBack) {

		this._validateContentIsNotEmpty(content);

		var storeT = grid.getStore();

		var columns = MyGridTools.buildColumns(grid, condition);

		var __ret = this._buildObjForRemote(content, columns,
				otherFieldRemoteCfgs);

		var items = __ret.items;
		var remoteCfgss = __ret.remoteCfgss;

		this._gainAndSetOtherField(grid, remoteCfgss, items, storeT, callBack);
	},

	constructor : function() {

		return this;

	}

});