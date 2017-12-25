<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ include file="/pages/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>东经股份信息平台</title>
	<link  rel="stylesheet" type="text/css" href="${ctx}/css/list_allGoods.css"/>
	<link  rel="stylesheet" type="text/css" href="${ctx}/css/kkpager.css"/>
	<script type="text/javascript" src="<c:url value='/js/kkpager.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
	<script type="text/javascript">
	function gridCustproductTable(page){
			var keyword =$("#keyword").val();
			var obj1 =$("#custproductQueryForm").serialize();
			var param;
			if(keyword!=null && keyword!=""){
				param =obj1 +"&keyword="+keyword +"&pageNo="+page;
			}else if(obj1 ==null){
				param ="pageNo="+page;
			}else{
				param =obj1 +"&pageNo="+page;
			}
			var loadIndex = layer.load(2);
			$.ajax({
					type : "POST",
					url : "${ctx}/custproduct/load.net",
					dataType:"json",
					data:param,
					success : function(response) {
						$(".alltr").remove();
						if(response.list.length>0){
							$.each(response.list, function(i, ev) {
								var  html;
								if(ev.fiscommon==true){
									html =[
								    		'<tr height="170" class="alltr">'+
								    	  		'<td class="td1" width="510" height="170">'+
								    	  				'<p><input type="checkbox" class="ch_box"/><input  type="hidden" name="fid" value="',ev.fid,'"/></p>'+
								    	  				'<p><img src=',ev.pathImg+' style="border:1px solid lightgray;" width="143" height="133"/></p>'+
							                            '<p class="explan">',ev.fname,'<br />',ev.fspec,'</p>'+
								    	  		'</td>'+
								    	  		'<td>',ev.supplierName,'</td>'+
						                        '<td>',ev.balanceqty,'</td>'+
						                        '<td>',ev.flastordertime,'<br />',ev.flastorderfamount,'</td>'+
						                        '<td class="act">'+
						                        	'<a class="xd" href='+window.getRootPath()+'/custproduct/placeOrder.net?fid='+ev.fid+'>下单</a><br /><br />'+
						                        	'<a class="zc" href="javascript:void(0);"  onclick="onclickImg();">暂存</a><br /><br />'+
						                            '<a class="a1" href="javascript:void(0);"  onclick="cancelCommon(\''+ev.fid+'\');">取消常用</a>'+
						                        '</td>'+
								    	  '</tr>'
								    ].join('');						
								}else if(ev.fiscommon==false){
									html =[
								    		'<tr height="170" class="alltr">'+
								    	  		'<td class="td1" width="510" height="170">'+
								    	  				'<p><input type="checkbox" class="ch_box"/><input  type="hidden" name="fid" value="',ev.fid,'"/></p>'+
								    	  				'<p><img src=',ev.pathImg+' style="border:1px solid lightgray;" width="143" height="133"/></p>'+
							                            '<p class="explan">',ev.fname,'<br />',ev.fspec,'</p>'+
								    	  		'</td>'+
								    	  		'<td>',ev.supplierName,'</td>'+
						                        '<td>',ev.balanceqty,'</td>'+
						                        '<td>',ev.flastordertime,'<br />',ev.flastorderfamount,'</td>'+
						                        '<td class="act">'+
						                        	'<a class="xd" href='+window.getRootPath()+'/custproduct/placeOrder.net?fid='+ev.fid+'>下单</a><br /><br />'+
						                        	'<a class="zc" href="javascript:void(0);"  onclick="onclickImg();">暂存</a><br /><br />'+
						                            '<a class="a1" href="javascript:void(0);"  onclick="isCommon(\''+ev.fid+'\');">设为常用</a>'+
						                        '</td>'+
								    	  '</tr>'
								    ].join('');	
								}
							    $(html).appendTo("#titleTool");

							});
							/********************************************循环添加TR结束******************************/
							kkpager.pno =response.pageNo;
							kkpager.total =Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
							kkpager.totalRecords =response.totalRecords;
							kkpager.generPageHtml({
								click : function(n){
									window.gridCustproductTable(n);
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
						}
						layer.close(loadIndex); 
					}
			});
	}
	
	$(document).ready(function(){
		$(".btn1").hover(function(){$(".btn1").css("color","red");},function(){$(".btn1").css("color","");});
		$(".btn2").hover(function(){$(".btn2").css("color","red");},function(){$(".btn2").css("color","");});
		$(".btn3").hover(function(){$(".btn3").css("color","red");},function(){$(".btn3").css("color","");});
	    $(".left2").css("background-color","#CC0000");
		//加载列表
			window.gridCustproductTable(1);
		$("#searchCustproductQuery").click(function() {
			window.gridCustproductTable(1);
		});
		
		
	});
	
	
	//新建窗口
	function create(){
			layer.open({
			    title: ['新建','background-color:#CC0000; color:#fff;'],
			    type: 2,
			    anim:true,
			    area: ['300px', '200px'],
			    content: "${ctx}/custproduct/create.net"
			});
	}
	//修改窗口
	function edit(){
			if(getIds()==""){
				jNotify("注意：请先选择<strong>产品</strong>",{VerticalPosition : 'center',HorizontalPosition : 'center'});
			}else if ($('input:checkbox[class=ch_box]:checked').next().length >1){
				jNotify("注意：每次只能选<strong>1</strong>条信息",{VerticalPosition : 'center',HorizontalPosition : 'center'});
			}else{
				layer.open({
				    title: ['修改','background-color:#CC0000; color:#fff;'],
				    type: 2,
				    anim:true,
				    area: ['300px', '200px'],
				    content: "${ctx}/custproduct/edit.net?"+getId()
				});
			}
	}
	
	//删除
	function del(){
			if(getIds()==""){
				 jNotify("注意：请先选择<strong>产品</strong>",{VerticalPosition : 'center',HorizontalPosition : 'center'});
			}else{
				 $.ajax({
						type : "POST",
						url : "${ctx}/custproduct/delete.net",
						data : getIds(),
						success : function(response) {
							if (response == "success") {
								jSuccess("操作成功!",{VerticalPosition : 'center',HorizontalPosition : 'center'});
								window.gridCustproductTable(1);
							}else {
								jError("操作失败!",{VerticalPosition : 'center',HorizontalPosition : 'center'});
							}
						}
				  });
			}
	}
	//全选-反选
	function sel(css){
		var a=document.getElementsByClassName(css);
		if(document.getElementById("checkall").checked){
			for(var i = 0;i<a.length;i++){
				  if(a[i].type == "checkbox") a[i].checked = true;
			}
		}else{
			for(var i = 0;i<a.length;i++){
				if(a[i].type == "checkbox") a[i].checked = false;
			}
		}
	}
	//将选中对象的流水号拼接
	function getIds() {
		var paramStr = '';
		$('input:checkbox[class=ch_box]:checked').next().each(function(i){
			if (i == 0) {
				paramStr += 'ides=' + $(this).val();
			}else{
				paramStr += "&ides=" + $(this).val();
			}
		});
		return paramStr;
	}

	//将选中对象的流水号拼接
	function getId() {
		var paramStr = '';
		$('input:checkbox[class=ch_box]:checked').next().each(function(i){
			if (i == 0) {
				paramStr += 'id=' + $(this).val();
			}
		});
		return paramStr;
	}
	function setJudgeMark(obj,a,b){
		$(".left"+obj).css("background-color","#CC0000");
		$("#goods"+obj).css("color","white");
		$(".left"+a).css("background-color","white");
		$("#goods"+a).css("color","#999");
		$(".left"+b).css("background-color","white");
		$("#goods"+b).css("color","#999");
		if(obj==2){
			$("#judgeMark").val(1);
		}else if(obj==3){
			$("#judgeMark").val(2);
		}else if(obj==4){
			$("#judgeMark").val(3);
		}
		window.gridCustproductTable(1);
	}
	
	</script>
</head>

<body>
	<div id="nav">
        <div id="container">
        	<div class="c_top">
                <p class="left2"><a href="javascript:void(0);" onclick="setJudgeMark(2,3,4);"  id="goods2" style="color:#FFFFFF;">全部商品</a></p>
                <p class="left3"><a href="javascript:void(0);" onclick="setJudgeMark(3,2,4);"  id="goods3" style="color:#999;">常用商品</a></p>
                <!--  <p class="left4"><a href="javascript:void(0);" onclick="setJudgeMark(4,2,3);"  id="goods4" style="color:#999;">暂存商品</a></p>-->
                <form id="custproductQueryForm">
                	<input type="hidden" id="judgeMark" name="custproductQuery.judgeMark" value="1"/>
                	<table>
                			<tr>
                				<td class="td1">制造商:</td>
	                        	<td class="m-info-content">
									<select id="supplierId" name="custproductQuery.supplierId" class="lst">
										<option value="0">--请选择--</option>
										<c:forEach var="entry" items="${supplier}">
											<option value="${entry.fid}" id="${entry.fid}" >${entry.fname}</option>
										</c:forEach>
									</select>
								</td>
								<td>
	                                <input type="button" id="searchCustproductQuery" value="搜索"  style="height:25px;width:60px;background-color:#eee;border:1px solid #ccc;cursor:pointer"/>
                                </td>
                			</tr>
                	</table>
                </form>
                <p class="right1"><input type="button" value="新增" class="btn1" onClick="create();"/></p>
                <p class="right2"><input type="button" value="修改" class="btn2" onClick="edit();"/></p>
                <p class="right3"><input type="button" value="删除" class="btn3" onClick="del();"/></p>
            </div>
            <div class="c_buttom">
            	<table id ="titleTool" cellpadding="0" cellspacing="0" width="1260" >
                	<tr  class="tbl_tlt">
                        <td width="510"><p class="_chk"><input id ="checkall" type="checkbox" class="check" onclick="sel('ch_box')"/>&nbsp;&nbsp;全选</p><p class="txt">产品</p></td>
                        <td width="165"><p style="height:25px;line-height:25px;border-left:1px solid lightgray;border-right:1px solid lightgray;">制造商</p></td>
                        <td width="180">库存</td>
                        <td width="180"><p style="height:25px;line-height:25px;border-left:1px solid lightgray;border-right:1px solid lightgray;">最近下单</p></td>
                        <td>操作</td>
                    </tr>
                </table>
            </div>
            <div id="kkpager"></div>
        </div>
        <!--  
        <div id="foot" style="height:40px;width:1280px;">
        	<iframe src="all_foot.html" frameborder="0" scrolling="no" width="1280px" height="40px"></iframe>
        </div>
        -->
	</div>
</body>
</html>
