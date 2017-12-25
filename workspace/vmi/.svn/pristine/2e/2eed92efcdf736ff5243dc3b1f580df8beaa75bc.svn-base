/**
 * 
 * 简单图片列
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2015-6-9 下午4:18:36
 * 
 */
Ext.define('Ext.ux.grid.column.MySimpleImageColumn', {
	extend : 'Ext.grid.column.Column',

	alias : ['widget.mysimpleimagecolumn'],

	requires : ['Ext.XTemplate'],

	/**
	 * 图片地址域
	 */
	imgUrlField : 'imgUrl',

	actionUrl : '',
	
	imgClickAble : true,
	
	onclick : '',
	
	/**
	 * @cfg {String/Ext.XTemplate} tpl An {@link Ext.XTemplate XTemplate}, or
	 *      an XTemplate *definition string* to use to process a
	 *      {@link Ext.data.Model Model}'s
	 *      {@link Ext.data.Model#persistenceProperty data} to produce a
	 *      column's rendered value.
	 */

	tpl : '<a href="{4}"><img onclick="{5}" src="{0}" width="{1}" height="{2}" alt="{3}"/></a>',

	/**
	 * @cfg {Object} renderer
	 * @hide
	 */

	/**
	 * @cfg {Object} scope
	 * @hide
	 */

	initComponent : function() {
		var me = this;

		if (me.imgClickAble) {

			me.tpl = Ext.String.format(me.tpl, me.imgUrlField, me.width,
					me.height || me.width || '', me.text || me.head,
					me.actionUrl, me.onclick);

		} else {

			var tplT = '<img onclick="{4}" src="{0}" width="{1}" height="{2}" alt="{3}"/>';

			me.tpl = Ext.String
					.format(tplT, me.imgUrlField, me.width, me.height
							|| me.width || '', me.text || me.head, me.onclick);

		}
		

		me.tpl = (!Ext.isPrimitive(me.tpl) && me.tpl.compile)
				? me.tpl
				: new Ext.XTemplate(me.tpl);
		// Set this here since the template may access any record values,
		// so we must always run the update for this column
		me.hasCustomRenderer = true;
		me.callParent(arguments);
	},

	defaultRenderer : function(value, meta, record) {
		var data = Ext.apply({}, record.data, record.getAssociatedData());
		return this.tpl.apply(data);
	}
});