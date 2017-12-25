<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>备货订单</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/productRecord_list.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/kkpager.css"  />
 <%-- <link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.selectlist.css">--%>
<link type="text/css" rel="stylesheet" href="${ctx}/js/jqwidgets-ver3.9.1/jqx.base.css" />
<script type="text/javascript" src="${ctx}/js/_common.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/pages/productdef/js/productRecord_list.js"></script>
 <%--<script src="${ctx}/js/jquery.selectlist.js" type="text/javascript" language="javascript"></script>--%>
 <script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxcore.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxscrollbar.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxbuttons.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxlistbox.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxcombobox.js" ></script> 
 <script type="text/javascript" language="javascript" src="${ctx}/js/kkpager.js"></script>
<style>
.jqx-fill-state-normal{ background: 0; }
.jqx-combobox-input{line-height:27px;}
.execlskin{border-radius:15px;}
</style>
</head>
<body style="min-height:300px;">
	<div id="nav">
    	<p id="p_title">平台首页&nbsp;&gt;&nbsp;产品档案</p>
        <form  method="post" class="condition_form" onsubmit="return false;">
         	<div class="selectdiv">
        	<span class="_txt">选择客户:</span>
            <select id="selectList"  name="myProductDefQuery.fcustomerid">
            	 <c:forEach var="customer" items="${customer}">
			   <option value="${customer.fid}">${customer.fname}</option>
			</c:forEach>
            </select>
             </div>
           <input type="submit" value="搜索" class="_submit" />
            <input type="text" class="_key" name="myProductDefQuery.searchKey"/>
        </form>
        <table cellpadding="0" cellspacing="0" border="0" width="1045" class="tbl">
        	<tr class="firstTr">
            	<td width="67"><input type="checkbox" id="product"/></td>
                <td width="158"><p style="height:22px;width:156px;border-left:1px solid lightgray;border-right:1px solid lightgray;line-height:22px;">附件信息</p></td>
                <td width="169">名称</td>
                <td width="188"><p style="height:22px;width:186px;border-left:1px solid lightgray;border-right:1px solid lightgray;line-height:22px;">规格</p></td>
                <td width="168">材料</td>
                <td width="160"><p style="height:22px;width:160px;border-left:1px solid lightgray;border-right:1px solid lightgray;line-height:22px;">备注</p></td>
            	<td width="125">操作</td>
            </tr>
            <tr height="45" class="btngroup">
            	<td colspan="7">
                	<input type="button" value="新建" class="_add"/>
                    <input type="button" value="删除" class="_del"/>
                    <input type="button" value="设置交期" class="_setting" onclick="set_DeliverTimeConfig()"/>
                	<input type="button" value="导入产品" class="_excel"/>
                </td>
            </tr>
            <!--两行显示一条数据-->
          <%--   <tr class="data_title">
            	<td colspan="7">
                	<span class="span1">客户:大白集团</span>
                    <span class="span2">创建时间:2015-09-25</span>
                    <span class="span3">详情</span>
                    <span class="span4">&nbsp;</span>
                </td>
            </tr>
            <tr class="_data"> 
            	<td><input type="checkbox" name="product"/></td>
                <td><img src="${ctx}/css/images/xz.png" height="87" width="87"/></td>
                <td>手机包装</td>
                <td>12×11×15</td>
                <td>瓦楞纸</td>
                <td>阿卡丽的哈库拉；阿萨德考虑；啊打卡了；大事件开打开打屎壳郎；大屎壳郎；打算的抵抗力；暗示健康了大屎壳郎答对了；的健康拉屎kashadad考虑；按时打卡了的哈市大屎壳郎；代打</td>
           	   <td class="buttonTR">
				<a id="_edit" data-fid="" onclick="">修改<br/></a>
<!-- 					<a id="_edit" data-fid="" onclick="">删除<br/></a>
 -->				</td>
            </tr> --%>
        </table>
        <div id="kkpager"></div>
    </div>

</body>
</html>
