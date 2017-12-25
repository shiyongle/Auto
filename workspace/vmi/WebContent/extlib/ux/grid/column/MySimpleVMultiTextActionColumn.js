/**
 * 
 * 垂直多文字动作列
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2015-6-9 下午4:18:36
 * 
 */
Ext.define('Ext.ux.grid.column.MySimpleVMultiTextActionColumn', {
	extend : 'Ext.grid.column.Column',

	alias : ['widget.mysimplevmultitextactioncolumn'],

	requires : ['Ext.XTemplate'],

	/**
	 * str
	 * 
	 * @type {Boolean}
	 */
	showCondition : "1==1",

	xStyle : '',

	textActions : [],

	divAlign : 'center',
	
	_obj : {

		// 文字
		text : '',
		condition : '',
		// 行为
		action : ''

	},

	/**
	 * @cfg {String/Ext.XTemplate} tpl An {@link Ext.XTemplate XTemplate}, or
	 *      an XTemplate *definition string* to use to process a
	 *      {@link Ext.data.Model Model}'s
	 *      {@link Ext.data.Model#persistenceProperty data} to produce a
	 *      column's rendered value.
	 */

	/**
	 * @cfg {Object} renderer
	 * @hide
	 */

	/**
	 * @cfg {Object} scope
	 * @hide
	 */

	buildTpl : function() {

		var me = this;

		var tpl = Ext.String.format('&lt;tpl if="{0}"&gt;', me.showCondition);

		tpl += Ext.String
				.format(
						'<table width="100%" height="{1}" border="0" cellspacing="10" style="',
						me.height || me.width || '')
				+ me.xStyle
				+ '"><tpl for="."><tr align="{[this.gainAlignWay(xindex, xcount)]}"><td><div align="'
				+ me.divAlign
				+ '"><a href="javascript:{action}();">{text}</a></div></td></tr></tpl></table>&lt;/tpl&gt;';

		var tplx = new Ext.XTemplate(tpl);

		tplx.gainAlignWay = function(xindex, xcount) {

			if (xcount == 1) {

				return 'center';

			} else if (xindex == 1) {

				return 'bottom';

			} else if (xindex == xcount) {

				return 'top';

			} else {

				return 'center';

			}

		};

		me.tpl = tplx.apply(me.textActions);

		
		me.tpl = Ext.htmlDecode(me.tpl);
	},

	initComponent : function() {
		var me = this;

		me.buildTpl();

		me.tpl = Ext.String.format(me.tpl, me.imgUrlField, me.width, me.height,
				me.text);

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