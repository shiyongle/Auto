$(document).ready(function(){
	/*切换二维码*/
	var judge=true;
	$(".login_change").click(function(){
		if(judge){			
		$(this).find("img").stop().animate({"margin-top":"-59px","margin-left":"-59px"},300);
		$(".page1").hide();
		$(".page2").fadeIn();
		$(".welcome").fadeOut();
		judge=false;
		}
		else{
		$(this).find("img").stop().animate({"margin-top":"0px","margin-left":"0px"},300);
		$(".page2").hide();
		$(".page1,.welcome").fadeIn();
		judge=true;
		}
		
		ewm_timeout(3);
	})
	
	$("#ewm_err").click(function(){
			$("#ewm_login").show();
			ewm_timeout(3);
	})
	
	
});



/*二维码失效*/
var timing;
function  ewm_timeout(t){
	clearTimeout(timing);
	$("#ewm_login").show();
	$("#ewm_err").hide();
	t=t*1000
	timing=setTimeout(function(){
			$("#ewm_login").hide();
			$("#ewm_err").show();
		},t);
	}