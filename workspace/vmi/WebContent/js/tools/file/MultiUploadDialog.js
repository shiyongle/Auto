/**
 * 
 * 多文件上传对话框 zjz,可以用这个
 * 
 * @version 0.1 2014-8-28 上午10:51:15
 * 
 */
 
Ext.require("DJ.tools.file.MultiUploadPanel");


Ext.define("DJ.tools.file.MultiUploadDialog", {
	
	extend : 'Ext.window.Window',
	
	alias : "widget.djmultiuploaddialog",

	title : '文件上传',

	height : 340,

	resizable : false,
	
	modal : true,
	
	width : 792,

	initComponent : function() {
		var me = this;

		
		
		var djmultiuploadPanelCfg = {
		
			
				xtype : 'djmultiuploadPanel',

				url : me.url,
				max_file_size : me.max_file_size,
				unique_names : me.unique_names,
				multiple_queues : me.multiple_queues,
				chunk_size : me.chunk_size,
				file_data_name : me.file_data_name,
				multipart : me.multipart,
				multipart_params : me.multipart_params,
				filters : me.filters,
				runtimes : me.runtimes
			
			
		};
		
		if (me.panelId) {
		
			djmultiuploadPanelCfg.id = me.panelId;
			
		}
		
		Ext.apply(djmultiuploadPanelCfg, me.uploadCfg);
		
		if (me.uploadListeners) {
		
			djmultiuploadPanelCfg.uploadListeners = me.uploadListeners;
			
		}
		
		Ext.applyIf(me, {
			items : [djmultiuploadPanelCfg]
		});

		me.callParent(arguments);
	}

});