<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客户管理</title>
<script type="text/javascript" language="javascript" src="<c:url value='/js/_common.js'/>"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/kkpager.css"  />
<script type="text/javascript" language="javascript" src="${ctx}/js/kkpager.js" ></script>
<style type="text/css">
*{
	padding:0px;
	margin:0px;
}
.customer{
	width:1070px;
	height:auto;
	font:12px 宋体;
}
.title{
    padding-top: 10px;
    padding-left: 10px;
    padding-right: 10px;
	background-color:#f1f1f1;
	height:20px;
	font:12px 宋体;
}
.button{
	margin:10px;
}
.button input[type=text]{
/* 	margin:10px 15px 10px 150px; */
	width:230px;
	height:25px;
	border:1px solid lightgray;
	padding-left:5px;
    outline: none;
    line-height:25px;
}
.button input[type=button]{
	width:80px;
	height:27px;
}
.button a,.contenTr a{
    display: inline-table;
    text-decoration: none;
    height: 20px;
    width: 80px;
    border: 1px solid lightgray;
    color: black;
    text-align: center;
    line-height: 20px;
    cursor: pointer;
    margin: 3px;
}
.button a:hover,.contenTr a:hover{
	color:#fff;
	background-color:red;
}
.conten table{
    border-collapse: collapse;
    text-align: center;
    font-size: 12px;
    border:1px;
    width:1050px;
    margin-left:10px;
}
.titleTr{
	height:35px;
	background-color:#F0F0F0;
	color:#545454;
}
.tdTitle{
	border-left: 1px solid #ccc;
    border-right: 1px solid #ccc;
}
.contenTr{
	border:1px solid lightgrey;
	margin-top:10xp;
}
#kkpager{
    float: right;
    margin-right: 25px;
     height:40px; 
}
</style>
<script type="text/javascript">
	$(this).ready(function(){
		getCustomerList(1);
		$('.button input[type=button]').click(function(){
			getCustomerList(1);
		});
		$('.button input[type=text]').keypress(function(e){
			if(e.keyCode==13){
				getCustomerList(1);
			}
		});
		$(".titleTr input[type=checkbox]").click(function(){	
			$('.data input[type=checkbox]').attr('checked',$(this).is(":checked"));
		});
	});
	//列表加载数据
	function getCustomerList(page){
		var obj = $(".button input[type=text]").val();
		$.ajax({
			url:'${ctx}/customer/getMyCustomersList.net?pageNo='+page,
			data:{'filter':obj},
			dataType:'json',
			success:function(response){
				var obj = response;
				$('.data').remove();
				if(obj.list){
					$.each(obj.list,function(i,e){
						var d = new Date(e.fcreatetime);
						var html = '<tr height="5px" class="data"></tr>'+
							'<tr class="contenTr data">'+
							'<td><input type="checkbox" data-fid="'+e.fid+'"/></td>'+
							'<td>'+e.fname+'</td>'+
							'<td>'+e.flinkman+'</td>'+
							'<td>'+e.fphone+'</td>'+
							'<td>'+e.addressname+'</td>'+
							'<td>'+d.getFullYear()+"-"+eval(d.getMonth()+1)+"-"+d.getDate()+'</td>'+
							'<td>'+e.fdescription+'</td>'+
							'<td>'+
								'<a onclick="editCustomer(\''+e.fid+'\')">修改</a>'+
								'<a onclick="delCustomer(\''+e.fid+'\')">删除</a>'+
// 								'<a>管理地址</a>'+
							'</td>'+
						'</tr>';
						$('.conten table').append(html);
					});
					/********************************************循环添加TR结束******************************/
					kkpager.pno =response.pageNo;
					kkpager.total =Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
					kkpager.totalRecords =response.totalRecords;
					kkpager.generPageHtml({
						pagerid : 'kkpager',
						click : function(n){
							window.getCustomerList(n);
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
					//行点击事件选中复选框
					$('.data').click(function(){
						var bool = $(this).find('input[type=checkbox]').attr('checked')?false:true;
						$(this).find('input[type=checkbox]').attr('checked',bool);
					});
					$('.data input[type=checkbox]').click(function(event){
				    	event.stopPropagation();//禁止冒泡
				    });
					window.getHtmlLoadingAfterHeight();
				}else{
					parent.layer.alert(obj.msg);
				}
			}
		});
	}
	function addCustomer(){
		var index = parent.layer.open({
		    type: 2,
		    title:['客户管理新增界面','background-color:red;border-top-left-radius: 10px;border-top-right-radius: 10px;font:18px 微软雅黑;color:#fff'],
		    area: ['730px', '380px'],
		    content: '${ctx}/pages/customer/customer_edit.jsp'//iframe的url
// 		    end:function(index){
// 		    	parent.layer.close(index);
// 		    	parent.layer.msg('保存成功');
// 		    	getCustomerList(1);
// 		    }
		}); 
		parent.layer.style(index,{
			'border-radius':'10px'
		});
	}
	function editCustomer(fid){
		var index = parent.layer.open({
		    type: 2,
		    title:['客户管理新增界面','background-color:red;border-top-left-radius: 10px;border-top-right-radius: 10px;font:18px 微软雅黑;color:#fff'],
		    area: ['730px', '380px'],
		    content: '${ctx}/customer/editCustomer.net?fid='+fid//iframe的url
		}); 
		parent.layer.style(index,{
			'border-radius':'10px'
		});
	}
	function delCustomer(fid){
		fid = fid||'';
		if($.isEmptyObject(fid)){
			var checkbox = $('.data input[type=checkbox]:checked');
			$.each(checkbox,function(i,e){
				fid +=$(this).attr('data-fid');
				if(i<checkbox.length){
					fid += ",";
				}
			});
			if($.isEmptyObject(fid)){
				parent.layer.alert("请选择数据！");
				return false;
			}
		}
		parent.layer.confirm('是否删除？', {
		    btn: ['是','否'] //按钮
		}, function(index){
			$.ajax({
				url:'${ctx}/customer/delCustomer.net',
				type:'post',
				data:{'fid':fid},
				dataType:'json',
				success:function(response){
					if(response.success){
						parent.layer.msg(response.msg);
						getCustomerList(1);
						parent.layer.close(parent.layer.getFrameIndex(window.name));
					}else{
						parent.layer.alert(response.msg);
					}
				}
			});
			parent.layer.close(index);
		}, function(){
		    
		});
		
	}
</script>
</head>
<body>
<div class="customer">
	<div class="title">平台首页  > 客户管理</div>
	<div class="button">
		<a onclick="addCustomer()">新增</a>
		<a onclick="delCustomer()">删除</a>
		<input type="button" value="查询" style="float:right;margin-left: 10px;margin-right: 300px;"/>
		<input type="text" placeholder="可按照客户名称查询" style="float:right;"/>
	</div>
	<div class="conten">
		<table>
			<tr class="titleTr">
				<td width="80px"><input type="checkbox"/></td>
				<td width="120px" class="tdTitle">客户名称</td>
				<td width="120px">联系人</td>
				<td width="120px" class="tdTitle">手机号</td>
				<td width="200px">地址</td>
				<td width="120px" class="tdTitle">建档时间</td>
				<td width="120px" style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">备注</td>
				<td width="120px" class="tdTitle" style="border-right:none;">操作</td>
			</tr>
			<tr height="5px"></tr>
<!-- 			<tr height="20px"></tr> -->
<!-- 			<tr class="contenTr"> -->
<!-- 				<td><input type="checkbox"/></td> -->
<!-- 				<td>南方红</td> -->
<!-- 				<td>ltc</td> -->
<!-- 				<td>13587672606</td> -->
<!-- 				<td>俄罗斯</td> -->
<!-- 				<td>2015-12-25</td> -->
<!-- 				<td>善良劫匪</td> -->
<!-- 				<td> -->
<!-- 					<a>修改</a> -->
<!-- 					<a>删除</a> -->
<!-- 					<a>管理地址</a> -->
<!-- 				</td> -->
<!-- 			</tr> -->
		</table>
		<div id="kkpager"></div>
	</div>
</div>
</body>
</html>