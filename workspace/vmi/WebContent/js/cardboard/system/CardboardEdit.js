Ext.define('DJ.cardboard.system.CardboardEdit',{
	id:'DJ.cardboard.system.CardboardEdit',
	extend:'Ext.c.BaseEditUI',
	title:'纸板材料编辑界面',
	url : 'saveOrUpdateCardboard.do',
	viewurl : 'getCardboardInfo.do', 
	bodyPadding:'20 15',
	width : 420,
	//height : 335,
	ctype:'Productdef',
	listeners:{
		show:function(){
			if(this.editstate=='add'){
				var supplier = Ext.getCmp(this.parent).up('panel').up('panel').down('treepanel').getSelectionModel().getSelection();
				if(supplier[0].get('text')=='所有制造商'){
					Ext.Msg.alert('提示',"请选择一个制造商");
					this.close();
				}else{
					this.down('[name=fsupplierid]').setValue(supplier[0].get('id'));
				}
			}else if(this.editstate=='view'){
				var record = Ext.getCmp(this.parent).getSelectionModel().getSelection();
				var ftilemodelid = record[0].get('ftilemodelid');//Ext.getCmp(EditUI.EditUI).down('[name=ftilemodelid]').getValue();
				var flayer = record[0].get('flayer');//Ext.getCmp(EditUI.EditUI).down('[name=flayer]').getValue();
				if(ftilemodelid!='BC'){
					this.down('button[text=BC]').toggle();
				}
				if(flayer!='1'){
					this.down('button[text=1]').toggle();
				}
				var masks = this.getEl().query('.x-mask');
				masks.forEach(function(item,index){
					item.remove();
				});
				
			}
		},
		render:function(){
			var c0 = this;
			c0.down('button[text=保  存]').setHandler(
					function() {
						try {
							if(c0.Action_BeforeSubmit(c0)===false){
										c0.Action_Submit(c0);
										c0.Action_AfterSubmit(c0)
							}else{
								c0.Action_Submit(c0);
								c0.Action_AfterSubmit(c0)
							}
						
							
						} catch (e) {
							Ext.MessageBox.alert('提示', e);
						}
					}
				
			
			)
		}
	},
	Action_BeforeSubmit:function(me){
		var fname = me.down('textfield[name=fname]').getValue();
		var fnumber = me.down('textfield[name=fnumber]').getValue();
		if(Ext.isEmpty(fname)&&Ext.isEmpty(fnumber)){
			throw '信息填写不完整!';
		}else{
			return true;
		}
		
	},
	onload:function(){
	},
	removeButtonCls:function(me){
		var css = 'x-btn x-unselectable x-box-item x-btn-default-toolbar-small x-noicon x-btn-noicon x-btn-default-toolbar-small-noicon';
		var record = Ext.getCmp(me.up('window').parent).getSelectionModel().getSelection();
		if(record.length==1){
			if(me.getValue()!=record[0].get(me.name)){
				me.up('window').down('button[text='+record[0].get(me.name)+']').removeCls(css+" x-pressed x-btn-pressed x-btn-default-toolbar-small-pressed");
				me.up('window').down('button[text='+record[0].get(me.name)+']').addCls(css);
			}
		}
	},
	items:[{
		name : 'fid',
		xtype : 'textfield',
		fieldLabel : 'ID',
		width : 260,
		labelWidth : 50,
		hidden:true
	},{
		name : 'feffect',
		xtype : 'textfield',
		fieldLabel : 'feffect',
		width : 260,
		labelWidth : 50,
		hidden:true,
		value:1
	},{
		name : 'feffected',
		xtype : 'textfield',
		value:0,
		hidden:true
	},{
		name : 'fcreatetime',
		xtype : 'textfield',
		fieldLabel : 'time',
		width : 260,
		labelWidth : 50,
		hidden:true
	},{
		name : 'fsupplierid',
		xtype : 'textfield',
		fieldLabel : '制造商ID',
		width : 260,
		labelWidth : 50,
		hidden:true
	},{
		name : 'fname',
		xtype : 'textfield',
		fieldLabel : '材料',
		width : 260,
		labelWidth : 50
	},{
		name : 'fnumber',
		xtype : 'textfield',
		fieldLabel : '代码',
		width : 260,
		labelWidth : 50,
		listeners:{
			render:function(me){
				Ext.tip.QuickTipManager.register({
				    target: me.id,
				    text: '按照面纸、瓦纸、芯纸、瓦纸、里纸的排列顺序填写材料代码'
				});
			}
		}
	},{
		layout: {
		      type: 'hbox',
		      align: 'middle'
		},
		baseCls:'x-plain',
		windth:'100%',
		frame :true,
		items:[{
			xtype : 'label',
			text : '楞型:',
			width:50
		},{
			xtype:'buttongroup',
			//layout: 'hbox',
			columns: 7,
			id:'DJ.cardboard.system.CardboardEdit.ftilemodelid',
			items:[{
				xtype : 'button',
				text : 'BC',
				pressed : true,
				width : 35,
				margin : '5 0 5 5',
				enableToggle:true,
				toggleGroup : "ftilemodelid",
				handler:function(me){
					me.up().up().nextSibling('[name=ftilemodelid]').setValue(me.text);
				}
			}, {
				xtype : 'button',
				text : 'BE',
				width : 35,
				toggleGroup : "ftilemodelid",
				margin : '5 0 5 5',
				handler:function(me){
					me.up().up().nextSibling('[name=ftilemodelid]').setValue(me.text);
				}
			}, {
				xtype : 'button',
				toggleGroup : "ftilemodelid",
//				pressed : true,
				text : 'EBC',
				width : 35,
				margin : '5 0 5 5',
				handler:function(me){
					me.up().up().nextSibling('[name=ftilemodelid]').setValue(me.text);
				}
			},  {
				xtype : 'button',
				toggleGroup : "ftilemodelid",
				text : 'BCC',
				width : 35,
				margin : '5 0 5 5',
				handler:function(me){
					me.up().up().nextSibling('[name=ftilemodelid]').setValue(me.text);
				}
			},{
				xtype : 'button',
				toggleGroup : "ftilemodelid",
				text : 'E',
				width : 35,
				margin : '5 0 5 5',
				handler:function(me){
					me.up().up().nextSibling('[name=ftilemodelid]').setValue(me.text);
				}
			}, {
				xtype : 'button',
				toggleGroup : "ftilemodelid",
				text : 'B',
				width : 35,
				margin : '5 0 5 5',
				handler:function(me){
					me.up().up().nextSibling('[name=ftilemodelid]').setValue(me.text);
				}
			}, {
				xtype : 'button',
				toggleGroup : "ftilemodelid",
				text : 'C',
				width : 35,
				margin : '5 0 5 5',
				handler:function(me){
					me.up().up().nextSibling('[name=ftilemodelid]').setValue(me.text);
				}
			}, {
				xtype : 'button',
				toggleGroup : "ftilemodelid",
				text : 'F',
				width : 35,
				margin : '5 0 5 5',
				handler:function(me){
					me.up().up().nextSibling('[name=ftilemodelid]').setValue(me.text);
				}
			}, {
				xtype : 'button',
				toggleGroup : "ftilemodelid",
				text : 'AB',
				width : 35,
				margin : '5 0 5 5',
				handler:function(me){
					me.up().up().nextSibling('[name=ftilemodelid]').setValue(me.text);
				}
			}]
			
		}]
	},{
		name : 'ftilemodelid',
		xtype : 'textfield',
		fieldLabel : '楞型',
		width : 260,
		labelWidth : 50,
		margin : '5 0 5 0',
//		hidden:true,
		value:'BC'
	},{
		layout: {
		      type: 'hbox',
		      align: 'middle'
		},
		baseCls:'x-plain',
		windth:'100%',
		frame :true,
		items:[{
			xtype : 'label',
			text : '层数:',
			width:50
		},{
			xtype:'buttongroup',
			layout: 'hbox',
			id:'DJ.cardboard.system.CardboardEdit.flayer',
			items:[{
				xtype : 'button',
				text : '1',
				pressed : true,
				width : 35,
				margin : '5 0 5 5',
				toggleGroup : "flayer",
				handler:function(me){
					me.up().up().nextSibling('[name=flayer]').setValue(me.text);
				}
			}, {
				xtype : 'button',
				text : '2',
				width : 35,
				margin : '5 0 5 5',
				toggleGroup : "flayer",
				handler:function(me){
					me.up().up().nextSibling('[name=flayer]').setValue(me.text);
				}
			}, {
				xtype : 'button',
				toggleGroup : "flayer",
//				pressed : true,
				text : '3',
				width : 35,
				margin : '5 0 5 5',
				handler:function(me){
					me.up().up().nextSibling('[name=flayer]').setValue(me.text);
				}
			}, {
				xtype : 'button',
				toggleGroup : "flayer",
				text : '4',
				width : 35,
				margin : '5 0 5 5',
				handler:function(me){
					me.up().up().nextSibling('[name=flayer]').setValue(me.text);
				}
			}, {
				xtype : 'button',
				toggleGroup : "flayer",
				text : '5',
				width : 35,
				margin : '5 0 5 5',
				handler:function(me){
					me.up().up().nextSibling('[name=flayer]').setValue(me.text);
				}
			}, {
				xtype : 'button',
				toggleGroup : "flayer",
				text : '6',
				width : 35,
				margin : '5 0 5 5',
				handler:function(me){
					me.up().up().nextSibling('[name=flayer]').setValue(me.text);
				}
			}, {
				xtype : 'button',
				toggleGroup : "flayer",
				text : '7',
				width : 35,
				margin : '5 0 5 5',
				handler:function(me){
					me.up().up().nextSibling('[name=flayer]').setValue(me.text);
				}
			}]
			
		}]
	},{
		name : 'flayer',
		xtype : 'textfield',
		fieldLabel : '层数',
		width : 260,
		labelWidth : 50,
		margin : '5 0 0 0',
//		hidden:true,
		value:'1'
	},{
		name : 'fnewtype',
		xtype : 'combo',
		forceSelection : true,
		value : 0,
		store :[['3', '普通'], ['2', '裱胶']],
		fieldLabel : '类型',
		width : 260,
		labelWidth : 50,
		margin : '5 0 0 0',
		value:'3'
	
	}]
})