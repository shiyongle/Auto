
/**
 * 
 * 域相关工具
 * 
 */
Ext.define("DJ.tools.fieldRel.MyFieldRelTools", {
	singleton : true,
	// alias : "djmyfieldreltools",

	alternateClassName : "MyFieldRelTools",

	dateFormat : "Y-m-d H:i",

//	constructor : function() {
//
//		var me = this;
//
//		Ext.apply(me.dateCommonColumnCfg, {
//			format : me.dateFormat
//		});
//		
////		Ext.apply(me.dateCommonFieldCfg, {
////			dateFormat : me.dateFormat
////		});
//
////		console.log(this);
//
//		return this;
//	},

	dateCommonColumnCfg : {
		'header' : 'default',
		'dataIndex' : 'default',
		width : 140,
		sortable : true,
		xtype : "datecolumn",
		format : "Y-m-d H:i"
	},

	dateCommonFieldCfg : {
		name : 'default',
		type : "date",
		dateFormat : "Y-m-d H:i:s"
	}

	,
	buildDateCommonColumnCfg : function(cfg) {

		return this._buildCfgCommon(this.dateCommonColumnCfg, cfg);
	},

	buildDateCommonFieldCfg : function(cfg) {

		return this._buildCfgCommon(this.dateCommonFieldCfg, cfg);

	}

	,

	_buildCfgCommon : function(objCfg, cfg) {

		var me = this;

		var cfgT = objCfg;

		Ext.apply(cfgT, cfg);

		return cfgT;

	},

	showEmptyWhenNull : function(value) {

		if (value == null || value == 0 || value == 'null' || Ext.String.trim(value) == "") {

			return "";

		} else {
			return value;
		}


	},
	convertToEmptyWhenNull : function(value) {

		if (value == null || value == 0 || value == 'null' || Ext.String.trim(value) == "") {

			return "";

		} else {
			return value;
		}


	},
	
	showEmptyWhenNullNoZERO : function(value) {

		if (value == null || value == 'null') {

			return "";

		} else {
			return value;
		}


	}

});