Ext.define('DJ.System.bodyPape.BodyPapeEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : "DJ.System.bodyPape.BodyPapeEdit",
	modal : true,
	
	ctype : 'Bodypape',
	
	onload : function() {
		// 加载后事件，可以设置按钮，控件值等

	},
	title : "原纸编辑界面",
	width : 700,// 230, //Window宽度
	height : 320,// 137, //Window高度
	
	resizable : false,
	
	url : 'saveBodypape.do',
	infourl : 'gainBodypapeByID.do', // 指定界面数据获取，combobox根据name+"_"+valueField赋隐藏值，name+"_"+displayField赋显示值;在SQL查询的时候需要自己构建
	viewurl : 'gainBodypapeByID.do', // 查看状态数据源
	closable : true, // 关闭按钮，默认为true
	items : [{
		xtype : 'hidden',
		name : 'fid'
	}, {
		layout : 'column',
		baseCls : 'x-plain',
		items : [{
			bodyStyle : 'padding-top:5px;padding-left:5px;padding-right:5px',
			baseCls : 'x-plain',
			columnWidth : .5,
			layout : 'form',
			items : [{
				xtype : 'textfield',
				name : 'fname',
				fieldLabel : '原纸名称',
				allowBlank : false
//				,
//				blankText : '原纸名称不能为空'
					// ,
					// labelWidth : 70,
					// regex : /^([\u4E00-\u9FA5]|\w)*$/,//
					// /^[^,\!@#$%^&*()_+}]*$/,
					// regexText : "不能包含特殊字符"

					}, {
						xtype : 'numberfield',
						name : 'fgramweight',
						fieldLabel : '克重',
						allowBlank : false,

						allowDecimals : true,
						decimalPrecision : 2,

						minValue : 0

					}, {
						xtype : 'numberfield',
						name : 'fpopstrength',
						fieldLabel : '耐破',

						allowDecimals : true,
						decimalPrecision : 2,
						labelWidth : 70,
						minValue : 0

					}, {
						xtype : 'numberfield',
						name : 'fwaterabsorption',
						fieldLabel : '吸水',

						allowDecimals : true,
						decimalPrecision : 2,
						labelWidth : 70,
						minValue : 0

					}, {
						xtype : 'combo',
						name : 'fusecategory',
						fieldLabel : '适用类型',

						store : DJ.System.bodyPape.BodyPapeList.USE_CATEGORY_MAP,

						triggerAction : 'all',
						queryMode : 'local',

						forceSelection : true,
						typeAhead : true,
						value : 1

					}]
		}, {
			bodyStyle : 'padding-top:5px;padding-left:5px;padding-right:5px',
			baseCls : 'x-plain',
			columnWidth : .5,
			layout : 'form',
			items : [{
				labelWidth : 70,
				xtype : 'textfield',
				name : 'fmanufacturer',
				fieldLabel : '生产厂家',
				allowBlank : false,
				blankText : '生产厂家不能为空',
				regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
				regexText : "不能包含特殊字符"
			},

			{
				xtype : 'numberfield',
				name : 'ferror',
				fieldLabel : '误差±',

				allowDecimals : true,
				decimalPrecision : 2,
				labelWidth : 70,
				minValue : 0

			}, {
				xtype : 'numberfield',
				name : 'fhardfold',
				fieldLabel : '耐折',

				allowDecimals : true,
				decimalPrecision : 2,
				labelWidth : 70,
				minValue : 0

			}

			, {
				labelWidth : 70,
				xtype : 'textfield',
				name : 'fbasepapercategory',
				fieldLabel : '原纸类别',
//				allowBlank : false,
//				blankText : '名称不能为空',
				regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
				regexText : "不能包含特殊字符"
			}]
		}]
	}, {
		layout : 'form',
		bodyStyle : 'padding-left:5px;padding-right:5px',
		baseCls : 'x-plain',
		items : [{
			labelWidth : 100,
			height : 100,
			xtype : 'textarea',
			name : 'fremark',
			fieldLabel : '备注',
			regex : /^([\u4E00-\u9FA5]|\w)*$/,// /^[^,\!@#$%^&*()_+}]*$/,
			regexText : "不能包含特殊字符"

		}]
	}],
	bodyStyle : "padding-top:5px;padding-left:15px",
	listeners : {}

});