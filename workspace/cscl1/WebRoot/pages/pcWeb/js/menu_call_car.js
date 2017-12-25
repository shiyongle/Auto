$(window).load(function(){
	$("#nav_call_car").addClass("active");
	/*左边菜单点击事件*/
	$(".menu_list li a").click(function(){
		$(".menu_list li a").removeClass("active");
		$(this).addClass("active");
		$(this).parent().siblings().find("a").removeClass("active");
	})
	
	/*左边菜单跟随页面滑动效果*/
	$(window).scroll(function(){
		$(".menu_container").stop().animate({"top":$(window).scrollTop()+"px"},500);
	})
	
	/*iframe的高度*/
	$(".menu_container a").click(function(){
		setTimeout(function(){			
			var iframe=parent.document.getElementById('iframepage');
			if($(window).width()>=992&&iframe.height<550){
				iframe.height=600;
			}
		},1000)
	})
	
	/*小屏幕下点击导航条*/
	$(".menu:first-child .menu_title").click(function(){
		if($(window).width()<992){
			if($(".menu:first-child .menu_list").css("display")=="none"){
				$(".menu:first-child .menu_title").css({"color":"#e6454a"});
				$(".menu:nth-child(2) .menu_title").css({"color":"#5c6068"});
			}else{
				$(".menu:first-child .menu_title").css({"color":"#5c6068"});
			}
			$(".menu:first-child .menu_list").slideToggle();
			$(".menu:nth-child(2) .menu_list").slideUp();
		}
	})
	$(".menu:nth-child(2) .menu_title").click(function(){
		if($(window).width()<992){
			if($(".menu:nth-child(2) .menu_list").css("display")=="none"){
				$(".menu:first-child .menu_title").css({"color":"#5c6068"});
				$(".menu:nth-child(2) .menu_title").css({"color":"#e6454a"});
			}else{
				$(".menu:nth-child(2) .menu_title").css({"color":"#5c6068"});
			}
			$(".menu:nth-child(2) .menu_list").slideToggle();
			$(".menu:first-child .menu_list").slideUp();
		}
	})
	$(".menu_list li a").click(function(){
		if($(window).width()<992){
			$(".menu:first-child .menu_list").slideUp();
			$(".menu:nth-child(2) .menu_list").slideUp();
		}
	})
})