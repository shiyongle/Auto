<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>银行卡--钱包</title>
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/pages/pcWeb/css/wallet_card.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/public.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/wallet_card.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/bankcard.js"></script>
</head>
<body>
	<div class="jumbotron wallet_card">
		<div class="row">
			<div class="col-xs-10 col-xs-offset-1">
				<div>我的银行卡</div>
				<div>
					<ul id="cardList">
<!-- 						<li> -->
<!-- 							<div class="line1"> -->
<%-- 								<img src="${ctx}/pages/pcWeb/css/images/wallet/zs_bank.png" style="vertical-align:middle;" /> --%>
<!-- 								<span>中国招商银行</span> -->
<!-- 								<a href="#" class="redClolr right del_card">删除</a> -->
<!-- 							</div> -->
							
<!-- 							<div class="line2 line"> -->
<!-- 								**** **** ****2256 -->
<!-- 							</div> -->
<!-- 							<div class="line"> -->
<!-- 								**** **** ****2256 -->
<!-- 							</div> -->
<!-- 							<div class="line"> -->
<!-- 								银行预留手机：188****8181 -->
<!-- 							</div> -->
<!-- 						</li> -->

						<li>
							<div class="add">
								<div class="add_img redClolr" onclick="add()">添加银行卡</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<!--删除银行卡-->
	<div class="del">
		<div class="del_in">
			<div class="del_tit row">
				<div class="col-xs-12">
				删除银行卡
				<img src="${ctx}/pages/pcWeb/css/images/wallet/del.png" class="right" id="colse"/>
				</div>
			</div>
			
			<div class="reson">
				<form id="del_form">
<!-- 				<div>我要关闭银行卡，因为</div> -->
<!-- 				<div><input type="radio" name="del_reson" value="1"/>我担心资金安全</div> -->
<!-- 				<div><input type="radio" name="del_reson" value="2"/>不明白什么是快捷支付</div> -->
<!-- 				<div><input type="radio" name="del_reson" value="3"/>不再使用这张卡</div> -->
<!-- 				<div><input type="radio" name="del_reson" value="4"/>其他</div> -->
<!-- 				<div> -->
<!-- 					<input type="text" class="form-control" name="suggest" id="suggest" placeholder="您的建议将帮助我们提升产品和服务" /> -->
<!-- 				</div> -->
				<div class="row">
				<div class="col-xs-6"><a href="#" class="btn btn-danger btn-a" id="ok">确定</a></div>
				<div class="col-xs-6"><a href="#" class="btn btn-danger btn-a" id="cancel">取消</a></div>
				</div>
				</form>
			</div>
		</div>
	</div>
	<div class="masker"></div>
<script>
$(document).ready(function(){
	//得到银行卡信息
	bankList();

	
	//删除银行卡界面判断文本框是否可输入
// 	$("#suggest").attr("disabled",true);
// 	$(".reson input").click(function(){
// 		var reson=$('input[name="del_reson"]:checked').val();
// 		if(reson==4){
// 			$("#suggest").attr("disabled",false);
// 		}else{
// 			$("#suggest").attr("disabled",true);
// 		}
// 	})

	$("#colse").click(function(){
		$(".masker,.del").hide();
	})
	//删除银行卡
	$("#ok").click(function(){
// 		var reson=$('input[name="del_reson"]:checked').val();
// 		if(reson==4){
// 			reson=$("#suggest").val();
// 		}
// 		alert(fid)
		$.ajax({
			type:"post",
			url:"${ctx}/pcWeb/bank/delByBank.do?fid="+fid,					
			dataType:'json',
			success:function(response){
				if(response.success=="true"){
					$(".masker,.del").hide();
					bankList();
				}
			}
		});
	});
	//取消删除
	$("#cancel").click(function(){
		$(".masker,.del").hide();
	});
	
});

//点击＋号跳转到添加页面
function add(){
	location.href="${ctx}/pages/pcWeb/wallet/wallet_addcard.jsp";
}

/*删除银行卡*/
var fid="";
function delcard(id){
	var screenWidth = $(window).width();//当前窗口宽度  
	var screenHeight = $(window).height();//当前窗口高度  
	$(".masker").css({"height":screenHeight,"width":screenWidth});	
	$(".masker,.del").show();	
	return fid=id;
}

/*银行卡列表*/
function bankList(){
	$.ajax({
        type:"post",
        url:"${ctx}/pcWeb/bank/findByBankList.do",
		dataType:"json",
  		success:function(response){
    		$("#cardList").empty();    		
    		if(response.success=="true"){  
    		var html="";
    		$.each(response.data, function(i,e){
    			html+='<li>'+
    			'<div class="line1">'+
    				'<img style="width:20px; vertical-align:top;" src="${ctx}/bankIco/'+e.ficon+'" />'+
    				'<span>'+e.fbankName+'</span>'+
   					'<a class="redClolr right del_card" onclick="delcard('+e.fid+')">删除</a>'+
   				'</div>'+
   				'<div class="line2 line">'+
				'**** **** ****'+e.fcardNumber+
				'</div>'+
				'<div class="line">'+
				'储蓄卡'+
				'</div>'+
   				'<div class="line">'+
    				'银行预留手机'+e.ftel.substring(0,3)+'****'+e.ftel.substring(7)+
   				'</div>'+
    			'</li>';
   			});
    		}
    		html+='<li><div class="add"><div class="add_img redClolr" onclick="add()">添加银行卡</div></div></li>';
    		$("#cardList").append(html);
    		getHtmlLoadingAfterHeight();
  		}
    });
}	
</script>
</body>
</html>