/**
 * 配置在grid的toolbar中，支持显示历史记录的查询框，必配项：
 * storageId - 本地存储localStorage的id (String)
 * supportFields - 提供模糊查询的字段 (Array)
 * 可选项：
 * handleRequest - 处理查询方式
 */
Ext.define('Ext.ux.form.TipComboBox', {
	extend: Ext.form.field.ComboBox,
	alias: 'widget.tipcombo',
	hideTrigger: true,
	queryMode: 'local',
	displayField: 'query',
	valueField: 'id',
	enableKeyEvents: true,
	supportFields: null,
	handleSelect: function(){
		var value = this.getValue();
		this.handleRequest(value);
		var store = this.store;
		if(value && value.length>1 && !store.findRecord('query',value,null,null,null,true)){
			store.add({query:value});
		}
		store.sync();
	},
	handleRequest: function(value){
		if(!this.supportFields){
			throw '未配置supportFields';
		}
		value = (value == null ? '' : value);
		var filter = [],
			supportFieldsLength = this.supportFields.length,
			mask=[];
		for(var i=0;i<supportFieldsLength;i++){
			filter.push({
				myfilterfield : this.supportFields[i],
				CompareType : " like ",
				type : "string",
				value : value
			});
			mask.push(' #'+i+' ');
		}
		var gridStore = this.up('grid').getStore();
		gridStore.setDefaultfilter(filter);
		gridStore.setDefaultmaskstring(mask.join('or'));
		gridStore.loadPage(1);
	},
	listeners:{},
	doFocus: Ext.emptyFn,
	doChange: Ext.emptyFn,
	doKeydown: Ext.emptyFn,
	doSelect: Ext.emptyFn,
	definedListeners: {
    	select: function(me,records){
    		this.doSelect();
    		this.setValue(records[0].get('query'));
    		this.handleSelect();
    	},
    	focus: function(){
    		this.doFocus();
    		this.store.load();
    		this.expand();
    	},
    	change : function(me,newValue,oldValue){
    		this.doChange();
			if(newValue === '' || newValue === null){
				this.handleSelect();
			}
		},
		keydown: function(me,e){
			this.doKeydown();
			if(e.getKey()==Ext.EventObject.ENTER){
				this.handleSelect();
			}
		}
	},
	initComponent: function(){
		var me = this;
		if(!me.storageId){
			throw '请配置storageId';
		}
		Ext.apply(me.listeners,me.definedListeners);
		me.store = Ext.create('Ext.data.Store', {
			fields: ['id', 'query'],
			proxy: {
				type: 'localstorage',
				id  : me.storageId+'-Search'
			}
		});
		me.callParent();
	}
});