$(document).ready(function(){
	//$(".navigation li:eq(0)").find("a").css({"color":"#e6454a !important"});
	$(".navigation li:eq(0)").find("a").addClass("redClolr");
	$(".content li:eq(0)").css({"display":"block"});
	$(".navigation li").each(function(i,e){
		$(e).click(function(){
			$(".navigation li").find("a").removeClass("redClolr");
			$(e).find("a").addClass("redClolr");
			$(".content li").css({"display":"none"});
			$(".content").find("li:eq("+i+")").css({"display":"block"});
		})
	})
	$(".app").each(function(i,e){
		$(e).click(function(){
			$(".app_ul:eq("+i+")").slideToggle();
		})
	})
});

