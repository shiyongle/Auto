<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增优惠券</title>
<script>
    //新增优惠券
        $(function(){
            //业务类型
            $('#businessMoldSel').combobox({
                width:200,
                required : true,
                missingMessage : "请选择业务类型",
                validType : "comboRequired",
                invalidMessage : "请选择业务类型",
                width:145,
                editable : false
            });

            $('#luckName').validatebox({
                required:true,
                missingMessage:'优惠券名称必须填写！',
               	validType:['maxLength[20]']
            });

            $('#discount').numberbox({
                required:true,
                missingMessage:'折扣必须填写！',
                validType:['greaterZero','lessOneHundred'],
                precision:0
            });

            $('#lapse').numberbox({
                required:true,
                missingMessage:'直减必须填写！',
                validType:['greaterZero','lessOneMillon'],
               	precision:2
            }).prop('disabled',true).numberbox('disableValidation');

            $('#orderCoupon').click(function(){
            	$('#lapse').prop('disabled',true).numberbox('disableValidation').numberbox('clear');
            	$('#discountRadio').prop('checked',true);
            	$('#discount').prop('disabled',false).numberbox('enableValidation');
                $('#discountRadio').removeAttr('disabled');
                $('#lapseRadio').removeAttr('disabled').prop('checked',false);
                $('#serviceRadioBox input[type="radio"]').removeAttr('checked',false);
                $('#serviceRadioBox input[type="radio"]').attr('disabled',true);
            });

            $('#lapseRadio').click(function(){
            	$(this ).siblings().removeAttr("checked");
            	$('#discount').prop('disabled',true).numberbox('disableValidation').numberbox('clear');
            	$('#lapse').prop('disabled',false).numberbox('enableValidation');
            	
            });

            $('#discountRadio').click(function(){
            	$(this ).siblings().removeAttr("checked");
            	$('#lapse').prop('disabled',true).numberbox('disableValidation').numberbox('clear');
            	$('#discount').prop('disabled',false).numberbox('enableValidation');
            });
            
            $('#serviceRadio').click(function(){
            	$('#serviceRadioBox input[type="radio"]').eq(0).prop('checked',true);
                $('#serviceRadioBox input[type="radio"]').removeAttr('disabled',false);
                $('#discountRadio').attr('disabled',true);
                $('#lapseRadio').attr('disabled',true);
                $('#discountRadio').removeAttr('checked',false);
                $('#lapseRadio').removeAttr('checked',false);
                $('#discount').prop('disabled',true).numberbox('disableValidation').numberbox('clear');
                $('#lapse').prop('disabled',true).numberbox('disableValidation').numberbox('clear');
            });
            
            //确定按钮
            $('#sumbit').click(function(){
               // var params = decodeURIComponent($("#luckForm").serialize(), true);
               	if(/^\s+$/.test($('#luckName').val())){
               		$.messager.alert('提示', "你就输入了几个空格乖好好输入!","info",function(){
               			$('#luckName').focus();
               		});
               		return;
               	}
                if($('#luckForm').form('validate')){
                	$.messager.confirm('提示', '确定要提交吗？', function(r){
                		if(r){
		                    $.ajax({
		                        type:'POST',
		                        url:'${ctx}/coupons/save.do',
		                        data:{
		                        	fbusinessType:$('#businessMoldSel').combobox('getValue'),
		                        	fcouponsName:$('#luckName').val(),
		                        	ftype:$('input[name="ftype"]:checked').val(),
		                        	fdiscount:$('input[name="fdiscount"]').val(),
		                        	fsubtract:$('input[name="fsubtract"]').val(),
		                        	faddserviceName:$('input[name="faddserviceName"]:checked').val()
		                        },
		                        dataType:'json',
		                        success:function(res){
		                            if(res.success){
		                            	$.messager.alert('提示','添加成功','info',function(){
			                                $("#createWindow").window('close');
										    $("#luckTicketManageTable").datagrid('reload');
		                            	});
		                            }else{
		                                $.messager.alert('提示', res.msg);
		                            }
		                        }
		                    });
                		}
                	});
                }
            });
            
            // 取消按钮
            $('#cancel').click(function(){
                $('#createWindow').window('close');
            });
        });
    </script>
</head>
    <div style="width:440px;">
        <form action="" id="luckForm" accept-charset="utf-8">
            <table>
                <tr>
                    <td class="m-info-title" style="width:80px;">业务类型   </td>
                    <td colspan="2">
                    	<select id="businessMoldSel" name="fbusinessType">
                    		<option value="-1">请选择</option>
                    		<option value="一路好运">一路好运</option>
                    	</select>
                    </td>
                </tr>
                <tr>
                    <td class="m-info-title">方案名称</td>
                    <td colspan="2"><input type="text" id="luckName" style="width:200px;" name="fcouponsName"></td>
                </tr>
                <tr>
                    <td class="m-info-title">优惠券   </td>
                    <td><input type="radio" id="orderCoupon" name="ftype" value="1" checked></td>
                    <td>订单优惠券</td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td><input type="radio" id="discountRadio" checked>折扣<input type="text" id="discount" style="width:100px;" name="fdiscount">%<input type="radio" id="lapseRadio" >直减<input type="text" id="lapse" style="width:100px;" name="fsubtract">元</td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="radio" id="serviceRadio" name="ftype" value="2"></td>
                    <td>增值服务券</td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td id="serviceRadioBox">
                    	<input type="radio" disabled name="faddserviceName" value="装货">装货<input type="radio" disabled name="faddserviceName" value="卸货">卸货<input type="radio" disabled name="faddserviceName" value="回单原件">回单原件<input type="radio" disabled name="faddserviceName" value="上楼">上楼<input type="radio" disabled name="faddserviceName" value="代收货款">代收货款
                    </td>
                </tr>
            </table>
        </form>
        <div style="overflow:hidden; width:160px; margin:20px auto 0;">
            <div  class="Mbutton25 createButton" style="float:left;" id="cancel">取消</div>
            <div  class="Mbutton25 createButton" style="float:right;" id="sumbit">提交</div>
        </div>
    </div>
</html>