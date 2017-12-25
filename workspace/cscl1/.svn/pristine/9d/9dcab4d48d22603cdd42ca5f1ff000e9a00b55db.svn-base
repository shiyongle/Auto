<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看优惠券活动</title>
<style>
	h3{
		margin:0px;
		padding:0px;
	}
</style>
<script>
        $(function(){
            // 表单控制
            if("${couponsActivity.fuseTime}" == 0){
            	$('#seeUseEnabledDateText').val('');
            }
            if("${couponsActivity.fdollars}" == 0){
            	$('#seeOrderMoneyText').val('');
            }
            if("${couponsActivity.fgetType}" == 1){
            	$('#seeTomorrowCheck').prop('checked',true);
            }
            
            if("${couponsActivity.fgetType}" == 2){
            	$('#seeTodayCheck').prop('checked',true);
            }
            
            $('#seeOrderMoneyText').val("${couponsActivity.fdollars}");
            
            if("${couponsActivity.fdollars}"){
            	$('#seeOrderMoneyRadio').prop('checked',true);
            }
       		//factivityType 来判断
       		if("${couponsActivity.factivityType}" == 0){
       			$('#differentTypes').html('<td style="font-size:14px; ">发放人群:</td>\
                        <td><input type="text" disabled value="${couponsActivity.fexcelName}" style="width:140px;" class="easyui-validatebox"></td>\
                        <td></td>');
       		}else{
       			$('#differentTypes').html('<td style="font-size:14px; ">充值金额:</td>\
                        <td><input type="text" disabled value="${couponsActivity.ftopUpDollars}" style="width:140px;" class="easyui-validatebox"></td>\
                        <td>元</td>');
       		}
            if("${couponsActivity.fcarSpecId}"){
            	var carSpecIdArray = "${couponsActivity.fcarSpecId}".split(',');
                for(var i=0; i<carSpecIdArray.length; i++){
                	if(carSpecIdArray[i] == 3){
                		$('#seeCarTypeBox').append('<input type="checkbox" disabled checked>4.2米');
                	}else if(carSpecIdArray[i] == 5){
                		$('#seeCarTypeBox').append('<input type="checkbox" disabled checked>6.8米');
                	}else if(carSpecIdArray[i] == 1){
                		$('#seeCarTypeBox').append('<input type="checkbox" disabled checked>面包车');
                	}else if(carSpecIdArray[i] == 2){
                		$('#seeCarTypeBox').append('<input type="checkbox" disabled checked>2.5米');
                	}else if(carSpecIdArray[i] == 4){
                		$('#seeCarTypeBox').append('<input type="checkbox" disabled checked>5.2米');
                	}else if(carSpecIdArray[i] == 6){
                		$('#seeCarTypeBox').append('<input type="checkbox" disabled checked>9.6米');
                	}
                }
            }else{
            	$('#seeCarTypeBox').text('没有选择任何车型！');
            }
            
            if("${couponsActivity.farea}"){
            	var areaArray = "${couponsActivity.farea}".split(',');
                for(var i=0; i<areaArray.length; i++){
                	if(i==6){
                		$('#seeAreabox').append('<br>');
                	}
                	$('#seeAreabox').append('<input type="checkbox" disabled checked>'+areaArray[i]);
                }
            }else{
            	$('#seeAreabox').text('没有选择任何区域！');
            }     
        })
    </script>
</head>
    <div style="width:565px; overflow-y:scroll; height:360px; padding:10px;" >
        <form id="luckActivityForm">
            <fieldset>
                <legend><span style="font-size:16px; ">优惠券活动</span><span style="color:red;">(以下允许选择多个方法)</span></legend>
                <h3>前置条件</h3>
                <div style="border:1px solid #6c6c6c; padding:10px 10px; margin-bottom:10px;">
	                <table>
	                	<tr id="differentTypes">
	                        <td style="font-size:14px; ">发放人群:</td>
	                        <td><input type="text" disabled value="${couponsActivity.fexcelName}" style="width:140px;" class="easyui-validatebox"></td>
	                        <td></td>
	                    </tr>
	                    <tr>
	                        <td style="font-size:14px; ">方案选择:</td>
	                        <td>
	                        	 <input type="text" disabled  value="${couponsActivity.fcouponsName}" style="width:140px;" class="easyui-validatebox">
	                        </td>
	                        <td></td>
	                    </tr>
	                </table>
                </div>
                <h3>活动选择</h3>
                <div style="border:1px solid #6c6c6c; padding:10px 10px; margin-bottom:10px;">
	                <table>
	                    <tr>
	                        <td style="font-size:14px; ">业务类型:</td>
	                        <td>
	                            <input type="text" disabled  value="${couponsActivity.fbusinessType}" style="width:140px;"class="easyui-validatebox">
	                        </td>
	                        <td></td>
	                    </tr>
	                    <tr>
	                        <td style="font-size:14px; ">活动日期:</td>
	                        <td><input type="text" class="easyui-validatebox" value="${couponsActivity.factivityStartTimeString}" name="factivityStartTime" disabled style="width:140px;"></td>
							<td>至<input type="text" class="easyui-validatebox" disabled value="${couponsActivity.factivityEndTimeString}" style="width:140px;"></td>
	                    </tr>
	                </table>
	            </div>
            </fieldset>
            <fieldset>
                <legend><span style="font-size:16px; ">使用条件</span><span style="color:red;">(以下允许选择多个方案)</span></legend>
                <table style="padding:10px;">
                    <tr>
                        <td style="font-size:14px; ">使用时间</td>
                        <td style="font-size:14px;">生效时间:</td>
                        <td>
                            <input type="checkbox" class="validCheck" id="seeTomorrowCheck" value="1"  name="fgetType" disabled>领取后次日生效
                            <input type="checkbox" class="validCheck" id="seeTodayCheck" value="2" name="fgetType" disabled>领取后即时生效&nbsp;&nbsp;
                            有效期<input type="text" style="width:50px;" disabled id="seeUseEnabledDateText" name="fuseTime" value="${couponsActivity.fuseTime}" >天
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="font-size:14px;">生效时间:</td>
                        <td>
                            <span style="font-family:'Times New Roman';">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>使用有效期
                            <input type="text" class="easyui-validatebox"  style="width:100px;" disabled value="${couponsActivity.fuseStartTimeString}">
                            至<input type="text"  class="easyui-validatebox"  disabled value="${couponsActivity.fuseEndTimeString}" style="width:100px;" name="fuseEndTime" >
                        </td>
                    </tr>
                    <tr>
                        <td style="font-size:14px; ">订单条件</td>
                        <td style="font-size:14px;">订单金额:</td>
                        <td>
                            <input type="checkbox" id="seeOrderMoneyRadio" disabled>订单金额满
                            <input type="text" id="seeOrderMoneyText" name="fdollars" disabled class="easyui-validatebox"> 元
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="font-size:14px;">车<span style="font-family:'Times New Roman';">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>型:</td>
                        <td id="seeCarTypeBox">
                            
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="font-size:14px;">区<span style="font-family:'Times New Roman';">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>域:</td>
                        <td id="seeAreabox">
                        </td>
                    </tr>
                </table>
            </fieldset>
        </form>
    </div>
</html>