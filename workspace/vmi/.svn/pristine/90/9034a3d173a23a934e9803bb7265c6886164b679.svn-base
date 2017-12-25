Ext.define('DJ.quickOrder.orderInfo.simpleCompnent.StateShowerEle', {
	extend : 'Ext.container.Container',

	alias : 'widget.stateshowerele',

	requires : ['Ext.form.Label', 'Ext.form.field.Radio'],

	stateText : '',

	countText : '',
	indexText : '',

	setTimeS : function(timeS, isLater) {

		var me = this;

		var timeL = me.down('label[itemId=TimeLable]');

		if (isLater) {

			timeL.setText(Ext.String.format('<font color = "red">{0}</font>',
					timeS),false);

		} else {

			timeL.setText(timeS);

		}

		var radioF = me.down('radiofield[itemId=radioDoneLable]');

		radioF.setValue(true);

	}

	,

	clearState : function() {

		var me = this;

		var timeL = me.down('label[itemId=TimeLable]');

		timeL.setText('');

		var radioF = me.down('radiofield[itemId=radioDoneLable]');

		radioF.setValue(false);

	},

	height : 100,
	width : 100,

	layout : {
		type : 'vbox',
		align : 'center'
	},

	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [{
				itemId : 'stateLable',
				xtype : 'label',
				flex : 1,
				text : me.stateText
			}, {
				itemId : 'radioDoneLable',
				xtype : 'radiofield',
				flex : 1,
				boxLabel : me.indexText,
				readOnly : true
			}, {
				itemId : 'TimeLable',
				xtype : 'label',
				flex : 1,
				text : ''
			}]
		});

		me.callParent(arguments);
	}

});

Ext.define('DJ.quickOrder.orderInfo.simpleCompnent.StateShower', {
	extend : 'Ext.container.Container',

	requires : ['Ext.container.Container', 'Ext.toolbar.Spacer',
			'Ext.form.Label'],

//	autoShow : true,
	height : 120,
	width : 700,
//	closable : false,

	header : false,

	layout : {
		type : 'vbox',
		align : 'stretch'
	},

	resizable : false,
	
	// frame : true,

	// style : {
	// background : 'white'
	// },

	setTimes : function(times) {

		var me = this;

		var stateshowereles = me.query('stateshowerele');
        var i = 0;
		Ext.each(stateshowereles, function(ele, index, all) {
				var issueItems;
				issueItems = Ext.Array.filter(times,function(item){
					if(item.fstate == ele.countText){
						return true;
				}
				});
				if(issueItems.length>0){
						ele.setTimeS(issueItems[0].time,issueItems[0].isLater);	
				}			
				else{
						ele.clearState();
				}		
		});

	},

	initComponent : function() {
		var me = this;

		Ext.applyIf(me, {
			items : [{
				xtype : 'container',
				flex : 1,
				layout : {
					type : 'hbox',
					align : 'middle'
				},

				items : [{

					xtype : 'stateshowerele',
					stateText : '未接收',
					flex : 1,
					indexText : '1',
					countText : 0

				}, {

					xtype : 'stateshowerele',
					stateText : '已接收',
					flex : 1,
					indexText : '2',
					countText : 1

				}, {

					xtype : 'stateshowerele',
					stateText : '已入库',
					flex : 1,
					indexText : '3',
					countText : 4

				}, {

					xtype : 'stateshowerele',
					stateText : '部分发货',
					flex : 1,
					indexText : '4',
					countText : 5

				}, {

					xtype : 'stateshowerele',
					stateText : '全部发货',
					flex : 1,
					indexText : '5',
					countText : 6

				}]
			}
//			, {
//				xtype : 'container',
//				height : 30,
//				layout : {
//					type : 'hbox',
//					align : 'stretch'
//				},
//				items : [{
//					xtype : 'tbspacer',
//					flex : 1
//				}, {
//					xtype : 'label',
//					flex : 9,
//					html : '<hr />'
//				}, {
//					xtype : 'label',
//					flex : 1,
//					text : '〉'
//				}]
//			}
			]
		});

		me.callParent(arguments);
	}

});

//wint = Ext.create('DJ.quickOrder.orderInfo.simpleCompnent.StateShower');
