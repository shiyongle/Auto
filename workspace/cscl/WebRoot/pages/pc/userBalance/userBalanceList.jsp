<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %><!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>设置</title>
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
$(document).ready(function(){		
				$('#balanceTable').datagrid({
					title : '用户余额调整',
					loadMsg : '数据装载中......',
					url:"${ctx}/userBalance/load.do",
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
				        	  	id:'cancelBalanceBtn',
								text : '删除',
								iconCls : 'icon-remove',
								handler : function() {									
									var rows = $("#balanceTable").datagrid('getSelections');
									 if(rows.length<=0){
							    	 	$.messager.alert('提示', '请选择记录！', 'info');
								     }else{
									 	$.ajax({
									 			type : "POST",
												url : "${ctx}/userBalance/del.do?"+toStrId(rows),//取消下过的单的操作
												success : function(response) {
													if (response == "success") {
								                        $.messager.alert('提示', '操作成功', 'info', function() {
								                        	$('#balanceTable').datagrid('reload');
								                        });
								                    } else {
								                        $.messager.alert('提示', '删除失败！');
								                    }
												}	
											});
									 }
							    }
				         	 },'-',{
				         		 	id:'editBalanceBtn',
									text : '新增',
									iconCls : 'icon-add',
									handler : function() {
										$("#createWindow").window({
											title : "新增",
											href : '${ctx}/userBalance/userBalanceSave.do',
											width :340,
										    height :320,
										    draggable:true,
											modal : true
										});
								    }
					         	}
				          ],
					columns : [[
					        {title : '用户ID',hidden:true,field : 'fuser_role_id',rowspan : 1,align : 'center',width : '1 00'}, 
					        {title : '客户 | 司机',field : 'fuser_name',rowspan : 1,align : 'center',width : '150'}, 
					        {title : '用户类型',field : 'ftype',rowspan : 1,align : 'center',width : '150',formatter:userType}, 
					        {title : '余额',field : 'money',rowspan : 1,align : 'center',width : '150'},
					        {title : '操作金额',field : 'foperate_money',rowspan : 1,align : 'center',width : '150'},
					        {title : '操作人ID',hidden:true,field : 'foperator',rowspan : 1,align : 'center',width : '150'},
					        {title : '操作人',field : 'operator',rowspan : 1,align : 'center',width : '150'},
					        {title : '创建时间',field : 'foperate_time',rowspan : 1,align : 'center',width : '200',formatter:fbilldateType} ,
					        {title : '认证人ID',hidden:true,field : 'fauditor',rowspan : 1,align : 'center',width : '150'}, 
					        {title : '认证人',field : 'auditor',rowspan : 1,align : 'center',width : '150'}, 
					        {title : '认证时间',field : 'faudit_time',rowspan : 1,align : 'center',width : '200',formatter:fbilldateType},
					        {title : '状态',field : 'fis_pass_identify',rowspan : 1,align : 'center',width : '150',formatter:getState},
					        {title : '备注',field : 'fremark',rowspan : 1,align : 'center',width : '200'}
					      ]]
				});
				$('#balanceTable').datagrid('getPager').pagination({
					beforePageText : '第',//页数文本框前显示的汉字 
					afterPageText : '页    共 {pages} 页',
					displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
					onBeforeRefresh : function(pageNumber, pageSize) {
						$(this).pagination('loading');
					}
				});
	//余额查询按钮点击事件

	$("#balanceButton").click(function() {
		console.log(JSON.parse(toJOSNStr($("#searchBalanceForm").serializeArray())));
		$('#balanceTable').datagrid('load', JSON.parse(toJOSNStr($("#searchBalanceForm").serializeArray())));
	});
});

	//用户类型
	function userType(value,row,index){
		if(value=="1"){
			return '<font color="blue">客户</font>';
		}else if (value=="2") {
			return '<font color="red">司机</font>';
		}
	}
	//状态
	function getState(value, row, index){
		if(value=="0"){
			return '<font color="red">未认证</font>';
			}else if(value=="1"){
			return '<font color="green">已认证</font>';
			}else{
			return '<font color="black">已驳回</font>';
		}
	}	
	// 时间戳转换 
	function fbilldateType(value,rowData,rowIndex){
		if(value==null){
			return "- -";
		}else{
			var d = new Date(value);    //根据时间戳生成的时间对象
			var date = (d.getFullYear()) + "-" + toDou(d.getMonth() + 1)+"-"+toDou(d.getDate())+" "+toDou(d.getHours())+":"+toDou(d.getMinutes())+":"+toDou(d.getSeconds());
			return date;
		}
	}
	//时间补零
	function toDou(n){
		return n<10?"0"+n:""+n;
	}
	
	//批量删除
	function toStrId(rows) {
		var paramStr = '';
		for ( var i = 0; i < rows.length; i++) {
			if (i == 0) {
				paramStr += 'ids=' + rows[i].fid;
			} else {
				paramStr += "&ids=" + rows[i].fid;
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

</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true"
		style="border-left: 0px; border-right: 0px;">
		<div data-options="region:'north',title:'查询条件'"
			style="height: 120px; border: 0px;">
			<form id="searchBalanceForm" method="post">
				<table style="margin: 20px auto 0 ;">
					<tr>
						<td class="m-info-title" align="right">选择用户类型:</td>
						<td colspan="2">
							<input type="radio" name="ftype" value="" checked="checked" />全部
							<input type="radio" name="ftype" value="1" />客户
							<input type="radio" name="ftype" value="2"/>司机
						</td>
					</tr>
					<tr>
						<td class="m-info-title" align="right">查询条件:</td>
						<td>
						<input id="search_fman" name="fuser_name" placeholder="请输入客户名或司机名" style="width: 140px;"/>
						</td>
						<td>
							<a id="balanceButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
						</td>
					</tr>
				</table>
				<div id="account"></div>
			</form>
		</div>	
		<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'">
			<table id="balanceTable">
			</table>
		</div>
		<div id="createWindow" style="margin: 0 auto; overflow:hidden;" ></div>
	</div>
</body>
</html>
