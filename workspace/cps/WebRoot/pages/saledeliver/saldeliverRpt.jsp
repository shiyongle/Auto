<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>

 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
 <link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
 <link rel="stylesheet" href="${ctx}/css/daterangepicker-bs3.css" /> 
 <link type="text/css" rel="stylesheet" href="${ctx}/js/jqwidgets-ver3.9.1/jqx.base.css" />
<link rel="stylesheet" href="${ctx}/css/jqpagination.css" />
<script src="${ctx}/js/jquery.jqpagination.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxcore.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxscrollbar.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxbuttons.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxlistbox.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxcombobox.js" ></script>
 <script src="${ctx}/js/moment.js" type="text/javascript"></script>
<script src="${ctx}/js/daterangepicker.js" type="text/javascript"></script>  
<title>对账单</title>
<style>
* {
	margin: 0px auto;
	padding: 0px;
	font: 12px 宋体;
}
.content {
	width: 850px;
	font: 12px 宋体;
}

.title {
	background-color: #d80c18;
	padding: 15px;
	color: #fff;
	 height:44px;*height:auto;/*ie7不兼容*/ 
		/* border-top-left-radius: 10px;
	border-top-right-radius: 10px; */
}

.filter {
	margin: 5px 0px 10px 10px;
}
.filter .filtercontent
{
float:left;width:234px;
}
.filter .filtercontent *
{
float:left;line-height:25px;display:inline-block;
}
.filter .querybutton {
	display: inline-block;
	border: 1px solid lightgray;
	background: 0;
	width: 50px;
	height: 25px;
	text-align: center;
	cursor: pointer;
}
 .tabletop
{
 height:353px;
} 
.tablediv {
	float: left;
 	border: 1px solid lightgray;
  	height:100%;
  	width:850px;*width:848px;
	position: relative;
}

.tablediv table {
	table-layout: fixed;
	width: 100%;
	border-collapse: collapse;
	text-align: center;
}

table tr {
	height: 27px;
}

.tdBorder {
	border-left: 1px solid lightgray;
	border-right: 1px solid lightgray;
}

.titleTR {
	height: 30px;
	background-color: #F0F0F0;
}
.selecttime-down {
    position: absolute; 
    top: 10px;
    right: 8px;
    height: 0;
    width: 0;
    overflow: hidden;
    font-size: 0;
    border-color: #333 transparent transparent transparent;
    border-style: solid;
    border-width: 6px;
    cursor: pointer;
}
.querytime{
	width:170px;
	height:25px;
	border: 1px solid lightgray;
	/*  float:left;  */
}
#kkpager {
	float: left;
	height: 22px;
	line-height: 22px;
	margin-left: 5px;
	margin-top: 5px;
}

.bottomPage {
	position: absolute;
	bottom: 0px;
	background-color: #f1f1f1;
	width: 100%;
	height: 30px;
}

.texttotalfamout {
	float: right;
	line-height: 22px;
	margin-right: 50px;
	margin-top: 5px;
}
.textprices {
	float: right;
	line-height: 22px;
	margin-right: 70px;
	margin-top: 5px;
}

.content1Tr td {
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}
input.querybutton:hover{color:white;background-color:red;border:red;}
.jqx-fill-state-normal{-moz-box-sizing: content-box; box-sizing: content-box; -ms-touch-action: none;font-family: Verdana,Arial,sans-serif; font-style: normal; font-size: 13px; border-color:#aaa; background: 0; }
</style>
<script type="text/javascript">
	 $(document).ready(function(){
		  	 $('.selecttime-down').click(function(){
				$(this).prev().click();
						return false;}) 
		  $('#querytime').on('apply.daterangepicker',function(){
			   loadSaledeliver(1);
		    }).daterangepicker({
				format: 'YYYY-MM-DD',
				showCustomBtn: true,
				separator : " 到 "
			});  
	
			$("#customerSelect").jqxComboBox({
			    width: 168, 
			    height: 23,
			    animationType:'fade',
				autoDropDownHeight:false,
				keyboardSelection : false,//下拉框键盘上下不触发选择
				autoComplete:true,
			    searchMode:'contains'//模糊搜索,
			   });
			$("#customerSelect").on('open', function (event) {
				var owner= event.owner;
				if(owner.listBox.items.length<9)
				{
					$("#customerSelect").jqxComboBox({autoDropDownHeight: true});
				}
		
				console.log($(this));
			});
			$('#customerSelect').on('select', function (event) {

                  	  loadSaledeliver(1);
            });
			loadSaledeliver(1,1);
			 $("#excelFtu").click(function() {
				var params=getftuIds();
				if($("#customerSelect").val()==="")
				{
					parent.layer.alert('请选择客户查询！', function(index){parent.layer.close(index);});
					return;
				}
				 $("#hideform").empty();
			        $("#hideform").attr("action","${ctx}/saledeliver/excelFTUstatement.net");  
			        var html=['<input type="hidden" name="fids" value="',params,'"/>'].join("");
			       var data=$("#searchForm").serializeArray();
			       $.each(data,function(i, field){
			    	   html+=['<input type="hidden" name="',field.name,'" value="',field.value,'"/>'].join("");
			      });
			       $(html).appendTo("#hideform");
			        //打开窗体，并post提交页面  
			        $("#hideform").attr("target","_blank");  
			        $("#hideform").submit(); 
			 });
	 }); 		
			
	 function loadSaledeliver(page,by){
	 	var loadDel = layer.load(2);
		var obj =$("#searchForm").serialize();	
		if($("#customerSelect").val()===""&&!by)
		{
			parent.layer.alert('请选择客户查询！', function(index){parent.layer.close(index);});
			layer.close(loadDel);  
			return;
		}
		$.ajax({
			type : "POST",
			url : "${ctx}/saledeliver/loadRpt.net?pageNo="+page,
			dataType:"json",
			data:obj,
			success : function(response) {
				$(".content1Tr").remove();
				$.each(response.list, function(i, ev) {
					  var  html =['<tr class="content1Tr">',
						 '<td><input type="checkbox"  data-productid ="',ev.fid,'" /></td>',
						 '<td >',(page*10+i-9),'</td>',
						 '<td>',ev.fprinttime,'</td>',
						 '<td style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">',ev.fname,'</td>',
						 '<td style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">',ev.fspec,'</td>',
						 '<td class="famount">',eval(!ev.famount||isNaN(ev.famount)?0:ev.famount),'</td>',
						 '<td>',eval(!ev.fprice||isNaN(ev.fprice)?0:ev.fprice),'</td>',
						 '<td class="fprices">',eval(!ev.fprices||isNaN(ev.fprices)?0:ev.fprices),'</td>',
						 '<td>',ev.fnumber,'</td>',
						 '<td style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">',ev.fdescription,'</td>',
						 '</tr>'].join("");
					   $(html).appendTo("#tbl1");
				});
				var famount=0,fprices =0;
				$("td.famount").each(function(i){
					famount += eval($(this).text());
				});
				$("td.fprices").each(function(i){
					fprices += eval($(this).text());
				});
				var totalPage = Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
					$("#bottomPage1").empty();
					var pagehtml =['<div class="pagination" style="float:left;margin:5px 0px 0px 5px;">',
					 '<a href="#" class="first" data-action="first">&laquo;</a>',
					 '<a href="#" class="previous" data-action="previous">&lsaquo;</a>',
					 '<a style="font:14px 宋体;line-height:21px;">第</a>',
					 '<input type="text" value=',page,' readonly="readonly"  data-current-page="',page,'" data-max-page="',totalPage,'" style="width:40px;line-height:20px;"/>',
					 '<a style="font:14px 宋体;line-height:21px;">页</a>',
					 '<a href="#" class="next" data-action="next">&rsaquo;</a>',
					 '<a href="#" class="last" data-action="last">&raquo;</a>',
					 '</div>',
					 '<span id="kkpager">共',totalPage,'页/',response.totalRecords,'条数据</span>',
					 '<span class="texttotalfamout">总金额：',(Math.round(fprices * 1000)/ 1000),'</span>',
					 '<span  class="textprices" >总数量：',(Math.round(famount * 1000)/ 1000),'</span>'].join("");
									 $(pagehtml).appendTo("#bottomPage1");
									 $('.allCheck').click(function(){
											var me = $(this);
											var checked = me.attr('checked')==undefined?false:true;
											me.parents("table").find('input[type=checkbox]').attr('checked',checked);
										});	
									$('.pagination').jqPagination({
							   		   paged: function(page) {
									        // do something with the page variable
											loadSaledeliver(page);
							  		  }
									
									});
			
				layer.close(loadDel);  
				}
			});
	}
	//将选中对象的流水号拼接
	 function getftuIds() {
	 	var fid = '';
		$('input:checkbox[class!=allCheck]:checked').each(function(i,e){
			if($(this).data("productid")){
				fid += $(this).data("productid");
				if(i<$('input:checkbox[class!=allCheck]:checked').length-1){
					fid += ",";
				}
			}
		});
		return fid;
	 }
</script>
</head>
<body>
<div class="content">
	<div >
	<!-- 	 <p class="title">对账单</p>  -->
		<form  id="searchForm" class="filter">
			 <div class="filtercontent" >
			 <span >客户名称:</span>
			<select  id="customerSelect" name="fcustomerid" >
			  <c:forEach var="customer" items="${customer}">
			   <option value="${customer.fid}">${customer.fname}</option>
			</c:forEach>
			</select>	
			</div>
			<div class="filtercontent" style="height:25px;">
			  <div style="position:absolute;"> <span>出库时间:</span>
			<input type="text"  class="querytime" id="querytime" name="timeQuantum" readonly />
			<i class="selecttime-down" style=" position:absolute; border-width: 6px; border-color: rgb(51, 51, 51) transparent transparent;"></i>
			 </div>  
			</div>
		  <input type="button" value="查询" class="querybutton"  onclick="loadSaledeliver(1)"/>
		  <input type="button" value="导出Execl" class="querybutton" style="width:80px;" id="excelFtu" /> 
		</form>
	</div>
	<div class="tabletop">
			<div class="tablediv">
				<table id ="tbl1" >
					<tr class="titleTR">
						<td width="30px"><input type="checkbox" class="allCheck" style="vertical-align:middle;" /></td>
						<td width="38px" class="tdBorder">序号</td>
						<td width="95px">出库时间</td>
						<td width="150px" class="tdBorder">产品名称</td>
						<td width="110px">规格</td>
						<td  width="60px" class="tdBorder">数量</td>
						<td width="70px" > 单价</td>
						<td width="80px" class="tdBorder">金额</td>
						<td width="95px">出库单编号</td>
						<td width="100px" style="border-left:1px solid lightgray;">备注</td>

					</tr>
					<tr style="height:10px"><td colspan="10"></td></tr>
				</table>
				  <div id = "bottomPage1" class="bottomPage">
			</div>
	</div>
</div>
<form action="#" method="post" id="hideform" target="_blank" style="display:none" >  
 </form>  
</body>
</html>