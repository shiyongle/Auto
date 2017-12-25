Ext._createDaySelectBox = function(){
	var monthSelectBox = [];
	for(var i=1;i<32;i++){
		monthSelectBox.push({
			text: i,
			value: i
		});
	}
	return monthSelectBox;
}
Ext.define('DJ.cardboard.system.ProducePlanEdit',{
	extend : 'Ext.Window',
	title: '排产计划编辑界面',
	autoShow:  true,
	width: 1000,
	height: 450,
	modal: true,
	requires: 'Ext.ux.form.SelectBox',
	layout: 'border',
	isWeek: true,
	weeks: null,		//开机星期
	nonMonthes: null,	//不开机日期
	monthes: null,		//开机日期
	saveData: function(index,selectBox){
		selectBox.toggleSelect();
		var me = this,
			value = selectBox.value,
			weeks = me.weeks,
			nonMonthes = me.nonMonthes,
			monthes = me.monthes,panel,selectBoxes;
		if(index==1){
			me.isWeek = true;
			selectBox.isSelected ? weeks[value] = value : weeks[value] = null;
			panel = me.down('panel[region=south]');
			selectBoxes = panel.query('selectbox');
			selectBoxes.forEach(function(item,index){
				if(item.isSelected){
					item.removeSelect();
				}
			})
			me.monthes = [];
		}else if(index==2){
			me.isWeek = true;
			selectBox.isSelected ? nonMonthes[value] = value : nonMonthes[value] = null;
			panel = me.down('panel[region=south]');
			selectBoxes = panel.query('selectbox');
			selectBoxes.forEach(function(item,index){
				if(item.isSelected){
					item.removeSelect();
				}
			})
			me.monthes = [];
		}else if(index==3){
			me.isWeek = false;
			selectBox.isSelected ? monthes[value] = value : monthes[value] = null;
			panel = me.down('panel[region=center]');
			selectBoxes = panel.query('selectbox');
			selectBoxes.forEach(function(item,index){
				if(item.isSelected){
					item.removeSelect();
				}
			})
			me.weeks = [];
			me.nonMonthes = [];
		}
	},
	getData: function(){
		var me = this,data = {};
		data.fsupplierid = me.fsupplierid;
		data.fproductid = me.fproductid;
		data.fcreatetime = me.fcreatetime;
		data.fid = me.fproduceplanid;
		if(me.isWeek){
			data.fisweek = '1';
			data.fweek = me.weeks.join('');		//格式为 \[0-6]+\ ，周日为0
			data.fnoday = me.nonMonthes.join('_').replace(/_+/g,'_').replace(/(^_|_$)/g,'');
		}else{
			data.fisweek = '0';
			data.fday = me.monthes.join('_').replace(/_+/g,'_').replace(/(^_|_$)/g,'');
		}
		return data;
	},
	tbar: [{
		text: '保存',
		iconCls: 'save',
		handler: function(){
			var win = this.up('window'),
			data = win.getData();
			if(!(data.fweek||data.fnoday) && !data.fday){
				Ext.Msg.alert('提示','请先选择数据再保存！');
				return;
			}
			Ext.Ajax.request({
				url: 'saveProducePlan.do',
				params: {
					ProducePlan: Ext.encode(data)
				},
				success: function(res){
					var obj = Ext.decode(res.responseText);
					if(obj.success){
						djsuccessmsg('保存成功！');
						win.parentGrid.doLoad(win.fsupplierid);
						win.close();
					}else{
						Ext.Msg.alert('错误',obj.msg);
					}
				}
			});
			
		}
	},{
		text: '取消',
		iconCls: 'cancel',
		handler: function(){
			this.up('window').close();
		}
	}],
	items: [{
		title: '按周排产',
		region: 'center',
		flex: 1,
		frame: true,
		layout: {
	        type: 'hbox',
	        align: 'stretch'
	    },
		items: [{
			flex: 1,
			layout: 'absolute',
			margin: '0 15 0 0',
			items: [{
				xtype: 'displayfield',
				fieldLabel: '开机星期',
				labelStyle: 'font-weight: bold',
				x: 20,
				y: 10
			},{
				xtype: 'fieldcontainer',
				x: 20,
				y: 40,
				defaultType: 'selectbox',
				defaults: {
					style: {
						borderLeft: 'solid 1px',
						borderTop: 'solid 1px',
						borderBottom: 'solid 1px',
						padding: '3px 8px'
					},
					listeners: {
						render: function(){
							var me = this;
							me.getEl().on('click',function(){
								me.up('window').saveData(1,me);
							})
						}
					}
				},
				items: [{
					text: '周一',
					value: 1
				},{
					text: '周二',
					value: 2
				},{
					text: '周三',
					value: 3
				},{
					text: '周四',
					value: 4
				},{
					text: '周五',
					value: 5
				},{
					text: '周六',
					value: 6
				},{
					text: '周日',
					value: 0,
					style: {
						border: 'solid 1px',
						padding: '3px 8px'
					}
				}]
			}]
		},{
			flex: 1,
			layout: 'absolute',
			items: [{
				xtype: 'displayfield',
				fieldLabel: '不开机日期',
				labelStyle: 'font-weight: bold',
				x: 20,
				y: 10
			},{
				xtype: 'fieldcontainer',
				x: 20,
				y: 40,
				anchor: '90% 90%',
				style: {
					border: '1px solid'
				},
				defaultType: 'selectbox',
				defaults: {
					style: {
						padding: '6px 12px',
						display: 'block',
						'float': 'left',
						border: 'solid 1px #fff'
					},
					selectCls: 'selectbox-select2',
					listeners: {
						render: function(){
							var me = this;
							me.getEl().on('click',function(){
								me.up('window').saveData(2,me);
							})
						}
					}
				},
				items: Ext._createDaySelectBox()
			}]
		}]
	},{
		title: '按天排产',
		region: 'south',
		flex: 1,
		layout: 'absolute',
		items: [{
			xtype: 'displayfield',
			fieldLabel: '开机日期',
			labelStyle: 'font-weight: bold',
			x: 20,
			y: 10
		},{
			xtype: 'fieldcontainer',
			x: 20,
			y: 40,
			anchor: '50% 90%',
			style: {
				border: '1px solid #000'
			},
			defaultType: 'selectbox',
			defaults: {
				style: {
					padding: '6px 12px',
					display: 'block',
					'float': 'left',
					border: 'solid 1px #fff'
				},
				selectCls: 'selectbox-select2',
				listeners: {
					render: function(){
						var me = this;
						me.getEl().on('click',function(){
							me.up('window').saveData(3,me);
						})
					}
				}
			},
			items: Ext._createDaySelectBox()
		}]
	}]
});