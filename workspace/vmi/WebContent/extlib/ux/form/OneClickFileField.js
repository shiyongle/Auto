/**
 * 文件上传按钮，必配项：
 * doChange - 初始化params,传递fparentid
 * url - 文件上传路径
 */
Ext.define('Ext.ux.form.OneClickFileField', {
	extend : Ext.form.field.File,
	alias : 'widget.ocfile',
	buttonOnly : true,
	buttonText : '上传',
	buttonWidth : 55,
	url : '',
	params : {},
	fileType : '',
	doRender : Ext.emptyFn,
	doChange : Ext.emptyFn,
	success : function(res){
		responseText = res.responseText;
		if(responseText.indexOf('HTTP Status 500')>-1){
			Ext.Msg.alert('错误','图片上传失败，图片太大或不支持此格式！');
			return;
		}
		var obj = Ext.decode(responseText);
		if(obj.success){
			djsuccessmsg('上传成功！');
		}else{
			Ext.Msg.alert('提示',obj.msg);
		}
		var btn = this.previousSibling('button[text=刷    新]');
		if(btn){
			btn.handler();
		}
	},
	getAccept: function(){
		var fileType = this.fileType,
			imgType = ['jpg','jpeg','png','gif','bmp'];
		if(typeof fileType=='string'&& Ext.Array.contains(imgType,fileType)|| (Ext.isArray(fileType)&&Ext.Array.difference(fileType,imgType).length===0)){
			return 'image/gif, image/jpeg';
		}
		return '';
	},
	listeners:{
		render:function(){
			var input = document.getElementById(this.id+'-button-fileInputEl');
			input.style.width = this.buttonWidth+'px';
			input.setAttribute('accept',this.getAccept());
			input.setAttribute('multiple','multiple');
			this.doRender();
		},
		change:function(o,value){
			var me = this,
				url = me.url,
				fileType = me.fileType,
				typeRe,
				formatText = fileType;
			if(!url){
				Ext.Msg.alert('提示','文件上传url未定义！');
				return;
			}
			if(me.doChange.apply(me,arguments)===false){
				return;
			}
			url += Ext.Object.isEmpty(me.params)? '' : ('?'+Ext.Object.toQueryString(me.params));
			if(Ext.isArray(fileType)){
				formatText = fileType.join('、');
				fileType = '('+fileType.join('|')+')';
			}
			if(!fileType || new RegExp("\\."+fileType+"$").test(value)){
				var input = document.getElementById(me.id+'-button-fileInputEl'),
					parent = input.parentNode,
					form;
				form = document.createElement('form');
				form.appendChild(input);
				document.body.appendChild(form);
				Ext.Ajax.upload(form,url,null,{
					success : function(res){
						me.success.apply(me,arguments);
					},
					failure : function(){
						Ext.Msg.alert('提示','操作失败，请稍后再试！');
					}
				});
				input.value='';
 				parent.appendChild(input);
 				document.body.removeChild(form);
			}else{
				Ext.Msg.alert('提示','请上传 '+formatText+'格式的文件！');
			}
		}
	}
});