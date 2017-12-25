Ext.require("DJ.tools.common.MyCommonToolsZ");

Ext.define('DJ.System.bodyPape.BodyPapeList', {
	extend : 'Ext.c.GridPanel',
	title : "原纸",
	id : 'DJ.System.bodyPape.BodyPapeList',
	pageSize : 50,
	closable : true,// 是否现实关闭按钮,默认为false
	url : 'gainBodypapes.do',
	Delurl : "deleteBodypapes.do",
	EditUI : "DJ.System.bodyPape.BodyPapeEdit",

	mixins : ['DJ.tools.grid.MySimpleGridMixer',
			'DJ.tools.grid.mixer.MyGridSearchMixer'],

	statics : {

		USE_CATEGORY_MAP : [['1', '面纸'], ['2', '里纸'], ['3', '瓦纸'], ['4', '芯纸']]

	},

	// exporturl:"outWarehousetoexcel.do",
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等
		this._hideButtons(['viewbutton', 'querybutton', 'exportbutton']);
	},
	Action_BeforeAddButtonClick : function(EditUI) {
		// 新增界面弹出前事件
	},
	Action_AfterAddButtonClick : function(EditUI) {
		// 新增界面弹出后事件

	},
	Action_BeforeEditButtonClick : function(EditUI) {
		// 修改界面弹出前事件

	},
	Action_AfterEditButtonClick : function(EditUI) {
		// 修改界面弹出后事件
		// 可对编辑界面进行复制
	},
	Action_BeforeDelButtonClick : function(me, record) {
		// 删除前事件

	},
	Action_AfterDelButtonClick : function(me, record) {
		// 删除后事件
	},
	custbar : [{

		text : '启用',
		handler : function() {

			var me = this;

			me.statics.enable(me, 1);

		},
		statics : {

			enable : function(me, enable) {

				var grid = me.up("grid");

				var items = MyCommonToolsZ.pickSelectItems(grid);

				if (items == -1) {

					return;

				}

				var ids = [];

				Ext.each(items, function(ele, index, all) {

					ele.set("fenable", enable);

					ids.push(ele.get('fid'));

				});

				Ext.Ajax.request({
					timeout : 6000,

					params : {

						fids : ids.join(","),
						enable : enable

					},

					url : "enableBodyPape.do",
					success : function(response, option) {

						var obj = Ext.decode(response.responseText);
						if (obj.success == true) {

						} else {
							Ext.MessageBox.alert('错误', obj.msg);
						}

					}
				});

			}

		}

	}, {

		text : '禁用',
		handler : function() {
			var me = this;

			var preButon = me.previousNode("button[text=启用]");

			preButon.statics.enable(me, 0);

		}

	}, {
		xtype : 'mysimpleuploadexcelwinbutton',
		uploadUrl : 'uploadBodyPapeFile.do',
		text : 'EXCEL文件导入'
	}, {
		text : '导入模版下载',
		handler : function() {
			window.open('downloadBodyPapeFile.do', '下载模板');
		}

	}, {

		xtype : 'mymixedsearchbox',
		condictionFields : ['fname', 'fmanufacturer', 'fgramweight',
				'fpopstrength', 'fwaterabsorption', 'fhardfold'],
		useDefaultfilter : true,
		filterMode : true

	}, {

		xtype : 'mysimplesearchercombobox',
		filterMode : true,
		fields : [{

			displayName : '面纸',
			trueValue : '1',
			field : 'fusecategory'

		}, {

			displayName : '里纸',
			trueValue : '2',
			field : 'fusecategory'
		}, {

			displayName : '瓦纸',
			trueValue : '3',
			field : 'fusecategory'

		}, {

			displayName : '芯纸',
			trueValue : '4',
			field : 'fusecategory'
		}, {

			displayName : '启用',
			trueValue : '1',
			field : 'fenable'

		}, {

			displayName : '禁用',
			trueValue : '0',
			field : 'fenable'
		}]

	}],
	fields : [{
		name : 'fid'
	}, 'fname', 'fmanufacturer', {

		name : 'fgramweight',
		type : 'float'

	}, {

		name : 'ferror',
		type : 'float'

	}, {

		name : 'fpopstrength',
		type : 'float'

	}, {

		name : 'fhardfold',
		type : 'float'

	}, {

		name : 'fwaterabsorption',
		type : 'float'

	},

	'fbasepapercategory', {
		name : 'fusecategory',
		type : 'int'
	}, 'fremark', {
		name : 'fenable',
		type : 'int'
	}, 'fcreatetime', 'fupdatetime'],
	columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
		'header' : 'fid',
		'dataIndex' : 'fid',
		hidden : true,
		hideable : false,
		sortable : true
	}, {
		'header' : '原纸名称',
		'dataIndex' : 'fname',
		sortable : true
	}, {
		'header' : '生产厂家',
		dataIndex : 'fmanufacturer',
		sortable : true

	}, {
		'header' : '克重',
		dataIndex : 'fgramweight',
		width : 50,
		sortable : true

	}, {
		'header' : '误差',
		dataIndex : 'ferror',
		width : 50,
		sortable : true,
		renderer : function(value) {
			return '±' + value;
		}

	}, {
		'header' : '耐破',
		dataIndex : 'fpopstrength',
		width : 50,
		sortable : true

	}, {
		'header' : '耐折',
		dataIndex : 'fhardfold',
		width : 50,
		sortable : true

	}, {
		'header' : '吸水',
		dataIndex : 'fwaterabsorption',
		width : 50,
		sortable : true

	}, {
		'header' : '原纸类别',
		dataIndex : 'fbasepapercategory',
		// width : 70,
		sortable : true

	}, {
		'header' : '适用类型',
		dataIndex : 'fusecategory',
		width : 70,
		sortable : true,
		renderer : function(value) {

			return value == null || value == 0
					? ''
					: DJ.System.bodyPape.BodyPapeList.USE_CATEGORY_MAP[value
							- 1][1];
		}

	}, {
		'header' : '状态',
		dataIndex : 'fenable',
		width : 50,
		sortable : true,
		renderer : function(value) {
			if (value == 1) {
				return '<font color = "green">启用</font>';
			} else {
				return '<font color = "red">禁用</font>';
			}
		}

	}, {
		'header' : '备注',
		dataIndex : 'fremark',
		width : 150,
		sortable : true

	}, {
		'header' : '创建时间',
		dataIndex : 'fcreatetime',
		width : 150,
		sortable : true

	}, {
		'header' : '修改时间',
		dataIndex : 'fupdatetime',
		width : 150,
		sortable : true

	}],
	selModel : Ext.create('Ext.selection.CheckboxModel')
})