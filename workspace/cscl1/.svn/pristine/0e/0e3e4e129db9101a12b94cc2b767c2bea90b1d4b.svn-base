<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
<title>地图</title>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=WM9Cx2m5ALmhZQ00iZSVgLFr"></script>

</head>
<style type="text/css">
body, html{margin:0;font-family:"微软雅黑";font-size:14px;}
#allmap {width:100%;height:100%;}
#r-result{width:100%;}
</style>
<body>
<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
//通过ID找元素
function G(id) {
		return document.getElementById(id);
	}
	
// var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
// 		{"input" : "address_th"
// 		,"location" : map
// 	});
	
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	var point = new BMap.Point(116.331398,39.897445);
	map.centerAndZoom(point,12);
	var geoc = new BMap.Geocoder(); 

	
	//默认一些工具的加载
	var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
	var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
	//var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}); //右上角，仅包含平移和缩放按钮
	map.addControl(top_left_control);        
	map.addControl(top_left_navigation);     
	//map.addControl(top_right_navigation);   
	map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用 
	
	
	
	//根据IP定位当前位置
	function myFun(result){
		var cityName = result.name;
		map.setCenter(cityName);
	}
	var myCity = new BMap.LocalCity();
	myCity.get(myFun); 
	
	
	var map_i;//第几个地图点击按钮
	$(document).on("click",".map_icon",function(){
		map_i=$(".map_icon").index(this);
	})
	//点击地图获取当前位置
	map.addEventListener("click", function(e){        
		var pt = e.point;
		geoc.getLocation(pt, function(rs){
			var addComp = rs.addressComponents;
			var address=addComp.province  + addComp.city + addComp.district + addComp.street+ addComp.streetNumber;
			if(map_i==0){				
				G("address_th").value=address;
			}
			if(map_i==1){
				G("address_xh").value=address;	
			}
			if(map_i==2){
				G("address_xh1").value=address;	
			}
			if(map_i==3){
				G("address_xh2").value=address;	
			}
			if(map_i==4){
				G("address_xh3").value=address;	
			}
			if(map_i==5){
				G("address_xh4").value=address;	
			}
		});    		    
	});
	
	
	
// 	ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
// 	var str = "";
// 		var _value = e.fromitem.value;
// 		var value = "";
// 		if (e.fromitem.index > -1) {
// 			value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
// 		}    
// 		str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;
		
// 		value = "";
// 		if (e.toitem.index > -1) {
// 			_value = e.toitem.value;
// 			value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
// 		}    
// 		str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
// 		G("searchResultPanel").innerHTML = str;
// 	});

// 	var myValue;
// 	ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
// 	var _value = e.item.value;
// 		myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
// 		G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
		
// 		setPlace();
// 	});

// 	function setPlace(){
// 		map.clearOverlays();    //清除地图上所有覆盖物
// 		function myFun(){
// 			var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
// 			map.centerAndZoom(pp, 18);
// 			map.addOverlay(new BMap.Marker(pp));    //添加标注
// 			G("lng").value=pp.lng;//经度
// 			G("lat").value=pp.lat;//纬度

// 		}
// 		var local = new BMap.LocalSearch(map, { //智能搜索
// 		  onSearchComplete: myFun
// 		});
// 		local.search(myValue);
// 	}
	
	
</script>

