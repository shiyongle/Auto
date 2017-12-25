
function converInteger(v, record){
    return record.data.isleaf=='1'?true : false;
    
}
Ext.define('DJ.System.Customer.CustomerTreeStore', {
	extend : 'Ext.data.TreeStore',
//	id : 'DJ.System.Customer.CustomerTreeStore',
	fields : [ {
		name : 'id'
	}, {
		name : 'text'
	},{
		name : 'isleaf'
	},
		{ name : 'leaf',convert:converInteger}
	],
	proxy : {
		type : 'ajax',
		url : 'GetCustomerTree.do',
		timeout:300000
	},
	
	reader : {
		type : 'json'
	},
	autoLoad : false,
	nodeParam : 'id',
	defaultRootId : '-1',
	root: {
		id      : '-1',
		text    : '所有客户',		
		expanded: true,
		leaf: false
	}
});



