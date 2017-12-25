<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ include file="/pages/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的需求-需求裂变</title>
<link href="${ctx}/css/myRequirement_list.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/css/kkpager.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/js/jquery-1.8.3.min.js" type="text/javascript" language="javascript"></script>
<script src="${ctx}/js/kkpager.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript">
function gridFirstproducteMandTable(page){
	var obj =$("#queryDemandForm").serialize();
	var loadDel = layer.load(2);
	$("#kkpager").html("");
   		$.ajax({
				type : "POST",
				url : "${ctx}/firstproductdemand/load.net?pageNo="+page,
				dataType:"json",
				data:obj,
				success : function(response) {
					$(".allSpace").remove();
					$(".allContent").remove();
					$(".plan_list").remove();
					$(".setting_plan").remove();
					  $.each(response.list, function(i, ev) {
					  	  var fasjTr = ev.fid+"_tr";
					  	  var fasjTd = ev.fid+"_td";
					  	  var toolBar =ev.fid+"_bar";
						  var  html =[
					                    '<tr class="allSpace" height="20">',
											'<td colspan="10">&nbsp;</td>',
										'</tr>',
										'<tr class="allContent">',
											'<td><input type="checkbox" name="check_name" class="_check"/><input  type="hidden" name="fid" value="',ev.fid,'"/></td>',
											'<td>',
												'<a class="tdcls" href='+window.getRootPath()+'/firstproductdemand/show.net?id='+ev.fid+'>',ev.fnumber,'</a>',
											'</td>',
											'<td>',ev.fname,'</td>',
											'<td>',ev.customerName,'</td>',
											'<td>',ev.fstate,'</td>',
											'<td>',ev.supplierName,'</td>',
											'<td>',ev.fauditorid,'</td>',
											'<td>',ev.fauditortimeString,'<br/>',ev.fTimeString,'</td>',
											'<td>',ev.freceiver,'<br />',ev.freceiverTel,'<br />',
											     '<a href="#">在线沟通</a>',
											'</td>',
											'<td id='+toolBar+' class="_setting">',
												'<a href="javascript:void(0);"  onclick="sure_fb(\''+ev.fid+'\');">发布</a>',
												'<a href="javascript:void(0);"  onclick="edit(\''+ev.fid+'\');">修改</a>',
												'<a href="javascript:void(0);"  onclick="single_del(\''+ev.fid+'\');">删除</a>',
											'</td>',
										'</tr>',
										'<tr id="'+ev.fid+'" class="plan_list"></tr>',
										'<tr id='+fasjTr+' class="setting_plan">',
										' 	<td id='+fasjTd+' colspan="10"><a href="javascript:void(0);" onclick="scheme_Design(\''+ev.fid+'\');">方案设计&nbsp;&nbsp;<img src="${ctx}/css/images/down.png"/></a></td>',
										'</tr>'
									].join("");
						   $(html).appendTo("#firstproductdemandTool");
						    if(ev.fstate=='已分配' || ev.fstate=='已接收' || ev.fstate=='已设计' || ev.fstate=='已完成'){
								$("#"+toolBar).html("");
						    }else if(ev.fstate=='已发布'){
						    	var htmlT ='<a href="javascript:void(0);"  onclick="cancel_fb(\''+ev.fid+'\');">取消发布</a>';
						    	 $("#"+toolBar).html(htmlT);
						    }else if(ev.fstate=='存草稿'){
						    	var htmlT ='<a href="javascript:void(0);"  onclick="sure_fb(\''+ev.fid+'\');">发布</a>'+
						    			   '<a href="javascript:void(0);"  onclick="edit(\''+ev.fid+'\');">修改</a>'+
										   '<a href="javascript:void(0);"  onclick="single_del(\''+ev.fid+'\');">删除</a>';
						    	$("#"+toolBar).html(htmlT);
						    }
						});
						/********************************************循环添加TR结束******************************/
						var vpno =response.pageNo;
						var vtotal =Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
						var vtotalRecords =response.totalRecords;
						kkpager.generPageHtml({
							pno : vpno,//当前页码
							total : vtotal,//总页码
							totalRecords : vtotalRecords,//总数据条数
							click : function(n){
									window.gridFirstproducteMandTable(n);
									window.table_check();
									window.hang_xg();
									this.selectPage(n);
							},
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
					layer.close(loadDel); 
				}
		});
}

function table_check(){
	var $num;
	$("#checkmand").click(function(){	
		if($(this).is(":checked")){
		$("input[name='check_name']").attr("checked",true);
		   $num=$("input[name='check_name']").length;
		}else{
		  $("input[name='check_name']").attr("checked",false);
		}
	});
	
	$("input[name='check_name']").click(function(){
		if($("input[name='check_name']").size()==$("input[name='check_name']:checked").size()){
			$("#checkmand").attr("checked",true);
			return;
		}
		$("input[name='check_name']").each(function(index, e) {
	        if($(this).is(":checked")==false){
				$("#checkmand").attr("checked",false);
					return;
				}
	   	});
	});
}

function hang_xg(){
	$("tr[class='allContent']").mousemove(function(){
		$(this).addClass("_color").siblings().removeClass("_color");
	});
	$("tr[class='allContent']").mouseleave(function(){
		$(this).removeClass("_color");
	});
	/* $("tr[class='allContent']").dblclick( function () { 
		$(this).removeClass("d_color");
		$(this).addClass("d_color").siblings().removeClass("d_color");
		layer.open({
		    title: ['查看','background-color:#CC0000; color:#fff;'],
		    type: 2,
		    area: ['800px', '500px'],
		    content: "${ctx}/firstproductdemand/show.net"
		});
	}); */
}

$(document).ready(function () {
	window.gridFirstproducteMandTable(1);
	window.table_check();
	window.hang_xg();
	$("#searchMandTable").click(function() {
		window.gridFirstproducteMandTable(1);
		window.table_check();
		window.hang_xg();
	});
	//*******************************导出***************************/
	$("#excelFirstproducteMand").click(function() {
		var execlload = layer.load(2);
		$.ajax({
				type : "POST",
				dataType:"json",
				url : "${ctx}/execl/excelMand.net?"+getIds(),
				data: $("#queryDemandForm").serialize(),
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
//方案查询
function detail_query(obj){
	var tablleId = obj+"_tool";
	var queryTable =obj+"_table";
	$("#"+tablleId+" .plan_list_content").remove();
	$.ajax({
			type : "POST",
			url : "${ctx}/schemedesign/loadDetail.net?fid="+obj,
			dataType:"json",
			async:false,
			data:$("#"+queryTable).serialize(),
			success : function(response) {
					$.each(response.list, function(i, evl) {
					 	var  htmlTool_2 =[
									'<tr class="plan_list_content">',
										'<td>',evl.fnumber,'</td>',
										'<td>',evl.customerName,'</td>',
										'<td>',evl.supplierName,'</td>',
										'<td>',evl.fcreator,'</td>',
										'<td>',evl.crateTime_ymr,'<br />',evl.crateTime_sfm,'</td>',
										'<td>',evl.fauditorid,'</td>',
										'<td>',evl.fconfirmer,'</td>',
										'<td>',evl.confirmTime_ymr,'<br />',evl.confirmTime_sfm,'</td>',
										'<td><a href="javascript:void(0);"  onclick="onclickImg();">附件下载</a></td>',
										'<td class="plan_setting">',
										   '<a href="javascript:void(0);"  onclick="onclickImg();">确认</a>',
										   '<a href="javascript:void(0);"  onclick="onclickImg();">评价</a>',
										'</td>',
									'</tr>'].join("");
						$(htmlTool_2).appendTo("#"+tablleId);
					 });
			}
	});
}



//展开方案设计
function scheme_Design(obj){
	var fasjT_tr =obj+"_tr";
	var fasjT_td =obj+"_td";
	var queryTable =obj+"_table";
	var tool = obj+"_tool";
	$.ajax({
			type : "POST",
			url : "${ctx}/schemedesign/loadDetail.net",
			dataType:"json",
			async:false,
			data:{"fid":obj},
			success : function(response) {
				if(response.totalRecords>0){
					$('#'+fasjT_td).remove();
					var narrowTool ='<td id='+fasjT_td+' colspan="10"><a href="javascript:void(0);" onclick="narrow_Design(\''+obj+'\');">方案设计&nbsp;&nbsp;<img src="${ctx}/css/images/up.png"/></a></td>';
					$('#'+fasjT_tr).html(narrowTool);
					var  htmlTool =['<td colspan="10">',
										'<table id='+tool+' cellpadding="0" cellspacing="0" border="0" width="1240">',
											'<tr height="60">',
												'<td colspan="10">',
													'<form id='+queryTable+'>',
													'<input type="button" disabled="disabled" value="方案设计" class="_title"/> ',
													'<input type="button" value="查询" class="_submit1" onclick="detail_query(\''+obj+'\');"/>',
													'<input type="text" class="keyWord1" id="searchKey" name="schemedesignQuery.searchKey" placeholder="请输入关键字" />   ',
													'</form>',
												'</td>',
											'</tr>',
											'<tr class="firstTr">',
												'<td width="106">方案名称</td>',
												'<td width="170">客户名称</td>',
												'<td width="110">制造商</td>',
												'<td width="120">创建人</td>',
												'<td width="147">创建时间</td>',
												'<td width="110">审核人</td>',
												'<td width="110">确认人</td>',
												'<td width="130">确认时间</td>',
												'<td>附件</td>',
												'<td width="100">操作</td>',
											'</tr>',
										'</table>',
									'</td>'].join("");
					$(htmlTool).appendTo('#'+obj);
					$.each(response.list, function(i, evl) {
					 	var  htmlTool_2 =[
									'<tr class="plan_list_content">',
										'<td>',evl.fnumber,'</td>',
										'<td>',evl.customerName,'</td>',
										'<td>',evl.supplierName,'</td>',
										'<td>',evl.fcreator,'</td>',
										'<td>',evl.crateTime_ymr,'<br />',evl.crateTime_sfm,'</td>',
										'<td>',evl.fauditorid,'</td>',
										'<td>',evl.fconfirmer,'</td>',
										'<td>',evl.confirmTime_ymr,'<br />',evl.confirmTime_sfm,'</td>',
										'<td><a href="javascript:void(0);"  onclick="onclickImg();">附件下载</a></td>',
										'<td class="plan_setting">',
										   '<a href="javascript:void(0);"  onclick="onclickImg();">确认</a>',
										   '<a href="javascript:void(0);"  onclick="onclickImg();">评价</a>',
										'</td>',
									'</tr>'].join("");
						$(htmlTool_2).appendTo("#"+tool);
					 });
				}else{
					layer.msg('没有相关方案！');
				}
			}
	});
}
//缩小方案设计
function narrow_Design(obj){
	$('#'+obj).html("");
	var fasjT_tr =obj+"_tr";
	var fasjT_td =obj+"_td";
	$('#'+fasjT_td).remove();
	var narrowTool ='<td id='+fasjT_td+' colspan="10"><a href="javascript:void(0);" onclick="scheme_Design(\''+obj+'\');">方案设计&nbsp;&nbsp;<img src="${ctx}/css/images/down.png"/></a></td>';
	$('#'+fasjT_tr).html(narrowTool);
}
//点击状态选项卡
function o_click(obj1,obj2){
	$("#"+obj1).addClass("current");
	$("#"+obj1).siblings().removeClass("current");
	var a=document.getElementsByClassName("_check");
	for(var i = 0;i<a.length;i++){
		if(a[i].type == "checkbox") a[i].checked = false;
	}
	if(obj2=='全部'){
	    $("#fstate").val("");
	}else if(obj2=='存草稿'){
		$("#fstate").val("存草稿");
	}else if(obj2=='已发布'){
		$("#fstate").val("已发布");
	}else if(obj2=='已分配'){
		$("#fstate").val("已分配");
	}else if(obj2=='已接收'){
		$("#fstate").val("已接收");
	}else if(obj2=='已设计'){
		$("#fstate").val("已设计");
	}else if(obj2=='已完成'){
		$("#fstate").val("已完成");
	}else if(obj2=='已关闭'){
		$("#fstate").val("已关闭");
	}
	window.gridFirstproducteMandTable(1);
	window.table_check();
	window.hang_xg();
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
//新建窗口
function create(){
		layer.open({
		    title: ['新建','background-color:#CC0000; color:#fff;'],
		    type: 2,
		    area: ['1200px', '470px'],
		    content: "${ctx}/firstproductdemand/create.net",
		    cancel: function(index){
		    	//var issc =$(document.getElementById('layui-layer-iframe'+index).contentWindow.document.getElementById('sc_photo')).html();
		        var tbale = layer.getChildFrame('#sc_photo', index).find("tr");
		    	if(tbale.length!=0){
		    	  for(var i =0;i<tbale.length;i++){
		    	  	//同步删除图片
					 	$.ajax({
								type : "POST",
								url : "${ctx}/productfile/deleteImg.net",
								dataType:"text",
								async:false,
								data: {"fid":tbale.attr("id")},  
								error:function (){
									layer.alert('操作失败！');
								}
						});
		    	  }
		    	}
		    }
		});
}

function edit(obj){
		layer.open({
		    title: ['修改','background-color:#CC0000; color:#fff;'],
		    type: 2,
		     area: ['1200px', '470px'],
		    content: "${ctx}/firstproductdemand/edit.net?id="+obj,
		    cancel: function(index){ 
		    	var tbale = layer.getChildFrame('#sc_photo', index).find("tr");
		    	if(tbale.length==0){
		    		layer.msg('附件不能为空！');
			    	return false; 
		    	}
		    }
		});
}
	
//删除
function del(){
		var valuelast = '';
		$('input:checked').parent().parent().not($(".firstTr")).each(function(i){
				if (i == 0) {
					valuelast += $(this).find("td").eq(4).text();
				}else{
					valuelast += "," + $(this).find("td").eq(4).text();
				}
		});
		if(getIds()==""){
			 layer.msg('请先选中记录');
			 return;
		}else{
			 var strs= new Array();
			 strs=valuelast.split(",");
			 for (i=0;i<strs.length ;i++ ){
				if(strs[i] !='存草稿'){
					layer.msg('只有<strong>&nbsp;&nbsp;存草稿&nbsp;&nbsp;</strong>状态可删');
					return;
				}
			 } 
			 $.ajax({
						type : "POST",
						url : "${ctx}/firstproductdemand/delete.net",
						data : getIds(),
						success : function(response) {
							if (response == "success") {
								layer.alert('操作成功！', function(alIndex){
                        			window.gridFirstproducteMandTable(1);
                        			window.table_check();
							        window.hang_xg();										
                        			if($("tr[class='allContent']").length==0){
                        				if($("#checkmand").is(':checked')){
											$("#checkmand").attr("checked",false);						
										}
                        			}
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
function single_del(obj){
	$.ajax({
			type : "POST",
			url : "${ctx}/firstproductdemand/delete.net",
			data : {"fids":obj},
			success : function(response) {
				if (response == "success") {
					layer.alert('操作成功！', function(alIndex){
                   		window.gridFirstproducteMandTable(1);
                   		window.table_check();
						window.hang_xg();
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
function cancel_fb(obj){
	$.ajax({
				type : "POST",
				url : "${ctx}/firstproductdemand/cancel_fb.net",
				dataType:"text",
				data: {"fid":obj},  
				success : function(response) {
					if(response =="success"){
						layer.alert('操作成功！', function(index){
							window.gridFirstproducteMandTable(1);
							window.table_check();
							window.hang_xg();
							layer.close(index);
							}
						);
					}else{
						layer.alert('操作失败！', function(index){layer.close(index);});
					}
				},
				error:function (){
					layer.alert('操作失败！', function(index){layer.close(index);});
				}
		});
}

function sure_fb(obj){
	$(this).find("td").eq(0).find("input[type='checkbox']").attr("checked", true);
	$.ajax({
				type : "POST",
				url : "${ctx}/firstproductdemand/sure_fb.net",
				dataType:"text",
				data: {"fid":obj},  
				success : function(response) {
					if(response =="success"){
						layer.alert('操作成功！', function(index){
							window.gridFirstproducteMandTable(1);
							window.table_check();
							window.hang_xg();
							layer.close(index);
							}
						);
					}else{
						layer.alert('操作失败！', function(index){layer.close(index);});
					}
				},
				error:function (){
					layer.alert('操作失败！', function(index){layer.close(index);});
				}
		});
}

</script>
</head>
<body>
	<div id="nav">
        <div id="container">
        	<div class="instruct">
            	<p>我的需求&nbsp;&nbsp;&gt;&nbsp;&nbsp;<strong>需求列表</strong></p>
            </div>
            <div class="menu">
            	<ul>
                	<li id="all_l" class="current"><a href="javascript:void(0);" onclick="o_click('all_l','全部');">全部</a></li>
                    <li id="ccg_l"><a href="javascript:void(0);" onclick="o_click('ccg_l','存草稿');">存草稿</a></li>
                    <li id="yfb_l"><a href="javascript:void(0);" onclick="o_click('yfb_l','已发布');">已发布</a></li>
                    <li id="yfp_l"><a href="javascript:void(0);" onclick="o_click('yfp_l','已分配');">已分配</a></li>
                    <li id="yjs_l"><a href="javascript:void(0);" onclick="o_click('yjs_l','已接收');">已接收</a></li>
                    <li id="ysj_l"><a href="javascript:void(0);" onclick="o_click('ysj_l','已设计');">已设计</a></li>
                    <li id="ywc_l"><a href="javascript:void(0);" onclick="o_click('ywc_l','已完成');">已完成</a></li>
                    <li id="ygb_l"><a href="javascript:void(0);" onclick="o_click('ygb_l','已关闭');">已关闭</a></li>
                </ul>
            </div>
            <div class="condition">
            	<form id="queryDemandForm">
                	<input type="button" value="新增" class="_add" onclick="create()"/>
                    <input type="button" value="删除" class="_del" onclick="del()"/>
                    <input type="button" value="导出" class="_excel" id="excelFirstproducteMand"/>
                    <input type="button" value="查询" class="_submit"  id="searchMandTable"/>
                    <input type="hidden"  id="fstate" name="firstproductdemandQuery.fstate" />
                    <input type="text" class="keyWord" id="searchKey" name="firstproductdemandQuery.searchKey" placeholder="请输入关键字"/>
                </form>
            </div>
            <table id="firstproductdemandTool" cellpadding="0" cellspacing="0" border="0" width="1260">
            	<tr class="firstTr">
                	<td width="90"><input id ="checkmand" type="checkbox" class="_check" /></td>
                    <td width="130">需求编号</td>
                    <td width="130">需求名称</td>
                    <td width="130">客户名称</td>
                    <td width="130">需求状态</td>
                    <td width="130">制造商</td>
                    <td width="130">发布人</td>
                    <td width="130">发布时间</td>
                    <td width="130">联系设计师</td>
                    <td width="130">操作</td>
                </tr>
            </table>
            <div id="kkpager"></div>
        </div>
  </div>
</body>
</html>
