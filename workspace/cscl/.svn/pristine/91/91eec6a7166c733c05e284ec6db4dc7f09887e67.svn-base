<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>（新）客户月账单</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	/*月份查询单选按钮事件*/
	$("#monthloadedTimeBegin,#monmthloadedTimeEnd").click(function(){
		$("input[name='month']").eq(2).prop("checked",true);
	});
	
	$("#thisMonth,#lastMonth").click(function(){
	if($("#thisMonth").is(":checked")||$("#lastMonth").is(":checked"))
		{
			$("#monthloadedTimeBegin,#monmthloadedTimeEnd").attr("disabled",true).val("");
		}
		
	})
	
	$("#otherMonth").click(function(){
		$("#monthloadedTimeBegin,#monmthloadedTimeEnd").attr("disabled",false)
	})
	
	
	
	//货主下拉框数据加载
	$("#search_cmbuser").combobox({
		url : "${ctx}/select/getByRoleId.do",
		editable : true,
		width:100,
		mode: 'local',
		valueField: 'optionId',//提交值
		textField: 'optionName',//显示值
		filter:searchItem,
		//默认选中项
//         onLoadSuccess: function () { //数据加载完毕事件
//             var data = $('#search_cmbuser').combobox('getData');
//             if (data.length > 0) {
//                 $("#search_cmbuser").combobox('select', -1);
//             }
//         }
	}).combobox('select',-1);

	$('#customerReportTable').datagrid({
		title : '客户账单明细表',
		loadMsg : '数据装载中......',
		//url:'${ctx}/report/custOrderMxList.do',
		//fit:true,
		fitColumns:false,
		nowrap : true,
		showFooter : true,//展示底部栏，显示合计
		striped : true,    //为true,显示斑马线效果。
		rownumbers : true, //为true,则显示一个行号列
		frozenColumns : [[{field : 'id1',checkbox : true}]],
		onHeaderContextMenu : gridHeaderMenu,
		onLoadSuccess : function(data){
			if(data.rows.length>0){
				var fuserPay=0;
				var fdriverEarn=0;
				for(var i=0;i<data.rows.length;i++){
					fuserPay += data.rows[i].fuserPay;
					fdriverEarn += data.rows[i].fdriverEarn;
				}
				$('#customerReportTable').datagrid('appendRow',{
					createTime : '合计',
					fuserPay:fuserPay,
					fdriverEarn:fdriverEarn				
				});	
			}
		},
		toolbar : [{
			id : "exportReportExecl",
			text : '导出',
			iconCls : 'icon-excel',
			handler : function() {
				//getExcelXML有一个JSON对象的配置，配置项看了下只有title配置，为excel文档的标题  
				//获取datagrid数据对应的excel需要的xml格式的内容  	  
	            var xmlUrl = $('#customerReportTable').datagrid('getExcelXml', { title: '客户账单明细报表' });    
	            window.location = xmlUrl;
			}
		}],
		columns : [[
					{title : 'ID',field : 'id',hidden : true,rowspan : 1,align : 'center'},
					{title : '日期',field : 'createTime',rowspan : 1,align : 'center',width : '100'},
					{title : '客户名称',field : 'custName',rowspan : 1,align : 'center',width : '200'},
					{title : '订单编号',field : 'orderNumber',rowspan : 1,align : 'center',width : '200'},
					{title : '业务员',field : 'saleMan',rowspan : 1,align : 'center',width : '100'},
					{title : '部门',field : 'saleMandept',rowspan : 1,align : 'center',width : '100'},
					{title : '区域',field : 'region',rowspan : 1,align : 'center',width : '100'},
					{title : '车牌号',field : 'carNum',rowspan : 1,align : 'center',width : '200'},
					// 货主支付，司机运费  在收支明细表里面取，freight 只是订单费用
// 					{title : '货主支付',field : 'freight',rowspan : 1,align : 'center',width : '100'},
//                  {title : '司机运费',field : 'freight',rowspan : 1,align : 'center',width : '100'},
					{title : '货主支付',field : 'fuserPay',rowspan : 1,align : 'center',width : '100'},
					{title : '司机运费',field : 'fdriverEarn',rowspan : 1,align : 'center',width : '100'},
				    //月结用户 ,在线支付的 都不统计在平台中
// 					{title : '支付方式',field : 'payMethod',rowspan : 1,align : 'center',width : '100',formatter:applyToPayType},
					{title : '备注',field : 'fremark',rowspan : 1,align : 'center',width : '200'},
		           ]]
	}).datagrid("hideColumn","id").datagrid("columnMoving");
	
	//月账单查询按钮点击事件
	$("#searchMonthReportButton").click(function() {
		//先清空，否则选择-1是还是原来的值
		$('#fuserid').val('');
	    var fuserid = $("#search_cmbuser").combobox("getValue");
	    if(fuserid && fuserid!= -1){
	    	$('#fuserid').val(fuserid);
	    }
		var mydate = new Date();
	    if($("input[name='month']:checked").val()=="thisMonth"){
	    	var month = mydate.getMonth()+1+"";
	    	month = month.length==1?"0"+month:month;
	    	$('#fstartMonth').val(mydate.getFullYear()+"-"+month);
	    	$('#fendMonth').val(mydate.getFullYear()+"-"+month);    	
	    }else if($("input[name='month']:checked").val()=="lastMonth"){
			var year=mydate.getFullYear();
			var lastMonth=mydate.getMonth();
			if(lastMonth==0)
				{
					lastMonth=12;
					year--;
				}
			lastMonth = (""+lastMonth).length==1?"0"+lastMonth:lastMonth
			
	    	$('#fstartMonth').val(year+"-"+lastMonth);
	    	$('#fendMonth').val(year+"-"+lastMonth);	    	
	    }else{
			var beginDate=$("#monthloadedTimeBegin").val();
			var endDate=$("#monmthloadedTimeEnd").val();
			if(beginDate==""||endDate=="")
				{
					$.messager.alert('提示', '查询日期不能为空！');
					return false;
				}
			$('#fstartMonth').val(beginDate);
			$('#fendMonth').val(endDate);
	    }
	    var opts = $("#customerMonthReportTable").datagrid("options");
	    opts.url = '${ctx}/report/findUserMonthBillList.do';
		$('#customerMonthReportTable').datagrid('load', JSON.parse(toJOSNStr($("#searchMonthReportForm").serializeArray())));
	});
	$('#customerMonthReportTable').datagrid({
		title : '月账单',
		loadMsg : '数据装载中......',
		//fit:true,
		singleSelect: true,
		rownumbers : true,
		fitColumns:false,
		nowrap : true,
		striped : true,    //为true,显示斑马线效果。
		//pagination : true, //为true,则底部显示分页工具栏
		rownumbers : true, //为true,则显示一个行号列
		collapsible:true,//可折叠
		columns : [[
					{title : '货主ID',field : 'fuserId',hidden : true,rowspan : 1,align : 'center',width : '100'},
					{title : '账单日期',field : 'fmonth',rowspan : 1,align : 'center',width : '100'},
					{title : '货主名称',field : 'fuserName',rowspan : 1,align : 'center',width : '100'},
					{title : '金额',field :'fsumPay',rowspan : 1,align : 'center',width : '200'},
					{title : '收款金额',field :'fsumisPay',rowspan : 1,align : 'center',width : '200'},
					{title : '未收金额',field :'fsumunPay',rowspan : 1,align : 'center',width : '200',formatter:calUnPay}
		           ]],
	    onSelect:function(rowIndex,rowData){ 
		    var opts = $("#customerReportTable").datagrid("options");
		    opts.url = '${ctx}/report/custOrderMxList.do';
			$('#customerReportTable').datagrid('load',{	
				fmonth:rowData.fmonth,
				fuserid:rowData.fuserId
			}); 
	    } 		           
		
	})

});

//未付渲染函数
function calUnPay(value,rowData,rowIndex){
	if(rowData.fsumunPay){
		return rowData.fsumPay-rowData.fsumunPay;
	}
	else{
		return rowData.fsumPay;
	}
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
<style>
.reportTop{overflow:hidden; width:100%;margin:0 auto; font-size:12px;}
</style>
<body>
<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
			
<div data-options="region:'center',title:'月账单(月结用户)'" style="height: 300px;">	
		       
<div class="reportTop">
<table style="margin: 0 0; margin-top:5px;">
<tr>
	<td class="m-info-title">货主名称:</td>
	<td>
	<select id="search_cmbuser" name="search_cmbuser" panelHeight="200">	
	</select>
	</td>
	<td><input type="radio" name="month" id="thisMonth" value="thisMonth" checked="checked" /></td>
	<td class="m-info-title">当月　</td>
	<td><input type="radio" name="month" id="lastMonth" value="lastMonth" /></td>
	<td class="m-info-title">上月　</td>
	<td><input type="radio" name="month" id="otherMonth" value="otherMonth" /></td>
	<td class="m-info-title">其他月份查询:</td>
	<td>
	<input type="text" disabled="disabled" id="monthloadedTimeBegin" class="datePicker MtimeInput"  onclick="WdatePicker({dateFmt:'yyyy-MM'})" name="monthloadedTimeBegin"  /> -
	<input type="text" disabled="disabled" id="monmthloadedTimeEnd" class="MtimeInput" onclick="WdatePicker({dateFmt:'yyyy-MM'})" name="monthloadedTimeEnd"  />
	<a id="searchMonthReportButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
	</td>
</tr>
</table>
<form id="searchMonthReportForm" method="post">
	<input type="hidden" id="fstartMonth" name="fstartMonth"/>
	<input type="hidden" id="fendMonth" name="fendMonth"/>
	<input type="hidden" id="fuserid" name="fuserid"/>
</form>
<table id="customerMonthReportTable" style="border:0px;height:200px;overflow-y: scroll;"></table>
<table id="customerReportTable" style="border:0px"></table>
</div>

</div>		

</div>

</body>
</html>