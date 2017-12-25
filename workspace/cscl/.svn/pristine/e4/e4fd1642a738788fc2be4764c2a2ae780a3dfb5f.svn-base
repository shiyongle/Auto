<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统禁用</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min2.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/datagrid-detailview.js" type="text/javascript" ></script>
<script src="${ctx}/pages/pc/js/messageTips.js" type="text/javascript" ></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#systemSettingsTable').datagrid({
		title : '系统配置',
		loadMsg : '数据装载中......',
		url:'${ctx}/system/refuseLoad.do',
		fit:true,
		fitColumns:false,
		nowrap : true,
		singleSelect:true,
		striped : true,    //为true,显示斑马线效果。
		multiSort : true,  //定义是否允许多列排序
		remoteSort : true, //定义从服务器对数据进行排序
		pagination : true, //为true,则底部显示分页工具栏
		rownumbers : true, //为true,则显示一个行号列
		frozenColumns : [[{field : 'id1',checkbox : true}]],
		pageSize : 20,//每页显示的记录条数，默认为10 
		pageList : [ 10, 20, 50, 100 ],//可以设置每页记录条数的列表 
		toolbar : [{
		        	  	id : "create",
						text : '设置',
						iconCls : 'icon-add',
						handler :function(){
				     		$("#window").window({
								title : "设置",
								href : '${ctx}/system/refuseCreate.do',
								width : 300,
								height:290,
								draggable:true,
								resizable:false,
								maximizable:false,
								minimizable:false,
								collapsible:false,
								modal : true
							});
						}
		          	},'-',{
				  		id : "edit",
						text : '修改',
						iconCls : 'icon-edit',
						handler : function() {
							var rows = $("#systemSettingsTable").datagrid('getSelections');
							if(rows.length<=0){
						    	$.messager.alert('提示', '请选择记录！', 'info');
						    }else if (rows.length > 1) {
								$.messager.alert('提示', '每次只能选择一条记录！', 'info');
							}else{								 
						    	 $("#window").window({
									title : "修改",
									href : '${ctx}/system/refuseEdit.do?id='+rows[0].fid,
									width : 300,
									height:290,
									draggable:true,
									resizable:false,
									maximizable:false,
									minimizable:false,
									collapsible:false,
									modal : true
								});
							}
						}
					} ,'-',{
						id : "del",
						text : '删除',
						iconCls : 'icon-remove',
						handler : function() {
							var rows = $("#systemSettingsTable").datagrid('getSelections');
							 if(rows.length<=0){
					    	 	$.messager.alert('提示', '请选择记录！', 'info');
						     }else{
							 	$.messager.confirm('提示', '确定要删除吗？', function(r){
							 		if(r){
							 			$.ajax({
								 			type : "POST",
								 			url : '${ctx}/sys/refuseDel.do?id='+rows[0].fid+'&ftype='+rows[0].ftype,
								 			dataType:'json',
											success : function(response) {
												if (response.success) {
							                        $.messager.alert('提示', '操作成功', 'info', function() {
							                        	$('#systemSettingsTable').datagrid('reload');
							                        });
							                    } else {
							                        $.messager.alert('提示', '操作失败');
							                    }
											}	
										});
							 		}
							 	})
							 }
						}
			        } 
			    ],
		columns :[
		           [
					{title : 'ID',field : 'fid',hidden : true,rowspan : 1,align : 'center'},				
					{title : '开始时间',field : 'fstart_time',rowspan : 1,align : 'center',width : '150',formatter:dateFormatter},
					{title : '结束时间',field : 'fend_time',rowspan : 1,align : 'center',width : '150',formatter:dateFormatter},
					{title : '提示内容',field : 'fkey',rowspan : 1,align : 'center',width : '200'},
					{title : '配置类型',field : 'ftype',rowspan : 1,align : 'center',width : '100',formatter:function (value,rowData,rowIndex){
						if(value == '0'){
							return '停止接单';
						}else if(value == '1'){
							return '上线维护';
						}
					}},
					{title : '创建人',field : 'fcreatorName',rowspan : 1,align : 'center',width : '100'},
					{title : '可用人',field : 'fvaluesName',rowspan : 1,align : 'center',width : '200'},
					{title : '创建时间',field : 'fcreate_time',rowspan : 1,align : 'center',width : '150',formatter:dateFormatter},
					{title : '修改时间',field : 'fupdate_time',rowspan : 1,align : 'center',width : '150',formatter:dateFormatter},
		           ]
		        ]
	});
	
	$('#systemSettingsTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
	
	//批量获取ids
	/* function toStrId(rows) {
		var paramStr = '';
		for ( var i = 0; i < rows.length; i++) {
			if (i == 0) {
				paramStr += 'ids=' + rows[i].fid;
			} else {
				paramStr += "&ids=" + rows[i].fid;
			}
		}
		return paramStr;
	}  */
	
	// 时间戳转换 
	function dateFormatter(value,rowData,rowIndex){
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
	
});

</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
		<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'" >
			<table id="systemSettingsTable"></table>
		</div>
		<div id="window" style="margin: 0 auto; overflow:hidden;"></div>
	</div>
</body>
</html>