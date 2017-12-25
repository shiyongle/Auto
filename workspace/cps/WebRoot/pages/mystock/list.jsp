<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ include file="/pages/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>备货订单</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/stockingAndTogoods.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/kkpager.css"  />
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery-1.8.3.min.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/kkpager.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript">
function two(obj,page){
var loadDel = layer.load(2);
   		$.ajax({
				type : "POST",
				url : "${ctx}/mystock/load.net?pageNo="+page,
				dataType:"json",
				data:obj,
				success : function(response) {
					  $(".alltr").remove();
					  $.each(response.list, function(i, ev) {
						  var  html =[
						    		'<tr height="113" class="alltr">'+
					                        '<td class="td1" width="368" height="113">'+
					                            '<p><input type="checkbox" class="ch_box"/><input  type="hidden" name="fid" value="',ev.fid,'"/></p>'+
					                            '<p><img src="${ctx}/css/images/tu.png" style="border:1px solid lightgray;" width="78" height="74"/></p>'+
					                            '<p class="explan">'+
									                 '<strong>',ev.fpdtname,'</strong>'+
													 '<br />订单号:',ev.fordernumber +
													 '<br />',ev.fpdtspec +
						    					'</p>'+
					                        '</td>'+
					                        '<td>',ev.fplanamount,'</td>'+
					                        '<td>',ev.suppliername,'</td>'+
					                        '<td>',ev.ffinishtime,'<br/>'+'<br />',ev.fconsumetime,'</td>'+
					                        '<td>',ev.fnumber,'</td>'+
					                        '<td id="fstate">',ev.fstate,'</td>'+
					                        '<td class="act">'+
					                            '<input type="button" value="修改" class="_upd" onclick="edit(\''+ev.fid+'\');" /><br/>'+
					                        	'<input type="button" value="删除" class="_del" style="color:#CC0000;" onclick="del(\''+ev.fid+'\');" /><br />'+
					                        '</td>'+
			                    	'</tr>'
						    	  ].join('');
						   $(html).appendTo("#deliverTool");
						});
						/********************************************循环添加TR结束******************************/
						kkpager.pno =response.pageNo;
						kkpager.total =Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
						kkpager.totalRecords =response.totalRecords;
						kkpager.generPageHtml({
							click : function(n){
								window.two($("#queryForm").serialize(),n);
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
								gopageButtonOkText : '确定',
								gopageAfterText : '页',
								buttonTipBeforeText : '第',
								buttonTipAfterText : '页'
							}
					    });
						/********************************************渲染分页主键结束******************************/
					$("#deliverTool tr #fstate").each(function(i) {
			                var t = $(this).text();
			                if(t==0){
			                	$(this).text('未接受');
			                }else if(t==1){
			                	$(this).text('已接受');
			                }else if(t==2){
			                	$(this).text('已接受');
			                }
            		});
					setTimeout(function(){
						layer.close(loadDel);
					},200); 
				}
		});
}
$(document).ready(function () {
	/*选项卡*/
	$(".menu li").click(function(){
		$(this).addClass("current");
		$(this).siblings().removeClass("current");
	});

	window.two($("#queryForm").serialize(),1);
	$("#searchDeliverapply").click(function() {
		window.two($("#queryForm").serialize(),1);
	});
	
	//导出
	$("#excelDeliverapply2").click(function() {
		var execlload = layer.load(2);
		$.ajax({
				type : "POST",
				dataType:"json",
				url : "${ctx}/execl/excelMystock.net?"+getIds(),
				data: $("#queryForm").serialize(),
				success : function(response) {
					if(response.success){
						layer.alert('操作成功！', function(alIndex){
							window.location.href ="${ctx}/excel/"+response.url;
							layer.close(alIndex);
							layer.close(execlload);
					   });
						
					}else{
						layer.alert('操作失败！', function(alIndex){
							layer.close(alIndex);
							layer.close(execlload);
					});
					}
				}
		});
    });
});

function dck_fstate(obj){
	$("#fstate").val(obj);
	window.two($("#queryForm").serialize(),-1);
}

function edit(obj){
	layer.open({
			    title: ['修改订单','background-color:#CC0000; color:#fff;'],
			    type: 2,
			    anim:true,
			  	area: ['550px', '350px'],
			    content: "${ctx}/mystock/edit.net?id="+obj
	});
}
function del(obj){
	if(confirm("确认删除吗？")){
	$.ajax({
			type : "POST",
			url : "${ctx}/mystock/delete.net?id="+obj,
			success : function(response) {
				if (response == "success") {
					layer.alert('操作成功！', function(alIndex){
							window.two($("#queryForm").serialize(),1);
							layer.close(alIndex);
					});
				}else {
					layer.alert('操作失败！', function(alIndex){
							layer.close(alIndex);
					});
				}
			}
	  });
	 }
}
//全选
function selectCheckBoxMystock(css){
		var a=document.getElementsByClassName(css);
		if(document.getElementById("check_mystock").checked){
			for(var i = 0;i<a.length;i++){
				  if(a[i].type == "checkbox") a[i].checked = true;
			}
		}else{
			for(var i = 0;i<a.length;i++){
				if(a[i].type == "checkbox") a[i].checked = false;
			}
		}
}
//将选中对象的流水号拼接
function getIds() {
	var paramStr = '';
	$('input:checkbox[class=ch_box]:checked').next().each(function(i){
		if (i == 0) {
			paramStr += 'fids=' + $(this).val();
		}else{
			paramStr += "&fids=" + $(this).val();
		}
	});
	return paramStr;
}

</script>
</head>

<body>
	<div id="nav">
        <div id="container">
        	<div class="instruct">
            	<p>订单监控&nbsp;&nbsp;&gt;&nbsp;&nbsp;备货订单</p>
            </div>
            <div class="menu">
            	<ul>
                	<li class="current"><a href="javascript:void(0);" onclick="dck_fstate(-1);">全部</a></li>
                    <li><a href="javascript:void(0);" onclick="dck_fstate(0);">未接收</a></li>
                    <li><a href="javascript:void(0);" onclick="dck_fstate(1);">已接收</a></li>
                </ul>
            </div>
            <div class="condition">
            	<form id="queryForm" >
            				  <input type="hidden" id="fstate" name="myStockQuery.fstate" value="-1"/>
                	下单时间：  <input type="text" class="for_date" id="fordertimeBegin"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'fordertimeEnd\')}'})" name="myStockQuery.fordertimeBegin" value="" /> &nbsp;-&nbsp;
							  <input type="text" class="for_date" id="fordertimeEnd"    onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'fordertimeBegin\')}'})" name="myStockQuery.fordertimeEnd" value="" />&nbsp;&nbsp;
                   	          交期：  <input type="text" class="for_date" id="fconsumetimeBegin"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'fconsumetimeEnd\')}'})" name="myStockQuery.fconsumetimeBegin" value="" /> &nbsp;&nbsp;
							  <input type="text" class="for_date" id="fconsumetimeEnd"    onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'fconsumetimeBegin\')}'})" name="myStockQuery.fconsumetimeEnd" value="" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                  关键词搜索：<input type="text" class="key_word" id="searchKey" name="myStockQuery.searchKey"/>&nbsp;&nbsp;&nbsp;&nbsp;
                              <input type="button" id="searchDeliverapply" value="搜索"     class="for_submit"/>
                              <input type="button" id="excelDeliverapply2"  value="导出订单" class="export"/>
                </form>
            </div>
            <div class="data">
            	<table id ="deliverTool" cellpadding="0" cellspacing="0" width="1260">
                    <tr class="tbl_tlt">
                        <td width="368"><p class="_chk"><input id="check_mystock" type="checkbox" class="ch_box" onclick="selectCheckBoxMystock('ch_box')"/>&nbsp;&nbsp;全选</p><p class="txt">产品</p></td>
                        <td width="115"><p style="height:25px;line-height:25px;border-left:1px solid lightgray;border-right:1px solid lightgray;">计划数量</p></td>
                        <td width="119">制造商</td>
                        <td width="275"><p style="height:25px;line-height:25px;border-left:1px solid lightgray;border-right:1px solid lightgray;">首次发货/备货周期</p></td>
                        <td width="149">备货单号</td>
                        <td width="110"><p style="height:25px;line-height:25px;border-left:1px solid lightgray;border-right:1px solid lightgray;">状态</p></td>
                        <td>操作</td>
                    </tr>
                </table>
            </div>
            <div id="kkpager"></div>
        </div>
    </div>
</body>
</html>
