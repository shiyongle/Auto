<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>车辆可视化</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="https://cache.amap.com/lbs/static/main1119.css"/>
<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.3&key=08dc9d81ec525d98f8a71b19bdd60647&plugin=AMap.Autocomplete,AMap.PlaceSearch,AMap.Driving"></script>
<style type="text/css">
*{ margin:0; padding:0;}
#mapContainer { width:100%; height:100%;}
.tip{font-size:14px;font-family:"微软雅黑";padding:5px 2px;line-height:25px;}
.searchMap{
font-family:"微软雅黑";
position:fixed;
top:10px;
right:10px;
width:260px;
height:80px;
background:#fff;
border:1px solid #ccc;
border-radius:5px;
box-shadow:4px 4px 10px #ccc;
font-size:14px;
}
#searchOrder{
width: 150px; 
height: 30px;
line-height: 30px;
margin-top:5px;
border-radius:5px;
font-family:"Microsoft Yahei";
outline:none;
border:1px solid #ccc;
}
.searchMo{
margin-top:10px;
margin-left:20px;
}

a#searchBus{text-decoration:none; width:50px; height:20px; margin:6px auto; display:block; background:#fff;border:1px solid #ccc; font-size:14px; line-height:20px; color:#000; text-align:center; border-radius:2px;}
a#searchBus:hover{ color:#000; background:#ebebeb;}

</style>  
</head>
<body>

	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
			
			<div id="mapContainer">
			<center style="margin-top:200px;"><img src="${ctx}/pages/pc/orderMap/images/loading.gif"/>地图加载中......</center>
			</div>
			<div class="searchMap" style="display:block">
			<div class="searchMo">司机：<input id="searchOrder"/> <a id="searchBus" href="javascript: void(0)">搜索</a></div>
			</div> 
			
	</div>
</body>
</html>


<script type="text/javascript">
var markerArry=[],//经纬度数组
 	tipArry=[],//消息框数组
 	markers=[],//所有的点
 	startPosition,//地图起始地点
 	map,//地图
 	mapTime;//标记刷新的时间

$(window).load(function(){	
	//搜索
	$("#searchBus").click(function(){
		var result=false;
		var key=$("#searchOrder").val();	
		$(".buslable").each(function(i,e){
			var order=$(e).data("drivername");
			if(key==order){
				var message="　司机："+$(e).data("drivername")+"<br/><br/>　车牌号:"+$(e).data("carnum")+"<br/><br/>　车型:"+$(e).data("cartype")+"<br/><br/>　好运司机:"+$(e).data("luckydriver")+"<br/><br/>　好运司机:"+$(e).data("positiontime");
				$.messager.alert('查询结果', message);
				map.setZoomAndCenter(15, [$(e).data("lng"), $(e).data("lat")]);//搜索后定位到司机
				$(this).css({"color":"red"});
				result=true;				
			}
		})
		if(!result){
			$.messager.alert('提示', '暂无查询到相关司机信息！<br/><br/><p style="font-size:12px">提示：试试缩小地图再进行查询。</p>', 'warning');
		}
	})
	
})





setInterval(function(){
	
$.ajax({
	type:"GET",
	//url :"map.json",
	url:"${ctx}/car/drvierPostion.do",	
	cache:false,
	success:function(response){
		  var position,message;
		  markerArry=[];
		  tipArry=[];
		  $.each(JSON.parse(response).list,function(i,ev){
			  if(i==0)
				  {
				  startPosition=[ev.lng,ev.lat];
				  }
					
					position=[ev.lng,ev.lat];//经纬度记录
					var luckyDriver;//好运司机
					if(ev.luckDriver=="0"){
						luckyDriver="否";
					}
					else{
						luckyDriver="是";
					}
// 					message=[ev.driverName,ev.carNum,ev.lng,ev.lat]//其他信息记录
						message=[ev.driverName,ev.carNum,ev.lng,ev.lat,ev.carType,luckyDriver,ev.positionTime,ev.foperate_time];//其他信息记录
					markerArry.push(position);
					tipArry.push(message);
				})
			
		}
	})
},5000)

setTimeout("map()",7000);

	
function map(){
    //初始化加载地图时，若center及level属性缺省，地图默认显示用户当前城市范围
     map = new AMap.Map('mapContainer', {
        zoom:15,//缩放级别
        
		center:startPosition,//默认位置
    });
    
    //工具栏
    map.plugin(["AMap.ToolBar"], function() {
		map.addControl(new AMap.ToolBar());
	});


var infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(0, -30)});//新建消息框

	//var markers=[];
mapTime=setInterval(function(){
	if(markers.length>0)
		{
		map.remove(markers);//车辆位置刷新时移除之前的点标记
		}

for (var i = 0; i < markerArry.length; i++) {
		var bus=new AMap.Marker({
           map:map,
           bubble:true,
		   icon: new AMap.Icon({     //设置地图上定位的图标       
           size: new AMap.Size(50, 101),  //图标大小
           image: tipArry[i][7]=="超时"?"${ctx}/pages/pc/orderMap/images/warnCar.png":"${ctx}/pages/pc/orderMap/images/taxi.png",
           imageOffset: new AMap.Pixel(0,0)//图标偏移
       }),
       position: markerArry[i]	
});
	markers.push(bus);	
	// 设置label标签
    bus.setLabel({//label默认蓝框白底左上角显示，样式className为：amap-marker-label
        offset: new AMap.Pixel(20, 20),//修改label相对于bus的位置
        content: "<div class='buslable' data-drivername="+tipArry[i][0]+" data-carnum="+tipArry[i][1]+" data-lng="+tipArry[i][2]+" data-lat="+tipArry[i][3]+" data-cartype="+tipArry[i][4]+" data-luckydriver="+tipArry[i][5]+" data-positiontime="+tipArry[i][6]+">司机："+tipArry[i][0]+"</div>"
    });
    $(".buslable").hide();
	bus.content = '<div class="tip" >司机：'+tipArry[i][0]+'<br/>车牌号：'+tipArry[i][1]+'<br/>车型：'+tipArry[i][4]+'<br/>好运司机：'+tipArry[i][5]+'<br/>时间：'+tipArry[i][6]+'</div>';
	bus.on('mouseover', markerClick);
	bus.on('mouseout', markerClose);
	bus.emit('click', {target: bus});
	bus.setPosition(markerArry[i]);
}
},5000)

	//var lng_jz=map.getCenter().lng;//获取当前经度
	//var lat_jz=map.getCenter().lat;//获取当前纬度



function markerClick(e) {
    infoWindow.setContent(e.target.content);
    infoWindow.open(map, e.target.getPosition());
}
function markerClose(e) {
    infoWindow.close(map, e.target.getPosition());
}
map.setFitView();

}
</script>