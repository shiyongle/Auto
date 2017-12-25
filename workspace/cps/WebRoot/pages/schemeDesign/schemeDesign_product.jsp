<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新增产品</title>
<script type="text/javascript" src="${ctx}/js/_common.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/js/webuploader-0.1.5/webuploader.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/pages/schemeDesign/css/schemeDesign_product.css">
<script type="text/javascript" language="javascript" src="${ctx}/js/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="${ctx}/pages/schemeDesign/schemeDesign_product.js"></script>
</head>
<script type="text/javascript">
var productState = 'add';
if(parseInt("${products.size()}")>0) productState = 'edit';
</script>


<body class="noselect" onselectstart="return false;">
	<div id="header">
    	<p>
        	<a href="#">平台首页</a> &gt; <a href="#">我的业务</a> &gt; <a href="#">我的设计</a> &gt; <a href="#">新增产品</a>
        </p>
    </div>
    <div id="main">
    	<h1>新增产品（高端设计）</h1>
    	<div class="cust_sup">
    		<input type="hidden" id="firstdemandfid" value="${demand.fid}"/>
    		<label>客户名称：</label>
    		<input type="hidden" value="${demand.fcustomerid}"/><input class="customer" value="${demand.customer}" readonly="readonly" onfocus="this.blur();" />
    		<label>制造商：</label>
    		<input type="hidden" value="${demand.fsupplierid}"/><input  value="${demand.supplier}" readonly="readonly" onfocus="this.blur();" />
    	</div>
    	<div class="ope_add">
    		新增产品：
    		<div class="plus" id="addProduct">+</div>
    	</div> 
    				   <c:forEach var="product" items="${products}" varStatus="pstatus" >

						<div class="product${products.size()>1&&pstatus.index==0?' parentProduct':''}">
						<form>
					            <div class="product_info">
									 <input type="hidden" name="fid" value="${product.fid}"/>
									 <input type="hidden" name="fparentid" value="${product.fparentid}" />
								 <input type="hidden" name="schemedesignid"  value="${product.schemedesignid}"/>
					                <label>产品名称：</label>
					                <input class="name" name="fname" value="${product.fname}"/>
					                <label>规格：</label>
					                <div class="spec">
				                    <input class="length" placeholder="长/mm" name="fboxlength" value="${product.fboxlength}" /> X <input class="width" placeholder="宽/mm" name="fboxwidth" value="${product.fboxwidth}"/> X <input class="height" placeholder="高/mm" name="fboxheight" value="${product.fboxheight}"/>
					                </div>
					                <label>材料：</label>
					                <input class="material" name="fmaterialcodeid" value="${product.fmaterialcodeid}"/>
					            </div>
					            <div class="product_info" >
					                      <label>产品编码：</label>
					             	      <input class="fnumber" name="fnumber" value="${product.fnumber}"/>
					            </div>
					            <div class="file">
					                 <div class="file_count" ${pstatus.index==0?'style=\"display:none\"':''}>
					                    <span class="file_label">附件数：</span>
					                    <div class="input_group">
					                        <span class="minus">-</span><input  name="subProductAmount" class="amount" value="${product.subProductAmount}"/><span class="plus">+</span>
					                    </div>
					                </div>
					                <div class="ope_add fl"> 添加附件：
					                    <div class="plus" >+</div>
					                </div>
										<div  class="uploader-list fl thelist" >
									    	<c:forEach var="file" items="${product.files}" varStatus="status">
										    	<div id="WU_FILE_EIDT_${status.index}" class="itemafter">
														<p class="infoafter editinfoafter"><a href="${ctx}/productfile/getFileSource2.net?fid=${file.fid}" target="_blank">${file.fname}</a><a id="${file.fid}" class="delfile"  href="javascript:void(0);">&times;</a></p>
														<p class="state" style="display: none;">已上传</p>
														<div class="progress progress-striped active" style="display: none;">
															<div class="progress-bar" style="width: 100%;" role="progressbar"></div>
														</div>
												</div>
											</c:forEach>
										</div>
					                <br class="clear" />
					            </div></form>
					            <a href="javascript:void(0);" class="delete delproduct">&times;</a>
					        </div>
					        </c:forEach>    
        
        <div id="operate">
            <a href="javascript:void(0);" class="fl">确定</a>
            <a href="javascript:void(0);" class="fr">返回</a>
        </div>
    </div>
</body>
</html>
