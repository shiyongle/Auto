// JavaScript Document
$(function(){
	beforeSearch();
	showImg();
	highlight();
	scrollImg();
	starhover();
});
/*鼠标悬浮展开图片*/
function showImg(){
	var imgs = $(".mk img"),
		INTERVAL = 300;
	function animate(index,left){
		imgs.eq(index).stop(true).animate({left:left+'px'},INTERVAL);
	}
	imgs.mouseover(function(e){
		if(imgs.index($(e.relatedTarget))>-1){
			imgs.stop(true);
		}
		var index = imgs.index($(this)), 
			position = 0;
		imgs.each(function(i){    
			animate(i,position);          
			if(index==i){            
				position += 512;   
			}else{
				position += 256;
			}
		});
	}).mouseout(function(){  //初始状态
		animate(0,0);
		animate(1,356);
		animate(2,640);
		animate(3,924);
	}).click(function(){
		var me = $(this);
		if(me.data('url')){
			if(me.data('url').indexOf("lineDesign/_create.net")>0)  window.location.href=me.data('url');
			else  validateSkip(me.data('url'));
		}	
	});
}
/*鼠标悬浮边框变色*/
function highlight(){
	$("#main li").mouseover(function(){
		$(this).addClass("hover");
	}).mouseout(function(){
		$(this).removeClass("hover");
	});
}
/*查询时隐藏登录框*/
function beforeSearch(){
	$('#search,#logon').one('mouseover',function(){
		$('#logon div').removeAttr('style');
	});
}
/*图片轮播*/
function scrollImg(){
	/*hss  根据屏幕大小自动等比例缩放banner图 2016年3月31日*/
	if($.browser.msie) { 
		var srollbarwidth=21;//ie浏览器滚动条为21px
	}else{
		var srollbarwidth=17;
	}
	var realwidth=screen.width-srollbarwidth;
	if(realwidth>=1920){
		$("#banner,#banner img ").width("100%");
		$("#banner,#banner img").height(410);
	}else if(realwidth>=1280&&realwidth<1920){
		$("#banner,#banner img").width(realwidth);
		$("#banner,#banner img ").height(realwidth*410/1920);		
		$(".prev,.next").css("top",($("#banner").height()-$(".prev").height())/2)
	}else{
		$("#banner,#banner img").width(1280);
		$("#banner,#banner img ").height(1280*410/1920);		
		$(".prev,.next").css("top",($("#banner").height()-$(".prev").height())/2)
	}
	$("#banner img").click(function(){  
		var cindex=$(this).parent().index();
		if(cindex==0)
		    window.location.href="pages/index_banner/index_banner01.jsp";
		if(cindex==1)
		    window.location.href="pages/index_banner/index_banner02.jsp";
		if(cindex==2)
		    window.location.href="pages/index_banner/index_banner03.jsp";
		if(cindex==3)
		    window.location.href="pages/index_banner/index_banner04.jsp";		
	})		
	/*-------------------------------------------------------------------*/
	var $bg = $('#banner'),
		$li = $bg.find('ul li'),
		$dot = $bg.find('ol li'),
		len = $li.length,
		currentIndex = 0,
		width = $li.get(0).offsetWidth,
		timer = null,
		INTERVAL = 400;
	function scroll(current,next,right){
		var currentImg,nextImg;
		if(current == next){
			return;
		}
		currentImg = $li.eq(current);
		nextImg = $li.eq(next);
		if(right){
			nextImg.css('left',width+'px');
			currentImg.animate({left:-width+'px'},INTERVAL);
		}else{
			nextImg.css('left',-width+'px');
			currentImg.animate({left:width+'px'},INTERVAL);
		}
		nextImg.animate({left:0},INTERVAL);		
		currentIndex = next;
	}
	/*-----------hss 添加左右箭头点击换图片事件------------*/
	$("#banner").mouseover(function(){
		$(".prev,.next").show()	
		clearInterval(timer);
		$li.stop(true,true);
		
	}).mouseout(function(){
		$(".prev,.next").hide();
		clearInterval(timer);
		timer = setInterval(function(){   
			scroll(currentIndex,(currentIndex+1)%len,true);
		},3000);
	});
	$(".prev").click(function(){
		clearInterval(timer);
		$li.stop(true,true);
		scroll(currentIndex,(currentIndex-1)%len,false);
		 //执行点击出现上一张后重新开始滚动
		timer = setInterval(function(){
			scroll(currentIndex,(currentIndex+1)%len,true);
		},3000);		
	});
	$(".next").click(function(){
		clearInterval(timer);
		$li.stop(true,true);
		scroll(currentIndex,(currentIndex+1)%len,true);	
		timer = setInterval(function(){   
			scroll(currentIndex,(currentIndex+1)%len,true);
		},3000);
	});  	
	if($li.eq(0).css('left')=="0px"){  //循环到第一张重新开始
		timer = setInterval(function(){
			scroll(currentIndex,(currentIndex+1)%len,true);
		},3000);
	}
	/*-----------------------------------------------*/	
}

//明星产品鼠标响应事件
function starhover(){
	$(".StarProduct ul li").mouseover(function(){		
		  $(this).find(".fugai").css({opacity: 0.0, "z-index": 1}).animate({opacity: 0.7});
		}).mouseout(function(){
		 $(this).find(".fugai").css("z-index","-1");
		 });
}


