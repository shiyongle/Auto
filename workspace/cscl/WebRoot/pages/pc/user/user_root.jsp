<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>权限分配</title>
<script type="text/javascript">
	$(document).ready(function() {
				
		//加载时的未分配权限展示
		$('#userRootTreeLeft').tree({    
			//------------------notmymenu 这个参数只显示已分配权限-----------
		    url : '${ctx}/menu/getMenuList.do?urid=${fuserRoleId}&&notmymenu=1',
		    method:"get",
		    animate:true,
		    lines:true,//定义是否显示树控件上的虚线
		}); 
		//加载时的已分配权限展示
		$('#userRootTreeRight').tree({    
			 url : '${ctx}/menu/getMenuList.do?urid=${fuserRoleId}',
		    method:"get",
		    animate:true,
		    lines:true,//定义是否显示树控件上的虚线
		}); 
		
		//分配权限
		$("#treeToRight").click(function(){
			var items = $("#userRootTreeLeft").tree('getSelected');
			if(items){				
			var data = {fmenuitemId:items.id,fuserRoleId:$('#fuserRoleId').val()};
			$.ajax({
	 			type : "POST",
				url : '${ctx}/menu/allotmenu.do',
				data : data,
				success : function(response) {
					if (response == "success") {
                        $.messager.alert('提示', '操作成功', 'info', function() {
                        	$('#userRootTreeLeft').tree('reload');
                        	$('#userRootTreeRight').tree('reload');
                        });
                    } else {
                        $.messager.alert('提示', '操作失败');
                    }
				}	
			});
			}
			else{
				$.messager.alert('提示', '请选择一个菜单记录！', 'info');
			}
		});
		
		//取消权限
		$("#treeToleft").click(function(){
			var items = $("#userRootTreeRight").tree('getSelected');
			if(items){				
			var data = {fmenuitemId:items.id,fuserRoleId:$('#fuserRoleId').val()};
			$.ajax({
	 			type : "POST",
	 			data : data,
	 			url : '${ctx}/menu/unallotmenu.do',
				success : function(response) {
					if (response == "success") {
                        $.messager.alert('提示', '操作成功', 'info', function() {
                          	$('#userRootTreeLeft').tree('reload');
                        	$('#userRootTreeRight').tree('reload');
                        });
                    } else {
                        $.messager.alert('提示', '操作失败');
                    }
				}	
			});
			}
			else{
				$.messager.alert('提示', '请选择一个菜单记录！', 'info');
			}
		});
		
		$("#saveUserRootButton").click(function() {
			if ($("#userRootForm").form("validate")) {
				var params = decodeURIComponent($("#userRootForm").serialize(), true);
				$.ajax({
					type : "POST",
					url : "${ctx}/user/saveUserRoot.do",
					data : params,
					success : function(response) {
						if(response == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#userRoot_Window").window("close");
								$("#userRootTreeLeft").tree('reload');
							});
						}else if(response=="failure") {
	                        $.messager.alert('提示', '操作失败,已有相同规则');
	                    }
						else {
							$.messager.alert('提示', "操作失败！");
						}
					}
				});
			}
		});
	});
</script>
<style>
.treeDiv{margin:10px;padding:15px;overflow:hidden;}
.treeDiv_ul01,.treeDiv_ul02{float:left;}
.treeDiv_ul03{float:right;}
.treeDiv_ul01,.treeDiv_ul03{border:1px solid #ccc;width:200px;height:450px;overflow:auto;}
.treeDiv_ul02{width:130px;height:450px;overflow:hidden;}
.treeDiv_ul02 div{margin:0 auto;margin-top:120px;}
</style>
</head>
	<form id="userRootForm" action="" method="post">
	<input type="hidden" id="fuserRoleId" name="fuserRoleId" value="${fuserRoleId}"/><!--用户的ID-->
	<div class="treeDiv">
	
	<div class="treeDiv_ul01">
	<div style="background:#ddd;text-align:center;height:25px;line-height:25px;">未分配权限</div>
	<ul id="userRootTreeLeft" class="easyui-tree"></ul> 
	</div>
	
	<div class="treeDiv_ul02">
	<div class="Mbutton25" id="treeToRight">→</div>
	<div class="Mbutton25" id="treeToleft">←</div>
	</div>
	
	<div class="treeDiv_ul03">
	<div style="background:#ddd;text-align:center;height:25px;line-height:25px;">已分配权限</div>
	<ul id="userRootTreeRight" class="easyui-tree"></ul> 
	</div>
	
	</div>

	</form>
</html>
