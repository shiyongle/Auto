<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务员好运券管理</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/datagrid-detailview.js" type="text/javascript" ></script>
<script src="${ctx}/pages/pc/js/messageTips.js" type="text/javascript" ></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#searchCouponsSalesTableButton").click(function() {
		$("#CLCouponsSalesTable").datagrid('load', JSON.parse(toJOSNStr($("#searchCouponsSalesForm").serializeArray())));
	});
	
	$("#CLCouponsSalesTable").datagrid({
		title : '业务员好运券管理',
		loadMsg : '数据装载中......',
		url:'${ctx}/couponsSales/load.do',
		fit:true,
		fitColumns:false,
		nowrap : true,
		striped : true,    //为true,显示斑马线效果。
		multiSort : true,  //定义是否允许多列排序
		remoteSort : true, //定义从服务器对数据进行排序
		pagination : true, //为true,则底部显示分页工具栏
		rownumbers : true, //为true,则显示一个行号列
		sortable : true,
		showFooter : true,
		frozenColumns : [[{field : 'id1',checkbox : true}]],
		pageSize : 20,//每页显示的记录条数，默认为10 
		pageList : [ 10, 20, 50, 100 ],//可以设置每页记录条数的列表 
		toolbar : [{
					id:"NEWCouponsSalesButton",
					text : '新建业务好运券',
					iconCls : 'icon-add',
					handler : function() {
						 	$("#createCouponsSalesWindow").window({
								title : "新建指派业务员好运券",
								href : '${ctx}/couponsSales/create.do?',
								width : 500,
							    height :320,
							    draggable:true,
								resizable:false,
								maximizable:false,
								minimizable:false,
								collapsible:false,
								inline:false,
								modal : true
							});
				    }
				  },"-",{
								id : "exportExecl",
								text : '导出',
								iconCls : 'icon-excel',
								handler : function() {
									$("#CLCouponsSalesTable").datagrid("loading");
									var rows = $("#CLCouponsSalesTable").datagrid('getSelections');
									var params = $("#searchCouponsSalesForm").serialize();
									$.ajax({
										type : "POST",
										dataType:"json",
										url : "${ctx}/couponsSales/exportExecl.do",
										data : getStrId(rows)+ "&" +params,
										success : function(response) {
											if(response.success){
												window.location.href ="${ctx}/excel/"+response.url;
												$("#CLCouponsSalesTable").datagrid("loaded");
											}else{
												$.messager.alert('提示', '操作失败');
												$("#CLCouponsSalesTable").datagrid("loaded");
											} 
										}
									});
								}
				}],
		columns : [[
					{title : 'ID',field : 'id',hidden : true,rowspan : 1,align : 'center',sortable:true},
		            {title : '优惠券类型',field : 'type',rowspan : 1,align : 'center',width : '100',formatter:applyToType},
		            {title : '批次号',field : 'batchNumber',rowspan : 1,align : 'center',width : '150'},
		            {title : '开始时间',field : 'startTimeString',rowspan : 1,align : 'center',width : '110'},
		            {title : '结束时间',field : 'endTimeString',rowspan : 1,align : 'center',width : '110'},
		            {title : '面额',field : 'dollars',rowspan : 1,align : 'center',width : '100'},
		            {title : '满N元可用',field : 'compareDollars',rowspan : 1,align : 'center',width : '100'},
		            {title : '是否有效',field : 'isEffective',rowspan : 1,align : 'center',width : '100',formatter:applyToIsEffective},
		            {title : '业务员',field : 'salesMan',rowspan : 1,align : 'center',width : '100'},
		            {title : '描述',field : 'describes',rowspan : 1,align : 'center',width : '200'},
		            //{title : '兑换码',field : 'redeemCode',rowspan : 1,align : 'center',width : '270'},
		            {title : '创建人',field : 'creatorName',rowspan : 1,align : 'center',width : '100'},
		            {title : '创建时间',field : 'createTimeString',rowspan : 1,align : 'center',width : '130'}
		           ]],
		onLoadSuccess : function(data) {
				$('#CLCouponsSalesTable').datagrid('doCellTip',{cls:{'background-color':'#FFFF99'},delay:500});   
		}
	});
	
	$("#CLCouponsSalesTable").datagrid('getPager').pagination({
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

//datagird列的格式化
function applyToType(value,rowData,rowIndex){
	if(value==1){
		return '<span style="color:red;">业务跟踪优惠券</span>';
	}
}
//datagird列的格式化
function applyToIsEffective(value,rowData,rowIndex){
	if(value==1){
		return '<span style="color:green;">有效</span>';
	}else if(value==0){
		return '<span style="color:#DDDDDD;">失效</span>';
	}
}

/***订单流水号***/
function getStrId(rows) {
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
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="bCouponsSales-left: 0px; bCouponsSales-right: 0px;">
			<div data-options="region:'north',title:'查询条件'" style="height: 100px; bCouponsSales: 0px;">
			       <form id="searchCouponsSalesForm" method="post">
						<table style="margin: 0 auto; margin-top: 20px;">
							<tr>
								<td class="m-info-title">活动时间：</td>
								<td >
									<input type="text" id="startTime3" class="datePicker MtimeInput" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime3\')}'})" name="startTime" value="" /> -
									<input type="text" id="endTime3" class="MtimeInput" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime3\')}'})" name="endTime" value="" />
								</td>
								<td class="m-info-title">是否有效：</td>
								<td>
									<select id="isEffective3" class="easyui-combobox" data-options="editable:false" style="width: 80px; height: 27px;" name="isEffective">
										<option value="">请选择</option>
										<option value="1">有效</option>
										<option value="0">失效</option>
									</select>
									<a id="searchCouponsSalesTableButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
								</td>
							</tr>
						</table>
					</form>
			</div>
			<div data-options="region:'center',title:'',bodyCls:'MnolrbbCouponsSales'"  >
				<table id="CLCouponsSalesTable"></table>
			</div>
			<div id="createCouponsSalesWindow"  style="margin: 0 auto;" ></div>
	</div>
</body>
</html>