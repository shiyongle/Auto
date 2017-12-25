<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>纸板下单</title>
<link href="${ctx}/css/boardOrder.css" rel="stylesheet" />
<link href="${ctx}/css/selectivity-full.css" rel="stylesheet" />
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/js/_common.js"></script>
<script type="text/javascript" src="${ctx}/js/djSelect.js"></script>
<script type="text/javascript" src="${ctx}/pages/board/js/selectivity-full.js"></script>
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>

<script type="text/javascript">
	var orderInfo = '${orderInfo}',
		materialLimit = '${materiallimit}',
		orderState,custformulas;
	if(orderInfo){
		orderState = 'edit';
		orderInfo = $.parseJSON(orderInfo);
		$.each(orderInfo,function(key,val){
			if(val == null){
				orderInfo[key] = '';
			}
		});
	}else{
		orderState = 'add';
		custformulas = $.parseJSON('${custformula}') || [];
	}
	if(materialLimit){
		materialLimit = $.parseJSON(materialLimit);
	}
	
	/**	*常用材料 */
	function my_materialCom(){
		if("${session.user.fname}" !=""){
			var i = parent.layer.open({
			    title:'',move:false,
			    type: 2,
			    anim:true,
			    area: ['830px', '480px'],
			    //content:  "${ctx}/pages/board/commonFmaterial.jsp"
			    content: "${ctx}/productdef/commonFmaterial.net"
			});
			parent.layer.style(i,{
				'border-radius':'10px'
			});
		}else{
			window.goToSmallLogin();
		}
	}
</script>
<script type="text/javascript" src="${ctx}/pages/board/js/boardOrder.js"></script>
</head>
<body>
	<!-- <div id="header">
    	<p>
        	<a>平台首页</a> &gt; <a>我的业务</a> &gt; <a>纸板订单</a> &gt; <a>纸板下单</a>
        </p>
    </div> -->
    <div id="main">
    	<p id="menu">
    		<a href="#" class="menu1" onclick="my_materialCom()" >新增常用材料</a>
    		<a href="javascript:void(0)" class="menu2" id="addAddress">新增地址</a>
    		<a href="javascript:top.layer.closeAll()">关闭</a>
    	</p>
    	<div class="form">
    		<form id="order_form">
    			<label class="label long"><span class="label_title">客户标签：</span><input name="flabel" id="flabel" class="fl"/></label>
    			<a class="fr" id="delLabel" href="javascript:void(0)">删除标签</a>
    			<label class="label fl normal">
    				<span class="label_title">制造商　：</span>
    				<select id="fsupplierid" name="fsupplierid" class="select">
    					<%-- <c:if test="${supplierid!=null}">
	    					<option value="${supplierid}" checked="checked">${suppliername}</option>
	    				</c:if> --%>
    				</select>
    			</label>
    			<label class="label fr normal">
    				<span class="label_title">材　　料：</span>
    				<select id="fmaterialfid" name="fmaterialfid" class="select">
    					<%-- <c:if test="${materialid!=null}">
	    					<option value="${materialid}" checked="checked">${materialname}</option>
	    				</c:if> --%>
    				</select>
    			</label>
    			<label class="label fl normal">
    				<span class="label_title">箱　　型：</span><select id="fboxmodel" name="fboxmodel" class="select">
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
    			</label>
    			<label class="label fr normal">
    				<span class="label_title">压线方式：</span><select id="fstavetype" name="fstavetype" class="select">
    					<option>不压线</option>
    					<option selected>普通压线</option>
    					<option>内压线</option>
    					<option>外压线</option>
    					<option>横压</option>
    				</select>
    			</label>
    			<div class="fl label disable" id="materialspec">
    				<span class="label_title">下料规格：</span><input name="fmateriallength" id="fmateriallength" placeholder="总长" readonly="readonly"/> X <input name="fmaterialwidth" id="fmaterialwidth" placeholder="总高" readonly="readonly"/>
    				<div class="wrap"></div>
    			</div>
    			<div class="fr label" id="boxspec">
    				<span class="label_title">纸箱规格：</span><input name="fboxlength" id="fboxlength"/> X <input name="fboxwidth" id="fboxwidth"/> X <input name="fboxheight" id="fboxheight"/>
    				<div class="wrap"></div>
    			</div>
    			<label class="label fl normal">
    				<span class="label_title">成型方式：</span>
    				<select id="fseries" name="fseries" class="select">
    					<option selected>连做</option>
    					<option>不连做</option>
    				</select>
    			</label>
    			<label class="label fr amount"><span class="label_title">配送数量：</span><input name="famount" id="famount" placeholder="只"/><input name="famountpiece" id="famountpiece" readonly="readonly" placeholder="片"/></label>
    			<label class="label fl">
    				<span class="label_title">横向公式：</span>
    				<div id="hgongsi" data-token="0">
    					<!-- <input /><span>+长+宽+长+(宽-</span><input /><span>)</span> -->
    				</div>
    			</label>
    			<label class="label fr"><span class="label_title">纵向公式：</span><div id="vgongsi" data-token="0"></div></label>
    			<label class="label fl"><span class="label_title">横向压线：</span><input name="fhline" id="fhline" readonly="readonly"/></label>
    			<label class="label fr"><span class="label_title">纵向压线：</span><input name="fvline" id="fvline" readonly="readonly"/></label>
    			
    			<label class="label fl">
    				<span class="label_title">配送时间：</span>
    				<input type="text" id="farrivetime"  name="farrivetime" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:formatDate()})"/>
				</label>
    			<label class="label fr">
    				<span class="label_title">配送地址：</span>
	    			<select id="faddressid" name="faddressid" class="select">
	    				<%-- <c:if test="${address!=null}">
	    					<option value="${address.fid}" checked="checked">${address.fdetailaddress}</option>
	    				</c:if> --%>
	    			</select>
    			</label>
    			<label class="label long"><span class="label_title">特殊要求：</span><input name="fdescription" id="fdescription" value="${descrition}"/></label>
    			<a class="fr" id="descSetBtn" href="javascript:void(0)">设为默认</a>
    			<div class="clear"></div>
    			<div class="operate">
    				<a href="javascript:void(0)" id="place_order">立即下单</a>
    				<a href="javascript:void(0)" id="add_car">加入购物车</a>
    			</div>
    			<input type="hidden" name="fid" />
    			<input type="hidden" name="fcreatorid" />
    			<input type="hidden" name="fcreatetime" />
    			<input type="hidden" name="fnumber" />
    			<input type="hidden" id="fstate" name="fstate" value="7"/>
    			<input type="hidden" name="fiscreate" />
    			<input type="hidden" name="fiscommonorder" />
    			<input type="hidden" name="layer" id="layer"/>
    		</form>
    	</div>
    </div>
    <script type="text/javascript">
    	getHtmlLoadingBeforeHeight();
    	getHtmlLoadingAfterHeight();
    </script>
</body>
</html>