<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>货主认证管理</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/jquery.imageLens.js" type="text/javascript"></script>
<style type="text/css">
#createWindow #Div0 {width: 100%;height: 663px;}
#createWindow #Div1 {float: left;width: 460;height: 662px;}
#createWindow #Div2 {float: left;width: 216px;height: 662px;}
#mp3{visibility:hidden;}
</style>
<script type="text/javascript">
$(document).ready(function() {
	//查询按钮点击事件
	$("#searchButton").click(function() {
			$('#CLIdentificationTable').datagrid('load', JSON.parse(toJOSNStr($("#searchIdenForm").serializeArray())));
	});
	
	$("#userid").combobox({
		url : "${ctx}/select/getByIdentify.do",
		editable : true,
		width:140,
		mode: 'local',
		valueField: 'optionName',//提交值
		textField: 'optionName',//显示值
		filter:searchItem
	});
	
	$('#CLIdentificationTable').datagrid({
		title : '货主认证管理',
		loadMsg : '数据装载中......',
		url:'${ctx}/iden/loadHuo.do',
		fit:true,
		fitColumns:false,
		nowrap : true,
		striped : true,    //为true,显示斑马线效果。
		multiSort : true,  //定义是否允许多列排序
		remoteSort : true, //定义从服务器对数据进行排序
		pagination : true, //为true,则底部显示分页工具栏
		rownumbers : true, //为true,则显示一个行号列
		singleSelect:true,//只允许选择一行
		frozenColumns : [[{field : 'id1',checkbox : true}]],
		pageSize : 20,//每页显示的记录条数，默认为10 
		pageList : [ 10, 20, 50, 100 ],//可以设置每页记录条数的列表 
		toolbar : [{
					id : "create",
					text : '审核',
					iconCls : 'icon-add',
					handler : function() {
						var row=$("#CLIdentificationTable").datagrid('getSelected');
						if(row!=null && row.status==1){
							$("#createWindow").window({
								left:"250px",
								top:"0px",
								title : "审核",
								href : '${ctx}/iden/vettedForBoss.do',
								queryParams: {id: row.id},
								width : 710,
								height : 535,
								draggable:true,
								resizable:true,
								maximizable:true,
								minimizable:true,
								collapsible:false,
								inline:false,
								modal : true
						    });
						}else if(row==null){
							$.messager.alert('提示','请选择一条记录！'); 
						}else{
							$.messager.alert('提示','请选择待审核记录！');
						}
				}
		},"-" ,{
				id : "queryButton",
				text : '查看',
				disabled : true,
				iconCls : 'icon-ok',
				handler : function() {				
					var rows = $("#CLIdentificationTable").datagrid('getSelections');
					if (rows.length <= 0) {
						$.messager.alert('提示', '请选择记录', 'info');
					}else if(rows.length > 1) {
						$.messager.alert('提示', '每次只能查看一条信息', 'info');
					}else {
						$("#createWindow").window({
								title : "查看",
								href : '${ctx}/iden/queryInfo.do?id=' + rows[0].id,
								width : 710,
								height : 535,
								draggable:true,
								resizable:false,
								maximizable:false,
								minimizable:false,
								collapsible:false,
								inline:false,
								modal : true
							});
					}//end else
				}//end  handler
			},"-",{
				id : "exportExecl",
				text : '导出',
				iconCls : 'icon-excel',
				handler : function() {
					$("#CLIdentificationTable").datagrid("loading");
					var rows = $("#CLIdentificationTable").datagrid('getSelections');
					var params = $("#searchIdenForm").serialize();
					$.ajax({
						type : "POST",
						dataType:"json",
						url : "${ctx}/iden/exportExecl.do",
						data : getStrId(rows)+ "&" +params,
						success : function(response) {
							if(response.success){
								window.location.href ="${ctx}/excel/"+response.url;
								$("#CLIdentificationTable").datagrid("loaded");
							}else{
								$.messager.alert('提示', '操作失败');
								$("#CLIdentificationTable").datagrid("loaded");
							} 
						}
					});
				}
			}],
		columns : [[
					{title : 'ID',field : 'id',hidden : true,rowspan : 1,align : 'center',sortable:true},
// 					{title : '角色类型id',field : 'roleId',hidden:true,rowspan : 1,align : 'center'},
		            {title : '状态',field : 'status',rowspan : 1,align : 'center',width : '100',formatter:applyToStatus},
		            {title : '货主名称',field : 'name',rowspan : 1,align : 'center',width : '250'},
		            {title : '手机号',field : 'vmiUserPhone',rowspan : 1,align : 'center',width : '100'},
		            {title : '认证类型',field : 'type',rowspan : 1,align : 'center',width : '150',formatter:formartType},
		            {title : '角色类型',field : 'roleName',rowspan : 1,align : 'center',width : '150'},
		            {title : '业务员',field : 'driverName',rowspan : 1,align : 'center',width : '120'},
		            {title : '业务部门',field : 'driverCode',rowspan : 1,align : 'center',width : '120'},
		            {title : '客户区域',field : 'carNumber',rowspan : 1,align : 'center',width : '120'},
		            {title : '注册时间',field : 'registerTime',rowspan : 1,align : 'center',width : '120',formatter:localTime}
		           ]],
		/* onLoadSuccess : function(data) {
				$('#CLIdentificationTable').datagrid('doCellTip',{cls:{'background-color':'#FFFF99'},delay:500});   
		}, */
	    onSelect : isButtonEnable,
		onUnselect : isButtonEnable,
		onSelectAll: isButtonEnable,
		onUnselectAll: isButtonEnable
	});
	$('#CLIdentificationTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
});

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

function formartType(value,rowData,rowIndex){
	if(value==1){
		return '企业认证';
	}else if(value==2){
		return '个人认证';
	}else{
		return value;
	}
}

// function formartRoleType(value,rowData,rowIndex){
// 	if(value=="车主"){
// 		return '<span style="color:green;font-weight:bold;">车主</span>';
// 	}else{
// 		return value;
// 	}
// }

//批量删除
function toStrId(row) {
		var paramStr = '';
		for ( var i = 0; i < row.length; i++) {
			if (i == 0) {
				paramStr += 'ids=' + row[i].id;
			} else {
				paramStr += "&ids=" + row[i].id;
			}
		}
		return paramStr;
}

//datagird列的格式化
function applyToStatus(value,rowData,rowIndex){
	if(value==1){
		return '<span style="color:red;">待审核</span>';
	}else if(value==2){
		return '<span style="color:blue;">已驳回</span>';
	}else if(value==3){
		return '<span style="color:green;">已通过</span>';
	}else if(value==4){
		return '<span style="color:#DDDDDD;">已关闭</span>';
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

//判断按钮是否可用
	function isButtonEnable(){
		$("#queryButton").linkbutton('disable');
		var selections = $("#CLIdentificationTable").datagrid('getSelections');
		if(selections.length > 0){
			var isable = true;
			for ( var i = 0; i < selections.length; i++) {
				if( selections[i].status == 1  ){
					isable = false;
					break;
				}
			}
			if(isable){
				$("#queryButton").linkbutton('enable');
			}
		}
	}

</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
		 <div data-options="region:'north',title:'查询条件'" style="height: 100px; border: 0px;">
			<form id="searchIdenForm" method="post">
				<table style="margin: 0 auto; margin-top: 0px;">
					<tr>
						<td class="m-info-title">手机号：</td>
						<td>
							<input type="text" name="ftel" />
						</td>
						<td class="m-info-title">用户名：</td>
						<td>
							<input type="hidden" value="huo" name="roletype"/>
							<select id="userid" name="fusername" panelHeight="200">
								<option value="">请选择</option>
						</select>
						</td>
						<td class="m-info-title">认证状态：</td>
						<td>
							<select name="status" class="easyui-combobox" data-options="editable:false" style="width: 80px; height: 27px;" id="status">
								<option value="">请选择</option>
								<option value="1">待审核</option>
								<option value="2">已驳回</option>
								<option value="3">已通过</option>
								<option value="4">已关闭</option>
						   </select>
						</td>
<!-- 						<td class="m-info-title">角色类型：</td> -->
<!-- 						<td> -->
<!-- 							<select name="userRoleId" class="easyui-combobox" data-options="editable:false" style="width: 80px; height: 27px;" id="userRoleId"> -->
<!-- 								<option value="">请选择</option> -->
<!-- 								<option value="1">货主</option> -->
<!-- 								<option value="2">车主</option> -->
<!-- 							</select> -->
<!-- 						</td> -->
<!-- 						<td class="m-info-title">认证类型：</td> -->
<!-- 						<td> -->
<!-- 						    <select name="type" class="easyui-combobox"  data-options="editable:false" style="width: 80px; height: 27px;"  id="type"> -->
<!-- 								<option value="">请选择</option> -->
<!-- 								<option value="1">企业认证</option> -->
<!-- 								<option value="2">个人认证</option> -->
<!-- 							</select> -->
<!-- 					    </td> -->
						<td><a id="searchButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'">
			<table id="CLIdentificationTable" style="text-align:'center';"></table>
		</div>
		<div id="createWindow" style="margin: 0 auto; overflow:hidden;"></div>
		
	</div>
<script type="text/javascript">
	var temp,semp;
	//20秒查询一次数据是否有待审核的数据，如果有就发送提示信息
	function sendMessage(){
		$.ajax( {  
		     url:'${ctx}/iden/getIdenOfType.do',// 跳转到  
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
							msg:'<p style="color:red;">您有待审核的认证信息需要处理，请注意刷新。</p><audio id="mp3" controls="controls" autoplay="autoplay"><source src="${ctx}/pages/pc/identification/auit.mp3" type="audio/mp3" /></audio>',
							timeout:0,
							height:200,
							width:300,
							showType:'slide',
							showSpeed:2000
						});
		         	}else if(/chrome/.test(navigator.userAgent.toLowerCase())){//谷歌和谷歌内核的浏览器，包括360
						semp=$.messager.show({
							title:'我的消息',
							msg:'<p style="color:red;">您有待审核的认证信息需要处理，请注意刷新。</p><audio id="mp3" controls="controls" autoplay="autoplay"><source src="${ctx}/pages/pc/identification/auit.mp3" type="audio/mp3" /></audio>',
							timeout:0,
							height:200,
							width:300,
							showType:'slide',
							showSpeed:2000
						});
					}else{
						semp=$.messager.show({
							title:'我的消息',
							msg:'<p style="color:red;">您有待审核的认证信息需要处理，请注意刷新。</p><object id="mp3" controls="no" height="100" width="100" data="${ctx}/pages/pc/identification/auit.mp3"></object>',
							timeout:0,
							height:200,
							width:300,
							showType:'slide',
							showSpeed:2000
						});
					}
					temp=setTimeout("sendMessage()",60000);//一分钟查询一次
		         }else{
		         	temp=setTimeout("sendMessage()",60000);
		         }
		         setTimeout(function(){
					if($('#mp3')){$('#mp3').get(0).play();};
				},1000);
		      }  
		    
 		});
	}
	sendMessage();
</script>
</body>
</html>