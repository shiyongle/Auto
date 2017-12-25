Ext.define('Ext.picker.DateMTime',{
	extend: 'Ext.picker.Date',
	beforeRender: function(){
		this.timeRadio = Ext.widget({
	        xtype: 'radiogroup',
	        ownerCt: this,
	        columns: 2,
	        style:'margin: 0 auto',
	        items: [
	            { boxLabel: '上午', name: 'time', inputValue: '1', checked: true},
	            { boxLabel: '下午', name: 'time', inputValue: '2'}
	        ]
	    });
		this.callParent(arguments);
	},
	renderTpl: [
        '<div id="{id}-innerEl" role="grid">',
            '<div role="presentation" class="{baseCls}-header">',
                 // the href attribute is required for the :hover selector to work in IE6/7/quirks
                '<a id="{id}-prevEl" class="{baseCls}-prev {baseCls}-arrow" href="#" role="button" title="{prevText}" hidefocus="on" ></a>',
                '<div class="{baseCls}-month" id="{id}-middleBtnEl">{%this.renderMonthBtn(values, out)%}</div>',
                 // the href attribute is required for the :hover selector to work in IE6/7/quirks
                '<a id="{id}-nextEl" class="{baseCls}-next {baseCls}-arrow" href="#" role="button" title="{nextText}" hidefocus="on" ></a>',
            '</div>',
            '<table id="{id}-eventEl" class="{baseCls}-inner" cellspacing="0" role="grid">',
                '<thead role="presentation"><tr role="row">',
                    '<tpl for="dayNames">',
                        '<th role="columnheader" class="{parent.baseCls}-column-header" title="{.}">',
                            '<div class="{parent.baseCls}-column-header-inner">{.:this.firstInitial}</div>',
                        '</th>',
                    '</tpl>',
                '</tr></thead>',
                '<tbody role="presentation"><tr role="row">',
                    '<tpl for="days">',
                        '{#:this.isEndOfWeek}',
                        '<td role="gridcell" id="{[Ext.id()]}">',
                            // the href attribute is required for the :hover selector to work in IE6/7/quirks
                            '<a role="presentation" hidefocus="on" class="{parent.baseCls}-date" href="#"></a>',
                        '</td>',
                    '</tpl>',
                '</tr></tbody>',
            '</table>',
            '<div id="{id}-footerEl1" class="{baseCls}-footer">{%this.renderTimeRadio(values, out)%}</div>',
            '<tpl if="showToday">',
                '<div id="{id}-footerEl" role="presentation" class="{baseCls}-footer">{%this.renderTodayBtn(values, out)%}</div>',
            '</tpl>',
        '</div>',
        {
            firstInitial: function(value) {
                return Ext.picker.Date.prototype.getDayInitial(value);
            },
            isEndOfWeek: function(value) {
                // convert from 1 based index to 0 based
                // by decrementing value once.
                value--;
                var end = value % 7 === 0 && value !== 0;
                return end ? '</tr><tr role="row">' : '';
            },
            renderTodayBtn: function(values, out) {
                Ext.DomHelper.generateMarkup(values.$comp.todayBtn.getRenderTree(), out);
            },
            renderMonthBtn: function(values, out) {
                Ext.DomHelper.generateMarkup(values.$comp.monthBtn.getRenderTree(), out);
            },
            renderTimeRadio: function(values, out) {
                Ext.DomHelper.generateMarkup(values.$comp.timeRadio.getRenderTree(), out);
            }
        }
    ],
    finishRenderChildren: function () {
    	var me = this;
        me.callParent();
        me.timeRadio.finishRender();
    },
    beforeDestroy: function(){
    	if(this.rendered) {
	    	Ext.destroy(this.timeRadio);
    	}
    	this.callParent();
    }
});
Ext.define('Ext.ux.form.DateTime2Field',{
	extend: 'Ext.form.field.Date',
	format: 'Y-m-d H:i:s',
	alias: 'widget.datetime2field',
	onSelect: function(m,val){
		if(m.timeRadio.getChecked()[0].boxLabel == '上午'){
			val.setHours(9);
		}else{
			val.setHours(14);
		}
		this.callParent(arguments);
	},
	onExpand: function(){
		var value = this.getValue();
		value = Ext.isDate(value) ? value : new Date();
        this.picker.setValue(value);
		if(value.getHours()<=12){
			this.picker.timeRadio.setValue({time:1});
		}else{
			this.picker.timeRadio.setValue({time:2});
		}
	},
	createPicker: function() {
        var me = this,
            format = Ext.String.format;

        return new Ext.picker.DateMTime({
            pickerField: me,
            ownerCt: me.ownerCt,
            renderTo: document.body,
            floating: true,
            hidden: true,
            focusOnShow: true,
            minDate: me.minValue,
            maxDate: me.maxValue,
            disabledDatesRE: me.disabledDatesRE,
            disabledDatesText: me.disabledDatesText,
            disabledDays: me.disabledDays,
            disabledDaysText: me.disabledDaysText,
            format: me.format,
            showToday: me.showToday,
            startDay: me.startDay,
            minText: format(me.minText, me.formatDate(me.minValue)),
            maxText: format(me.maxText, me.formatDate(me.maxValue)),
            listeners: {
                scope: me,
                select: me.onSelect
            },
            keyNavConfig: {
                esc: function() {
                    me.collapse();
                }
            }
        });
    }
});