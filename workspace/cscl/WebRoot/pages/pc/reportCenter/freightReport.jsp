<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务员司机运费报表</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#freightReportTable').datagrid({
		title : '业务员司机运费报表',
		loadMsg : '数据装载中......',
// 		url:'${ctx}/report/findDriverFreightList.do',
		fit:true,
		fitColumns:false,
		nowrap : true,
		striped : true,    //为true,显示斑马线效果。
		multiSort : true,  //定义是否允许多列排序
		remoteSort : true, //定义从服务器对数据进行排序
		pagination : true, //为true,则底部显示分页工具栏
		rownumbers : true, //为true,则显示一个行号列
		frozenColumns : [[{field : 'id1',checkbox : true}]],
		pageSize : 20,//每页显示的记录条数，默认为10 
		pageList : [ 10, 20, 50, 100 ],//可以设置每页记录条数的列表 
		toolbar :[{
			id : "exportReportExecl",
			text : '导出',
			iconCls : 'icon-excel',
			handler : function() {
	            var xmlUrl = $('#freightReportTable').datagrid('getExcelXml', { title: '司机运费报表' });    
	            window.location = xmlUrl;
			}
		}],
		columns : [[				
				{title : '日期',field : 'fcreateTime',rowspan : 1,align : 'center',width : '100'},
		        {title : '司机名称',field : 'fdriver',rowspan : 1,align : 'center',width : '120'},
		        {title : '车牌号',field : 'fcarNum',rowspan : 1,align : 'center',width : '100'},
		        {title : '金额',field : 'freight',rowspan : 1,align : 'center',width : '100'},
		        {title : '类型',field : 'fbusinessType',rowspan : 1,align : 'center',width : '150',formatter:fmt_businessType},
		        ]]
	});
	$('#freightReportTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
	/*牌照下拉框combogrid*/
   $("#search_fuserid").combogrid({
			panelWidth: 500,
			panelHeight: 300,
			idField: 'id',//提交值
			textField: 'carNum',//选中显示的值
			url: '${ctx}/report/mxCustOrderCarList.do',
			method: 'get',
			multiple: false,//可选择多行
			mode: 'local',
			filter: function(q, row){
 				var opts = $(this).combogrid('options');
 				if(row.driverName.indexOf(q)==0){
 					return true;
 				}
 				if(row[opts.textField]){
				return row[opts.textField].indexOf(q) == 0; 					
 				}
			},
			columns: [[
				{field:'id',title:'id',width:80,align:'center',hidden : true},
				{field:'driverName',title:'司机',width:80,align:'center'},
				{field:'carNum',title:'车牌号',width:120,align:'center'}
			]],
			fitColumns: true//true表示所有列长度会适应panelWidth的长
		});
	
	
	//用户管理查询按钮点击事件
	$("#searchfreightReportButton").click(function() {
		var beginDate=$("#fstarttime").val();
		var endDate=$("#fendtime").val();
		if(beginDate==""||endDate=="")
		{
				$.messager.alert('提示', '查询日期不能为空！');
				return false;
		}		
	    var opts = $("#freightReportTable").datagrid("options");
	    opts.url = '${ctx}/report/findDriverFreightList.do';
		$('#freightReportTable').datagrid('load', JSON.parse(toJOSNStr($("#searchFreightReportForm").serializeArray())));
		
	});
	
	//赋默认值
	$("#fstarttime").val(formatDate(new Date()));
	$("#fendtime").val(formatDate(new Date()));
	
	//费用类型渲染函数
	function fmt_businessType (value,rowData,rowIndex){
		if(value==4){
			return '<span style="color:blue;font-weight:bold;">订单完成</span>';
		}
		else if(value==3){
			return '<span style="color:red;font-weight:bold;">运营异常</span>';
		}
		else if(value==2){
			return '<span style="color:red;font-weight:bold;">补交货款</span>';
		}
	}
});

// js格式化日期
function formatDate(date, format) {   
    if (!date) return;   
    if (!format) format = "yyyy-MM-dd";   
    switch(typeof date) {   
        case "string":   
            date = new Date(date.replace(/-/, "/"));   
            break;   
        case "number":   
            date = new Date(date);   
            break;   
    }    
    if (!date instanceof Date) return;   
    var dict = {   
        "yyyy": date.getFullYear(),   
        "M": date.getMonth() + 1,   
        "d": date.getDate(),   
        "H": date.getHours(),   
        "m": date.getMinutes(),   
        "s": date.getSeconds(),   
        "MM": ("" + (date.getMonth() + 101)).substr(1),   
        "dd": ("" + (date.getDate() + 100)).substr(1),   
        "HH": ("" + (date.getHours() + 100)).substr(1),   
        "mm": ("" + (date.getMinutes() + 100)).substr(1),   
        "ss": ("" + (date.getSeconds() + 100)).substr(1)   
    };       
    return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {   
        return dict[arguments[0]];   
    });                   
}  

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

</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
	
<div data-options="region:'north',title:'业务员司机运费报表查询'" style="height:100px;">		       
<div class="reportTop" style="margin-top:20px;">

<form id="searchFreightReportForm" method="post">
<table style="margin: 0 auto; margin-top:5px;">
<tr>
<td class="m-info-title">日期（运输时间）:</td>
<td>
<input type="text" id="fstarttime" class="datePicker MtimeInput"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="fstarttime"  /> -
<input type="text" id="fendtime" class="MtimeInput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'fstarttime\')}'})" name="fendtime"  />
</td>
<td class="m-info-title">司机名称:</td>
<td>
	<select  id="search_fuserid" name="fuserid" style="width:100px;">
	</select>
</td>
<td>
<a id="searchfreightReportButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
</td>
</tr>
</table>
</form>

</div>
</div>
<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'"  >
<table id="freightReportTable"></table>
</div>
		
		
</div>

</body>


</html>