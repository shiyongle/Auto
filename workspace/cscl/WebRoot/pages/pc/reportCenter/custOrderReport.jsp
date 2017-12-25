<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>承运订单统计表</title>
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
	
	//客户下拉框数据加载
	$("#search_customerReportId").combogrid({
		panelWidth: 250,
		panelHeight: 300,
		idField: 'id',//提交值
		textField: 'name',//选中显示的值
		url: '${ctx}/user/combogrid.do',
		method: 'get',
		multiple: false,//可选择多行
		mode: 'local',
		filter: function(q, row){
				var opts = $(this).combogrid('options');
				if(row.phone.indexOf(q)==0){
					return true;
				}
				if(row[opts.textField]){
				return row[opts.textField].indexOf(q) == 0;					
				}
		},
		columns: [[
			{field:'id',title:'id',width:80,align:'center',hidden : true},
			{field:'name',title:'用户名',width:125,align:'center'},
			{field:'phone',title:'手机号',width:125,align:'center'}
		]],
		fitColumns: true//true表示所有列长度会适应panelWidth的长
	});
	
	/*牌照下拉框combogrid*/
   $("#search_carNumberReportId").combogrid({
			panelWidth: 500,
			width:145,
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
	
   var height=$("#Mcenter").height();
	$('#customerReportTable').datagrid({
		title : '明细',
		loadMsg : '数据装载中......',
		height:height,
		//url:'${ctx}/report/custOrderOMxList.do',
		//fit:true,
		fitColumns:false,
		nowrap : true,
		showFooter : true,//展示底部栏，显示合计
		striped : true,    //为true,显示斑马线效果。
		rownumbers : true, //为true,则显示一个行号列
		frozenColumns : [[{field : 'id1',checkbox : true}]],
		onHeaderContextMenu : gridHeaderMenu,
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
		onLoadSuccess : function(data){
			if(data.rows.length>0){
				var fuserPay=0;
				var fdriverEarn=0;
				var fuserranorm=0;
				var fdranorm=0;
				for(var i=0;i<data.rows.length;i++){
					fuserPay += data.rows[i].fuserPay;
					fdriverEarn += data.rows[i].fdriverEarn;
					fuserranorm += data.rows[i].fuserranorm;
					fdranorm +=data.rows[i].fdranorm;
				}
				fuserPay = (Math.round(fuserPay*100)/100).toFixed(2);//合计保留两位小数
				fdriverEarn = (Math.round(fdriverEarn*100)/100).toFixed(2);//合计保留两位小数
				fuserranorm=(Math.round(fuserranorm*100)/100).toFixed(2);//合计保留两位小数
				fdranorm=(Math.round(fdranorm*100)/100).toFixed(2);//合计保留两位小数
				$('#customerReportTable').datagrid('appendRow',{
					createTime : '合计',
					fuserPay:fuserPay,
					fdriverEarn:fdriverEarn	,
					fdranorm:fdranorm,
					fuserranorm:fuserranorm
					
				});	
			}
		},
		columns : [[
					{title : 'ID',field : 'id',hidden : true,rowspan : 1,align : 'center'},
					{title : '日期',field : 'createTime',rowspan : 1,align : 'center',width : '100'},
					{title : '客户名称',field : 'custName',rowspan : 1,align : 'center',width : '200'},
					{title : '订单编号',field : 'orderNumber',rowspan : 1,align : 'center',width : '200'},
					{title : '业务员',field : 'saleMan',rowspan : 1,align : 'center',width : '100'},
					{title : '部门',field : 'saleMandept',rowspan : 1,align : 'center',width : '100'},
					{title : '区域',field : 'region',rowspan : 1,align : 'center',width : '100'},
					{title : '司机',field : 'driverName',rowspan : 1,align : 'center',width : '100'},	
					{title : '车牌号',field : 'carNum',rowspan : 1,align : 'center',width : '200'},					
					{title : '货主支付',field : 'fuserPay',rowspan : 1,align : 'center',width : '100',formatter:applyToDecimal},
// 					{title : '货主支付',field : 'freight',rowspan : 1,align : 'center',width : '100'},
					{title : '货主异常费用',field : 'fuserranorm',rowspan : 1,align : 'center',width : '100',formatter:applyToDecimal},
 					{title : '司机运费',field : 'fdriverEarn',rowspan : 1,align : 'center',width : '100',formatter:applyToDecimal},
 					{title : '司机异常费用',field : 'fdranorm',rowspan : 1,align : 'center',width : '100',formatter:applyToDecimal},
 					{title : '毛利额',field : 'fgrossprofit',rowspan : 1,align : 'center',width : '100',formatter:function(value,row,index){return (eval(row.fuserPay)-eval(row.fuserranorm)-eval(row.fdriverEarn)-eval(row.fdranorm)).toFixed(2);}},
 					{title : '优惠券支付',field : 'fcpDollars',rowspan : 1,align : 'center',width : '100'},
					{title : '支付方式',field : 'payMethod',rowspan : 1,align : 'center',width : '100',formatter:applyToPayType},
					{title : '备注',field : 'fremark',rowspan : 1,align : 'center',width : '200'},
		           ]]
	}).datagrid("hideColumn","id").datagrid("columnMoving");
	
	//月账单查询按钮点击事件
// 	$("#searchMonthReportButton").click(function() {
// 		var mydate = new Date();
// 	    if($("input[name='month']:checked").val()=="thisMonth"){
// 	    	var month = mydate.getMonth()+1+"";
// 	    	month = month.length==1?"0"+month:month;
// 	    	$('#fstartMonth').val(mydate.getFullYear()+"-"+month);
// 	    	$('#fendMonth').val(mydate.getFullYear()+"-"+month);    	
// 	    }else if($("input[name='month']:checked").val()=="lastMonth"){
// 			var year=mydate.getFullYear();
// 			var lastMonth=mydate.getMonth();
// 			if(lastMonth==0)
// 				{
// 					lastMonth=12;
// 					year--;
// 				}
// 			lastMonth = (""+lastMonth).length==1?"0"+lastMonth:lastMonth
			
// 	    	$('#fstartMonth').val(year+"-"+lastMonth);
// 	    	$('#fendMonth').val(year+"-"+lastMonth);	    	
// 	    }else{
// 			var beginDate=$("#monthloadedTimeBegin").val();
// 			var endDate=$("#monmthloadedTimeEnd").val();
// 			if(beginDate==""||endDate=="")
// 				{
// 					$.messager.alert('提示', '查询日期不能为空！');
// 					return false;
// 				}
// 			$('#fstartMonth').val(beginDate);
// 			$('#fendMonth').val(endDate);
// 	    }
// 	    var opts = $("#customerMonthReportTable").datagrid("options");
// 	    opts.url = '${ctx}/report/custMonthOrderList.do';
// 		$('#customerMonthReportTable').datagrid('load', JSON.parse(toJOSNStr($("#searchMonthReportForm").serializeArray())));
// 	});
// 	$('#customerMonthReportTable').datagrid({
// 		title : '月账单',
// 		loadMsg : '数据装载中......',
// 		//fit:true,
// 		singleSelect: true,
// 		rownumbers : true,
// 		fitColumns:false,
// 		nowrap : true,
// 		striped : true,    //为true,显示斑马线效果。
// 		//pagination : true, //为true,则底部显示分页工具栏
// 		rownumbers : true, //为true,则显示一个行号列
// 		columns : [[
// 					{title : '月份',field : 'fmonth',rowspan : 1,align : 'center',width : '100'},
// 					{title : '金额',field :'freight',rowspan : 1,align : 'center',width : '200'}
// 		           ]],
// 	    onSelect:function(rowIndex,rowData){ 
// 	    	//alert(1);
// 		    var opts = $("#customerReportTable").datagrid("options");
// 		    opts.url = '${ctx}/report/custOrderOMxList.do';
// 			$('#customerReportTable').datagrid('load',{
// 				fmonth:rowData.fmonth
// 			}); 
// 	    } 		           
		
// 	})
	
	
 	//明细查询按钮点击事件
	$("#searchReportButton").click(function() {
	    var opts = $("#customerReportTable").datagrid("options");
	    opts.url = '${ctx}/report/custOrderOMxList.do';
		$('#customerReportTable').datagrid(
				'load', 
				JSON.parse(toJOSNStr($("#searchReportForm").serializeArray())));
	});
//  报表不用分页了	
// 	$('#customerReportTable').datagrid('getPager').pagination({
// 		beforePageText : '第',//页数文本框前显示的汉字 
// 		afterPageText : '页    共 {pages} 页',
// 		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
// 		onBeforeRefresh : function(pageNumber, pageSize) {
// 			$(this).pagination('loading');
// 		}
// 	});
});

/***导出多条明细***/
function getOrderId(rows) {
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
//支付方式
function applyToPayType(value,rowData,rowIndex){
	if(value==0){
		return '余额';
	}else if(value==1){
		return '支付宝';
	}
	else if(value==2){
		return '微信';
	}
	else if(value==3){
		return '银联';
	}
	else if(value==4){
		return '运费到付';
	}
	else if(value==5){
		return '月结';
	}
}
//前端控制数据小数位
function applyToDecimal(value,rowData,rowIndex){
	var num=value;
	if (isNaN(num)) {  
        return;  
    }  
    num = Math.round(value*100)/100;
    num	= num.toFixed(2);
    return num;  
}

</script>
</head>
<style>
.reportTop{overflow:hidden; width:100%;margin:0 auto; font-size:12px;}
#fstarttime,#fendtime{width:180px;}
</style>
<body>
<div id="Dlg-Export_xiazai"></div>
<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
			
<!-- <div data-options="region:'center'">	 -->
	
<!-- 	<div class="easyui-layout" data-options="fit:true" style="border:0px;overfolw:hidden">		 -->
<!-- 	<div data-options="region:'north',split:true,title:'月账单'" style="height:200px;border:0;">        -->
<!-- 	<div class="reportTop"> -->
<!-- 	<table style="margin: 0 0; margin-top:5px;"> -->
<!-- <tr> -->
<!-- 	<td><input type="radio" name="month" id="thisMonth" value="thisMonth" checked="checked" /></td> -->
<!-- 	<td class="m-info-title">当月　</td> -->
<!-- 	<td><input type="radio" name="month" id="lastMonth" value="lastMonth" /></td> -->
<!-- 	<td class="m-info-title">上月　</td> -->
<!-- 	<td><input type="radio" name="month" id="otherMonth" value="otherMonth" /></td> -->
<!-- 	<td class="m-info-title">其他月份查询:</td> -->
<!-- 	<td> -->
<!-- 	<input type="text" id="monthloadedTimeBegin" class="datePicker MtimeInput"  onclick="WdatePicker({dateFmt:'yyyy-MM'})" name="monthloadedTimeBegin"  /> - -->
<!-- 	<input type="text" id="monmthloadedTimeEnd" class="MtimeInput" onclick="WdatePicker({dateFmt:'yyyy-MM'})" name="monthloadedTimeEnd"  /> -->
<!-- 	<a id="searchMonthReportButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a> -->
<!-- 	</td> -->
<!-- </tr> -->
<!-- </table> -->
<!-- <form id="searchMonthReportForm" method="post"> -->
<!-- 	<input type="hidden" id="fstartMonth" name="fstartMonth"/> -->
<!-- 	<input type="hidden" id="fendMonth" name="fendMonth"/> -->
<!-- </form> -->
<!-- <table id="customerMonthReportTable" style="border:0px"></table> -->
<!-- </div> -->
<!-- </div> -->
	<div data-options="region:'north',split:true,title:'月账单'" style="height:130px;border:0;">
				<form id="searchReportForm" method="post">
						<table style="margin: 0 auto; margin-top: 10px; margin-bottom: 5px;">
							<tr>						
								<td class="m-info-title">客户名称:</td>
								<td>
									<select id="search_customerReportId" style="width: 145px; height: 25px;" name="fuserid">
<!-- 									<option value="-1">全部</option> -->
									</select>
								</td>
								
								<td class="m-info-title">司机名称:</td>
								<td>
								<input id="fdriverName" name="fdriverName" style="width: 100px; height: 16px;"/>
								</td>
									
								<td class="m-info-title">　　业务员:</td>
								<td>
								<input id="fsaleman" name="fsaleman" style="width: 100px; height: 16px;"/>
								</td>													
								<td class="m-info-title">　部门:</td>
								<td>
								<input id="fsalesmandept" name="fsalesmandept" style="width: 100px; height: 16px;"/>
								</td>
								</tr>
								<tr>
<!-- 								<td class="m-info-title">　车牌号:</td> -->
<!-- 								<td> -->
<!-- 									<select class="easyui-combogrid" id="search_carNumberReportId" name="fcarid" style="width:100px;"> -->
<!-- 									</select> -->
<!-- 								</td> -->
								<td class="m-info-title">　区域:</td>
								<td>
								<input id="fregion" name="fregion" style="width: 145px; height: 16px;"/>
								</td>
								<td class="m-info-title">　日期:</td>
								<td colspan="5">
									<input type="text" id="fstarttime" class="datePicker MtimeInput"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="fstarttime"  /> -
									<input type="text" id="fendtime" class="MtimeInput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'fstarttime\')}'})" name="fendtime"  />
									<a id="searchReportButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
								</td>
							</tr>
						</table>
					</form>
	</div>

			<div id="Mcenter" data-options="region:'center',split:'true',title:'查询条件',bodyCls:'Mnolrbborder'" style="border:0px">
			<table id="customerReportTable" style="border:0px"></table>
			</div>
<!-- 		</div> -->
<!-- </div> -->
</div>	
</body>
</html>