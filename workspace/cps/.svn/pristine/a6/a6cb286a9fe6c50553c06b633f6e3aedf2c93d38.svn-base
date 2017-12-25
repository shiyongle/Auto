
$(document).ready(function(){

	//根据屏幕大小自动等比例缩放banner图 
   if($.browser.msie) { 
		var srollbarwidth=21;//ie浏览器滚动条为21px
	}else{
		var srollbarwidth=17;
	}
	var realwidth=screen.width-srollbarwidth;
	if(realwidth>=1920){
		$(".banner,.banner img ").width("100%");
		$(".banner,.banner img").height(410);
	}else if(realwidth>=1280&&realwidth<1920){
		$(".banner,.banner img").width(realwidth);
		$(".banner,.banner img ").height(realwidth*410/1920);				
	}else{
		$(".banner,.banner img").width(1280);
		$(".banner,.banner img ").height(1280*410/1920);				
	}
    var liindex=$(".content2 ul li").index()
    var h=$(".content2 ul").height()
	if(liindex>4&&liindex<=9){   //5个li一行，每增加一行，ul高度+60px
		$(".content2 ul").height(h+60);
	}
	if(liindex>9&&liindex<=14){
		$(".content2 ul").height(h+120);
	}

});
