<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>在线下单</title>
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
<link href="${ctx}/js/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<script src="${ctx}/js/jquery.selectlist.js" type="text/javascript" language="javascript"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.selectlist.css" />
<style>
*{
	margin:0px auto;
	padding:0px;
	}
input{
	outline:none;
}
#nav{
	width:1070px;
	height:auto;
	}
#container{
	width:1070px;
	margin-top:20px;
	height:650px;
	}
#container_right{
	width:1070px;
	/* margin-left:20px; */
	}
#container_right{
	float:left;
	}
/*右边form*/
#container_right .tab{
	width:820px;
	height:34px;
	list-style:none;
	margin-left:20px;
	}
.tab li{
	height:33px;
	width:80px;
	line-height:33px;
	float:left;
	text-align:center;
	font-family:"宋体";
	font-size:14px;
	cursor:pointer;
	border:1px solid #cccccc;
	border-bottom:none;
	}
/*当前鼠标选中的tab选项*/
.tab .current{
	/* border:1px solid red; */
	border-top:3px solid red;
	border-left:1px solid red;
	border-right:1px solid red;
	border-bottom:none;
	height:32px;
	color:red;
	background-color:#fff;
	}
#container_right .formlist{
	border:1px solid lightgray;
	border-top: 1px solid red;
	background-color:#fff;
	}
.formlist .public_st{
	width:705px;
	height:75px;
	padding-left:115px;
	padding-top:25px;
	}
#container_right form table{
	line-height:50px;
	/* font-size:12px; */
	/* font-family:"宋体"; */
	color:#909090;
	}
#container_right .tlt{
	font-size:24px;
	color:#333;
	}
#container_right .td1{
	text-align:center;
	width:100px;
	font-family:"宋体"; 
	}
#container_right .lst{
	width:372px;
	height:30px;
	font-size:16px;
	border:1px solid #cccccc;
	line-height:30px
	}
#container_right .plus,#container_right .reduce{
	display:block;
	float:left;
	width:32px;
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
	color:#000;
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
#container_right .date_{
	display:block;
	float:left;
	width:180px;
	height:30px;
	border:1px solid #cccccc;
	border-right:none;
	margin-top:6px;
	line-height:30px
	}
#container_right .for_date{
	display:block;
	float:left;
	height:28px;
	padding-top:2px;
	width:28px;
	border:1px solid #cccccc;
	border-left:none;
	margin-top:6px;
	line-height:28px
	}
.for_date img{
	border:none;
	}
#container_right .am{
	display:block;
	float:left;
	width:70px;
	height:30px;
	border:1px solid #ccc;
	margin-top:6px;
	line-height:30px;
	text-align:center;
	text-decoration:none;
	font-size:16px;
	color:#909090;
	margin-left:20px;
	}
#container_right .pm{
	display:block;
	float:left;
	width:70px;
	height:30px;
	border:1px solid #ccc;
	margin-top:6px;
	line-height:30px;
	text-align:center;
	text-decoration:none;
	font-size:16px;
	color:#909090;
	}
#container_right ._add{
	width:100px;
	height:28px;
	background-color:#CC0000;
	color:white;
	border:none;
	line-height:28px;
	margin-left:10px;
	font-family:"微软雅黑";
	font-size:18px;
	letter-spacing:5px;
	}
#container_right ._submit{
	cursor:pointer;
	width:180px;
	height:47px;
	font-size:16px;
	color:white;
	background-color:#CC0000;
	border:none;
	font-family:"微软雅黑";
	line-height:47px;
	}
#container_right ._btn{
	cursor:pointer;
	width:180px;
	height:47px;
	font-size:22px;
	color:white;
	background-color:#f57771;
	border:1px solid #CC0000;
	font-family:"微软雅黑";
	line-height:45px;
	margin-left:20px;
	}
	a{font-family:"宋体";font-size:12px;}
	/* a:hover{color:#e03d46;cursor:pointer;} */
	.showImage{
		margin:15px;
		float:left;
		border:1px solid #ccc;
		width:360;
		height:347px;
	}
	.details{float:left;}
	.smallImage{
		width:88px;
		height:85px;
		margin:15px 10px -5px 10px;
		background-color:#f1f1f1;
		border:1px solid #ccc;
		padding:5px;
	}
	.productdetails{
		color:#909090;
		margin-top:15px;
		font-family:"宋体"; 
		margin-left:10px;
	}
	.productContent{
		color:#545454;
	}
	#container_right ._color{
		border:1px solid #e03d46;
		background-color:#e03d46;
		color:#fff;
	}
	#container_right table input{
		/* *padding-left:10px;
		padding-left:10px\0; */
		padding-left:10px;
	}
	#container_right .downloadImg{
	cursor:pointer;
	width:120px;
	height:36px;
	font-size:16px;
	color:white;
	background-color:#E03D46;
	border:none;
	font-family:"微软雅黑";
	line-height:36px;
	margin:20px 0 15px 0;
	}
</style>
</head>

<body style="background-color:#f1f1f1;">
	<div id="nav">
       	<div id="container">
       		<div style="margin-bottom:10px;">
       			<a>平台首页</a> &gt; <a>我的业务</a> &gt; <a>在线下单</a>
       		</div>
            <div id="container_right">
            	<ul class="tab">
                	<li class="current">要货</li>
                	<c:if test="${fbacthstock==true }">
	                    <li style="border-left:none;">备货</li>
                	</c:if>
                </ul>
                <div class="formlist">
	            		<c:if test="${type!=2}">
	            		<div style="height:380px;">
	            			<div class="showImage">
	            				<img src="${picurl}" width="360px" height="347px"/>
	            			</div>
	            			<div class="details">
	            				<div class="imagelist">
		            				 <c:forEach var="entry" items="${productfile}">
											<img  src="${entry.replace('vmifile', 'smallvmifile')}" class="smallImage"/>
									</c:forEach>
	            				<!-- 	<img  src="css/images/shqk.png" class="smallImage"/>
	            					<img  src="css/images/sj.png" class="smallImage"/>
	            					<img  src="css/images/sj.png" class="smallImage"/>
	            					<img  src="css/images/sj.png" class="smallImage"/>
	            					<img  src="css/images/s.png" class="smallImage"/> -->
	            				</div>
	            				<div style="width:660px;" align="right"><input type="button" value="全部下载" class="downloadImg"  onclick="downLoad('${custproduct.fid}')" /></div>
	            				<div>
	            					<p class="productdetails"><span>名&nbsp;&nbsp;称：</span><span class="productContent">${custproduct.fname}</span></p>
	            					<p class="productdetails"><span>规&nbsp;&nbsp;格：</span><span class="productContent">${custproduct.fspec} </span></p>
	            					<p class="productdetails"><span>库&nbsp;&nbsp;存：</span><span class="productContent">${stock[0].fbalanceqty} （发货中${stock[0].amount } 只）</span></p>
	            					<p class="productdetails" style="margin-top:20px;"><span>最近下单时间：</span><span class="productContent">${custproduct.flastordertime}</span></p>
	            					<p class="productdetails"><span>最近下单数量：</span><span class="productContent">${custproduct.flastorderfamount }只</span></p>
	            				</div>
	            			</div>
	            		</div>
	            		<div style="margin:5px 15px 15px 15px;"><hr style="height:1px;border:none;border-top:1px solid #CCCCCC;"/></div>
	            		</c:if>
	            		<form id="deliverapply">
	                	<table cellspacing="0" cellpadding="0" width="1060" style="display:block;" name="deliverapply">
	                		<input type="hidden"  name="fcusproductid" value="${fcustproducts}" shopping="fcusproduct"/>
	            			<input type="hidden"  name="fsupplierid" value="${fsupplierid}" shopping="fsupplierid"/>
	            			<input type="hidden"  name="fnumber" value="${deliverapply.fnumber}" shopping="fnumber"/>
	            			<c:if test="${deliverapply!=null }">
		            			<input type="hidden"  name="fid" value="${deliverapply.fid}"/>
	            			</c:if>
	                        <tbody><tr>
	                        	<td width="110" class="td1">采购单号:</td>
	                            <td width="725"><input type="text" class="lst" id="fordernumber" name="fordernumber" shopping="fpcmordernumber" value="${deliverapply.fordernumber }"/></td>
	                        </tr>
	                        <c:if test="${type!=2}">
	                        <tr>
	                        	<td class="td1">数&nbsp;&nbsp;量:<span style="color:red;">*</span></td>
	                            <td>
	                            	<input type="button" value="－" class="reduce" style="padding-left:0px;"/>
	                            	<input type="text" class="amount" style="padding-left:0px;" name="famount" shopping="famount" value="${deliverapply.famount==null?0:deliverapply.famount }"/>
	                            	<input type="button" value="＋" class="plus" style="padding-left:0px;"/>
	                            </td>
	                        </tr>
	                        </c:if>
	                        <tr>
	                        	<td class="td1">交&nbsp;&nbsp;期:<span style="color:red;">*</span></td>
	                            <td>
	                            	<input type="text" class="date_" id="jdate" name="farrivetime"  shopping="farrivetime" readonly="readonly" onfocus="var mindate=getMinDate();WdatePicker({dateFmt:'yyyy-MM-dd',minDate:mindate,onpicked:function(){$(this).removeClass('WdateFmtErr');}})"/>
	                                <a class="for_date"><img onclick="var mindate=getMinDate();WdatePicker({el:$dp.$('jdate'),minDate:mindate,onpicked:function(){$(this).removeClass('WdateFmtErr');}})" src="${ctx}/css/images/sj.png"/></a>
	                                <a href="javascript:void(0);" id="am" class="am" name="apm">上午</a>
	                                <a href="javascript:void(0);" id="pm" class="pm" name="apm">下午</a>
	                            </td>
	                        </tr>
	                        <tr>
	                        	<td class="td1">地&nbsp;&nbsp;址:<span style="color:red;">*</span></td>
	                            <td>
	                            	<!-- <input type="text" class="lst" style="width:671px" id="fordernumber" name="deliverapply.fordernumber"/> -->
	                            	<select id="faddressid" name="faddressid"  class="lst" style="width:580px;height:32px;border-top:1px solid #CCCCCC;">
									     <c:choose>
						                  <c:when test="${address==''}">
						                 	 <option value='0' >--请新建地址--</option>
						                  </c:when>
							              <c:otherwise>
							              	<c:forEach var="entry" items="${address}">
											 <option value="${entry.fid }" > ${entry.fdetailaddress }</option>
											</c:forEach>
										  </c:otherwise>
									  </c:choose>							
									</select>
									<input type="button" value="新增地址" onclick="add_address()" style="width:85px;height:32px;background-color:#e03d46;color:#fff;font-size:16px;font-family:微软雅黑;border:none;cursor:pointer;padding:0px;"/>
	                            </td>
	                        </tr>
	                        <tr style="float:top;">
	                        	<td class="td1">备&nbsp;&nbsp;注:</td>
	                            <td>
	                            	<textarea style="width:671px;height:94px;resize: none;border:1px solid #cccccc;" name="fdescription" shopping="fdescription">${deliverapply.fdescription }</textarea>
	                            </td>
	                        </tr>
                    </tbody></table>
                     </form>
                     <form id="mystock">
                   <table cellspacing="0" cellpadding="0" width="1200" style="display:none;" name="mystock">
                    <input type="hidden"  name="mystock.fsupplierid" value="${fsupplierid}" shopping="fsupplierid"/>
                     <input type="hidden"  name="mystock.fcustproductid" value="${fcustproducts}" shopping="fcusproduct"/>
                     <input type="hidden"  name="mystock.fid" value="${mystock.fid}"/>
	                        <tbody><tr>
	                        	<td width="110" class="td1">采购单号:</td>
	                            <td width="725"><input type="text" class="lst" name="mystock.fpcmordernumber" shopping="fpcmordernumber" value="${mystock.fpcmordernumber }"/></td>
	                        </tr>
	                        <c:if test="${type!=2}">
	                        <tr>
	                        	<td class="td1">计划数量:<span style="color:red;">*</span></td>
	                            <td>
	                            	<input type="button" value="－" class="reduce" style="padding-left:0px;"/>
	                            	<input type="text" value="${mystock.fplanamount==null?0:mystock.fplanamount}" class="amount" name="mystock.fplanamount" style="padding-left:0px;" shopping="famount"/>
	                            	<input type="button" value="＋" class="plus" style="padding-left:0px;"/>
	                            </td>
	                        </tr>
	                        </c:if>
	                        <tr>
	                        	<td class="td1">首次发货:<span style="color:red;">*</span></td>
	                            <td>
	                            	<input type="text" class="date_" id="firstdate" name="mystock.ffinishtime" shopping="farrivetime" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:''})" value="${mystock.ffinishtime }"/>
	                                <a class="for_date"><img onclick="WdatePicker({el:$dp.$('firstdate'),minDate:'${nowDate} 00:00:00'})" src="${ctx}/css/images/sj.png"/></a>
	                                <a href="javascript:void(0);" class="am" name="shortday" setday="2">2日内</a>
	                                <a href="javascript:void(0);" class="pm" style="margin-left:10px;" name="shortday" setday="5"/>5日内</a>
	                                <a href="javascript:void(0);" class="pm" style="margin-left:10px;" name="shortday" setday="7"/>7日内</a>
	                            </td>
	                        </tr>
	                        <tr>
	                        	<td class="td1">末次发货:<span style="color:red;">*</span></td>
	                            <td>
	                            	<input type="text" class="date_" id="lastdate" name="mystock.fconsumetime" shopping="freqdate"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:''})" value="${mystock.fconsumetime }"/>
	                                <a class="for_date"><img onclick="WdatePicker({el:$dp.$('lastdate'),minDate:'${nowDate} 00:00:00'})" src="${ctx}/css/images/sj.png"/></a>
	                                  <a href="javascript:void(0);" class="am" name="longday" setday="7">一周内</a>
	                                <a href="javascript:void(0);" class="pm" style="margin-left:10px;" name="longday" setday="15">半月内</a>
	                                <a href="javascript:void(0);" class="pm" style="margin-left:10px;" name="longday" setday="30">一月内</a>
	                            </td>
	                        </tr>
	                        <tr style="float:top;">
	                        	<td class="td1" align="">备&nbsp;&nbsp;注:</td>
	                            <td>
	                            	<textarea style="width:671px;height:94px;resize: none;border:1px solid #cccccc;" name="mystock.fdescription" shopping="fdescription">${mystock.fremark }</textarea>
	                            </td>
	                        </tr>
                    </tbody></table>
                     </form>
	                    <p class="public_st">
	                    	<c:if test="${type==0}">
	                    	<input type="button" value="下单" class="_submit" id="saveButton"/>
	                    	</c:if>
	                    	<c:if test="${type!=0}">
	                    	<input type="button" value="加入购物车" class="_submit" id="saveShopping"/>
	                    	</c:if>
	                    	<input type="button" value="返回" class="_submit" style="margin-left:60px;" onclick="lastStep();"/>
	                    </p>
	               
                </div>
            </div>
          </div>
    </div>
<script type="text/javascript">
$(document).ready(function(){
	getHtmlLoadingBeforeHeight();
	getHtmlLoadingAfterHeight();
	//选项卡
	var $li = $(".tab li");
	var $tbl = $(".formlist table");			
	$(".tab li").click(function(){
		var $this = $(this);
		var $t = $this.index();
		$li.removeClass();
		$li.attr('style','');
		if($this.prev().length){
			$this.prev().attr('style','border-right:none;');
		}else if($this.next().length){
			$this.next().attr('style','border-left:none;');
		}
		$this.addClass("current");
		$tbl.css('display','none');
		$tbl.eq($t).css('display','block');
	});
	//.trim() IE不支持
	if($.trim('${mystock.fid}')!=''){
		$('.tab :last-child').click();
		$('.tab :first-child').hide();
	}else{//备货界面的 首次、末次发货默认值
		$('table a:not(.for_date)[setDay=7],[setDay=30]').click();
	}
	
	var $numb3=$("#faddressid option").size();
	$("#faddressid").selectlist({
		zIndex: $numb3,
		width: 578,
		height: 32
	});	
	var currentDate = new Date();
	var currentHours = currentDate.getHours();
	if(currentHours <= 13){
		$("#am").addClass("_color");
		$("#am").siblings().removeClass("_color");
		/* $("#hours").val("am"); */
	}else{
		$("#pm").addClass("_color");
		$("#pm").siblings().removeClass("_color");
		/* $("#hours").val("pm"); */
	}
	//数字检验
	window.addNumOnlyEvent($('.amount'));
	
	$('.amount').bind('keydown', function (e){
		if(e.which==13){
			e.preventDefault();
			$(this).blur();
		}
	});
	//校验表单提交数据
	function verifyData(){
		var datas;
		var time = true;
		if($('table:visible .amount').val()<=0){
			layer.tips('数量不能为0和负数！', 'table:visible .amount', {tips: [1, '#F7874E'],time: 4000});
			return false;
		}
		if($('table:visible').attr('name')=='deliverapply'){//要货
			
			if(!$('table:visible .date_').val()){
				layer.tips('交期不能为空！', 'table:visible .date_', {tips: [1, '#F7874E'],time: 4000});
				return false;
			}
			var stock='${stock[0].fbalanceqty-stock[0].amount}';
			if("${fisManageStock}"==="0"||stock==="0"||eval($('table:visible .amount').val())>eval(stock))
			{
				var selectdate = new Date(Date.parse($('table:visible .date_').val().replace(/-/g,  "/")))
				var mindate = new Date(Date.parse('${nowDate}'.replace(/-/g,  "/")));
				mindate.setDate(mindate.getDate()+parseInt('${fdays==null?0:fdays}'));
				if(selectdate.getTime()<mindate.getTime())
				{
					layer.tips("制造商设置的最早交期为"+mindate.getFullYear()+"-"+eval(mindate.getMonth()+1)+"-"+mindate.getDate(), 'table:visible .date_', {tips: [1, '#F7874E'],time: 4000});
					return false;
				}
			}
			if($('#faddressid input[name=faddressid]').val()==0){
				layer.tips('请选择地址！', '#faddressid', {tips: [1, '#F7874E'],time: 4000});
				return false;
			}
			var apm = "";
			if($('table:visible ._color').text()=='上午'){
				apm = " 09:00:00";
			}else{
				apm = " 14:00:00";
			}
			datas = $('#deliverapply').serializeArray();
			for(var i=0;i<datas.length;i++){
				if(datas[i].name=="farrivetime"){
					datas[i].value = datas[i].value+apm;
				}
			}
			return datas;
		}else{//备货
			$('table:visible .date_').each(function(i,d){
				if(!$(this).val()){
					layer.tips('首次、末次发货时间不能为空！', '#'+d.id, {tips: [1, '#F7874E'],time: 4000});
					time = false;
				}
			});
			if(!time){
				return false;
			}
			datas = $('#mystock').serializeArray();
			return datas;
		}
	}
	//下单按钮提交数据
	$('#saveButton').click(function(){
		var datas = verifyData();
		if(datas===false){//验证数据
			return false;
		}
		
		var loadDel = layer.load(2);
		if($('table:visible').attr('name')=='deliverapply'){//要货
			
			$.post("${ctx}/deliverapply/saveOrderOnlineByDeliverapply.net",datas,function(data){
				if(data.success){ 
					gotoPages();
					layer.close(loadDel);
				}else if(data.success===false)
				{
					layer.close(loadDel); 
					parent.layer.msg(data.msg);
				}
			},"json");
		}else{//备货
			
			
			$.post("${ctx}/deliverapply/saveOrderOnlineByMystock.net",datas,function(data){
				if(data.success){
// 					parent.$('#c3d28df961a3c9ebfc7994361031186c').click();//跳转  ？待定
					gotoPages();
					layer.close(loadDel);
				}else if(data.success===false)
				{
					layer.close(loadDel);
					parent.layer.msg(data.msg);
				}
				else{
					layer.close(loadDel);
					parent.layer.msg('数据错误！');
				}
			},"json");
		}
		});
		
	//下单调整提示
	function gotoPages(){
		parent.layer.confirm('下单成功！', {
		    btn: ['继续下单','查看订单'] //按钮
		}, function(index){//1
			javascript:history.go(-1);
			location.reload();
//		parent.$('#c3d28df961a3c9ebfc7994361031186c').click();//跳转  ？待定
			parent.layer.close(index);
		}, function(){//2
			parent.$('#c3d28df961a3c9ebfc7994361031186c').click();
			localStorage.gotoPage = "#tab_list :nth-child(3)";//订单记录
//				window.$(function(){
//					parent.setTimeout(function(){
//						window.$(window.document).find("#tab_list li").eq(2).trigger('click');
//					},400);
//				});
		});
	}
	//加入购物车提交数据
	$('#saveShopping').click(function(){
		var datas = verifyData();
		if(datas===false){//验证数据
			return false;
		}
		$(datas).each(function(i,d){
			if($("table:visible *[name='"+d.name+"']").attr('shopping')==undefined){//地址用了插件 暂不支持需特殊处理
				d.name = "faddress";
			}else{
				d.name = $("table:visible *[name='"+d.name+"']").attr('shopping');
			}
		});
		if($('table:visible').attr('name')=='deliverapply'){//要货
			datas.push({name:'ftype',value:'0'});
		}else{
			datas.push({name:'ftype',value:'1'});
		}
		$.post("${ctx}/shopcar/saveCusprivatedelivers.net",datas,function(data){
				if(data=="success"){
					parent.layer.confirm('下单成功！', {
					    btn: ['继续下单','查看购物车'] //按钮
					}, function(index){//1
						parent.$('#c3d28df961a3c9ebfc7994361031186c').click();//跳转  ？待定
						parent.layer.close(index);
					}, function(){//2
						parent.$('#c3d28df961a3c9ebfc7994361031186c').click();
						localStorage.gotoPage = ".shopCar";//订单记录
					});
					
				}else{
					parent.layer.msg('数据错误！');
				}
		});
	});
	//要货默认当前时间
	var d = new Date(Date.parse('${nowDate}'.replace(/-/g,  "/")));
	var defaultday = '${fdefaultdays==null?5:fdefaultdays}';//默认交期
	d.setDate(d.getDate()+parseInt(defaultday));//当前天数加5天
	$('#jdate').val(d.getFullYear()+"-"+eval(d.getMonth()+1)+"-"+d.getDate());
	//.trim() IE不支持
	if($.trim("${deliverapply}")!=''){//要货修改
		var date = "${deliverapply.farrivetime}".substr(0,10);
		var apm = "${deliverapply.farrivetime}".substr(10);
		if($.trim(apm) =="09:00:00.0"){
			$("#am").click();
		}else{
			$("#pm").click();
		}
		//交期
		$('#jdate').val(date);
		//地址
		$('.select-button').val("${addressname}");
		$('.faddressid').val("${deliverapply.faddressid}");
		console.log("${addressname}");
	}
});

	
	
	//新增地址
	function add_address(){
		parent.layer.open({
				    title: ['新增收货地址','background-color:#CC0000; color:#fff;'],
				    type: 2,
				    anim:true,
				    area: ['530px', '200px'],
				    content: "${ctx}/custproduct/add_address.net?fcustomerid="+"${custproduct.fcustomerid}"
		});
	}
	
	//局部刷新地址选择框
	function sendAjax(){
		 $.ajax({
				url:"${ctx}/custproduct/getAddress.net",
				type:"post",
				dataType:"json",
				data:{fid:"${custproduct.fid}"},
				success:function(data){
					$("#faddressid").empty();
					$("<option value='0'>--请选择--</option>").appendTo("#faddressid");
					$.each(data.address,function(i,item){
						$("<option></option>").val(item.fid).text(item.fdetailaddress).appendTo("#faddressid");	
					});
					var $numb3=$("#faddressid option").size();
					$("#faddressid").selectlist({
						zIndex: $numb3,
						width: 578,
						height: 32
					});	
				}
			});
	}
	
	//返回按钮 后退历史记录
	function lastStep(){
		history.back();
	}
	
	//小图片单击事件
	$('.smallImage').click(function(){
		$($('.showImage').children()).attr('src',$(this).attr('src'));
		$($('.showImage').children()).attr('style','padding:10px;width:340px;height:330px;');
	});
	
	//为a标签 赋单击事件
	$('table a:not(.for_date)').each(function(i,a){
		$(this).click(function(){
			$("[name="+$(this).attr('name')+"]").removeClass('_color');
			$(this).addClass('_color');//改变样式
			if($(this).attr('setDay')){//设置首次发货、末次发货时间
				var newDate = new Date();
				var d;
				if($(this).attr('setDay')==30||$(this).attr('setDay')==31){//30、31天表示 +1月
					d = newDate.setMonth(newDate.getMonth()+1);
				}else{//+天数
					d = newDate.setDate(newDate.getDate()+eval($(this).attr('setDay')));
				}
				var dd = new Date(d);
				
				$(this).prevAll('input').val(dd.getFullYear()+"-"+eval(dd.getMonth()+1)+"-"+dd.getDate());
			}
		});
	});
	
	//加减数量
	$('.reduce,.plus').each(function(i,num){
		$(this).click(function(){
			if($(this).prev().length){//+
				$(this).prev().val(eval($(this).prev().val())+1);
			}else if($(this).next().length){//-
				if($(this).next().val()==0){
					return;
				}
				$(this).next().val(eval($(this).next().val())-1);
			}
		});
	});
	
	//全部下载
	function downLoad(obj){
		window.open("${ctx}/productfile/downProductdemandFiles.net?pfid="+obj+"&ftype=ddinfo","_blank");
	}
	
	function getMinDate()
	{
		var stock='${stock[0].fbalanceqty-stock[0].amount}';
		if("${fisManageStock}"==="0"||stock==="0"||eval($('table:visible .amount').val())>eval(stock))
		{
			var mindate = new Date(Date.parse('${nowDate}'.replace(/-/g,  "/")));
			mindate.setDate(mindate.getDate()+parseInt('${fdays==null?0:fdays}'));
			return mindate.getFullYear()+"-"+eval(mindate.getMonth()+1)+"-"+mindate.getDate();
		}
		return '${nowDate}';
	}
</script>

</body>
</html>