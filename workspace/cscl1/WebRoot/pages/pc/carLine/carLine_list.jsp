<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %><!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>车辆排队</title>
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
	//查询select
	$("#factory").combobox({
		url : "${ctx}/select/getUseFactory.do",
		editable : true,
		width:120,
		mode: 'local',
		valueField: 'optionId',//提交值
		textField: 'optionName',//显示值
		validType:"comboxValidate['factory']",
		delay:2000,
		filter:searchItem
	});
	//车辆排队表	
	$('#vehicleQueueTable').datagrid({
		title : '车辆排队',
		loadMsg : '数据装载中......',
		url:"${ctx}/carLine/load.do",
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
		columns : [[
		        /* {title : '用户ID',hidden:true,field : '',rowspan : 1,align : 'center',width : '1 00'},  */
		        {title : '车牌号',field : 'carNum',rowspan : 1,align : 'center',width : '120'}, 
		        {title : '司机姓名',field : 'driverName',rowspan : 1,align : 'center',width : '120'}, 
		        {title : '车辆规格',field : 'carSpec',rowspan : 1,align : 'center',width : '150',formatter:carSpec},
		        {title : '车辆类型',field : 'carType',rowspan : 1,align : 'center',width : '150',formatter:carType},
		        {title : '司机联系方式',field : 'vmiUserPhone',rowspan : 1,align : 'center',width : '150'},
		        {title : '签到工厂',field : 'factory',rowspan : 1,align : 'center',width : '150'},
		        {title : '所属区域',field : 'activeArea',rowspan : 1,align : 'center',width : '150',formatter:area} ,
		        {title : '状态', field : 'fstatus',rowspan : 1,align : 'center',width : '150',formatter:fstatus}, 
		        {title : '签到时间',field : 'fsign_time',rowspan : 1,align : 'center',width : '150',formatter:fbilldateType}, 
		        {title : '操作人',field : 'foperator1',rowspan : 1,align : 'center',width : '120'},
		        {title : '操作时间',field : 'foperate_time',rowspan : 1,align : 'center',width : '150',formatter:fbilldateType},
		        {title : '备注',field : 'fremark',rowspan : 1,align : 'center',width : '150'}
		      ]]
	});
	$('#vehicleQueueTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
	//余额查询按钮点击事件
	$("#vehicleQueueButton").click(function() {
		$('#vehicleQueueTable').datagrid('load', JSON.parse(toJOSNStr($("#searchVehicleQueueForm").serializeArray())));
	});
});
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
	function fstatus(value,rowData,rowIndex){
		if(value==1){
			return "排队中";
		}
		if(value==2){
			return "已取消";
		}
		if(value==3){
			return "已配货";
		}
	}
	//车型 
	function carType(value,rowData,rowIndex){
		if(value==1){
			return "小面7座";
		}
		if(value==2){
			return "中面9座";
		}
		if(value==3||value==7||value==11||value==15||value==19){
			return "任意车型";
		}
		if(value==4||value==8||value==12||value==16||value==20){
			return "厢式货车";
		}
		if(value==5||value==9||value==13||value==17||value==21){
			return "高栏货车";
		}
		if(value==6||value==10||value==14||value==18||value==22){
			return "低栏货车";
		}
		return null;
	}
	//主要区域
	function area(value,rowData,rowIndex){
		if(value==1){
			return "苍南";
		}else if(value==2){
			return "瑞安";
		}else if(value==3){
			return "平阳";
		}else if(value==4){
			return "乐清";
		}else if(value==5){
			return "龙湾";
		}else if(value==6){
			return "瓯海";
		}else if(value==7){
			return "鹿城";
		}else if(value==8){
			return "台州";
		}else if(value==9){
			return "永嘉";
		}else{
			return "其他";
		}
	}
	//规格
	function carSpec(value,rowData,rowIndex){
		if(value == null){
			return " ";
		} else if(value == 1){
			return "面包车";
		} else if (value == 2) {
			return "2.5";
		} else if (value == 3) {
			return "4.2";
		} else if (value == 4) {
			return "5.2";
		} else if (value == 5) {
			return "6.8";
		} else if (value == 6){
			return "9.6";
		} else {
			return "规格有误";
		}
	}
	//时间补零
	function toDou(n){
		return n<10?"0"+n:""+n;
	}
	//表单信息转JSON
	function toJOSNStr(jObject) {
		var results = '{';
		jQuery.each(jObject, function(i, field) {
			if (i == 0) {
				results += '"' + field.name + '":"' + field.value.trim() + '"';
			} else {
				results += ',"' + field.name + '":"' + field.value.trim() + '"';
			}
		});
		return results + '}';
	}

</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true"
		style="border-left: 0px; border-right: 0px;">
		<div data-options="region:'north',title:'查询条件'" style="height: 120px; border: 0px;">
			<form id="searchVehicleQueueForm">
				<table style="margin: 20px auto;">
					<tr>
						<td class="m-info-title">
							操作时间:
						</td>
						<td>
							<input type="text" id="loadedTimeBegin" class="datePicker MtimeInput"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH',maxDate:'#F{$dp.$D(\'loadedTimeEnd\')}'})" name="operatedTimeBegin"  /> -
							<input type="text" id="loadedTimeEnd" class="MtimeInput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH',minDate:'#F{$dp.$D(\'loadedTimeBegin\')}'})" name="operatedTimeEnd"  />
						</td>
						<td class="m-info-title" align="right">状态:</td>
						<td>
							<select name="fstatus" id="fstatus" class="easyui-combobox" data-options="width:120,editable: false">
								<option value="">全部</option>
								<option value="1">排队中</option>
								<option value="2">已取消</option>
								<option value="3">已配货</option>
							</select>
						</td>
						<td class="m-info-title" align="right"> 签到工厂:</td>
						<td>
							<select id="factory" name="factory_id">
								<option value="-1">全部</option>
							</select>
						</td>
						<td class="m-info-title" align="right"> 所属区域:</td>
						<td>
							<select name="activeArea" id="activeArea" class="easyui-combobox" data-options="width:120,editable: false">
								<option value="">全部</option>
								<option value="1">苍南</option>
								<option value="2">瑞安</option>
								<option value="3">平阳</option>
								<option value="4">乐清</option>
								<option value="5">龙湾</option>
								<option value="6">瓯海</option>
								<option value="7">鹿城</option>
								<option value="8">台州</option>
								<option value="9">永嘉</option>
								<option value="10">其他</option>
							</select>
						</td>
						<td class="m-info-title" align="right"> 车辆规格:</td>
						<td>
							<select name="carSpec" id="carSpec" class="easyui-combobox" data-options="width:120,editable: false">
								<!-- 全部、6.8米、4.2米、2.5米 -->
								<option value="">全部</option>
								<option value="1">面包车</option>
								<option value="2">2.5米</option>
								<option value="3">4.2米</option>
								<option value="4">5.2米</option>
								<option value="5">6.8米</option>
								<option value="6">9.6米</option>
							</select>
						</td>
						<td class="m-info-title" align="right"> 关键字:</td>
						<td>
							<input name="keyWord" class="easyui-validatebox" placeholder="请输入车牌号或司机" style="width: 140px;"/>
						</td>
						<td>
							<a id="vehicleQueueButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>	
		<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'">
			<table id="vehicleQueueTable">
			</table>
		</div>
		<div id="createWindow" style="margin: 0 auto; overflow:hidden;" ></div>
	</div>
</body>
</html>
