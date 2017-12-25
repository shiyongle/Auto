<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单评价列表</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/messageTips.js" type="text/javascript" ></script>
<script type="text/javascript">
$(document).ready(function() {
	//订单号下拉框
// 	$("#rating_forderId").combobox({
// 		url : "",
// 		mode: 'local',
// 		width:150,
// 		valueField: 'optionId',//提交值
// 		textField: 'optionName',//显示值
// 		filter:function(q, row){
// 			var opts = $(this).combobox('options');
// 			return row[opts.textField].indexOf(q) == 0;
// 		}
// 	});
	//查询按钮点击事件
	$("#searchRatingButton").click(function() {
		$('#ratingListTable').datagrid('load', JSON.parse(toJOSNStr($("#ratingForm").serializeArray())));
	});
	$('#ratingListTable').datagrid({
		title : '订单评价',
		loadMsg : '数据装载中......',
		url:'${ctx}/rating/load.do',
		fit:true,
		fitColumns:false,
		nowrap : true,
		striped : true,    //为true,显示斑马线效果。
		multiSort : true,  //定义是否允许多列排序
		remoteSort : true, //定义从服务器对数据进行排序
		pagination : true, //为true,则底部显示分页工具栏
		rownumbers : true, //为true,则显示一个行号列
		frozenColumns : [[{field : 'id1',rowspan : 1,checkbox : true}]],
		pageSize : 20,//每页显示的记录条数，默认为10 
		pageList : [ 10, 20, 50, 100 ],//可以设置每页记录条数的列表 
		columns : [[
					{title : 'id',field : 'orderid',hidden : true,rowspan : 1},
		            {title : '订单编号',field : 'orderNum',rowspan : 1,align : 'center',width : '200'},
		            {title : '被评价人',field : 'name',rowspan : 1,align : 'center',width : '100'},
		            {title : '评价内容',field : 'remark',rowspan : 1,align : 'center',width : '100'},
		            {title : '类型',field : 'ratingType',rowspan : 1,align : 'center',width : '100',formatter:applyToType},
		            {title : '星级',field : 'ratingScore',rowspan : 1,align : 'center',width : '100'},
		           ]],
// 		          data:[
// 		                 {"fid":"1","orderNumber":"01"},
// 		                 {"fid":"2","orderNumber":"01"},
// 		                 {"fid":"3","orderNumber":"02"},
// 		                 {"fid":"4","orderNumber":"02"},
// 		                 ],
		           onLoadSuccess:function(){
		        	      //合并所有
		        	      //$(this).datagrid("autoMergeCells");
		        	      //指定列的相同行的数据合并
		        	      $(this).datagrid("autoMergeCells",['orderNumber']);
		        	      //Tip提示
		        	      $('#ratingListTable').datagrid('doCellTip',{cls:{'background-color':'#E8FFE8'},delay:500});
		        	    },
	});
	$('#ratingListTable').datagrid('getPager').pagination({
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


/*发货异常*/
function applyToType(value, rowData, rowIndex) {
		if (value == 0) {
			return '货主评价 ';
		} else if (value == 1) {
			return '车主评价';
		}
	}

</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
			<div data-options="region:'north',title:'查询条件'" style="height: 100px; border: 0px;">
			       <form id="ratingForm" method="post">
						 <table style="margin: 0 auto; margin-top: 0px;">
							<tr>
								<td class="m-info-title">订单编号:</td>
								<td>
								<input type="text" name="number"/>
<!-- 									<select id="rating_forderId" name="number"  style="width: 145px; height: 25px;" > -->
<!-- 										<option value="-1">请选择</option>																	 -->
<!-- 									</select>								 -->
									<a id="searchRatingButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
								</td>
							</tr>
						</table>
					</form>
			
			</div>
			<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'"  >
				<table id="ratingListTable"></table>
			</div>
			<div id="rating_Window" style="margin: 0 auto; overflow:hidden;"></div>
	</div>

</body>
</html>