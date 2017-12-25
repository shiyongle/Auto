// JavaScript Document
$(function(){
		flash();
		jumper();
		$(".demo1").myScroll({
		speed:80, //数值越大，速度越慢
		rowHeight:30 //li的高度
	});
});
/*图片轮换*/
var i=-1;var time=0; 
function jumper(){
			i++;
			if(i>4)
			i=0;
			$(".picture_num ul li").eq(i).addClass("bg").siblings().removeClass("bg");
			$(".picture_img ul li").eq(i).fadeIn(1000,"linear").siblings().fadeOut(100);
			$(".picture_img ul li").eq(i).find(".img1").animate({left:"1005px"},1000);
	$(".picture_num ul li").click(function(){
			i=$(this).index();
			$(".picture_num ul li").eq(i).addClass("bg").siblings().removeClass("bg");
			$(".picture_img ul li").eq(i).fadeIn(500).siblings().fadeOut(500);
			$(".picture_img ul li").eq(i).find(".img1").animate({left:"0px"},1000);
		});
	$(".picture_img ul li").hover(function(){
			clearInterval(time);
		});
	$(".picture_img ul li").mouseleave(function(){
			time=setInterval("jumper()",2000);
		});
	}
time=setInterval("jumper()",2000);
//无缝滚动
(function($){
	$.fn.myScroll = function(options){
	//默认配置
	var defaults = {
		speed:40,  //滚动速度,值越大速度越慢
		rowHeight:24 //每行的高度
	};
	
	var opts = $.extend({}, defaults, options),intId = [];
	
	function marquee(obj, step){
	
		obj.find("ul").animate({
			marginTop: '-=1'
		},0,function(){
				var s = Math.abs(parseInt($(this).css("margin-top")));
				if(s >= step){
					$(this).find("li").slice(0, 1).appendTo($(this));
					$(this).css("margin-top", 0);
				}
			});
		}
		
		this.each(function(i){
			var sh = opts["rowHeight"],speed = opts["speed"],_this = $(this);
			intId[i] = setInterval(function(){
				if(_this.find("ul").height()<=_this.height()){
					clearInterval(intId[i]);
				}else{
					marquee(_this, sh);
				}
			}, speed);

			_this.hover(function(){
				clearInterval(intId[i]);
			},function(){
				intId[i] = setInterval(function(){
					if(_this.find("ul").height()<=_this.height()){
						clearInterval(intId[i]);
					}else{
						marquee(_this, sh);
					}
				}, speed);
			});
		
		});
	};
})(jQuery);
/*flash动画效果*/
function flash(){
	$("#menu ul li").mouseover(function(){
		$(this).css(
			{
			fontSize:"64px",
			opacity:1,
			textAlign:"center"
		});
		$(this).addClass("yy");
		$(this).siblings().css("opacity",0.8);
	});
	$("#menu ul li").mouseout(function(){
		$(this).removeClass("yy");
		$(this).css(
			{
			fontSize:"42px",
			lineHeight:"232px",
			opacity:1
		});
		$(this).siblings().css("opacity",0.8);
	});
}