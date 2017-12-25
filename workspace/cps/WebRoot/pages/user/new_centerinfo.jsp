<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户中心-基础资料</title>
<link href="${ctx }/css/kkpager.css" rel="stylesheet" type="text/css" />
<script src="${ctx }/js/_common.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery.form.js" ></script>
<script src="${ctx }/js/kkpager.js" type="text/javascript" language="javascript"></script>
<style>
* {
	margin: 0px auto;
	padding: 0px;
}

input[type=text] {
	padding-left: 5px;
	outline: none;
	width: 300px;
	height: 30px;
	border: 1px solid lightgray;
}

#nav {
	width: 100%;
}

#container {
	width: 1080px;
	height: 780px;
}

#container .p1 {
	font-size: 12px;
	line-height: 60px;
	height: 60px;
	width: 1080px;
}

.address,.subaccount,.certificate,.supplierDes {
	width: 1080px;
	background-color: white;
}

.subaccount,.certificate,.supplierDes {
	height: 715px;
	display: none;
}

.subaccount {
	overflow: hidden;
	padding-bottom: 50px;
}

.img_left {
	display: inline-block;
	float: left;
	margin-left: 94px; +
	margin-left: 61px; *+
	margin-left: 61px;
}

.img_right {
	display: inline-block;
	float: left;
}

.title_add {
	display: inline-block;
	float: left;
	width: 210px;
	text-align: center;
	font-family: "微软雅黑";
	font-size: 24px;
	text-decoration: none;
	color: black;
	cursor: default;
}

#container .address .phone {
	width: 180px;
}

._update {
	position: absolute;
	cursor: pointer;
	width: 100px;
	height: 32px;
	background-color: #C00;
	border: none;
	margin-left: 16px;
	color: white;
	font-size: 16px;
	line-height: 30px;
	letter-spacing: 5px;
}

._submit {
	width: 110px;
	height: 35px;
	border: none;
	background-color: #C00;
	color: white;
	font-family: "微软雅黑";
	font-size: 18px;
	letter-spacing: 2px;
	cursor: pointer;
}

._back {
	width: 135px;
	height: 48px;
	background-color: #F66;
	border: none;
	color: white;
	line-height: 48px;
	font-size: 16px;
	border-radius: 10px;
}

#head {
	height: 112px;
}

#foot {
	height: 150px;
}

table .tip {
	float: left;
	height: 20px;
	width: 200px;
	text-align: left;
	line-height: 20px;
	color: #666;
	font-size: 12px;
	border: 1px solid #3C0;
	word-wrap: break-word;
}

table .warn {
	float: left;
	height: 20px;
	width: 200px;
	text-align: left;
	line-height: 20px;
	color: red;
	font-size: 12px;
	border: 1px solid #FF0033;
}

.subaccount_list {
	width: 775px;
	margin: 0 auto;
	margin-top: 50px;
	border: 0px solid #bfbfbf;
}

.subaccount_list_table {
	border: 1px solid #bfbfbf;
	border-collapse: collapse;
	text-align: center;
}

.subaccount_list_table tr td {
	border: none;
}

.subaccount_list_table a {
	text-decoration: none;
	color: #000;
}

.subaccount_list_table a:hover {
	color: #000;
}

.searchButton {
	height: 32px;
	line-height: 30px;
	width: 30px;
	background: url(../css/images/fdj1.png) white 5px 5px no-repeat;
	cursor: pointer;
	border: 1px solid lightgray;
	border-left: 0px;
	float: right;
}

.searchKey {
	float: right;
	width: 200px !important;
}
/*tab标签*/
/*2016年3月18日09:30:38 HT*/
#require {
	width: 1080px;
	margin: 0 auto;
	margin-top: 10px;
}

#tbl1 {
	font-size: 12px;
	text-align: center;
	border-collapse: collapse;
	clear: both;
}

a {
	text-decoration: none;
}

#fstate1 {
	float: left;
}

#fstate1 a {
	float: left;
	padding: 4px 10px;
	color: #3a3a3a;
	border-radius: 2px 2px 0 0;
	font-size: 17px;
	font-family: 'Microsoft Yahei';
}

#fstate1 .active {
	color: #fff;
	background: #f76350;
}
/*2016年3月18日09:30:38 HT*/
#kkpager {
	width: 1000px;
	height: 180px;
	line-height: 100px;
	text-align: right;
	padding-right: 50px;
	background-color: white;
	margin: 0 auto;
}

.subaccount_list_tr a.reset_pass {
	color: #999 !important;
}

.subaccount_list_tr a:hover,.subaccount_list_tr a.reset_pass:hover {
	color: #ff0000 !important;
}

.certificateImg>div {
	float: left;
	width: 200px;
	height: 200px;
	margin-right: 100px;
	position: relative;
	border: 1px solid rgb(201,201,204);
	background-color: rgb(230,230,230);
}

.infomation {
	color: rgb(211, 32, 32);
	padding-left: 94px;
	padding-bottom: 5px;
	font-size: 18px;
	font-family: "微软雅黑";
}

.tbl_form {
	cellpadding: 0;
	cellspacing: 0;
	border: 0;
	width: 1080px;
	border-collapse:collapse;
}
</style>
</head>

<body style="background-color:#f1f1f1;" onload="IFrameResize();">
	<div id="nav">
        <div id="container">
        	<p class="p1">平台首页&nbsp;&nbsp;&gt;&nbsp;&nbsp;用户中心&nbsp;&nbsp;&gt;&nbsp;&nbsp;用户资料</p>
<!--tab -->
        <div id="require">
		<table cellpadding="0" cellspacing="0" border="0" width="1080" id="tbl1" style="table-layout: fixed; ">   
		 <tr align="left">
		    <td colspan="5" style="border-bottom: 2px solid #f76350;">
		     <p id="fstate1">
		      <a href="javascript: void(0)" class="active" id="userMessage">用户资料</a>
		      <c:if test="${TSysUser.fisreadonly == '0'}">
			      <a href="javascript: void(0)" id="certificate">认证资料</a>
			      <c:if test="${designerCertificateInfo.fstatus== '1'}">
			      <a href="javascript: void(0)" id="supplierDes">企业简介</a>
			      </c:if>
		      </c:if>
		      <a href="javascript: void(0)" id="subAccountManage">子账户管理</a> 
		     </p>  
		    </td>
		   </tr> 
		</table>
		</div>
<!-- 用户资料 tab -->
             <div class="address">
				<form id="user_info"  >
				<input type="hidden" id="fid" name="fid" value="${TSysUser.fid}"/>
        		<input type="hidden" id="fname" name="fname" value="${TSysUser.fname}"/>
                	<table  class="tbl_form">
                    	<tr height="124">
                        	<td colspan="3">
                            	<img src="${ctx}/css/images/hx-l.png" class="img_left"/>
                                <a href="javascript:void(0);" class="title_add">用&nbsp;户&nbsp;资&nbsp;料&nbsp;</a>
                                <img src="${ctx}/css/images/hx-r.png" class="img_right"/>
                            </td>
                        </tr>
                        <tr>
                        	<td height="60" width="380px" align="right">账户名：</td>
                            <td width="300px"><input id="fname"  type="text"  name="fname" value="${TSysUser.fname}" /></td>
                            <td width="300px">&nbsp;</td>
                        </tr>
                        <tr>
                        	<td height="60" width="380px" align="right">本公司名称：</td>
                            <td width="300px"><input id="fcustomername"  type="text"  name="fcustomername" value="${TSysUser.fcustomername}" /></td>
                            <td width="300px">&nbsp;</td>
                        </tr>
                        <tr>
                        	<td height="60" align="right">联系电话：</td>
                            <!-- <td><input type="text" class="phone"/><input type="button" value="更改" class="_update"/></td> -->
                             <td><input  type="text" class="phone" id="ftel" name="ftel" value="${TSysUser.ftel}" readonly="true" /><input type="button" onclick="changeTel()" value="更改" class="_update"/></td>
                            <td id="tipTel">&nbsp;</td>
                        </tr>
<!--                         <tr id="yzm"  style="display: none"> -->
<!-- 	                    	<td height="60" align="right"><span style="color:red;">*</span>验证码 ：</td> -->
<!-- 	                        <td> -->
<!-- 	                            <input id="identCode" name="identCode" type="text" maxlength="6" class="checkNum" style="width:70px;"/> -->
<!-- 	                            <input id="IdentCode" type="button" value="获取验证码" class="IdentCode" style="width:130px;height:30px;" onclick="getYzm()"/> -->
<!-- 	                         </td> -->
<!-- 	                         <td id="tipIdentCode"></td> -->
<!--                     	</tr> -->
                         <tr>
                        	<td height="60" align="right">邮&nbsp;&nbsp;箱：</td>
                            <td><input type="text"  id="femail" name="femail" value="${TSysUser.femail}" /></td>
                            <td id="tipEmail">&nbsp;</td>
                        </tr>
                          <tr>
                        	<td height="60" align="right">电&nbsp;&nbsp;话：</td>
                            <td><input type="text" id="fphone" name="fphone" value="${TSysUser.fphone}" /></td>
                            <td id="">&nbsp;</td>
                        </tr>
                         <tr>
                        	<td height="60" align="right">传&nbsp;&nbsp;真：</td>
                            <td><input type="text" id="ffax" name="ffax" value="${TSysUser.ffax}" /></td>
                            <td id="tipFax">&nbsp;</td>
                        </tr>
                        <tr>
                        	<td height="60" align="right">Q&nbsp;Q：</td>
                            <td><input type="text" name="fqq" value="${TSysUser.fqq}" /></td>
                            <td>&nbsp;</td>
                        </tr>
                        <%-- <tr>
                        	<td height="60" align="right">平台身份：</td>
                            <td><img src="${ctx}/css/images/cpsshejishi.png"/></td>
                            <td>&nbsp;</td>
                        </tr> --%>
                        <tr>
                        	<td colspan="3" height="100" align="center">
                            	<input type="button" id="saveButton" value="保存" class="_submit"/>
                                <!-- <input type="button" value="返回" class="_back"/> -->
                            </td>
                        </tr>
                    </table>
                    
                    
                    
                    
                </form>
            </div>
        
			<!-- 子账户管理 tab --> 
              <div class="subaccount">
				<form id="user_subaccount"  >
                	<table class="tbl_form">
                    	<tr height="124">
                        	<td colspan="3">
                            	<img src="${ctx}/css/images/hx-l.png" class="img_left"/>
                                <a href="javascript:void(0);" class="title_add">子&nbsp;账&nbsp;户&nbsp;管&nbsp;理&nbsp;</a>
                                <img src="${ctx}/css/images/hx-r.png" class="img_right"/>
                                <input type="hidden" name="user.fid" id="subaccountId" />
                            </td>
                        </tr>
                        <tr>
                        	<td height="60" width="380px" align="right"><span style="color:red;">*</span>用户名：</td>
                            <td width="300px"><input id="subaccountName"  type="text" name="user.fname"  value="" /></td>
                            <td width="300px">&nbsp;</td>
                        </tr>
                        <tr>
                        	<td height="60" width="380px" align="right"><span style="color:red;">*</span>联系电话：</td>
                            <td width="300px"><input id="subaccountTel"  type="text" name="user.ftel" value="" /></td>
                            <td width="300px">&nbsp;</td>
                        </tr>
                        <tr>
                        	<td colspan="3" height="100" align="center">
                            	<input type="button" style="width:120px;" id="saveButtonDesing" value="保存" class="_submit" onclick="saveDesigner()"/>
                            </td>
                        </tr>
                    </table>
                </form>
				<div class="subaccount_list">
					<form action="#" method="post" id="searchForm"
						onsubmit="return false">
						<input type="button" value="" class="searchButton"
							id="searchButton" /> <input type="text" id="searchKey"
							class="searchKey" name="keyword"
							placeholder="搜索" />
					</form>
					<table width="775" border="1" cellspacing="0" cellpadding="0"
						class="subaccount_list_table"  onscroll=SetCookie("scroll",body.scrollTop);   onload="scrollback();">
						<tr height="40" style="background: #d9d7d7;">
							<td width="200">用户名</td>
							<td>手机</td>
							<td>操作</td>
							<td>重置</td>
						</tr>
					</table>
				</div>
				<div id="kkpager"></div>
			</div>
			<!--子账户-->
			<!-- 认证资料 --> 
			<div  class="certificate">
				<table class="tbl_form">
                    	<tr height="124">
                        	<td colspan="5">
                            	<img src="${ctx}/css/images/hx-l.png" class="img_left"/>
                                <a href="javascript:void(0);" class="title_add">认&nbsp;证&nbsp;资&nbsp;料&nbsp;</a>
                                <img src="${ctx}/css/images/hx-r.png" class="img_right"/>
                            </td>
                        </tr>
                        <tr>
                        	<td colspan="5" class="infomation">
                        		<c:choose>
                        			<c:when test="${designerCertificateInfo.fstatus== '0'}">
                        				正在审核中，请耐心等待...
                        			</c:when>
                        			<c:when test="${designerCertificateInfo.fstatus== '1'}">
                        				恭喜您已认证成功！
                        			</c:when>
                        			<c:when test="${designerCertificateInfo.fstatus== '2'}">
                        				审核未通过<c:if test="${not empty designerCertificateInfo.fremark }">(${designerCertificateInfo.fremark})</c:if>
                        			</c:when>
                        			<c:otherwise>
                        				您还未提交认证信息
                        			</c:otherwise>
                        		</c:choose>
                        	</td>
                        </tr>
                        <c:if test="${empty designerCertificateInfo.fstatus}">
                       	<tr>
                        	<td colspan="5" align="center">
                        		<input type="button"  value="立即认证" class="_submit" onclick="window.parent.location.href='${ctx}/usercenter/d_certificate.net';"/>
                        	</td>
                        </tr>
                        </c:if>
                        <c:if test="${not empty designerCertificateInfo.fstatus}">
                        <tr>
                        	<td height="60" width="75px" style="padding-left: 127px;">企业名称</td>
                            <td width="400"><input disabled="disabled" style="width:250px;" type="text" value="${designerCertificateInfo.fcompanyname}" /></td>
                            <td height="60" width="75px" >所属行业</td>
                            <td width="300"><input disabled="disabled"  style="width:250px;" type="text" value="${designerCertificateInfo.findustry}" /></td>
                            <td></td>
                        </tr>
                        <tr>
                        	<td height="40" style="padding-left: 127px;" colspan="5">已上传的证件：</td>
                        </tr>
                         <tr>
                        	<td colspan="5" class="certificateImg" style="padding-left: 127px;">
                        	</td>
                        </tr>
                        <tr>
                        	<td colspan="5" style="padding-left: 427px;padding-top: 40px;">
                        		<c:choose>
	                        		<c:when test="${designerCertificateInfo.fstatus=='2'}">
	                        			<input type="button" style="width:200px;"  value="修改认证信息" class="_submit" onclick="window.parent.location.href='${ctx}/usercenter/d_certificate.net';"/>
	                        		</c:when>
	                        		<c:when test="${designerCertificateInfo.fstatus=='1'}">
	                        			<input type="button" style="width:200px;"  value="去完善企业资料" class="_submit" onclick="$('#supplierDes').trigger('click');"/>
	                        		</c:when>
                        		</c:choose>
                        	</td>
                        </tr>
                        </c:if>
                    </table>
			</div>
			<!-- 认证资料 --> 
			
			
			<!-- 企业简介 -->
			<div  class="supplierDes">
				<c:if test="${designerCertificateInfo.fstatus=='1'}">
				<form id="supplierInfo" onsubmit="return false;">
					<table class="tbl_form" >
	                    	<tr height="124">
	                        	<td colspan="6">
	                            	<img src="${ctx}/css/images/hx-l.png" class="img_left"/>
	                                <a href="javascript:void(0);" class="title_add">企&nbsp;业&nbsp;简&nbsp;介&nbsp;</a>
	                                <img src="${ctx}/css/images/hx-r.png" class="img_right"/>
	                            </td>
	                        </tr>
	                        <tr height="60">
	                        	<td width=110px"></td>
	                        	<td width="75px">企业名称</td>
	                            <td width="370px"><input disabled="disabled" style="width:250px;" type="text" name="fname"  value="${supplier.fname}" /></td>
	                            <td width="75px" >传&nbsp;&nbsp;真</td>
	                            <td width="300px"><input style="width:250px;" type="text" name="ffax" value="${supplier.ffax}" /></td>
	                            <td></td>
	                        </tr>
	                        
	                        <tr height="60">
	                        	<td></td>
	                        	<td width="75px">电&nbsp;&nbsp;话</td>
	                            <td width="370px"><input style="width:250px;" type="text" name="ftel"  value="${supplier.ftel}" /></td>
	                            <td width="75px" >地&nbsp;&nbsp;址</td>
	                            <td width="300px"><input style="width:250px;" type="text" name="faddress" value="${supplier.faddress}" /></td>
	                            <td></td>
	                        </tr>
	                        <tr height="15"></tr>
	                        <tr>
	                         	<td></td>
	                        	<td width="75px" valign="top">企业简介</td>
	                            <td colspan="3">
	                            	<textarea rows="6" id="supplierInfo_fdescription" style="resize: none;width: 700px">${supplier.fdescription}</textarea>
	                            </td>
	                            <td></td>
	                        </tr>
	                        <tr height="60">
	                            <td colspan="6" align="center">
	                            	<input type="button"  value="保存" class="_submit" onclick="saveSupplierInfo()"/>
	                            </td>
	                        </tr>
						</table>
					</form>
					</c:if>
			</div>
			<!-- 企业简介 --> 
        </div>
    </div>

<script type="text/javascript">
/*重置密码 Start*/
function reset_pass(id){
	$.ajax({
		type : "POST",
		url : "${ctx}/user_editResetPwd.net?id="+id,
		dataType:"text",		
		success : function(response) {
			if(response=='success')
			{
				layer.msg("已重置密码，初始密码为手机号码后6位！");
			}else
			{
				layer.alert("密码重置失败，请联系客服！");
			}
		}
	});
}
/*重置密码 End*/

/*启用禁用 Start*/
function subaccount_state(id){
	$.ajax({
		type : "POST",
		url : "${ctx}/user_editSubState.net?id="+id,
		dataType:"text",
		success : function(response) {
			if(response=='success')
			{
				layer.msg("状态修改成功！");
				gridUaddressTable(1);
			}else
			{
				layer.alert("状态修改失败，请联系客服！");
			}
		}
	});
}
/*启用禁用 End*/

/*保存或修改设计师 Start*/
function saveDesigner(){
	if($("#subaccountTel").val() =='' || $("#subaccountName").val() ==''){
		parent.layer.msg('红色*为必填项,请完善信息！');
	}else{
		var obj =$("#user_subaccount").serialize();
		$.ajax({
			type : "POST",
			url : "${ctx}/user_editSubAccount.net",
			dataType:"text",
			data:obj,
			success : function(response) {
				if(response == "nameunique_fail"){
					layer.alert("该用户名已存在！");
				}else if(response == "telunique_fail")
				{
					layer.alert("该手机号已注册！");
				}else if(response=="tel_invalid")
				{
					layer.alert("手机号格式不合法，请检查后重试！");
				}
				else if(response == "savesuccess")
				{
					layer.msg("子账户添加成功！");
					update();
					gridUaddressTable(1);
				}else if(response == "updatesuccess")
				{
					layer.msg("子账户修改成功！");
					update();
					gridUaddressTable(1);
				}
				else{
					layer.alert("操作失败！");
				}
			}
		});
	}
}
/*保存或修改设计师 End*/

/*刷新详情的表单 Start*/
function update()
{
	$('#subaccountName').val('');
	$('#subaccountTel').val('');
	$('#subaccountId').val('');
}
/*刷新详情的表单 End*/

/**加载列表 Start*/
function gridUaddressTable(page){
	var obj=$("#searchForm").serialize();
  		$.ajax({
		type : "POST",
		url : "${ctx}/user_loadSubAccount.net?pageNo="+page+"&pageSize="+5,
		dataType:"json",
		data:obj,
		success : function(response) {
			$(".subaccount_list_tr").remove();
			/*循环添加TR开始*/
			$.each(response.list, function(i, ev) {
				var  html =['<tr height="70" class="subaccount_list_tr" bgcolor="',ev.feffect==1?'#d9d7d7':'','" id="'+ev.fid+'">',
				            '<td>',ev.fname,'</td>',
				            '<td>',ev.ftel,'</td>',
 				            '<td><a href="javascript:void(0)" class="sublist_edit" >修改</a>   <a href="javascript:void(0)" class="subaccount_state" onclick="subaccount_state(\''+ev.fid+ '\')">',ev.feffect==0?'禁用':'启用','</a></td>',						    
				            '<td><a href="javascript:void(0)" class="reset_pass" onclick="reset_pass(\''+ev.fid+ '\')" >重置密码</a></td>',
				            '</tr>'].join("");
                   $(html).appendTo(".subaccount_list_table");
			});
			window.getHtmlLoadingAfterHeight();
			/*循环添加TR结束*/
			/*渲染分页主键开始*/
			kkpager.pno =response.pageNo;
			kkpager.total =Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
			kkpager.totalRecords =response.totalRecords;
			kkpager.generPageHtml({
				click : function(n){
					window.gridUaddressTable(n);
					this.selectPage(n);
				},
				pno : response.pageNo,//当前页码
				total : Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize)),//总页码
				totalRecords : response.totalRecords,//总数据条数
				lang : {
					prePageText : '上一页',
					nextPageText : '下一页',
					totalPageBeforeText : '共',
					totalPageAfterText : '页',
					totalRecordsAfterText : '条数据',
					gopageBeforeText : '转到',
					gopageButtonOkText : 'Go',
					gopageAfterText : '页',
					buttonTipBeforeText : '第',
					buttonTipAfterText : '页'
				}
			});
			/*渲染分页主键结束*/
		}
	});
}
/**加载列表 End*/
	
/*记录滚动条位置 Start*/
	function Trim(strValue) {
		return strValue.replace(/^s*|s*$/g, "");
	}
	function SetCookie(sName, sValue) {
		document.cookie = sName + "=" + escape(sValue);
	}
	function GetCookie(sName) {
		var aCookie = document.cookie.split(";");
		for (var i = 0; i < aCookie.length; i++) {
			var aCrumb = aCookie[i].split("=");
			if (sName == Trim(aCrumb[0])) {
				return unescape(aCrumb[1]);
			}
		}
		return null;
	}
	function scrollback() {
		if (GetCookie("scroll") != null) {
			document.body.scrollTop = GetCookie("scroll")
		}
	}
/*记录滚动条位置 End*/




var images = ${images};
/*窗体加载运行---tab切换*/
$(document).ready(function(){	
	//2016-4-6 by les 如果是子账号 暂时不允许看见子账号选项卡
	if('${session.TSysUser.fisreadonly}'==1){
		$('#subAccountManage').css('display','none');
	}
	//点击切换对应显示隐藏
	  $('#fstate1 a').on('click',function(event){  
		  $(this).addClass('active').siblings().removeClass('active');
	  });
	  $('#userMessage').click(function(){		  
		  $('.address').show();
		  $('.subaccount').hide();
		  $('.certificate').hide();
		  $('.supplierDes').hide();
	  });
	   $('#subAccountManage').click(function(){		  
		  $('.address').hide();
		  $('.subaccount').show();
		  $('.certificate').hide();
		  $('.supplierDes').hide();
	 	  window.gridUaddressTable(1);
	  });
	   $('#certificate').click(function(){		  
			  $('.address').hide();
			  $('.subaccount').hide();
			  $('.certificate').show();
			  $('.supplierDes').hide();
		 	  window.gridUaddressTable(1);
	  });
	   $('#supplierDes').click(function(){		  
			  $('.address').hide();
			  $('.subaccount').hide();
			  $('.certificate').hide();
			  $('.supplierDes').show();
		 	  window.gridUaddressTable(1);
	  });
	 //页面打开时分页
	//搜索
	$("#searchButton").click(function() {
	 	  window.gridUaddressTable(1);
	});
	//点击子账号修改
	$('.sublist_edit').live("click",function(){
		var tel=$(this).parent().prev().text();
		var id=$(this).parent().parent().attr("id");
		var name=$(this).parent().prev().prev().text();
		$('#subaccountName').val(name);
		$('#subaccountTel').val(tel);
		$('#subaccountId').val(id);
		$("#subaccountName").focus();
	});
	
	$("#subaccountName,#subaccountTel").focus(function(){
		$(this).css("background-color","#FFFFCC");
	});
	$("#subaccountName,#subaccountTel").blur(function(){
		$(this).css("background-color","#fff");
	}); 
// 	//启用，禁用
// 	var state;
// 	$(".subaccount_state").click(function(){
// 		if($(this).text()=="启用")
// 			{
// 			state=$(this).text("禁用");
// 			}
// 		else
// 			{
// 			state=$(this).text("启用");
// 			}
// 	});

	//加载图片
	for(var key in images){
		$(".certificateImg").append("<div></div>");
		loadImage("${ctx}/productfile/getFileSource.net?fid=" + images[key], key);
	}
});

	function loadImage(src, index){
		if(src){
			var image=new Image();   
			image.src = src;
			image.onload=function(){
				$div = $(".certificateImg").find("div:eq("+index+")");
				if($div.length > 0){
					var width = $div.width();
					var height =$div.height();
					if(this.width != 0 && this.height != 0){
						var b = image.width / image.height;
						if(b > width / height){
							height = width / b;
						} else {
							width  = b * height;
						}
					}
					var margin_top = ($div.height() - height) / 2;
					var margin_left = ($div.width() - width) / 2;
					$div.append("<img style='width:"+width+"px;height:"+height+"px;margin-top:"+margin_top+"px;margin-left:"+margin_left+"px;' src="+ image.src +">");
				}
			};
		}
	}

/*tab切换*/

	function IFrameResize(){
		getHtmlLoadingBeforeHeight();
		getHtmlLoadingAfterHeight();
	} 
	var beginValue=$("#ftel").val();
	var c_time = 900;
	var t_time;
	/*********************校验-提示****************开始******************************/
	$("#femail").focus(function(){
		$("#tipEmail").html("<p class="+'tip'+">请输入正确的邮箱地址</p>");
	});
	$("#femail").focusout(function(){
		$("td .tip").remove();
		if($("#femail").val()!=''){
			var reg=/^[a-zA-Z0-9_\-]{1,}@[a-zA-Z0-9_\-]{1,}\.[a-zA-Z0-9_\-.]{1,}$/;
			if(!(reg.test($("#femail").val()))){
			 	$("#tipEmail").html("<p class="+'warn'+">输入的邮箱格式错误！</p>");
			}
		}
	});
	$("#ffax").focus(function(){
		$("#tipFax").html("<p class="+'tip'+">请输入传真</p>");
	});
	$("#ffax").focusout(function(){
		$("td .tip").remove();
		if($("#ffax").val()!=''){
			var reg=/^((\+?[0-9]{2,4}\-[0-9]{3,4}\-)|([0-9]{3,4}\-))?([0-9]{7,8})(\-[0-9]+)?$/;
			if(!(reg.test($("#ffax").val()))){
			 	$("#tipFax").html("<p class="+'warn'+">输入的传真格式错误！(86-0577-85555555或0577-85555555或85555555)</p>");
			}
		}
	});
// 	$("#fphone").focus(function(){
// 		$("#tipPhone").html("<p class="+'tip'+">请输入电话</p>");
// 	});
// 	$("#fphone").focusout(function(){
// 		$("td .tip").remove();
// 		if($("#fphone").val()!=''){
// 			var reg=/^((\+?[0-9]{2,4}\-[0-9]{3,4}\-)|([0-9]{3,4}\-))?([0-9]{7,8})(\-[0-9]+)?$/;
// 			if(!(reg.test($("#fphone").val()))){
// 			 	$("#tipPhone").html("<p class="+'warn'+">输入的电话格式错误！</p>");
// 			}
// 		}
// 	});
	
	$("#identCode").focus(function(){
		$("#tipIdentCode").html("<p class="+'tip'+">请输入6为验证码</p>");
	});
	$("#identCode").focusout(function(){
		$("td .tip").remove();
		var reg =/^\d{6}$/;
		if($("#identCode").val()!=''){
			if(!(reg.test($("#identCode").val()))){
				 	$("#tipIdentCode").html("<p class="+'warn'+">输入的验证码格式错误！</p>");
			}
		}
	});
	
	function changeTel(){
		 layer.open({
			    title: ['联系方式修改','background-color:#CC0000; color:#fff;'],
			    type: 2,
			    area: ['405px', '315px'],
			    content: "${ctx}/usercenter/changeTel.net"
			}); 
// 		$("#ftel").val("");
// 		$("#yzm").show(); 
	}
	
// 	$("#ftel").focus(function(){
// 		$("#tipTel").html("<p class="+'tip'+">请输入手机号码</p>");
// 	});
// 	$("#ftel").focusout(function(){
// 		$("td .tip").remove();
// 		$("#tipIdentCode").html("");
// 		if($("#ftel").val()!=''){
// 			var reg=/^1[3|4|5|8][0-9]{9}$/;
// 			if(!(reg.test($("#ftel").val()))){
// 			 	$("#tipTel").html("<p class="+'warn'+">输入的手机格式错误！</p>");
// 			}else{
// 				var afterValue = $("#ftel").val();
// 				if(afterValue!=beginValue){
// 					$.ajax({
// 							type:"POST",
// 							url:"${ctx}/user_checkFtel.net",
// 							async:false,
// 							data: {"ftel":$("#ftel").val(),"fname":$("#fname").val()}, 
// 							dataType:"text", 
// 							success:function(response){
// 								if(response=="fail"){
// 									  $("#tipTel").html("<p class="+'warn'+">该手机号已经被注册</p>");
// 									  $("#yzm").hide();
// 								}else{
// 									  $("#yzm").show();
// 								}
// 							}
// 					});
// 				}else{
// 					 $("#yzm").hide();
// 				}
// 			}
// 		}
// 	});
	
function timedCount(){
	if(c_time>0){
		c_time--;
		$("#IdentCode").val("请在" + c_time + "秒内输入");
		t_time=setTimeout("timedCount()",1000);
	}else{
		clearTimeout(t_time);
		$("#IdentCode").removeAttr("disabled");
        $("#IdentCode").val("重新发送验证码");
	}
}

function getYzm(){
			c_time =900;
			$("#IdentCode").val("正在发送中");
			$("#IdentCode").attr("disabled", "true");
	    	$.ajax({
					type:"POST",
					url:"${ctx}/user_getMobileCode.net",
					async:false,
					data: {"fname":"${TSysUser.fname}","ftel":$("#ftel").val()}, 
					dataType:"text", 
					success:function(response){
						if(response=="fail"){
							  c_time =900;
				    		  clearTimeout(t_time);
			    		 	  $("#IdentCode").removeAttr("disabled");
		               		  $("#IdentCode").val("重新发送验证码");
						}else{
							  timedCount();
						}
					}
			});
}

function valiCheckInfo(){
	/* if($("#fphone").val()!=''){
		var reg=/^((\+?[0-9]{2,4}\-[0-9]{3,4}\-)|([0-9]{3,4}\-))?([0-9]{7,8})(\-[0-9]+)?$/;
		if(!(reg.test($("#fphone").val()))){
		 	$("#tipPhone").html("<p class="+'warn'+">输入的电话格式错误！</p>");
		 	return false;
		}
	 } */
	 if($("#ffax").val()!=''){
		var reg=/^((\+?[0-9]{2,4}\-[0-9]{3,4}\-)|([0-9]{3,4}\-))?([0-9]{7,8})(\-[0-9]+)?$/;
		if(!(reg.test($("#ffax").val()))){
		 	$("#tipFax").html("<p class="+'warn'+">输入的传真格式错误！(86-0577-85555555或0577-85555555或85555555)</p>");
		 	return false;
		}
	 }
	 if($("#femail").val()!=''){
		var reg=/^[a-zA-Z0-9_\-]{1,}@[a-zA-Z0-9_\-]{1,}\.[a-zA-Z0-9_\-.]{1,}$/;
		if(!(reg.test($("#femail").val()))){
		 	$("#tipEmail").html("<p class="+'warn'+">输入的邮箱格式错误！</p>");
		 	return false;
		}
	 }
	if($("#ftel").val()==''){
		$("#tipTel").html("<p class="+'tip'+">手机号码不能为空</p>");
		return false;
	}
// 	else{
// 		if($("#ftel").val()!=beginValue){
// 			var ispass;
// 			$.ajax({
// 					type:"POST",
// 					url:"${ctx}/user_checkFtel.net",
// 					async:false,
// 					data: {"ftel":$("#ftel").val()}, 
// 					dataType:"text", 
// 					success:function(response){
// 						if(response=="fail"){
// 							  $("#tipTel").html("<p class="+'warn'+">该手机号已经被注册</p>");
// 							  ispass= false;
// 						}else{
// 							if($("#identCode").val()!=''){
// 							  		var reg =/^\d{6}$/;
// 									if(!(reg.test($("#identCode").val()))){
// 										 $("#tipIdentCode").html("<p class="+'warn'+">输入的验证码格式错误！</p>");
// 										 ispass= false;
// 									}else if(c_time <=0){
// 							    		$("#tipIdentCode").html("<p class="+'warn'+">验证码超时,请重新发送</p>");
// 							    		$("#identCode").val("");
// 							    		 clearTimeout(t_time);
// 						    		 	 $("#IdentCode").removeAttr("disabled");
// 					               		 $("#IdentCode").val("重新发送验证码");
// 							    		ispass= false;
// 							    	}else{
// 										ispass= true;
// 									}
// 							}else{
// 								$("#tipIdentCode").html("<p class="+'warn'+">请输入验证码！</p>");
// 							  	 ispass= false;
// 							}
// 						}
// 					}
// 			});
// 			return ispass;
// 		}else{
// 			return true;
// 		}
// 	}
 	return true;
}

$("#saveButton").click(function() {
	 if(valiCheckInfo()==true){
	 	var options = {
                   url : "${ctx}/usercenter/saveUserInfo.net",
                   dataType:"json",
                   type : "POST",
                   success : function(msg) {
                       if(msg.success == "success"){
                    	   parent.layer.alert('操作成功！', function(alIndex){
                       		parent.layer.close(alIndex);
                       	    /* $('#certificate', parent.document).addClass("selected");
                       	    $('#certificate', parent.document).siblings().removeClass("selected");
                       	    location.href="${ctx}/usercenter/certificate.net"; */
                       	    });
                       }else{
	                       	$("#tipIdentCode").html("<p class="+'warn'+">输入验证码错误!</p>");
	                       }
                   },
                   error:function(){
                	   parent.layer.alert('操作失败！', function(alIndex){parent.layer.close(alIndex);});
                   }
        };  
        $("#user_info").ajaxSubmit(options);//绑定页面中form表单的id
	 }
 });
 // 2015-11-19  如果页面text等文本设置了只读属性，阻止默认退格事件，防止页面后退
$(document).keydown(function(e){ 
    var keyEvent; 
    if(e.keyCode==8){ 
        var d=e.srcElement||e.target; 
        if(d.tagName.toUpperCase()=='INPUT'||d.tagName.toUpperCase()=='TEXTAREA'){ 
            keyEvent=d.readOnly||d.disabled; 
        }else{ 
            keyEvent=true; 
        } 
    }else{ 
        keyEvent=false; 
    } 
    if(keyEvent){ 
        e.preventDefault(); 
    } 
});
 
 
 function saveSupplierInfo(){
	 var postdata = $("#supplierInfo").serialize() + "&fdescription=" + $("#supplierInfo_fdescription").val();
	 $.ajax({
			type : "POST",
			url : "${ctx}/usercenter/saveSupplier.net",
			dataType:"text",
			async:false,
			data: postdata,
			success : function(response) {
				if(response =="success"){
					layer.alert('保存成功', function(index){layer.close(index);});
				}else{
					layer.alert('提交失败！', function(index){layer.close(index);});
				}
			},
			error:function (msg){
				layer.alert(msg, function(index){layer.close(index);});
			}
		});
 }
</script>
</body>
</html>
