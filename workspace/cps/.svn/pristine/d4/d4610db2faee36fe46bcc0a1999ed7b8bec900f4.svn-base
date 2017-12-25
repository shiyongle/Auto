<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设计师指定</title>
<link type="text/css" rel="stylesheet" href="${ctx}/js/jqwidgets-ver3.9.1/jqx.base.css" />
<script type="text/javascript" src="${ctx}/js/_common.js"></script>
 <script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxcore.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxscrollbar.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxbuttons.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxlistbox.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxcombobox.js" ></script> 
<style>
*{margin:0px auto;padding:0px;font-family:"宋体";}
#nav{width:385px;height:230px;border-radius:15px;overflow:hidden;}
#p_title{width:375px;height:45px;line-height:45px;background-color:#D80C18;color:white;padding-left:10px;*+width:385px;}
.desiginForm #designer{width:240px;height:25px;outline:none;border:1px solid lightgray;}
._button{height:40px;width:90px;background-color:#D80C18;border:none;font-family:"微软雅黑";color:white;font-size:15px;}
.jqx-fill-state-normal{ background: 0; }
.jqx-combobox-input{line-height:27px;}
</style>
</head>

<body>
	<div id="nav" >
    	<p id="p_title">设计师指定</p>
        <form action="#" method="post" class="desiginForm" onSumit="return false;" >
            <table cellpadding="0" cellspacing="0" border="0" width="320" height="100%">
            	<tr height="30">
                	<td colspan="2">&nbsp;<input type="hidden" name="id" value="${firstdemandid}"></td>
                </tr>
                <tr height="50">
                    <td width="85">设计师:</td>
					<td><select id="designerList"
						name="fdesigner">
							<c:forEach var="designer" items="${designer}">
	                        <option value="${designer.fid}"  data-fqq="${designer.fqq}" data-ftel="${designer.ftel}">${designer.fname}</option>			
							</c:forEach>
					</select></td>
				</tr>
                <tr height="120" align="center">
                    <td  colspan="2"><input type=button value="确定" class="_button"/></td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>
<script type="text/javascript">
 $(function(){
	$("#designerList").jqxComboBox({
	    width: 240, 
	    height: 25,
	    animationType:'fade',
		autoDropDownHeight:false,
		keyboardSelection : false,//下拉框键盘上下不触发选择
		autoComplete:true,
		dropDownHeight: 90,
		selectedIndex: -2,
	    searchMode:'contains',
	    renderer: function (index, label, value) {
          var option= $("select[name=fdesigner] option:eq("+index+")");
            var div = '<table style="min-width: 150px;float:left;"><tr><td>姓名：' + label+ '</td></tr><tr>' + (option.data("fqq")?("<td min-width='75px'>qq:"+option.data("fqq")+"</td>"):"")+(option.data("ftel")?("<td min-width='75px'>电话:"+option.data("ftel")+"</td>"):"")+ '</tr></table>';
           return div;
	   }
	});
	if("${selectedDesigner}") $("#designerList").val("${selectedDesigner}");
	 $("#designerList").on('open', function (event) {
		var owner= event.owner;
		if(owner.listBox.items.length<3)
		{
			$("#designerList").jqxComboBox({autoDropDownHeight: true});
		}
		});

		$("._button").on('click', function (event) {
			saveDesginer();
		});
});
 function saveDesginer(data,form,options){
 	if($("#designerList").val()===""||$("#designerList").find("input:hidden").val()===""||$("#designerList").val()!=$("#designerList").find("input:hidden").val())
	{
		layer.msg('请选择设计师',{ time: 1000});
		return false;
	} 
	var params=$(".desiginForm").serialize();
	$.ajax({
		url:"${ctx}/schemedesign/saveDesigner.net",
		type:"post",
		dataType:"json",
		data:params,
		success:function(data){
			if(data.success){
					parent.document.getElementById("iframepage").contentWindow.refreshDemandData();
					parent.layer.closeAll();
			}else{
				parent.layer.alert(!data.msg?"操作失败！":('<div style="text-align:center">'+data.msg+'</div>'), function(index){parent.layer.close(index);});
			}
		},
		error:function (){
			parent.layer.alert('操作失败！', function(index){parent.layer.close(index);});
		}
	});
}  
</script>
