<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="${ctx}/css/kkpager.css"  />
	<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
	<script type="text/javascript" language="javascript" src="${ctx}/js/kkpager.js"></script>
	<title>个人中心-申请认证</title>
	<style>
	*{margin:0px auto;padding:0px;}
	#nav{width:1085px;height:700px;margin-top:10px;}
	#nav_menu{height:45px;width:1060px;padding-left:20px;}
	#nav_menu a{text-decoration:none;}
	#nav_menu input{width:70px;height:45px;line-height:45px;background-color:white;border:none;font-family:"微软雅黑";font-size:16px;cursor:pointer;color:#aaa;}
	#nav_menu input:hover{color:#C00;}
	#nav_data{height:345px;width:1080px;}
	#nav_data table{border-collapse:collapse;text-align:center;color:#666666;font-family:"微软雅黑";font-size:14px;}
	#nav_data .choice{width:20px;height:20px;}
	#nav_data .otherRow{border-bottom:1px solid lightgray;}
	._color{background-color:#e9f7ff;}
	.setting{border:none;background-color:#C00;color:white;height:25px;width:70px;font-family:"微软雅黑";cursor:pointer;}
	#fdefault{font-family:"微软雅黑";line-height:25px;color:#C00;}
	#kkpager{width:1080px;height:80px;text-align:center;line-height:80px;}
</style>
<script type="text/javascript">
function gridUaddressTable(page){
var loadDel = layer.load(2);
   		$.ajax({
				type : "POST",
				url : "${ctx}/address/load.net?pageNo="+page+"&pageSize="+5,
				dataType:"json",
				//data:obj,
				success : function(response) {
					$(".otherRow").remove();
					  $.each(response.list, function(i, ev) {
						  var  html =[
						  			  '<tr height="60" class="otherRow">',
										  '<td><input type="checkbox" class="choice"/><input  type="hidden" name="fid" value="',ev.fid,'"/></td>',
										  '<td>',ev.flinkman,'</td>',
										  '<td>',ev.fdetailaddress,'</td>',
										  '<td>',ev.fphone,'</td>',
										  '<td id="feffect">',ev.feffect,'</td>',
										  '<td id="fdefault">',ev.fdefault,'</td>',
									  '</tr>'
									  ].join("");
						   $(html).appendTo("#addressTool");
						});
						$("#addressTool tr #feffect").each(function(i) {
			                var t = $(this).text();
			                if(t==0){
			                	$(this).text('禁用');
			                }else if(t==1){
			                	$(this).text('启用');
			                }
            		    });
            		    $("#addressTool tr #fdefault").each(function(i) {
			                var t = $(this).text();
			                if(t==1){
			                	$(this).text('默认地址');
			                }else if(t==0){
			                	$(this).text("");
			                }
            		    });
						$(".otherRow").mousemove(function(){
						   var id = $(this).children("td:first").children().last().val();
							$(this).addClass("_color").siblings().removeClass("_color");
							if($(this).children().last().html()==""){
								var tdhtml =['<input type="button" value="设为默认" class="setting" onclick="do_click(\''+id+'\')"/>'].join("");
								$(this).children().last().html(tdhtml);
								//$(this).children().last().html("<a class='a1' href='javascript:void(0);' class='setting' onclick="+do_click(id)+">设为默认</a>");
								
							}
						});
						$(".otherRow").mouseleave(function(){
							$(this).removeClass("_color");
							if($(this).children().last().text()!="默认地址"){
								$(this).children().last().html("");
							}
						});
						/********************************************循环添加TR结束******************************/
						kkpager.pno =response.pageNo;
						kkpager.total =Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
						kkpager.totalRecords =response.totalRecords;
						kkpager.generPageHtml({
							click : function(n){
									window.gridUaddressTable(n);
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
					layer.close(loadDel); 
				}
		});
}

$(document).ready(function () {
	window.gridUaddressTable(1);
});

//全选
function selectCheckBoxAddr(css){
		var a=document.getElementsByClassName(css);
		if(document.getElementById("checkaddr").checked){
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
	$('input:checkbox[class=choice]:checked').next().each(function(i){
		if (i == 0) {
			paramStr += 'ids=' + $(this).val();
		}else{
			paramStr += "&ids=" + $(this).val();
		}
	});
	return paramStr;
}
//将选中对象的流水号拼接
function getId() {
	var paramStr = '';
	$('input:checkbox[class=choice]:checked').next().each(function(i){
		if (i == 0) {
			paramStr += 'id=' + $(this).val();
		}
	});
	return paramStr;
}
/*** 启用/禁用*/
function isEnable(obj){
	var valuelast = '';
	$('input:checked').parent().parent().each(function(i){
			if (i == 0) {
				valuelast += $(this).children().last().text();
			}else{
				valuelast += "," + $(this).children().last().text();
			}
	});
	if(getIds()==""){
		layer.msg('请先选中记录！');
		return;
	}else if(valuelast.indexOf("默认地址")>=0 && obj==0 ){
		layer.msg('默认地址不可禁用！');
		return;
	}else{
	    var execlload = layer.load(2);
		$.ajax({
			type : "POST",
			url : "${ctx}/address/isEnabled.net?"+getIds(),
			data:{"isenable":obj},
			success : function(response) {
				if (response == "success") {
					layer.alert('操作成功！', function(index){
								layer.close(index);
								layer.close(execlload);
								window.gridUaddressTable(1);
							});
				}else{
					layer.alert('操作失败！', function(index){layer.close(index);layer.close(execlload);});
				}
			}
		});
	}
}
//新建窗口
function create(){
	layer.open({
	    title: ['新建','background-color:#CC0000; color:#fff;'],
	    type: 2,
	    anim:true,
	    area: ['500px', '200px'],
	    content: "${ctx}/address/create.net"
	});
}
//修改窗口
function edit(){
		if(getIds()==""){
			layer.msg('请先选择修改记录！');
		}else if ($('input:checkbox[class=choice]:checked').next().length >1){
			layer.msg('注意：每次只能选<strong>1</strong>条信息');
		}else{
			layer.open({
			    title: ['修改','background-color:#CC0000; color:#fff;'],
			    type: 2,
			    anim:true,
			    area: ['500px', '200px'],
			    content: "${ctx}/address/edit.net?"+getId()
			});
		}
}

//删除
function del(){
		if(getIds()==""){
			layer.msg('请先选择修改记录！');
		}else{
			 $.ajax({
					type : "POST",
					url : "${ctx}/address/delete.net",
					data : getIds(),
					success : function(response) {
						if (response == "success") {
							layer.msg('操作成功');
							window.gridUaddressTable(1);
						}else {
							layer.msg('操作失败');
						}
					}
			  });
		}
}

function do_click(obj){
	$.ajax({
			type : "POST",
			url : "${ctx}/address/isDefault.net",
			data :{"id":obj},
			success : function(response) {
				if (response == "success") {
					layer.msg('操作成功');
					window.gridUaddressTable(1);
				}else {
					layer.msg('操作失败');
				}
			}
	});
}
</script>
</head>

<body>
	<div id="nav">
    	<div id="nav_menu">
        	<a href="javascript:void(0);"><input type="button" value="启用" onclick="isEnable(1)"/></a>
            <a href="javascript:void(0);"><input type="button" value="禁用" onclick="isEnable(0)"/></a>
            <a href="javascript:void(0);"><input type="button" value="新增" onclick="create()"/></a>
            <a href="javascript:void(0);"><input type="button" value="修改" onclick="edit()"/></a>
            <a href="javascript:void(0);"><input type="button" value="删除" onclick="del()"/></a>
        </div>
        <div id="nav_data">
        	<table id="addressTool" cellpadding="0" cellspacing="0" border="0" width="1080">
            	<tr height="40" style="background-color:#f2f2f2;">
                    <td width="40"><input id="checkaddr" type="checkbox" class="choice" onclick="selectCheckBoxAddr('choice')"/></td>
                    <td width="150">联系人</td>
                    <td width="480"><p style="height:25px;line-height:25px;border-left:1px solid lightgray;border-right:1px solid lightgray;">收货地址</p></td>
                    <td width="194">联系电话</td>
                    <td width="120"><p style="height:25px;line-height:25px;border-left:1px solid lightgray;border-right:1px solid lightgray;">状态</p></td>
                    <td>&nbsp;</td>
            	</tr>
            </table>
        </div>
        <div id="kkpager"></div>
    </div>
</body>
</html>