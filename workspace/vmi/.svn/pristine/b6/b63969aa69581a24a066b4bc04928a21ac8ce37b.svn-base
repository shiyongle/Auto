Ext.define('Ext.picker.DoubleDate',{
	extend: 'Ext.container.Container',
	alias: 'widget.doubledatapicker',
	width: 480,
	style: 'background-color:#fff',
	defaultType: 'container',
	border: true,
	padding: 5,
	defaults: {
		layout: 'hbox'
	},
	dateConfig: {},
	initComponent: function(){
		var me = this;
		Ext.apply(this,{
			items: [{
				defaults: Ext.apply({
					flex: 1,
					margin: 5
				},me.dateConfig),
				items: [{
					xtype: 'datepicker',
					itemId: 'left'
				},{
					xtype: 'datepicker',
					itemId: 'right'
				}]
			},{
				defaultType: 'button',
				defaults: {
					flex: 1,
					height: 34,
					margin: 5
				},
				items: [{
					text: '昨天',
					handler: function(){
						me.doYesterday();
					}
				},{
					text: '今天',
					handler: function(){
						me.doToday();
					}
				},{
					text: '本周',
					handler: function(){
						me.doWeek();
					}
				},{
					text: '上周',
					handler: function(){
						me.doLastWeek();
					}
				},{
					text: '本月',
					handler: function(){
						me.doMonth();
					}
				},{
					text: '上月',
					handler: function(){
						me.doLastMonth();
					}
				},{
					text: '全部',
					handler: function(){
						me.doAll();
					}
				}]
			},{
				defaultType: 'button',
				defaults: {
					height: 35,
					flex: 1,
					margin: 5
				},
				items: [{
					text: '确    定',
					handler: function(){
						me.doConfirm();
					}
				},{
					text: '取    消',
					handler: function(){
						me.doCancel();
					}
				}]
			}]
		});
		this.callParent(arguments);
		this.leftPicker = this.down('datepicker[itemId=left]');
		this.rightPicker = this.down('datepicker[itemId=right]');
	},
	doYesterday: function(){
		this.comboValue = this.formatDate(Ext.Date.subtract(new Date(), Ext.Date.DAY, 1));
		this.onSelect(this.comboValue + this.separator + this.comboValue);
	},
	doToday: function(){
		this.comboValue = this.formatDate(new Date());
		this.onSelect(this.comboValue + this.separator + this.comboValue);
	},
	doWeek: function(){
		var now = new Date();
		this.comboValue = this.formatDate(Ext.Date.subtract(now, Ext.Date.DAY, now.getDay()-1)) + this.separator + this.formatDate(Ext.Date.add(now, Ext.Date.DAY, 7 - now.getDay()));
		this.onSelect(this.comboValue);
	},
	doLastWeek: function(){
		var now = new Date();
		this.comboValue = this.formatDate(Ext.Date.subtract(now, Ext.Date.DAY, now.getDay()+6)) + this.separator + this.formatDate(Ext.Date.subtract(now, Ext.Date.DAY, now.getDay()));
		this.onSelect(this.comboValue);
	},
	doMonth: function(){
		var now = new Date(),
			nextMonth = Ext.Date.add(now, Ext.Date.MONTH, 1);
		this.comboValue = this.formatDate(Ext.Date.subtract(now, Ext.Date.DAY, now.getDate()-1)) + this.separator + this.formatDate(Ext.Date.subtract(nextMonth, Ext.Date.DAY, now.getDate()));
		this.onSelect(this.comboValue);
	},
	doLastMonth: function(){
		var now = new Date(),
		lastMonth = Ext.Date.subtract(now, Ext.Date.MONTH, 1);
		this.comboValue = this.formatDate(Ext.Date.subtract(lastMonth, Ext.Date.DAY, lastMonth.getDate()-1)) + this.separator + this.formatDate(Ext.Date.subtract(now, Ext.Date.DAY, now.getDate()));
		this.onSelect(this.comboValue);
	},
	doAll: function(){
		this.onSelect('');
	},
	doConfirm: function(){
		var leftValue = this.leftPicker.getValue(),
			rightValue = this.rightPicker.getValue();
		if(leftValue>rightValue){
			this.comboValue = '';
		}else{
			this.comboValue = this.formatDate(leftValue) + this.separator + this.formatDate(rightValue);
		}
		this.onSelect(this.comboValue);
	},
	doCancel: function(){
		this.collapse();
	},
	collapse: Ext.emptyFn,
	onSelect: Ext.emptyFn,
	formatDate : function(date){
        return Ext.isDate(date) ? Ext.Date.dateFormat(date, this.format) : date;
    }
});
//DoubleDateField
Ext.define('Ext.ux.form.DoubleDateField', {
    extend:'Ext.form.field.Picker',
    alias: 'widget.doubledatefield',
    matchFieldWidth: false,
    format: 'Y-m-d',
    separator: ' 到 ',
    editable: false,
    getValues: function(){
    	var val = this.getValue();
    	if(val){
    		return val.split(this.separator);
    	}
    	return val;
    },
    getSubmitValues: function(){
    	var vals = this.getValues();
    	vals[0] = vals[0] + ' 00:00:00';
    	vals[1] = vals[1] + ' 23:59:59';
    	return vals;
    },
    onSelect: function(d) {
        var me = this;
        me.setValue(d);
        me.fireEvent('select', me, d);
        me.collapse();
    },
    onExpand: function() {
        var value = Ext.String.trim(this.getValue()),
        	picker = this.picker,
        	format = this.format,
        	separator = this.separator,
        	dt,leftDate,rightDate,dateValues;
        if(!value){
        	leftDate = rightDate = new Date();
        }else{
        	dt = Ext.Date.parse(value, format);
        	if(dt){
        		leftDate = rightDate = dt;
        	}else{
        		dateValues = value.split(separator);
        		leftDate = Ext.Date.parse(dateValues[0], format);
        		rightDate = Ext.Date.parse(dateValues[1], format);
        		if(!(leftDate&&rightDate)){
        			leftDate = rightDate = new Date();
        		}
        	}
        }
        picker.leftPicker.setValue(leftDate);
        picker.rightPicker.setValue(rightDate);
    },
    createPicker: function() {
        var me = this,
            format = Ext.String.format;

        return new Ext.picker.DoubleDate({
        	pickerField: me,
            floating: true,
            focusOnShow: true,
            collapse: Ext.Function.bind(me.collapse,me),
            onSelect: Ext.Function.bind(me.onSelect,me),
            dateConfig: {
            	update: function(date,forceRefresh){
        	    	this.value = Ext.Date.clearTime(date, true);	//更改update方法为setValue方法，点击键盘导航键时更改值
        		    var me = this,
        	        active = me.activeDate;
        	        if (me.rendered) {
        	            me.activeDate = date;
        	            if(!forceRefresh && active && me.el && active.getMonth() == date.getMonth() && active.getFullYear() == date.getFullYear()){
        	                me.selectedUpdate(date, active);
        	            } else {
        	                me.fullUpdate(date, active);
        	            }
        	        }
        	        return me;
        		}
            },
            format: me.format,
            separator: me.separator
        });
    }
});