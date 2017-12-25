<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看优惠券</title>
<script>
    //新增优惠券
        $(function(){
        	if("${coupons.ftype}" == 1){
        		$('.addservice').hide();
        	}else{
        		$('.orderCoupon').hide();
            	$('#serviceRadioBox').text("${coupons.faddserviceName}");
        	}
        	if("${coupons.fdiscount}" == ''){
        		$('#discount').hide();
        	}
        	if("${coupons.fsubtract}" == ''){
        		$('#lapse').hide();
        	}
        })
    </script>
</head>
    <div style="width:440px;">
        <form action="" id="luckForm">
            <table>
                <tr>
                    <td class="m-info-title">业务类型   </td>
                    <td colspan="2">
                    	<select id="businessMoldSel" name="fbusinessType" class="easyui-combobox" data-options="width:120">
                    		<option value="一路好运">${coupons.fbusinessType}</option>
                    	</select>
                    </td>
                </tr>
                <tr>
                    <td class="m-info-title">优惠券名称</td>
                    <td colspan="2"><input type="text" value="${coupons.fcouponsName}" id="luckName"  class="easyuicombobox" style="width:200px;" name="fcouponsName" disabled></td>
                </tr>
                <tr class="orderCoupon">
                    <td class="m-info-title" class="m-info-title">优惠券  </td>
                    <td colspan="2" >订单优惠券</td>
                </tr>
                <tr class="orderCoupon">
                    <td></td>
                    <td></td>
                    <td><div id="discount">折扣<input type="text" class="easyui-numberbox" value="${coupons.fdiscount}" disabled style="width:100px;" name="fdiscount">%</div><div id="lapse" >直减<input type="text" style="width:100px;" name="fsubtract" value="${coupons.fsubtract}" class="easyui-numberbox" disabled>元</div></td>
                </tr>
                <tr class="addservice">
                    <td class="m-info-title">优惠券</td>
                    <td colspan="2">增值服务券</td>
                </tr>
                <tr class="addservice">
                    <td></td>
                    <td></td>
                    <td id="serviceRadioBox"></td>
                </tr>
            </table>
        </form>
    </div>
</html>