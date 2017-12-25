Ext.Loader.setConfig({
	enabled : true,
	paths : {
		'DJ' : 'js',
		'Ext.ux' : 'extlib/ux'
	}
});
var cookiemanager = new Ext.state.CookieProvider();
function hasValidateCode(){
	Ext.Ajax.request({
		url: 'hasValidateCode.do',
		success: function(res){
			var obj = Ext.decode(res.responseText),
				yzmDom = document.getElementById('yzm');
			if(obj.success){
				yzmDom.style.display = 'block';
				document.getElementById('validateCode').value = '';
				document.getElementById('yzmImg').src = "getValidateCode.do?_dc="+new Date().getTime();
			}else{
				yzmDom.style.display = 'none';
			}
		}
	});
}
function setErrorInfo(msg){
	var trDom = document.getElementById('tr_verifier'),
		verifier = document.getElementById('verifier');
	if(trDom.style.display == 'none'){
		trDom.style.display = '';
	}
	verifier.innerHTML = msg;
}
function initLogin(){
	function getValidateCode(){
		document.getElementById('yzmImg').src = "getValidateCode.do?_dc="+new Date().getTime();
	}
	if (Ext.isIE) {
		/*Ext.create('widget.uxNotification', {
			position : 'tc',
			manager : 'demo1',
			iconCls : 'ux-notification-icon-information',
			autoCloseDelay : 60000,
			closable : false,
			resizable : false,
			spacing : 20,
			html : '<a target = "_blank" href="http://dlsw.baidu.com/sw-search-sp/soft/9d/14744/ChromeStandaloneSetup.1416972703.exe">系统检测到您正使用性能较慢的IE浏览器，建议使用性能更好的谷歌chrome浏览器。点击直接下载</a>'
		}).show();*/
	}
	hasValidateCode();
	var loginBtn = Ext.get('loginBtn'),
		userEl = Ext.get('username'),
		pswEl = Ext.get('password'),
		codeEl = Ext.get('validateCode'),
		yzmImgEl = Ext.get('yzmImg'),
		yzmImg2El = Ext.get('yzmImg2');
	Ext.state.Manager.setProvider(cookiemanager);
	if(cookiemanager.get("username")){
		userEl.dom.value = cookiemanager.get("username");
	}
	loginBtn.on('click',function(e){
		var username = Ext.String.trim(userEl.getValue()),
			password = Ext.String.trim(pswEl.getValue()),
			code = Ext.String.trim(codeEl.getValue()),
			el;
		e.stopEvent();
		if(!username){
			setErrorInfo('帐号不能为空！');
			return;
		}
		if(!password){
			setErrorInfo('密码不能为空！');
			return;
		}
		el = Ext.get('form');
		el.mask("系统处理中,请稍候……");
		Ext.Ajax.request({
			url : "logonAction.do",
			params: {
				username: username,
				password: MD5(password),
				code: code
			},
			timeout: 60000,
			success: function(response){
				var obj = Ext.decode(response.responseText);
				if (obj.success == true) {
					if(!Ext.isEmpty(obj.data.redirectUrl)){
						window.location.href = obj.data.redirectUrl;
					}else{
						cookiemanager.set("username",obj.data.username);
						window.location.href = 'index.do';
					}
				}else{
					setErrorInfo(obj.msg);
					hasValidateCode();
					el.unmask();
				}
			},
			failure : function(response, option){
				setErrorInfo('无法连接服务器!');
				el.unmask();
				pswEl.dom.value = password;
			}
		});
	});
	userEl.on('keydown',function(e){
		if(e.getKey()== Ext.EventObject.ENTER){
			pswEl.focus(true);
		}
	});
	yzmImgEl.on('click',getValidateCode);
	yzmImg2El.on('click',getValidateCode);
}

	
Ext.onReady(initLogin);

