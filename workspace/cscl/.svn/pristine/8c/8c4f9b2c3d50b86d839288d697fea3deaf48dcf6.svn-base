<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>货运班车列表</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#search_userRoleId").combobox({
		url : "${ctx}/select/getByRoleId.do",
		editable : true,
		width:100,
		mode: 'local',
		valueField: 'optionName',
		textField: 'optionName',
		filter:searchItem
	  
	});
	
	$("#List_carSpecId").combobox({
			url : "${ctx}/select/getAllCarType.do",
			editable : true,
			width:100,
			mode: 'remote',
			valueField: 'optionId',
			textField: 'optionName',
		  
		});
 	//查询按钮点击事件
	$("#searchRuleButton").click(function() {
		$('#triverBusTable').datagrid('load', JSON.parse(toJOSNStr($("#searchRuleForm").serializeArray())));
	});
	$('#triverBusTable').datagrid({
		title : '货运班车',
		loadMsg : '数据装载中......',
		url:'${ctx}/travelBus/load.do',//货运班车订单列表接口
// 		url:'${ctx}/pages/pc/travelBus/1.json',
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
					id:'addBusOrderBtn',
					text : '下单',
					iconCls : 'icon-add',
					handler : function() {
							$("#triverBus_Window").window({
								title : "下单",
								href : '${ctx}/travelBus/travelBusAdd.do',//货运班车下单接口
								width : 850,
								height :400,								
								draggable:true,
								resizable:false,
								maximizable:false,
								minimizable:false,
								collapsible:false,
								modal : true
						    });
				          }
			          },'-',{
			        	  	id:'cancelBusOrderBtn',
							text : '删除',
							iconCls : 'icon-remove',
							disabled : true,
							handler : function() {									
								var rows = $("#triverBusTable").datagrid('getSelections');
								 if(rows.length<=0){
						    	 	$.messager.alert('提示', '请选择记录！', 'info');
							     }else{
								 	$.ajax({
								 			type : "POST",
											url : '${ctx}/travelBus/del.do?'+toStrId(rows),//取消下过的单的操作
											success : function(response) {
												if (response == "success") {
							                        $.messager.alert('提示', '操作成功', 'info', function() {
							                        	$('#triverBusTable').datagrid('reload');
							                        });
							                    } else {
							                        $.messager.alert('提示', '取消失败！');
							                    }
											}	
										});
								 }
						          }
			         	 },'-',{
			         		 	id:'editBusOrderBtn',
								text : '修改',
								iconCls : 'icon-edit',
								disabled : true,
								handler : function() {
									var rows = $("#triverBusTable").datagrid('getSelections');
									if(rows.length<=0){
								    	 $.messager.alert('提示', '请选择订单！', 'info');
								    }else if (rows.length > 1) {
											$.messager.alert('提示', '每次只能选择一条订单！', 'info');
									}else{
								    	 $("#triverBus_Window").window({
											title : "修改",
											href : '${ctx}/travelBus/travelBusEdit.do?id='+rows[0].id,
											width : 850,
										    height :400,
											modal : true
										});																
									} 
							          }
				         	}
			          ],
		columns : [[
					{title : 'ID',field : 'id',hidden : true,rowspan : 1,align : 'center',sortable:true},
					{title : '日期',field : 'fbizDateTimeString',rowspan : 1,align : 'center',width : '150'},
					{title : '业务经理',field : 'fbizManager',rowspan : 1,align : 'center',width : '100'},
		            {title : '提货站点',field : 'fpickupSite',rowspan : 1,align : 'center',width : '200'},
		            {title : '数量',field : 'famount',rowspan : 1,align : 'center',width : '100'},
		            {title : '公斤',field : 'fkilo',rowspan : 1,align : 'center',width : '100'},
		            {title : '送达站点',field : 'fdeliverySite',rowspan : 1,align : 'center',width : '200'},
		            {title : '收货地址',field : 'freceiptAddress',rowspan : 1,align : 'center',width : '200'},		            
		            {title : '货运班车车牌',field : 'fbusPlate',rowspan : 1,align : 'center',width : '100'},
		            {title : '转运班车车牌',field : 'ftransferPlate',rowspan : 1,align : 'center',width : '100'},
		            {title : '总费用',field : 'fallCost',rowspan : 1,align : 'center',width : '100'},
		            {title : '提货站点费用',field : 'fdeliverySiteCost',rowspan : 1,align : 'center',width : '100'},
		            {title : '货运班车费用',field : 'fbusCost',rowspan : 1,align : 'center',width : '100'},
		            {title : '转运班车费用',field : 'ftransferCost',rowspan : 1,align : 'center',width : '100'},
		            {title : '派送站点费用',field : 'fsendCost',rowspan : 1,align : 'center',width : '100'},		            
		            {title : '运费支付方',field : 'fFreightPaymentParty',rowspan : 1,align : 'center',width : '100'},
		            {title : '代收货款',field : 'fcollectionDelivery',rowspan : 1,align : 'center',width : '100'},
		            {title : '运费结算情况',field : 'fFreightSettlement',rowspan : 1,align : 'center',width : '150'},
		            {title : '单据编号',field : 'fnumber',rowspan : 1,align : 'center',width : '200'},
		            {title : '下单人员',field : 'userName',rowspan : 1,align : 'center',width : '100'},
		            {title : '下单时间',field : 'fcreateTimeString',rowspan : 1,align : 'center',width : '150'},
		            {title : '备注',field : 'fremark',rowspan : 1,align : 'center',width : '200'}
		       
		            ]],
		  onSelect : isButtonEnable,
		  onUnselect : isButtonEnable,
		  onSelectAll: isButtonEnable,
		  onUnselectAll: isButtonEnable		            
	});
	$('#triverBusTable').datagrid('getPager').pagination({
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
//批量取消
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

//判断按钮是否可用
function isButtonEnable(){
	$("#cancelBusOrderBtn").linkbutton('disable');//取消	
	$("#editBusOrderBtn").linkbutton('disable');//修改
	var selections = $("#triverBusTable").datagrid('getSelections');
	if(selections.length > 0){
		$("#cancelBusOrderBtn").linkbutton('enable');				
	}
	if(selections.length > 0&&selections.length ==1){
		$("#editBusOrderBtn").linkbutton('enable');		
	}
	
}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
<!-- 			<div data-options="region:'north',title:'查询条件'" style="height: 100px; border: 0px;"> -->
<!-- 			       <form id="searchRuleForm" method="post"> -->
<!-- 						<table style="margin: 0 auto; margin-top: 0px;"> -->
<!-- 							<tr>						 -->
<!-- 								<td class="m-info-title">客户名称:</td> -->
<!-- 								<td> -->
<!-- 									<select id="search_userRoleId" name="userName" panelHeight="200"> -->
<!-- 										<option value="">请选择</option> -->
<!-- 									</select> -->
<!-- 									<a id="searchRuleButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a> -->
<!-- 								</td> -->
<!-- 							</tr> -->
<!-- 						</table> -->
<!-- 					</form> -->
			  
			
<!-- 			</div> -->
			<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'"  >
				<table id="triverBusTable"></table>
			</div>
			<div id="triverBus_Window" style="margin: 0 auto; overflow:hidden;"></div>
	</div>
</body>
</html>