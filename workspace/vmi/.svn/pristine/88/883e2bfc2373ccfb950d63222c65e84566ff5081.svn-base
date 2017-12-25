/**
 * 
 * 
 * @since 2013-10-25
 * @version 0.3 2014-1-9 上午10:22:30,新增模型数据转换
 * @author zjz
 * 
 * 简单打印数据转换便利类
 * 
 * @version 0.3 2014-1-9 上午10:22:30,新增模型数据转换
 * 
 * 
 */
Ext.define("MyApp.tools.MyDataHelper", {
	singleton : true,
	alias : "MyDataHelper",

	TYPE : {
		simple : "simple",
		startAndL : "StartAndL",
		byIndexs : "byIndexs"
	},

	/**
	 * store构建，一般用于远程
	 * 
	 * @param {}
	 *            otherData
	 * @param {}
	 *            store，store
	 * @param {}
	 *            headerAndDataIndex，对象，类似 { headDataIndexs : [],//索引 header : []
	 *            //文本 }
	 * 
	 * @return {}
	 */
	buildDataByStore : function(otherData, store, headerAndDataIndex) {

		return this._buildDataCommon(4, null, otherData, [headerAndDataIndex,
						store]);
	},

	/**
	 * 用模型数组构建数据，多记录打印时有使用
	 * @param {} otherData
	 * @param {} models，模型数组
	 * @param {} headerAndDataIndex
	 * @return {}
	 */
	buildDataByModels : function(otherData, models, headerAndDataIndex) {
		return this._buildDataCommon(5, null, otherData, [headerAndDataIndex,
						models]);
	},

	/**
	 * 构建数据
	 * 
	 * @param {}
	 *            gridpanlT，gridpanl
	 * @param {}
	 *            startIndex，开始位置，从0开始
	 * @param {}
	 *            length，长度
	 * @param {}
	 *            otherData，其他的属性，用json对象格式
	 * @return {}，用于打印模板的数据
	 */
	buildDataByStartAndL : function(gridpanlT, startIndex, length, otherData) {
		return this._buildDataCommon(1, gridpanlT, otherData, [startIndex,
						length]);
	},

	/**
	 * 构建数据简化，参数参考原方法
	 * 
	 * @param {}
	 *            gridpanlT
	 * @param {}
	 *            otherData
	 * @return {}
	 */
	buildDataSimply : function(gridpanlT, otherData) {
		return this.buildDataByStartAndL(gridpanlT, 0,
				gridpanlT.columns.length, otherData);
	},

	/**
	 * 构建数据，指定欲插入的表格列。
	 * 
	 * @param {}
	 *            gridpanlT
	 * @param {}
	 *            indexs，指定的表格列数组，从0开始。也可以是dataindex的文本索引
	 * @param {}
	 *            otherData
	 * @return {}
	 */
	buildDataByIndexs : function(gridpanlT, indexs, otherData) {
		var r = {};
		if (Ext.typeOf(indexs[0]) == "number") {
			r = this._buildDataCommon(2, gridpanlT, otherData, indexs);
		} else if (Ext.typeOf(indexs[0]) == "string") {
			r = this._buildDataCommon(3, gridpanlT, otherData, indexs);
		}

		return r;
	},

	/**
	 * 内部类，用于构建数据时的代码复用
	 * 
	 * @param {}
	 *            buildHeaderType，int。1 means by start index；2 means by indexs;3
	 *            means by dataIndexs.4 by store.
	 * @param {}
	 *            gridpanlT
	 * @param {}
	 *            otherData
	 * @param {}
	 *            sortEles,对应的数据，根据buildHeaderType对于添加
	 * @return {}
	 */
	_buildDataCommon : function(buildHeaderType, gridpanlT, otherData, sortEles) {
		var data = {};

		var headDataIndexs = [];

		data.header = [];

		data.rows = [];

		/**
		 * 抽出代码，以便于复用和维护
		 */
		function headerCommon(item) {
			data.header.push(item.text);
			headDataIndexs.push(item.dataIndex);
		}

		/**
		 * 4时进行特殊处理
		 */
		if (buildHeaderType == 4 || buildHeaderType == 5) {
			headDataIndexs = Ext.Array.clone(sortEles[0].headDataIndexs);
			data.header = Ext.Array.clone(sortEles[0].header);
		} else {
			/**
			 * 遍历列，加入表头文本和对应的索引
			 */
			Ext.each(gridpanlT.columns, function(item, index) {

						switch (buildHeaderType) {
							case 1 :
								if (index >= sortEles[0]
										&& index - sortEles[0] < sortEles[1]) {
									headerCommon(item);
								}

								break;
							case 2 :
								if (Ext.Array.contains(sortEles, index)) {
									headerCommon(item);
								}

								break;
							case 3 :
								if (Ext.Array
										.contains(sortEles, item.dataIndex)) {
									headerCommon(item);
								}

								break;
						}
					});
		}

		var itemst;

		if (buildHeaderType == 4) {
			itemst = sortEles[1].data.items;
		} else if (buildHeaderType == 5) {
			itemst = sortEles[1];
		} else {
			itemst = gridpanlT.store.data.items;
		}

		/**
		 * 根据表格头加入数据
		 */
		Ext.each(itemst, function(itemP, index) {
					var rowT = {};
					Ext.each(headDataIndexs, function(item, index) {

								if (buildHeaderType == 5) {
									rowT[item] = itemP[item];
								} else {
									rowT[item] = itemP.data[item];
								}

							});
					data.rows.push(rowT);
				});

		/**
		 * 复制额外的属性到对象，是覆盖性复制
		 */
		Ext.apply(data, otherData);

		return data;
	}

});