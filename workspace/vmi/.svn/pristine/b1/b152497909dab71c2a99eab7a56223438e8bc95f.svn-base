Ext.define('Ext.ux.grid.FieldQueryFilter', {
	extend : 'Ext.AbstractPlugin',
	alias : 'plugin.fieldqueryfilter',
	onChange:Ext.emptyFn(),
	onBlur:Ext.emptyFn(),
	onSpecialkey:Ext.emptyFn(),
	init : function() {
		var me = this;
		if(localStorage.$userFilterText){
			if(this.cmp.down('textfield[id*=leftDJfiltervalue]')){
				this.cmp.down('textfield[id*=leftDJfiltervalue]').setValue(localStorage.$userFilterText);//为左边查询赋值	
			}
		}
		this.cmp.down('textfield[id*=filtervalue]').on({
			change:onChange,
//			blur:onBlur,
			specialkey:onSpecialkey
		});
		function	onChange(m, newValue, oldValue, eOpts ){
			if(me.onChange){
				return me.onChange();
			}
			me.setDefaultfilters(newValue);
		}
		function onBlur( m, The, eOpts ){
			if(me.onBlur){
				return me.onBlur();
			}
			if(!Ext.isEmpty(m.getValue())){
				me.setDefaultfilters(m.getValue());
			}
		}
		function onSpecialkey( m, e, eOpts){
			if(me.onSpecialkey){
				return me.onSpecialkey();
			}
			if(e.getKey()==13){
				me.setDefaultfilters(m.getValue());
			}
		}
	},
	clearLocalStorage:function(){
		delete localStorage.$userFilterText 
	},
	setLocalStorage:function(val){
		localStorage.$userFilterText = val;//给界面一个临时变量,接收查询字段,下次打开自动赋值
		Ext.getCmp(this.cmp.up('window').parent).on({//用户界面关闭/隐藏时清除本地存储
			hide:this.clearLocalStorage,
			close:this.clearLocalStorage
		})
		window.onbeforeunload =this.clearLocalStorage;//浏览器刷新时 清除本地存储
	},
	setDefaultfilters:function(val){
		var store = this.cmp.getStore(),
		maskstring = [],
		filterfield = this.cmp.fields,
		defaultfilter = [];
		filterfield.forEach(function(field){
			if(field.myfilterfield){
				defaultfilter.push({
					myfilterfield : field.myfilterfield,
					CompareType : "like",
					type : "string",
					value : val
				})
				maskstring.push('#'+eval(defaultfilter.length-1));
			}
		})
		store.setCusfilter(defaultfilter);
		store.setMaskstring(maskstring.join(' or '));
		store.load();
		this.setLocalStorage(val);//设置本地存储 查询字段
	}
})