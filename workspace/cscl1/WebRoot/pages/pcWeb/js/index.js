$(document).ready(function(){
	$("#nav_index").addClass("active");
	
	$(document).ready(function(){
		$('#myCarousel').carousel({
		  interval: 3000
		});
	})
	// 屏幕小于414二维码位置
	if($(window).width()<=414)
	{
		//图片修改
		$(".carousel-inner img").attr("src","../css/images/index/s_banner1.png");
		$(".icon-title div img").width(120);	
	}
	
	/*点击跳转*/
	$(".click_tiaozhuan").click(function(){
		var url_tz=["abundantSupply","massCar","richResources","intimateService","reliableDriver","waybillTracking","insuredTraffic"];
		var i=$('.click_tiaozhuan').index(this);
		if(window.location.href.indexOf('/pages/pcWeb')!=-1){
			/*点击跳转修改*/
			window.location.href="../index/index_"+url_tz[i]+".jsp";
		}else{
			window.location.href=window.location.href+'pages/pcWeb/index/index_'+url_tz[i]+'.jsp';
		}
	})
	
	
})

