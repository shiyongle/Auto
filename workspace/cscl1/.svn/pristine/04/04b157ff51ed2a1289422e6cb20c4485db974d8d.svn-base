<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>投诉受理</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/datagrid-detailview.js" type="text/javascript" ></script>
<script src="${ctx}/pages/pc/js/messageTips.js" type="text/javascript" ></script>
<script type="text/javascript">
$(document).ready(function() {
	//查询按钮点击事件
	$("#searchButton").click(function() {
		$('#complainTable').datagrid('load', JSON.parse(toJOSNStr($("#searchComplainForm").serializeArray())));
	});
	$('#complainTable').datagrid({
		title : '异常订单投诉',
		loadMsg : '数据装载中......',
		url:'${ctx}/usercomplain/load.do',
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
		        	  	id : "doComplainOrder",
						text : '异常审核',
						iconCls : 'icon-edit',
						handler :function(){
							var rows = $("#complainTable").datagrid('getSelections');
							if(rows.length<=0){
				    	 		$.messager.alert('提示', '请选择记录！', 'info');
							}else if(rows.length ==1){
						     	if(rows[0].fisdeal == 0){
						     		$("#docomplainOrder_Window").window({
										title : "异常审核",
										href : '${ctx}/usercomplain/audit.do?id='+rows[0].fid,
										width : 900,
										top:0,
										draggable:true,
										resizable:false,
										maximizable:false,
										minimizable:false,
										collapsible:false,
										modal : true
									});
						     	}else{
						     		$.messager.alert('提示','请选择未受理的记录','info');
						     	}
							}else{
								$.messager.alert('提示','每次只能选择一条记录','info');
							}
						}
		          },'-',{
					  		id : "exportComplainExecl",
							text : '导出',
							iconCls : 'icon-excel',
							handler : function() {
								$("#complainTable").datagrid("loading");
								$.ajax({
									type : "POST",
									dataType:"json",
									url : "${ctx}/usercomplain/exportExecl.do",
									success : function(response) {
										if(response.success){
											window.location.href ="${ctx}/excel/"+response.url;
											$("#complainTable").datagrid("loaded");
										}else{
											$.messager.alert('提示', '操作失败');
											$("#complainTable").datagrid("loaded");
										} 
									}
								});
							}
						}
			     ],
		/*数据表格展开开始*/	          
	    view: detailview,
	    detailFormatter: function(rowIndex, rowData){//函数返回行详细内容
	    	return "<div id='complainListDetail" + rowIndex + "'></div>";	    
	    },
	    onExpandRow: function(rowIndex, rowData){//点击展开一条数据时触发
	    	$("#complainListDetail"+rowIndex).datagrid({
				title : '',
				url : '${ctx}/usercomplain/getEntrysByParentId.do?fid='+rowData.fid,
				loadMsg : '数据装载中......',
				remoteSort : false,
				sortable : false, 
				onResize:function(){   
	                $("#complainTable").datagrid('fixDetailRowHeight',rowIndex);   
	            },   
	            onLoadSuccess:function(){   
	                setTimeout(function(){
	                	$("#complainTable").datagrid('fixDetailRowHeight',rowIndex);
	                },0);   
	            },
	            columns : [[
						  {title : '相关人',field : 'fuserName',width : '199',rowspan : 1,align : 'center'},
	    		          {title : '类型',field : 'ftype',width : '100',rowspan : 1,align : 'center',formatter:applyToType},
			   		      {title : '金额',field : 'famount',width : '100',rowspan :1,align : 'center'},
			   		 	  {title : '备注',field : 'fremark',width : '200',rowspan :1,align : 'center'}			   		   
			   		      ]],
			});
			$("#complainTable").datagrid('fixDetailRowHeight',rowIndex);
	    },
	    /*数据表格展开结束*/	
		columns :[
		           [
					{title : 'ID',field : 'fid',hidden : true,rowspan : 2,align : 'center'},
					{title : 'forderId',field : 'forderId',hidden : true,rowspan : 2,align : 'center'},					
					{title : '订单编号',field : 'forderNum',rowspan : 2,align : 'center',width : '200'},
					{title : '创建人',field : 'fcreate_name',rowspan : 2,align : 'center',width : '200'},
					{title : '处理原因',field : 'fremark',rowspan : 2,align : 'center',width : '100'},
					{title : '财务受理人',field : 'fdeal_name',rowspan : 2,align : 'center',width : '100'},
					{title : '是否受理',field : 'fisdeal',rowspan : 2,align : 'center',width : '100',formatter:fisdealStatus},
					{title : '司机处理',colspan:6},
					{title : '货主处理',colspan:3},
					{title : '投诉记录',field : 'fcomplainContent',rowspan : 2,align : 'center',width : '200'},
					{title : '沟通记录',field : 'fcomplainCommunicateContent',rowspan : 2,align : 'center',width : '200'},
					{title : '创建时间',field : 'fcreateTimeString',rowspan : 2,align : 'center',width : '100'}
		           ],
		           [
		            /*司机处理*/
		            {title : '司机',field : 'fdriverName',rowspan : 1,align : 'center',width : '100'},
		            {title : '好运司机',field : 'fluckDriver',rowspan : 1,align : 'center',width : '100',formatter:applyToCommon},
		            {title : '账号禁用',field : 'fdriverAccountDisable',rowspan : 1,align : 'center',width : '100',formatter:applyToCommon},
		            {title : '账号冻结',field : 'fdriverFreezeDisable',rowspan : 1,align : 'center',width : '100',formatter:applyToCommon},
		            {title : '扣除金额',field : 'fdriverFineAmount',rowspan : 1,align : 'center',width : '100'},
		            {title : '返利金额',field : 'fdriverRewardAmount',rowspan : 1,align : 'center',width : '100'},
		            /*货主处理*/
		            {title : '货主',field : 'fuserName',rowspan : 1,align : 'center',width : '100'},
		            {title : '退返运费',field : 'fshipperFineAmount',rowspan : 1,align : 'center',width : '100'},
		            {title : '好运券发放',field : 'fshipperRewardCouponsAmount',rowspan : 1,align : 'center',width : '100'}
		            ]
		        ],
		           onLoadSuccess : function(data) {
						$('#complainTable').datagrid('doCellTip',{cls:{'background-color':'#E8FFE8'},delay:500});   
				},
			rowStyler: function(index,row){
	       		if (row.fisdeal == 2){
	       			return 'background-color:#EFEFDA;color:#CC0000;';
	       		}else if(row.fisdeal == 1){
	       			return 'background-color:#EFEFDA;color:green;';
	       		}
	       	}
	});
	$('#complainTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
	
	/*订单下拉框combogrid*/
	$("#forderId").combogrid({
			width:200,
			panelWidth: 500,
			panelHeight: 200,
			idField: 'id',
			textField: 'forderNum',//选中显示的值
			valueField:'forderId',//提交值
			url: '${ctx}/usercomplain/getOrderDropdown.do',
			method: 'get',
			multiple: false,//可选择多行
			mode: 'local',
			filter: function(q, row){
					var opts = $(this).combogrid('options');
					if(row.fdriverName.indexOf(q)==0 || row.fuserName.indexOf(q)==0 ){
						return true;
					}
					if(row[opts.textField]){	
				return row[opts.textField].indexOf(q) == 0;
					}
			},
			columns: [[
				{field:'id',title:'id',width:80,align:'center',hidden : true},
				{field:'forderNum',title:'订单号',width:100,align:'center'},
				{field:'fuserName',title:'货主',width:50,align:'center'},
				{field:'fdriverName',title:'司机',width:50,align:'center'}
			]],
			fitColumns: true
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
function fisdealStatus(value,rowData,rowIndex){
	if(value == 0){
		return '未处理';
	}else if(value == 1){
		return '已审核';
	}else if(value==2){
		return '驳回';
	}
}
function applyToCommon(value,rowData,rowIndex){
	if(value==false){
		return '否';
	}else {
		return '是';
	}
}

function applyToType(value,rowData,rowIndex){
	if(value==0){
		return '扣款';
	}else {
		return '返利';
	}
}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
		<div data-options="region:'north',title:'查询条件'" style="height: 100px; border: 0px;">
	       <form id="searchComplainForm" method="post">
				 <table style="margin: 0 auto; margin-top: 20px;">
					<tr>
						<td class="m-info-title">单号:</td>
						<td>
							<select name="forderId" id="forderId">								
							</select>
						</td>
						<td class="m-info-title">订单状态:</td>
						<td>
							<select class="easyui-combobox" style="width: 100px; height: 20px;" name="fisdeal">
								<option value="">全部</option>
								<option value="0">未处理</option>
								<option value="1">已审核</option>
								<option value="2">驳回</option>
							</select>
							<a id="searchButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
						</td>
					</tr>
				</table>
			</form>			
		</div>
		<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'"  >
			<table id="complainTable"></table>
		</div>
		<div id="docomplainOrder_Window" style="margin: 0 auto; overflow:hidden;"></div>
	</div>
</body>
</html>