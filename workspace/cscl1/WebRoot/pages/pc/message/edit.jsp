<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>修改用户</title>
<link href="${ctx}/umeditor1_2_2-utf8-jsp/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript">window.UMEDITOR_HOME_URL = "/cscl/umeditor1_2_2-utf8-jsp/";</script>
<script type="text/javascript" charset="utf-8" src="${ctx}/umeditor1_2_2-utf8-jsp/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/umeditor1_2_2-utf8-jsp/umeditor.min.js"></script>
<script type="text/javascript" src="${ctx}/umeditor1_2_2-utf8-jsp/lang/zh-cn/zh-cn.js"></script>
<script>
$(document).ready(function() {
	var um = UM.getEditor('myEditor');
	$('#type1').validatebox({ 
	    disabled:true
	});
	
// 	$("#content").validatebox({ 
// 		required:true ,
// 		missingMessage : "请输入消息内容",
// 		validType:"emptyText",
// 		invalidMessage : "消息内容必填"
// 	});
	$("#title").validatebox({ 
		required:true ,
		missingMessage : "请输入消息标题",
		validType:"emptyText",
		invalidMessage : "消息标题必填"
	});
	
	$("#saveMessageButton").click(function() {
		if ($("#createMessageForm").form("validate")) {
			var content=UM.getEditor('myEditor').getContent();
			$('#content').val(content);
			var params = decodeURIComponent($("#createMessageForm").serialize(), true);
			$("#messageWindow").mask();
			$.ajax({
				type : "POST",
				async:false,
				url : "${ctx}/message/update.do",
				data : params,
				success : function(response) {
					if(response == "success") {
						$.messager.alert('提示', '操作成功', 'info', function() {
							$("#messageWindow").window("close");
							$("#CLMessageTable").datagrid('reload');
							$("#messageWindow").mask("hide");
						});
					}else {
						$.messager.alert('提示', "操作失败！");
						$("#messageWindow").mask("hide");
					}
				}
			});
		}
	});
});
</script>
</head>
	<form id="createMessageForm" action="" method="post">
		<table  id="createMessageTable" style="line-height: 40px;">
			<tr>
				<td class="m-info-title">标题<span class="red">*</span>:</td>
				<td class="m-info-content"><input type="text" id="title" name="title" size="72" value="${message.title }" /></td>
			</tr>
			<tr>
				<td class="m-info-title">内容<span class="red">*</span>:</td>
				<td>
				<script type="text/plain" id="myEditor" style="width:800px;height:350px;">
    				${message.content}
				</script>
			<input type="hidden" id="content" name="content" />
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<input type="hidden" name="id" value="${message.id}"/>
					<div class="Mbutton25 createButton" id="saveMessageButton">保存</div>
				</td>
			</tr>
		</table>
	</form>
</html>