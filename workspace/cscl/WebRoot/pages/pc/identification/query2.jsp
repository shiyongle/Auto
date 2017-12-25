<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>车主查看界面</title>
<script type="text/javascript">
	$(document).ready(function() {
			console.log("${car.fprovince_id}");
			//驳回原因可用时，validate会校验，disable时 不会校验
			$("input[type='radio']").click(function(){
				if($("#passradio").is(":checked")){
					$("#reasonRow").hide();
					$("#reason").attr("disabled",true);
				}else{
					$("#reasonRow").show();
					$("#reason").attr("disabled",false);
				}
			});
			//局部放大
			$("#img_01").imageLens({
				borderColor : "red"
			});
			$("#img_02").imageLens({
				borderColor : "#0FF"
			});

			//关闭
			$("#closeButton2").click(function() {
						$("#createWindow").window("close");
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
			
			/*图片拖拽*/
			/*不能使用$("#")获取DOM*/
			var oImg1 = document.getElementById('img_tab1');
			var oImg2 = document.getElementById('img_tab2');
			var oImg3 = document.getElementById('img_tab3');
			dragImg(oImg1);dragImg(oImg2);dragImg(oImg3);
			/*******************图片操作结束*******************/
																		
		});
	
	
	function DownLoadReportIMG(imgPathURL) {
        
        //如果隐藏IFRAME不存在，则添加
        if (!document.getElementById("IframeReportImg"))
            $('<iframe style="display:none;" id="IframeReportImg" name="IframeReportImg" onload="DoSaveAsIMG();" width="0" height="0" src="about:blank"></iframe>').appendTo("body");
        if (document.all.IframeReportImg.src != imgPathURL) {
            //加载图片
            document.all.IframeReportImg.src = imgPathURL;
        }
        else {
            //图片直接另存为
            DoSaveAsIMG();  
        }
    }
    function DoSaveAsIMG() {
        if (document.all.IframeReportImg.src != "about:blank")
            document.frames("IframeReportImg").document.execCommand("SaveAs");        
    }
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
				<table class="add-user" id="createBursarInfo" cellpadding="0" cellspacing="0" width="200" border="0">
					<tr>
						<td width="100" height="40" class="red">状态:</td>
						<td class="m-info-content" >
								<c:if test="${identification.status==1}">待审核</c:if>
								<c:if test="${identification.status==2}">已驳回</c:if>
								<c:if test="${identification.status==3}">已通过</c:if>
								<c:if test="${identification.status==4}">已关闭</c:if>
						</td>
					</tr>
					<tr>
						<td width="100" height="40">车牌号<span class="red"></span>:</td>
						<td><input type="text" id="carNum" name="carNum" style="width:100px;" value="${car.carNum }" /></td>
					</tr>
					<tr height="40">
						<td>驾驶证号<span class="red"></span>:</td>
						<td><input type="text" id="driverNum" name="driverNum" style="width:100px;"  value="${car.driverCode }" /></td>
					</tr>
					<tr height="40">
						<td>司机姓名<span class="red"></span>:</td>
						<td><input type="text" id="driverName" name="driverName" style="width:100px;" value="${car.driverName }" /></td>
					</tr>
					<tr height="80">
						<td>活动区域<span class="red">*</span>:</td>
						<td>
							省:<select class="easyui-combobox" id="province" name="fprovince" style="width:86px;">
								 <option value="${car.fprovince_id}">${car.fprovince}</option>
							</select>
							<div style="height:4px;"></div>
							市:<select class="easyui-combobox" id="city" name="fcity" style="width:86px;">
								<option value="${car.fcity_id}">${car.fcity}</option>
							</select>
							<div style="height:4px;"></div>
							区:<select class="easyui-combobox" name="activeArea" id="area" style="width:86px;">
								   <option value="${car.activeArea}">${car.area}</option> 
							</select>
						</td>
					</tr>
					<tr height="40">
						<td>车辆规格<span class="red"></span>:</td>
						<td><input type="text" id="spec" name="spec" style="width:100px;" value="${car.carSpecName }" /></td>
					</tr>
					<tr height="40">
						<td>车辆类型<span class="red"></span>:</td>
						<td><input type="text" id="itype" name="type" style="width:100px;" value="${car.carTypeName }" /></td>
					</tr>
					<c:if test="${identification.status==2}">
						<tr id="reasonRow">
							<td>驳回原因<span class="red"></span>:</td>
							<td rowspan="2">
								<textarea id="reason" name="reason" style="width:100px;resize:none;height:100px;"   value="${identification.backReason }" >${identification.backReason }</textarea>
							</td>
						</tr>
					</c:if>
				</table>
			</fieldset>
			<div class="Mbutton25 createButton" id="closeButton2" >关闭</div>
		</form>
	</div>
</div>
</html>