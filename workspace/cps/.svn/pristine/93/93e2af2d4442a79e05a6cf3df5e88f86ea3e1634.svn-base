<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产品档案-新建纸箱和新建内盒</title>
<link href="${ctx}/css/productRecord_addBox.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/js/_common.js"></script>
<script src="${ctx}/js/jQuery.jqzoom.fdj.js" type="text/javascript" language="javascript"></script>
<script src="${ctx}/js/jquery.imagezoom.min.js" type="text/javascript" language="javascript"></script>
<script src="${ctx}/js/djSelect.js" type="text/javascript"></script>
<script src="${ctx}/pages/productdef/js/productRecord_addBox.js" type="text/javascript"></script>
<script>
	var orderInfo = '${orderInfo}',
		materialLimit = '${materiallimit}',
		viewProduct = '${view}',
		orderState,custformulas;
		
	orderInfo = orderInfo.replace("\r\n", "</br>");
	if(orderInfo){
		orderState = 'edit';
		orderInfo = $.parseJSON(orderInfo);
		$.each(orderInfo,function(key,val){
			if(val == null){
				orderInfo[key] = '';
			} else if(typeof(val) == "string" && val.indexOf("</br>") >= 0){
				orderInfo[key] = orderInfo[key].replace("</br>","\r\n");
			}
		});
	}else{
		orderState = 'add';
		custformulas = $.parseJSON('${custformula}') || [];
	}
	if(materialLimit){
		materialLimit = $.parseJSON(materialLimit);
	}
</script>
</head>

<body>
	<div id="nav">
    	<p id="p_title">平台首页 &gt; 产品档案 &gt; 新建</p>
        <div id="frm">
        	<p class="frm_title">客户: ${customername}</p>
            <!--数据填写-->
            <div class="info">
            	<div class="info_top">
            		<form id="product_info">
            			<input type="hidden" id="fid" name="fid" value="${fid}" />
            			<input type="hidden" id="fcustomerid" name="fcustomerid" value="${fcustomerid}" />
            			<input type="hidden" id="fcustpdtid" name="fcustpdtid" value="${fcustpdtid}" />
	                	<table width="648" height="199" border="0" cellpadding="0" cellspacing="0">
	                    	<tr height="36">
	                        	<td width="67">产品名称</td>
	                            <td width="251"><input type="text" name="fname"/><label>*</label></td>
	                            <td width="69">产品材料</td>
	                            <td width="251"><input type="text" name="fmaterialcode"/><label>*</label></td>
	                        </tr>
	                        <tr height="36">
	                        	<td>产品规格</td>
	                            <td><input type="text" name="fcharacter"/><label>*</label></td>
	                            <td>单　　价</td>
	                            <td><input type="text" name="fprice" id="fprice"/><label>*</label></td>
	                        </tr>
	                        <tr height="100">
	                        	<td>
	                            	<p style="height:35px;line-height:35px;">备　　注</p>
	                                <p style="height:35px;">&nbsp;</p>
	                                <p style="height:35px;">&nbsp;</p>
	                            </td>
	                            <td colspan="3"><textarea class="tara" name="fdescription" placeholder="请输入您需要额外添加的信息"></textarea></td> 
	                        </tr>
	                    </table>
            		</form>
                </div>
                <div class="info_bottom">
                	<ul class="tab_title">
                    	<li class="choice" onclick="checkTab('pagebox',this);" data-form="form_pagebox" id="pageboxTab">纸箱信息</li>
                        <li onclick="checkTab('paddingbox',this);" data-form="form_paddingbox" id="paddingboxTab">内盒信息</li>
                    </ul>
                    <div class="message">
                    	<!--纸箱信息-->
                    	<form id="form_pagebox">
                    		<input type="hidden" name="fisboardcard" value="1" />
	                        <table cellpadding="0" cellspacing="0" border="0" width="615" class="pagebox">
	                        	<tr>
	                            	<td width="70">制 造 商:</td>
	                                <td width="235"><select class="produce" id="fsupplierid" name="fmtsupplierid"><option>ERP功能网站</option></select></td>
	                                <td width="70">材　　料:</td>
	                                <td width="235">
	                                	<input type="text" class="txt" placeholder="请先选制造商，再选材料信息" id="fmaterialfid" name="fmaterialfid" />
	                                	<input type="hidden" name="fqueryname" id="fqueryname"/>
	                                </td>
	                            </tr>
	                            <tr>
	                            	<td>箱　　型:</td>
	                                <td>
	                                	<select class="boxModel" name="fboxmodelid" id="fboxmodel">
	                                		<option value="1" selected>普通</option>
					    					<option value="2">全包</option>
					    					<option value="3">半包</option>
					    					<option value="4">有底无盖</option>
					    					<option value="5">有盖无底</option>
					    					<option value="6">围框</option>
					    					<option value="7">天地盖</option>
					    					<option value="8">立体箱</option>
					    					<option value="0">其它</option>
	                                	</select>
	                                </td>
	                                <td>材料信息:</td>
	                                <td><input type="text" name="fmaterialinfo" id="fmaterialinfo"/></td>
	                            </tr>
	                            <tr>
	                            	<td>下料规格:</td>
	                                <td id="materialspec" class="disable">
	                                	<input type="text" class="num" name="fmateriallength" id="fmateriallength" readonly="readonly"/> × <input type="text" class="num" name="fmaterialwidth" id="fmaterialwidth" readonly="readonly" />
	                                	<div class="wrap"></div>
	                                </td>
	                                <td>压线方式:</td>
	                                <td>
		                                <select id="fstavetype" name="fstavetype" class="pressWay">
					    					<option>不压线</option>
					    					<option selected="selected">普通压线</option>
					    					<option>内压线</option>
					    					<option>外压线</option>
					    					<option>横压</option>
					    				</select>
	                                </td>
	                            </tr>
	                            <tr>
	                            	<td>成型方式:</td>
	                                <td align="left">
	                                	<select id="fseries" name="fseries" class="fseries">
					    					<option selected>连做</option>
					    					<option>不连做</option>
					    				</select>
	                                </td>
	                                <td>纸箱规格:</td>
	                                <td id="boxspec">
	                                	<input name="fboxlength" id="fboxlength" type="text" class="num2"/> × <input name="fboxwidth" id="fboxwidth" type="text" class="num2"/> × <input name="fboxheight" id="fboxheight" type="text" class="num2"/>
	                                	<div class="wrap"></div>
	                                </td>
	                            </tr>
	                            <tr>
	                            	<td>横向公式:</td>
	                                <td id="hgongsi"></td>
	                                <td>纵向公式:</td>
	                                <td align="left" id="vgongsi"></td>
	                            </tr>
	                            <tr>
	                            	<td>横向压线:</td>
	                                <td><input type="text" name="fhformula" id="fhformula" readonly="readonly"/></td>
	                                <td>纵向压线:</td>
	                                <td><input type="text" name="fvformula" id="fvformula" readonly="readonly" /></td>
	                            </tr>
	                            <tr>
	                            	<td>冲版编号:</td>
	                                <td><input type="text" name="fcbnumber" id="fcbnumber" /></td>
	                                <td>印版编号:</td>
	                                <td><input type="text" name="fybnumber" id="fybnumber" /></td>
	                            </tr>
	                            <tr>
	                            	<td>印刷颜色:</td>
	                                <td><input type="text" name="fprintcolor" id="fprintcolor" /></td>
	                                <td>打包方式:</td>
	                                <td><input type="text" name="fpackway" id="fpackway" /></td>
	                            </tr>
	                            <tr>
	                            	<td>工艺流程:</td>
	                                <td><input type="text" name="fworkproc" id="fworkproc" /></td>
	                                <td>&nbsp;</td>
	                                <td>&nbsp;</td>
	                            </tr>
	                        </table>
                        </form>
                    	<!--内盒信息-->
                    	<form id="form_paddingbox" style="display:none;">
                    		<input type="hidden" name="fisboardcard" value="0" />
	                    	<table cellspacing="0" cellpadding="0" border="0" width="615" class="paddingbox">
	                        	<tr>
	                            	<td width="70">采购规格:</td>
	                                <td width="235"><input type="text" class="num3" name="fboxlength" id="fboxlength1"/>×<input type="text" class="num3" name="fboxwidth" id="fboxwidth1"/>×<input type="text" class="num3" name="fboxheight" id="fboxheight1"/></td>
	                                <td width="70">拼　　数:</td>
	                                <td width="235"><input type="text" name="fpinamount" id="pad0" /></td>
	                            </tr>
	                            <tr>
	                            	<td>冲版刀位:</td>
	                                <td><input type="text" name="fcblocation" id="pad1" /></td>
	                                <td>供纸规格:</td>
	                                <td><input type="text" name="fpaperspec" id="pad2" /></td>
	                            </tr>
	                            <tr>
	                            	<td>面纸规格:</td>
	                                <td><input type="text" name="ffacespec" id="pad3" /></td>
	                                <td>瓦楞规格:</td>
	                                <td><input type="text" name="ftmodelspec" id="pad4" /></td>
	                            </tr>
	                            <tr>
	                            	<td>菲林张数:</td>
	                                <td><input type="text" name="ffylinamount" id="pad5" /></td>
	                                <td>印刷颜色:</td>
	                                <td><input type="text" name="fprintcolor" id="pad6" /></td>
	                            </tr>
	                            <tr>
	                            	<td>冲版编号:</td>
	                                <td><input type="text" name="fcbnumber" id="pad7" /></td>
	                                <td>印版编号:</td>
	                                <td><input type="text" name="fybnumber" id="pad8" /></td>
	                            </tr>
	                            <tr>
	                            	<td>打包方式:</td>
	                                <td><input type="text" name="fpackway" id="pad9" /></td>
	                                <td>工艺流程:</td>
	                                <td><input type="text" name="fworkproc" id="pad10" /></td>
	                            </tr>
	                        </table>
                        </form>
                    </div>
                </div>
            </div>
            <!--右边的放大镜效果-->
            <div class="frm_right">
            	<!--...-->
                 <div class="box">
                    <div class="tb-booth tb-pic tb-s310">
                        <a href="javascript:void(0);"><img src="${ctx}/productfile/getDefaultImg.net" rel="${ctx}/productfile/getDefaultImg.net" class="jqzoom" /></a>
                    </div>
                    <div class="spec-scroll"><a class="prev">&lt;</a><a class="next">&gt;</a>
                    <div class="items">
                    	<ul class="tb-thumb" id="thumblist">
                            <!-- <li class="tb-selected"><div class="tb-pic tb-s40"><a href="#"><img src="/cps-vmi/images/01_small.jpg" mid="/cps-vmi/images/01_mid.jpg" big="/cps-vmi/images/8-1.jpg" /></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="#"><img src="/cps-vmi/images/03_small.jpg" mid="/cps-vmi/images/03_mid.jpg" big="/cps-vmi/images/03.jpg"/></a></div></li> -->
                    	</ul>
                    </div>
                    </div>
                </div>
                 <!--...-->
               	<form id="uploadImg" method="post" action="${ctx}/productfile/uploadImg.net" enctype="multipart/form-data" target="fileUL">
               		<input type="hidden" name="fid" value="${fcustpdtid}"/>
                	<a href="javascript:void(0)" class="smart">上传图片<input type="file" name="file" id="fl" onchange="$(this).parents('#uploadImg').submit();"/></a>
                	<iframe name="fileUL" style="display:none;" id="fileUL" src="javascript:void(0);"></iframe>
               	</form>
               	<a href="javascript:void(0)" class="smart" id="deleteImg">删除图片</a>
                <input type="button" value="保存" class="_submit" id="_submit"/>
            </div>
        </div>
    </div>
</body>
</html>
