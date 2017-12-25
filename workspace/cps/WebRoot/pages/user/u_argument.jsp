<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人中心-用户参数</title>
<script src="${ctx}/js/jquery-1.8.3.min.js" type="text/javascript" language="javascript"></script>
<style>
	*{
		margin:0px auto;
		padding:0px;
		border-collapse:collapse;
		}
	#nav{
		width:1085px;
		height:700px;
		}
	#nav_top{
		height:40px;
		width:1085px;
		line-height:40px;
		text-align:center;
		background-color:#f2f2f2;
		font-family:"微软雅黑";
		font-size:15px;
		color:#666;
		}
	.p1,.p2{
		float:left;
		}
	.p1{
		width:190px;
		}
	.p2{
		width:280px;
		}
	.td1{
		width:148px;
		height:60px;
		text-align:right;
		font-family:"微软雅黑";
		font-size:15px;
		}
	table input[type=button]{
		height:35px;
		width:110px;
		border:1px solid #666;
		background-color:white;
		font-family:"微软雅黑";
		font-size:14px;
		}
	form table ._color{
		background-color:#C00;
		color:white;
		border:none;
		}
	._submit{
		width:110px;
		height:35px;
		border:none;
		background-color:#C00;
		color:white;
		font-family:"微软雅黑";
		font-size:18px;
		letter-spacing:5px;
		}
</style>
</head>

<body>
	<div id="nav">
    	<div id="nav_top"><p class="p1">名称</p><p class="p2">值</p></div>
    	<form action="#" method="post">
        	<table cellpadding="0" cellspacing="0" width="1085">
                <tr>
                	<td class="td1">下单方式：</td>
                    <td>
                    	<input type="button" value="单款新增" class="_color"/>
                        <input type="button" value="批量新增"/>
                        <input type="button" value="批量导入"/>
                    </td>
                </tr>
                <tr>
                	<td class="td1">启用安全控件：</td>
                    <td>
                    	<input type="button" value="启用" class="_color"/>
                        <input type="button" value="禁用"/>
                    </td>
                </tr>
                <tr>
                	<td class="td1">打印模板模式：</td>
                    <td>
                    	<input type="button" value="二等分" class="_color"/>
                        <input type="button" value="三等分"/>
                        <input type="button" value="A4打印"/>
                    </td>
                </tr>
                <tr height="100">
                	<td>&nbsp;</td>
                    <td><input type="submit" value="确认" class="_submit"/></td>
                </tr>
            </table>
        </form>
    </div>
<script type="text/javascript">
	$("input[type=button]").click(function(){
			$(this).addClass("_color");
			$(this).siblings().removeClass("_color");
		});
</script>
</body>
</html>
