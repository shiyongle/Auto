<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>货主角色查看界面</title>
<script type="text/javascript">
	$(document).ready(function() {
						//加载先隐藏驳回原因栏
						$("#reasonRow").hide();
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
						//通过 先更改认证表的状态，更新原因和审核时间；插入car表一条数据，
						$("#closeButton1").click(function() {
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
						dragImg(oImg1);dragImg(oImg2);
						/*******************图片操作结束*******************/
	});
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
    			<div title="证件一">
    				<div id="img1_div">    
        			<img id="img_tab1" src="${cty}/${img01}"/>
        			<div id="imgTool1">
					<a id="imgBig1"   href="javascript:void(0)"><img src="${ctx}/pages/pc/identification/images/bigImg.png" width="22"/></a>
					<a id="imgSmall1" href="javascript:void(0)"><img src="${ctx}/pages/pc/identification/images/smallImg.png" width="22"/></a>
					<a id="imgRoate1" href="javascript:void(0)"><img src="${ctx}/pages/pc/identification/images/rotateImg.png" width="22"/></a>
					</div> 
        			</div> 
    			</div>   
    			<div title="证件二" id="img2_div">   
        			<img id="img_tab2" src="${cty}/${img02}"/>
        			<div id="imgTool2">
					<a id="imgBig2"   href="javascript:void(0)"><img src="${ctx}/pages/pc/identification/images/bigImg.png" width="22"/></a>
					<a id="imgSmall2" href="javascript:void(0)"><img src="${ctx}/pages/pc/identification/images/smallImg.png" width="22"/></a>
					<a id="imgRoate2" href="javascript:void(0)"><img src="${ctx}/pages/pc/identification/images/rotateImg.png" width="22"/></a>
					</div>     
    			</div>         
			</div>
		</fieldset>
		<div class="Mbutton25 createButton" id="closeButton1" id="closeButton1">关闭</div>
	</div>
</div>
</html>
