<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ include file="/pages/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>收货情况</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/Goods_condition.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/kkpager.css"  />
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery-1.8.3.min.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/kkpager.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/js/highcharts/exporting.js"></script>
<script type="text/javascript">
function gridMyDeliveryTable(page){
var obj=$("#queryForm").serialize();
var loadDel = layer.load(2);
   		$.ajax({
				type : "POST",
				url : "${ctx}/mydelivery/load.net?pageNo="+page,
				dataType:"json",
				data:obj,
				success : function(response) {
					$(".alltr").remove();
					  $.each(response.list, function(i, ev) {
						  var  html =['<tr class="alltr">',
										'<td><input type="checkbox" class="_check"/><input  type="hidden" name="fid" value="',ev.fid,'"/></p>'+
										'<td><strong>',ev.cutpdtname,'</strong></td>',// 产品名称
										'<td>',ev.fboxspec,'</td>',//产品规格
										'<td id="fboxtype">',ev.fboxtype,'</td>',//订单类型
										'<td>',ev.saleorderNumber,'</td>',//收货单号
										'<td>',ev.fpcmordernumber,'</td>',//采购订单号
										'<td>',ev.fsuppliername,'</td>',//制造商
										'<td>',ev.famount,'</td>',//出库数量
										'<td>',ev.farrivetime,'</td>',//出库时间
										'<td>',ev.fmaterialspec,'</td>',//下料规格
										'<td>',ev.faddress,'</td>',//收货信息
										'<td>',ev.fdescription,'</td>',//备注
									  '</tr>'].join("");
						   $(html).appendTo("#mydeliverTool");
						});
						/********************************************循环添加TR结束******************************/
						kkpager.pno =response.pageNo;
						kkpager.total =Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
						kkpager.totalRecords =response.totalRecords;
						kkpager.generPageHtml({
							click : function(n){
										window.gridMyDeliveryTable(n);
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
					
						$("#mydeliverTool tr #fboxtype").each(function(i) {
				                var t = $(this).text();
				                if(t==0){
				                	$(this).text('纸箱');
				                }else if(t==1){
				                	$(this).text('纸板');
				                }
	            		});
					layer.close(loadDel); 
				}
		});
}
var line_chart;
$(document).ready(function () {
	/**************************load线形图-begin**********************************/
	Highcharts.setOptions({
            lang: {
            	  printChart:"打印图表",
                  downloadJPEG: "下载JPEG 图片" , 
                  downloadPDF: "下载PDF文档"  ,
                  downloadPNG: "下载PNG 图片"  ,
                  downloadSVG: "下载SVG 矢量图" , 
                  exportButtonTitle: "导出图片" 
            }
        });
	 var options_line = {
    		chart : {
				     renderTo : 'linear-x',
				     type : 'areaspline'
			 },
			 title: {text: '最近7天变动曲线图',x: -20 },
	         subtitle: {text: '收货',x: -20},
	         xAxis: {},
	         yAxis: {min : 0,title: {text: '出库数量'},labels: {format: '{value}'}  },
	         plotOptions: {
                    areaspline: {
                        dataLabels: {
                            enabled: true
                        }
                    }
             },
	         tooltip: {
                    formatter: function () {
                        return '<b>' + this.x + '</b><br/>' + this.series.name + ': ' + this.y + '只';
                    }
             },
             credits: {
                 enabled: false
             },
             series: [{
                 name: "收货变动数量"
             }]
    };
    $.ajax({
				type : "POST",
				url : "${ctx}/mydelivery/getLinear_x.net",
				dataType:"json",
				async:false,
				success : function (data) {
	                var xatrnames = [];
	                var yvalidators = [];
	                for (var i = 0; i < data.list.length; i++) {
	                    xatrnames.push([
	                    	data.list[i].farrivetime
	                    ]);
	                    yvalidators.push([
                            data.list[i].farrivetime,
                            parseInt(data.list[i].famounts)
	                    ]);
                	}
	                options_line.xAxis.categories = xatrnames;
	                options_line.series[0].data = yvalidators;
	                line_chart = new Highcharts.Chart(options_line);
                }
    });
    /**************************load线形图-end*****加载列表开始****************/
	window.gridMyDeliveryTable(1);
	$("#searchMyDeliverapply").click(function() {
		window.gridMyDeliveryTable(1);
	});
	//导出
	$("#excelMyDeliverapply").click(function() {
		var execlload = layer.load(2);
		$.ajax({
				type : "POST",
				dataType:"json",
				url : "${ctx}/execl/excelMyDelivery.net?"+getIds(),
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
	
	$("._icon").click(function(){
		var reduce ="${ctx}/css/images/reduce.png";
		if($("._icon").attr("src") ==reduce){
			$("#linear-x").hide();//缩放
			$("._icon").attr("src","${ctx}/css/images/zk.png");
		}else{
			$("#linear-x").show();//展开
			$("._icon").attr("src","${ctx}/css/images/reduce.png");
		}
	});
});
function selectCheckBox(css){
		var a=document.getElementsByClassName(css);
		if(document.getElementById("checkmdy").checked){
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
	$('input:checkbox[class=_check]:checked').next().each(function(i){
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
        	<div class="container_image">
            	<p class="heading"><span><strong>最近7天内收货曲线</strong></span> <img src="${ctx}/css/images/reduce.png" class="_icon"/></p>
                <div id="linear-x" class="bight">
                	
                </div>
            </div>
            <div class="container_data">
            	<p class="heading"><strong>收货列表</strong></p>
                <div class="info_search">
                	<input id="excelMyDeliverapply" type="button" value="导出Excel" class="export"/>
                	<form id="queryForm">
                                                            出库时间：  <input type="text" class="timer" id="farrivetimeBegin"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'farrivetimeEnd\')}'})" name="mydeliveryQuery.farrivetimeBegin" value="${begin}" /> &nbsp;-&nbsp;
							     <input type="text" class="timer" id="farrivetimeEnd"    onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'farrivetimeBegin\')}'})" name="mydeliveryQuery.farrivetimeEnd" value="${end}" />&nbsp;&nbsp;
		               	    关键字：<input type="text" class="info" id="searchKey" name="mydeliveryQuery.searchKey"/>
		                         <input id="searchMyDeliverapply" type="button" value="查询" class="_submit"/>
                    </form>
                </div>
               <div class="data">
	                <table id ="mydeliverTool" cellpadding="0" cellspacing="0" width="1280" >
	                	<tr class="no1">
	                    	<td width="37"><input id ="checkmdy" type="checkbox" class="_check" onclick="selectCheckBox('_check')"/></td>
	                        <td width="160">产品名称</td>
	                        <td width="130">产品规格</td>
	                        <td width="86">订单类型</td>
	                        <td width="120">收货单号</td>
	                        <td width="120">采购订单号</td>
	                        <td width="100">制造商</td>
	                        <td width="60">出库数量</td>
	                        <td width="130">出库时间</td>
	                        <td width="110">下料规格</td>
	                        <td width="121">收货信息</td>
	                        <td >备注</td>
	                    </tr>
	                </table>
                </div>
                <div id="kkpager"></div>
            </div>
        </div>
        <!--尾部引用
        <div id="foot">
        	<iframe src="all_foot.html" frameborder="0" scrolling="no" width="1280" height="40"></iframe>
        </div>
        iframe-->
    </div>
</body>
</html>
	

