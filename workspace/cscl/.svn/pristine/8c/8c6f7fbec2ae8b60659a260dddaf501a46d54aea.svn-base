<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>核销</title>
<script type="text/javascript">
	$(document).ready(function() {
		var fuserid=$('#fuserid').val();
		$('#verificationTable').datagrid({
			title : '账单',
			loadMsg : '数据装载中......',
			url:'${ctx}/verify/loadBill.do?fuserId='+fuserid,
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
// 			toolbar : [{
// 						text : '核销',
// 						iconCls : 'icon-ok',
// 						handler : function() {
// 						}
// 					}
// 				],
			columns : [[
				{title : 'ID',field : 'fid',hidden : true,rowspan : 1,align : 'center',sortable:true},
				{title : '货主ID',field : 'fcustId',hidden : true,rowspan : 1,align : 'center'},
				{title : '账单日期',field :'billDateString',rowspan : 1,align : 'center',width : '150'},
			    {title : '货主名称',field : 'fcustName',rowspan : 1,align : 'center',width : '120'},
			    {title : '金额',field : 'fbillAmount',rowspan : 1,align : 'center',width : '80'},
			    {title : '未收金额',field : 'funPayAmount',rowspan : 1,align : 'center',width : '80'},
			    {title : '是否结清',field : 'fverification',rowspan : 1,align : 'center',width : '80',formatter:isClear}
			]]
		});
		
		$('#verificationTable').datagrid('getPager').pagination({
			beforePageText : '第',//页数文本框前显示的汉字 
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
			onBeforeRefresh : function(pageNumber, pageSize) {
				$(this).pagination('loading');
			}
		});
		
	
		$("#saveVerificationButton").click(function() {
			var rows = $("#verificationTable").datagrid('getSelections');
			 if(rows.length<=0){
	    	 	$.messager.alert('提示', '请选择记录！', 'info');
		     }else if(rows.length>1){
		    	$.messager.alert('提示', '每次只能选择一条记录！', 'info');
		     }
			 else{
				 var freceiveId=$("#fid").val();
			 	$.ajax({
			 			type : "POST",
						url : '${ctx}/verify/verification.do?fbillId='+rows[0].fid+'&freceiveId='+freceiveId,
						success : function(response) {
		                    var jsRes = JSON.parse(response);
							if (jsRes.success=="true") {
		                        $.messager.alert('提示', '操作成功', 'info', function() {
		                        	$('#remainingSum').text(jsRes.remain);
		                        	$('#verificationTable').datagrid('reload');
		                        });
		                    } else {
		                        $.messager.alert('提示', '操作失败');
		                    }
						}	
					});
			 }
		});
	});
	
	function isClear(value,rowData,rowIndex){
		if(value==false){
			return '<span style="color:red">未结清</span>';
		}else if(value==true){
			return '已结清';
		}
	}
</script>
</head>
<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">

<div data-options="region:'north'" style="height: 50px; border: 0px;padding-top:10px">
<table>
				<tr>
					<td width="30">
					<input type="hidden" id="fid" value="${fid}" />
					<input type="hidden" id="fuserid" value="${fuserId}" />
					</td>
					<td class="m-info-title">付款人:</td>
					<td class="m-info-content">${fusername}</td>
					<td width="30"></td>
					<td class="m-info-title">金额:</td>
					<td class="m-info-content">${famount}元</td>
					<td width="30"></td>
					<td class="m-info-title">剩余金额:</td>
					<td class="m-info-content" id="remainingSum">${fremainAmount}元</td>
					<td width="30"></td>
					<td>
					<a id="saveVerificationButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">核销</a></td>
			    </tr>
</table>
</div>

		<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'">
			<table id="verificationTable"></table>
		</div>


</div>

</html>
