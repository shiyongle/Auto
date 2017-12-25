/**
 * 文件上传按钮，必配项： doChange - 初始化params,传递fparentid url - 文件上传路径, 支持多文件
 * 
 * 
 */
Ext.define('Ext.ux.form.OneClickFileFieldForMulitiChoice', {
	extend : Ext.form.field.File,
	alias : 'widget.oneclickfilefieldformulitichoice',

	requires : ['DJ.tools.common.MyCommonToolsZ'],

	statics : {

		tipTpl : '<div align="center"><table width="320" border="0" cellspacing="5"><tpl for="."><tr><td height="240" ><div align="center"><img src="{imgUrl}"  height="210" style="max-width:98%" ></div></td></tr></tpl></table></div>'

	},
	
	isFormField:false,
	buttonOnly : true,

	// 按钮文本
	buttonText : '上传',

	buttonWidth : 70,
	// 上传路径
	url : '',
	// 多文件选择
	mulitiChoice : true,

	params : {},
	// 在上传之后显示图片，需要服务端配合，回传data:[{imgUrl:'图片路径'}]
	showImgTip : false,

	/**
	 * ['jpg','jpeg','png','gif','bmp'] 单个或是数组
	 */
	fileType : ['jpg', 'jpeg', 'png', 'gif', 'bmp','cdr'],

	/**
	 * 可以在这里为params赋值
	 * 
	 * @type {}
	 */
	beforeChangeAction : Ext.emptyFn,

	doRender : Ext.emptyFn,
	doChange : Ext.emptyFn,

	afterSuccessCallback : Ext.emptyFn,

	noStore : false,

	gainStore : function() {

		return this.up('grid').getStore(); 

	},

	hasUpLoaded : false,
	
	success : function(res) {

		var me = this;

		var obj = Ext.decode(res.responseText);
		if (obj.success) {

			me.hasUpLoaded = true;
			
			djsuccessmsg('上传成功！');

			if (me.showImgTip) {

				me.buildComTip(obj.data);

			}

			me.afterSuccessCallback(obj.data);

			if (!me.noStore) {

				me.gainStore().loadPage(1);
			}

		} else {
			Ext.Msg.alert('提示', obj.msg);
		}

	},

	/**
	 * 构建提示
	 * 
	 * @param {}
	 *            datas
	 */
	buildComTip : function(datas) {

		var me = this;

		if (Ext.isEmpty(datas)) {

			return;

		}

		var tpl = new Ext.XTemplate(me.self.tipTpl);

		var htmlS = tpl.apply(datas);

		MyCommonToolsZ.setToolTipZ(me.id, htmlS);

	},

	getAccept : function() {
		var fileType = this.fileType, imgType = ['jpg', 'jpeg', 'png', 'gif',
				'bmp'];
		if (typeof fileType == 'string'
				&& Ext.Array.contains(imgType, fileType)
				|| (Array.isArray(fileType) && Ext.Array.difference(fileType,
						imgType).length === 0)) {
			return 'image/gif, image/jpeg';
		}
		return '';
	},

	listeners : {
		render : function() {

			var me = this;

			var input = document
					.getElementById(this.id + '-button-fileInputEl');
			input.style.width = this.buttonWidth + 'px';
			input.setAttribute('accept', this.getAccept());

			if (me.mulitiChoice) {

				input.setAttribute('multiple', "multiple");

			}

			this.doRender();
		},
		change : function(o, value) {
			
			var me = this, url = me.url, fileType = me.fileType, typeRe, formatText = fileType;
			
			
			
			if (!url) {
				Ext.Msg.alert('提示', '文件上传url未定义！');
				return;
			}
			if (me.doChange.apply(me, arguments) === false) {
				return;
			}

			me.beforeChangeAction();

			url += Ext.Object.isEmpty(me.params) ? '' : ('?' + Ext.Object
					.toQueryString(me.params));
			if (Array.isArray(fileType)) {
				formatText = fileType.join('、');
				fileType = '(' + fileType.join('|') + ')';
			}
			if (!fileType || new RegExp("\\." + fileType + "$").test(value)) {
				var input = document.getElementById(me.id
						+ '-button-fileInputEl'), parent = input.parentNode, form;
				form = document.createElement('form');
				form.appendChild(input);
				document.body.appendChild(form);
				
				me.hasUpLoaded = false;
				
				Ext.Ajax.upload(form, url, null, {
					success : function(res) {
						me.success.apply(me, arguments);
					},
					failure : function() {
						Ext.Msg.alert('提示', '操作失败，请稍后再试！');
					}
				});
				input.value = '';
				parent.appendChild(input);
				document.body.removeChild(form);
			} else {
				Ext.Msg.alert('提示', '请上传 ' + formatText + '格式的文件！');
			}
		}
	}
});