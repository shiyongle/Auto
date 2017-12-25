Ext.define('DJ.System.note.NoteContainer',

{

	extend : 'Ext.container.Container',

	alias : 'widget.notecontainer',

	gainFields : function(callback) {

		var me = this;

		var fieldsSelector = ['textareafield[name=fcontent]',
				'textfield[name=fuserName]', 'textfield[name=fphone]'];

		Ext.each(fieldsSelector, function(ele, index, all) {

			// me.down(ele).setReadOnly( readOnly );

			callback(me.down(ele), index);

		});

	},

	setReadOnlyF : function(readOnly) {

		var me = this;

		me.gainFields(function(field) {

			field.setReadOnly(readOnly);

		});

		// var fieldsSelector =
		// ['textfield[name=fuserName]','textfield[name=fphone]','textareafield[name=fcontent]'];
		//			
		// Ext.each(fieldsSelector, function(ele, index, all) {
		//				
		// me.down(ele).setReadOnly( readOnly );
		//				
		// } );

	}

	,

	setValuesF : function(values) {

		var me = this;

		me.gainFields(function(field, index) {

			field.setValue(values[index]);

		});

	},
	
	getValuesF : function() {
		
		var values = [];

		var me = this;

		me.gainFields(function(field, index) {

			values.push(field.getValue());

		});
		
		return values;
	},

	layout : {
		align : 'stretch',
		type : 'vbox',

		defaultMargins : {
			top : 5,
			right : 5,
			bottom : 5,
			left : 5
		}

	},

	initComponent : function() {
		var me = this;

		me.items = [{
			name : "fcontent",
//			xtype : 'textareafield',
			xtype : 'textareatranslatebrauto',
			
			flex : 5,
			enableKeyEvents : true,
			allowBlank : false,
			listeners : {

				keypress : function(com, e, eOpts) {

					if (e.ctrlKey && e.getKey() == 10) {

						var saveBtn = com.up("window")
								.down("button[text=保  存]");

						saveBtn.handler.call(saveBtn);

					}

				}

			}

		}, {
			xtype : 'container',
			flex : 1,
			layout : {
				type : 'hbox',
				defaultMargins : {
					// top : 5,
					right : 5,
					// bottom : 5,
					left : 5
				}
			},
			items : [{
				xtype : 'textfield',
				name : "fuserName",
				flex : 1,
				fieldLabel : '联系人',
				allowBlank : false,
				value : cookiemanager.get("username")
					// ,
					// blankText : function() {
					//
					// return this.fieldLabel + "为空";
					//
					// }
					}, {
						name : "fphone",
						xtype : 'textfield',
						flex : 1,
						fieldLabel : '联系电话',
						allowBlank : false
					// ,
					// blankText : function() {
					//
					// return this.fieldLabel + "为空";
					//
					// }
					}]
		}, {

			xtype : 'hidden',
			name : 'fid'

		}, {

			xtype : 'hidden',
			name : 'fprenoteMsg'

		}, {

			xtype : 'hidden',
			name : 'fpreNote',
			value : "-1"

		}, {

			xtype : 'hidden',
			name : 'fids',
			value : "-1"

		}, {

			xtype : 'hidden',
			name : 'replyFids',
			value : "-1"

		}

		]

		me.callParent(arguments);

	}

})