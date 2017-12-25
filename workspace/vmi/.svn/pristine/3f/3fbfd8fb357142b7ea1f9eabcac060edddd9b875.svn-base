Ext.define('DJ.System.Customer.CustomerStore', {
	extend : 'Ext.data.Store',
	id : 'DJ.System.Customer.CustomerStore',
	fields : [ {
		name : 'fid'
	}, {
		name : 'fname'
	}, {
		name : 'fnumber'
	}, {
		name : 'fdescription'
//	}, {
//		name : 'fcreatorid'
	}, {
		name : 'fcreatetime'
	}, {
//		name : 'flastupdateuserid'
//	}, {
//		name : 'flastupdatetime'
//	}, {
//		name : 'fcountryid'
//	}, {
//		name : 'fcityid'
//	}, {
//		name : 'fprovince'
//	}, {
		name : 'findustryid'
	}, {
		name : 'fartificialperson'
	}, {
		name : 'fbizregisterno'
	}, {
		name : 'fisinternalcompany'
	}, {
		name : 'ftxregisterno'
//	}, {
//		name : 'fbarcode'
	}, {
		name : 'fmnemoniccode'
//	}, {
//		name : 'fbusilicence'
//	}, {
//		name : 'fbusiexequatur'
//	}, {
//		name : 'fgspauthentication'
//	}, {
//		name : 'fforeignname'
	}, {
		name : 'faddress'
	}, {
		name : 'fusedstatus'
	} ],
	pageSize : 100,
	proxy : {
		type : 'ajax',
		url : 'GetCustomerList.do',
		reader : {
			type : 'json',
			root : 'data',
			totalProperty : 'total'
		}
	},
	autoLoad : true
});

Ext.define('DJ.System.Customer.CountryStore', {
	extend : 'Ext.data.Store',
	id : 'DJ.System.Customer.CountryStore',
	fields : [ {
		name : 'fid'
	}, {
		name : 'fname'
	}],
	proxy : {
		type : 'ajax',
		url : 'GetCountryAll.do',
		reader : {
			type : 'json',
			root : 'data'
		}
	},
	autoLoad : false
});

Ext.define('DJ.System.Customer.ProvinceStore', {
	extend : 'Ext.data.Store',
	id : 'DJ.System.Customer.ProvinceStore',
	fields : [ {
		name : 'fid'
	}, {
		name : 'fname'
	}],
	proxy : {
		type : 'ajax',
		url : 'GetProvice.do',
		reader : {
			type : 'json',
			root : 'data'
		}
	},
	autoLoad : false
});

Ext.define('DJ.System.Customer.CityStore', {
	extend : 'Ext.data.Store',
	id : 'DJ.System.Customer.CityStore',
	fields : [ {
		name : 'fid'
	}, {
		name : 'fname'
	}],
	proxy : {
		type : 'ajax',
		url : 'GetCity.do',
		reader : {
			type : 'json',
			root : 'data'
		}
	},
	autoLoad : false
});
Ext.define('DJ.System.Customer.RegionStore', {
	extend : 'Ext.data.Store',
	id : 'DJ.System.Customer.RegionStore',
	fields : [ {
		name : 'fid'
	}, {
		name : 'fname'
	}],
	proxy : {
		type : 'ajax',
		url : 'GetRegion.do',
		reader : {
			type : 'json',
			root : 'data'
		}
	},
	autoLoad : false
});

