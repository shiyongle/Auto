<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ include file="/pages/all_head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>服务宝</title>

	<script type="text/javascript" src="${ctx}/js/jquery-1.8.3.min.js"></script>

	<link rel="stylesheet" type="text/css" href="${ctx}/css/fuwu_index.css" />
	
</head>

<body>
	<!--banner-->
	<div class="lk_fuwu_banner">
		<ul>
			<li><img src="${ctx}/css/images/fuwu_title.gif"/></li>
			<li style="float: right;margin-top:10px;"><img src="${ctx}/css/images/fuwu_D.png"/></li>
		</ul>
	</div>
	<!--服务宝内容-->
	<div id="lk_fuwu_con">
		<!--服务宝标题-->
		<div class="lk_fuwu_title">
			<ul>
				<li style="color:#fff;" class="fuwu_bgcolor">保证完成</li>
				<li>保证原创</li>
				<li>保证维护咨询</li>
				<li>包装提供原图</li>
			</ul>
		</div>
		<!--保证完成-->
		<div class="lk_yuanchuang_content">
			<!--保证原创内容-->
			<div class="lk_fuwu_yuanchuang">
				<ul>
					<li class="lk_yuanchuang_pic"><img src="${ctx}/css/images/fuwu_wan.png"/></li>
					<li class="lk_yuanchuang_text">
						<h1>保证完成</h1>
						<p>服务商签署《东经网保证完成服务协议》，承诺保证完成，保证质量并修改到满意为止。雇主和服务商就需求完成时间、工作内容及具体要求等内容签订协议。如部分或者全额付款后出现问题，雇主有权在指定期限内发起维权，若维权成功，雇主可获得双倍赔付（东经网将扣除服务商的保证金先行赔付给雇主，同时要求服务商退还交易款项给雇主）。</p>
					</li>
				</ul>
			</div>
			<!--保障范围说明-->
			<div class="lk_shuoming_text" style="display:none">
				<h1><span></span>保障范围说明</h1>
				<p>1、适用于加入了“保证完成”服务项目的所有服务商。</p>
				<p style="margin-top:10px;">2、适用于雇主在东经网发布的设计需求。</p>
				<p style="margin-top:10px;">3、适用于所有类目。</p>
			</div>
			<!--保障期限-->
			<div class="lk_shuoming_text">
				<h1><span></span>保障期限</h1>
				<p>协议履行完成，部分或者全额付款给服务商后，10天内。</p>
			</div>
			<!--雇主可享受的保障权益-->
			<div class="lk_shuoming_text">
				<h1><span></span>雇主可享受的保障权益</h1>
				<p style="width:1040px;line-height: 30px;">雇主与加入“保证完成”的服务商订立合作关系后，服务商提供以下服务：保证完成；保证质量并修改到满意为止。若服务商未在约定时间完成需求或拒绝根据协议要求为雇主修改作品，双方协商未果时，雇主有权在保障期限内发起维权。若判定成立，雇主可获得双倍赔付（东经网将扣除服务商的保证金先行赔付给雇主，同时要求服务商退还交易款项给雇主）。</p>
			</div>
			<!--雇主发起维权的条件-->
			<div class="lk_shuoming_text">
				<h1><span></span>雇主发起维权的条件</h1>
				<p>部分或者全额付款给服务商后10日内，若出现以下情况，雇主有权发起维权。</p>
				<p style="margin-top:10px;">1、服务商无能力完成工作或因服务商其他原因导致未在约定时间完成雇主需求时。</p>
				<p style="margin-top:10px;">2、服务商拒绝根据双方协议要求为雇主修改作品，且雇主和服务商协商未果时。</p>
				<p style="margin-top:10px;">3、服务商提供作品未达到双方协议要求，且雇主和服务商协商未果时。</p>
			</div>
			<!--流程图-->
			<div class="lk_flow_pic">
				<img src="${ctx}/css/images/fuwu_flow.gif"/>
			</div>
		</div>

		<!--保证原创-->
		<div class="lk_yuanchuang_content" style="display:none">
			<!--保证完成内容-->
			<div class="lk_fuwu_yuanchuang">
				<ul>
					<li class="lk_yuanchuang_pic"><img src="${ctx}/css/images/fuwu_yuan.png"/></li>
					<li class="lk_yuanchuang_text">
						<h1>保证原创</h1>
						<p>服务商签署《东经网保证原创服务协议》，承诺向雇主提供原创或全新作品。全额付款给服务商后一年内，如雇主发现服务商提供作品为非原创（包括复制、剽窃、模仿、抄袭等），且与服务商协商未果的情况下，雇主有权发起赔付申请，东经网判定成立后，雇主可获得双倍赔付（东经网将扣除服务商的保证金先行赔付给雇主，同时要求服务商退还交易款项给雇主）。</p>
					</li>
				</ul>
			</div>
			<!--保障范围说明-->
			<div class="lk_shuoming_text">
				<h1><span></span>保障范围说明</h1>
				<p>1、适用于雇主在东经网发布的设计需求。</p>
				<p style="margin-top:10px;">2、适用于加入了“保证原创”服务项目的所有服务商。</p>
			</div>
			<!--保障期限-->
			<div class="lk_shuoming_text">
				<h1><span></span>保障期限</h1>
				<p>全额付款给服务商后一年内。</p>
			</div>
			<!--雇主可享受的保障权益-->
			<div class="lk_shuoming_text">
				<h1><span></span>雇主可享受的保障权益</h1>
				<p style="width:1040px;line-height: 30px;">雇主与加入“保证原创”的服务商订立合作关系后，服务商保证其提供作品为原创。在交易完成一年内，如雇主发现服务商提供作品为非原创（包括复制、剽窃、模仿、抄袭等），且双方协商未果情况下，雇主有权在保障期限内发起维权并申请赔付。若判定成立，雇主可获得双倍赔付（东经网将扣除服务商的保证金先行赔付给雇主，同时要求服务商退还交易款项给雇主）。</p>
			</div>
			<!--雇主发起维权的条件-->
			<div class="lk_shuoming_text">
				<h1><span></span>雇主发起维权的条件</h1>
				<p>全额付款给服务商后的一年内，如交易过程中出现以下情况时，雇主有权发起维权。</p>
				<p style="margin-top:10px;">1、服务商加入“保证原创”，且双方确定合作关系。</p>
				<p style="margin-top:10px;">2、如雇主发现服务商提供作品为非原创（包括复制、剽窃、模仿、抄袭等），并能提供确凿证据。</p>
			</div>
			<!--流程图-->
			<div class="lk_flow_pic">
				<img src="${ctx}/css/images/fuwu_flow.gif"/>
			</div>
		</div>

		<!--保证维护咨询-->
		<div class="lk_yuanchuang_content" style="display:none">
			<!--保证维护咨询内容-->
			<div class="lk_fuwu_yuanchuang">
				<ul>
					<li class="lk_yuanchuang_pic"><img src="${ctx}/css/images/fuwu_wei.png"/></li>
					<li class="lk_yuanchuang_text">
						<h1>保证维护咨询</h1>
						<p>服务商签署《东经网保证维护服务协议》，承诺为雇主提供维护服务。在全额付款给服务商后三个月内，如服务商拒绝根据协议内容为雇主提供维护咨询服务，且与服务商协商未果的情况下，雇主有权发起赔付申请，东经网在核实后将实行双倍赔付（东经网将扣除服务商的保证金先行赔付给雇主，同时要求服务商退还交易款项给雇主）以保障雇主权益。</p>
					</li>
				</ul>
			</div>
			<!--保障范围说明-->
			<div class="lk_shuoming_text">
				<h1><span></span>保障范围说明</h1>
				<p>1、适用于雇主在东经网发布的设计需求。</p>
				<p style="margin-top:10px;">3、适用于加入了“保证维护”服务项目的所有服务商。</p>
			</div>
			<!--保障期限-->
			<div class="lk_shuoming_text">
				<h1><span></span>保障期限</h1>
				<p>全额付款给服务商后的90天内</p>
			</div>
			<!--雇主可享受的保障权益-->
			<div class="lk_shuoming_text">
				<h1><span></span>雇主可享受的保障权益</h1>
				<p style="width:1040px;line-height: 30px;">全额付款给服务商后90天内，雇主对服务商提交作品有维护咨询需求，且符合双方交易协议内容，若服务商拒绝为雇主提供维护，双方在协商未果情况下，雇主有权在保障期限内发起维权并申请赔付。若判定成立，东经网将对雇主实行双倍赔付（东经网将扣除服务商的保证金先行赔付给雇主，同时要求服务商退还交易款项给雇主）以保障雇主权益。</p>
			</div>
			<!--雇主发起维权的条件-->
			<div class="lk_shuoming_text">
				<h1><span></span>雇主发起维权的条件</h1>
				<p>全额付款给服务商后90天内，如交易过程中出现以下情况时，雇主有权发起维权。</p>
				<p style="margin-top:10px;">1、服务商加入“保证维护”，且双方确定合作关系。</p>
				<p style="margin-top:10px;">2、雇主对服务商提交作品有维护咨询需求，且符合双方交易协议内容。</p>
			</div>
			<!--流程图-->
			<div class="lk_flow_pic">
				<img src="${ctx}/css/images/fuwu_flow.gif"/>
			</div>
		</div>

		<!--保证提供原图-->
		<div class="lk_yuanchuang_content" style="display:none">
			<!--保证提供原图内容-->
			<div class="lk_fuwu_yuanchuang">
				<ul>
					<li class="lk_yuanchuang_pic"><img src="${ctx}/css/images/fuwu_yuan.png"/></li>
					<li class="lk_yuanchuang_text">
						<h1>保证提供原图</h1>
						<p>服务商签署《东经网保证提供原图服务协议》，承诺向雇主提供设计原图。交易完成后5天内，如服务商拒绝按协议履行，且雇主与服务商协商未果的情况下，雇主有权发起赔付申请，东经网判定成立后，雇主可得双倍赔付（东经网将扣除服务商的保证金先行赔付给雇主，同时要求服务商退还交易款项给雇主）。 </p>
					</li>
				</ul>
			</div>
			<!--保障范围说明-->
			<div class="lk_shuoming_text">
				<h1><span></span>保障范围说明</h1>
				<p>1、适用于雇主在东经网发布的设计需求。</p>
				<p style="margin-top:10px;">2、适用于加入了“保证提供原图”服务项目的所有服务商。</p>
				<p style="margin-top:10px;">3、保证提供原图服务不包含维护修改服务，维护修改服务由双方另行协商。</p>
			</div>
			<!--保障期限-->
			<div class="lk_shuoming_text">
				<h1><span></span>保障期限</h1>
				<p>交易完成后5天内</p>
			</div>
			<!--雇主可享受的保障权益-->
			<div class="lk_shuoming_text">
				<h1><span></span>雇主可享受的保障权益</h1>
				<p style="width:1040px;line-height: 30px;">雇主与加入“保证提供原图”的服务商订立合作关系后，服务商保证承诺向雇主提供设计原图。在交易过程中及5天内，如服务商拒绝按协议履行，且雇主与服务商协商未果的情况下，雇主有权发起赔付申请，东经网判定成立后，雇主可得双倍赔付（东经网将扣除服务商的保证金先行赔付给雇主，同时要求服务商退还交易款项给雇主）。</p>
			</div>
			<!--雇主发起维权的条件-->
			<div class="lk_shuoming_text">
				<h1><span></span>雇主发起维权的条件</h1>
				<p>1、服务商加入“保证提供原图”，且双方确定合作关系。</p>
				<p style="margin-top:10px;">2、服务商拒绝按约履行，并雇主能提供确凿证据证明。</p>
				<p style="margin-top:10px;">3、交易完成后5天内。</p>
			</div>
			<!--流程图-->
			<div class="lk_flow_pic">
				<img src="${ctx}/css/images/fuwu_flow.gif"/>
			</div>
		</div>
	</div>
	<div id="foot">
       	<iframe src="${ctx}/pages/all_foot.jsp" scrolling="no" width=100% height="150" frameborder="0" id="allfoot"></iframe>
	</div>
<script type="text/javascript">
	//页面切换
	$(document).ready(function(){

		

		$(".lk_fuwu_title ul li").click(function(){
			var textIndex = $(this).index();

			$(this).addClass("fuwu_bgcolor").siblings().removeClass("fuwu_bgcolor");
			$(this).css("color","#fff").siblings().css("color","#000");
			//alert(textIndex);
			$(".lk_yuanchuang_content").eq(textIndex).css("display","block").siblings(".lk_yuanchuang_content").css("display","none");
			
		})
	})
</script>
</body>
</html>
