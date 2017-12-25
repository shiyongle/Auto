<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>修改界面</title>
<script type="text/javascript">
	$(document).ready(function() {
						//局部放大
						$("#img_01").imageLens({
							borderColor : "red"
						});//end img
						$("#img_02").imageLens({
							borderColor : "#0FF"
						});
							
						$("#spec").combobox({
											url : "${ctx}/select/getAllCarType1.do",
											required : true,
											missingMessage : "请选择",
											validType : "comboRequired",
											invalidMessage : "请选择",
											editable : true,
											width : 110,
											mode : 'remote',
											valueField : 'optionId',
											textField : 'optionName',
											onSelect : function(record) {
												 $("#Itype").combobox({
																	url : "${ctx}/select/getAllCarSpecByCarType.do?optionTemp="
																			+ record.optionId,
																	required : true,
																	missingMessage : "请选择",
																	validType : "comboRequired",
																	invalidMessage : "请选择",
																	editable : true,
																	width:110,
																	mode : 'remote',
																	valueField : 'optionId',
																	textField : 'optionName'
																});
											}
										});
						/*******************图片操作开始*******************/
						/***图片放大***/
						var m=1.0,l=1.0,n=1.0;
						$("#imgBig1").click(function(){
							$("#img_tab1").css({"-webkit-transform":"scale("+eval(m+0.1)+") rotate("+90*(i-1)+"deg)","transform":"scale("+eval(m+0.1)+") rotate("+90*(i-1)+"deg)"});
							m+=0.1;
						})
						$("#imgBig2").click(function(){
							$("#img_tab2").css({"-webkit-transform":"scale("+eval(l+0.1)+") rotate("+90*(j-1)+"deg)","transform":"scale("+eval(l+0.1)+") rotate("+90*(j-1)+"deg)"});
							l+=0.1;
						})
						$("#imgBig3").click(function(){
							$("#img_tab3").css({"-webkit-transform":"scale("+eval(n+0.1)+") rotate("+90*(k-1)+"deg)","transform":"scale("+eval(n+0.1)+") rotate("+90*(k-1)+"deg)"});
							n+=0.1;
						})
						/***图片缩小***/
						$("#imgSmall1").click(function(){							
							if(m<=0.5){
								return
							}
							else{								
							$("#img_tab1").css({"-webkit-transform":"scale("+eval(m-0.1)+") rotate("+90*(i-1)+"deg)","transform":"scale("+eval(m-0.1)+") rotate("+90*(i-1)+"deg)"});
							m-=0.1;
							}
						})
						$("#imgSmall2").click(function(){							
							if(l<=0.5){
								return
							}
							else{								
							$("#img_tab2").css({"-webkit-transform":"scale("+eval(l-0.1)+") rotate("+90*(j-1)+"deg)","transform":"scale("+eval(l-0.1)+") rotate("+90*(j-1)+"deg)"});
							l-=0.1;
							}
						})
						$("#imgSmall3").click(function(){							
							if(n<=0.5){
								return
							}
							else{								
							$("#img_tab3").css({"-webkit-transform":"scale("+eval(n-0.1)+") rotate("+90*(k-1)+"deg)","transform":"scale("+eval(n-0.1)+") rotate("+90*(k-1)+"deg)"});
							n-=0.1;
							}
						})
						/***图片旋转***/
						var i=1,j=1,k=1;						
						$("#imgRoate1").click(function(){
							$("#img_tab1").css({"-webkit-transform":"rotate("+90*i+"deg) scale("+m+")","transform":"rotate("+90*i+"deg) scale("+m+")"});
							i++;
						})
						$("#imgRoate2").click(function(){
							$("#img_tab2").css({"-webkit-transform":"rotate("+90*j+"deg) scale("+l+")","transform":"rotate("+90*j+"deg) scale("+l+")"});
							j++;
						})
						$("#imgRoate3").click(function(){
							$("#img_tab3").css({"-webkit-transform":"rotate("+90*k+"deg) scale("+n+")","transform":"rotate("+90*k+"deg) scale("+n+")"});
							k++;
						})
					         $("#area").combobox({
						        url: "${ctx}/select/getAllArea.do?fcity_id=" +"${car.fcity_id}",
						        required: true,
						        missingMessage: "请选择",
						        validType: "comboRequired",
						        invalidMessage: "请选择",
						        editable: true,
						        mode: 'remote',
						        width: 90,
						        valueField: 'optionId',
						        textField: 'optionName'
						    })
						    $("#area").combobox('select',"${car.activeArea}"); 
						    $("#city").combobox({
						        url: "${ctx}/select/getAllCity.do?fprovince_id=" +"${car.fprovince_id}",
						        required: true,
						        missingMessage: "请选择",
						        validType: "comboRequired",
						        invalidMessage: "请选择",
						        editable: true,
						        width: 90,
						        mode: 'remote',
						        valueField: 'optionId',
						        textField: 'optionName',
						        onSelect:function(record){
						        	var option;
						        	if(record == null){
						        		option = "${car.fcity_id}";
						        	}else{
						        		option =record.optionId;
						        	}
						        	$("#area").combobox({
								        url: "${ctx}/select/getAllArea.do?fcity_id=" + option,
								        required: true,
								        missingMessage: "请选择",
								        width: 90,
								        validType: "comboRequired",
								        invalidMessage: "请选择",
								        editable: true,
								        mode: 'remote',
								        valueField: 'optionId',
								        textField: 'optionName'
								    })
								    if(record == null){
						        		$("#area").combobox('select',"${car.activeArea}");
						        	}
						        }
						    })    
						    $("#province").combobox({
						        url: "${ctx}/select/getAllProvince.do",
						        required: true,
						        missingMessage: "请选择",
						        validType: "comboRequired",
						        invalidMessage: "请选择",
						        editable: true,
						        width: 90,
						        mode: 'remote',
						        valueField: 'optionId',
						        textField: 'optionName',
						        onSelect: function(record) {
						            $("#city").combobox({
								        url: "${ctx}/select/getAllCity.do?fprovince_id=" + record.optionId,
								        required: true,
								        missingMessage: "请选择",
								        validType: "comboRequired",
								        invalidMessage: "请选择",
								        editable: true,
								        width: 90,
								        mode: 'remote',
								        valueField: 'optionId',
								        textField: 'optionName'
								    })
						        }
						    })
						    $("#city").combobox("select", "${car.fcity_id}");
						    $("#submit").click(function() {
						        if ($("#TFormE").form("validate")) {
						            var params = decodeURIComponent($("#TFormE").serialize(), true);
						            //alert(params);
						            $.ajax({
						                type: "POST",
						                url: "${ctx}/car/update.do",
						                data: params,
						                success: function(response) {
						                    if (response == "success") {
						                        $.messager.alert('提示', '修改成功', 'info',
						                        function() {
						                            $("#createWindow").window("close");
						                            $("#CLCarTable").datagrid('reload');
						                        });
						                    } else {
						                        $.messager.alert('提示', "错误！");
						                    }
						                }
						            });
						        }
						    }); 
			        	
			        	
						/*图片拖拽*/
						/*不能使用$("#")获取DOM*/
						var oImg1 = document.getElementById('img_tab1');
						var oImg2 = document.getElementById('img_tab2');
						var oImg3 = document.getElementById('img_tab3');
						dragImg(oImg1);dragImg(oImg2);dragImg(oImg3);
						/*******************图片操作结束*******************/
						
	});//ready end
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
						<td width="100" height="50">车牌号<span class="red">*</span>:</td>
						<td><input type="text" id="carNum" name="carNum"
							style="width:100px;" class="easyui-validatebox" value="${updateIden.carNumber}" required="true"
							missingMessage="车牌号必须填写" />
						</td>
					</tr>
					<tr height="50">
						<td>驾驶证号<span class="red">*</span>:</td>
						<td><input type="text" id="driverNum" name="driverNum"
							style="width:100px;" class="easyui-validatebox" value="${updateIden.driverCode}" required="true"
							missingMessage="驾驶证号必须填写" />
						</td>
					</tr>
					<tr height="50">
						<td>司机姓名<span class="red">*</span>:</td>
						<td><input type="text" id="driverName" name="driverName"
							style="width:100px;" class="easyui-validatebox" value="${updateIden.driverName}" required="true"
							missingMessage="请填写司机姓名" />
						</td>
					</tr>
					<tr height="80">
						<td>活动区域<span class="red">*</span>:</td>
						<td>
							省:<select id="province" name="fprovince">
								<option id="provinveOption" value="${car.fprovince_id}">${car.fprovince}</option>
							</select>
							<div style="height:4px;"></div>
							市:<select id="city" name="fcity">
								<%-- <option id="cityOption" value="${car.fcity_id}">${car.fcity}</option> --%>
								<option value="-1">请选择</option>
							</select>
							<div style="height:4px;"></div>
							区:<select  name="activeArea" id="area">
								<%-- <option id="areaOption" value="${car.activeArea}">${car.area}</option>
								 --%>
								 <option value="-1">请选择</option>
							</select>
						</td>
					</tr>
					<tr height="50">
						<td>车辆规格<span class="red">*</span>:</td>
						<td>
							<select id="spec" name="spec">
								<option value="${updateIden.carSpecId}">${updateIden.specName}</option>
							</select>
						</td>
					</tr>
					<tr height="50">
						<td>车辆类型<span class="red">*</span>:</td>
						<td>
							<input id="tagType" type="hidden" name="typemaster" value="${updateIden.carType}"/>
							<select id="Itype" name="type" style="width:110px;">
								<option value="请选择">${updateIden.typeName}</option>
							</select> 
						</td>
					</tr>
				</table>
			</fieldset>
			<div class="Mbutton25 createButton" id="confirmUpdate" onclick="updateIden();">确认修改</div>
		</form>
	</div>
<script type="text/javascript">
						//修改事件
						function updateIden(){
							//验证表单所有数据
							if ($("#TForm").form("validate")) {
								$.messager.confirm('提示', '是否确定修改？', function(r) {
									if(r){
										//序列化表单数据
										var params = decodeURIComponent($("#TForm").serialize(),true);
										//异步提交数据
										$.ajax({
											type : "POST",
											url : "${ctx}/iden/updateExclude.do",
											data : params,
											success : function(response) {
												if(response == "success"){
													$.messager.alert(
																'提示',
																'修改成功',
																'info',
																function() {
																	$("#createWindow").window("close");
																	$("#CLIdentificationTable").datagrid('reload');
																});
												}else{
													$.messager.alert(
																'提示',
																'错误！',
																'info',
																function() {
																	$("#createWindow").window("close");
																});
												}
											}//end success
										});//end ajax
									}//end if(r)
								});//end confirm
							}//end if
						}//end  function	
</script>
</div>
</html>
