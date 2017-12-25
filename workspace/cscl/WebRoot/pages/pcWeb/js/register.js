$(document).ready(function(){
	/*step  border*/
	var gra_h=$(".step").height();
	$(".bor").height(gra_h);
	
	/*司机货主选择*/
	$(".driver").click(function(){
		$(this).attr("src","../css/images/register_iden/driver_checked.png");
		$(".customer").attr("src","../css/images/register_iden/custorm.png");
		$("#ftype").val(2);
	})
	$(".customer").click(function(){
		$(this).attr("src","../css/images/register_iden/custorm_checked.png");
		$(".driver").attr("src","../css/images/register_iden/driver.png");
		$("#ftype").val(1);
	})
	
	
	/*服务协议提示事件*/
	$("#agree").click(function(){
		if($(this).is(":checked")){
			$("#agree_err").tooltip('hide');
		}
	})
	
	

})