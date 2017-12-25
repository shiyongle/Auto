<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>系统禁用修改</title>

</head>
<body>
<script src="${ctx}/pages/pc/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		//用户
		$("#creator").combobox({
			url : "${ctx}/select/getAll.do",
			editable : true,
			width:200,
			height:65,
			panelHeight:200,
			mode: 'local',
			valueField: 'optionId',//提交值
			textField: 'optionName',//显示值
			multiple:true ,
			multiline:true,
			delay:2000,
			filter:searchItem
		}).combobox('setValues',"${refuse.fvalues}".split(','));
		
		console.log("${refuse.ftype}")
		// 配置类型
		$('#configType').combobox({
	        width:200,
	        required : true,
	        missingMessage : "请选择配置类型！",
	        validType : "comboRequired",
	        width:200,
	        height:30,
	        editable : false
        });
        //锁定
        $('#configType').combobox("select","${refuse.ftype}");

        // 开始时间 结束时间
		$('#startTime,#endTime').validatebox({
	        required:true,
	        missingMessage:'时间必须填写！'
        });

		//警用提示语
		$('#policeTips').validatebox({
	        required:true,
	        missingMessage:'禁用提示语必须填写！',
	       	validType:['maxLength[20]']
        });
        
		//保存
		$("#save").click(function() {
			var creator = $('#creator').combobox('getValues');
			
				creator = creator.filter(function(item){
					return /^\d+$/.test(item);
				})
				
				creator = creator.join(',');
			if(creator == ''){
				$.messager.alert('提示','允许人员必须填写！','info');
				return;
			}
			if ($("#systemSettingsForm").form("validate")) {
				var params = decodeURIComponent($("#systemSettingsForm").serialize(), true);
				$.messager.confirm('提示', '确定要提交吗？', function(r){
					if(r){
						$.ajax({
							type : "POST",
							url : "${ctx}/sys/refuseUpdate.do",
							data : params+'&fvalues='+creator,
							dataType:'json',
							success : function(res) {
								if(res.success){
									$.messager.alert('提示', '操作成功', 'info', function() {
										$("#window").window("close");
										$("#systemSettingsTable").datagrid('reload');
									});
								} else {
									$.messager.alert('提示', res.msg);
								}
							}
						});
					}
				})
			}
		});
		
		//取消按钮
		$('#cancel').click(function(){
			$("#window").window("close");
		})
	});
	
	
</script>
<main>
	<form id="systemSettingsForm" action="" method="post">	
		<input type="hidden" name="fid" value="${refuse.fid}" />
		<input type="hidden" name="fcreator" value="${refuse.fcreator}" />
		<table>
			<tr>
				<td class="m-info-title">开始时间:</td>
				<td class="m-info-content">
					<input type="text" name="StartTime" value="${refuse.getFstart_timeString()}" class="easyui-validatebox datePicker MtimeInput"  style="width:200px;" id="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')}'})">
				</td>
			</tr>
			<tr>
				<td class="m-info-title">结束时间:</td>
				<td class="m-info-content">
					<input type="text"  name="EndTime" value="${refuse.getFend_timeString()}" class="easyui-validatebox MtimeInput"  id="endTime" style="width:200px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'})">
				</td>
			</tr>
			<tr>
				<td class="m-info-title">允许人员:</td>
				<td class="m-info-content">
					<select id="creator">
					</select>
				</td>
			</tr>
			<tr>
				<td class="m-info-title">配置类型:</td>
				<td class="m-info-content">
					<select name="ftype"  id ="configType">
						<option value="-1">请选择</option>
						<option value="0">停止接单</option>
						<option value="1">上线维护</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="m-info-title">提示内容:</td>
				<td class="m-info-content">
					<input type="text" id="policeTips" style="width:200px;" name="fkey" value="${refuse.fkey}">
				</td>
			</tr>
		</table>
		<div style="width:150px;margin:10px auto 0;">
			<div class="Mbutton25 createButton" id="save" align="center" style="margin-left:20px;">保存</div>
			<div class="Mbutton25 createButton" align="center" id="cancel">取消</div>
		</div>
	</form>
</main>
</body>
</html>

