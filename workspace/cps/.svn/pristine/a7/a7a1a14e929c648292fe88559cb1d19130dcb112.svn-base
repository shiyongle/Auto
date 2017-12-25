<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx}/css/kkpager.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="${ctx}/css/myReceipt_list.css" rel="stylesheet"/>
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/css/daterangepicker-bs3.css" />
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<script src="${ctx}/js/kkpager.js" type="text/javascript" language="javascript"></script>
<script src="${ctx}/js/moment.js" type="text/javascript"></script>
<script src="${ctx}/js/daterangepicker.js" type="text/javascript"></script>
<title>我的收货</title>
<script type="text/javascript">
	$(document).ready(function(){
		$('.queryField').keyup(function(e){
			  if(e.keyCode==13){
				  $(this).next().click();
			  }
		});
		$('.select-down').click(function(){
		
$(this).prev().click();
		
			return false;})
		window.getHtmlLoadingBeforeHeight();
		
		$('#allCheck').click(function(){
			var checked = $(this).attr('checked')==undefined?false:true;
			$('input[type=checkbox]').attr('checked',checked);
		})
		
// 		$('#querytime').change(function(){
// 			window.gridFirstproducteMandTable(1);
// 		});
		
	    $('#querytime').on('apply.daterangepicker',function(){
	    	window.gridFirstproducteMandTable(1);
	    }).daterangepicker({
			format: 'YYYY-MM-DD',
			showCustomBtn: true,
			separator : " 到 "
		});
		window.gridFirstproducteMandTable(1);
	})
	
   function refresh(){
		window.gridFirstproducteMandTable(1);
	}
	
	function seachLoad(){
		window.gridFirstproducteMandTable(1);
	}
	
	function gridFirstproducteMandTable(page){
		var loadDel = layer.load(2);
		var obj =$("#searchForm").serialize();
			$.ajax({
				type : "POST",
				url : "${ctx}/mydelivery/loadNew.net?pageNo="+page,
				dataType:"json",
				data:obj,
				//async: false,
				success : function(response) {
					 $(".spaceTr").remove(); 
					$(".supplierTr").remove();
					$(".contentTr").remove();
					$.each(response.list, function(i, ev) {
						  var  html =[
						        '<tr height="5px" class="spaceTr"></tr>',	        
						        '<tr class="supplierTr">',
								'<td colspan="2">制造商：',ev.fsuppliername,'</td>',
								'<td colspan="2">订单类型：',ev.fboxtype==1? '纸板订单': '纸箱订单','</td>',
								'<td colspan="4">收货单号：',ev.saleorderNumber,'</td>',
						        '</tr>',
								'<tr class="contentTr">',
								'<td><input type="checkbox" /></td>',
								'<td>',ev.cutpdtname,'</td>',
								'<td>',ev.fboxspec,'</td>',
								'<td>',ev.fmaterialspec,'</td>',
								'<td>',ev.famounts,'</td>',
								'<td>',ev.farrivetime,'</td>',
								'<td>',ev.faddress,'</td>',
								'<td>',ev.fdescription,'</td>',
							'</tr>'].join("");
						   $(html).appendTo("#tbl1");
					});
					kkpager.pno =response.pageNo;
					kkpager.total =Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
					kkpager.totalRecords =response.totalRecords;
					kkpager.generPageHtml({
						click : function(n){
							window.gridFirstproducteMandTable(n);
							this.selectPage(n);
						},
						pno : response.pageNo,//当前页码
						total : Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize)),//总页码
						totalRecords : response.totalRecords,//总数据条数
						lang : {
							prePageText : '上一页',
							nextPageText : '下一页',
							totalPageBeforeText : '共',
							totalPageAfterText : '页',
							totalRecordsAfterText : '条数据',
							gopageBeforeText : '转到',
							gopageButtonOkText : 'GO',
							gopageAfterText : '页',
							buttonTipBeforeText : '第',
							buttonTipAfterText : '页'
						}
				    });
					//子页面设置父级iframe高度						
					$('.personMind').unbind('click');
					window.getHtmlLoadingAfterHeight();
					layer.close(loadDel); 
				}
		});
	}
	
</script>
</head>
<body>
<div id="myReceipt">
	<div class="topDiv">
		<a href ="javascript:void(0);">平台首页</a> > <a href ="javascript:void(0);">我的业务</a> > <a href ="javascript:void(0);">我的收货</a>
		<div align="right">
			<input type="button" value="刷新" class="refreshButton" onclick="refresh()"/>
		</div>
		<hr style="height:1px;border:none;border-top:1px solid #C9C9C9;margin-top:4px;">
	</div>
	<form action="" method="post" id="searchForm">
		<div style="position:relative;width:234px;height:26px;display:inline-block;float:left;">
			<input type="text" placeholder="出库时间" class="querytime" id="querytime" name="mydeliveryQuery.timeQuantum" readonly />
			<i class="select-down" style="position:absolute;border-width: 6px; border-color: rgb(51, 51, 51) transparent transparent;"></i>
		</div>
		<input type="text" placeholder="搜索" class="queryField"  id="searchKey" name="mydeliveryQuery.searchKey"/>
		<input type="button" value="" class="_submit" id="searchButton" onclick="seachLoad()" >
	</form>
	<div id="content">
		<table style="border-collapse:collapse;" id="tbl1">
			<tr id="tableTitle">
				<td style="width:62px;"><input type="checkbox" style="vertical-align:middle;" id="allCheck"/>&nbsp;全选</td>
				<td class="tdBorder" style="width:175px;">名称</td>
				<td style="width:128px;">纸箱规格</td>
				<td class="tdBorder" style="width:128px;">下料规格</td>
				<td style="width:128px">出库数量</td>
				<td class="tdBorder" style="width:128px;">出库时间</td>
				<td style="width:175px;">收货地址</td>
				<td class="tdBorder" style="width:128px;border-right:none;">备注</td>
			</tr>
			<tr height="5px"></tr>
<!-- 			<tr class="supplierTr">
				<td colspan="2">制造商：东经包装</td>
				<td colspan="2">订单类型：XXX XXX</td>
				<td colspan="4">收货单号：496465521486</td>
			</tr>
			<tr class="contentTr">
				<td><input type="checkbox" /></td>
				<td>东力3号</td>
				<td>50*50*50</td>
				<td>150*50</td>
				<td>200片</td>
				<td>2015-11-9</td>
				<td>浙江温州瓯海区东经一路1号</td>
				<td>请在下午1点到3点送货</td>
			</tr> -->
		</table>								
	</div>
		  <div id="kkpager" align="right" ></div>
</div>
</body>
</html>