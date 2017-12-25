<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>送货凭证列表</title>
<script type="text/javascript" language="javascript"
	src="<c:url value='/js/_common.js'/>"></script>
<script src="${ctx}/js/jquery.jqprint-0.3.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/kkpager.css" />
<script type="text/javascript" language="javascript"
	src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" language="javascript"
	src="${ctx}/js/kkpager.js"></script>
<link
	href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css"
	rel="stylesheet">
	<link rel="stylesheet" href="${ctx}/css/daterangepicker-bs3.css" />
	<script src="${ctx}/js/moment.js" type="text/javascript"></script>
	<script src="${ctx}/js/daterangepicker.js" type="text/javascript"></script>
	<style>
* {
	margin: 0 auto;
	padding: 0;
	font-family: "宋体"
}
#nav {
	width: 1070px
}

.p_title {
	font-size: 12px;
	font-family: "宋体";
	height: 35px;
	line-height: 35px;
	background-color: #f1f1f1
}

.conditionForm {
	height: 50px;
	width: 1045px
}

.conditionForm .condition {
	height: 47px;
	width: 400px;
	padding-top: 18px
}

.conditionForm .allList {
	height: 47px;
	width: 410px;
	padding-top: 18px
}

.conditionForm .condition, .conditionForm .allList {
	float: left;
	font-size: 14px
}

.condition * {
	display: inline-block;
	float: left
}

.condition .listTime {
	width: 70px;
	height: 30px;
	line-height: 30px;
	text-align: center
}

.condition .startTime, .condition .lastTime {
	height: 27px;
	width: 115px;
	padding-left: 5px;
	border: 1px solid lightgray;
	border-right: 0
}

.condition .for_startTime, .condition .for_lastTime {
	width: 20px;
	height: 27px;
	float: left;
	border: 1px solid lightgray;
	border-left: none;
	background: url(${ctx}/css/images/sj.png) white 0 2px no-repeat
}

.condition .connect {
	height: 30px;
	width: 30px;
	line-height: 30px;
	text-align: center
}

.allList .keyWords {
	display: inline-block;
	width: 60px;
	height: 30px;
	float: left;
	line-height: 30px;
	text-align: center
}

.allList .for_key {
	display: inline-block;
	float: left;
	height: 27px;
	width: 280px;
	border: 1px solid lightgray;
	padding-left: 5px;
}

.allList .selector {
	float: left;
	height: 27px;
	width: 73px;
	line-height: 27px;
	text-align: center;
	background: url(${ctx}/css/images/xlk.png) 58px 12px no-repeat;
	border: 1px solid lightgray;
	border-left: none;
	letter-spacing: 5px;
	color: #a8a8a8;
	cursor: pointer
}

.allList .selectlist {
	width: 73px;
	height: auto;
	border: 1px solid lightgray;
	text-align: center;
	letter-spacing: 6px;
	position: relative;
	left: 163px; *+
	left: -3px;
	top: 28px; *+
	top: 0;
	z-index: 100;
	border-top: 0;
	background-color: #fff;
	display: none;
	cursor: pointer
}

.allList .selectlist .changeall {
	color: red
}

.allList .selectlist li {
	list-style: none;
	height: 28px;
	line-height: 28px;
	color: #a8a8a8;
	cursor: pointer
}

.conditionForm ._submit {
	display: inline-block;
	border: 1px solid lightgray;
	background: 0;
	width: 78px;
	height: 29px;
	letter-spacing: 8px;
	text-align: center;
	margin-top: 18px;
	margin-left: 20px;
	cursor: pointer
}

#forData {
	width: 1045px
}

#forData table {
	border-collapse: collapse;
	text-align: center;
	font-size: 14px
}

#forData .menuBtn a {
	text-decoration: none;
	display: inline-table;
	float: left;
	width: 78px;
	height: 20px;
	border: 1px solid #999;
	margin-right: 5px !important;
	cursor: pointer;
	background-color: #fff;
	color: #545454
}

#forData .menuBtn a:hover {
	color: white;
	background-color: red;
	border: red
}

#forData .menuBtn .choice {
	color: white;
	background-color: red
}

#forData .customer {
	height: 30px;
	background-color: #f0f0f0;
	font-size: 12px;
	border: 1px solid #ccc;
}

#forData .customer span {
	display: inline-block;
	height: 30px;
	line-height: 30px;
	text-align: left
}

#forData .Data {
	border: 1px solid lightgray;
	border-top: 0
}

#forData .Data .choice {
	color: white;
	background-color: red;
	border: red
}

#forData ._del, #forData ._recover {
	display: inline-table;
	width: 78px;
	height: 22px;
	border: 1px solid #999;
	text-decoration: none;
	color: #666;
	line-height: 22px;
	font-size: 12px
}

#forData .has_recover {
	display: inline-table;
	width: 78px;
	height: 22px;
	border: 1px solid #ccc;
	text-decoration: none;
	color: white;
	background-color: #ccc;
	line-height: 22px;
	font-size: 12px
}

#forData ._detail {
	color: blue
}

#forData ._detail:hover {
	color: red
}

#sendVoucherTableTool {
	width: 1045px;
}

#kkpager {
	width: 1050px;
	height: 100px;
	line-height: 100px;
	text-align: right;
	padding-right: 30px;
	background-color: #f1f1f1;
	background-image:
}

.tr2 {
	border-bottom: 1px solid lightgray;
}

.buttonTd a {
	display: inline-table;
	text-decoration: none;
	height: 20px;
	width: 80px;
	border: 1px solid lightgray;
	color: black;
	text-align: center;
	line-height: 20px;
	cursor: pointer;
	margin: 8px;
}

.buttonTd a:hover {
	color: #fff;
	background-color: red;
}

.querytime {
	/*width:234px;*/
	width: 220px;
	height: 27px;
	line-height: 27px;
	padding-left: 5px;
	margin-left: 10px;
	/* 	float:left; */
	border: 1px solid lightgray;
}

.select-down {
	position: absolute;
	top: 28px;
	/*right: 6px;*/
	right: 20px;
	height: 0;
	width: 0;
	overflow: hidden;
	font-size: 0;
	border-color: #333 transparent transparent transparent;
	border-style: solid;
	border-width: 6px;
	cursor: pointer;
}

.tdTitle {
	border-left: 1px solid #ccc;
	border-right: 1px solid #ccc;
}

span.customerTip{width:300px;}

#kkpager_btn_go {
	width: 44px;
	height: 18px;
	line-height: 18px;
	padding: 0px;
	font-family: arial, 宋体, sans-serif;
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

#kkpager_btn_go_input {
	line-height: 19px;
	font-size: smaller;
}
</style>
	<script type="text/javascript">
		function getSendVoucherTable(page) {
			var loadIndex = parent.layer.load(2);
			var obj = $('.conditionForm').serializeArray();//序列化
			$
					.ajax({
						type : "POST",
						url : "${ctx}/saledeliver/load.net?pageNo=" + page,
						dataType : "json",
						async : false,
						data : obj,
						success : function(response) {
							$(".customer").remove();
							$(".Data").remove();
							$(".kong").remove();
							if (response.list.length > 0) {
								$
										.each(
												response.list,
												function(i, ev) {
													var html;
													var toolTr = ev.fid
															+ "_2tabe_tr";
													var toolA = ev.fid + "_a";
													var d = new Date(
															ev.fcreatetime);
													if ($("#" + toolTr).length > 0) {
														$("#" + toolTr).find(
																"tr").addClass(
																"tr2");
														var html_2tr = [
																// 									   					'<tr height="75">',
																'<tr height="40">',//2016年4月13日11:18:23 HT 显示高度
																'<td width="300">',
																ev.fname,
																'/',
																ev.fspec,
																'</td>',
																'<td width="120">',
																ev.famount,
																'',
																ev.funit,
																'</td>',
																'<td width="120">',
																ev.fdanjia,
																'</td>',
																// 															'<td width="144">',d.getFullYear()+"-"+eval(d.getMonth()+1)+"-"+d.getDate(),'</td>',
																'<td width="120">',
																ev.fprice,
																'</td>',
																'</tr>', ]
																.join("");
														$(html_2tr).appendTo(
																"#" + toolTr);
													} else {
														var p = new Date(
																ev.fprinttime);
														html = [
																'<tr class="customer">',
																'<td colspan="7">',
																'<span title="删除" style="width:25px;float:right;background:url(${ctx}/css/images/del1.png) 0px 4px no-repeat;cursor: pointer;" onclick="delSaledeliver(\''
																		+ ev.fid
																		+ '\')"></span>',
																'<span title="修改" style="width:25px;float:right;background:url(${ctx}/css/images/edit1.png) 0px 4px no-repeat;cursor: pointer;margin-right:3px;" onclick="dju_edit(\''
																		+ ev.fid
																		+ '\');"></span>',//2016年4月13日11:11:49 HT
																'<span class="customerTip">客户名称:',
																ev.fcustomer,
																'</span>',
																'<span class="customerTip">联系电话:',
																ev.fphone,
																'</span>',
																'<span class="customerTip">开单时间:',
																d.getFullYear()
																		+ "-"
																		+ eval(d
																				.getMonth() + 1)
																		+ "-"
																		+ d
																				.getDate(),
																'</span>',
																'<span style="color:#00e;cursor: pointer;" onclick="seeSaledeliver(\''
																		+ ev.fid
																		+ '\')">详情</span>',
																'</td>',
																'</tr>',
																'<tr class="Data" id='+ev.fid+'>',
																'<td><input type="checkbox" name="product"/><input  type="hidden" name="fid" value="',ev.fid,'"/></td>',
																'<td>',
																ev.fnumber,
																'</td>',//<a href="#" class="_detail"></a>
																'<td colspan="4">',
																'<table id='+toolTr+' cellpadding="0" cellspacing="0" border="0">',
																// 																'<tr height="75">',
																'<tr height="40">',
																'<td width="300">',
																ev.fname,
																'/',
																ev.fspec,
																'</td>',
																'<td width="120">',
																ev.famount,
																'',
																ev.funit,
																'</td>',
																'<td width="120">',
																ev.fdanjia,
																'</td>',
																// 																	'<td width="144">',d.getFullYear()+"-"+eval(d.getMonth()+1)+"-"+d.getDate(),'</td>',
																'<td width="120">',
																ev.fprice,
																'</td>',
																'</tr>',
																'</table>',
																'</td>',
																'<td class="buttonTd">',
																// 															'<a href="javascript:void(0);"  onclick="dju_edit(\''+ev.fid+'\');">修改</a>',
																// 															'<br><a onclick="seeSaledeliver(\''+ev.fid+'\')">查看</a>',
																'<a id='
																		+ toolA
																		+ ' href="javascript:void(0);"  onclick="dju_collection(\''
																		+ ev.fid
																		+ '\');">',
																ev.fstate,
																'</a>',
																'</td>',
																'</tr>',
																'<tr height="5" class="kong">',
																'<td colspan="7"></td>',
																'</tr>' ]
																.join("");
													}
													$(html)
															.appendTo(
																	"#sendVoucherTableTool");
												});
								//子页面设置父级iframe高度
								window.getHtmlLoadingAfterHeight();
								/********************************************循环添加TR结束******************************/
								kkpager.pno = response.pageNo;
								kkpager.total = Math
										.floor((response.totalRecords
												+ response.pageSize - 1)
												/ (response.pageSize));
								kkpager.totalRecords = response.totalRecords;
								kkpager.generPageHtml({
									pagerid : 'kkpager',
									click : function(n) {
										window.getSendVoucherTable(n);
										this.selectPage(n);
									},
									pno : response.pageNo,//当前页码
									total : Math.floor((response.totalRecords
											+ response.pageSize - 1)
											/ (response.pageSize)),//总页码
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
								$("#sendVoucherTableTool .Data")
										.each(
												function(i) {
													var t = $(this).attr('id');
													var aid = t + "_a";
													if ($("#" + aid).html() == '0') {
														$("#" + aid).text(
																'单据回收');
													} else if ($("#" + aid)
															.html() == '2') {
														$("#" + aid)
																.text('已回收');
														$("#" + aid)
																.removeAttr(
																		"onmousemove");
														$("#" + aid)
																.removeAttr(
																		"onmouseout");
														$("#" + aid)
																.removeAttr(
																		"onclick");
														$("#" + aid).attr(
																"class",
																"has_recover");
													}
												});
							}
							trClick();
							$('.Data input[type=checkbox]')
									.mousedown(
											function(event) {
												var bool = $(this).attr(
														'checked') ? false
														: true;
												$(this).attr('checked', bool);
												event.stopPropagation();//禁止冒泡
											});
							parent.layer.close(loadIndex);
						}
					});
		}
		$(document).ready(function() {
			//初始化iframe高度
			window.getHtmlLoadingBeforeHeight();
			window.getSendVoucherTable(1);//默认进来的时候加载页面，page=1

			$('#querytime').on('apply.daterangepicker', function() {
				getSendVoucherTable(1);
			}).daterangepicker({
				format : 'YYYY-MM-DD',
				showCustomBtn : true,
				separator : " 到 "
			});
			$('.select-down').click(function() {
				$(this).prev().focus();
				return false;
			});
			//回车搜索事件
			$('.for_key').keypress(function(e) {
				if (e.keyCode == 13) {
					$(this).parent().next().click();
				}
			});
		});
		//查询
		function queryFilter() {
			getSendVoucherTable(1);
		}
		//行点击事件选中复选框
		function trClick() {
			$('.Data').click(
					function() {
						var bool = $(this).find('input[type=checkbox]').attr(
								'checked') ? false : true;
						$(this).find('input[type=checkbox]').attr('checked',
								bool);
					});
		}
	</script>
</head>

<body>
	<div id="nav">
		<p class="p_title">平台首页&nbsp;&gt;&nbsp;送货凭证</p>
		<form action="#" method="post" class="conditionForm">
			<div
				style="position: relative; width: 307px; height: 26px; display: inline-block; float: left; padding-top: 18px; margin-right: 10px;">
				<span>开单时间:<input type="text" class="querytime"
					id="querytime" name="fcreatetime" readonly /> <i
					class="select-down"
					style="position: absolute; border-width: 6px; border-color: rgb(51, 51, 51) transparent transparent;"></i>
				</span>
			</div>
			<div class="allList">
				<span class="keyWords">关键字:</span><input type="text" class="for_key"
					name="queryfilter" placeholder="可输入客户、编号、规格、名称、电话查询" />
				<!--             	<div class="selector">全部</div> -->
				<!--                 <ul class="selectlist"> -->
				<!--                 	<li style="position:relative;top:-30px;*+position:static;">全部</li> -->
				<!--                     <li>客户</li> -->
				<!--                     <li>编号</li> -->
				<!--                     <li>规格</li> -->
				<!--                     <li>名称</li> -->
				<!--                     <li>电话</li> -->
				<!--                 </ul> -->
			</div>
			<input type="button" value="搜索" class="_submit"
				onclick="queryFilter()" />
		</form>
		<div id="forData">
			<!--         	<table cellpadding="0" cellspacing="0" border="0" width="1045" id="sendVoucherTableTool"> -->
			<table cellpadding="0" cellspacing="0" border="0"
				id="sendVoucherTableTool">
				<tr height="45" class="menuBtn">
					<td colspan="7"><a href="javascript:void(0)"
						onclick="addSaledeliver()">新建</a> <a href="javascript:void(0)"
						onclick="copySaledeliver()">复制</a> <a href="javascript:void(0)"
						onclick="execftu()">导出Excel</a> <a href="javascript:void(0)"
						onclick="printTemplate()">打印模板</a> <a href="javascript:void(0)"
						onclick="print()">打印</a> <a href="javascript:void(0)"
						onclick="statement()">对账单</a></td>
				</tr>
				<tr
					style="height: 35px; background-color: #F0F0F0; font-size: 12px;">
					<td width="70"><input type="checkbox" id="product" /></td>
					<td width="140" class="tdTitle">出库单编号</td>
					<td width="310">名称/规格</td>
					<td width="120" class="tdTitle">数量</td>
					<td width="120">单价</td>
					<!--                     <td width="144" class="tdTitle">开单时间</td> -->
					<td width="120" class="tdTitle">金额</td>
					<td width="196">操作</td>
				</tr>
				<tr height="10px">
					<td colspan="7"></td>
				</tr>
			</table>
		</div>
		<div id="kkpager"></div>
	</div>
	<div class="template"></div>
	<form action="#" method="post" id="hideftuform" target="_blank"
		style="display: none"></form>
	<script type="text/javascript">
		function _text(dom) {
			return $.trim($(dom).text());
		}
		function isEmpty(text) {
			if (typeof text === 'object') {
				return $.isEmptyObject(text);
			} else {
				text = String(text);
				return text == null || text === '';
			}
		}
		function querySelector(dom, s) {
			if ($.browser.msie) {
				return $(dom).find(s)[0];
			} else {
				return dom.querySelector(s);
			}
		}

		function querySelectorAll(dom, s) {
			if ($.browser.msie) {
				return $(dom).find(s);
			} else {
				return dom.querySelectorAll(s);
			}
		}

		//针对360浏览器
		$(".for_key").focus(function(e) {
			$(this).css("border-color", "lightgray");
			$(this).css("outline", "none");
		});
		//模拟下拉框
		$(".selector").mouseover(function(e) {
			$(this).css("border-bottom", "none");
			$(".selectlist").show();
			$(this).css("background-image", "url(${ctx}/css/images/sgk.png)");
		});
		$(".selector").mouseout(function(e) {
			$(".selectlist").hide();
			$(this).css("border-bottom-width", "1px");
			$(this).css("border-bottom-style", "solid");
			$(this).css("border-bottom-color", "lightgray");
			$(this).css("background-image", "url(${ctx}/css/images/xlk.png)");
		});
		$(".selectlist").mouseover(
				function(e) {
					$(this).show();
					$(".selector").css("background-image",
							"url(${ctx}/css/images/sgk.png)");
				});
		$(".selectlist").mouseout(
				function(e) {
					$(this).hide();
					$(".selector").css("background-image",
							"url(${ctx}/css/images/xlk.png)");
				});
		$(".selectlist li").mouseover(function(e) {
			$(this).addClass("changeall");
			$(this).siblings().removeClass("changeall");
		});
		$(".selectlist li").click(
				function(e) {
					$(".selector").text($(this).text());
					$(".selectlist").hide();
					$(".selector").css("background-image",
							"url(${ctx}/css/images/xlk.png)");
				});
		//全选
		var $num;
		$("#product").click(function() {
			if ($(this).is(":checked")) {
				$("input[name='product']").attr("checked", true);
				$num = $("input[name='product']").length;
			} else {
				$("input[name='product']").attr("checked", false);
			}
		});

		$("input[name='product']").click(
				function() {
					if ($("input[name='product']").size() == $(
							"input[name='product']:checked").size()) {
						$("#product").attr("checked", true);
						return;
					}
					$("input[name='product']").each(function(index, e) {
						if ($(this).is(":checked") == false) {
							$("#product").attr("checked", false);
							return;
						}
					});
				});
		//回收or已回收的点击切换
		function menuBtnLink(t) {
			$(t).addClass("choice");
			$(t).siblings().removeClass("choice");
			onclickImg();
		}
		//表格数据操作按钮修改和单据回收
		function for_delAndfor_recover(t) {
			$(t).addClass("choice");
		}
		function for_delAndfor_recover_forleave(t) {
			$(t).removeClass("choice");
		}
		//修改
		function dju_edit(obj) {
			parent.layer.closeAll();
			var index = parent.layer
					.open({
						type : 2,
						title : [
								'送货凭证修改界面',
								'background-color:red;border-top-left-radius: 10px;border-top-right-radius: 10px;font:18px 微软雅黑;color:#fff' ],
						area : [ '810px', '425px' ], //宽高 2016年3月23日16:17:36 HT
						content : '${ctx}/saledeliver/saledeliveredit.net?fstate=edit&fid='
								+ obj
					});
			parent.layer.style(index, {
				'border-radius' : '10px'
			});
		}
		//单据回收
		function dju_collection(obj) {
			$.ajax({
				type : 'post',
				url : '${ctx}/saledeliver/updateFTUstate.net',
				data : {
					'fid' : obj
				},
				dataType : 'json',
				success : function(response) {
					var obj = response;
					if (obj.success == true) {
						getSendVoucherTable(1);
						parent.layer.msg(obj.msg);
					} else {
						parent.layer.msg(obj.msg);
					}
				}
			})
		}

		function printPreview() {
			onclickImg();
			//alert(getCommonIds());
		}
		//新增送货凭证
		function addSaledeliver() {
			// 	parent.window.fstate='add';
			parent.layer.closeAll();
			var index = parent.layer
					.open({
						type : 2,
						scrollbar : false,
						title : [
								'送货凭证新增界面',
								'background-color:red;border-top-left-radius: 10px;border-top-right-radius: 10px;font:18px 微软雅黑;color:#fff' ],
						//area: ['810px', '425px'], //宽高  旧的
						area : [ '810px', '430px' ], //2016年3月23日15:16:48 HT
						content : '${ctx}/saledeliver/saledeliveredit.net?fstate=add'
					});
			parent.layer.style(index, {
				'border-radius' : '10px'
			});
		}
		//查看送货凭证
		function seeSaledeliver(fid) {
			// 	parent.window.fstate='view';
			parent.layer.closeAll();
			var index = parent.layer
					.open({
						type : 2,
						title : [
								'送货凭证查看界面',
								'background-color:red;border-top-left-radius: 10px;border-top-right-radius: 10px;font:18px 微软雅黑;color:#fff' ],
						area : [ '810px', '420px' ], //宽高
						content : "${ctx}/saledeliver/saledeliveredit.net?fstate=view&fid="
								+ fid
					});
			parent.layer.style(index, {
				'border-radius' : '10px'
			});
		}
		//打印模板设置
		function printTemplate() {
			parent.layer.closeAll();
			function req(param) {
				area = '';
				if (param == 2) {
					//area = ['800px','591px'];
					area = [ '800px', '520px' ];/*2016年3月24日16:39:46 ht*/
				} else {
					//area = ['800px', '430px'];

					/*2016年3月23日13:06:36 HT*/
					area = [ '800px', '410px' ];
					/*2016年3月23日13:06:36 HT*/
				}
				var index = parent.layer
						.open({
							type : 2,
							title : false,
							title : [
									'送货凭证模板界面',
									'background-color:red;border-top-left-radius: 10px;border-top-right-radius: 10px;font:18px 微软雅黑;color:#fff' ],
							area : area, //宽高
							content : '${ctx}/pages/saledeliver/printTemplate.jsp'
						});
				parent.layer.style(index, {
					'border-radius' : '10px'
				});
			}
			$
					.ajax({
						type : 'post',
						url : '${ctx}/saledeliver/getCfgByFkey.net',
						data : {
							'key' : 'thePrintTP'
						},
						success : function(response) {
							var obj = JSON.parse(response);
							if (obj.success == true) {
								if (obj.data == 2) {//2等分
									req(2);
								} else if (obj.data == 3) {//3等分
									req(3);
								} else {
									parent.layer
											.confirm(
													'请选择打印模板大小',
													{
														btn : [ '二等分', '三等分' ]
													//按钮
													},
													function(index) {
														$
																.ajax({
																	type : 'post',
																	url : '${ctx}/saledeliver/updateSyscfg.net',
																	data : {
																		'key' : 'thePrintTP',
																		'value' : 2
																	},
																	success : function(
																			response) {
																		var obj = JSON
																				.parse(response);
																		if (obj.success == true) {
																			parent.layer
																					.close(index);
																			req(2);
																		}
																	}
																});
													},
													function() {
														$
																.ajax({
																	type : 'post',
																	url : '${ctx}/saledeliver/updateSyscfg.net',
																	data : {
																		'key' : 'thePrintTP',
																		'value' : 3
																	},
																	success : function(
																			response) {
																		var obj = JSON
																				.parse(response);
																		if (obj.success == true) {
																			req(3);
																		}
																	}
																});
													});

								}
							}
						}
					});
		}

		//批量打印
		function doprint() {
			var html = querySelector(document.body, '#printTemplate').parentNode;
			$
					.each(
							querySelectorAll(html,
									'.resizeLeftDivClass,.resizeRightDivClass,td[noprint=true]'),
							function(i, c) {
								c.style.display = 'none';
							});
			var i = 0;
			$
					.each(
							querySelector(html, 'table table').rows,
							function(y, row) {
								$.each(row.cells, function(ii, cell) {
									if (cell.style.display == 'none') {
										i++;
									}
								});
								if (querySelector(row, 'table')) {//打印时隐藏不需要打印的列，里面table要改变colspan
									if (i >= querySelector(html, 'table table').rows.length - 1) {
										querySelector(row, 'td')
												.setAttribute(
														'colspan',
														querySelector(row, 'td')
																.getAttribute(
																		'colspan')
																- eval((i / (querySelector(
																		html,
																		'table table').rows.length - 1))));
									}
								}
							});
			// 	parent.$('#panelPrint')[0].contentWindow.document.body.innerHTML ="<style>@media print {@page {margin: 2mm 10mm 0mm 10mm;}} table{table-layout:fixed;}</style>" + html.innerHTML;//this.up('window').body.dom.innerHTML);
			// 	parent.$('#panelPrint')[0].contentWindow.focus();
			// 	parent.$('#panelPrint')[0].contentWindow.print();
			PageSetup_Null();//修改打印边距，主要是IE
			$('#printTemplate')
					.append(
							"<style>@media print{@page{margin: 4mm 10mm 0mm 10mm;} nav,aside{display:none;} *{margin:0;padding:0;}} #printTemplate table table td{ border:1px solid #000000;} #printTemplate table table table td{border:0px;}</style>")
			// 	setTimeout("$('.template').jqprint();",500);
			setTimeout("$('.template').jqprint();$('#printTemplate').hide();",
					500);
			$
					.each(
							querySelectorAll(html,
									'.resizeLeftDivClass,.resizeRightDivClass,td[noprint=true]'),
							function(y, c) {
								c.style.display = '';
							});
			$
					.each(
							querySelector(html, 'table table').rows,
							function(y, row) {
								$.each(row.cells, function(y, cell) {
									if (cell.style.display == 'none') {
										i++;
									}
								});
								if (querySelector(row, 'table')) {//还原回来
									if (i >= querySelector(html, 'table table').rows.length - 1) {
										querySelector(row, 'td')
												.setAttribute(
														'colspan',
														eval(querySelector(row,
																'td')
																.getAttribute(
																		'colspan'))
																+ eval((i / (querySelector(
																		html,
																		'table table').rows.length - 1))));
									}
								}
							});
		}
		//注册表修改打印边距开始
		//获得IE浏览器版本
		function checkIEV() {
			var X, V, N;
			V = navigator.appVersion;
			N = navigator.appName;
			if (N == "Microsoft Internet Explorer")
				X = parseFloat(V.substring(V.indexOf("MSIE") + 5, V
						.lastIndexOf("Windows")));
			else
				X = parseFloat(V);
			return X;
		}

		//设置网页打印的页眉页脚和页边距
		function PageSetup_Null() {
			var HKEY_Root, HKEY_Path, HKEY_Key;
			HKEY_Root = "HKEY_CURRENT_USER";
			HKEY_Path = "\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
			try {
				var Wsh = new ActiveXObject("WScript.Shell");
				HKEY_Key = "header";
				//设置页眉（为空）
				//Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key)可获得原页面设置   
				Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
				HKEY_Key = "footer";
				//设置页脚（为空）   
				Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");

				//这里需要浏览器版本，8.0以下的页边距设置与8.0及以上不一样，注意注册表里的单位是英寸，打印设置中是毫米，1英寸=25.4毫米
				if (checkIEV() < 8.0) {
					HKEY_Key = "margin_left";
					//设置左页边距
					Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0");
					HKEY_Key = "margin_right";
					//设置右页边距
					Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0");
					HKEY_Key = "margin_top";
					//设置上页边距
					Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0");
					HKEY_Key = "margin_bottom";
					//设置下页边距   
					Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0");
				} else {
					HKEY_Key = "margin_left";
					//设置左页边距
					Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.2");
					HKEY_Key = "margin_right";
					//设置右页边距
					Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0");
					HKEY_Key = "margin_top";
					//设置上页边距
					Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.145");
					HKEY_Key = "margin_bottom";
					//设置下页边距   
					Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0");
				}
			} catch (e) {

			}
		}
		//注册表修改结束       
		//送货凭证批量打印
		function print(fid) {
			var size = 6;
			parent.layer.closeAll();
			var fids = [];
			$.each($('input:checked'), function(i, o) {
				fids.push($(o).next().val());
			});
			fids = fid || fids.join(',');
			if (isEmpty(fids)) {/*兼容IE*/
				parent.layer.msg('请选择数据！');
				return false;
			}
			$.ajax({
				type : 'post',
				url : '${ctx}/saledeliver/getCfgByFkey.net',
				async : 'false',
				data : {
					'key' : 'thePrintTP'
				},
				dataType : 'json',
				success : function(response) {
					var obj = response;
					if (obj.success == true) {
						if (obj.data == '2') {//2等分
							req(2);
						} else if (obj.data == '3') {//3等分
							req(3);
						} else {
							parent.layer.msg('请先设置模板！');
						}
					}
				}
			});
			function req(param) {
				$
						.ajax({
							type : 'post',
							async : 'false',
							url : '${ctx}/saledeliver/getPrintFTUList.net',
							//data:{fids:fids,size:$(div).find('table table[contenteditable=false]')[0].rows.length-2},//-2为去掉列标题和去掉尾部
							data : {
								fids : fids,
								size : size
							},
							dataType : 'json',
							success : function(responses, opts) {//3联的
								var obj = responses;
								if (obj.success == true) {
									try {
										$
												.each(
														obj.data,
														function(i, d) {
															$
																	.each(
																			d.product,
																			function(
																					y,
																					product) {
																				product.famount = !eval(product.famount) ? ''
																						: eval(product.famount);
																				product.fprice = !eval(product.fprice) ? ''
																						: eval(product.fprice);
																				product.fdanjia = !eval(product.fdanjia) ? ''
																						: eval(product.fdanjia);
																			});
															d.sum = eval(d.sum) == 0 ? ''
																	: eval(d.sum);
															//									d.fsumprices = eval(d.fsumprices)==0?'':eval(d.fsumprices);
														});
									} catch (ee) {

									}
									var data = obj.data;
									$
											.ajax({
												url : '${ctx}/saledeliver/getFtuPrintTemplate.net',
												async : 'false',
												data : {
													key : param
												},
												dataType : 'html',
												success : function(response) {
													$('.template').empty();
													$('.template').append(
															response);
													//window.getHtmlLoadingAfterHeight();
													//界面数据渲染    执行打印 
													onload(data);
													doprint();
												}
											});
								}
							}
						});
			}
		}

		function onload(data) {
			var me = this;
			// 	var data = parent.window.data;
			var dom, temp;
			var printTemplate = querySelector(document.body, '#printTemplate');
			for (var i = 0; i < data.length; i++) {
				if (i == 0) {
					continue;
				}
				dom = $.clone(printTemplate);
				dom.setAttribute('id', dom.getAttribute('id') + i);
				printTemplate.parentNode.appendChild(dom);
			}
			for (i = 0; i < data.length; i++) {
				var div = querySelector(document.body, '#printTemplate'
						.concat(i == 0 ? '' : i));
				querySelector(div, 'div').innerHTML = data[i].fsuppliername;
				$
						.each(
								querySelector(div, 'table table').rows,
								function(index, row) {//表格内容
									if (index > 0) {
										$
												.each(
														row.cells,
														function(indexs, cell) {
															if (data[i].product[index - 1]) {
																if (!querySelector(
																		cell,
																		'table')) {
																	cell.innerHTML = data[i].product[index - 1][querySelector(
																			div,
																			'table table').rows[0].cells[indexs]
																			.getAttribute('name')];
																}
															}
															if (querySelector(
																	cell,
																	'table')) {
																$
																		.each(
																				querySelector(
																						cell,
																						'table').rows,
																				function(
																						y,
																						row) {
																					$
																							.each(
																									row.cells,
																									function(
																											ii,
																											cell) {
																										if (cell
																												.getAttribute('name')) {
																											temp = _text(cell);
																											cell.innerHTML = temp
																													.replace(
																															temp,
																															temp
																																	+ data[i][cell
																																			.getAttribute('name')]);
																										}
																									});
																				});
															}
														});
									}
								});
				$.each(querySelector(div, 'table').rows, function(index, row) {//表格标题
					$.each(row.cells, function(indexs, cell) {
						if (!querySelector(cell, 'table')) {
							if (cell.getAttribute('name')) {
								cell.innerHTML = isEmpty(data[i][cell
										.getAttribute('name')]) ? ''
										: data[i][cell.getAttribute('name')];
							}
						}
					});
				});
			}
		}
		//复制功能
		function copySaledeliver() {
			parent.layer.closeAll();
			var fids = [];
			$.each($('input:checked'), function(i, o) {
				fids.push($(o).next().val());
			});
			if (fids.length != 1) {
				parent.layer.msg('请选择1条数据！');
				return false;
			}
			var index = parent.layer
					.open({
						type : 2,
						title : [
								'送货凭证编辑界面',
								'background-color:red;border-top-left-radius: 10px;border-top-right-radius: 10px;font:18px 微软雅黑;color:#fff' ],
						area : [ '810px', '425px' ], //宽高
						content : "${ctx}/saledeliver/saledeliveredit.net?fstate=copy&fid="
								+ fids[0]
					});
			parent.layer.style(index, {
				'border-radius' : '10px'
			});
		}
		//删除送货凭证
		function delSaledeliver(fid) {
			parent.layer.confirm('是否删除？', {
				btn : [ '是', '否' ]
			//按钮
			}, function(index) {
				$.ajax({
					type : 'post',
					url : '${ctx}/saledeliver/delFTUsaledeliver.net',
					data : {
						fidcls : fid
					},
					dataType : "json",
					success : function(response) {
						var obj = response;
						if (obj.success) {
							parent.layer.msg(obj.msg);
							getSendVoucherTable(1);
						} else {
							parent.layer.msg(obj.msg);
						}
					}
				});
				parent.layer.close(index);
			}, function(index) {

			});

		}
		//对账单
		function statement() {
			var i = parent.layer
					.open({
						title : [ '对账单',
								'background-color: #d80c18;color: #fff;height:44px;*height:42px;' ],
						type : 2,
						anim : true,
						offset : Math.max(window.screen.height - 442, 550) / 2
								+ 'px',
						area : [ '850px', '438px' ],
						content : window.getRootPath()
								+ "/saledeliver/getRptInfo.net"
					});

		}

		function execftu() {

			$("#hideftuform").empty();
			$("#hideftuform").attr("action",
					"${ctx}/saledeliver/execFtulist.net");
			var html = [].join("");
			var data = $(".conditionForm").serializeArray();
			$
					.each(
							data,
							function(i, field) {
								html += [ '<input type="hidden" name="',field.name,'" value="',field.value,'"/>' ]
										.join("");
							});
			$(html).appendTo("#hideftuform");
			//打开窗体，并post提交页面  
			$("#hideftuform").submit();
		}

		// $(function(){
		// 	var p = [];
		// 	p.push({'fname':'产品名称','fieldtype':0,'fsaledeliverentry':'fcusproductname'},{'fname':'规格','fieldtype':0,'fsaledeliverentry':'fspec'},{'fname':'单位','fieldtype':0,'fsaledeliverentry':'funit'},{'fname':'数量','fieldtype':1,'fdecimals':1,'fsaledeliverentry':'famount'},{'fname':'单价','fieldtype':1,'fdecimals':3,'fsaledeliverentry':'fdanjia'},{'fname':'金额','fieldtype':1,'fdecimals':2,'fcomputationalformula':'数量*单价','fsaledeliverentry':'fprice'},{'fname':'备注','fieldtype':0,'fsaledeliverentry':'fdescription'});
		// 	$.ajax({
		// 		type:'post',
		// 		url:"${ctx}/saledeliver/saveAllFtuParams.net",
		// 		data:{ftu:JSON.stringify(p)},
		// 		success:function(response){
		// 			var obj = JSON.parse(response);
		// 			if(obj.success==true){

		// 			}
		// 		}
		// 	});
		// });
	</script>
</body>
</html>
