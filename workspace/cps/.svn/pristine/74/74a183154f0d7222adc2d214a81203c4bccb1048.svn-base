function doFANGDA(){
	//图片放大镜效果
	$(".jqzoom").imagezoom();
	/*$("#thumblist li a").mousemove(function(){
		$(this).parents("li").addClass("tb-selected").siblings().removeClass("tb-selected");
		$(".jqzoom").attr('src',$(this).find("img").attr("mid"));
		$(".jqzoom").attr('rel',$(this).find("img").attr("big"));
	});*/
	$('#thumblist').delegate('li a','mouseover',function(e){
		var $li = $(e.target).parents("li");
		scrollItems.removeClass("tb-selected");
		$li.addClass("tb-selected");
		$(".jqzoom").attr('src',$li.find("img").attr("mid"));
		$(".jqzoom").attr('rel',$li.find("img").attr("big"));
	});
	//图片预览小图移动效果,页面加载时触发
	var tempLength = 0; //临时变量,当前移动的长度
	var viewNum = 5; //设置每次显示图片的个数量
	var moveNum = 2; //每次移动的数量
	var moveTime = 300; //移动速度,毫秒
	var scrollDiv = $(".spec-scroll .items ul"); //进行移动动画的容器
	var scrollItems = scrollDiv.children(); //移动容器里的集合
	var moveLength = scrollItems.eq(0).width() * moveNum; //计算每次移动的长度
	var countLength = (scrollItems.length - viewNum) * scrollItems.eq(0).width(); //计算总长度,总个数*单个长度
	
	//下一张
	var nextClick = $(".spec-scroll .next").bind("click",function(){
		if(tempLength < countLength){
			if((countLength - tempLength) > moveLength){
				scrollDiv.animate({left:"-=" + moveLength + "px"}, moveTime);
				tempLength += moveLength;
			}else{
				scrollDiv.animate({left:"-=" + (countLength - tempLength) + "px"}, moveTime);
				tempLength += (countLength - tempLength);
			}
		}
	});
	//上一张
	var prevClick = $(".spec-scroll .prev").bind("click",function(){
		if(tempLength > 0){
			if(tempLength > moveLength){
				scrollDiv.animate({left: "+=" + moveLength + "px"}, moveTime);
				tempLength -= moveLength;
			}else{
				scrollDiv.animate({left: "+=" + tempLength + "px"}, moveTime);
				tempLength = 0;
			}
		}
	});
	
	$.addFdImg = function(src,mid,bid,fileId){
		var li = '<li data-fileid="'+fileId+'"><div class="tb-pic tb-s40"><a href="#"><img width="40" height="40" src="'+src+'" mid="'+mid+'" big="'+bid+'"/></a></div></li>';
		scrollDiv.append(li);
		scrollItems = scrollDiv.children();
		countLength = (scrollItems.length - viewNum) * scrollItems.eq(0).width();
		var $li = scrollItems.removeClass("tb-selected").last().addClass("tb-selected");
		$(".jqzoom").attr('src',$li.find("img").attr("mid"));
		$(".jqzoom").attr('rel',$li.find("img").attr("big"));
		if(moveLength == 0){
			moveLength = scrollItems.eq(0).width() * moveNum;
		}
		if(scrollItems.length>viewNum){
			nextClick.click();
		}
	};
	$.delFdImg = function($li){
		$li.remove();
		scrollItems = scrollDiv.children();
		countLength = (scrollItems.length - viewNum) * scrollItems.eq(0).width();
		$li = scrollItems.first().addClass("tb-selected");
		$(".jqzoom").attr('src',$li.find("img").attr("mid"));
		$(".jqzoom").attr('rel',$li.find("img").attr("big"));
		prevClick.click();
	};
}