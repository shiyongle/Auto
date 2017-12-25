<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>审核界面</title>
<script type="text/javascript">
$(document).ready(function() {
    //加载先隐藏驳回原因栏
    $("#reasonRow").hide();
    //驳回原因可用时，validate会校验，disable时 不会校验
    $("input[type='radio']").click(function() {
        if ($("#passradio").is(":checked")) {
            $("#reasonRow").hide();
            $("#reason").attr("disabled", true);
            //如果是通过,那么除了驳回原因,并且不用校验,属性构建对象
            $("#carNum").validatebox("enableValidation");
            $("#driverNum").validatebox("enableValidation");
            $("#driverName").validatebox("enableValidation");
            $("#spec").combobox("enableValidation");
            $("#Itype").combobox("enableValidation");
            $("#reason").validatebox("disableValidation");
        } else {
            $("#reasonRow").show();
            $("#reason").attr("disabled", false);
            //如果是驳回,那么除了驳回原因,并且不用校验,属性构建对象
            $("#carNum").validatebox("disableValidation");
            $("#driverNum").validatebox("disableValidation");
            $("#driverName").validatebox("disableValidation");
            $("#spec").combobox("disableValidation");
            $("#Itype").combobox("disableValidation");
            $("#reason").validatebox("enableValidation");
        }
    });
    //局部放大
    $("#img_01").imageLens({
        borderColor: "red"
    });
    $("#img_02").imageLens({
        borderColor: "#0FF"
    });

    $("#Itype").combobox({
        required: true,
        missingMessage: "请选择",
        validType: "comboRequired",
        invalidMessage: "请选择",
        editable: true,
        width: 110,
        mode: 'remote',
        valueField: 'optionId',
        textField: 'optionName'
    });

    $("#spec").combobox({
        url: "${ctx}/select/getAllCarType1.do",
        required: true,
        missingMessage: "请选择",
        validType: "comboRequired",
        invalidMessage: "请选择",
        editable: true,
        width: 110,
        mode: 'remote',
        valueField: 'optionId',
        textField: 'optionName',
        onSelect: function(record) {
            $("#Itype").combobox({
                url: "${ctx}/select/getAllCarSpecByCarType.do?optionTemp=" + record.optionId,
                required: true,
                missingMessage: "请选择",
                validType: "comboRequired",
                invalidMessage: "请选择",
                editable: true,
                mode: 'remote',
                valueField: 'optionId',
                textField: 'optionName'
            });
        }
    });

    $("#licenseType1").combobox({
        width: 110,
        required: true,
        editable: false,
        panelHeight: "auto",
        validType: "comboRequired",
        invalidMessage: "请选择",
        missingMessage: "请选择"
    });
    $("#province").combobox({
        url: "${ctx}/select/getAllProvince.do",
        required: true,
        missingMessage: "请选择",
        validType: "comboRequired",
        invalidMessage: "请选择",
        editable: true,
        mode: 'remote',
        valueField: 'optionId',
        textField: 'optionName',
        onSelect:function(record){
        	 $("#city").combobox({
                url: "${ctx}/select/getAllCity.do?fprovince_id=" + record.optionId,
                required: true,
                missingMessage: "请选择",
                validType: "comboRequired",
                invalidMessage: "请选择",
                editable: true,
                mode: 'remote',
                valueField: 'optionId',
                textField: 'optionName',
                onSelect:function(record){
                	$("#area").combobox({
		                url: "${ctx}/select/getAllArea.do?fcity_id=" + record.optionId,
		                required: true,
		                missingMessage: "请选择",
		                validType: "comboRequired",
		                invalidMessage: "请选择",
		                editable: true,
		                mode: 'remote',
		                valueField: 'optionId',
		                textField: 'optionName'
	            	});	
                }
            });
        }
    })
    //通过 先更改认证表的状态，更新原因和审核时间；插入car表一条数据，
    $("#passButton").click(function() {
        if ($("#passradio").is(":checked")) {
            if ($("#TForm").form("validate")) {
                $.messager.confirm('提示', '确定要通过吗？',
                function(r) {
                    if (r) {
                        var params = decodeURIComponent($("#TForm").serialize(), true);
                        $.ajax({
                            type: "POST",
                            url: "${ctx}/iden/pass.do",
                            data: params,
                            success: function(response) {
                                if (response == "success") {
                                    $.messager.alert('提示', '添加成功', 'info',
                                    function() {
                                        $("#createWindow").window("close");
                                        $("#CLIdentificationTable").datagrid('reload');
                                    });
                                } else {
                                    $.messager.alert('提示', '错误！', 'info',
                                    function() {
                                        $("#createWindow").window("close");
                                    });
                                }
                            } //function的结束
                        }); //ajax end
                    }
                });
            } //if end
        } else if ($("#refuseradio").is(":checked")) {
            if ($("#reason").val() != "") {
                $.messager.confirm('提示', '确定要驳回吗？',
                function(r) {
                    if (r) {
                        var params = decodeURIComponent($("#TForm").serialize(), true);
                        $.ajax({
                            type: "POST",
                            url: "${ctx}/iden/pass.do",
                            data: params,
                            success: function(response) {
                                if (response == "success") {
                                    $.messager.alert('提示', '添加成功', 'info',
                                    function() {
                                        $("#createWindow").window("close");
                                        $("#CLIdentificationTable").datagrid('reload');
                                    });
                                } else {
                                    $.messager.alert('提示', '错误！', 'info',
                                    function() {
                                        $("#createWindow").window("close");
                                    });
                                }
                            } //function的结束
                        }); //ajax end
                    }
                });
            } //if end
        } //else end
    }); //click end
    /*******************图片操作开始*******************/
    /***图片放大***/
    var m = 1.0,
    l = 1.0,
    n = 1.0;
    $("#imgBig1").click(function() {
        $("#img_tab1").css({
            "-webkit-transform": "scale(" + eval(m + 0.1) + ") rotate(" + 90 * (i - 1) + "deg)",
            "transform": "scale(" + eval(m + 0.1) + ") rotate(" + 90 * (i - 1) + "deg)"
        });
        m += 0.1;
    })
    $("#imgBig2").click(function() {
        $("#img_tab2").css({
            "-webkit-transform": "scale(" + eval(l + 0.1) + ") rotate(" + 90 * (j - 1) + "deg)",
            "transform": "scale(" + eval(l + 0.1) + ") rotate(" + 90 * (j - 1) + "deg)"
        });
        l += 0.1;
    })
    $("#imgBig3").click(function() {
        $("#img_tab3").css({
            "-webkit-transform": "scale(" + eval(n + 0.1) + ") rotate(" + 90 * (k - 1) + "deg)",
            "transform": "scale(" + eval(n + 0.1) + ") rotate(" + 90 * (k - 1) + "deg)"
        });
        n += 0.1;
    })
    /***图片缩小***/
    $("#imgSmall1").click(function() {
        if (m <= 0.5) {
            return
        } else {
            $("#img_tab1").css({
                "-webkit-transform": "scale(" + eval(m - 0.1) + ") rotate(" + 90 * (i - 1) + "deg)",
                "transform": "scale(" + eval(m - 0.1) + ") rotate(" + 90 * (i - 1) + "deg)"
            });
            m -= 0.1;
        }
    })
    $("#imgSmall2").click(function() {
        if (l <= 0.5) {
            return
        } else {
            $("#img_tab2").css({
                "-webkit-transform": "scale(" + eval(l - 0.1) + ") rotate(" + 90 * (j - 1) + "deg)",
                "transform": "scale(" + eval(l - 0.1) + ") rotate(" + 90 * (j - 1) + "deg)"
            });
            l -= 0.1;
        }
    }) 
    $("#imgSmall3").click(function() {
        if (n <= 0.5) {
            return
        } else {
            $("#img_tab3").css({
                "-webkit-transform": "scale(" + eval(n - 0.1) + ") rotate(" + 90 * (k - 1) + "deg)",
                "transform": "scale(" + eval(n - 0.1) + ") rotate(" + 90 * (k - 1) + "deg)"
            });
            n -= 0.1;
        }
    })
    /***图片旋转***/
    var i = 1,
    j = 1,
    k = 1;
    $("#imgRoate1").click(function() {
        $("#img_tab1").css({
            "-webkit-transform": "rotate(" + 90 * i + "deg) scale(" + m + ")",
            "transform": "rotate(" + 90 * i + "deg) scale(" + m + ")"
        });
        i++;
    }) 
    $("#imgRoate2").click(function() {
        $("#img_tab2").css({
            "-webkit-transform": "rotate(" + 90 * j + "deg) scale(" + l + ")",
            "transform": "rotate(" + 90 * j + "deg) scale(" + l + ")"
        });
        j++;
    }) 
    $("#imgRoate3").click(function() {
        $("#img_tab3").css({
            "-webkit-transform": "rotate(" + 90 * k + "deg) scale(" + n + ")",
            "transform": "rotate(" + 90 * k + "deg) scale(" + n + ")"
        });
        k++;
    })

    /*图片拖拽*/
    /*不能使用$("#")获取DOM*/
    var oImg1 = document.getElementById('img_tab1');
    var oImg2 = document.getElementById('img_tab2');
    var oImg3 = document.getElementById('img_tab3');
    dragImg(oImg1);
    dragImg(oImg2);
    dragImg(oImg3);
    /*******************图片操作结束*******************/

}); //ready end
</script>
</head>
<style>
#img1_div,#img2_div,#img3_div{height:409px;position: relative;cursor:move;overflow:hidden;}
#imgTool1,#imgTool2,#imgTool3{width:425px;background:#fff;margin:0 auto;text-align:center;position:absolute;bottom:0;}
#img_tab1,#img_tab2,#img_tab3{position:absolute;width:423px;}
</style>
<div id="Div0">
	<div id="Div1">
		<fieldset style="height:450px;">
			<legend>
				<font style="font-weight:bold;font-style:italic;color:red">附件信息</font>
			</legend>
<!-- 			<table width="450" align="center" border="0" cellspacing="0" -->
<!-- 				cellpadding="0"> -->
<!-- 				<tr> -->
<!-- 					<td align="center"> -->
<!-- 						<p style="border:1px solid #666;"> -->
<%-- 							<a href="${ctx}/pages/pc/identification/viewImg.html?${cty}/${img01}" target="_blank"> --%>
<%-- 							<img id="img_01" src="${cty}/${img01}" height="200" width="445" /> --%>
<!-- 							</a> -->
<!-- 						</p> -->
<!-- 						<p style="border:1px solid #666;"> -->
<%-- 							<a href="${ctx}/pages/pc/identification/viewImg.html?${cty}/${img02}" target="_blank"> --%>
<%-- 							<img id="img_02" src="${cty}/${img02}" height="200" width="445" /> --%>
<!-- 							</a> -->
<!-- 						</p> -->
<!-- 					</td> -->
<!-- 				</tr> -->
<!-- 			</table> -->

			<div id="identityImg" class="easyui-tabs" style="width:425px;height:440px;overflow:hidden">   
    			<div title="行驶证">
    				<div id="img1_div">    
        			<img id="img_tab1" src="${cty}/${img01}"/>
        			<div id="imgTool1">
					<a id="imgBig1"   href="javascript:void(0)"><img src="${ctx}/pages/pc/identification/images/bigImg.png" width="22"/></a>
					<a id="imgSmall1" href="javascript:void(0)"><img src="${ctx}/pages/pc/identification/images/smallImg.png" width="22"/></a>
					<a id="imgRoate1" href="javascript:void(0)"><img src="${ctx}/pages/pc/identification/images/rotateImg.png" width="22"/></a>
					</div> 
        			</div> 
    			</div>   
    			<div title="驾驶证" id="img2_div">   
        			<img id="img_tab2" src="${cty}/${img02}"/>
        			<div id="imgTool2">
					<a id="imgBig2"   href="javascript:void(0)"><img src="${ctx}/pages/pc/identification/images/bigImg.png" width="22"/></a>
					<a id="imgSmall2" href="javascript:void(0)"><img src="${ctx}/pages/pc/identification/images/smallImg.png" width="22"/></a>
					<a id="imgRoate2" href="javascript:void(0)"><img src="${ctx}/pages/pc/identification/images/rotateImg.png" width="22"/></a>
					</div>     
    			</div>   
    			<div title="车辆照片" id="img3_div">   
        			<img id="img_tab3" src="${cty}/${img03}"/> 
        			<div id="imgTool3">
					<a id="imgBig3"   href="javascript:void(0)"><img src="${ctx}/pages/pc/identification/images/bigImg.png" width="22"/></a>
					<a id="imgSmall3" href="javascript:void(0)"><img src="${ctx}/pages/pc/identification/images/smallImg.png" width="22"/></a>
					<a id="imgRoate3" href="javascript:void(0)"><img src="${ctx}/pages/pc/identification/images/rotateImg.png" width="22"/></a>
					</div>   
    			</div>   
			</div> 
			
		</fieldset>
	</div>
	<div id="Div2">
		<form id="TForm">
			<fieldset style="height:450px;">
				<legend>
					<font style="font-weight:bold;font-style:italic;color:blue">车辆信息</font>
				</legend>
				<table class="add-user" id="createBursarInfo" cellpadding="0"
					cellspacing="0" width="200" border="0">
					<tr>
						<td width="100" height="40">车牌号<span class="red">*</span>:</td>
						<td><input type="text" id="carNum" name="carNum"
							style="width:100px;" class="easyui-validatebox" required="true"
							missingMessage="车牌号必须填写" />
						</td>
					</tr>
					<tr height="40">
						<td>驾驶证号<span class="red">*</span>:</td>
						<td><input type="text" id="driverNum" name="driverNum"
							style="width:100px;" class="easyui-validatebox" required="true"
							missingMessage="驾驶证号必须填写" />
						</td>
					</tr>
					<tr height="40">
						<td>司机姓名<span class="red">*</span>:</td>
						<td><input type="text" id="driverName" name="driverName"
							style="width:100px;" class="easyui-validatebox" required="true"
							missingMessage="请填写司机姓名" />
						</td>
					</tr>
					<tr height="80">
						<td>活动区域<span class="red">*</span>:</td>
						<td>
							省:<select class="easyui-combobox" id="province" name="fprovince" style="width:90px;">
								<option value="-1">请选择</option>
							</select>
							<div style="height:4px;"></div>
							市:<select class="easyui-combobox" id="city" name="fcity" style="width:90px;">
								<option value="-1">请选择</option>
							</select>
							<div style="height:4px;"></div>
							区:<select class="easyui-combobox" name="activeArea" id="area" style="width:90px;">
								<option value="-1">请选择</option>
							</select>
						</td>
					</tr>
					<tr height="40">
						<td>车辆规格<span class="red">*</span>:</td>
						<td><select id="spec" name="spec">
								<option value="-1">请选择</option>
						</select></td>
					</tr>
					<tr height="40">
						<td>车辆类型<span class="red">*</span>:</td>
						<td><select id="Itype" name="type">
								<option value="-1">请选择</option>
						</select></td>
					</tr>
					<tr height="40">
						<td>牌照类型<span class="red">*</span>:</td>
						<td>
						<select id="licenseType1" name="licenseType" missingMessage="牌照类型必须选择">
							<option value="-1">请选择</option>
							<option value="0">黄牌</option>
							<option value="1">蓝牌-市</option>
							<option value="2">蓝牌-县</option>
						</select>
						</td>
					</tr>
					<tr>
						<td>审核结果<span class="red">*</span>:</td>
						<td><input type="radio" name="result" value="1" checked="checked" id="passradio"/>通过<input type="radio" name="result" value="2" id="refuseradio"/>驳回</td>
					</tr>
					<tr id="reasonRow">
						<td>驳回原因<span class="red">*</span>:</td>
						<td rowspan="2">
							<textarea id="reason" name="reason" style="width:100px;resize:none;height:100px;" disabled="disabled" class="easyui-validatebox" required="true"
							missingMessage="请填写驳回原因"></textarea>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td colspan="2"><div class="Mbutton25 createButton" id="passButton" id="passButton">审核完成</div></td>
					</tr>
				</table>
			</fieldset>
			
		</form>
	</div>
</div>
</html>
