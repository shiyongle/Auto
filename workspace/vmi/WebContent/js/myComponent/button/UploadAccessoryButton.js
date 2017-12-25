/**
 * 上传按钮
 * 
 * 
 */
Ext.define('DJ.myComponent.button.UploadAccessoryButton', {
	extend : 'Ext.button.Button',

	alias : ['widget.uploadaccessorybutton'],

	text : "上传附件",

	// 父grid
	gridId : null,

	// 请求地址
	fileUrl : "",

	fileGridId : null,

	filedName : "fid",

	filters : [],

	initComponent : function() {
		var me = this;

		var handlerT = function(button, event) {

			var grid = Ext.getCmp(me.gridId);

			var record = grid.getSelectionModel().getSelection();

			if (record.length != 1) {

				Ext.Msg.alert("提示", "只能选择一条进行操作");

				return;

			}

			var uploaderCfg = {

				url : me.fileUrl+"?fid="+record[0].get(me.filedName),
				max_file_size : '11mb',
				unique_names : false,
				multiple_queues : true,
				chunk_size : '10mb',
				file_data_name : "upload1",
				multipart : true,
				multipart_params : {
					fid : record[0].get(me.filedName)
				},

				listeners : {

					close : function(panel, eOpts) {

						if (me.fileGridId != null) {

							Ext.getCmp(me.fileGridId).getStore().loadPage(1);

						}

					}
				},
				filters : [{
					title : "图片",
					extensions : "jpg,jpeg,png,gif,bmp,cdr"
				}]

			};

			if (me.filters.length != 0) {

				uploaderCfg.filters = Ext.Array.merge(uploaderCfg.filters,
						me.filters);

			}

			var win = Ext
					.create("DJ.tools.file.MultiUploadDialog", uploaderCfg);

			win.show();

		};

		me.handler = handlerT;

		me.callParent(arguments);
	}
});