<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
<title>司机加盟--一路好运</title>
<link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet"/>
<link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet" />
<link href="${ctx}/pages/pcWeb/css/join_driver.css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/public.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/join_driver.js"></script>
</head>
<body>
	<%@ include file="/pages/pcWeb/top/top.jsp"%>	
	
	<div class="container main">
		<!--车辆照片-->
		<div class="row">
			<div class="col-xs-12 text-center">
				<img src="${ctx}/pages/pcWeb/css/images/join_us/top_car.png" class="img-responsive" />
			</div>
		</div>
		
		
		<div class="row line">
			<div class="col-xs-12 text-center">
				<h3 class="driver_title">一路好运司机加盟申请资料填写</h3>
			</div>
		</div>
		
		<form class="form-horizontal" id="apply_form">
		<!--司机性质-->
		<div class="row line">
			<div class="col-xs-10 col-xs-offset-1 col-sm-6 col-sm-offset-3 col-lg-8 col-lg-offset-2">
			<div class="form-group">
				<label class="col-sm-4 control-label" style="font-weight: normal;">司机性质：</label>
				
				<div class="col-sm-8 apply_name">
				<font class="driver_font">
					<span class="radio_check radio_checked">
						<input class="radio-inline radio_default" type="radio" name="ftype" id="driverType" checked="checked" value="1" />
					</span>
					个人
				</font>
				
				<font>
					<span class="radio_check">
						<input class="radio-inline radio_default" type="radio" name="ftype" id="driverType" value="2" />
					</span>
					公司
				</font>
				</div>
			</div>
			</div>
		</div>
		
		<!--申请人姓名：-->
		<div class="row line">
			<div class="col-xs-10 col-xs-offset-1 col-sm-6 col-sm-offset-3 col-lg-8 col-lg-offset-2">
				
			<div class="form-group">
				<label class="col-sm-4 control-label" style="font-weight: normal;">申请人姓名：</label>
				<div class="col-sm-8">
				<input type="text" class="form-control" name="fname" id="driver_name" />
				<a id="name_err"  data-placement="right" title="姓名不能为空"></a>
				</div>
			</div>	
			</div>
		</div>
		
		<!--联系电话：-->
		<div class="row line">
			<div class="col-xs-10 col-xs-offset-1 col-sm-6 col-sm-offset-3 col-lg-8 col-lg-offset-2">
				
			<div class="form-group">
				<label class="col-sm-4 control-label" style="font-weight: normal;">联系电话：</label>
				<div class="col-sm-8">
				<input type="text" class="form-control" name="ftel" id="driver_phone" />
				<a id="phone_err"  data-placement="right" title="联系电话不能为空"></a>
				</div>
			</div>	
			</div>
		</div>
		
		
		<!--固定电话：-->
		<div class="row line">
			<div class="col-xs-10 col-xs-offset-1 col-sm-6 col-sm-offset-3 col-lg-8 col-lg-offset-2">
				
			<div class="form-group">
				<label class="col-sm-4 control-label" style="font-weight: normal;">固定电话：</label>
				<div class="col-sm-8">
				<input type="text" class="form-control" name="fphone" id="driver_tel"/>					
				</div>
			</div>	
			</div>
		</div>
		
		<!--联系地址：-->
		<div class="row line">
			<div class="col-xs-10 col-xs-offset-1 col-sm-6 col-sm-offset-3 col-lg-8 col-lg-offset-2">
				
			<div class="form-group">
				<label class="col-sm-4 control-label" style="font-weight: normal;">联系地址：</label>
				<div class="col-sm-8">
				<input type="text" class="form-control" name="faddress" id="driver_address" />
				<a id="address_err"  data-placement="right" title="地址不能为空"></a>
				</div>
			</div>	
			</div>
		</div>
		
		<!--同意一路好运：-->
		<div class="row line">
			<div class="col-xs-10 col-xs-offset-1 col-sm-6 col-sm-offset-3 col-lg-8 col-lg-offset-2">
				
			<div class="form-group" style="font-size: 13px;">
				<div class="col-sm-8 col-sm-offset-4">
				<span class="radio_check radio_checked">
						<input class="radio-inline radio_default" type="radio" name="agreement" checked="checked" value="1" />
				</span>同意<a href="#" class="redClolr">《一路好运服务协议》</a>		
				</div>
			</div>	
			</div>
		</div>
		
		<!--确认-->
		<div class="row line">
			<div class="col-xs-10 col-xs-offset-1 col-sm-6 col-sm-offset-3 col-lg-8 col-lg-offset-2">
				
			<div class="form-group">
				<div class="col-sm-8 col-sm-offset-4">
				<a href="javascript:void(0)" class="btn btn-danger btn-submit" onclick="formValidate()">提交申请 </a>	
				</div>
			</div>	
			</div>
		</div>
	</form>
</div>
<%@ include file="/pages/pcWeb/foot/foot.jsp"%>	

<script>
//表单验证 
function formValidate(){
	var r1=checkForm("driver_name","name_err");
	var r2=checkForm("driver_phone","phone_err");
	var r3=checkForm("driver_address","address_err");
	
	var driverName=$("#driver_name").val(),
		driver_phone=$("#driver_phone").val(),
		driver_tel=$("#driver_tel").val(),
		driver_address=$("#driver_address").val();
	if(driverName.length>15){
		layer.msg("姓名最多15个字！");
		return false;
	}
	if(driver_tel!=""&&checkPhone(driver_tel)==false){
		layer.msg("固定电话格式不对！");
		return false;
	}
	if(driver_address.length>50){
		layer.msg("地址过长！");
		return false;
	}
	if(r1&&r2&&r3){	//验证都通过以后
		var tel=$("#driver_phone").val();
		var re = /^1[3|4|5|7|8][0-9]\d{4,8}$/;//手机号码正则表达式
		if(!(re.test(tel))){
			layer.msg("手机格式错误！");
			return false;
		}
		var result=$("#apply_form").serialize();
		$.ajax({
			type:"POST",
			url:"${ctx}/pcWeb/participate/saveParticipate.do",
			data:result,			
			dataType:'json',
			success:function(response){
				if(response.success=="failure"){
					layer.msg("提交失败！");
					$('#driver_name').val("");
					$('#driver_phone').val("");
					$('#driver_tel').val("");
					$('#driver_address').val("");
	    			//ispass = false;
				}else{
					layer.msg("信息提交成功！");
					$('#driver_name').val("");
					$('#driver_phone').val("");
					$('#driver_tel').val("");
					$('#driver_address').val("");
				}				
			}
		});
		
	}
}
</script>
</body>
</html>

