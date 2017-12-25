<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产品导入</title>
<link type="text/css" rel="stylesheet" href="${ctx}/js/jqwidgets-ver3.9.1/jqx.base.css" />
<script type="text/javascript" src="${ctx}/js/_common.js"></script>
 <script type="text/javascript" language="javascript" src="${ctx}/js/jquery.form.js" ></script>
 <script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxcore.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxscrollbar.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxbuttons.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxlistbox.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxcombobox.js" ></script> 
<style>
*{margin:0px auto;padding:0px;font-family:"宋体";}
#nav{width:425px;height:245px;border-radius:15px;overflow:hidden;}
#p_title{width:415px;height:45px;line-height:45px;background-color:#D80C18;color:white;padding-left:10px;*+width:425px;}
.execlForm #fcustomerid{width:210px;height:25px;outline:none;border:1px solid lightgray;}
._button{height:40px;width:90px;background-color:#D80C18;border:none;font-family:"微软雅黑";color:white;font-size:15px;}
.file-box{z-index:100; position:relative;width:212px;height:26px;} 
.btn{float:left;background-color:#FFF; border:1px solid lightgray;height:27px; width:70px;margin-left:5px;} 
.file{z-index:99; position:absolute; top:0;left:0;height:27px; filter:alpha(opacity:0);opacity: 0;width:210px;line-height:27px; } 
.txt{float:left;width:130px;height:25px;outline:none;border:1px solid lightgray;line-height:25px;} 
.jqx-fill-state-normal{ background: 0; }
.jqx-combobox-input{line-height:27px;}
</style>
</head>

<body>
	<div id="nav" >
    	<p id="p_title">产品上传</p>
        <form action="${ctx}/execldata/saveUploadCustExcelDataToJxlOrPoi.net" method="post" class="execlForm" enctype="multipart/form-data" >
            <table cellpadding="0" cellspacing="0" border="0" width="300">
            	<tr height="30">
                	<td colspan="2">&nbsp;</td>
                </tr>
                <tr height="50">
                    <td width="85">客户:</td>
					<td><select id="selectcustomerList"
						name="fcustomerid">
							<c:forEach var="customer" items="${customer}">
						
							<c:choose>
	                            <c:when test="${customer.fid==customerid}">
	                            <option value="${customer.fid}"  selected = "selected" >${customer.fname}</option>
	                            </c:when>
	                          <c:otherwise>
									<option value="${customer.fid}" >${customer.fname}</option>
								</c:otherwise>
								</c:choose>
							</c:forEach>
					</select></td>
				</tr>
                <tr height="50">
                    <td>上传:</td>
                    <td><div class="file-box">
					<input type='text' name='filepath' id='filepath' class='txt' /> 
					<input type='button' class='btn' value='选择文件' /> 
					<input type="file" name="file" class="file" id="fileField" size="28"  accept=".xls,.xlsx" onchange="document.getElementById('filepath').value=this.value" unselectable="on" /> </td>
					</div>
                </tr>
                <tr height="70" align="center">
                <td></td>
                    <td align="right" ><input type="button" value="模板下载" class="_button tempdown"/>&nbsp;<input type=button value="上传" class="_button upfile"/></td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>
<script type="text/javascript">
$(function(){
	$("#selectcustomerList").jqxComboBox({
	    width: 210, 
	    height: 25,
	    animationType:'fade',
		autoDropDownHeight:false,
		keyboardSelection : false,//下拉框键盘上下不触发选择
		autoComplete:true,
		dropDownHeight: 100,
	    searchMode:'contains'//模糊搜索,
	   });
	$("#selectcustomerList").on('open', function (event) {
		var owner= event.owner;
		if(owner.listBox.items.length<4)
		{
			$("#selectcustomerList").jqxComboBox({autoDropDownHeight: true});
		}
		});
		$(".tempdown").on('click', function (event) {
		window.open("${ctx}/productdef/downtempproduct.net","下载模板");
		});
		$(".upfile").on('click', function (event) {
			var loadIndex = layer.load(2);
		 	var options={
					url:"${ctx}/execldata/saveUploadCustExcelDataToJxlOrPoi.net",
					type:"post",
				 	beforeSubmit:validateinfo,
				 	parentobj:loadIndex,
				 	success: function(data){
				 		layer.close(loadIndex);
				 		if(data.success ===true){
				 			   layer.alert(data.msg, function(index){
				 				parent.parent.document.getElementById("iframepage").contentWindow.loadData(1);//刷新数据
							 	 parent.layer.closeAll();//关闭所有层
				 				});
				 		}else{
				 			layer.msg(!data.msg?"导入失败！":('<div style="text-align:center">'+data.msg+'</div>'));
				 		}
				 	},
				  	dataType:"json"	,
				  	 error: function(XmlHttpRequest){  
				  		layer.close(loadIndex);
				  		layer.msg("导入失败!");
	                   } 
			};
			$(".execlForm").ajaxSubmit(options); 
		});
});
function validateinfo(data,form,options){
	layer.close(options.parentobj);
	if($("#selectcustomerList").val()==="")
	{
		layer.msg('请选择客户！',{ time: 1000});
		return false;
	}
	if($("#filepath").val()==="")
	{
		layer.msg('请选择要导入的文件！',{ time: 1000});
		return false;
	}
	   if(!/(\.xls|\.xlsx)$/.test($("#filepath").val()))
	{
		layer.msg('只支持excel导入！',{ time: 1000});
		return false;
	}   
	return true;
}
</script>
