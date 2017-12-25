<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>基本地图展示</title>
    <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
	<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=08dc9d81ec525d98f8a71b19bdd60647&plugin=AMap.Geocoder,AMap.Autocomplete,AMap.PlaceSearch"></script>
	<script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
</head>
<body>
<style>
body{margin:0;padding:0;height:auto;}/*如果设置高度，会影响嵌入了该地图的iframe下页面的高度*/
#mapContainer{width:100%;height:100%;}
#zheng_map_input{
	position: fixed;
	top: -11px;
	right: 100px;
	margin: 10px auto;
	padding:6px;
	font-family: "Microsoft Yahei", "微软雅黑", "Pinghei";
	font-size: 14px;
}
#zheng_map_input input{
	border-radius: 4px;
    border: 1px solid #ddd;
    height: 30px;
    width:300px;
}
.amap-sug-result{
	z-idnex:999999;
	top:0;
	left:0;
}
</style>
<div id="mapContainer"></div>
<div id="zheng_map_input"><input id="tipinput" type="text" placeholder="搜索地址仅作参考，搜索后请点击地图！"/></div>
<!-- <div id="panel"></div> -->
<script>
$(function(){
	$("#zheng_map_input").append($(".amap-sug-result"));
})
var lnglatXY;
var address_s,address_e1,address_e2,address_e3,address_e4,address_e5;//整车地点
var lin_address_s,lin_address_e1,lin_address_e2,lin_address_e3,lin_address_e4,lin_address_e5;//零担地点
var address_s_lnglat=[],address_e1_lnglat=[],address_e2_lnglat=[],address_e3_lnglat=[],address_e4_lnglat=[],address_e5_lnglat=[];//经纬度
var distance;//驾车距离
/*新建地图*/
var map = new AMap.Map("mapContainer", {
    resizeEnable: true,
	zoom: 15
});
//使用CSS默认样式定义地图上的鼠标样式
map.setDefaultCursor("pointer");
//输入提示
var autoOptions = {
    input: "tipinput"
};
var auto = new AMap.Autocomplete(autoOptions);
var placeSearch = new AMap.PlaceSearch({
    map: map
});  //构造地点查询类
AMap.event.addListener(auto, "select", select);//注册监听，当选中某条记录时会触发
function select(e) {
    placeSearch.setCity(e.poi.adcode);
    placeSearch.search(e.poi.name);  //关键字查询查询
//     var s_name='"'+e.poi.name+'"';
//     console.log(s_name);
    $("#tipinput").attr("value",e.poi.name).blur();
}

/*地图绑定点击事件*/
var marker=[];
map.on('click', function(e) {
	if(marker!=null){		
		map.remove(marker);
	}
    lnglatXY=[e.lnglat.getLng(),e.lnglat.getLat()]; 
    marker = new AMap.Marker({
    	map: map,
        position:lnglatXY,   
        icon: new AMap.Icon({            
            size: new AMap.Size(40, 50),  //图标大小
            image: "http://webapi.amap.com/theme/v1.3/markers/n/mark_r.png"           
        })        
    });
     regeocoder();
 });
function regeocoder() {  //逆地理编码
    var geocoder = new AMap.Geocoder({
        radius: 1000,
        extensions: "all"
    });        
    geocoder.getAddress(lnglatXY, function(status, result) {
//     	console.log(status);
        if (status === 'complete' && result.info === 'OK') {          
          geocoder_CallBack(result);
        }
    });          

    map.setFitView();
}

/*临时用车下单地图*/
var zheng_map_i,lin_map_i;//第几个地图点击按钮
$(document).on("click","#zheng .map_icon",function(){
	zheng_map_i=$("#zheng .map_icon").index(this);
})
$(document).on("click","#zheng .map",function(){
	zheng_map_i=$("#zheng .map").index(this);
})
$(document).on("click","#lin .map_icon",function(){
	lin_map_i=$("#lin .map_icon").index(this);
})
$(document).on("click","#lin .map",function(){
	lin_map_i=$("#lin .map").index(this);
})
/*搜索地址后赋值*/
$("#tipinput").on("blur",function(){
	var search_address=$("#tipinput").val();
	/*整车*/
// 	if(zheng_map_i==0){
// 		document.getElementById("address_th").value=search_address;
// 	}
// 	if(zheng_map_i==1){
// 		document.getElementById("address_xh").value=search_address;
// 	}
// 	if(zheng_map_i==2){
// 		document.getElementById("address_xh1").value=search_address;
// 	}
// 	if(zheng_map_i==3){
// 		document.getElementById("address_xh2").value=search_address;
// 	}
// 	if(zheng_map_i==4){
// 		document.getElementById("address_xh3").value=search_address;
// 	}
// 	if(zheng_map_i==5){
// 		document.getElementById("address_xh4").value=search_address;
// 	}
	/*零担*/
// 	if(lin_map_i==0){
// 		document.getElementById("lin_address_th").value=search_address;
// 	}
// 	if(lin_map_i==1){
// 		document.getElementById("lin_address_xh").value=search_address;
// 	}
// 	if(lin_map_i==2){
// 		document.getElementById("lin_address_xh1").value=search_address;
// 	}
// 	if(lin_map_i==3){
// 		document.getElementById("lin_address_xh2").value=search_address;
// 	}
// 	if(lin_map_i==4){
// 		document.getElementById("lin_address_xh3").value=search_address;
// 	}
// 	if(lin_map_i==5){
// 		document.getElementById("lin_address_xh4").value=search_address;
// 	}
})

function geocoder_CallBack(data) {
    var address = data.regeocode.formattedAddress; //返回地址描述
    var geocoder = new AMap.Geocoder({});  
    $("#tipinput").val(address);
    /*整车*/
    if(zheng_map_i==0){//提货
		$("#address_th").val(address);    	
		address_s=$("#address_th").val();
		geocoder.getLocation(address_s, function(status, result) {
            if (status === 'complete' && result.info === 'OK') {
            	var lng=result.geocodes[0].location.lng;
            	var lat=result.geocodes[0].location.lat;
            	address_s_lnglat=[lng,lat];
            	/* console.log(address_s_lnglat+"-----1"); */
            	$("#zheng_address_th_lng").val(lng);
            	$("#zheng_address_th_lat").val(lat);
            }
        }); 
	}
    if(zheng_map_i==1){
    	$("#address_xh").val(address);
    	address_e1=$("#address_xh").val();
    	/*地址转经纬度*/		
    	geocoder.getLocation(address_e1, function(status, result) {
            if (status === 'complete' && result.info === 'OK') {
            	var lng=result.geocodes[0].location.lng;
            	var lat=result.geocodes[0].location.lat;
            	address_e1_lnglat=[lng,lat];
            	/* console.log(address_e1_lnglat+"-----2"); */
            	$("#zheng_address_xh_lng").val(lng);
            	$("#zheng_address_xh_lat").val(lat);
            }
        }); 
	}
	if(zheng_map_i==2){
		$("#address_xh1").val(address);
		address_e2=$("#address_xh1").val();
		/*地址转经纬度*/
    	/* if(address_e1_lnglat!=""){    */ 		
    	geocoder.getLocation(address_e2, function(status, result) {
            if (status === 'complete' && result.info === 'OK') {
            	var lng=result.geocodes[0].location.lng;
            	var lat=result.geocodes[0].location.lat;
            	address_e2_lnglat=[lng,lat];
            	/* console.log(address_e2_lnglat+"-----3"); */
            	$("#zheng_address_xh_lng1").val(lng);
            	$("#zheng_address_xh_lat1").val(lat);
            }
        }); 
   /*  			
    	} */
	}
	if(zheng_map_i==3){
		$("#address_xh2").val(address);
		address_e3=$("#address_xh2").val();
		/*地址转经纬度*/
    	/* if(address_e2_lnglat!=""){    */ 		
    	geocoder.getLocation(address_e3, function(status, result) {
            if (status === 'complete' && result.info === 'OK') {
            	var lng=result.geocodes[0].location.lng;
            	var lat=result.geocodes[0].location.lat;
            	address_e3_lnglat=[lng,lat];
            	/* console.log(address_e3_lnglat+"-----4"); */
            	$("#zheng_address_xh_lng2").val(lng);
            	$("#zheng_address_xh_lat2").val(lat);
            }
        }); 
    			
    /* 	} */
	}
	if(zheng_map_i==4){
		$("#address_xh3").val(address);
		address_e4=$("#address_xh3").val();
		/*地址转经纬度*/
    	/* if(address_e3_lnglat!=""){   */  		
        	geocoder.getLocation(address_e4, function(status, result) {
                if (status === 'complete' && result.info === 'OK') {
                	var lng=result.geocodes[0].location.lng;
                	var lat=result.geocodes[0].location.lat;
                	address_e4_lnglat=[lng,lat];
                	/* console.log(address_e4_lnglat+"-----5"); */
                	$("#zheng_address_xh_lng3").val(lng);
                	$("#zheng_address_xh_lat3").val(lat);
                }
            }); 
/*     	} */
	}
	if(zheng_map_i==5){
		$("#address_xh4").val(address);
		address_e5=$("#address_xh4").val();
		/*地址转经纬度*/
    	/* if(address_e4_lnglat!=""){   */  		
        	geocoder.getLocation(address_e5, function(status, result) {
                if (status === 'complete' && result.info === 'OK') {
                	var lng=result.geocodes[0].location.lng;
                	var lat=result.geocodes[0].location.lat;
                	address_e5_lnglat=[lng,lat];
                	$("#zheng_address_xh_lng4").val(lng);
                	$("#zheng_address_xh_lat4").val(lat);
                }
            }); 
    		
	}
	
	/*零担*/
	if(lin_map_i==0){
		$("#lin_address_th").val(address);    	
		lin_address_s=$("#lin_address_th").val();
		geocoder.getLocation(lin_address_s, function(status, result) {
            if (status === 'complete' && result.info === 'OK') {
            	var lng=result.geocodes[0].location.lng;
            	var lat=result.geocodes[0].location.lat;
            	address_s_lnglat=[lng,lat];
            	$("#lin_address_th_lng").val(lng);
            	$("#lin_address_th_lat").val(lat);
            }
        }); 
	}
    if(lin_map_i==1){
    	$("#lin_address_xh").val(address);
    	lin_address_e1=$("#lin_address_xh").val();
    	/*地址转经纬度*/
    	/* if(address_s_lnglat!=""){    */ 		
    	geocoder.getLocation(lin_address_e1, function(status, result) {
            if (status === 'complete' && result.info === 'OK') {
            	var lng=result.geocodes[0].location.lng;
            	var lat=result.geocodes[0].location.lat;
            	address_e1_lnglat=[lng,lat];
            	$("#lin_address_xh_lng").val(lng);
            	$("#lin_address_xh_lat").val(lat);
            }
        }); 
    			
    	/* } */
	}
	if(lin_map_i==2){
		$("#lin_address_xh1").val(address);
		lin_address_e2=$("#lin_address_xh1").val();
		/*地址转经纬度*/
    	/* if(address_e1_lnglat!=""){    */ 		
    	geocoder.getLocation(lin_address_e2, function(status, result) {
            if (status === 'complete' && result.info === 'OK') {
            	var lng=result.geocodes[0].location.lng;
            	var lat=result.geocodes[0].location.lat;
            	address_e2_lnglat=[lng,lat];
            	$("#lin_address_xh_lng1").val(lng);
            	$("#lin_address_xh_lat1").val(lat);
            }
        }); 
    			
    /* 	} */
	}
	if(lin_map_i==3){
		$("#lin_address_xh2").val(address);
		lin_address_e3=$("#lin_address_xh2").val();
		/*地址转经纬度*/
    	/* if(address_e2_lnglat!=""){     */		
    	geocoder.getLocation(lin_address_e3, function(status, result) {
            if (status === 'complete' && result.info === 'OK') {
            	var lng=result.geocodes[0].location.lng;
            	var lat=result.geocodes[0].location.lat;
            	address_e3_lnglat=[lng,lat];
            	$("#lin_address_xh_lng2").val(lng);
            	$("#lin_address_xh_lat2").val(lat);
            }
        }); 
    			
    	/* } */
	}
	if(lin_map_i==4){
		$("#lin_address_xh3").val(address);
		lin_address_e4=$("#lin_address_xh3").val();
		/*地址转经纬度*/
    	/* if(address_e3_lnglat!=""){   */  		
    	geocoder.getLocation(lin_address_e4, function(status, result) {
            if (status === 'complete' && result.info === 'OK') {
            	var lng=result.geocodes[0].location.lng;
            	var lat=result.geocodes[0].location.lat;
            	address_e4_lnglat=[lng,lat];
            	$("#lin_address_xh_lng3").val(lng);
            	$("#lin_address_xh_lat3").val(lat);
            }
        }); 
    			
    /* 	} */
	}
	if(lin_map_i==5){
		$("#lin_address_xh4").val(address);
		lin_address_e5=$("#lin_address_xh4").val();
		/*地址转经纬度*/
    	/* if(address_e4_lnglat!=""){   */  		
    	geocoder.getLocation(lin_address_e5, function(status, result) {
            if (status === 'complete' && result.info === 'OK') {
            	var lng=result.geocodes[0].location.lng;
            	var lat=result.geocodes[0].location.lat;
            	address_e5_lnglat=[lng,lat];
            	$("#lin_address_xh_lng4").val(lng);
            	$("#lin_address_xh_lat4").val(lat);
            }
        }); 
    			
    	/* } */
	}
	
}

$(".map_close").click(function(){//地图关闭的时候计算距离
	
	/*卸货点为1个的时候*/
	if(address_s_lnglat.length!=0&&address_e1_lnglat.length!=0&&address_e2_lnglat.length==0&&address_e3_lnglat.length==0&&address_e4_lnglat.length==0&&address_e5_lnglat.length==0){
		$.ajax({
			type:"GET", 
			url:"http://restapi.amap.com/v3/direction/driving?key=06e535282012d8b87592834b80202fe6&origin="+address_s_lnglat[0]+","+address_s_lnglat[1]+"&destination="+address_e1_lnglat[0]+","+address_e1_lnglat[1]+"&waypoints=&destinationid=&extensions=base&strategy=0&output=JSON",
			success:function(result){
	//	 			console.log(result.route.paths[0].distance);
				distance=(result.route.paths[0].distance)/1000;  
				/* console.log(distance+"距离"); */
				$("#zheng_order_distance,#lin_order_distance").text(distance.toFixed(1));
				 console.log(distance+'----------1');
			}		
		})
	}
	/*卸货点不止1个的时候*/
	else{
		if(address_e2_lnglat.length!=0&&address_e3_lnglat.length==0&&address_e4_lnglat.length==0&&address_e5_lnglat.length==0){//2个卸货点
			$.ajax({
				type:"GET",
				url:"http://restapi.amap.com/v3/direction/driving?key=06e535282012d8b87592834b80202fe6&origin="+address_s_lnglat[0]+","+address_s_lnglat[1]+"&destination="+address_e1_lnglat[0]+","+address_e1_lnglat[1]+"&waypoints="+address_e2_lnglat[0]+","+address_e2_lnglat[1]+"&destinationid=&extensions=base&strategy=0&output=JSON",
				success:function(result){
					distance=(result.route.paths[0].distance)/1000;  				
// 					$("#zheng_order_distance").text(distance);
					$("#zheng_order_distance,#lin_order_distance").text(distance.toFixed(1));
					console.log(distance+'----------2');
				}		
			})
		}
		
		if(address_e2_lnglat.length!=0&&address_e3_lnglat.length!=0&&address_e4_lnglat.length==0&&address_e5_lnglat.length==0){//3个卸货点
			$.ajax({
				type:"GET", 
				url:"http://restapi.amap.com/v3/direction/driving?key=06e535282012d8b87592834b80202fe6&origin="+address_s_lnglat[0]+","+address_s_lnglat[1]+"&destination="+address_e1_lnglat[0]+","+address_e1_lnglat[1]+"&waypoints="+address_e2_lnglat[0]+","+address_e2_lnglat[1]+";"+address_e3_lnglat[0]+","+address_e3_lnglat[1]+"&destinationid=&extensions=base&strategy=0&output=JSON",
				success:function(result){
					distance=(result.route.paths[0].distance)/1000;  				
// 					$("#zheng_order_distance").text(distance);
					$("#zheng_order_distance,#lin_order_distance").text(distance.toFixed(1));
					console.log(distance+'----------3');
				}		
			})
		}
		
		if(address_e2_lnglat.length!=0&&address_e3_lnglat.length!=0&&address_e4_lnglat.length!=0&&address_e5_lnglat.length==0){//4个卸货点
			$.ajax({
				type:"GET",
				url:"http://restapi.amap.com/v3/direction/driving?key=06e535282012d8b87592834b80202fe6&origin="+address_s_lnglat[0]+","+address_s_lnglat[1]+"&destination="+address_e1_lnglat[0]+","+address_e1_lnglat[1]+"&waypoints="+address_e2_lnglat[0]+","+address_e2_lnglat[1]+";"+address_e3_lnglat[0]+","+address_e3_lnglat[1]+";"+address_e4_lnglat[0]+","+address_e4_lnglat[1]+"&destinationid=&extensions=base&strategy=0&output=JSON",
				success:function(result){
					distance=(result.route.paths[0].distance)/1000;  				
// 					$("#zheng_order_distance").text(distance);
					$("#zheng_order_distance,#lin_order_distance").text(distance.toFixed(1));
					console.log(distance+'----------4');
				}		
			})
		}
		
		if(address_e2_lnglat.length!=0&&address_e3_lnglat.length!=0&&address_e4_lnglat.length!=0&&address_e5_lnglat.length!=0){//5个卸货点
			$.ajax({
				type:"GET",
				url:"http://restapi.amap.com/v3/direction/driving?key=06e535282012d8b87592834b80202fe6&origin="+address_s_lnglat[0]+","+address_s_lnglat[1]+"&destination="+address_e1_lnglat[0]+","+address_e1_lnglat[1]+"&waypoints="+address_e2_lnglat[0]+","+address_e2_lnglat[1]+";"+address_e3_lnglat[0]+","+address_e3_lnglat[1]+";"+address_e4_lnglat[0]+","+address_e4_lnglat[1]+";"+address_e5_lnglat[0]+","+address_e5_lnglat[1]+"&destinationid=&extensions=base&strategy=0&output=JSON",
				success:function(result){
					distance=(result.route.paths[0].distance)/1000;  				
// 					$("#zheng_order_distance").text(distance);
					$("#zheng_order_distance,#lin_order_distance").text(distance.toFixed(1));
					console.log(distance+'----------5');
				}		
			})
		}
	}
})

var zheng_address_i,lin_address_i;//第几个地图点击按钮
$(document).on("click",".address_close",function(){//选择常用地址以后计算距离(整车)
	var address_s_lnglat=[$("#zheng_address_th_lng").val(),$("#zheng_address_th_lat").val()];
	var address_e1_lnglat=[$("#zheng_address_xh_lng").val(),$("#zheng_address_xh_lat").val()];
	var address_e2_lnglat=[$("#zheng_address_xh_lng1").val(),$("#zheng_address_xh_lat1").val()];
	var address_e3_lnglat=[$("#zheng_address_xh_lng2").val(),$("#zheng_address_xh_lat2").val()];
	var address_e4_lnglat=[$("#zheng_address_xh_lng3").val(),$("#zheng_address_xh_lat3").val()];
	var address_e5_lnglat=[$("#zheng_address_xh_lng4").val(),$("#zheng_address_xh_lat4").val()];
	
	
	var lin_address_s_lnglat=[$("#lin_address_th_lng").val(),$("#lin_address_th_lat").val()];
	var lin_address_e1_lnglat=[$("#lin_address_xh_lng").val(),$("#lin_address_xh_lat").val()];
	var lin_address_e2_lnglat=[$("#lin_address_xh_lng1").val(),$("#lin_address_xh_lat1").val()];
	var lin_address_e3_lnglat=[$("#lin_address_xh_lng2").val(),$("#lin_address_xh_lat2").val()];
	var lin_address_e4_lnglat=[$("#lin_address_xh_lng3").val(),$("#lin_address_xh_lat3").val()];
	var lin_address_e5_lnglat=[$("#lin_address_xh_lng4").val(),$("#lin_address_xh_lat4").val()];
	
	
	if(address_s_lnglat!=""){		
	//整车
	/*卸货点为1个的时候*/
	if(address_s_lnglat!=""&&address_e1_lnglat!=""&&address_e2_lnglat==""&&address_e3_lnglat==""&&address_e4_lnglat==""&&address_e5_lnglat==""){
	$.ajax({
		type:"GET", 
		url:"http://restapi.amap.com/v3/direction/driving?key=06e535282012d8b87592834b80202fe6&origin="+address_s_lnglat[0]+","+address_s_lnglat[1]+"&destination="+address_e1_lnglat[0]+","+address_e1_lnglat[1]+"&waypoints=&destinationid=&extensions=base&strategy=0&output=JSON",
		success:function(result){
//	 			console.log(result.route.paths[0].distance);
			distance=(result.route.paths[0].distance)/1000;  				
			$("#zheng_order_distance").text(distance);
		}		
	})
	}
	
	/*卸货点不止1个的时候*/
	else{
		if(address_e2_lnglat!=""){//2个卸货点
			$.ajax({
				type:"GET",
				url:"http://restapi.amap.com/v3/direction/driving?key=06e535282012d8b87592834b80202fe6&origin="+address_s_lnglat[0]+","+address_s_lnglat[1]+"&destination="+address_e1_lnglat[0]+","+address_e1_lnglat[1]+"&waypoints="+address_e2_lnglat[0]+","+address_e2_lnglat[1]+"&destinationid=&extensions=base&strategy=0&output=JSON",
				success:function(result){
					distance=(result.route.paths[0].distance)/1000;  				
// 					$("#zheng_order_distance").text(distance);
					$("#zheng_order_distance").text(distance);
				}		
			})
		}
		
		if(address_e2_lnglat!=""&&address_e3_lnglat!=""){//3个卸货点
			$.ajax({
				type:"GET", 
				url:"http://restapi.amap.com/v3/direction/driving?key=06e535282012d8b87592834b80202fe6&origin="+address_s_lnglat[0]+","+address_s_lnglat[1]+"&destination="+address_e1_lnglat[0]+","+address_e1_lnglat[1]+"&waypoints="+address_e2_lnglat[0]+","+address_e2_lnglat[1]+";"+address_e3_lnglat[0]+","+address_e3_lnglat[1]+"&destinationid=&extensions=base&strategy=0&output=JSON",
				success:function(result){
					distance=(result.route.paths[0].distance)/1000;  				
// 					$("#zheng_order_distance").text(distance);
					$("#zheng_order_distance").text(distance);
				}		
			})
		}
		
		if(address_e2_lnglat!=""&&address_e3_lnglat!=""&&address_e4_lnglat!=""){//4个卸货点
			$.ajax({
				type:"GET",
				url:"http://restapi.amap.com/v3/direction/driving?key=06e535282012d8b87592834b80202fe6&origin="+address_s_lnglat[0]+","+address_s_lnglat[1]+"&destination="+address_e1_lnglat[0]+","+address_e1_lnglat[1]+"&waypoints="+address_e2_lnglat[0]+","+address_e2_lnglat[1]+";"+address_e3_lnglat[0]+","+address_e3_lnglat[1]+";"+address_e4_lnglat[0]+","+address_e4_lnglat[1]+"&destinationid=&extensions=base&strategy=0&output=JSON",
				success:function(result){
					distance=(result.route.paths[0].distance)/1000;  				
// 					$("#zheng_order_distance").text(distance);
					$("#zheng_order_distance").text(distance);
				}		
			})
		}
		
		if(address_e2_lnglat!=""&&address_e3_lnglat!=""&&address_e4_lnglat!=""&&address_e5_lnglat!=""){//5个卸货点
			$.ajax({
				type:"GET",
				url:"http://restapi.amap.com/v3/direction/driving?key=06e535282012d8b87592834b80202fe6&origin="+address_s_lnglat[0]+","+address_s_lnglat[1]+"&destination="+address_e1_lnglat[0]+","+address_e1_lnglat[1]+"&waypoints="+address_e2_lnglat[0]+","+address_e2_lnglat[1]+";"+address_e3_lnglat[0]+","+address_e3_lnglat[1]+";"+address_e4_lnglat[0]+","+address_e4_lnglat[1]+";"+address_e5_lnglat[0]+","+address_e5_lnglat[1]+"&destinationid=&extensions=base&strategy=0&output=JSON",
				success:function(result){
					distance=(result.route.paths[0].distance)/1000;  				
// 					$("#zheng_order_distance").text(distance);
					$("#zheng_order_distance").text(distance);
				}		
			})
		}
		
	}
	}
	
	
	
	if(lin_address_s_lnglat!=""){		
		//零担
		/*卸货点为1个的时候*/
		if(lin_address_s_lnglat!=""&&lin_address_e1_lnglat!=""&&lin_address_e2_lnglat==""&&lin_address_e3_lnglat==""&&lin_address_e4_lnglat==""&&lin_address_e5_lnglat==""){
		$.ajax({
			type:"GET", 
			url:"http://restapi.amap.com/v3/direction/driving?key=06e535282012d8b87592834b80202fe6&origin="+lin_address_s_lnglat[0]+","+lin_address_s_lnglat[1]+"&destination="+lin_address_e1_lnglat[0]+","+lin_address_e1_lnglat[1]+"&waypoints=&destinationid=&extensions=base&strategy=0&output=JSON",
			success:function(result){
//		 			console.log(result.route.paths[0].distance);
				distance=(result.route.paths[0].distance)/1000;  				
				$("#lin_order_distance").text(distance);
			}		
		})
		}
		
		/*卸货点不止1个的时候*/
		else{
			if(lin_address_e2_lnglat!=""){//2个卸货点
				$.ajax({
					type:"GET",
					url:"http://restapi.amap.com/v3/direction/driving?key=06e535282012d8b87592834b80202fe6&origin="+lin_address_s_lnglat[0]+","+lin_address_s_lnglat[1]+"&destination="+lin_address_e1_lnglat[0]+","+lin_address_e1_lnglat[1]+"&waypoints="+lin_address_e2_lnglat[0]+","+lin_address_e2_lnglat[1]+"&destinationid=&extensions=base&strategy=0&output=JSON",
					success:function(result){
						distance=(result.route.paths[0].distance)/1000;  				
//	 					$("#zheng_order_distance").text(distance);
						$("#lin_order_distance").text(distance);
					}		
				})
			}
			
			if(lin_address_e2_lnglat!=""&&lin_address_e3_lnglat!=""){//3个卸货点
				$.ajax({
					type:"GET", 
					url:"http://restapi.amap.com/v3/direction/driving?key=06e535282012d8b87592834b80202fe6&origin="+lin_address_s_lnglat[0]+","+lin_address_s_lnglat[1]+"&destination="+lin_address_e1_lnglat[0]+","+lin_address_e1_lnglat[1]+"&waypoints="+lin_address_e2_lnglat[0]+","+lin_address_e2_lnglat[1]+";"+lin_address_e3_lnglat[0]+","+lin_address_e3_lnglat[1]+"&destinationid=&extensions=base&strategy=0&output=JSON",
					success:function(result){
						distance=(result.route.paths[0].distance)/1000;  				
//	 					$("#zheng_order_distance").text(distance);
						$("#lin_order_distance").text(distance);
					}		
				})
			}
			
			if(lin_address_e2_lnglat!=""&&lin_address_e3_lnglat!=""&&lin_address_e4_lnglat!=""){//4个卸货点
				$.ajax({
					type:"GET",
					url:"http://restapi.amap.com/v3/direction/driving?key=06e535282012d8b87592834b80202fe6&origin="+lin_address_s_lnglat[0]+","+lin_address_s_lnglat[1]+"&destination="+lin_address_e1_lnglat[0]+","+address_e1_lnglat[1]+"&waypoints="+lin_address_e2_lnglat[0]+","+lin_address_e2_lnglat[1]+";"+lin_address_e3_lnglat[0]+","+lin_address_e3_lnglat[1]+";"+lin_address_e4_lnglat[0]+","+lin_address_e4_lnglat[1]+"&destinationid=&extensions=base&strategy=0&output=JSON",
					success:function(result){
						distance=(result.route.paths[0].distance)/1000;  				
//	 					$("#zheng_order_distance").text(distance);
						$("#lin_order_distance").text(distance);
					}		
				})
			}
			
			if(lin_address_e2_lnglat!=""&&lin_address_e3_lnglat!=""&&lin_address_e4_lnglat!=""&&lin_address_e5_lnglat!=""){//5个卸货点
				$.ajax({
					type:"GET",
					url:"http://restapi.amap.com/v3/direction/driving?key=06e535282012d8b87592834b80202fe6&origin="+lin_address_s_lnglat[0]+","+lin_address_s_lnglat[1]+"&destination="+lin_address_e1_lnglat[0]+","+lin_address_e1_lnglat[1]+"&waypoints="+lin_address_e2_lnglat[0]+","+lin_address_e2_lnglat[1]+";"+lin_address_e3_lnglat[0]+","+lin_address_e3_lnglat[1]+";"+lin_address_e4_lnglat[0]+","+lin_address_e4_lnglat[1]+";"+lin_address_e5_lnglat[0]+","+lin_address_e5_lnglat[1]+"&destinationid=&extensions=base&strategy=0&output=JSON",
					success:function(result){
						distance=(result.route.paths[0].distance)/1000;  				
//	 					$("#zheng_order_distance").text(distance);
						$("#lin_order_distance").text(distance);
					}		
				})
			}
			
		}
		}
})

// $(document).on("blur","#address_xh",function(){
// 	 address_s=$("#address_th").val();
// 	 address_e1=$("#address_xh").val();
// 	/*地址转经纬度*/
// 	var geocoder0 = new AMap.Geocoder({});
// 	geocoder0.getLocation(address_s, function(status, result) {
//         if (status === 'complete' && result.info === 'OK') {
//         	var lng=result.geocodes[0].location.lng;
//         	var lat=result.geocodes[0].location.lat;
//         	address_s_lnglat=[lng,lat];
//         }
//     }); 
// 	/*地址转经纬度*/
// 	var geocoder1 = new AMap.Geocoder({});
// 	geocoder1.getLocation(address_e1, function(status, result) {
//         if (status === 'complete' && result.info === 'OK') {
//         	var lng=result.geocodes[0].location.lng;
//         	var lat=result.geocodes[0].location.lat;
//         	address_e1_lnglat=[lng,lat];
//         }
//     }); 
// //	if(address_s_lnglat!=""&&address_e1_lnglat!=""){
// // 		$.ajax({
// // 			type:"GET",
// // 			url:"http://restapi.amap.com/v3/direction/driving?key=06e535282012d8b87592834b80202fe6&origin="+address_s_lnglat[0]+","+address_s_lnglat[1]+"&destination="+address_e1_lnglat[0]+","+address_e1_lnglat[1]+"&waypoints=&originid=&destinationid=&extensions=base&strategy=0&output=JSON",
// // 			success:function(result){
// // //	 			console.log(result.route.paths[0].distance);
// // 				distance=(result.route.paths[0].distance)/1000;
// // 				console.log(distance+"公里")
// // 			}		
// // 		})
// // 	}
// })
// $("#address_th,#address_xh").focus(function(){
// 	$(this).blur();
// })

// $("#address_xh1").blur(function(){
// 	address_e2=$("#address_xh1").val();
// 	/*地址转经纬度*/
// 	var geocoder2 = new AMap.Geocoder({city: "010"});
// 	geocoder2.getLocation(address_e2, function(status, result) {
//         if (status === 'complete' && result.info === 'OK') {
//         	var lng=result.geocodes[0].location.lng;
//         	var lat=result.geocodes[0].location.lat;
//         	address_e2_lnglat=[lng,lat];
//         }
//     }); 

// })
// $("#address_xh2").blur(function(){
// 	address_e3=$("#address_xh2").val();
// 	/*地址转经纬度*/
// 	var geocoder3 = new AMap.Geocoder({city: "010"});
// 	geocoder3.getLocation(address_e3, function(status, result) {
//         if (status === 'complete' && result.info === 'OK') {
//         	var lng=result.geocodes[0].location.lng;
//         	var lat=result.geocodes[0].location.lat;
//         	address_e3_lnglat=[lng,lat];
//         }
//     }); 
// })
// $("#address_xh3").blur(function(){
// 	address_e4=$("#address_xh3").val();
// 	/*地址转经纬度*/
// 	var geocoder4 = new AMap.Geocoder({city: "010"});
// 	geocoder4.getLocation(address_e4, function(status, result) {
//         if (status === 'complete' && result.info === 'OK') {
//         	var lng=result.geocodes[0].location.lng;
//         	var lat=result.geocodes[0].location.lat;
//         	address_e4_lnglat=[lng,lat];
//         }
//     }); 
// })
// $("#address_xh4").blur(function(){
// 	address_e5=$("#address_xh4").val();
// 	/*地址转经纬度*/
// 	var geocoder5 = new AMap.Geocoder({city: "010"});
// 	geocoder5.getLocation(address_e5, function(status, result) {
//         if (status === 'complete' && result.info === 'OK') {
//         	var lng=result.geocodes[0].location.lng;
//         	var lat=result.geocodes[0].location.lat;
//         	address_e5_lnglat=[lng,lat];
//         }
//     }); 
// })	
	
	/*
	 AMap.service(["AMap.Driving"], function() {
	        var driving = new AMap.Driving({
	            map: map,
	            panel: "panel"
	        }); //构造路线导航类
	        // 根据起终点坐标规划步行路线
	        driving.search([{keyword:address_s},{keyword:address_e1}], function(status, result){
// 	            console.log(result);
	        	distance= result.routes[0].distance;//驾车的距离（米）
	        });  
	    });
	*/
	


</script>
</body>
</html>