<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单出入库</title>
<style>
form,body,h1,input,label{padding:0;margin:0;}
input{outline:none;}
body{font-size:14px;background:#fff;-user-select:none;-webkit-user-select:none;}
h1{font-size:14px;color:#fff;background:#d80c18;height:30px;line-height:30px;text-indent:15px;}
#main{padding:40px 0 0;}
.left,.right{width:40%;text-align:center;padding:0 5%;}
.left{float:left;}
.right{float:right;}
label{float:left;height:30px;line-height:30px;}
.input_group{float:right}
.input_group *{float:left;}
input{width:60px;height:24px;line-height:24px;text-align:center;}
input.active{outline:1px solid red;}
#confirm{text-decoration:none;color:#f1f1f1;text-align:center;background:#aaa;display:block;
margin:60px auto 0;width:80px;height:30px;line-height:30px;border-radius:4px;}
#confirm:hover{background:#d80c18;}
.input_group span{background:#eee;cursor:pointer;width:26px;height:28px;line-height:28px;text-align:center;}
.input_group span:hover{color:#fff;background:#d80c18;}
.input_group span:active{color:#ccc;}
.minus{border-radius:4px 0 0 4px;}
.plus{border-radius:0 4px 4px 0;}
</style>
<script type="text/javascript" src="${ctx}/js/_common.js"></script>
<script type="text/javascript">
$(function(){
	$('.minus').click(function(){
		var $input = $(this).next(),
			val = parseInt($input.val()) || 0;
		if(val>0){
			$input.val(val-1);
			$input.change();
		}
	});
	$('.plus').click(function(){
		var $input = $(this).prev(),
			val = parseInt($input.val()) || 0;
		$input.val(val+1);
		$input.change();
	});
	$('.amount').change(function(){
		var me = $(this),
			val = parseInt(me.val()) || 0;
		me.val(val);
		if(val !== 0){
			$('.amount').not(this).val('0');
		}
	});
	function validate(){
		$('input').blur();
		var inStock = parseInt($('#in').val()),
			outStock = parseInt($('#out').val());
		if(inStock<=0 && outStock<=0){
			layer.msg('请正确填写出入库数量！');
			return false;
		}
		return true;
	}
	$('#confirm').click(function(){
		if(validate()){
			$.ajax({
				url: '${ctx}/productplan/orderInOut.net',
				data: $('#form').serialize(),
				type: 'post',
				success: function(res){
					console.log(res);
					if(res == 'success'){
						getFrameWin().loadData();
						parent.layer.closeAll();
					}else{
						parent.layer.msg(res);
					}
				}
			});
		}
	});
});
</script>
</head>
<body>
	<!-- <h1>订单出入库</h1> -->
	<form action="${ctx}/productplan/orderInOut.net" method="post" id="form">
		<div id="main">
			<div class="left">
				<label for="in">入库数量</label>
				<div class="input_group">
					<span class="minus">-</span>
					<input id="in" value="0" name="orderInOutBean.stockIn" class="amount"/>
					<span class="plus">+</span>
				</div>
			</div>
			<div class="right">
				<label for="out">出库数量</label>
				<div class="input_group">
					<span class="minus">-</span>
					<input id="out" value="0" name="orderInOutBean.stockOut" class="amount"/>
					<span class="plus">+</span>
				</div>
			</div>
			<input type="hidden" name="orderInOutBean.productPlanId" value="${id}" />
			<input type="hidden" name="history" value="${queryHistory}" />
			<a href="javascript:void(0);" id="confirm">确 定</a>
		</div>
	</form>
</body>
</html>