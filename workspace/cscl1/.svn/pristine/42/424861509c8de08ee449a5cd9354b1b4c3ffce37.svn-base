//项目的根目录
function getBasePath(){
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	//测试环境
	return (localhostPaht + projectName);  //http://192.168.1.10:8080/cscl
	//正式环境
	//return localhostPaht   //http://192.168.1.10:8080/
}

/*登录，删除session后的跳转*/
function login(){
	window.location.href=getBasePath()+"/pages/pcWeb/login/login_in.jsp";
}

//子页面动态拼接html后设置iframe高度-动态拼接代码后-调用
function getHtmlLoadingAfterHeight(){
	var iframe  = parent.document.getElementById('iframepage');
	var bHeight = iframe.contentWindow.document.body.scrollHeight;
	var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
	var height = Math.max(bHeight, dHeight);
	iframe.height =  height;
} 

//设置iframe高度为其引入的页面的高度
function iFrameHeight() {   
	var ifm= document.getElementById("iframepage");   
	var subWeb = document.frames ? document.frames["iframepage"].document : ifm.contentDocument;   
	if(ifm != null && subWeb != null) {
	   ifm.height = subWeb.body.offsetHeight;
	   getHtmlLoadingAfterHeight();
	}   
	}   

/*表单验证*
html_dom：输入框的ID
tip_dom:提示标签的ID
配合提交按钮的点击事件使用，举个栗子给你看：
$(".btn-submit").click(function(){
	var r1=checkForm("driver_name","name_err");
	var r2=checkForm("driver_phone","phone_err");
	var r3=checkForm("driver_address","address_err");
	if(r1&&r2&&r3){//验证都通过以后}
})
* */
function checkForm(html_dom,tip_dom){
	var val=$("#"+html_dom).val();
	if(val==""||val==null){
		$("#"+tip_dom).tooltip("show");
		setTimeout(function(){
			$("#"+tip_dom).tooltip("hide");	
		},1000)
		return false;
	}
	else{
		return true;
	}
}

//验证手机号 eg:  checkTel(13812345678)
function checkTel(tel){
	var re = /^1[3|4|5|7|8][0-9]\d{4,8}$/;//手机号码正则表达式
	if(!(re.test(tel))){//不是手机号码
		return false;
	}
	else{
		return true;
	}
}

//验证座机号  eg:  checkPhone("0577-12345678")
function checkPhone(phone){
	var re=/^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;//座机号正则表达式
	if(!(re.test(phone))){//不是手机号码
		return false;
	}
	else{
		return true;
	}
}