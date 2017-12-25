<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>档案管理-交期设置</title>
<style>
*{margin:0px auto;padding:0px;font-family:"宋体";}
#nav{width:525px;border-radius:15px;overflow:hidden;}
#p_title{width:515px;height:45px;line-height:45px;background-color:#D80C18;color:white;padding-left:10px;*+width:525px;}
.onlyForm input[type=text]{width:55px;height:25px;outline:none;border:1px solid lightgray;text-align:center;font-weight:bold;margin-right:10px;}
._submit{height:40px;width:90px;background-color:#D80C18;border:none;font-family:"微软雅黑";color:white;font-size:15px;cursor:pointer;border-radius:4px;}
</style>
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
</head>

<body>
	<div id="nav">
    	<p id="p_title">交期设置</p>
        <form action="${ctx}/productdef/setDeliverTimeConfig.net" method="post" class="onlyForm" id="createForm" >
            <table cellpadding="0" cellspacing="0" border="0" width="300">
            	<tr height="30">
                	<td colspan="2">&nbsp;</td>
                </tr>
                <tr height="60">
                <input type="hidden" id="fsupplier" name="fsupplier" value="${fsupplier}"/>
                    <td width="125">最早发货交期:</td>
                    <td><input type="text" name ="fdays" value="${fdays}" >日内可发货</td>
                </tr>
                <tr height="60">
                    <td>默认发货交期:</td>
                    <td><input type="text" name="fdefaultdays" value="${fdefaultdays}" />日内可发货</td>
                </tr>
                <tr height="90" align="center">
                    <td colspan="2"><input type="button" id="saveButton" value="保存" class="_submit"/></td>
                </tr>
            </table>
        </form>
    </div>
</body>
  <script type="text/javascript">
		//2016-01-05 保存制造商交期
		$("#saveButton").click(function() {
				var reg = /^\d+$/;
				var index = parent.layer.getFrameIndex(window.name);
				var url = $("#createForm").attr("action");
				var fdays = $("input[name='fdays']")[0].value;
				var fdefaultdays= $("input[name='fdefaultdays']")[0].value;
				if(!reg.test(fdays)){
					layer.msg('最早发货交期必须为整数');
					return false;
				}
				if(!reg.test(fdefaultdays)){
					layer.msg('默认发货交期必须为整数');
					return false;
				}
				$.post(url,$("#createForm").serialize(),function(data){
				 		if(data=="success"){
				 			layer.alert('操作成功！', function(alIndex){
									layer.close(alIndex);
									parent.layer.close(index);
							});
				 		}else{
				 			layer.alert('操作失败！', function(alIndex){
									layer.close(alIndex);
									parent.layer.close(index);
							});
				 		}
				});
		});
  </script>
</html>
