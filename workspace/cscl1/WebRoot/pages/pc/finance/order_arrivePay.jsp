<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单管理</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/messageTips.js" type="text/javascript" ></script>
<style type="text/css">#mp3{visibility:hidden;}</style>
<script type="text/javascript">
$(document).ready(function() {	
	//搜索
	$("#searchOrderTableButton").click(function() {		
		$("#CLOrderTable").datagrid('load', JSON.parse(toJOSNStr($("#searchOrderForm").serializeArray())));
	});
	
	$("#CLOrderTable").datagrid({
		title : '到付订单管理',
		loadMsg : '数据装载中......',
		url:'${ctx}/order/arriveload.do',
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
		onHeaderContextMenu : gridHeaderMenu,
		toolbar : [{
					id:"pay",
					text : '运费上缴',
// 					disabled : true,
					iconCls : 'icon-add',
					handler : function() {
							var rows = $("#CLOrderTable").datagrid('getSelections');
							     if(rows.length<=0){
							    	 $.messager.alert('提示', '请选择记录！', 'info');
							     }else if (rows.length > 1) {
									 $.messager.alert('提示', '每次只能选择一条记录！', 'info');
								 }else if(rows[0].fofflinePay == '1'){
								 	 $.messager.alert('提示', '选择未上缴的记录', 'info');
								 }
// 							     else if(rows[0].status!=2){
// 							    	 $.messager.alert('提示', '请选择派车中的记录！', 'info');
// 								 }
							     
							     else{
									 /* $.messager.confirm('确认','您确认想要运费上缴吗？',function(r){    
										    if (r){    
										    	$.ajax({
														type:"POST",
														url:'${ctx}/order/offlinepay.do?id='+rows[0].id+'&type=1',
														success:function(response){															
															var res = JSON.parse(response);
															if (res.flag == "success") {
										                        $.messager.alert('提示', '上缴成功', 'info', function() {
										                        	$('#CLOrderTable').datagrid('reload');
										                        });
										                    } else {
										                        $.messager.alert('提示', res.msg);
										                        $("#CLOrderTable").linkbutton('enable');
										                    }	
														}
												});    
										    }    
										}); */
							    	 $("#createWindow").window({
											title : "运费上缴",
											href : '${ctx}/order/freightVerify.do?id='+rows[0].id,
											width : 320,
										    height :120,
											modal : true
									});
								 } 
				          }
			          }/* ,'-',{	
			          	id:"pay_cancle",
						text : '取消上缴',
// 						disabled : true,
						iconCls : 'icon-tip',
						handler : function() {
								var rows = $("#CLOrderTable").datagrid('getSelections');
								     if(rows.length<=0){
								    	 $.messager.alert('提示', '请选择记录！', 'info');
								     }else if (rows.length > 1) {
										 $.messager.alert('提示', '每次只能选择一条记录！', 'info');
									 }else{
										 $.messager.confirm('确认','您确认想要取消上缴吗？',function(r){    
											    if (r){    
											    	$.ajax({
															type:"POST",
															url:'${ctx}/order/offlinepay.do?id='+rows[0].id+'&type=0',
															success:function(response){
																var res = JSON.parse(response);
																if (res.flag == "success") {
											                        $.messager.alert('提示', '操作成功', 'info', function() {
											                        	$('#CLOrderTable').datagrid('reload');
											                        });
											                    } else {
											                        $.messager.alert('提示', res.msg);
											                        $("#CLOrderTable").linkbutton('enable');
											                    }															
															}
													});    
											    }    
											});  
									 }
					          }
				          }*/],
		columns : [[
					{title : 'ID',field : 'id',hidden : true,rowspan : 1,align : 'center',sortable:true},
		            {title : '订单类型',field : 'type',rowspan : 1,align : 'center',width : '100',formatter:applyToType},
		            {title : '协议用车',field : 'protocolType',rowspan : 1,align : 'center',width : '60',formatter:protocolToType},
		            {title : '上缴运费',field : 'fofflinePay',rowspan : 1,align : 'center',width : '60',formatter:protocolToType},
		            {title : '订单编号',field : 'number',rowspan : 1,align : 'center',width : '120'},
		            {title : '用户名',field : 'creatorName',rowspan : 1,align : 'center',width : '110'},
		            {title : '客户',field : 'custName',rowspan : 1,align : 'center',width : '110'},
		            {title : '客户编码',field : 'custNumber',rowspan : 1,align : 'center',width : '110'},
		            {title : '业务员',field : 'custSaleMan',rowspan : 1,align : 'center',width : '110'},
		            {title : '司机名称',field : 'orderDriverName',rowspan : 1,align : 'center',width : '100'},
		            {title : '司机电话',field : 'orderDriverphone',rowspan : 1,align : 'center',width : '100'},
		            {title : '实付运费',field : 'ftotalFreight',rowspan : 1,align : 'center',width : '110'/* ,formatter:function(value,row,index){return eval(row.faddNumber)+eval(row.freight);} */},
		            {title : '司机总运费',field : 'fdriverAllin',rowspan : 1,align : 'center',width : '80'},
		            {title : '运费',field : 'freight',rowspan : 1,align : 'center',width : '80'},
		            {title : '追加费用',field : 'faddNumber',rowspan : 1,align : 'center',width : '80'},		            
		            {title : '装车时间',field : 'loadedTimeString',rowspan : 1,align : 'center',width : '120'},
		            {title : '提货点',field : 'addressName',rowspan : 1,align : 'center',width : '110'},
		            {title : '提货联系方式',field : 'phone',rowspan : 1,align : 'center',width : '110'},
		            {title : '增值服务费',field : 'freightAuto',rowspan : 1,align : 'center',width : '110',formatter:function(value,row,index){return '0'; } },
		            {title : '增值服务项目',field : 'fincrementServe',rowspan : 1,align : 'center',width : '110'},
		            {title : '创建时间',field : 'createTimeString',rowspan : 1,align : 'center',width : '125'},
		            {title : '状态',field : 'status',rowspan : 1,align : 'center',width : '100',formatter:applyToStatus},
		            {title : '代收金额',field : 'purposeAmount',rowspan : 1,align : 'center',width : '100'},
		            {title : '支付方式',field : 'fpayMethod',rowspan : 1,align : 'center',width : '100',formatter:applyToPayMethod},
		            {title : '卸货点数',field : 'fopint',rowspan : 1,align : 'center',width : '100'},
		            {title : '初始运费',field : 'foriginfreight',rowspan : 1,align : 'center',width : '80'},
				    {title : '修改业务员',field : 'fupdateman',rowspan : 1,align : 'center',width : '80'},
				    {title : '修改时间',field : 'fupdatetime',rowspan : 1,align : 'center',width : '120'}
		           ]],
		onLoadSuccess : function(data) {
				$('#CLOrderTable').datagrid('doCellTip',{cls:{'background-color':'#FFFF99'},delay:500});   
		},
	});
	
	
	$("#CLOrderTable").datagrid({  
	    rowStyler:function(rowIndex,rowData){  
	        if (rowData.isoverdue=='1'){  
	           // return 'font-weight:bold;color:red;';  
	           return ' background-color:#FF9966;';
	        }
	        if(rowData.status=='7'){
	        	return ' background-color:#6a536f;color:yellow';
	        }
	        if(rowData.foriginfreight!=null)
	        {
	        	return ' background-color:#F00;color:#FFF';
	        }
	    }  
	}).datagrid("hideColumn","id").datagrid("columnMoving");;
	
	$("#CLOrderTable").datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
	
	
});


function applyToPayMethod(value,rowData,rowIndex){
	if(value==0){
		return '<span>余额</span>';
	}else if(value==1){
		return '<span>支付宝</span>';
	}else if(value==2){
		return '<span>微信</span>';
	}else if(value==3){
		return '<span>银联</span>';
	}else if(value==4){
		return '<span>运费到付</span>';
	}else if(value==5){
		return '<span>月结</span>';
	}
}


function applyToStatus(value,rowData,rowIndex){
	if(value==1){
		return '<span style="color:red; ">待付款</span>';
	}else if(value==2){
		return '<span style="color:black; " >派车中</span>';
	}else if(value==3){
		return '<span style="color:blue; " >运输中</span>';
	}else if(value==4){
		return '<span style="color:skyblue; " >待评价</span>';
	}else if(value==5){
		return '<span style="color:green; " >已完成</span>';
	}else if(value==6){
		return '<span style="color:gray; " >已取消</span>';
	}else if(value==7){
		return '<span style="color:yellow; " >异常关闭</span>';
	}
}

function applyToPass (value,rowData,rowIndex){
	if(value==0){
		return '<span style="color:blue;font-weight:bold;">否</span>';
	}
	else if(value==1){
		return '<span style="color:red;font-weight:bold;">是</span>';
	}
}

//协议用车
function protocolToType (value,rowData,rowIndex){
	if(value==0){
		return '<span style="color:blue;font-weight:bold;">否</span>';
	}
	else if(value==1){
		return '<span style="color:yellow;font-weight:bold;">是</span>';
	}
}
function applyToType(value,rowData,rowIndex){
	if(value==1){
		return '<span style="color:#5CACEE;font-weight:bold;">整车订单</span>';
	}else if(value==2){
		return '<span style="color:#698B22;font-weight:bold;">零担订单</span>';
	}else if(value==3){
		return '<span style="color:#698B22;font-weight:bold;">包天订单</span>';
	}
}
function applyToDetailType(value,rowData,rowIndex){
	if(value==1){
		return '<span style="color:blue;">提货</span>';
	}else if(value==2){
		return '<span style="color:blue;">卸货</span>';
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
<body>
	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
			<div data-options="region:'north',title:'查询条件'" style="height: 100px; border: 0px;">
			       <form id="searchOrderForm" method="post">  			  
						<table style="margin: 0 auto; margin-top: 0px;">
						
							<tr>
								<td class="m-info-title">关键字：</td>
								<td colspan="10">
									<input type="text" id="number"  class="Msearch-key" name="mySearchKey" style="width:235px;"/>
									<a id="searchOrderTableButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
								</td>
							</tr>
						</table>
					</form>
			</div>
			<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'"  >
				<table id="CLOrderTable"></table>
			</div>
			<div id="createWindow"  style="margin: 0 auto; overflow:hidden;" ></div>
	</div>
</body>
</html>