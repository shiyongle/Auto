<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %><!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>固定距离设置</title>
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
			$(document).ready(function() {
				//查询按钮点击事件
				$("#searchDistanceButton").click(function() {
					$('#CLDistanceTable').datagrid('load', JSON.parse(toJOSNStr($("#searchDistanceForm").serializeArray())));
				});
				$('#CLDistanceTable').datagrid({
					title : '固定距离设置',
					loadMsg : '数据装载中......',
					url : '${ctx}/distance/load.do',
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
					toolbar : [{
							id : "save",
							text : '新增',
							iconCls : 'icon-add',
							handler : function(){
									$("#createWindow").window({
										title : "新增",
										href : '${ctx}/distance/distanceSave.do',
										width :480,
									    height :160,
										modal : true
									});
							}
					}, '-',{
							id : "del",
							text : '删除',
							iconCls : 'icon-remove',
							handler : function() {
								var rows = $('#CLDistanceTable').datagrid('getSelections');
								$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
									 if(r){
											if(rows.length<=0){
										    	 	$.messager.alert('提示', '请选择记录！', 'info');
											     }
											 else{
									 			$.ajax({
								 						type : "POST",
														url : '${ctx}/distance/del.do?'+toStrId(rows),
														success : function(response) {
															if (response == "success") {
								                      			$.messager.alert('提示', '操作成功', 'info', function() {
								                        			$('#CLDistanceTable').datagrid('reload');
								                      			 });
								                      			} else {
								                        			$.messager.alert('提示', '操作失败');
								                   		 	 }
														}	
													});
												 }
									     	}
							       	});
							}
					}, '-',{
						id:"Edit",
						text:'编辑',
						iconCls : "icon-edit",
						handler : function() {
							var rows = $("#CLDistanceTable").datagrid('getSelections');
						     if(rows.length<=0){
						    	 $.messager.alert('提示', '请选择记录！', 'info');
						     }else if (rows.length > 1) {
									$.messager.alert('提示', '每次只能选择一条记录！', 'info');
							 }else{
						    	 $("#createWindow").window({
									title : "编辑信息",
									href : '${ctx}/distance/distanceEdit.do?id='+rows[0].fid,
									width :485,
								    height :170,
									modal : true
								});
							} 
						}
					}],
					columns : [ [ 			
							{title : '客户名称',field : 'fcustomer',rowspan : 1,align : 'center',width : '160'},
							{title : '发货地址',field : 'faddressDel',rowspan : 1,align : 'center',width : '300'},
							{title : '发货地址ID',hidden:true,field : 'faddressDel_id',rowspan : 1,align : 'center',width : '300'},
						    {title : '卸货地址',field : 'faddressRec',rowspan : 1,align : 'center',width : '300'},
						    {title : '卸货地址ID',hidden:true,field : 'faddressRec_id',rowspan : 1,align : 'center',width : '300'},
						    {title : '公里数',field : 	'fmileage',rowspan : 1,align : 'center',width : '300',formatter:addKm},
						    {title : '创建时间',field : 'fcreate_time',rowspan : 1,align : 'center',width : '160',formatter:fbilldateType}, 
						    {title : '创建人',field : 'fcreater',rowspan : 1,align : 'center',width : '160'},
						    {title : '修改时间',field : 'fedit_time',rowspan : 1,align : 'center',width : '160',formatter:fbilldateType}, 
						    {title : '修改人',field : 'feditor',rowspan : 1,align : 'center',width : '160'}
						    
				     ] ]
				});
				
				$('#CLDistanceTable').datagrid('getPager').pagination({
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
		// 时间戳转换 
		function fbilldateType(value,rowData,rowIndex){
				var d = new Date(value);    //根据时间戳生成的时间对象
				var date = (d.getFullYear()) + "-" + toDou(d.getMonth() + 1)+"-"+toDou(d.getDate())+" "+toDou(d.getHours())+":"+toDou(d.getHours())+":"+toDou(d.getSeconds());
				return date;
		}
		function addKm(value,rowData,rowIndex){
				return value+'km';
		}
		//时间补零
		function toDou(n){
			return n<10?"0"+n:""+n;
		}
		</script>
</head>
<body>
 	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
		<div data-options="region:'north',title:'查询条件'" style="height: 100px; border: 0px;">
			 <form id="searchDistanceForm" method="post">
				<table style="margin: 0 auto; margin-top: 20px;">
					<tr>	
						<td class="m-info-title">客户名称：</td>
						<td>
							<input type="text" id="search_fcustomer"  class="Msearch-fcustomer" name="fcustomer"  style="width:200px;"/>
							<a id="searchDistanceButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'"><table id="CLDistanceTable">			
			
		</table></div>
		<div id="createWindow" style="margin: 0 auto;" ></div>
	</div>
</body>
</html>
