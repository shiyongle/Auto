<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>新增用户</title>
<link href="${ctx}/umeditor1_2_2-utf8-jsp/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript">
 window.UMEDITOR_HOME_URL = "/cscl/umeditor1_2_2-utf8-jsp/";
</script>

<script type="text/javascript" charset="utf-8" src="${ctx}/umeditor1_2_2-utf8-jsp/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/umeditor1_2_2-utf8-jsp/umeditor.min.js"></script>
<script type="text/javascript" src="${ctx}/umeditor1_2_2-utf8-jsp/lang/zh-cn/zh-cn.js"></script>

<script>
$(document).ready(function() {
	//实例化编辑器
	var um = UM.getEditor('myEditor');
	
	var typedata =[{"id":0,"text":"全部用户"},{"id":1,"text":"全部货主"},{"id":2,"text":"全部司机"}];
	$("#type").combobox({    
	    data:typedata,
	    listHeight:10,
	    width:175,
	    valueField:'id',    
	    textField:'text',
		required : true,
		missingMessage : "请选择接收人类型",
		validType : "comboRequired",
		invalidMessage : "请选择接收人类型"
	}).combobox("select","0");
	
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
	
	$('#upload').filebox({    
	    buttonText: '选择文件', 
	    buttonAlign: 'right' 
	});
	$('#imagebtn').linkbutton({
		iconCls : 'icon-search',
		onClick : function() {
			$('#uploadImg').css('display', 'block');
			$('#content').css('display', 'none');
		}
	});
	$('#contentbtn').linkbutton({
		iconCls : 'icon-edit',
		onClick : function() {
			$('#uploadImg').css('display', 'none');
			$('#content').css('display', 'block');
		}
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
				url : "${ctx}/messagesend/save.do",
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

function uploadimg()
{
	$('#uploadImg').submit();
}
 
</script>
</head>
<form id="createMessageForm" action="" method="post">
	<table id="createMessageTable" style="line-height: 40px;">
		<tr>
			<td class="m-info-title">接收人<span class="red">*</span>:
			</td>
			<td class="m-info-content"><input type="text" id=type
				name="type" data-options="editable:false,panelHeight:'auto'" /></td>
		</tr>
		<tr>
			<td class="m-info-title">标题<span class="red">*</span>:
			</td>
			<td class="m-info-content"><input type="text" id="title"
				name="title" size="77" /></td>
		</tr>
		<tr>
			<td class="m-info-title" style="float: left;">
				内容
				<span class="red">*</span>:
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
			<!--style给定宽度可以影响编辑器的最终宽度-->
			<script type="text/plain" id="myEditor" style="width:800px;height:350px;">
    			<p>这里我可以写一些输入提示</p>
			</script>
			<input type="hidden" id="content" name="content" />
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<div class="Mbutton25 createButton" id="saveMessageButton">保存</div>
			</td>
		</tr>
	</table>
</form>
<form id="uploadImg" style="display: none;left: 75px;position: absolute; top:165px;"  method="post" action="${ctx}/messagesend/savefile.do"
		enctype="multipart/form-data" target="fileUL">
<!-- 		<input type="file" name="file" id="fl" value="选择图片" onchange="$(this).parents('#uploadImg').submit();" /> -->
		<input type="file" name="file" id="fl" value="选择图片" onchange="javascript:uploadimg();"   />
            
</form>
</html>