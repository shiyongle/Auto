<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ include file="/pages/all_head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>购物车</title>
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/js/kkpager.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/kkpager.css" />
<script type="text/javascript" language="javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
<link href="${ctx}/js/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<style>
*{
	margin:0px auto;
	padding:0px;
	}
input{outline:none;}
#nav1{
	width:1280px;
	height:auto;
	background-color:#f1f1f1;
	}
#container1{
	width:1280px;
	margin-top:20px;
	height:650px;

	}
#container_right{
	width:1280px;
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
/*当前鼠标选中的tab选项*/
.tab .current{
	/* border:1px solid red; */
	border-top:4px solid red;
	border-left:1px solid red;
	border-right:1px solid red;
	/* border-bottom:none; */
	height:31px;
	color:red;
	background-color:#fff;
}
#container1 a,.formlist form table{
	font-family:"宋体";
	font-size:12px;
	text-decoration:none;
}
#top{margin:15px 15px 20px 0px;width:1280px;height:50px;}
#top .n1{
	float:left;
	height:35px;
	line-height:35px;
	width:380px;
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
	height:41px;
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
	height:35px;
	float:left;
	border:3px solid  #D80C18;
	margin-left:480px;
	border-right:none;
	cursor:default;
	vertical-align:middle;
	margin-top:10px;
}
#container_right .formlist{
	width:1280px;
	height:auto;
	border-top:1px solid red;
	border-left:none;
	border-right:none;
	border-bottom:none;
	background-color:#fff;
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
	font-family:"宋体";
	font-size:16px;
	color:#151515;
}
#container1 a:hover{
	color:#ff0505;
	cursor:pointer;
}
.leftTd{
	text-align:left;
}
#container1 .bottomTr{
	font-size:18px;
	font-family:微软雅黑;
	color:#656464;
}
.imageTr{
	 width:136px;
	 height:116px;
	 vertical-align:middle;
	 float:left;
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
</style>
</head>

<body style="background-color:#f1f1f1;">
	<div id="nav1">
       	<div id="container1">
       		<div style="margin-bottom:10px;">
       			<a>平台首页</a> &gt; <a>我的业务</a> &gt; <a>快速下单</a>&nbsp;&gt;&nbsp;<a>购物车</a>
       		</div>
			<div id="top">
				
				<div> 
					  <image src="${ctx}/css/images/olcps.png" style="vertical-align:middle;float:left"/>
					  <input type="image" src="${ctx}/css/images/product.png" class="productBG"/>
					  <input type="text" class="n1" placeholder="请输入产品名称,规格" id="keyword" name="keyword" value=""/>
					  <span class="s3"><input type="submit" value="搜索" class="n2" onclick="search()"/></span>
				</div>
			</div>
            <div id="container_right">
            	<ul class="tab">
                	<li class="current">要货</li>
            		<c:if test="${fbacthstock }">
	                    <li style="border-left:none;">备货</li>
            		</c:if>
					<li style="float:right;border:none;color:red;" disable="true"><a href="${ctx}/menuTree/center.net?menu=c3d28df961a3c9ebfc7994361031186c">继续下单</a></li>
					<li style="float:right;border:none;width:25px;color:red;" disable="true"><a href="${ctx}/menuTree/center.net?menu=c3d28df961a3c9ebfc7994361031186c">返回</a></li>
                </ul>

                <div class="formlist">
	            	<form>

						<table data-type="0" width="1250" height="auto"  border=0 style="display:block;border-collapse:collapse;">
						<tr style="height:20px;"></tr>
						<tr style="background-color:#f1f1f1;">
							<td width="85px"><input type="checkbox" style="vertical-align:middle;" class="allCheckbox"/>&nbsp;全选</td>
							<td width="360px" style="border-left:1px solid lightgray;border-right:1px solid lightgray;">商品信息</td>
							<td width="200px">地址</td>
							<td width="200px" style="border-left:1px solid lightgray;border-right:1px solid lightgray;">数量</td>
							<td width="200px">交期</td>
							<td width="200px" style="border-left:1px solid lightgray;">操作</td>
						</tr>
						<tr ></tr>
						<tr  id="deliverapplycontent" style="background-color:#D9D7D7;height:37px;" class="contentTR">
							<td style="border-left:1px solid #bfbfbf;" class="leftTd">
								<input type="checkbox" style="margin-left:17px;" class="allCheckbox"/>
							</td>
							<td colspan="4" class="leftTd bottomTr">
								<span class="bottomTr">全选</span><span><a href="" class="bottomTr deleteAll" style="margin-left:70px;">删除</a></span>
							</td>
							<td style="text-align:right;border-right:1px solid #bfbfbf;">
								<input type="button" value="下单" class="order"/>
							</td>
						</tr>
						</table>


					<table data-type="1" width="1250" height="auto"  border=0 style="display:none;border-collapse:collapse;">
						<tr style="height:20px;"></tr>
						<tr style="background-color:#f1f1f1;">
							<td width="85px"><input type="checkbox" style="vertical-align:middle;" class="allCheckbox"/>&nbsp;全选</td>
							<td width="360px" style="border-left:1px solid lightgray;border-right:1px solid lightgray;">商品信息</td>
							<td width="160px">规格</td>
							<td width="160px" style="border-left:1px solid lightgray;border-right:1px solid lightgray;">计划数量</td>
							<td width="160px">首次发货</td>
							<td width="160px" style="border-left:1px solid lightgray;border-right:1px solid lightgray;">末次发货</td>
							<td width="160px">操作</td>
						</tr>
						<tr ></tr>
							<tr id="mystockcontent" style="background-color:#D9D7D7;height:37px;" class="contentTR">
							<td style="border-left:1px solid #bfbfbf;" class="leftTd">
								<input type="checkbox" style="margin-left:17px;" class="allCheckbox"/>
							</td>
							<td colspan="5" class="leftTd bottomTr">
								<span class="bottomTr">全选</span><span><a href="" class="bottomTr deleteAll" style="margin-left:70px;">删除</a></span>
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
     <div id="foot">
        	<iframe src="${ctx}/pages/all_foot.jsp" scrolling="no" width=100% height="150" frameborder="0" id="allfoot"></iframe>
     </div>
<script type="text/javascript">
//快速下单
function getShopListTable(obj,page){
		var params="ftype="+obj;
		var keyword =$("#keyword").val();
		if(keyword!=null && keyword!=""){
			params +="&keyword="+keyword +"&pageNo="+page;
		}else{
			params +="&pageNo="+page;
		}
 		var loadIndex = parent.layer.load(2);
		 $.ajax({
				type : "POST",
				url : "${ctx}/shopcar/loadShopCar.net",
				dataType:"json",
				async: false,
				data:params,
				success : function(response) {
					$(".alltr").remove();
					var index;
					  $.each(response.list, function(j, ev) {
						  var  html =['<tr class="alltr">'+//备货7列 要货6列
										'<td colspan="'+6+obj+'" class="leftTd" style="background-color:#D9d7d7;color:#fff;border:1px solid #bfbfbf;">'+
										'<span style="margin-left:17px;"><input type="checkbox" style="vertical-align:middle;" class="supplierCheckbox" id="'+ev.fid.replace(/\W+/g,'')+'"/>&nbsp;&nbsp;制造商：'+ev.fname+'</span>'+
									'</td>'+
								'</tr>'  ].join('');
						  if(obj==0){
						  $.each(ev.cusprivate,function(i,ed)
						  {
						  	  index = j+''+i;
							  html+=['<tr class="contentTR contentFontTr alltr" data-fid="'+ed.fid+'">',
							   '<td style="border-left:1px solid #bfbfbf;" class="leftTd"><span style="margin-left:17px;"><input name="'+ev.fid.replace(/\W+/g,'')+'" type="checkbox" class="ch_box"/></span></td>',
							   '<td class="leftTd"><image src="${ctx}/productfile/getFileSourceByParentId.net?fid=',ed.fcusproduct,'" class="imageTr"/><div class="productContent">'+ed.cutpdtname+'</div><div class="productContent">'+ed.fspec+'</div></td>',
							   '<td>'+ed.faddressdetail+'</td>',
							   '<td>',
							   '<div style="width:140px;height:32px;">',
							   '<input  type="button" value="－" class="reduce"/>',
							   '<input type="text" value="'+ed.famount+'" class="amount" style="padding-left:0px;" name="deliverapply.famount"/>',
							   '<input  type="button" value="＋" class="plus"/>',
							   '</div>',
							   '</td>',
							   '<td>',
							   '<div>',
							   '<p style="height:25px;">',
							   '<span class="arrivetime" id="date'+index+'" data-mdate='+ev.fusedstatus+'>'+ed.farriveString+'</span>',
							   '<image class="date_picker" src="${ctx}/css/images/xljt-b.png" style="vertical-align:middle;cursor:pointer;" onclick="var me= $(this).prev();var mindate=getshopMinDate(',ev.fisManageStock,',',ev.fusedstatus,');WdatePicker({el:$dp.$(\'date'+index+'\'),minDate:mindate,onpicked:function(){me.removeClass(\'WdateFmtErr\');me.trigger(\'click\')}})" />',
							   '</p>',
							   '<p style="height:25px;">',
							   '<span class="arrivezone">'+ed.farrivezone+'</span><image class="apm" src="${ctx}/css/images/xljt-b.png" style="vertical-align:middle;cursor:pointer;" onclick="changeAPM(this)"/>',
							   '</p>',
							   '</div>',
							   '</td>',
							   '<td style="border-right:1px solid #bfbfbf"><a class="deleteData" href="javascript:void(0)">删除</a></td>',
							   '</tr>'].join("");
							  
							
						  });		    	
						  $("#deliverapplycontent").before(html);  
						  }else{
							  $.each(ev.cusprivate,function(i,ed)
								{
								  index = j+''+i;
								  html+=['<tr class="contentTR contentFontTr alltr" data-fid="'+ed.fid+'">',
								   '<td style="border-left:1px solid #bfbfbf;" class="leftTd"><span style="margin-left:17px;"><input name="'+ev.fid.replace(/\W+/g,'')+'" type="checkbox"  class="ch_box"/></span></td>',
								   '<td class="leftTd"><image src="${ctx}/productfile/getFileSourceByParentId.net?fid=',ed.fcusproduct,'" class="imageTr"/><div class="productContent">'+ed.cutpdtname+'</div></td>',
								   '<td>'+ed.fspec+'</td>',
								   '<td>',
								   '<div style="width:140px;height:32px;">',
								   '<input  type="button" value="－" class="reduce"/>',
								   '<input type="text" value="'+ed.famount+'" class="amount" style="padding-left:0px;" name="deliverapply.famount"/>',
								   '<input  type="button" value="＋" class="plus"/>',
								   '</div>',
								   '</td>',
								   '<td>',
								   '<div>',
								   '<p style="height:25px;">',
								   '<span class="arrivetime" id="date3'+index+'">'+ed.farriveString+'</span>',
								   '<image class="date_picker" src="${ctx}/css/images/xljt-b.png" style="vertical-align:middle;cursor:pointer;"  onclick="var me= $(this).prev();WdatePicker({el:$dp.$(\'date3'+index+'\'),minDate:\'%y-%M-%d 00:00:00\',onpicked:function(){me.trigger(\'click\')}})" />',
								   '</p>',
								   '</div>',
								   '</td>',
								   '<td>',
								   '<div>',
								   '<p style="height:25px;">',
								   '<span class="fconsumetime" id="date2'+index+'">'+ed.fconsumetime+'</span>',
								   '<image src="${ctx}/css/images/xljt-b.png" style="vertical-align:middle;cursor:pointer;"  onclick="var me= $(this).prev();WdatePicker({el:$dp.$(\'date2'+index+'\'),minDate:\'%y-%M-%d 00:00:00\',onpicked:function(){me.trigger(\'click\')}})" />',
								   '</p>',
								   '</div>',
								   '</td>',
								   '<td style="border-right:1px solid #bfbfbf"><a class="deleteData" href="javascript:void(0)">删除</a></td>',
								   '</tr>'].join("");	
									  });		    	
									  $("#mystockcontent").before(html); 
						  }
						});
					     addNumOnlyEvent($(".amount"));
					 	 addPlusReduceEvent();
					 	updateData(obj);
					//子页面设置父级iframe高度
						/********************************************循环添加TR结束******************************/
						kkpager.pno =response.pageNo;
						kkpager.total =Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
						kkpager.totalRecords =response.totalRecords;
						kkpager.generPageHtml({
							click : function(n){
									window.scrollTo(0,0);
									getShopListTable(obj,n);
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
					//勾选同一制造商产品复选框
						$('.supplierCheckbox').click(function(){
							var checked = $(this).attr('checked')==undefined?false:true;
							$("table:visible input[name='"+$(this).attr('id')+"']").attr('checked',checked);
						});
						//动态改变div高度
						$('#nav1').height(eval($('#container_right').height()+120));
						layer.close(loadIndex);   
				}
		
		});  
}

function search()
{
	var obj=$('li.current').index();
	getShopListTable(0,1);
}


$(document).ready(function(){
	//下单按钮点击样式
	$('.order').each(function(){
		$(this).mousedown(function(){
			$(this).addClass('orderDown');
		}).mouseup(function(){
			$(this).removeClass('orderDown');
		});
	});
	//选项卡
	var $li = $(".tab li");
	var $tbl = $(".formlist table");			
	$(".tab li:not([disable=true])").click(function(){
		var $this = $(this);
		var $t = $this.index();
		$li.removeClass();
		$(".tab li:not([disable=true])").attr('style','');
		if($this.prev().length){
			$this.prev().attr('style','border-right:none;');
		}else if($this.next().length){
			$this.next().attr('style','border-left:none;');
		}
		$this.addClass("current");
		$tbl.css('display','none');
		$tbl.eq($t).css('display','block');
		getShopListTable($t,1);

	});

	  $("#keyword.n1").keydown(function(e){
			if(e.keyCode==13){
				$(this).next().find("input").click();
				return false;
			}
		});
	 //勾选全部复选框
	$(".allCheckbox").click(function(){
		//if($(this).attr('checked')){
			var checked = $(this).attr('checked')==undefined?false:true;
			$('table:visible input[type=checkbox]').attr('checked',checked);
		//}
	});
	$('.deleteAll').click(function(){
		var $table = $(this).parents('table'),
			trs = $table.find('tr'),
			ids = [];
		trs.each(function(){
			var fid = $(this).data('fid');
			if(fid){
				ids.push('ides='+fid);
			}
		});
		deleteData(ids.join('&'),$table.data('type'));
	});
	$('.order').click(function(){
		var $tr = $(this).parents('table').find('tr'),fids=[];
		var checked = $(':checked').length;
		if(checked<=0){
			layer.msg('请选择数据!');
			return false;
		}
		$tr.each(function(){
			var me = $(this);
			var fid = me.data('fid');
			if(fid && me.find('.ch_box').attr('checked')){
				if(me.find(".amount").val()==0)
				{
					layer.tips("要下单的产品数量不能为0", me.find(".amount"), {tips: [1, '#F7874E'],time: 4000});
					//jError("要下单的产品数量不能为0",{VerticalPosition : 'center',HorizontalPosition : 'center'});
					fids=[];
					return false;
				}
				var mdate=me.find(".arrivetime").data("mdate");
				if(mdate)
				{
					var selectdate = new Date(Date.parse(me.find(".arrivetime").text().replace(/-/g,  "/")))
					var mindate = new Date(Date.parse('${nowDate}'.replace(/-/g,  "/")));
					mindate.setDate(mindate.getDate()+parseInt(mdate));
					if(selectdate.getTime()<mindate.getTime())
					{
						layer.tips("制造商设置的最早交期为"+mindate.getFullYear()+"-"+eval(mindate.getMonth()+1)+"-"+mindate.getDate(), me.find(".arrivetime"), {tips: [1, '#F7874E'],time: 4000});
						return false;
					}
				}
				fids.push("ides="+fid);
			}
		});
		if(fids.length>0){
		$.ajax({
			type : "POST",
			url : "${ctx}/shopcar/convertorders.net",
			data: fids.join('&'),
			success : function(response) {
				if(response == "success") {
					window.location.href="${ctx}/menuTree/center.net?menu=c3d28df961a3c9ebfc7994361031186c";
				}else{
					jError(response,{VerticalPosition : 'center',HorizontalPosition : 'center'});
				}
				
			},
			failure:function()
			{
				jError("网络异常!",{VerticalPosition : 'center',HorizontalPosition : 'center'});
			}
		});
		}
	});
	getShopListTable(0,1);
});
function changeAPM(m){
	if($(m).prev().text()=='上午'){
		$(m).prev().text('下午');
	}else if($(m).prev().text()=='下午'){
		$(m).prev().text('上午');
	}
}
function addPlusReduceEvent(){
	$('.reduce,.plus').each(function(){//数量+-
		$(this).click(function(){
			if($(this).prev().length){
				$(this).prev().val(eval($(this).prev().val())+1);
			}else if($(this).next().length){
				if($(this).next().val()==0){
					return;
				}
				$(this).next().val(eval($(this).next().val())-1);
			}
		});
	});
}
function deleteData(data,type){
	$.ajax({
		type : "POST",
		url : "${ctx}/shopcar/delete.net",
		data: data,
		success : function(response) {
			if(response == "success") {
				getShopListTable(type,1);
			}else{
				jError("操作失败!",{VerticalPosition : 'center',HorizontalPosition : 'center'});
			}
			
		},
		failure:function()
		{
			jError("网络异常!",{VerticalPosition : 'center',HorizontalPosition : 'center'});
		}
	});
}
function updateData(obj){
	var datas = {},timeout;
	function submitData(){
		var $tr = $(this).parents('tr'),
			fid = $tr.data('fid');
		datas[fid] = {fid:fid,famount: $tr.find('.amount').val(),farriveString:$tr.find('.arrivetime').text(),farrivezone:$tr.find('.arrivezone').text(),fconsumetime:$tr.find('.fconsumetime').text()};
		if(timeout){
			clearTimeout(timeout);
		}
		timeout = setTimeout(function(){
			var arr = [];
			$.each(datas,function(_,data){
				arr.push(data);
			});
			if(arr.length==0){
				return;
			}
			$.ajax({
				type : "POST",
				url : "${ctx}/shopcar/update.net",
				dataType:"json",
				data: {cusprivatedelivers:JSON.stringify(arr)},
				success : function(response) {
					if(response != "success") {
						jError("操作失败!",{VerticalPosition : 'center',HorizontalPosition : 'center'});
					}
				}
			});
			datas = {};
			timeout = null;
		},1000);
	}
	$('.reduce,.plus,.arrivetime,.apm,.fconsumetime').click(submitData);
	$('.amount').bind('keydown', function (e){
		if(e.which==13){
			e.preventDefault();
			$(this).blur();
		}
	});
	$('.amount').blur(submitData);
//	$('.arrivetime').change(submitData);
	$('.deleteData').click(function(){
		var fid =$(this).parents('tr').data('fid');
		deleteData({ides: fid},obj);
		delete datas[fid];
	});
}
function getshopMinDate(fismangestock,fdays)
{
	if(fdays)
	{
		var mindate = new Date(Date.parse('${nowDate}'.replace(/-/g,  "/")));
		mindate.setDate(mindate.getDate()+parseInt(fdays));
		console.log(mindate.getFullYear()+"-"+eval(mindate.getMonth()+1)+"-"+mindate.getDate());
		return mindate.getFullYear()+"-"+eval(mindate.getMonth()+1)+"-"+mindate.getDate();
	}
	console.log('${nowDate}');
	return '${nowDate}';
}
</script>


</body></html>