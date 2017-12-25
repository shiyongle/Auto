<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户资料</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	//用户搜索框的下拉列表
	$("#fname").combobox({
		url : "",/*方法里应自带搜索匹配*/
		editable : true,
		width:100,
		mode: 'remote',
		valueField: 'optionId',
		textField: 'optionName',  
	});
 
	//查询按钮点击事件
	$("#searchRuleButton").click(function() {
		$('#CLCustomerTable').datagrid('load', JSON.parse(toJOSNStr($("#searchCustForm").serializeArray())));
	});
	$('#CLCustomerTable').datagrid({
		title : '客户资料',
		loadMsg : '数据装载中......',
		url:'${ctx}/usercustomer/load.do',
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
		toolbar : [{
					id:"CreateCustomer",
					text : '新建',
					iconCls : 'icon-add',
					hidden : true,
					handler : function() {
							$("#createCustomer_Window").window({
								title : "新建",
								href : '${ctx}/pages/pc/customer/create_customer.jsp',
								width : 550,
								height :300,
								draggable:false,
								resizable:false,
								maximizable:false,
								minimizable:false,
								collapsible:false,
								modal : true
						    });
				          }
				     },'-',{
							id:"EditCustomer",
							text:'修改',
							iconCls : "icon-edit",
							handler : function() {
								var rows = $("#CLCustomerTable").datagrid('getSelections');
								if(rows.length<=0){
							    	 $.messager.alert('提示', '请选择记录！', 'info');
							    }else if (rows.length > 1) {
										$.messager.alert('提示', '每次只能选择一条记录！', 'info');
								}else{
								 
							    	 $("#createCustomer_Window").window({
										title : "修改",
										href : '${ctx}/usercustomer/editCustomer.do?fid='+rows[0].fid,
										width : 550,
									    height :340,
										modal : true
									});
									}
								} 
					},{
						id : "DelCustomer",
						text : '删除',
						iconCls : 'icon-remove',
						handler : function() {
							var rows = $("#CLCustomerTable").datagrid('getSelections');
							 if(rows.length<=0){
					    	 	$.messager.alert('提示', '请选择记录！', 'info');
						     }else{
							 	$.messager.confirm('提示','确定要删除吗？',function(r){
							 		if(r){
							 			$.ajax({
								 			type : "POST",
											url : '${ctx}/car/del.do?'+toStrId(rows),
											success : function(response) {
												if (response == "success") {
							                        $.messager.alert('提示', '操作成功', 'info', function() {
							                        	$('#CLCustomerTable').datagrid('reload');
							                        });
							                    } else if(response == "fail"){
							                    	  $.messager.alert('提示', '该客户有下单记录，不可删除喵~', 'info', function() {
							                        	$('#CLCustomerTable').datagrid('reload');
							                        });
							                    }else {
							                        $.messager.alert('提示', '操作失败');
							                    }
											}	
										});
							 		}
							 	});
							 }
						}
			          },'-',{
					  		id : "exportCustomerExecl",
							text : '导出',
							iconCls : 'icon-excel',
							handler : function() {
								$("#CLCustomerTable").datagrid("loading");
								var params = $("#searchCustForm").serialize();
								$.ajax({
									type : "POST",
									dataType:"json",
									url : "${ctx}/usercustomer/exportExecl.do",
									data : params,
									success : function(response) {
										if(response.success){
											window.location.href ="${ctx}/excel/"+response.url;
											$("#CLCustomerTable").datagrid("loaded");
										}else{
											$.messager.alert('提示', '操作失败');
											$("#CLCustomerTable").datagrid("loaded");
										} 
									}
								});
							}
						}
			          ],
		columns : [[
					{title : 'ID',field : 'fid',hidden : true,rowspan : 1,align : 'center',sortable:true},
		            {title : '客户编码',field : 'fnumber',rowspan : 1,align : 'center',width : '200'},
		            {title : '客户名称',field : 'fname',rowspan : 1,align : 'center',width : '150'},
		            {title : '客户简称',field : 'fsimplename',rowspan : 1,align : 'center',width : '100'},
		            {title : '登录帐号',field : 'userName',rowspan : 1,align : 'center',width : '100'},
		            {title : '客户手机',field : 'userPhone',rowspan : 1,align : 'center',width : '100'},
		            {title : '客户类型',field : 'ftype',rowspan : 1,align : 'center',width : '100'},
		            {title : '客户所在区域',field : 'fregion',rowspan : 1,align : 'center',width : '100'},
		            {title : '业务员',field : 'fsalesMan',rowspan : 1,align : 'center',width : '100'},
		            {title : '业务员所在部门',field : 'fsalesManDept',rowspan : 1,align : 'center',width : '150'},
		            {title : '结算周期',field : 'fsettleCycle',rowspan : 1,align : 'center',width : '100'},
		            {title : '客户相关注意事项',field : 'fattention',rowspan : 1,align : 'center',width : '300'},
		            {title : '认证时间',field : 'createTimeString',rowspan : 1,align : 'center',width : '200'},
		            {title : '修改时间',field : 'auditTimeString',rowspan : 1,align : 'center',width : '200'}
		           ]],
	});
	$('#CLCustomerTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
	$("#CreateCustomer").remove();
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
				paramStr += 'cusIds=' + rows[i].fid;
			} else {
				paramStr += "&cusIds=" + rows[i].fid;
			}
		}
		return paramStr;
}
function applyToStatus(value,rowData,rowIndex){
	if(value==1){
		return '<span style="color:red; ">有效</span>';
	}else if(value==0){
		return '<span style="color:blue; " >失效</span>';
	}
}

/*客户类型显示判断*/
// function applyToType(value,rowData,rowIndex){
// 	if(value==1){
// 		return 'B2B物流';
// 	}else if(value==2){
// 		return '快递物流';
// 	}
// 	else if(value==3){
// 		return '商超';
// 	}
// }
 
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
			<div data-options="region:'north',title:'查询条件'" style="height: 100px; border: 0px;">
			       <form id="searchCustForm" method="post">
						 <table style="margin: 0 auto; margin-top: 20px;">
							<tr>
								<td class="m-info-title">客户名称/简称:</td>
								<td>
									<input id="search_fname" name="fname" style="width: 100px;"/>
								</td>
								<td class="m-info-title">登录账号:</td>
								<td>
									<input id="search_fuser" name="fuserName" style="width: 100px;"/>
								</td>
								<td class="m-info-title">业务员:</td>
								<td>
									<input id="search_fsalesman" name="fsalesMan" style="width: 100px;"/>
								</td>
								<td class="m-info-title">部门:</td>
								<td>
									<input id="search_fdepartment" name="fsalesManDept" style="width: 100px;"/>
								</td>
								<td class="m-info-title">认证时间:</td>
								<td>
									<input id="search_fstarttime" class="datePicker MtimeInput" style="width: 100px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="fcreateTimeBegin"/>-
								</td>								
								<td>
									<input id="search_fendtime" class="datePicker MtimeInput" style="width: 100px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="fcreateTimeEnd"/>
								</td>
								<td>
									<a id="searchRuleButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
								</td>
							</tr>
						</table>
					</form>
			
			</div>
			<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'"  >
				<table id="CLCustomerTable"></table>
			</div>
			<div id="createCustomer_Window" style="margin: 0 auto; overflow:hidden;"></div>
	</div>
</body>
</html>