/**
 * 新增时自动恢复上次输入状态，在form里用
 * 
 * @date 2014-6-9 下午1:54:38, ZJZ (447338871@qq.com, any Question)
 */
Ext.define('Ext.ux.FormDataRecorder', {
	extend : "Ext.AbstractPlugin",
	alias : 'plugin.formdatarecorder',

	alternateClassName : 'Ext.data.FormDataRecorder',

	init : function() {

		// load
		var com = this.cmp;
		var paramsS = localStorage.getItem(com.id);

		if (paramsS != null && paramsS != "") {

			var formT = com.getForm();

			var obj = Ext.JSON.decode(paramsS);

			formT.setValues(obj);
		}

		// save
		this.cmp.on("beforedestroy", function(com, eOpts) {

			if (com.getForm().getValues().fid == null
					|| com.getForm().getValues().fid == "") {

				localStorage.setItem(com.id, Ext.JSON.encode(com.getForm()
						.getValues()));

			}

		}, this);

	}
});