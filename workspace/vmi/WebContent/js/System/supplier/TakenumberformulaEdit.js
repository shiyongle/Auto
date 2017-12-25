Ext.define('DJ.System.supplier.TakenumberformulaEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.System.supplier.TakenumberformulaEdit",
	modal : true,
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等

	},
	title : " 取数公式编辑界面",
	width : 400,// 230, //Window宽度
	height : 300,// 137, //Window高度
	resizable : true,
	url : 'saveTakenumberformula.do',
	infourl : 'selectTakenumberformulaByID.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	viewurl : 'selectTakenumberformulaByID.do', // 查看状态数据源
	closable : true, // 关闭按钮，默认为true
	ctype : "Takenumberformula",
	initComponent : function() {
		Ext.apply(this, {
			items : [{
				layout : "column",
				baseCls : "x-plain",

				items : [{// title:"列1",
					baseCls : "x-plain",
					columnWidth : 1,

					defaults : {

						labelWidth : 70,
						width : 300,
						labelAlign : "left"

					},

					// bodyStyle :
					// 'padding-top:0px;padding-left:5px;padding-right:5px',
					items : [{
						name : 'fid',
						xtype : 'textfield',

						hidden : true
					}, {
						name : 'fname',
						xtype : 'textfield',
						fieldLabel : '名称',
						allowBlank : false
					}, {
						name : 'fnumber',
						xtype : 'textfield',
						fieldLabel : '编码',
						allowBlank : false,
						blankText : '编码不能为空',
						regex : /^([\u4E00-\u9FA5]|\w|[@.()\-])*$/,// /^[^,\!@#$%^&*()_+}]*$/,
						regexText : "不能包含特殊字符"

					}, {
						name : 'fsqlStatement',
						xtype : 'textarea',
						fieldLabel : '语句',
						allowBlank : false,
						rows : 10
					}]
				}]

			}]
		}), this.callParent(arguments);
	},
	bodyStyle : "padding-top:5px;padding-left:30px"
});
