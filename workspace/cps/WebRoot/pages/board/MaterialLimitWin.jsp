<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<title>下料规格编辑</title>
<style>
*{
	margin:0px;
	padding:0px;		
}
#content{
	width:740px;
	height:198px;
	border-radius:10px;
	border:1px solid red;
}
.title{
	background-color:#d80c18;
	color:#fff;
	padding:10px;
	border-top-left-radius:10px;
	border-top-right-radius:10px;
	font:14px 宋体;
}
.toolbar{
	height:30px;
	width:740px;
	float:left;
	font:14px 宋体;
	border-bottom:1px solid lightgray;
}
.toolbar img{
    vertical-align: bottom;
    margin-left:10px;
}
.toolbar  span{
	cursor: pointer;
}
table tr{
	height:45px;
	font:14px 宋体;
}
table tr td{
	padding-left:40px;
	padding-top:15px;
	width:40%;
}
td input{
	height:26px;
	border:1px solid lightgray;
	outline:none;
	padding-left:5px;
	float:left;
}
.tdTitle{
    margin-top: 6px;
}
td span{
	float:left;
}
.add{
	background:url(${ctx}/css/images/addorminus.png) -1px 0px no-repeat;
	width:23px;
	height:14px;
	cursor: pointer;
}
.minus{
	background:url(${ctx}/css/images/addorminus.png) -1px -15px no-repeat;
	width:23px;
	height:14px;
	cursor: pointer;
}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		$('.add').click(function(){
			var val = $(this).parent().prev().val();
			if($.isEmptyObject(val)){
				val = 0;
			}
			$(this).parent().prev().val(eval(val)+1);
		})
		$('.minus').click(function(){
			var val = $(this).parent().prev().val();
			if($.isEmptyObject(val)){
				val = 0;
			}
			if(val==0){
				$(this).parent().prev().val(0);
				return false;
			}
			$(this).parent().prev().val(eval(val)-1);
		})
		$('td input').keydown(function(e){
			if(e.keyCode==38){//上
				$(this).next().children()[0].click();
				return false;
			}else if(e.keyCode==40){//下
				$(this).next().children()[1].click();
				return false;
			}
		});
		/*JQuery 限制文本框只能输入数字和小数点*/  
        $("td input").keyup(function(){    
            $(this).val($(this).val().replace(/[^0-9.]/g,''));    
        }).bind("paste",function(){  //CTR+V事件处理    
            $(this).val($(this).val().replace(/[^0-9.]/g,''));     
        }).css("ime-mode", "disabled"); //CSS设置输入法不可用    
        
        //回显，保存
        $.each(parent.materialLimit || {},function(key,val){
        	var field = $('#'+key);
        	if(field.length){
        		field.val(val);
        	}
        });
		$('#save').click(function(){
			var	data = {};
				fmaxlength = data.fmaxlength = parseFloat($('#fmaxlength').val()),
				fminlength = data.fminlength = parseFloat($('#fminlength').val()),
				fmaxwidth = data.fmaxwidth = parseFloat($('#fmaxwidth').val()),
				fminwidth = data.fminwidth = parseFloat($('#fminwidth').val()),
				customerid = data.customerid = $('#customerid').val();
			if(fmaxlength<fminlength){
				layer.msg('最大长度不能小于最小长度，请更改！');
			}else if(fmaxwidth<fminwidth){
				layer.msg('最大宽度不能小于最小宽度，请更改！');
			}
			$.ajax({
				url: '${ctx}/board/saveMaterialLimit.net',
				data: data,
				success: function(res){
					if(res == 'success'){
						var index = parent.layer.getFrameIndex(window.name);
						parent.materialLimit = data;
						parent.layer.close(index);
					}else{
						layer.msg(res);
					}
				}
			});
		});
		$('#cancel').click(function(){
			parent.layer.closeAll();
		});
	});
</script>
</head>
<body>
	<div id="content">
		<p class="title">下料规格编辑界面</p>
		<div class="toolbar">
			<span id="save"><img src="${ctx}/css/images/save.png"/>保 存</span>
			<span id="cancel"><img src="${ctx}/css/images/cancel.png"/>取 消</span>
			<div style="margin-left:325px;display: inline;color:lightslategrey;;">请您先设置下料规格的最值，再下单</div>
		</div>
		<input type="hidden" name="customerid" value="${fcustomerid}" id="customerid"/>
		<table width="740px">
			<tr>
				<td><span class="tdTitle">最大长度：</span><input type="text" value="0" name="fmaxlength" id="fmaxlength"/><span><div class="add"></div><div class="minus"></div></span></td>
				<td><span class="tdTitle">最小长度：</span><input type="text" value="0" name="fminlength" id="fminlength"/><span><div class="add"></div><div class="minus"></div></span></td>
			</tr>
			<tr>
				<td><span class="tdTitle">最大宽度：</span><input type="text" value="0" name="fmaxwidth" id="fmaxwidth"/><span><div class="add"></div><div class="minus"></div></span></td>
				<td><span class="tdTitle">最小宽度：</span><input type="text" value="0" name="fminwidth" id="fminwidth"/><span><div class="add"></div><div class="minus"></div></span></td>
			</tr>
		</table>
	</div>
</body>
</html>