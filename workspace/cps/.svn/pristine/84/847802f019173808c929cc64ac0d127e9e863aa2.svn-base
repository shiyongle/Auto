<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>送货凭证列表</title>
<script type="text/javascript" language="javascript" src="<c:url value='/js/_common.js'/>"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/kkpager.css"  />
<script type="text/javascript" language="javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/kkpager.js" ></script>
<style>
	*{margin:0 auto;padding:0;font-family:"宋体"}#nav{width:1070px}.p_title{font-size:12px;font-family:"宋体";height:35px;line-height:35px;background-color:#f1f1f1}.conditionForm{height:65px;width:1045px}.conditionForm .condition{height:47px;width:400px;padding-top:18px}.conditionForm .allList{height:47px;width:410px;padding-top:18px}.conditionForm .condition,.conditionForm .allList{float:left;font-size:14px}.condition *{display:inline-block;float:left}.condition .listTime{width:70px;height:30px;line-height:30px;text-align:center}.condition .startTime,.condition .lastTime{height:27px;width:115px;padding-left:5px;border:1px solid lightgray;border-right:0}.condition .for_startTime,.condition .for_lastTime{width:20px;height:27px;float:left;border:1px solid lightgray;border-left:none;background:url(../css/images/sj.png) white 0 2px no-repeat}.condition .connect{height:30px;width:30px;line-height:30px;text-align:center}.allList .keyWords{display:inline-block;width:50px;height:30px;float:left;line-height:30px;text-align:center}.allList .for_key{display:inline-block;float:left;height:27px;width:280px;border:1px solid lightgray;border-right:0}.allList .selector{float:left;height:27px;width:73px;line-height:27px;text-align:center;background:url(../css/images/xlk.png) 58px 12px no-repeat;border:1px solid lightgray;border-left:none;letter-spacing:5px;color:#a8a8a8;cursor:pointer}.allList .selectlist{width:73px;height:auto;border:1px solid lightgray;text-align:center;letter-spacing:6px;position:relative;left:163px;*+left:-3px;top:28px;*+top:0;z-index:100;border-top:0;background-color:#fff;display:none;cursor:pointer}.allList .selectlist .changeall{color:red}.allList .selectlist li{list-style:none;height:28px;line-height:28px;color:#a8a8a8;cursor:pointer}.conditionForm ._submit{display:inline-block;border:1px solid lightgray;background:0;width:78px;height:29px;letter-spacing:8px;text-align:center;margin-top:18px;margin-left:20px;cursor:pointer}#forData{width:1045px}#forData table{border-collapse:collapse;text-align:center;font-size:14px}#forData .menuBtn input{display:inline-table;float:left;width:78px;height:20px;border:1px solid #999;margin-right:5px!important;cursor:pointer;background-color:#fff;color:#545454}#forData .menuBtn input:hover{color:white;background-color:#C00;border:#c00;}#forData .menuBtn a{display:inline-table;width:70px;height:20px;border:1px solid red;text-decoration:none;font-size:12px;color:red;float:right;line-height:20px}#forData .menuBtn .choice{color:white;background-color:#C00;}#forData .customer{height:30px;background-color:#ccc;color:white;font-size:12px;border:1px solid #ccc;border-bottom:0}#forData .customer span{display:inline-block;height:30px;line-height:30px;text-align:left}#forData .Data{border:1px solid lightgray;border-top:0}#forData .Data .choice{color:white;background-color:red;border:red}#forData ._del,#forData ._recover{display:inline-table;width:78px;height:22px;border:1px solid #999;text-decoration:none;color:#666;line-height:22px;font-size:12px}#forData .has_recover{display:inline-table;width:78px;height:22px;border:1px solid #ccc;text-decoration:none;color:white;background-color:#ccc;line-height:22px;font-size:12px}#forData ._detail{color:blue}#forData ._detail:hover{color:red}#kkpager{width:1015px;height:100px;line-height:100px;text-align:right;padding-right:30px;background-color:#f1f1f1;background-image:}
	.tr2{
		border-bottom:1px solid lightgray;
	}
 </style>
<script type="text/javascript">
function getSendVoucherTable(page){
		var loadIndex = parent.layer.load(2);
		$.ajax({
				type : "POST",
				url : "${ctx}/saledeliver/load.net?pageNo="+page,
				dataType:"json",
				async: false,
				//data:obj,
				success : function(response) {
					$(".customer").remove();
					$(".Data").remove();
					$(".kong").remove();
					if(response.list.length>0){
						$.each(response.list, function(i, ev) {
							var  html;
							var toolTr = ev.fid+"_2tabe_tr";
							var toolA = ev.fid+"_a";
							if($("#"+toolTr).length>0){
									   $("#"+toolTr).find("tr").addClass("tr2");
									   var html_2tr = [
									   					'<tr height="95">',
															'<td width="190">',ev.fname,'<br/><br/>',ev.fspec,'</td>',
															'<td width="120">',ev.famount,'',ev.funit,'</td>',
															'<td width="85">',ev.fprice,'</td>',
															'<td width="144">2015-05-xx</td>',
															'<td width="100">',ev.fprice,'</td>',
														'</tr>',
									   	               ].join("");
									   $(html_2tr).appendTo("#"+toolTr);
							}else{
										html =['<tr class="customer">',
														'<td colspan="8">',
															'<span style="width:25px;float:right;background:url(../css/images/iconfont-lajitong9.png) -4px 1px no-repeat;"></span>',
															'<span style="width:255px;">客户名称:',ev.fcustomer,'</span>',
															'<span style="width:310px;">联系电话:',ev.fphone,'</span>',
															'<span style="width:425px;">出库时间:',ev.fprinttime,'</span>',
														'</td>',
												'</tr>',
												'<tr class="Data" id='+ev.fid+'>',
														'<td><input type="checkbox" name="product"/><input  type="hidden" name="fid" value="',ev.fid,'"/></td>',
														'<td><a href="#" class="_detail">',ev.fnumber,'</a></td>',
														'<td colspan="5">',
															'<table id='+toolTr+' cellpadding="0" cellspacing="0" border="0" width="639">',
																'<tr height="95">',
																	'<td width="190">',ev.fname,'<br/><br/>',ev.fspec,'</td>',
																	'<td width="120">',ev.famount,'',ev.funit,'</td>',
																	'<td width="85">',ev.fprice,'</td>',
																	'<td width="144">2015-05-xx</td>',
																	'<td width="100">',ev.fprice,'</td>',
															    '</tr>',
															'</table>',
														'</td>',
														'<td>',
															'<a href="javascript:void(0);" class="_del" onmousemove="for_delAndfor_recover(this);" onmouseout="for_delAndfor_recover_forleave(this);"  onclick="dju_edit(\''+ev.fid+'\');">修改</a>',
															'<br/><br/><a id='+toolA+' href="javascript:void(0);" class="_recover" onmousemove="for_delAndfor_recover(this);" onmouseout="for_delAndfor_recover_forleave(this);"  onclick="dju_collection(\''+ev.fid+'\');">',ev.fstate,'</a>',
														'</td>',
												'</tr>',
												'<tr height="25" class="kong">',
														'<td colspan="8">&nbsp;</td>',
												'</tr>'
										].join("");
							}
							$(html).appendTo("#sendVoucherTableTool");
						});
						//子页面设置父级iframe高度
						window.getHtmlLoadingAfterHeight();
						/********************************************循环添加TR结束******************************/
						kkpager.pno =response.pageNo;
						kkpager.total =Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
						kkpager.totalRecords =response.totalRecords;
						kkpager.generPageHtml({
							pagerid : 'kkpager',
							click : function(n){
								window.getSendVoucherTable(n);
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
						$("#sendVoucherTableTool .Data").each(function(i) {
			                var t = $(this).attr('id');
			                var aid = t+"_a";
			                if($("#"+aid).html()=='0'){
			                	$("#"+aid).text('单据回收');
			                }else if($("#"+aid).html()=='2'){
			                	$("#"+aid).text('已回收');
			                	$("#"+aid).removeAttr("onmousemove");
			                	$("#"+aid).removeAttr("onmouseout");
			                	$("#"+aid).removeAttr("onclick");
			                	$("#"+aid).attr("class","has_recover");
			                }
            		     });
					}
					parent.layer.close(loadIndex); 
				}
		});
}
$(document).ready(function () {
	//初始化iframe高度
	window.getHtmlLoadingBeforeHeight();
	window.getSendVoucherTable(1);
});

</script>
</head>

<body>
	<div id="nav">
    	<p class="p_title">平台首页&nbsp;&gt;&nbsp;送货凭证</p>
    	<form action="#" method="post" class="conditionForm">
        	<div class="condition">
                <span class="listTime">开单时间:</span><input type="text" class="startTime" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/><a href="javascript:void(0);" class="for_startTime" onclick="document.getElementsByClassName('startTime')[0].focus();"></a>
                <span class="connect">-</span>
                <input type="text" class="lastTime" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/><a href="javascript:void(0);" class="for_lastTime" onclick="document.getElementsByClassName('lastTime')[0].focus();"></a> 
            </div>
            <div class="allList">
            	<span class="keyWords">关键字:</span><input type="text" class="for_key"/>
            	<div class="selector">全部</div>
                <ul class="selectlist">
                	<li style="position:relative;top:-30px;*+position:static;">全部</li>
                    <li>客户</li>
                    <li>编号</li>
                    <li>规格</li>
                    <li>名称</li>
                    <li>电话</li>
                </ul>
            </div>
            <input type="button" value="搜索" class="_submit" onclick="onclickImg();"/>
        </form>
        <div id="forData">
        	<table cellpadding="0" cellspacing="0" border="0" width="1045" id="sendVoucherTableTool">
            	<tr style="height:35px;background-color:#F0F0F0;font-size:12px;color:#545454;">
                	<td width="70"><input type="checkbox" id="product"/></td>
                    <td width="140"><p style="height:20px;width:138px;line-height:20px;border-left:1px solid #ccc;border-right:1px solid #ccc;">出库单编号</p></td>
                    <td width="190">名称/规格</td>
                    <td width="120"><p style="height:20px;width:118px;line-height:20px;border-left:1px solid #ccc;border-right:1px solid #ccc;">数量</p></td>
                    <td width="85">单价</td>
                    <td width="144"><p style="height:20px;width:142px;line-height:20px;border-left:1px solid #ccc;border-right:1px solid #ccc;">开单时间</p></td>
                    <td width="100">合计</td>
                    <td width="196"><p style="height:20px;width:195px;line-height:20px;border-left:1px solid #ccc;">操作</p></td>
                </tr>
                <tr height="45" class="menuBtn">
                	<td colspan="8">
                    	<input type="button" value="新建"      onclick="onclickImg();"/>
                        <input type="button" value="设为常用"  onclick="onclickImg();"/>
                        <input type="button" value="导出Excel" onclick="onclickImg();"/>
                        <input type="button" value="打印预览"  onclick="printPreview();"/>
                         <input type="button" value="对账单"  onclick="statement();"/>
                      <a href="javascript:void(0);" onclick="menuBtnLink(this);">已回收</a><a href="javascript:void(0);" class="choice" onclick="menuBtnLink(this);">全部</a>
                    </td>
                </tr>
            </table>
        </div>
        <div id="kkpager"></div>
    </div>
<script type="text/javascript">
//针对360浏览器
$(".for_key").focus(function(e){
	$(this).css("border-color","lightgray");
	$(this).css("outline","none");
});
//模拟下拉框
$(".selector").mouseover(function(e){
	$(this).css("border-bottom","none");
	$(".selectlist").show();
	$(this).css("background-image","url(../css/images/sgk.png)");
});
$(".selector").mouseout(function(e){
	$(".selectlist").hide();
	$(this).css("border-bottom-width","1px");
	$(this).css("border-bottom-style","solid");
	$(this).css("border-bottom-color","lightgray");
	$(this).css("background-image","url(../css/images/xlk.png)");
});
$(".selectlist").mouseover(function(e){
	$(this).show();
	$(".selector").css("background-image","url(../css/images/sgk.png)");
});
$(".selectlist").mouseout(function(e){
	$(this).hide();
	$(".selector").css("background-image","url(../css/images/xlk.png)");
});
$(".selectlist li").mouseover(function(e){
	$(this).addClass("changeall");
	$(this).siblings().removeClass("changeall");
});
$(".selectlist li").click(function(e){
	$(".selector").text($(this).text());
	$(".selectlist").hide();
	$(".selector").css("background-image","url(../css/images/xlk.png)");
});
//全选
var $num;
$("#product").click(function(){
	if($(this).is(":checked")){
		$("input[name='product']").attr("checked",true);
		$num=$("input[name='product']").length;
	}else{
		$("input[name='product']").attr("checked",false);
	}
});
	
$("input[name='product']").click(function(){
	if($("input[name='product']").size()==$("input[name='product']:checked").size()){
		$("#product").attr("checked",true);
		return;
	}
	$("input[name='product']").each(function(index, e) {
        if($(this).is(":checked")==false){
			$("#product").attr("checked",false);
				return;
		}
  	});
});
//回收or已回收的点击切换
function menuBtnLink(t){
		$(t).addClass("choice");
		$(t).siblings().removeClass("choice");
		onclickImg();
}
//表格数据操作按钮修改和单据回收
function for_delAndfor_recover(t){
		$(t).addClass("choice");
}
function for_delAndfor_recover_forleave(t){
		$(t).removeClass("choice");
}
//修改
function dju_edit(obj){
	alert(obj);
}
//单据回收
function dju_collection(obj){
	alert(obj);
}

function printPreview(){
	onclickImg();
	//alert(getCommonIds());
}
function statement()
{
	var i = parent.layer.open({
	    title:'',move:false,
	    type: 2,
	    anim:true,
 	    offset: Math.max(window.screen.height-442,550)/2+'px',
 	    area: ['850px', '442px'],
	    content:  window.getRootPath()+"/saledeliver/getRptInfo.net"
	});

}
</script>
</body>
</html>
