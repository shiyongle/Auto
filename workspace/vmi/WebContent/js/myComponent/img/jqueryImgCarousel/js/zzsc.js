

// $(".picimglink").css("text-decoration", "none");
// $(".picimglink").bind({
// mouseenter: function() {
// $(this).children("span").animate({
// bottom: "0px"
// },
// 500)
// },
// mouseleave: function() {
// $(this).children("span").clearQueue().animate({
// bottom: "-30px"
// },
// 500)
// }
// });

//$(function() {

	var cfgObj = {

		images : [],

		biZhiDelayLoadImgLength : 8,

		imgLabel : ' <li>'
				+ '<a target="_blank"><img width="710" height="350"/></a>'
				+ '    <span class="pic-txt"> </span></li>',

		_setImgLi : function(imgBox, image) {

			imgBox.find("a").attr("href", image.href);

			imgBox.find("span").text(image.text);

			imgBox.find("img").attr("src", image.url);

		},

		initImageUrls : function(imgs) {

			var me = this;

			me.images = [];

			var length = imgs.length;

			for (var i = 0; i < length; i++) {

				// var imgT = {
				// url : "images/img (" + i + ").jpg",
				// href : "http://www.baidu.com",
				// text : i + "http://www.baidu.com"
				// };

				me.images.push(imgs[i]);

			}

		},

		initImageUrlsTest : function() {

			var me = this;

			me.images = [];

			for (var i = 0; i < me.biZhiDelayLoadImgLength; i++) {

				var imgT = {
					url : "images/defaultNewsBg.jpg",
					href : "javascript:hi;",
					text : "包装智能服务平台"
				};

				me.images.push(imgT);

			}

		},

		initImageComs : function() {

			var me = this;

			for (var i = 0; i < me.biZhiDelayLoadImgLength; i++) {

				var imgBox = $(me.imgLabel);

				me._setImgLi(imgBox, me.images[i]);

				$("#bigSlideUl").append(imgBox);

			}
		},

		resetImageComs : function() {

			var me = this;

			var biZhiDelayLoadImg = $("#bigSlideUl li");

			$.each(me.images, function(index, ele) {

				var imgT = biZhiDelayLoadImg.eq(index);

				me._setImgLi(imgT, me.images[index]);

			});

		}

	}

	cfgObj.initImageUrlsTest();

	cfgObj.initImageComs();

	// // 配置图片
	// var biZhiDelayLoadImg = $("#bigSlideUl img");
	// biZhiDelayLoadImg.attr("width", 720);
	// var biZhiDelayLoadImgLength = 7;//biZhiDelayLoadImg.length;
	//
	//
	// for (var i = 0; i < biZhiDelayLoadImgLength; i++) {
	// var curDelayImg = biZhiDelayLoadImg.eq(i);
	// curDelayImg.attr("src", cfgObj.imageUrls[i]);
	//
	//
	// // if (curDelayImg.attr("srch")) {
	// // curDelayImg.attr("src", curDelayImg.attr("srch"));
	// // curDelayImg.removeAttr("srch")
	// // }
	// }

	var _focus_num = $("#smallSlideUl > li").length;
	var _focus_direction = true;
	var _focus_pos = 0;
	var _focus_max_length = _focus_num * 720;
	var _focus_li_length = 720;
	var _focus_dsq = null;
	var _focus_lock = true;

	// 自动轮播
	function autoExecAnimate() {
		$("#mypic" + _focus_pos).addClass("info-cur").siblings("li.info-cur")
				.removeClass("info-cur");
		var moveLen = _focus_pos * _focus_li_length;
		$("#bigSlideUl").animate({
			left : "-" + moveLen + "px"
		}, 600);
		if (_focus_pos == (_focus_num - 1)) {
			_focus_direction = false
		}
		if (_focus_pos == 0) {
			_focus_direction = true
		}
		if (_focus_direction) {
			_focus_pos++
		} else {
			_focus_pos--
		}
	}
	_focus_dsq = setInterval("autoExecAnimate()", 6000);

	// 切换按钮
	$("#smallSlideUl > li").hover(function() {
		_focus_pos = parseInt($(this).attr("sid"));
		if (_focus_lock) {
			clearInterval(_focus_dsq);
			_focus_lock = false
		}
		$("#mypic" + _focus_pos).addClass("info-cur").siblings("li.info-cur")
				.removeClass("info-cur");
		var moveLen = _focus_pos * _focus_li_length;
		$("#bigSlideUl").stop(true, true).animate({
			left : "-" + moveLen + "px"
		}, 600)
	}, function() {
		if (_focus_lock == false) {
			_focus_dsq = setInterval("autoExecAnimate()", 6000);
			_focus_lock = true
		}
	});
	$("#bigSlideUl").hover(function() {
		if (_focus_lock) {
			clearInterval(_focus_dsq);
			_focus_lock = false
		}
	}, function() {
		if (_focus_lock == false) {
			_focus_dsq = setInterval("autoExecAnimate()", 6000);
			_focus_lock = true
		}
	});
	$(".pic-list2 li").hover(function() {
		$(this).addClass("hover").siblings().removeClass("hover")
	}, function() {
		$(this).removeClass("hover")
	});

	function sendReqToGetImg() {

		$.get(parent.location.pathname.substring(0,parent.location.pathname.lastIndexOf('/')+1)+"gainIndexImgs.do", function(data) {


			//火狐的话比较严格，会解析错误
			if (data.documentElement) {
				
				
				var soS = data.documentElement.childNodes[1].innerHTML;
				
				var s1 = soS.replace(/&lt;/g, "<");
				
				var s2 = s1.replace(/&gt;/g, ">");
				
				data = parent.Ext.JSON.decode(s2.substring(0,s2.length-1));
				
				
			} else {
				
				data = parent.Ext.JSON.decode(data);
				
			}

			

			if (data.data) {

				var imgsT = [];

				$.each(data.data, function(index, ele) {

					var imgObjT = {
						url : ele.url,
						text : ele.text,
						href : ele.href
					}
					imgsT.push(imgObjT);
				});

				if (data.data.lenght == 0) {

					cfgObj.initImageUrlsTest();

				} else {

					cfgObj.initImageUrls(imgsT);

				}

				cfgObj.resetImageComs();

				parent.DJ.myComponent.img.jqueryImgCarousel.JqueryImgCarouselContainer
						.loadListStore();

			}

		});

	}

	sendReqToGetImg();

//为了应对火狐间歇性无法解析问题，发送3次请求。如果效果没达到，可以再进行几次请求。
	(function(){
		
		for (var i = 0; i <= 3; i++) {
			
			sendReqToGetImg();
			
		}
		
	})();
	var explorer = window.navigator.userAgent ;
	if(explorer.indexOf("MSIE") < 0){
		setInterval(function() {
			sendReqToGetImg();
		}, 60000);
	}

	// 60s

//});
