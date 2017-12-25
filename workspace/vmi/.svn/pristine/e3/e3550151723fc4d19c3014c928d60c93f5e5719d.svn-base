Ext.define('DJ.System.product.ProductreqallocationrulesList', {
	extend : 'Ext.c.GridPanel',
	title : "产品需求分配规则",
	id : 'DJ.System.product.ProductreqallocationrulesList',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'selectProductreqallocationruleses.do',
	Delurl : "deleteProductreqallocationrules.do",// DeleteCycles.do
	EditUI : "DJ.System.product.ProductreqallocationrulesEdit",// DJ.System.product.ProductCycleEdit

	// EditUI : "DJ.System.product.ProportionEdit",

	// custbar : [
	// {
	// // id : 'DelButton',
	// text : '比例分配',
	// height : 30,
	// handler : editProportion
	//						
	// }
	// ],
	// ,
	// myfilterfield : 'f.fname',
	// myfiltername : '产品名称',
	// myfilterable : true

	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
	},

	_setFieldsReadonly : function() {

		var timeF = Ext
				.getCmp("DJ.System.product.ProductreqallocationrulesEdit")
				.down("[name=fcreatetime]");
		var creatorF = Ext
				.getCmp("DJ.System.product.ProductreqallocationrulesEdit")
				.down("[name=fcreatorname]");

		timeF.setReadOnly(true);
		creatorF.setReadOnly(true);

	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件

	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件
		this._setFieldsReadonly();
	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件
	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
		// 可对编辑界面进行复制
		this._setFieldsReadonly();
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件
	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	fields : [{
		name : 'fid'
	}, {
		name : 'fcustomerid'
	}, {
		name : 'fsupplierid'
	}, {
		name : 'fcreatorid'
	}, {
		name : 'fcreatetime'
	}, {
		name : 'fcustomername',
		myfilterfield : 'tbc.fname',
		myfiltername : '客户名称',
		myfilterable : true
	}, {
		name : 'fsuppliername',
		myfilterfield : 'tss.fname',
		myfiltername : '制造商名称',
		myfilterable : true
	}, {
		name : 'fcreatorname'
	},{
		name : 'fisstock'
	},{ name:'fbacthstock'
	}],
	columns : {
		items : [Ext.create('DJ.Base.Grid.GridRowNum',{
			width : 30
		}), {
			'header' : 'fid',
			'dataIndex' : 'fid',
			hidden : true,
			hideable : false,
			sortable : true
		}, {
			'header' : '供应商名称',
			'dataIndex' : 'fsuppliername',
			sortable : true
		}, {
			text : '客户名称',
			dataIndex : 'fcustomername',
//			width : 150,
			sortable : true
		}, {
			text : '创建人',
			dataIndex : 'fcreatorname',
			sortable : true
		}, {
			text : '创建时间',
			dataIndex : 'fcreatetime',
//			width : 150,
			sortable : true
		}, {
			text : '是否快速下单',
			dataIndex : 'fisstock',
			sortable : true,
			renderer : function(value) {
			if (value ==="1") {
				return '是';
			} else {
				return '否';
			}
		}
			}, {
			text : '批量备货',
			dataIndex : 'fbacthstock',
			sortable : true,
			renderer : function(value) {
			if (value ==="1") {
				return '是';
			} else {
				return '否';
			}
		}

		}],
		defaults : {
			width : 200
		}
	},
	selModel : Ext.create('Ext.selection.CheckboxModel')
});