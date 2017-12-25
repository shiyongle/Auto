$(document).ready(function(){
	$("#nav_driver").addClass("active");
	/*司机性质选择*/
	$(".radio_check").click(function(){
		$(this).css({"background-position":"0 0"}).find("input").attr("checked",true);
		
		$(this).parent().siblings().find("span").css({"background-position":"-25px 0"}).find("input").attr("checked",false);
	})

})


/*表单验证
 html_dom：输入框dom
 tip_dom:提示dom
*/
function checkForm(html_dom,tip_dom){
	var val=$("#"+html_dom).val();
	if(val==""||val==null){
		$("#"+tip_dom).tooltip("show");
//		$("#"+html_dom).focus();
		setTimeout(function(){
			$("#"+tip_dom).tooltip("hide");	
		},1000)
		return false;
	}
	else{
		return true;
	}
}
