<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人中心-站内信</title>
<link href="${ctx}/css/kkpager.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/js/jquery-1.8.3.min.js" type="text/javascript" language="javascript"></script>
<script src="${ctx}/js/kkpager.js" type="text/javascript" language="javascript"></script>
<style>
	*{
		margin:0px auto;
		padding:0px;
		}
	#nav{
		height:700px;
		width:1085px;
		}
	#nav_top{
		height:42px;
		width:1065px;
		line-height:42px;
		font-family:"微软雅黑";
		font-size:15px;
		color:#666;
		padding-left:20px;
		margin-top:10px;
		}
	#nav_top select{
		width:160px;
		height:25px;
		text-align:center;
		border:1px solid lightgray;
		background:none;
		}
	#nav_top input[type=button]{
		width:85px;
		height:42px;
		font-family:"微软雅黑";
		font-size:15px;
		color:#666;
		background:none;
		border:none;
		}
	#nav_content table{
		font-family:"微软雅黑";
		font-size:15px;
		color:#444;
		text-align:center;
		border-collapse:collapse;
		}
	#nav_content table input[type=checkbox]{
		width:20px;
		height:20px;
		}
	#nav_content table .tr1{
		height:40px;
		background-color:#f2f2f2;
		color:#666;
		}
	#nav_content .title1{
		text-decoration:none;
		}
	#nav_content .setting{
		text-decoration:none;
		color:#C00;
		}
	#kkpager{
		width:1030px;
		height:80px;
		text-align:right;
		line-height:80px;
		padding-right:50px;
	}
</style>
</head>

<body>
	<div id="nav">
    	<div id="nav_top">
        	类别:<select>
            		<option>--请选择--</option>
                    <option>--请选择--</option>
                    <option>--请选择--</option>
                </select>
            <input type="button" value="标记已读" onclick="signRead();"/>
            <input type="button" value="删除选中"/>
        </div>
        <div id="nav_content">
        	<table cellpadding="0" cellspacing="0" width="1085">
            	<tr class="tr1">
                	<td width="45"><input type="checkbox"/></td>
                    <td width="300">主题</td>
                    <td width="170">发件人</td>
                    <td width="150">时间</td>
                    <td width="150">类别</td>
                    <td>状态</td>
                    <td width="140">操作</td>
                </tr>
                <tr style="border-bottom:1px solid lightgray;" height="60">
                	<td><input type="checkbox"/></td>
                    <td><a href="javascript:void(0);" target="_parent" class="title1">平台好礼大放送</a></td>
                    <td>CPS平台</td>
                    <td>2015-09-16</td>
                    <td>平台公告</td>
                    <td><img src="${ctx}/css/images/on.png"/></td>
                    <td><a href="javascript:void(0);" class="setting">删除</a></td>
                </tr>
                <tr style="border-bottom:1px solid lightgray;" height="60">
                	<td><input type="checkbox"/></td>
                    <td><a href="javascript:void(0);" target="_parent" class="title1">平台好礼大放送</a></td>
                    <td>CPS平台</td>
                    <td>2015-09-16</td>
                    <td>平台公告</td>
                    <td><img src="${ctx}/css/images/on.png"/></td>
                    <td><a href="javascript:void(0);" class="setting">删除</a></td>
                </tr>
                <tr style="border-bottom:1px solid lightgray;" height="60">
                	<td><input type="checkbox"/></td>
                    <td><a href="javascript:void(0);" target="_parent" class="title1">平台好礼大放送</a></td>
                    <td>CPS平台</td>
                    <td>2015-09-16</td>
                    <td>平台公告</td>
                    <td><img src="${ctx}/css/images/on.png"/></td>
                    <td><a href="javascript:void(0);" class="setting">删除</a></td>
                </tr>
                <tr style="border-bottom:1px solid lightgray;" height="60">
                	<td><input type="checkbox"/></td>
                    <td><a href="javascript:void(0);" target="_parent" class="title1">平台好礼大放送</a></td>
                    <td>CPS平台</td>
                    <td>2015-09-16</td>
                    <td>平台公告</td>
                    <td><img src="${ctx}/css/images/on.png"/></td>
                    <td><a href="javascript:void(0);" class="setting">删除</a></td>
                </tr>
                <tr style="border-bottom:1px solid lightgray;" height="60">
                	<td><input type="checkbox"/></td>
                    <td><a href="javascript:void(0);" target="_parent" class="title1">平台好礼大放送</a></td>
                    <td>CPS平台</td>
                    <td>2015-09-16</td>
                    <td>平台公告</td>
                    <td><img src="${ctx}/css/images/off.png"/></td>
                    <td><a href="javascript:void(0);" class="setting">删除</a></td>
                </tr>
            </table>
        </div>
         <!--翻页组件-->
        <div id="kkpager"></div>
    </div>
<script language="javascript">
//标记已读
function signRead(){
	var $num=$("input:checked").parent().siblings().eq(4).children();
	$num.attr("src","${ctx}/css/images/on.png");
	}
//init
function getParameter(name) { 
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r!=null) return unescape(r[2]); return null;
	}
$(function(){
	var totalPage = 20;
	var totalRecords = 390;
	var pageNo = getParameter('pno');
	if(!pageNo){
		pageNo = 1;
	}
	//生成分页
	//有些参数是可选的，比如lang，若不传有默认值
	kkpager.generPageHtml({
		pno : pageNo,
		//总页码
		total : totalPage,
		//总数据条数
		totalRecords : totalRecords,
		//链接前部
		hrefFormer : 'pager_test',
		//链接尾部
		hrefLatter : '.html',
		getLink : function(n){
			//这里的点击标签跳转的action。return this.hrefFormer + this.hrefLatter + "?pno="+n;
			return "?pno="+n;
		},
		lang : {
			prePageText : '上一页',
			nextPageText : '下一页',
			totalPageBeforeText : '共',
			totalPageAfterText : '页',
			totalRecordsAfterText : '条数据',
			gopageBeforeText : '转到',
			gopageButtonOkText : '确定',
			gopageAfterText : '页',
			buttonTipBeforeText : '第',
			buttonTipAfterText : '页'
		}
		/*,
		mode : 'click',//默认值是link，可选link或者click
		click : function(n){
			this.selectPage(n);
		  return false;
		}*/
	});
});
</script>
</body>
</html>
