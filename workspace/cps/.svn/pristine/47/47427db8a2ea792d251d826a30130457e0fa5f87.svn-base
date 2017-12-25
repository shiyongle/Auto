<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<script src="${ctx}/js/jquery.jqpagination.js"></script>
<link rel="stylesheet" href="${ctx}/css/jqpagination.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.selectlist.css" />
<script src="${ctx}/js/jquery.selectlist.js" type="text/javascript" language="javascript"></script>

<style>
* {
	margin: 0px auto;
	padding: 0px;
}

.content {
	width: 820px;
	font: 14px 宋体;
}

.title {
	background-color: #d80c18;
	padding: 15px;
	color: #fff;
	border-top-left-radius: 10px;
	border-top-right-radius: 10px;
}

.table {
	width: 42%;
	margin: 10px;
	float: left;
	border: 1px solid lightgray;
	height: 360px;
	position: relative;
	margin-top: 0px;
}

.table table {
	border-collapse: collapse;
	width: 100%;
	text-align: center;
	height: 360;
}

table tr {
	height: 27px;
}

.table1 table {
	border-collapse: collapse;
	width: 100%;
	text-align: center;
	height: 360;
	padding: 30px;
}

.tdBorder {
	border-left: 1px solid lightgray;
	border-right: 1px solid lightgray;
}

.titleTR {
	height: 30px;
	background-color: #f1f1f1;
}

.table1 {
	width: 10%;
	margin-top: 130px;
	float: left;
	height: 230px;
	position: relative;
}

.buttomAble {
	width: 90%;
	height: 30px;
	outline: none;
	background-color:#FF0000;
	color: #fff;
	border: none;
	cursor: pointer;
	font-size: 16px;
}

.buttomUnable {
	width: 90%;
	height: 30px;
	outline: none;
	background-color:#868282;
	color: #fff;
	border: none;
	cursor: pointer;
	font-size: 16px;
}
</style>
<title>菜单配置</title>
<script type="text/javascript">

	$(document).ready(function() {
		$("#btnUnEffect").addClass('buttomUnable').attr("disabled","disabled");
		$("#btnEffect").addClass('buttomUnable').attr("disabled","disabled");
		
		$(".allCheck1").click(function(){
			var check = $(this).prop("checked");
			$('.item1').prop('checked',check);
			$("#btnEffect").removeClass().addClass('buttomUnable').attr("disabled","disabled");
			if(check){
				$("#btnUnEffect").removeClass().addClass('buttomAble').removeAttr("disabled");
			}
			else{
				$("#btnUnEffect").removeClass().addClass('buttomUnable').attr("disabled","disabled");
			}
		});
		$(".allCheck2").click(function(){
			var check = $(this).prop("checked");
			$('.item2').prop('checked',check);
			$("#btnUnEffect").removeClass().addClass('buttomUnable').attr("disabled","disabled");
			if(check){
				$("#btnEffect").removeClass().addClass('buttomAble').removeAttr("disabled");
			}
			else{
				$("#btnEffect").removeClass().addClass('buttomUnable').attr("disabled","disabled");
			}
		});
		 		 
		 $("table").delegate(".item1","click",function(){
			 $('.item2').attr('checked',false);
			 $("#btnEffect").addClass('buttomUnable').attr("disabled","disabled");
			 if($('.item1:checked').length>0){
				 $("#btnUnEffect").removeClass('buttomUnable').addClass('buttomAble').removeAttr("disabled");
			 }
			 else{
				 $("#btnUnEffect").removeClass('buttomAble').addClass('buttomUnable').attr("disabled","disabled");
			 }
		 });
		 
		 $("table").delegate(".item2","click",function(){
			 $('.item1').attr('checked',false);
			 $("#btnUnEffect").addClass('buttomUnable').attr("disabled","disabled");
			 if($('.item2:checked').length>0){
				 $("#btnEffect").removeClass('buttomUnable').addClass('buttomAble').removeAttr("disabled");
			 }
			 else{
				 $("#btnEffect").removeClass('buttomAble').addClass('buttomUnable').attr("disabled","disabled");
			 }
		 });
		 
			 $("#btnUnEffect").click(function(){
			  var myvalues = $('.item1:checked').map(function(){
				  return this.getAttribute('myvalue');
			  }).get().join();
			  $.ajax({
					type : "POST",
					url : "${ctx}/usermenu/saveOrUpdate.net" ,
					dataType:'text',
					data :({datas:myvalues,type:'disable'}),
					success : function(response) {
						if (response == "success") {
							layer.alert("操作成功");
							gridUserMenuTable();
						}
					},error: function(response) {
							alert("操作失败");
						}
				});
		  });
		  
		  $("#btnEffect").click(function(){
			  var myvalues = $('.item2:checked').map(function(){
				  return this.getAttribute('myvalue');
			  }).get().join();
			  $.ajax({
					type : "POST",
					url : "${ctx}/usermenu/saveOrUpdate.net" ,
					dataType:'text',
					data :({datas:myvalues,type:"able"}),
					success : function(data) {
						if(data ="success"){
							layer.alert('操作成功！');
							gridUserMenuTable();
						}else{
							layer.alert('操作失败！', function(index){layer.close(index);});
						}
					}
				});
		  });
	});

	function gridUserMenuTable()
	{
		$.ajax({
			type : "POST",
			url : "${ctx}/usermenu/getUserMenuList.net",
			dataType:"json",
// 			data:obj,
			//async: false,
			success : function(response) {
				$(".buttomComButton ").css("background","#868282"); 
				  $(".buttomComButton ").attr("disabled","disabled");
				  $(".buttomUnComButton ").css("background","#868282"); 
				  $(".buttomUnComButton ").attr("disabled","disabled");
				$(".content1Tr").remove();
				$.each(response.menulist, function(i, ev) {
					  var  html =[      
					        '<tr class="content1Tr">',
							'<td><input type="checkbox"  myvalue="',ev.fid,'" class="item1" name="product" productid ="',ev.fid,'" /></td>',
							'<td>',ev.forder,'</td>',
							'<td>',ev.fname,'</td>',
						'</tr>'].join("");
					   $(html).appendTo("#tbl1");
				});
				$.each(response.menudislist, function(i, ev) {
					  var  html =[      
					        '<tr style="height: 25px;" class="content1Tr">',
							'<td><input type="checkbox"  myvalue="',ev.fid,'" class="item2" name="product" productid ="',ev.fid,'" /></td>',
							'<td>',ev.forder,'</td>',
							'<td>',ev.fname,'</td>',
						'</tr>'].join("");
					   $(html).appendTo("#tbl2");
				});
			}
		});
	}
	
</script>
</head>
<body>
	<div class="content">
		<div>
			<p class="title">菜单配置</p>
		</div>
		<table width="810px" height="10px" style="margin-left:10px;">
			<tr>
				<td width=42%>全部菜单></td>
				<td width=10%>&nbsp;</td>
				<td width=42%>禁用菜单></td>
			</tr>
		</table>
		<div width="820px">
			<div class="table">
				<table id="tbl1">
					<tr class="titleTR">
						<td><input type="checkbox" class="allCheck1" /></td>
						<td class="tdBorder">序号</td>
						<td>菜单名称</td>
					</tr>
					<c:forEach var="menu" items="${menulist }">
					<tr style="height: 25px" class="content1Tr">
						<td><input type="checkbox" myvalue="${menu.fid }" class="item1"  /></td>
						<td>${menu.forder }</td>
						<td>${menu.fname }</td>
					</tr>
<%-- 						<td><input type="checkbox" class="l${menu.forder }" value="${menu.forder }"  onclick="check();" /></td> --%>
					 </c:forEach>
				</table>

			</div>
		</div>
		<div class="table1">
			<table id="tbl3">
				<tr>
				<td><input type="button" id="btnUnEffect" value="禁用"  /></td>				
				</tr>
				
				<tr>
				<td><input type="button" id ="btnEffect" value="启用"   /></td>
				</tr>
			</table>
		</div>
		<div class="table">

			<table id="tbl2">
				<tr class="titleTR">
					<td><input type="checkbox" class="allCheck2" /></td>
					<td class="tdBorder">序号</td>
					<td>菜单名称</td>

				</tr>
				<c:forEach var="dismenu" items="${menudislist }">
				<tr style="height: 25px;" class="content1Tr">
					<td><input type="checkbox" myvalue="${dismenu.fid }" class="item2" /></td>
					<td>${dismenu.forder }</td>
					<td>${dismenu.fname }</td>
				</tr>
<%-- 						<td><input type="checkbox" name="message" class="${dismenu.forder }"  data-fid="${dismenu.forder }"/></td> --%>
				</c:forEach>
			</table>

		</div>

	</div>
</body>
</html>