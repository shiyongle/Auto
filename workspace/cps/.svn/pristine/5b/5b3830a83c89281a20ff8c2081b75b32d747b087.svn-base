<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的设计-需求包详情</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/packageDesign.css" />
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<script src="${ctx}/js/packageDesign.js" type="text/javascript"></script>
<script type="text/javascript">
function getFujian(){
		$.ajax({
				type : "POST",
				url : "${ctx}/productfile/loadByParentId.net?id="+"${mand.fid }",
				dataType:"json",
				success : function(response) {
						$.each(response.data, function(i, ev) {
							var  url = ev.fpath;
							var path = url.substring(url.indexOf("/"));
							//var html =  ['<span>',ev.fname,'</span><a href="'+window.getBasePath()+path+'" class="download">下载</a>'].join("");
							var html =  ['<span>',ev.fname,'</span><a href="${ctx}/productfile/downProductdemandFile.net?fid=',ev.fid,'" class="download" target="_blank" >下载</a>'].join("");
							 $(html).appendTo("#mand_fj");
						 }); 
				}
	    });
}

/*** 关闭需求
 * 改为关闭功能
 */
function closeFp(){
	parent.$('#c3d28df961a3c9ebfc7994361031186c').click();
	localStorage.gotoPage = "#tab_list :nth-child(4)";//需求包记录
}

/*** 完成需求*/
function finishFp(){
	$.ajax({
			type : "POST",
			url : "${ctx}/firstproductdemand/finishFp.net?id="+"${mand.fid }",
			dataType:"text",
			success : function(response) {
				if (response == "success") {
						parent.layer.alert('操作成功！', function(alIndex){
						parent.layer.close(alIndex);
						parent.location.href="${ctx}/menuTree/center.net?menu=64d6d70f6f80f70bbe73b62d9cf9b004";
				    });
				}else {
						parent.layer.alert('操作失败！', function(alIndex){
						parent.layer.close(alIndex);
				    });
				}
			}
    });
}

/*** 方案确认*/
function affirm(){
	var schemeDesignIDs="";
	$('input[type="checkbox"]:checked').each(
		function() {
			if(schemeDesignIDs==""){
				schemeDesignIDs="'"+$(this).val()+"'";
			}else{
				schemeDesignIDs=schemeDesignIDs+",'"+$(this).val()+"'";
			}
		}
	);
	if(schemeDesignIDs==""){
			parent.layer.alert('请先选择方案再操作！', function(alIndex){
			    parent.layer.close(alIndex);
	        });
	       return ;
	}
	$.ajax({
			type : "POST",
			url : "${ctx}/firstproductdemand/affirm.net?id="+schemeDesignIDs,
			dataType:"text",
			success : function(response) {
				if (response == "success") {
						parent.layer.alert('操作成功！', function(alIndex){
						    parent.layer.close(alIndex);
						    parent.location.href="${ctx}/menuTree/center.net?menu=64d6d70f6f80f70bbe73b62d9cf9b004";
				       });
				}else {
						parent.layer.alert('操作失败！', function(alIndex){
						    parent.parent.layer.close(alIndex);
				        });
				}
			}
    });
}

/*** 方案列表*/
function getSchemedesign(){
	function leftPad(string, size, character) {
        var result = String(string);
        character = character || " ";
        while (result.length < size) {
            result = character + result;
        }
        return result;
    }
	var fangan = [ "一", "二", "三", "四", "五", "六", "七", "八", "九","十","十一","十二","十三","十四","十五","十六","十七","十八","十九","二十"];
 $.ajax({
		type : "POST",
		url : "${ctx}/schemedesign/loadDetailWithScheme.net?id="+"${mand.fid }&queryHistory="+"${queryHistory}",
		dataType:"json",
		success : function(response) {
				var imageTypes = ['jpg','gif','bmp','png'];
				var htmls = [];
				var isUnaffirm = true;
				var designTime ="";
				$.each(response, function(i, ev) {
					if(ev.Fconfirmed==1){
						isUnaffirm = false;
					}
					if(i==0){
						designTime = ev.time;
					}
						var files = ev.files,imgs=[],list=[];
						$.each(files,function(index,file){
								   if( jQuery.inArray(file.fname.substring(file.fname.lastIndexOf('.')+1).toLowerCase(), imageTypes)>-1){
									   //var path = window.getBasePath()+file.fpath.substring(file.fpath.indexOf("/"));
									   var path = file.fpath.substring(file.fpath.indexOf("/"));
									   imgs.push('<img src="'+path+'" alt="设计图" />');
								   } 
								  // list.push('<li><span>'+file.fname+'</span><a href="'+window.getBasePath()+file.fpath+'" class="download" download="'+file.fname+'" target="_blank">下载</a></li>'); 
								   list.push('<li><span>'+file.fname+'</span><a href="${ctx}/productfile/downProductdemandFile.net?fid='+file.fid+'" class="download"  target="_blank">下载</a></li>'); 
							}); 

						//方案的产品;
						var productlist = ev.productlist||'',pdtlist=[],j=0;
						$.each(productlist,function(index,product){
							j = j+1;
							pdtlist.push(
									/* '<li><span>'+file.fname+'</span><a href="${ctx}/productfile/downProductdemandFile.net?fid='+file.fid+'" class="download"  target="_blank">下载</a></li>' */
									'<tr> <td>',product.productfname,'</td> <td>',product.fmaterialcode,'</td> <td>',product.fspec,'</td> <td>',product.subProductAmount,'</td> </tr>'
							); 
						}); 
						var tpHtml = '' ;
						if(j>=1){
							tpHtml =  '<div id = "pdtlist" class="content3"> <table> <thead> <tr> <th width="208">产品名称</th> <th width="150">材料</th> <th width="150">规格</th>\
				               <th width="284">附件 数/套</th> </tr> </thead> <tbody id = "productfile">'+pdtlist.join("") +'</tbody> </table> </div>';
						}else{
							//方案的特性;
							var schemeEntry = ev.schemeEntry||'';
							$.each(schemeEntry,function(index,entry){
								tpHtml =  '<div class="design"><div class=" pdtEntry"><p><label>产品名称 :</label><span class="and">'+entry.fname +'</span><label>规&nbsp;&nbsp;格 :</label><span> '+entry.fspec +'</span></p> <p><label>材&nbsp;&nbsp;&nbsp;&nbsp;料 :</label>\
								<span class="and"> '+entry.fmaterial +'</span><label>&nbsp;&nbsp;数&nbsp;&nbsp;量 :</label> <span> '+entry.famount +'</span></p> <p><label>交付时间 :</label><span class="and"> '+entry.foverdate +'</span></p></div> </div>';
							});
						}
						
						var n =  i<9?+"0"+(i+1):(i+1);
						
							if(imgs.length==0){
								imgs.push('<img src="" alt="默认图片" />');
							}
							var html =  '<div class="design"> \
					            	<h5 onselectstart="return false;"> \
					                	<div class="bg"></div> \
					                    <span><strong>' + n + '</strong><br/>订单' + fangan[i]+ '</span> \
					                </h5> \
					                <div class="img_query"> <div class="frame">' +imgs.join("") + '</div> \
					                   <span class="left"></span> \
					                    <span class="right"></span> \
					                </div> \
					                <p style="display:none">'+ev.time+'</p> \
					                <img class="best" src="${ctx}/css/images/mascot.png"/> \
					                <div class="files"> \
					                	<span class="file_title">附件：</span> \
					                    <ul> '+list.join("") +'</ul> \
					                    <div class="clear"></div>  \
					                </div>  \
					                <label style="display:none;" class="choose"><input type="checkbox" value="'+ev.schemdesignID+'"  data-index="'+ev.Fconfirmed+'"/>&nbsp;选择订单</label> \
					            </div>';
					            if(tpHtml != '' && tpHtml.length>0){
					            	html =  html + tpHtml;
					            }
            					htmls.push(html); 
				 }); 
				/* htmls.push('<a href="#" class="btn" id="backEdit" onclick = "backEdit() ">退回修改</a><a href="#" id="affirm"  class="btn" onclick = "affirm()">确 认</a>'); 
				htmls.push('<a href="#" id="affirm"  class="btn" onclick = "affirm()">确 认</a>');*/
				$(htmls.join('')).appendTo("#schemedesign");
				$('.img_query').each(toggleImg);
				window.getHtmlLoadingAfterHeight();
				$('.design input[type=checkbox]').change(function(){
					var me = $(this);
					me.parents('.design').toggleClass('selected',!!me.prop('checked'));
				});
				if(isUnaffirm && designTime !=""){
					/* $("#affirm").show();
					chooseDesign(); */
				}else{
					$('.design').find('input[type=checkbox]').filter('input[data-index=1]').attr('checked',true).parents('.design').addClass('selected');
					/*$('h5').css('cursor','default').find('a').css('display','none');
					 $("#backEdit").hide(); */
					$("#affirm").hide();
				}
				if(designTime !=""){
					$("#ysjState").text(designTime);
					$("#ysj").addClass('done');
					$("#ysj em").text("已设计");
				}
		}
	}); 
 window.getHtmlLoadingAfterHeight();
}

/*** 产品档案*/
function getfirstpdProduct(obj){
	$.ajax({
		type : "POST",
		url : "${ctx}/custproduct/getProductWithfp.net?id="+"${mand.fid }",
		dataType:"json",
		success : function(response) {
			var hasProductTemp = 0;
			$.each(response, function(i, ev) {
				hasProductTemp += 1;
				var files = ev.files,list=[];
				$.each(files,function(index,file){
				   //list.push('<li><span>'+file.fname+'</span><a href="'+window.getBasePath()+file.fpath+'" class="download">下载</a></li>');
					 list.push('<li><span>'+file.fname+'</span><a href="${ctx}/productfile/downProductdemandFile.net?fid='+file.fid+'" class="download" target="_blank" >下载</a></li>');
			});
			var html = ['<tr>',
			            '                    	<td>',ev.productfname,'</td>',
			            '                        <td>',ev.fmaterialcode,'</td>',
			            '                        <td>',ev.fspec,'</td>',
			            '                        <td>',ev.subProductAmount,'</td>',
			            '                    </tr>',
			            '                    <tr>',
			            '                    	<td colspan="4" class="files">',
			            '                        	<span class="file_title">附件：</span>',
			            '                            <ul>',
			            list.join(""),
			            '                            </ul>',
			            '                        </td>',
			            '                    </tr>'].join("");
			 $(html).appendTo("#productfile");
			 
			 }); 
			/* if(obj==2 && hasProductTemp>0){
				$("#finishFp").show();
			} */
			if(hasProductTemp==0){
				$("#product").hide();
				$("#pdtlist").hide();
			} 
			window.getHtmlLoadingAfterHeight();
		}
});
}

//状态修改
function stateUpdate(){
	var obj = {'已分配':0,'已发布':0,'已接收':1,'已设计':2,'已完成':3,'已生成要货':3,'关闭':3};
	var list = $('.status li');
	var a = obj["${mand.fstate}"];
	if(a==undefined){
		 a = 0;
		 list.eq(0).addClass('done');
	}
	if(a>=1){
		$("#yjs em").text("已接收");
		list.eq(1).addClass('done');
	}
	if(a>=2){
		list.eq(1).addClass('done');
	}
	/* if(a>=3){
		$("#last em").text("已完成");
		list.eq(3).addClass('done');
	} */
	
	if(a!=2){
		//$("#backEdit").hide();
		$("#affirm").hide();
	}
	
	$("#finishFp").hide();
	
	return a;
}
function winScrollEvent(){
	var bar = $('.main_head'),
		oTop = bar.offset().top,
		iTop = top.$('#iframepage').offset().top;
	$(top).scroll(function(){
		var st = this.document.body.scrollTop || this.document.documentElement.scrollTop;
		if(st>(iTop+oTop)){
			if(bar.css('position')!='fixed'){
				bar.css({
					position: 'absolute'
				});
			}
			bar.css('top',(st-iTop)+'px');
		}else if(bar.css('position')!='static'){
			bar.css('position','static');
		}
	});
}
function tabScrollEvent(){
	var doms = $('.ctitle'),
		iTop = top.$('#iframepage').offset().top,
		win = top,
		height = $('#main_nav').outerHeight();
	$('#main_nav a').click(function(){
		var me = $(this);
		if(!me.hasClass('active')){
			me.addClass('active').siblings().removeClass('active');
		}
		win.$('html,body').stop(true).animate({
			scrollTop: iTop + doms.eq(me.index()).offset().top - height
		},500);
		/* console.log(me.index()); */
	});
}
$(document).ready(function(e) {
	  window.getHtmlLoadingBeforeHeight();
	  var state = stateUpdate();//状态修改
	  getFujian(); //加载需求附件
      getSchemedesign();//方案列表
      winScrollEvent();
      tabScrollEvent();
      /* getfirstpdProduct(state); //产品档案及附件*/
 });
</script>
</head>

<body class="noselect" onselectstart="return false;">
	<div id="header">
    	<p>
        	<a href="#">平台首页</a> &gt; <a href="#">我的业务</a> &gt; <a href="#">快速下单</a> &gt; <a href="#">订单详情</a>
        </p>
        <a href="#" class="header_btn" onclick = "closeFp()">关闭</a>
        <a href="#" id="finishFp" class="header_btn" onclick = "finishFp()">完成需求</a>
    </div>
    <div id="status_wrap">
    	<ul class="status">
            <li class="done"><em>提交需求</em><span>${mand.fauditortimeString} </span></li>
            <li id="yjs"><em>未接收</em><span id = "yjsState">${mand.freceivetimeString}  </span></li>
            <li id="ysj"><em>未设计</em><span id = "ysjState">  </span></li>
            <%-- <li id = "last" class="last"><em>未完成</em><span> ${mand.foverdateString} </span></li> --%>
            <div class="clear"></div>
        </ul>
    </div>
    <div id="main">
    	<div class="main_head" style="width:1070px;background:#fff;z-index:9999">
	        <p id="main_nav">
	   			<a href="javascript: void(0)" class="active tab1">需求信息</a>
	   			<a href="javascript: void(0)" class="tab2">方案设计</a>
	   		</p>
	   		<p class="designer">
	   			设计师：${mand.freceiver}　　　联系电话：${mand.freceiverTel }　　
	   			<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&amp;uin=${mand.fqq}&amp;site=qq&amp;menu=yes"><img border="0" height="25px" src="${ctx}/css/images/QQ.png" alt="点击这里给我发消息" title="点击这里给我发消息"></a>
	   		</p>
        </div>
        <h3 class="ctitle">需求信息<br/><span>${mand.fname}</span></h3>
         <div class="content1">
            <p><label>订单编号 :</label><span class="and"> ${mand.fnumber }</span><label>需求包名称 :</label><span> ${mand.fname }</span></p>
            <p><label>采购单号 :</label><span class="and"> ${mand.fpurordernumber }</span><label>制造商 :</label><span> ${mand.supplierName }</span></p>
            <p><label>提交时间 :</label><span class="and"> ${mand.fauditortimeString }</span><label>交付时间：</label><span> ${mand.farrivetimeString} </span></p>
            <p><label>具体要求 :</label><span>  ${mand.fdescription} </span></p>
            <p id="mand_fj"><label>附件：</label></p>
        </div>
        <h3 class="ctitle">方案设计<br/><span>The project design</span></h3>
        <div id = "schemedesign" class="content2">
        	<%-- <em>
            	<img src="${ctx}/css/images/figure.jpg"/>
            </em>
            <p>
            	设计师：<span>  ${mand.freceiver} </span>
            	<a class="qq" title="点击这里给我发消息" target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${mand.fqq}&site=qq&menu=yes"></a>
            </p>
           <p>联系电话：<span> ${mand.freceiverTel } </span></p> --%>
        </div>
        <!-- <h3 id = "product">产品档案<br/><span>Product archives</span></h3>
        <div id = "pdtlist" class="content3">
        	<table>
            	<thead>
                	<tr>
                        <th width="208">产品名称</th>
                        <th width="150">材料</th>
                        <th width="150">规格</th>
                        <th width="284">附件 数/套</th>
                    </tr>
                </thead>
                <tbody id = "productfile">
                </tbody>
            </table>
        </div> -->
    </div>
</body>
</html>
