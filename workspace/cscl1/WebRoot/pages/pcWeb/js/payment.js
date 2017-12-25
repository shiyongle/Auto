$(document).ready(function(){
	$(document).on("click",".radio",function(){		
		$(".radio").each(function(i,e){
			$(e).removeClass("yezf_checked yfdf_checked");
		})
		$(this).css("border","3px solid #E33431").siblings().css({"border":"none","border-bottom":"1px solid #E6E6E6"});
		if($(this).hasClass("yezf")){//选择余额支付			
			$(this).addClass("yezf_checked");
			$('#password_box').show();
			getHtmlLoadingAfterHeight();
			$('.password_txt').focus();
		}else{
			$('#password_box').hide();
			$('.password_txt').val('');
		}
		if($(this).hasClass("yfdf")){//选择运费到付			
			$(this).addClass("yfdf_checked");
		}
		$(this).find("input[type=radio]").prop("checked","checked");
	})
	
	
	var screenWidth = $(window).width();//当前窗口宽度  
	var screenHeight = $(window).height();//当前窗口高度  
	$(".masker").css({"height":screenHeight,"width":screenWidth});	
	
	$(".add-pay").click(function(){
		$(".masker").show();
		$(".import").show();
	})
})