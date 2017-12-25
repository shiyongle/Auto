Ext.define('Ext.ux.form.FileEditUI',{
	extend: 'Ext.c.BaseEditUI',
	alias: 'widget.fileedit',
	files : {},
	replaceBtn : true,		//**
	onload : function(){
		var me = this,
			cTable = me.filePanel = me.down('cTable[isFilePanel=true]');	//**
		if(!cTable){
			throw '未找到filePanel';
		}
		
		//上传按钮
		me.insertFileBtn();
		//删除按钮
		var delBtn = Ext.getCmp(cTable.id+'.dellinebutton');
		delBtn.setHandler(function(){
			var records = cTable.getSelectionModel().getSelection();
			if (records.length == 0) {
				Ext.MessageBox.alert('提示', '请选择你要操作的行！');
				return;
			};
			cTable.Action_BeforeDelButtonClick(cTable, records);
			cTable.Action_DelButtonClick(cTable, records);
			me.removeFile(records[0].get(cTable.fileId));
			cTable.Action_AfterDelButtonClick(cTable, records);
		});
		me.doCreate();
	},
	listeners: {
		show : function(){
			var me = this;
			if(!me.filePanel){
				throw '错误，未配置FileEditUI的filePanel';
			}
			if(me.replaceBtn){
				Ext.getCmp(me.filePanel.id+'.addlinebutton').hide();
			}
			me.doShow();
		}
	},
	insertFileBtn: function(){
		var me = this,
			cTable = me.filePanel,
			index = me.replaceBtn ? 0 : 1,
			toolbar = cTable.down('toolbar');
		me.fileField = toolbar.insert(index,{
				xtype : 'filefield',
				buttonOnly : true,
				buttonText : '上传附件',
				buttonConfig :{
					iconCls : 'upload'
				},
				listeners:{
					render:function(){
						var input = document.getElementById(this.id+'-button-fileInputEl');
						input.style.width = '80px';
					},
					change: function(){
						var record,fileName,id,name,idValue;
						id = cTable.fileId;
						name = cTable.fileName;
						if(!name){
							throw '请设置文件cTable的fileName';
						}
						if(!id){
							throw '请设置文件cTable的fileId';
						}
						if(!me.replaceBtn){
							var records = cTable.getSelectionModel().getSelection();
							if(records.length!=1){
								Ext.Msg.alert('提示','请选择一条记录进行操作！');
								return;
							}
							record = records[0];
						}
						idValue = Ext.id();
						me.addFile(idValue);
						if(!record){
							record = cTable.getStore().insert(0, {})[0];
						}
						record.set(id,idValue);
						record.set(name,this.getValue().substr(this.getValue().lastIndexOf('\\')+1));
					}
				}
			});
	},
	doShow: Ext.emptyFn,
	doCreate: Ext.emptyFn,
	addFile: function(key){
		var form = this.getform(),
			fileField = this.fileField;
		form.add(fileField);
		fileField.hide();
		this.files[key] = fileField;
		this.insertFileBtn();
	},
	removeFile: function(key){
		var fileField = this.files[key];
		this.getform().remove(fileField);
		delete this.files[key];
	}
});