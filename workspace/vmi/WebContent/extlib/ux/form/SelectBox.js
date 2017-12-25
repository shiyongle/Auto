Ext.define('Ext.ux.form.SelectBox', {
    extend:'Ext.Component',
    alias: 'widget.selectbox',
    requires: ['Ext.util.Format'],

    autoEl: 'span',
    
    cls: 'x-unselectable',
    
    overCls: 'selectbox-over',
    
    selectCls: 'selectbox-select',
    
    isSelected: false,
    
    value: null,
    
    toggleSelect: function(){
    	if(!this.isSelected){
    		this.addCls(this.selectCls);
    		this.isSelected = true;
    	}else{
    		this.removeCls(this.selectCls);
    		this.isSelected = false;
    	}
    },
    removeSelect: function(){
    	if(this.isSelected){
    		this.removeCls(this.selectCls);
    		this.isSelected = false;
    	}
    },
    
    maskOnDisable: false,

    getElConfig: function(){
        var me = this;

        me.html = me.text ? Ext.util.Format.htmlEncode(me.text) : (me.html || '');
        return me.callParent();
    },

    setText : function(text, encode){
        var me = this;
        
        encode = encode !== false;
        if(encode) {
            me.text = text;
            delete me.html;
        } else {
            me.html = text;
            delete me.text;
        }
        
        if(me.rendered){
            me.el.dom.innerHTML = encode !== false ? Ext.util.Format.htmlEncode(text) : text;
            me.updateLayout();
        }
        return me;
    }
});