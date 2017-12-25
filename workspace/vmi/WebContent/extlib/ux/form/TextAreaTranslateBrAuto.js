/**
 * 自转义<br/>，TextArea
 * 
 * @author ZJZ（447338871@qq.com）
 * 
 * @version 0.1 2015-1-24 上午11:24:06
 * 
 */
Ext.define("Ext.ux.form.TextAreaTranslateBrAuto", {

	extend : 'Ext.form.field.TextArea',

	alias : 'widget.textareatranslatebrauto',

	translateBrAuto : true,

	statics : {
	
		translateBr : function(com, newValue){
		
			if (com.readOnly && com.translateBrAuto) {

				var tedV = newValue.replace(/<br\/>/g, String.fromCharCode(10));

				return tedV;
			}
			
			return Ext.emptyString;
		}
		
	},
	
//	listeners : {
//
//		change : function(com, newValue, oldValue, eOpts) {
//
//			Ext.ux.form.TextAreaTranslateBrAuto.translateBr(com, newValue);
//
//		},
//		
//		beforeshow : function( com, eOpts ) {
//		
//			Ext.ux.form.TextAreaTranslateBrAuto.translateBr(com, com.getValue());
//			
//		},
//		show: function( com, eOpts ) {
//		
//			Ext.ux.form.TextAreaTranslateBrAuto.translateBr(com, com.getValue());
//			
//		}
//	},

	setValue : function(value) {
	
		var me = this;
		
		var valueT = Ext.ux.form.TextAreaTranslateBrAuto.translateBr(me, value);
		
		return me.callParent([valueT]);
		
	},
	
	initComponent : function() {

		var me = this;

		
		
		
		me.callParent(arguments);
	}
});