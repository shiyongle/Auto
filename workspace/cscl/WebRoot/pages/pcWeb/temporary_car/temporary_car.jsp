<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
  <meta charset="utf-8">
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
	<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>临时整车、零担下单</title>

    <!-- Bootstrap -->
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/temporary_car.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <script src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/layer/layer.js"></script>
    <script src="${ctx}/pages/pcWeb/js/public.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/page.cscl.js" ></script>
    <script src="${ctx}/pages/pcWeb/js/temporary_car.js"></script>

    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
<body>
	<!-- 分页头 -->
	<div class="jumbotron container_title">
		<ul id="myTab" class="row nav nav-tabs">
			<li class="col-lg-2 col-md-2 col-sm-3 col-xs-6">
				<a href="#zheng" data-toggle="tab" style="color: #E64249 !important;border-bottom: 2px solid #E64249!important;" id="zheng_tab">整车</a>
			</li>
			<li class="col-lg-2 col-md-2 col-sm-3 col-xs-6">
				<a href="#lin" data-toggle="tab" id="lin_tab">零担</a>
			</li>
			<input type="hidden" id="orderType" value="1"/>
		</ul>
	</div>
	<!-- 分页内容 -->
		<div class="jumbotron container_tem">
			<div id="myTabContent" class="tab-content">
				<!-- 临时用车整车下单 -->
				<div class="tab-pane fade in active" id="zheng">
				<form id="form-tem-zheng" name="order" method="post">
				<input type="hidden" name="type" value="1">
				<input type="hidden" name="orderType" value="1">
					<div class="row">
						<div class="col-xs-12 car_mame "><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/time.png"/>&nbsp&nbsp&nbsp装车时间</div>
						<!-- 下拉时间 -->
						<div class="col-lg-5 col-md-5 col-sm-5 col-xs-6 input_left" >
							<select class="form-control zheng_time1" id="time" name="zheng_time1">
							  <option value="useDate">--请输入预约用车日期--</option>
							</select>
							<a id="time_err" data-placement="right" title="请选择预约用车日期"></a>
						</div>
						<div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
							<select class="form-control zheng_time2" id="time2" name="zheng_time2">
							  <option value="useDate">--请输入预约用车时段--</option>
							  <option value="07:00-08:00">07:00 ~ 08:00</option>
							  <option value="08:00-09:00">08:00 ~ 09:00</option>
							  <option value="09:00-10:00">09:00 ~ 10:00</option>
							  <option value="10:00-11:00">10:00 ~ 11:00</option>
							  <option value="11:00-12:00">11:00 ~ 12:00</option>
							  <option value="12:00-13:00">12:00 ~ 13:00</option>
							  <option value="13:00-14:00">13:00 ~ 14:00</option>
							  <option value="14:00-15:00">14:00 ~ 15:00</option>
							  <option value="15:00-16:00">15:00 ~ 16:00</option>
							  <option value="16:00-17:00">16:00 ~ 17:00</option>
							  <option value="17:00-18:00">17:00 ~ 18:00</option>
							  <option value="18:00-19:00">18:00 ~ 19:00</option>
							  <option value="19:00-20:00">19:00 ~ 20:00</option>
							  <option value="20:00-21:00">20:00 ~ 21:00</option>
							  <option value="21:00-22:00">21:00 ~ 22:00</option>
							</select>
							<a id="time2_err" data-placement="right" title="请选择预约用车时段"></a>
						</div>
					</div>
					<!-- 提货 -->
					<div class="row lin_car">
						<div class="col-xs-12 car_mame "><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/ti.png"/>&nbsp&nbsp&nbsp提货地</div>
						<!-- 地址 -->
						<div class="col-lg-8 col-sm-11 input_left">
							<div class="row">
								<div class="col-xs-12 car_Smame color_text">
									<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 提货地址
								</div>
							</div>
							
							<div class="form-group">
								<div class="input-group">
									<input type="text" class="form-control map zheng_address_th" id="address_th" name="addressDeliver" placeholder="请点击地图选择地址" readonly>
									<input type="hidden"  name="deliverLongitude" id="zheng_address_th_lng">
									<input type="hidden"  name="deliverLatitude" id="zheng_address_th_lat">	
									<div class="input-group-addon add">
										<img src="${ctx}/pages/pcWeb/css/images/car/add.png" class="map_icon" width="14"/>
									</div>
									<div class="input-group-addon">
										<img src="${ctx}/pages/pcWeb/css/images/car/very.png" class="address_icon" width="14"/>
									</div>
								</div>
							</div>
							<a id="address_th_err" data-placement="right" title="提货地址不能为空"></a>
						</div>
						
						<!-- 提货地址联系人 -->
						<div class="form-group col-lg-5 col-md-5 col-sm-6 col-xs-6 input_left">
							<div class="row">
							    <div class="col-xs-12 color_text">
									<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 联系人姓名
								</div>
							</div>
						    <input type="text" class="form-control zheng_contactName" id="contactName" name="addressDeliverName" placeholder="输入联系人姓名">
						    <a id="contactName_err" data-placement="right" title="联系人姓名不能为空"></a>
						</div>
						<div class="form-group col-lg-3 col-md-3 col-sm-5 col-xs-6">
							<div class="row">
							    <div class="col-xs-12 color_text">
									<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 联系电话
								</div>
							</div>
						    <input type="text" class="form-control zheng_contactPhone telValidate" id="contactPhone" name="addressDeliverPhone" placeholder="输入联系电话">
						    <a id="contactPhone_err" data-placement="right" title="联系人电话不能为空"></a>
						</div>
					</div>
					<!-- 卸点 -->
					<div class="row xie_huo zheng_xiehuo_div">
						<div id="zheng_div_xie">
							<div class="col-lg-7 col-md-5 col-sm-5 col-xs-5 car_mame ">
								<img class="icons" src="${ctx}/pages/pcWeb/css/images/car/xie.png"/>&nbsp&nbsp&nbsp卸货地	
							</div>
							<div class="col-lg-5 col-md-5 col-sm-5 col-xs-7 deletebtn">
								<img src="${ctx}/pages/pcWeb/css/images/car/delete.png"/>
							</div>
							<div class="col-lg-8 col-sm-11 input_left">
								<div class="row">
									<div class="col-xs-12 color_text">
										<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 卸货地址
									</div>
								</div>
								
								<div class="form-group">
									<div class="input-group">
										<input type="text" class="form-control map zheng_address_xh" id="address_xh" name="addressReceipt" placeholder="请点击地图选择地址" readonly>
										<input type="hidden"  name="longitude" id="zheng_address_xh_lng">
									    <input type="hidden"  name="latitude" id="zheng_address_xh_lat">
										<div class="input-group-addon add">
											<img src="${ctx}/pages/pcWeb/css/images/car/add.png" class="map_icon" width="14"/>
										</div>
										<div class="input-group-addon">
											<img src="${ctx}/pages/pcWeb/css/images/car/very.png" class="address_icon" width="14"/>
										</div>
									</div>
								</div>
								<a id="address_xh_err" data-placement="right" title="卸货地址不能为空"></a>	
							</div>
							<!-- 联系人 -->
							<div class="form-group col-lg-5 col-md-5 col-sm-6 col-xs-6 input_left">
							<div class="row">
								<div class="col-xs-12 color_text">
									<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 联系人姓名
								</div>
							</div>
							<input type="text" class="form-control zheng_contactName2" id="contactName2" name="addressReceiptName" placeholder="输入联系人姓名">
							<a id="contactName2_err" data-placement="right" title="联系人姓名不能为空"></a>
							</div>
							<div class="form-group col-lg-3 col-md-3 col-sm-5 col-xs-6 ">
							<div class="row">
								<div class="col-lg-12 color_text">
									<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 联系电话
								</div>
							</div>
							<input type="text" class="form-control zheng_contactPhone2 telValidate" id="contactPhone2" name="addressReceiptPhone" placeholder="输入联系电话">
							<a id="contactPhone2_err" data-placement="right" title="联系人电话不能为空"></a>
							</div>
						</div>
					</div>
					<!-- 按钮 -->
					<div class="row add_btn">
						<div class="col-lg-8 col-md-11 col-sm-11 col-xs-12 input_left">
							<button type="button" class="btn btn-default btn-block btn_colorXie" id="add_unload1" onclick="zheng_xie_btn()">
								<img src="${ctx}/pages/pcWeb/css/images/car/have.png"> 添加一个卸货点
							</button>
						</div>
					</div>
					<!-- 卸货点数 -->
					<div class="row number lin_car">
						<div class="col-xs-12 car_mame "><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/map.png"/>&nbsp&nbsp&nbsp卸货点数</div>
						
						<div class="col-lg-8 col-sm-11 input_left">
							<input type="text" id="disabledTextInput" name="fopint" class="form-control zheng_xiehuo_number" placeholder="1" readonly value="1">
						</div>
					</div>
					<!-- 所需车型 -->
					<div class="row lin_car">
						<div class="col-xs-12 car_mame"><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/car.png"/>&nbsp&nbsp&nbsp所需车型</div>
						<div class="col-lg-8 col-sm-11 input_left">
							<div class="row">
								<div class="col-lg-12 color_text">
									<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> （核载<span class="red_span" id="zheng_hezai">0</span>吨，体积<span class="red_span" id="zheng_tiji">0</span>立方米）
								</div>
							</div>
							<div class="col-lg-12 car_border">
								<ul class="items_type" id="ul_cartype"></ul>
								<input type="hidden" name="specId" id="zheng_carname" class="zheng_carname" value=""/>
							</div>
						</div>
					</div>
					<!-- 所需车型 -->
					<div class="row car_details" style="display:none">
						<div class="col-lg-8 col-sm-11 input_left">
							<div class="row">
								<div class="col-lg-12 color_text car_models">
									车型（备选车型越多，叫车速度越快）
								</div>
							</div>
							<div class="row models_btn" id="zheng_car_type_div"></div>
							<input type="hidden" name="carTypeId" id="zheng_cartype" class="zheng_cartype" value=""/>
						</div>
					</div>
					<!-- 其他 -->
					<div class="row car_details" style="display:none">
						<div class="col-lg-8 col-sm-11 input_left">
							<div class="row">
								<div class="col-lg-12 color_text car_models">
									其他（其他条件越多，叫车速度越慢）
								</div>
							</div>
							<div class="row models_btn" id="zheng_other_div"></div>
							<input type="hidden" name="otherId" id="zheng_carother" class="zheng_carother" value=""/>
						</div>
					</div>
					<!-- 货物类型 -->
					<div class="row">
						<div class="col-xs-12 car_mame "><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/type.png"/>&nbsp&nbsp&nbsp货物属性</div>
						<div class="col-lg-8 col-sm-11 input_left ">
							<div class="row">
								<div class="col-xs-12 color_text">
									<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 货物类型
								</div>
							</div>
							<div class="row models_btn" id="zheng_goods_type_div"></div>
							<input type="hidden" name="goodsTypeId" id="zheng_goodstype" class="zheng_goodstype" value=""/>
							<input type="hidden" name="goodsTypeName" id="zheng_goodstypename" class="zheng_goodstypename" value=""/>
						</div>
					</div>
					<!-- 增值服务 -->
					<div class="row">
						<div class="col-xs-12 car_mame"><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/serve.png"/>&nbsp&nbsp&nbsp增值服务</div>
						<div class="col-lg-8 col-sm-11 input_left ">
							<div class="row models_btn noShow" id="zheng_service_div">
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
         					<input type="text" class="form-control zheng_servicePay" id="zheng_servicePay" class="zheng_servicePay" name="purposeAmount" placeholder="金额" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
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
							<input type="hidden" name="mileage"  id="zheng_mileage"/>
						</div>
						<div class="col-md-4 col-sm-12 cost">
							<p>预计运费<span id="zheng_order_price">0</span>元</p>
						</div>
						<div class="col-md-4 col-sm-12">
							<input type="hidden" name="pcWeb"  value="1"/>
							<button type="button" class="btn btn-danger btn-block red_btn btn-submit" id="btn-tem-zheng">提交订单</button>
						</div>
						</div>
					</div>
					</form>
				</div>
				<!-- 零担 -->
				<div class="tab-pane fade" id="lin">
				<form id="form-tem-lin" method="post">
				<input type="hidden" name="type" value="2">
				<input type="hidden" name="orderType" value="2">
					<div class="row">
						<div class="col-xs-12 car_mame "><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/time.png"/>&nbsp&nbsp&nbsp装车时间</div>
						<!-- 下拉时间 -->
						<div class="col-lg-5 col-md-5 col-sm-5 col-xs-6 input_left" >
							<select class="form-control" id="time3" name="zheng_time1">
							  <option value="useDate">--请输入预约用车日期--</option>
							</select>
							<a id="time3_err" data-placement="right" title="请选择预约用车日期"></a>
						</div>
						<div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
							<select id="time4" name="zheng_time2" class="form-control">
							  <option value="useDate">--请输入预约用车时段--</option>
							  <option value="07:00-08:00">07:00 ~ 08:00</option>
							  <option value="08:00-09:00">08:00 ~ 09:00</option>
							  <option value="09:00-10:00">09:00 ~ 10:00</option>
							  <option value="10:00-11:00">10:00 ~ 11:00</option>
							  <option value="11:00-12:00">11:00 ~ 12:00</option>
							  <option value="12:00-13:00">12:00 ~ 13:00</option>
							  <option value="13:00-14:00">13:00 ~ 14:00</option>
							  <option value="14:00-15:00">14:00 ~ 15:00</option>
							  <option value="15:00-16:00">15:00 ~ 16:00</option>
							  <option value="16:00-17:00">16:00 ~ 17:00</option>
							  <option value="17:00-18:00">17:00 ~ 18:00</option>
							  <option value="18:00-19:00">18:00 ~ 19:00</option>
							  <option value="19:00-20:00">19:00 ~ 20:00</option>
							  <option value="20:00-21:00">20:00 ~ 21:00</option>
							  <option value="21:00-22:00">21:00 ~ 22:00</option>
							</select>
							<a id="time4_err" data-placement="right" title="请选择预约用车时段"></a>
						</div>
					</div>
					<!-- 提货 -->
					<div class="row lin_car">
						<div class="col-xs-12 car_mame "><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/ti.png"/>&nbsp&nbsp&nbsp提货地</div>
						<!-- 地址 -->
						<div class="col-lg-8 col-sm-11 input_left">
							<div class="row">
								<div class="col-xs-12 car_Smame color_text">
									<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 提货地址
								</div>
							</div>
							
							<div class="form-group">
								<div class="input-group">
									<input type="text" class="form-control map" id="lin_address_th" name="addressDeliver" placeholder="请点击地图选择地址" readonly>
									<input type="hidden" name="deliverLongitude" id="lin_address_th_lng">
									<input type="hidden" name="deliverLatitude" id="lin_address_th_lat">
									<div class="input-group-addon add">
										<img src="${ctx}/pages/pcWeb/css/images/car/add.png" width="14" class="map_icon"/>
									</div>
									<div class="input-group-addon">
										<img src="${ctx}/pages/pcWeb/css/images/car/very.png" class="address_icon" width="14"/>
									</div>
								</div>
							</div>
							<a id="lin_address_th_err" data-placement="right" title="提货地址不能为空"></a>
						</div>
						<!-- 联系人 -->
						<div class="form-group col-lg-5 col-md-5 col-sm-6 col-xs-6 input_left">
							<div class="row">
							    <div class="col-xs-12 color_text">
									<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 联系人姓名
								</div>
							</div>
						    <input type="text" class="form-control" id="lin_contactName" name="addressDeliverName" placeholder="输入联系人姓名">
						    <a id="lin_contactName_err" data-placement="right" title="联系人姓名不能为空"></a>
						</div>
						<div class="form-group col-lg-3 col-md-3 col-sm-5 col-xs-6">
							<div class="row">
							    <div class="col-xs-12 color_text">
									<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 联系电话
								</div>
							</div>
						    <input type="text" class="form-control telValidate" id="lin_contactPhone" name="addressDeliverPhone" placeholder="输入联系电话">
						    <a id="lin_contactPhone_err" data-placement="right" title="联系人电话不能为空"></a>
						</div>
					</div>
					<!-- 卸点 -->
					<div class="row xie_huo lin_xiehuo_div">
						<div id="lin_div_xie">
							<div class="col-lg-7 col-md-5 col-sm-5 col-xs-5 car_mame ">
								<img class="icons" src="${ctx}/pages/pcWeb/css/images/car/xie.png"/>&nbsp&nbsp&nbsp卸货地
								
							</div>
							<div class="col-lg-5 col-md-5 col-sm-5 col-xs-7 deletebtn">
								<img src="${ctx}/pages/pcWeb/css/images/car/delete.png"/>
							</div>
							<div class="col-lg-8 col-sm-11 input_left">
								<div class="row">
									<div class="col-xs-12 color_text">
										<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 卸货地址
									</div>
								</div>
								
								<div class="form-group">
									<div class="input-group">
										<input type="text" class="form-control map" id="lin_address_xh" name="addressReceipt" placeholder="请点击地图选择地址" readonly>
										<input type="hidden" name="longitude" id="lin_address_xh_lng">
										<input type="hidden" name="latitude" id="lin_address_xh_lat">
										<div class="input-group-addon add">
											<img src="${ctx}/pages/pcWeb/css/images/car/add.png" class="map_icon" width="14"/>
										</div>
										<div class="input-group-addon">
											<img src="${ctx}/pages/pcWeb/css/images/car/very.png" class="address_icon" width="14"/>
										</div>
									</div>
								</div>
								<a id="lin_address_xh_err" data-placement="right" title="卸货地址不能为空"></a>
							</div>
						
							<!-- 联系人 -->
							<div class="form-group col-lg-5 col-md-5 col-sm-6 col-xs-6 input_left">
							<div class="row">
								<div class="col-xs-12 color_text">
									<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 联系人姓名
								</div>
							</div>
							<input type="text" class="form-control" id="lin_xh_contactName" name="addressReceiptName" placeholder="输入联系人姓名">
							<a id="lin_xh_contactName_err" data-placement="right" title="联系人姓名不能为空"></a>
							</div>
							<div class="form-group col-lg-3 col-md-3 col-sm-5 col-xs-6 ">
							<div class="row">
								<div class="col-lg-12 color_text">
									<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 联系电话
								</div>
							</div>
							<input type="text" class="form-control telValidate" id="lin_xh_contactPhone" name="addressReceiptPhone" placeholder="输入联系电话">
							<a id="lin_xh_contactPhone_err" data-placement="right" title="联系人电话不能为空"></a>
							</div>
						</div>
					</div>
					<!-- 按钮 -->
					<div class="row add_btn">
						<div class="col-lg-8 col-md-11 col-sm-11 col-xs-12 input_left">
							<button type="button" class="btn btn-default btn-block btn_colorXie" id="add_unload2" onclick="lin_xie_btn()">
								<img src="${ctx}/pages/pcWeb/css/images/car/have.png"> 添加一个卸货点
							</button>
						</div>
					</div>
					<!-- 默认点数 -->
					<div class="row number lin_car">
						<div class="col-xs-12 car_mame "><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/map.png"/>&nbsp&nbsp&nbsp卸货点数</div>
						
						<div class="col-lg-8 col-sm-11 input_left">
							<input type="text" id="disabledTextInput" name="fopint"  class="form-control xie_Input2 lin_xiehuo_number" placeholder="1" readonly>
						</div>
					</div>
					<!-- 货物类型 -->
					<div class="row">
						<div class="col-xs-12 car_mame "><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/type.png"/>&nbsp&nbsp&nbsp货物属性</div>
						<div class="col-lg-8 col-sm-11 input_left ">
							<div class="row">
								<div class="col-xs-12 color_text">
									<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 货物类型
								</div>
							</div>
							<div class="row models_btn" id="lin_goods_type_div">						
							</div>
							<input type="hidden" name="goodsTypeId" id="lin_goodstype" class="lin_goodstype" value="">
							<input type="hidden" name="goodsTypeName" id="lin_goodstypename" class="lin_goodstypename" value="">
						</div>
					</div>
					
					
					<!-- 重量-->
					<div class="row length_number" > 
						<div class="col-lg-8 col-sm-11 input_left form-group has-feedback"> 
							<div class="row">
								<div class="col-xs-12 color_text"> 
									<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/>货物重量 
								</div>
							</div> 
						  	<input type="text" class="form-control" id="goodweight" name="weight" placeholder="请输入重量" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
 						  	<a id="goodweight_err" data-placement="right" title="货物重量不能为空"></a> 
 						  	<span class="form-control-feedback">吨</span> 
 						</div> 					
 						</div>	
					
					<!-- 货物长度 -->
					<div class="row">
						<div class="col-lg-8 col-sm-11 input_left form-group has-feedback">
							<div class="row">
								<div class="col-xs-12 color_text">
									<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 货物长度
								</div>
							</div>
						  	<input type="text" class="form-control" id="goodsLength2" name="length" placeholder="请输入长度" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
<!-- 						  	<a id="goodsLength2_err" data-placement="right" title="货物长度不能为空"></a> -->
						  	<span class="form-control-feedback">厘米</span>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-8 col-sm-11 input_left form-group has-feedback">
							<div class="row">
								<div class="col-xs-12 color_text">
									<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 货物体积
								</div>
							</div>
						  	<input type="text" class="form-control" id="goodsVolume2" name="volume" placeholder="请输入体积" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
						  	<a id="goodsVolume2_err" data-placement="right" title="货物体积不能为空"></a>
						  	<span class="form-control-feedback">立方米</span>
						</div>
					</div> 
					<!-- 增值服务 -->
					<div class="row">
						<div class="col-xs-12 car_mame"><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/serve.png"/>&nbsp&nbsp&nbsp增值服务</div>
						<div class="col-lg-8 col-sm-11 input_left ">
							<div class="row models_btn noShow" id="lin_service_div">
								<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6 ">
									<button type="button" class="btn btn-default btn-block" data-service="装卸">装卸</button>
								</div>
								<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6 ">
									<button type="button" class="btn btn-default btn-block" data-service="电子回单">电子回单</button>
									<input type="hidden" name="freceiptSave" id="lin_freceiptSave"/>
								</div>
								<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6 ">
									<button type="button" class="btn btn-default btn-block" data-service="回单原件返回">回单原件返回</button>
								</div>
								<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6 bv">
									<button type="button" class="btn btn-default btn-block" data-service="上楼">上楼</button>
								</div>
								<input type="hidden" name="fincrementServe" id="lin_service" class="lin_service" value=""/>
								<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6 input-group collection">
        					<span class="input-group-addon">代收货款</span>
         					<input type="text" class="form-control lin_servicePay" id="lin_servicePay" class="lin_servicePay" name="purposeAmount" placeholder="金额" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
         					<a id="lin_servicePay_err" data-placement="right" title="代收货款只能一到七位整数"></a>
         					<a id="lin_servicePay_err2" data-placement="right" title="不能是空格"></a>
								</div>
							</div>
						</div>
					</div>
					<!-- 注意事项 -->
					<div class="row">
						<div class="col-xs-12 car_mame "><img class="icons" src="${ctx}/pages/pcWeb/css/images/car/note.png"/>&nbsp&nbsp&nbsp注意事项</div>
						<div class="col-lg-8 col-sm-11 input_left">
							<textarea class="form-control" rows="3"></textarea>
						</div>
					</div>
					<!-- 按钮 -->
					<div class="row">
						<div class="col-lg-8 col-sm-11 input_left submit_form">
						<div class="col-md-4 col-sm-12 mileage">
							<p>总里程约：<font id="lin_order_distance">0</font>公里</p>
						</div>
						<div class="col-md-4 col-sm-12 cost">
							<p>预计运费<span id="lin_order_price">0</span>元</p>
							<input type="hidden" name="mileage" id="lin_mileage" value="">
						</div>
						<div class="col-md-4 col-sm-12">
							<input type="hidden" name="pcWeb"  value="1"/>
							<button type="button" class="btn btn-danger btn-block red_btn btn-submit2" id="btn-tem-lin">提交订单</button>
						</div>
						</div>
					</div>
					</form>
				</div>
			</div>
		</div>
	
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
$(function(){
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
		submitOrder_zheng();//执行整车的价格计算
		submitOrder_lin();//执行零担的价格计算
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
	submitOrder_zheng();//执行整车的价格计算
	submitOrder_lin();//执行零担的价格计算
	$(".address_close").click();
})
	
// 获取电脑时间（整车，临担时间）
	var myDate = new Date();
	var year=myDate.getFullYear();//获取当前的年份
	var month=myDate.getMonth()+1;//获取当前月份
	var day=myDate.getDate();//获取当前天数
	var hours=myDate.getHours();//获取当前的小时
	
	$("#time2").find("option").each(function(i,e){//整车
		if(i!=0){
			var order_hours=$(this).val();
			order_hours=order_hours.split("-")[1].split(":")[0];//获取页面选择框里的小时
			if(order_hours<=eval(hours+2)){
				$(this).attr("disabled",true).hide();
			}
		}
	})
	$(document).on("change","#time",function(){//整车	
		$("#time option").each(function(i,e){
		if($("#time option").eq(2).prop("selected")||$("#time option").eq(3).prop("selected")){
			$("#time2").find("option").each(function(i,e){									
				$(this).attr("disabled",false).show();									
			})
			}
		else{
			$("#time2").find("option").each(function(i,e){//整车
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
	
	$("#time4").find("option").each(function(i,e){//零担
		if(i!=0){
			var order_hours=$(this).val();
			order_hours=order_hours.split("-")[1].split(":")[0];//获取页面选择框里的小时
			if(order_hours<=eval(hours+2)){
				$(this).attr("disabled",true).hide();
			}
		}
	})
	$(document).on("change","#time3",function(){//零担	
		$("#time3 option").each(function(i,e){
		if($("#time3 option").eq(2).prop("selected")||$("#time3 option").eq(3).prop("selected")){
			$("#time4").find("option").each(function(i,e){									
				$(this).attr("disabled",false).show();									
			})
			}
		else{
			$("#time4").find("option").each(function(i,e){//零担
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
	$("#time,#time3").append("<option value='"+day1+"'>今天　"+day1+"</option>");
	$("#time,#time3").append("<option value='"+day2+"'>明天　"+day2+"</option>");
	$("#time,#time3").append("<option value='"+day3+"'>后天　"+day3+"</option>");
	
	$("#myTab li a").click(function(){
		$(this).attr("style","background-color: #eeeeee!important;border:none!important;border-bottom: 2px solid #E64249!important;color:#E64249!important;border-top:1px solid transparent!important;").parent().siblings().find("a").attr("style","background-color: #eeeeee!important;border-bottom: 1px solid #DDDDDD!important;");
	})

	/*整车车型图片加载判断*/
	$.ajax({
 		type:"POST",
 		url:"${ctx}/pcWeb/order/loadSpec.do",
 		success:function(response){ 
 			var list=JSON.parse(response);
 			list=list.data;
 			var html="",carImg="";
 			$("#ul_cartype").empty();
 			$.each(list,function(i,e){
 				switch(e.optionId){ 
 				case 1: carImg="Mianbao_car_1.png"; break; 
 				case 2: carImg="2.5m_car_1.png"; break;
 				case 3: carImg="4.2m_car_1.png"; break;
 				case 4: carImg="6.2m_car_1.png"; break;
 				case 5: carImg="7.2m_car_1.png"; break;/*数据库暂无此类型车*/
 				case 6: carImg="9.6m_car_1.png"; break;
 				default : carImg=""; break; 
 				} 
 				if(carImg!=""||carImg==null)
 				{
 					html+='<li class="col-lg-4 col-md-4 col-sm-4 col-xs-6">'+
					  '<img src="${ctx}/pages/pcWeb/css/images/tem_car/'+carImg+'" data-carname="'+e.optionName+'" data-carhezai="'+e.optionWe+'" data-cartiji="'+e.optionVal+'" data-caroptionId="'+e.optionId+'"/>'+					  
					  '<p>'+e.optionName+'</p>'+
					  '</li>';	
 				} 					
 			})
 			$("#ul_cartype").append(html);
 			getHtmlLoadingAfterHeight();//iframe高度动态变化
 		}
 	})
 	
 	
 	// 整车所需车型点击图片变换效果,点击车子图片执行运行算法
	$(document).on("click",".items_type li img",function(){		
		var _url = $(this).attr("src");
		var caroptionid=$(this).data("caroptionid");
		var car_hezai=$(this).data("carhezai");
		var car_tiji=$(this).data("cartiji");
		$("#zheng_carname").val(caroptionid);//车名的隐藏域赋值
		$("#zheng_hezai").text(car_hezai);//核载
		$("#zheng_tiji").text(car_tiji);//体积
		$(this).attr("src",_url.replace("1.png","2.png"));
		var _red = $(this).parent().siblings().find("img");
		// 车型细节展示
		$(".car_details").show();		
		$(_red).each(function(i,e){
			_red.eq(i).attr("src",_red.eq(i).attr("src").replace("2.png","1.png"));
		})
		submitOrder_zheng();//执行价格计算
		getHtmlLoadingAfterHeight();//iframe高度动态变化
	})
 	/*点击整车不同车图片，车型变化，其他变化*/
 	$(document).on("click","#ul_cartype li img",function(){
 		/*车型变化*/
 		var optionTemp=$(this).data("caroptionid");
 		$.ajax({
 			type:"post",
 			url:"${ctx}/pcWeb/order/loadCarType.do?optionId="+optionTemp,
 			success:function(response){
 				var list=JSON.parse(response);
 				list=list.data;
 				var html="";
 				$("#zheng_car_type_div").empty();
 				$.each(list,function(i,e){
						html+='<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6 ">'+
						      '<button type="button" class="btn btn-default btn-block" data-caroptionid="'+optionTemp+'" data-cartype="'+e.optionId+'">'+e.optionName+'</button>'+
							  '</div>';
 				});
 				$("#zheng_car_type_div").append(html);
 				getHtmlLoadingAfterHeight();//iframe高度动态变化
 			}
 		});
 		/*整车其他条件变化*/
 		var optionId=$(this).data("caroptionid"); 
 		$.ajax({
				type:"post",
				url:"${ctx}/pcWeb/order/CarOther.do?optionId="+optionId, 					
				success:function(response){
					var list=JSON.parse(response);
					list=list.data;
					var html="";
					$("#zheng_other_div").empty();
					$.each(list,function(i,e){
						html+='<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6 ">'+
						  '<button type="button" class="btn btn-default btn-block" data-other="'+e.optionId+'">'+e.optionName+'</button>'+
						  '</div>';
					})
					$("#zheng_other_div").append(html);
					getHtmlLoadingAfterHeight();//iframe高度动态变化
				}
			}); 		
 	})
 	
 	
 	/*整车货物类型加载*/
 		$.ajax({
 			type:"post",
 			url:"${ctx}/pcWeb/order/loadGoods.do",
 			success:function(response){
 				var list=JSON.parse(response);
 				list=list.data;
//  				console.log(list);
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
	
 	/*零担货物类型加载*/
		$.ajax({
			type:"post",
			url:"${ctx}/pcWeb/order/loadGoods.do",
			success:function(response){
				var list=JSON.parse(response);
				list=list.data;
//				console.log(list);
				var html="";
				$("#lin_goods_type_div").empty();
				$.each(list,function(i){
					html+='<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6 ">'+
					'<button type="button" class="btn btn-default btn-block" data-protype="'+list[i].optionId+'">'+list[i].optionName+'</button>'+
					'</div>';
				});
				$("#lin_goods_type_div").append(html);
			}
		});
 	
 	
 	
 	
 	/*页面上所有条件选择的边框效果*/
 	$(document).on("click",".models_btn button",function(){
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
		/* console.log(zeng_car_service_val); */
		$("#zheng_service").val(zeng_car_service_val);
		submitOrder_zheng();
	})
	//点击林单零担服务算法
	$(document).on("click","#lin_service_div button",function(){
	 	var lin_car_service_val="";
		$("#lin_service_div button").each(function(i,e){
			var r=$(e).attr("class").indexOf("lin_color_btn");
			if(r!=-1){
				lin_car_service_val+=$(e).data("service")+",";
			}
		})
		lin_car_service_val=lin_car_service_val.substring(0,lin_car_service_val.length-1);
		/* console.log(lin_car_service_val); */
		$("#lin_service").val(lin_car_service_val);
		submitOrder_lin();
	})
 	/*整车车型选择的判断效果*/
 	var car_type_i=0,f=0;
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
 			var judge=$("#zheng_car_type_div button:gt(0)").eq(i).attr("class").indexOf("lin_color_btn"); 			
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
 	
 	
 	/*临时用车整车订单提交验证*/
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
		
		/*提交整车订单*/
		if(telOk){			
			var data=$("#form-tem-zheng").serialize();
			$.ajax({
				type:"post",
				url:"${ctx}/pcWeb/order/saveOrder.do",
				data:data,
				dataType:"json",
				success:function(response){
	// 				console.log(response);
					if(response.success=="false"){
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
	
	/*临时用车零担订单提交验证*/
	$("#btn-tem-lin").click(function(){		
		var val = ["time3","time4","lin_address_th","lin_contactName","lin_contactPhone","lin_address_xh","lin_xh_contactName","lin_xh_contactPhone","lin_xh_contactPhone","goodweight","goodsVolume2"];
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
		if($('#lin_servicePay').val()){
			if(/^\s+$/.test($('#lin_servicePay').val())){
				$('#lin_servicePay').focus();
				$('#lin_servicePay_err2').tooltip("show");
				setTimeout(function(){
					$("#lin_servicePay_err2").tooltip("hide");
				},1000);
				return false;
			}
		}
		//代收货款控制一到七位整数
		if($('#lin_servicePay').val()){
			if(!/^\d{1,7}$/.test($('#lin_servicePay').val().trim())){
				$('#lin_servicePay').focus();
				$('#lin_servicePay_err').tooltip("show");
				setTimeout(function(){
					$("#lin_servicePay_err").tooltip("hide");
				},1000);
				return false;
			}
		}
		/*货物类型(多选)*/
		var car_goodstypeid_val="";
		var car_goodstypename_val="";
		$("#lin_goods_type_div button").each(function(i,e){
			var r=$(this).attr("class").indexOf("lin_color_btn");
			if(r!=-1){
				car_goodstypeid_val+=$(this).data("protype")+",";
				car_goodstypename_val+=$(this).text()+",";
			}
		})		
		car_goodstypeid_val=car_goodstypeid_val.substring(0,car_goodstypeid_val.length-1);
		car_goodstypename_val=car_goodstypename_val.substring(0,car_goodstypename_val.length-1);
		$("#lin_goodstype").val(car_goodstypeid_val);
		$("#lin_goodstypename").val(car_goodstypename_val);
		
		/*增值服务(多选)*/
		var car_service_val="";
		$("#lin_service_div button").each(function(i,e){
			var r=$(this).attr("class").indexOf("lin_color_btn");
			if(r!=-1){
				car_service_val+=$(this).data("service")+",";
			}
		})
		car_service_val=car_service_val.substring(0,car_service_val.length-1);
		if(car_service_val.indexOf("电子回单")!=-1)
		{
			$("#lin_freceiptSave").val(1);
		}
		else{
			$("#lin_freceiptSave").val("");
		}
		$("#lin_service").val(car_service_val);
		/*公里数*/		
		var mileage=$("#lin_order_distance").text();
		$("#lin_mileage").val(mileage);	
		
		
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
		
		/*提交零担订单*/
		if(telOk){
			var data=$("#form-tem-lin").serialize();
			$.ajax({
				type:"post",
				url:"${ctx}/pcWeb/order/saveOrder.do",
				data :data,
				dataType:"json",
				success:function(response){
					if(response.success=="false"){
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
 	

	// 下拉选数量单位
	$(".piao").click(function(){
		$("#ul_down").show();
	})
	$("#ul_down li").click(function(){
		var _text = $(this).text();
		$(".piao").text(_text);
		$("#ul_down").hide();
	})
	
	
	// 判断是否点击头部。
	$("#myTab").click(function(){
		
		$("#zheng_order_distance,#lin_order_distance,#zheng_order_price,#lin_order_price").text("0");		
		//初始化添加卸货点
		$("#zheng_div_xie .deletebtn,#lin_div_xie .deletebtn").attr("style","visibility:hidden");
		$("#zheng_div_xie1,#zheng_div_xie2,#zheng_div_xie3,#zheng_div_xie4,#zheng_div_xie5").remove();
		$("#lin_div_xie1,#lin_div_xie2,#lin_div_xie3,#lin_div_xie4,#lin_div_xie5").remove();
		lin_xie_i = 0;
		_i = 0;
		$("#disabledTextInput").attr("placeholder",""+(lin_xie_i+1)+"");
		$(".xie_Input2").attr("placeholder",""+(lin_xie_i+1)+"");

	})	
	
	$("#zheng_tab").click(function(){
		location.href="${ctx}/pages/pcWeb/temporary_car/temporary_car.jsp";
		address_s_lnglat=[];
		address_e1_lnglat=[];
		address_e2_lnglat=[];
		address_e3_lnglat=[];
		address_e4_lnglat=[];
		address_e5_lnglat=[];
		$("#orderType").val("1");
	})
	$("#lin_tab").click(function(){
		address_s_lnglat=[];
		address_e1_lnglat=[];
		address_e2_lnglat=[];
		address_e3_lnglat=[];
		address_e4_lnglat=[];
		address_e5_lnglat=[];
		$("#orderType").val("2");
	})
})

// 零担添加卸货点
var _j = 1;
function lin_xie_btn()
{
	if($('#lin_address_th').val() == ''){
		top.layer.alert('请先填写提货地址！！！')
		$('#lin_address_th').focus();
		return;
	}
	if($('#lin_address_xh').val() == ''){
		top.layer.alert('请先填写卸货点1！！！')
		$('#lin_address_xh').focus();
		return;
	}
	$("#lin_servicePay").attr("disabled",false);//卸货地址不止1个的时候，代收款可编辑
	var html='<div id="lin_div_xie'+_j+'">'+
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
			'<input type="text" class="form-control map lin_address_xh'+_j+'" id="lin_address_xh'+_j+'" name="addressReceipt" placeholder="请点击地图选择地址" readonly>'+
			'<input type="hidden"  name="longitude" id="lin_address_xh_lng'+_j+'">'+
			'<input type="hidden"  name="latitude" id="lin_address_xh_lat'+_j+'">'+
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
			'<input type="text" class="form-control lin_xh_contactName'+(2+_j)+'" id="lin_xh_contactName"'+(2+_j)+' name="addressReceiptName'+'" placeholder="输入联系人姓名">'+
			'</div>'+
			'<div class="form-group col-lg-3 col-md-3 col-sm-5 col-xs-6 ">'+
			'<div class="row">'+
			'<div class="col-lg-12 color_text">'+
			'<img src="${ctx}/pages/pcWeb/css/images/car/yuan.png"/> 联系电话'+
			'</div>'+
			'</div>'+
			'<input type="text" class="form-control lin_xh_contactPhone'+(2+_j)+' telValidate" id="lin_xh_contactPhone'+(2+_j)+'" name="addressReceiptPhone'+'" placeholder="输入联系电话">'+
			'</div>'+
			'</div>';
	if( _j<=4)
	{	
		if(_j>=2){
			if($('#lin_address_xh'+(_j-1)).val() == ''){
				top.layer.alert('请先填写卸货点'+(_j));
				$('#lin_address_xh'+(_j-1)).focus();
				return;
			}
		}
		$(".lin_xiehuo_div").append(html);
		$(".lin_xiehuo_number").attr("placeholder",""+(_j+1)+"").attr("value",""+(_j+1)+"");
		_j++;
		getHtmlLoadingAfterHeight();//iframe高度动态变化
	}
	// 当卸货点有5个时，移除onclick
	if(_j==5)
	{
		$("#add_unload2").removeAttr("onclick");
	}
	
	if(_j>=1)
	{
		$(".lin_xiehuo_div .deletebtn").css("visibility","inherit");
		$("#lin_div_xie .deletebtn").attr("style","visibility:hidden");

		//删除卸货地点
		var lin_xie_index = _j;
		var delArr = document.getElementsByClassName('deletebtn');
		for(var i=0; i<delArr.length; i++){
			;(function(nowI){
				delArr[i].onclick = function(){
					if(nowI<lin_xie_index){
						top.layer.alert('不能从中间删除卸货地！！！');
						return false;
					}
					//删除第2个卸货点
					if(lin_xie_index==2){
						$("#lin_servicePay").attr("disabled",true);//卸货地址只剩1个的时候，代收款不可编辑
						address_e2_lnglat=[];
						$(".map_close").click();//触发地图距离的计算事件
					}
					//删除第3个卸货点
					if(lin_xie_index==3){
						address_e3_lnglat=[];
						$(".map_close").click();//触发地图距离的计算事件
					}
					//删除第4个卸货点
					if(lin_xie_index==4){
						address_e4_lnglat=[];
						$(".map_close").click();//触发地图距离的计算事件
					}
					//删除第5个卸货点
					if(lin_xie_index==5){
						address_e5_lnglat=[];
						$(".map_close").click();//触发地图距离的计算事件
					}
					// 当卸货点小于5个时，绑定onclick
					if(lin_xie_index<6)
					{
						$("#add_unload2").attr("onclick","lin_xie_btn();");
					}
					$(this).parent().remove();	//移除卸货点
					lin_xie_index--;//i--
					$(".lin_xiehuo_number").attr("placeholder",""+lin_xie_index+"").attr("value",""+lin_xie_index+"");//卸货点数修改
					_j = lin_xie_index;//xie_index的值给xie_i;
					getHtmlLoadingAfterHeight();//iframe高度动态变化
					return false;
				}
			})(i)
		}
	}else
	{
		$(".lin_xiehuo_div .deletebtn").css("visibility","hidden");
	}
	//判断个数
	if($(".lin_xiehuo_div .deletebtn").length===1)//判断卸货点个数==1，隐藏按钮
	{
		$(".lin_xiehuo_div .deletebtn").css("visibility","hidden");
		
	}
}

// 整车卸货点添加
var _i = 1;
function zheng_xie_btn()
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
		$("#zheng .deletebtn").css("visibility","inherit");
		$("#zheng_div_xie .deletebtn").attr("style","visibility:hidden");

		//删除卸货地点
		var zheng_xie_index = _i;
		var delArr = document.getElementsByClassName('deletebtn');
		for(var i=0; i<delArr.length; i++){
			;(function(nowI){
				delArr[i].onclick = function(){
					// 当卸货点小于5个时，绑定onclick
					if(zheng_xie_index<6)
					{
						$("#add_unload1").attr("onclick","zheng_xie_btn();");
					}
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


/*整车*/
function submitOrder_zheng(){
			//返回运费	
			  $.ajax({
				   type:"post",
					url:"${ctx}/pcWeb/order/ruleCost.do",
					data:{orderType:$("#orderType").val(),fopint:$(".zheng_xiehuo_number").val(),specId:$("#zheng_carname").val(),mileage:$("#zheng_order_distance").text(),fincrementServe:$("#zheng_service").val()},
					success:function(response){
						response=JSON.parse(response);	
// 						console.log(response);
						$("#zheng_order_price").text(response.data);
					}
				})
}


$(document).on("blur","#goodweight,#goodsVolume2",function(){
	submitOrder_lin();
})
/*零担*/
function submitOrder_lin(){
		//返回运费	
		  $.ajax({
			   type:"post",
				url:"${ctx}/pcWeb/order/ruleCost.do",
				data:{orderType:$("#orderType").val(),fopint:$(".lin_xiehuo_number").val(),mileage:$("#lin_order_distance").val(),volume:$("#goodsVolume2").val(),weight:$("#goodweight").val(),fincrementServe:$("#lin_service").val()},
				success:function(response){
					response=JSON.parse(response);
//					console.log(response);
					$("#lin_order_price").text(response.data);
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