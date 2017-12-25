$(document).ready(function(){
	$(document).on("click",".radio",function(){		
		$(this).css("border","3px solid #E33431").siblings().css({"border":"none","border-bottom":"1px solid #E6E6E6"});
		$(this).find("input[type=radio]").prop("checked","checked");
	})
	$(".add-pay").click(function(){
		$(".masker").show();
		$(".import").show();
	})
})