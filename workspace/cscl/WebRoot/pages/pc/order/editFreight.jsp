<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>运费调整</title>
<script type="text/javascript">
    $(document).ready(function(){
        //司机补贴
        $('#driver_subsidy').on('input propertychange',function(){         
                $('#fdriverfee').val(numAdd(parseFloat($(this).val()),parseFloat($('#forigin_driverfee').val())));
                 if($('#fdriverfee').val()=='NaN'){$('#fdriverfee').val('')} ;  
        });      
        /***运费调整*/
        $("#submit").click(function() {
            if ($("#TForm").form("validate")) {
                var params = decodeURIComponent($("#TForm").serialize(), true);
                console.log(params);
                $("#createWindow").mask();
                $.ajax({	
                    type : "POST",
                    url : "${ctx}/order/editfreight.do?id=${order.id}",
                    data : params,
                    dataType:"json",
                    success : function(response) {
                        if(response.success == "success") {
                            $.messager.alert('提示', '操作成功', 'info', function() {
                                $("#createWindow").window("close");
                                $("#createWindow").mask("hide");
                                $("#CLOrderTable").datagrid('reload');
                            });
                        }else {
                            $("#createWindow").mask("hide");
                            $.messager.alert('提示', response.data);
                        }
                    }
                });
            }
        });
    });
    $.extend($.fn.validatebox.defaults.rules, {
        greaterZero: {
            validator: function(value,param){
                return parseFloat(value)>=0;
            },
            message: '不能为负数!'
        },
        twoDecimal:{     
            validator:function(value,param){
                var reg = /^\-?\d+(\.\d{1,2})?$/;
                return reg.test(value);
            },
            message:'最多小数点后两位！'         
        }
    });
    //加法
    function numAdd(num1, num2) {
        var baseNum, baseNum1, baseNum2;
        try {
            baseNum1 = num1.toString().split(".")[1].length;
        } catch (e) {
            baseNum1 = 0;
        }
        try {
            baseNum2 = num2.toString().split(".")[1].length;
        } catch (e) {
            baseNum2 = 0;
        }
        baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
        return (num1 * baseNum + num2 * baseNum) / baseNum;
    };
</script>
</head>
<div id="createWindow"></div>
    <form id="TForm" >
        <%-- <input type="hidden" name="id" value="${order.id }"> --%>
        <table class="add-user" id="createBursarInfo">
            <tr>
                <td class="m-info-title">原运费(元)<span class="red">*</span>:</td>
                <td class="m-info-content">
                    <input id="foriginfreight" value="${order. freight}"  disabled="disabled" />
                </td>
            </tr>
            <tr>
                <td class="m-info-title">实收运费(元)<span class="red">*</span>:</td>
                <td class="m-info-content">
                    <input id="freight" name="freight"  value="${order. freight}" type="number" class="easyui-validatebox" data-options="required:true,
                    missingMessage:'实收运费必须填写！',validType:['greaterZero','twoDecimal']"/>
                </td>
            </tr>
            <tr>
                <td class="m-info-title">司机运价(元)<span class="red">*</span>:</td>
                <td class="m-info-content">
                    <input id="forigin_driverfee" name="forigin_driverfee" value="${order.forigin_driverfee }" disabled="disabled"  />
                </td>
            </tr>
            <tr>
                <td class="m-info-title">司机补贴(元)<span class="red">*</span>:</td>
                <td class="m-info-content"> 
                    <input type="number" id="driver_subsidy"class="easyui-validatebox" data-options="required:true,validType:'twoDecimal',missingMessage:'司机补贴必须填写！(不变填零)'"/>
                </td>
            </tr>
            <tr>
                <td class="m-info-title">司机合计(元)<span class="red">*</span>:</td>
                <td class="m-info-content">
                    <input  name="fdriverfee" id="fdriverfee" readonly="readonly" />
                </td>
            </tr>
            <tr>
                <td class="m-info-title">&nbsp;</td>
                <td class="m-info-content"><div class="Mbutton25 createButton" id="submit">确定</div></td>
            </tr>
        </table>
    </form>
</html>
