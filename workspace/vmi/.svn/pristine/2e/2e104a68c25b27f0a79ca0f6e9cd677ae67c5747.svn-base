Ext.define('DJ.System.product.ProductDefStore', {
	extend : 'Ext.data.Store',
	id : 'DJ.System.product.ProductDefStore',
	fields : [ {
		name : 'd_fid'
	}, {
		name : 'd_fname'
	}, {
		name : 'd_fnumber'
	}, {
		name : 'd_fcreatorid'
	}, {
		name : 'd_fcreatetime'
	}, {
		name : 'd_flastupdateuserid'
	}, {
		name : 'd_flastupdatetime'
	}, {
		name : 'u2_fname'
	}, {
		name : 'u1_fname'
	}, {
		name : 'd_fcharacter'
	}, {
		name : 'd_fboxmodelid'
	}, {
		name : 'd_feffect'
	}, {
		name : 'd_fnewtype'
	}, {
		name : 'd_fversion'
	}, {
		name : 'd_fishistory'
			}, {
						name : 'u3_fname'
						}, {
						name : 'd_faudittime'
						}, {
						name : 'd_faudited'
	},{
		name : 'schemedesignid'
	},{name:'fcharactername'},{name:'fcharacterid'} ],
	pageSize : 100,
	proxy : {
		type : 'ajax',
		url : 'GetProducts.do',
		extraParams: {
            fcustomerid: ''
        },
		reader : {
			type : 'json',
			root : 'data',
			totalProperty : 'total'
		}
	},
	autoLoad : false
});
function converInteger(v, record){
    return record.data.isleaf=='1'?true : false;
    
}
Ext.define('DJ.System.product.ProductStructTreeStore', {
	extend : 'Ext.data.TreeStore',
	id : 'DJ.System.product.ProductStructTreeStore',
	fields : [ {
		name : 'id'
	}, {
		name : 'text'
	},{
		name : 'isleaf'
	},
	{
		name:'fnumber'
	},
		{ name : 'leaf',convert:converInteger}
	],
	proxy : {
		type : 'ajax',
		url : 'GetProductStructTree.do'
	},
	reader : {
		type : 'json'
	},
	autoLoad : false,
	nodeParam : 'id'
//	defaultRootId : '-1',
//	root: {
//		id      : '-1',
//		text    : '所有客户',		
//		expanded: true,
//		leaf: false
//	}
});
Ext.define('DJ.System.product.pCustomerStore', {
	extend : 'Ext.data.Store',
	id : 'DJ.System.product.pCustomerStore',
	fields : [ {
		name : 'fid'
	}, {
		name : 'fname'
	}],
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : 'GetCustomerList.do',
		reader : {
			
			type : 'json',
			root : 'data',
			totalProperty : 'total'
		}
	},
	autoLoad : false
});

