<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/js/webuploader-0.1.5/webuploader.css" />
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery.form.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/webuploader-0.1.5/webuploader.js"></script>
<link href="${ctx}/pages/schemeDesign/css/schemeDesign_add.css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/pages/schemeDesign/schemeDesign_add.js"></script>
<title>新增方案(高端设计)</title>
<script type="text/javascript">
var eachMain = {};
var eachInfo = [];
$(document).ready(function(){
	//首次加载的方案
	uploadFile($('.content'));
	/////提交
	$("#tj_Button").click(function(){		        	
			if(checkGdsjData()){
				//加个阴影 防止重复保存		
				layer.load(2, {shade: 0.1});
				eachMain = JSON.stringify(eachMain);
				eachInfo = JSON.stringify(eachInfo);
				var options = {  
	              url : "${ctx}/schemedesign/saveGdSjcjnfo.net",
	              //dataType:"json",
	              //后台返回的是字符串 必须 类型是text 否则一直进error中
	              dataType:"text",
	              type : "POST",   
	              data : {eachMain:eachMain,eachInfo:eachInfo},
	              success : function(msg) {
	                  if(msg == "success"){
	                  	layer.alert('操作成功！', function(alIndex){
									layer.close(alIndex);
									//加载列表界面
									var win= parent.$('#iframepage')[0].contentWindow;
									$(win.document).find("tr[data-fid="+win.demanid+"]").find("td.fstate").html("已设计");
									win.loadScheme(1,win.demanid);
									parent.layer.closeAll();
									//parent.$('#8f3b223239bbc3454aaf308e406a351b').click();
									//window.location.href="${ctx}/menuTree/center.net?menu=7a403c6ed40df9351325af3b5cfdce5r";
							});
	                  }else{
	                  	layer.alert(msg, function(alIndex){
	                  				parent.layer.closeAll();
							});
	                  }
	              },
	              error:function(msg){
	              	layer.alert('操作失败！', function(alIndex){
									layer.close(alIndex);
						});
	              }
	      };  
		  $.ajax(options);
		}
		else{
			/* layer.tips('方案名称、编号、不能为空！', '#fdescription', {
			    tips: [1, '#F7874E'],
			    time: 4000
			}); */
		}	
	});
	
});

</script>
</head>
<body>
<div class="title">平台首页 > 我的业务 > 新增方案</div>
<div>
	<div class="hx">
			<img alt="" src="${ctx}//css/images/hx-l.png" class="hx_l"> 
			<span class="hx_title">新增方案(高端设计)</span>
			<img alt="" src="${ctx}/css/images/hx-r.png"  class="hx_r"> 
	</div>
	<div class="addSchemeDesign">
		<span>新增方案：<image src="${ctx}/css/images/addSchemeDesign.png" onclick="addSchemeDesign()"/></span>
		<input type="hidden" id="fcustomerid" name="fcustomerid" value="${fcustomerid}"/>
		<input type="hidden" id="fsupplierid" name="fsupplierid" value="${fsupplierid}"/>
		<input type="hidden" id="ffirstproductid" name="ffirstproductid" value="${ffirstproductid}"/>	
		<input type="hidden" id="fcreatorid" name="fcreatorid" value="${fcreatorid}"/>		
		<div class="content">
			<a href="javascript:void(0);" class="del" onclick="del(this)">删除</a>
			<table>
				<tr>			
					<td width="100px">方案名称：<input type="hidden" id="fid" name="fid" value="${fid}"/></td>
					<td width="200px"><input type="text" name = "fname" value="${fname}" data-required="true"/></td>
					<td width="100px">方案编号：</td>
					<td width="200px"><input type="text" name = "fnumber" value="${fnumber1}" data-required="true" readOnly="true"/></td>		
				</tr>
				<tr>
					<td>方案描述：</td>
					<td colspan="3">
						<textarea style="float:left;width:600px;height:55px;resize:none;" id="fdescription" name="fdescription" maxlength="200"></textarea>
					</td>
				</tr>
				<tr>
					<td>添加附件：</td>
					<td colspan="3">
						<div id="uploader" class="wu-example">
						    <div class="btns">									    	
						        <div id="picker" ><img src="${ctx}/css/images/addSchemeDesign1.png" /></div>
						    </div>
						    <div id="thelist" class="uploader-list">
						    </div>
						</div>		
					</td>
				</tr>
			</table>
		</div>		
	</div>
	<div class="button">			
		<input type="button" value="确定" class="_btn" id="tj_Button"  />
		<input type="button" value="返回" class="_btn" id="bc_Button" onclick="lastStep()"  />
	</div>
</div>
</body>
</html>