<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>购物车地址管理</title>
<link href="${ctx }/css/kkpager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<!-- 高德地图引入资源 -->
    <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=608d75903d29ad471362f8c58c550daf&plugin=AMap.Autocomplete,AMap.PlaceSearch,AMap.Driving"></script> 
<script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
<!-- 高德地图引入资源 -->
<script src="${ctx }/js/kkpager.js" type="text/javascript" language="javascript"></script>
<style>
	*{
		margin:0px auto;
		padding:0px;
		}
	#flinkman,#fdetailaddress,#fphone{padding-left:5px;}
	#container{
		width:1070px;
		}
	#container .p1{
		width:1070px;
		height:60px;
		line-height:60px;
		font-size:12px;
		}
	#container .p2{
		width:1070px;
		height:75px;
		line-height:75px;
		color:#DD2F3A;
		font-family:"新宋体";
		}
	/*新增地址*/
	#container .address,#container .address_list{
		width:1070px;
		background-color:#ffffff;
		}
	.address .img_left,.address_list .al_left{
		display:inline-block;
		float:left;
		margin-left:94px;
		+margin-left:61px;
		*+margin-left:61px;
		}
	.address .img_right,.address_list .al_right{
		display:inline-block;
		float:left;
		}
	.address .title_add,.address_list .title_save{
		display:inline-block;
		float:left;
		width:210px;
		text-align:center;
		font-family:"微软雅黑";
		font-size:24px;
		text-decoration:none;
		color:black;
		cursor:default;
		}
	.address_list .title_save:hover{
		color:black;
		}
	.address input[type=text]{
		width:375px;
		height:30px;
		border:1px solid lightgray;
		vertical-align:middle;
		line-height:30px;
		}
	.address .tab1_submit{
		width:110px;
		height:35px;
		border:none;
		background-color:#C00;
		color:white;
		font-family:"微软雅黑";
		font-size:18px;
		letter-spacing:5px;
		cursor:pointer;
		}
	.address_list table{
		border-collapse:collapse;
		text-align:center;
		font-size:14px;
		font-family:"微软雅黑";
		color:#666;
		}
	.address_list table tr{
		border:1px solid lightgray;
		}
	.address_list table a{
		text-decoration:none;
		color:black;
		}
	.address_list table a:hover{
		color:red;
		}
	#kkpager{
		width:1020px;
		height:180px;
		line-height:100px;
		text-align:right;
		padding-right:50px;
		background-color:white;
		}
	/*浮动样式*/
	._color{
		background-color:#e9f7ff;
		}
	.setting{
		border:none;
		background-color:#C00;
		color:white;
		height:25px;
		width:70px;
		font-family:"微软雅黑";
		cursor:pointer;
		}
	#selected{
		font-family:"微软雅黑";
		line-height:25px;
		color:#C00;
		}

/*地图*/
#mapContainer {width:518px; height:450px; margin:0px auto;border:1px solid #c3c3c3;border-radius:3px;}  
.amap-sug-result{visibility: hidden;}

.addressMsg{ width:520px; margin:0 auto; overflow:hidden; font-size:14px;}
.addressMsg ul{ overflow:hidden;}
.addressMsg ul li{ list-style:none; margin:25px 0px;padding:5px 0px;}

.addressMsg ul li input{width: 375px;border: 1px solid lightgray;}
.addressMsg_red{ color:red; margin-right:10px; line-height: 30px;}
.addressMsg_01{ width:100px; display:inline-block; line-height: 30px;}
#panel {display:none;} 

</style>

<script type="text/javascript">
function gridUaddressTable(page){
var loadDel = layer.load(2);
   		$.ajax({
				type : "POST",
				url : "${ctx}/address/load.net?pageNo="+page+"&pageSize="+5,
				dataType:"json",
				//data:obj,
				success : function(response) {
					$(".otherRow").remove();
					  $.each(response.list, function(i, ev) {
	                    var html = '<tr height="80" class="otherRow" id="'+ev.fid+'"> \
                    	<td>'+ev.flinkman+'</td> \
                        <td>'+ev.fdetailaddress+'</td> \
                    	<td>'+ev.fphone+'</td> \
                    	<td><a href="#" onclick="edit(\''+ev.fid+ '\')">修改</a><br /><br/><a href="#" onclick="del(\''+ev.fid+ '\')">删除</a></td> \
                        <td id="fdefault">'+ev.fdefault+'</td> \
                    </tr>';
						   $(html).appendTo("#addressTool");
						});
            		    $("#addressTool tr #fdefault").each(function(i) {
			                var t = $(this).text();
			                if(t==1){
			                	$(this).text('默认地址');
			                }else if(t==0){
			                	$(this).text("");
			                }
            		    });
						$(".otherRow").mousemove(function(){
						   var id = $(this).attr("id");
							$(this).addClass("_color").siblings().removeClass("_color");
							if($(this).children().last().html()==""){
								var tdhtml =['<input type="button" value="设为默认" class="setting" onclick="do_click(\''+id+'\')"/>'].join("");
								$(this).children().last().html(tdhtml);
								//$(this).children().last().html("<a class='a1' href='javascript:void(0);' class='setting' onclick="+do_click(id)+">设为默认</a>");
								
							}
						});
						$(".otherRow").mouseleave(function(){
							$(this).removeClass("_color");
							if($(this).children().last().text()!="默认地址"){
								$(this).children().last().html("");
							}
						});
						window.getHtmlLoadingAfterHeight();
						/********************************************循环添加TR结束******************************/
						kkpager.pno =response.pageNo;
						kkpager.total =Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
						kkpager.totalRecords =response.totalRecords;
						kkpager.generPageHtml({
							click : function(n){
									window.gridUaddressTable(n);
									this.selectPage(n);
							},
							pno : response.pageNo,//当前页码
							total : Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize)),//总页码
							totalRecords : response.totalRecords,//总数据条数
							lang : {
								prePageText : '上一页',
								nextPageText : '下一页',
								totalPageBeforeText : '共',
								totalPageAfterText : '页',
								totalRecordsAfterText : '条数据',
								gopageBeforeText : '转到',
								gopageButtonOkText : '确定',
								gopageAfterText : '页',
								buttonTipBeforeText : '第',
								buttonTipAfterText : '页'
							}
					    });
						/********************************************渲染分页主键结束******************************/
					layer.close(loadDel); 
				}
		});
}

$(document).ready(function () {
	window.getHtmlLoadingBeforeHeight();
	window.gridUaddressTable(1);
});


//新建窗口
function create(){
	layer.open({
	    title: ['新建','background-color:#CC0000; color:#fff;'],
	    type: 2,
	    anim:true,
	    area: ['500px', '200px'],
	    content: "${ctx}/address/create.net"
	});
}
//修改窗口
function edit(obj){
		parent.layer.open({
			    title: ['修改','background-color:#CC0000; color:#fff;'],
			    type: 2,
			    anim:true,
			    area: ['500px', '200px'],
			    content: "${ctx}/address/new_edit.net?id="+obj
		});  
}

//删除
function del(obj){
			 $.ajax({
					type : "POST",
					url : "${ctx}/address/delete.net?ids="+obj,
					//data : getIds(),
					success : function(response) {
						if (response == "success") {
							parent.layer.msg('操作成功');
							window.gridUaddressTable(1);
						}else {
							parent.layer.msg('操作失败');
						}
					}
			  });
}

function do_click(obj){
	$.ajax({
			type : "POST",
			url : "${ctx}/address/isDefault.net",
			data :{"id":obj},
			success : function(response) {
				if (response == "success") {
					layer.msg('操作成功');
					window.gridUaddressTable(1);
				}else {
					layer.msg('操作失败');
				}
			}
	});
}

function save(obj){
	if($("#flinkman").val() =='' || $("#fphone").val() =='' || $("#fdetailaddress").val() ==''){
		parent.layer.msg('红色*为必填项,请完善信息！');
	}else{
		var obj =$("#addressform").serialize();
		$.ajax({
			type : "POST",
			url : "${ctx}/address/save.net" ,
			dataType:'text',
			data :obj,
			success : function(response) {
				if (response == "success") {
					parent.layer.msg('操作成功');
					$("#flinkman").val("");
					$("#fdetailaddress").val("");
					$("#fphone").val("");
					window.gridUaddressTable(1);
				}else {
					parent.layer.msg('操作失败');
				}
			}
		});
	}
}
</script>
</head>

<body style="background-color:#f1f1f1;">
	<div id="nav">
        <div id="container">
        	<p class="p1">
            	平台首页&nbsp;&nbsp;&gt;&nbsp;&nbsp;用户中心&nbsp;&nbsp;&gt;地址管理
            </p>
            <!--  <p class="p2"><span style="font-size:60px;">OLCPS</span><span style="font-size:32px;">.COM</span></p>-->
            <div class="address">
				<form id="addressform" method="post">
				<input type="hidden" id="fcustomerid" name="address.fcustomerid" value="${fcustomerid}"/>
                	<table cellpadding="0" cellspacing="0" border="0" width="1070" class="tbl_form">
                    	<tr height="124">
                        	<td colspan="3">
                            	<img src="${ctx}/css/images/hx-l.png" class="img_left"/>
                                <a href="javascript:void(0);" class="title_add">新增收货地址</a>
                                <img src="${ctx}/css/images/hx-r.png" class="img_right"/>
                            </td>
                        </tr>
</table>

<div class="addressMsg">
<ul>
                <li>  <span class="addressMsg_red">*</span><span class="addressMsg_01">收货人：</span><input type="text" id="flinkman" name="address.flinkman"/></li>
                                                                    
                 <li> <span class="addressMsg_red">*</span><span class="addressMsg_01">联系电话：</span><input type="text" id="fphone" name="address.fphone"/></li>
                            
                        
                        
                 <li> <span class="addressMsg_red">*</span><span class="addressMsg_01">收货地址：</span><input type="text" id="fdetailaddress" name="address.fdetailaddress" /></li>

				<li><span class="addressMsg_red">*</span><span class="addressMsg_01">坐标：</span>
				经度：<input type="text" id="flng" name="flng"  style="width:100px;margin-right:10px"/>
				纬度：<input type="text" id="flat" name="flat" style="width:100px;"/></li>				
				<li>

<fieldset align="center"  style="border:1px solid #c3c3c3;font-size:14px; line-height:50px;width:400px; margin:0 auto;text-align: center; display:none">
 <legend>距离计算</legend>
<p>起点：<input type="text" id="add_start" name="add_start"  style="width:250px;"/></p>
<p>终点：<input type="text" id="add_end" name="add_end" style="width:250px;""/></p>
<p style="margin-bottom:30px;">开车距离：<input type="text" id="add_jl" name="add_jl" style="width:250px;"/></p>
 </fieldset>
				</li>
				
                <li><div id="mapContainer"></div><div id="panel"></div> </li>
               
               <li style="text-align:center"> <input type="button" onclick="save()" value="保存" class="tab1_submit"/></li>
                
         </ul>  
         </div>     
                </form>

            <div class="address_list">
            	<div style="height:100px;padding-top:80px;">
                	<img src="${ctx}/css/images/hx-l.png" class="al_left"/>
                    <a href="javascript:void(0);" class="title_save">已保存地址</a>
                    <img src="${ctx}/css/images/hx-r.png" class="al_right"/>
                </div>
            	<table id="addressTool"  cellpadding="0" cellspacing="0" border="0" width="1070">
                    <tr height="40" style="background-color:#D9D7D7;">
                    <!-- <td width="50"><input id="checkaddr" type="checkbox" class="choice" onclick="selectCheckBoxAddr('choice')"/></td> -->
                    	<td width="150">收货人</td>
                    	<td width="400">收货地址</td>
                        <td width="180">手机</td>
                        <td width="170">操作</td>
                    	<td width="170">默认</td>
                    </tr>
                </table>
            </div>
            <div id="kkpager"></div>
        </div>
    </div>
<script type="text/javascript">
	//用户地址表格特效
	$(".otherRow").mousemove(function(){
		$(this).addClass("_color").siblings().removeClass("_color");
		if($(this).children().last().html()==""){
			$(this).children().last().html("<input type='button' value='设为默认' class='setting'/>");
		}
	});
	$(".otherRow").mouseleave(function(){
		$(this).removeClass("_color");
		if($(this).children().last().text()!="默认地址"){
			$(this).children().last().html("");
		}
	})
	//百分比宽度缩放
	$(window).resize(function(){
		if($(window).width()>1280){
			$("#head").width("100%");
			$("#foot").width("100%");
		}else{
			$("#foot").width(1280);
			$("#head").width(1280);
		}
	});

//2016年3月17日    ht  地图嵌入
var s_lng,s_lat,e_lng,e_lat;
var lnglat_start,lnglat_m;
var address,_distance;
var oFdetailaddress=document.getElementById("fdetailaddress");
var oAdd_start=document.getElementById("add_start");
var oAdd_end=document.getElementById("add_end");
var oLng=document.getElementById("flng");
var oLat=document.getElementById("flat");
var oAdd_jl=document.getElementById("add_jl");
//初始化加载地图时，若center及level属性缺省，地图默认显示用户当前城市范围
var map = new AMap.Map('mapContainer', {
    zoom:15
});
var lng_jz=map.getCenter().lng;//加载进来时候的经度
var lat_jz=map.getCenter().lat;//加载进来时候的纬度
//页面加载时执行
window.onload=function(){	
	regeocoder();
}
//经纬度解析成地址	
var  lnglatXY = [lng_jz,lat_jz]; //已知点的经纬度坐标
//打开后界面地址、经纬度默认赋值
function regeocoder() {  //逆地理编码
	s_lng=lng_jz;
	s_lat=lat_jz;	
    var geocoder_ldz = new AMap.Geocoder({
        radius: 1000,
        extensions: "all"
    });        
    geocoder_ldz.getAddress(lnglatXY, function(status, result) {
        if (status === 'complete' && result.info === 'OK') {
            geocoder_CallBack(result);
			oFdetailaddress.value = address;
			oAdd_start.value = address;
			oLng.value =lng_jz;
			oLat.value =lat_jz;
        }
    });        

}
function geocoder_CallBack(data) {
    address = data.regeocode.formattedAddress; //返回地址描述	
}

//地图中添加地图操作ToolBar插件
map.plugin(['AMap.ToolBar'], function() {
    //设置地位标记为自定义标记
    var toolBar = new AMap.ToolBar();
    map.addControl(toolBar);
});

//输入提示起点
var autoOptions = {
    input: "fdetailaddress"
};
var auto = new AMap.Autocomplete(autoOptions);
var placeSearch = new AMap.PlaceSearch({
    map: map
});  //构造地点查询类
AMap.event.addListener(auto, "select", select);//注册监听，当选中某条记录时会触发
function select(e) {
    placeSearch.setCity(e.poi.adcode);
    placeSearch.search(e.poi.name);  //关键字查询查
    oLng.value = e.poi.location.lng;
    oLat.value = e.poi.location.lat;
	s_lng=e.poi.location.lng;
	s_lat=e.poi.location.lat;
	oAdd_start.value=e.poi.name;
}

//输入提示终点
var autoOptions02= {
input: "add_end",
};
var auto02= new AMap.Autocomplete(autoOptions02);
var placeSearch02 = new AMap.PlaceSearch({
map: map
});  //构造地点查询类
AMap.event.addListener(auto02, "select", select02);//注册监听，当选中某条记录时会触发
function select02(e) {
placeSearch02.setCity(e.poi.adcode);
placeSearch02.search(e.poi.name);  //关键字查询查
oAdd_end.value=e.poi.name;
e_lng=e.poi.location.lng;
e_lat=e.poi.location.lat;


//构造路线导航类
var driving = new AMap.Driving({
    map: map,
    panel: "panel"
}); 
// 根据起终点经纬度规划驾车导航路线
driving.search(new AMap.LngLat(s_lng,s_lat), new AMap.LngLat(e_lng, e_lat));

//获取距离
setTimeout(function(){
	var str1=$('.planTitle p').text().split("(");
	var str2=str1[1].toString().split("公");
	//alert(str2[0]);
	oAdd_jl.value=parseFloat(str2[0])*1000+"米";//路线的距离
	},1000)

}

//为地图注册click事件获取鼠标点击出的经纬度坐标
var clickEventListener = map.on('click', function(e) {
	s_lng=e.lnglat.getLng();
	s_lat= e.lnglat.getLat();
    oLng.value = e.lnglat.getLng() ;
    oLat.value = e.lnglat.getLat();
});
var auto = new AMap.Autocomplete({
    input: "fdetailaddress"
});

//点击获取位置
  AMap.plugin('AMap.Geocoder',function(){
    var geocoder = new AMap.Geocoder({
        city: "全国"//城市，默认：“全国”
    });
    var marker = new AMap.Marker({
        map:map,
        bubble:true
    })
    map.on('click',function(e){
        marker.setPosition(e.lnglat);
        geocoder.getAddress(e.lnglat,function(status,result){
          if(status=='complete'){
        	 oFdetailaddress.value = result.regeocode.formattedAddress;
        	 oAdd_start.value = result.regeocode.formattedAddress
          }
        })
    })

});



</script>
</body>
</html>
