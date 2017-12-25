/**
 * field持久化， useage : 设置id，加载插件，配置插件
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2014-9-9 下午5:00:12
 * @version 0.2 2014-9-17 上午10:14:50,增加otherEvent，支持自定义保存事件
 * 
 */
Ext.define('Ext.ux.form.FieldRecorder', {
	extend : "Ext.AbstractPlugin",
	alias : 'plugin.fieldrecorder',

	otherEvent : "",

	_saveValueToLoacal : function() {

		var me = this;
		var com = this.cmp;

		if (!me.disabled && com.getId() != null && com.getId() != '') {

			localStorage.setItem(com.getId(), com.getValue());

		}

		if (me.disabled) {
			localStorage.setItem(com.getId(), "");
		}

	},

	init : function() {

		var me = this;

		// load
		var com = this.cmp;

		// if (!me.disabled) {
		var value = localStorage.getItem(com.getId());

		if (value != null && value != "") {

			com.setValue(value);
		}

		// }

		if (me.otherEvent != "") {
			
			this.cmp.on(me.otherEvent, function() {

				me._saveValueToLoacal();

			}, this);
			
		} else {

			// save,失去焦点时保存，可能有些许性能问题
			this.cmp.on("blur", function(com, eOpts) {

				me._saveValueToLoacal();

			}, this);
		}

	}
});