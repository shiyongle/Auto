<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>车辆管理</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css"	type="text/css" rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"	rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js"	type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#licenseType").combobox({
			width:145,
			editable : false,
			panelHeight:"auto"
		});
		//渲染多条件查询的下拉框
		$("#carType").combobox({
			editable : true,
			width : 110,
			mode : 'remote',
			valueField : 'optionId',
			textField : 'optionName',
			filter: searchItem
		});
		//规格
		$("#carSpecId").combobox({
				url : "${ctx}/select/getAllCarType1.do",
				required : true,
				missingMessage : "全部",
				validType : "comboRequired",
				invalidMessage : "全部",
				editable : true,
				width : 110,
				mode : 'remote',
				valueField : 'optionId',
				textField : 'optionName',
				onSelect : function(record) {
					$("#carType").combobox({
						url : "${ctx}/select/getAllCarSpecByCarType.do?optionTemp="+ record.optionId,
						required : true,
						missingMessage : "全部",
						validType : "comboRequired",
						invalidMessage : "全部",
						editable : true,
						mode : 'remote',
						valueField : 'optionId',
						textField : 'optionName'
					});
				}
		});
		//读取下拉框的内容个数
		//alert($("#_easyui_combobox_i4_1").next().attr("id"));
		//查询按钮点击事件
		$("#searchCarButton").click(function() {
			$('#CLCarTable').datagrid('load', JSON.parse(toJOSNStr($("#searchCarForm").serializeArray())));
		});
	
		$('#CLCarTable').datagrid({
			title : '车辆管理',
			loadMsg : '数据装载中......',
			url : '${ctx}/car/load.do',
			fit : true,
			fitColumns : false,
			nowrap : true,
			striped : true, //为true,显示斑马线效果。
			multiSort : true, //定义是否允许多列排序
			remoteSort : true, //定义从服务器对数据进行排序
			pagination : true, //为true,则底部显示分页工具栏
			rownumbers : true, //为true,则显示一个行号列
			frozenColumns : [ [ {field : 'id1',checkbox : true}] ],
			pageSize : 20,//每页显示的记录条数，默认为10 
			pageList : [ 10, 20, 50, 100 ],//可以设置每页记录条数的列表 
			toolbar : [
			   //       {	
			  //			id : "add",
			 //		text : '新建',
			//			iconCls : 'icon-add',
			//			handler : function() {
			//				$("#createWindow").window({
			//					title : "新建",
			//					href : '${ctx}/car/add.do',
			//					width : 480,
			//					height :180,
			//					modal : true
			//				});
			//			}
			//			},'-',
						{
							id:"Edit",
							text:'修改',
							iconCls : "icon-edit",
							handler : function() {
								var rows = $("#CLCarTable").datagrid('getSelections');
							     if(rows.length<=0){
							    	 $.messager.alert('提示', '请选择记录！', 'info');
							     }else if (rows.length > 1) {
										$.messager.alert('提示', '每次只能选择一条记录！', 'info');
								 }else{
							    	 $("#createWindow").window({
										title : "修改",
										href : '${ctx}/car/edit.do?id='+rows[0].id,
										width :540,
									    height :250,
										modal : true
									});
								} 
							}
						}, '-', {
								id : "del",
								text : '删除',
								iconCls : 'icon-remove',
								handler : function() {
									var rows = $('#CLCarTable').datagrid('getSelections');
									$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
										 if(r){
												if(rows.length<=0){
											    	 	$.messager.alert('提示', '请选择记录！', 'info');
												     }
												 else{
											 			$.ajax({
											 						type : "POST",
																	url : '${ctx}/car/del.do?'+toStrId(rows),
																	success : function(response) {
																	if (response == "success") {
										                      				  $.messager.alert('提示', '操作成功', 'info', function() {
										                        					$('#CLCarTable').datagrid('reload');
										                      					  });
										                 		   } else if (response == "fail"){
										                 		   				$.messager.alert('提示','该司机有订单存在，不可删除喵~','info',function(){
										                 		   					$('#CLCarTable').datagrid('reload');
										                 		   				})
										                 		   }else {
										                        				$.messager.alert('提示', '操作失败');
										                   		 	 }
																	}	
		       													});
														 }
										     	}
								       	});
								}
			},"-",{
								id : "exportExecl",
								text : '导出',
								iconCls : 'icon-excel',
								handler : function() {
									$("#CLCarTable").datagrid("loading");
									var rows = $("#CLCarTable").datagrid('getSelections');
									var params = $("#searchCarForm").serialize();
									$.ajax({
										type : "POST",
										dataType:"json",
										url : "${ctx}/car/exportExecl.do",
										data : toStrId(rows)+ "&" +params,
										success : function(response) {
											if(response.success){
												window.location.href ="${ctx}/excel/"+response.url;
												$("#CLCarTable").datagrid("loaded");
											}else{
												$.messager.alert('提示', '操作失败');
												$("#CLCarTable").datagrid("loaded");
											} 
										}
									});
								}
				} ],
			columns : [ [ 
					{title : 'ID',field : 'id',hidden : true,rowspan : 1,align : 'center'},
					{title : '好运司机',field : 'fluckDriver',rowspan : 1,align : 'center',formatter:applyToLucky},					
					{title : '车辆类型ID',field : 'carType',hidden : true,rowspan : 1,align : 'center'},
					{title : '车辆规格ID',field : 'carSpecId',hidden : true,rowspan : 1,align : 'center'},
				    {title : '注册用户',field : 'userName',rowspan : 1,align : 'center',width : '160'},
				    {title : '角色类型',field : 'roleName',rowspan : 1,align : 'center',width : '110',formatter:applyToRole},
				    {title : '车牌号',field : 'carNum',rowspan : 1,align : 'center',width : '160'}, 
				    {title : '司机名字',field : 'driverName',rowspan : 1,align : 'center',width : '160'},
				    {title : '车辆类型',field : 'carTypeName',rowspan : 1,align : 'center',width : '150'},
				    {title : '车辆规格',field : 'carSpecName',rowspan : 1,align : 'center',width : '170'},
				    {title : '驾驶证编码',field : 'driverCode',rowspan : 1,align : 'center',width : '190'},
				    {title : '司机电话号码',field : 'carFtel',rowspan : 1,align : 'center',width : '170'},
				    {title : '牌照类型',field : 'licenseType',rowspan : 1,align : 'center',width : '110',formatter:applyToLicenseType},
				    {title : '核载',field : 'weight',rowspan : 1,align : 'center',width : '110'},
				    {title : '主要活动区域',field : 'activeArea',rowspan : 1,align : 'left',formatter:area}
				    
		     ] ]
		});
		
		$('#CLCarTable').datagrid('getPager').pagination({
			beforePageText : '第',//页数文本框前显示的汉字 
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
			onBeforeRefresh : function(pageNumber, pageSize) {
				$(this).pagination('loading');
			}
		});
	});
function area(value,rowData,rowIndex){
	if(value==1){
		return "苍南";
	}else if(value==2){
		return "瑞安";
	}else if(value==3){
		return "平阳";
	}else if(value==4){
		return "乐清";
	}else if(value==5){
		return "龙湾";
	}else if(value==6){
		return "瓯海";
	}else if(value==7){
		return "鹿城";
	}else if(value==8){
		return "台州";
	}else if(value==9){
		return "永嘉";
	}else{
		return "其他";
	}
}
function applyToRole(value,rowData,rowIndex){
	if(value=='货主'){
		return '<span style="color:blue;">货主</span>';
	}else if(value=='车主'){
		return '<span style="color:red;font-weight:bold;">车主</span>';
	}else if(value=='平台'){
		return '<span style="color:red;">平台</span>';
	}
}
function applyToLicenseType(value,rowData,rowIndex){
	if(value==0){
		return '<span>黄牌</span>';
	}else if(value==1){
		return '<span>蓝牌-市</span>';
	}else if(value==2){
		return '<span>蓝牌-县</span>';
	}
}

function applyToLucky(value,rowData,rowIndex){
	if(value==0){
		//return '<img src="${ctx}/pages/pc/car/images/car_3.png" width="50" />';
	}else if(value==1){
		return '<img src="${ctx}/pages/pc/car/images/car_3.png" width="20" />';
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
//批量删除
function toStrId(rows) {
		var paramStr = '';
		for ( var i = 0; i < rows.length; i++) {
			if (i == 0) {
				paramStr += 'carIds=' + rows[i].id;
			} else {
				paramStr += "&carIds=" + rows[i].id;
			}
		}
		return paramStr;
	}
</script>
</head>
<body>
 	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
			<div data-options="region:'north',title:'查询条件'" style="height: 100px; border: 0px;">
				 <form id="searchCarForm" method="post">
						<table style="margin: 0 auto; margin-top: 0px;">
							<tr>
								<td class="m-info-title">牌照类型:</td>
								<td>
									<select id="licenseType" name="licenseType">
										<option value="-1">全部</option>
 										<option value="0">黄牌</option> 
										<option value="1">蓝牌-市</option> 
										<option value="2">蓝牌-县</option> 
									</select>
								</td>
								<td class="m-info-title">车辆规格:</td>
								<td>
									<select id="carSpecId" name="carSpecId">
										<option value="-1">全部</option>
									</select>
								</td>
								<td class="m-info-title">车辆类型:</td>
								<td>
									<select id="carType" name="carType">
										<option value="-1">全部</option>
									</select>
								</td>
								<td class="m-info-title">关键字：</td>
								<td>
									<input type="text" id="searchKey"  class="Msearch-key" name="searchKey"  style="width:200px;"/>
									<select id="keyType" class="easyui-combobox" style="width: 100px; height: 20px;" name="keyType">
										<option value="-1">全部</option>
										<option value="1">车牌号</option>
										<option value="2">司机姓名</option>
									</select>
									<a id="searchCarButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
								</td>
							</tr>
						</table>
					</form>
			</div>
			<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'"  ><table id="CLCarTable"> </table></div>
			<div id="createWindow"  style="margin: 0 auto;" ></div>
	</div>
</body>
</html>