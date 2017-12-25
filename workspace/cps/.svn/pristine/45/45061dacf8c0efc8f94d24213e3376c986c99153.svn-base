<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户参数</title>
<link rel="stylesheet" href="${ctx}/css/userParam.css" />
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<script type="text/javascript">
	$(function(){
		getHtmlLoadingBeforeHeight();
		getHtmlLoadingAfterHeight();
		if('${cardAutoReceiveCfg}'==1){
			$('input[name=fuser][value=1]').click();
		}
		if('${sendMessageCfg}'==1){
			$('input[name=send_message]').click();
		}
		$('input[name=fuser]').change(function(){
			$.ajax({
				url: '${ctx}/usercenter/saveCardAutoReceiveCfg.net',
				data: {value: this.value},
				success: function(data){
					if('success' == data){
						layer.msg('操作成功！');
					}else{
						layer.msg(data);
					}
				}
			});
		});
		$('input[name=send_message]').change(function(){
			$.ajax({
				url: '${ctx}/usercenter/saveUserCfg.net',
				data: {key:this.name,value: Number(this.checked)},
				success: function(data){
					if('success' == data){
						layer.msg('操作成功！');
					}else{
						layer.msg(data);
					}
				}
			});
		});
	});
</script>
</head>
<body>
	<div id="header">
    	<p>
        	<a href="#">平台首页</a> &gt; <a href="#">我的业务</a> &gt; <a href="#">纸板接单</a> &gt; <a href="#">详情</a>
        </p>
    </div>
    <div id="container">
    	<div class="item">
    		<span>纸箱接单：</span>
    		<label><input type="radio" name="fuser" value="0" checked="checked"/> 手动接收</label>
    		<label><input type="radio" name="fuser" value="1"/> 自动接收</label>
    	</div>
    	 <div class="item">
    		<span>新单提醒：</span>
    		<label><input type="checkbox" name="send_message" value="1" /> 接收短信提醒</label>
    	</div> 
    </div>
</body>
</html>