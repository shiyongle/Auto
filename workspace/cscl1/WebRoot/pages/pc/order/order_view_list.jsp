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
<script src="${ctx}/pages/pc/js/easyui/datagrid-detailview.js" type="text/javascript" ></script>
<script src="${ctx}/pages/pc/js/messageTips.js" type="text/javascript" ></script>
<style type="text/css">#mp3{visibility:hidden;}</style>
<script type="text/javascript">
$(document).ready(function() {	
	//货主下拉框数据加载
	$("#creator").combobox({
		url : "${ctx}/select/getByRoleId.do",
		editable : true,
		width:120,
		mode: 'local',
		valueField: 'optionId',//提交值
		textField: 'optionName',//显示值
		validType:"comboxValidate['creator']",
		delay:2000,
		filter:searchItem
	});
	
	//搜索
	$("#searchOrderTableButton").click(function() {	
		if ($("#searchOrderForm").form("validate")){	
			$("#CLOrderTable").datagrid('load', JSON.parse(toJOSNStr($("#searchOrderForm").serializeArray())));
		}
	});
	
	$("#CLOrderTable").datagrid({
		title : '订单管理',
		loadMsg : '数据装载中......',
		url:'${ctx}/order/load.do',
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
					id : "exportExecl",
					text : '导出',
					iconCls : 'icon-excel',
					handler : function() {
						$("#CLOrderTable").datagrid("loading");
						var rows = $("#CLOrderTable").datagrid('getSelections');
						var params = $("#searchOrderForm").serialize();
						$.ajax({
							type : "POST",
							dataType:"json",
							url : "${ctx}/order/exportExecl.do",
							data : getOrderId(rows)+ "&" +params,
							success : function(response) {
								if(response.success){
									window.location.href ="${ctx}/excel/"+response.url;
									$("#CLOrderTable").datagrid("loaded");
								}else{
									$.messager.alert('提示', '操作失败');
									$("#CLOrderTable").datagrid("loaded");
								} 
							}
						});
					}
		  }],
		view: detailview,
			detailFormatter: function(rowIndex, rowData){
				return "<div id='adjustDetail" + rowIndex + "'></div>";
			},
			onExpandRow: function(rowIndex, rowData){
					$("#adjustDetail"+rowIndex).datagrid({
						title : '',
						url : '${ctx}/order/detailLoader.do?orderId='+rowData.id,
						loadMsg : '数据装载中......',
						remoteSort : false,
						sortable : false, 
						onResize:function(){   
			                $("#CLOrderTable").datagrid('fixDetailRowHeight',rowIndex);   
			            },   
			            onLoadSuccess:function(){   
			                setTimeout(function(){
			                	$("#CLOrderTable").datagrid('fixDetailRowHeight',rowIndex);
			                },0);   
			            },
			            columns : [[
								  {title : '类型',field : 'detailType',width : '100',rowspan : 1,align : 'center',sortable:true,formatter:applyToDetailType},
			    		          {title : '联系人',field : 'linkman',width : '200',rowspan : 1,align : 'center',sortable:true},
					   		      {title : '电话',field : 'phone',width : '120',rowspan : 2,align : 'center',sortable:true},
					   		      {title : '地址',field : 'addressName',width : '785',rowspan : 1,align : 'center',sortable:true},
					   		      {title : '随机验证码',field : 'securityCode',width : '200',rowspan : 1,align : 'center',sortable:true},
					   		      {title : '是否通过验证',field : 'pass',width : '100',rowspan : 1,align : 'center',sortable:true,formatter:applyToPass}
					   		      ]]
					});
					$("#CLOrderTable").datagrid('fixDetailRowHeight',rowIndex);
			},
		columns : [[
					{title : 'ID',field : 'id',hidden : true,rowspan : 1,align : 'center',sortable:true},
					{title : 'FID',field : 'abnormityId',hidden : true,rowspan : 1,align : 'center',width : '100'},
		    		{title : '订单类型',field : 'type',rowspan : 1,align : 'center',width : '100',formatter:applyToType},
		    		{title : '协议用车',field : 'protocolType',rowspan : 1,align : 'center',width : '60',formatter:protocolToType},
		    		{title : '回程车',field : 'freturn_car',rowspan : 1,align : 'center',width : '60',formatter:ToReturnCar},
		    		{title : '订单编号',field : 'number',rowspan : 1,align : 'center',width : '120',formatter:function(value,rowData,rowIndex){
                			return "<a href=\"javascript:void(0)\" onclick=\"fnnn("+rowData.id+")\">"+ rowData.number + "</a>";
               		}},
					{title : '所需车辆类型',field : 'carTypeId',rowspan : 1,align : 'center',width : '110',formatter:applyToCarType},
		            {title : '所需车辆规格',field : 'carSpecId',rowspan : 1,align : 'center',width : '110',formatter:applyToCarSpec},
		            {title : '用户名',field : 'creatorName',rowspan : 1,align : 'center',width : '110'},
		            {title : '客户',field : 'custName',rowspan : 1,align : 'center',width : '110'},
		            {title : '客户编码',field : 'custNumber',rowspan : 1,align : 'center',width : '110'},
		            {title : '客户类型',field : 'custType',rowspan : 1,align : 'center',width : '110'},
		            {title : '业务员',field : 'custSaleMan',rowspan : 1,align : 'center',width : '110'},
		            {title : '提货点',field : 'addressName',rowspan : 1,align : 'center',width : '110'},
		            {title : '提货联系方式',field : 'takephone',rowspan : 1,align : 'center',width : '110'},
		            {title : '增值服务费',field : 'freightAuto',rowspan : 1,align : 'center',width : '110',formatter:function(value,row,index){return '0'; } },
		            {title : '增值服务项目',field : 'fincrementServe',rowspan : 1,align : 'center',width : '110'},
		            {title : '审批状态',field : 'fispass_audit',rowspan : 1,align : 'center',width : '110',formatter:function(value,rowData,rowIndex){
		            	if(value == '1'){return '<span style="color:green;">已审批</span>'}else if(value == '0'){return '<span style="color:red;">未审批</span>'}}},
			        {title : '总运费',field : 'totalMoney',rowspan : 1,align : 'center',width : '110',formatter:function(value,row,index){return numAdd(parseFloat(row.faddNumber),parseFloat(row.freight));}},
		            {title : '司机运费',field : 'fdriverfee',rowspan : 1,align : 'center',width : '80'},
		            {title : '里程',field : 'mileage',rowspan : 1,align : 'center',width : '80'},
		            {title : '运费',field : 'freight',rowspan : 1,align : 'center',width : '80'},
		            {title : '追加费用',field : 'faddNumber',rowspan : 1,align : 'center',width : '80'},
		            {title : '审核人',field : 'auditor',rowspan : 1,align : 'center',width : '110'},
		            {title : '审核时间',field : 'faudit_timeString',rowspan : 1,align : 'center',width : '110'},
		            {title : '调整人',field : 'regulator',rowspan : 1,align : 'center',width : '110'},
		            {title : '调整时间',field : 'fregulate_timeString',rowspan : 1,align : 'center',width : '110'},
		            {title : '货物类型',field : 'goodsTypeName',rowspan : 1,align : 'center',width : '200'},
		            {title : '装车时间',field : 'loadedTimeString',rowspan : 1,align : 'center',width : '120'},
		            {title : '到达提货点时间',field : 'farrivePickUpTime',rowspan : 1,align : 'center',width : '120',formatter:localTime},
		            {title : '离开提货点时间',field : 'fleavePickUpTime',rowspan : 1,align : 'center',width : '120',formatter:localTime},
		            {title : '重量',field : 'weight',rowspan : 1,align : 'center',width : '80'},
		            {title : '体积',field : 'volume',rowspan : 1,align : 'center',width : '80'},
		            {title : '长度',field : 'length',rowspan : 1,align : 'center',width : '80'},
		            {title : '司机名称',field : 'orderDriverName',rowspan : 1,align : 'center',width : '100'},
		            {title : '司机电话',field : 'orderDriverphone',rowspan : 1,align : 'center',width : '100'},
		            {title : '车型',field : 'carType',rowspan : 1,align : 'center',width : '100',formatter:applyToCarType},
		            {title : '车牌号',field : 'orderDriverCarNumber',rowspan : 1,align : 'center',width : '100'},
		            {title : '创建时间',field : 'createTimeString',rowspan : 1,align : 'center',width : '125'},
		            {title : '状态',field : 'status',rowspan : 1,align : 'center',width : '100',formatter:applyToStatus},
		            {title : '代收金额',field : 'purposeAmount',rowspan : 1,align : 'center',width : '100'},
		            {title : '支付方式',field : 'fpayMethod',rowspan : 1,align : 'center',width : '100',formatter:applyToPayMethod},
		            {title : '卸货点数',field : 'fopint',rowspan : 1,align : 'center',width : '100'},
		            {title : '操作人',field : 'operatorName',rowspan : 1,align : 'center',width : '100'},
		            {title : '数量',field : 'famount',rowspan : 1,align : 'center',width : '100'},
		            {title : '单位类型',field : 'funitId',rowspan : 1,align : 'center',width : '100',formatter:applyToUnit},
		            {title : '线下支付',field : 'fonlinePay',rowspan : 1,align : 'center',width : '100',formatter:applyToOnlinePay},
				    {title : '备注',field : 'fremark',rowspan : 1,align : 'center',width : '200'},
				    {title : '初始运费',field : 'foriginfreight',rowspan : 1,align : 'center',width : '80'},
				    {title : '修改业务员',field : 'fupdateman',rowspan : 1,align : 'center',width : '80'},
				    {title : '修改时间',field : 'fupdateTimeString',rowspan : 1,align : 'center',width : '120'}
		           ]],
		onLoadSuccess : function(data) {
				$('#CLOrderTable').datagrid('doCellTip',{cls:{'background-color':'#FFFF99'},delay:500});   
		},
	    onSelect : isButtonEnable,
		onUnselect : isButtonEnable,
		onSelectAll: isButtonEnable,
		onUnselectAll: isButtonEnable
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
	    	
	        if (rowData.abnormityId!=null&&rowData.abnormityId!=0){  
	        	if(rowData.foriginfreight!=null)
		        {
		        	return ' background-color:#deec9e;color:#F00';
		        }
	        	return ' background-color:#deec9e;';
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

//回程车
function ToReturnCar (value,rowData,rowIndex){
	if(value==1){
		return '<span style="color:red; font-weight:bold;">是</span>';
	}else if(value==2){
		return '<span style="color:blue; font-weight:bold;">否</span>';
	} else {
	 	return '';
	 }
}

//加法
function numAdd(num1, num2) {
    var baseNum, baseNum1, baseNum2;
    try {
        baseNum1 = num1.toString().split(".")[1].length;
    } catch (e) {
        baseNum1 = 0;
    }
    try {
        baseNum2 = num2.toString().split(".")[1].length;
    } catch (e) {
        baseNum2 = 0;
    }
    baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
    return (num1 * baseNum + num2 * baseNum) / baseNum;
};

function applyToType(value,rowData,rowIndex){
	if(value==1){
		return '<span style="color:#5CACEE;font-weight:bold;">整车订单</span>';
	}else if(value==2){
		return '<span style="color:#698B22;font-weight:bold;">零担订单</span>';
	}else if(value==3){
		return '<span style="color:#698B22;font-weight:bold;">包天订单</span>';
	}
}
function applyToCarSpec(value,rowData,rowIndex){
	if(value==1){
		return '<span>面包车</span>';
	}
	if(value==2){
		return '<span>2.5米</span>';
	}
	if(value==3){
		return '<span>4.2米</span>';
	}
	if(value==4){
		return '<span>5.2米</span>';
	}
	if(value==5){
		return '<span>6.8米</span>';
	}
	if(value==6){
		return '<span>9.6米</span>';
	}
}
function applyToCarType(value,rowData,rowIndex){
	if(value==1){
		return '<span>小面7座</span>';
	}
	if(value==2){
		return '<span>中面9座</span>';
	}
	if(value==3||value==7||value==11||value==15||value==19){
		return '<span>任意车型</span>';
	}
	if(value==4||value==8||value==12||value==16||value==20){
		return '<span>厢式货车</span>';
	}
	if(value==5||value==9||value==13||value==17||value==21){
		return '<span>高栏货车</span>';
	}
	if(value==6||value==10||value==14||value==18||value==22){
		return '<span>低栏货车</span>';
	}
}
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
function applyToUnit(value,rowData,rowIndex){
	if(value==0){
		return '<span>无</span>';
	}else if(value==1){
		return '<span>票</span>';
	}else if(value==2){
		return '<span>吨</span>';
	}else if(value==3){
		return '<span>立方米</span>';
	}else if(value==4){
		return '<span>件</span>';
	}else if(value==5){
		return '<span>平方米</span>';
	}else if(value==6){
		return '<span>托</span>';
	}
}
function applyToOnlinePay(value,rowData,rowIndex){
	if(value==0){
		return '<span>未选择</span>';
	}else if(value==1){
		return '<span>线上</span>';
	}else if(value==2){
		return '<span>线下</span>';
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
		return '<span style="color:red;font-weight:bold;">是</span>';
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
		return '<span style="color:red;">卸货</span>';
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

/***订单流水号***/
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

function fnnn(data){
	window.location.href="${ctx}/orderCarDetail/list.do?orderId="+data ;
}

//判断按钮是否可用
	function isButtonEnable(){
		$("#scheduleButton").linkbutton('disable');//调度
		$("#specialButton").linkbutton('disable');//专调
		$("#cancelButton").linkbutton('disable');//取消
		$("#editButton").linkbutton('disable');//取消
		var selections = $("#CLOrderTable").datagrid('getSelections');
		if(selections.length > 0){
			var isable = true;
			for ( var i = 0; i < selections.length; i++) {
				if(selections[i].status == 3 || selections[i].status == 4 || selections[i].status == 5 || selections[i].status == 6|| selections[i].status == 7 ){
					isable = false;
					break;
				}
			}
			if(isable){
				$("#cancelButton").linkbutton('enable');
			}
		}
		if(selections.length ==1){
			if(selections[0].status == 2){
				$("#scheduleButton").linkbutton('enable');
				$("#specialButton").linkbutton('enable');
			}
			if(selections[0].fpayMethod==4&&(selections[0].status==2||selections[0].status==3))
			{
				$("#editButton").linkbutton('enable');
			}
		}else{
			$("#scheduleButton").linkbutton('disable');
			$("#specialButton").linkbutton('disable');
		}
		
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
			<div data-options="region:'north',title:'查询条件'" style="height: 150px; border: 0px;">
			       <form id="searchOrderForm" method="post">  			  
						<table style="margin: 0 auto; margin-top: 0px;">
						<tr>
							<td class="m-info-title">用户:</td>
							<td>
							<select id="creator" name="creator" panelHeight="200" >
							</select>
							</td>
						</tr>
						<tr>
								<td class="m-info-title">状态:</td>
								<td>
									<select  id="orderStatus"  name="status" class="easyui-combobox" style="width: 100px; height: 20px;" data-options="editable:false">
										<option value="">全部</option>
										<option value="1">待付款</option>
										<option value="2">派车中</option>
										<option value="3">运输中</option>
										<option value="4">待评价</option>
										<option value="5">已完成</option>
										<option value="6">已取消</option>
										<option value="7">异常关闭</option>
									</select>
								</td>
								<td class="m-info-title">装车日期：</td>
								<td>
									<input type="text" id="loadedTimeBegin" class="datePicker MtimeInput"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH',maxDate:'#F{$dp.$D(\'loadedTimeEnd\')}'})" name="loadedTimeBegin"  /> -
									<input type="text" id="loadedTimeEnd" class="MtimeInput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH',minDate:'#F{$dp.$D(\'loadedTimeBegin\')}'})" name="loadedTimeEnd"  />
								</td>
							</tr>
							<tr>
								<td class="m-info-title">订单类型:</td>
								<td>
								     <select  id="orderType"  name="type" class="easyui-combobox" style="width: 100px; height: 20px;" data-options="editable:false" >
										<option value="">全部</option>
										<option value="1">整车订单</option>
										<option value="2">零担订单</option>
									</select>
								</td>
								<td class="m-info-title">订单编码：</td>
								<td colspan="10">
									<input type="text" id="number"  class="Msearch-key" name="number" style="width:235px;"/>
									<a id="searchOrderTableButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
								</td>
							</tr>
						</table>
					</form>
			</div>
			<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'"  >
				<table id="CLOrderTable"></table>
			</div>
			<div id="createWindow"  style="margin: 0 auto;" ></div>
	</div>
<script type="text/javascript">
	var semp;
	//60秒查询一次关于派车中的订单时间，距离派车时间相差半小时的时候，如果有就发送提示信息
	function sendMessage(){
		$.ajax( {  
		     url:'${ctx}/order/show.do',// 跳转到  
		     data:"",  
		     type:'post',  
		     cache:false,  
		     dataType:'text',  
		     success:function(msg) {
		         if(msg=="success"){
		         	if(semp!=null){
		         		semp.parent().hide();
			     	}
		         	if(/firefox/.test(navigator.userAgent.toLowerCase())){//如果是火狐
		         		semp=$.messager.show({
							title:'我的消息',
							msg:'<p style="color:red;">有需要派车信息需要处理，请注意刷新。</p><audio id="mp3" controls="controls" autoplay="autoplay"><source src="${ctx}/pages/pc/identification/sendCar.mp3" type="audio/mp3" /></audio>',
							timeout:0,
							height:200,
							width:300,
							showType:'slide',
							showSpeed:2000
						});
		         	}else if(/chrome/.test(navigator.userAgent.toLowerCase())){//谷歌和谷歌内核的浏览器，包括360
						semp=$.messager.show({
							title:'我的消息',
							msg:'<p style="color:red;">有需要派车信息需要处理，请注意刷新。</p><audio id="mp3" controls="controls" autoplay="autoplay"><source src="${ctx}/pages/pc/identification/sendCar.mp3" type="audio/mp3" /></audio>',
							timeout:0,
							height:200,
							width:300,
							showType:'slide',
							showSpeed:2000
						});
					}else{
						semp=$.messager.show({
							title:'我的消息',
							msg:'<p style="color:red;">有需要派车信息需要处理，请注意刷新。</p><object id="mp3" controls="no" height="100" width="100" data="${ctx}/pages/pc/identification/sendCar.mp3"></object>',
							timeout:0,
							height:200,
							width:300,
							showType:'slide',
							showSpeed:2000
						});
					}
					$('#mp3').get(0).play();
		         }
		      }  
 		});
	}
	sendMessage();
	setInterval("sendMessage()",600000);//十分钟查询一次
</script>
</body>
</html>