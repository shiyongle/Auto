Ext.define('DJ.order.Deliver.FtuTemplateParameterEdit',{
	extend:'Ext.form.Panel',
	id:'DJ.order.Deliver.FtuTemplateParameterEdit',
	 bodyPadding : 10,
	 height:185,
	 items : [{
		layout:'hbox',
	 	border:false,
	 	items:[{
			 xtype : 'textfield',
			 name:'falias',
			 labelWidth:55,
			 fieldLabel : '名称'
		},{
	 		xtype: 'component',
			style: 'margin-left:23px;margin-top:5px;',
			html: '<img  src="data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==" class="x-tool-img x-tool-help" role="presentation">',
			listeners:{
				render:function(me){
					Ext.create('Ext.tip.ToolTip', {
						    target: me.el.dom.firstChild,
						    dismissDelay : 0,
						    html: '列标题的名称'
					});
					me.el.dom.onmouseover = function(){
						me.el.dom.firstChild.style.backgroundPosition = '15px -300px';
						
					}
					me.el.dom.onmouseout = function(){
						me.el.dom.firstChild.style.backgroundPosition = '0px -300px';
					}
				}
			}
		}]
	},{
	 	layout:'hbox',
	 	defaultType: 'radiofield',
	 	border:false,
	 	items:[{
			 xtype : 'displayfield',
			 value : '类型:'
	 	},{
	 		boxLabel  : '数字',
	 		name:'fieldtype',
            inputValue: '1',
            margin:'0 10 0 30',
            readOnlyCls:'x-item-disabled',
            listeners:{
            	change:function(me, newValue, oldValue, eOpts ){
            		if(newValue==true){
                		this.up().up().down('numberfield[fieldLabel=小数位数]').up().show();
                		this.up('window').down('textfield[fieldLabel=计算公式]').up().show();
            		}else{
            			this.up().up().down('numberfield[fieldLabel=小数位数]').up().hide();
                		this.up('window').down('textfield[fieldLabel=计算公式]').up().hide();
            		}
            	}
            }
	 	},{
	 		boxLabel  : '文本',
	 		readOnlyCls:'x-item-disabled',
	 		name:'fieldtype',
	 		margin:'0 10 0 10',
	 		checked   : true,
            inputValue: '0'
	 	},{
	 		boxLabel  : '日期',
	 		hidden:true,
	 		readOnlyCls:'x-item-disabled',
	 		name:'fieldtype',
	 		margin:'0 10 0 10',
            inputValue: '2'
	 	},{
	 		xtype: 'component',
			style: 'margin-left:60px;margin-top:5px;',
			html: '<img  src="data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==" class="x-tool-img x-tool-help" role="presentation">',
			listeners:{
				render:function(me){
					Ext.create('Ext.tip.ToolTip', {
						    target: me.el.dom.firstChild,
						    dismissDelay : 0,
						    html: '数字类型规定列只能输入0~9数字，文本类型规定列能输入一切字符'
					});
					me.el.dom.onmouseover = function(){
						me.el.dom.firstChild.style.backgroundPosition = '15px -300px';
						
					}
					me.el.dom.onmouseout = function(){
						me.el.dom.firstChild.style.backgroundPosition = '0px -300px';
					}
				}
			}
	 	}]
			 
	},  {
		 xtype : 'textfield',
		 name:'fname',
		 hidden:true,
		 labelWidth:55,
		 fieldLabel : '名称'
	},{
		layout:'hbox',
		border:false,
		hidden:true,
		items:[{
			xtype : 'numberfield',
			 labelWidth:55,
			 name:'fdecimals',
			 width:160,
			 fieldLabel : '小数位数' ,
			 value:0,
			 maxValue:3,
			 minValue:0
		},{
			xtype:'displayfield',
			value:'(例如：1)'
		},{
	 		xtype: 'component',
			style: 'margin-left:17px;margin-top:5px;',
			html: '<img  src="data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==" class="x-tool-img x-tool-help" role="presentation">',
			listeners:{
				render:function(me){
					Ext.create('Ext.tip.ToolTip', {
						    target: me.el.dom.firstChild,
						    dismissDelay : 0,
						    html: '数字类型规定的小数位数，最高为3位，最小为0位，0表示没有小数'
					});
					me.el.dom.onmouseover = function(){
						me.el.dom.firstChild.style.backgroundPosition = '15px -300px';
						
					}
					me.el.dom.onmouseout = function(){
						me.el.dom.firstChild.style.backgroundPosition = '0px -300px';
					}
				}
			}
		
		}]
		},{
			layout:'hbox',
			border:false,
			hidden:true,
			items:[{
				 xtype : 'textfield',
				 name:'fcomputationalformula',
				 labelWidth:55,
				 margin:'5 0 0 00',
				 fieldLabel : '计算公式'
			},{
		 		xtype: 'component',
				style: 'margin-left:23px;margin-top:5px;',
				html: '<img  src="data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==" class="x-tool-img x-tool-help" role="presentation">',
				listeners:{
					render:function(me){
						Ext.create('Ext.tip.ToolTip', {
							    target: me.el.dom.firstChild,
							    dismissDelay : 0,
							    html: '数字类型规定的计算公式，一般都用来计算金额，计算公式默认为（数量*单价）,注意，计算公式填写名称必须与表格列名称一致'
						});
						me.el.dom.onmouseover = function(){
							me.el.dom.firstChild.style.backgroundPosition = '15px -300px';
							
						}
						me.el.dom.onmouseout = function(){
							me.el.dom.firstChild.style.backgroundPosition = '0px -300px';
						}
					}
				}
		}
		]
		},{
			layout:'hbox',
			border:false,
			items:[{
				xtype:'displayfield',
				value:'打印显示:'
			},{
				xtype:'checkboxfield',
				name:'fisprint',
				margin:'0 10 0 30',
				checked   : true,
			    inputValue: '1'
				}]
		},{
			layout:'hbox',
			border:false,
			items:[{
				xtype:'radiofield',
				name:'inserttype',
				boxLabel:'前面插入列',
			    inputValue: '0'
			},{
				xtype:'radiofield',
				name:'inserttype',
				boxLabel:'后面插入列',
				margin:'0 10 0 30',
				checked   : true,
			    inputValue: '1'
				}]
		}]
})