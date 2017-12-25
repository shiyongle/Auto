<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
  <meta charset="utf-8">
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
	<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
    <title>司机--首页</title>
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/index_customer_driver.css" rel="stylesheet">
    <script src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>    
    <script src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/echarts.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/index_customer_driver.js"></script>
    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
<body>
<%@ include file="/pages/pcWeb/top/top.jsp"%>
	<div class="container index_container">
		<div class="month_bg">
			<div><span id="thisMonth"></span>月</div>
		</div>
		
		<div class="row line1">
			<div class="col-xs-12">
				2016/5/01-2016/5/31
			</div>
		</div>
		
		
		<div class="row" style="border-bottom: 1px solid #ddd;">
			<div class="col-md-3 demo">
				<div class="text-center demo_in">已成单（次）</div>
				<div class="text-center redClolr num" id="valEndOrder"></div>
			</div>
		 	
			<div class="col-md-3 demo">
				<div class="text-center demo_in">被评价（次）</div>
				<div class="text-center redClolr num" id="valRate"></div>
			</div>
			<div class="col-md-6 text-center djboy">
				<img src="${ctx}/pages/pcWeb/css/images/index/djboy.png" />
			</div>
		</div>
		
		<div class="row data">
			<div class="col-md-8 border text-center">
					<div id="data_pic"></div>
			</div>
			<div class="col-md-4 progress1">
					<div id="data_radius"></div>
			</div>
		</div>
		
	</div>
	
	<div class="container index_container">
		<div class="row">
			<div class="col-xs-12 text-center pay">
<!-- 				充值: <span class="redClolr">2747.50</span> 元　　　支出: <span class="green">2747.50</span> 元 -->
<!-- 			收入: <span class="green">2747.50</span> 元  -->
			</div>
		</div>
		
		
			<div class="row">
			<div class="col-xs-10 col-xs-offset-1">
				<form class="form-inline">
					<div class="row">
					<div class="col-md-6">
					<span>时间：</span>
					<input type="text" class="form-control" id="time1" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'time2\')}'})" placeholder="开始时间">--
    				<input type="text" class="form-control" id="time2" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'time1\')}'})" placeholder="结束时间">
					</div>
					
					<div class="col-md-4" id="pay_time">
					<span>周期：</span>
					<a href="javascript:void(0)" class="btn" data-time="1">一周</a>
					<a href="javascript:void(0)" class="btn" data-time="2">一月</a>
					<a href="javascript:void(0)" class="btn" data-time="3">一年</a>
					</div>
					
					<div class="col-md-2 text-right redClolr">
						<a href="#" class="redClolr">搜索</a>　|　<a href="#" class="redClolr">导出EXCLE</a>
					</div>
					
					</div>
				</form>
			</div>
		</div>
		
		<!--表格-->
		<div class=" row">
			<div class="col-xs-12 col-sm-10 col-sm-offset-1">				
  			<table class="table table-hover" id="wallet_index_table">
<!--   				<tr> -->
<!--   					<td>时间</td> -->
<!--   					<td>金额（元）</td> -->
<!--   					<td>类型</td> -->
<!--   					<td>　</td> -->
<!--   				</tr> -->
  				
<!--   				<tr> -->
<!--   					<td>2016-05-30  20:10</td> -->
<!--   					<td class="redClolr">1000.00</td> -->
<!--   					<td>支出</td> -->
<!--   					<td><a href="#" class="blue">查看</a></td> -->
<!--   				</tr> -->
<!--   				<tr> -->
<!--   					<td>2016-05-30  20:10</td> -->
<!--   					<td class="redClolr">1000.00</td> -->
<!--   					<td>支出</td> -->
<!--   					<td><a href="#" class="blue">查看</a></td> -->
<!--   				</tr> -->
  				
  			</table>
			</div>
		</div>
		
		
	</div>
	<%@ include file="/pages/pcWeb/foot/foot.jsp"%>	
	<script src="${ctx}/pages/pcWeb/js/page.cscl.js" ></script>
	<script type="text/javascript" src="${ctx}/pages/pcWeb/js/My97DatePicker/WdatePicker.js"></script>
</body>
</html>
<script type="text/javascript">
var date=new Date();
var thisMonth=date.getMonth()+1;
var circleData,circleDataArr=[];//饼状图数据
var url="${ctx}/app/statement/loadStatement.do?"+"&pageNum="+1+"&pageSize="+10;
$(function(){
	$("#thisMonth").text(thisMonth);	
	$("#nav_index").addClass("active");
	//成单，评价数
	$.ajax({
		type:"post",
		url:"${ctx}/pcWeb/select/userEndOrder.do",
		dataType:"json",
		success:function(response){
			$("#valEndOrder").text(response.data.endOrder);
			$("#valRate").text(response.data.rateTimes);
		}
	});
	//司机收支明细
	var html_table_head='<tr><td>时间</td><td>金额（元）</td><td>类型</td><td>　</td></tr>';
	$.ajax({
		type:"post",
		url:url,
		dataType:"json",
		success:function(response){
// 			console.log(response);
			$("#wallet_index_table").empty();
			var html=html_table_head;   				
			$.each(response.data, function(i,e){
				var type;
				switch(e.fbusiness){//判断类型
				case 1:type="收入";break;
				case -1:type="支出";break;
				case 3:type="";break;
				default:break;						
				}
				html+='<tr>'+
					'<td>'+e.createTimeString+'</td>'+
					'<td class="redClolr">'+e.famount+'</td>'+
					'<td>'+type+'</td>'+
//隐藏掉查看 			'<td><a href="javascript:void(0)" class="blue" class="view_detail">查看</a></td>'+
					'<td></td>'+      					
					'</tr>';
			});
			$("#wallet_index_table").append(html);
		}
	});
	
	//折线图
	$.ajax({
			type:"post",
			url:"${ctx}/pcWeb/select/getOrderCountById.do?type=1",	
			dataType:"json",
			success:function(response){						
			if(response.success=="true"){
				var dom = document.getElementById("data_pic");
				var myChart = echarts.init(dom);//初始化一个数据图
				var app = {};
				option = null;
				var axisData =["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"];//横坐标
				var num=[];
				
				$.each(response.data,function(i,e){			
				num.push(Number(response.data[i]));
				})
				
				var data = axisData.map(function (item, i) {	
//		 			return Math.round(Math.random() * 1000 * (i + 1));
					return num[i];
				});			
				var links = data.map(function (item, i) {
				    return {
				        source: i,
				        target: i + 1
				    };
				});
				links.pop();
				option = {
				    title: {
				        text: '一路好运订单数量统计折线图'
				    },
				    tooltip: {},
				    xAxis: {
				        type : 'category',
				        boundaryGap : false,
				        data : axisData
				    },
				    yAxis: {
				        type : 'value'
				    },
				    series: [
				        {
				            type: 'graph',
				            layout: 'none',
				            coordinateSystem: 'cartesian2d',
				            symbolSize: 30,
				            label: {
				                normal: {
				                    show: true
				                }
				            },
				            edgeSymbol: ['circle', 'arrow'],
				            edgeSymbolSize: [2, 5],
				            data: data,
				            links: links,
				            lineStyle: {
				                normal: {
				                    color: '#666'
				                }
				            }
				        }
				    ]
				};
				myChart.setOption(option);
			}
			}
		});

	
	//饼状图数据
	$.ajax({
		type:"post",
		url:"${ctx}/pcWeb/select/findOrderByType.do?type=2",
		dataType:"json",
		success:function(response){
			circleData=response.data;
			for(var key in circleData){
				var circleDataObj={};
				circleDataObj.name=key;
				circleDataObj.value=circleData[key];
				circleDataArr.push(circleDataObj);				
			}
			var dom1 = document.getElementById("data_radius");
			var myChart1 = echarts.init(dom1);//初始化一个数据图
			var app1 = {};
			option1 = null;
			option1 = {
					tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
			    series : [
			        {
			            name: '类型',
			            type: 'pie',
			            radius: '55%',
			            data:circleDataArr,
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                }
			            }
			        }
			    ]
			};
			myChart1.setOption(option1);

		}
	});	
})
</script>


<script type="text/javascript">
//交易记录
var size=10;//一页显示多少条
var total;//总共多少条记录
var html_table_head='<tr><td>时间</td><td>金额（元）</td><td>类型</td><td>　</td></tr>';
var url="${ctx}/pcWeb/pcWebPay/UsePayAccountStatement.do?pagesize="+size+"&number=1";
pageOrderSearch(total,size,url);//支付分页，传入总数量(total)，每页数量(size)
$(function(){	
	//周期点击切换
	$("#pay_time a").on("click",function(){
		$("#time1").val("");
		$("#time2").val("");
		$(this).addClass("btn-danger btn-a").siblings("a").removeClass("btn-a btn-danger");
		var key=$(this).data("time");
		var url="${ctx}/pcWeb/pcWebPay/UsePayAccountStatement.do?pagesize="+size+"&number=1"+"&key="+key;
		pageOrderSearch(total,size,url);
	})
	
	//按时间筛选交易记录
	$("#time1").blur(function(){
		$("#pay_time a").each(function(){
			$(this).removeClass("btn-a btn-danger");
		})
		var time1=$("#time1").val();
		var time2=$("#time2").val();
		var url="${ctx}/pcWeb/pcWebPay/UsePayAccountStatement.do?pagesize="+size+"&number=1"+"&time1="+time1+"&time2="+time2;
		if(time1!=""&&time2!=""){	
		pageOrderSearch(total,size,url);
		}		
	});
	$("#time2").blur(function(){
		$("#pay_time a").each(function(){
			$(this).removeClass("btn-a btn-danger");
		})
		var time1=$("#time1").val();
		var time2=$("#time2").val();
		var url="${ctx}/pcWeb/pcWebPay/UsePayAccountStatement.do?pagesize="+size+"&number=1"+"&time1="+time1+"&time2="+time2;
		if(time1!=""&&time2!=""){			
			pageOrderSearch(total,size,url);
		}
	});
})


//页码配置
function pageOrderSearch(total,size,url){
	/*页面加载后*/
	$.ajax({
			type:"post",
			url:url,
			dataType:'json',
			async:false,
			success:function(response){
//				console.log(response);
				total=response.total;//获取到交易记录的总数
				$("#wallet_index_table").empty();
				var html=html_table_head;   				
				$.each(response.data, function(i,e){
					var type;
					switch(e.type){//
					case 1:type="支出";						   
						   break;
					case 2:type="收入";break;
				    case 3:type="";
						   break;
					default:break;							
					}
					html+='<tr>'+
					'<td>'+e.f_createtime+'</td>'+
					'<td class="redClolr">'+e.f_money+'</td>'+
					'<td>'+e.f_ordertype+'</td>'+
					//隐藏掉查看 			'<td><a href="javascript:void(0)" class="blue" class="view_detail">查看</a></td>'+
					'<td></td>'+ 
					'</tr>';
				});
				$("#wallet_index_table").append(html);
// 				getHtmlLoadingAfterHeight();
			}
		});
	
	$("#PageCode").empty().unbind();//移除分页绑定的相关事件
	$("#PageCode").createPage({//分页
      pageCount:Math.ceil(total/size),//向下取整
      current:1,
      backFn:function(p){//回调函数，p为点击的那页
          $.ajax({
  			type:"post",
  			url:"${ctx}/pcWeb/pcWebPay/UsePayAccountStatement.do?pagesize="+size+"&number="+p+"&key="+key,
  			dataType:'json',
  			success:function(response){
  				$("#wallet_index_table").empty();
  				var html=html_table_head;   				
  				$.each(response.data, function(i,e){
  					var type;
  					switch(e.type){//
  					case 1:type="支出";						   
  						   break;
  					case 2:type="收入";break;
  					case 3:type="";
  						   break;
  					default:break;							
  					}
  					html+='<tr>'+
    					'<td>'+e.f_createtime+'</td>'+
    					'<td class="redClolr">'+e.f_money+'</td>'+
    					'<td>'+e.f_ordertype+'</td>'+
    					//隐藏掉查看 			'<td><a href="javascript:void(0)" class="blue" class="view_detail">查看</a></td>'+
    					'<td></td>'+ 
    					'</tr>';
  				});
  				$("#wallet_index_table").append(html);
//   				getHtmlLoadingAfterHeight();
  			}
  		});
      }
  });	
}
</script>
