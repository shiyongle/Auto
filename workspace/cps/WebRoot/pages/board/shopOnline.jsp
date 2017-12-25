<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>购物车</title>
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<script type="text/javascript" src="${ctx}/js/kkpager.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/kkpager.css" />
<style>
*{
	margin:0px auto;
	padding:0px;
	}
input{outline:none;}
#nav1{
	width:1070px;
	height:auto;
	}
#container1{
	width:1070px;
	margin-top:20px;
	height:650px;

	}
#container_right{
	width:1070px;
	/* margin-left:20px; */
	}
#container_right .tab{
	width:auto;
	height:34px;
	list-style:outside none none;
	margin-left:20px;
	text-align:left;
}

.tab li{
	height:33px;
	width:80px;
	line-height:33px;
	float:left;
	text-align:center;
	font-family:"宋体";
	font-size:12px;
	cursor:pointer;
	border:1px solid #cccccc;
	border-bottom:none;
	}
#container1 a{
	text-decoration:none;
}
#container1 span,.formlist form table{
	font-family:"宋体";
	font-size:12px;
	text-decoration:none;
}
#top{margin:15px 15px 20px 0px;width:1070px;height:50px;}
#top .n1{
	float:left;
	height:30px;
	line-height:35px;
	width:300px;
	padding-left:40px;
	font-size:16px;
	font-family:Arial, Helvetica,sans-serif;
	border:3px solid  #D80C18;
	border-left:none;
	background:url(${ctx}/css/images/fdj.png) #FFF 10px 8px no-repeat;
	margin-top:10px;
}
#top .n1 span{float:left;}
#top .s3{width:80px;height:38px;}
#container1 .n2{
	height:36px;
	width:80px;
	font-size:17px;
	text-align:center;
	background-color:#D80C18;
	color:white;
	border:none;
	font-family:Arial, Helvetica,
	sans-serif;
	cursor:pointer;
	float:left;
	margin-top:10px;
}
#top .productBG{
	width:75px;
	height:30px;
	float:left;
	border:3px solid  #D80C18;
	margin-left:200px;;
	border-right:none;
	cursor:default;
	vertical-align:middle;
	margin-top:10px;
}
#container_right .formlist{
	width:1070px;
	height:auto;
	border-left:none;
	border-right:none;
	border-bottom:none;
	background-color:#fff;
}
.formlist form table{
	width:1050px;
	margin:0px 10px 0px 10px;
}
.formlist form table tr{
	height:37px;
	line-height:25px;
	text-align:center;
}
.formlist table table td{
	border:1px solid #bfbfbf;
}
.formlist form table tr.contentTR{
	border:1px solid #bfbfbf;
	height:170px;
}
.contentTR td{
	*border-top:1px solid #bfbfbf;
	*border-bottom:1px solid #bfbfbf;
}
.contentFontTr{
	font-family:"宋体";
	font-size:14px;
	color:#151515;
}
#container1 .contentFontTr a{
    font: 12px 宋体;
	color:#151515;
}
.leftTd{
	text-align:left;
}

.order{
	height:37px;
	width:160px;
/* 	background-color:#DB471F; */
	background-color:#D80C18;
	border:none;
	font-size:28px;
	font-family:微软雅黑;
	color:#fff;
	cursor:pointer;
}
.orderDown{
	background-color:#DB471F;
	padding:1px;
}
#container_right .plus,#container_right .reduce{
	display:block;
	float:left;
	width:30px;
	height:32px;
	font-size:15px;
	padding:2px 5px;
	background-color:#e9e9e9;
	border:1px solid #ccc;
	text-decoration:none;
	color:#545454;
	line-height:20px
}
	
#container_right .plus:hover,#container_right .reduce:hover{
	cursor:pointer;
	color:#E60012;
	border:1px solid #E60012;
	font-size:20px;
}
#container_right .amount{
	text-align:center;
	display:block;
	float:left;
	height:30px;
	width:80px;
	border:1px solid #ccc;
	border-left:none;
	border-right:none;
	line-height:30px
}
.productContent{
	margin-left:10px;
	float:left;
	width:200px;
	margin-top:10px;
}
.formlist .supplierTR{
	height:30px;
	text-align:left;
	background-color:#f1f1f1;
	color:#000;
	border:1px solid #bfbfbf;
}
.supplierTR span{
	margin-right:65px;
	padding-left:5px;
}
.formlist .conterTR{
	height:110px;
	border:1px solid #bfbfbf;
}
.buttonTR span{
	margin:15px;
	display:block;
	cursor:pointer;
}
#container1 .bottomTr{
	font-size:18px;
	font-family:微软雅黑;
	color:#656464;
}
.boarddetails{
	float:right;
	cursor: pointer;
	color:#00e;
}
/* .boarddetails:hover{
	color:#00e;
} */

#container1 #kkpager_btn_go {
    width: 44px;
    height: 20px;
    line-height: 20px;
    padding: 0px;
    font-family: arial,宋体,sans-serif;
    text-align: center;
    border: 0px;
    background-color: #bfbfbf;
    color: #FFF;
    position: absolute;
    left: 0px;
    top: -1px;
    display: none;
    cursor: pointer;
}
</style>
</head>

<body style="background-color:#f1f1f1;">
	<div id="nav1">
       	<div id="container1">
       		<div style="margin-bottom:10px;">
       			<span><a>平台首页</a> > <a>用户中心</a> > <a>我的业务</a> > <a>购物车</a></span>
       		</div>
			<div id="top">
				<form  id="searchForm" class="queryfilter" onsubmit="return false">
					<div> 
						  <image src="${ctx}/css/images/olcps.png" style="vertical-align:middle;float:left"/>
						  <input type="image" src="${ctx}/css/images/product.png" class="productBG"/>
						  <input type="text" class="n1"  id="keyword" name="deliverapplyQuery.searchKey" value=""/>
						  <span class="s3"><input type="submit" value="搜索" class="n2" onclick="search()"/></span>
					</div>
				</form>
			</div>
            <div id="container_right">
            	<ul class="tab">
					<li style="float:right;border:none;" disable="true"><a onclick="back()" style="color:red;">继续下单</a></li>
					<li style="float:right;border:none;width:25px;" disable="true"><a onclick="back()" style="color:red;">返回</a></li>
                </ul>

                <div class="formlist">
	            	<form>

					<table data-type="1" width="1250" height="auto"  border=0 style="border-collapse:collapse;">
						<tr style="height:20px;"></tr>
						<tr style="background-color:#f1f1f1;">
							<td width="60px"><input type="checkbox" style="vertical-align:middle;" class="allCheckbox"/> 全选</td>
							<td width="174px" style="border-left:1px solid lightgray;border-right:1px solid lightgray;">材料</td>
							<td width="160px">产品特征</td>
							<td width="160px" style="border-left:1px solid lightgray;border-right:1px solid lightgray;">规格</td>
							<td width="160px">压线方式</td>
							<td width="160px" style="border-left:1px solid lightgray;border-right:1px solid lightgray;">数量</td>
							<td width="160px">操作</td>
						</tr>
						<tr style="height:17px;"></tr>
						<!-- 循环开始 -->
	<!-- 					<tr style="height:20px;"></tr>
						<tr class="supplierTR">
							<td colspan="7">
								<span>制造商：东经包装</span>
								<span>配送时间：1265894</span>
								<span>地址：2015-11-09</span>
								<span class="boarddetails">详情</span>
							</td>
						</tr>
						<tr class="conterTR">
							<td><input type="checkbox"/></td>
							<td>东力3号/5BC</td>
							<td>普通连做压线</td>
							<td>纸箱：55*55*55<br/>下料：150*50</td>
							<td>纵：5.4+10+5.4<br/>下料：5.4+10+5.4</td>
							<td>200只<br/>400片</td>
							<td class="buttonTR">
								<span>修改</span>
								<span>删除</span>
							</td>
						</tr> -->
						<!-- 循环结束 -->
						<tr id="mystockcontent" style="background-color:#D9D7D7;height:37px;" class="contentTR">
							<td style="border-left:1px solid #bfbfbf;" class="leftTd">
								<input type="checkbox" style="margin-left:17px;" class="allCheckbox"/>
							</td>
							<td colspan="5" class="leftTd bottomTr">
								<span class="bottomTr">全选</span><span><a href="javascript:void(0);" class="bottomTr deleteAll" style="margin-left:70px;color:#D80C18;">删除</a></span>
							</td>
							<td style="text-align:right;border-right:1px solid #bfbfbf;">
								<input type="button" value="下单" class="order"/>
							</td>
						</tr>
					</table>

					</form>
                </div>
				<div id="kkpager" align="right" style="margin:15px 40px 0px 0px;"></div>
            </div>
          </div>
    </div>
<script type="text/javascript">
	$(document).ready(function(){
		parent.scrollTo(0,0);
		$('.allCheckbox').click(function(){
			var checked = $(this).attr('checked')==undefined?false:true;
			$('input[type=checkbox]').attr('checked',checked);
		})
		
		
		$('.order').click(function(){
			var fid = '';
			$('.conterTR').find('input:checked').each(function(i,e){
				if($(this).attr('data-fid')){
					fid +=  $(this).attr('data-fid');
					if(i<$('.conterTR').find('input:checked').length-1){
						fid += ",";
					}
				}
			});
			if($('.conterTR').find('input:checked').length==0){
				parent.layer.alert('请选择数据！');
				return false;
			}
			$.post('${ctx}/board/shopOrder.net',	{'fids':fid},
				function(data){
					if(data.success){
						parent.layer.open({
						    type: 2,
						    title: '',
						    move:false,
						    shade: 0.8,
						    area: ['600px', '330px'],
						    closeBtn:0,
						    content: '${ctx}/pages/board/shopSuccess.jsp',
						    end:function(){
						    	parent.document.getElementById("iframepage").contentWindow.getShopListTable(1);
						    }
						}); 
					}else{
						parent.layer.alert(data.msg);
					}
				},'json'
			);
		});
		$('#keyword').keyup(function(e){
			if(e.keyCode==13){
				$(this).next().children().click();
			}
		});
		$('.deleteAll').click(function(){
			var fid = '';
			$('input:checked').each(function(i,e){
				if($(this).attr('data-fid')){
					fid +=  $(this).attr('data-fid');
					if(i<$('input:checked').length-1){
						fid += ",";
					}
				}
			});
			if($.isEmptyObject(fid)){
				parent.layer.alert('请选择数据！');
				return false;
			}
			board_del(fid);
		});
		getShopListTable(1);
	})
	function back(){
		top.$('#7a403c6ed40df9351325af3b5cfdce5b').click();	// 返回纸板订单列表界面
	}
	function search(){
		getShopListTable(1);
	}
	function boarddetails(btn){
		location.href="${ctx}/board/boarddetails.net?id="+btn.getAttribute("data-fid");
	}
	function getShopListTable(page){
		var params=$('.queryfilter').serializeArray();
 		var loadIndex = parent.layer.load(2);
		 $.ajax({
				type : "POST",
				url : "${ctx}/board/loadboardShop.net?pageNo="+page,
				dataType:"json",
				async: false,
				data:params,
				success : function(response) {
					$(".alltr").remove();
					  $.each(response.list, function(j, ev) {
						  var html = '<tr style="height:20px;" class="alltr"></tr>'+
								'<tr class="supplierTR alltr">'+
							'<td colspan="7">'+
								'<span>制造商：'+ev.suppliername+'</span>'+
								'<span>配送时间：'+ev.farrivetimeString+'</span>'+
								'<span>地址：'+ev.faddress+'</span>'+
								'<span class="boarddetails" data-fid="'+ev.fid+'" onclick="boarddetails(this)">详情</span>'+
							'</td>'+
						'</tr>'+
						'<tr class="conterTR alltr">'+
							'<td><input type="checkbox" data-fid="'+ev.fid+'"/></td>'+
							'<td>'+ev.fpdtname+'</td>'+
							'<td><span class="fboxmodel">'+ev.fboxmodel+'</span>-'+ev.fseries+'-'+ev.fstavetype+'</td>'+
							'<td>纸箱：'+ev.fboxlength.toFixed(1)+'*'+ev.fboxwidth.toFixed(1)+'*'+ev.fboxheight.toFixed(1)+'<br/>下料：'+ev.fmateriallength.toFixed(1)+'*'+ev.fmaterialwidth.toFixed(1)+'</td>'+
							'<td style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">纵：'+ev.fvline+'<br/>横：'+ev.fhline+'</td>'+
							'<td>'+ev.famount+'只<br/>'+ev.famountpiece+'片</td>'+
							'<td class="buttonTR">'+
								'<span onclick="board_edit.call(this)" data-fid="'+ev.fid+'">修改</span>'+
								'<span onclick="board_del(\''+ev.fid+'\')">删除</span>'+
							'</td>'+
						'</tr>';
						$('#mystockcontent').before($(html));
						});
					  $("table td .fboxmodel").each(function(i) {
			                var t = $(this).text();
			                switch(t)
			                {
			                 case '1':$(this).text('普通'); break;
			                 case '2':$(this).text('全包'); break;
			                 case '3':$(this).text('半包'); break;
			                 case '4':$(this).text('有底无盖'); break;
			                 case '5':$(this).text('有盖无底'); break;
			                 case '6':$(this).text('围框'); break; 
			                 case '7':$(this).text('天地盖'); break; 
							 case '8':$(this).text('立体箱'); break;
			                  break;
			                 default:
			                	 $(this).text('其它');
			                }
		          		}); 
					//子页面设置父级iframe高度
						/********************************************循环添加TR结束******************************/
						kkpager.pno =response.pageNo;
						kkpager.total =Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
						kkpager.totalRecords =response.totalRecords;
						kkpager.generPageHtml({
							click : function(n){
									window.scrollTo(0,0);
									getShopListTable(n);
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
						/********************************************渲染分页主键结束******************************/
					//动态改变div高度
					getHtmlLoadingAfterHeight();
					parent.layer.close(loadIndex);   
				}
		
		});  
	}
	//删除
	function board_del(obj){
		$.ajax({
			url:"${ctx}/board/deleteboard.net",
			type:"post",
			dataType:"json",
			async: false,
			data:{fids:obj},
			success:function(data){
				if(data.success ===true){
					parent.layer.alert('操作成功！', function(index){
						parent.document.getElementById("iframepage").contentWindow.getShopListTable(1);
						parent.layer.close(index);
						});
				}else{
					parent.layer.alert(data.msg, function(index){parent.layer.close(index);});
				}
			},
			error:function (){
				parent.layer.alert('操作失败！', function(index){parent.layer.close(index);});
			}
		});
		
	}
	// 修改
	function board_edit(){
		var fid = $(this).data('fid');
		location.href = "${ctx}/board/edit.net?id="+fid;
	}
</script>


</body></html>