<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>转介绍报表</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	//用户搜索框的下拉列表
	$("#searchInviter").combobox({
		url : "${ctx}/select/getAll.do",
		editable : true,
		width:100,
		mode: 'local',
		valueField: 'optionId',//提交值
		textField: 'optionName', //显示值 
		filter:searchItem
	});
 
	//查询按钮点击事件
	$("#searchInviteButton").click(function() {
		$('#inviteTable').datagrid('load', JSON.parse(toJOSNStr($("#searchInviteForm").serializeArray())));
	});
	$('#inviteTable').datagrid({
		title : '转介绍报表',
		loadMsg : '数据装载中......',
		url:'${ctx}/invite/load.do',
		fit:true,
		fitColumns:false,
		nowrap : true,
		striped : true,    //为true,显示斑马线效果。
		multiSort : true,  //定义是否允许多列排序
		remoteSort : true, //定义从服务器对数据进行排序
// 		pagination : true, //为true,则底部显示分页工具栏
		rownumbers : true, //为true,则显示一个行号列
		frozenColumns : [[{field : 'id1',checkbox : true}]],
// 		pageSize : 20,//每页显示的记录条数，默认为10 
// 		pageList : [ 10, 20, 50, 100 ],//可以设置每页记录条数的列表 
// 		toolbar : [],
		toolbar : [{
			id : "exportReportExecl",
			text : '导出',
			iconCls : 'icon-excel',
			handler : function() {
				//getExcelXML有一个JSON对象的配置，配置项看了下只有title配置，为excel文档的标题  
				//获取datagrid数据对应的excel需要的xml格式的内容  	  
	            var xmlUrl = $('#inviteTable').datagrid('getExcelXml', { title: '转介绍报表' });    
	            window.location = xmlUrl;
			}
		}],
		columns : [[
					{title : 'ID',field : 'fid',hidden : true,rowspan : 1,align : 'center',sortable:true},
					{title : 'RoleId',field : 'fuserroleid',hidden : true,rowspan : 1,align : 'center'},
// 					{title : 'AppType',field : 'fapptype',hidden : true,rowspan : 1,align : 'center'},
		            {title : '被推荐人',field : 'finviteename',rowspan : 1,align : 'center',width : '150'},
		            {title : '推荐人',field : 'fusername',rowspan : 1,align : 'center',width : '150'},
		            {title : '日期',field : 'fcreatetimeString',rowspan : 1,align : 'center',width : '150',formatter:localTime},
		            {title : '是否发放奖励',field : 'fbonus',rowspan : 1,align : 'center',width : '100',formatter:applyToType}
		           ]],
	});
	$('#inviteTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
});

//表单信息转JSON
function toJOSNStr(jObject) {
	var results = '{';
	jQuery.each(jObject, function(i, field) {
		if (i == 0) {
			results += '"' + field.name + '":"' + field.value + '"';
		} else {
			results += ',"' + field.name + '":"' + field.value + '"';
		}
	});
	return results + '}';
}
//批量删除
function toStrId(rows) {
		var paramStr = '';
		for ( var i = 0; i < rows.length; i++) {
			if (i == 0) {
				paramStr += 'ids=' + rows[i].id;
			} else {
				paramStr += "&ids=" + rows[i].id;
			}
		}
		return paramStr;
}

/*是否发放奖励判断*/
function applyToType(value,rowData,rowIndex){
	if(value==0){
		return '否';
	}else {
		return '是';
	}
}
 
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
			<div data-options="region:'north',title:'查询条件'" style="height: 100px; border: 0px;">
			       <form id="searchInviteForm" method="post">
						 <table style="margin: 0 auto; margin-top:15px;">
							<tr>
								<td class="m-info-title">日期：</td>
								<td>
									<input type="text" id="createTimeBegin" style="margin-top:5px;" class="datePicker MtimeInput"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'createTimeEnd\')}'})" name="createTimeBegin"  /> -
									<input type="text" id="createTimeEnd" style="margin-top:5px" class="MtimeInput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'createTimeBegin\')}'})" name="createTimeEnd"  />
								</td>
								<td class="m-info-title">推荐人:</td>
								<td>
								     <select  id="searchInviter"  name="fuserroleid" panelHeight="200" style="width: 100px; height: 20px;" >
										<option value="-1">请选择</option>
									</select>
								</td>
								<td class="m-info-title">奖励发放:</td>
								<td>
								     <select id="searchReward"  name="isReward"  style="width: 100px; height: 20px;" data-options="editable:false" >
										<option selected="selected" value="0">请选择</option>
										<option value="1">否</option>
										<option value="2">是</option>
									</select>
									<a id="searchInviteButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
								</td>
							</tr>
						</table>
					</form>
			
			</div>
			<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'"  >
				<table id="inviteTable"></table>
			</div>
			<div id="invite_Window" style="margin: 0 auto; overflow:hidden;"></div>
	</div>
</body>
</html>