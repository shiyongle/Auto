<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
<title>子账号管理--一路好运</title>
<link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet"/>
<link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet" />
<link href="${ctx}/pages/pcWeb/css/childAccount_manage.css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/page.cscl.js" ></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/public.js"></script>
<!--<script type="text/javascript" src="${ctx}/pages/pcWeb/js/childAccount_manage.js"></script>-->
</head>
<body>
	<div class="jumbotron main">
		<!--状态-->
		<div class="row">
			<div class="col-sm-12 time_recent" id="state_type">
				<div style="line-height: 30px">状态：</div>
				<div class="time_recent_check">
				<a href="javascript:void(0)" class="active" data-type='0'>全部</a>
				<a href="javascript:void(0)" data-type='1'>使用中</a>
				<a href="javascript:void(0)" data-type='2'>已停用</a>
				</div>
			</div>
		</div>
		
		<!--关键字搜索-->
		<div class="row">
			<div class="col-sm-12 time_recent">
				<form class="form-horizontal">
				<div class="form-group">	
					<div style="line-height: 30px">关键字：</div>
					<div>
      				<input type="text" class="form-control" id="search" name="search" placeholder="搜索全部订单">
    				</div>
					
					</div>
				</form>
			</div>
		</div>
		
		<!--子账号管理数据列表-->
		<div class="table-responsive">
			<table class="table  table-hover" id="childAccount_table"></table>
			<!--页码-->
			<div class="row">					
				<div class="page col-xs-12">
					<div id="PageCode"></div>
				</div>
			</div>
		</div>
		<a href="${ctx}/pages/pcWeb/user_center/childAccount_add.jsp" class="addAccount">+增加账户</a>
		<span>（一个企业账户下可添加10个子账户）</span>
	</div>
<script>
var size=10;//一页显示多少条
var total;//总共多少条记录
var html_table_head='<tr class="table_head">'+
					'<th>用户名</th>'+
					'<th>手机号</th>'+
					'<th>创建日期</th>'+
					'<th>操作</th>'+
					'</tr>';
pageOrderSearch(total,size);//子账号列表分页，传入总数量(total)，每页数量(size)
$(document).ready(function(){
	
//	状态选择,搜索
	$("#state_type a").click(function(){
		$(this).addClass("active").siblings().removeClass("active");//样式切换
		var key=$(this).data("val");
		pageOrderSearch(total,size,key);	
	})
	
//	搜索
	$("#search").blur(function(){
		var key=$(this).val();
		pageOrderSearch(total,size,key);
	})

});


//页码配置
function pageOrderSearch(total,size,key){
	/*页面加载后*/
	$.ajax({
			type:"post",
			url:"${ctx}/pcWeb/user/loadSubNum.do?pagesize="+size+"&pagenum=1"+"&key="+key,
			dataType:'json',			
			success:function(response){	
// 				console.log(response);
				total=response.total;//获取到子账号的总数
				$("#childAccount_table").empty();
				var html=html_table_head;   				
				$.each(response.rows, function(i,e){
					html+='<tr>'+
					'<td>'+e.fname+'</td>'+
					'<td>'+e.vmiUserPhone+'</td>'+
					'<td>'+e.createTimeString+'</td>'+
					'<td>'+
					'<a href="javascript:void(0)" data-username="'+e.fname+'" data-tel="'+e.vmiUserPhone+'" onclick="editAccount(this,'+e.id+')">修改</a>　'+
					'<a href="javascript:void(0)" onclick="delAccount('+e.id+')">删除</a>'+
					'</td>'+
					'</tr>';	
				});
				$("#childAccount_table").append(html);
				getHtmlLoadingAfterHeight();
			}
		});
	
	$("#PageCode").empty().unbind();//移除分页绑定的相关事件
	$("#PageCode").createPage({//分页
        pageCount:Math.ceil(total/size),//向下取整
        current:1,
        backFn:function(p){//回调函数，p为点击的那页
            $.ajax({
    			type:"post",
    			url:"${ctx}/pcWeb/user/loadSubNum.do?pagesize="+size+"&pagenum="+p+"&key="+key,
    			dataType:'json',
    			success:function(response){    				
    				total=response.total;//获取到子账号的总数
    				$("#childAccount_table").empty();
    				var html=html_table_head;   				
    				$.each(response.rows, function(i,e){
    					html+='<tr>'+
    					'<td>'+e.fname+'</td>'+
    					'<td>'+e.vmiUserPhone+'</td>'+
    					'<td>'+e.createTimeString+'</td>'+
    					'<td>'+
    					'<a href="javascript:void(0)" data-username="'+e.fname+'" data-tel="'+e.vmiUserPhone+'" onclick="editAccount(this,'+e.id+')">修改</a>　'+
    					'<a href="javascript:void(0)" onclick="delAccount('+e.id+')">删除</a>'+
    					'</td>'+
    					'</tr>';	
    				});
    				$("#childAccount_table").append(html);
    				getHtmlLoadingAfterHeight();
    			}
    		});
        }
    });	
}

//修改子账号
function editAccount(e,id){
	var tel=$(e).data("tel");
  	var username=$(e).data("username");
	var html='<div class="row">'+
	'<form id="edit_form">'+
	'<div class="col-xs-12">'+
	'<div class="form-inline">'+
	'<div class="form-group myform">'+	
	'<span>　用户名：</span><input type="text" class="form-control" id="user_name" name="vmiUserName" placeholder="" value="'+username+'">'+
		'<a id="user_name_err"  data-placement="right" title="用户名不能为空"></a>'+
	'</div>'+
	'</div>'+
	'</div>'+
	'<div class="col-xs-12">'+	
	'<div class="form-inline">'+
	'<div class="form-group myform">'+
		'<span>手机号码：</span>'+
			'<input type="text" class="form-control" id="phone_number" name="vmiUserPhone" placeholder="" value="'+tel+'">'+
			'<a id="phone_number_err"  data-placement="right" title="手机号码不能为空"></a>'+
			'<a id="phone_number_err2"  data-placement="right" title="手机号码格式错误"></a>'+
	'</div>'+
	'</div>'+
	'</div>'+
	'<div class="col-xs-12">'+
		'<div class="form-inline">'+
		'<div class="form-group myform">'+	
			'<span>　　密码：</span>'+
    				'<input type="text" class="form-control" id="password" name="fpassword" placeholder="">'+
    				'<a id="pass_err"  data-placement="right" title="密码不能为空"></a>'+
		'</div>'+
		'</div>'+
	'</div>'+
	'</form>'+	
'</div>';
	layer.open({		
		  title: '子账号修改',
		  content:html,
		  yes: function(index, layero){//点击确定以后			  		  
			  var user_name=$("#user_name").val();//用户名
			  var phone_number=$("#phone_number").val();//手机号
			  var password=$("#password").val();//密码
			  if(user_name==""||user_name==null){		
					$("#user_name").focus();
					$("#user_name_err").tooltip('show')
					setTimeout(function(){
						$("#user_name_err").tooltip("hide");
					},1000);
					return false;
				}
				if(phone_number==""||phone_number==null){		
					$("#phone_number").focus();
					$("#phone_number_err").tooltip('show')
					setTimeout(function(){
						$("#phone_number_err").tooltip("hide");
					},1000);
					return false;
				}
				var re = /^1[3|4|5|7|8][0-9]\d{4,8}$/;//手机号码正则表达式
				if(!(re.test(phone_number))){
					$("#phone_number_err2").tooltip('show')
					setTimeout(function(){
						$("#phone_number_err2").tooltip("hide");
					},1000);
					return false;
				}
				if(password==""||password==null){		
					$("#password").focus();
					$("#pass_err").tooltip('show')
					setTimeout(function(){
						$("#pass_err").tooltip("hide");
					},1000);
					return false;
				}
			    var result=$("#edit_form").serialize();
			    $.ajax({
			    	type:"post",
			    	url:"${ctx}/pcWeb/user/updateSubNum.do?id="+id,//子账号修改接口			    	
			    	dataType:"json",
			    	data:result,
			    	success:function(response){	
			    		console.log(response);
			    		layer.msg("修改成功");
			    		pageOrderSearch(total,size);
			    	}
			    });
			  }
		});    	
}

//删除子账号
function delAccount(id){
	layer.confirm('确认删除？', {icon: 3, title:'提示'}, function(index){		  
		   $.ajax({
			    	type:"post",
			    	url:"${ctx}/pcWeb/user/deleteSubNum.do?id="+id,//子账号删除接口			    	
			    	dataType:"json",
			    	success:function(response){			    	
			    	if(response.success=="true"){
			    		layer.msg("删除成功！");
			    		layer.close(index);
			    		pageOrderSearch(total,size);
			    	}
			    	}
			    });		  
		});
}
</script>
</body>
</html>
