<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
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
*{
	margin:0px auto;
	padding:0px;
}
.content{
	width:820px;
	font:14px 宋体;
}
.title{
	background-color:#d80c18;
	padding:15px;
	color:#fff;
	border-top-left-radius:10px;
	border-top-right-radius:10px;
}
.filter{
	margin:10px 0px 10px 35px;
}
.filter .query{
	width:200px;
	height:25px;
	outline: none;
	line-height:25px;
	margin-right:5px;
	margin-left:230px;
	border: 1px solid lightgray;
	padding-left:5px;
}
.filter .querybutton{
	border: none;
	font-size:18px;
	position: relative;
    top: 3px;
    cursor: pointer;
}
.table{
	width:48%;
	margin:5px;
	float:left;
	border:1px solid lightgray;
	height:360px;
	position: relative;
}
.table table{
	border-collapse:collapse;
	width:100%;
	text-align:center;
	height:360;
}
table tr{
	height:27px;
}
.tdBorder{
	border-left:1px solid lightgray;
	border-right:1px solid lightgray;
}
.titleTR{
	height:30px;
	background-color:#f1f1f1;
}
#kkpager{
	float:left;
	height:22px;
	line-height:22px;
	margin-left:5px;
	margin-top:5px;
}
.bottomPage{
	position: absolute;
	bottom:0px;
	background-color:#f1f1f1;
	width:100%;
	height:30px;
}
.buttomComButton{
	width:80px;
	height:30px;
	float:right;
    outline: none;
    background-color:#868282;
    color:#fff;
    border:none;
    cursor: pointer;
    font-size:16px;
}
.buttomUnComButton{
	width:80px;
	height:30px;
	float:right;
    outline: none;
    background-color:#868282;
    color:#fff;
    border:none;
    cursor: pointer;
    font-size:16px;
}
</style>
<title>常用材料</title>
<script type="text/javascript">
	$(document).ready(function(){
		var $num4=$("#supplierSelect option").size();
		$("#supplierSelect").selectlist({
			zIndex: $num4,
			width: 185,
			height: 25,
			onChange : function(){
				
			}
		});
			
		$("#supplierSelect li").click(function(){
	    	var value =$(this).attr("data-value");
	    	$("#supplierSelect").val(value);
			gridSupplierCardboardTable(1,"firstORbywhere");
			gridCommonMaterialTable(1,"firstORbywhere");
		});
			
		$("#searchButtonQuickOrder").click(function() {
			gridSupplierCardboardTable(1,"firstORbywhere");
			gridCommonMaterialTable(1,"firstORbywhere");
		});
		
		$("#searchKey").keydown(function(e){
			if(e.keyCode==13){
				$(this).next()[0].click()
				return false;
			}
		});
		
		$('.allCheck').click(function(){
			var me = $(this);
			var checked = me.attr('checked')==undefined?false:true;
			console.log(1);
			me.parents("table").find('input[type=checkbox]').attr('checked',checked);
		})
		
		gridSupplierCardboardTable(1,"firstORbywhere");
		gridCommonMaterialTable(1,"firstORbywhere");
	})
	function setCommonMaterial(state){
		var fids='';
		//常用
		if(state==1){
			$(".content1Tr").each(function(i,ev){
				if($(ev).find("input[type=checkbox]").prop('checked')){
					fids += $(ev).find("input[type=checkbox]").attr('productid')+",";
				}
			});	
		}
		else{
			$(".content2Tr").each(function(i,ev){
				if($(ev).find("input[type=checkbox]").prop('checked')){
					fids += $(ev).find("input[type=checkbox]").attr('productid')+",";
				}
			});	
		}
		if(fids.length==0){
			layer.msg('请选择数据！');
			return false;
		}
		if(fids!=''){
			fids = fids.substring(0,fids.length-1);
		}
		$.ajax({
			url:window.getBasePath()+"/productdef/setCommonMaterial.net",
			type:"post",
			dataType:"text",
			data:{fids:fids,fstate:state},
			success:function(data){
				if(data ="success"){
					layer.alert('操作成功！', function(index){
						layer.close(index);
						gridSupplierCardboardTable(1,"firstORbywhere");
						gridCommonMaterialTable(1,"firstORbywhere");
						});
					var reloadMaterial = getFrameWin().reloadMaterial;
					reloadMaterial && reloadMaterial();
				}else{
					layer.alert('操作失败！', function(index){layer.close(index);});
				}
			},
			error:function (){
				layer.alert('操作失败！', function(index){layer.close(index);});
			}
		});
	}
	function gridSupplierCardboardTable(page,by){
		var loadDel = layer.load(2);
		var obj =$("#searchForm").serialize();	
		$.ajax({
			type : "POST",
			url : "${ctx}/productdef/getSupplierCardboardList.net?pageNo="+page,
			dataType:"json",
			data:obj,
			//async: false,
			success : function(response) {
				$(".content1Tr").remove();
				$.each(response.list, function(i, ev) {
					  var  html =[      
					        '<tr class="content1Tr">',
							'<td><input type="checkbox" name="product" productid ="',ev.fid,'" /></td>',
							'<td>',(page*10+i-9),'</td>',
							'<td>',ev.fname,'</td>',
							'<td>',ev.flayer,'</td>',
							'<td>',ev.ftilemodelid,'</td>',	
						'</tr>'].join("");
					   $(html).appendTo("#tbl1");
				});
				var totalPage = Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
				if(by=="firstORbywhere"){
					$("#bottomPage1").empty();
					var pagehtml = ['<div class="pagination" style="float:left;margin:5px 0px 0px 5px;">',
								    '<a href="#" class="first" data-action="first">&laquo;</a>',
								    '<a href="#" class="previous" data-action="previous">&lsaquo;</a>',
								    '<a style="font:14px 宋体;line-height:21px;">第</a>',
								    '<input type="text" readonly="readonly" data-max-page="',totalPage,'" style="width:40px;line-height:20px;"/>',
								    '<a style="font:14px 宋体;line-height:21px;">页</a>',
								    '<a href="#" class="next" data-action="next">&rsaquo;</a>',
								    '<a href="#" class="last" data-action="last">&raquo;</a>',
								    '</div>',
						    		'<span id="kkpager">共',totalPage,'页/',response.totalRecords,'条数据</span>',
						    	    '<input type="button" class="buttomComButton" value="设为常用"/>'].join("");
			
									 $(pagehtml).appendTo("#bottomPage1");
									 
									 $('.buttomComButton').mousemove (function(){
										$(this).css('backgroundColor','#d80c18')
									 }).mouseout(function(){
										$(this).css('backgroundColor','#868282')
									 }).click(function(){
										 setCommonMaterial(1);
									 });
									 
									$("input[name=product]").click(function(){
										if($(this).is(":checked")==false){
											$(".allCheck").attr("checked",false);	
										}
									});
										
									$('.pagination').jqPagination({
							   		   paged: function(page) {
									        // do something with the page variable
											gridSupplierCardboardTable(page);
							  		  }
									});
				}
				layer.close(loadDel); 
			}
		});
	}
	
	function gridCommonMaterialTable(page,by){
		var obj =$("#searchForm").serialize();
		var loadDel = layer.load(2);
		$.ajax({
			type : "POST",
			url : "${ctx}/productdef/getCommonMaterialList.net?pageNo="+page,
			dataType:"json",
			data:obj,
			//async: false,
			success : function(response) {
				$(".content2Tr").remove();
				$.each(response.list, function(i, ev) {
					  var  html =[      
					        '<tr class="content2Tr">',
							'<td><input type="checkbox" name="product" productid ="',ev.fid,'" /></td>',
							'<td>',(page*10+i-9),'</td>',
							'<td>',ev.fname,'</td>',
							'<td>',ev.flayer,'</td>',
							'<td>',ev.ftilemodelid,'</td>',	
						'</tr>'].join("");
					   $(html).appendTo("#tbl2");
				});
				var totalPage = Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
				
				if(by=="firstORbywhere"){
					$("#bottomPage2").empty();	
					var pagehtml = ['<div class="pagination" style="float:left;margin:5px 0px 0px 5px;">',
								    '<a href="#" class="first" data-action="first">&laquo;</a>',
								    '<a href="#" class="previous" data-action="previous">&lsaquo;</a>',
								    '<a style="font:14px 宋体;line-height:21px;">第</a>',
								    '<input type="text" readonly="readonly" data-max-page="',totalPage,'" style="width:40px;line-height:20px;"/>',
								    '<a style="font:14px 宋体;line-height:21px;">页</a>',
								    '<a href="#" class="next" data-action="next">&rsaquo;</a>',
								    '<a href="#" class="last" data-action="last">&raquo;</a>',
								    '</div>',
						    		'<span id="kkpager">共',totalPage,'页/',response.totalRecords,'条数据</span>',
						    	    '<input type="button" class="buttomUnComButton" value="取消常用"/>'].join("");
		
									 $(pagehtml).appendTo("#bottomPage2");
									 
									 $('.buttomUnComButton').mousemove (function(){
										$(this).css('backgroundColor','#d80c18')
									 }).mouseout(function(){
										$(this).css('backgroundColor','#868282')
									 }).click(function(){
										 setCommonMaterial(0);
									 });
								 
									$("input[name=product]").click(function(){
										if($(this).is(":checked")==false){
											$(".allCheck").attr("checked",false);	
										}
									});
										
									$('.pagination').jqPagination({
							   		   paged: function(page) {
									        // do something with the page variable
											gridCommonMaterialTable(page);
							  		  }
									});
				}				
				
				layer.close(loadDel); 
			}
		});
	}
</script>
</head>
<body>
<div class="content">
	<div>
		<p class="title">常用材料</p>
		<form  id="searchForm" class="filter">
			<span>制造商：</span>
			<select style="width:185px;height:25px;" id="supplierSelect" name="myProductDefQuery.supplierid" >
                <c:forEach var="entry" items="${supplier}">
                  <c:choose>
	                  <c:when test="${entry.fid=='39gW7X9mRcWoSwsNJhU12TfGffw='}">
	                 	 <option value="${entry.fid}" id="${entry.fid}" selected = "selected" >${entry.fname}</option>
	                  </c:when>
		              <c:otherwise>
		              	 <option value="${entry.fid}" id="${entry.fid}" >${entry.fname}</option>
					  </c:otherwise>
				  </c:choose>
				</c:forEach>
			</select>		 
							
	        <input type="text" class="query" id="searchKey" name="myProductDefQuery.searchKey"  placeholder="按材料、层数、愣型查询"/>
	        <input type="button" value="查询" class="querybutton" id="searchButtonQuickOrder"/>
		</form>
	</div>
	<div width="820px">
			<div class="table">
				<table id ="tbl1" >
					<tr class="titleTR">
						<td><input type="checkbox" class="allCheck"/></td>
						<td class="tdBorder">序号</td>
						<td>材料</td>
						<td class="tdBorder">层数</td>
						<td>楞型</td>
					</tr>
					<tr style="height:15px"><td colspan="5"></td></tr>
				</table>
				<div id = "bottomPage1" class="bottomPage">
				</div>
			</div>
			<div class="table">
				<table  id ="tbl2" >
					<tr class="titleTR">
						<td><input type="checkbox" class="allCheck"/></td>
						<td class="tdBorder">序号</td>
						<td>材料</td>
						<td class="tdBorder">层数</td>
						<td>楞型</td>
					</tr>
					<tr style="height:15px"><td colspan="5"></td></tr>		
				</table>
				<div id="bottomPage2" class="bottomPage">
				</div>
			</div>
	</div>
</div>
</body>
</html>