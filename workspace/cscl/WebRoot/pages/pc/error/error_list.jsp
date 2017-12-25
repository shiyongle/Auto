<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>异常订单</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	//订单号下拉框
	$("#error_forderId").combobox({
		url : "${ctx}/select/getFinishOrders.do",
		mode: 'local',
		width:150,
		panelHeight:200,
		valueField: 'optionId',//提交值
		textField: 'optionName',//显示值
		filter:searchItem
	});
	//查询按钮点击事件
	$("#searchErrorButton").click(function() {
		$('#errorListTable').datagrid('load', JSON.parse(toJOSNStr($("#errorForm").serializeArray())));
	});
	$('#errorListTable').datagrid({
		title : '异常订单',
		loadMsg : '数据装载中......',
		url:'${ctx}/abnormity/load.do',
		fit:true,
		fitColumns:false,
		nowrap : true,
		striped : true,    //为true,显示斑马线效果。
		multiSort : true,  //定义是否允许多列排序
		remoteSort : true, //定义从服务器对数据进行排序
		pagination : true, //为true,则底部显示分页工具栏
		rownumbers : true, //为true,则显示一个行号列
		frozenColumns : [[{field : 'id1',rowspan : 1,checkbox : true}]],
		pageSize : 20,//每页显示的记录条数，默认为10 
		pageList : [ 10, 20, 50, 100 ],//可以设置每页记录条数的列表 
		toolbar : [
					{
					id:"controlButton",
					text : '异常调度',
					iconCls : 'icon-tip',
					handler : function() {
							var rows = $("#errorListTable").datagrid('getSelections');
			     			if(rows.length<=0){
			    	 		$.messager.alert('提示', '请选择记录！', 'info');
			    			}else if (rows.length > 1) {
							$.messager.alert('提示', '每次只能选择一条记录！', 'info');
				 			}else{
				 	$("#error_Window").window({
						title : "异常调度",
						href : '${ctx}/abnormity/errorControl.do?forderId='+rows[0].forderId+'&fabid='+rows[0].fid,
						width : 280,
					    height :120,
					    draggable:true,
						resizable:false,
						maximizable:false,
						minimizable:false,
						collapsible:false,
						inline:false,
						modal : true
					});
				 }
          }
      }
	],
		columns : [[
					{title : '异常ID',field : 'fid',hidden : true,rowspan : 1},
					{title : '订单ID',field : 'forderId',hidden : true,rowspan : 1},
		            {title : '订单编号',field : 'orderNumber',rowspan : 1,align : 'center',width : '200'},
		            {title : '发货异常',field : 'ftakeproblem',rowspan : 1,align : 'center',width : '100',formatter:applyToTake},
		            {title : '收货异常',field : 'frecproblem',rowspan : 1,align : 'center',width : '100',formatter:applyToRecp},
		            {title : '司机异常',field : 'fcarproblem',rowspan : 1,align : 'center',width : '100',formatter:applyToDriver},
		            {title : '反馈人',field : 'userName',rowspan : 1,align : 'center',width : '150'},
		            {title : '反馈时间',field : 'fcreatTime',rowspan : 1,align : 'center',width : '150',formatter:localTime},
		            {title : '异常图片',field : ' ',rowspan : 1,align : 'center',width : '340',formatter:applyToImg}
		           ]],
		           onLoadSuccess:function(){
		        	      //合并所有
		        	      //$(this).datagrid("autoMergeCells");
		        	      //指定列的相同行的数据合并
		        	      $(this).datagrid("autoMergeCells",['orderNumber']);
		        	    },
	});
	$('#errorListTable').datagrid('getPager').pagination({
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


/*发货异常*/
function applyToTake(value, rowData, rowIndex) {
		if (value == 1) {
			return '货品不符 ';
		} else if (value == 2) {
			return '少件';
		}else if (value == 3) {
			return '质量问题';
		}else if (value == 0) {
			return '';
		}
	}
/*收货异常*/
function applyToRecp(value, rowData, rowIndex) {
		if (value == 1) {
			return '拒收 ';
		} else if (value == 2) {
			return '其他';
		}
	}

/*司机异常*/
function applyToDriver(value, rowData, rowIndex) {
		if (value == 1) {
			return '晚点';
		} else if (value == 2) {
			return '破损';
		}else if (value == 3) {
			return '丢失';
		}else if (value == 4) {
			return '服务';
		}
		else if (value == 0) {
			return '';
		}
	}

	/*缩略图*/
	function applyToImg(value, rowData, rowIndex) {
		var html="";
		var fid = rowData.fid;
		$.ajax({
			type : "POST",
			async:false, 
			url : '${ctx}/abnormity/getPicUrls.do?fid='+fid+'&mode=cl_abnormity',
// 			dataType:"json",
// 			data:obj,
			success : function(response) {
				//JSONparse(response)
				var json=JSON.parse(response);				
				$.each(json.imgList, function(i, ev) {
                    html += '<div style="float:left;margin:5px;height:100px">'+
                    		/* '<a href="${cty}/'+ev.imgUrl+'" target="_blank">'+ */
                    		'<a href="${ctx}/pages/pc/error/viewImg.html?${cty}/'+ev.imgUrl+'" target="_blank">'+
                    		'<img src="${cty}/'+ev.imgUrl+'" width="100px"/></a></div>';	
				});
			}
		});
		return html;
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
			<div data-options="region:'north',title:'查询条件'" style="height: 100px; border: 0px;">
			       <form id="errorForm" method="post">
						 <table style="margin: 0 auto; margin-top: 0px;">
							<tr>
<!-- 								<td class="m-info-title">时间：</td> -->
<!-- 								<td > -->
<!-- 									<input type="text" id="errorStartTime" class="datePicker MtimeInput" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'errorEndTime\')}'})" name="errorStartTime" value="" /> - -->
<!-- 									<input type="text" id="errorEndTime" class="MtimeInput" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'errorStartTime\')}'})" name="errorEndTime" value="" /> -->
<!-- 								</td> -->
								<td class="m-info-title">订单编号:</td>
								<td>
									<select id="error_forderId" name="forderId"  style="width: 145px; height: 25px;" >
										<option value="-1">请选择</option>																	
									</select>								
									<a id="searchErrorButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
								</td>
							</tr>
						</table>
					</form>
			
			</div>
			<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'"  >
				<table id="errorListTable"></table>
			</div>
			<div id="error_Window" style="margin: 0 auto; overflow:hidden;"></div>
	</div>
	
	
<script type="text/javascript">
	var temp,semp;
	//60秒查询一次关于派车中的订单时间，距离派车时间相差半小时的时候，如果有就发送提示信息
	function sendMessage(){
		$.ajax( {  
		     url:'${ctx}/abnormity/haveNewAbnormal.do',// 跳转到  
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
							msg:'<p style="color:red;">有新异常订单，请注意刷新。</p>',
							timeout:0,
							height:200,
							width:300,
							showType:'slide',
							showSpeed:2000
						});
		         	}else if(/chrome/.test(navigator.userAgent.toLowerCase())){//谷歌和谷歌内核的浏览器，包括360
						semp=$.messager.show({
							title:'我的消息',
							msg:'<p style="color:red;">有新异常订单，请注意刷新。</p>',
							timeout:0,
							height:200,
							width:300,
							showType:'slide',
							showSpeed:2000
						});
					}else{
						semp=$.messager.show({
							title:'我的消息',
							msg:'<p style="color:red;">有新异常订单，请注意刷新。</p>',
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
		      }  
 		});
	}
	sendMessage();
</script>
</body>
</html>