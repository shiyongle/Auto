/**
 * 
 * 
 * @since 2013-10-21
 * @version 0.1
 * @author zjz
 * 
 * 打印模板管理类，一般不用自己调用。 只需在_xTemplateStringsArray_中添加对象即可。
 * 
 */

Ext.define("Ext.app.MySimplePrinterXTemplateString", {
	singleton : true,

	alias : "MySimplePrinterXTemplateString",

	requires : ['MyApp.tools.MyNumberUtility'],

	_xTemplateStrings_ : Ext.create("Ext.util.MixedCollection"),

	/**
	 * 添加缓存功能，避免重复创建销毁而带来的不必要的资源浪费。
	 */
	_xTemplatesCache_ : Ext.create("Ext.util.MixedCollection"),

	/**
	 * 
	 * @type Number,缓存的大小（缓存多少个xtemplate对象，一般无须改动）
	 */
	_xTemplatesCacheCount_ : 10,

	/**
	 * 在这个数组中添加新的模版字符串,用json格式创建。id为查询依据，格式类似。 模板编写建议，外层用div标签，以便于组合和与其他模板兼容 {
	 * id : "",//必须 fhtml : '',//必须 myMethord : {//一般不需要，适合于高级功能。 } },
	 * 
	 * @type
	 */
	_xTemplateStringsArray_ : [{
		id : "tplTest",
		fhtml : '<div align="center"><p ><p><h1>{companyName}Company</h1></p><p>员工：{name}, age : {age},action : {action} </p><p align="right"> print date: {nowTime}. </p><table width="200" border="1"><caption>table</caption><tr><th>no </th><th>name </th><th> age</th></tr><tpl for="rows"><tr><td>{#}</td><td>{name}</td><td>{age}</td></tr></tpl></table></p></div>'

	}, {
		id : "tplTestPageSize",
		fhtml : '<div align="center"><p><h1>{companyName}Company</h1></p><p>员工：{name}, age : {age},action : {action} </p><p align="right"> print date: {nowTime}. </p><tpl for="rows"><input type= "hidden" value="{[parent.currentIndex = xindex]}"/><input type= "hidden" value="{[parent.allCount = xcount]}"/><tpl if="parent.currentIndex == 1"><table width="200" border="1"><caption>table</caption><tr><th>no </th><th>name </th><th> age</th></tr></tpl><tr><td>{#}</td><td>{name}</td><td>{age} </td></tr><tpl if="parent.currentIndex % parent.pageSize == 0"><tpl if="parent.currentIndex != parent.allCount"></table><div style= "height: {parent.tableInterval}cm;"></div><table width="200" border="1"><caption>table</caption><tr><th>no </th><th>name </th><th> age</th></tr></tpl></tpl><tpl if="parent.currentIndex == parent.allCount"></table></tpl></tpl></p></div>'

	}, {
		id : "gridCommomTest",
		fhtml : '<div align="center"><p><h1>{companyName}Company</h1></p><p>员工：{name}, age : {age},action : {action} </p><p align="right"> print date: {nowTime}. </p><tpl for="rows"><input type= "hidden" value="{[parent.currentIndex = xindex]}{[parent.allCount = xcount]}"/><tpl if="parent.currentIndex == 1"><table width="200" border="parent.border"><caption>table</caption><tr><th></th><tpl for="parent.header"><th>{.}</th></tpl></tr></tpl><tr><td>{#}</td><tpl foreach="."><td>{.}</td></tpl></tr><tpl if="parent.currentIndex % parent.pageSize == 0"><tpl if="parent.currentIndex != parent.allCount"></table><div style= "height: {parent.tableInterval}cm;"></div><table width="200" border="1"><caption>table</caption><tr><th></th><tpl for="parent.header"><th>{.}</th></tpl></tr></tpl></tpl><tpl if="parent.currentIndex == parent.allCount"></table></tpl></tpl></p></div>'

	}, {
		id : "gridCommomFillByNameTest",
		fhtml : '<div align="center"><p><h1>{companyName}Company</h1></p><p>员工：{name}, age : {age},action : {action} </p><p align="right"> print date: {nowTime}. </p><tpl for="rows"><input type= "hidden" value="{[parent.currentIndex = xindex]}{[parent.allCount = xcount]}"/><tpl if="parent.currentIndex == 1"><table width="200" border="parent.border"><caption>table</caption><tr><th></th><tpl for="parent.header"><th>{.}</th></tpl></tr></tpl><tr><td>{#}</td><tpl foreach="."><tpl if="{$} == parent.fileds[xindex - 1]"><td>{.}</td></tpl></tpl></tr><tpl if="parent.currentIndex % parent.pageSize == 0"><tpl if="parent.currentIndex != parent.allCount"></table><div style= "height: {parent.tableInterval}cm;"></div><table width="200" border="1"><caption>table</caption><tr><th></th><tpl for="parent.header"><th>{.}</th></tpl></tr></tpl></tpl><tpl if="parent.currentIndex == parent.allCount"></table></tpl></tpl></p></div>',
		myMethord : {
 
		}
	}, {
		id : "repeatTheOrder",
		fhtml : '<div align="center"><tpl for="rows"><input type= "hidden" value="{[parent.currentIndex = xindex]}{[parent.allCount = xcount]}"/><tpl if="parent.currentIndex == 1"><div id="header" align="center"><p><h1>{parent.companyName}Company</h1></p><p>员工：{parent.name}, age : {parent.age},action : {parent.action} </p><p align="right"> print date: {parent.nowTime}. </p></div><table width="200" border="1"><caption>{parent.tableName}</caption><tr><th></th><tpl for="parent.header"><th>{.}</th></tpl></tr></tpl><tr><td>{#}</td><tpl foreach="."><td>{.}</td></tpl></tr><tpl if="parent.currentIndex % parent.pageSize == 0"><tpl if="parent.currentIndex != parent.allCount"></table><div id="footer" align="right"> 签名__________,日期___________</div><div style= "height: {parent.tableInterval}cm;"></div><div id="header" align="center"><p><h1>{parent.companyName}Company</h1></p><p>员工：{parent.name}, age : {parent.age},action : {parent.action} </p><p align="right"> print date: {parent.nowTime}. </p></div><table width="200" border="1"><caption>{parent.tableName}</caption><tr><th></th><tpl for="parent.header"><th>{.}</th></tpl></tr></tpl></tpl><tpl if="parent.currentIndex == parent.allCount"></table><div id="footer" align="right"> 签名__________,日期___________</div></tpl></tpl></p></div>'

	}, {

		/**
		 * 下面五个part是被header和footer分割而成的，主要目的是代码复用。id约定规则为，customsName + Part +
		 * no（in 1- 5）
		 * 
		 * 
		 * @type String
		 */

		id : "gridTestPart1",
		fhtml : '<div align="center"><tpl for="rows"><input type= "hidden" value="{[parent.currentIndex = xindex]}{[parent.allCount = xcount]}"/><tpl if="parent.currentIndex == 1">'

	}, {
		id : "gridTestPart2",
		fhtml : '<table width="95%" border="{parent.border}" bordercolor="#000000" cellspacing="{parent.cellspacing}" style="{parent.style}" ><caption>{parent.tableName}</caption><tr><th></th><tpl for="parent.header"><th>{.}</th></tpl></tr></tpl><tr><td>{#}</td><tpl foreach="."><td>{.}</td></tpl></tr><tpl if="parent.currentIndex % parent.pageSize == 0"><tpl if="parent.currentIndex != parent.allCount"></table>'

	}, {
		id : "gridTestPart3",
		fhtml : '<div style= "height: {parent.tableInterval};"></div>'

	}, {
		id : "gridTestPart4",
		fhtml : '<table width="95%" border="{parent.border}" bordercolor="#000000" cellspacing="{parent.cellspacing}" style="{parent.style}" ><caption>{parent.tableName}</caption><tr><th></th><tpl for="parent.header"><th>{.}</th></tpl></tr></tpl></tpl><tpl if="parent.currentIndex == parent.allCount"></table>'

	}, {
		id : "gridTestPart5",
		fhtml : '</tpl></tpl></p></div>'

	}, {

		/**
		 * 下面五个part是被header和footer分割而成的，主要目的是代码复用。id约定规则为，customsName + Part +
		 * no（in 1- 5）
		 * 
		 * 
		 * @type String
		 */

		/**
		 * 2014/02/26 14:38 出库单（送货单）格式调整
		 * 
		 * @type String
		 */

		id : "stockOutPart1",
		fhtml : '<div align="center"><tpl for="rows"><input type= "hidden" value="{[parent.currentIndex = xindex]}{[parent.allCount = xcount]}"/><tpl if="parent.currentIndex == 1">'

	}, {
		id : "stockOutPart2",
		fhtml : '<table width="95%" border="{parent.border}" bordercolor="#000000" cellspacing="{parent.cellspacing}" style="{parent.style}" ><caption>{parent.tableName}</caption><tr><th></th><tpl for="parent.header"><th>{.}</th></tpl></tr></tpl><tr><td>{#}</td><tpl foreach="."><td>{.}</td></tpl></tr><tpl if="parent.currentIndex % parent.pageSize == 0"><tpl if="parent.currentIndex != parent.allCount"></table>'

	}, {
		id : "stockOutPart3",
		fhtml : '<div style= "height: {parent.tableInterval};"></div>'

	}, {
		id : "stockOutPart4",
		fhtml : '<table width="95%" border="{parent.border}" bordercolor="#000000" cellspacing="{parent.cellspacing}" style="{parent.style}" ><caption>{parent.tableName}</caption><tr><th></th><tpl for="parent.header"><th>{.}</th></tpl></tr></tpl></tpl><tpl if="parent.currentIndex == parent.allCount"></table>'

	}, {
		id : "stockOutPart5",
		fhtml : '</tpl></tpl></p></div>'

	}, {
		/**
		 * 表格前面和后面的重复部分，命名约定为，customName + Header，customName + Footer
		 * 
		 * @type String
		 */
		id : "testHeader",
		fhtml : '<div id="header" align="center"><p><h1>{parent.companyName}Company</h1></p><p>员工：{parent.name}, age : {parent.age},action : {parent.action} </p><p align="right"> print date: {parent.nowTime}. </p></div>'
	}, {
		id : "testFooter",
		fhtml : '<div id="footer" align="right"> 签名__________,日期___________</div>'
	}, {
		/**
		 * 出库单打印所用
		 * 
		 * <div style="height:10.87cm; width:21.5cm">,ord
		 * 
		 * @type String
		 */
		id : "outboundOrderHeader",

		fhtml : '<div style="height:10.87cm; width:21.5cm"> <div id="header" align="center" style="height: 90px;"><div style="position:relative; z-index:1; width: 70%; height: 5;"><h4>东经智能供应协同平台</h4></div><font size="2"><div style="position:relative; left:200; z-index:1; width: 30%; height: 5; top: 0;"><p align="left" >单据编号：{parent.receiptNo}</p></div><div style="position:relative; left:-200; z-index:1; height: 5; width: 30%; top: -20;"><p align="left">供应商名称：{parent.supplierName}</p></div><div style="position:relative; left:-200; z-index:1; height: 5; width: 30%; top: -20;"><p align="left">客户名称：{parent.customerName}</p></div><div style="position:relative; z-index:3; width: 30%; top:-40; height: 5; left: 200;"><p align="left">车辆名称：{parent.vehicle}</p></div><div style="position:relative; width: 60%; top: -40; height: 5; left: -78;"><p align="left">送货地址： {parent.deliveryAddress}</p></div></font> </div>'

	}, {
		id : "outboundOrderFooter",
		fhtml : '<div id="footer" align="center" style="height: 40px;"> <font size="2"><div style="position:relative; left:-200px; z-index:1; width: 30%; height: 10px; top: 0;"><p align="left" >总数：{[this.sumCount(xindex,parent,parent.pageSize,parent.countField)]}</p></div><div style="position:relative; z-index:3; width: 30%; top:-25; height: 10px;left:200px;"><p align="left" >价格：{[this.sumPrice(xindex,parent,parent.pageSize,parent.priceField)]}</p></div><div style="position:relative; left:-200px; z-index:1; width: 30%; height: 10px; top: -20;"><p align="left" >以现场卸货清点的数量签收为准</p></div></font><div style="position:relative; z-index:3; width: 30%; height: 10px; left:200px; top: -40;"><div><div align="left"><font size="2"> 检验员签字：</font></div></div><div><div align="left"><font size="2"> 客户签字：</font></div></div></div></div> </div>',
		myMethord : {
			sumCount : function(xindex, parent, pageSize, field) {

				return this._sumCommonBringOut(xindex, parent, pageSize, field,
						false);

			},
			sumPrice : function(xindex, parent, pageSize, field) {

				return this._sumCommonBringOut(xindex, parent, pageSize, field,
						true);

			},
			_sumCommonBringOut : function(xindex, parent, pageSize, field,
					price) {

				// field空或是目标field没值时，直接返回域
				if (field == "" || parent.rows[0][field] == undefined) {

					return field;
				}

				var s = 0, r = "";

				var setIFirst = false;

				for (var i = xindex - pageSize <= 0 ? 0 : xindex - pageSize; i < xindex; i++) {

					// 对最后不满pageSize的几个进行特殊处理
					if (xindex == parent.allCount && !setIFirst) {
						setIFirst = true;
						var lastCount = xindex % pageSize;
						i = xindex - lastCount;
					}

					var ct = Number(parent.rows[i][field]);

					s += ct;
				}

				var st = MyApp.tools.MyNumberUtility.buildChineseNoWord(s,
						price);

				r = s + "   " + st;

				return r;
			}
		}
	}, {
		/**
		 * 出库单打印所用 出库单（送货单）格式调整,@date 2014-2-26 下午4:37:21
		 * 
		 * @type String
		 */
		id : "outboundOrderHeader2",

		fhtml : '<div style="height:10.87cm; width:21.5cm"> <div id="header" align="center" style="height: 4cm;"> <font size="2"><table width="95%" border="0" align="center" style="word-break:break-all;"><tr><td colspan="3"><div><p align="center" style="font-size: 20px" "><strong>东经智能供应协同平台 送货单</strong></p></div></td></tr><tr><td width="30%"><p align="left" >供方名称：{parent.supplierName}</p></td><td width="30%"><p align="left" >收货单位：{parent.customerName}</p></td><td width="35%"><p align="left" >送货单号：{parent.receiptNo}</p></td></tr><tr><td width="30%"><p align="left" >供方编号：{parent.supplierNumber}</p></td><td width="30%"><p align="left" >运输单位：{parent.vehicle}</p></td><td width="35%"><p align="left" >生成日期：{parent.createTime}</p></td></tr><tr><td colspan="3"><p align="left" >交货地址：{parent.deliveryAddress}</p></td></tr></table></font> </div>'

	}, {
		id : "outboundOrderFooter2",
		fhtml : '<table width="95%" border="0"><tr><td width="30%"><p align="left" >供方经办人：</p></td><td width="30%"><p align="left" >需方经办人：</p></td><td width="35%"><p align="left" >签收时间：</p></td></tr><tr><td colspan="3"><hr size="1" /></td></tr><tr><td colspan="3"><p align="left" style="font-size: x-small" >注：此表一式三联，第一联需方经办人，第二联需方仓库，第三联供方 </p></td></tr></table> </div>',
		myMethord : {
			isNumber : function(value) {
				if (Ext.typeOf(value) == 'number') {
					return true;
				} else {
					return false;
				}
			}
		}
	}],

	constructor : function() {
		this._processMyGridPanel_();
		return this;
	},

	// initComponent : function() {
	// var me = this;
	// me.processMyGridPanel(me);
	// },

	_processMyGridPanel_ : function() {
		// this.xTemplateStrings = ;
		this._xTemplateStrings_.addAll(this._xTemplateStringsArray_);
	},

	/**
	 * 用这个方法获取xTemplateString
	 * 
	 * @param {}
	 *            id
	 * @return {}
	 */
	getxTemplateStringByID : function(id) {
		return this._xTemplateStrings_.get(id).fhtml;
	},

	/**
	 * 
	 * 用这个方法获取xTemplate
	 * 
	 * @param {}
	 *            ids,arrays
	 * @return {}
	 */
	getxTemplateByID : function(ids) {
		var r, id;
		if (ids.length > 1) {
			id = ids.join();
		} else {
			id = ids[0];
		}

		if (this._xTemplatesCache_.containsKey(id)) {
			r = this._xTemplatesCache_.get(id);
		} else {

			r = this.createXTemplate(ids);

			if (this._xTemplatesCache_.getCount() < this._xTemplatesCacheCount_) {
				this._xTemplatesCache_.add(id, r);
			} else {
				this._xTemplatesCache_.removeAt(0);
				this._xTemplatesCache_.add(id, r);
			}
		}

		return r
	},

	/**
	 * 用这个方法create xTemplate
	 * 
	 * @param {}
	 *            ids,arrays
	 * @return {}
	 */
	createXTemplate : function(ids) {
		var r = new Ext.XTemplate();

		var html = "";

		for (var i = 0; i < ids.length; i++) {
			html += this.getxTemplateStringByID(ids[i]);
			if (this._xTemplateStrings_.get(ids[i]).myMethord != null) {
				Ext.apply(r, this._xTemplateStrings_.get(ids[i]).myMethord);
			}
		}

		r.set(html);
		// if (this._xTemplateStrings_.get(id).myMethord != null) {
		// r = new Ext.XTemplate(this.getxTemplateStringByID(id),
		// this._xTemplateStrings_.get(id).myMethord);
		// } else {
		// r = new Ext.XTemplate(this.getxTemplateStringByID(id));
		// }

		return r;
	}

});
// @ sourceURL=js/tools/myPrint/MySimplePrinterXTemplateString.js
