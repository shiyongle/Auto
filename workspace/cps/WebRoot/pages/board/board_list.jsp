<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link type="text/css" rel="stylesheet" href="${ctx}/css/kkpager.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/board_list.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/js/jqwidgets-ver3.9.1/jqx.base.css" />
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/kkpager.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxcore.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxscrollbar.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxbuttons.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxlistbox.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxdropdownlist.js" ></script>
<script src="${ctx}/js/jquery.cookie.js" type="text/javascript" language="javascript"></script>

<title>纸板订单</title>
<script type="text/javascript">
$(document).ready(function(){
	window.getHtmlLoadingBeforeHeight();
	parent.$('#quickTip').show();
	parent.$('#quickTip div').eq(1).click(function(){
		$('.shopButton').click();
	});
	
	//点击后tabButton取值
	$('#fstate1 a').click(function(){
		searchByState($(this));
	});
	
	// 状态查询
	function searchByState($this){
		state = $this.attr("fstate");
		$this.addClass('active').siblings().removeClass('active');
		$('#queryState').val(state);
		window.getHtmlLoadingBeforeHeight();
	    window.getboardsTable(1);
	}

	
	$("#fstate").on('change', function (event) {
	     	window.getHtmlLoadingBeforeHeight();
		    window.getboardsTable(1);
	});
	
	$('#allcheckbox').click(function(){
		var checked = $(this).attr('checked')==undefined?false:true;
		$('input[type=checkbox]').attr('checked',checked);
	});
	$('.filter').keyup(function(e){
		  if(e.keyCode==13){
			  $(this).next().click();
		  }
	});
	$('.shopButton').click(function(){
		location.href = "${ctx}/pages/board/shopOnline.jsp";
	});
	//$('.personMind').unbind('click');
	$("#searchButton").click(function() {
		getboardsTable(1);
	});
	getboardsTable(1);
	//全部删除
	$('.deleteAll').click(function(){
		var fid = '';
		$('input:checkbox[class=_check]:checked').next().each(function(i,e){
			if( $(this).val()){
				fid +=  $(this).val();
				if(i<$('input:checkbox[class=_check]:checked').length-1){
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
	//导出 
	$("#excelBoard").click(function() {
		var execlload = layer.load(2);
		var params=getIds(1);
		var formparams= $("#searchForm").serialize();
		if(formparams!=null&&formparams!="") params+="&"+formparams;
		$.ajax({
				type : "POST",
				dataType:"json",
				url : "${ctx}/execl/excelboardDeliverapply.net",
				data: params,
				success : function(response) {
					if(response.success){
						parent.layer.alert('操作成功！',{offset: Math.max(window.screen.height-422,550)/2+'px'}, function(alIndex){
							location.href ="${ctx}/excel/"+response.url;
							parent.layer.close(alIndex);
							layer.close(execlload);
					   });
						
					}else{
						parent.layer.alert('操作失败！', function(alIndex){
							parent.layer.close(alIndex);
							layer.close(execlload);
					});
					}
				}
		});
	});

	var current_date = new Date();
	var strdate = current_date.getFullYear() + current_date.getMonth() + current_date.getDay();
	if(!$.cookie("notify_date") || $.cookie("notify_date") != strdate ){
		var pngurl="<img width='760px' height='559px' src='${ctx}/css/images/notify.png'/>";
	 	parent.layer.alert("",{content:pngurl,scrollbar:false,area:[760,559]},function(alertindex){
		 parent.layer.close(alertindex); 
	 	}); 
	 	$.cookie("notify_date", strdate);
 	}
});

/**
 *  纸板订单详情
 */
 function boarddetails(btn){
	//location.href="${ctx}/pages/board/boardDetail.jsp";
	var queryHistory=$('#queryState').val();
	location.href="${ctx}/board/boarddetails.net?id="+btn.getAttribute("data-id")+"&queryHistory="+queryHistory;
}

$(window).unload(function(){
	parent.$('#quickTip').hide();
});

/**
 * 纸板订单修改
 */
function board_edit(add){
	var fid = $(this).data('fid');
	var action = add?'add': 'edit';
	var queryHistory=$('#queryState').val();
	top.layerIndex = parent.layer.open({
	    title: ['纸板下单','background-color:#CC0000; color:#fff;'],
	    type: 2,
	    anim:true,
	    area: ['770px', '590px'],
	    content: ["${ctx}/board/edit.net?id="+fid+"&action="+action+"&queryHistory="+queryHistory, 'no']
	});
}

/**
 * 纸板订单下单
 */
function add_Board(){
	top.layerIndex = parent.layer.open({
	    title: ['纸板下单','background-color:#CC0000; color:#fff;'],
	    type: 2,
	    anim:true,
	    area: ['770px', '590px'],
	    content: ["${ctx}/board/create.net", 'no']
	});
//	location.href = "${ctx}/board/create.net";
}
//由下单界面新增地址layer调用
function sendAjax(){
	top.addressSelectObj && top.addressSelectObj.load();
}
function reloadMaterial(){
	top.materialSelectObj && top.materialSelectObj.reload();
}
//将选中对象的流水号拼接
function getIds(index) {
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
//删除
function board_del(obj){
	var deleteload = layer.load(2);
	$.ajax({
		url:"${ctx}/board/deleteboard.net",
		type:"post",
		dataType:"json",
		data:{fids:obj},
		success:function(data){
			if(data.success ===true){
				parent.layer.alert('操作成功！', function(index){
					parent.document.getElementById("iframepage").contentWindow.getboardsTable(1);
					parent.layer.close(index);
					layer.close(deleteload);
					});
			}else{
				parent.layer.alert('<div style="text-align:center">'+data.msg+'</div>', function(index){parent.layer.close(index);	layer.close(deleteload);});
			}
		},
		error:function (){
			parent.layer.alert('操作失败！', function(index){parent.layer.close(index);	layer.close(deleteload);});
		}
	});
}

//快速下单
function getboardsTable(page){
		var obj =$("#searchForm").serializeArray();
		var loadIndex = parent.layer.load(2);
		$("#kkpager").html("");
		layer.load(2, {shade: 0.1});
		$.ajax({
				type : "POST",
				url : "${ctx}/board/loadboard.net?pageNo="+page,
				dataType:"json",
				async: false,
				data:obj,
				success : function(response) {
					layer.closeAll('loading');
					$(".alltr").remove();
					if(response.list.length>0){
						$.each(response.list, function(i, ev) {
							var  html =['<tr class="supplierTR alltr">',
							            '<td colspan="7">',
							            '<span>制造商：',ev.suppliername,'</span>',
							            '<span>订单编号：',ev.fnumber,'</span>',
							            '<span>交期：',ev.farrivetimeString,'</span>',
							            '<span>',(ev.flabel==null || ev.flabel=="")?"":('客户标签:'+ev.flabel.substring(0,6)),'</span>',
							            '<span >订单状态：<span class="fstate">',ev.fstate,'</span></span>',
							            '<span class="boarddetails" onclick="boarddetails(this)" data-id="',ev.fid,'">详情</span>',
							            '</td>',
							            '</tr>',
							            '<tr class="conterTR alltr">',
							            '<td><input type="checkbox" class="_check"/><input  type="hidden" name="fid" value="',ev.fid,'"/></td>',
							            '<td>',ev.fpdtname,'</td>',
							            '<td><span class="fboxmodel">',ev.fboxmodel,'</span>','-',ev.fseries,'-',ev.fstavetype,(ev.fdescription==null || ev.fdescription=="")?"":('<br/>特殊要求：'+ev.fdescription),'</td>',
							            '<td>纸箱：',ev.fboxlength==null?0.0:ev.fboxlength.toFixed(1),'*',ev.fboxwidth==null?0.0:ev.fboxwidth.toFixed(1),'*',ev.fboxheight==null?0.0:ev.fboxheight.toFixed(1),'<br/>下料：',ev.fmateriallength==null?0.0:ev.fmateriallength.toFixed(1),'*',ev.fmaterialwidth==null?0.0:ev.fmaterialwidth.toFixed(1),'</td>',
							            '<td style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">纵：',ev.fvline,'<br/>横：',ev.fhline,'</td>',
							            '<td>',ev.famount,'只<br/>',ev.famountpiece,'片</td>',
							            '<td	class="buttonTR">',
							            '<a id="',ev.fid,'_edit" data-fid="'+ev.fid+'" onclick="board_edit.call(this)">修改<br/></a>',
							            '<a id="',ev.fid,'_del" onclick="board_del(\''+ev.fid+'\')">删除<br/></a>',
							            '<a  data-fid="'+ev.fid+'" onclick="board_edit.call(this,true)">再次下单</a>',
							            '</td>',
							            '</tr>'].join("");
							if(i<response.list.length-1) html+=['<tr height="5px" class="alltr"></tr>'].join("");
								
							$(html).appendTo(".table");
						});
						
						$(".table tr .fstate").each(function(i) {
			                var t = $(this).text();
			                var tdId = $(this).parent().next().data("id");
			                if(t>0) {
			                	$("#"+tdId+"_del").remove();
					    		$("#"+tdId+"_edit").remove();
			                }
			                if(t==0){
			                	$(this).text('已下单');
			                }else if(t==2 || t==3){
			                	$(this).text('已接收');
			                }else if( t==4)
			                {
			                	$(this).text('已入库');
			                }else if(t==5 ||t==6)
			                {
			                	$(this).text('已发货');
			                }else if(t==8)
			                {
			                	$(this).text('已作废');
			                }else
			                {
			                	$(this).text('未知');
			                }
            		});
						$(".table tr.conterTR").click(function(i) {
			                var t = $(this).find("input._check");
			                t.prop("checked",!t.attr("checked"));
			               
            		});
						$(".table ._check").click(function(i) {
							i.stopPropagation();
            		});
						/*** 箱型：1普通 2全包,3半包,4有底无盖,5有盖无底,6围框,7天地盖,8立体箱*/
						 $(".table td .fboxmodel").each(function(i) {
							
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
					//window.getHtmlLoadingAfterHeight();	
						/********************************************循环添加TR结束******************************/
						kkpager.pno =response.pageNo;
						kkpager.total =Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
						kkpager.totalRecords =response.totalRecords;
						kkpager.generPageHtml({
							pagerid : 'kkpager', //divID
							click : function(n){
								window.getboardsTable(n);
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
					}
					getHtmlLoadingAfterHeight();
					parent.layer.close(loadIndex); 
				}
				
		});
}



</script>
</head>
<body>
<div  class="conterDiv">
	<div class="title">
		<span>平台首页</span> > <span>我的业务</span> > <span>纸板订单</span>
	</div>
	<!-- 2016/03/17  hss 更改状态选项栏 -->
		<table cellpadding="0" cellspacing="0" border="0" width="1045" 
			id="tbl1" style="table-layout: fixed;">
			<tr height="20">
			</tr>
			<tr align="left">
				<td colspan="5" style="border-bottom: 2px solid #f76350;">
					<p id="fstate1">
						<a href="javascript: void(0)" fstate="" class="active" >全部</a> 
						<a href="javascript: void(0)" fstate="0" >已下单</a> 
						<a href="javascript: void(0)" fstate="2,3">已接收</a>
						<a href="javascript: void(0)" fstate="4">已入库</a> 
						<a href="javascript: void(0)" fstate="5,6">已发货</a> 
						<a href="javascript: void(0)" fstate="8">已作废</a>
					    <a href="javascript: void(0)" fstate="100">三月前数据</a>
					</p>
					<form id="searchForm" class="queryfilter" onsubmit="return false">
                        <input name="deliverapplyQuery.fstate" type="hidden" id="queryState" value=""/>
						<input type="text" style="margin-left: 80px;" placeholder="搜索" class="filter" name="deliverapplyQuery.searchKey"> 
						<input type="button" value="" class="_submit" id="searchButton" style="float:left;">
						<input type="button" value="购物车" class="shopButton" />
					</form>
				</td>
			</tr>
		</table>

	<div>
		<table class="table">
			<tr class="titleTR">
				<td width="65px"><input type="checkbox" id="allcheckbox" style="vertical-align:middle;"/>  全选</td>
				<td width="174px" class="tdBorder">材料</td>
				<td width="163px">产品特征</td>
				<td width="163px" class="tdBorder">规格</td>
				<td width="166px">压线方式</td>
				<td width="163px" class="tdBorder">配送数量</td>
				<td width="163px">操作</td>
			</tr>
			<tr class="tableButton">
				<td colspan="7">
				<!-- <a>产品档案</a> -->
				<a id="excelBoard">导出Excel</a><a onclick="add_Board()">下单</a><a onclick="getboardsTable(1)">刷新</a><a onclick="javascript:void(0);" class="deleteAll">删除</a></td>
			</tr>
		</table>
		<div id="kkpager" align="right" style="margin:15px 40px 15px 0px;"></div>
	</div>
	
</div>
</body>
</html>