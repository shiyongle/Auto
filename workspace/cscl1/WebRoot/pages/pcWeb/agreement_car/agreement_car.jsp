<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>协议用车</title>

    <!-- Bootstrap -->
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/agreement_car.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <script src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/layer/layer.js"></script>
    <script src="${ctx}/pages/pcWeb/js/public.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/page.cscl.js" ></script>
    <script src="${ctx}/pages/pcWeb/js/agreement_car.js"></script>
     
    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
<body>
<form  class="form_container" id="agreement_form">
<!-- 类型选择 -->
<div class="jumbotron car_title aggrement_car_lin1">
<input type="hidden" name="specId" id="zheng_carname" class="zheng_carname" value=""/><!-- 车型 -->
<input type="hidden" name="protocolId" id="protocolId" value=""/>
	<div class="row" id="agreement-type">
	</div>
</div>
<!-- 下单界面-->
<div id="zheng">
	<!-- 订单类型 -->
	<input type="hidden" id="orderType" name="orderType" value="3"/>
	<input type="hidden" id="type" name="type" value="3"/>	
	<div class="jumbotron car_title" style="margin-bottom:5%">
		<div class="row">
			<div class="col-xs-12 car_mame "><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/time.png"/>&nbsp&nbsp&nbsp装车时间</div>
			<!-- 下拉时间 -->
			<div class="col-lg-5 col-md-5 col-sm-5 col-xs-6 input_left myForm" >
				<select class="form-control" id="time" name="zheng_time1">
				  <option value="useDate">--请输入预约用车日期--</option>
				</select>
				<a id="time_err" data-placement="right" title="请选择预约用车日期"></a>
			</div>
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-6 myForm">
				<select id="time2" class="form-control" name="zheng_time2">
				  <option value="useDate">--请输入预约用车时段--</option>
				  <option value="07:00-08:00">07:00  ~ 08:00</option>
				  <option value="08:00-09:00">08:00  ~ 09:00</option>
				  <option value="09:00-10:00">09:00  ~ 10:00</option>
				  <option value="10:00-11:00">10:00  ~ 11:00</option>
				  <option value="11:00-12:00">11:00  ~ 12:00</option>
				  <option value="12:00-13:00">12:00  ~ 13:00</option>
				  <option value="13:00-14:00">13:00  ~ 14:00</option>
				  <option value="14:00-15:00">14:00  ~ 15:00</option>
				  <option value="15:00-16:00">15:00  ~ 16:00</option>
				  <option value="16:00-17:00">16:00  ~ 17:00</option>
				  <option value="17:00-18:00">17:00  ~ 18:00</option>
				  <option value="18:00-19:00">18:00  ~ 19:00</option>
				  <option value="19:00-20:00">19:00  ~ 20:00</option>
				  <option value="20:00-21:00">20:00  ~ 21:00</option>
				  <option value="21:00-22:00">21:00  ~ 22:00</option>
				</select>
				<a id="time2_err" data-placement="right" title="请选择预约用车时段"></a>
			</div>
		</div>
		<!-- 提货 -->
		<div class="row">
			<div class="col-xs-12 car_mame "><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/ti.png"/>&nbsp&nbsp&nbsp提货地</div>
			<!-- 地址 -->
			<div class="col-lg-8 col-sm-11 input_left">
				<div class="col-xs-12 car_Smame">
					<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 提货地址
				</div>
				
				<div class="form-group myForm">
					<div class="input-group">
						<input type="text" class="form-control map" id="address_th" name="addressDeliver" placeholder="请点击地图选择地址" readonly>
						<input type="hidden"  name="deliverLongitude" id="zheng_address_th_lng">
						<input type="hidden"  name="deliverLatitude" id="zheng_address_th_lat">	
						<div class="input-group-addon add">
							<img src="${ctx}/pages/pcWeb/css/images/car/add.png" class="map_icon" width="14"/>
						</div>
						<div class="input-group-addon">
							<img src="${ctx}/pages/pcWeb/css/images/car/very.png" class="address_icon" width="14"/>
						</div>
					</div>
					<a id="address_th_err" data-placement="right" title="提货地址不能为空"></a>
				</div>
			</div>
			<!-- 联系人 -->
			<div class="form-group col-lg-5 col-md-5 col-sm-6 col-xs-6 input_left myForm">
			    <div class="col-xs-12 car_Smame">
					<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 联系人姓名
				</div>
			    <input type="text" class="form-control" id="contactName" name="addressDeliverName" placeholder="输入联系人姓名">
			    <a id="contactName_err" data-placement="right" title="联系人姓名不能为空"></a>
			</div>
			<div class="form-group col-lg-3 col-md-3 col-sm-5 col-xs-6 myForm">
			    <div class="col-xs-12 car_Smame">
					<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 联系电话
				</div>
			    <input type="text" class="form-control telValidate" id="contactPhone" name="addressDeliverPhone"  placeholder="输入联系电话">
			    <a id="contactPhone_err" data-placement="right" title="联系电话不能为空"></a>
			</div>
		</div>
		<!-- 卸点 -->
		<div class="row xie_huo zheng_xiehuo_div">
			<div id="div_xie">
				<div class="col-lg-7 col-md-5 col-sm-5 col-xs-5 car_mame ">
					<img class="icons" src="${ctx}/pages/pcWeb/css/images/car/xie.png"/>&nbsp&nbsp&nbsp卸货地	
				</div>
				<div class="col-lg-5 col-md-5 col-sm-5 col-xs-7 deletebtn firstBtn">
					<img src="${ctx}/pages/pcWeb/css/images/car/delete.png"/>
				</div>
				<div class="col-lg-8 col-sm-11 input_left">
					<div class="col-xs-12 car_Smame">
						<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 卸货地址
					</div>
					
					<div class="form-group myForm">
						<div class="input-group">
							<input type="text" class="form-control map" id="address_xh" name="addressReceipt" placeholder="请点击地图选择地址" readonly/>
							<input type="hidden"  name="longitude" id="zheng_address_xh_lng">
							<input type="hidden"  name="latitude" id="zheng_address_xh_lat">
							<div class="input-group-addon add">
								<img src="${ctx}/pages/pcWeb/css/images/car/add.png" class="map_icon" width="14"/>
							</div>
							<div class="input-group-addon">
								<img src="${ctx}/pages/pcWeb/css/images/car/very.png" class="address_icon" width="14"/>
							</div>
						</div>
						<a id="address_xh_err" data-placement="right" title="卸货地址不能为空"></a>
					</div>
				</div>
			
				<!-- 联系人 -->
				<div class="form-group col-lg-5 col-md-5 col-sm-6 col-xs-6 input_left myForm">
				<div class="col-xs-12 car_Smame">
					<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 联系人姓名
				</div>
				<input type="text" class="form-control" id="contactName2" name="addressReceiptName" placeholder="输入联系人姓名">
				<a id="contactName2_err" data-placement="right" title="联系人姓名不能为空"></a>
				</div>
				<div class="form-group col-lg-3 col-md-3 col-sm-5 col-xs-6 myForm">
				<div class="col-lg-12 car_Smame">
					<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 联系电话
				</div>
				<input type="text" class="form-control telValidate" id="contactPhone2" name="addressReceiptPhone" placeholder="输入联系电话">
				<a id="contactPhone2_err" data-placement="right" title="联系人电话不能为空"></a>
				</div>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="row add_btn">
			<div class="col-lg-8 col-md-11 col-sm-11 col-xs-12 input_left">
				<button type="button" class="btn btn-default btn-block btn_colorXie" id="add_unload" onclick="xie_btn()">
					<img src="${ctx}/pages/pcWeb/css/images/car/have.png"> 添加一个卸货点
				</button>
			</div>
		</div>
		<!-- 默认点数 -->
		<div class="row number">
			<div class="col-xs-12 car_mame "><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/map.png"/>&nbsp&nbsp&nbsp卸货点数</div>
			
			<div class="col-lg-8 col-sm-11 input_left">				
				<input type="text" id="disabledTextInput" name="fpoint" class="form-control zheng_xiehuo_number" placeholder="1" readonly value="1">
			</div>
		</div>
		<!-- 货物类型 -->
		<div class="row">
			<div class="col-xs-12 car_mame lingdan_hidden"><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/type.png"/>&nbsp&nbsp&nbsp货物类型</div>
			<div class="col-xs-12 car_mame lingdan_show"><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/type.png"/>&nbsp&nbsp&nbsp货物属性</div>
			<div class="col-lg-8 col-sm-11 input_left ">
				<div class="row lingdan_show">
					<div class="col-xs-12 color_text">
						<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 货物类型
					</div>
				</div>
				<div class="row items_type" id="zheng_goods_type_div"></div>
				<input type="hidden" name="goodsTypeId" id="zheng_goodstype" class="zheng_goodstype" value=""/>
				<input type="hidden" name="goodsTypeName" id="zheng_goodstypename" class="zheng_goodstypename" value=""/>				
			</div>
		</div>
		<!-- 货物长度 -->
		<div class="row lingdan_show">
			<div class="col-lg-8 col-sm-11 input_left form-group has-feedback myForm">
				<div class="row">
					<div class="col-xs-12 color_text">
						<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 货物长度
					</div>
				</div>
				<input type="text" class="form-control" id="goodsLength" name="length" placeholder="请输入长度" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
				<span class="form-control-feedback">厘米</span>
				<a id="goodsLength_err" data-placement="right" title="货物长度不能为空"></a>
			</div>
		</div>
		<!-- 货物数量 -->
		<div class="row lingdan_show">
			<div class="col-lg-8 col-sm-11 input_left form-group has-feedback myForm">
				<div class="row">
					<div class="col-xs-12 color_text">
						<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 货物数量
					</div>
				</div>
				<div style="cursor: pointer;">
					<span class="form-control-feedback piao" >立方</span>
				</div>
		  		<input type="text" class="form-control" id="goodsNumber" name="famount" placeholder="请输入数量" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
			 	<a id="goodsNumber_err" data-placement="right" title="货物数量不能为空"></a>
			</div>
			
		</div>
		<!-- 所需车型 -->
		<div class="row lingdan_hidden">
			<div class="col-xs-12 car_mame "><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/car.png"/>&nbsp&nbsp&nbsp所需车型</div>
			<div class="col-lg-8 col-sm-11 input_left">
				<div class="col-xs-12 car_Smame">
					车型（备选车型越多，叫车速度越快）
				</div>
				<div class="row items_type" id="zheng_car_type_div"></div>
				<input type="hidden" name="carTypeId" id="zheng_cartype" class="zheng_cartype" value="">
			</div>
		</div>
		<!-- 其他 -->
		<div class="row lingdan_hidden">
			<div class="col-xs-12 car_mame "><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/other.png"/>&nbsp&nbsp&nbsp其他</div>
			<div class="col-lg-8 col-sm-11 input_left">
				<div class="col-xs-12 car_Smame">
					其他（其他条件越多，叫车速度越慢）
				</div>
				<div class="row items_type" id="zheng_other_div">
				</div>
				<input type="hidden" name="otherId" id="zheng_carother" class="zheng_carother" value="">
			</div>
		</div>
		<!-- 增值服务 -->
		<div class="row bao_hidden">
			<div class="col-xs-12 car_mame"><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/serve.png"/>&nbsp&nbsp&nbsp增值服务</div>
			<div class="col-lg-8 col-sm-11 input_left ">
				<div class="row items_type" id="zheng_service_div">
					<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6 ">
						<button type="button" class="btn btn-default btn-block" data-service="装卸">装卸</button>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6 ">
						<button type="button" class="btn btn-default btn-block" data-service="电子回单">电子回单</button>
						<input type="hidden" name="freceiptSave" id="freceiptSave"/>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6 ">
						<button type="button" class="btn btn-default btn-block" data-service="回单原件返回">回单原件返回</button>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6 bv">
						<button type="button" class="btn btn-default btn-block" data-service="上楼">上楼</button>
					</div>
						<input type="hidden" name="fincrementServe" id="zheng_service" class="zheng_service" value=""/>
					<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6 input-group collection">
        				<span class="input-group-addon">代收货款</span>
         				<input type="text" class="form-control zheng_servicePay" id="zheng_servicePay" class="zheng_servicePay" name="purposeAmount" placeholder="金额">
         				<a id="zheng_servicePay_err" data-placement="right" title="代收货款只能一到七位整数"></a>
						<a id="zheng_servicePay_err2" data-placement="right" title="非空格"></a>
					</div>
				</div>
			</div>
		</div>
		<!-- 注意事项 -->
		<div class="row">
			<div class="col-xs-12 car_mame "><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/note.png"/>&nbsp&nbsp&nbsp注意事项</div>
			<div class="col-lg-8 col-sm-11 input_left">
				<textarea class="form-control zheng_mark" rows="3" name="fremark"></textarea>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="row">
			<div class="col-lg-8 col-sm-11 input_left submit_form">
			<div class="col-md-4 col-sm-12 mileage">
				<p>总里程约：<font id="zheng_order_distance">0</font>公里</p>
				<input type="hidden" name="mileage" id="zheng_mileage">
			</div>
			<div class="col-md-4 col-sm-12 cost">
				<p>预计运费<span id="zheng_order_price">0</span>元</p>
			</div>
			<div class="col-md-4 col-sm-12">
				<input type="hidden" name="pcWeb" id="pcWeb"  value="1"/>
				<button type="button" class="btn btn-danger btn-block red_btn btn-submit" id="btn-tem-zheng">提交订单</button>
			</div>
			</div>
		</div>
	</div>

</div>
<div>
</form>
<!--显示地图 -->
	<div id="map_container">
		<div class="map_line1">
		<div class="map_tip">点击地图选择地址</div>
		<div class="map_close">关闭</div>
		</div>
		<%@ include file="/pages/pcWeb/map/map_gd.jsp"%>	 	
	</div>
	<!--显示常用地址 -->
	<div id="address_container">
		<div class="address_line1">
		<div class="address_tip">已保存了<span id="total">0</span>条地址(单击选择)</div>
		<div class="address_close">关闭</div>
		</div>
		<!--收货地址数据列表-->
		<div class="table-responsive" style="overflow:hidden">  <!-- class="table-responsive"-->
		<table class="table table-hover" id="address_table"></table>
		<!--页码-->
		<div class="row">
			<div class="page col-xs-12">
			<div id="PageCode"></div>
			</div>
		</div>
		</div>
	</div>
	<div class="masker"></div>
</body>
</html>
<script>
var type;
$(document).ready(function(){
	/*根据用户的角色动态渲染协议规则*/
		  $.ajax({
		   	type:"post",
			url:"${ctx}/pcWeb/protocol/findProtocol.do",
			dataType:"json",
			success:function(response){							
// 				console.log(response);
				if(response.success=="true"){
					var html="";					
					$("#agreement-type").empty();
					$.each(response.data,function(i,e){	
						if(e.type==1){
						html+='<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12"><div>'+						
								  '<div class="mask_img"></div>'+
								  '<div class="car_border car_img_zheng" data-carspecid="'+e.carSpecId+'" data-id="'+e.id+'" data-type="'+e.type+'">'+
								  '<h1 class="car_big">车厢：'+e.carSpecName+'</h1>'+
								  '<p class="car_text">起步'+e.startKilometre+'公里'+e.startPrice+'元，超过每公里'+e.outKilometre+'元</p>'+
								  '</div></div></div>';								
						}
						if(e.type==2){
							var ling_unit;
							switch(e.funitId){//1:票 2：重量3：体积4：件5:面积6：托；
							case 1:ling_unit="票";break;
							case 2:ling_unit="重量";break;
							case 3:ling_unit="体积";break;
							case 4:ling_unit="件";break;
							case 5:ling_unit="面积";break;
							case 6:ling_unit="托";break;
							default:break;							
							}
	
						html+='<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12" name="ling"><div>'+					
								  '<div class="mask_img"></div>'+
								  '<div class="car_border car_img_ling" data-carspecid="'+e.carSpecId+'" data-id="'+e.id+'" data-othertypename="'+e.otherTypeName+'" data-type="'+e.type+'">'+
								  '<h1 class="car_big">按'+ling_unit+'计算</h1>'+
								  '<p class="car_text">起步价：'+e.startPrice+'元包'+e.startKilometre +'公里内'+e.startNumber+e.otherTypeName+'</p>'+
								  '<p class="car_text">超出每'+e.otherTypeName+e.outNumprice+'元，超出每公里'+e.outKilometre+'元</p>'+
								  '</div></div></div>';				
						}
						if(e.type==3){	
						html+='<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12"  name="bao"><div>'+
							  '<div class="mask_img"></div>'+
							  '<div class="car_border car_img_bao" data-carspecid="'+e.carSpecId+'" data-id="'+e.id+'" data-type="'+e.type+'">'+
							  '<h1 class="car_big">车厢：'+e.carSpecName+'</h1>'+
							  '<p class="car_text">一天8小时，价格'+e.timePrice+'元，邮费，过路费另付</p>'+
							  '</div></div></div>';
						}
					})
					if(response.data.length==0){
						html+='该用户暂无签订任何协议！';
					}
					$("#agreement-type").append(html);	
					getHtmlLoadingAfterHeight();
				}
			}
		})

		//点击协议规则后相关操作
		$(document).on("click",".car_border",function(){
			$("input:not(#pcWeb,.zheng_xiehuo_number),textarea").val("");//初始化
			$("#zheng_order_distance").text("0");//初始化
			$("#zheng_order_price").text("0");//初始化
			var carspecid=$(this).data("carspecid");
			var id=$(this).data("id");
			var othertypename=$(this).data("othertypename");
			type=$(this).data("type");//订单类型
			$("#zheng_carname").val(carspecid);//车型
			$("#protocolId").val(id);//协议类型
			$(".piao").text(othertypename);//零担的单位
			if(type==1){
				$("#orderType").val("1");
				$("#type").val("1");
			}
			if(type==2){
				$("#orderType").val("2");
				$("#type").val("2");
			}			
			if(type==3){//包天的运费计算
				$("#orderType").val("3");
				$("#type").val("3");
				orderPrice_xie();
			}
		})
		
// 	弹出地图
	$(document).on("click",".map_icon,.map",function(e){
		masker();
		var h=e.pageY+100;
		$("#map_container").css({"top":h+"px"}).show();
		$("#tipinput").val("");//清空地图的搜索框
		$(".masker").show();		
	})
// 关闭地图
	$(document).on("click",".map_close",function(){
		$("#map_container").hide();
		$(".masker").hide();
		setTimeout(function(){
			orderPrice_xie();//协议价格计算
		},500);
	})

	// 	弹出地址
	var address_i;//第几个地址
	$(document).on("click",".address_icon",function(e){
		address_i=$(".address_icon").index(this);
		var h=e.pageY+100;
		$("#address_container").css({"top":h+"px"}).show();
		$(".masker").show();
	})
	// 关闭地址
	$(document).on("click",".address_close",function(){
		$("#address_container").hide();
		$(".masker").hide();
	})
	
var loadAddress;
var size=6;//一页显示多少条
var total;//总共多少条记录
loadAddress=$.ajax({
	type:"post",
	url:"${ctx}/app/query/loadAddress.do?"+"type=2&pagesize="+size+"&pagenum=1",
	success:function(response){
		response=JSON.parse(response);
// 		console.log(response.data);
		if(response.success=="true"){				
		$("#total").text(response.total);//总共多少条地址
		total=$("#total").text();//总共多少条
		$("#address_table").empty();
		var html=""; 
		$.each(response.data, function(i,e) {
			html+='<tr>'+
			'<td>'+e.linkMan+'</td>'+
			'<td data-lng="'+e.longitude+'" data-lat="'+e.latitude+'">'+e.addressName+'</td>'+				
			'<td>'+e.linkPhone+'</td>'+
			'</tr>';
		});
		$("#address_table").append(html);
		}
		
		
		//页码配置
	    $("#PageCode").createPage({
	        pageCount:Math.ceil(total/size),//向下取整
	        current:1,
	        backFn:function(p){//回调函数，p为点击的那页
	        	$.ajax({
	            	type:"post",
	        		url:"${ctx}/app/query/loadAddress.do?"+"type=2&pagesize="+size+"&pagenum="+p,
	        		success:function(response){
	        			response=JSON.parse(response);
	        			if(response.success=="true"){				
	        			$("#total").text(response.total);//总共多少条地址
	        			$("#address_table").empty();
	        			var html=html_table_head; 
	        			$.each(response.data, function(i,e) {
	        				html+='<tr>'+
	        				'<td>'+e.linkMan+'</td>'+
	        				'<td data-lng="'+e.longitude+'" data-lat="'+e.latitude+'">'+e.addressName+'</td>'+				
	        				'<td>'+e.linkPhone+'</td>'+
	        				'</tr>';
	        			});
	        			$("#address_table").append(html);
	        			}
	        		}
	        	});
	        }
	    });
	}
});	

//选择地址
$(document).on("click","#address_table tr",function(){
	var name=$(this).find("td:eq(0)").text(),
	 	add=$(this).find("td:eq(1)").text(),
	    tel=$(this).find("td:eq(2)").text(),
		lng=$(this).find("td:eq(1)").data("lng"),
		lat=$(this).find("td:eq(1)").data("lat");
	$(".address_icon").eq(address_i).parents(".input-group").find("input").eq(0).val(add);
	$(".address_icon").eq(address_i).parents(".input-group").find("input").eq(1).val(lng);
	$(".address_icon").eq(address_i).parents(".input-group").find("input").eq(2).val(lat);
	$(".address_icon").eq(address_i).parents(".input_left").next().find("input").val(name);
	$(".address_icon").eq(address_i).parents(".input_left").next().next().find("input").val(tel);
	orderPrice_xie();//协议价格计算
	$(".address_close").click();
})
		
		
		
		
	// 获取电脑时间（整车，临担时间）
	var myDate = new Date();
	var year=myDate.getFullYear();//获取当前的年份
	var month=myDate.getMonth()+1;//获取当前月份
	var day=myDate.getDate();//获取当前天数
	var hours=myDate.getHours();//获取当前的小时	
	$("#time2").find("option").each(function(i,e){
		if(i!=0){
			var order_hours=$(this).val();
			order_hours=order_hours.split("-")[1].split(":")[0];//获取页面选择框里的小时
			if(order_hours<=eval(hours+2)){
				$(this).attr("disabled",true).hide();
			}
		}
	})
	$(document).on("change","#time",function(){
		$("#time option").each(function(i,e){
		if($("#time option").eq(2).prop("selected")||$("#time option").eq(3).prop("selected")){
			$("#time2").find("option").each(function(i,e){									
				$(this).attr("disabled",false).show();									
			})
			}
		else{
			$("#time2").find("option").each(function(i,e){
				if(i!=0){
					var order_hours=$(this).val();
					order_hours=order_hours.split("-")[1].split(":")[0];//获取页面选择框里的小时
					if(order_hours<=hours+2){
						$(this).attr("disabled",true).hide();
					}
				}
			})
		}
		})
				
	})
	
	

	var day1=year+"-"+month+"-"+day;//今天
	var day2=year+"-"+month+"-"+(day+1);//明天
	var day3=year+"-"+month+"-"+(day+2);//后天
	$("#time").append("<option value='"+day1+"'>今天　"+day1+"</option>");
	$("#time").append("<option value='"+day2+"'>明天　"+day2+"</option>");
	$("#time").append("<option value='"+day3+"'>后天　"+day3+"</option>");
	
	
	
	
	
	
	/*协议下单类型选择（整车，零担，包天）*/
	$(document).on("click",".car_border",function(){
		
		$(this).parent().addClass("mask");//添加遮罩层
		$(this).prev().css("display","block");//添加钩钩
		$(this).parent().parent().siblings().find(".mask").removeClass("mask");//移除遮罩层
		$(this).parent().parent().siblings().find(".mask_img").css("display","none");//隐藏钩钩
// 		$(".form_container").attr("style","block");
		$("#zheng").show();
		var _name = $(this).parent().parent().attr("name");
		//获取是否零担，改变相应样式
		if(_name=="ling"){
			$(".lingdan_show").css("display","block");
			$(".lingdan_hidden").css("display","none");
			getHtmlLoadingAfterHeight();
		}else{
			$(".lingdan_show").css("display","none");
			$(".lingdan_hidden").css("display","block");
			getHtmlLoadingAfterHeight();
		}
		//获取是否包天，包天没有卸货点
		if(_name =="bao"){
			$(".xie_huo,.add_btn,.number").css("display","none");	
			$(".bao_hidden").css("display","none");			
			$("#div_xie").find("input").each(function(){$(this).val("0")}).attr("disabled",true);//卸货点地址等不提交,赋值跳过验证
			$(".telValidate").each(function(i,e){if(i!=0){$(this).val("13812345678")}});//电话号码赋值一个正确的值，但不会提交，跳过手机号码的验证
			$("#zheng_xiehuo_number").attr("disabled",true);//卸货点数不提交
			getHtmlLoadingAfterHeight(); 
		}else{
			$(".xie_huo,.add_btn,.number").css("display","block");
			$(".bao_hidden").css("display","block");
			$("#div_xie").find("input").attr("disabled",false);
			$("#zheng_xiehuo_number").attr("disabled",false);
			getHtmlLoadingAfterHeight();
		}
		
		
		/*页面高度增加了*/
		var screenWidth = $(window).width();//当前窗口宽度  
		var screenHeight = $(window).height();//当前窗口高度  
		$(".masker").css({"height":screenHeight,"width":screenWidth});
		
	})
	
 	
	/*页面上所有条件选择的边框效果*/
 	$(document).on("click",".items_type button",function(){
		$(this).toggleClass("lin_color_btn");		 			
 	});
	// 点击整单增值服务算法
	$(document).on("click","#zheng_service_div button",function(){
	 	var zeng_car_service_val="";
		$("#zheng_service_div button").each(function(i,e){
			var r=$(e).attr("class").indexOf("lin_color_btn");
			if(r!=-1){
				zeng_car_service_val+=$(e).data("service")+",";
			}
		})
		zeng_car_service_val=zeng_car_service_val.substring(0,zeng_car_service_val.length-1);
		console.log(zeng_car_service_val);
		$("#zheng_service").val(zeng_car_service_val);
		orderPrice_xie();
	})
	/*货物类型加载*/
 		$.ajax({
 			type:"post",
 			url:"${ctx}/pcWeb/order/loadGoods.do", 			
 			success:function(response){ 				
 				var list=JSON.parse(response);
 				list=list.data; 				
 				var html="";
 				$("#zheng_goods_type_div").empty();
 				$.each(list,function(i){
						html+='<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6 ">'+
						'<button type="button" class="btn btn-default btn-block" data-protype="'+list[i].optionId+'">'+list[i].optionName+'</button>'+
						'</div>';
 				});
 				$("#zheng_goods_type_div").append(html);
 			}
 		}); 
	
		/*车型变化*/
		var optionId=2;
		$.ajax({
			type:"post",
			url:"${ctx}/pcWeb/order/loadCarType.do?optionId="+optionId,
			success:function(response){
				var list=JSON.parse(response);
				list=list.data;
				var html="";
				$("#zheng_car_type_div").empty();
				$.each(list,function(i,e){
					html+='<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6 ">'+
					      '<button type="button" class="btn btn-default btn-block" data-caroptionid="'+optionId+'" data-cartype="'+e.optionId+'">'+e.optionName+'</button>'+
						  '</div>';
				});
				$("#zheng_car_type_div").append(html);
			}
		});
		/*整车其他条件变化*/
		$.ajax({
			type:"post",
			url:"${ctx}/pcWeb/order/CarOther.do?optionId="+optionId, 					
			success:function(response){
				var list=JSON.parse(response);
				list=list.data;
				var html="";
				$("#zheng_other_div").empty();
				if(list.length!=0){									
					$.each(list,function(i,e){
						html+='<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6 ">'+
						  '<button type="button" class="btn btn-default btn-block" data-other="'+e.optionId+'">'+e.optionName+'</button>'+
						  '</div>';
					});						
				}
				else{
					html+='<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6 ">暂无其他条件！</div>';
				}
				$("#zheng_other_div").append(html);
				
			}
		});
	
		/*整车车型选择的判断效果,一定要放在车型加载后*/
	 	var car_type_i=0,f=0,judge;
	 	$(document).on("click","#zheng_car_type_div button",function(){	
			car_type_i=$("#zheng_car_type_div button").index(this);		
	 		if(car_type_i==0){//点击任意车型 
	 		if(f==0){ 			
	 			$(this).parent().siblings().find("button").removeClass("lin_color_btn").attr("disabled",true);
	 			f=1;
	 			}
	 		else{
	 			$(this).parent().siblings().find("button").attr("disabled",false);
	 			f=0;
	 			}
	 		
	 		}
	 		else{//点击其他
	 			$("#zheng_car_type_div button:gt(0)").each(function(i,e){
	 			judge=$("#zheng_car_type_div button:gt(0)").eq(i).attr("class").indexOf("lin_color_btn"); 			 			
	 			if(judge!=-1){//包含了class'lin_color_btn'	 				
	 				$("#zheng_car_type_div button").eq(0).removeClass("lin_color_btn").attr("disabled",true);//任意车型不可点 
	 								
	 				return false;
	 			}
	 			else{
	 				$("#zheng_car_type_div button").eq(0).removeClass("lin_color_btn").attr("disabled",false);//任意车型可点 			 				
	 			} 		
	 			})
	 		}

	 	});		

	/*零担的运费计算*/
	$(document).on("blur","#goodsNumber",function(){
		setTimeout(function(){
			orderPrice_xie();//协议价格计算
		},500);
	})
	 	
	 	
 	/*订单提交验证*/
	$("#btn-tem-zheng").click(function(){		
		var val = ["time","time2","address_th","contactName","contactPhone","address_xh","contactName2","contactPhone2"];		
		for(var i=0;i<val.length;i++){
			var name=$("#"+val[i]).val();
			if(name==""||name==null||name=="useDate"){
				$("#"+val[i]).focus();
				$("#"+val[i]+"_err").tooltip("show");
				setTimeout(function(){
					$("#"+val[i]+"_err").tooltip("hide");
				},1000);
				return false;
			}
		}
		//代收货款控制不能输入纯空格
		if($('#zheng_servicePay').val()){
			if(/^\s+$/.test($('#zheng_servicePay').val())){
				$('#zheng_servicePay').focus();
				$("#zheng_servicePay_err2").tooltip("show");
				setTimeout(function(){
					$("#zheng_servicePay_err2").tooltip("hide");
				},1000);
				return;
			}
		}
		//代收货款控制一到七位整数
		if($('#zheng_servicePay').val()){
			if(!/^\d{1,7}$/.test($('#zheng_servicePay').val().trim())){
				$('#zheng_servicePay').focus();
				$("#zheng_servicePay_err").tooltip("show");
				setTimeout(function(){
					$("#zheng_servicePay_err").tooltip("hide");
				},1000);
				return;
			}
		}
		
		/*车型(多选)*/
		var car_type_val="";
		$("#zheng_car_type_div button").each(function(i,e){
			var r=$(this).attr("class").indexOf("lin_color_btn");
			if(r!=-1){
				car_type_val+=$(this).data("cartype")+",";
			}
		})		
		car_type_val=car_type_val.substring(0,car_type_val.length-1);
		$("#zheng_cartype").val(car_type_val);
		
		/*车型的其他条件(多选)*/
		var car_typeOther_val="";
		$("#zheng_other_div button").each(function(i,e){
			var r=$(this).attr("class").indexOf("lin_color_btn");
			if(r!=-1){
				car_typeOther_val+=$(this).data("other")+",";
			}
		})		
		car_typeOther_val=car_typeOther_val.substring(0,car_typeOther_val.length-1);
		$("#zheng_carother").val(car_typeOther_val);
		
		/*货物类型(多选)*/
		var car_goodstypeid_val="";
		var car_goodstypename_val="";
		$("#zheng_goods_type_div button").each(function(i,e){
			var r=$(this).attr("class").indexOf("lin_color_btn");
			if(r!=-1){
				car_goodstypeid_val+=$(this).data("protype")+",";
				car_goodstypename_val+=$(this).text()+",";
			}
		})		
		car_goodstypeid_val=car_goodstypeid_val.substring(0,car_goodstypeid_val.length-1);
		car_goodstypename_val=car_goodstypename_val.substring(0,car_goodstypename_val.length-1);
		$("#zheng_goodstype").val(car_goodstypeid_val);
		$("#zheng_goodstypename").val(car_goodstypename_val);
		
		/*增值服务(多选)*/
		var car_service_val="";
		$("#zheng_service_div button").each(function(i,e){
			var r=$(this).attr("class").indexOf("lin_color_btn");
			if(r!=-1){
				car_service_val+=$(this).data("service")+",";
			}
		})
		car_service_val=car_service_val.substring(0,car_service_val.length-1);
		if(car_service_val.indexOf("电子回单")!=-1)
		{
			$("#freceiptSave").val(1);
		}
		else{
			$("#freceiptSave").val("");
		}
		$("#zheng_service").val(car_service_val);
		/*公里数*/		
		var mileage=$("#zheng_order_distance").text();
		$("#zheng_mileage").val(mileage);
		
		
		//验证手机号码
		var telOk=true;
		$.each($(".telValidate"),function(i,e){
				var tel=$(e).val();
				if(tel!=""){					
					if(!(checkTel(tel)||checkPhone(tel))){
						top.layer.alert('联系电话格式为"13812345678"或"0577-12345678"');	
						$(e).focus()
						telOk=false;
						return false;
					}
					else{
						telOk=true;
					}
				}
		});
		/*提交整车订单*/
		if(telOk){			
			var data=$("#agreement_form").serialize();	
			$.ajax({
				type:"post",
				url:"${ctx}/pcWeb/order/saveProtocolOrder.do",
				data :data,
				dataType:"json",
				success:function(response){
					console.log(response);
					if(response.success=="false"){//失败
						var height=$(parent.window.document).height();
						$(parent.window.document).find("body").animate({scrollTop:(height/3)+"px"},500)
						top.layer.alert(response.msg);
						return false;
					}
					location.href="${ctx}/pcWeb/pcWebPay/pcWebPayMent.do?id="+response.data.id;
					$("body").append('<div class="loading"><img src="${ctx}/pages/pcWeb/css/images/payment/loading.gif"/></div>');
					masker();
					$(".masker,.loading").show();					
					$(parent.document).find("html,body").animate({"scrollTop":parent.document.getElementById("iframepage").height/3+"px"},500);
				}			
			});
		}

	})
	
})

//协议用车卸货点添加
var _i = 1;
function xie_btn()
{	
	if($('#address_th').val() == ''){
		top.layer.alert('请先填写提货地址！！！')
		$('#address_th').focus();
		return;
	}
	if($('#address_xh').val() == ''){
		top.layer.alert('请先填写卸货点1！！！')
		$('#address_xh').focus();
		return;
	}
	$("#zheng_servicePay").attr("disabled",true);//卸货地址不止1个的时候，代收款可编辑
	var html='<div id="zheng_div_xie'+_i+'">'+
			 '<div class="col-lg-7 col-md-5 col-sm-5 col-xs-5 car_mame ">'+
			 '<img class="icons" src="${ctx}/pages/pcWeb/css/images/car/xie.png"/>&nbsp&nbsp&nbsp卸货地'+
			 '</div>'+
			 '<div class="col-lg-5 col-md-5 col-sm-5 col-xs-7 deletebtn">'+
			 '<img src="${ctx}/pages/pcWeb/css/images/car/delete.png"/>'+
			 '</div>'+
			 '<div class="col-lg-8 col-sm-11 input_left">'+
			 '<div class="row">'+
			 '<div class="col-xs-12 color_text">'+
			 '<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 卸货地址'+
			 '</div>'+
			 '</div>'+
			 '<div class="form-group">'+
			 '<div class="input-group">'+
			'<input type="text" class="form-control map zheng_address_xh'+_i+'" id="address_xh'+_i+'" name="addressReceipt'+'" placeholder="请点击地图选择地址" readonly>'+
			'<input type="hidden"  name="longitude" id="zheng_address_xh_lng'+_i+'">'+
			'<input type="hidden"  name="latitude" id="zheng_address_xh_lat'+_i+'">'+
			'<div class="input-group-addon add">'+
			'<img src="${ctx}/pages/pcWeb/css/images/car/add.png" class="map_icon" width="14"/>'+
			'</div>'+
			'<div class="input-group-addon">'+
			'<img src="${ctx}/pages/pcWeb/css/images/car/very.png" class="address_icon" width="14"/>'+
			'</div>'+
			'</div>'+
			'</div>'+
			'</div>'+
			'<div class="form-group col-lg-5 col-md-5 col-sm-6 col-xs-6 input_left">'+
			'<div class="row">'+
			'<div class="col-xs-12 color_text">'+
			'<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 联系人姓名'+
			'</div>'+
			'</div>'+
			'<input type="text" class="form-control zheng_contactName'+(2+_i)+'" id="contactName"'+(2+_i)+' name="addressReceiptName'+'" placeholder="输入联系人姓名">'+
			'</div>'+
			'<div class="form-group col-lg-3 col-md-3 col-sm-5 col-xs-6 ">'+
			'<div class="row">'+
			'<div class="col-lg-12 color_text">'+
			'<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 联系电话'+
			'</div>'+
			'</div>'+
			'<input type="text" class="form-control zheng_contactPhone'+(2+_i)+' telValidate" id="contactPhone'+(2+_i)+'" name="addressReceiptPhone'+'" placeholder="输入联系电话">'+
			'</div>'+
			'</div>';
	if( _i<=4)
	{	
		if(_i>=2){
			if($('#address_xh'+(_i-1)).val() == ''){
				top.layer.alert('请先填写卸货点'+(_i));
				$('#address_xh'+(_i-1)).focus();
				return;
			}
		}
		$(".zheng_xiehuo_div").append(html);
		$(".zheng_xiehuo_number").attr("placeholder",""+(_i+1)+"").attr("value",""+(_i+1)+"");
		_i++;
		getHtmlLoadingAfterHeight();//iframe高度动态变化
	}
	// 当卸货点有5个时，移除onclick
	if(_i==5)
	{
		$("#add_unload1").removeAttr("onclick");
	}
	
	if(_i>=1)
	{
		$("#zheng .deletebtn").not('.firstBtn').css("visibility","inherit");
		$("#zheng_div_xie .deletebtn").attr("style","visibility:hidden");
		//删除卸货地点
		var zheng_xie_index = _i;
		var delArr = document.getElementsByClassName('deletebtn');
		for(var i=0; i<delArr.length; i++){
			;(function(nowI){
				delArr[i].onclick = function(){
					//删除第2个卸货点
					if(nowI+1<zheng_xie_index){
						top.layer.alert('不能从中间删除卸货地！！！');
						return false;
					}
					if(zheng_xie_index==2){
						$("#zheng_servicePay").attr("disabled",false);//卸货地址只剩1个的时候，代收款不可编辑
						address_e2_lnglat=[];
						$(".map_close").click();//触发地图距离的计算事件
					}
					//删除第3个卸货点
					if(zheng_xie_index==3){
						address_e3_lnglat=[];
						$(".map_close").click();//触发地图距离的计算事件
					}
					//删除第4个卸货点
					if(zheng_xie_index==4){
						address_e4_lnglat=[];
						$(".map_close").click();//触发地图距离的计算事件
					}
					//删除第5个卸货点
					if(zheng_xie_index==5){
						address_e5_lnglat=[];
						$(".map_close").click();//触发地图距离的计算事件
					}
					// 当卸货点小于5个时，绑定onclick
					if(zheng_xie_index<6)
					{
						$("#add_unload1").attr("onclick","zheng_xie_btn();");
					}
					$(this).parent().remove();	//移除卸货点
					zheng_xie_index--;//i--
					$(".zheng_xiehuo_number").attr("placeholder",zheng_xie_index).attr("value",zheng_xie_index);//卸货点数修改
					_i = zheng_xie_index;//xie_index的值给xie_i;
					getHtmlLoadingAfterHeight();//iframe高度动态变化
					return false;
				}
			})(i)
		}
	}else
	{
		$("#zheng .deletebtn").css("visibility","hidden");
	}
	//判断个数
	if($("#zheng .deletebtn").length===1)//判断卸货点个数==1，隐藏按钮
	{
		$("#zheng .deletebtn").css("visibility","hidden");
		
	}
}

/*协议返回运费*/
function orderPrice_xie(){
	var data;
	var protocolId=$("#protocolId").val();
	var fpoint=$(".zheng_xiehuo_number").val();
	var specId=$("#zheng_carname").val();
	var famount=$("#goodsNumber").val();
	var mileage=$("#zheng_order_distance").text();
	var fincrementServe = $("#zheng_service").val();
	if(type==1){
		data={protocolId:protocolId,orderType:type,fpoint:fpoint,specId:specId,mileage:mileage,fincrementServe:fincrementServe};
	}
	if(type==2){
		data={protocolId:protocolId,orderType:type,fpoint:fpoint,famount:famount,mileage:mileage,fincrementServe:fincrementServe};
	}
	if(type==3){
		data={protocolId:protocolId,orderType:type,specId:specId,fincrementServe:fincrementServe};
	}
			  $.ajax({
				   type:"post",
					url:"${ctx}/pcWeb/order/proRuleCost.do",
					data:data,
					dataType:'json',
					success:function(response){						
// 						console.log(response);
						if(response.success=="true"){							
						$("#zheng_order_price").text(response.data);
						}
					}
				})
}

/*遮罩*/
function masker(){
	var screenWidth = $(window).width();//当前窗口宽度  
	var screenHeight = $(window).height();//当前窗口高度  
	$(".masker").css({"height":screenHeight,"width":screenWidth});
}
</script>
